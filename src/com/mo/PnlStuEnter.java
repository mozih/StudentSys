package com.mo;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import javax.swing.*;

public class PnlStuEnter extends JPanel implements ActionListener, ItemListener {
	JLabel number,name,sex,xueyuan,major,banji;
	JTextField numTex, nameTex,banjiTex;
	JRadioButton rabM, rabF;
	ButtonGroup btgSex;	
	JComboBox cmbXueYuan,cmbMajor;
	String school[],majorStr[][];
	JButton btnLogin,btnReset;
	DBConnection dbc;
	
	public PnlStuEnter(){
		//各控件的初始化
		school= new String [] {"信息与通信工程学院","海运学院","轮机工程学院","船舶与海洋工程学院",
				"航运经贸学院","外语学院","艺术设计学院","港口与航运管理学院","航务工程学院"};
		majorStr=new String [] [] {{"计算机科学与技术","通信工程","电子商务","电子信息工程","软件工程"},
				{ "航海技术","海事管理","旅游管理","法学"}, {"轮机工程","船舶电子电气工程","邮轮工程与管理"}, 
				{"船舶与海洋工程","电气工程及其自动化","能源与动力工程","机械工程","机器人工程"}, 
				{"国际商务","跨境电商","金融学","财务管理"}, {"商务英语"}, {"数字媒体艺术"}, 
				{"交通运输","物流工程","交通管理","物流管理"}, 
				{"港口航道与海岸工程", "工程管理","土木工程","道路桥梁与渡河工程"}};
		number=new JLabel("学号");
		name=new JLabel("姓名");
		sex=new JLabel("性别");
		xueyuan=new JLabel("学院");
		major=new JLabel("专业");
		banji=new JLabel("班级");
		numTex=new JTextField(10);
		nameTex=new JTextField(10);
		banjiTex=new JTextField(10);
		rabM=new JRadioButton("男");
		rabF=new JRadioButton("女");
		btgSex=new ButtonGroup();
		cmbXueYuan=new JComboBox(school);
		cmbXueYuan.addItemListener(this);
		cmbMajor=new JComboBox(majorStr[0]);
		btnLogin=new JButton("录入");
		btnReset=new JButton("重置");
		//把两个单选控件设为同一组
		btgSex.add(rabM);
		btgSex.add(rabF);
		this.setLayout(null);
		addIn();
		setLocation();//设置各控件的位置
		/*mywindow.pnlMain.setEnabled(true);
		mywindow.pnlMain.setVisible(true);
		this.setEnabled(false);
		this.setVisible(false);*/
	}
	void addIn() {//把各个控件放入本容器中
		this.add(number);
		this.add(name);
		this.add(sex);
		this.add(xueyuan);
		this.add(major);
		this.add(banji);
		this.add(numTex);
		this.add(nameTex);
		this.add(banjiTex);
		this.add(rabM);
		this.add(rabF);
		this.add(cmbXueYuan);
		this.add(cmbMajor);
		this.add(btnLogin);
		this.add(btnReset);
	}
	void setLocation() {//设置各控件的属性及位置
		number.setBounds(220, 50, 70, 25);
		number.setFont(new Font("宋体",Font.BOLD,20));	
		numTex.setBounds(290, 50, 70, 25);
		numTex.setSize(150, 25);
		
		name.setBounds(220, 100, 70, 25);
		name.setFont(new Font("宋体",Font.BOLD,20));
		nameTex.setBounds(290,100,70,25);
		nameTex.setSize(150, 25);
		
		sex.setBounds(260, 140, 60, 25);
		sex.setFont(new Font("宋体",Font.BOLD,20));
		rabM.setBounds(320, 140, 60, 25);
		rabM.setFont(new Font("宋体",Font.BOLD,20));
		rabM.setSelected(true);
		rabF.setBounds(380, 140, 60, 25);
		rabF.setFont(new Font("宋体",Font.BOLD,20));
		
		xueyuan.setBounds(220, 190, 70, 25);
		xueyuan.setFont(new Font("宋体",Font.BOLD,20));
		cmbXueYuan.setBounds(290, 190, 70, 25);
		cmbXueYuan.setSize(150,25);
		cmbXueYuan.setFont(new Font("宋体",Font.BOLD,13));
		
		major.setBounds(220, 250, 70, 25);
		major.setFont(new Font("宋体",Font.BOLD,20));
		cmbMajor.setBounds(290,250,70,25);
		cmbMajor.setSize(150,25);
		cmbMajor.setFont(new Font("宋体",Font.BOLD,13));
		
		banji.setBounds(220, 305, 70, 25);
		banji.setFont(new Font("宋体",Font.BOLD,20));
		banjiTex.setBounds(290,305,70,25);
		banjiTex.setSize(150,25);
		
		btnLogin.setBounds(285,370,70,25);
		btnLogin.setFont(new Font("宋体",Font.BOLD,13));
		btnLogin.addActionListener(this);
		btnReset.setBounds(385,370,70,25);
		btnReset.setFont(new Font("宋体",Font.BOLD,13));
		btnReset.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {//设置各按钮点击后的行为
		String command=e.getActionCommand();
		if("重置".equals(command)) {
			numTex.setText("");
			nameTex.setText("");
			banjiTex.setText("");
		}
		else {
			if(!isLegal((String)numTex.getText())) {
				JOptionPane.showMessageDialog(this, "您输入的学号不规范：不能是空值或包含空格！！！","消息对话框",JOptionPane.ERROR_MESSAGE);
				numTex.requestFocus();
				numTex.selectAll();
			}else if(!numTex.getText().matches("\\d+")) {
				JOptionPane.showMessageDialog(this, "您输入的学号不规范：不是全部由数字构成！！！","消息对话框",JOptionPane.INFORMATION_MESSAGE);
				numTex.requestFocus();
				numTex.selectAll();
			}else if(!isLegal((String)nameTex.getText())) {
				JOptionPane.showMessageDialog(this, "您输入的姓名不规范：不能是空值或包含空格！！！","消息对话框",JOptionPane.ERROR_MESSAGE);
				nameTex.requestFocus();
				nameTex.selectAll();
			}else if(!isLegal((String)banjiTex.getText())) {
				JOptionPane.showMessageDialog(this, "您输入的班级不规范：不能是空值或包含空格！！！","消息对话框",JOptionPane.ERROR_MESSAGE);
				banjiTex.requestFocus();
				banjiTex.selectAll();
			}else {//连接数据库
						dbc=new DBConnection();
					if(dbc.containsNumber(numTex.getText())) {
						JOptionPane.showMessageDialog(this, "您输入的学号已存在，请重新输入！！！","消息对话框",JOptionPane.INFORMATION_MESSAGE);
					numTex.requestFocus();
					numTex.selectAll();
					}else {
						String sex="男";
						if(rabF.isSelected()) {
							sex="女";
						}
						if(dbc.insert(new String [] {numTex.getText(), nameTex.getText(), sex,
								(String)cmbXueYuan.getSelectedItem(),(String)cmbMajor.getSelectedItem(),banjiTex.getText()})){
							JOptionPane.showMessageDialog(this, "学生记录插入成功！！！","消息对话框",JOptionPane.INFORMATION_MESSAGE);
						}else {
							JOptionPane.showMessageDialog(this, "学生记录插入失败！！！","消息对话框",JOptionPane.INFORMATION_MESSAGE);
						}
						dbc.close();
						numTex.setText(null);
						nameTex.setText(null);
						banjiTex.setText(null);
					}
			}
		}
	}

	public void itemStateChanged(ItemEvent e) {//学院与专业的联动
		int index=cmbXueYuan.getSelectedIndex();
		if(!((String)cmbMajor.getSelectedItem()).equals(majorStr[index][0])) {
			cmbMajor.removeAllItems();
			for(String str:majorStr[index]) {
				cmbMajor.addItem(str);
			}
		}
	}
	public boolean isLegal(String str) {//该方法用于判断用户输入是否合法
		if(str.isEmpty()) {
			return false;
		}else if(str.contains(" ")) {
			return false;
		}else if(str.equals(" ")) {
			return false;
		}
		return true;
	}
}
