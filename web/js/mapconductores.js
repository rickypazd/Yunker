/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var url = "admin/adminController";
var map;
var markers = [];
function initMap() {
    var perm = $();
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -13.38, lng: -77.597},
        zoom: 8
    });
    hilo();
}

function hilo() {
    $.post(url, {"evento": "get_pos_vehiculos"}, function (resp) {
        json = $.parseJSON(resp);
        deleteMarkers();
        $.each(json, function (i, obj) {
            addMarker(obj);
           
        });
            showMarkers();
            
    });
    setTimeout(hilo, 2000);
}
function addMarker(obj) {
    var marker = new google.maps.Marker({
        position: {lat: obj.lat, lng: obj.lon},
        map: map,
        label:obj.placa
    });
 
    markers.push(marker);
}
function setMapOnAll(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}
function clearMarkers() {
    setMapOnAll(null);
}

// Shows any markers currently in the array.
function showMarkers() {
    setMapOnAll(map);
}

// Deletes all markers in the array by removing references to them.
function deleteMarkers() {
    clearMarkers();
    markers = [];
}