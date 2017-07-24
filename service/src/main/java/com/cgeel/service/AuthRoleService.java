package com.cgeel.service;

import com.cgeel.common.datatable.DataTablePaginator;

import java.util.Map;

public interface AuthRoleService {

     public DataTablePaginator auth_role_list(Map<String, Object> map);

}
