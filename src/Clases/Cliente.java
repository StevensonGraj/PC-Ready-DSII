package Clases;

public class Cliente extends Persona {

    private Long telefono;
    private String correo;

    public Cliente(long identificacion, String nombre, String apellido, long telefono, String correo) throws Exception {
        super(identificacion, nombre, apellido);
        String strTelefono = Long.toString(telefono);
        if (!(strTelefono.length() == 10)) {
            throw new Exception("El numero telefonico de la persona debe contener 10 digitos");
        }
        
        this.telefono = telefono;
        this.correo = correo;
    }

    public Long getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
