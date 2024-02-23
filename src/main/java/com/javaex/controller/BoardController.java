package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	//private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if("list".equals(action)) {//리스트폼
			System.out.println(action);
			
			
			BoardDao bd = new BoardDao();
			
			List<BoardVo> bList = bd.boardList();
			
			request.setAttribute("bList", bList);
			
			
			
			WebUtil.forward("/WEB-INF/views/board/list.jsp", request, response);
			
		}else if("writeform".equals(action)) {//글쓰기폼
			System.out.println(action);
			WebUtil.forward("/WEB-INF/views/board/writeForm.jsp", request, response);
			
		}else if("write".equals(action)) {
			System.out.println(action);
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int userNo = Integer.parseInt(request.getParameter("userNo"));
			
			BoardVo bv = new BoardVo(title, content, userNo);
			
			BoardDao bd = new BoardDao();
			
			bd.boardWrite(bv);
			
			WebUtil.redir("/mysite3/board?action=list", request, response);
			
		}else if("read".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao bd = new BoardDao();
			
			BoardVo bContent = bd.readContent(no);
			
			request.setAttribute("bContent", bContent);
			
			WebUtil.forward("/WEB-INF/views/board/read.jsp", request, response);
			
		}else if("modifyForm".equals(action)) {
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao bd = new BoardDao();
			
			BoardVo mContent = bd.modifyContent(no);
			
			request.setAttribute("mContent", mContent);
			
			
			WebUtil.forward("/WEB-INF/views/board/modifyForm.jsp", request, response);
			
		}else if("modify".equals(action)) {
			
			String title = request.getParameter("title");
			
			String content = request.getParameter("content");
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao bd = new BoardDao();
			
			List<BoardVo> bList = bd.boardModify(title, content, no);
			
			request.setAttribute("bList", bList);
			
			WebUtil.redir("/mysite3/board?action=list", request, response);
			
		}else if("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao bd =new BoardDao();
			
			bd.boardDelete(no);
			
			WebUtil.redir("/mysite3/board?action=list", request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
