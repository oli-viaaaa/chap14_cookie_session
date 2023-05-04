<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<title>welcome</title>
</head>
<body>
	<h1 style="text-align:center">환영합니다.</h1>
	
	${cookie.id.value}님 환영합니다.<br><br>
	
	<a href="${pageContext.request.contextPath}/cookieLogout">로그아웃</a>

</body>
</html>