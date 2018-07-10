package com.frame.base.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.lang.reflect.ParameterizedType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frame.base.dao.BaseDao;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;



@Service("baseService")
public class BaseServiceImpl<T> implements BaseService<T> {
	
	@Autowired
	private BaseDao<T> baseDao;
	
	
	
	@Override
	public Serializable save(T o) {
		return baseDao.save(o);
	}
	
	@Override
	public Serializable create(T o) {
		return save(o);
	}
	
	@Override
	public void delete(T o) {
		baseDao.delete(o);
	}
	
	@Override
	public void delete(Serializable id) {
		delete(getById(id));
	}
	
	@Override
	public void update(T o) {
		baseDao.update(o);
	}
	
	@Override
	public void saveOrUpdate(T o) {
		baseDao.saveOrUpdate(o);
	}
	
	// --------------------- 获取唯一值 ---------------------
	
	@Override
	public Object getUniqueByHql(String hql) {
		return baseDao.getUniqueByHql(hql);
	}
	
	@Override
	public Object getUniqueByHql(String hql, Map<String, Object> params) {
		return baseDao.getUniqueByHql(hql, params);
	}
	
	@Override
	public Object getUniqueBySql(String sql) {
		return baseDao.getUniqueBySql(sql);
	}
	
	@Override
	public Object getUniqueBySql(String sql, Map<String, Object> params) {
		return baseDao.getUniqueBySql(sql, params);
	}
	
	// --------------------- 获取对象 ---------------------
	
	@Override
	public T get(Serializable id) {
		if (id == null)
			return null;
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return baseDao.get(c, id);
	}
	
	@Override
	public T getById(Serializable id) {
		return get(id);
	}
	
	@Override
	public T getByHql(String hql) {
		return baseDao.getByHql(hql);
	}
	
	@Override
	public T getByHql(String hql, Map<String, Object> params) {
		return baseDao.getByHql(hql, params);
	}
	
