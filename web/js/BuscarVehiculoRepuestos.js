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
    
    var idSubcategoria = getQueryVariable("idSubCatAct");

    mostrar_progress();
    var busq = $("#in_buscar").val() || "";
    $.post(url, {evento: "getAll_repuesto_de_vehiculo", TokenAcceso: "servi12sis3", id_sub_categoria_activa: idSubcategoria}, function (resp) {
        cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != "1") {
                alert(obj.mensaje);
            } else {
                var arr = $.parseJSON(obj.resp);
                $("#resultados").html("(" + arr.length + ")");
                $.each(arr, function (i, obj) {
                    var html = "<a href='javaScript:void(0)' onclick='ver_repuesto(this)' data-obj='" + JSON.stringify(obj) + "' class='list-group-item list-group-item-action flex-column align-items-start'>";
                    html += "               <div class='row iten_repuesto_row'>";
                    html += "                     <div class='col-3'>";
                    html += "                         <img src='" + obj.url_foto + "' class='' height='60px' alt='' />";
                    html += "                      </div>";
                    html += "                     <div class='col-2'>";
                    html += "                         <h6>" + obj.nombre + "</h6>";
                    html += "                     </div>";
                    html += "                     <div class='col-2'>";
                    html += "                         <h6>" + obj.serie + "</h6>";
                    html += "                     </div>";
                    html += "                     <div class='col-2'>";
                    html += "                         <h6>" + obj.fabricante + "</h6>";
                    html += "                     </div>";
                    html += "                     <div class='col-2'>";
                    html += "                         <h6>" + obj.descripcion + "</h6>";
                    html += "                     </div>";
                    html += "                 </div>";
                    html += "            </a>";
                    $("#list_conductores").append(html);
                });

            }
        }

    });
}

function buscar_repuesto(itn) {
    var bus = $(itn).val();

}
function ver_conductor(id) {
    window.location.href = "verPerfil.html?id=" + id;
}

function ver_repuesto(itn) {
    var data = $(itn).data("obj");
    window.location.href = 'art_repuesto_vehiculo_perfil.html?id=' + data.id + "&id_sub_categoria=" + data.id_sub_categoria;
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
