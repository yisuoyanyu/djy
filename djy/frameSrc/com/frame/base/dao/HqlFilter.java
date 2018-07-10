package com.frame.base.dao;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;

import com.frame.base.utils.StringUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * HQL过滤器 用于添加where条件和排序，过滤结果集。 添加过滤条件则使用addFilter方法
 * 
 * @author puzd
 * 2017.07.19 puzd 
 *     1、支持notIn；
 *     2、支持or运算（addFilterFirstOr、addFilterNextOr、addFilterEndOr）；
 *     3、同时把几个没用到且未经测试的方法注释。
 */
public class HqlFilter {

	// 为了获取request里面传过来的动态参数
	private HttpServletRequest request;

	// 条件参数
	private Map<String, Object> params = new HashMap<String, Object>();
	
	// 查询过滤hql部分
	private StringBuffer hql = new StringBuffer();

	// 排序hql部分
	private StringBuffer orderBy = new StringBuffer();

	/**
	 * 默认构造
	 */
	public HqlFilter() {

	}



	/**
	 * 添加排序字段 默认正序
	 *
	 * @param columnName
	 */
	public void addOrder(String columnName) {
		addOrder(columnName, false);
	}

	/**
	 * 添加排序字段
	 *
	 * @param columnName
	 * @param isDesc
	 */
	public void addOrder(String columnName, boolean isDesc) {
		if (orderBy.length() == 0) {
			orderBy.append(" order by ");
		} else {
			orderBy.append(",");
		}
		if (columnName.indexOf(".") < 1) {
			orderBy.append("t.");
		}
		orderBy.append(columnName);
		orderBy.append(isDesc ? " desc " : " asc ");
	}

	
	/**
	 * 转换SQL操作符
	 *
	 * @param operator
	 * @return
	 */
	private String getSqlOperator(SqlOperator operator) {
		switch (operator) {
		case equal:
			return " = ";
		case notEqual:
			return " != ";
		case greaterThen:
			return " > ";
		case greaterEqual:
			return " >= ";
		case lessThen:
			return " < ";
		case lessEqual:
			return " <= ";
		case like:
		case leftLike:
		case rightLike:
			return " like ";
		case in:
			return " IN ";
		case notIn:
			return " NOT IN ";
		default:
			return " = ";
		}
	}

	/**
	 * 获得添加过滤字段后的HQL
	 *
	 * @return
	 */
	public String getWhereHql() {
		return hql.toString();
	}

	/**
	 * 获得添加过滤字段后加上排序字段的HQL
	 *
	 * @return
	 */
	public String getWhereAndOrderHql() {
		if (orderBy.length() > 0) {
			hql.append(orderBy); // 添加排序信息
		} else {
			if (request != null) {
				String orderField = request.getParameter("orderField");
				String orderDirection = request.getParameter("orderDirection");
				if (!StringUtil.isBlank(orderField) && !StringUtil.isBlank(orderDirection)) {
					if (orderField.indexOf(".") < 1) {
						orderField = "t." + orderField;
					}
					hql.append(" order by " + orderField + " " + orderDirection + " ");// 添加排序信息
				}
			}
		}
		return hql.toString();
	}

