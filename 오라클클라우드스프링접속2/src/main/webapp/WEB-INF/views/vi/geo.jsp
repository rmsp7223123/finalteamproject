<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<!-- <style type="text/css">
#container {
	width: 700px;
	min-height: 700px;
	float: left;
	margin: 15px 35px;
	background-color: #ffffff;
}

#states path {
	fill: #d3d3d3;
	stroke: #000000;
	stroke-width: 1.5px;
}

#states path:hover {
	fill: #009300;
}

#states .active {
	fill: #00B700;
}

#states .activeDetail {
	fill: #00B700;
}

#states path {
	cursor: pointer;
}

#states text {
	cursor: pointer;
	font-size: 12px;
	fill: #fff;
}
</style> -->

<meta charset="UTF-8">
<title>Insert title here</title>
<%-- <link href="<c:url value='/css/korea.css'/>" rel="stylesheet" /> --%>
</head>
<%-- <script src="<c:url value = '/js/korea.js'/>"></script> --%>
<script src="<c:url value = '/js/d3.js'/>"></script>

<body>
	<h3>지도 그리기</h3>


	<div id="container"></div>



	<script type="text/javascript">

    drawMap('#container');

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
//        .call(zoom);
    
    // 선택한 지역에 따라 색상을 다르게 설정하기 위한 색상 매핑 정보
        var regionColors = {
            '서울특별시': '#FF5733',
            '부산광역시': '#FFC300',
            '인천광역시': '#36A2EB',
            '대구광역시': '#4CAF50',
            '광주광역시': '#9C27B0'
        };
    
    
        $.ajax({
            url: 'region',
            type: 'get',
            dataType: 'json',
            success: function(data) {
                // 데이터 처리 후 색상 설정
                var regionColors = {}; // 지역명과 색상을 저장할 객체 초기화
                
                // 데이터 처리 예시 (data가 배열로 가정)
                for (var i = 0; i < data.length; i++) {
                    var region = data[i].region; // 지역명 가져오기
                    var count = data[i].count;   // 조회 값 가져오기
                    
                    // 조건에 따라 지역별로 다른 색상 적용
                    if (count < 500) {
                        regionColors[region] = '#775995';
                    } else if (count < 1000) {
                    	regionColors[region] = '#29797f';
					}else if (count < 1500) {
						regionColors[region] = '#29d1bb';
					}else if (count < 3000) {
                        regionColors[region] = '#f8cf63';
                    }else {
                        regionColors[region] = '#fc7b1d';
                    }
                }
                
     
    states
        .append('rect')
        .attr('class', 'background')
        .attr('width', width + 'px')
        .attr('height', height + 'px')
    	.attr('fill', '#ffffff'); //배경색(흰색)
		
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

    function zoom() {
        projection.translate(d3.event.translate).scale(d3.event.scale);
        states.selectAll('path').attr('d', path);
        labels.attr('transform', translateTolabel);
    }
			
}
</script>
</body>
</html>