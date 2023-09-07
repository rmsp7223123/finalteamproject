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

	<ul class="nav nav-tabs active mt-3">
		<li class="nav-item active"><a class="nav-link" data-toggle="tab"
			href="#gender">성별 통계</a></li>
		<li class="nav-item"><a class="nav-link" data-toggle="tab"
			href="#age">연령 통계</a></li>
		<li class="nav-item"><a class="nav-link" data-toggle="tab"
			href="region">지역 통계</a></li>
	</ul>

	<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<!-- <script src="https://cdn.jsdelivr.net/npm/chart.js/dist/chart.umd.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/chartjs-plugin-autocolors@0.2.2/dist/chartjs-plugin-autocolors.min.js"></script>
 -->

	<div id="tab-content" class="container">
	<div class="canvas">
		<canvas id="Chart" width="900" height="500" style="margin-left : 100px"></canvas>
		</div>

		<div class="tab-pane fade show active" id="gender"></div>
		<div class="tab-pane fade" id="age"></div>
	</div>



	<script>
	
	
	
	
	//탭 선택 이벤트
	$('.nav-tabs .nav-item').click(function(){
		initCanvas();
		console.log($(this).index())
		if($(this).index()==0)
		gender();
		else if($(this).index()==1)
		age();
		else
		count();
	})
	
	
	//초기화면 트리거
		$(function() {
			$('.nav-tabs .nav-item').eq(0).trigger('click')
		});
	
	
	
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
								backgroundColor : [ "#e94c3b", "#01adc1" ],
								borderColor : [ "#e94c3b", "#01adc1" ],
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




	<!-- 연령 통계 -->
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
						type : 'line',
						data : {
							labels : ageList,
							datasets : [ {
								data : population,
								label : "연령현황",
								backgroundColor : [ "#e94c3b", "#01adc1" ],
								borderColor : [ "#f7941d" ],
								borderWidth : 3,
								fill : false
							} ]
						},
						options : {
							responsive : false,
							plugins:{
							legend: {
	                            display: false
	                        },
							title : {
								display : true,
								text : '연령현황'
							}
							}
						}
					});
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("some error");
				}
			}); //ajax
		} //그래프

		
		<!-- 지역별 통계 -->
		function count() {
			let region = [];
			let count = [];
			
			$.ajax({
				url : 'count',
				type : 'get',
				dataType : 'json',
				success : function(data) {
					
					for (let i = 0; i < data.length; i++) {
						region.push(data[i].region);
						count.push(data[i].count);
					}

					new Chart(document.getElementById('Chart'), {
						type : 'bar',
						data : {
							labels : region,
							datasets : [ {
								data : count,
								label : "전국 경로당",
								backgroundColor : count.map(value => {
									if (value < 1) {
		                                return "#775995";
		                            } else if (value < 3) {
		                                return "#29797f";
		                            } else if (value < 5) {
		                                return "#29d1bb";
		                            } else if (value < 7) {
		                            	return "#f8cf63";
									}else {
		                                return "#fc7b1d";
		                            }
								}),
								borderColor : [  ],
								borderWidth : 0,
								fill : false
							} ]
						},
						options : {
							responsive : false,
							indexAxis: 'y',
							plugins:{
								legend: {
		                            display: false
		                        },
							title : {
								display : true,
								text : '지역별 회원 수'
							}
							},
							scales: {
					            x: {
					                beginAtZero: true,
					                ticks: {
					                    stepSize: 1,
					                    precision: 0
					                }
					            }
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
					`<canvas id="Chart" width="900" height="500" style="margin-left : 100px"></canvas>`);
		}
	</script>


</body>
</html>