	/**
	 * 获得过滤字段参数和值
	 *
	 * @return
	 */
	public Map<String, Object> getParams() {
		return params;
	}



	
	/**
	 * 添加值相等的过滤
	 * 
	 * @param columnName
	 * @param value
	 */
	public void addFilter(String columnName, Object value) {
		addFilter(columnName, value, SqlOperator.equal);
	}

	
	private Object getObjValue(SqlOperator operator, Object value) {
		if (operator.equals(SqlOperator.like)) {
			value = "%%" + value + "%%";
		} else if (operator.equals(SqlOperator.leftLike)) {
			value = value + "%%";
		} else if (operator.equals(SqlOperator.rightLike)) {
			value = "%%" + value;
		} else if ( operator.equals(SqlOperator.in) || operator.equals(SqlOperator.notIn) ) {
			if ( !value.getClass().isArray() ) {
				value = new Object[]{value};
			}
		}
		return value;
	}
	
	
	private String getCondition(String columnName, Object value, SqlOperator operator, String placeholder) {
		
		// 拼HQL
		if ( operator.equals(SqlOperator.in) ) {
			return " " + columnName + " " + getSqlOperator(operator) + "(:param" + placeholder + ") ";
		} else if( operator.equals(SqlOperator.notIn) ) {
			if ( value == null ) {
				return " " + columnName + " " + getSqlOperator(operator) + "(:param" + placeholder + ") ";
			} else {
				return " (" + columnName + " " + getSqlOperator(operator) + "(:param" + placeholder + ") or " + columnName + " is null ) ";
			}
		} else if( operator.equals(SqlOperator.notEqual) ) {
			if (value == null) {
				return " " + columnName + " " + getSqlOperator(operator) + " :param" + placeholder + " ";
			} else {
				return " (" + columnName + " " + getSqlOperator(operator) + " :param" + placeholder  + " or " + columnName + " is null ) ";
			}
		} else {
			return " " + columnName + " " + getSqlOperator(operator) + " :param" + placeholder + " ";
		}
		
	}
	
	
	/**
	 * 添加过滤
	 * @param columnName
	 * @param value
	 * @param operator
	 */
	public void addFilter(String columnName, Object value, SqlOperator operator) {
		if (hql.toString().indexOf(" where 1=1") < 0) {
			hql.append("  where 1=1 ");
		}
		
		// 生成一个随机的参数名称
		String placeholder = UUID.randomUUID().toString().replace("-", "");
		
		// 拼HQL
		hql.append( " and " + getCondition(columnName, value, operator, placeholder) );
		
		// 添加参数
		params.put("param" + placeholder, getObjValue(operator, value));
	}
	
	
	/**
	 * 添加or运算
	 * @param columnName
	 * @param value
	 * @param operator
	 */
	public void addFilterFirstOr(String columnName, Object value, SqlOperator operator) {
		if (hql.toString().indexOf(" where 1=1") < 0) {
			hql.append("  where 1=1 ");
		}

		// 生成一个随机的参数名称
		String placeholder = UUID.randomUUID().toString().replace("-", "");
		
		// 拼HQL
		hql.append( " and (" + getCondition(columnName, value, operator, placeholder) );
		
		// 添加参数
		params.put("param" + placeholder, getObjValue(operator, value));
	}
	
	
	public void addFilterNextOr(String columnName, Object value, SqlOperator operator) {
		
		// 生成一个随机的参数名称
		String placeholder = UUID.randomUUID().toString().replace("-", "");
		
		// 拼HQL
		hql.append( " or " + getCondition(columnName, value, operator, placeholder) );
		
		// 添加参数
		params.put("param" + placeholder, getObjValue(operator, value));
	}
	
	
	public void addFilterEndOr(String columnName, Object value, SqlOperator operator) {
		// 生成一个随机的参数名称
		String placeholder = UUID.randomUUID().toString().replace("-", "");
		
		// 拼HQL
		hql.append(" or " + getCondition(columnName, value, operator, placeholder) + " ) ");
		
		// 添加参数
		params.put("param" + placeholder, getObjValue(operator, value));
	}
	

