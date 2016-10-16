/**
 * Created by Gochan on 16.10.2016.
 */
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


function showNext() {
    $('#nextButton').toggle(true);
}