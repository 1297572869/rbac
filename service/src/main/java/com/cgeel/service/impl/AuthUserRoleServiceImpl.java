package com.cgeel.service.impl;

import java.util.List;
import java.util.Map;

import com.cgeel.common.datatable.DataTablePaginator;
import com.cgeel.common.datatable.DataTableParam;
import com.cgeel.persistence.AuthUserRoleMapper;
import com.cgeel.service.AuthUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthUserRoleServiceImpl implements AuthUserRoleService {

     @Autowired
     private AuthUserRoleMapper auth_user_roleMapper;
     @Override
     public DataTablePaginator auth_user_role_list(Map<String, Object> map) {
         DataTableParam param = (DataTableParam) map.get("param");
         DataTablePaginator paginator = new DataTablePaginator(param);
         int count = auth_user_roleMapper.auth_user_role_count(map);
         List<Map<String, Object>> list = auth_user_roleMapper.auth_user_role_list(map);
         paginator.setiTotalDisplayRecords(count);
         paginator.setiTotalRecords(count);
         paginator.setAaData(list);
         return paginator;
     }

}
