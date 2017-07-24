package com.cgeel.service;

import com.cgeel.common.datatable.DataTablePaginator;

import java.util.Map;

public interface AuthRoleFunctionService {

     public DataTablePaginator auth_role_function_list(Map<String, Object> map);

}
