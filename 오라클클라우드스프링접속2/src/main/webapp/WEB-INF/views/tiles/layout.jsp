<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var="now" value="<%=new java.util.Date()%>" />

<!DOCTYPE html>
<html>
<c:choose>
	<c:when test="${  category  eq   'cu'  }">
		<c:set var="title" value="회원 관리" />
	</c:when>
	<c:when test="${category eq 'cs'}">
		<c:set var="title" value="고객센터 관리" />
	</c:when>
	<c:when test="${category eq 'go'}">
		<c:set var="title" value="고독사 관리" />
	</c:when>
	<c:when test="${category eq 'vi'}">
		<c:set var="title" value="시각화" />
	</c:when>
	<c:when test="${category eq 'test'}">
		<c:set var="title" value="보류" />
	</c:when>

</c:choose>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>우동탑 <c:if test="${not empty title}">- ${title} </c:if></title>

<!-- Custom fonts for this template-->
<link href="<c:url value = '/vendor/fontawesome-free/css/all.min.css'/>"
	rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="<c:url value = '/css/sb-admin-2.min.css'/>" rel="stylesheet">

</head>

<body id="page-top">

	<!-- Page Wrapper -->
	<div id="wrapper">

		<!-- Sidebar -->
		<ul
			class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion"
			id="accordionSidebar">

			<!-- Sidebar - Brand -->
			<a
				class="sidebar-brand d-flex align-items-center justify-content-center"
				href="<c:url value = '/home'/>">
				<div class="sidebar-brand-icon rotate-n-15">
					<i class="fas fa-laugh-wink"></i>
				</div>
				<div class="sidebar-brand-text mx-3">우동탑</div>
			</a>

			<!-- Divider -->
			<hr class="sidebar-divider my-0">
			<!-- Nav Item - Pages Collapse Menu -->
			<li class="${category eq 'cu' ? 'active':''} nav-item"><a
				class="nav-link" href="<c:url value = '/cu/home'/>"> <i
					class="fas fa-fw fa-cog"></i> <span>회원 관리</span>
			</a></li>
			<li class="${category eq 'cs' ? 'active':''} nav-item"><a
				class="nav-link" href="<c:url value = '/cs/home'/>"><i
					class="fas fa-fw fa-wrench"></i> <span>고객센터 관리</span> </a></li>
			<!-- Nav Item - Pages Collapse Menu -->
			<li class="${category eq 'go' ? 'active':''} nav-item"><a
				class="nav-link" href="<c:url value = '/go/home'/>"> <i
					class="fas fa-fw fa-folder"></i> <span>고독사 관리</span>
			</a></li>
			<li class="${category eq 'vi' ? 'active':''} nav-item"><a
				class="nav-link" href="<c:url value = '/vi/home'/>"> <i
					class="fas fa-fw fa-chart-area"></i> <span>시각화</span>
			</a></li>

			<!-- Nav Item - Tables -->
			<li class="${category eq 'test' ? 'active':''} nav-item"><a
				class="nav-link" href="#"> <i class="fas fa-fw fa-table"></i> <span>보류</span>
			</a></li>

		</ul>
		<!-- End of Sidebar -->

		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">

			<!-- Main Content -->
			<div id="content">

				<!-- Topbar -->
				<nav
					class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

					<!-- Sidebar Toggle (Topbar) -->
					<button id="sidebarToggleTop"
						class="btn btn-link d-md-none rounded-circle mr-3">
						<i class="fa fa-bars"></i>
					</button>

					<!-- Topbar Navbar -->
					<ul class="navbar-nav ml-auto">

						<!-- Nav Item - Search Dropdown (Visible Only XS) -->
						<li class="nav-item dropdown no-arrow d-sm-none"><a
							class="nav-link dropdown-toggle" href="#" id="searchDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <i class="fas fa-search fa-fw"></i>
						</a> <!-- Dropdown - Messages -->
							<div
								class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
								aria-labelledby="searchDropdown">
								<form class="form-inline mr-auto w-100 navbar-search">
									<div class="input-group">
										<input type="text"
											class="form-control bg-light border-0 small"
											placeholder="Search for..." aria-label="Search"
											aria-describedby="basic-addon2">
										<div class="input-group-append">
											<button class="btn btn-primary" type="button">
												<i class="fas fa-search fa-sm"></i>
											</button>
										</div>
									</div>
								</form>
							</div></li>

						<!-- Nav Item - User Information -->
						<li class="nav-item dropdown no-arrow"><a
							class="nav-link dropdown-toggle" href="#" id="userDropdown"
							role="button" data-toggle="dropdown" aria-haspopup="true"
							aria-expanded="false"> <span
								class="mr-2 d-none d-lg-inline text-gray-600 small">관리자</span> <img
								class="img-profile rounded-circle"
								src="<c:url value = '/img/undraw_profile.svg'/>">
						</a> <!-- Dropdown - User Information -->
							<div
								class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
								aria-labelledby="userDropdown">
								<a class="dropdown-item" href="#"> <i
									class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i> Profile
								</a> <a class="dropdown-item" href="#"> <i
									class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i>
									Settings
								</a> <a class="dropdown-item" href="#"> <i
									class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
									Activity Log
								</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#" data-toggle="modal"
									data-target="#logoutModal"> <i
									class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
									Logout
								</a>
							</div></li>

					</ul>

				</nav>
				<div class="container-fluid">
					<tiles:insertAttribute name="container" />
				</div>
			</div>
			<!-- Footer -->
			<footer class="sticky-footer bg-white">
				<div class="container my-auto">
					<div class="copyright text-center my-auto">
						<span>Copyright &copy; Your Website 2021</span>
					</div>
				</div>
			</footer>
		</div>

	</div>
	</div>
	<!-- Scroll to Top Button-->
	<a class="scroll-to-top rounded" href="#page-top"> <i
		class="fas fa-angle-up"></i>
	</a>

	<!-- Bootstrap core JavaScript-->
	<script src="<c:url value = '/vendor/jquery/jquery.min.js'/>"></script>
	<script
		src="<c:url value = '/vendor/bootstrap/js/bootstrap.bundle.min.js'/>"></script>

	<!-- Core plugin JavaScript-->
	<script
		src="<c:url value = '/vendor/jquery-easing/jquery.easing.min.js'/>"></script>

	<!-- Custom scripts for all pages-->
	<script src="<c:url value = '/js/sb-admin-2.min.js'/>"></script>

	<!-- Page level plugins -->
	<script src="<c:url value = '/vendor/chart.js/Chart.min.js'/>"></script>

	<!-- Page level custom scripts -->
	<script src="<c:url value = '/js/demo/chart-area-demo.js'/>"></script>
	<script src="<c:url value = '/js/demo/chart-pie-demo.js'/>"></script>

</body>

</html>