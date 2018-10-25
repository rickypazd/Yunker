/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var url = "admin/adminController";
$(document).ready(function () {
//aki
//
//
//
//post({
//cojntenido del post;
//alert();
//});
//calc_dist();
//nnuevo comentario
  $('#tbl_articulos').dynatable({
                    features: {
                        paginate: false,
                        recordCount: false
                    }
                    // We have one column, but it contains multiple types of info.
                    // So let's define a custom reader for that column to grab
                    // all the extra info and store it in our normalized records.
                });
    cargar_articulos();
    cargar_departamentos();
    cargar_categoria();
    cargar_marca();
    cargar_unidad_medida();

});
function cargar_articulos() {


    var html = "";
    mostrar_progress();
    $.post(url, {TokenAcceso: "servi12sis3", evento: "get_articulos"}, function (resp) {

        cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != "1") {
                alert(obj.mensaje);
                alert(obj.error);
            } else {
                var arr = $.parseJSON(obj.resp);
                $.each(arr, function (i, obj) {
                    html += "<tr>";
                    html += "        <td>" + obj.clave + "</td>";
                    html += "        <td>" + obj.nombre + "</td>";
                    html += "        <td>" + obj.descripcion + "</td>";
                    html += "        <td>" + obj.precio_compra_ref + "</td>";
                    html += "        <td>" + obj.precio_venta_ref + "</td>";
                    html += "        <td>" + obj.margen_de_utilidad + "</td>";
                    html += "        <td>" + obj.id_unidad_medida + "</td>";
                    html += "        <td>" + obj.factor + "</td>";
                    html += "        <td>" + obj.id_categoria + "</td>";
                    html += "        <td>" + obj.id_marca + "</td>";
                    html += "        <td>" + obj.id_departamento + "</td>";
                    html += "</tr>";
                });
                $("#body_tbl_articulos").html(html);
                $('#tbl_articulos').dynatable({
                    features: {
                        paginate: false,
                        recordCount: false
                    }
                    // We have one column, but it contains multiple types of info.
                    // So let's define a custom reader for that column to grab
                    // all the extra info and store it in our normalized records.
                });
                $("#dynatable-query-search-tbl_articulos").attr("placeholder", "Precione fuera para buscar.");
            }
        }

    });
}
function buscar_departamento(input) {
    var text = $(input).val() + "";
    var arr = $("#lista_departamentos").find("li");
    if (text == "") {
        $(arr).css("display", "");
    } else {
        var te;
        $.each(arr, function (i, obj) {
            te = $(obj).html() + "";
            te = te.toLocaleLowerCase() + "";
            var res = te.search(text.toLowerCase());
            if (res) {
                $(obj).css("display", "none");
            } else {
                $(obj).css("display", "");
            }
        });
    }
}
function buscar_categoria(input) {
    var text = $(input).val() + "";
    var arr = $("#lista_categoria").find("li");
    if (text == "") {
        $(arr).css("display", "");
    } else {
        var te;
        $.each(arr, function (i, obj) {
            te = $(obj).html() + "";
            te = te.toLocaleLowerCase() + "";
            var res = te.search(text.toLowerCase());
            if (res) {
                $(obj).css("display", "none");
            } else {
                $(obj).css("display", "");
            }
        });
    }
}
function buscar_marca(input) {
    var text = $(input).val() + "";
    var arr = $("#lista_marca").find("li");
    if (text == "") {
        $(arr).css("display", "");
    } else {
        var te;
        $.each(arr, function (i, obj) {
            te = $(obj).html() + "";
            te = te.toLocaleLowerCase() + "";
            var res = te.search(text.toLowerCase());
            if (res) {
                $(obj).css("display", "none");
            } else {
                $(obj).css("display", "");
            }
        });
    }
}
function buscar_unidad_medida(input) {
    var text = $(input).val() + "";
    var arr = $("#lista_unidad_medida").find("li");
    if (text == "") {
        $(arr).css("display", "");
    } else {
        var te;
        $.each(arr, function (i, obj) {
            te = $(obj).html() + "";
            te = te.toLocaleLowerCase() + "";
            var res = te.search(text.toLowerCase());
            if (res) {
                $(obj).css("display", "none");
            } else {
                $(obj).css("display", "");
            }
        });
    }
}

