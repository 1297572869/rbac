package com.cgeel.service;

import com.cgeel.common.datatable.DataTablePaginator;

import java.util.Map;

public interface AuthUserRoleService {

     public DataTablePaginator auth_user_role_list(Map<String, Object> map);

}
