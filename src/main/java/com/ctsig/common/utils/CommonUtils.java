package com.ctsig.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CommonUtils {

	public static Log log = LogFactory.getLog(CommonUtils.class);
	public static final String DATE_PATTERN = "yyyyMMdd";
	public static final String TIMES_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 首字母小写 2016-4-6
	 * @author 韩志伟
	 * @param s
	 * @return
	 */
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(
					Character.toLowerCase(s.charAt(0))).append(s.substring(1))
					.toString();
	}

	/**
	 * 首字母转大写 2016-4-6
	 * @author 韩志伟
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(
					Character.toUpperCase(s.charAt(0))).append(s.substring(1))
					.toString();
	}

	/**
	 * 将字符串转换为 T string Integer Date Timestamp等 2016-4-6
	 * 
	 * @author 韩志伟
	 * @param <T>
	 * @param value
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseStringToObject(String value, Class<T> type) {
		if (value == null || value.length() < 1) {
			return null;
		}
		// 如果是字符串直接返回
		if (String.class.getName().equals(type.getName())) {
			return (T) value;
		}
		// 如果是日期类型
		if (Date.class.getName().equals(type.getName())) {
			// 先用yyyy-MM-dd解析
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = format.parse(value);
				return (T) date;
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			if (null == date) {
				try {
					format = new SimpleDateFormat("yyyyMMdd");
					date = format.parse(value);
					return (T) date;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		// 如果是Double类型
		if (Double.class.getName().equals(type.getName())) {
			return (T) new Double(Double.parseDouble(value));
		}
		// 如果是Integer类型
		if (Integer.class.getName().equals(type.getName())) {
			if (value != null && value.length() > 0) {
				return (T) new Integer(Integer.parseInt(value));
			} else {
				return (T) null;
			}
		}
		return null;
	}

	/**
	 * 将字符串转为数组 2016-4-6
	 * 
	 * @author 韩志伟
	 * @param strOrigin
	 *            转换字符串
	 * @param separator
	 *            切分标志 ","等
	 * @return
	 */
	public static String[] parseStringToArray(String strOrigin, String separator) {
		if (strOrigin == null || strOrigin.length() < 1) {
			return null;
		}
		String[] returnArray = strOrigin.split(separator);
		return returnArray;
	}

	/**
	 * 将主键数组转为 sql中的in参数 2016-4-6
	 * 
	 * @author 韩志伟
	 * @param primaryKeys
	 *            主键id的数组
	 * @return ('a','b','c','d')
	 */
	public static String parseArrayToSql(String[] primaryKeys) {
		if (primaryKeys != null && primaryKeys.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (String id : primaryKeys) {
				sb.append(",'" + id + "'");
			}
			return "(" + sb.substring(1) + ")";
		} else {
			return "('')";
		}
	}

	/**
	 * 判断字符串是否为空 2016-4-6
	 * 
	 * @author 韩志伟
	 * @param value
	 *            被判断的字符串
	 * @return 不为空返回true 为空返回false
	 */
	public static boolean isNotNull(String value) {
		if (value != null && !value.trim().equals("")
				&& !"null".equalsIgnoreCase(value.trim())) {
			// 不为null 不为空 不为"null"
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为null 2016-4-6
	 * @author 韩志伟 
	 * @param value
	 * @return
	 */
	public static boolean isNull(String value) {
		return !isNotNull(value);
	}

	/**
	 * 判断集合是否为空
	 * 
	 * @author 韩志伟 2016-4-6
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isNotEmpty(Collection list) {
		if (list != null && list.size() > 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(Collection list) {
		return !isNotEmpty(list);
	}

	/**
	 * 将数组转换为字符串 以指定的分隔符分割 2016-4-6
	 * 
	 * @author 韩志伟
	 * @param strArray
	 *            被转换数组
	 * @param splitStr
	 *            分隔符
	 * @return 分割完成字符串
	 */
	public static String convertArrayToString(String[] strArray, String splitStr) {
		String returnStr = null;
		if (strArray != null && strArray.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (String t : strArray) {
				sb.append(splitStr).append(t);
			}
			returnStr = sb.substring(splitStr.length());
		}
		return returnStr;
	}

	/**
	 * 根据文件名将属性文件解析为map
	 * 
	 * @author 韩志伟 2016-4-6
	 * @param baseName
	 * @return
	 */
	public static Map<String, String> convertBundleToMap(String baseName) {
		Map<String, String> map = new HashMap<String, String>();
		ResourceBundle rb = ResourceBundle.getBundle(baseName);
		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			map.put(key, rb.getString(key));
		}
		return map;
	}

	/**
	 * 获得当前项目的物理路径 ---- E:/workspaces/basic/WebRoot 2016-4-6
	 * 
	 * @author 韩志伟
	 * @return
	 */
	public static String getProjectWebRootPath() {
		String physicalPath = "";
		String className = "CommonUtils.class";
		physicalPath = CommonUtils.class.getResource(className).getPath();
		int startPoint = 0;
		if (physicalPath.indexOf(":") != -1) {
			startPoint = 1;
		}
		physicalPath = physicalPath.substring(startPoint,
				physicalPath.indexOf("WEB-INF") - 1).replaceAll(":", "\\:");
		return physicalPath;

	}

	/**
	 * 去掉换行符 2016-4-6
	 * 
	 * @author 韩志伟
	 * @param origStr
	 * @return
	 */
	public static String replaceSpecialStringCharForJs(String origStr) {
		String target = null;
		if (isNotNull(origStr)) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(origStr);
			target = m.replaceAll("");
		}
		return target;
	}

	/**
	 * 判断是否为邮箱 2016-4-6
	 * 
	 * @author 韩志伟
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.length() <= 0) {
			return false;
		}
		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}


	/**
	 * 生成json字符串
	 * 
	 * @author 韩志伟 2016-4-6
	 * @param obj
	 *            被打印的对象,一般为map
	 * @return
	 */
	public static String toJson(Object obj) {
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(Date.class, new DateSerializer());
		gb.registerTypeAdapter(Timestamp.class, new TimeSerializer());
		gb.serializeNulls(); // 处理空值
		Gson g = gb.create();
		return g.toJson(obj);

	}

	@SuppressWarnings("unchecked")
	public static final List<Class> clsList = new ArrayList<Class>();
	static {
		clsList.add(String.class);
	}

	/**
	 * @author 韩志伟 2016-4-6
	 * @param obj
	 *            需要输出的对象
	 * @param needList
	 *            仅输出对象的这些属性
	 * @return
	 */
	public static String toJson(Object obj, final List<String> needList) {
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(Date.class, new DateSerializer());
		gb.registerTypeAdapter(Timestamp.class, new TimeSerializer());
		gb.setExclusionStrategies(new ExclusionStrategy() {
			@SuppressWarnings("unchecked")
			public boolean shouldSkipField(FieldAttributes attr) {
				// 返回true就表示需要过滤
				Class cls = attr.getDeclaringClass();
				if (clsList.contains(cls)) {
					return !needList.contains(attr.getName());
				}
				return false;
			}

			public boolean shouldSkipClass(Class<?> cls) {
				return false;
			}
		});
		Gson g = gb.create();
		// log.info("toJson:"+g.toJson(obj));
		return g.toJson(obj);
	}

	/**
	 * 输入成功信息 自动添加resCode为0
	 * 
	 * @author 韩志伟 2016-4-6
	 * @param out
	 * @param map
	 */
	public static void successMsg(PrintWriter out, Map<String, Object> map) {
		map.put("resCode", "0");
		out.print(toJson(map));
	}

	/**
	 * 输出成功信息 只有一条描述信息或者一个object对象
	 * 
	 * @author 韩志伟 2016-4-6
	 * @param out
	 * @param msg
	 */
	public static void successMsg(PrintWriter out, Object msg) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resCode", "0");
		map.put("resMsg", msg);
		out.print(toJson(map));
	}

	/**
	 * 输出成功信息 传入需要输出的字段list
	 * 
	 * @author 韩志伟 2016-4-6
	 * @param out
	 * @param map
	 * @param needList
	 */
	public static void successMsg(PrintWriter out, Map<String, Object> map,
			final List<String> needList) {
		map.put("resCode", "0");
		out.print(toJson(map, needList));
	}

	/**
	 * 将json解析成map
	 * 
	 * @author 韩志伟 2016-4-6
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parserJsonToMap(String json) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.fromObject(json);
		Iterator<String> keys = jsonObject.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String value = jsonObject.get(key).toString();
			if (value.startsWith("{") && value.endsWith("}")) {
				map.put(key, parserJsonToMap(value));
			} else {
				map.put(key, jsonObject.get(key));
			}
		}
		return map;
	}

	/**
	 * 处理date类型的json
	 * 
	 * @author 韩志伟
	 */
	public static class DateSerializer implements JsonSerializer<Date>,
			JsonDeserializer<Date> {
		// java-->json
		public JsonElement serialize(Date date, Type arg1,
				JsonSerializationContext arg2) {
			return new JsonPrimitive(new SimpleDateFormat(DATE_PATTERN)
					.format(date));
		}

		// json-->java
		public Date deserialize(JsonElement json, Type arg1,
				JsonDeserializationContext context) throws JsonParseException {
			try {
				return new SimpleDateFormat(DATE_PATTERN).parse(json
						.getAsString());
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}

	}

	/**
	 * 处理时间类型的json
	 * 
	 * @author 韩志伟 2016-4-6
	 * 
	 */
	public static class TimeSerializer implements JsonSerializer<Timestamp>,
			JsonDeserializer<Timestamp> {
		// java-->json
		public JsonElement serialize(Timestamp date, Type arg1,
				JsonSerializationContext arg2) {
			return new JsonPrimitive(new SimpleDateFormat(TIMES_PATTERN)
					.format(date));
		}

		// json-->java
		public Timestamp deserialize(JsonElement json, Type arg1,
				JsonDeserializationContext context) throws JsonParseException {
			try {
				return new Timestamp(new SimpleDateFormat(TIMES_PATTERN).parse(
						json.getAsString()).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	private static String ftpProperties = "ftpparameters";
	/**
	 * 
	 * 获取ftp配置属性文件 2016-4-6
	 * 
	 * @author 韩志伟
	 * @param key
	 * @return
	 */
	public static String getFtpString(String key) {
		String value = null;
		try {
			value = getString(ftpProperties, key);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return value;
	}

	/**
	 * 从资源包获取给定键的字符串。
	 * 
	 * @param baseName
	 *            --资源文件的文件名(不包含扩展名) 资源文件位于项目的classpath下
	 * @param key
	 *            --资源文件中的指定键值
	 * @throws Exception
	 */
	public static String getString(String baseName, String key) {
		String value = null;
		try {
			ResourceBundle rb = ResourceBundle.getBundle(baseName);
			try {
				value = rb.getString(key);
			} catch (Exception e2) {
				log.error("获取值失败");
				e2.printStackTrace();
			}
		} catch (NullPointerException e) {
			log.error("资源文件名称为空");
			log.error(e.getMessage(), e);
		} catch (MissingResourceException e1) {
			log.error("找不到资源文件");
			// e1.printStackTrace();
			log.error(e1.getMessage());
		}
		return value;
	}
	
	/**
	 * @author 韩志伟 2016-4-6
	 * @param requestParam
	 *            请求参数
	 * @param address
	 *            请求地址
	 * @param encoding
	 *            获取返回数据的编码方式
	 * @return 返回请求的结果
	 */
	public static String postURL(String requestParam, String address,
			String encoding) {
		String rec_string = "";
		URL url = null;
		log.debug("postURL:"+address);
		HttpURLConnection urlConn = null;
		try {
			/* 得到url地址的URL类 */
			url = new URL(address);
			/* 获得打开需要发送的url连接 */
			urlConn = (HttpURLConnection) url.openConnection();
			/* 设置连接超时时间 */
			urlConn.setConnectTimeout(30000);
			/* 设置读取响应超时时间 */
			urlConn.setReadTimeout(30000);
			/* 设置post发送方式 */
			urlConn.setRequestMethod("POST"); 
			/* 发送requestParam */
			urlConn.setDoOutput(true); 
			OutputStream out = urlConn.getOutputStream();
			out.write(requestParam.getBytes());
			out.flush();
			out.close();
			/* 发送完毕 获取返回流，解析流数据 */
			BufferedReader rd = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), encoding));
			StringBuffer sb = new StringBuffer();
			int ch;
			while ((ch = rd.read()) > -1) {
				sb.append((char) ch);
			}
			rec_string = sb.toString().trim();
			/* 解析完毕关闭输入流 */
			rd.close();
		} catch (Exception e) {
			/* 异常处理 */
			rec_string = "-107";
			System.out.println(e);
		} finally {
			/* 关闭URL连接 */
			if (urlConn != null) {
				urlConn.disconnect();
			}
		}
		/* 返回响应内容 */
		return rec_string;
	}

	/**
	 * 补齐不足长度
	 * 
	 * @param length
	 *            长度
	 * @param number
	 *            数字
	 * @return
	 */
	public static String lpad(int length, int number) {
		String f = "%0" + length + "d";
		return String.format(f, number);
	}

	/**
	 * io流写入文件
	 * 
	 * @param file     保存文件
	 * @param content  文件内容
	 * @param type  true,保存原有文件内容
	 * @return
	 */
	public static Boolean writerFile(File file, String content,Boolean type) {
		FileWriter writer;
		try {
			writer = new FileWriter(file, type);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 创建文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static boolean createFile(File file) throws IOException {
		if (!file.exists()) {
			makeDir(file.getParentFile());
		}
		return file.createNewFile();
	}

	/**
	 * 创建文件夹
	 * 
	 * @param dir
	 */
	public static void makeDir(File dir) {
		if (!dir.getParentFile().exists()) {
			makeDir(dir.getParentFile());
		}
		dir.mkdir();
	}
	
	public static String replace(String s, char oldSub, String newSub) {
		if ((s == null) || (newSub == null)) {
			return null;
		}

		char[] c = s.toCharArray();

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < c.length; i++) {
			if (c[i] == oldSub) {
				sb.append(newSub);
			}
			else {
				sb.append(c[i]);
			}
		}

		return sb.toString();
	}
	
	/**
	 * <p>Object转String ,空字符串返回ifNull, 非空返回自己</p>	.
	 * @returnS
	 * @throws Exception
	 */
	public static String nvl(String str, String ifNull) {
		if ( str == null ) return ifNull;
		str = str.trim();
		if ( str.equals("") || "null".equals(str)) {
			return ifNull;
		} else {
			return str;
		}
	}
}
