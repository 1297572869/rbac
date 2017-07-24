package com.cgeel.controller;

import com.cgeel.common.datatable.DataTablePaginator;
import com.cgeel.common.datatable.DataTableParam;
import com.cgeel.common.utils.BeanUtils;
import com.cgeel.common.utils.DateUtils;
import com.cgeel.model.AuthRole;
import com.cgeel.persistence.AuthRoleMapper;
import com.cgeel.service.AuthRoleService;
import com.cgeel.utils.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Map;

@Controller
public class AuthRoleController {

     @Autowired
     private AuthRoleService auth_roleService;

     @Autowired
     private AuthRoleMapper auth_roleMapper;

     @RequestMapping(value = "/admin/auth_role.do", method = RequestMethod.GET)
     public String auth_role(ModelMap modelMap) {
         return "/admin/auth_role";
     }

     @RequestMapping(value = "/admin/auth_role_list.do", method = RequestMethod.GET)
     @ResponseBody
     public DataTablePaginator auth_role_list(DataTableParam param, AuthRole auth_role, String starttime, String endtime) throws IllegalAccessException, IntrospectionException, InvocationTargetException, ParseException{
         Map<String, Object> map= BeanUtils.BeanToMap(auth_role);
         map.put("param",param);
         Long start = DateUtils.getTimeMillisbyDate(starttime,"yyyy/MM/dd");
         Long end =DateUtils.getTimeMillisbyDate(endtime,"yyyy/MM/dd");
         map.put("start",start);
         map.put("end",end);
         DataTablePaginator p =  auth_roleService.auth_role_list(map);
         return p;
     }

     @RequestMapping(value = "/admin/add_edit_auth_role.do", method = RequestMethod.POST)
     @ResponseBody
     public RestResult add_edit_auth_role(AuthRole auth_role) {
         if (auth_role.getId()!=null){
         }else{
         }
         return RestResult.SUCCESS();
     }

     @RequestMapping(value = "/admin/get_auth_role.do", method = RequestMethod.GET)
     @ResponseBody
     public RestResult get_auth_role(Integer auth_roleId) {
         return RestResult.SUCCESS();
     }

}
