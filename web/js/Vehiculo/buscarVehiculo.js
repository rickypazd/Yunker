/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
$(document).ready(function () {

    buscar_conductor();
});

function buscar_conductor() {
    var busq = $("#in_buscar").val() || "";

    $.post(url, {evento: "buscar_vehiculo", busqueda: busq}, function (resp) {
        var json = JSON.parse(resp);
        var html = "";
        $.each(json, function (i, obj) {
            html += "<a href='javaScript:void(0);' onclick='ver_conductor(" + obj.id_vehiculo + ")' class='list-group-item list-group-item-action flex-column align-items-start'>";
            html += "<div class='d-flex w-100 justify-content-between'>";
            html += "<h5 class='mb-1'>Placa: " + obj.placa + "</h5>";
            if (obj.estado == '0') {
                html += "<small>HABILITADO</small>";
            } else if (obj.estado == '1') {
                html += "<small>DESABILITADO</small>";
            }

            html += "</div>";

            html += "<p class='mb-1'><b>Marca:</b> <span >" + obj.marca + "</span>&nbsp; &nbsp;<b>Tipo:</b> <span >" + obj.modelo + "</span></p>";
            html += "<small><b>Modelo:</b> "+obj.ano+"&nbsp; &nbsp;<b>Color:</b> "+obj.color+"</small>";
            html += "</a>";
        });
        $("#list_conductores").html(html);
    });
}



function ver_conductor(id) {
    window.location.href = "VehiculoPerf.html?id=" + id;
}