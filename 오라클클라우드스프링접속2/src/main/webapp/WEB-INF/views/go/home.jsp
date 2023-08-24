<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script
	src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js'></script>
<script src='<c:url value = "/fullcalendar/dist/index.global.js"/>'></script>
<script>
	document.addEventListener('DOMContentLoaded', function() {
		var calendarEl = document.getElementById('calendar');

		var calendar = new FullCalendar.Calendar(calendarEl, {
			locale : 'ko',
			editable : true,
			selectable : true,
			businessHours : true,
			dayMaxEvents : true,
			events: '<c:url value = "/go/addEvents"/>',
			customButtons: {
				myCustomButton: {
					text: '전체보기', // 버튼 텍스트
					click: function() {
			            window.location.href = '<c:url value="/go/list" />';
			        }
				}
			},
			headerToolbar: {
				left: 'prev,next today myCustomButton', // 버튼 추가
				center: 'title',
				right: 'dayGridMonth,timeGridWeek,timeGridDay'
			},
			views: {
				dayGridMonth: { buttonText: '월간' },
				timeGridWeek: { buttonText: '주간' },
				timeGridDay: { buttonText: '일간' }
			},
			eventClick: function(info) {
	            var eventTitle = info.event.title;
	            var eventStart = info.event.start;
	            alert('이벤트 제목: ' + eventTitle + '\n시작 시간: ' + eventStart);
	        }
		});
		calendar.render();
	});
   
	
</script>
<style>
body {
	padding: 0;
	font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
	font-size: 14px;
}

#calendar {
	max-width: 1100px;
	max-width: 950px;
	margin: 0 auto;
}
</style>

</head>
<body>
	<div id="calendar"></div>
<!-- 	<div class="modal fade" id="listModal" tabindex="-1" role="dialog" aria-labelledby="listModalLabel" aria-hidden="true"> -->
		<jsp:include page="/WEB-INF/views/include/modal-list.jsp"/>
<!-- 	</div> -->

</body>
</html>