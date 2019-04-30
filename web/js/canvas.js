/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var canvas = document.getElementById('myCanvas');
var context = canvas.getContext('2d');
var img = new Image();
var obj_esquema = $.parseJSON(sessionStorage.getItem("esquema"));
img.src = obj_esquema.url_foto;


img.onload = function () {
    context.drawImage(img, 0, 0, canvas.width, canvas.height);
};

var flag2 = 0;
var flag = 0;
var click = null;


canvas.addEventListener("mousedown", function (evt) {
    flag = 0;
    flag2 = 1;
    click = getMousePos(canvas, evt);


}, false);
canvas.addEventListener("mousemove", function (evt) {
    if (flag2 == 1) {
        clearCanvas(context, canvas);
        context.drawImage(img, 0, 0, canvas.width, canvas.height);
        var mousePos = getMousePos(canvas, evt);
        var mousePos2 = click;
        var width = mousePos.x - mousePos2.x;
        var heigh = mousePos.y - mousePos2.y;
        context.lineWidth = "1";
        context.strokeStyle = "black";
        context.rect(mousePos2.x, mousePos2.y, width + 1, heigh + 1);
        context.stroke();


    }
    flag = 1;
}, false);
canvas.addEventListener("mouseup", function (evt) {
    flag2 = 0;
    if (flag === 0) {
//                    console.log("click");
//                    var mousePos = getMousePos(canvas, evt);
//                    var message = 'Mouse click ' + mousePos.x + ',' + mousePos.y;
//                    writeMessage(canvas, message);

    } else if (flag === 1) {
        console.log("drag");
        var mousePos = getMousePos(canvas, evt);
        var mousePos2 = click;
        var mouseFinal = {"x": 0, "y": 0};
        var width = mousePos.x - mousePos2.x;
        var heigh = mousePos.y - mousePos2.y;
        context.lineWidth = "1";
        context.strokeStyle = "black";
        if (width > 0) {

            mouseFinal.x = mousePos2.x;
        } else {
            mouseFinal.x = mousePos.x;
        }

        if (heigh > 0) {
            mouseFinal.y = mousePos2.y;
        } else {
            mouseFinal.y = mousePos.y;
        }
        context.rect(mouseFinal.x, mouseFinal.y, Math.abs(width), Math.abs(heigh));
        context.stroke();
        var croppedCan = crop(canvas, {x: mouseFinal.x, y: mouseFinal.y}, {x: Math.abs(width), y: Math.abs(heigh)});
        var img = document.getElementById("img_url_foto");

        $('#myModal').modal('show');


        img.src = croppedCan.toDataURL();






    }
}, false);
function crop(can, a, b) {
    // get your canvas and a context for it
    var ctx = can.getContext('2d');

    // get the image data you want to keep.
    var imageData = ctx.getImageData(a.x, a.y, b.x, b.y);

    // create a new cavnas same as clipped size and a context
    var newCan = document.createElement('canvas');
    newCan.width = b.x;
    newCan.height = b.y;
    var newCtx = newCan.getContext('2d');

    // put the clipped image on the new canvas.
    newCtx.putImageData(imageData, 0, 0);

    return newCan;
}
function clearCanvas(context, canvas) {
    context.clearRect(0, 0, canvas.width, canvas.height);
    var w = canvas.width;
    canvas.width = 1;
    canvas.width = w;
}


function mostrar() {
    var archivo = document.getElementById("myFile").files[0];
    var reader = new FileReader();
    if (archivo) {
        reader.readAsDataURL(archivo);
        reader.onloadend = function () {
            //   document.getElementById("img").src = reader.result;
            img.src = reader.result;
            img.onload = function () {
                context.drawImage(img, 0, 0, canvas.width, canvas.height);
            };
        }
    }
}
function writeMessage(canvas, message) {
    var context = canvas.getContext('2d');
    context.clearRect(0, 0, canvas.width, canvas.height);
    context.font = '18pt Calibri';
    context.fillStyle = 'black';
    context.fillText(message, 10, 25);
}
function getMousePos(canvas, evt) {
    var rect = canvas.getBoundingClientRect();
    return {
        x: evt.clientX - rect.left,
        y: evt.clientY - rect.top
    };
}



function registrar_rep_partes() {
    mostrar_progress();
    var exito = true;

    var codigo = $("#text_Codigo").val() || null; //id
    var nombre = $("#text_Nombre").val() || null; //id
    var marca = $("#text_Marca").val() || null; //id
    var precio = $("#text_Precio").val() || null; //id
    var descripcion = $("#text_Descripcion").val() || null; //id
    var id_repuesto = getQueryVariable("id");
    //var foto = $("#img_url_foto").val() || null; // foto


    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));

    if (codigo != null && codigo.length > 0) {
        $("#text_Codigo").css("background", "#ffffff");
    } else {
        $("#text_Codigo").css("background", "#df5b5b");
        exito = false;
    }
    if (nombre != null && nombre.length > 0) {
        $("#text_Nombre").css("background", "#ffffff");
    } else {
        $("#text_Nombre").css("background", "#df5b5b");
        exito = false;
    }
    if (precio != null && precio.length > 0) {
        $("#text_Precio").css("background", "#ffffff");
    } else {
        $("#text_Precio").css("background", "#df5b5b");
        exito = false;
    }
    if (descripcion != null && descripcion.length > 0) {
        $("#text_Descripcion").css("background", "#ffffff");
    } else {
        $("#text_Descripcion").css("background", "#df5b5b");
        exito = false;
    }

    /*  if (foto != null) {
     $("#img_url_foto").css("background", "#ffffff");
     } else {
     $(".file-caption").css("background", "#df5b5b");
     exito = false;
     }*/



    if (exito) {
        mostrar_progress();
        $.post(url,
                {
                    evento: "registrar_rep_partes",
                    TokenAcceso: TokenAcceso,
                    id_usr: usr_log.id,
                    var_codigo: codigo,
                    var_nombre: nombre,
                    var_marca: marca,
                    var_precio: precio,
                    var_descripcion: descripcion,
                    var_id_repuesto: id_repuesto
                            //var_url_foto: foto
                }, function (respuesta) {
            cerrar_progress();
            if (respuesta != null) {
                var obj = $.parseJSON(respuesta);
                if (obj.estado != 1) {
                    //alert(obj.mensaje);
                     $('#myModal').modal('show-hidden');
                    $('#myModal').addClass('hidden');
                    Lista_repuesto();
                } else {
                    var resp = obj.resp;
                      $('#myModal').modal('show-hidden');
                    $('#myModal').addClass('hidden');
                    Lista_repuesto();
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
