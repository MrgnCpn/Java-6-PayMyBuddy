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

	changeRadio(input_radio, slc_class, input_text) {
		$(input_radio).on('click', function(){
			$(input_radio).each(function(){
				$(this).removeClass(slc_class);
			})
			$(this).addClass(slc_class);
			$(input_text).val($(this).attr('data-value'));
		})
	}

	changeTab(section_class, btn_class, slc_section_class, slc_btn_class) {
		$(btn_class).on('click', function(){
			$(section_class).each(function(){
				$(this).removeClass(slc_section_class);
			})

			$(btn_class).each(function(){
				$(this).removeClass(slc_btn_class);
			})

			$(this).addClass(slc_btn_class);
			$('#' + $(this).attr('data-targetID')).addClass(slc_section_class);
		})
	}

	enabledForm(form_selector, btn_selector) {
		$(btn_selector).on('click', function(){

			if ($(form_selector).hasClass('disabled')) {
				$(form_selector + ' input').each(function(){
					$(this).attr('disabled', false)
				})

				$(form_selector + ' .input_select').each(function(){
					$(this).removeClass('disabled');
				})

				$(form_selector + ' .input_select span').each(function(){
					$(this).toggleClass('c_greyE1').toggleClass('c_black')
				})

				$(form_selector + ' .input_select feather').each(function(){
					$(this).toggleClass('f_greyE1').toggleClass('f_black')
				})

				$(form_selector).removeClass('disabled')
			} else {
				$(form_selector + ' input').each(function(){
					$(this).attr('disabled', true)
				})

				$(form_selector + ' .input_select').each(function(){
					$(this).addClass('disabled');
				})

				$(form_selector + ' .input_select span').each(function(){
					$(this).toggleClass('c_greyE1').toggleClass('c_black')
				})

				$(form_selector + ' .input_select feather').each(function(){
					$(this).toggleClass('f_greyE1').toggleClass('f_black')
				})

				$(form_selector).addClass('disabled')
			}

			$(form_selector + ' .input_ctn.btns').each(function(){
				$(this).removeClass('disable')
			})

			$(this).parent().addClass('disable')
		})
	}
}

const template = new Template();

$(() => {
	template.initTemplate();
})