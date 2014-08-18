package com.likg.image;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUtils {

	/**
	 * 下载网络图片
	 * @param urlStr
	 * @param savePath
	 * @throws Exception
	 * @author likaige
	 * @create 2014年1月20日 下午7:17:07
	 */
	public static void downloadWebImg(String urlStr, String savePath) throws Exception {
		String threadName = "["+Thread.currentThread().getName()+"]";
		System.out.println(threadName+"开始下载图片："+urlStr);
		HttpURLConnection conn = null;
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			
			//超时响应时间为3秒
			conn.setConnectTimeout(3 * 1000);
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			in = new DataInputStream(conn.getInputStream());
			out = new DataOutputStream(new FileOutputStream(savePath + ImageUtils.getFileName(urlStr)));
			
			byte[] data = new byte[1024];
			int count = 0;
			while((count = in.read(data)) != -1) {
				out.write(data, 0, count);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.err.println("下载图片<"+urlStr+">时出现异常！");
			throw e;
		} finally {
			if(in != null) {
				in.close();
			}
			if(out != null) {
				out.close();
			}
			if(conn != null) {
				conn.disconnect();
			}
		}
	}
	
	/**
	 * 获取URL中的文件名
	 * <br/>例如："http://www.baidu.com/img/bdlogo.gif" --> bdlogo.gif
	 * @param urlStr 完整URL
	 * @return
	 * @author likaige
	 * @create 2014年1月20日 下午7:02:47
	 */
	public static String getFileName(String urlStr) {
		String fileName = null;
		int lastSeparatorIndex = urlStr.lastIndexOf("/");
		if(lastSeparatorIndex != -1) {
			fileName = urlStr.substring(lastSeparatorIndex);
			//判断文件名是否包含特殊字符
			String[] specialChars = {"/", "\\", "?", ":", ">", "<", ":", "*", "|"};
			for(String c : specialChars) {
				if(fileName.contains(c)) {
					fileName = System.currentTimeMillis() + ".jpg";
					break;
				}
			}
		}
		//自动生成一个
		else {
			fileName = System.currentTimeMillis() + ".jpg";
		}
		return fileName;
	}
	
	public static void main(String[] args) throws Exception {
		//http://www.baidu.com/img/bdlogo.gif
		//String url = "http://www.baidu.com/feizhuliu/102787.html";
		downloadWebImg("http://home.duokan.com/<%= imgAdapt(itm.link_cover,", "D:/img/");
	}
}
