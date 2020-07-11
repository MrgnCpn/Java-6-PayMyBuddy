class Template {
	toggleCheckbox(input_chk_field, input_chk_btn) {
		$(input_chk_btn).on('click', function(){
			$(input_chk_btn).toggleClass('on').toggleClass('off');

			$(input_chk_field).attr("value") === 'on' ? $(input_chk_field).attr("value", "off") : $(input_chk_field).attr("value", "on");

			console.log($(input_chk_field).attr("value"));
		});
	}
}

var template = new Template();