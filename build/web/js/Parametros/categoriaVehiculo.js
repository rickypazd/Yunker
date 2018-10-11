/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
$(document).ready(function () {

    get_categorias();
});

function get_categorias() {
    $.post(url, {evento: "get_categorias_vehiculo"}, function (resp) {
        var json = JSON.parse(resp);
        var html = "";
        $.each(json, function (i, obj) {
            html += "<li class='li_roles' >" + obj.CATEGORIA + "</li>";
        });
        $("#list_categorias").html(html);
    });
}



function agergar_categoria() {
    
    var categoria=$("#cc_categoria").val() || null;
     if (categoria == null) {
        $("#cc_categoria").css("background-color", "#f386ab");
       return;
    }
    $.post(url,{evento:"agg_categoria",categoria:categoria},function(resp){
        if(resp=="falso"){
            alert("Ocurrio Algun Error al agregar la categoria");
        }else{
             window.location.reload();    
        }
        
    });
}

function ver_agg_categoria() {
        $("#exampleModalCenter").modal("show");
}
function remover_permiso(id_permiso,id_rol,elemento){
       
    $.post(url,{evento:"remover_permiso",id_rol:id_rol,id_permiso:id_permiso},function(resp){
        if(resp=="exito"){
            $(elemento).parent().remove();
        }else{
            alert("Ocurrio un error al borrar no se efectuaron cambios");
        }
    });
}