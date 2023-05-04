<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
<title>로그인 실패</title>
</head>
<body>
	<h1 style="text-align:center">로그인에 실패했습니다.</h1>
	
	<a href="${pageContext.request.contextPath}/sessionLogin">로그인 페이지로 이동</a>

</body>
</html>