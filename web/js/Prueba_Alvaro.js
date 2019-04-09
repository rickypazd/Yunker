var url = "repuestosController";
$(document).ready(function () {
});

function registrar_esquema() {
    mostrar_progress();
    var exito = true;
    var id = $("#id_repuesto_esquema").val() || null; //id
    var idRep = getQueryVariable("id"); //id respuesto
    var foto = $("#file-0d").val() || null; // foto

    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));


    if (foto != null && nombre.length > 0) {
        $("#file-0d").css("background", "#ffffff");
    } else {
        $(".file-caption").css("background", "#df5b5b");
        exito = false;
    }
    if (exito) {
        mostrar_progress();
        var formData = new FormData($("#submitform")[0]);
        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            contentType: false,
            cache: false,
            processData: false,
            success: function (data)
            {
                //despues de cargar
                cerrar_progress();
                alert(data);
            }
        });
    } else {
        cerrar_progress();
    }
}




function getQueryVariable(varia) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == varia) {
            return pair[1];
        }
    }
    return (false);
}

