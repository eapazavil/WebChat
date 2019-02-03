/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function(window, document, JSON){
    'use strict';
    var url = 'ws://'+window.location.host+'/Webchat/chat',
        ws  = new WebSocket(url),
        mensajes = document.getElementById('conversacion'),
        boton = document.getElementById('btnEnviar'),
        
        nombre = document.getElementById('nombre'),
        mensaje = document.getElementById('mensaje');
        
    ws.onopen = onOpen;
    ws.onclose = onClose;
    ws.onmessage = onMessage;
    boton.addEventListener('click', enviar);
    function onOpen(){
        console.log('conectado...');
    }
    function onClose(){
        console.log('Desconectado...');
    }
    function enviar(){
        var msg = {
            codigo: '1234',
            nombre: nombre.value,
            mensaje: mensaje.value 
        };
        ws.send(JSON.stringify(msg))
    }
    function onMessage(evt){
        var obj = JSON.parse(evt.data),
            msg = obj.nombre+ ' dice: '+obj.mensaje ;
        mensajes.innerHTML += '<br/>'+msg;
    }
})(window, document, JSON)


