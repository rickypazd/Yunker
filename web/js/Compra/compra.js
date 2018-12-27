
var url = "admin/adminController";
var articulos = [];
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
});

function registrar_compra() {
    mostrar_progress();
    var exito = true;
    var unidad_compra = $("#com_unidad_compra").val() || null;
    var unidad_venta = $("#com_unidad_venta").val() || null;
    var factor = $("#com_factor").val() || null;
    var precio_compra = $("#com_precio_compra").val() || null;
    var precio_venta = $("#com_precio_venta").val() || null;
    var margen = $("#com_margen").val() || null;
    var cantidad = $("#com_cantidad").val() || null;
    var forma_pago = $("#com_forma_pago").val() || null;
    var fecha = $("#com_fecha").val() || null;
    if (nombre != null && nombre.length > 0) {
        $("#dep_nombre").css("background", "#ffffff");
    } else {
        $("#dep_nombre").css("background", "#00ff00");
        exito = false;
    }
    if (exito) {
        $.post(url, {evento: "registrar_articulo", unidad_compra: unidad_compra, unidad_venta: unidad_venta,
            factor: factor, precio_compra: precio_compra,
            precio_venta: precio_venta, margen: margen, cantidad: cantidad, forma_pago: forma_pago, fecha: fecha}, function (respuesta) {
            cerrar_progress();
            if (respuesta != null) {
                var obj = $.parseJSON(respuesta);
                if (obj.estado != 1) {
                    //error
                    alert(obj.mensaje);
                } else {
                    //exito                    
                    var resp = obj.resp;
                    alert(resp);
                }
            }
        });
    }
}

function cargar_articulos() {
    var html = "";
    // mostrar_progress();
    $.post(url, {TokenAcceso: "servi12sis3", evento: "get_articulos"}, function (resp) {
        //cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != "1") {
                alert(obj.mensaje);
            } else {
                var arr = $.parseJSON(obj.resp);
                $.each(arr, function (i, obj) {
                    html += "<li class='list-group-item' onclick='seleccionar_articulo(" + obj.id + ",this);' data-articulo='" + JSON.stringify(obj) + "'>" + obj.nombre + "</li>";
                });
                $("#lista_articulos").html(html);
            }
        }
    });
}

function seleccionar_articulo(id, obj) {
    $(".modal_buscar_articulo").modal('toggle');
//    jQuery.noConflict();
    $('.model-compra').modal('show');
    var jsona = $(obj).data("articulo");
    $("#text_clave").html(jsona.id);
    $("#text_nombre").html(jsona.nombre);
    $("#text_descripcion").html(jsona.descripcion);
    $("#text_precio").html(jsona.precio_compra_ref);
    $("#text_margen").html(jsona.margen_de_utilidad);
    $("#btn_marcar_detalle").data(jsona);
}

function cargar_proveedor() {
    var html = "";
    // mostrar_progress();
    $.post(url, {TokenAcceso: "servi12sis3", evento: "getall_persona"}, function (resp) {
        //cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != "1") {
                alert(obj.mensaje);
            } else {
                var arr = $.parseJSON(obj.resp);
                $.each(arr, function (i, obj) {
                    html += "<li class='list-group-item' onclick=\"seleccionar_proveedor(" + obj.id + ",'" + obj.nombre + "');\">" + obj.nombre + "</li>";
                });
                $("#lista_proveedor").html(html);
            }
        }
    });
}

function seleccionar_proveedor(id, nombre) {
    $("#text_proveedor").html(nombre);
    $("#text_proveedor").data("id", id);
    $(".model-proveedor").modal('toggle');
}

function cargar_almacen() {
    var html = "";
    // mostrar_progress();
    $.post(url, {TokenAcceso: "servi12sis3", evento: "getall_almacen"}, function (resp) {
        //cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != "1") {
                alert(obj.mensaje);
            } else {
                var arr = $.parseJSON(obj.resp);
                $.each(arr, function (i, obj) {
                    html += "<li class='list-group-item' onclick=\"seleccionar_almacen(" + obj.id + ",'" + obj.nombre + "');\">" + obj.nombre + "</li>";
                });
                $("#lista_almacen").html(html);
            }
        }
    });
}

