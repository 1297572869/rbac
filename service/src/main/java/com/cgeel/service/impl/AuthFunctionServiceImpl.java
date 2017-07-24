package com.cgeel.service.impl;

import java.util.List;
import java.util.Map;

import com.cgeel.common.datatable.DataTablePaginator;
import com.cgeel.common.datatable.DataTableParam;
import com.cgeel.persistence.AuthFunctionMapper;
import com.cgeel.service.AuthFunctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthFunctionServiceImpl implements AuthFunctionService {

     @Autowired
     private AuthFunctionMapper auth_functionMapper;
     @Override
     public DataTablePaginator auth_function_list(Map<String, Object> map) {
         DataTableParam param = (DataTableParam) map.get("param");
         DataTablePaginator paginator = new DataTablePaginator(param);
         int count = auth_functionMapper.auth_function_count(map);
         List<Map<String, Object>> list = auth_functionMapper.auth_function_list(map);
         paginator.setiTotalDisplayRecords(count);
         paginator.setiTotalRecords(count);
         paginator.setAaData(list);
         return paginator;
     }

}
