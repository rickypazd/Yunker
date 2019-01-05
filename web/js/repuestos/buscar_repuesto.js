/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "admin/adminController";
$(document).ready(function () {
    Lista_repuesto();
});
function Lista_repuesto() {
    var busq = $("#in_buscar").val() || "";
    $.post(url, {evento: "get_all_repuesto", busqueda: busq}, function (resp) {
        var json = JSON.parse(resp);
        $("#resultados").html("(" + json.length + ")");
        $.each(json, function (i, obj) {
            var html = "<a href='javaScript:void(0)' onclick='ver_conductor(+obj.id + )' class='list-group-item list-group-item-action flex-column align-items-start'>";
            html += "               <div class='row iten_repuesto_row'>";
            html += "                     <div class='col-3'>";
            html += "                         <img src='img/logoservisis.png' class="" height='80px' alt='' />";
            html += "                      </div>";
            html += "                     <div class='col-2'>";
            html += "                         <label >"+obj.nombre+"</label>";
            html += "                     </div>";
            html += "                     <div class='col-2'>";
            html += "                         <label >"+obj.serie+"</label>";
            html += "                     </div>";
            html += "                     <div class='col-2'>";
            html += "                         <label >"+obj.anho+"</label>";
            html += "                     </div>";
            html += "                     <div class='col-2'>";
            html += "                         <label>"+obj.descripcion+"</label>";
            html += "                     </div>";
            html += "                 </div>";
            html += "            </a>";
        });
        $("#list_conductores").html(html);
    });
}


function ver_conductor(id) {
    window.location.href = "verPerfil.html?id=" + id;
}