/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var url = "repuestosController";
 

$(document).ready(function () {
    var id = getQueryVariable("id");
    $('#text_marca').val(sessionStorage.getItem("dato1"));
    $('#text_modelo').val(sessionStorage.getItem("dato2"));
    $('#text_año').val(sessionStorage.getItem("dato3"));
    $('#text_version').val(sessionStorage.getItem("dato4"));
    cargar_categorias(id);
});
//----------CATEGORIA-------------

function cargar_categorias(id) {
    mostrar_progress();
    $.post(url, {evento: "getAll_rep_categoria_de_vehiculo", TokenAcceso: "servi12sis3", "id_vehiculo":id}, function (resp) {
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
function cargar_categoria_iten(obj) {
    var html = "<div class='card'>";
    html += "                    <div class='card-header iten_categoria' id='heading-" + obj.id + "' data-toggle='collapse' data-target='#collapse-" + obj.id + "' aria-expanded='false' aria-controls='collapse-" + obj.id + "'  data-subcategorias_cargadas='false' data-obj='" + JSON.stringify(obj) + "' onclick='abrir_categoria(this)'>";
    //html += "                         <h5 class='mb-0'>";
    html += "                             <button class='btn btn-link collapsed' style='color: #000;' >";
    html += obj.nombre;
    html += "                             </button>";
    
   // html += "                         </h5>";
    html += "                                    <span class='badge badge-primary badge-pill'>"+obj.count+"</span>";
    html += "                     </div>";
    html += "                     <div id='collapse-" + obj.id + "' class='collapse' aria-labelledby='heading-" + obj.id + "' data-parent='#accordion'>";
    html += "                         <div class='card-body'>";
    html += "                                <p><b>Seleccione la sub-categoria del repuesto.</b></p>";
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
        var iten_lista = $(iten).parent().find(".lista_sub_categorias");
        //cargar_sub_categoria_iten({},iten_lista);
        var data_obj = $(iten).data("obj");
        var id_vehiculo = getQueryVariable("id");
        mostrar_progress();
        $.post(url, {evento: "get_rep_sub_categoria_by_id_rep_categoria_de_id_vehiculo", TokenAcceso: "servi12sis3", id_rep_categoria: data_obj.id, id_vehiculo:id_vehiculo}, function (resp) {
            cerrar_progress();
            if (resp != null) {
                var obj = $.parseJSON(resp);
                if (obj.estado != "1") {
                    alert(obj.mensaje);
                } else {
                    var arr = $.parseJSON(obj.resp);
                    $.each(arr, function (i, object) {
                        cargar_sub_categoria_iten(object, iten_lista);
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

function cargar_sub_categoria_iten(obj, iten) {
    var html = " <li class='list-group-item d-flex justify-content-between align-items-center sub_categoria_iten' data-obj='" + JSON.stringify(obj) + "' onclick='seleccionar_sub_categoria(this);'>";
    html += obj.nombre;
    html += "                                    <span class='badge badge-primary badge-pill'>></span>";
    html += "                                </li>";
    $(iten).append(html);
}

function seleccionar_sub_categoria(iten) {
    
    var idvehiculo= getQueryVariable("id");
    var obj = $(iten).data("obj");
    //alert(obj.id+" -- "+obj.nombre);
    
    window.location.href = "compra_buscarVehiculoRepuestos.html?idvehiculo"+idvehiculo+"&idSubCatAct=" + obj.id_rep_sub_categoria_activa;

}

function buscar_lista_categorias() {
    alert("ok");
}


// compra_buscarVehiculoRepuestos



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
