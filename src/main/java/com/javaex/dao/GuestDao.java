package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVo;

public class GuestDao {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/web_db";
	private String id = "web";
	private String pw = "web";
	
	private void getConnection() {

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);
			
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		
		} catch (SQLException e) {
			System.out.println("error:" + e);
		
		}
		
	}//getConnection()//
	
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
		        rs.close();
		    }
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public int contentInsert(GuestVo guestvo) {
		int count = -1;
		
		this.getConnection();
		try {
			String query="";
			query+=" insert into guestbook ";
			query+=" value(null, ?, ?, ?, now()) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, guestvo.getName());
			pstmt.setString(2, guestvo.getPassword());
			pstmt.setString(3, guestvo.getContent());
			
			count = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		return count;
	}

	public List<GuestVo> guestbookList() {
		List<GuestVo> gList = new ArrayList<GuestVo>();
		
		this.getConnection();
		
		try {
			String query="";
			query+=" select	no, ";
			query+=" 		name, ";
			query+="        password,";
			query+=" 		content,";
			query+="		reg_date";
			query+=" from guestbook ";
			
			pstmt = conn.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String date = rs.getString("reg_date");
				
				GuestVo gv = new GuestVo(no, name, password, content, date);
				System.out.println(gv.toString());
				gList.add(gv);
			}
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
	
		return gList;
	}
	
	public void guestdelete(GuestVo guestvo) {
		this.getConnection();
		
		try {
			String query="";
			query+=" delete from guestbook ";
			query+=" where 	no = ? ";
			query+=" and	password = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, guestvo.getNo());
			pstmt.setString(2, guestvo.getPassword());
			
			pstmt.executeUpdate();
			
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		
		
	}
}
