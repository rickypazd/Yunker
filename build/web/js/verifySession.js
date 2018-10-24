/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    if (!sessionStorage.getItem("usr_log")) {
        window.location.href = "Login.html";
    } else {
        var arr = $("body").find("[data-permiso]");
        var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
        var permisos = usr_log.permisos;
        var dataurl = $("body").data("permisourl");
        if (dataurl != null) {
            if (contiene(permisos, dataurl) === false) {
                alert("Usted no cuenta con permisos para ingresar a esta pagina, favor de contactarse con el administrador");
                window.location.href = "index.html";
            }

        }
        $.each(arr, function (i, obj) {
            var permisodata = $(obj).data("permiso");
            if (contiene(permisos, permisodata) === false) {
                $(obj).remove();
            }
        });
    }
});
function contiene(arr, val) {
    var bol = false;
    $.each(arr, function (i, obj) {
        if (val == obj.NOMBRE) {
            bol = true;
        }
    });
    return bol;
}
function cerrarSesion() {
    if (sessionStorage.getItem("usr_log")) {
        sessionStorage.removeItem("usr_log");
        window.location.href = "Login.html";
    }
}

function ver_pagina(pag){
    window.location.href=pag;
}