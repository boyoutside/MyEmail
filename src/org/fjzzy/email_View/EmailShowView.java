package org.fjzzy.email_View;

import org.fjzzy.email_Message.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;

import org.fjzzy.email_Message.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

public class EmailShowView {

	protected Shell shell;
	private Browser browserEmail;
	private Label lblNewLabel;
	private Label label;
	private Label label_1;
	private Label lblReceivePerson;
	private Label lblSubject;
	private Label lblSentDate;
	private Button button;
	private Button button_1;
	
	
	private String emailContent;
	private String receivePerson;
	private String subject;
	private String sentDate;
	
	
	public EmailShowView(String emailContent, String receivePerson,
			String subject, String sentDate) {
		super();
		this.emailContent = emailContent;
		this.receivePerson = receivePerson;
		this.subject = subject;
		this.sentDate = sentDate;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
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
	public String getSentDate() {
		return sentDate;
	}
	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}
	/**
	 * Launch the application.
	 * @param args
	 */
	public EmailShowView(){
	}
//	public static void main(String[] args) {
//		try {
//			EmailShowView window = new EmailShowView();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		showEmailContent();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private void showEmailContent(){
		lblReceivePerson.setText(receivePerson);
		lblSubject.setText(subject);
		lblSentDate.setText(sentDate);
		browserEmail.setText(emailContent);
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
//		EmailReceive prop=new EmailReceive();
		shell = new Shell();
		shell.setSize(715, 602);
		shell.setText("SWT Application");
		
		Button btnBack = new Button(shell, SWT.NONE);
		btnBack.setText("<<\u8FD4\u56DE");
		btnBack.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		btnBack.setBounds(559, 514, 102, 39);
		
		browserEmail = new Browser(shell, SWT.NONE);
		browserEmail.setBounds(27, 100, 634, 396);
		
		lblNewLabel = new Label(shell, SWT.RIGHT);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		lblNewLabel.setBounds(35, 10, 74, 21);
		lblNewLabel.setText("\u6536\u4EF6\u4EBA\uFF1A");
		
		label = new Label(shell, SWT.RIGHT);
		label.setText("\u4E3B\u9898\uFF1A");
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label.setBounds(35, 37, 74, 21);
		
		label_1 = new Label(shell, SWT.RIGHT);
		label_1.setText("\u53D1\u9001\u65E5\u671F\uFF1A");
		label_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_1.setBounds(7, 64, 102, 21);
		
		lblReceivePerson = new Label(shell, SWT.NONE);
		lblReceivePerson.setBounds(115, 14, 277, 17);
		lblReceivePerson.setText("New Label");
		
		lblSubject = new Label(shell, SWT.NONE);
		lblSubject.setText("New Label");
		lblSubject.setBounds(115, 41, 277, 17);
		
		lblSentDate = new Label(shell, SWT.NONE);
		lblSentDate.setText("New Label");
		lblSentDate.setBounds(115, 68, 277, 17);
		
		button = new Button(shell, SWT.NONE);
		button.setText("\u2193\u2193\u4E0B\u8F7D\u9644\u4EF6");
		button.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		button.setBounds(27, 514, 116, 39);
		
		button_1 = new Button(shell, SWT.NONE);
		button_1.setText("\u2193\u2193\u4E0B\u8F7D\u56FE\u7247");
		button_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		button_1.setBounds(164, 514, 102, 39);

	}
}
