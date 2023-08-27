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
	<h3>경로당 현황</h3>

	<ul class="nav nav-tabs">
		<li class="nav-item"><a class="nav-link" data-toggle="tab"
			href="#region">전국 경로당</a></li>
		<li class="nav-item"><a class="nav-link" data-toggle="tab"
			href="#pop">인기 경로당 TOP10</a></li>
	</ul>

	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<!-- <script src="https://cdn.jsdelivr.net/npm/chart.js/dist/chart.umd.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/chartjs-plugin-autocolors@0.2.2/dist/chartjs-plugin-autocolors.min.js"></script>
 -->

	<div id="tab-content">
		<canvas id="Chart" width="900" height="500"></canvas>

		<div class="tab-pane fade show active" id="region"></div>
		<div class="tab-pane fade" id="pop"></div>
	</div>



	<script>
	
	
	
	
	//탭 선택 이벤트
	$('.nav-tabs .nav-item').click(function(){
		initCanvas();
		console.log($(this).index())
		if($(this).index()==0)
		region();
		else
		pop();
	})
	
	
	//초기화면 트리거
		$(function() {
			$('.nav-tabs .nav-item').eq(0).trigger('click')
		});
	
	
	function region(){
		
	}
	
	
	
	<!-- 성별 통계 -->
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
						type : 'doughnut',
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
							responsive : false,
							title : {
								display : true,
								text : '회원통계'
							}
						}
					});
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("some error");
				}
			}); //ajax
		} //그래프




	<!-- 인기경로당 -->
		function pop() {

			let popList = [];
			let likeNum = [];

			$.ajax({
				url : 'pop',
				type : 'get',
				dataType : 'json',
				success : function(data) {
					for (let i = 0; i < data.length; i++) {
						popList.push(data[i].senior_name);
						likeNum.push(data[i].senior_like_num);
					}

					new Chart(document.getElementById('Chart'), {
						type : 'bar',
						data : {
							labels : popList,
							datasets : [ {
								data : likeNum,
								label : "인기많은 경로당",
								backgroundColor : [ "#3e95cd", "#8e5ea2"],
								borderColor : [ "#3e95cd", "#8e5ea2"],
								borderWidth : 1,
								fill : false
							} ]
						},
						options : {
							responsive : false,
							title : {
								display : true,
								text : '경로당 현황'
							}
						}
					});
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("some error");
				}
			}); //ajax
		} //그래프

		
		
		
		function initCanvas() {
			$('canvas#Chart').remove();
			$('#tab-content').append(
					`<canvas id="Chart" width="900" height="500"></canvas>`);
		}
	</script>


</body>
</html>