package Clases;

public class Tecnico extends Persona {

    public static enum Estado {
        Si, No
    }

    private Estado Estado;

    public Tecnico(long identificacion, String nombre, String apellido) throws Exception {
        super(identificacion, nombre, apellido);

    }

    public Estado getEstado() {
        return Estado;
    }

    public void setEstado(Estado Estado) {
        this.Estado = Estado;
    }

}