function registrar_departamento() {
    mostrar_progress();
    var exito = true;
    var nombre = $("#dep_nombre").val() || null;
    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
    if (nombre != null && nombre.length > 0) {
        $("#dep_nombre").css("background", "#ffffff");
    } else {
        $("#dep_nombre").css("background", "#00ff00");
        exito = false;
    }
    if (exito) {
        $.post(url, {evento: "registrar_art_departamento", TokenAcceso: TokenAcceso, id_usr: usr_log.id,
            nombre: nombre}, function (respuesta) {
            cerrar_progress();
            if (respuesta != null) {
                var obj = $.parseJSON(respuesta);
                if (obj.estado != 1) {
                    //error
                    alert(obj.mensaje);
                } else {
                    //exito
                    $("#dep_nombre").val("");
                    var resp = $.parseJSON(obj.resp);
                    var html = "<li class='list-group-item'  onclick=\"seleccionar_departamento(" + obj.id + ",'" + obj.nombre + "');\">" + resp.nombre + "</li>";
                    $("#lista_departamentos").prepend(html);
                    var resp = obj.resp;
                    alert(resp);
                }
            }
        });
    } else {
        cerrar_progress();
    }
}

function registrar_categoria() {
    mostrar_progress();
    var exito = true;
    var nombre = $("#cat_nombre").val() || null;
    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
    if (nombre != null && nombre.length > 0) {
        $("#cat_nombre").css("background", "#ffffff");
    } else {
        $("#cat_nombre").css("background", "#00ff00");
        exito = false;
    }
    if (exito) {
        $.post(url, {evento: "registrar_art_categoria", TokenAcceso: TokenAcceso, id_usr: usr_log.id,
            nombre: nombre}, function (respuesta) {
            cerrar_progress();
            if (respuesta != null) {
                var obj = $.parseJSON(respuesta);
                if (obj.estado != 1) {
//error
                    alert(obj.mensaje);
                } else {
//exito             
                    $("#cat_nombre").val("");
                    var resp = $.parseJSON(obj.resp);
                    var html = "<li class='list-group-item'  onclick=\"seleccionar_categoria(" + obj.id + ",'" + obj.nombre + "');\">" + resp.nombre + "</li>";
                    $("#lista_categoria").prepend(html);
                    var resp = obj.resp;
                    alert(resp);
                }
            }
        });
    } else {
        cerrar_progress();
    }
}

function registrar_marca() {
    mostrar_progress();
    var exito = true;
    var nombre = $("#mar_nombre").val() || null;
    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
    if (nombre != null && nombre.length > 0) {
        $("#mar_nombre").css("background", "#ffffff");
    } else {
        $("#mar_nombre").css("background", "#00ff00");
        exito = false;
    }
    if (exito) {
        $.post(url, {evento: "registrar_art_marca", TokenAcceso: TokenAcceso, id_usr: usr_log.id,
            nombre: nombre}, function (respuesta) {
            cerrar_progress();
            if (respuesta != null) {
                var obj = $.parseJSON(respuesta);
                if (obj.estado != 1) {
//error
                    alert(obj.mensaje);
                } else {
//exito
                    $("#mar_nombre").val("");
                    var resp = $.parseJSON(obj.resp);
                    var html = "<li class='list-group-item'  onclick=\"seleccionar_marca(" + obj.id + ",'" + obj.nombre + "');\">" + resp.nombre + "</li>";
                    $("#lista_marca").prepend(html);
                    var resp = obj.resp;
                    alert(resp);
                }
            }
        });
    } else {
        cerrar_progress();
    }
}


