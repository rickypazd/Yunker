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
        $("#btn_volver").attr("href","VehiculoPerf.html?id="+id_usr);
    }
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
        if (id_usr > 0) {
            $("#id_usr").val(id_usr);
        } else {
            exito = false;
            alert("Ocurrio un Error por favor vuelva a intentarlo.");
            window.location.href = "index.html";
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
                    if (returndata == "exito") {
                        window.location.href = "editarVehiculo.html?id=" + id_usr;
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
    mostrar_progress();
    $.post(url, {evento: "get_categorias_vehiculo"}, function (resp) {
        cerrar_progress();
        if (resp == "falso") {
            alert("Ocurrio un error inesperado al cargar la pagina, intente nuevamente.");
            window.location.href = "index.html";
        } else {
            var json = $.parseJSON(resp);
            var html = "";
            $.each(json, function (i, obj) {
                html += " <div class='form-check'>";
                html += "             <label>";
                html += obj.CATEGORIA;
                html += "           </label>";
                html += "          <input type='checkbox' id='categoria_" + obj.ID + "' value='" + obj.ID + "' class='cat_vehiculo'>";
                html += "       </div>";
            });
            $("#cv_categoria").html(html);
            cargar_vehiculo();
        }
    });
});

function cargar_vehiculo() {
    mostrar_progress();
    $.post(url, {evento: "get_vehiculo_id", id: id_usr}, function (resp) {
        cerrar_progress();
        if (resp == "falso") {
            alert("Ocurrio un error inesperado al cargar la pagina, intente nuevamente.");
            window.location.href = "index.html";
        } else {
            var obj = $.parseJSON(resp);
            var foto_perfil = obj.foto_perfil || "";
            if (foto_perfil.length > 0) {
                $("#foto_perfil").attr("src", foto_perfil);
            }
            $("#cv_placa").val(obj.placa);
            $("#cv_marca").val(obj.marca);
            $("#cv_modelo").val(obj.modelo);
            $("#cv_anho").val(obj.ano);
            $("#cv_color").val(obj.color);
            $("#cv_npuertas").val(obj.n_puertas);
            $("#cv_motor").val(obj.motor);
            $("#cv_chasis").val(obj.chasis);
            var arrcat = obj.categorias;
            $.each(arrcat,function(i,obj){
                $("#categoria_"+obj.id_categoria).prop("checked","true");
            });
           
        }
    });
}
function ok_crear() {
    var acepted = true;
 var placa = $("#cv_placa").val() || null;
    var marca = $("#cv_marca").val() || null;
    var modelo = $("#cv_modelo").val() || null;
    var anho = $("#cv_anho").val() || null;
    var color = $("#cv_color").val() || null;
    var chasis = $("#cv_chasis").val() || null;
    var npuertas = $("#cv_npuertas").val() || null;
    var motor = $("#cv_motor").val() || null;
    var categoria = $("#cv_categoria").find(".cat_vehiculo") || null;
     var select="[";
     var acp=false;
     $.each(categoria,function (i,obj){
         if($(obj).is(":checked")){
             select+="{'id':'"+$(obj).val()+"','estado':'true'},";
             acp=true;
         }else{
             select+="{'id':'"+$(obj).val()+"','estado':'false'},";
         }
         
     });
      select = select.substring(0, select.length - 1);
      select+="]";
       if (!acp) {
        $("#cv_categoria").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cv_categoria").css("background-color", "#ffffff");
    }

    if (acepted) {
 var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
        mostrar_progress();
        $.post(url, {evento: "actualizar_vehiculo",
            id:id_usr,
             placa: placa,
            marca: marca,
            modelo: modelo,
            anho: anho,
            color: color,
            categoria: select,
            chasis:chasis,
            npuertas:npuertas,
            motor:motor,
            id_admin:usr_log.id
        }, function (resp) {
            cerrar_progress();
            if (resp == "falso" || resp.length <= 0) {
                alert("Ocurrio un herror al Actualizar Usuario");
            } else {
                window.location.href = "VehiculoPerf.html?id=" + id_usr;

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
