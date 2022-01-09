
package client.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.Date;
import javax.swing.*;
import client.*;
import client.model.entity.MyCellRenderer;
import client.model.entity.OnlineUserListModel;
import client.util.*;
import common.model.entity.*;

/** ���촰�� */
public class ChatFrame extends JFrame{
	private static final long serialVersionUID = -2310785591507878535L;
	/**����Է�����ϢLabel*/
	private JLabel otherInfoLbl;
	/** ��ǰ�û���ϢLbl */
	private JLabel currentUserLbl;
	/**������Ϣ�б�����*/
	public static JTextArea msgListArea;
	/**Ҫ���͵���Ϣ����*/
	public static JTextArea sendArea;
	/** ���ߺ����б� */
	public static JList onlineList;
	/** �����û���ͳ��Lbl */
	public static JLabel onlineCountLbl;
	/** ׼�����͵��ļ� */
	public static FileInfo sendFile;

	/** ˽�ĸ�ѡ�� */
	public JCheckBox rybqBtn;

	public ChatFrame(){
		this.init();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public void init(){
		this.setTitle("QQ������");
		this.setSize(550, 500);
		this.setResizable(false);

		//����Ĭ�ϴ�������Ļ����
		int x = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int y = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((x - this.getWidth()) / 2, (y-this.getHeight())/ 2);

		//��������
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		//�ұ��û����
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BorderLayout());

		// ����һ���ָ�����
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				mainPanel, userPanel);
		splitPane.setDividerLocation(380);
		splitPane.setDividerSize(10);
		splitPane.setOneTouchExpandable(true);
		this.add(splitPane, BorderLayout.CENTER);

		//���ϱ���Ϣ��ʾ���
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		//������������Ϣ���
		JPanel sendPanel = new JPanel();
		sendPanel.setLayout(new BorderLayout());

