package com.mo;

import java.sql.*;

public class DBConnection {
	Connection con;
	Statement stmt;
	ResultSet rs;
	String sql;
	String URL="jdbc:mysql://127.0.0.1:3306/xuejidb";
	String USER="root";
	String password="mozihao";
	
	public Connection getCon() {
		return con;
	}
	public DBConnection() {
			try {
				//1,加载驱动器类
				Class.forName("com.mysql.jdbc.Driver");
				//2.获得数据库的连接对象
				con=DriverManager.getConnection(URL,USER,password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public void close() {
		try {
			if(con!=null&&con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean containsNumber(String number) {//与数据库中的学号对比
		sql="select*from student";
		try {
			stmt=con.createStatement();
			String sb;
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				sb=new String();
				sb=rs.getString("number");
				if(sb.equals(number))
					return true;
				sb=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean insert(String [] info) {//插入新纪录
		sql="insert into student values('";
		//update
		try {
			stmt=con.createStatement();
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<info.length-1;i++) {
				sb.append(info[i]).append("','");
			}
			sb.append(info[info.length-1]).append("')");
			sql += sb.toString();
			//执行crud（增删改查）
			if(stmt.executeUpdate(sql)==1)
				return true;
			else 
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public boolean updateByNum(String [] info) {//通过学号修改学生信息
		sql="update student set name='";
		String [] s=new String[] {",sex='",",school='",",major='",",class='",""};
		try {
			stmt=con.createStatement();	
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<info.length-1;i++) {
				sb.append(info[i+1]).append("'").append(s[i]);
		}
		sb.append("where number='").append(info[0]).append("'");
		sql += sb.toString();
		//执行修改
		if(stmt.executeUpdate(sql)>0) 
			return true;
		else
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public ResultSet findByNumber(String number){//通过学号查找学生信息
		if(con!=null){
			try {
				stmt=con.createStatement();
				sql="select * from student where number='"+number+"'";
				rs=stmt.executeQuery(sql);
				if(rs.next()){
					return rs;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		return null;
	}
	
	public boolean deleteByNum (String num) {// 通过学号删除学生信息
		sql="DELETE FROM student WHERE number='"+num+"'";
		try {
			stmt=con.createStatement();
			if(stmt.executeUpdate(sql)>0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ResultSet findAll() {//查询所有记录
		sql="select*from student";
		try {//3.利用连接对象，获得statement语句对象
			stmt=con.createStatement();
			//4.执行crud（增删改查）
			rs=stmt.executeQuery(sql);
			String [] sb;
			/*while(rs.next()) {
				sb=new String [6];
				sb[0]=rs.getString("number");
				sb[1]=rs.getString("name");
				sb[2]=rs.getString("sex");
				sb[3]=rs.getString("school");
				sb[4]=rs.getString("major");
				sb[5]=rs.getString("class");
				for(int i=0;i<sb.length;i++) 
				System.out.println(sb[i]);
				sb=null;
			}*/
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int queryRecordNumber(){//查询记录个数
		if(con!=null){
			try {
				stmt=con.createStatement();
				sql="select count(*) from student ";
				rs=stmt.executeQuery(sql);
				if(rs.next()){
					return rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		return 0;
	}
}
