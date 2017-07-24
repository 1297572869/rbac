package com.cgeel.persistence;

import com.cgeel.common.mybatis.BaseMapper;
import com.cgeel.model.AuthUserRole;


import java.util.Map;

import java.util.List;

public interface AuthUserRoleMapper extends BaseMapper<AuthUserRole> {

     Integer  auth_user_role_count(Map<String, Object> map);

     List<Map<String, Object>> auth_user_role_list(Map<String, Object> map);

}
