package com.cgeel.controller;

import com.cgeel.common.utils.BeanUtils;
import com.cgeel.common.utils.DateUtils;
import java.beans.IntrospectionException;
import java.text.ParseException;
@Controller
public class SUserController {

     @Autowired
     private SUserService s_userService;

     @Autowired
     private SUserMapper s_userMapper;

     @RequestMapping(value = "/admin/s_user.do", method = RequestMethod.GET)
     public String s_user(ModelMap modelMap) {
         return "/admin/s_user";
     }

     @RequestMapping(value = "/admin/s_user_list.do", method = RequestMethod.GET)
     @ResponseBody
     public DataTablePaginator s_user_list(DataTableParam param, SUser s_user, String starttime, String endtime) throws IllegalAccessException, IntrospectionException, InvocationTargetException, ParseException{
         Map<String, Object> map= BeanUtils.BeanToMap(s_user);
         map.put("param",param);
         Long start = DateUtils.getTimeMillisbyDate(starttime,"yyyy/MM/dd");
         Long end =DateUtils.getTimeMillisbyDate(endtime,"yyyy/MM/dd");
         map.put("start",start);
         map.put("end",end);
         DataTablePaginator p =  s_userService.s_user_list(map);
         return p;
     }

     @RequestMapping(value = "/admin/add_edit_s_user.do", method = RequestMethod.POST)
     @ResponseBody
     public RestResult add_edit_s_user(SUser s_user) {
         if (s_user.getId()!=null){
         }else{
         }
         return RestResult.SUCCESS();
     }

     @RequestMapping(value = "/admin/get_s_user.do", method = RequestMethod.GET)
     @ResponseBody
     public RestResult get_s_user(Integer s_userId) {
         return RestResult.SUCCESS();
     }

}
