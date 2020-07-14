class Template {
	initTemplate(){	
		var containers = $('.comp_input_ctn');

		$('.comp_input_ctn').each(function() {
			var idx = 0;
			this.childNodes.forEach((item, index) => {
				(index % 2 !== 0 && $(item).hasClass('input_ctn')) ? idx ++ : null;
			})

			this.childNodes.forEach((item, index) => {
				(index % 2 !== 0 && $(item).hasClass('input_ctn')) ? $(item).css('width', 'calc(' + (100 / idx) + '% - 10px)') : null;
			})
		})
	}

	toggleCheckbox(input_chk_field, input_chk_btn) {
		$(input_chk_btn).on('click', function(){
			$(input_chk_btn).toggleClass('on').toggleClass('off');
			$(input_chk_field).attr("value") === 'on' 
				? $(input_chk_field).attr("value", "off")
				: $(input_chk_field).attr("value", "on");
		});
	}

	changeRadio(input_radio, selectedClass, input_text) {
		$(input_radio).on('click', function(){
			$(input_radio).each(function(){
				$(this).removeClass(selectedClass);
			})
			$(this).addClass(selectedClass);
			$(input_text).val($(this).attr('data-value'));
		})
	}
}

const template = new Template();

$(() => {
	template.initTemplate();
})