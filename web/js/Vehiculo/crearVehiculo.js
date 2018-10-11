/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
var rol_id;
var placa_existe;
$(document).ready(function () {
    $("#placa_exist").css("display","none");
    mostrar_progress();
    $.post(url, {evento: "get_categorias_vehiculo"}, function (resp) {
        cerrar_progress();
        if (resp == "falso") {
            alert("Ocurrio un error inesperado al cargar la pagina, intente nuevamente.");
            window.location.href = "index.html";
        } else {
            var json = $.parseJSON(resp);
            var html="";
            $.each(json,function(i,obj){
               html+= " <div class='form-check'>";
                html+= "             <label>";
                  html+= obj.CATEGORIA;
                  html+= "           </label>";
                   html+= "          <input type='checkbox' value='"+obj.ID+"' class='cat_vehiculo'>";
                   html+="       </div>"; 
            });
            $("#cv_categoria").html(html);
        }
    });
});

function ok_crear() {
    
    var acepted = true;

    var placa = $("#cv_placa").val() || null;
    var marca = $("#cv_marca").val() || null;
    var modelo = $("#cv_modelo").val() || null;
    var anho = $("#cv_anho").val() || null;
    var color = $("#cv_color").val() || null;
    var chasis = $("#cv_chasis").val() || "n/a";
    var npuertas = $("#cv_npuertas").val() || null;
    var motor = $("#cv_motor").val() || null;
    
     var categoria = $("#cv_categoria").find(".cat_vehiculo") || null;
     var select="[";
     var acp=false;
     $.each(categoria,function (i,obj){
         if($(obj).is(":checked")){
             select+="{'id':'"+$(obj).val()+"'},";
             acp=true;
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
    if (placa == null || placa_existe==true) {
        $("#cv_placa").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cv_placa").css("background-color", "#ffffff");
    }
    if (marca == null) {
        $("#cv_marca").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cv_marca").css("background-color", "#ffffff");
    }
    if (modelo == null) {
        $("#cv_modelo").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cv_modelo").css("background-color", "#ffffff");
    }
    if (anho == null) {
        $("#cv_anho").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cv_anho").css("background-color", "#ffffff");
    }
    if (color == null) {
        $("#cv_color").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cv_color").css("background-color", "#ffffff");
    }
    if (npuertas == null) {
        $("#cv_npuertas").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cv_npuertas").css("background-color", "#ffffff");
    }
    if (motor == null) {
        $("#cv_motor").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#cv_motor").css("background-color", "#ffffff");
    }
 
    if (acepted) {
           var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
           mostrar_progress();
        $.post(url, {evento: "registrar_vehiculo",
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
                alert("Ocurrio un herror al registrar Vehiculo");
            } else {
                alert("Vehiculo registrado con exito");
                window.location.href="VehiculoPerf.html?id="+resp;
            }
        });
    }

}

function placa_exist(){
    var plac=$("#cv_placa").val() || null;
    
    if(plac==null){
        $("#placa_exist").css("display","none");
        placa_existe=false;
    }else{
        $.post(url,{evento:"comprovar_placa",placa:plac},function(resp){
            if(resp=='true'){
                 $("#placa_exist").css("display","");
                      placa_existe=true;
            }else if(resp=='false'){
                 $("#placa_exist").css("display","none");
                      placa_existe=false;
            }
        });
    }
}