function seleccionar_almacen(id, nombre) {
    $("#text_almacen").val(nombre);
    $("#text_almacen").data("id", id);
    $(".model-almacen").modal('toggle');
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

function seleccionar_unidad_medida(id, nombre) {
    $("#text_unidad").val(nombre);
    $("#text_unidad").data("id", id);
    $(".bd-unidad").modal('toggle');
}

function marcar_detalle_compra(btn) {
    var obj = $(btn).data();
    var exito = true;
    var cantidad = $("#text_cantidad").val() || null;
    var unidad_medida = $("#text_unidad").data("id") || null;
    var unidad_medida_val = $("#text_unidad").val() || null;
    var factor = $("#text_factor").val() || null;
    var precio_compra = $("#text_precio_compra").val() || null;
    var precio_venta = $("#text_precio_venta").val() || null;
    var almacen = $("#text_almacen").data("id") || null;
    var almacen_val = $("#text_almacen").val() || null;
    if (cantidad != null && cantidad.length > 0) {
        $("#text_cantidad").css("background", "#ffffff");
    } else {
        $("#text_cantidad").css("background", "#df5b5b");
        exito = false;
    }
    if (unidad_medida_val != null && unidad_medida_val.length > 0 && unidad_medida > 0) {
        $("#text_unidad").css("background", "#ffffff");
    } else {
        $("#text_unidad").css("background", "#df5b5b");
        exito = false;
    }
    if (factor != null && factor.length > 0) {
        $("#text_factor").css("background", "#ffffff");
    } else {
        $("#text_factor").css("background", "#df5b5b");
        exito = false;
    }
    if (precio_compra != null && precio_compra.length > 0) {
        $("#text_precio_compra").css("background", "#ffffff");
    } else {
        $("#text_precio_compra").css("background", "#df5b5b");
        exito = false;
    }
    if (precio_venta != null && precio_venta.length > 0) {
        $("#text_precio_venta").css("background", "#ffffff");
    } else {
        $("#text_precio_venta").css("background", "#df5b5b");
        exito = false;
    }
    if (almacen_val != null && almacen_val.length > 0 && almacen > 0) {
        $("#text_almacen").css("background", "#ffffff");
    } else {
        $("#text_almacen").css("background", "#df5b5b");
        exito = false;
    }
    if (exito) {
        var obja = JSON.parse(JSON.stringify(obj));
        obja.cantidad = cantidad;
        obja.unidad_medida = unidad_medida;
        obja.unidad_medida_val = unidad_medida_val;
        obja.precio_compra = precio_compra;
        obja.precio_venta = precio_venta;
        obja.factor = factor;
        obja.almacen = almacen;
        obja.almacen_val = almacen_val;
        $("#tbody_tabla_compra").append(cargar_iten(obja));
//        articulos.push(obja);
        $(".model-compra").modal('toggle');
        precio_total();
    }
}

function precio_total() {
    var precio = 0;
    var lis_articulo = $("#tbody_tabla_compra").find("tr");
    $.each(lis_articulo, function (i, obj) {
        var aux = $(obj).data("obj").precio_compra;
        precio += Math.floor(aux);
    });
    $("#total_precio").html(precio);
    alert(precio);
}



function cargar_iten(obj) {
    var html = "";
    var cantidad = obj.cantidad;
    var factor = obj.factor;
    var total_articulo = (cantidad * factor);
    html += "  <tr data-obj='" + JSON.stringify(obj) + "'>";
    html += "                        <th scope=+row'>" + obj.id + "</th>";
    html += "                        <td>" + cantidad + "</td>";
    html += "                        <td>" + obj.unidad_medida_val + "</td>";
    html += "                        <td>" + obj.descripcion + "</td>";
    html += "                        <td>" + factor + "</td>";
    html += "                        <td>" + obj.precio_compra + "</td>";
    html += "                        <td>" + obj.precio_venta + "</td>";
    html += "                        <td>" + total_articulo + "</td>";
    html += "                        <td class='text-center' style='width: 220px;'>";
    html += "                            <button class='btn btn-default btn-warning'>Editar</button>";
    html += "                            <button class='btn btn-default btn-danger'>Eliminar</button>";
    html += "                        </td>";
    html += "                    </tr>                 ";
    return html;
}


function cerrar_compra() {
    var exito = true;
    var text_fecha = $("#text_fecha").val() || null;
    var text_documento = $("#text_documento").val() || null;
    var text_serie = $("#text_serie").val() || null;
    var text_proveedor = $("#text_proveedor").data("id") || null;
    var text_codigo_control = $("#text_codigo_control").val() || null;
    var text_autorizacion = $("#text_autorizacion").val() || null;
    if (text_fecha != null && text_fecha.length > 0) {
        $("#text_fecha").css("background", "#ffffff");
    } else {
        $("#text_fecha").css("background", "#df5b5b");
        exito = false;
    }
    if (text_documento != null && text_documento.length > 0) {
        $("#text_documento").css("background", "#ffffff");
    } else {
        $("#text_documento").css("background", "#df5b5b");
        exito = false;
    }
    if (text_serie != null && text_serie.length > 0) {
        $("#text_serie").css("background", "#ffffff");
    } else {
        $("#text_serie").css("background", "#df5b5b");
        exito = false;
    }
    if (text_proveedor > 0) {
        $("#text_proveedor").css("background", "#ffffff");
    } else {
        $("#text_proveedor").css("background", "#df5b5b");
        exito = false;
    }
    if (text_codigo_control != null && text_codigo_control.length > 0) {
        $("#text_codigo_control").css("background", "#ffffff");
    } else {
        $("#text_codigo_control").css("background", "#df5b5b");
        exito = false;
    }
    if (text_autorizacion != null && text_autorizacion.length > 0) {
        $("#text_autorizacion").css("background", "#ffffff");
    } else {
        $("#text_autorizacion").css("background", "#df5b5b");
        exito = false;
    }
    if (exito) {
        var precio = 0;
        var lis_articulo = $("#tbody_tabla_compra").find("tr");
        $.each(lis_articulo, function (i, obj) {
            var aux = $(obj).data("obj").precio_compra;
            precio += Math.floor(aux);
        });
        //alert(precio);
        $("#precio_compra").html("Bs. " + precio);
        $('.model-cerrar-compra').modal('show');
    }
}


function registrar_compra() {
    mostrar_progress();
    var exito = true;
    var text_fecha = $("#text_fecha").val() || null;
    var text_documento = $("#text_documento").val() || null;
    var text_serie = $("#text_serie").val() || null;
    var text_proveedor = $("#text_proveedor").data("id") || null;
    var text_codigo_control = $("#text_codigo_control").val() || null;
    var text_autorizacion = $("#text_autorizacion").val() || null;
    var text_forma_pago = $("#com_forma_pago").val() || null;
    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
    var lista = [];
    var lis_articulo = $("#tbody_tabla_compra").find("tr");
        $.each(lis_articulo, function (i, obj) {
            var objeto = $(obj).data("obj");            
            lista.push(objeto);
        });
        alert(lista);
    if (exito) {        
        $.post(url,
                {
                    evento: "registrar_compra",
                    TokenAcceso: TokenAcceso,
                    id_usr: usr_log.id,
                    fecha: text_fecha,
                    documento: text_documento,
                    serie: text_serie,
                    autorizacion: text_autorizacion,
                    codigo_control: text_codigo_control,
                    forma_pago: text_forma_pago,
                    id_persona: text_proveedor, 
                    detalle_compra: lista
                }, function (respuesta) {
            cerrar_progress();
            if (respuesta != null) {
                var obj = $.parseJSON(respuesta);
                if (obj.estado != 1) {
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