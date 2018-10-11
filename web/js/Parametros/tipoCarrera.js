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

    var id = $("#ctc_id").val() || null;
    var nombre = $("#ctc_nombre").val() || null;
    var desc = $("#ctc_desc").val() || null;
    var base = $("#ctc_tarifa_base").val() || null;
    var km = $("#ctc_tarifa_km").val() || null;
    var minuto = $("#ctc_tarifa_minuto").val() || null;
    var comision = $("#ctc_comision").val() || null;
    var cancelacion_basica = $("#ctc_tarifa_cancelacion_basica").val() || null;
    var cancelacion_minuto = $("#ctc_tarifa_cancelacion_minuto").val() || null;
    var minutos_para_cancelar = $("#ctc_minutos_para_cancelar").val() || null;
    var cancelacion_minuto_conductor = $("#ctc_tarifa_cancelacion_minuto_conductor").val() || null;
    var cancelacion_basica_conductor = $("#ctc_tarifa_cancelacion_basica_conductor").val() || null;
    var minutos_para_cancelar_conductor = $("#ctc_minutos_para_cancelar_conductor").val() || null;


    if (id == null) {
        $("#ctc_id").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_id").css("background-color", "#ffffff");
    }
    if (nombre == null) {
        $("#ctc_nombre").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_nombre").css("background-color", "#ffffff");
    }
    if (desc == null) {
        $("#ctc_desc").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_desc").css("background-color", "#ffffff");
    }
    if (base == null) {
        $("#ctc_tarifa_base").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_tarifa_base").css("background-color", "#ffffff");
    }
    if (km == null) {
        $("#ctc_tarifa_km").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_tarifa_km").css("background-color", "#ffffff");
    }
    if (minuto == null) {
        $("#ctc_tarifa_minuto").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_tarifa_minuto").css("background-color", "#ffffff");
    }
    if (comision == null) {
        $("#ctc_comision").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_comision").css("background-color", "#ffffff");
    }
    if (cancelacion_basica == null) {
        $("#ctc_tarifa_cancelacion_basica").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_tarifa_cancelacion_basica").css("background-color", "#ffffff");
    }
    if (cancelacion_minuto == null) {
        $("#ctc_tarifa_cancelacion_minuto").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_tarifa_cancelacion_minuto").css("background-color", "#ffffff");
    }
    if (minutos_para_cancelar == null) {
        $("#ctc_minutos_para_cancelar").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_minutos_para_cancelar").css("background-color", "#ffffff");
    }
    if (cancelacion_minuto_conductor == null) {
        $("#ctc_tarifa_cancelacion_minutoctc_tarifa_cancelacion_minuto_conducto").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_tarifa_cancelacion_minuto_conducto").css("background-color", "#ffffff");
    }
    if (cancelacion_basica_conductor == null) {
        $("#ctc_tarifa_cancelacion_basica_conductor").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_tarifa_cancelacion_basica_conductor").css("background-color", "#ffffff");
    }
    if (minutos_para_cancelar_conductor == null) {
        $("#ctc_minutos_para_cancelar_conductor").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#ctc_minutos_para_cancelar_conductor").css("background-color", "#ffffff");
    }

    if (acepted) {

        $.post(url, {evento: "registrar_tipo_carrera",
            id: id,
            nombre: nombre,
            desc: desc,
            base: base,
            km: km,
            minuto: minuto,
            comision:comision,
            cancelacion_basica:cancelacion_basica,
            cancelacion_minuto:cancelacion_minuto,
            minutos_para_cancelar:minutos_para_cancelar,
            minutos_para_cancelar_conductor:minutos_para_cancelar_conductor,
            cancelacion_basica_conductor:cancelacion_basica_conductor,
            cancelacion_minuto_conductor:cancelacion_minuto_conductor
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