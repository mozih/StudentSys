package com.mo;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import javax.swing.*;

import com.mysql.jdbc.ResultSet;

public class PnlStuUpdate extends JPanel implements ActionListener,ItemListener {
	JLabel numImput,name,sex,xueyuan,major,banji;
	JTextField numTex, nameTex,banjiTex;
	JRadioButton rabM, rabF;
	ButtonGroup btgSex;	
	JComboBox cmbXueYuan,cmbMajor;
	String school[],majorStr[][];
	JButton btnRevise,btnReset,check;
	DBConnection dbc;
	ResultSet rs;
	
	public PnlStuUpdate() {
		//各控件的初始化
		school= new String [] {"信息与通信工程学院","海运学院","轮机工程学院","船舶与海洋工程学院",
						"航运经贸学院","外语学院","艺术设计学院","港口与航运管理学院","航务工程学院"};
		majorStr=new String [] [] {{"计算机科学与技术","通信工程","电子商务","电子信息工程","软件工程"},
						{ "航海技术","海事管理","旅游管理","法学"}, {"轮机工程","船舶电子电气工程","邮轮工程与管理"}, 
						{"船舶与海洋工程","电气工程及其自动化","能源与动力工程","机械工程","机器人工程"}, 
						{"国际商务","跨境电商","金融学","财务管理"}, {"商务英语"}, {"数字媒体艺术"}, 
						{"交通运输","物流工程","交通管理","物流管理"}, 
						{"港口航道与海岸工程", "工程管理","土木工程","道路桥梁与渡河工程"}};
		numImput=new JLabel("输入要修改信息的学号");
		check=new JButton("查询");
		name=new JLabel("（新）姓名");
		sex=new JLabel("（新）性别");
		xueyuan=new JLabel("（新）学院");
		major=new JLabel("（新）专业");
		banji=new JLabel("（新）班级");
		numTex=new JTextField(10);
		nameTex=new JTextField(10);
		banjiTex=new JTextField(10);
		rabM=new JRadioButton("男");
		rabF=new JRadioButton("女");
		btgSex=new ButtonGroup();
		cmbXueYuan=new JComboBox(school);
		cmbXueYuan.addItemListener(this);
		cmbMajor=new JComboBox(majorStr[0]);
		btnRevise=new JButton("修改");
		btnReset=new JButton("重置");
		//把两个单选控件设为同一组
		btgSex.add(rabM);
		btgSex.add(rabF);
		this.setLayout(null);
		addIn();
		setLocation();//设置各控件的位置
	}

	void addIn() {//把各个控件放入本容器中
		this.add(numImput);
		this.add(check);
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
		this.add(btnRevise);
		this.add(btnReset);
	}
	
