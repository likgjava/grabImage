package com.likg.image;

import java.util.List;

/**
 * 下载网络图片的线程
 * @author likaige
 * @create 2014年1月24日 上午9:26:14
 */
public class DownloadWebImgThread implements Runnable {
	
	private String html; //源文件
	private String parentUrl; //网页的父路径
	private String savePath; //图片保存路径
	
	public DownloadWebImgThread(String html, String parentUrl, String savePath) {
		this.html = html;
		this.parentUrl = parentUrl;
		this.savePath = savePath;
	}

	@Override
	public void run() {
		//获取图片路径
		List<String> srcList = WebPageUtils.getImgSrc(html, parentUrl);
		for(String src : srcList) {
			try {
				ImageUtils.downloadWebImg(src, savePath);
			} catch (Exception e) {
				//e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
	}

}