	@Override
	public T getByFilter(HqlFilter hqlFilter) {
		String className = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0]).getName();
		String hql = "select distinct t from " + className + " t";
		hql = hql + hqlFilter.getWhereAndOrderHql();
		return getByHql(hql, hqlFilter.getParams());
	}
	
	@Override
	public T getByFilter(String columnName, Object value) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter(columnName, value);
		return getByFilter(hqlFilter);
	}
	
	// --------------------- 通过hql获取对象列表 ---------------------
	
	@Override
	public List<?> findGeneric(String hql) {
		return baseDao.findGeneric(hql);
	}
	
	@Override
	public List<?> findGeneric(String hql, Map<String, Object> params) {
		return baseDao.findGeneric(hql, params);
	}
	
	@Override
	public List<?> findGeneric(String hql, int page, int rows) {
		return baseDao.findGeneric(hql, page, rows);
	}
	
	@Override
	public List<?> findGeneric(String hql, Map<String, Object> params, int page, int rows) {
		return baseDao.findGeneric(hql, params, page, rows);
	}
	
	@Override
	public List<?> findGeneric(String hql, PagingBean pb) {
		return baseDao.findGeneric(hql, pb);
	}
	
	@Override
	public List<?> findGeneric(String hql, Map<String, Object> params, PagingBean pb) {
		return baseDao.findGeneric(hql, params, pb);
	}
	
	// --------------------- 直接获取对象列表 ---------------------
	
	@Override
	public List<T> find() {
		return findByFilter(new HqlFilter());
	}
	
	@Override
	public List<T> find(int page, int rows) {
		return findByFilter(new HqlFilter(), page, rows);
	}
	
	@Override
	public List<T> find(PagingBean pb) {
		return findByFilter(new HqlFilter(), pb);
	}
	
	// --------------------- 通过hql获取对象列表 ---------------------
	
	@Override
	public List<T> find(String hql) {
		return baseDao.find(hql);
	}
	
	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		return baseDao.find(hql, params);
	}
	
	@Override
	public List<T> find(String hql, int page, int rows) {
		return baseDao.find(hql, page, rows);
	}
	
	@Override
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		return baseDao.find(hql, params, page, rows);
	}
	
	@Override
	public List<T> find(String hql, PagingBean pb) {
		return baseDao.find(hql, pb);
	}
	
	@Override
	public List<T> find(String hql, Map<String, Object> params, PagingBean pb) {
		return baseDao.find(hql, params, pb);
	}
	
	@Override
	public List<T> findByHql(String queryHql, PagingBean pb) {
		return baseDao.findByHql(queryHql, pb);
	}
	
	@Override
	public List<T> findByHql(String queryHql, Map<String, Object> params, PagingBean pb) {
		return baseDao.findByHql(queryHql, params, pb);
	}
	
	// --------------------- 通过sql获取对象列表 ---------------------
	
	@Override
	public List<T> findBySql(String sql){
		return baseDao.findBySql(sql);
	}
	
	@Override
	public List<T> findBySql(String sql, Map<String, Object> params) {
		return baseDao.findBySql(sql, params);
	}
	
	@Override
	public List<T> findBySql(String sql, int page, int rows) {
		return baseDao.findBySql(sql, page, rows);
	}
	
	@Override
	public List<T> findBySql(String sql, Map<String, Object> params, int page, int rows) {
		return baseDao.findBySql(sql, params, page, rows);
	}
	
	@Override
	public List<T> findBySql(String sql, PagingBean pb) {
		return baseDao.findBySql(sql, pb);
	}
	
	@Override
	public List<T> findBySql(String sql, Map<String, Object> params, PagingBean pb) {
		return baseDao.findBySql(sql, params, pb);
	}
	
	// --------------------- 通过HqlFilter获取对象列表 ---------------------
	
	@Override
	public List<T> findByFilter(HqlFilter hqlFilter) {
		
		if (hqlFilter == null)
			hqlFilter = new HqlFilter();
		
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		String className = c.getName();
		
		String hql = "select distinct t from " + className + " t";
		return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
		
	}
	
	@Override
	public List<T> findByFilter(HqlFilter hqlFilter, int page, int rows) {
		
		if (hqlFilter == null)
			hqlFilter = new HqlFilter();
		
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		String className = c.getName();
		
		String hql = "select distinct t from " + className + " t";
		return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams(), page, rows);
		
	}
	
	@Override
	public List<T> findByFilter(HqlFilter hqlFilter, PagingBean pb) {
		
		if (hqlFilter == null)
			hqlFilter = new HqlFilter();
		
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		String className = c.getName();
		
		String queryHql = " FROM " + className + " t" + hqlFilter.getWhereAndOrderHql();
		return findByHql(queryHql, hqlFilter.getParams(), pb);
		
	}
	
	// --------------------- 通过HqlFilter删除 ---------------------
	
	public int deleteByFilter(HqlFilter hqlFilter) {
		
		if (hqlFilter == null) 
			return 0;
		
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		String className = c.getName();
		
		String hql = "DELETE FROM " + className + " t" + hqlFilter.getWhereHql();
		return executeHql(hql, hqlFilter.getParams());
		
	}
	
	// --------------------- 统计数目 ---------------------
	
	@Override
	public Long count() {
		return countByFilter(new HqlFilter());
	}
	
	@Override
	public Long countByFilter(HqlFilter hqlFilter) {
		
		if (hqlFilter == null)
			hqlFilter = new HqlFilter();
		
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		String className = c.getName();
		
		String hql = "select count(distinct t) from " + className + " t";
		return (Long) getUniqueByHql(hql + hqlFilter.getWhereHql(), hqlFilter.getParams());
		
	}
	
	// --------------------- 执行hql语句 ---------------------
	
	@Override
	public int executeHql(String hql) {
		return baseDao.executeHql(hql);
	}
	
	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		return baseDao.executeHql(hql, params);
	}
	
	// --------------------- 执行sql语句 ---------------------
	
	@Override
	public int executeSql(String sql) {
		return baseDao.executeSql(sql);
	}
	
	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		return baseDao.executeSql(sql, params);
	}
	
}
