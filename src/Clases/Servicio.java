package Clases;

public class Servicio {

    private int codigo;
    private String nombre;
    private float costo;

    public Servicio(int codigo, String nombre, float costo) throws Exception {
        if (codigo <= 0) {
            throw new Exception("El codigo del servicio no debe ser menor o igual a cero");
        }
        if (nombre == null || "".equals(nombre.trim())) {
            throw new Exception("El nombre del servicio no debe estar vacio ni ser solo espacios");
        }
        if (costo <= 0) {
            throw new Exception("El costo del servicio no debe ser menor o igual a cero");
        }

        this.codigo = codigo;
        this.nombre = nombre;
        this.costo = costo;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public float getCosto() {
        return costo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return codigo + "  " + nombre + "  costo: " + costo;
    }
}
