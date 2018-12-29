/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var id = 0;
$(document).ready(function () {

});

function agregar_categoria() {
    mostrar_progress();
    var exito = true;
    var nombre = $("#nombre_categoria").val() || null;
    if (nombre != null && nombre.length > 0) {
        $("#nombre_categoria").css("background", "#ffffff");
    } else {
        $("#nombre_categoria").css("background", "#df5b5b");
        exito = false;
    }
    if (exito) {
        cerrar_progress();
        id++;
        var obj = {id: id, nombre: nombre};
        cargar_categoria_iten(obj)
        $("#nombre_categoria").val("");
        $("#modal_agregar_categoria").modal("toggle");

    } else {
        cerrar_progress();
    }
}


function cargar_categoria_iten(obj) {
    var html = "<div class='card'>";
    html += "                    <div class='card-header' id='heading-" + obj.id + "'>";
    html += "                         <h5 class='mb-0'>";
    html += "                             <button class='btn btn-link collapsed' data-toggle='collapse' data-target='#collapse-" + obj.id + "' aria-expanded='false' aria-controls='collapse-" + obj.id + "'  data-subcategorias_cargadas='false' onclick='abrir_categoria(this)'>";
    html += obj.nombre;
    html += "                             </button>";
    html += "                         </h5>";
    html += "                     </div>";
    html += "                     <div id='collapse-" + obj.id + "' class='collapse' aria-labelledby='heading-" + obj.id + "' data-parent='#accordion'>";
    html += "                         <div class='card-body'>";
    html += "<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#modal_agregar_categoria'>  Agregar Sub-Categoria</button>";
    html += "                             <ul class='list-group lista_sub_categorias'>";
    html += "                                 ";
    html += "                             </ul>";
    html += "                         </div>";
    html += "                     </div>";
    html += "                 </div>";
    $("#accordion").append(html);
}

function abrir_categoria(iten) {
    var cargado = $(iten).data("subcategorias_cargadas");
    if (!cargado) {
        $(iten).data("subcategorias_cargadas", true);
        
    }
}

function cargar_sub_categoria_iten(obj)
    
}