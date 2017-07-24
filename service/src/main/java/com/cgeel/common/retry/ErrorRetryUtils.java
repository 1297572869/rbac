package com.cgeel.common.retry;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ZXW on 2014/11/12.
 */
public class ErrorRetryUtils {

    private static Logger logger = LoggerFactory.getLogger(ErrorRetryUtils.class);

    /**
     * 错误重试功能 次数不要超过5，否则等待时间会很长很长
     * @param retry 需要实现的方法
     * @param count 次数
     * @param interval 错误等待时间
     * @return
     * @throws Exception
     */
    public static <T, V> V retry(Retry<T, V> retry, int count, int interval, T obj)throws Exception{
        int c = 0;
        Exception ex = null;
        while(c < count) {
            if(c > 0){
                Thread.sleep((interval * (int)Math.pow(10, c-1))/(int)Math.pow(c, 2));
            }
            try {
                return retry.execute(obj);
            } catch (Exception e) {
                ex = e;
            }
            c++;
            logger.warn("重试" + c + "次");
            int code = retry.errorHandle(ex, obj, c);
            if(code == 1){
                return null;
            }
        }
        logger.warn("重试"+c+"次结束，系统未恢复");
        throw ex;
    }

    /**
     * 错误重试功能 等待2秒
     * @param retry 需要实现的方法
     * @param count 次数
     * @return
     * @throws Exception
     */
    public static <T, V> V retry(Retry<T, V> retry, int count, T obj)throws Exception{
        return retry(retry, count, 2000, obj);
    }

}
