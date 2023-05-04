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
 * 세션 로그인 서블릿
 */
@WebServlet("/sessionLogin")
public class SessionLoginServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   
   // [멤버 변수]
   // 서블릿의 멤버변수로 싱글톤으로 만든 boardDao 객체의 주소를 얻어옴   
   private MemberDAO memberDao = MemberDAO.getInstance();
   
   // 로그인 폼을 뛰워주는 메소드
   protected void doGet(HttpServletRequest request, 
               HttpServletResponse response) 
               throws ServletException, IOException {
      System.out.println("doGet");
      
      // 컨텍스트패스 
       String contextPath = request.getContextPath();

        // 1. 세션에 로그인 정보가 있는지 확인
       // 세션 객체 얻기
       HttpSession session = request.getSession();
       MemberVo member = (MemberVo)session.getAttribute("member"); 
       
       if(member != null) {   // 로그인 정보 존재
    	   // 방법 1. sendRedirect
            response.sendRedirect(contextPath + "/session/alreadyLogin.jsp"); 
            // return 이 없을 시 아래로 계속 흘러가면서 진행되기 때문에 실행되지 않음. 
            return;
          
    	   // 방법 2. forward
//    	   RequestDispatcher dispatcher = request.getRequestDispatcher("/session/alreadyLogin.jsp");
//    	   dispatcher.forward(request, response);
//    	   return; // 메소드 exit   
       }
       
        // 세션에 로그인 정보가 없으므로 로그인 페이지 띄워줌.
        // 자신의 현재 위치를 표시해 줌 사용자가 현재 어떤 페이지에 와있는지 직관적으로 확인 가능
//         방법 1. sendRedirect
         response.sendRedirect(contextPath + "/session/loginForm.jsp");
       
       	// 방법 2. forward
//       	RequestDispatcher dispatcher = request.getRequestDispatcher("/session/loginForm.jsp");
//       	dispatcher.forward(request, response);
       	
   }

   /*
    * 로그인 처리 메소드
    */
   protected void doPost(HttpServletRequest request, 
               HttpServletResponse response) 
               throws ServletException, IOException {
      System.out.println("doPost");
      
      // 1. 임시 변수 선언
      String id = "";
      String pwd = "";
      
      String contextPath = request.getContextPath();
      String url = "/session/loginForm.jsp";   //로그인 실패시 다시 로그인 페이지
      
      // 2. 넘어온 아이디 비번 추출
      id = request.getParameter("id");
      pwd = request.getParameter("pwd");
      
      // 3. 아이디 비밀번호 재검증
      if (id == null || id.equals("")) {
         RequestDispatcher rds = request.getRequestDispatcher(url); // 로그인으로
         rds.forward(request, response);
         return;
      }
      if (pwd == null || pwd.equals("")) {
         RequestDispatcher rds = request.getRequestDispatcher(url);  // 로그인으로
         rds.forward(request, response);
         return;
      }
      
      // 화면에서 받은 값으로 객체 생성
      MemberVo mb = new MemberVo(id, pwd);   
      
      // 4 입력한 사용자가 있는지 체크
      MemberVo member = memberDao.getMemberById(mb);
      // 4.1 쿼리 결과로 사용자 객체를 받아옴. 객체가 널이면 회원이 없다는 의미
      if(member == null) {
         // 4.2 로그인 오류 = true로 세팅
         request.setAttribute("message", "아이디와 비밀번호를 확인하세요.");         
         RequestDispatcher rds = request.getRequestDispatcher(url);
         rds.forward(request, response);

      }else {      
         // 4.3 로그인 성공 -> 세션에 사용자 정보 기록
         HttpSession session = request.getSession();
         // 4.4 세션에 객체 저장(Jsp에서 꺼내 쓸때는 ${sessionScope.member.id})
         // 세션에 객체 통으로 삽입 가능.
         // member라는 이름(key)으로 member 객체 저장
         session.setAttribute("member", member);
         
         // 4.5 세션의 유지 시간 설정
         session.setMaxInactiveInterval(3600);   // 세션 유지 시간 지정(옵션, 초단위)
         System.out.println("세션 저장 성공");
         
         url = contextPath + "/session/welcome.jsp";
         response.sendRedirect(url);   
      }

   }
}