function registrar_unidad_medida() {
    mostrar_progress();
    var exito = true;
    var nombre = $("#unidad_medida_nombre").val() || null;
    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
    if (nombre != null && nombre.length > 0) {
        $("#unidad_medida_nombre").css("background", "#ffffff");
    } else {
        $("#unidad_medida_nombre").css("background", "#00ff00");
        exito = false;
    }
    if (exito) {
        $.post(url, {evento: "registrar_art_unidad_medida", TokenAcceso: TokenAcceso, id_usr: usr_log.id,
            nombre: nombre}, function (respuesta) {
            cerrar_progress();
            if (respuesta != null) {
                var obj = $.parseJSON(respuesta);
                if (obj.estado != 1) {
//error
                    alert(obj.mensaje);
                } else {
//exito
                    $("#unidad_medida_nombre").val("");
                    var resp = $.parseJSON(obj.resp);
                    var html = "<li class='list-group-item' onclick=\"seleccionar_unidad_medida(" + resp.id + ",'" + resp.nombre + "');\">" + resp.nombre + "</li>";
                    $("#lista_unidad_medida").prepend(html);
                    var resp = obj.resp;
                    alert(resp);
                }
            }
        });
    } else {
        cerrar_progress();
    }
}



function registrar_articulo() {
    mostrar_progress();
    var exito = true;
    var clave = $("#art_clave").val() || null;
    var nombre = $("#art_nombre").val() || null;
    var descripcion = $("#art_descripcion").val() || null;
    var departamento = $("#art_departamento").val() || null;
    var id_departamento = $("#art_departamento").data("id");
    var categoria = $("#art_categoria").val() || null;
    var id_categoria = $("#art_categoria").data("id");
    var marca = $("#art_marca").val() || null;
    var id_marca = $("#art_marca").data("id");
    var unidad_medida = $("#art_unidad_medida").val() || null;
    var id_unidad_medida = $("#art_unidad_medida").data("id");
    var factor = $("#art_factor").val() || null;
    var precio_compra_ref = $("#art_precio_compra").val() || null;
    var precio_venta_ref = $("#art_precio_venta").val() || null;
    var margen = $("#art_margen").val() || null;
    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
    if (clave != null && clave.length > 0) {
        $("#art_clave").css("background", "#ffffff");
    } else {
        $("#art_clave").css("background", "#df5b5b");
        exito = false;
    }
    if (nombre != null && nombre.length > 0) {
        $("#art_nombre").css("background", "#ffffff");
    } else {
        $("#art_nombre").css("background", "#df5b5b");
        exito = false;
    }
    if (descripcion != null && descripcion.length > 0) {
        $("#art_descripcion").css("background", "#ffffff");
    } else {
        $("#art_descripcion").css("background", "#df5b5b");
        exito = false;
    }
    if (departamento != null && departamento.length > 0 && id_departamento > 0) {
        $("#art_departamento").css("background", "#ffffff");
    } else {
        $("#art_departamento").css("background", "#df5b5b");
        exito = false;
    }
    if (categoria != null && categoria.length > 0 && id_categoria > 0) {
        $("#art_categoria").css("background", "#ffffff");
    } else {
        $("#art_categoria").css("background", "#df5b5b");
        exito = false;
    }
    if (marca != null && marca.length > 0 && id_marca > 0) {
        $("#art_marca").css("background", "#ffffff");
    } else {
        $("#art_marca").css("background", "#df5b5b");
        exito = false;
    }
    if (unidad_medida != null && unidad_medida.length > 0 && id_unidad_medida > 0) {

        $("#art_unidad_medida").css("background", "#ffffff");
    } else {
        $("#art_unidad_medida").css("background", "#df5b5b");
        exito = false;
    }

    if (factor != null && factor.length > 0) {
        $("#art_factor").css("background", "#ffffff");
    } else {
        $("#art_factor").css("background", "#df5b5b");
        exito = false;
    }
    if (precio_compra_ref != null && precio_compra_ref.length > 0) {
        $("#art_precio_compra").css("background", "#ffffff");
    } else {
        $("#art_precio_compra").css("background", "#df5b5b");
        exito = false;
    }
    if (precio_venta_ref != null && precio_venta_ref.length > 0) {
        $("#art_precio_venta").css("background-color", "#ffffff");
    } else {
        $("#art_precio_venta").css("background-color", "#df5b5b");
        exito = false;
    }
    if (margen != null && margen.length > 0) {
        $("#art_margen").css("background", "#ffffff");
    } else {
        $("#art_margen").css("background", "#df5b5b");
        exito = false;
    }
    if (exito) {
        $.post(url,
                {
                    evento: "registrar_articulo",
                    TokenAcceso: TokenAcceso,
                    id_usr: usr_log.id,
                    clave: clave,
                    nombre: nombre,
                    descripcion: descripcion,
                    id_departamento: id_departamento,
                    id_categoria: id_categoria,
                    id_marca: id_marca,
                    id_unidad_medida: id_unidad_medida,
                    factor: factor,
                    precio_compra_red: precio_compra_ref,
                    precio_venta_ref: precio_venta_ref,
                    margen: margen
                }, function (respuesta) {
            cerrar_progress();
            if (respuesta != null) {
                var obj = $.parseJSON(respuesta);
                if (obj.estado != 1) {
//
                    alert(obj.mensaje);
                } else {
//exito                    
                    var resp = obj.resp;
                    alert(resp);
                }
            }
        });
    } else {
        cerrar_progress();
    }
}

