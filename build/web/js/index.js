/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var url = "admin/adminController";
$(document).ready(function(){
 //   $.post(url,{evento:"get_dist_carrera",id:"91"},function(resp){
 //       alert(resp);
 //   });
});

function calc_dist(){
    var id = $("#id_car").val();
        $.post(url,{evento:"get_dist_carrera",id:id},function(resp){
        alert(resp);
         });
}