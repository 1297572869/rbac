package com.cgeel.persistence;

import com.cgeel.common.mybatis.BaseMapper;
import com.cgeel.model.AuthRoleFunction;


import java.util.Map;

import java.util.List;

public interface AuthRoleFunctionMapper extends BaseMapper<AuthRoleFunction> {

     Integer  auth_role_function_count(Map<String, Object> map);

     List<Map<String, Object>> auth_role_function_list(Map<String, Object> map);

}
