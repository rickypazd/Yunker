/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
$(document).ready(function () {
    $.post(url, {evento: "get_costos_extras"}, function (resp) {
        var json = $.parseJSON(resp);
        var html = "";
        $.each(json, function (i, obj) {
            html += "<li class='li_roles' onclick='ver_permisos(" + obj.id + ",this);'><b>Nombre:</b> " + obj.nombre + " &nbsp; <b>Costo:</b> "+obj.costo+"</li>";
        });
        if (json.length <= 0) {
            html += "<li class='li_roles' >NO HAY COSTOS EXTRAS REGISTRADOS</li>";
        }
        $("#lista_costos").html(html);
    });
});

function agregar_costo() {
    var acepted = true;

    var nombre = $("#nombre").val() || null;
    var costo = $("#costo").val() || null;

    if (nombre == null) {
        $("#nombre").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#nombre").css("background-color", "#ffffff");
    }
    if (costo == null) {
        $("#costo").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#costo").css("background-color", "#ffffff");
    }
    if (acepted) {
        mostrar_progress();
        $.post(url, {evento: "agg_costo_extra",
            nombre: nombre,
            costo:costo
        }, function (resp) {
            cerrar_progress();
            if (resp == "falso" || resp.length <= 0) {
                alert("Ocurrio un herror al registrar costo");
            } else {
                alert("Exito");
                window.location.href = "CostosExtras.html";
            }
        });
    }

}

function ver_agg_permiso() {
    $("#exampleModalCenter").modal("show");
}