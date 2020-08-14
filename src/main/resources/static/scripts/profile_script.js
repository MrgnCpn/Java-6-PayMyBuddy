$(() => {
	template.enabledForm('#usrProfileForm', '#input_btn_edit');
	template.enabledForm('#usrProfileForm', '#input_btn_cancel');

	$('.input_select').on('click', function(){
		if ($('#usrProfileForm').hasClass('disabled') == false) $('.main_alerts').addClass('enable').removeClass('disable');
	});

	$('.alert_closeBtn').on('click', function(){
		$('.main_alerts').removeClass('enable').addClass('disable');
	});

	$('#countryAlert .resultSet_ctn .result_option').on('click', function(){
		$('#country').val($(this).data('value'));
		$('#countrySelect .input_select span').text($(this).data('value') + ' - ' + $(this).data('name'))
		template.closeElement(".main_alerts", "enable", "disable");
	})
})