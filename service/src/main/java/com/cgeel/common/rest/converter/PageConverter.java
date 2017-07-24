package com.cgeel.common.rest.converter;

import com.cgeel.common.Paginator;
import com.cgeel.common.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zxw on 2015/7/6.
 */
public class PageConverter implements BeanConverter {

    @Override
    public <T> T converter(String orgin, Class<?> tClass) {
        Map<String, Object> result = StringUtils.toObject(orgin, Map.class);
        Paginator paginator = new Paginator();
        Field[] fields = Paginator.class.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            if(field.getName().equals("results")){
                List<Object> objList = (List<Object>)result.get("results");
                List<Object> list = new ArrayList<>();
                try {
                    if(objList != null) {
                        for (Object obj : objList) {
                            Object t = tClass.newInstance();
                            BeanUtils.copyProperties(t, obj);
                            list.add(t);
                        }
                    }
                    field.set(paginator, list);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    field.set(paginator, result.get(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return (T)paginator;
    }
}
