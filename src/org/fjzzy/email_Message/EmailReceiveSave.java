package org.fjzzy.email_Message;

public class EmailReceiveSave {
	private String emailFrom;
	private String emailContent;
	private String emailSendDate;
	private String emailSubject;
	
	public EmailReceiveSave(String emailFrom, String emailContent,
			String emailSendDate, String emailSubject) {
		super();
		this.emailFrom = emailFrom;
		this.emailContent = emailContent;
		this.emailSendDate = emailSendDate;
		this.emailSubject = emailSubject;
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
	
}
