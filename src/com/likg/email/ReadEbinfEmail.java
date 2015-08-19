package com.likg.email;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeUtility;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * 首先利用InstallCert导入证书
 * @author likaige
 * @create 2015年8月19日 下午4:26:39
 */
public class ReadEbinfEmail {
	
	private static final String host = "mail.ebinf.com";
	private static final String username = "likaige@ebinf.com";
	private static final String password = "likaigemmb";
	
	//附件保存目录
	private static final String ATTACH_PATH = "D:/emailAttach/";

	public static void main(String[] args) throws Exception {
		
		Store store = getStore();
		
		//连接到邮件服务器
		store.connect(host, username, password);
		
		// 获取该用户Folder对象，并以只读方式打开
		Folder folder = store.getFolder("inbox");
		folder.open(Folder.READ_ONLY);
		
		// 检索所有邮件，按需填充
		Message message[] = folder.getMessages();
		for (int i = 0; i < message.length; i++) {
			// 打印出每个邮件的发件人和主题
			String subject = message[i].getSubject();
			System.out.println(i + ":" + message[i].getFrom()[0] + "\t" + subject);
			
			try {
				saveAttachMent((Part) message[i]);
			} catch (Exception e) {
				System.out.println("保存邮件["+subject+"]中附件时出现异常！");
				e.printStackTrace();
			}
		}
		folder.close(false);
		store.close();
	}
	
	public static Store getStore() throws Exception{
		//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		
		//System.setProperty("javax.net.ssl.trustStore", "E:/cacerts");
		Properties props = System.getProperties();
		
		props.put("mail.store.protocol", "pop3"); // 收邮件协议
		props.put("mail.pop3.port", "995");
		props.put("mail.pop3.ssl.enable", "true");
		props.put("mail.pop3.ssl.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.pop3.socketFactory.fallback", "false");
		//props.setProperty("mail.pop3.socketFactory.port", "995");  
		//props.put("mail.pop3.auth.plain.disable", "true");
		
		props.put("javax.net.ssl.trustStore", "E:/cacerts");

		// 获取会话
		Session session = Session.getDefaultInstance(props, null);
		// 获取Store对象，使用POP3协议，也可能使用IMAP协议
		Store store = session.getStore("pop3");
		return store;
	}
	
	public static void saveAttachMent(Part part) throws Exception {
		String fileName = "";
		if (part.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) part.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				BodyPart mpart = mp.getBodyPart(i);
				String disposition = mpart.getDisposition();
				if ((disposition != null)
						&& ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
					fileName = mpart.getFileName();
					if (fileName.toLowerCase().indexOf("gb2312") != -1) {
						fileName = MimeUtility.decodeText(fileName);
					}
					saveFile(fileName, mpart.getInputStream());
				} else if (mpart.isMimeType("multipart/*")) {
					saveAttachMent(mpart);
				} else {
					fileName = mpart.getFileName();
					if ((fileName != null) && (fileName.toLowerCase().indexOf("GB2312") != -1)) {
						fileName = MimeUtility.decodeText(fileName);
						saveFile(fileName, mpart.getInputStream());
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachMent((Part) part.getContent());
		}
	}
	
	private static void saveFile(String fileName, InputStream in) throws Exception {
		
		
		File file = new File(ATTACH_PATH + fileName);
		FileUtils.writeByteArrayToFile(file, IOUtils.toByteArray(in));
		
//		String osName = System.getProperty("os.name");
//		String storedir = ATTACH_PATH;
//		
//		System.out.println("storefile's path: " + storefile.toString());
//		// for(int i=0;storefile.exists();i++){
//		// storefile = new File(storedir+separator+fileName+i);
//		// }
//		BufferedOutputStream bos = null;
//		BufferedInputStream bis = null;
//		try {
//			bos = new BufferedOutputStream(new FileOutputStream(storefile));
//			bis = new BufferedInputStream(in);
//			int c;
//			while ((c = bis.read()) != -1) {
//				bos.write(c);
//				bos.flush();
//			}
//		} catch (Exception exception) {
//			exception.printStackTrace();
//			throw new Exception("文件保存失败!");
//		} finally {
//			bos.close();
//			bis.close();
//		}
	}
}
