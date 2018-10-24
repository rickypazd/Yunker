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
});

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
        $.post(url, {evento: "registrar_art_departamento", TokenAcceso : TokenAcceso , id_usr : usr_log.id ,
            nombre: nombre}, function (respuesta) {
           cerrar_progress();
           if(respuesta!=null){
               var obj= $.parseJSON(respuesta);
               if(obj.estado!=1){
                   //error
                   alert(obj.mensaje);
               }else{
                   //exito
                   var resp=obj.resp;
                       alert(resp);
               }
           }
        });
    }
}

function registrar_categoria(){
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
        $.post(url, {evento: "registrar_categoria", TokenAcceso : TokenAcceso , id_usr : usr_log.id ,
            nombre: nombre}, function (respuesta) {
           cerrar_progress();
           if(respuesta!=null){
               var obj= $.parseJSON(respuesta);
               if(obj.estado!=1){
                   //error
                   alert(obj.mensaje);
               }else{
                   //exito
                   var resp=obj.resp;
                       alert(resp);
               }
           }
        });
    }
}

function registrar_marca() {
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
        $.post(url, {evento: "registrar_marca", TokenAcceso : TokenAcceso , id_usr : usr_log.id ,
            nombre: nombre}, function (respuesta) {
           cerrar_progress();
           if(respuesta != null){
               var obj= $.parseJSON(respuesta);
               if(obj.estado != 1){
                   //error
                   alert(obj.mensaje);
               }else{
                   //exito
                   var resp=obj.resp;
                       alert(resp);
               }
           }
        });
    }
}

function registrar_articulo() {
    mostrar_progress();
    var exito = true;
    var clave = $("#art_clave").val() || null;
    var nombre = $("#art_nombre").val() || null;
    var descripcion = $("#art_descripcion").val() || null;
    var departamento = $("#art_departamento").val() || null;
    var categoria = $("#art_categoria").val() || null;
    var marca = $("#art_marca").val() || null;
    var unidad_compra = $("#art_unidad_compra").val() || null;
    var unidad_venta = $("#art_unidad_venta").val() || null;
    var factor = $("#art_factor").val() || null;
    var precio_compra_ref = $("#art_precio_compra").val() || null;
    var precio_venta_ref = $("#art_precio_venta").val() || null;
    var margen = $("#art_margen").val() || null;    
    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));   
    if (nombre != null && nombre.length > 0) {
        $("#dep_nombre").css("background", "#ffffff");
    } else {
        $("#dep_nombre").css("background", "#00ff00");
        exito = false;
    }
    if (exito) {
        $.post(url, {evento: "registrar_articulo",  TokenAcceso : TokenAcceso , id_usr : usr_log.id ,
        clave : clave , nombre: nombre , descripcion: descripcion,
        departamento:departamento,categoria:categoria,marca:marca , unidad_compra : unidad_compra , 
        unidad_venta : unidad_venta , factor : factor , precio_compra_red :precio_compra_ref ,
        precio_venta_ref : precio_venta_ref , margen : margen }, function (respuesta) {
           cerrar_progress();
           if(respuesta != null){
               var obj= $.parseJSON(respuesta);
               if(obj.estado != 1){
                   //error
                    alert(obj.mensaje);
               }else{
                   //exito                    
                    var resp=obj.resp;
                    alert(resp);
               }
           }
        });
    }
}

