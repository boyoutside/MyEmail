package org.fjzzy.email_Message;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.fjzzy.email_View.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
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
	private int emailNum=-1;
	private ArrayList<InputStream> attachStream = null;
	private ArrayList<String> attachName = null;
	private ArrayList<String> imgId=null;
	public ArrayList<EmailReceiveSave> receive = null;

	
	//被EmailListView实例化
	public EmailParse(Folder folder, int emailNum) {
		super();
		this.folder = folder;
		this.emailNum = emailNum;
	}
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

	public ArrayList<InputStream> getAttachStream() {
		return attachStream;
	}

	public void setAttachStream(ArrayList<InputStream> attachStream) {
		this.attachStream = attachStream;
	}

	public ArrayList<String> getAttachName() {
		return attachName;
	}

	public void setAttachName(ArrayList<String> attachName) {
		this.attachName = attachName;
	}

	// 获取全部信息
	public void saveBasic() throws MessagingException, IOException {
		folder.open(Folder.READ_WRITE);
		Message[] message = folder.getMessages();
		receive = new ArrayList<EmailReceiveSave>();
		// 关闭远程储存连接
		// content=parse.emailparse(message[2]);
		for (int i = 0, emailCount = message.length; i < emailCount; i++) {
			if(i==emailNum){
				Message msgDelete=message[i];
				msgDelete.setFlag(Flags.Flag.DELETED, true);
			}else{
				MimeMessage msg = (MimeMessage) message[i];
				imgId=new ArrayList<>();
				attachStream = new ArrayList<>();
				attachName = new ArrayList<>();
				emailFrom = getFrom(msg);
				emailContent = getContent(msg);
				emailSendDate = getSendDate(msg);
				emailSubject = getSubject(msg);
				receive.add(new EmailReceiveSave(emailFrom, emailContent,
						emailSendDate, emailSubject, attachStream, attachName,imgId));
				// attachStream.clear();
				// attachName.clear();
			}
		}
		
		folder.close(false);
	}
	
	//创建邮件列表
	public void createTable(Table table) throws MessagingException, IOException {
		table.removeAll();
		TableItem item;
		new org.eclipse.swt.widgets.TableColumn(table, SWT.CENTER)
				.setText("发件人                                            ");
		table.getColumn(0).pack();
		new org.eclipse.swt.widgets.TableColumn(table, SWT.CENTER)
				.setText("主题                                                          ");
		table.getColumn(1).pack();
		new org.eclipse.swt.widgets.TableColumn(table, SWT.CENTER)
				.setText("发送日期                                                  ");
		table.getColumn(2).pack();
		for (int row = 0; row < receive.size(); row++) {
			item = new TableItem(table, SWT.None);
			item.setText(0, receive.get(row).getEmailFrom());
			item.setText(1, receive.get(row).getEmailSubject());
			item.setText(2, receive.get(row).getEmailSendDate());
		}
	}

	// 获取邮件主题
	private String getSubject(MimeMessage message) throws MessagingException {
		String subject = "";
		if (message.getSubject() != null) {
			subject = message.getSubject();
		}
		return subject;
	}

	// 获取发件人
	private String getFrom(MimeMessage message) throws MessagingException,
			UnsupportedEncodingException {
		String from = "";
		Address[] froms = message.getFrom();
		if (froms.length < 1) {
			from = "";
		} else {
			InternetAddress address = (InternetAddress) froms[0];
			String person = address.getPersonal();
			if (person != null) {
				person = MimeUtility.decodeText(person);
			} else {
				person = "";
			}
			from = person + "<" + address.getAddress() + ">";
		}
		return from;
	}

	// 获取邮件内容
	private String getContent(MimeMessage message) throws MessagingException,
			IOException {
		String content = "";
		StringBuffer singleEmail = new StringBuffer();
		content = emailparse(message, singleEmail);
		if (content == null) {
			content = "";
		}
		return content;
	}

	// 获取发送日期
	private String getSendDate(MimeMessage message) throws MessagingException {
		Date sendDate;
		sendDate = message.getSentDate();
		if (sendDate == null) {
			return "";
		}
		// return sendDate.toString();
		return new SimpleDateFormat("yyyy年MM月dd日 E HH:mm").format(sendDate);
	}

	//检查图片cid时用到
	public String checkChar(String cid){
		char[] c = cid.toCharArray();
		for(int i = 0;i < cid.length(); i++ ){	
			switch (c[i]) {
			case '\\':
			case '/':
			case ':':
			case '*':
			case '?':
			case '<':
			case '>':
			case '"':
				c[i] = '-';
				break;
			default:
				break;
			}
		}
		return new String(c);
	}

	//邮件解析
	StringBuffer content = new StringBuffer();
	public String emailparse(Part msg, StringBuffer content)
			throws MessagingException {
		if (msg.isMimeType("text/*")) {
			try {
				content.append((String) msg.getContent());
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} else if (msg.isMimeType("multipart/*")) {
			Multipart multipart=null;
			try {
				multipart = (Multipart) msg.getContent();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			int count = multipart.getCount();
			for (int i = 0; i < count; i++) {
				BodyPart bodypart = multipart.getBodyPart(i);
				if (bodypart.getDisposition() != null) {
					// if(bodypart.getContentType().indexOf("application")!=-1){
					if (bodypart.getFileName() != null) {
						try {
							attachStream.add(bodypart.getInputStream());
							attachName.add(MimeUtility.decodeText(bodypart
									.getFileName()));
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						
						System.out.println(attachName.size());
					}
				}
				emailparse(bodypart, content);
			}
		}else if(msg.isMimeType("application/octet-stream")||msg.isMimeType("image/*")){
			if(msg.getContentType().indexOf("name")<0||msg.isMimeType("image/*")){
				if(msg.getHeader("Content-id")!=null){
					String cid=msg.getHeader("Content-id")[0];
					cid = checkChar(cid);
					File file=new File("d:/EmailImg");
					if(!file.exists()){
						file.mkdir();
					}
					//保存图片ID
					imgId.add(cid);
					File imgFile=new File("d:/EmailImg/"+cid+".bin");
					FileOutputStream output=null;
					BufferedOutputStream bufferOut=null;
					InputStream input=null;
					try {
						input = msg.getInputStream();
						output=new FileOutputStream(imgFile);
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
					bufferOut=new BufferedOutputStream(output);
					byte[] b=new byte[512];
					try {
						while(input.read(b)!=-1){
							try {
								bufferOut.write(b);
							} catch (IOException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							}
						}
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}finally{
						try{
							if(bufferOut!=null){
								bufferOut.close();
							}
							if(output!=null){
								output.close();
							}
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
					System.out.println(cid);
				}
			}
		}
		return content.toString();
	}
}
