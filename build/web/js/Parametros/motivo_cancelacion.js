/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
$(document).ready(function () {

});

function ok_crear() {
    var acepted = true;
    
    var nombre = $("#ctc_nombre").val() || null;
    
    if (nombre == null) {
        $("#ctc_nombre").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_nombre").css("background-color", "#ffffff");
    }

    if (acepted) {

        $.post(url, {evento: "agg_motivo_cancelacion",
            nombre: nombre
        }, function (resp) {
            if (resp == "falso" || resp.length <= 0) {
                alert("Ocurrio un herror al registrar tipo carrera");
            } else {
                alert("Tipo carrera registrado con exito");
                window.location.href = "index.html";
            }
        });
    }

}