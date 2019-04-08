/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var canvas = document.getElementById('myCanvas');
var context = canvas.getContext('2d');
var img = new Image();

img.src = "img/esquema.jpg";

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
        var croppedCan = crop(canvas, {x: mouseFinal.x, y: mouseFinal.y}, {x:  Math.abs(width), y:  Math.abs(heigh)});
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
    var archivo = document.getElementById("file").files[0];
    var reader = new FileReader();
    if (file) {
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
