package com.cgeel.utils;

import com.cgeel.common.rest.exception.RestException;
import com.cgeel.model.Admin;


/**
 * Created with IntelliJ IDEA. User: ZXW Date: 14-4-2 Time: 下午2:47 To change
 * this template use File | Settings | File Templates.
 */
public class UserHolder {

	public static final String ADMIN_USER_SESSION_KEY = "_ADMIN_USER_SESSION_KEY";
	private static ThreadLocal<Admin> tl = new ThreadLocal<>();

	public static Admin getAdmin() {
		return tl.get();
	}

	public static void clear() {
        tl.remove();
	}
	
	public static Integer getUserId() throws RestException {
		Admin ci = getAdmin();
	        if(ci == null || ci.getId() == null){
	            throw RestException.Error(403, "用户未登陆");
	        }
	        return ci.getId();
	}
	
    public static void setAdmin(Admin User){
        tl.set(User);
	}

}
