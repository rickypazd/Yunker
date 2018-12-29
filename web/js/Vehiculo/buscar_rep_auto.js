/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "repuestosController";
$(document).ready(function () {
    cargar_marcas();
});

function cargar_marcas() {
    mostrar_progress();
    $.post(url, {
        evento: "getAll_rep_auto_marca_registrados",
        TokenAcceso: "servi12sis3"
    }, function (resp) {
        cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != 1) {
                //error
                alert(obj.mensaje);
            } else {
                //exito
                //alert(resp);
                $("#lista_marca").html("");
                cargar_lista_marca($.parseJSON(obj.resp));

            }
        }
    });
}
function cargar_lista_marca(arr) {
    $.each(arr, function (i, obj) {
        cargar_iten_marca(obj);
    });
}
function cargar_iten_marca(obj) {
    var html = "<li onclick='seleccionar_marca(" + JSON.stringify(obj) + ")'>";
    html += "   <img src='" + obj.url_foto + "' height='50' width='80' alt=''/>";
    html += "   <span>" + obj.nombre + "</span>";
    html += "</li>";
    $("#lista_marca").append(html);
}

function seleccionar_marca(obj) {
    //$("#id_rep_auto_marca").val(obj.id);
    $("#rep_auto_marca").val(obj.nombre);
    $("#rep_auto_marca").data("id", obj.id);
    $("#img_marca").attr("src", obj.url_foto);
    $("#rep_auto_modelo").val("");
    $("#rep_auto_modelo").data("id", 0);
    $("#lista_anhos").html("");
    $("#img_modelo").attr("src", "img/Sin_imagen.png");

    $("#rep_auto_anho").val("");
    $("#rep_auto_anho").data("id", 0);
    $(".bd-marca").modal("toggle");
    cargar_modelo(obj.id);
}

function cargar_modelo(id) {
    mostrar_progress();
    $.post(url, {
        evento: "get_rep_auto_modelo_by_id_rep_auto_marca_registrados",
        TokenAcceso: "servi12sis3",
        id_rep_auto_marca: id

    }, function (resp) {
        cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != 1) {
                //error
                alert(obj.mensaje);
            } else {
                //exito
                //alert(resp);
                $("#lista_modelo").html("");
                cargar_lista_modelo($.parseJSON(obj.resp));

            }
        }
    });
}
function cargar_lista_modelo(arr) {
    $.each(arr, function (i, obj) {
        cargar_iten_modelo(obj);
    });
}
function cargar_iten_modelo(obj) {
    var html = "<li onclick='seleccionar_modelo(" + JSON.stringify(obj) + ")'>";
    html += "   <img src='" + obj.url_foto + "' height='50' width='80' alt=''/>";
    html += "   <span>" + obj.nombre + "</span>";
    html += "</li>";
    $("#lista_modelo").append(html);
}

function seleccionar_modelo(obj) {

    $("#rep_auto_modelo").val(obj.nombre);
    $("#rep_auto_modelo").data("id", obj.id);
    $("#img_modelo").attr("src", obj.url_foto);
    $("#rep_auto_anho").val("");
    $("#rep_auto_anho").data("id", 0);
    $("#lista_anhos").html("");
    $(".bd-modelo").modal("toggle");

    cargar_anhos($("#rep_auto_marca").data("id"), $("#rep_auto_modelo").data("id"));

}

function cargar_anhos(id_marca, id_modelo) {
    mostrar_progress();
    $.post(url, {
        evento: "getBy_id_marca_and_id_modelo",
        TokenAcceso: "servi12sis3",
        id_marca: id_marca,
        id_modelo: id_modelo

    }, function (resp) {
        cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != 1) {
                //error
                alert(obj.mensaje);
            } else {
                //exito
                //alert(resp);
                $("#lista_anhos").html("");
                cargar_lista_anhos($.parseJSON(obj.resp));

            }
        }
    });
}
function cargar_lista_anhos(arr) {
    $.each(arr, function (i, obj) {
        cargar_iten_anhos(obj);
    });
}
function cargar_iten_anhos(obj) {
    var html = "<li onclick='seleccionar_anho(" + JSON.stringify(obj) + ")'>";
    html += "   <span>" + obj.anho + "</span>";
    html += "</li>";
    $("#lista_anhos").append(html);
}

function seleccionar_anho(obj) {

    $("#rep_auto_anho").val(obj.anho);
    $("#rep_auto_anho").data("id", obj.id);
    $(".bd-anhos").modal("toggle");


}

