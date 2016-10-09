$(function() {
	$(".calendario").datepicker({
		changeMonth : true,
		changeYear : true,
		dateFormat: "dd/mm/yy",
		dayNamesMin: [ "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab" ],
		monthNamesShort: [ "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez" ],
		minDate: "-20Y", 
		maxDate: "+10Y"
	});
	$(".calendarioMesAno").keypress(function (e){
		if (e.keyCode != 8 && e.keyCode != 46 ) {
			var input = $(this);
			var tam = input.val().length;
			var valor = input.val();
			if (tam == 2) {
				input.val(valor + "/");
			}
		}
	});
	
});
