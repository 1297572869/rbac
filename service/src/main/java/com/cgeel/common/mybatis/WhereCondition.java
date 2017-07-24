package com.cgeel.common.mybatis;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZXW on 2015/1/16.
 */
public class WhereCondition {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<String> conditions;
    private List<WhereColumn> properties;
    private Map<String, String> replaceMap = new HashMap<>();
    private Map<String, String> aliasMap = new HashMap<>();
    private boolean forUpdate = false;

    public List<String> getConditions() {
        return conditions;
    }

    public List<WhereColumn> getProperties() {
        return properties;
    }

    public Map<String, String> getReplaceMap() {
        return replaceMap;
    }

    public boolean isForUpdate() {
        return forUpdate;
    }

    WhereCondition(List<String> conditions, List<WhereColumn> properties){
        this.conditions = conditions;
        this.properties = properties;
    }

    WhereCondition(List<String> conditions, List<WhereColumn> properties, Map<String, String> aliasMap, boolean forUpdate){
        this.conditions = conditions;
        this.properties = properties;
        this.aliasMap = aliasMap;
        this.forUpdate = forUpdate;
    }

    public void appendWhere(EntityScan.ClassMapper classMapper,String cascadeName, String prefix, String alias, Object data){
        if(CollectionUtils.isEmpty(getProperties())){
            return;
        }
        String aliasDot = "";
        if(StringUtils.isNotBlank(alias)) {
            aliasDot = alias + ".";
        }
        if(StringUtils.isNotBlank(cascadeName)){
            cascadeName = cascadeName + "@";
        }else{
            cascadeName = "";
        }
        for(WhereColumn wc : getProperties()){
            if(StringUtils.isBlank(cascadeName) && StringUtils.isBlank(wc.getPrefix())){
                if(data == null || StringUtils.isBlank(prefix)){
                    addReplaceMap(cascadeName, aliasDot, prefix, wc, classMapper, true, null);
                }else {
                    Object obj = getProperty(data, wc.getName());
                    if (obj == null) {
                        addReplaceMap(cascadeName, aliasDot, prefix, wc, classMapper, false, null);
                    } else {
                        addReplaceMap(cascadeName, aliasDot, prefix, wc, classMapper, true, obj);
                    }
                }
            }else if(StringUtils.isNotBlank(cascadeName) && cascadeName.equals(wc.getPrefix()+"@")){
                if(data == null || StringUtils.isBlank(prefix)){
                    addReplaceMap(cascadeName, aliasDot, prefix, wc, classMapper, true, null);
                }else {
                    Object obj = getProperty(data, wc.getPrefix()+"@"+wc.getName());
                    if (obj == null) {
                        addReplaceMap(cascadeName, aliasDot, prefix, wc, classMapper, false, null);
                    } else {
                        addReplaceMap(cascadeName, aliasDot, prefix, wc, classMapper, true, obj);
                    }
                }
            }
        }
    }

    private void addReplaceMap(String cascadeName, String aliasDot, String prefix, WhereColumn wc, EntityScan.ClassMapper classMapper, boolean flag, Object data){
        if(!flag){
            if(Operator.NOT_EQ.getOp().equals(wc.getOperator())){
                replaceMap.put(cascadeName + wc.getName(), aliasDot + classMapper.getColumnMap().get(getAliasMapName(wc.getName())) + " is not null");
            }else{
                replaceMap.put(cascadeName + wc.getName(), aliasDot + classMapper.getColumnMap().get(getAliasMapName(wc.getName())) + " is null");
            }
        }else {
            if(Operator.IN.getOp().equals(wc.getOperator()) || Operator.NOT_IN.getOp().equals(wc.getOperator())){
                List<Object> list = (List<Object>) data;
                StringBuilder sb = new StringBuilder();
                for(Object o : list){
                    if(o instanceof String){
                        sb.append(",").append("'").append(o).append("'");
                    }else {
                        sb.append(",").append(o);
                    }
                }
                replaceMap.put(cascadeName + wc.getName(), aliasDot + classMapper.getColumnMap().get(getAliasMapName(wc.getName())) + wc.getOperator() + "( " + sb.substring(1) + " )");
            }else{
                replaceMap.put(cascadeName + wc.getName(), aliasDot + classMapper.getColumnMap().get(getAliasMapName(wc.getName())) + wc.getOperator() + "#{" + prefix + cascadeName + wc.getName() + "}");
            }
        }
    }

    public String getWhere(){
        StringBuilder whereStr = new StringBuilder();
        for(String str : getConditions()){
            if(WhereBuilder.isOperator(str)){
                whereStr.append(str);
            }else{
                whereStr.append(replaceMap.get(str));
            }
        }
        return whereStr.toString();
    }

    public boolean isEmpty(){
        return properties.isEmpty();
    }

    private Object getProperty(Object data, String property){
        try {
            if(data instanceof Map){
                return ((Map)data).get(property);
            }
            return BeanUtils.getProperty(data, property);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return null;
    }

    private String getAliasMapName(String name){
        if(aliasMap.size() == 0){
            return name;
        }
        String actual = aliasMap.get(name);
        return actual == null ? name : actual;
    }
}