	void setLocation() {//设置各控件的属性及位置
		numImput.setBounds(110, 50, 200, 25);
		numImput.setFont(new Font("宋体",Font.BOLD,17));	
		numTex.setBounds(290, 50, 60, 25);
		numTex.setSize(150, 25);
		check.setBounds(450,50,70,25);
		check.setFont(new Font("宋体",Font.BOLD,13));
		check.addActionListener(this);
		
		name.setBounds(195, 100, 200, 25);
		name.setFont(new Font("宋体",Font.BOLD,17));
		nameTex.setBounds(290,100,60,25);
		nameTex.setSize(150, 25);
		
		sex.setBounds(220, 140, 200, 25);
		sex.setFont(new Font("宋体",Font.BOLD,17));
		rabM.setBounds(320, 140, 60, 25);
		rabM.setFont(new Font("宋体",Font.BOLD,17));
		rabF.setBounds(380, 140, 60, 25);
		rabF.setFont(new Font("宋体",Font.BOLD,17));
		
		xueyuan.setBounds(195, 190, 200, 25);
		xueyuan.setFont(new Font("宋体",Font.BOLD,17));
		cmbXueYuan.setBounds(290, 190, 60, 25);
		cmbXueYuan.setSize(150,25);
		cmbXueYuan.setFont(new Font("宋体",Font.BOLD,13));
		
		major.setBounds(195, 250, 200, 25);
		major.setFont(new Font("宋体",Font.BOLD,17));
		cmbMajor.setBounds(290,250,60,25);
		cmbMajor.setSize(150,25);
		cmbMajor.setFont(new Font("宋体",Font.BOLD,13));
		
		banji.setBounds(195, 305, 200, 25);
		banji.setFont(new Font("宋体",Font.BOLD,17));
		banjiTex.setBounds(290,305,60,25);
		banjiTex.setSize(150,25);
		
		btnRevise.setBounds(285,370,70,25);
		btnRevise.setFont(new Font("宋体",Font.BOLD,13));
		btnRevise.addActionListener(this);
		btnReset.setBounds(385,370,70,25);
		btnReset.setFont(new Font("宋体",Font.BOLD,13));
		btnReset.addActionListener(this);
	}
	@Override
	public void itemStateChanged(ItemEvent e) {//学院与专业的联动
		int index=cmbXueYuan.getSelectedIndex();
		if(!((String)cmbMajor.getSelectedItem()).equals(majorStr[index][0])) {
			cmbMajor.removeAllItems();
			for(String str:majorStr[index]) {
				cmbMajor.addItem(str);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {//设置三个按钮点击后的行为
		String command=e.getActionCommand();
		if("重置".equals(command)) {
			numTex.setEnabled(true);
			numTex.setText("");
			nameTex.setText("");
			banjiTex.setText("");
		}else if("查询".equals(command)){
			if(!isLegal(numTex.getText())) {
				JOptionPane.showMessageDialog(this, "您输入的学号不规范：不能是空值或包含空格！！！","消息对话框",JOptionPane.ERROR_MESSAGE);
				numTex.requestFocus();
				numTex.selectAll();
			}else if(!numTex.getText().matches("\\d+")) {
				JOptionPane.showMessageDialog(this, "您输入的学号不规范：不是全部由数字构成！！！","消息对话框",JOptionPane.INFORMATION_MESSAGE);
				numTex.requestFocus();
				numTex.selectAll();
			}else {
				nameTex.setText("");
				banjiTex.setText("");
				//连接数据库
				dbc=new DBConnection();
				int index_1=0,index_2=0;
				String [] sb =new String[6];
					try {
						if(!dbc.containsNumber(numTex.getText())) {
							JOptionPane.showMessageDialog(this, "您输入的学号不存在 ，请重新输入！！！","消息对话框",JOptionPane.INFORMATION_MESSAGE);
						}else {
							numTex.setEnabled(false);
							for(int i=0;i<sb.length;i++)
								sb[i]=dbc.findByNumber(numTex.getText()).getString(i+1);
							//将各信息填入组件内
							nameTex.setText(sb[1]);
							if("男".equalsIgnoreCase(sb[2]))
								rabM.setSelected(true);
							else
								rabF.setSelected(false);
							for(int i=0;i<school.length;i++) {
								if(school[i].equals(sb[3]))
									index_1=i;
							}
							for(int i=0;i<majorStr[index_1].length;i++) {
								if(majorStr[index_1][i].equals(sb[4]))
									index_2=i;
							}
							cmbXueYuan.setSelectedIndex(index_1);
							cmbMajor.setSelectedIndex(index_2);
							banjiTex.setText(sb[5]);
						}
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			}
		}else if("修改".equals(command)) {
			if(!isLegal(nameTex.getText())) {
				JOptionPane.showMessageDialog(this, "您输入的姓名不规范：不能是空值或包含空格！！！","消息对话框",JOptionPane.ERROR_MESSAGE);
				nameTex.requestFocus();
				nameTex.selectAll();
			}else if(!isLegal(banjiTex.getText())) {
				JOptionPane.showMessageDialog(this, "您输入的班级不规范：不能是空值或包含空格！！！","消息对话框",JOptionPane.INFORMATION_MESSAGE);
				banjiTex.requestFocus();
				banjiTex.selectAll();
			}else {
				//连接数据库
				dbc=new DBConnection();
				String sex="男";
				if(rabF.isSelected()) {
					sex="女";
				}
				String [] info=new String[] {numTex.getText(), nameTex.getText(),sex,(String)cmbXueYuan.getSelectedItem(),(String)cmbMajor.getSelectedItem(),banjiTex.getText()};
				if(dbc.updateByNum(info)) {
					JOptionPane.showMessageDialog(this, "学生记录修改成功！！！","消息对话框",JOptionPane.INFORMATION_MESSAGE);
					numTex.setText("");
					nameTex.setText("");
					banjiTex.setText("");
				}
				else
					JOptionPane.showMessageDialog(this, "学生记录修改失败！！！","消息对话框",JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	public boolean isLegal(String str) {//该方法用于判断用户输入是否合法
		if( str.isEmpty() || str.equals(" ")) {
			return false;
		}else if(str.contains(" ")) {
			return false;
		}
		return true;
	}
}
