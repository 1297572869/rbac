package com.cgeel.common.mybatis;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by ZXW on 2015/1/8.
 */
public interface BaseMapper<T> {

    @InsertProvider(type = CRUDProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id")
    public int insert(@Param("entity") T entity, @Param("includeProperty") String... includeProperty);

    @InsertProvider(type = CRUDProvider.class, method = "insert")
    @Options(useGeneratedKeys = true, keyProperty = "entity.id")
    public int insertExclude(@Param("entity") T entity, @Param("excludeProperty") String... excludeProperty);

    @UpdateProvider(type = CRUDProvider.class, method = "update")
    public int update(@Param("entity") T entity, @Param("includeProperty") String... includeProperty);

    @UpdateProvider(type = CRUDProvider.class, method = "update")
    public int updateWhere(@Param("entity") T entity, @Param("where") WhereCondition where, @Param("includeProperty") String... includeProperty);

    @DeleteProvider(type = CRUDProvider.class, method = "delete")
    public int delete(@Param("entity") T entity, @Param("where") WhereCondition where);

    @SelectProvider(type = CRUDProvider.class, method = "get")
    public T get(@Param("id") Integer id, @Param("class") Class<T> clazz, @Param("cascade") boolean... cascade);

    @SelectProvider(type = CRUDProvider.class, method = "get")
    public T getBy(@Param("entity") Object entity, @Param("where") WhereCondition where, @Param("class") Class<T> clazz, @Param("cascade") boolean... cascade);

    @SelectProvider(type = CRUDProvider.class, method = "get")
    public T getInclude(@Param("id") Integer id, @Param("class") Class<T> clazz, @Param("includeProperty") String[] includeProperty, @Param("cascade") boolean... cascade);

    @SelectProvider(type = CRUDProvider.class, method = "list")
    public List<T> list(@Param("entity") Object entity, @Param("where") WhereCondition where, @Param("includeProperty") String[] includeProperty,
                        @Param("orderBy") String[] orderBy, @Param("first") Integer first, @Param("max") Integer max, @Param("class") Class<T> clazz, @Param("cascade") boolean... cascade);

    @SelectProvider(type = CRUDProvider.class, method = "count")
    public Integer count(@Param("entity") Object entity, @Param("where") WhereCondition where, @Param("class") Class<T> clazz, @Param("cascade") boolean... cascade);

}
