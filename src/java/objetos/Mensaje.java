/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

/**
 *
 * @author ACER
 */
//convertir esta clase en un objeto json para eso encoder y decoder
public class Mensaje {
    private String codigo;
    private String nombre;
    private String mensaje;
    
    public Mensaje(){
    
    }

    public String getNombre() {
        return nombre;
    }

    public String getMensaje() {
        return mensaje;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    } 

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}