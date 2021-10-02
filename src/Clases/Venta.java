package Clases;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Venta {

    private ArrayList<DetalleVenta> detalleVentas = new ArrayList<>();
    private Persona cliente;
    private LocalDateTime fecha;
    private int CostoVentaTotal = 0;

    public Venta(Persona cliente) {
        this.cliente = cliente;
        this.fecha = LocalDateTime.now();
    }

    public ArrayList<DetalleVenta> getDetalleVentas() {
        return detalleVentas;
    }

    public void setDetalleVentas(ArrayList<DetalleVenta> detalleVentas) {
        this.detalleVentas = detalleVentas;
    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getCostoVentaTotal() {

        int a = 0;

        for (DetalleVenta dV : this.detalleVentas) {
            a += dV.getCostoTotal();
        }

        return CostoVentaTotal = a;
    }

    public void setCostoVentaTotal(int CostoVentaTotal) {
        this.CostoVentaTotal = CostoVentaTotal;
    }

    public void addDetalleVenta(DetalleVenta dV) {
        this.detalleVentas.add(dV);
    }
}
