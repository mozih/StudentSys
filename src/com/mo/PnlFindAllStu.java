package com.mo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.sql.*;


public class PnlFindAllStu extends JPanel {
	DBConnection dbc;
	JTable tabUser;
	DefaultTableModel dtmUser;
	JScrollPane snpTab;
	
	public PnlFindAllStu() {
		tabUser=new JTable();
		snpTab=new JScrollPane(tabUser);		
		this.setLayout(null);
		init();
	}
	public void init() {
		this.add(snpTab);
		snpTab.setBounds(0,0,700,500);
		//定义表头数据
		Vector<String> head=new Vector<String>();
		head.add("学号");
		head.add("姓名");
		head.add("性别");
		head.add("学院");
		head.add("专业");
		head.add("班级");
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Vector<String> row;
		ResultSet rs=null;
		dbc=new DBConnection();
		rs=dbc.findAll();//把数据库内的数据放入结果集中
		try {
			while(rs.next()) {
				row=new Vector<String>();
				for(int i=0;i<6;i++) {
					row.add(rs.getString(i+1));	
				}
				data.add(row);
			}
			this.dtmUser = new DefaultTableModel(data,head);//把数据放入表中
			this.tabUser.setModel(dtmUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*public static void main(String[] args) {
		JFrame jframe=new JFrame();
		PnlFindAllStu p=new PnlFindAllStu();
		jframe.add(p);
		jframe.setBounds(450,200,700,500);
		jframe.setVisible(true);
	}*/

}
