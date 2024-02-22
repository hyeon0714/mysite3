package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	
	//getConnection으로 드라이버 정상연결을 확인하고
	//사용할 메소드로 쿼리문을 실행
	//getclose로 실행된 필드값을 리셋
	
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
	
	public int insertUser(UserVo uservo) {
		int count = -1;
		/*
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		// 2. Connection 얻어오기
			String url = "jdbc:mysql://localhost:3306/web_db";
			conn = DriverManager.getConnection(url, "web", "web");
		*///메소드로 대체
		this.getConnection();
		
		try {
		
		// 3. SQL문 준비 / 바인딩 / 실행
			String query="";
			query+=" insert into users ";
			query+=" value(null, ?,?,?,?) ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uservo.getId());
			pstmt.setString(2, uservo.getPassword());
			pstmt.setString(3, uservo.getName());
			pstmt.setString(4, uservo.getGender());
			
			count = pstmt.executeUpdate();
			
		// 4.결과처리
		}catch (SQLException e) {
			System.out.println("error:" + e);
		
		}
		this.close();
		
		return count;
	}
	
	public UserVo selectUserLogin(UserVo uservo) {
		
		UserVo authUser = null;
		
		this.getConnection();
		
		try {
		
		// 3. SQL문 준비 / 바인딩 / 실행
			String query="";
			query+=" select	no, ";
			query+=" 		id, ";
			query+=" 		password, ";
			query+=" 		name, ";
			query+=" 		gender ";
			query+=" from users ";
			query+=" where id = ? ";
			query+=" and password = ? ";
			
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uservo.getId());
			pstmt.setString(2, uservo.getPassword());

			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				
				authUser = new UserVo(no, id, password, name, gender);
				
			}
			
		// 4.결과처리
		}catch (SQLException e) {
			System.out.println("error:" + e);
		
		}
		this.close();
		
		return authUser;
	}
	
	public UserVo userModify(UserVo uservo) {
		
		UserVo authUser = null;
		
		this.getConnection();
		
		try {
		
		// 3. SQL문 준비 / 바인딩 / 실행
			String query="";
			query+=" update users ";
			query+=" set	password = ?, ";
			query+=" 		name = ?, ";
			query+=" 		gender = ? ";
			query+=" where 	no = ? ";

			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, uservo.getPassword());
			pstmt.setString(2, uservo.getName());
			pstmt.setString(3, uservo.getGender());
			pstmt.setInt(4,  uservo.getNo());

			
			pstmt.executeUpdate();
			
			
		// 4.결과처리
		}catch (SQLException e) {
			System.out.println("error:" + e);
		
		}
		this.close();
		
		this.getConnection();
		
		try {
		
		// 3. SQL문 준비 / 바인딩 / 실행
			String query="";
			query+=" select	no, ";
			query+=" 		id, ";
			query+=" 		password, ";
			query+=" 		name, ";
			query+=" 		gender ";
			query+=" from users ";
			query+=" where no = ? ";
			
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, uservo.getNo());

			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				int no = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				
				authUser = new UserVo(no, id, password, name, gender);
				
			}
			

			
		// 4.결과처리
		}catch (SQLException e) {
			System.out.println("error:" + e);
		
		}
		this.close();
		
		return authUser;
	}
	
}
