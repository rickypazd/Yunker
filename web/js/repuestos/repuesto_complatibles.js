/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "repuestosController";
$(document).ready(function () {
    var idRep = getQueryVariable("idRep");
    var nombre = getQueryVariable("nbr");
    var serie = getQueryVariable("sre");
    var url_foto = getQueryVariable("ft");
    if (idRep == false || nombre == false || serie == false || url_foto == false) {
        alert("Ocurrio algun problema. Disculpe las molestias.");
        window.location.href = "index.html";
        return;
    }
    $("#img_foto_repuesto").attr("src", url_foto);
    $("#text_nombre").html(nombre);
    $("#text_serie").html(serie);

    cargar_marcas();
});

function Cargar_repuesto() {
    mostrar_progress();
    $.post(url, {
        evento: "getById_repuesto",
        TokenAcceso: "servi12sis3",
        id: id_usr
    }, function (resp) {
        cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != 1) {
                //error
                alert(obj.mensaje);
            } else {
                var obje = $.parseJSON(obj.resp);
                $("#text_nombre").html(obje.nombre);
                $("#text_precio").html(obje.precio);
                $("#text_serie").html(obje.serie);
                $("#text_segundo_nombre").html(obje.otros_nombres);
                $("#text_descripcion").html(obje.descripcion);
                $("#text_fabricante").html(obje.fabricante);
            }
        }
    });
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

function cargar_marcas() {
    mostrar_progress();
    $.post(url, {
        evento: "getAll_rep_auto_marca_registrados",
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

function cargar_lista_marca(arr) {
    $.each(arr, function (i, obj) {
        cargar_iten_marca(obj);
    });
}

function cargar_iten_marca(obj) {
    var html = "<li onclick='seleccionar_marca(" + JSON.stringify(obj) + ")'>";
    html += "   <img src='" + obj.url_foto + "' height='50' width='80' alt=''/>";
    html += "   <span>" + obj.nombre + "</span>";
    html += "</li>";
    $("#lista_marca").append(html);
}

function seleccionar_marca(obj) {
    //$("#id_rep_auto_marca").val(obj.id);
    $("#rep_auto_marca").val(obj.nombre);
    $("#rep_auto_marca").data("id", obj.id);
    $("#img_marca").attr("src", obj.url_foto);
    $("#rep_auto_modelo").val("");
    $("#rep_auto_modelo").data("id", 0);
    $("#lista_anhos").html("");
    $("#lista_modelo").html("");
    $("#lista_version").html("");

    $("#img_modelo").attr("src", "img/Sin_imagen.png");
    $("#rep_auto_version").val("");
    $("#rep_auto_version").data("id", 0);
    $("#rep_auto_anho").val("");
    $("#rep_auto_anho").data("id", 0);
    $(".bd-marca").modal("toggle");
    cargar_modelo(obj.id);
}

function cargar_modelo(id) {
    mostrar_progress();
    $.post(url, {
        evento: "get_rep_auto_modelo_by_id_rep_auto_marca_registrados",
        TokenAcceso: "servi12sis3",
        id_rep_auto_marca: id

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
                $("#lista_modelo").html("");
                cargar_lista_modelo($.parseJSON(obj.resp));

            }
        }
    });
}

function cargar_lista_modelo(arr) {
    $.each(arr, function (i, obj) {
        cargar_iten_modelo(obj);
    });
}

function cargar_iten_modelo(obj) {
    var html = "<li onclick='seleccionar_modelo(" + JSON.stringify(obj) + ")'>";
    html += "   <img src='" + obj.url_foto + "' height='50' width='80' alt=''/>";
    html += "   <span>" + obj.nombre + "</span>";
    html += "</li>";
    $("#lista_modelo").append(html);
}

function seleccionar_modelo(obj) {

    $("#rep_auto_modelo").val(obj.nombre);
    $("#rep_auto_modelo").data("id", obj.id);
    $("#img_modelo").attr("src", obj.url_foto);
    $("#rep_auto_anho").val("");
    $("#rep_auto_anho").data("id", 0);
    $("#lista_anhos").html("");
    $("#lista_version").html("");
    $("#rep_auto_version").val("");
    $("#rep_auto_version").data("id", 0);
    $(".bd-modelo").modal("toggle");

    cargar_anhos($("#rep_auto_marca").data("id"), $("#rep_auto_modelo").data("id"));

}

function cargar_anhos(id_marca, id_modelo) {
    mostrar_progress();
    $.post(url, {
        evento: "getBy_id_marca_and_id_modelo",
        TokenAcceso: "servi12sis3",
        id_marca: id_marca,
        id_modelo: id_modelo

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
                $("#lista_anhos").html("");
                cargar_lista_anhos($.parseJSON(obj.resp));

            }
        }
    });
}

function cargar_lista_anhos(arr) {
    $.each(arr, function (i, obj) {
        cargar_iten_anhos(obj);
    });
}
function cargar_iten_anhos(obj) {
    var html = "<li onclick='seleccionar_anho(" + JSON.stringify(obj) + ")'>";
    html += "   <span>" + obj.anho + "</span>";
    html += "</li>";
    $("#lista_anhos").append(html);
}

function seleccionar_anho(obj) {

    $("#rep_auto_anho").val(obj.anho);
    $("#rep_auto_anho").data("id", obj.id);
    $("#rep_auto_version").val("");
    $("#rep_auto_version").data("id", 0);
    $("#lista_version").html("");
    $(".bd-anhos").modal("toggle");
    cargar_version(obj.id);

}

function cargar_version(id_auto) {
    mostrar_progress();
    $.post(url, {
        evento: "getAll_rep_auto_version_by_id_auto",
        TokenAcceso: "servi12sis3",
        id: id_auto

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
                $("#lista_version").html("");
                cargar_lista_version($.parseJSON(obj.resp));

            }
        }
    });
}

function cargar_lista_version(arr) {
    $.each(arr, function (i, obj) {
        cargar_iten_version(obj);
    });
}

function cargar_iten_version(obj) {
    var html = "<li onclick='seleccionar_version(" + JSON.stringify(obj) + ")'>";
    html += "   <span>" + obj.nombre + "</span>";
    html += "</li>";
    $("#lista_version").append(html);
}

function seleccionar_version(obj) {
    $("#rep_auto_version").val(obj.nombre);
    $("#rep_auto_version").data("id", obj.id_version);
    $(".bd-version").modal("toggle");
}

function Guardar_version() {
    mostrar_progress();
    var id_version = $("#rep_auto_version").data("id");
    var idRep = getQueryVariable("idRep");
    if (id_version > 0 && idRep > 0) {
        $.post(url, {
            evento: "getBy_id_marca_and_id_modelo",
            TokenAcceso: "servi12sis3",
            id_version: id_version,
            id_repuesto: idRep

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
                    $("#lista_anhos").html("");
                    cargar_lista_anhos($.parseJSON(obj.resp));
                }
            }
        });
    } else {
         cerrar_progress();
        alert("Ocurrio algun problema. Disculpe las molestias.");
       
    }

}




