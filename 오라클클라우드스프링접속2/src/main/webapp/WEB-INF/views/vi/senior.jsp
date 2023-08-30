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

	<ul class="nav nav-tabs active">
		<li class="nav-item active"><a class="nav-link" data-toggle="tab"
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

	<div id="tab-content" class="container">
	<div class="canvas">
		<canvas id="Chart" width="900" height="500"></canvas>
		</div>

		<div class="tab-pane fade" id="region"></div>
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
	
	
	//초기화면 탭 강제 클릭
		$(function() {
			$('.nav-tabs .nav-item').eq(0).trigger('click');
		})
	
	
	
	<!-- 지역별 통계 -->
		function region() {
			let region = [];
			let count = [];
			
			$.ajax({
				url : 'region',
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
									if (value < 500) {
		                                return "#775995";
		                            } else if (value < 1000) {
		                                return "#29797f";
		                            } else if (value < 1500) {
		                                return "#29d1bb";
		                            } else if (value < 3000) {
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
								text : '전국 경로당'
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
								backgroundColor : [ "#e94c3b", "#01adc1", "#f7941d", "#0d9788", "#f7b419" ],
								borderColor : [ ],
								borderWidth : 0,
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
								text : '인기많은 경로당'
							}
							},
							scales: {
					            y: {
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
					`<canvas id="Chart" width="900" height="500"></canvas>`);
		}
	</script>


</body>
</html>