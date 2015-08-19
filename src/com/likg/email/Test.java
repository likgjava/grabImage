package com.likg.email;

import java.security.Security;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class Test {

	public static void main(String[] args) throws Exception {
		
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		
		String host = "mail.ebinf.com";
		String username = "likaige@ebinf.com";
		String password = "likaigemmb";
		//Properties props = new Properties();
		
		System.setProperty("javax.net.ssl.trustStore", "E:/cacerts");
		Properties props = System.getProperties();
		
		props.put("mail.store.protocol", "pop3"); // 收邮件协议
		props.put("mail.pop3.port", "995");
		props.put("mail.pop3.ssl.enable", "true");
		//props.setProperty("mail.pop3.socketFactory.port", "995");  
		props.put("mail.pop3.ssl.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.pop3.socketFactory.fallback", "false");
		//props.put("mail.pop3.auth.plain.disable", "true");
		
		props.put("javax.net.ssl.trustStore", "E:/cacerts");

		// 获取会话
		Session session = Session.getDefaultInstance(props, null);
		// 获取Store对象，使用POP3协议，也可能使用IMAP协议
		Store store = session.getStore("pop3");
		
		
		// 连接到邮件服务器
		store.connect(host, username, password);
		// 获取该用户Folder对象，并以只读方式打开
		Folder folder = store.getFolder("inbox");
		folder.open(Folder.READ_ONLY);
		// 检索所有邮件，按需填充
		Message message[] = folder.getMessages();
		for (int i = 0; i < message.length; i++) {
			// 打印出每个邮件的发件人和主题
			System.out.println(i + ":" + message[i].getFrom()[0] + "\t" + message[i].getSubject());
		}
		folder.close(false);
		store.close();
	}
}