	// 以下部分未经测试，暂时注释
	//---------------------------------------------------------------------
	
	

//	/**
//	 * 将String值转换成Object，用于拼写HQL，替换操作符和值
//	 * 
//	 * @param columnType
//	 * @param operator
//	 * @param value
//	 * @return
//	 */
//	private Object getObjValue(ColumnType columnType, SqlOperator operator, String value) {
//		
//		Object ret = value;
//		
//		if (columnType.equals(ColumnType.String)) {
//			if (operator.equals(SqlOperator.like)) {
//				ret = "%%" + value + "%%";
//			} else if (operator.equals(SqlOperator.leftLike)) {
//				ret = value + "%%";
//			} else if (operator.equals(SqlOperator.rightLike)) {
//				ret = "%%" + value;
//			} else if ( operator.equals(SqlOperator.in) || operator.equals(SqlOperator.notIn) ) {
//				ret = new String[]{value};
//			}
//			return ret;
//		}
//
//		if (columnType.equals(ColumnType.Long)) {
//			ret = Long.parseLong(value);
//			if (operator.equals(SqlOperator.in) || operator.equals(SqlOperator.notIn)) {
//				return new Long[]{(Long)ret};
//			}
//			return ret;
//		}
//		
//		if (columnType.equals(ColumnType.Integer)) {
//			ret = Integer.parseInt(value);
//			if (operator.equals(SqlOperator.in) || operator.equals(SqlOperator.notIn)) {
//				return new Integer[]{(Integer)ret};
//			}
//			return ret;
//		}
//		
//		if (columnType.equals(ColumnType.Short)) {
//			ret = Short.parseShort(value);
//			if (operator.equals(SqlOperator.in) || operator.equals(SqlOperator.notIn)) {
//				return new Short[]{(Short)ret};
//			}
//			return ret;
//		}
//		
//		if (columnType.equals(ColumnType.BigDecimal)) {
//			ret = BigDecimal.valueOf(Long.parseLong(value));
//			if (operator.equals(SqlOperator.in) || operator.equals(SqlOperator.notIn)) {
//				return new BigDecimal[]{(BigDecimal)ret};
//			}
//			return ret;
//		}
//		
//		if (columnType.equals(ColumnType.Float)) {
//			ret = Float.parseFloat(value);
//			if (operator.equals(SqlOperator.in) || operator.equals(SqlOperator.notIn)) {
//				return new Float[]{(Float)ret};
//			}
//			return ret;
//		}
//		
//		if (columnType.equals(ColumnType.Date)) {
//			try {
//				ret = DateUtils.parseDate(value,
//						new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyy/MM/dd" });
//				if (operator.equals(SqlOperator.in) || operator.equals(SqlOperator.notIn)) {
//					return new Date[]{(Date)ret};
//				}
//				return ret;
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return null;
//	}
//	
//	
//	/**
//	 * 添加过滤
//	 *
//	 * @param columnName
//	 * @param value
//	 * @param columnType
//	 * @param operator
//	 */
//	public void addFilter(String columnName, String value, ColumnType columnType, SqlOperator operator) {
//		if (hql.toString().indexOf(" where 1=1") < 0) {
//			hql.append("  where 1=1 ");
//		}
//
//		//生成一个随机的参数名称
//		String placeholder = UUID.randomUUID().toString().replace("-", "");
//		
//		//拼HQL
//		if ( operator.equals(SqlOperator.in) ) {
//			hql.append(" and " + columnName + " " + getSqlOperator(operator) + " (:param" + placeholder + ") ");
//		} else {
//			hql.append(" and " + columnName + " " + getSqlOperator(operator) + " :param" + placeholder + " ");
//		}
//		
//		//添加参数
//		params.put("param" + placeholder, getObjValue(columnType, operator, value));
//	}
//	
//	
//	/**
//	 * 添加过滤
//	 *
//	 * @param request
//	 */
//	public void addFilter(HttpServletRequest request) {
//		Enumeration<String> names = request.getParameterNames();
//		while (names.hasMoreElements()) {
//			String name = names.nextElement();
//			String value = request.getParameter(name);
//			addFilter(name, value, ColumnType.String, SqlOperator.equal);
//		}
//	}
//	
//	
//	/**
//	 * 带参构造
//	 *
//	 * @param request
//	 */
//	public HqlFilter(HttpServletRequest request) {
//		this.request = request;
//		addFilter(request);
//	}
	
	
}
