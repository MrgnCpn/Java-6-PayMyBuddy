$(() => {
	template.changeRadio('.currencyRadio', 'selected', 'div.input_ctn #currency');
	template.openElement(".main_alerts", ".input_select", ".alert_closeBtn", "enable", "disable");

	var countryAjax = (url) => {
		$.get('https://restcountries.eu/rest/v2/' + url + '?fields=name;alpha3Code;', function (data) {
			$.each(data, function (index, element) {
				var result_option =
					'<div class="result_option" th:data-value="' + element.alpha3Code + '">' +
					'	<span class="option_icon">' +
					'		<h2>' + element.alpha3Code + '</h2>' +
					'	</span>' +
					'	<div class="option_info">' +
					'		<h2>' + element.name + '</h2>' +
					'	</div>' +
					'	<span class="option_btn">' +
					'		<svg class="feather f_black">' +
					'			<use th:href="@{/assets/icons/feather-sprite.svg#check}"/>' +
					'		</svg>' +
					'	</span>' +
					'</div>'

				$('#countryAlert .resultSet_ctn').append(result_option);
			});
		})
	}

	countryAjax('all');

	$('#countryAlert #alert_search_field').on('input', function(e){
		$('#countryAlert .resultSet_ctn').html('');
		if ($(this).val() !== ''){
			countryAjax('name/' + $(this).val());
			console.log($(this).val())
		}
	})

	$('#countryAlert #alert_search_field').on('click');

})