package org.fjzzy.email_Message;

import java.io.InputStream;
import java.util.ArrayList;

public class EmailReceiveSave {
	private String emailFrom;
	private String emailContent;
	private String emailSendDate;
	private String emailSubject;
	private ArrayList<InputStream> attachStream;
	private ArrayList<String> attachName;
	private ArrayList<String> imgId;
	
	
	
	public EmailReceiveSave(String emailFrom, String emailContent,
			String emailSendDate, String emailSubject,
			ArrayList<InputStream> attachStream, ArrayList<String> attachName,ArrayList<String> imgId) {
		super();
		this.emailFrom = emailFrom;
		this.emailContent = emailContent;
		this.emailSendDate = emailSendDate;
		this.emailSubject = emailSubject;
		this.attachStream = attachStream;
		this.attachName = attachName;
		this.imgId=imgId;
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
	public String getEmailSubject() {
		return emailSubject;
	}
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public String getEmailFrom() {
		return emailFrom;
	}
	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}
	public EmailReceiveSave() {
		super();
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
	public ArrayList<String> getImgId() {
		return imgId;
	}
	
}
