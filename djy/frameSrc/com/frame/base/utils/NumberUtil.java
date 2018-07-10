package com.frame.base.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

public class NumberUtil {

	private static double EARTH_RADIUS = 6378.137;

	private static double rad(double d){
		return d * Math.PI / 180.0;
	}
	
	/**
	 * 计算两个地点间距离
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double getDistance (
			double lat1, double lng1, 
			double lat2, double lng2
	) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;//km
		s = Math.round(s * 1000);//m
		DecimalFormat df = new DecimalFormat("0.00");
		s = parseDouble(df.format(s));
		return s;
	}
	

	/**
	 * 格式化为int数字类型
	 * @param obj
	 * @return
	 */
	public static int parseInt(Object obj){
		if (obj==null)
			return 0;
		String temp = obj.toString().trim();
		if (StringUtil.isEmpty(temp))
			return 0;
		try {
			int num = Integer.parseInt(temp);
			return num;
		} catch(Exception ex) {
			return 0;
		}
	}
	
	
	/**
	 * 格式化为long数字类型
	 * @param obj
	 * @return
	 */
	public static long parseLong(Object obj){
		if (obj==null)
			return 0L;
		String temp = obj.toString().trim();
		if(StringUtil.isEmpty(temp))
			return 0L;
		try {
			long num = Long.parseLong(temp);
			return num;
		} catch(Exception ex) {
			return 0L;
		}
	}
	
	
	/**
	 * 格式化为double数字类型
	 * @param obj
	 * @return
	 */
	public static double parseDouble(Object obj){
		if (obj==null)
			return 0l;
		String temp = obj.toString().trim();
		if (StringUtil.isEmpty(temp))
			return 0l;
		try {
			double num = Double.parseDouble(temp);
			return num;
		} catch(Exception ex) {
			return 0l;
		}
	}
	
	
	/**
	 * 格式化为金额0.00格式的字符串
	 * @param p
	 * @return
	 */
	public static String formatPrice(Object p) {
		if ( p==null )
			return new DecimalFormat("0.00").format(0l);
		double d = parseDouble(p);
		if (d==0l)
			return "0.00";
		return new DecimalFormat("0.00").format(d);
	}
	
	
	/**
	 * 转化为金额格式的double型数值
	 * @param p
	 * @return
	 */
	public static double formatPriceForDouble(Object p){
		return parseDouble(formatPrice(p));
	}
	
	
	/**
	 * 把元的金额格式化为分
	 * @param obj
	 * @return
	 */
	public static String formatMoneyToCent(Object obj){
		try {
			if (obj==null || "".equals(obj)) {
				return "0";
			}
			
			return new BigDecimal(obj.toString()).multiply(new BigDecimal(100)).toBigInteger().toString();
		}catch(Exception e){
			e.printStackTrace();
			return "0";
		}
	}
	
	
	/**
	 * 按位数格式化数字,不足前面补0
	 * @param obj
	 * @param digit
	 * @return
	 */
	public static String formatNumberToDigit(Object obj, int digit) {
		try{
			String format = "";
			
			if (digit<1) {
				format = "0";
			}
			
			for(int i=0;i<digit;i++){
				format += "0";
			}
			
			DecimalFormat nf = new DecimalFormat(format);
			if (obj==null || "".equals(obj)) {
				return nf.format(0);
			} else {
				return nf.format(parseLong(obj));
			}
		}catch(Exception e){
			e.printStackTrace();
			return "0";
		}
	}
	
	
	/**
	 * 按规则格式化金额
	 * @param obj
	 * @param format
	 * @return
	 */
	public static String formatMoney(Object obj, String format) {
		DecimalFormat nf = new DecimalFormat(format);
		try {
			if(obj==null || "".equals(obj)){
				return "0.00";
			}else{
				return nf.format(new BigDecimal(obj.toString()).doubleValue());
			}
		} catch (Exception e) {
			return "0.00";
		}
	}

	
	public static String formatMoney(Object obj) {
		return formatMoney(obj, "0.00");
	}
	
	
	/**
	 * double型数值转为保留小数点两位数的字符串，不补0
	 * @param d
	 * @return
	 */
	public static String formatMoney(double d) {
		NumberFormat nf = new DecimalFormat("##.##");
		return nf.format(d);
	}
	
	
	/**
	 * 从最小值到最大值之间选取几个随机数
	 * @param num 选取几个
	 * @param min 最小值
	 * @param max 最大值
	 * @return
	 */
	public static int[] getRandom(int num, int min, int max){
		if (num > max - min + 1) {
			num = max - min + 1;
		}
		int[] intRet = new int[num]; 
        int intRd = 0; //存放随机数
        int count = 0; //记录生成的随机数个数
        int flag = 0; //是否已经生成过标志
        Random rdm = new Random();
        while ( count < num ) {
             intRd = rdm.nextInt(max - min +1);
             intRd = intRd + min;
             for ( int i=0; i<count; i++) {
                 if ( intRet[i]==intRd ) {
                     flag = 1;
                     break;
                 } else {
                     flag = 0;
                 }
             }
             if (flag==0) {
                 intRet[count] = intRd;
                 count++;
             }
        }	
		return intRet;
	}
	
	
	
}
