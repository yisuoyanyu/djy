package com.frame.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.frame.base.web.vo.PagingBean;

public interface BaseDao<T> {
	
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
	 * 删除对象
	 * 
	 * @param o
	 *            对象
	 */
	void delete(T o);
	
	/**
	 * 更新对象
	 * 
	 * @param o
	 *            对象
	 */
	void update(T o);
	
	/**
	 * 保存或更新一个对象
	 * 
	 * @param o
	 *            对象
	 */
	void saveOrUpdate(T o);
	
	// ------------------- 获取唯一值 -------------------
	
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
	
	// ------------------- 获取对象 -------------------
	
	T get(Class<T> c, Serializable id);
	
	/**
	 * 通过主键获得对象
	 *
	 * @param c
	 *            类名.class
	 * @param id
	 *            主键
	 * @return 对象
	 */
	T getById(Class<T> c, Serializable id);
	
	T get(String hql);
	
	T get(String hql, Map<String, Object> params);
	
	/**
	 * 通过HQL语句获取一个对象
	 * 
	 * @param hql
	 *            HQL语句
	 * @return 对象
	 */
	T getByHql(String hql);
	
	/**
	 * 通过HQL语句获取一个对象
	 * 
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return 对象
	 */
	T getByHql(String hql, Map<String, Object> params);
	
	// --------------------- 通过hql获取泛型对象列表 ---------------------
	
	/**
	 * 通过HQL语句获取泛型对象列表
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
	
	// ------------------- 通过hql获取对象列表 -------------------
	
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
	
	// ------------------- 通过sql获取对象列表 -------------------
	
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
	
	// ------------------- 执行hql语句 -------------------
	
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
	 * 执行一条SQL语句
	 * 
	 * @param sql
	 *            SQL语句
	 * @return
	 *            响应结果数目
	 */
	int executeSql(String sql);
	
	/**
	 * 执行一条SQL语句
	 * 
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return
	 *            响应结果数目
	 */
	int executeSql(String sql, Map<String, Object> params);
	
}
