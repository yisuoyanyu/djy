package com.frame.base.service;

import com.frame.base.dao.HqlFilter;
import com.frame.base.web.vo.PagingBean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService<T> {

	/**
	 * 保存对象
	 *
	 * @param o
	 *            对象
	 * @return
	 *            对象的ID
	 */
	Serializable save(T o);
	
	/**
	 * 新建对象
	 * 
	 * @param o
	 *            对象
	 * @return
	 *            对象的ID
	 */
	Serializable create(T o);

	/**
	 * 删除对象
	 *
	 * @param o
	 *            对象
	 */
	void delete(T o);
	
	/**
	 * 删除对象
	 * 
	 * @param id
	 *            对象的ID
	 */
	void delete(Serializable id);

	/**
	 * 更新对象
	 *
	 * @param o
	 *            对象
	 */
	public void update(T o);

	/**
	 * 保存或更新一个对象
	 *
	 * @param o
	 *            对象
	 */
	void saveOrUpdate(T o);

	// --------------------- 获取唯一值 ---------------------
	
	/**
	 * 通过HQL语句获取唯一值
	 * 
	 * @param hql
	 *            HQL语句
	 * @return
	 *            结果值
	 */
	Object getUniqueByHql(String hql);
	
	/**
	 * 通过HQL语句获取唯一值
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return
	 *            结果值
	 */
	Object getUniqueByHql(String hql, Map<String, Object> params);
	
	/**
	 * 通过SQL语句获取唯一值
	 * 
	 * @param sql
	 *            SQL语句
	 * @return
	 *            结果值
	 */
	Object getUniqueBySql(String sql);
	
	/**
	 * 通过SQL语句获取唯一值
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return
	 *            结果值
	 */
	Object getUniqueBySql(String sql, Map<String, Object> params);

	// --------------------- 获取对象 ---------------------

	/**
	 * 通过主键获得对象
	 * 
	 * @param id
	 *            主键
	 * @return
	 *            对象
	 */
	T get(Serializable id);
	
	/**
	 * 通过主键获得对象
	 *
	 * @param id
	 *            主键
	 * @return
	 *            对象
	 */
	T getById(Serializable id);

	/**
	 * 通过HQL语句获取一个对象
	 *
	 * @param hql
	 *            HQL语句
	 * @return
	 *            对象
	 */
	T getByHql(String hql);

	/**
	 * 通过HQL语句获取一个对象
	 *
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return
	 *            对象
	 */
	T getByHql(String hql, Map<String, Object> params);

	/**
	 * 通过HqlFilter获取一个对象
	 *
	 * @param hqlFilter
	 *            HqlFilter过滤对象
	 * @return
	 *            对象
	 */
	T getByFilter(HqlFilter hqlFilter);
	
	/**
	 * 通过单条件过滤获取一个对象
	 * 
	 * @param columnName
	 *            属性名
	 * @param value
	 *            属性值
	 * @return
	 *            对象
	 */
	T getByFilter(String columnName, Object value);
	
	// --------------------- 通过hql获取泛型对象列表 ---------------------
	
	/**
	 * 通过HQL语句获取泛型对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @return
	 *            泛型对象列表
	 */
	List<?> findGeneric(String hql);
	
	/**
	 * 通过HQL语句获取泛型对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return
	 *            泛型对象列表
	 */
	List<?> findGeneric(String hql, Map<String, Object> params);
	
	/**
	 * 通过HQL语句获取分页后的泛型对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param page
	 *            第几页，下标从1开始。
	 * @param rows
	 *            每页几行
	 * @return
	 *            泛型对象列表
	 */
	List<?> findGeneric(String hql, int page, int rows);
	
	/**
	 * 通过HQL语句获取分页后的泛型对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            第几页，下标从1开始。
	 * @param rows
	 *            每页几行
	 * @return
	 *            泛型对象列表
	 */
	List<?> findGeneric(String hql, Map<String, Object> params, int page, int rows);
	
	/**
	 * 通过HQL语句获取分页后的泛型对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param pb
	 *            分页对象
	 * @return
	 *            泛型对象列表
	 */
	List<?> findGeneric(String hql, PagingBean pb);
	
	/**
	 * 通过HQL语句获取分页后的泛型对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @param pb
	 *            分页对象
	 * @return
	 *            泛型对象列表
	 */
	List<?> findGeneric(String hql, Map<String, Object> params, PagingBean pb);
	
	// --------------------- 直接获取对象列表 ---------------------
	
	/**
	 * 获得对象列表
	 *
	 * @return
	 *            实体对象列表
	 */
	List<T> find();
	
	/**
	 * 获取分页后的对象列表
	 *
	 * @param page
	 *            第几页，下标从1开始。
	 * @param rows
	 *            每页几行
	 * @return
	 *            实体对象列表
	 */
	List<T> find(int page, int rows);
	
	/**
	 * 获取分页后的对象列表
	 * 
	 * @param pb
	 *            分页对象
	 * @return
	 *            实体对象列表
	 */
	List<T> find(PagingBean pb);
	
	// --------------------- 通过hql获取对象列表 ---------------------
	
	/**
	 * 通过HQL语句获取对象列表
	 *
	 * @param hql
	 *            HQL语句
	 * @return
	 *            实体对象列表
	 */
	List<T> find(String hql);
	
	/**
	 * 通过HQL语句获取对象列表
	 *
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return
	 *            实体对象列表
	 */
	List<T> find(String hql, Map<String, Object> params);
	
	/**
	 * 通过HQL语句获取分页后的对象列表
	 *
	 * @param hql
	 *            HQL语句
	 * @param page
	 *            第几页，下标从1开始。
	 * @param rows
	 *            每页几行
	 * @return
	 *            实体对象列表
	 */
	List<T> find(String hql, int page, int rows);
	
	/**
	 * 通过HQL语句获取分页后的对象列表
	 *
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            第几页，下标从1开始。
	 * @param rows
	 *            每页几行
	 * @return
	 *            实体对象列表
	 */
	List<T> find(String hql, Map<String, Object> params, int page, int rows);
	
	/**
	 * 通过HQL语句获取分页后的对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param pb
	 *            分页对象
	 * @return
	 *            实体对象列表
	 */
	List<T> find(String hql, PagingBean pb);
	
	/**
	 * 通过HQL语句获取分页后的对象列表
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @param pb
	 *            分页对象
	 * @return
	 *            实体对象列表
	 */
	List<T> find(String hql, Map<String, Object> params, PagingBean pb);
	
	/**
	 * 通过HQL语句获取分页后的对象列表
	 * 
	 * @param queryHql
	 *            从FROM关键字开始的HQL语句部分
	 * @param pb
	 *            分页方式
	 * @return
	 *            实体对象列表
	 */
	List<T> findByHql(String queryHql, PagingBean pb);
	
	/**
	 * 通过HQL语句获取分页后的对象列表
	 * 
	 * @param queryHql
	 *            从FROM关键字开始的HQL语句
	 * @param params
	 *            参数
	 * @param pb
	 *            分页方式
	 * @return
	 *            实体对象列表
	 */
	List<T> findByHql(String queryHql, Map<String, Object> params, PagingBean pb);
	
	// --------------------- 通过sql获取对象列表 ---------------------
	
	/**
	 * 通过SQL语句获取对象列表
	 * 
	 * @param sql
	 *            SQL语句
	 * @return
	 *            实体对象列表
	 */
	List<T> findBySql(String sql);
	
	/**
	 * 通过SQL语句获取对象列表
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return
	 *            实体对象列表
	 */
	List<T> findBySql(String sql, Map<String, Object> params);
	
	/**
	 * 通过SQL语句获取分页后的对象列表
	 * 
	 * @param sql
	 *            SQL语句
	 * @param page
	 *            第几页，下标从1开始。
	 * @param rows
	 *            每页几行
	 * @return
	 *            实体对象列表
	 */
	List<T> findBySql(String sql, int page, int rows);
	
	/**
	 * 通过SQL语句获取分页后的对象列表
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            第几页，下标从1开始。
	 * @param rows
	 *            每页几行
	 * @return
	 *            实体对象列表
	 */
	List<T> findBySql(String sql, Map<String, Object> params, int page, int rows);
	
	/**
	 * 通过SQL语句获取分页后的对象列表
	 * 
	 * @param sql
	 *            SQL语句
	 * @param pb
	 *            分页方式
	 * @return
	 *            实体对象列表
	 */
	List<T> findBySql(String sql, PagingBean pb);
	
	/**
	 * 通过SQL语句获取分页后的对象列表
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @param pb
	 *            分页方式
	 * @return
	 *            实体对象列表
	 */
	List<T> findBySql(String sql, Map<String, Object> params, PagingBean pb);
	
	// --------------------- 通过HqlFilter获取对象列表 ---------------------
	
	/**
	 * 通过 过滤条件 获取对象列表
	 *
	 * @param hqlFilter
	 *            过滤条件
	 * @return
	 *            实体对象列表
	 */
	List<T> findByFilter(HqlFilter hqlFilter);
	
	/**
	 * 通过 过滤条件 获取分页后的对象列表
	 *
	 * @param hqlFilter
	 *            过滤条件
	 * @param page
	 *            第几页，下标从1开始。
	 * @param rows
	 *            每页几行
	 * @return
	 *            实体对象列表
	 */
	List<T> findByFilter(HqlFilter hqlFilter, int page, int rows);
	
	/**
	 * 通过 过滤条件 获取分页后的对象列表
	 * 
	 * @param hqlFilter
	 *            过滤条件
	 * @param pb
	 *            分页方式
	 * @return
	 *            实体对象列表
	 */
	List<T> findByFilter(HqlFilter hqlFilter, PagingBean pb);
	
	// --------------------- 通过HqlFilter删除 ---------------------
	
	/**
	 * 通过 过滤条件 执行删除
	 * 
	 * @param hqlFilter
	 *            过滤条件
	 * @return
	 *            响应结果数目
	 */
	public int deleteByFilter(HqlFilter hqlFilter);
	
	// --------------------- 统计数目 ---------------------

	/**
	 * 统计数目
	 *
	 * @return
	 *            结果数目
	 */
	Long count();
	
	/**
	 * 统计数目
	 *
	 * @param hqlFilter
	 *            过滤条件
	 * @return
	 *            结果数目
	 */
	Long countByFilter(HqlFilter hqlFilter);
	
	// --------------------- 执行hql语句 ---------------------
	
	/**
	 * 执行一条HQL语句
	 *
	 * @param hql
	 *            HQL语句
	 * @return
	 *            响应结果数目
	 */
	int executeHql(String hql);
	
	/**
	 * 执行一条HQL语句
	 *
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return
	 *            响应结果数目
	 */
	int executeHql(String hql, Map<String, Object> params);
	
	// --------------------- 执行sql语句 ---------------------
	
	/**
	 * 执行一条HQL语句
	 * 
	 * @param sql
	 *            SQL语句
	 * @return
	 *            响应结果数目
	 */
	int executeSql(String sql);
	
	/**
	 * 执行一条SQL语句
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return
	 *            响应结果数目
	 */
	int executeSql(String sql, Map<String, Object> params);
	
}
