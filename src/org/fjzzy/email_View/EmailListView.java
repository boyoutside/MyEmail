package org.fjzzy.email_View;

import java.io.IOException;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.swing.table.TableColumn;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.fjzzy.email_Message.EmailParse;
import org.fjzzy.email_Message.EmailReceive;

public class EmailListView {

	protected Shell shell;
	private Table table;
	private Folder folder;
	
	EmailReceive emailReceive=null;
	EmailParse parse=null;
	
	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			EmailListView window = new EmailListView();
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
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	public EmailListView() {
		super();
	}


	public EmailListView(Folder folder) {
		super();
		this.folder = folder;
	}


	public Folder getFolder() {
		return folder;
	}


	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	
	
	

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(932, 629);
		shell.setText("SWT Application");
		
		Button btnEmailWrite = new Button(shell, SWT.NONE);
		btnEmailWrite.setText("\u5199\u4FE1");
		btnEmailWrite.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		btnEmailWrite.setBounds(22, 96, 82, 39);
		
		//����򿪰�ť
		Button btnEmailReceive = new Button(shell, SWT.NONE);
		btnEmailReceive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
				try {
					parse=new EmailParse(folder);
					parse.saveBasic();
//					parse.getEmailBasic();
					parse.createTable(table);
				} catch (MessagingException | IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		btnEmailReceive.setText("\u6536\u4FE1");
		btnEmailReceive.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		btnEmailReceive.setBounds(22, 47, 82, 39);
		
		//�����ָ���ʼ�
		Button btnEmailOpen = new Button(shell, SWT.NONE);
		btnEmailOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int emailNum=table.getSelectionIndex();
				String receivePerson=parse.getReceive().get(emailNum).getEmailFrom();
				String subject=parse.getReceive().get(emailNum).getEmailSubject();
				String sentDate=parse.getReceive().get(emailNum).getEmailSendDate();
				String content=parse.getReceive().get(emailNum).getEmailContent();
				System.out.println(subject);
				EmailShowView showView=new EmailShowView(content,receivePerson,subject,sentDate);
//				shell.dispose();
				showView.open();
			}
		});
		btnEmailOpen.setText("\u6253\u5F00");
		btnEmailOpen.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		btnEmailOpen.setBounds(22, 141, 82, 39);
		
		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.setText("\u9000\u51FA");
		btnExit.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		btnExit.setBounds(22, 186, 82, 39);
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				System.out.println(table.getSelectionIndex());
			}
		});
		table.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.BOLD));
		table.setBounds(132, 20, 758, 560);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
	}
}
