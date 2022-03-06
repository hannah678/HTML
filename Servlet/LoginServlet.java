package com.campus.myapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//로그인폼
		res.setContentType("text/html; charset=utf-8");
		
		PrintWriter pw = res.getWriter();
		String html = "<!DOCTYPE html>";
		
		html += "<html>";
		html += "<head><title>로그인</title>";
		html += "<script>";
		html += "function logFormCheck(){";
		html += "if(document.getElementById('userid').value==''){";
		html += "alert('아이디를 입력하세요...'); return false;}";
		html += "if(document.querySelector('#userpwd').value==''){";
		html += "alert('비밀번호를 입력하세요...'); return false;}";
		html += "return true;}</script></head><body>";
		html += "<h1>로그인</h1>";
		html += "<form method='post' action='"+req.getContextPath()+"/login.do' onsubmit='return loginFormCheck()'>";
		html += "아이디 : <input type='text' name='userid' id='userid'/><br/>";
		html += "비밀번호 : <input type='password' name='userpwd' id='userpwd'/><br/>";
		html += "<input type='submit' value='Login'/></form></body></html>";
		
		pw.println(html);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//Db조회
		String userid = req.getParameter("userid");
		String userpwd = req.getParameter("userpwd");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		res.setContentType("text/html; charset=utf-8");
		PrintWriter pw = res.getWriter();
		try {
			//1. 드라이브로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			//2. DB연결
			String url = "jdbc:mysql://127.0.0.1/campusdb";
			String id="root";
			String pwd="root";
			conn = DriverManager.getConnection(url, id, pwd);
			
			//3. PrepareadStatement 생성
			String sql = "select userid, username from member where userid=? && userpwd=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			pstmt.setString(2, userpwd);
			
			//4. 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {//로그인 성공
				// 세션에.... 이름과 아이디를 저장한다.
				// 세션객체를 request에서 얻어올 수 있다.
				HttpSession session = req.getSession();
				session.setAttribute("logId", rs.getString(1)); //변수이름 logId는 사용자정의
				session.setAttribute("logName", rs.getString(2)); //변수이름 logName은 사용자정의
				
				pw.println("<script>");
				pw.println("alert('로그인 성공하였습니다. \\n홈페이지로 이동합니다.')");
				pw.println("location.href='/webServlet/index.do';");
				pw.println("</script>");
				System.out.println("ffffff");
			}else {//로그인 실패
				pw.println("<script>");
				pw.println("alert('로그인 실패하였습니다.');");
				pw.println("history.back(); </script>");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
			//db닫기
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			}catch(SQLException s) {
				s.printStackTrace();
			}
		}
	}

}
