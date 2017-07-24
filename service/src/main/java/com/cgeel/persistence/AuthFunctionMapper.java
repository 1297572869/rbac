package com.cgeel.persistence;

import com.cgeel.common.mybatis.BaseMapper;
import com.cgeel.model.AuthFunction;


import java.util.Map;

import java.util.List;

public interface AuthFunctionMapper extends BaseMapper<AuthFunction> {

     Integer  auth_function_count(Map<String, Object> map);

     List<Map<String, Object>> auth_function_list(Map<String, Object> map);

}
