<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test</title>
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

<!-- Main Stylesheet -->
<link rel="stylesheet" href="<c:url value = '/theme/css/style.css'/>">
<link href="<c:url value='/css/common.css?${now }'/>" rel="stylesheet" />

<!-- Theme Stylesheet -->
<link rel="stylesheet" href="#" id="color-changer">

<!--Favicon-->
<link rel="icon" href="<c:url value = '/theme/images/favicon.png'/>"
	type="image/x-icon">

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
	<div style= "padding : 80px 100px 0px 100px;">
		<tiles:insertAttribute name ="container"/>
		</div>
	</div>
	
	<nav class="page-nav clear" >
  <div class="container">
    <div class="flex flex-middle space-between">
      <span class="prev-page"><a href="services.html" class="link">← Prev Page</a></span>
      <span class="copyright">Copyright © 2021, Designed &amp; Developed by <a href="https://themefisher.com/">Themefisher</a>.</span>
      <span class="next-page"><a href="works.html" class="link">Next Page →</a></span>
    </div>
  </div>
</nav>
	<script src="<c:url value = '/theme/plugins/jquery-2.2.4.min.js'/>"></script>
	<script src="<c:url value = '/theme/plugins/bootstrap/bootstrap.min.js'/>"></script>
	<script src="<c:url value = '/theme/plugins/jquery.nicescroll.min.js'/>"></script>
	<script src="<c:url value = '/theme/plugins/isotope/isotope.pkgd.min.js'/>"></script>
	<script src="<c:url value = '/theme/plugins/slick/slick.min.js'/>"></script>

	<script src="<c:url value = '/theme/js/script.js'/>"></script>
	<script>
		
	</script>
</body>
</html>