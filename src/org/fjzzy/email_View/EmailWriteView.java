package org.fjzzy.email_View;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.fjzzy.email_Message.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class EmailWriteView {

	protected Shell shell;
	private Text txtSentPerson;
	private Text txtReceivePerson;
	private Text txtSubject;
	private Text txtContent;
	
	private String user;
	private String password;
	private String[] filerName;
	private String path;
	private String sentPerson;
	private String receivePerson;
	private String emailContent;
	private boolean isReply=false;
	private boolean isTransmit=false;
	
	EmailWrite write=null;
	private Label lblHint;
	private Label lblAddHint;
	
	//接收回复时传送过来的信息
	public EmailWriteView(String user, String password, String sentPerson,
			String receivePerson,boolean isReply,String emailContent) {
		super();
		this.user = user;
		this.password = password;
		this.sentPerson = sentPerson;
		this.receivePerson = receivePerson;
		this.isReply=isReply;
		this.emailContent=emailContent;
	}
	//接收转发传送过来的信息
	public EmailWriteView(String user, String password, String sentPerson,
			String emailContent, boolean isTransmit) {
		super();
		this.user = user;
		this.password = password;
		this.sentPerson = sentPerson;
		this.emailContent = emailContent;
		this.isTransmit = isTransmit;
	}
	//接收写信传送过来的信息
	public EmailWriteView(String user, String password) {
		super();
		this.user = user;
		this.password = password;
	}
	
	public EmailWriteView() {
		super();
	}
	
	
	
	public void setUser(String user) {
		this.user = user;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setSentPerson(String sentPerson) {
		this.sentPerson = sentPerson;
	}
	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	
	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			EmailWriteView window = new EmailWriteView();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		txtSentPerson.setText(user);
		if(isReply){
			txtReceivePerson.setText(receivePerson);
			txtContent.setText(emailContent+"\r\n\b你说：");
		}
		if(isTransmit){
			txtContent.setText(emailContent);
			txtContent.setEditable(false);
		}
		System.out.println(path+filerName);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		shell.setSize(680, 598);
		shell.setText("\u5199\u4FE1\u754C\u9762");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		lblNewLabel.setBounds(33, 72, 64, 22);
		lblNewLabel.setText("\u53D1\u4EF6\u4EBA\uFF1A");
		
		//点击“发送”按钮，发送邮件
		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(txtReceivePerson.getText()==""){
					MessageDialog.openInformation(shell, "发送提示！", "邮件收件人不能为空！");
					txtReceivePerson.forceFocus();
				}else if(txtSentPerson.getText()==""){
					MessageDialog.openInformation(shell, "发送提示！", "邮件发件人不能为空！");
					txtSentPerson.forceFocus();
				}else{
					String sentPerson=txtSentPerson.getText();
					String receivePerson=txtReceivePerson.getText();
					String subject=txtSubject.getText();
					String content=txtContent.getText();
					write=new EmailWrite(sentPerson, receivePerson, subject, content,
							user,password,filerName,path);
					
					//发送邮件并提示发送成功
					try {
						write.sentEmail();
						lblHint.setText("发送成功！");
						txtSubject.setText("");
						txtContent.setText("");
						lblAddHint.setText("");
						filerName=null;
						path="";
						txtContent.forceFocus();
					} catch (MessagingException | UnsupportedEncodingException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.BOLD));
		btnNewButton.setBounds(28, 20, 80, 35);
		btnNewButton.setText("\u2190\u53D1\u9001");
		
		//点击“关闭”按钮，退出写信界面
		Button btnClose = new Button(shell, SWT.NONE);
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.dispose();
			}
		});
		btnClose.setText("\u2190\u5173\u95ED");
		btnClose.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.BOLD));
		btnClose.setBounds(134, 20, 80, 35);
		
		Label label = new Label(shell, SWT.NONE);
		label.setText("\u6536\u4EF6\u4EBA\uFF1A");
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label.setBounds(33, 106, 64, 22);
		
		Label label_1 = new Label(shell, SWT.RIGHT);
		label_1.setText("\u4E3B\u9898\uFF1A");
		label_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.NORMAL));
		label_1.setBounds(33, 142, 64, 22);
		
		
		//添加附件按钮
		Button btnAddAttach = new Button(shell, SWT.NONE);
		btnAddAttach.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				//弹出打开对话框
				FileDialog dialog=new FileDialog(shell,SWT.OPEN|SWT.MULTI);
				dialog.setFilterPath("C:/windows");
				dialog.open();
				filerName=dialog.getFileNames();
				path=dialog.getFilterPath();
				//提示出来的邮件名称
				if(filerName.length>0){
					int count=filerName.length;
					String hint="";
					for(int i=0;i<count;i++){
						hint=hint+filerName[i]+"、";
					}
					lblAddHint.setText("成功添加："+hint+"附件");
				}
			}
		});
		btnAddAttach.setText("\u25CE\u6DFB\u52A0\u9644\u4EF6");
		btnAddAttach.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.BOLD));
		btnAddAttach.setBounds(43, 170, 80, 35);
		
		txtSentPerson = new Text(shell, SWT.NONE);
		txtSentPerson.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.BOLD));
		txtSentPerson.setBounds(103, 72, 288, 23);
		
		txtReceivePerson = new Text(shell, SWT.NONE);
		txtReceivePerson.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.BOLD));
		txtReceivePerson.setBounds(103, 106, 288, 23);
		
		//让提示消息小时
		txtSubject = new Text(shell, SWT.NONE);
		txtSubject.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				lblHint.setText("");
			}
		});
		txtSubject.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.BOLD));
		txtSubject.setBounds(103, 142, 288, 23);
		
		//让提示消息小时
		txtContent = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		txtContent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				lblHint.setText("");
			}
		});
		txtContent.setBackground(SWTResourceManager.getColor(SWT.COLOR_LIST_BACKGROUND));
		txtContent.setToolTipText("\u8BF7\u7740\u6B64\u5904\u8F93\u5165\u6B63\u6587\u5185\u5BB9");
		txtContent.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 12, SWT.BOLD));
		txtContent.setBounds(33, 224, 595, 311);
		
		lblHint = new Label(shell, SWT.NONE);
		lblHint.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblHint.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 17, SWT.BOLD));
		lblHint.setBounds(458, 92, 127, 36);
		
		lblAddHint = new Label(shell, SWT.NONE);
		lblAddHint.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		lblAddHint.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		lblAddHint.setBounds(134, 173, 494, 32);

	}
}
