$(() => {
	template.enabledForm('#usrProfileForm', '#input_btn_edit');
	template.enabledForm('#usrProfileForm', '#input_btn_cancel');

	$('.input_select').on('click', function(){
		if ($('#usrProfileForm').hasClass('disabled') == false) $('.main_alerts').addClass('enable').removeClass('disable');
	});

	$('.alert_closeBtn').on('click', function(){
		$('.main_alerts').removeClass('enable').addClass('disable');
	});

	const initClick = () => {
		$('#countryAlert .resultSet_ctn .result_option').on('click', function(){
			$('#country').val($(this).data('value'));
			$('#countrySelect .input_select span').text($(this).data('value') + ' - ' + $(this).data('name'))
			template.closeElement(".main_alerts", "enable", "disable");
			template.changeSelect('#countrySelect .input_select', true);
		})
	}

	initClick();

	const countryAjax = (url) => {
		$.get('https://restcountries.eu/rest/v2/' + url + '?fields=name;alpha3Code;', function (data) {
			$.each(data, function (index, element) {
				let result_option =
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
			initClick();
		})
	}

	$('#countryAlert #alert_search_field').on('input', function(e){
		$('#countryAlert .resultSet_ctn').html('');
		if ($(this).val() !== ''){
			countryAjax('name/' + $(this).val());
		}
	})
})