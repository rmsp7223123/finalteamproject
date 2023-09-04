<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<script src="<c:url value = '/js/d3.js'/>"></script>
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

	<div id="tab-content" class="container d-flex align-items-center">
		<div id="container"></div>
		<div class="canvas">
			<canvas id="Chart" width="900" height="500"
				style="margin-left: 100px"></canvas>
		</div>
	</div>


	<script>
	
	//탭 선택 이벤트
	$('.nav-tabs .nav-item').click(function(){
		console.log($(this).index())
		if($(this).index()==0){
			initCanvas();
			drawMap('#container');
			legend();
			//region();
		}
			
		else{
			initdiv();
			pop();
			$('#legend').remove();
		}
			
	});
	
	
	//초기화면 탭 강제 클릭
		$(function() {
			$('.nav-tabs .nav-item').eq(0).trigger('click');
		})
	
	
	//범례
	function legend() {
    var legendDiv = $('<div>').attr('id', 'legend').css({
        border: '1px dotted',
        padding: '10px',
        position: 'fixed',
        top: '400px',
        left: '1200px'   
    }).text('전국 경로당 수');
    $('#tab-content').append(legendDiv);

    var wrap1 = $('<div>').addClass('wrap');
    var nemo1 = $('<div>').addClass('nemo').css('background-color', '#fc5d1e');
    var text1 = $('<p>').text('3,000개 이상');
    wrap1.append(nemo1).append(text1);
    
    var wrap2 = $('<div>').addClass('wrap');
    var nemo2 = $('<div>').addClass('nemo').css('background-color', '#fc936a');
    var text2 = $('<p>').text('1,500개 이상');
    wrap2.append(nemo2).append(text2);
    
    var wrap3 = $('<div>').addClass('wrap');
    var nemo3 = $('<div>').addClass('nemo').css('background-color', '#ffb699');
    var text3 = $('<p>').text('1,000개 이상');
    wrap3.append(nemo3).append(text3);
    
    var wrap4 = $('<div>').addClass('wrap');
    var nemo4 = $('<div>').addClass('nemo').css('background-color', '#fce5dc');
    var text4 = $('<p>').text('1,000개 미만');
    wrap4.append(nemo4).append(text4);
   
    legendDiv.append(wrap1);
    legendDiv.append(wrap2);
    legendDiv.append(wrap3);
    legendDiv.append(wrap4);
}
	
	
	
		//지도 그리기
		function drawMap(target) {
		    var width = 700; //지도의 넓이
		    var height = 700; //지도의 높이
		    var initialScale = 5500; //확대시킬 값
		    var initialX = -11900; //초기 위치값 X
		    var initialY = 4050; //초기 위치값 Y
		    var labels;

		    var projection = d3.geo
		        .mercator()
		        .scale(initialScale)
		        .translate([initialX, initialY]);
		    var path = d3.geo.path().projection(projection);
		    var zoom = d3.behavior
		        .zoom()
		        .translate(projection.translate())
		        .scale(projection.scale())
		        .scaleExtent([height, 800 * height])
		        .on('zoom', zoom);

		    var svg = d3
		        .select(target)
		        .append('svg')
		        .attr('width', width + 'px')
		        .attr('height', height + 'px')
		        .attr('id', 'map')
		        .attr('class', 'map');

		    var states = svg
		        .append('g')
		        .attr('id', 'states')
		    
		        $.ajax({
		            url: 'region',
		            type: 'get',
		            dataType: 'json',
		            success: function(data) {
		                // 데이터 처리 후 색상 설정
		                var regionColors = {}; // 지역명과 색상
		                
		                for (var i = 0; i < data.length; i++) {
		                    var region = data[i].region;
		                    var count = data[i].count;
		                    
		                    // 조건에 따라 지역별로 다른 색상 적용
		                    if (count >= 3000) {
		                        regionColors[region] = '#fc5d1e';
		                    } else if (count >= 1500 && count < 3000) {
		                    	regionColors[region] = '#fc936a';
							}else if (count >= 1000 && count < 1500) {
								regionColors[region] = '#ffb699';
							}else if (count < 1000) {
		                        regionColors[region] = '#fce5dc';
		                    }else {
		                        regionColors[region] = '#ff4800';
		                    }
		                }
		                
		     
		    states
		        .append('rect')
		        .attr('class', 'background')
		        .attr('width', width + 'px')
		        .attr('height', height + 'px')
		    	.attr('fill', '#a6a6a6'); //배경색(흰색)
				
		    //geoJson데이터를 파싱하여 지도그리기
		    d3.json('/cloudWeb/json/korea.json', function(json) {
		        states
		            .selectAll('path') //지역 설정
		            .data(json.features)
		            .enter()
		            .append('path')
		            .attr('d', path)
		            .attr('id', function(d) {
		                return 'path-' + d.properties.name_eng;
		            })
		            
		            // 선택한 지역명에 맞는 색상 적용
		            .style('fill', function(d) {
		            var selectedRegion = d.properties.name; // 예시로 지역명 가져오기
		            return regionColors[selectedRegion] || '#585858'; // 색상 매칭 없을 경우 기본 색상
		        });

		        labels = states
		            .selectAll('text')
		            .data(json.features) //라벨표시
		            .enter()
		            .append('text')
		            .attr('transform', translateTolabel)
		            .attr('id', function(d) {
		                return 'label-' + d.properties.name_eng;
		            })
		            .attr('text-anchor', 'middle')
		            .attr('dy', '.35em')
		            .text(function(d) {
		                return d.properties.name;
		            });
		    });
		    	
		            },
		            error: function(XMLHttpRequest, textStatus, errorThrown) {
		                alert("some error");
		            }
		        });

		    //텍스트 위치 조절 - 하드코딩으로 위치 조절을 했습니다.
		    function translateTolabel(d) {
		        var arr = path.centroid(d);
		        if (d.properties.code == 31) {
		            //서울 경기도 이름 겹쳐서 경기도 내리기
		            arr[1] +=
		                d3.event && d3.event.scale
		                    ? d3.event.scale / height + 20
		                    : initialScale / height + 20;
		        } else if (d.properties.code == 34) {
		            //충남은 조금 더 내리기
		            arr[1] +=
		                d3.event && d3.event.scale
		                    ? d3.event.scale / height + 10
		                    : initialScale / height + 10;
		        }
		        return 'translate(' + arr + ')';
		    }
					
		}
	
	
	<!-- 지역별 통계 -->
		/* function region() {
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
		} //그래프 */




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
					`<div id="container"></div>`);
		}
		function initdiv() {
			$('div#container').remove();
			$('#tab-content').append(
					`<canvas id="Chart" width="900" height="500" style="margin-left : 100px"></canvas>`);
		}
	</script>
</body>
</html>