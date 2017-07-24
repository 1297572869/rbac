package com.cgeel.common.syn;

import com.cgeel.common.SpringContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by zxw on 2015/9/18.
 */
public class SynchronizationStrategyImpl extends SynchronizationStrategy {

    private String prefix = "SYN:";

    private int timeout = 7200;
    private int waitTimeout = 20;
    private boolean wait = false;
    private StringRedisTemplate stringRedisTemplate;

    public SynchronizationStrategyImpl(){}

    public SynchronizationStrategyImpl(int timeout){
        if(timeout > 0) {
            this.timeout = timeout;
        }else{
            this.timeout = Integer.MAX_VALUE;
        }
    }

    public SynchronizationStrategyImpl(boolean wait){
        this.wait = wait;
    }

    public SynchronizationStrategyImpl(boolean wait, int waitTimeout){
        this.wait = wait;
        if(waitTimeout > 0) {
            this.waitTimeout = waitTimeout;
        }else{
            this.waitTimeout = Integer.MAX_VALUE;
        }
    }

    private boolean canExec(String syn){
        if(stringRedisTemplate == null){
            stringRedisTemplate = SpringContext.getBean(StringRedisTemplate.class);
        }
        long currentTime = System.currentTimeMillis() / 1000;
        boolean bool = stringRedisTemplate.opsForValue().setIfAbsent(syn, String.valueOf(currentTime));
        if(!bool){
            String flag = stringRedisTemplate.opsForValue().get(syn);
            if(StringUtils.isNotBlank(flag)) {
                long updateTime = Long.parseLong(flag);
                if (currentTime > (updateTime + timeout)) {
                    return true;
                } else {
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean beforeExecute(String syn) {
        long first = System.currentTimeMillis()/1000;
        if(wait){
            long next = System.currentTimeMillis()/1000;
            boolean canExec = canExec(prefix+syn);
            while(!canExec && (waitTimeout > (next - first))){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                canExec = canExec(prefix+syn);
                next = System.currentTimeMillis()/1000;
            }
            return canExec;
        }else{
            return canExec(prefix+syn);
        }
    }

    @Override
    public void afterExecute(String syn) {
        stringRedisTemplate.delete(prefix+syn);
    }

    public void setTimeout(int timeout) {
        if(timeout > 0) {
            this.timeout = timeout;
        }else{
            this.timeout = Integer.MAX_VALUE;
        }
    }

    public void setWaitTimeout(int waitTimeout) {
        if(waitTimeout > 0) {
            this.waitTimeout = waitTimeout;
        }else{
            this.waitTimeout = Integer.MAX_VALUE;
        }
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }
}
