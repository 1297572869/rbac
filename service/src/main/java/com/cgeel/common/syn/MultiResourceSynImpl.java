package com.cgeel.common.syn;

import com.cgeel.common.SpringContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zxw on 2015/9/23.
 */
public class MultiResourceSynImpl extends MultiResourceSyn {

    private String prefix = "SYN:";

    private int timeout = 7200;
    private int waitTimeout = 20;
    private boolean wait = false;
    private StringRedisTemplate stringRedisTemplate;

    public MultiResourceSynImpl(){}

    public MultiResourceSynImpl(int timeout){
        if(timeout > 0) {
            this.timeout = timeout;
        }else{
            this.timeout = Integer.MAX_VALUE;
        }
    }

    public MultiResourceSynImpl(boolean wait){
        this.wait = wait;
    }

    public MultiResourceSynImpl(boolean wait, int waitTimeout){
        this.wait = wait;
        if(waitTimeout > 0) {
            this.waitTimeout = waitTimeout;
        }else{
            this.waitTimeout = Integer.MAX_VALUE;
        }
    }

    private boolean canExec(List<String> syn){
        if(stringRedisTemplate == null){
            stringRedisTemplate = SpringContext.getBean(StringRedisTemplate.class);
        }
        long currentTime = System.currentTimeMillis() / 1000;
        Map<String, String> map = new HashMap<>();
        for(String str : syn){
            map.put(prefix + str, String.valueOf(currentTime));
        }
        boolean bool = stringRedisTemplate.opsForValue().multiSetIfAbsent(map);
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
    public boolean beforeExecute(List<String> syn) {
        long first = System.currentTimeMillis()/1000;
        if(wait){
            long next = System.currentTimeMillis()/1000;
            boolean canExec = canExec(syn);
            while(!canExec && (waitTimeout > (next - first))){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                canExec = canExec(syn);
                next = System.currentTimeMillis()/1000;
            }
            return canExec;
        }else{
            return canExec(syn);
        }
    }

    @Override
    public void afterExecute(List<String> syn) {
        List<String> del = new ArrayList<>();
        for(String str : syn){
            del.add(prefix + str);
        }
        stringRedisTemplate.delete(del);
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
