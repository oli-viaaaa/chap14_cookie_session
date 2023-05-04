<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<title>welcome</title>
</head>
<body>
	<h1 style="text-align:center">환영합니다.</h1>
<!-- 	세션에 통으로 넣어둔 member에서 id꺼내올 수 있음. -->
		<b>${sessionScope.member.id}</b>님이 로그인 하셨습니다.<br><br>
	<a href="${pageContext.request.contextPath}/sessionLogout"><b>로그아웃</b></a>

</body>
</html>