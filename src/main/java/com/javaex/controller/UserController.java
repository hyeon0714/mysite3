package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;


@WebServlet("/user")
public class UserController extends HttpServlet {
	//private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//user에서 업무구분
		String action = request.getParameter("action");
		System.out.println(action);
		
		
		if("joinform".equals(action)) {
			//회원가입폼
			WebUtil.forward("/WEB-INF/views/user/joinForm.jsp", request, response);
		}else if("join".equals(action)) {
			//회원가입
			
			//데이터꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//vo로 묶기
			UserVo uv = new UserVo(id, password, name, gender);
			
			//dao 불러오기
			UserDao ud = new UserDao();
			
			//dao로 실행
			ud.insertUser(uv);
			
			WebUtil.forward("/WEB-INF/views/user/joinOk.jsp", request, response);
		}else if("loginForm".equals(action)) {
			
			WebUtil.forward("/WEB-INF/views/user/loginForm.jsp", request, response);
			
		}else {
			System.out.println("action값을 다시확인해주세요");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
