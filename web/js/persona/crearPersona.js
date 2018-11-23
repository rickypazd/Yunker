/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
var rol_id;

$(document).ready(function () {
//    $.post(urlRoles, {evento: "get_rol_por_nombre", nombre: "Cliente"}, function (resp) {
//        if (resp == "falso") {
//            alert("Ocurrio un error inesperado al cargar la pagina, intente nuevamente.");
//            window.location.href = "index.html";
//        } else {
//            var json = $.parseJSON(resp);
//            if (json.exito == "si") {
//                rol_id = json.ID;
//            } else if (json.exito == "no") {
//                alert("No se pudo obtener el rol Conductor, favor de contactarse con el administrador.");
//            }
//        }
//    });

});

function cambiarTipo() {
    var tipo = $("#cu_tipo").val();
    if (tipo == 1) {
        $('#cu_ap_pa').parent().find("label").html("Persona Natural");
        $('#cu_ap_pa').attr("placeholder", "Escriba su Nombre de Persona Natural");
    } else if (tipo == 2) {
        $('#cu_ap_pa').parent().find("label").html("Razon Social");
        $('#cu_ap_pa').attr("placeholder", "Escriba la razon Social");
    }

}

function ok_crear() {
    var acepted = true;
    var tipo = rol_id;
    var nombre = $("#cu_nombre").val() || null;
    var apellido_na = $("#cu_ap_pa").val() || null;
    var carnet = $("#cu_ci_nit").val() || null;
    var telefono = $("#cu_telf").val() || null;
    var correo = $("#cu_correo").val() || null;
    var tipoPersona = $("#cu_tipo").val() || null;


    if (nombre == null) {
        $("#cu_nombre").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_nombre").css("background-color", "#ffffff");
    }

    if (apellido_na == null) {
        $("#cu_ap_pa").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_ap_pa").css("background-color", "#ffffff");
    }
    if (carnet == null) {
        $("#cu_ci_nit").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_ci_nit").css("background-color", "#ffffff");
    }
    if (telefono == null) {
        $("#cu_telf").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_telf").css("background-color", "#ffffff");
    }
    if (correo == null) {
        $("#cu_correo").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_correo").css("background-color", "#ffffff");
    }

    if (acepted) {

        $.post(url, {evento: "registrar_usuario_persona",
            nombre: nombre,
            apellido_na: apellido_na,
            id_rol: tipo,
            tipoPersona: tipoPersona,
            correo: correo,
            telefono: telefono,
            carnet: carnet
        }, function (resp) {
            if (resp === "false" || resp.length <= 0) {
                alert("Ocurrio un herror al registrar Persona");
            } else {
                alert("Persona registrada con exito");
            }
        });
    }

}