<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
	<h3>회원 통계</h3>
	
	<ul class="nav nav-tabs">
		<li class="nav-item"><a class="nav-link">성별 통계</a></li>
		<li class="nav-item"><a class="nav-link">연령 통계</a></li>
	</ul>

	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<!-- <script src="https://cdn.jsdelivr.net/npm/chart.js/dist/chart.umd.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/chartjs-plugin-autocolors@0.2.2/dist/chartjs-plugin-autocolors.min.js"></script>
 -->

	<canvas id="Chart" width="900" height="500"></canvas>



	<script>
	$(function(){
			gender();
		});

		function gender() {
			let genderList = [];
			let population = [];

			$.ajax({
				url : 'genderchart',
				type : 'get',
				dataType : 'json',
				success : function(data) {
					for (let i = 0; i < data.length; i++) {
						genderList.push(data[i].gender);
						population.push(data[i].population);
					}

					new Chart(document.getElementById('Chart'), {
						type : 'bar',
						data : {
							labels : genderList,
							datasets : [ {
								data : population,
								label : "성별현황",
								backgroundColor : [ "#3e95cd", "#8e5ea2" ],
								borderColor : [ "#3e95cd", "#8e5ea2" ],
								borderWidth : 1,
								fill : false
							} ]
						},
						options : {
							responsive:false,
							title : {
								display : true,
								text : '회원통계'
							}
						}
					});
				},
				error :function(XMLHttpRequest, textStatus, errorThrown) {
				     alert("some error");
				  }
			}); //ajax
		} //그래프
	</script>
	
	<!-- <script>
	$(function(){
			age();
		});

		function age() {
			let ageList = [];
			let population = [];

			$.ajax({
				url : 'agechart',
				type : 'get',
				dataType : 'json',
				success : function(data) {
					for (let i = 0; i < data.length; i++) {
						ageList.push(data[i].age);
						population.push(data[i].population);
					}

					new Chart(document.getElementById('Chart'), {
						type : 'doughnut',
						data : {
							labels : ageList,
							datasets : [ {
								data : population,
								label : "연령현황",
								backgroundColor : [ "#3e95cd", "#8e5ea2" ],
								borderColor : [ "#3e95cd", "#8e5ea2" ],
								borderWidth : 1,
								fill : false
							} ]
						},
						options : {
							responsive:false,
							title : {
								display : true,
								text : '회원통계'
							}
						}
					});
				},
				error :function(XMLHttpRequest, textStatus, errorThrown) {
				     alert("some error");
				  }
			}); //ajax
		} //그래프
	</script> -->


</body>
</html>