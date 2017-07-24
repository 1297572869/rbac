package com.cgeel.controller;

import com.cgeel.common.utils.BeanUtils;
import com.cgeel.common.utils.DateUtils;
import java.beans.IntrospectionException;
import java.text.ParseException;
@Controller
public class AuthRoleFunctionController {

     @Autowired
     private AuthRoleFunctionService auth_role_functionService;

     @Autowired
     private AuthRoleFunctionMapper auth_role_functionMapper;

     @RequestMapping(value = "/admin/auth_role_function.do", method = RequestMethod.GET)
     public String auth_role_function(ModelMap modelMap) {
         return "/admin/auth_role_function";
     }

     @RequestMapping(value = "/admin/auth_role_function_list.do", method = RequestMethod.GET)
     @ResponseBody
     public DataTablePaginator auth_role_function_list(DataTableParam param, AuthRoleFunction auth_role_function, String starttime, String endtime) throws IllegalAccessException, IntrospectionException, InvocationTargetException, ParseException{
         Map<String, Object> map= BeanUtils.BeanToMap(auth_role_function);
         map.put("param",param);
         Long start = DateUtils.getTimeMillisbyDate(starttime,"yyyy/MM/dd");
         Long end =DateUtils.getTimeMillisbyDate(endtime,"yyyy/MM/dd");
         map.put("start",start);
         map.put("end",end);
         DataTablePaginator p =  auth_role_functionService.auth_role_function_list(map);
         return p;
     }

     @RequestMapping(value = "/admin/add_edit_auth_role_function.do", method = RequestMethod.POST)
     @ResponseBody
     public RestResult add_edit_auth_role_function(AuthRoleFunction auth_role_function) {
         if (auth_role_function.getId()!=null){
         }else{
         }
         return RestResult.SUCCESS();
     }

     @RequestMapping(value = "/admin/get_auth_role_function.do", method = RequestMethod.GET)
     @ResponseBody
     public RestResult get_auth_role_function(Integer auth_role_functionId) {
         return RestResult.SUCCESS();
     }

}
