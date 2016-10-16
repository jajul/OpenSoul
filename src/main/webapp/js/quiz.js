/**
 * Created by Gochan on 16.10.2016.
 */

var quiz_threshold = 0.7;
var quiz_threshold_percent = (quiz_threshold*100)+'%';

// data: {
//      type: 'quiz',
//      num: 7,
//      text: 'What is love?',
//      options: [{text: 'Baby'},
//                {text: 'Dont hurt me'},
//                {text: 'No more'}
//               ]
// }
function printQuiz(data){
    var html = '<form>'
    data.options.forEach(function(item){
        html += '<div class="radio">\
                    <label> \
                        <input type="radio" name="answer" onclick="showNext()" value="'+item.num+'" /> '+item.text+' \
                    </label>\
                 </div>';
    });
    html += '</form>';

    $('#quiz_container').show().html(html);
}

function printQuizResult(){
    $.ajax({
        url: '/get_quiz_result',
        data: {login: login_name},
        success: function(data){
            var percent = (data.result*100)+'%';
            var html = '<div class="your-result">Your result is '+percent+'</div>';
            if(data.result < quiz_threshold){
                html += '<div class="your-result">Unfortunatly, your result is less than minimum threshold ('+quiz_threshold_percent+')</div>';
                $('#nextButton').hide();
            }
            else{
                html += '<div class="your-result">Now you are ready to start quest with video answers!</div>';
                $('#nextButton').show();
            }
            $('#question_text').html(html);
        },
        error: function (xhr, error) {
            console.error(xhr);
        }
    });
}

function showNext() {
    $('#nextButton').toggle(true);
}