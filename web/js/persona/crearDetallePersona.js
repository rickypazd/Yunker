/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
var token = "servi12sis3";
var rol_id;
var id_persona;


$(document).ready(function () {
    var id = getQueryVariable("id");
    alert(id);
    id_persona = id;
    getUsuario();
});

function cambiarTipo() {
    var tipo = $("#cu_tipo").val();
    var aux = tipo;
    if (tipo == 1) {
        $('#cu_ap_pa').parent().find("label").html("Persona Natural:");
        $('#cu_ap_pa').attr("placeholder", "Escriba su Nombre de Persona Natural:");
    } else if (tipo == 2) {
        $('#cu_ap_pa').parent().find("label").html("Razon Social:");
        $('#cu_ap_pa').attr("placeholder", "Escriba la razon Social:");
    }
}

function cambiarCombo(a)
{
    var b;
    if (a == 1) {
        b = true;
    }else if (a == 2) {
        b = false;
    }
    return b;
}

function getUsuario() {

    mostrar_progress();
    $.post(url, {evento: "getbyid_persona",
        TokenAcceso: token,
        id: id_persona
    }, function (respuesta) {
        cerrar_progress();
        if (respuesta != null) {
            var obj = $.parseJSON(respuesta);
            if (obj.estado != 1) {
                //error
                alert(obj.mensaje);

            } else {
                //exito
                var resp = $.parseJSON(obj.resp);
                var respd = obj.mensaje;
                //alert(respd);
                
                var aux; 
                if (resp.tipo_rol == 1) {                    
                    
                    $("#cu_nombre").attr("placeholder", resp.nombre);
                    $("#cu_ap_pa").attr("placeholder", resp.ap_rs);
                    $("#cu_ci_nit").attr("placeholder", resp.ci_nit);
                    $("#cu_telf").attr("placeholder", resp.telefono);
                    $("#cu_correo").attr("placeholder", resp.correo);
                    $("#cu_tipo").attr("placeholder", resp.tipo);
                    
                } else if (resp.tipo_rol == 2)
                {                    
                    $("#cu_nombre").attr("placeholder", resp.nombre);
                    $("#cu_ap_pa").attr("placeholder", resp.ap_rs);
                    $("#cu_ci_nit").attr("placeholder", resp.ci_nit);
                    $("#cu_telf").attr("placeholder", resp.telefono);
                    $("#cu_correo").attr("placeholder", resp.correo);
                    $("#cu_tipo").attr("placeholder", resp.tipo);
                    
                }


            }
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

function ver_conductor(id) {
    window.location.href = "VehiculoPerf.html?id=" + id;
}