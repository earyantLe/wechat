package com.ctsig.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertisUtils {
  public  static String getProperty(String path,String key){
	  Properties prop = new Properties();
		ClassLoader cl = MD5S.class.getClassLoader();
		InputStream stream = cl.getResourceAsStream(path);
		try {
			prop.load(stream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String url = prop.getProperty(key);
		return url;
  }
}
