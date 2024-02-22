package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestVo;


@WebServlet("/guest")
public class GuestController extends HttpServlet {
	//private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("addList".equals(action)) {//등록폼
			
			GuestDao gd = new GuestDao();
			
			List<GuestVo> gList = gd.guestbookList();
			
			request.setAttribute("gList", gList);
			//여기까지 리스트
			
			WebUtil.forward("/WEB-INF/views/guestbook/addList.jsp", request, response);//이건 등록폼
		}else if("add".equals(action)) {//등록
			
			System.out.println("add");
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");
			
			GuestVo gv = new GuestVo(name, password, content);
			
			GuestDao gd = new GuestDao();
			
			gd.contentInsert(gv);
			
			
			WebUtil.redir("http://localhost:8080/mysite3/guest?action=addList", request, response);
		}else if("delete".equals(action)) {//삭제폼
			/*
			int no = Integer.parseInt(request.getParameter("no"));
			System.out.println(no);
			 
			
			request.setAttribute("no", no);
			*/// <!-- +++ jsp 에서도 파라미터값을 받아올수 있기에 굳이 Attribute로 보내고 받을 필요는 없다 -->
			
			WebUtil.forward("/WEB-INF/views/guestbook/deleteForm.jsp", request, response);
		}else if("delete2".equals(action)) {//삭제
			
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			GuestVo gv = new GuestVo(no, password);
			
			GuestDao gd = new GuestDao();
			
			gd.guestdelete(gv);
			
			WebUtil.redir("http://localhost:8080/mysite3/guest?action=addList", request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
