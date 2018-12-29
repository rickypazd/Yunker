/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "repuestosController";
$(document).ready(function () {
    cargar_categorias();
});
//----------CATEGORIA-------------

function cargar_categorias() {
    mostrar_progress();
    $.post(url, {evento: "getAll_rep_categoria", TokenAcceso: "servi12sis3"}, function (resp) {
        cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != "1") {
                alert(obj.mensaje);
            } else {
                var arr = $.parseJSON(obj.resp);
                $.each(arr, function (i, object) {
                    cargar_categoria_iten(object);
                });

            }
        }

    });
}
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
        $.post(url, {evento: "registrar_rep_categoria", TokenAcceso: "servi12sis3", nombre: nombre}, function (resp) {
            cerrar_progress();
            if (resp != null) {
                var obj = $.parseJSON(resp);
                if (obj.estado != "1") {
                    alert(obj.mensaje);
                } else {
                    var object = $.parseJSON(obj.resp);
                    cargar_categoria_iten(object);
                }
            }
            $("#nombre_categoria").val("");
            $("#modal_agregar_categoria").modal("toggle");
        });
    } else {
        cerrar_progress();
    }
}
function cargar_categoria_iten(obj) {
    var html = "<div class='card'>";
    html += "                    <div class='card-header' id='heading-" + obj.id + "'>";
    html += "                         <h5 class='mb-0'>";
    html += "                             <button class='btn btn-link collapsed' data-toggle='collapse' data-target='#collapse-" + obj.id + "' aria-expanded='false' aria-controls='collapse-" + obj.id + "'  data-subcategorias_cargadas='false' data-obj='"+JSON.stringify(obj)+"' onclick='abrir_categoria(this)'>";
    html += obj.nombre;
    html += "                             </button>";
    html += "                         </h5>";
    html += "                     </div>";
    html += "                     <div id='collapse-" + obj.id + "' class='collapse' aria-labelledby='heading-" + obj.id + "' data-parent='#accordion'>";
    html += "                         <div class='card-body'>";
    html += "                             <button type='button' class='btn btn-primary' data-toggle='modal' data-target='#modal_agregar_sub_categoria' onclick='abrir_agregar_sub_categoria(" + obj.id + ");'>  Agregar Sub-Categoria</button>";
    html += "                             <ul class='list-group lista_sub_categorias' id='lista_sub_categoria_" + obj.id + "'>";
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
        var iten_lista = $(iten).parent().parent().parent().find(".lista_sub_categorias");
        //cargar_sub_categoria_iten({},iten_lista);
        var data_obj = $(iten).data("obj");
        mostrar_progress();
        $.post(url, {evento: "get_rep_sub_categoria_by_id_rep_categoria", TokenAcceso: "servi12sis3", id_rep_categoria:data_obj.id}, function (resp) {
            cerrar_progress();
            if (resp != null) {
                var obj = $.parseJSON(resp);
                if (obj.estado != "1") {
                    alert(obj.mensaje);
                } else {
                    var arr = $.parseJSON(obj.resp);
                    $.each(arr, function (i, object) {
                        cargar_sub_categoria_iten(object,iten_lista);
                    });

                }
            }

        });
    }
}
//----------SUB-CATEGORIA----------
var lista_selected;
function abrir_agregar_sub_categoria(id) {
    $("#btn_agregar_sub_categoria").attr("onclick", "agregar_sub_categoria(" + id + ")");
}
function agregar_sub_categoria(id) {
    mostrar_progress();
    var exito = true;
    var nombre = $("#nombre_sub_categoria").val() || null;
    if (nombre != null && nombre.length > 0) {
        $("#nombre_sub_categoria").css("background", "#ffffff");
    } else {
        $("#nombre_sub_categoria").css("background", "#df5b5b");
        exito = false;
    }
    if (exito) {

        $.post(url, {evento: "registrar_rep_sub_categoria", TokenAcceso: "servi12sis3", nombre: nombre, id_rep_categoria: id}, function (resp) {
            cerrar_progress();
            if (resp != null) {
                var obj = $.parseJSON(resp);
                if (obj.estado != "1") {
                    alert(obj.mensaje);
                } else {
                    var object = $.parseJSON(obj.resp);
                    cargar_sub_categoria_iten(object, $("#lista_sub_categoria_" + id));
                }
            }
            $("#nombre_sub_categoria").val("");
            $("#modal_agregar_sub_categoria").modal("toggle");
        });
    } else {
        cerrar_progress();
    }
}

function cargar_sub_categoria_iten(obj, iten) {
    var html = " <li class='list-group-item d-flex justify-content-between align-items-center'>";
    html += obj.nombre;
    html += "                                    <span class='badge badge-primary badge-pill'>14</span>";
    html += "                                </li>";
    $(iten).append(html);
}