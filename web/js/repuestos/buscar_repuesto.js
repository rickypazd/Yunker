/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "repuestosController";
$(document).ready(function () {
    Lista_repuesto();
});
function Lista_repuesto() {
    mostrar_progress();
    var busq = $("#in_buscar").val() || "";
    $.post(url, {evento: "getAll_repuesto", TokenAcceso: "servi12sis3"}, function (resp) {
        cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != "1") {
                alert(obj.mensaje);
            } else {
                var arr = $.parseJSON(obj.resp);
                $("#resultados").html("(" + arr.length + ")");
                $.each(arr, function (i, obj) {
                    var html = "<a href='javaScript:void(0)' onclick='ver_repuesto(this)' data-obj='"+JSON.stringify(obj)+"' class='list-group-item list-group-item-action flex-column align-items-start'>";
                    html += "               <div class='row iten_repuesto_row'>";
                    html += "                     <div class='col-3'>";
                    html += "                         <img src='"+obj.url_foto+"' class='' height='80px' alt='' />";
                    html += "                      </div>";
                    html += "                     <div class='col-2'>";
                    html += "                         <label >" + obj.nombre + "</label>";
                    html += "                     </div>";
                    html += "                     <div class='col-2'>";
                    html += "                         <label >" + obj.serie + "</label>";
                    html += "                     </div>";
                    html += "                     <div class='col-2'>";
                    html += "                         <label >" + obj.fabricante + "</label>";
                    html += "                     </div>";
                    html += "                     <div class='col-2'>";
                    html += "                         <label>" + obj.descripcion + "</label>";
                    html += "                     </div>";
                    html += "                 </div>";
                    html += "            </a>";
                     $("#list_conductores").append(html);
                });
               
            }
        }

    });
}

function buscar_repuesto(itn){
    var bus = $(itn).val();
    
}
function ver_conductor(id) {
    window.location.href = "verPerfil.html?id=" + id;
}

function ver_repuesto(itn){
    var data = $(itn).data("obj");
    window.location.href='art_repuesto_vehiculo_perfil.html?id='+data.id;
}