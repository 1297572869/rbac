package com.cgeel.service.impl;

import java.util.List;
import java.util.Map;

import com.cgeel.common.datatable.DataTablePaginator;
import com.cgeel.common.datatable.DataTableParam;
import com.cgeel.persistence.AuthRoleMapper;
import com.cgeel.service.AuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthRoleServiceImpl implements AuthRoleService {

     @Autowired
     private AuthRoleMapper auth_roleMapper;
     @Override
     public DataTablePaginator auth_role_list(Map<String, Object> map) {
         DataTableParam param = (DataTableParam) map.get("param");
         DataTablePaginator paginator = new DataTablePaginator(param);
         int count = auth_roleMapper.auth_role_count(map);
         List<Map<String, Object>> list = auth_roleMapper.auth_role_list(map);
         paginator.setiTotalDisplayRecords(count);
         paginator.setiTotalRecords(count);
         paginator.setAaData(list);
         return paginator;
     }

}
