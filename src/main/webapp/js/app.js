var params = getHashParams(),
    mode = typeof(params.mode) == 'undefined' ? 'webrtc' : params.mode,
    username,
    login_name,
    password,
    application_name = typeof(params.appname) == 'undefined' ? 'videochat' : params.appname,
    account_name = params.accname,
    dialog,
    showLog = true,
    currentCall = null,
    outboundCall = null;

function getHashParams() {
    var hashParams = {};
    var e,
        a = /\+/g,  // Regex for replacing addition symbol with a space
        r = /([^&;=]+)=?([^&;]*)/g,
        d = function (s) {
            return decodeURIComponent(s.replace(a, " "));
        },
        q = window.location.hash.substring(1);

    while (e = r.exec(q))
        hashParams[d(e[1])] = d(e[2]);

    return hashParams;
}

function log(str) {
    document.getElementById("log").innerHTML += str + "<br/>";
}

// create VoxImplant instance
var voxAPI = VoxImplant.getInstance();
// assign handlers
voxAPI.addEventListener(VoxImplant.Events.SDKReady, onSdkReady);
voxAPI.addEventListener(VoxImplant.Events.ConnectionEstablished, onConnectionEstablished);
voxAPI.addEventListener(VoxImplant.Events.ConnectionFailed, onConnectionFailed);
voxAPI.addEventListener(VoxImplant.Events.ConnectionClosed, onConnectionClosed);
voxAPI.addEventListener(VoxImplant.Events.AuthResult, onAuthResult);
voxAPI.addEventListener(VoxImplant.Events.MicAccessResult, onMicAccessResult);
voxAPI.addEventListener(VoxImplant.Events.SourcesInfoUpdated, onSourcesInfoUpdated);

// initialize SDK
try {
    voxAPI.init({
        useRTCOnly: true, // используем WebRTC принудительно
        //useFlashOnly: mode=='flash'?true:false, // force Flash mode
        micRequired: true, // force microphone/camera access request
        videoSupport: true, // enable video support
        progressTone: true, // play progress tone
        swfContainer: 'voximplant_container' // Flash movie will be loaded in the specified container
    });
} catch (e) {
    if (e.message == "OLD_FLASH_VERSION") alert("Please update your Flash Player to version 11.3 or higher");
}

// SDK ready - functions can be called now
function onSdkReady() {
    log("onSDKReady version " + VoxImplant.version);
    log("WebRTC supported: " + voxAPI.isRTCsupported());
    //connect();
    voxAPI.connect();
}

// Connection with VoxImplant established
function onConnectionEstablished() {
    log("Connection established: " + voxAPI.connected());

    // show authorization form
    var $authForm = $('<div id="authForm">' +
        '<form class="form-horizontal" role="form">' +
        '<div class="form-group">' +
        '<label for="inputUsername" class="col-sm-2 control-label">Username</label>' +
        '<div class="col-sm-10">' +
        '<input type="text" class="form-control" id="inputUsername" placeholder="Your name">' +
        '</div>' +
        '</div>' +
        '<div class="form-group">' +
        '<label for="inputLogin_name" class="col-sm-2 control-label">Login</label>' +
        '<div class="col-sm-10">' +
        '<input type="text" class="form-control" id="inputLogin_name" placeholder="Login">' +
        '</div>' +
        '</div>' +
        '<input type="submit" value="submit" class="hidden" />' +
        '</form>' +
        '</div>');

    if (typeof username == 'undefined' || typeof password == 'undefined') {
        dialog = new BootstrapDialog({
            title: 'Authorization',
            message: $authForm,
            buttons: [{
                label: 'Sign in',
                action: function (dialog) {
                    $('#authForm form').submit();
                }
            }],
            closable: false,
            onshown: function (dialog) {
                $('#inputUsername').focus();
                $('#authForm form').on('submit', function (e) {
                    username = $('#inputUsername').val();
                    login_name = $('#inputLogin_name').val();
                    login();
                    e.preventDefault();
                });
            }
        });
        dialog.open();
    } else login();
}

// Login function
function login() {
    log(username + " is going to connect as " + login_name + " to " + application_name);
    voxAPI.login(login_name + "@" + "videorecord" + "." + "samaramaks" + ".voximplant.com", "voximplant9085com");
}

// Connection with VoxImplant failed
function onConnectionFailed() {
    log("Connection failed");
    setTimeout(function () {
        voxAPI.connect();
    }, 1000);
}

// Connection with VoxImplant closed
function onConnectionClosed() {
    log("Connection closed");
    setTimeout(function () {
        voxAPI.connect();
    }, 1000);
}

// Handle authorization result
function onAuthResult(e) {
    log("AuthResult: " + e.result);
    if (e.result) {
        // Authorized successfully
        dialog.close();
        var title = $('.panel-title').html() + '. Hi ' + username;
        $('.panel-title').html(title);
        $('#controls').slideDown();
        showLocalVideo(true);
        if (mode == 'flash') {
            // Camera settings
            voxAPI.setLocalVideoSize(320, 240);
            voxAPI.setVideoSettings({
                "profile": "baseline",
                "level": "2.1",
                "width": "320",
                "height": "240",
                "fps": "25",
                "bandwidth": "65536",
                "quality": "75",
                "keyframeInterval": "40"
            });
        } else {
            // Move local video from camera to container
            $('#voximplantlocalvideo').appendTo('#voximplant_container');
            $('#voximplantlocalvideo')[0].play();
        }
    } else {
        // Wrong username or password
        if (!$('div.alert.alert-danger').length) $('#authForm').prepend('<div class="alert alert-danger" role="alert">Wrong login was specified</div>');
        log("Code: " + e.code);
    }
}

