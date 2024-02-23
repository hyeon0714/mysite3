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
				query+=" select	board.no, ";
				query+=" 		title, ";
				query+=" 		content, ";
				query+=" 		hit, ";
				query+=" 		reg_date, ";
				query+=" 		user_no, ";
				query+="		name ";
				query+=" from board, users ";
				query+=" where board.user_no = users.no ";
				
				
				
				pstmt = conn.prepareStatement(query);

				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int no = rs.getInt("no");
					String title = rs.getString("title");
					String content = rs.getString("content");
					int hit = rs.getInt("hit");
					String reg_date = rs.getString("reg_date");
					int userNo = rs.getInt("user_no");
					String name = rs.getString("name");
					
					BoardVo bv = new BoardVo(no, title, content, hit, reg_date, userNo, name);
					
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
	
	public BoardVo readContent(int no) {
		BoardVo bv = null;
		int hit = 0;
		
		this.getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
				String query="";
				query+=" select	title, ";
				query+=" 		content, ";
				query+=" 		hit, ";
				query+=" 		reg_date, ";
				query+="		users.no, ";
				query+=" 		name ";
				query+=" from board, users ";
				query+=" where board.user_no = users.no ";
				query+=" and		board.no = ? ";
				

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, no);
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					String title = rs.getString("title");
					String content = rs.getString("content");
					hit = rs.getInt("hit");
					String reg_date = rs.getString("reg_date");
					int user_no = rs.getInt("users.no");
					String name = rs.getString("name");
				
					hit = hit+1;
				
					bv= new BoardVo(title, content, hit, reg_date, user_no, name);
					
				}
				
			// 4.결과처리
			}catch (SQLException e) {
				System.out.println("error:" + e);
			
			}
		this.close();
		
		
		this.getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
				String query="";
				query+=" update	board ";
				query+=" set hit = ? ";
				query+=" where no = ? ";
				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, hit);
				pstmt.setInt(2, no);
				
				pstmt.executeUpdate();

			// 4.결과처리
			}catch (SQLException e) {
				System.out.println("error:" + e);
			
			}
		this.close();
		
		return bv;
	}
	
	public List<BoardVo> boardModify(String title , String content, int no) {
		
		
		this.getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
				String query="";
				query+=" update	board ";
				query+=" set title = ?, ";
				query+=" 	content = ? ";
				query+=" where no = ? ";

				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, title);
				pstmt.setString(2, content);
				pstmt.setInt(3, no);
				
				pstmt.executeUpdate();
								
			// 4.결과처리
			}catch (SQLException e) {
				System.out.println("error:" + e);
			
			}
		
		this.close();
		
		List<BoardVo> bList = new ArrayList<BoardVo>();
		
		this.getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
				String query="";
				query+=" select	board.no, ";
				query+=" 		title, ";
				query+=" 		content, ";
				query+=" 		hit, ";
				query+=" 		reg_date, ";
				query+=" 		user_no, ";
				query+="		name ";
				query+=" from board, users ";
				query+=" where board.user_no = users.no ";
				
				
				
				pstmt = conn.prepareStatement(query);

				
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int no1 = rs.getInt("no");
					String title1 = rs.getString("title");
					String content1 = rs.getString("content");
					int hit = rs.getInt("hit");
					String reg_date = rs.getString("reg_date");
					int userNo = rs.getInt("user_no");
					String name = rs.getString("name");
					
					BoardVo bv = new BoardVo(no1, title1, content1, hit, reg_date, userNo, name);
					
					bList.add(bv);
					
				}
				
				
			// 4.결과처리
			}catch (SQLException e) {
				System.out.println("error:" + e);
			
			}
		
		this.close();
		return bList;
	}
		

	
	public BoardVo modifyContent(int no) {
		BoardVo bv = null;
		int hit = 0;
		
		this.getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
				String query="";
				query+=" select	title, ";
				query+=" 		content, ";
				query+=" 		hit, ";
				query+=" 		reg_date, ";
				query+="		users.no, ";
				query+=" 		name ";
				query+=" from board, users ";
				query+=" where board.user_no = users.no ";
				query+=" and		board.no = ? ";
				

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, no);
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					String title = rs.getString("title");
					String content = rs.getString("content");
					hit = rs.getInt("hit");
					String reg_date = rs.getString("reg_date");
					int user_no = rs.getInt("users.no");
					String name = rs.getString("name");
				
				
					bv= new BoardVo(title, content, hit, reg_date, user_no, name);
					
				}
				
			// 4.결과처리
			}catch (SQLException e) {
				System.out.println("error:" + e);
			
			}
		this.close();
		return bv;
	}
	
	public void boardDelete(int no) {
		this.getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
				String query="";
				query+=" delete from board ";
				query+=" where no = ? ";

				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, no);
				
				pstmt.executeUpdate();
				
			// 4.결과처리
			}catch (SQLException e) {
				System.out.println("error:" + e);
			
			}
		this.close();
	}
}
