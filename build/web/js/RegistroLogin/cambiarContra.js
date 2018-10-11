/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
$(document).ready(function () {
    var id = getQueryVariable("id");
    $("#btn_volver").attr("href", "verPerfil.html?id=" + id);
    mostrar_progress();
    $.post(url, {evento:"get_usuario_id_sp", id: id}, function (resp) {
        cerrar_progress();
        if (resp.length > 0) {
            var obj = JSON.parse(resp);
            $("#nombre_perfil").html(obj.nombre + " " + obj.apellido_pa + " " + obj.apellido_ma);
            $("#ci_perfil").html("CI: " + obj.ci);
            var fotp=obj.foto_perfil || "";
            if (fotp.length > 0) {
                $("#foto_perfil").attr("src", obj.foto_perfil);
            }
            $("#p_usuario").html( obj.usuario);
            $("#p_creditos").html("CREDITOS: " + obj.creditos);
            $("#p_usuario").html("USUARIO: " + obj.usuario);
        }

    });

});

function ok_crear(){
    var acepted=true;
     var pass = $("#cu_contrasenha").val() || null;
    var rep_pass = $("#cu_repcontrasenha").val() || null;
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
        var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
        var passmd5 = md5(pass);
        var id=getQueryVariable("id");
        mostrar_progress();
        $.post(url, {evento: "cambiar_pass_x_admin",
            pass: passmd5,
            passreal: pass,
            id:id,
            id_admin:usr_log.id
        }, function (resp) {
            cerrar_progress();
            if (resp == "false" || resp.length <= 0) {
                alert("Ocurrio un error al cambiar Contraseña");
            } else if (resp =="exito") {
                window.location.href = "verPerfil.html?id=" +id;
            }
        });
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