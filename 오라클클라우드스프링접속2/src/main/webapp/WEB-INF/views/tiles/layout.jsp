<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>우동탑</title>
<!-- ** Mobile Specific Metas ** -->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="Portfolio HTML Template">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=5.0">
<meta name="author" content="Themefisher">
<meta name="generator"
	content="Themefisher Html5 Portfolio Template v1.0">

<!-- theme meta -->
<meta name="theme-name" content="phantom" />

<!-- Essential Stylesheets -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Libre+Baskerville:400,400i|Open+Sans:400,600,700,800">
<link rel="stylesheet"
	href="<c:url value = '/theme/plugins/bootstrap/bootstrap.min.css'/>">
<link rel="stylesheet" href="<c:url value = '/theme/plugins/animate.css'/>">
<link rel="stylesheet" href="<c:url value = '/theme/plugins/slick/slick.css'/>">
<link rel="stylesheet"
	href="<c:url value = '/theme/plugins/slick/slick-theme.css'/>">
<link rel="stylesheet"
	href="<c:url value = '/theme/plugins/themefisher-fonts/css/themefisher-fonts.min.css'/>">
<!--  cdnjs.com > fontawesome 검색 > styling, javascript 선언문 복사해서 넣기 -->        
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/js/all.min.js" ></script>
<!--  //fontawesome -->
<!-- Main Stylesheet -->
<link rel="stylesheet" href="<c:url value = '/theme/css/style.css'/>">
<link href="<c:url value='/css/common.css?${now }'/>" rel="stylesheet" />
<link href="<c:url value='/css/table.css?${now }'/>" rel="stylesheet" />


<!--Favicon-->
<link rel="icon" href="<c:url value = '/theme/images/logo.png'/>"
	type="image/x-icon">
	
	
<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
	<script src="<c:url value='/js/common.js?${now }'/>"></script>

<!-- fontawesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<div class="preloader">
    <div class="loading-mask"></div>
    <div class="loading-mask"></div>
    <div class="loading-mask"></div>
    <div class="loading-mask"></div>
    <div class="loading-mask"></div>
  </div>
	<div class="container" style = "width:100%; padding-left : 0; padding-right : 0;">
<!-- 	<div class="container site-wrapper" style = "width:100%; padding-left : 0; padding-right : 0;"> -->
	<div style= "padding : 80px 100px 0px 100px;">
	<a href="<c:url value ='/home'/>" class="page-close" id = "page-close"><i class="tf-ion-close"></i></a>
		<tiles:insertAttribute name ="container"/>
		</div>
	</div>
	
	<nav class="page-nav clear" >
  <div class="container">
    <div class="flex flex-middle space-between">
<!--       <span class="prev-page"><a href="#" class="link">← Prev Page</a></span> -->
      <span class="copyright">footerddddddddddddddddd</span>
<!--       <span class="next-page"><a href="#" class="link">Next Page →</a></span> -->
    </div>
  </div>
</nav>


<!-- <script src="https://code.jquery.com/jquery-2.2.4.min.js"></script> -->
	<script src="<c:url value = '/theme/plugins/bootstrap/bootstrap.min.js'/>"></script>
	<script src="<c:url value = '/theme/plugins/jquery.nicescroll.min.js'/>"></script>
	<script src="<c:url value = '/theme/plugins/isotope/isotope.pkgd.min.js'/>"></script>
	<script src="<c:url value = '/theme/plugins/slick/slick.min.js'/>"></script>
	
	<script src="<c:url value = '/theme/js/script.js'/>"></script>
	<script src="<c:url value = '/theme/js/common.js'/>"></script>
    <!-- Bootstrap core JS-->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	
	<script>
	
	
	//점검 후 수정 해야함;.
		
	 	function getCurrentUrl() {
	        return window.location.href;
	    }

	    function toggleButtonVisibility() {
	        var button = document.getElementById("page-close");
	        var currentUrl = getCurrentUrl();
	        var homeUrl = "http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/home";
	        if (currentUrl === homeUrl) {
	            button.style.display = "none";
	        } else {
	            button.style.display = "block";
	        }
	    }

	    window.onload = function () {
	        toggleButtonVisibility();
	    }
	    $(function(){
// 	    	$('.container').css('z-index', 9999);
	    	setTimeout(() => $('.preloader').remove(), 2000);
	    })
	</script>
</body>
</html>