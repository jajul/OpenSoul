/**
 * Created by Gochan on 15.10.2016.
 */
var question = null;
var state = {
    type: 'quiz',
    flow: 'start' // load_question, question, finish_quiz, finish, load_sending, sent
};
var loading = '<img class="small-loading" src="img/loading.gif"/>';

$(document).ready(startRender);

function startRender(){
    render('start');
}

function render(flow){
    if(flow){
        state.flow = flow;
    }

    $('#quiz_container').toggle( state.type == 'quiz' && state.flow != 'start' );

    if(state.type == 'quiz' && state.flow != 'start'){
        if(state.flow == 'load_question'){
            toggleLoading($('#quiz_container'));
        }
        else {
            printQuiz(question);
        }
    }

    $('#voximplant_container').toggle( state.type == 'plain' );

    $('#question_num').toggle( state.flow=='question' || state.flow=='load_question' );
    if(state.flow=='load_question'){
        toggleLoading($('#question_num'));
    }
    else if(state.flow=='question'){
        $('#question_num').show().html('Question ' + question.num);
    }

    $('#question_text').toggle( state.flow!='start' );
    if(state.flow=='load_question'){
        toggleLoading($('#question_text'));
    }
    else if(state.flow=='question'){
        $('#question_text').show().html(question.text);
    }
    else if(state.flow=='finish'){
        $('#question_text').html('Thank you!');
    }
    $('#startButton').toggle( state.flow=='start' );
    $('#nextButton').toggle( state.flow=='question' || state.flow=='load_question' );
    $('#sendButton').toggle( state.flow=='finish' );
    $('#sendSuccess').toggle( state.flow=='sent' );
    $('#loadingButton').toggle( state.flow=='load_sending' );

    if(state.flow=='load_question' || state.flow=='load_sending' ){
        $('.btn').prop('disabled', true);
    }
    else{
        $('.btn').prop('disabled', false);
    }
}

function get_question() {
    render('load_question');
    $.ajax({
        url: '/get_question',
        data: {
            user: login_name,
            type: (question!=null ? question.type : 'quiz'), // вначале пытаемся дать квиз
            num: (question!=null ? question.num + 1 : 1)
        },
        dataType: 'json',
        success: function (data) {
            setQuestion(data);
        },
        error: function (xhr, error) {
            console.error(xhr);
        }
    });
}

function setQuestion(data) {
    state.type = data.type;
    question = {};
    question.type = data.type;
    question.num = data.num;
    question.text = data.text;
    question.options = data.options;

    printQuestion();
}

function printQuestion() {
    if (question == null) {
        // Начало теста - отображаем кнопку Старт
        render('start');
    }
    else if (question.num > 0) {
        if (question.num == 1) {
            createCall();
        }
        // Отображаем следующий вопрос
        render('question');
    }
    else if (question.num == -1) {
        // Конец теста
        disconnectCall();
        render('finish');
    }
}


function send_result() {
    render('load_sending');
    $.ajax({
        url: '/send_result',
        data: {
            user: username,
            email: useremail,
            URL: videoURL
        },
        dataType: 'text',
        success: function (data) {
            state.flow = 'sent';
            render();
        },
        error: function (xhr, error) {
            console.error(xhr);
        }
    });
}

function toggleLoading($elem){
    $elem.show().html($('<img class="small-loading" src="img/loading.gif"/>'));
}

