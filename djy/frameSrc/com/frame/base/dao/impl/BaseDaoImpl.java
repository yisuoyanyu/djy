package com.frame.base.dao.impl;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.hql.ast.QueryTranslatorImpl;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.frame.base.dao.BaseDao;
import com.frame.base.utils.StringUtil;
import com.frame.base.web.vo.PagingBean;


@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDao<T> {
	
	protected SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getCurrentSession() {
		try {
			Session session = sessionFactory.getCurrentSession();
			return session;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	
	// --------------------------------------------
	
	@Override
	public Serializable save(T o) {
		return getCurrentSession().save(o);
	}
	
	@Override
	public void delete(T o) {
		getCurrentSession().delete(o);
	}
	
	@Override
	public void update(T o) {
		getCurrentSession().update(o);
	}
	
	@Override
	public void saveOrUpdate(T o) {
		getCurrentSession().saveOrUpdate(o);
	}
	
	// ------------------- 获取唯一值 -------------------
	
	@Override
	public Object getUniqueByHql(String hql) {
		Query q = getHqlQuery(hql);
		return q.uniqueResult();
	}
	
	@Override
	public Object getUniqueByHql(String hql, Map<String, Object> params) {
		Query q = getHqlQuery(hql, params);
		
		if ( q == null )
			return null;
		
		return q.uniqueResult();
	}
	
	@Override
	public Object getUniqueBySql(String sql) {
		SQLQuery q = getSqlQuery(sql);
		return q.uniqueResult();
	}
	
	@Override
	public Object getUniqueBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getSqlQuery(sql, params);
		
		if (q == null)
			return null;
		
		return q.uniqueResult();
	}
	
	// ------------------- 获取对象 -------------------
	
	@Override
	public T get(Class<T> c, Serializable id) {
		return (T) getCurrentSession().get(c, id);
	}
	
	@Override
	public T getById(Class<T> c, Serializable id) {
		return get(c, id);
	}
	
	@Override
	public T get(String hql) {
		
		Query q = getHqlQuery(hql);
		List<T> list = q.setFirstResult(0).setMaxResults(1).list();
		if ( list != null && list.size() > 0 ) {
			return list.get(0);
		}
		return null;
		
	}
	
	@Override
	public T get(String hql, Map<String, Object> params) {
		
		Query q = getHqlQuery(hql, params);
		
		if ( q == null )
			return null;
		
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}
	
	@Override
	public T getByHql(String hql) {
		return get(hql);
	}
	
	@Override
	public T getByHql(String hql, Map<String, Object> params) {
		return get(hql, params);
	}
	
	// --------------------- 通过hql获取泛型对象列表 ---------------------
	
	@Override
	public List<?> findGeneric(String hql) {
		Query q = getHqlQuery(hql);
		return q.list();
	}
	
	@Override
	public List<?> findGeneric(String hql, Map<String, Object> params) {
		Query q = getHqlQuery(hql, params);
		
		if ( q == null )
			return null;
		
		return q.list();
	}
	
	@Override
	public List<?> findGeneric(String hql, int page, int rows) {
		Query q = getHqlQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	
	@Override
	public List<?> findGeneric(String hql, Map<String, Object> params, int page, int rows) {
		Query q = getHqlQuery(hql, params);
		
		if ( q == null )
			return null;
		
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	
	@Override
	public List<?> findGeneric(String hql, PagingBean pb) {
		if (pb.getIsReturnTotal() == null || pb.getIsReturnTotal()) {
			Long totalItems = countHql(hql);
			pb.setTotalItems(totalItems);
		}
		return findGeneric(hql, pb.getToPage(), pb.getPageSize());
	}
	
	@Override
	public List<?> findGeneric(String hql, Map<String, Object> params, PagingBean pb) {
		if (pb.getIsReturnTotal() == null || pb.getIsReturnTotal()) {
			Long totalItems = countHql(hql, params);
			pb.setTotalItems(totalItems);
		}
		return findGeneric(hql, params, pb.getToPage(), pb.getPageSize());
	}
	
	// ------------------- 通过hql获取对象列表 -------------------

	@Override
	public List<T> find(String hql) {
		return (List<T>) findGeneric(hql);
	}
	
	@Override
	public List<T> find(String hql, Map<String, Object> params) {
		return (List<T>) findGeneric(hql, params);
	}

	@Override
	public List<T> find(String hql, int page, int rows) {
		return (List<T>) findGeneric(hql, page, rows);
	}
	
	@Override
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		return (List<T>) findGeneric(hql, params, page, rows);
	}
	
	@Override
	public List<T> find(String hql, PagingBean pb) {
		return (List<T>) findGeneric(hql, pb);
	}
	
	@Override
	public List<T> find(String hql, Map<String, Object> params, PagingBean pb) {
		return (List<T>) findGeneric(hql, params, pb);
	}
	
	@Override
	public List<T> findByHql(String queryHql, PagingBean pb) {
		if (pb.getIsReturnTotal() == null || pb.getIsReturnTotal()) {
			String countHql = "select count(*) " + queryHql;
			pb.setTotalItems( (Long) getUniqueByHql(countHql) );
		}
		return find(queryHql, pb.getToPage(), pb.getPageSize());
	}
	
	@Override
	public List<T> findByHql(String queryHql, Map<String, Object> params, PagingBean pb) {
		if (pb.getIsReturnTotal() == null || pb.getIsReturnTotal()) {
			String countHql = "select count(*) " + queryHql;
			pb.setTotalItems( (Long) getUniqueByHql(countHql, params) );
		}
		return find(queryHql, params, pb.getToPage(), pb.getPageSize());
	}
	
	// ------------------- 通过sql获取对象列表 -------------------
	
	@Override
	public List<T> findBySql(String sql) {
		
		SQLQuery q = getSqlQuery(sql);
		
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		if ( c.getName().matches("[\\p{Alpha}|.]+[.]model[.]\\p{Alpha}+") ) {
			q.addEntity(c);
		} else {
			q.setResultTransformer(Transformers.aliasToBean(c));
		}
		
		return q.list();
		
	}
	
	@Override
	public List<T> findBySql(String sql, Map<String, Object> params) {
		
		SQLQuery q = getSqlQuery(sql, params);
		
		if (q==null)
			return null;
		
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		if ( c.getName().matches("[\\p{Alpha}|.]+[.]model[.]\\p{Alpha}+") ) {
			q.addEntity(c);
		} else {
			q.setResultTransformer(Transformers.aliasToBean(c));
		}
		
		return q.list();
		
	}
	
	@Override
	public List<T> findBySql(String sql, int page, int rows) {
		
		SQLQuery q = getSqlQuery(sql);
		
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		if ( c.getName().matches("[\\p{Alpha}|.]+[.]model[.]\\p{Alpha}+") ) {
			q.addEntity(c);
		} else {
			q.setResultTransformer(Transformers.aliasToBean(c));
		}
		
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		
	}
	
	@Override
	public List<T> findBySql(String sql, Map<String, Object> params, int page, int rows) {
		
		SQLQuery q = getSqlQuery(sql, params);
		
		if (q==null)
			return null;
		
		Class<T> c = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		if ( c.getName().matches("[\\p{Alpha}|.]+[.]model[.]\\p{Alpha}+") ) {
			q.addEntity(c);
		} else {
			q.setResultTransformer(Transformers.aliasToBean(c));
		}
		
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		
	}
	
	@Override
	public List<T> findBySql(String sql, PagingBean pb) {
		
		if (pb.getIsReturnTotal() == null || pb.getIsReturnTotal()) {
			String countSql = "SELECT COUNT(*) FROM ( " + sql + " ) AS T ";
			pb.setTotalItems(Long.valueOf(((Number) getUniqueBySql(countSql)).longValue()));
		}
		
		return findBySql(sql, pb.getToPage(), pb.getPageSize());
		
	}
	
	@Override
	public List<T> findBySql(String sql, Map<String, Object> params, PagingBean pb) {
		
		if (pb.getIsReturnTotal() == null || pb.getIsReturnTotal()) {
			String countSql = "SELECT COUNT(*) FROM ( " + sql + " ) AS T ";
			pb.setTotalItems(Long.valueOf(((Number) getUniqueBySql(countSql, params)).longValue()));
		}
		
		return findBySql(sql, params, pb.getToPage(), pb.getPageSize());
		
	}
	
	// ------------------- 执行hql语句 -------------------

	@Override
	public int executeHql(String hql) {
		Query q = getHqlQuery(hql);
		return q.executeUpdate();
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = getHqlQuery(hql, params);
		
		if ( q == null )
			return 0;
		
		return q.executeUpdate();
	}

	// --------------------- 执行sql语句 ---------------------
	
	
	@Override
	public int executeSql(String sql) {
		SQLQuery q = getSqlQuery(sql);
		return q.executeUpdate();
	}
	
	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		
		SQLQuery q = getSqlQuery(sql, params);
		
		if (q == null)
			return 0;
		
		return q.executeUpdate();
	}
	
	
	// --------------------- 私有方法 -------------------
	
	private Query getHqlQuery(String hql) {
		
		Session session = getCurrentSession();
		
		if (session == null)
			return null;
		
		Query q = session.createQuery(hql);
		
		return q;
		
	}
	
	private Query getHqlQuery(String hql, Map<String, Object> params) {
		
		Session session = getCurrentSession();
		
		if (session == null)
			return null;
		
		String rHql = hql;	//最终要执行的hql语句
		Map<String, Object> rParams = new HashMap<String, Object>();	//最终要替换的参数
		
		if ( params != null && !params.isEmpty() ) {
			ArrayList<String> removeKeys = new ArrayList<String>();
			for ( String key : params.keySet() ) {
				Object value = params.get(key);
				if (value == null) {
					removeKeys.add(":" + key);
				} else {
					rParams.put(key, value);
				}
			}
			if (removeKeys.size()>0) {
				//组织要替换的正则表达式
				String regex = StringUtil.arrayToString(removeKeys.toArray(), "|");
				rHql = rHql.replaceAll(regex, "null");
			}
		}
		
		Query q = session.createQuery(rHql);
		
		if ( !rParams.isEmpty() ) {
			for (String key : rParams.keySet()) {
				Object value = rParams.get(key);
				if ( value instanceof Collection<?> ) {
					q.setParameterList(key, (Collection<?>)value); 
				} else if ( value instanceof Object[] ) {
					q.setParameterList(key, (Object[])value);
				} else {
					q.setParameter(key, value);
				}
			}
		}
		
		return q;
		
	}
	
	
	private SQLQuery getSqlQuery(String sql) {
		
		Session session = getCurrentSession();
		
		if (session == null)
			return null;
		
		SQLQuery q = session.createSQLQuery(sql);
		
		return q;
		
	}
	
	private SQLQuery getSqlQuery(String sql, Map<String, Object> params) {
		
		Session session = getCurrentSession();
		
		if (session == null)
			return null;
		
		SQLQuery q = session.createSQLQuery(sql);
		
		if ( params != null && !params.isEmpty() ) {
			for (String key : params.keySet()) {
				Object value = params.get(key);
				if ( value instanceof Collection<?> ) {
					q.setParameterList(key, (Collection<?>)value); 
				} else if ( value instanceof Object[] ) {
					q.setParameterList(key, (Object[])value);
				} else {
					q.setParameter(key, value);
				}
			}
		}
		
		return q;
		
	}
	
	private String hqlToSql(String hql) {
		SessionFactoryImpl sfi = (SessionFactoryImpl) sessionFactory;
		QueryTranslatorImpl qti = new QueryTranslatorImpl(hql, hql, Collections.EMPTY_MAP, sfi);
		qti.compile(Collections.EMPTY_MAP, false);
		String sql = qti.getSQLString();
		return sql;
	}
	
	private Object getUniqueBySql(String sql, Object[] objs) {
		
		Session session = getCurrentSession();
		if (session == null)
			return null;
		
		SQLQuery q = session.createSQLQuery(sql);
		if (q == null)
			return null;
		
		Type[] types = new Type[objs.length];
		for (int i=0; i<objs.length; i++) {
			Object value = objs[i];
			if ( value instanceof Integer ) {
				types[i] = Hibernate.INTEGER;
			} else if (value instanceof String) {
				types[i] = Hibernate.STRING;
			} else if (value instanceof Double) {
				types[i] = Hibernate.DOUBLE;
			} else if (value instanceof Float) {
				types[i] = Hibernate.FLOAT;
			} else if (value instanceof Long) {
				types[i] = Hibernate.LONG;
			} else if (value instanceof Boolean) {
				types[i] = Hibernate.BOOLEAN;
			} else if (value instanceof Date) {
				types[i] = Hibernate.DATE;
			} else if ( value instanceof Collection<?> ) {
				types[i] = Hibernate.OBJECT;
			} else if ( value instanceof Object[] ) {
				types[i] = Hibernate.OBJECT;
			} else {
				types[i] = Hibernate.OBJECT;
			}
		}
		
		q.setParameters(objs, types);
		return q.uniqueResult();
		
	}
	
	private Long countHql(String hql) {
		
		String sql = hqlToSql(hql);
		String countSql = "SELECT COUNT(0) FROM (" + sql + ") T";
		Long totalItems = Long.valueOf( ((Number) getUniqueBySql(countSql)).longValue() );
		return totalItems;
		
	}
	
	private Long countHql(String hql, Map<String, Object> params) {
		
		List<Object> objList = new ArrayList<>();
		
		String regex = ":\\p{Alpha}+";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(hql);
		while (m.find()) {
			String key = m.group().substring(1);
			Object value = params.get(key);
			objList.add(value);
		}
		
		Object[] objs = objList.toArray();
		
		String sql = hqlToSql(hql);
		String countSql = "SELECT COUNT(0) FROM (" + sql + ") T";
		Long totalItems = Long.valueOf( ((Number) getUniqueBySql(countSql, objs)).longValue() );
		
		return totalItems;
		
	}
	
	
}
