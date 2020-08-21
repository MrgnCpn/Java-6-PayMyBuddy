$(() => {
    $('.input_radio_ctn').each(function() {
        var idx = 0;
        this.childNodes.forEach((item, index) => {
            (index % 2 !== 0 && $(item).hasClass('radio_btn')) ? idx ++ : null;
        })

        this.childNodes.forEach((item, index) => {
            (index % 2 !== 0 && $(item).hasClass('radio_btn')) ? $(item).css('width', 'calc(' + (100 / idx) + '% - 10px)') : null;
        })
    })

    template.changeRadio('.typeCardRadio', 'selected', '#card_type');

    let card = [
        {
            layer : "bankCardLayer_nbr_1",
            input : "cardNumber_1"
        },
        {
            layer : "bankCardLayer_nbr_2",
            input : "cardNumber_2"
        },
        {
            layer : "bankCardLayer_nbr_3",
            input : "cardNumber_3"
        },
        {
            layer : "bankCardLayer_nbr_4",
            input : "cardNumber_4"
        },
        {
            layer : "bankCardLayer_lib",
            input : "account_wording"
        },
        {
            layer : "bankCardLayer_date",
            input : "date"
        },
    ].forEach(value => {
        $('#' + value.input).on('input', function(){
            $('#' + value.layer).html($(this).val())
        })
    })

    $('.typeCardRadio').on('click', function(){
        $('.bankCardLayer_img').addClass('disable');
        console.log($(this).attr('data-targetID'))
        $('.' + $(this).attr('data-value') + '_img').removeClass('disable');
    })
})