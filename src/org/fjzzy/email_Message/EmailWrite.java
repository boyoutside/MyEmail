package org.fjzzy.email_Message;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class EmailWrite {
	private String sentPerson;
	private String receivePerson;
	private String subject;
	private String content;
	private String user;
	private String password;
	private String[] filerName;
	private String path;
	
	Session session=null;
	
	
	
	public EmailWrite(String sentPerson, String receivePerson, String subject,
			String content, String user, String password, String[] filerName,
			String path) {
		super();
		this.sentPerson = sentPerson;
		this.receivePerson = receivePerson;
		this.subject = subject;
		this.content = content;
		this.user = user;
		this.password = password;
		this.filerName = filerName;
		this.path = path;
	}
	public String getSentPerson() {
		return sentPerson;
	}
	public void setSentPerson(String sentPerson) {
		this.sentPerson = sentPerson;
	}
	public String getReceivePerson() {
		return receivePerson;
	}
	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setFilerName(String[] filerName) {
		this.filerName = filerName;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
	//配置Prop协议
	private Properties getProp(){
//		1.实例化Properties()
		Properties prop=new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.host", "smtp.sina.cn");
		return prop;
	}
	//配置Transport协议
	private void getTransport(MimeMessage message) throws MessagingException{
//		6.获取传输功能，建立连接，发送邮件，关闭连接 
		Transport transport=session.getTransport("smtp");
		transport.connect("smtp.sina.com.cn",user,password);
		transport.sendMessage(message,new Address[] {new InternetAddress(receivePerson)});
		transport.close();
		System.out.println("发送成功！");
	}
	//添加附件方法
	private MimeMultipart addAttach(String[] fileName) throws MessagingException, UnsupportedEncodingException{
		String filePath;
		MimeMultipart mimeMultipart=new MimeMultipart("mixed");
		for(int i=0,count=fileName.length;i<count;i++){
			filePath=path+"/"+fileName[i];
			//创建附件1
			MimeBodyPart part1=new MimeBodyPart();
			DataSource ds1=new FileDataSource(filePath);
			DataHandler dh1=new DataHandler(ds1);
			part1.setDataHandler(dh1);
			part1.setFileName(MimeUtility.encodeText(fileName[i]));
			//添加附件
			mimeMultipart.addBodyPart(part1);
		}
		return mimeMultipart;
	}
	//发送邮件的方法
	public void sentEmail() throws AddressException, MessagingException, UnsupportedEncodingException{
//		2.建立会话Session对象(prop)，获取远程操作
		session=Session.getInstance(getProp());
//		3.创建邮件Message
		MimeMessage message=new MimeMessage(session);
//		4.创建邮件地址对象
//		5.用MimeMessage创建邮件。(基本四要素：发件人，收件人，主题，正文)
		message.setFrom(new InternetAddress(sentPerson));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(receivePerson));
		message.setSubject(subject);
		//附件和正文的容器mimeMultipart
		MimeMultipart mimeMultipart=new MimeMultipart("mixed");
		//正文bodyPart
		MimeBodyPart bodyPart=new MimeBodyPart();
		//判断有无附件
		if(filerName!=null){
			if(filerName.length>0){
				mimeMultipart=addAttach(filerName);
			}
		}
		//正文bodyPart的MimeMultipart2
		MimeMultipart mimeMultipart2=new MimeMultipart("related");
		MimeBodyPart html=new MimeBodyPart();
		html.setContent(content, "text/html;charset=utf-8");
		mimeMultipart2.addBodyPart(html);
		
		bodyPart.setContent(mimeMultipart2);
		mimeMultipart.addBodyPart(bodyPart);
		message.setContent(mimeMultipart);
		message.saveChanges();
		getTransport(message);
	}
}
