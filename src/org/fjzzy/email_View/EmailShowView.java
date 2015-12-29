package org.fjzzy.email_View;

import org.fjzzy.email_Message.*;

import java.awt.FileDialog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;

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
	private Label lblSentPerson;
	private Label lblSubject;
	private Label lblSentDate;
	private Button btnLoadAttach;
	
	
	private String emailContent;
	private String sentPerson;
	private String subject;
	private String sentDate;
	private ArrayList<InputStream> attachStream;
	private ArrayList<String> attachName;
	private String user;
	private String password;
	private Button btnReply;
	
	
	//接收邮件 主窗体EmailListView传送过来的信息
	public EmailShowView(String emailContent, String sentPerson,
			String subject, String sentDate,
			ArrayList<InputStream> attachStream, ArrayList<String> attachName,
			String user, String password) {
		super();
		this.emailContent = emailContent;
		this.sentPerson = sentPerson;
		this.subject = subject;
		this.sentDate = sentDate;
		this.attachStream = attachStream;
		this.attachName = attachName;
		this.user = user;
		this.password = password;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	public void setSentPerson(String sentPerson) {
		this.sentPerson = sentPerson;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}
	public void setAttachStream(ArrayList<InputStream> attachStream) {
		this.attachStream = attachStream;
	}
	public void setAttachName(ArrayList<String> attachName) {
		this.attachName = attachName;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setPassword(String password) {
		this.password = password;
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
	//在邮件显示窗体显示内容
	private void showEmailContent(){
		lblSentPerson.setText(sentPerson);
		lblSubject.setText(subject);
		lblSentDate.setText(sentDate);
		browserEmail.setText(emailContent);
		if(attachName.size()<=0){
			btnLoadAttach.setEnabled(false);
			System.out.println("不可编辑");
		}else{
			btnLoadAttach.setEnabled(true);System.out.println("可编辑");
		}
	}
	//下载附件
	private void loadAttach(String fileName) throws IOException {
		int count=attachStream.size();
		for(int i=0;i<count;i++){
			//缓冲输入--文件路径--文件流--缓冲输出
			BufferedInputStream bufferin = new BufferedInputStream(attachStream.get(i));
			File file=new File(fileName+ MimeUtility.decodeText(attachName.get(i)));
			FileOutputStream fileOut=new FileOutputStream(file);
			BufferedOutputStream bufferout = new BufferedOutputStream(fileOut);
			int len = -1;
			while ((len = bufferin.read()) != -1) {
				bufferout.write(len);
				bufferout.flush();
			}
			bufferout.close();
			fileOut.close();
			bufferin.close();
		}
	}
	
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
//		EmailReceive prop=new EmailReceive();
		shell = new Shell();
		shell.setSize(765, 600);
		shell.setText("SWT Application");
		
		Button btnClose = new Button(shell, SWT.NONE);
		btnClose.setText("<<\u5173\u95ED");
		btnClose.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		btnClose.setBounds(611, 514, 102, 39);
		
		browserEmail = new Browser(shell, SWT.NONE);
		browserEmail.setBounds(27, 100, 686, 396);
		
		lblNewLabel = new Label(shell, SWT.RIGHT);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		lblNewLabel.setBounds(35, 10, 74, 21);
		lblNewLabel.setText("\u53D1\u4EF6\u4EBA\uFF1A");
		
		label = new Label(shell, SWT.RIGHT);
		label.setText("\u4E3B\u9898\uFF1A");
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label.setBounds(35, 37, 74, 21);
		
		label_1 = new Label(shell, SWT.RIGHT);
		label_1.setText("\u53D1\u9001\u65E5\u671F\uFF1A");
		label_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_1.setBounds(7, 64, 102, 21);
		
		lblSentPerson = new Label(shell, SWT.NONE);
		lblSentPerson.setBounds(115, 14, 277, 17);
		lblSentPerson.setText("New Label");
		
		lblSubject = new Label(shell, SWT.NONE);
		lblSubject.setText("New Label");
		lblSubject.setBounds(115, 41, 277, 17);
		
		lblSentDate = new Label(shell, SWT.NONE);
		lblSentDate.setText("New Label");
		lblSentDate.setBounds(115, 68, 277, 17);
		
		//点击下载附件按钮
		btnLoadAttach = new Button(shell, SWT.NONE);
		btnLoadAttach.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				//弹出保存对话框
				org.eclipse.swt.widgets.FileDialog dialog=new org.eclipse.swt.widgets.FileDialog(shell,SWT.SAVE);
				dialog.open();
				String fileName=dialog.getFilterPath();
				if(fileName!=""){
					try {
						loadAttach(fileName+"/");
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			}
		});
		btnLoadAttach.setText("\u2193\u2193\u4E0B\u8F7D\u9644\u4EF6");
		btnLoadAttach.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		btnLoadAttach.setBounds(27, 514, 116, 39);
		
		//回复邮件
		btnReply = new Button(shell, SWT.NONE);
		btnReply.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				EmailWriteView writeView=new EmailWriteView(user,password,user,sentPerson,true);
				shell.dispose();
				writeView.open();
			}
		});
		btnReply.setText("<<\u56DE\u590D");
		btnReply.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		btnReply.setBounds(481, 514, 102, 39);

	}
}
