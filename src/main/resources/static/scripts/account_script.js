$(() => {
	template.changeTab('.subSection_ctn .subSection',
					   '.accountInfo_details_header .section_btn',
					   'active',
					   'active'
	);

	$('#accountInfo_details').css('height', $('.main_content').outerHeight(true) - $('#accountInfo').outerHeight(true) - 100 + 'px');
})