package com.likg.image;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
	
	/** 链接的父路径 */
	public static final String BASE_LINK_URL = "";
	
	/** 网页目录链接文件路径 */
	public static final String CATALOG_FILE_PATH = "E:/a/dir.txt";
	
	/** 图片保存路径 */
	public static final String IMG_SAVE_PATH = "E:/a/img/";

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		
		//获取网页目录链接
		String content = org.apache.commons.io.FileUtils.readFileToString(new File(CATALOG_FILE_PATH));
		Set<String> linkList = WebPageUtils.getLabelAttr(content, "a", "href");
		
		//过滤字符串
		StringFilterUtil.startsWithFilter(linkList, "http://se2016.xxx/thread");
		
		for(String s : linkList){
			System.out.println(s);
		}
		System.out.println("=============================================================");
		
		SimpleDateFormat sdf = new SimpleDateFormat("MMdd-HHmmss-");
		
		ExecutorService exec = Executors.newFixedThreadPool(10);
		
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
			
			title = FileUtils.processFileName(title);
			
			//保存路径
			String savePath = IMG_SAVE_PATH + sdf.format(new Date()) + title +"/";
			new File(savePath).mkdirs();
			
			//启动下载网络图片的线程
			exec.execute(new DownloadWebImgThread(html, parentUrl, savePath));
			
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
