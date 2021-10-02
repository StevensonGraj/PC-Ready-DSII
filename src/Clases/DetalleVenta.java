package Clases;

import java.util.regex.Pattern;

public class DetalleVenta {

    private int cantidad;
    private float costoTotal;
    private Producto producto;

    public DetalleVenta(int cantidad, Producto producto) throws Exception {
        if (producto == null) {
            Exception error = new Exception("Debe asignar un producto");
            throw error;
        }
        if (cantidad == 0) {
            Exception error = new Exception("Debe asignar la cantidad a comprar del producto");
            throw error;
        }
        String strCantidad = Integer.toString(cantidad);
        if (!Pattern.matches("[0-9]*", strCantidad)) {
            throw new Exception("La cantidad solo debe contener numeros");
        }

        this.cantidad = cantidad;
        this.costoTotal = cantidad * producto.getCosto();
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public float getCostoTotal() {
        return costoTotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCostoTotal(float costoTotal) {
        this.costoTotal = costoTotal;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return producto + "  Cantidad: " + cantidad + "  CostoTotal: " + costoTotal;
    }
}
