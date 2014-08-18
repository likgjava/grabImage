package com.likg.image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebPageUtils {

	/** 
	 * 网页字符编码
	 */
	public static final String CHARSET = "UTF-8";
	
	/** 
	 * 基本路径，
	 */
	public static final String BASE_URL = "";
	
	/**
	 * 获取标签属性内容
	 * @param html 源文件
	 * @param labelName 标签名称
	 * @param attrName 属性名称
	 * @return
	 * @author likaige
	 * @create 2014年1月22日 上午10:52:31
	 */
	public static List<String> getLabelAttr(String html, String labelName, String attrName) {
		List<String> attrList = new ArrayList<String>();
		//<[img][^>]*\\ssrc\\s*=\\s*[\"']?([^'\">]*)[\"'>]?[^>]*>
		String regex = "<["+labelName+"][^>]*\\s"+attrName+"\\s*=\\s*[\"']?([^'\">]*)[\"'>]?[^>]*>";
		Matcher m = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(html);
		while (m.find()) {
			String attr = m.group(1);
			if(attr!=null && attr.trim().length()>0) {
				attrList.add(attr.trim());
			}
		}
		return attrList;
	}
	
	/**
	 * 获取img标签的src属性内容
	 * @param html 源文件
	 * @param parentUrl 网页的父路径
	 * @return
	 * @author likaige
	 * @create 2014年1月22日 下午1:39:54
	 */
	public static List<String> getImgSrc(String html, String parentUrl) {
		List<String> srcList = WebPageUtils.getLabelAttr(html, "img", "src");
		
		//过滤无效图片路径
		for(int i=srcList.size()-1; i>=0; i--) {
			String src = srcList.get(i);
			if(src==null || src.trim().length()==0) {
				srcList.remove(i);
			}
			else if("about:blank".equalsIgnoreCase(src)) {
				srcList.remove(i);
			}
		}
		
		//补全图片路径
		for(int i=srcList.size()-1; i>=0; i--) {
			String src = srcList.get(i);
			if(!src.startsWith("http")) {
				srcList.set(i, parentUrl+src);
			}
		}
		return srcList;
	}
	
	/**
	 * 获取标签内容
	 * @param html 源文件
	 * @param labelName 标签名称
	 * @return
	 * @author likaige
	 * @create 2014年1月22日 上午9:54:53
	 */
	public static List<String> getLabelText(String html, String labelName) {
		List<String> textList = new ArrayList<String>();
		//<title>([^([</title>])]*)</title>
		String regex = "<"+labelName+">([^([</"+labelName+">])]*)</"+labelName+">";
		Matcher m1 = Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(html);
		while (m1.find()) {
			String text = m1.group(1);
			textList.add(text);
		}
		return textList;
	}
	
	/**
	 * 获取网页标题
	 * @param html 源文件
	 * @return
	 * @author likaige
	 * @create 2014年1月22日 上午10:46:04
	 */
	public static String getPageTitle(String html) {
		String title = null;
		List<String> textList = WebPageUtils.getLabelText(html, "title");
		if(!textList.isEmpty()) {
			title = textList.get(0);
		}
		return title;
	}
	
	/**
	 * 获取网页源代码
	 * @param urlStr
	 * @return
	 * @throws Exception
	 * @author likaige
	 * @create 2014年1月20日 下午7:52:36
	 */
	public static String getWebPageSource(String urlStr) {
		StringBuilder source = new StringBuilder();
		HttpURLConnection conn = null;
		BufferedReader br = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			//超时响应时间为3秒
			conn.setConnectTimeout(3 * 1000);
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), WebPageUtils.CHARSET));
			
			char[] data = new char[1024];
			int count = 0;
			while((count = br.read(data)) != -1) {
				source.append(data, 0, count);
			}
		} catch (Exception e) {
			System.out.println("获取网页<"+urlStr+">源代码时出现异常！");
			e.printStackTrace();
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				conn.disconnect();
			}
		}
		return source.toString();
	}
	
	
	public static void main(String[] args) throws Exception {
		//http://www.baidu.com/img/bdlogo.gif
		String url = "http://www.163.com/";
		
		String html = WebPageUtils.getWebPageSource(url);
		//System.out.println(html);
		//System.out.println("title="+WebPageUtils.getImgSrc(html));
		System.out.println("title="+WebPageUtils.getPageTitle(html));
		System.out.println(WebPageUtils.getLabelAttr(html, "img", "src").size());
	}
}
