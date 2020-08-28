$(() => {
    template.openElement(".main_alerts", ".input_select", ".alert_closeBtn", "enable", "disable");
    template.changeRadio('.currencyRadio', 'selected', 'div.input_ctn #currency');

    $('#selectTransferAlert .result_option').on('click', function(){
        let id = $(this).data('id');
        let name = $(this).data('name');
        let email = $(this).data('email');
        $('#transfer_ctn .selected_option .option_icon').html('').append($(this).children('.option_icon').clone())
        $('#transfer_ctn .selected_option .option_info h2').text(name);
        $('#transfer_ctn .selected_option .option_info h3').text(email);
        $('#to').attr('value', id);
        template.closeElement(".main_alerts", "enable", "disable");
    });
})