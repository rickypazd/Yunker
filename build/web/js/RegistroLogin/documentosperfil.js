/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var url = 'admin/adminController';
var id_usr;
var creador = false;
$(document).ready(function () {
    id_usr = getQueryVariable("id");

    $.post(url, {evento: "get_tipos_de_documentos"}, function (resp) {

        var arr = $.parseJSON(resp);
        mostrar_progress();
        $.post(url, {evento: "get_documento_faltante", id: id_usr}, function (respues) {
            cerrar_progress();
            var doc_faltantes = $.parseJSON(respues);
            var html = "";
            var html2 = "";

            $.each(arr, function (i, obj) {
                html += "<option value='" + obj.id + "' data-needfecha='" + obj.need_fecha + "'>" + obj.nombre + "</option>";
                html2 += "<div class='row'> <h2 class='col-md-4'>" + obj.nombre + "</h2>";
                if (respues.includes('"id":' + obj.id)) {
                    html2 += "<div class='alert alert-danger col-md-4'>No se encontro " + obj.nombre + " vigente.";
                    html2 += "</div>";

                }
                html2 += "</div><div class='row '/>";
                html2 += "<button type='button' class='btn btn-outline-info' data-toggle='collapse' data-target='#demo" + obj.id + "'>Ver " + obj.nombre + "</button>";
                html2 += " <div id='demo" + obj.id + "' class='collapse in donwloadpanel'>";
                html2 += " </div></div><hr/>";
            });
            $("#ctc_tipo").append(html);
            $("#conten_docus").append(html2);
            get_arch();
        });
    });

});

function get_arch() {
    mostrar_progress();
    $.post(url, {evento: "get_documentos_id", id: id_usr}, function (resp) {
        cerrar_progress();
        var arra = $.parseJSON(resp);
        var html = "";
        var nombre_ar;
        $.each(arra, function (i, obj) {
            html = "";
            nombre_ar = obj.src;
            ;
            var src = "";
            var extencion_arr = nombre_ar.split('.');
            var nombre_arr = nombre_ar.split('/');
            var nomnre = nombre_arr[nombre_arr.length - 1];
            var img = false;
            var extencion = extencion_arr[extencion_arr.length - 1];
            switch (extencion) {
                //word
                case "rar":
                    src = "img//rar.png";
                    break;
                case "doc":
                    src = "img//word.png";
                    break;
                case "docx":
                    src = "img//word.png";
                    break;
                case "dotx":
                    src = "img//word.png";
                    break;
                    //P.point
                case "pptx":
                    src = "img//powerpint.png";
                    break;
                case "ppt":
                    src = "img//powerpint.png";
                    break;
                case "ppsx":
                    src = "img//powerpint.png";
                    break;
                    //exel
                case "xltx":
                    src = "img//excel.png";
                    break;
                case "xls":
                    src = "img//excel.png";
                    break;
                case "xlsx":
                    src = "img//excel.png";
                    break;
                    //pdf
                case "pdf":
                    src = "img//pdf1.png";
                    break;
                    //txt
                case "txt":
                    src = "img//txt.png";
                    break;
                    //imagenes
                case "png":
                    src = "img//png.png";
                    img = true;
                    break;
                case "bmp":
                    src = "img//divx.png";
                    img = true;
                    break;
                case "jpg":
                    src = "img//jpg.png";
                    img = true;
                    break;
                case "jpeg":
                    src = "img//jpg.png";
                    img = true;
                    break;
                case "JPG":
                    src = "img//jpg.png";
                    img = true;
                    break;
                case "gif":
                    src = "img//divx.png";
                    img = true;
                    break;
                default :
                    src = "img//txt.png";

            }
            html += "<div class='col-md-2 archivo_para_descargar flex-column' style='display: inline-block;'>";
            html += " <img id=''  src='" + src + "' width='60px' alt='DOWNLOAD'>";
            html += " </br>";
            var fecha = obj.fecha_caducidad.substring(0, 10);
            if (obj.need_fecha) {
                html += " <span>" + fecha + "</span>";
                html += " </br>";
            }
            html += "<button type='button' class='d-inline-flex p-2 btn btn-primary' style='margin:5px;' onclick=\"descargar_archivo('" + nombre_ar + "'," + obj.id + ");\">Descargar</button>";
            html += " </br>";

            if (img) {
                html += "<button type='button' class='btn btn-primary' onclick=\"ver_foto('" + nombre_ar + "'," + obj.id + ");\">Ver Foto</button>";
                html += " </br>";
            }
            html += "</div>";
            $("#demo" + obj.id_tipo).append(html);
        });
    });
}


function descargar_archivo(nombre, id) {
    $("input[name=id_caso]").val(id);
    $("input[name=nombre_arc]").val(nombre);
    $("#btn_sbm").click();

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

function ver_foto(nombre, id) {
    $("#imagen").attr("src", "" );
    $("#nombre_docu").html("");
    $("#nombre").html("");
   
    $("#fecha").html("");

    mostrar_progress();
    $.post(url, {evento: 'ver_foto', id: id}, function (resp) {
        cerrar_progress();
        var obj = $.parseJSON(resp);
        $("#imagen").attr("src", "data:image/jpeg;base64," + obj.b64);
        $("#nombre_docu").html(obj.nombredocu);
        $("#nombre").html(obj.nombre);
        if (obj.need_fecha)
            var fecha = obj.fecha_caducidad.substring(0, 10);
        $("#fecha").html(fecha);

    });

    $('#modal').modal('show');
}