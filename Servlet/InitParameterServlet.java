package com.campus.myapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/init.do")
public class InitParameterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public InitParameterServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//web.xml의 init-param데이터를 가져오기
		String userid = getInitParameter("userid");
		String username = getInitParameter("username");
		
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter pw = response.getWriter();
		pw.println("<!DOCTYPE html><html><head><title>init테스트</title></head><body>");
		pw.println("<ul><li>아이디="+userid+"</li>");
		pw.println("<li>이름="+username+"</li></ul></body></html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}