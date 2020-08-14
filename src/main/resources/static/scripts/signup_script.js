$(() => {
	template.changeRadio('.currencyRadio', 'selected', 'div.input_ctn #currency');
	template.openElement(".main_alerts", ".input_select", ".alert_closeBtn", "enable", "disable");

	$('#countryAlert .resultSet_ctn .result_option').on('click', function(){
		$('#country').val($(this).data('value'));
		$('#countrySelect .input_select span').text($(this).data('value') + ' - ' + $(this).data('name'))
		template.closeElement(".main_alerts", "enable", "disable");
		template.changeSelect('#countrySelect .input_select', true);
	})
})