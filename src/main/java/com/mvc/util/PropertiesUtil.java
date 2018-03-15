package com.mvc.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class PropertiesUtil {

		private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
		
		private static Properties props;
		
		static{
			String fileName = "lili.properties";
			props = new Properties();
			try {
				props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
			} catch (UnsupportedEncodingException e) {
				logger.error("������ʽ��֧��", e);
			} catch (IOException e) {
				logger.error("�����ļ���ȡ�쳣", e);
			}
		}
		
		public static String getProperty(String key){
			String value = props.getProperty(key.trim());
			if(value == null || "".equals(value)){
				return null;
			}
			return value.trim();
		}
		
		public static String getProperty(String key, String defaultValue){
			String value = props.getProperty(key.trim());
			if(value == null || "".equals(value)){
				value = defaultValue;
			}
			return value.trim();
		}
		
	
}
