function callClientWS(content) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: 'http://localhost:64041/getFingerPrint',
        data: content,
        beforeSend: function () {
            sendMessage("Info", "Posicione o dedo no leitor biometrico!", "info");
        },
        success: function (data) {
            var employee = data['employee'];
            var person = data['person'];
            var fingerPosition = data['fingerPosition'];
            var fingerTemplate = data['fingerTemplate'];
            var fingerWSQ = data['fingerWSQ'];
            setResponse(
                [
                    {
                        name: 'employee',
                        value: employee
                    },
                    {
                        name: 'person',
                        value: person
                    },
                    {
                        name: 'fingerPosition',
                        value: fingerPosition
                    },
                    {
                        name: 'fingerTemplate',
                        value: fingerTemplate
                    },
                    {
                        name: 'fingerWSQ',
                        value: fingerWSQ
                    }
                ]
            );
        },
        error: function () {
            console.log("Erro!")
            sendMessage("Erro", "Ocorreu um erro! verifique se o leitor está conectado ou se o módulo biométrico está em funcionamento!", "error");
        }
    });
}

function compareFinger(conteudo) {
    var sexo = $('#sexoid').val();
    var posicao = $('#posicaoid').val();
    $.ajax({
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: 'http://127.0.0.1:64041/identifyFingerPrint',
        data:conteudo,
        beforeSend: function () {
            sendMessage("Info", "Posicione o dedo no leitor biometrico!", "info");
        },
        success: function (data) {
            var pessoa_Id = data;
            setResponse(
                [
                    {
                        name: 'pessoa_Id',
                        value: pessoa_Id
                    }
                ]
            );
        },
        error: function () {
            console.log("Erro!")
            sendMessage("Erro", "Ocorreu um erro! verifique se o leitor está conectado ou se o módulo biométrico está em funcionamento!", "error");
        }
    });
}

function verifyFingerPrint(conteudo) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: 'http://localhost:64041/verifyFingerPrint',
        data: conteudo,
        beforeSend: function () {
            sendMessage("Info", "Posicione o dedo no leitor biometrico!", "info");
        },
        success: function (data) {
            setResponse(
                [
                    {
                        name: 'resultado',
                        value: data
                    }
                ]
            );
        },
        error: function () {
            sendMessage("Erro", "Ocorreu um erro! verifique se o leitor está conectado ou se o módulo biométrico está em funcionamento!", "error");
        }
    });
}

function getSimpleFingerPrint() {
    $.ajax({
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        url: 'http://localhost:64041/getSimpleFingerPrint',
        beforeSend: function () {
            sendMessage("Info", "Posicione o dedo no leitor biometrico!", "info");
        },
        success: function (data) {
            var fingerTemplate = data['fingerTemplate'];
            setResponse(
                [
                    {
                        name: 'fingerTemplate',
                        value: fingerTemplate
                    }
                ]
            );
        },
        error: function () {
            console.log("Erro!");
            sendMessage("Erro", "Ocorreu um erro! verifique se o leitor está conectado ou se o módulo biométrico está em funcionamento!", "error");
        }
    });
}

function sendMessage(summary, detail, severity) {
    return PF('messages').renderMessage({
        "summary": summary,
        "detail": detail,
        "severity": severity
    });
}