function cargar_departamentos() {
    var html = "";
    //mostrar_progress();
    $.post(url, {TokenAcceso: "servi12sis3", evento: "get_art_departamentos"}, function (resp) {

        //  cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != "1") {
                alert(obj.mensaje);
            } else {
                var arr = $.parseJSON(obj.resp);
                $.each(arr, function (i, obj) {
                    html += "<li class='list-group-item'  onclick=\"seleccionar_departamento(" + obj.id + ",'" + obj.nombre + "');\">" + obj.nombre + "</li>";
                });
                $("#lista_departamentos").html(html);
            }
        }

    });
}
function cargar_categoria() {
    var html = "";
    //mostrar_progress();
    $.post(url, {TokenAcceso: "servi12sis3", evento: "get_art_categoria"}, function (resp) {

        //cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != "1") {
                alert(obj.mensaje);
            } else {
                var arr = $.parseJSON(obj.resp);
                $.each(arr, function (i, obj) {
                    html += "<li class='list-group-item'  onclick=\"seleccionar_categoria(" + obj.id + ",'" + obj.nombre + "');\">" + obj.nombre + "</li>";
                });
                $("#lista_categoria").html(html);
            }
        }

    });
}
function cargar_marca() {
    var html = "";
    // mostrar_progress();
    $.post(url, {TokenAcceso: "servi12sis3", evento: "get_art_marca"}, function (resp) {

        //cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != "1") {
                alert(obj.mensaje);
            } else {
                var arr = $.parseJSON(obj.resp);
                $.each(arr, function (i, obj) {
                    html += "<li class='list-group-item' onclick=\"seleccionar_marca(" + obj.id + ",'" + obj.nombre + "');\">" + obj.nombre + "</li>";
                });
                $("#lista_marca").html(html);
            }
        }

    });
}

function cargar_unidad_medida() {
    var html = "";
    // mostrar_progress();
    $.post(url, {TokenAcceso: "servi12sis3", evento: "get_art_unidad_medida"}, function (resp) {

        //cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != "1") {
                alert(obj.mensaje);
            } else {
                var arr = $.parseJSON(obj.resp);
                $.each(arr, function (i, obj) {
                    html += "<li class='list-group-item' onclick=\"seleccionar_unidad_medida(" + obj.id + ",'" + obj.nombre + "');\">" + obj.nombre + "</li>";
                });
                $("#lista_unidad_medida").html(html);
            }
        }

    });
}

function seleccionar_departamento(id, nombre) {
    $("#art_departamento").val(nombre);
    $("#art_departamento").data("id", id);
    $(".bd-departamento").modal('toggle');
}
function seleccionar_categoria(id, nombre) {
    $("#art_categoria").val(nombre);
    $("#art_categoria").data("id", id);
    $(".bd-familia").modal('toggle');
}
function seleccionar_marca(id, nombre) {
    $("#art_marca").val(nombre);
    $("#art_marca").data("id", id);
    $(".bd-marca").modal('toggle');
}

function seleccionar_unidad_medida(id, nombre) {
    $("#art_unidad_medida").val(nombre);
    $("#art_unidad_medida").data("id", id);
    $(".bd-unidad").modal('toggle');
}