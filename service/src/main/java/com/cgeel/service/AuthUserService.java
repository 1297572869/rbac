package com.cgeel.service;

import com.cgeel.common.datatable.DataTablePaginator;

import java.util.Map;

public interface AuthUserService {

     public DataTablePaginator auth_user_list(Map<String, Object> map);

}
