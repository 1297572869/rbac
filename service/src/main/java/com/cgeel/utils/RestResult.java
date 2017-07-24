package com.cgeel.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: ZXW Date: 14-4-2 Time: 下午2:15 To change
 * this template use File | Settings | File Templates.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResult {

	/**
	 * 状态码
	 */
	public int code;
	/**
	 * 时间戳
	 */
	public long expires;

    public String error;

	/**
	 * 返回内容
	 */
	public Map<String, Object> data = new HashMap<String, Object>();

	public RestResult(int code) {
		this.code = code;
		this.expires = System.currentTimeMillis() / 1000;
	}

	public RestResult(int code, String key, Object value) {
		this(code);
		put(key, value);
	}

	public RestResult(int code, Map<String, Object> map) {
		this(code);
		data.putAll(map);
	}

	/**
	 *
	 * @param key
	 * @param value
	 */
	public RestResult put(String key, Object value) {
		data.put(key, value);
		return this;
	}

    public RestResult setErrorMessage(String error){
        this.error = error;
        return this;
    }

	/**
	 *
	 * @param key
	 * @return
	 */
	public Object remove(String key) {
		return data.remove(key);
	}

	/**
	 *200正确
	 *
	 * @return
	 */
	public static RestResult SUCCESS() {
		return new RestResult(200);
	}

	/**
	 * 404错误
	 *
	 * @return
	 */
	public static RestResult ERROR_404() {
		return new RestResult(404);
	}

	/**
	 * 400错误
	 *
	 * @return
	 */
	public static RestResult ERROR_400() {
		return new RestResult(400);
	}

	/**
	 * 500 错误
	 *
	 * @return
	 */
	public static RestResult ERROR_500() {
		return new RestResult(500);
	}

	/**
	 * @description 501操作影响行数为0
	 * @return RestResult
	 * @author don
	 * @time 2014年8月22日 下午4:16:48
	 */
	public static RestResult ERROR_501() {
		return new RestResult(501).put("error", "操作影响行数为0！");
	}

	/**
	 * @description 502传入参数为空，不执行操作
	 * @return RestResult
	 * @author don
	 * @time 2014年8月22日 下午5:06:55
	 */
	public static RestResult ERROR_502() {
		return new RestResult(502).put("error", "传入参数不正确！");
	}

    public static RestResult ERROR(int code){
        return new RestResult(code);
    }
    public RestResult putAll(Map<String,Object> map) {
		data.putAll(map);
		return this;
	}
}
