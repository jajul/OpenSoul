/**
 * Created by Gochan on 15.10.2016.
 */
var question = null;
var state = {
    flow: 'start' // next, finish, sent
};

$(document).ready(render);

function render(){
    $('#question_num').toggle( state.flow=='next' );
    if(state.flow=='next'){
        $('#question_num').show().html('Question ' + question.num);
        $('#question_text').show().html(question.text);
    }

    $('#question_text').toggle( state.flow!='start' );
    if(state.flow=='next'){
        $('#question_text').show().html(question.text);
    }
    else if(state.flow=='finish'){
        $('#question_text').html('Thank you!');
    }
    $('#startButton').toggle( state.flow=='start' );
    $('#nextButton').toggle( state.flow=='next' );
    $('#sendButton').toggle( state.flow=='finish' );
    $('#sendSuccess').toggle( state.flow=='sent' );
}

function get_question() {
    $.ajax({
        url: '/get_question',
        data: {num: (question!=null ? question.num + 1 : 1)},
        dataType: 'json',
        success: function (data) {
            setQuestion(data.num, data.text);
        }
    });
}

function setQuestion(num, text) {
    question = {};
    question.num = num;
    question.text = text;
    printQuestion();
}

function printQuestion() {
    if (question == null) {
        // Начало теста - отображаем кнопку Старт
        state.flow = 'start';
    }
    else if (question.num > 0) {
        if (question.num == 1) {
            createCall();
        }
        // Отображаем следующий вопрос
        state.flow = 'next';
    }
    else if (question.num == -1) {
        // Конец теста
        disconnectCall();
        state.flow = 'finish';
    }
    render();
}


function send_result() {
    $.ajax({
        url: '/send_result',
        data: {
            user: username,
            email: useremail,
            URL: videoURL
        },
        dataType: 'json',
        success: function (data) {
            state.flow = 'sent';
            render();
        }
    });
}