		// ����һ���ָ�����
		JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				infoPanel, sendPanel);
		splitPane2.setDividerLocation(300);
		splitPane2.setDividerSize(1);
		mainPanel.add(splitPane2, BorderLayout.CENTER);

		otherInfoLbl = new JLabel("��ǰ״̬��Ⱥ����...");
		infoPanel.add(otherInfoLbl, BorderLayout.NORTH);

		msgListArea = new JTextArea();
		msgListArea.setLineWrap(true);
		infoPanel.add(new JScrollPane(msgListArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new BorderLayout());
		sendPanel.add(tempPanel, BorderLayout.NORTH);

		// ���찴ť���
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		tempPanel.add(btnPanel, BorderLayout.CENTER);


		//���ʹ��嶶����ť
		JButton shakeBtn = new JButton(new ImageIcon("images/shake.png"));
		shakeBtn.setMargin(new Insets(0,0,0,0));
		shakeBtn.setToolTipText("����ѷ��ʹ�����");
		btnPanel.add(shakeBtn);

		//�����ļ���ť
		JButton sendFileBtn = new JButton(new ImageIcon("images/sendPic.png"));
		sendFileBtn.setMargin(new Insets(0,0,0,0));
		sendFileBtn.setToolTipText("����ѷ����ļ�");
		btnPanel.add(sendFileBtn);

		//˽�İ�ť
		rybqBtn = new JCheckBox("˽��Ŷ��");
		tempPanel.add(rybqBtn, BorderLayout.EAST);

		//Ҫ���͵���Ϣ������---������
		sendArea = new JTextArea();
		sendArea.setLineWrap(true);
		sendPanel.add(new JScrollPane(sendArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		// ���찴ť���
		JPanel btn2Panel = new JPanel();
		btn2Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		this.add(btn2Panel, BorderLayout.SOUTH);
		JButton closeBtn = new JButton("�ر�");
		closeBtn.setToolTipText("�˳���������");
		btn2Panel.add(closeBtn);
		JButton submitBtn = new JButton("����");
		submitBtn.setToolTipText("��Enter����ݷ�����Ϣ��");
		btn2Panel.add(submitBtn);
		sendPanel.add(btn2Panel, BorderLayout.SOUTH);

		//�����û��б����
		JPanel onlineListPane = new JPanel();
		onlineListPane.setLayout(new BorderLayout());
		onlineCountLbl = new JLabel("���ߺ��ѣ�");
		onlineListPane.add(onlineCountLbl, BorderLayout.NORTH);

		//��ǰ�û����
		JPanel currentUserPane = new JPanel();
		currentUserPane.setLayout(new BorderLayout());
		currentUserPane.add(new JLabel("����"), BorderLayout.NORTH);

		// �ұ��û��б���һ���ָ�����
		JSplitPane splitPane3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
				onlineListPane, currentUserPane);
		splitPane3.setDividerLocation(340);
		splitPane3.setDividerSize(1);
		userPanel.add(splitPane3, BorderLayout.CENTER);

		//��ȡ�����û�������
		DataBuffer.onlineUserListModel = new OnlineUserListModel(DataBuffer.onlineUsers);
		//�����û��б�
		onlineList = new JList(DataBuffer.onlineUserListModel);
		onlineList.setCellRenderer(new MyCellRenderer());

		//����Ϊ��ѡģʽ
		onlineList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		onlineListPane.add(new JScrollPane(onlineList,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

		//��ǰ�û���ϢLabel
		currentUserLbl = new JLabel();
		currentUserPane.add(currentUserLbl);

		///////////////////////ע���¼�������/////////////////////////
		//�رմ���
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				logout();
			}
		});

		//�رհ�ť���¼�
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				logout();
			}
		});

		//ѡ��ĳ���û�˽��
		rybqBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(rybqBtn.isSelected()){
					User selectedUser = (User)onlineList.getSelectedValue();
					if(null == selectedUser){
						otherInfoLbl.setText("��˽��(��ѡ�����ߺ����б���ĳ�����ѽ���˽�İ�)...");
					}else if(DataBuffer.currentUser.getId() == selectedUser.getId()){
						otherInfoLbl.setText("��ǰ״̬�������Լ����Լ�����Ŷ");
					}else{
						otherInfoLbl.setText("��ǰ״̬�������� " + selectedUser.getNickname()
								+"(" + selectedUser.getId() + ") ˽����...");
					}
				}else{
					otherInfoLbl.setText("��ǰ״̬��Ⱥ��...");
				}
			}
		});
		//ѡ��ĳ���û�
		onlineList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				User selectedUser = (User)onlineList.getSelectedValue();
				if(rybqBtn.isSelected()){
					if(DataBuffer.currentUser.getId() == selectedUser.getId()){
						otherInfoLbl.setText("���棡�������Լ����Լ�����Ŷ");
					}else{
						otherInfoLbl.setText("��ǰ״̬���� "+ selectedUser.getNickname()
								+"(" + selectedUser.getId() + ") ˽����...");
					}
				}
			}
		});

		//�����ı���Ϣ
		sendArea.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == Event.ENTER){
					sendTxtMsg();//������Ϣ
				}
			}
		});
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				sendTxtMsg();
			}
		});

		//������
		shakeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				sendShakeMsg();
			}
		});

		//�����ļ�
		sendFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				sendFile();
			}
		});

		this.loadData();  //���س�ʼ����
	}

	/**  �������� */
	public void loadData(){
		//���ص�ǰ�û�����
		if(null != DataBuffer.currentUser){
			currentUserLbl.setIcon(
					new ImageIcon("images/" + DataBuffer.currentUser.getHead() + ".png"));
			currentUserLbl.setText(DataBuffer.currentUser.getNickname()
					+ "(" + DataBuffer.currentUser.getId() + ")");
		}
		//���������û��б�
		onlineCountLbl.setText("���ߺ����б�("+ DataBuffer.onlineUserListModel.getSize() +")");
		//����������������Ϣ���߳�
		new ClientThread(this).start();
	}

	/** ������ */
	public void sendShakeMsg(){
		User selectedUser = (User)onlineList.getSelectedValue();
		if(null != selectedUser){
			if(DataBuffer.currentUser.getId() == selectedUser.getId()){
				JOptionPane.showMessageDialog(ChatFrame.this, "��ô�ܸ��Լ���������!",
						"���ܷ���", JOptionPane.ERROR_MESSAGE);
			}else{
				Message msg = new Message();
				msg.setFromUser(DataBuffer.currentUser);
				msg.setToUser(selectedUser);
				msg.setSendTime(new Date());

				DateFormat df = new SimpleDateFormat("HH:mm:ss");
				StringBuffer sb = new StringBuffer();
				sb.append(" ").append(msg.getFromUser().getNickname())
						.append("(").append(msg.getFromUser().getId()).append(") ")
						.append(df.format(msg.getSendTime()))
						.append("\n  ��").append(msg.getToUser().getNickname())
						.append("(").append(msg.getToUser().getId()).append(") ")
						.append("������һ�����ڶ���\n");
				msg.setMessage(sb.toString());

				Request request = new Request();
				request.setAction("shake");
				request.setAttribute("msg", msg);
				try {
					ClientUtil.sendTextRequest2(request);
				} catch (IOException e) {
					e.printStackTrace();
				}
				ClientUtil.appendTxt2MsgListArea(msg.getMessage());
				new JFrameShaker(ChatFrame.this).startShake();
			}
		}else{
			JOptionPane.showMessageDialog(ChatFrame.this, "����Ⱥ������!ֻ��˽��Ŷ",
					"���ܷ���", JOptionPane.ERROR_MESSAGE);
		}
	}

	/** �����ı���Ϣ */
	public void sendTxtMsg(){
		String content = sendArea.getText();
		if ("".equals(content)) { //������
			JOptionPane.showMessageDialog(ChatFrame.this, "���ܷ��Ϳ���Ϣ!",
					"���ܷ���", JOptionPane.ERROR_MESSAGE);
		} else { //����
			User selectedUser = (User)onlineList.getSelectedValue();
			if(null != selectedUser &&
					DataBuffer.currentUser.getId() == selectedUser.getId()){
				JOptionPane.showMessageDialog(ChatFrame.this, "���ܸ��Լ�������Ϣ!",
						"���ܷ���", JOptionPane.ERROR_MESSAGE);
				return;
			}

			//���������ToUser��ʾ˽�ģ�����Ⱥ��
			Message msg = new Message();
			if(rybqBtn.isSelected()){  //˽��
				if(null == selectedUser){
					JOptionPane.showMessageDialog(ChatFrame.this, "û��ѡ��˽�Ķ���!ѡ���������",
							"���ܷ���", JOptionPane.ERROR_MESSAGE);
					return;
				}else{
					msg.setToUser(selectedUser);
				}
			}
			msg.setFromUser(DataBuffer.currentUser);
			msg.setSendTime(new Date());

			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			StringBuffer sb = new StringBuffer();
			sb.append(" ").append(df.format(msg.getSendTime())).append(" ")
					.append(msg.getFromUser().getNickname())
					.append("(").append(msg.getFromUser().getId()).append(") ");
			if(!this.rybqBtn.isSelected()){//Ⱥ��
				if(null == selectedUser){
					sb.append("��Ⱥ����ȫ������˵");
				}else{
					sb.append("��").append(selectedUser.getNickname())
							.append("(").append(selectedUser.getId()).append(")")
							.append("˵");
				}
			}
			sb.append("\n  ").append(content).append("\n");
			msg.setMessage(sb.toString());

			Request request = new Request();
			request.setAction("chat");
			request.setAttribute("msg", msg);
			try {
				ClientUtil.sendTextRequest2(request);
			} catch (IOException e) {
				e.printStackTrace();
			}

			//JTextArea�а���Enter��ʱ��������ݲ��ص��ı���Ϣ������
			InputMap inputMap = sendArea.getInputMap();
			ActionMap actionMap = sendArea.getActionMap();
			Object transferTextActionKey = "TRANSFER_TEXT";
			inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),transferTextActionKey);
			actionMap.put(transferTextActionKey,new AbstractAction() {
				private static final long serialVersionUID = 7041841945830590229L;
				public void actionPerformed(ActionEvent e) {
					sendArea.setText("");
					sendArea.requestFocus();
				}
			});
			sendArea.setText("");
			ClientUtil.appendTxt2MsgListArea(msg.getMessage());
		}
	}

	/** �����ļ� */
	private void sendFile() {
		User selectedUser = (User)onlineList.getSelectedValue();
		if(null != selectedUser){
			if(DataBuffer.currentUser.getId() == selectedUser.getId()){
				JOptionPane.showMessageDialog(ChatFrame.this, "���ܸ��Լ������ļ�!",
						"���ܷ���", JOptionPane.ERROR_MESSAGE);
			}else{
				JFileChooser jfc = new JFileChooser();
				if (jfc.showOpenDialog(ChatFrame.this) == JFileChooser.APPROVE_OPTION) {
					File file = jfc.getSelectedFile();
					sendFile = new FileInfo();
					sendFile.setFromUser(DataBuffer.currentUser);
					sendFile.setToUser(selectedUser);
					try {
						sendFile.setSrcName(file.getCanonicalPath());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					sendFile.setSendTime(new Date());

					Request request = new Request();
					request.setAction("toSendFile");
					request.setAttribute("file", sendFile);
					try {
						ClientUtil.sendTextRequest2(request);
					} catch (IOException e) {
						e.printStackTrace();
					}

					ClientUtil.appendTxt2MsgListArea("���ļ���Ϣ������ "
							+ selectedUser.getNickname() + "("
							+ selectedUser.getId() + ") �����ļ� ["
							+ file.getName() + "]���ȴ��Է�����...\n");
				}
			}
		}else{
			JOptionPane.showMessageDialog(ChatFrame.this, "��֧�ָ��������ߺ��ѷ����ļ�!��ѡ�񵥸�����",
					"���ܷ���", JOptionPane.ERROR_MESSAGE);
		}
	}

	/** �رտͻ��� */
	private void logout() {
		int select = JOptionPane.showConfirmDialog(ChatFrame.this,
				"ȷ���˳���\n\n�˳������ж��������������!", "�˳�QQ������",
				JOptionPane.YES_NO_OPTION);
		if (select == JOptionPane.YES_OPTION) {
			Request req = new Request();
			req.setAction("exit");
			req.setAttribute("user", DataBuffer.currentUser);
			try {
				ClientUtil.sendTextRequest(req);
			} catch (IOException ex) {
				ex.printStackTrace();
			}finally{
				System.exit(0);
			}
		}else{
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}
	}
}