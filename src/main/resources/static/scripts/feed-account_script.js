$(() => {
    $('.bankCardLayer').on('click', function(){
        $('.bankCardLayer').removeClass('selected');
        $(this).addClass('selected');
        $('#cardid').attr("value", $(this).attr('data-value'));
    })
})