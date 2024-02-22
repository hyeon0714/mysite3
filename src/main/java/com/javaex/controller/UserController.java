package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			
		}else if("login".equals(action)) {
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			UserVo uv = new UserVo(id, password);
			
			UserDao ud = new UserDao();
			//System.out.println(uv.toString());
			//확인용
			
			UserVo authUser = ud.selectUserLogin(uv);//메소드로 받아온 값을 새 UserVo의 변수에 저장
			/*
			System.out.println(authUser.getNo());
			System.out.println(authUser.getId());
			System.out.println(authUser.getPassword());
			System.out.println(authUser.getName());
			System.out.println(authUser.getGender());
			*/
			
			if(authUser!=null) {//받아온 authUser의 값이 null이 아니면 로그인 성공
				
				HttpSession session =  request.getSession();//세션에 로그인할때 가져온값 저장하기
				session.setAttribute("authUser", authUser);
				
				WebUtil.redir("/mysite3/main", request, response);
				
			}else {//null값인 경우는 로그인 실패
				System.out.println("실패");
				
				WebUtil.redir("/mysite3/user?action=loginForm", request, response);
			}
			
		}else if("logout".equals(action)) {//로그아웃
			System.out.println("logout");
			
			//지우는 방법은 여러가지
			
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");//해당 세션의 지정된 이름에 해당하는 객체를 지운다
			
			//session.invalidate();//해당세션의 값을 지운다
			
			//request.getSession(false);//세션을 리셋시킨다(로그인할때 저장한 값을 지운다)
			//이걸로도 사용가능
			
			WebUtil.redir("/mysite3/user?action=loginForm", request, response);
		
		}else if("modifyform".equals(action)) {//수정폼
			System.out.println("수정폼");
			
			WebUtil.forward("/WEB-INF/views/user/modifyForm.jsp", request, response);
			
		}else if("modify".equals(action)) {
			System.out.println("수정");
			
			int no = Integer.parseInt(request.getParameter("no"));
			String pw = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserVo uservo = new UserVo(no, pw, name, gender);
			
			UserDao ud = new UserDao();
			UserVo authUser = ud.userModify(uservo);
			
			System.out.println(authUser.toString());
			
			HttpSession session = request.getSession();
			session.setAttribute("authUser", authUser);
			
			WebUtil.redir("/mysite3/main", request, response);
			
		}else {
			System.out.println("action값을 다시확인해주세요");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
