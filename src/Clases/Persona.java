package Clases;

import java.util.regex.Pattern;

public class Persona {

    private long identificacion;
    private String nombre;
    private String apellido;

    public Persona(long identificacion, String nombre, String apellido) throws Exception {
        String strIdentificacion = Long.toString(identificacion);
        if (!(strIdentificacion.length() == 7 || strIdentificacion.length() == 8 || strIdentificacion.length() == 9 || strIdentificacion.length() == 10)) {
            throw new Exception("La identificacion de la persona debe ser de 7 digitos minimo y 10 maximo");
        }
        verificar_Nombre_Apellido(nombre);
        verificar_Nombre_Apellido(apellido);

        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    private void verificar_Nombre_Apellido(String contenidoVerificar) throws Exception {
        if (contenidoVerificar == null || "".equals(contenidoVerificar.trim())) {
            throw new Exception("El nombre o el apellido de la persona no debe estar vacio ni ser solo espacios\n"
                    + "El valor ingresado fue " + contenidoVerificar);
        }
        if (!Pattern.matches("[A-Za-z ]*", contenidoVerificar)) {
            throw new Exception("El nombre o el apellido de la persona solo debe contener letras\n"
                    + "El valor ingresado fue " + contenidoVerificar);
        }
    }

    public long getIdentificacion() {
        return identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setIdentificacion(long identificacion) {
        this.identificacion = identificacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Identificacion: " + identificacion + ", Nombres: " + nombre + ", Apellidos: " + apellido;
    }

}
