package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {
	
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
	
	
	public List<BoardVo> boardList() {
		
		List<BoardVo> bList = new ArrayList<BoardVo>();
		
		this.getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
				String query="";
				query+=" select	no, ";
				query+=" 		title, ";
				query+=" 		content, ";
				query+=" 		hit, ";
				query+=" 		reg_date, ";
				query+=" 		user_no ";
				query+=" from board ";
				
				
				pstmt = conn.prepareStatement(query);

				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int no = rs.getInt("no");
					String title = rs.getString("title");
					String content = rs.getString("content");
					int hit = rs.getInt("hit");
					String reg_date = rs.getString("reg_date");
					int userNo = rs.getInt("user_no");
					
					BoardVo bv = new BoardVo(no, title, content, hit, reg_date, userNo);
					
					bList.add(bv);
					
				}
				
				
			// 4.결과처리
			}catch (SQLException e) {
				System.out.println("error:" + e);
			
			}
		
		this.close();
		return bList;
	}
	
	public int boardWrite(BoardVo boardvo) {
		
		int count = -1;
		
		this.getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
				String query="";
				query+=" insert into board ";
				query+=" value(null, ?, ?, 0, date(now()), ?); ";
				
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, boardvo.getTitle());
				pstmt.setString(2, boardvo.getContent());
				pstmt.setInt(3, boardvo.getUser_no());
				
				count = pstmt.executeUpdate();
				
				
			// 4.결과처리
			}catch (SQLException e) {
				System.out.println("error:" + e);
			
			}
		
		this.close();
		return count;	
	}
	
	
}
