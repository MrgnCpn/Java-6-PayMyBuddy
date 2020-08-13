$(() => {
	template.changeRadio('.currencyRadio', 'selected', 'div.input_ctn #currency');
	template.openElement(".main_alerts", ".input_select", ".alert_closeBtn", "enable", "disable");

	var countryAjax = (url) => {
		$.get('https://restcountries.eu/rest/v2/' + url + '?fields=name;alpha3Code;', function (data) {
			$.each(data, function (index, element) {
				var result_option =
					'<div class="result_option" data-value="' + element.alpha3Code + '" data-name="' + element.name + '">' +
					'	<span class="option_icon">' +
					'		<h2>' + element.alpha3Code + '</h2>' +
					'	</span>' +
					'	<div class="option_info">' +
					'		<h2>' + element.name + '</h2>' +
					'	</div>' +
					'	<span class="option_btn">' +
					'		<svg class="feather f_black">' +
					'			<use href="/assets/icons/feather-sprite.svg#check"/>' +
					'		</svg>' +
					'	</span>' +
					'</div>'

				$('#countryAlert .resultSet_ctn').append(result_option);
			});
		})
	}

	//countryAjax('all');

	$('#countryAlert #alert_search_field').on('input', function(e){
		$('#countryAlert .resultSet_ctn').html('');
		if ($(this).val() !== ''){
			countryAjax('name/' + $(this).val());
		}
	})

	$('#countryAlert .resultSet_ctn .result_option').on('click', function(){
		$('#country').val($(this).data('value'));
		$('#countrySelect .input_select span').text($(this).data('value') + ' - ' + $(this).data('name'))
		template.closeElement(".main_alerts", "enable", "disable");
		template.changeSelect('#countrySelect .input_select', true);
	})
})