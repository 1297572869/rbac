package com.cgeel.controller;

import com.cgeel.common.utils.BeanUtils;
import com.cgeel.common.utils.DateUtils;
import java.beans.IntrospectionException;
import java.text.ParseException;
@Controller
public class AuthUserController {

     @Autowired
     private AuthUserService auth_userService;

     @Autowired
     private AuthUserMapper auth_userMapper;

     @RequestMapping(value = "/admin/auth_user.do", method = RequestMethod.GET)
     public String auth_user(ModelMap modelMap) {
         return "/admin/auth_user";
     }

     @RequestMapping(value = "/admin/auth_user_list.do", method = RequestMethod.GET)
     @ResponseBody
     public DataTablePaginator auth_user_list(DataTableParam param, AuthUser auth_user, String starttime, String endtime) throws IllegalAccessException, IntrospectionException, InvocationTargetException, ParseException{
         Map<String, Object> map= BeanUtils.BeanToMap(auth_user);
         map.put("param",param);
         Long start = DateUtils.getTimeMillisbyDate(starttime,"yyyy/MM/dd");
         Long end =DateUtils.getTimeMillisbyDate(endtime,"yyyy/MM/dd");
         map.put("start",start);
         map.put("end",end);
         DataTablePaginator p =  auth_userService.auth_user_list(map);
         return p;
     }

     @RequestMapping(value = "/admin/add_edit_auth_user.do", method = RequestMethod.POST)
     @ResponseBody
     public RestResult add_edit_auth_user(AuthUser auth_user) {
         if (auth_user.getId()!=null){
         }else{
         }
         return RestResult.SUCCESS();
     }

     @RequestMapping(value = "/admin/get_auth_user.do", method = RequestMethod.GET)
     @ResponseBody
     public RestResult get_auth_user(Integer auth_userId) {
         return RestResult.SUCCESS();
     }

}
