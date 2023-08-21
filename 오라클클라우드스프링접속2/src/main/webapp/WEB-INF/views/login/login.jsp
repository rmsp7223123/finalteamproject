<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-lg-6">
				<div class="p-5">
					<div class="text-center">
						<h1 class="h4 text-gray-900 mb-4">로그인</h1>
					</div>
					<form class="user">
						<div class="form-group">
							<input type="email" class="form-control form-control-user"
								id="exampleInputEmail" aria-describedby="emailHelp"
								placeholder="아이디를 입력해주세요.">
						</div>
						<div class="form-group">
							<input type="password" class="form-control form-control-user"
								id="exampleInputPassword" placeholder="비밀번호를 입력해주세요.">
						</div>
						<a href="<c:url value = '/home/'/>"
							class="btn btn-primary btn-user btn-block"> Login </a>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>