/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function (window, document, JSON) {
    'use strict';
    var url = 'ws://' + window.location.host + '/Webchat1/chat',
            ws = new WebSocket(url),
            usuarios = document.getElementById('usuario'),
            mensajes = document.getElementById('conversacion'),
            boton = document.getElementById('btnEnviar'),
            log = document.getElementById('btnLog'),
            voto = document.getElementById('btnVotar'),
            mensaje = document.getElementById('mensaje'),
            nombre = document.getElementById('nombre');
    ws.onopen = onOpen;
    ws.onclose = onClose;
    ws.onmessage = onMessage;
    boton.addEventListener('click', enviar);
    log.addEventListener('click', enviarUsuario);
    voto.addEventListener('click', votar);
    
    function onOpen() {
        console.log('conectado...');
    }
    function onClose() {
        console.log('Desconectado...');
    }
    function enviar() {
        var msg = {
            codigo: '1234',
            nombre: '',
            mensaje: mensaje.value
        };
        ws.send(JSON.stringify(msg))
    }
    function enviarUsuario() {
        var msg = {
            codigo: '1233',
            nombre: nombre.value,
            mensaje: mensaje.value
        };
        ws.send(JSON.stringify(msg))
    }
    function votar(){
        var msg = {
            codigo: '1235',
            nombre: nombre.value,
            mensaje: mensaje.value
        };
        ws.send(JSON.stringify(msg))
    }
    function onMessage(evt) {
        var obj = JSON.parse(evt.data),
                cod = obj.codigo,
                msg = obj.mensaje,
                nom = obj.nombre;
        if (cod === '1233') {

            usuarios.innerHTML =  msg;
        } else {

            mensajes.innerHTML += '\n' + msg;
        }
    }
})(window, document, JSON)

