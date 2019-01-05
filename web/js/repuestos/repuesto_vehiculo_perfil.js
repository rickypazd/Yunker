/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "repuestosController";
$(document).ready(function () {
    id_usr = getQueryVariable("id");
    Cargar_repuesto();
});

function Cargar_repuesto() {
    mostrar_progress();
    $.post(url, {
        evento: "getById_repuesto",
        TokenAcceso: "servi12sis3",
        id: id_usr
    }, function (resp) {
        cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != 1) {
                //error
                alert(obj.mensaje);
            } else {
                var obje = $.parseJSON(obj.resp);
                     $("#foto_perfil").attr("src",obje.url_foto);
                $("#text_nombre").html(obje.nombre);
                $("#text_precio").html(obje.precio);
                $("#text_serie").html(obje.serie);
                $("#text_segundo_nombre").html(obje.otros_nombres);
                $("#text_descripcion").html(obje.descripcion);
                $("#text_fabricante").html(obje.fabricante);
           
            }
        }
    });
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