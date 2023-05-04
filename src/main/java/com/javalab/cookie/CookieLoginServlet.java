package com.javalab.cookie;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javalab.dao.MemberDAO;
import com.javalab.vo.MemberVo;

/**
 * 쿠키를 사용한 로그인 처리 서블릿 1. Post 방식으로 로그인 처리하는 역할 - doPost() 메소드 2. Get 방식으로 로그인 폼을
 * 띄워주는 역할 - doGet() 메소드
 */
@WebServlet("/cookieLogin")
public class CookieLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// [멤버 변수]
	// 싱글톤으로 만든 boardDao 객체 얻어옴
	private MemberDAO memberDao = MemberDAO.getInstance();

	/*
	 * 1. 로그인 처리 메소드
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("doPost");

		// 사용자가 입력한 값을 읽어올때 한글 깨짐 방지
		request.setCharacterEncoding("utf-8");

		String contextPath = request.getContextPath();

		// 1. 파라미터 추출
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");

		// 2. 화면에서 전달받은 id/pwd로 회원 객체 생성
		MemberVo member = new MemberVo(id, pwd);

		// 3. 생성된 객체를 DAO에 보내서 회원정보 확인
		member = memberDao.getMemberById(member);
		if (member != null) { // 회원 존재
			// 쿠키 생성
			Cookie idCookie = new Cookie("id", member.getId());
			response.addCookie(idCookie);
			System.out.println("쿠키 저장 성공");
		} else { // 회원 정보가 데이터베이스에 없으면
			// 로그인 페이지로 이동하는데 두가지 방법으로 이동

			// [방법.1] sendRedirect
			// response.sendRedirect(contextPath + "/cookie/loginFail.jsp");

			// [방법.2] forward
			String errorMsg = "아이디와 비밀번호를 확인하세요";
			request.setAttribute("errorMsg", errorMsg);

			/*
			 * [forward와 return] 메소드가 끝나기 전에 forward를 하면 바로 제어가 이동되지 않고 아랫 문장 계속 수행함. 그러다가
			 * 널포인터 익셉션 발생할 가능성 있음. 그래서 return을 해서 메소드를 완전히 빠져나가도록 해줘야 함.
			 */
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cookie/loginForm.jsp");
			dispatcher.forward(request, response);
			return;
		}

		request.setAttribute("id", member.getId());

		// [방법.1] forward
		// welcome.jsp로 이동해도 주소창은 cookieLogin으로 보임
		// RequestDispatcher dispatcher =
		// request.getRequestDispatcher("/cookie/welcome.jsp");
		// dispatcher.forward(request, response);

		// [방법.2] sendRedirect
		// welcome.jsp로 이동하고 주소창 welcome.jsp로 보임
		response.sendRedirect(contextPath + "/cookie/welcome.jsp");

	}

	/*
	 * 2. 로그인폼을 띄워주는 메소드
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doGet");

		String contextPath = request.getContextPath();

		// 1. 입력한 아이디로 쿠키가 이미 만들어졌는지 확인
		// 전체 쿠키 추출
		Cookie[] cookies = request.getCookies();
		// 쿠키가 하나라도 존재하면
		if (cookies != null) {
			// 쿠키를 하나씩 꺼내서
			for (Cookie cookie : cookies) {
				// 쿠키명이 id인 쿠키가 있으면
				if (cookie.getName().equals("id")) {
					// 로그인했으므로 로그드인 페이지로(jsp 파일명 대소문자구분)
					System.out.println("쿠키 존재함");

					// 페이지 이동 방법 두가지
					// [방법.1] sendRedirect
					// response.sendRedirect(contextPath + "/cookie/alreadyLogin.jsp");

					// [방법.2] forward
					RequestDispatcher dispatcher = request.getRequestDispatcher("/cookie/alreadyLogin.jsp");
					dispatcher.forward(request, response);
					return; // 메소드 exit
				}
			}
		}

		// 쿠키가 없으므로 로그인 페이지를 띄워준다.
		System.out.println("쿠키가 존재하지 않아서 로그인 폼을 띄워줍니다.");
		// [방법.1] sendRedirect
		// response.sendRedirect(contextPath + "/cookie/loginForm.jsp");

		// [방법.2] forward
		RequestDispatcher dispatcher = request.getRequestDispatcher("/cookie/loginForm.jsp");
		dispatcher.forward(request, response);
	}
}
