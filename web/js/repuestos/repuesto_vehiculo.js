/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var url = "repuestosController";
$(document).ready(function () {
    var idSubCat = getQueryVariable("idSubCat");
    if (idSubCat > 0) {



    } else {
        alert("Ocurrio algun problema. Disculpe las molestias.");
        window.location.href = "index.html";
    }
});

function registrar_repuesto_vehiculo() {
    mostrar_progress();
    var exito = true;
    var nombre = $("#text_nombre").val() || null;
    var serie = $("#text_serie").val() || null;
    var precio = $("#text_precio").val() || null;
    var fabricante = $("#text_fabricante").val() || null;
    var segundo_nombre = $("#text_segundo_nombre").val() || null;
    var descripcion = $("#text_descripcion").val() || null;
    var foto = $("#file-0d").val() || null;
    var id_sub_cat = getQueryVariable("idSubCat");
    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));
    if (nombre != null && nombre.length > 0) {
        $("#text_nombre").css("background", "#ffffff");
    } else {
        $("#text_nombre").css("background", "#df5b5b");
        exito = false;
    }
    if (serie != null && serie.length > 0) {
        $("#text_serie").css("background", "#ffffff");
    } else {
        $("#text_serie").css("background", "#df5b5b");
        exito = false;
    }
    if (precio != null && precio.length > 0) {
        $("#text_precio").css("background", "#ffffff");
    } else {
        $("#text_precio").css("background", "#df5b5b");
        exito = false;
    }
    if (fabricante != null && fabricante.length > 0) {
        $("#text_fabricante").css("background", "#ffffff");
    } else {
        $("#text_fabricante").css("background", "#df5b5b");
        exito = false;
    }
    if (segundo_nombre != null && segundo_nombre.length > 0) {
        $("#text_segundo_nombre").css("background", "#ffffff");
    } else {
        $("#text_segundo_nombre").css("background", "#df5b5b");
        exito = false;
    }
    if (descripcion != null && descripcion.length > 0) {
        $("#text_descripcion").css("background", "#ffffff");
    } else {
        $("#text_descripcion").css("background", "#df5b5b");
        exito = false;
    }
    if (foto != null && nombre.length > 0) {
        $("#file-0d").css("background", "#ffffff");
    } else {
        $(".file-caption").css("background", "#df5b5b");
        exito = false;
    }
    if (id_sub_cat > 0) {
        //exito
        $("input[name=id_sub_categoria]").val(id_sub_cat);
    } else {
        alert("Ocurrio algun problema. Disculpe las molestias.");
        window.location.href = "index.html";
    }
    if (exito) {
        mostrar_progress();
        var formData = new FormData($("#submitform")[0]);
        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            contentType: false,
            cache: false,
            processData: false,
            success: function (resp)
            {
                cerrar_progress();
                if (resp != null) {
                    var obj = $.parseJSON(resp);
                    if (obj.estado != 1) {
                        alert(obj.mensaje);
                    } else {
                        var obje = $.parseJSON(obj.resp);
                        window.location.href = 'art_repuesto_vehiculo_perfil.html?id=' + obje.id + "&idSubCat=" + id_sub_cat;
                    }
                }
            }
        });
    } else {
        cerrar_progress();
    }
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