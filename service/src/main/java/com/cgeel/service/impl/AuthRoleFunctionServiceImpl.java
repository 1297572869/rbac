package com.cgeel.service.impl;

import java.util.List;
import java.util.Map;

import com.cgeel.common.datatable.DataTablePaginator;
import com.cgeel.common.datatable.DataTableParam;
import com.cgeel.persistence.AuthRoleFunctionMapper;
import com.cgeel.service.AuthRoleFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthRoleFunctionServiceImpl implements AuthRoleFunctionService {

     @Autowired
     private AuthRoleFunctionMapper auth_role_functionMapper;
     @Override
     public DataTablePaginator auth_role_function_list(Map<String, Object> map) {
         DataTableParam param = (DataTableParam) map.get("param");
         DataTablePaginator paginator = new DataTablePaginator(param);
         int count = auth_role_functionMapper.auth_role_function_count(map);
         List<Map<String, Object>> list = auth_role_functionMapper.auth_role_function_list(map);
         paginator.setiTotalDisplayRecords(count);
         paginator.setiTotalRecords(count);
         paginator.setAaData(list);
         return paginator;
     }

}
