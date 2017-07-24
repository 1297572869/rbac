package com.cgeel.utils;

import com.cgeel.common.rest.exception.RestException;
import com.cgeel.model.User;

/**
 * Created with IntelliJ IDEA. User: ZXW Date: 14-4-2 Time: 下午2:47 To change
 * this template use File | Settings | File Templates.
 */
public class UserHolder {

	public static final String COMMON_USER_SESSION_KEY = "_COMMON_USER_SESSION_KEY";
	private static ThreadLocal<User> tl = new ThreadLocal<>();

	public static User getCommonUser() {
		return tl.get();
	}
	
	public static Integer getUserId() throws RestException {
		User ci = getCommonUser();
	        if(ci == null || ci.getId() == null){
	            throw RestException.Error(403, "用户未登陆");
	        }
	        return ci.getId();
	}

	public static void clear() {
        tl.remove();
	}

    public static void setCommonUser(User User){
        tl.set(User);
	}

}
