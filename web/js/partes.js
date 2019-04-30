

$(document).ready(function () {
    var id_repuesto = getQueryVariable("id");

    //alert("list_partes "+id_repuesto);
    Lista_repuesto();
});



function Lista_repuesto() {
    mostrar_progress();
    //var busq = $("#in_buscar").val() || "";
    var id_repuesto = getQueryVariable("id");
    $.post(url, {evento: "getAll_rep_partes_by_id_repuesto", var_id_repuesto: id_repuesto, TokenAcceso: "servi12sis3"}, function (resp) {
        cerrar_progress();
        if (resp != null) {
            var obj = $.parseJSON(resp);
            if (obj.estado != "1") {
                alert(obj.mensaje);
            } else {
                var arr = $.parseJSON(obj.resp);
                //alert(obj.resp);
                $("#resultados_partes").html("(" + arr.length + ")");
                $("#list_partes").html(" ");
                $.each(arr, function (i, obj) {
                    var html = "<a href='javaScript:void(0)' onclick='ver_repuesto(this)' data-obj='" + JSON.stringify(obj) + "' class='list-group-item list-group-item-action flex-column align-items-start'>";
                    html += "               <div class='row iten_repuesto_row'>";
                    html += "                     <div class='col-2'>";
                    html += "                         <h6>" + obj.codigo + "</h6>";
                    html += "                      </div>";
                    html += "                     <div class='col-2'>";
                    html += "                         <h6>" + obj.nombre + "</h6>";
                    html += "                     </div>";
                    html += "                     <div class='col-2'>";
                    html += "                         <h6>" + obj.marca + "</h6>";
                    html += "                     </div>";
                    html += "                     <div class='col-2'>";
                    html += "                         <h6>" + obj.precio + "</h6>";
                    html += "                     </div>";
                    html += "                     <div class='col-4'>";
                    html += "                         <h6>" + obj.descripcion + "</h6>";
                    html += "                     </div>";
                    html += "                 </div>";
                    html += "            </a>";
                    $("#list_partes").append(html);
                });

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
