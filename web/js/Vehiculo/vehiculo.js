/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "repuestosController";
$(document).ready(function () {
    cargar_marcas();
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