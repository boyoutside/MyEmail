package org.fjzzy.email_Message;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.fjzzy.email_View.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class EmailParse {
	private Folder folder;
	private String emailFrom;
	private String emailContent;
	private String emailSendDate;
	private String emailSubject;
	
	
	public ArrayList<EmailReceiveSave> receive=new ArrayList<EmailReceiveSave>();
	
	public EmailParse(Folder folder) {
		super();
		this.folder = folder;
	}
	public EmailParse() {
		super();
	}
	public ArrayList<EmailReceiveSave> getReceive() {
		return receive;
	}
	public void setReceive(ArrayList<EmailReceiveSave> receive) {
		this.receive = receive;
	}
	public Folder getFolder() {
		return folder;
	}
	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	public String getEmailFrom() {
		return emailFrom;
	}
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	public String getEmailSendDate() {
		return emailSendDate;
	}
	public void setEmailSendDate(String emailSendDate) {
		this.emailSendDate = emailSendDate;
	}
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	
	
	
	public void saveBasic() throws MessagingException, IOException{
		folder.open(Folder.READ_ONLY);
		Message message[]=folder.getMessages();
		//�ر�Զ�̴�������
//		content=parse.emailparse(message[2]);
		for(int i=0,emailCount=message.length;i<emailCount;i++){
			MimeMessage msg=(MimeMessage)message[i];
			emailFrom=getFrom(msg);
			emailContent=getContent(msg);
			emailSendDate=getSendDate(msg);
			emailSubject=getSubject(msg);
			receive.add(new EmailReceiveSave(emailFrom,emailContent,emailSendDate,emailSubject));
		}
		folder.close(false);
	}
	public void createTable(Table table) throws MessagingException, IOException{
//		folder.open(Folder.READ_ONLY);
//		Message message[]=folder.getMessages();
		//�ر�Զ�̴�������
//		content=parse.emailparse(message[2]);
//		for(int i=0,emailCount=message.length;i<emailCount;i++){
//			MimeMessage msg=(MimeMessage)message[i];
//			emailFrom=getFrom(msg);
//			emailContent=getContent(msg);
////			emailSendDate=getSendDate(msg);
//			emailSubject=getSubject(msg);
//			receive.add(new EmailReceiveSave(emailFrom,emailContent,emailSendDate,emailSubject));
//		}
//		folder.close(false);
		TableItem item;
		new org.eclipse.swt.widgets.TableColumn(table, SWT.CENTER).setText("������                                            ");
		table.getColumn(0).pack();
		new org.eclipse.swt.widgets.TableColumn(table, SWT.CENTER).setText("����                                                ");
		table.getColumn(1).pack();
		new org.eclipse.swt.widgets.TableColumn(table, SWT.CENTER).setText("��������                                                  ");
		table.getColumn(2).pack();
		for(int row=0;row<receive.size();row++){
			item=new TableItem(table, SWT.None);
			item.setText(0, receive.get(row).getEmailFrom());
			item.setText(1, receive.get(row).getEmailSubject());
			item.setText(2, receive.get(row).getEmailSendDate());
		}
	}
	//��ȡ�ʼ�������Ϣ���ܷ���
		public void getEmailBasic(){
			for(int i=0;i<receive.size();i++){
				System.out.println("�����ˣ�"+receive.get(i).getEmailFrom());
				System.out.println("���⣺"+receive.get(i).getEmailSubject());
				System.out.println("���ݣ�"+receive.get(i).getEmailContent());
				System.out.println("���ڣ�"+receive.get(i).getEmailSendDate());
			}
		}
	//��ȡ�ʼ�����
		private String getSubject(MimeMessage message) throws MessagingException{
			String subject="";
			if(message.getSubject()!=null){
				subject=message.getSubject();
			}
			return subject;
		}
		//��ȡ������
		private String getFrom(MimeMessage message) throws MessagingException, UnsupportedEncodingException{
			String from="";
			Address[] froms=message.getFrom();
			if(froms.length<1){
				from="";
			}else{
				InternetAddress address=(InternetAddress)froms[0];
				String person=address.getPersonal();
				if(person!=null){
					person=MimeUtility.decodeText(person);
				}else{
					person="";
				}
				from=person+"<"+address.getAddress()+">";
			}
			return from;
		}
		//��ȡ�ʼ�����
		private String getContent(MimeMessage message) throws MessagingException, IOException{
			String content="";
			StringBuffer singleEmail=new StringBuffer();
			content=emailparse(message,singleEmail);
			if(content==null){
				content="";
			}
			return content;
		}
		//��ȡ��������
		private String getSendDate(MimeMessage message) throws MessagingException{
			Date sendDate;
			sendDate=message.getSentDate();
			if(sendDate==null){
				return "";
			}
			return new SimpleDateFormat("yyyy��MM��dd�� E HH:mm").format(sendDate);
		}




	StringBuffer content=new StringBuffer();
	public String emailparse(Part msg,StringBuffer content) throws MessagingException, IOException{
		 if(msg.isMimeType("text/*")){
			 content.append((String)msg.getContent());
		 }else if(msg.isMimeType("multipart/*")){
			 Multipart multipart=(Multipart) msg.getContent();
			 int count=multipart.getCount();
			 for(int i=0;i<count;i++){
				 BodyPart bodypart=multipart.getBodyPart(i);
//				 if(bodypart.getDisposition()!=null){
				 if(bodypart.getContentType().indexOf("application")!=-1){
					 if(bodypart.getFileName()!=null){
//						 InputStream input=bodypart.getInputStream();
//						 BufferedInputStream bufferin=new BufferedInputStream(input);
//						 BufferedOutputStream bufferout=new BufferedOutputStream(new FileOutputStream(
//								 new File("D:/JavaMail_API�ĵ�/�����ʼ�/"+MimeUtility.decodeText(bodypart.getFileName()))
//								 ));
//						 int len=-1;
//						 while((len=bufferin.read())!=-1){
//							 bufferout.write(len);
//							 bufferout.flush();
//						 }
//						 bufferout.close();
//						 bufferin.close();
					 }
				 }
				 emailparse(bodypart,content);
			 }
		 }
		 return content.toString();
	}
}
