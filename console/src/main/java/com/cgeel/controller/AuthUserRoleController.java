package com.cgeel.controller;

import com.cgeel.common.utils.BeanUtils;
import com.cgeel.common.utils.DateUtils;
import java.beans.IntrospectionException;
import java.text.ParseException;
@Controller
public class AuthUserRoleController {

     @Autowired
     private AuthUserRoleService auth_user_roleService;

     @Autowired
     private AuthUserRoleMapper auth_user_roleMapper;

     @RequestMapping(value = "/admin/auth_user_role.do", method = RequestMethod.GET)
     public String auth_user_role(ModelMap modelMap) {
         return "/admin/auth_user_role";
     }

     @RequestMapping(value = "/admin/auth_user_role_list.do", method = RequestMethod.GET)
     @ResponseBody
     public DataTablePaginator auth_user_role_list(DataTableParam param, AuthUserRole auth_user_role, String starttime, String endtime) throws IllegalAccessException, IntrospectionException, InvocationTargetException, ParseException{
         Map<String, Object> map= BeanUtils.BeanToMap(auth_user_role);
         map.put("param",param);
         Long start = DateUtils.getTimeMillisbyDate(starttime,"yyyy/MM/dd");
         Long end =DateUtils.getTimeMillisbyDate(endtime,"yyyy/MM/dd");
         map.put("start",start);
         map.put("end",end);
         DataTablePaginator p =  auth_user_roleService.auth_user_role_list(map);
         return p;
     }

     @RequestMapping(value = "/admin/add_edit_auth_user_role.do", method = RequestMethod.POST)
     @ResponseBody
     public RestResult add_edit_auth_user_role(AuthUserRole auth_user_role) {
         if (auth_user_role.getId()!=null){
         }else{
         }
         return RestResult.SUCCESS();
     }

     @RequestMapping(value = "/admin/get_auth_user_role.do", method = RequestMethod.GET)
     @ResponseBody
     public RestResult get_auth_user_role(Integer auth_user_roleId) {
         return RestResult.SUCCESS();
     }

}
