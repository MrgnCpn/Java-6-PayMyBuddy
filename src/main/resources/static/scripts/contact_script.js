$(() => {
    $('#search_field').on('input', function(){
        $('#contact_ctn .resultSet_ctn').html('');
        if ($(this).val() !== '') {
            $.get('http://localhost:9090/contact/friends/search?search=' + $(this).val(), function (data) {
                data = JSON.parse(data);
                $.each(data, function (index, element) {
                    let result_option =
                        '<div class="result_option">' +
                        '    <a href="/contact/profile/' + element.id + '" class="option_icon">';

                    if(element.isProfilePicture !== null) {
                        result_option += '<img src="/assets/profilePicture/' + element.id + '_' + element.firstName + '_' + element.lastName + element.isProfilePicture + '" alt="profile_pct">';
                    } else {
                        result_option += '<img src="/assets/icons/user.svg" alt="profile_pct">';
                    }

                    result_option +=
                        '    </a>' +
                        '    <a th:href="/contact/profile/' + element.id + '" class="option_info">' +
                        '        <h2>' + element.fullname + '</h2>' +
                        '        <h3>' + element.email + '</h3>' +
                        '    </a>';


                    if(element.isFriend == true) {
                        result_option +=
                            '    <a href="/contact/friends/remove?id=' + element.id + '&from=/contact" class="option_btn">' +
                            '        <svg class="feather f_red">' +
                            '           <use href="/assets/icons/feather-sprite.svg#user-minus"/>';
                    } else {
                        result_option +=
                            '    <a href="/contact/friends/add?id=' + element.id + '&from=/contact" class="option_btn">' +
                            '        <svg class="feather f_green">' +
                            '           <use href="/assets/icons/feather-sprite.svg#user-plus"/>';
                    }

                    result_option +=
                        '        </svg>' +
                        '    </a>' +
                        '</div>'

                    $('#contact_ctn .resultSet_ctn').append(result_option);
                });
            })
        }
    })
})