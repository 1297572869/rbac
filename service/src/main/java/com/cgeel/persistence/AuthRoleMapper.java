package com.cgeel.persistence;

import com.cgeel.common.mybatis.BaseMapper;
import com.cgeel.model.AuthRole;


import java.util.Map;

import java.util.List;

public interface AuthRoleMapper extends BaseMapper<AuthRole> {

     Integer  auth_role_count(Map<String, Object> map);

     List<Map<String, Object>> auth_role_list(Map<String, Object> map);

}
