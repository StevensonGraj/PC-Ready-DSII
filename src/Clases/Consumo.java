package Clases;

public class Consumo {

    private int cantidad;
    private Servicio servicio;
    private Producto producto;
    private int costoTotalCons;

    public Consumo(int cantidad, Servicio servicio, Producto producto) throws Exception {
        if (cantidad <= 0) {
            throw new Exception("La cantidad del consumo no puede ser menor o igual a cero");
        }
        if (servicio == null) {
            throw new Exception("El servicio del consumo no debe estar vacio");
        }
        if (producto == null) {
            throw new Exception("El producto del consumo no debe estar vacio");
        }

        this.cantidad = cantidad;
        this.servicio = servicio;
        this.producto = producto;
        this.costoTotalCons = (int) (cantidad * producto.getCosto());
    }

    public int getCantidad() {
        return cantidad;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCostoTotalCons() {
        return costoTotalCons;
    }

    public void setCostoTotalCons(int costoTotalCons) {
        this.costoTotalCons = costoTotalCons;
    }

    @Override
    public String toString() {
        return producto + "  Cantidad : " + cantidad;
    }
}
