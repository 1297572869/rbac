package com.cgeel.service;

import com.cgeel.common.datatable.DataTablePaginator;

import java.util.Map;

public interface AuthFunctionService {

     public DataTablePaginator auth_function_list(Map<String, Object> map);

}
