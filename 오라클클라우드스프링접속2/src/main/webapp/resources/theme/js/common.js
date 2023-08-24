$(function(){
		$('#listModal button.close').click(function(){
			$('#listModal').removeClass("show").addClass("fade");
			$("#listModal").modal("hide");
		})
//		$("#listModal").on('show.bs.modal', function() {
//            $(this).data('bs.modal')._config.backdrop = false;
//        });
	})