
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
        $.post(url, {evento: "registrar_articulo", unidad_compra: unidad_compra , unidad_venta: unidad_venta,        
        factor : factor , precio_compra :precio_compra ,
        precio_venta : precio_venta , margen : margen , cantidad : cantidad , forma_pago : forma_pago , fecha : fecha}, function (respuesta) {
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

