package com.mo;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class PnlFindStu extends JPanel implements ActionListener {
	JLabel numImput,name,sex,xueyuan,major,banji;
	JTextField numTex, nameTex,sexTex,xueyuanTex,majorTex,banjiTex;	
	JButton check;
	DBConnection dbc;
	
	public PnlFindStu() {
		//各控件的初始化
		numImput=new JLabel("输入要查询信息的学号");
		check=new JButton("查询");
		name=new JLabel("姓名");
		sex=new JLabel("性别");
		xueyuan=new JLabel("学院");
		major=new JLabel("专业");
		banji=new JLabel("班级");
		numTex=new JTextField();
		nameTex=new JTextField();
		sexTex=new JTextField();
		xueyuanTex=new JTextField();
		majorTex=new JTextField();
		banjiTex=new JTextField();
		//设置文本不可被编辑
		nameTex.setEditable(false);
		sexTex.setEditable(false);
		xueyuanTex.setEditable(false);
		majorTex.setEditable(false);
		banjiTex.setEditable(false);
		
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
		this.add(sexTex);
		this.add(xueyuanTex);
		this.add(majorTex);
		this.add(banjiTex);
	}
	
	void setLocation() {//设置各控件的属性及位置
		numImput.setBounds(100, 50, 200, 25);
		numImput.setFont(new Font("宋体",Font.BOLD,17));	
		numTex.setBounds(290, 50, 60, 25);
		numTex.setSize(150, 25);
		check.setBounds(450,50,70,25);
		check.setFont(new Font("宋体",Font.BOLD,13));
		check.addActionListener(this);
		
		name.setBounds(230, 100, 200, 25);
		name.setFont(new Font("宋体",Font.BOLD,17));
		nameTex.setBounds(290,100,60,25);
		nameTex.setSize(150, 25);
		
		sex.setBounds(230, 150, 200, 25);
		sex.setFont(new Font("宋体",Font.BOLD,17));
		sexTex.setBounds(290,150,60,25);
		sexTex.setSize(150, 25);
		
		xueyuan.setBounds(230, 200, 200, 25);
		xueyuan.setFont(new Font("宋体",Font.BOLD,17));
		xueyuanTex.setBounds(290,200,60,25);
		xueyuanTex.setSize(150, 25);
		
		major.setBounds(230, 250, 200, 25);
		major.setFont(new Font("宋体",Font.BOLD,17));
		majorTex.setBounds(290,250,60,25);
		majorTex.setSize(150, 25);
		
		banji.setBounds(230, 300, 200, 25);
		banji.setFont(new Font("宋体",Font.BOLD,17));
		banjiTex.setBounds(290,300,60,25);
		banjiTex.setSize(150,25);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
			sexTex.setText("");
			xueyuanTex.setText("");
			majorTex.setText("");
			banjiTex.setText("");
			//连接数据库
			dbc=new DBConnection();
			String [] sb =new String[6];
			try {
				if(!dbc.containsNumber(numTex.getText())) {
					JOptionPane.showMessageDialog(this, "您输入的学号不存在 ，请重新输入！！！","消息对话框",JOptionPane.INFORMATION_MESSAGE);
				}else {
					for(int i=0;i<sb.length;i++)
						sb[i]=dbc.findByNumber(numTex.getText()).getString(i+1);
						//将各信息填入组件内
						nameTex.setText(sb[1]);
						sexTex.setText(sb[2]);
						xueyuanTex.setText(sb[3]);
						majorTex.setText(sb[4]);
						banjiTex.setText(sb[5]);
					}
			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public boolean isLegal(String str) {//该方法用于判断用户输入是否合法
		if(str.isEmpty() || str.equals(" ")) {
			return false;
		}else if(str.contains(" ")) {
			return false;
		}
		return true;
	}
}
