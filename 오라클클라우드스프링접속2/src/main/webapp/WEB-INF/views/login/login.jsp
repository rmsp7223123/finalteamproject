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
					<form class="user" action="loginCheck" method="post">
						<div class="form-group">
							<input type="text" class="form-control form-control-user"
								id="member_id" aria-describedby="emailHelp" name="member_id"
								placeholder="아이디를 입력해주세요.">
						</div>
						<div class="form-group">
							<input type="password" class="form-control form-control-user"
								name="member_pw" id="member_pw" placeholder="비밀번호를 입력해주세요.">
						</div>
						<button class="btn btn-primary btn-user btn-block form-control">
							Login</button>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>