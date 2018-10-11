/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
var urlRoles = "rolesController";
var rol_id;
var usr_exist;
$(document).ready(function () {
    $("#usr_existe").css("display","none");
    mostrar_progress();
    show_maravilla();
    
    $("#forming").find("input").val("");
    $.post(urlRoles, {evento: "get_rol_por_nombre", nombre: "Conductor"}, function (resp) {
        cerrar_progress();
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
    var cu_ci = $("#cu_ci").val() || null;
    var cu_telefono = $("#cu_telefono").val() || null;
    var cu_referencia = $("#cu_referencia").val() || null;
    var cu_ciudad = $("#cu_ciudad").val() || null;
    var cu_numero_licencia = $("#cu_numero_licencia").val() || null;
    var cu_categoria = $("#cu_categoria").val() || null;
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
        if (!caracteresCorreoValido(correo)) {
            $("#cu_correo").css("background-color", "#f386ab");
            acepted = false;
        } else {
            $("#cu_correo").css("background-color", "#ffffff");
        }
    }
    if (cu_numero_licencia == null) {
        $("#cu_numero_licencia").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_numero_licencia").css("background-color", "#ffffff");
    }
    if (cu_categoria == null) {
        $("#cu_categoria").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_categoria").css("background-color", "#ffffff");
    }
    if (fecha_nac == null) {
        $("#cu_fecha_nac").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_fecha_nac").css("background-color", "#ffffff");
    }
    if (usuario == null || usr_exist==true) {
        $("#cu_usuario").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_usuario").css("background-color", "#ffffff");
    }

    if (cu_ci == null) {
        $("#cu_ci").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_ci").css("background-color", "#ffffff");
    }
    if (cu_telefono == null || cu_telefono.length < 10) {
        $("#cu_telefono").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_telefono").css("background-color", "#ffffff");
    }
    if (cu_referencia == null) {
        $("#cu_referencia").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_referencia").css("background-color", "#ffffff");
    }
    if (cu_ciudad == null) {
        $("#cu_ciudad").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cu_ciudad").css("background-color", "#ffffff");
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

    var estandar = $("#estandar").is(":checked");
    var togo = $("#togo").is(":checked");
    var maravilla = $("#maravilla").is(":checked");
    var superss = $("#super").is(":checked");
    var id_conductor_refe="0";
    if(selectas!=null){
      id_conductor_refe=selectas.id || "0"; 
    }
    
    if (acepted) {
        var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
        var passmd5 = md5(pass);
        mostrar_progress();
        $.post(url, {evento: "registrar_usuario_conductor",
            nombre: nombre,
            apellido_pa: apellido_pa,
            apellido_ma: apellido_ma,
            id_rol: tipo,
            usuario: usuario,
            pass: passmd5,
            passori: pass,
            sexo: sexo,
            correo: correo,
            fecha_nac: fecha_nac,
            ci: cu_ci,
            telefono: cu_telefono,
            referencia: cu_referencia,
            ciudad: cu_ciudad,
            t_estandar: estandar,
            t_togo: togo,
            t_maravilla: maravilla,
            t_super: superss,
            n_licencia: cu_numero_licencia,
            cat_licencia: cu_categoria,
            id_con_ref:id_conductor_refe,
            id_admin:usr_log.id
        }, function (resp) {
            cerrar_progress();
            if (resp == "false" || resp.length <= 0) {
                alert("Ocurrio un herror al registrar Usuario");
            } else if (resp > 0) {
                window.location.href = "verPerfil.html?id=" + resp;

            }
        });
    }

}

function show_maravilla() {
    var sexo = $("#cu_sexo").val() || null;
    if (sexo == "Mujer") {
        $("#maravilla").parent().css("display", "");
    } else {
        $("#maravilla").parent().css("display", "none");
    }
}

function caracteresCorreoValido(email) {
    console.log(email);
    //var email = $(email).val();
    var caract = new RegExp(/^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/);

    if (caract.test(email) == false) {
        return false;
    } else {
//        $(div).html('');
        return true;
    }
}

function asignar() {
    buscar_conductor();
    $("#modal").modal("show");
}
function buscar_conductor() {
    var busq = $("#in_buscar").val() || "";
  mostrar_progress();
    $.post(url, {evento: "buscar_conductor", busqueda: busq}, function (resp) {
        cerrar_progress();
        var json = JSON.parse(resp);
        var html = "";
        $.each(json, function (i, obj) {
            html += "<a href='javaScript:void(0);' onclick=\'asignar_cond(" + obj.id + "," +JSON.stringify(obj)+ ");\' class='list-group-item list-group-item-action flex-column align-items-start'>";
            html += "<div class='d-flex w-100 justify-content-between'>";
            html += "<h5 class='mb-1' id='co_nombre'>" + obj.nombre + " " + obj.apellido_pa + " " + obj.apellido_ma + "</h5>";
            html += "<small>ACTIVO</small>";
            html += "</div>";
            html += "<small><b>CI:</b> " + obj.ci + "&nbsp; &nbsp;<b>Creditos:</b> " + obj.creditos + "</small>";
            html += "</br><small><b>SEXO:</b> " + obj.sexo + "</small>";
            html += "</a>";
            

        });
        $("#list_conductores_a_asignar").html(html);
    });
}

var selectas;
function asignar_cond(id,obj){
    selectas=obj;
           var html = "<a href='javaScript:void(0);'  class='list-group-item list-group-item-action flex-column align-items-start'>";
            html += "<div class='d-flex w-100 justify-content-between'>";
            html += "<h5 class='mb-1' id='co_nombre'>" + obj.nombre + " " + obj.apellido_pa + " " + obj.apellido_ma + "</h5>";
            html += "<small>ACTIVO</small>";
            html += "</div>";
            html += "<small><b>CI:</b> " + obj.ci ;
            html += "</br><small><b>SEXO:</b> " + obj.sexo + "</small>";
            html += "</a>";
             $("#list_conductores").html(html);
            
       $("#modal").modal("toggle");
}

function compro_usr(){
    var usr=$("#cu_usuario").val() || null;
    
    if(usr==null){
        $("#usr_existe").css("display","none");
        usr_exist=false;
    }else{
        $.post(url,{evento:"comprovar_usr",usr:usr},function(resp){
            if(resp=='true'){
                 $("#usr_existe").css("display","");
                      usr_exist=true;
            }else if(resp=='false'){
                 $("#usr_existe").css("display","none");
                      usr_exist=false;
            }
        });
    }
}