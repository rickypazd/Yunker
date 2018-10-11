/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var url = "admin/adminController";
$(document).ready(function () {
    var id = getQueryVariable("id");
    $("#editar_vehiculo").attr("href","editarVehiculo.html?id="+id);
    mostrar_progress();
    $.post(url, {evento: "get_vehiculo_id", id: id}, function (resp) {
        cerrar_progress();
        if (resp == "falso") {
            alert("Error al buscar el vehiculo.");
        }else{
            var obj = JSON.parse(resp);
             var foto_perfil = obj.foto_perfil || "";
            if (foto_perfil.length > 0) {
                $("#foto_perfil").attr("src", foto_perfil);
            }
            $("#v_placa").html(obj.placa);
            $("#v_marca").html(obj.marca);
            $("#v_modelo").html(obj.modelo);
            $("#v_ano").html(obj.ano);
            $("#v_color").html(obj.color);
            $("#v_puertas").html(obj.n_puertas);
            $("#v_chasis").html(obj.chasis);
            $("#v_motor").html(obj.motor);
        }

    });

});
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
