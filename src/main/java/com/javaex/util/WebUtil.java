package com.javaex.util;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtil {
	
	
	public static void forward(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = request.getRequestDispatcher(path);//등록폼 이동
		rd.forward(request, response);
	}
	
	public static void redir(String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.sendRedirect(url);	
	}
}
