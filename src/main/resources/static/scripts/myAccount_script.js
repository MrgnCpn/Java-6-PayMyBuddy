$(() => {
	template.changeTab('.subSection_ctn .subSection',
					   '.accountInfo_details_header .section_btn',
					   'active',
					   'active'
	);

	$('#accountInfo_details').css('height', $('.main_content').outerHeight() - $('#accountInfo').outerHeight(true) + 'px');
})