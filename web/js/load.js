var myApp;
myApp = myApp || (function () {
    var pleaseWaitDiv = $('\
        <div class="modal hide" id="pleaseWaitDialog" data-backdrop="static" data-keyboard="false">\n\
            <div class="modal-header">\n\
                <h1>Esperando Respuesta, Por favor esperar.</h1>\n\
            </div>\n\
            <div class="modal-body">\n\
                <div class="progress">\n\
                    <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width:100%">\n\
                        Loading...\n\
                    </div>\n\
                </div>\n\
            </div>\n\
        </div>');
    return {
        showPleaseWait: function() {
            pleaseWaitDiv.modal();
        },
        hidePleaseWait: function () {
            pleaseWaitDiv.modal('hide');
        }

    };
})();
function mostrar_progress(){
    myApp.showPleaseWait();
}
function cerrar_progress(){
    myApp.hidePleaseWait();
}