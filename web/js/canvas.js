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
        var img = document.getElementById("fotoEsquema");

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

function registrar_esquema_partes() {
    mostrar_progress();
    var exito = true;

    var foto = $("#fotoEsquema").val() || null; // foto

    var codigo = $("#text_Codigo").val() || null; //id
    var nombre = $("#text_Nombre").val() || null; //id
    var marca = $("#text_Marca").val() || null; //id
    var precio = $("#text_Precio").val() || null; //id
    var descripcion = $("#text_Descripcion").val() || null; //id

    var idRep = getQueryVariable("id"); //id respuesto
    $("#id_repuesto_esquema").val(parseInt(idRep));



    var TokenAcceso = "servi12sis3";
    var usr_log = $.parseJSON(sessionStorage.getItem("usr_log"));

    if (foto != null) {
        $("#file-0d").css("background", "#ffffff");
    } else {
        $(".file-caption").css("background", "#df5b5b");
        exito = false;
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
            success: function (data)
            {
                //despues de cargar
                cerrar_progress();
                alert(data);
            }
        });
    } else {
        cerrar_progress();
    }
}

function ok_crear() {
    var acepted = true;

    var nombre = $("#cu_nombre").val() || null;
    var apellido_pa = $("#cu_ap_pa").val() || null;
    var apellido_ma = $("#cu_ap_ma").val() || null;
    var tipo = rol_id;
    var correo = $("#cu_correo").val() || null;
    var sexo = $("#cu_sexo").val() || null;
    var fecha_nac = $("#cu_fecha_nac").val() || null;
    var usuario = $("#cu_usuario").val() || null;
    var pass = $("#cu_contrasenha").val() || null;
    var rep_pass = $("#cu_repcontrasenha").val() || null;


    var foto = $("#fotoEsquema").val() || null;
    var codigo = $("#text_Codigo").val() || null;
    var nombre = $("#text_Nombre").val() || null;
    var marca = $("#text_Marca").val() || null;
    var precio = $("#text_Precio").val() || null;
    var descripcion = $("#text_Descripcion").val() || null;

    if (codigo == null) {
        $("#text_Codigo").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#text_Codigo").css("background-color", "#ffffff");
    }
    if (nombre == null) {
        $("#text_Nombre").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#text_Nombre").css("background-color", "#ffffff");
    }
    if (marca == null) {
        $("#text_Marca").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#text_Marca").css("background-color", "#ffffff");
    }
    if (precio == null) {
        $("#text_Precio").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#text_Precio").css("background-color", "#ffffff");
    }
    if (descripcion == null) {
        $("#text_Descripcion").css("background-color", "#f386ab");
        acepted = false;
    } else {
        $("#text_Descripcion").css("background-color", "#ffffff");
    }

    if (acepted) {
        var passmd5 = md5(pass);
        $.post(url, {evento: "registrar_esquema_partes",

            var_codigo: codigo,
            var_nombre: nombre,
            var_marca: marca,
            var_precio: precio,
            var_descripcion: descripcion
            
        }, function (resp) {
            if (resp == "false" || resp.length <= 0) {
                alert("Ocurrio un herror al registrar Usuario");
            } else {
                alert("Usuario registrado con exito");
            }
        });
    }

}


