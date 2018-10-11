/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";

var id_conductor;
$(document).ready(function () {

    get_vehiculos();
});
var arr_doc;

function get_vehiculos() {
    var id = getQueryVariable("idCn");
    id_conductor = id;
    mostrar_progress();
    $.post(url, {evento: "get_vehiculos_asignados_cond", id: id}, function (resp) {
        cerrar_progress();
        if (resp == "falso") {
            alert("Error al cargar datos");

        } else {
            var json = JSON.parse(resp);
            var html = "";
            $.each(json, function (i, obj) {
                html += "<a href='javaScript:void(0);' onclick='eliminar_vehiculo(" + obj.id_vehiculo + ",this);' class='list-group-item list-group-item-action flex-column align-items-start'>";
                html += "<div class='d-flex w-100 justify-content-between'>";
                html += "<h5 class='mb-1'>Placa: " + obj.placa + "</h5>";
                if (obj.estado == '0') {
                    html += "<small>HABILITADO</small>";
                } else if (obj.estado == '1') {
                    html += "<small>DESABILITADO</small>";
                }

                html += "</div>";

                html += "<p class='mb-1'><b>Marca:</b> <span >" + obj.marca + "</span>&nbsp; &nbsp;<b>Tipo:</b> <span >" + obj.modelo + "</span></p>";
                html += "<small><b>Modelo:</b> " + obj.ano + "&nbsp; &nbsp;<b>Color:</b> " + obj.color + "</small>";
                html += "</a>";
            });
            $("#lista_asignados").html(html);
            arr_doc=json;
            get_sin_asignar(id);
        }
    });
}

function get_sin_asignar() {
    var busq = $("#in_buscar").val() || "";
    var arr=arr_doc;
    mostrar_progress();
    $.post(url, {evento: "get_vehiculos_sin_asignar_cond", id: id_conductor, busq:busq}, function (resp) {
        cerrar_progress();
        if (resp == "falso") {
            alert("Error al cargar datos");

        } else {
            var json = $.parseJSON(resp);

            var html = "";
            $.each(json, function (i, obj) {
                var si = false;
                $.each(arr, function (i, objec) {
                    if (objec.id_vehiculo == obj.id_vehiculo) {
                        si = true;
                        $.break;
                    }
                });
                if (!si) {
                    html += "<a href='javaScript:void(0);' onclick='asignar_vehiculo(" + obj.id_vehiculo + ",this);' class='list-group-item list-group-item-action flex-column align-items-start'>";
                    html += "<div class='d-flex w-100 justify-content-between'>";
                    html += "<h5 class='mb-1'>Placa: " + obj.placa + "</h5>";
                    if (obj.estado == '0') {
                        html += "<small>HABILITADO</small>";
                    } else if (obj.estado == '1') {
                        html += "<small>DESABILITADO</small>";
                    }

                    html += "</div>";

                    html += "<p class='mb-1'><b>Marca:</b> <span >" + obj.marca + "</span>&nbsp; &nbsp;<b>Tipo:</b> <span >" + obj.modelo + "</span></p>";
                    html += "<small><b>Modelo:</b> " + obj.ano + "&nbsp; &nbsp;<b>Color:</b> " + obj.color + "</small>";
                    html += "</a>";

                }

            });

            $("#list_sin_asignar").html(html);
        }
    });
}

function asignar_vehiculo(id, obj) {
       var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
    mostrar_progress();
    $.post(url, {evento: "asignar_vehiculo", id_conductor: id_conductor, id_vehiculo: id,id_admin:usr_log.id}, function (resp) {
        cerrar_progress();
        if (resp == 'exito') {
            $(obj).remove();
            location.reload();
            $(obj).attr('onclick', 'eliminar_vehiculo(' + id + ',this)');
            $("#lista_asignados").append(obj);
        }
    });
}
function eliminar_vehiculo(id, obj) {
       var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
    mostrar_progress();
    $.post(url, {evento: "eliminar_vehiculo", id_conductor: id_conductor, id_vehiculo: id, id_admin:usr_log.id}, function (resp) {
        cerrar_progress();
        if (resp == 'exito') {
            $(obj).remove();
            $(obj).attr('onclick', 'asignar_vehiculo(' + id + ',this)');
            location.reload();
            $("#list_sin_asignar").append(obj);
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

