package com.frame.fssclient.api.json;

import net.sf.json.JSONException;
import net.sf.json.util.JavaIdentifierTransformer;
import net.sf.json.util.PropertySetStrategy;

public class BaseJson {

	// json 转 对象 时，忽略属性不存在的情况。
	public static class PropertyStrategyExt extends PropertySetStrategy {

		private PropertySetStrategy original;
		
		public PropertyStrategyExt(PropertySetStrategy original) {
			this.original = original;
		}
		
		@Override
		public void setProperty(Object o, String string, Object o1) throws JSONException {
			try {
				original.setProperty(o, string, o1);
			} catch (Exception ex) {
				//ignore
			}
		}
	}
	
	// json 转 对象，解决首字母为大写时，转化的值为空
	public static class JavaIdentifierTransformerExt extends JavaIdentifierTransformer {
		
		@Override
		public String transformToJavaIdentifier(String str) {
			// System.out.println("属性：" + str);
			char[] chars = str.toCharArray();
			if ( chars.length > 1 
					&& Character.isUpperCase(chars[0]) 
					&& Character.isUpperCase(chars[1]) ) {	// 长度大于1，并且前两个字符大写时，返回原字符串 
				
				return new String(chars);
				
			} else {	// 其他情况下，把原字符串的首个字符小写处理后返回
				chars[0] = Character.toLowerCase(chars[0]);
				return new String(chars);
			}
		}
		
	}
	
	
}
