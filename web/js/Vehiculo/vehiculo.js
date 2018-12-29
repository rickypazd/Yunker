/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "repuestosController";
$(document).ready(function () {
    cargar_marcas();
    cargar_version_auto();
});

function cargar_marcas() {
    mostrar_progress();
    $.post(url, {
        evento: "getAll_rep_auto_marca",
        TokenAcceso: "servi12sis3"
    }, function (resp) {
    cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != 1) {
            //error
                alert(obj.mensaje);
            } else {
            //exito
                //alert(resp);
                $("#lista_marca").html("");
                cargar_lista_marca($.parseJSON(obj.resp));
                
            }
        }
    });
}


function cargar_lista_marca(arr){
    $.each(arr,function(i,obj){
        cargar_iten_marca(obj); 
    });
}

function cargar_iten_marca(obj) {
    var url_foto= "img/Sin_imagen.png";
    if(obj.url_foto.length>0){
        url_foto = obj.url_foto;
    }
    var html = "<li onclick='seleccionar_marca("+JSON.stringify(obj)+")'>";
    html += "   <img src='"+url_foto+"' height='50' width='80' alt=''/>";
    html += "   <span>"+obj.nombre+"</span>";
    html += "</li>";
    $("#lista_marca").append(html);
}

function seleccionar_marca(obj){
    alert(obj.id);
}

function cargar_version_auto() {
    var html = "";
    //mostrar_progress();
    $.post(url, {TokenAcceso: "servi12sis3", evento: "getAll_rep_auto_version"}, function (resp) {
        //  cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != "1") {
                alert(obj.mensaje);
            } else {
                var arr = $.parseJSON(obj.resp);
                $.each(arr, function (i, obj) {
                    html += "<li class='list-group-item' data-obj='"+JSON.stringify(obj)+"' onclick='agregar_version(this);'>" + obj.nombre + "</li>";
                });
                $("#lista_version").html(html);
            }
        }
    });
}
    function agregar_version(iten){
       
        $(iten).remove();
        $("#lista_version_agregadas").add(iten);
    }



