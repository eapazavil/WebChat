/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import Objetos.DecoderMensaje;
import Objetos.EncoderMensaje;
import Objetos.Mensaje;
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

    private final static List<Session> conectados = new ArrayList<>();
    //private HiloVotar hilo;

    private static boolean estaVotando = false;
    private final static List<Integer> votos = new ArrayList<>();

    @OnOpen
    public void inicio(Session sesion) {
        System.out.println(sesion);
        conectados.add(sesion);

    }

    @OnClose
    public void salir(Session sesion) {
        conectados.remove(sesion);

    }

    @OnMessage
    public void mensaje(Mensaje mensaje, Session sesion) throws IOException, EncodeException {
        String user = (String) sesion.getUserProperties().get("user");
        String codigo = mensaje.getCodigo();

        switch (codigo) {
            case "1233":
                if (user == null && !estaVotando) {
                    isSesion(sesion, mensaje.getNombre());

                    mensaje.setMensaje(listUsers());
                    for (Session conectado : conectados) {
                        conectado.getBasicRemote().sendObject(mensaje);
                    }

                }
                break;
            case "1234":
                if (user != null && !estaVotando) {
                    mensaje.setNombre(user);
                    mensaje.setMensaje(user + ": " + mensaje.getMensaje());
                    for (Session sesion1 : conectados) {
                        sesion1.getBasicRemote().sendObject(mensaje);
                    }
                }
                break;
            case "1235":
                if (user != null && !estaVotando) {
                    startVoto();
                    mensaje.setNombre(user);
                    mensaje.setMensaje(user + " esta proponiendo votacion: " + mensaje.getMensaje());
                    for (Session sesion1 : conectados) {
                        sesion1.getBasicRemote().sendObject(mensaje);
                    }

                }
                if (user != null && estaVotando && sesion.getUserProperties().get("estado").equals("0")) {
                    //
                    try {
                        System.out.println(mensaje.getMensaje());
                        int vt = Integer.parseInt(mensaje.getMensaje());
                        votar(sesion, vt);

                        verificar();
                        
                        mensaje.setMensaje("exito");
                        sesion.getBasicRemote().sendObject(mensaje);
                        if (!estaVotando) {
                            mensaje.setMensaje("La propuesta ha sido "+ verificar());
                            for (Session conectado : conectados) {
                                conectado.getUserProperties().replace("estado", "0");
                                conectado.getBasicRemote().sendObject(mensaje);
                            }
                        }

                    } catch (Exception e) {
                        mensaje.setMensaje("Enviar Valor 1(a favor) o 0(en contra)");
                        sesion.getBasicRemote().sendObject(mensaje);
                    }

                }
                break;

            default:

                break;

        }

    }

    public void isSesion(Session sesion, String usuario) {

        sesion.getUserProperties().put("user", usuario);
        sesion.getUserProperties().put("estado", "0");
    }

    public String listUsers() {
        String users = "";

        for (Session conectado : conectados) {
            users += "<p>" + conectado.getUserProperties().get("user") + "</p>";
        }
        return users;
    }

    public void startVoto() {

        estaVotando = true;

    }

    private void votar(Session sc, int vt) {
        sc.getUserProperties().replace("estado", "1");
        votos.add(vt);

    }

    private String verificar() {
        String resultado = "";
        if (conectados.size() == votos.size()) {
            resultado = calcularResultado();
            estaVotando = false;
        }
        System.out.println(estaVotando);    
        return resultado;
        
    }

    private String calcularResultado() {
        int favor = 0;
        int contra = 0;
        for (Integer votacion : votos) {
            if (votacion == 1) {
                favor++;
            }
            if (votacion == 0) {
                contra++;
            }
        }
        if (favor > contra) {
            return " aceptada";
        }if(favor == contra){
            return "rechazada";
        }else {
            return "rechazada";
        }

    }
}
