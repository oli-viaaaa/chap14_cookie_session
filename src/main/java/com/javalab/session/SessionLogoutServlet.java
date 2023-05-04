package com.javalab.session;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javalab.dao.MemberDAO;
import com.javalab.vo.MemberVo;

/**
 * 세션 로그아웃 서블릿
 */
@WebServlet("/sessionLogout")
public class SessionLogoutServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    public SessionLogoutServlet() {
        super();
    }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
         throws ServletException, IOException {
      
	   // session 요청시 없으면 생성해주는데 없다면 만들지 말란 의미의 (false)
      HttpSession session = request.getSession(false);
      
      if(session != null) {
         session.invalidate();
         System.out.println("세션을 무효화 하였습니다.");
      }
      
      // sendRedirect(로그아웃 후에 인덱스 페이지로 이동)
      String contextPath = request.getContextPath();
      //String contextPath = this.getServletContext().getContextPath(); // 위와 동일
      response.sendRedirect(contextPath + "/index.jsp");
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
         throws ServletException, IOException {
   }

}