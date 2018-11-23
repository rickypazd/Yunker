/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
var listaArituclos = null;
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

function registrar_articulo() {
    mostrar_progress();
    var exito = true;
    var nombre = $("#alm_nombre").val() || null;
    var descripcion = $("#alm_direccion").val() || null;
    var direccion = $("#alm_descripcion").val() || null;
    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
    if (nombre != null && nombre.length > 0) {
        $("#alm_nombre").css("background", "#ffffff");
    } else {
        $("#alm_nombre").css("background", "#df5b5b");
        exito = false;
    }
    if (direccion != null && direccion.length > 0) {
        $("#alm_direccion").css("background", "#ffffff");
    } else {
        $("#alm_direccion").css("background", "#df5b5b");
        exito = false;
    }
    if (descripcion != null && descripcion.length > 0) {
        $("#alm_descripcion").css("background", "#ffffff");
    } else {
        $("#alm_descripcion").css("background", "#df5b5b");
        exito = false;
    }
    if (exito) {
        $.post(url,
                {
                    evento: "registrar_almacen",
                    TokenAcceso: TokenAcceso,
                    id_usr: usr_log.id,
                    nombre: nombre,
                    direccion: direccion,
                    descripcion: descripcion
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
