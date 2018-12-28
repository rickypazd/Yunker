/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "repuestosController";
$(document).ready(function () {
//aki
//
//
//
//post({
//cojntenido del post;
//alert();
//});
//calc_dist();
//nnuevo comentario   
});

//function registrar_marca_vehiculo() {
//    mostrar_progress();
//    var exito = true;
//    var nombre = $("#text_marca_nombre").val() || null;        
//    var TokenAcceso = "servi12sis3";
//    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
//    if (nombre != null && nombre.length > 0) {
//        $("#text_nombre").css("background", "#ffffff");
//    } else {
//        $("#text_nombre").css("background", "#df5b5b");
//        exito = false;
//    }   
//    if (exito) {
//        mostrar_progress();
//        $.post(url,
//                {
//                    evento: "registrar_rep_auto_marca",
//                    TokenAcceso: TokenAcceso,
//                    id_usr: usr_log.id,
//                    nombre: nombre                   
//                }, function (respuesta) {
//            cerrar_progress();
//            if (respuesta != null) {
//                var obj = $.parseJSON(respuesta);
//                if (obj.estado != 1) {
//                    alert(obj.mensaje);
//                } else {
//                    var resp = obj.resp;
//                    alert(resp);
//                }
//            }
//        });
//    } else {
//        cerrar_progress();
//    }
//}
function registrar_marca_vehiculo() {
    mostrar_progress();
    var exito = true;
    var nombre = $("#text_marca_nombre").val() || null;
    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
    if (nombre != null && nombre.length > 0) {
        $("#text_nombre").css("background", "#ffffff");
    } else {
        $("#text_nombre").css("background", "#df5b5b");
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
