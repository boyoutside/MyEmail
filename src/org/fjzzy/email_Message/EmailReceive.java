package org.fjzzy.email_Message;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.fjzzy.email_View.*;

import java.awt.List;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class EmailReceive {
	private String user;
	private String password;
	private String emailFrom;
	private String emailContent;
	private String emailSendDate;
	private String emailSubject;
	private EmailListView listView=null;
	Folder folder;
	public EmailReceive(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}
	public EmailReceive() {
		super();
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

	
//	1.实例化Properties()
	private Properties getProp(){
		Properties prop=new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.host", "pop.sina.com.cn");
		return prop;
	}
	//登录连接的方法
	public boolean isConnect() throws MessagingException, IOException{
		boolean connect;
		Session session=Session.getInstance(getProp());
		//获取INBOX
		Store stroe=session.getStore("pop3");
		stroe.connect(user, password);
		connect=stroe.isConnected();
		folder=stroe.getFolder("INBOX");
		
		return connect;
	}
	public Folder getFolder(){
		return folder;
	}
	//
	//调用方法---getProp()
	public void getMessage() throws MessagingException, IOException{
//		String content="";
//		2.建立会话Session对象(prop)，获取远程操作
		Session session=Session.getInstance(getProp());
		session.setDebug(true);
		//获取INBOX
		Store stroe=session.getStore("pop3");
		stroe.connect(user, password);
		Folder folder=stroe.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
	}
	
}
