package com.mo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyWindow extends JFrame implements ActionListener{
	CardLayout card;
	PnlStuEnter pnlstuenter;
	PnlStuUpdate pnlstuupdate;
	PnlFindStu pnlfindstu;
	PnlDelStu pnldelstu;
	PnlFindAllStu pnlfindallstu;
	JPanel pnlMain;
	JMenuBar menubar;
	JMenu menuOperation;
	JMenuItem [] items;
	String [] menunames = {"录入学生信息","修改学生信息","查询学生信息","删除学生信息","查看所有学生信息"};
//	DBConnection dbcon;
	
	public MyWindow(){
		super();
		card=new CardLayout();
		pnlMain=new JPanel(card);//把主容器设为卡片式布局
		menubar=new JMenuBar();
		menuOperation=new JMenu("操作菜单");
		pnlstuenter=new PnlStuEnter();
		pnlstuupdate=new PnlStuUpdate();
		pnlfindstu=new PnlFindStu();
		pnldelstu=new PnlDelStu();
		pnlfindallstu=new PnlFindAllStu();
		items=new JMenuItem[menunames.length];
		for(int i=0;i<menunames.length;i++) {
			items[i]=new JMenuItem(menunames[i],new ImageIcon("images/tmg.jpg"));
			items[i].addActionListener(this);
			menuOperation.add(items[i]);
			menuOperation.addSeparator();
		}
		menubar.add(menuOperation);
		this.setJMenuBar(menubar);
		init();
	}
	
	//该方法用于对窗口的初始化
	public void init() {
		//设置窗口的各个属性
		/*
		this.setLocation(450, 200);//设置窗口初始位置
		this.setSize(700,500);//设置窗口大小
		this.getContentPane().setBackground(Color.darkGray);//设置窗口背景颜色
		*/
		this.setBounds(450,200,750,550);//设置窗口初始位置以及大小
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗口的关闭模式，关闭窗口时程序结束
		this.setResizable(false);//设置窗口不能被调整大小
		JLabel lblTitle=new JLabel("欢迎来到广州航海学院学籍管理系统",JLabel.CENTER);
		lblTitle.setFont(new Font("宋体",Font.ITALIC,30));
		/*
		this.add(pnlMain);
		this.add(pnlstuenter);
		pnlMain.setEnabled(true);
		pnlMain.setVisible(true);*/
		//添加各容器在卡片中
		pnlMain.add(lblTitle,"first");
		pnlMain.add(pnlstuenter,"second");
		pnlMain.add(pnlstuupdate,"third");
		pnlMain.add(pnlfindstu,"fourth");
		pnlMain.add(pnldelstu,"fifth");
		
		this.add(pnlMain,BorderLayout.CENTER);
		this.setVisible(true);//设置窗口可见
	}

	public static void main(String[] args) {
		new MyWindow();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String content=e.getActionCommand();
		//System.out.println(content);
		if(menunames[0].equals(content)) {
			card.show(pnlMain, "second");//录入界面
		}
		if(menunames[1].equals(content)){//修改界面
			card.show(pnlMain, "third");
		}
		if(menunames[2].equals(content)) {//查询界面
			card.show(pnlMain, "fourth");
		}
		if(menunames[3].equals(content)) {//删除界面
			card.show(pnlMain, "fifth");
		}
		if(menunames[4].equals(content)) {//查看所有学生信息界面
			JFrame jframe=new JFrame();
			PnlFindAllStu p=new PnlFindAllStu();
			jframe.add(p);
			jframe.setBounds(450,200,700,500);
			jframe.setVisible(true);
			/*if(dbcon==null) {
				dbcon=new DBConnection();
				dbcon.findAll();
				dbcon.close();
			}*/
		}
	}

}

