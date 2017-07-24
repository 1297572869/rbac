package com.cgeel.controller;

import com.cgeel.common.datatable.DataTablePaginator;
import com.cgeel.common.datatable.DataTableParam;
import com.cgeel.common.utils.BeanUtils;
import com.cgeel.common.utils.DateUtils;
import com.cgeel.model.AuthFunction;
import com.cgeel.persistence.AuthFunctionMapper;
import com.cgeel.service.AuthFunctionService;
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
public class AuthFunctionController {

     @Autowired
     private AuthFunctionService auth_functionService;

     @Autowired
     private AuthFunctionMapper auth_functionMapper;

     @RequestMapping(value = "/admin/auth_function.do", method = RequestMethod.GET)
     public String auth_function(ModelMap modelMap) {
         return "/admin/auth_function";
     }

     @RequestMapping(value = "/admin/auth_function_list.do", method = RequestMethod.GET)
     @ResponseBody
     public DataTablePaginator auth_function_list(DataTableParam param, AuthFunction auth_function, String starttime, String endtime) throws IllegalAccessException, IntrospectionException, InvocationTargetException, ParseException{
         Map<String, Object> map= BeanUtils.BeanToMap(auth_function);
         map.put("param",param);
         Long start = DateUtils.getTimeMillisbyDate(starttime,"yyyy/MM/dd");
         Long end =DateUtils.getTimeMillisbyDate(endtime,"yyyy/MM/dd");
         map.put("start",start);
         map.put("end",end);
         DataTablePaginator p =  auth_functionService.auth_function_list(map);
         return p;
     }

     @RequestMapping(value = "/admin/add_edit_auth_function.do", method = RequestMethod.POST)
     @ResponseBody
     public RestResult add_edit_auth_function(AuthFunction auth_function) {
         if (auth_function.getId()!=null){
         }else{
         }
         return RestResult.SUCCESS();
     }

     @RequestMapping(value = "/admin/get_auth_function.do", method = RequestMethod.GET)
     @ResponseBody
     public RestResult get_auth_function(Integer auth_functionId) {
         return RestResult.SUCCESS();
     }

}
