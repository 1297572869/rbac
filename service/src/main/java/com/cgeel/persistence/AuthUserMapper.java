package com.cgeel.persistence;

import com.cgeel.common.mybatis.BaseMapper;
import com.cgeel.model.AuthUser;


import java.util.Map;

import java.util.List;

public interface AuthUserMapper extends BaseMapper<AuthUser> {

     Integer  auth_user_count(Map<String, Object> map);

     List<Map<String, Object>> auth_user_list(Map<String, Object> map);

}
