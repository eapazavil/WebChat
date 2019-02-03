/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import objetos.DecoderMensaje;
import objetos.EncoderMensaje;
import objetos.Mensaje;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author ACER
 */

@ServerEndpoint(value = "/chat", encoders = {EncoderMensaje.class}, decoders = {DecoderMensaje.class})
public class MiChat {
    private static final List<Session> conectados = new ArrayList<>();
    
    @OnOpen
    public void inicio(Session sesion){
        conectados.add(sesion);
    }
    
    @OnClose
    public void salir(Session sesion){
        conectados.remove(sesion);
    }
    @OnMessage
    public void mensaje(Mensaje mensaje) throws IOException, EncodeException {
    System.out.println("aqui");
    String codigo = mensaje.getCodigo();
    switch (codigo) {
        case "1234":
            System.out.println("aqui");
            for (Session sesion : conectados) {
                sesion.getBasicRemote().sendObject(mensaje);
            }
            break;
        default :
    System.out.println("error");
    break;
    }

    }
/*    @OnMessage
    public void mensaje(Mensaje mensaje) throws IOException, EncodeException{
        for (Session sesion : conectados) {
            sesion.getBasicRemote().sendObject(mensaje);
        }
    }*/
}
