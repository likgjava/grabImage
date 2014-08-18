package com.likg.image;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
	
	public static final String BASE_LINK_URL = "";

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		
		//获取网页目录链接
		String content = FileUtils.readFile("D:/dir.txt");
		List<String> linkList = WebPageUtils.getLabelAttr(content, "a", "href");
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMdd-HHmmss-");
		String baseSavePath = "D:/img/";
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		for(String link : linkList) {
			//获取网页源代码
			link = BASE_LINK_URL + link;
			String html = WebPageUtils.getWebPageSource(link);
			if(html==null || "".equals(html.trim())) {
				continue;
			}
			
			//获取网页的父路径
			String parentUrl = link.substring(0, link.lastIndexOf("/")+1);
			System.out.println(parentUrl);
			
			//获取网页标题
			String title = WebPageUtils.getPageTitle(html);
			System.out.println("《"+title+"》开始处理...");
			
			//保存路径
			String savePath = baseSavePath + sdf.format(new Date()) + title +"/";
			new File(savePath).mkdirs();
			
			//启动下载网络图片的线程
			exec.execute(new DownloadWebImgThread(html, parentUrl, savePath));
			
			/*单线程下载
			//获取图片路径
			List<String> srcList = WebPageUtils.getImgSrc(html, parentUrl);
			for(String src : srcList) {
				try {
					ImageUtils.downloadWebImg(src, savePath);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			*/
			System.out.println();
		}
		
		//启动一次顺序关闭，执行以前提交的任务，但不接受新任务
		exec.shutdown();
		//等待线程终止
		while(!exec.isTerminated()){
		}
		
		long end = System.currentTimeMillis();
		System.out.println("图片下载完毕，共耗时(秒)："+(end-start)/1000);
	}
}
