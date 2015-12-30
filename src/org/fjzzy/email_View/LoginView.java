package org.fjzzy.email_View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.mail.MessagingException;

import org.fjzzy.email_Message.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Combo;

public class LoginView {

	protected Shell shell;
	private Text txtUser;
	private Text txtPassword;

	EmailReceive emailReceive=null;
	private Button cbSavePC;
	private Combo cbType;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			LoginView window = new LoginView();
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
		getInfo();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	//初始化窗体默认设置最后一次登录的账户密码
	private void getInfo(){
		String[] InfoPC=null;
		File file=new File("Information/PC.txt");
		FileReader fileReader=null;
		BufferedReader buffer=null;
		try {
			fileReader=new FileReader(file);
			buffer=new BufferedReader(fileReader);
			try {
				InfoPC=buffer.readLine().split(" ");
				if(InfoPC!=null){
					txtUser.setText(InfoPC[0]);
					txtPassword.setText(InfoPC[1]);
					cbSavePC.setSelection(true);
				}
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
	}
	//设置保存密码的方法
	private void saveInfo(){
		File file=new File("Information/PC.txt");
		FileWriter fileWrite=null;
		BufferedWriter buffer=null;
		try {
			fileWrite=new FileWriter(file);
			buffer=new BufferedWriter(fileWrite);
			buffer.write(txtUser.getText().trim()+" "+txtPassword.getText().trim());
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}finally{
			try {
				buffer.close();
				fileWrite.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(496, 363);
		shell.setText("SWT Application");
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 25, SWT.BOLD));
		lblNewLabel.setBounds(156, 36, 176, 44);
		lblNewLabel.setText("\u90AE\u4EF6\u5BA2\u6237\u7AEF");
		
		Label lblNewLabel_1 = new Label(shell, SWT.CENTER);
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.BOLD));
		lblNewLabel_1.setBounds(98, 99, 66, 24);
		lblNewLabel_1.setText("\u8D26\u6237\uFF1A");
		
		txtUser = new Text(shell, SWT.BORDER);
		txtUser.setBounds(174, 101, 172, 23);
		
		Label label = new Label(shell, SWT.CENTER);
		label.setText("\u5BC6\u7801\uFF1A");
		label.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.BOLD));
		label.setBounds(98, 138, 66, 24);
		
		txtPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setBounds(174, 138, 172, 23);
		
		cbSavePC = new Button(shell, SWT.CHECK);
		cbSavePC.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.NORMAL));
		cbSavePC.setBounds(128, 179, 101, 24);
		cbSavePC.setText("\u8BB0\u4F4F\u5BC6\u7801");
		
		//点击“登录”按钮进入邮件客户端主界面
		Button btnEnter = new Button(shell, SWT.NONE);
		btnEnter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				String userName=txtUser.getText();
				String password=txtPassword.getText();
				emailReceive=new EmailReceive(userName, password,cbType.getText());
				try {
					if(emailReceive.isConnect()){
						System.out.println("成功");
						if(cbSavePC.getSelection()){
							saveInfo();
						}
						shell.dispose();
						System.out.println(emailReceive.getUser()+"+"+emailReceive.getPassword());
						EmailListView listView = new EmailListView(emailReceive.getFolder(),emailReceive.getUser(),
								emailReceive.getPassword());
						listView.open();
					}
				} catch (MessagingException | IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		btnEnter.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.BOLD));
		btnEnter.setBounds(142, 263, 87, 34);
		btnEnter.setText("\u767B\u5F55");
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.setText("\u53D6\u6D88");
		btnCancel.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 13, SWT.BOLD));
		btnCancel.setBounds(245, 263, 87, 34);
		
		cbType = new Combo(shell, SWT.NONE);
		cbType.setBounds(259, 183, 87, 25);
		cbType.add("imap");
		cbType.add("pop3");
		cbType.setText("pop3");

	}
}
