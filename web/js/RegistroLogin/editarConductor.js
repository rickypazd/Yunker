/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
var urlRoles = "rolesController";
var rol_id;
var id_usr;
$(document).ready(function () {
    id_usr = getQueryVariable("id");
     if(id_usr>0){
        $("#btn_volver").attr("href","verPerfil.html?id="+id_usr);
    }
    $("#maravilla").parent().css("display", "none");
    $("#submitform").submit(function (event) {
        event.preventDefault();
        var file = $("#file-0d").val() || null;
        var exito = true;
       
        if (file == null) {
            $("#file-0d").css("background-color", "#f386ab");
            alert("Es nesesario seleccioar un archibo.");
            exito = false;
        } else {
            $("#file-0d").css("background-color", "#ffffff");
        }
       if(id_usr>0){
           $("#id_usr").val(id_usr);
       }else{
              exito = false;
               alert("Ocurrio un Error por favor vuelva a intentarlo.");
               window.location.href="index.html";
       }
        if (exito) {
            
            mostrar_progress();
            var formData = new FormData($("#submitform")[0]);
            $.ajax({
                url: 'admin/adminController',
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (returndata) {
                    cerrar_progress();
                    if(returndata == "exito"){
                        window.location.href="editarConductor.html?id="+id_usr;
                       // alert("exito");
                    }
                    
                },
                error: function () {
                    alert("Error al contactarse con el servidor.");
                    cerrar_progress();
                }
            });
            // $(this).unbind('submit').submit();
        }

    });
    $.post(url, {evento: "get_usr_con", id: id_usr}, function (resp) {
        var obj = $.parseJSON(resp);
        
        var foto_perfil=obj.foto_perfil || "";
        if(foto_perfil.length>0){
             $("#foto_perfil").attr("src",foto_perfil);
        }
       
        $("#cu_nombre").val(obj.nombre);
        $("#cu_ap_pa").val(obj.apellido_pa);
        $("#cu_ap_ma").val(obj.apellido_ma);
        $("#cu_correo").val(obj.correo);
        $("#cu_numero_licencia").val(obj.numero_licencia);
        $("#cu_categoria").find('option:contains(' + obj.categoria_licencia + ')').attr("selected", true);

        $('#cu_sexo').find('option:contains(' + obj.sexo + ')').attr("selected", true);
        if (obj.sexo == "Mujer") {
            $("#maravilla").parent().css("display", "");
        }
        $("#cu_fecha_nac").val(obj.fecha_nac);
        $("#cu_ci").val(obj.ci);
        $("#cu_telefono").val(obj.telefono);
        $("#cu_referencia").val(obj.referencia);
        $('#cu_ciudad').find('option:contains(' + obj.ciudad + ')').attr("selected", true);
        var parametros = obj.parametros;
         $("#estandar").prop("checked",parametros.act_estandar);
         $("#togo").prop("checked",parametros.act_togo);
         $("#maravilla").prop("checked",parametros.act_maravilla);
         $("#super").prop("checked",parametros.act_super);
       
    });
});

function ok_crear() {
    var acepted = true;

    var nombre = $("#cu_nombre").val() || "";
    var apellido_pa = $("#cu_ap_pa").val() || "";
    var apellido_ma = $("#cu_ap_ma").val() || "";

    var correo = $("#cu_correo").val() || "";
    var sexo = $("#cu_sexo").val() || "";
    var fecha_nac = $("#cu_fecha_nac").val() || "";
    var cu_ci = $("#cu_ci").val() || "";
    var cu_telefono = $("#cu_telefono").val() || "";
    var cu_referencia = $("#cu_referencia").val() || "";
    var cu_ciudad = $("#cu_ciudad").val() || "";
    var usuario = $("#cu_usuario").val() || "";
    var pass = $("#cu_contrasenha").val() || "";
    var rep_pass = $("#cu_repcontrasenha").val() || "";
    var estandar = $("#estandar").is(":checked");
    var togo = $("#togo").is(":checked");
    var maravilla = $("#maravilla").is(":checked");
    var superss = $("#super").is(":checked");
    var numero_lic = $("#cu_numero_licencia").val() || "";
    var categoria_lic = $("#cu_categoria").val() || "";

    if (acepted) {
        
        var passmd5 = md5(pass);
        mostrar_progress();
        var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
        $.post(url, {evento: "actualizar_usuario_conductor",
            id: id_usr,
            nombre: nombre,
            apellido_pa: apellido_pa,
            apellido_ma: apellido_ma,
            sexo: sexo,
            correo: correo,
            fecha_nac: fecha_nac,
            ci: cu_ci,
            telefono: cu_telefono,
            referencia: cu_referencia,
            ciudad: cu_ciudad,
            estandar: estandar,
            togo: togo,
            maravilla: maravilla,
            super: superss,
            numero_lic: numero_lic,
            categoria_lic: categoria_lic,
            id_admin:usr_log.id
        }, function (resp) {
            cerrar_progress();
            if (resp == "falso" || resp.length <= 0) {
                alert("Ocurrio un herror al Actualizar Usuario");
            } else {
                window.location.href = "verPerfil.html?id=" + id_usr;

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
