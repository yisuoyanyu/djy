package com.frame.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathFmtUtil {	
	
	public static String parse (String input) {
		
		Pattern pattern = Pattern.compile( "\\{([^\\}]+)\\}", Pattern.CASE_INSENSITIVE  );
		Matcher matcher = pattern.matcher(input);
		
		Date currentDate = new Date();
		
		StringBuffer sb = new StringBuffer();
		
		while ( matcher.find() ) {
			
			matcher.appendReplacement(sb, PathFmtUtil.getString( matcher.group( 1 ), currentDate ) );
			
		}
		
		matcher.appendTail(sb);
		
		return sb.toString();
	}
		
	
	public static String parse (String input, String filename) {
	
		Pattern pattern = Pattern.compile( "\\{([^\\}]+)\\}", Pattern.CASE_INSENSITIVE  );
		Matcher matcher = pattern.matcher(input);
		String matchStr = null;
		
		Date currentDate = new Date();
		
		StringBuffer sb = new StringBuffer();
		
		while ( matcher.find() ) {
			
			matchStr = matcher.group( 1 );
			if ( matchStr.indexOf( "filename" ) != -1 ) {
				filename = filename.replace( "$", "\\$" ).replaceAll( "[\\/:*?\"<>|]", "" );
				matcher.appendReplacement(sb, filename );
			} else {
				matcher.appendReplacement(sb, PathFmtUtil.getString( matchStr, currentDate ) );
			}
			
		}
		
		matcher.appendTail(sb);
		
		return sb.toString();
	}
	
	
	/**
	 * 格式化路径, 把windows路径替换成标准路径
	 * @param input 待格式化的路径
	 * @return 格式化后的路径
	 */
	public static String format (String input) {
		
		return input.replace( "\\", "/" );
		
	}
	
	
	//===============================================================================
	
	private static final String TIME = "time";
	private static final String FULL_YEAR = "yyyy";
	private static final String YEAR = "yy";
	private static final String MONTH = "mm";
	private static final String DAY = "dd";
	private static final String HOUR = "hh";
	private static final String MINUTE = "ii";
	private static final String SECOND = "ss";
	private static final String RAND = "rand";
	
	private static String getString (String pattern, Date date) {
		
		pattern = pattern.toLowerCase();
		
		// time 处理
		if ( pattern.indexOf( PathFmtUtil.TIME ) != -1 ) {
			return PathFmtUtil.getTimestamp( date );
		} else if ( pattern.indexOf( PathFmtUtil.FULL_YEAR ) != -1 ) {
			return PathFmtUtil.getFullYear( date );
		} else if ( pattern.indexOf( PathFmtUtil.YEAR ) != -1 ) {
			return PathFmtUtil.getYear( date );
		} else if ( pattern.indexOf( PathFmtUtil.MONTH ) != -1 ) {
			return PathFmtUtil.getMonth( date );
		} else if ( pattern.indexOf( PathFmtUtil.DAY ) != -1 ) {
			return PathFmtUtil.getDay( date );
		} else if ( pattern.indexOf( PathFmtUtil.HOUR ) != -1 ) {
			return PathFmtUtil.getHour( date );
		} else if ( pattern.indexOf( PathFmtUtil.MINUTE ) != -1 ) {
			return PathFmtUtil.getMinute( date );
		} else if ( pattern.indexOf( PathFmtUtil.SECOND ) != -1 ) {
			return PathFmtUtil.getSecond( date );
		} else if ( pattern.indexOf( PathFmtUtil.RAND ) != -1 ) {
			return PathFmtUtil.getRandom( pattern );
		}
		
		return pattern;
		
	}

	private static String getTimestamp ( Date date ) {
		return date.getTime() + "";
	}
	
	private static String getFullYear ( Date date ) {
		return new SimpleDateFormat( "yyyy" ).format( date );
	}
	
	private static String getYear ( Date date ) {
		return new SimpleDateFormat( "yy" ).format( date );
	}
	
	private static String getMonth ( Date date ) {
		return new SimpleDateFormat( "MM" ).format( date );
	}
	
	private static String getDay ( Date date ) {
		return new SimpleDateFormat( "dd" ).format( date );
	}
	
	private static String getHour ( Date date ) {
		return new SimpleDateFormat( "HH" ).format( date );
	}
	
	private static String getMinute ( Date date ) {
		return new SimpleDateFormat( "mm" ).format( date );
	}
	
	private static String getSecond ( Date date ) {
		return new SimpleDateFormat( "ss" ).format( date );
	}
	
	private static String getRandom ( String pattern ) {
		
		int length = 0;
		pattern = pattern.split( ":" )[ 1 ].trim();
		
		length = Integer.parseInt( pattern );
		
		return ( Math.random() + "" ).replace( ".", "" ).substring( 0, length );
		
	}

}
