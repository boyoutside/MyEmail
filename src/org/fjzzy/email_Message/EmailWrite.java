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
	
	
	//����PropЭ��
	private Properties getProp(){
//		1.ʵ����Properties()
		Properties prop=new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.host", "smtp.sina.cn");
		return prop;
	}
	//����TransportЭ��
	private void getTransport(MimeMessage message) throws MessagingException{
//		6.��ȡ���书�ܣ��������ӣ������ʼ����ر����� 
		Transport transport=session.getTransport("smtp");
		transport.connect("smtp.sina.com.cn",user,password);
		transport.sendMessage(message,new Address[] {new InternetAddress(receivePerson)});
		transport.close();
		System.out.println("���ͳɹ���");
	}
	//��Ӹ�������
	private MimeMultipart addAttach(String[] fileName) throws MessagingException, UnsupportedEncodingException{
		String filePath;
		MimeMultipart mimeMultipart=new MimeMultipart("mixed");
		for(int i=0,count=fileName.length;i<count;i++){
			filePath=path+"/"+fileName[i];
			//��������1
			MimeBodyPart part1=new MimeBodyPart();
			DataSource ds1=new FileDataSource(filePath);
			DataHandler dh1=new DataHandler(ds1);
			part1.setDataHandler(dh1);
			part1.setFileName(MimeUtility.encodeText(fileName[i]));
			//��Ӹ���
			mimeMultipart.addBodyPart(part1);
		}
		return mimeMultipart;
	}
	//�����ʼ��ķ���
	public void sentEmail() throws AddressException, MessagingException, UnsupportedEncodingException{
//		2.�����ỰSession����(prop)����ȡԶ�̲���
		session=Session.getInstance(getProp());
//		3.�����ʼ�Message
		MimeMessage message=new MimeMessage(session);
//		4.�����ʼ���ַ����
//		5.��MimeMessage�����ʼ���(������Ҫ�أ������ˣ��ռ��ˣ����⣬����)
		message.setFrom(new InternetAddress(sentPerson));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(receivePerson));
		message.setSubject(subject);
		//���������ĵ�����mimeMultipart
		MimeMultipart mimeMultipart=new MimeMultipart("mixed");
		//����bodyPart
		MimeBodyPart bodyPart=new MimeBodyPart();
		//�ж����޸���
		if(filerName!=null){
			if(filerName.length>0){
				mimeMultipart=addAttach(filerName);
			}
		}
		//����bodyPart��MimeMultipart2
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
