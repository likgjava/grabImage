package com.likg.doubleball;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.likg.image.WebPageUtils;

public class DoubleBallDownload {

	public static void main(String[] args) throws IOException {
		String urlStr = "http://baidu.lecai.com/lottery/draw/view/50?phase=2003001";
		String html = WebPageUtils.getWebPageSource(urlStr);
		System.out.println(html);
		
		FileUtils.write(new File("D:/f.html"), html);
	}
}


