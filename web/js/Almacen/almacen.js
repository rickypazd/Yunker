/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
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

function registrar_almacen() {
    mostrar_progress();
    var exito = true;
    var nombre = $("#text_nombre").val() || null;
    var direccion = $("#text_direccion").val() || null;
    var descripcion = $("#text_descripcion").val() || null;
    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
    if (nombre != null && nombre.length > 0) {
        $("#text_nombre").css("background", "#ffffff");
    } else {
        $("#text_nombre").css("background", "#df5b5b");
        exito = false;
    }
    if (direccion != null && direccion.length > 0) {
        $("#text_direccion").css("background", "#ffffff");
    } else {
        $("#text_direccion").css("background", "#df5b5b");
        exito = false;
    }
    if (descripcion != null && descripcion.length > 0) {
        $("#text_descripcion").css("background", "#ffffff");
    } else {
        $("#text_descripcion").css("background", "#df5b5b");
        exito = false;
    }
    if (exito) {
        mostrar_progress();
        $.post(url,
                {
                    evento: "registrar_almacen",
                    TokenAcceso: TokenAcceso,
                    id_usr: usr_log.id,
                    nombre: nombre,
                    descripcion: descripcion,
                    direccion: direccion                    
                }, function (respuesta) {
            cerrar_progress();
            if (respuesta != null) {
                var obj = $.parseJSON(respuesta);
                if (obj.estado != 1) {
                    alert(obj.mensaje);
                } else {
                    var resp = obj.resp;
                    alert(resp);
                }
            }
        });
    } else {
        cerrar_progress();
    }
}
