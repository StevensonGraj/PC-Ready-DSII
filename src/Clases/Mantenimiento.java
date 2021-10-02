package Clases;

import java.time.LocalDate;
import java.util.ArrayList;

public class Mantenimiento {

    private ArrayList<Consumo> consumos = new ArrayList<>();
    private ArrayList<Servicio> servicios = new ArrayList<>();
    private Computador computador;
    private Persona tecnico;
    private LocalDate fecha;
    private int costoConsumos = 0;
    private int costoServicios = 0;
    private int CostoTotalMant;

    public Mantenimiento(Computador computador, Persona tecnico) throws Exception {
        if (computador == null) {
            throw new Exception("El mantenimiento debe poseer un computador");
        }

        this.computador = computador;
        this.tecnico = tecnico;
        this.fecha = LocalDate.now();
    }

    public ArrayList<Consumo> getConsumos() {
        return consumos;
    }

    public void setConsumos(ArrayList<Consumo> consumos) {
        this.consumos = consumos;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Computador getComputador() {
        return computador;
    }

    public void setComputador(Computador computador) {
        this.computador = computador;
    }

    public Persona getTecnico() {
        return tecnico;
    }

    public void setTecnico(Persona tecnico) {
        this.tecnico = tecnico;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void addServicio(Servicio ser) {
        this.servicios.add(ser);
    }

    public void addConsumo(Consumo cons) {
        this.consumos.add(cons);
    }

    public String Servicios() {

        String servicio = "";

        for (Servicio ser : this.servicios) {
            servicio += ser.toString() + ", ";
        }

        return servicio;
    }

    public int getCostoConsumos() {

        int a = 0;

        for (Consumo con : this.consumos) {
            a += con.getCostoTotalCons();
        }
        return costoConsumos = a;
    }

    public int getCostoServicios() {

        int a = 0;

        for (Servicio ser : this.servicios) {
            a += ser.getCosto();
        }
        return costoServicios = a;
    }

    public int getCostoTotalMant() {
        return CostoTotalMant = getCostoConsumos() + getCostoServicios();
    }

    @Override
    public String toString() {

        if (tecnico == null) {
            return "Computador: " + computador.getMarca() + "  " + computador.getSerialEquipo()+ " Servicio: " + Servicios() + " Tecnico: Sin Asignar";
        }
        return "Computador: " + computador.getMarca() + "  " + computador.getSerialEquipo()+ " Servicio: " + Servicios() + " Tecnico: " + tecnico.getNombre();
    }
}
