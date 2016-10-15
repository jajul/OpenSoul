/**
 * Created by Gochan on 15.10.2016.
 */

function get_question(){
    if($('#question').attr('num')!=-1){
        $.ajax({
            url: '/get_question',
            data: {num: $('#question').attr('num')},
            dataType: 'json',
            success: function (data) {
                if(data.stop != 1) {
                    $('#question').html(data.text).attr('num', data.num);
                }
                else{
                    $('#question').attr('num', -1);
                }
            }
        });
    }
    else{
        $('#question').html('Вопросы закончены!');
    }
}