/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
var urlRoles = "rolesController";
var rol_id;
$(document).ready(function () {
    $.post(urlRoles, {evento: "get_rol_por_nombre", nombre: "Cliente"}, function (resp) {
        if (resp == "falso") {
            alert("Ocurrio un error inesperado al cargar la pagina, intente nuevamente.");
            window.location.href = "index.html";
        } else {
            var json = $.parseJSON(resp);
            if (json.exito == "si") {
                rol_id = json.ID;
            } else if (json.exito == "no") {
                alert("No se pudo obtener el rol Conductor, favor de contactarse con el administrador.");
            }
        }
    });
});

function ok_crear() {
    var acepted = true;

    var nombre = $("#cu_nombre").val() || null;
    var apellido_pa = $("#cu_ap_pa").val() || null;
    var apellido_ma = $("#cu_ap_ma").val() || null;
    var tipo = rol_id;
    var correo = $("#cu_correo").val() || null;
    var sexo = $("#cu_sexo").val() || null;
    var fecha_nac = $("#cu_fecha_nac").val() || null;
    var usuario = $("#cu_usuario").val() || null;
    var pass = $("#cu_contrasenha").val() || null;
    var rep_pass = $("#cu_repcontrasenha").val() || null;

    if (nombre == null) {
        $("#cu_nombre").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_nombre").css("background-color", "#ffffff");
    }
    if (apellido_pa == null) {
        $("#cu_ap_pa").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_ap_pa").css("background-color", "#ffffff");
    }
    if (apellido_ma == null) {
        $("#cu_ap_ma").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_ap_ma").css("background-color", "#ffffff");
    }
    if (correo == null) {
        $("#cu_correo").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_correo").css("background-color", "#ffffff");
    }
    if (fecha_nac == null) {
        $("#cu_fecha_nac").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_fecha_nac").css("background-color", "#ffffff");
    }
    if (usuario == null) {
        $("#cu_usuario").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_usuario").css("background-color", "#ffffff");
    }
    if (pass == null) {
        $("#cu_contrasenha").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_contrasenha").css("background-color", "#ffffff");
    }
    if (rep_pass == null) {
        $("#cu_repcontrasenha").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_repcontrasenha").css("background-color", "#ffffff");
    }
    if (rep_pass != pass) {
        $("#cu_repcontrasenha").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_repcontrasenha").css("background-color", "#ffffff");
    }
    if (acepted) {
        var passmd5 = md5(pass);
        $.post(url, {evento: "registrar_usuario_conductor",
            nombre: nombre,
            apellido_pa: apellido_pa,
            apellido_ma: apellido_ma,
            id_rol: tipo,
            usuario: usuario,
            pass: passmd5,
            sexo:sexo,
            correo:correo,
            fecha_nac:fecha_nac
        }, function (resp) {
            if (resp == "false" || resp.length <= 0) {
                alert("Ocurrio un herror al registrar Usuario");
            } else {
                alert("Usuario registrado con exito");
            }
        });
    }

}