package com.likg.image;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileUtils {

	/**
	 * 读取文件内容
	 * @param filePath 文件路径
	 * @return
	 * @throws Exception
	 * @author likaige
	 * @create 2014年1月22日 下午2:20:07
	 */
	public static String readFile(String filePath) throws Exception {
		StringBuilder content = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filePath));
			String line = null;
			while((line = br.readLine()) != null) {
				content.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(br != null) {
				br.close();
			}
		}
		return content.toString();
	}
	
}