// Call connected - переопределения функций кнопок;  установка изображения с камеры в окно браузера
function onCallConnected(e) {
    log("CallConnected: "+currentCall.id());
    if ($('#cancelButton').length) {
        $('#cancelButton').html('Disconnect');
    } else {
        $('#callButton').replaceWith('<button type="button" class="btn btn-danger" id="cancelButton">Disconnect</button>');
        $('#cancelButton').click(function() {
            currentCall.hangup();
        });
    }
    if (mode == 'flash') {
        setTimeout(function() {
            sendVideo(true);
            showRemoteVideo(true);
            // For Flash WebSDK function call is required
            currentCall.setRemoteVideoSize(320, 240);
            currentCall.setRemoteVideoPosition(330, 0);
        }, 1000);
    } else {
        //обращение к API
        sendVideo(true);
        showRemoteVideo(true);

        // For WebRTC just using JS/CSS for transformation
        $video = $(document.getElementById(currentCall.getVideoElementId()));
        $video.appendTo('#voximplant_container');
        $video.css('margin-left', '10px').css('width', '320px').css('height', '240px').css('float', 'left');
        $video[0].play();
    }
}

// Call disconnected
function onCallDisconnected(e) {
    log("CallDisconnected: " + currentCall.id() + " Call state: " + currentCall.state());
    currentCall = null;
    $('#cancelButton').replaceWith('<button type="button" class="btn btn-success" id="callButton">Call</button>');
    $('#cancelButton').remove();
    $('#callButton').click(function () {
        createCall();
    });
}

// Call failed
function onCallFailed(e) {
    log("CallFailed: " + currentCall.id() + " code: " + e.code + " reason: " + e.reason);
    $('#cancelButton').replaceWith('<button type="button" class="btn btn-success" id="callButton">Call</button>');
    $('#cancelButton').remove();
    $('#callButton').click(function () {
        createCall();
    });
}

// Audio & video sources info available
function onSourcesInfoUpdated() {
    var audioSources = voxAPI.audioSources(),
        videoSources = voxAPI.videoSources();
}

// Camera/mic access result
function onMicAccessResult(e) {
    log("Mic/Cam access allowed: " + e.result);
    if (e.result) {
        // Access was allowed
        if (mode == 'webrtc') dialog.close();
    } else {
        // Access was denied
        $('div.bootstrap-dialog').addClass('type-danger');
        dialog.setMessage('You have to allow access to your microphone to use the service');
    }
}


// Progress tone play start
function onProgressToneStart(e) {
    log("ProgessToneStart for call id: " + currentCall.id());
}

// Progres tone play stop
function onProgressToneStop(e) {
    log("ProgessToneStop for call id: " + currentCall.id());
}

// Create outbound call - создаем исходящий
function createCall() {
    $('#callButton').replaceWith('<button type="button" class="btn btn-danger" id="cancelButton">Cancel</button>');
    $('#callButton').remove();
    $('#cancelButton').click(function () {
        currentCall.hangup();
    });
    log("Calling to " + document.getElementById('phonenum').value);

    //outboundCall = currentCall = voxAPI.call(document.getElementById('phonenum').value, true, "TEST CUSTOM DATA", {"X-DirectCall": "true"}); //делаем звонок
    //mm:
    outboundCall = currentCall = voxAPI.call("videorec", true);

    currentCall.addEventListener(VoxImplant.CallEvents.Connected, onCallConnected);
    currentCall.addEventListener(VoxImplant.CallEvents.Disconnected, onCallDisconnected);
    currentCall.addEventListener(VoxImplant.CallEvents.Failed, onCallFailed);
    //mm: слушатель на прием сообщений
    currentCall.addEventListener(VoxImplant.CallEvents.MessageReceived, onMessageReceived);
}

// Disconnect current call
function disconnectCall() {
    if (currentCall != null) {
        log("Disconnect");
        currentCall.hangup();
    }
}

//mm: обработчик приема сообщений
function onMessageReceived(e) {
    console.error(e);
    videoURL = e.text; //URL видео
    log(videoURL);
}

// Close connection with VoxImplant      
function closeConnection() {
    voxAPI.disconnect();
}

// Establish connection with VoxImplant
function connect() {
    log("Establishing connection...");
    voxAPI.connect();
    if (mode == 'webrtc' && voxAPI.isRTCsupported()) {
        dialog = new BootstrapDialog({
            title: 'Camera/Microphone access',
            message: 'Please click Allow to allow access to your camera and microphone',
            closable: false
        });
        dialog.open();
    }
}

// Show/hide local video
function showLocalVideo(flag) {
    voxAPI.showLocalVideo(flag);
}

// Show/hide remote video
function showRemoteVideo(flag) {
    currentCall.showRemoteVideo(flag);
}

// Start/stop sending video
function sendVideo(flag) {
    voxAPI.sendVideo(flag);
}

// Enable fullscreen
function fullScreenmode(flag) {
    if (mode == 'webrtc') {
        if (flag === true && currentCall != null) {
            var elem = document.getElementById(currentCall.getVideoElementId());
            if (elem.requestFullscreen) {
                elem.requestFullscreen();
            } else if (elem.msRequestFullscreen) {
                elem.msRequestFullscreen();
            } else if (elem.mozRequestFullScreen) {
                elem.mozRequestFullScreen();
            } else if (elem.webkitRequestFullscreen) {
                elem.webkitRequestFullscreen();
            }
        }
    } else {
        // enable FullScreen in Flash  mode
    }
}