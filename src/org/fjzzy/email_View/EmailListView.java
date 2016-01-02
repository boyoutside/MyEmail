package org.fjzzy.email_View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.swing.table.TableColumn;

import org.eclipse.jface.dialogs.MessageDialog;
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
import org.eclipse.swt.widgets.Label;

public class EmailListView {

	protected Shell shell;
	private Table table;
	private Folder folder;
	private int emailDelCount;
	
	private String user;
	private String password;
	EmailParse parse=null;
	private Label lblReceiveHint;
	
	
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
	public static void main(String[] args) {
		try {
			EmailListView window = new EmailListView();
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
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	public EmailListView() {
		super();
	}


	

	//���յ�¼����LoginView���͹��������䡢�˻���������
	public EmailListView(Folder folder, String user, String password) {
		super();
		this.folder = folder;
		this.user = user;
		this.password = password;
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
		
		//�����д�š���ť
		Button btnEmailWrite = new Button(shell, SWT.NONE);
		btnEmailWrite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				EmailWriteView writeView=new EmailWriteView(user,password);
				writeView.open();
			}
		});
		btnEmailWrite.setText("\u5199\u4FE1");
		btnEmailWrite.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		btnEmailWrite.setBounds(22, 128, 82, 39);
		
		//������Ű�ť
		Button btnEmailReceive = new Button(shell, SWT.NONE);
		btnEmailReceive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				lblReceiveHint.setText("�������ţ����Ե�Ƭ��......");
				try {
					//����folder��EmailParseȥ����
					parse=new EmailParse(folder);
					//�������ȫ����Ϣ
					parse.saveBasic();
					//���������ˡ����⡢�����б�
					parse.createTable(table);
				} catch (MessagingException | IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				lblReceiveHint.setText("");
			}
		});
		btnEmailReceive.setText("\u6536\u4FE1");
		btnEmailReceive.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		btnEmailReceive.setBounds(22, 55, 82, 39);
		
		//����򿪰�ť����ָ���ʼ�
		Button btnEmailOpen = new Button(shell, SWT.NONE);
		btnEmailOpen.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				//�򿪵�emailNum���ʼ�
				int emailNum=table.getSelectionIndex();
				//�ֱ��ȡ�����ˡ����⡢�������ڡ����ݡ��������븽������
				String sentPerson=parse.getReceive().get(emailNum).getEmailFrom();
				String subject=parse.getReceive().get(emailNum).getEmailSubject();
				String sentDate=parse.getReceive().get(emailNum).getEmailSendDate();
				String content=parse.getReceive().get(emailNum).getEmailContent();
				ArrayList<InputStream> attachStream=parse.getReceive().get(emailNum).getAttachStream();
				ArrayList<String> attachName=parse.getReceive().get(emailNum).getAttachName();
				ArrayList<String> imgId=parse.getReceive().get(emailNum).getImgId();
				
				System.out.println(parse.getReceive().get(emailNum).getAttachName().size());
				System.out.println(parse.getReceive().get(emailNum).getAttachName());
				System.out.println(parse.getReceive().get(emailNum).getEmailSubject());
				
				//����ȡ��Ϣ���͸��ʼ�չʾ����EmailShowView
				EmailShowView showView=new EmailShowView(content,sentPerson,subject,
						sentDate,attachStream,attachName,user,password,imgId);
//				shell.dispose();
				showView.open();
			}
		});
		btnEmailOpen.setText("\u6253\u5F00");
		btnEmailOpen.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		btnEmailOpen.setBounds(22, 201, 82, 39);
		
		//�˳��б�������
		Button btnExit = new Button(shell, SWT.NONE);
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shell.dispose();
			}
		});
		btnExit.setText("\u9000\u51FA");
		btnExit.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		btnExit.setBounds(22, 342, 82, 39);
		
		//˫���鿴�ʼ�
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				System.out.println(table.getSelectionIndex());
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if(table.getSelectionIndex()!=-1){
					//�򿪵�emailNum���ʼ�
					int emailNum=table.getSelectionIndex();
					//�ֱ��ȡ�����ˡ����⡢�������ڡ����ݡ��������븽������
					String sentPerson=parse.getReceive().get(emailNum).getEmailFrom();
					String subject=parse.getReceive().get(emailNum).getEmailSubject();
					String sentDate=parse.getReceive().get(emailNum).getEmailSendDate();
					String content=parse.getReceive().get(emailNum).getEmailContent();
					ArrayList<InputStream> attachStream=parse.getReceive().get(emailNum).getAttachStream();
					ArrayList<String> attachName=parse.getReceive().get(emailNum).getAttachName();
					ArrayList<String> imgId=parse.getReceive().get(emailNum).getImgId();
					
					System.out.println(parse.getReceive().get(emailNum).getAttachName().size());
					System.out.println(parse.getReceive().get(emailNum).getAttachName());
					System.out.println(parse.getReceive().get(emailNum).getEmailSubject());
					
					//����ȡ��Ϣ���͸��ʼ�չʾ����EmailShowView
					EmailShowView showView=new EmailShowView(content,sentPerson,subject,
							sentDate,attachStream,attachName,user,password,imgId);
//					shell.dispose();
					showView.open();
				}
			}
		});
		table.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 10, SWT.BOLD));
		table.setBounds(132, 55, 758, 525);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		//ɾ��ָ���ʼ�
		Button btnDelete = new Button(shell, SWT.NONE);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				emailDelCount=table.getSelectionIndex();
				String subject=parse.getReceive().get(emailDelCount).getEmailSubject();
				boolean result=MessageDialog.openConfirm(shell, "ɾ��������", "ȷ��Ҫɾ������Ϊ��<"+subject+">���ʼ���");
				if(result){
					lblReceiveHint.setText("����ɾ���ʼ������Ե�Ƭ��......");
					try {
						//����folder��EmailParseȥ����
						parse=new EmailParse(folder,emailDelCount);
						//�������ȫ����Ϣ
						parse.saveBasic();
						//���������ˡ����⡢�����б�
						parse.createTable(table);
					} catch (MessagingException | IOException e1) {
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}
				}
				lblReceiveHint.setText("");
			}
		});
		btnDelete.setText("\u5220\u9664");
		btnDelete.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 14, SWT.BOLD));
		btnDelete.setBounds(22, 266, 82, 39);
		
		lblReceiveHint = new Label(shell, SWT.NONE);
		lblReceiveHint.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_YELLOW));
		lblReceiveHint.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 15, SWT.BOLD));
		lblReceiveHint.setBounds(223, 10, 320, 28);
		
	}
}
