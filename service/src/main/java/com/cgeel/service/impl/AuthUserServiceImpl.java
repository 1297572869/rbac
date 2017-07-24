package com.cgeel.service.impl;

import java.util.List;
import java.util.Map;

import com.cgeel.common.datatable.DataTablePaginator;
import com.cgeel.common.datatable.DataTableParam;
import com.cgeel.persistence.AuthUserMapper;
import com.cgeel.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthUserServiceImpl implements AuthUserService {

     @Autowired
     private AuthUserMapper auth_userMapper;
     @Override
     public DataTablePaginator auth_user_list(Map<String, Object> map) {
         DataTableParam param = (DataTableParam) map.get("param");
         DataTablePaginator paginator = new DataTablePaginator(param);
         int count = auth_userMapper.auth_user_count(map);
         List<Map<String, Object>> list = auth_userMapper.auth_user_list(map);
         paginator.setiTotalDisplayRecords(count);
         paginator.setiTotalRecords(count);
         paginator.setAaData(list);
         return paginator;
     }

}
