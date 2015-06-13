package com.likg.image;

import java.io.File;

import org.apache.commons.lang.StringUtils;

public class Test {

	public static void main(String[] args) {
		
		String text = "0612-223001-骚女演绎激情性感制服诱惑,那腿，那胸那穴，都是那幺的诱人[19P] - 偷拍自拍   涩天使论坛 S E 2 0 1 6 . X X X  ";
		String s = StringUtils.replace(text, " ", "");
		s = StringUtils.replace(s, "，", "");
		s = StringUtils.replace(s, ",", "");
		
		System.out.println(s);
		
		File f = new File("E:/a/dir.txt");
		System.out.println(f.length());
	}
}
