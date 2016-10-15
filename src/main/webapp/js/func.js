/**
 * Created by Gochan on 15.10.2016.
 */
var question = {num: 0, text: ''};

$(document).ready(printQuestion);

function get_question(){
    $.ajax({
        url: '/get_question',
        data: {num: question.num+1},
        dataType: 'json',
        success: function (data) {
            setQuestion(data.num, data.text);
        }
    });
}

function setQuestion(num, text){
    question.num = num;
    question.text = text;
    printQuestion();
}

function printQuestion(){
    if(question.num > 0){
        $('#question_num').show().html('Question ' + question.num);
        $('#question_text').show().html(question.text);
        $('#btn_next_question').html('Next');
    }
    else if(question.num == 0){
        $('#question_num').hide();
        $('#question_text').hide();
    }
    else if(question.num == -1){
        $('#question_num').hide();
        $('#question_text').html('Thank you!');
        $('#btn_next_question').hide();
    }
}