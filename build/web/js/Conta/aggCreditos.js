/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
var id_usr;
$(document).ready(function () {
    id_usr = getQueryVariable("id");
    $.post(url, {evento: "get_usuario_id_sp", id: id_usr}, function (resp) {
        if (resp.length > 0) {
            var obj = JSON.parse(resp);
            $("#total_creditos").html(obj.creditos);
        }
    });
});
var cantidad;
function aceptar() {
    var correcto = true;
    cantidad = $("#cantidad").val() || null;
    if (cantidad == null) {
        correcto = false;
        $("#cantidad").css("background-color", "#bf484e");
        alert("Nesecita ingresar la cantidad");
    } else {
        $("#cantidad").css("background-color", "#fff");
    }
    if (correcto) {
        $('#exampleModalCenter').modal('show');
    }
}
function ok_agregar() {
    var correcto = true;
    var pass = $("#pass").val() || null;
    if (pass == null) {
        correcto = false;
        $("#pass").css("background-color", "#bf484e");
        $("#alert").css("display", "");
    } else {
        $("#pass").css("background-color", "#fff");
    }
    if (correcto) {
        if (!sessionStorage.getItem("usr_log")) {
            window.location.href = "Login.html";
        } else {
            var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
            var passmd5 = md5(pass);
            if (cantidad > 0 && id_usr > 0) {
                $.post("indexController", {evento: "login", usuario: usr_log.usuario, pass: passmd5}, function (resp) {
                    var obj = $.parseJSON(resp);
                    if (obj.exito == "si") {
                        $("#alert").css("display", "none");
                            $.post(url, {evento: "agg_creditos",
                                id_usr: id_usr,
                                cantidad: cantidad,
                                id_admin: usr_log.id
                            }, function (resp) {
                                if (resp == "exito") {
                                    alert("completo");
                                    window.location.href="verPerfil.html?id="+id_usr;
                                }
                            });
                    } else {
                        $("#pass").css("background-color", "#bf484e");
                        $("#alert").css("display", "");
                    }
                });


            }
        }
    }


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