/**
 * Created by Gochan on 15.10.2016.
 */

function get_question(){
    $.ajax({
        url: '/get_question',
        data: {num: $('#question').attr('num')},
        dataType: 'json',
        success: function (data) {
            $('#question').html(data.text).attr('num', data.num);
        }
    });
}