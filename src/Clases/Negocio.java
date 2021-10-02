package Clases;

import java.util.ArrayList;
import java.util.List;

public class Negocio {

    private long nit;
    private String nombre;
    private ArrayList<Servicio> servicios;
    private ArrayList<Producto> productos;
    private ArrayList<Venta> ventas;
    private ArrayList<Tecnico> tecnicos;
    private ArrayList<Cliente> clientes;
    private ArrayList<Mantenimiento> mantenimientosR;
    private ArrayList<Mantenimiento> mantenimientosP;
    private ArrayList<Computador> computadores;

    public Negocio(long nit, String nombre) {
        this.nit = nit;
        this.nombre = nombre;
        this.servicios = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.ventas = new ArrayList<>();
        this.tecnicos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.mantenimientosR = new ArrayList<>();
        this.mantenimientosP = new ArrayList<>();
        this.computadores = new ArrayList<>();

    }

    public long getNit() {
        return nit;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public ArrayList<Tecnico> getTecnicos() {
        return tecnicos;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setNit(long nit) {
        this.nit = nit;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public void setVentas(ArrayList<Venta> ventas) {
        this.ventas = ventas;
    }

    public void setTecnicos(ArrayList<Tecnico> tecnicos) {
        this.tecnicos = tecnicos;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Mantenimiento> getMantenimientosR() {
        return mantenimientosR;
    }

    public void setMantenimientosR(ArrayList<Mantenimiento> mantenimientosR) {
        this.mantenimientosR = mantenimientosR;
    }

    public ArrayList<Mantenimiento> getMantenimientosP() {
        return mantenimientosP;
    }

    public void setMantenimientosP(ArrayList<Mantenimiento> mantenimientosP) {
        this.mantenimientosP = mantenimientosP;
    }

    public ArrayList<Computador> getComputadores() {
        return computadores;
    }

    public void setComputadores(ArrayList<Computador> computadores) {
        this.computadores = computadores;
    }

    public void setMantPend(Mantenimiento mant) {
        this.mantenimientosP.remove(mant);
        this.mantenimientosP.add(mant);
    }

    public void setTecnico(Tecnico tec) {
        this.tecnicos.remove(tec);
        this.tecnicos.add(tec);
    }

    public void addPc(Computador pc) throws Exception {
        if (computadores.contains(pc)) {
            throw new Exception("El computador ya se encuentra registrado");
        }
        this.computadores.add(pc);

    }

    public void addServicio(Servicio servicio) {
        this.servicios.add(servicio);
    }

    public void addCliente(Cliente cl) throws Exception {
        if (clientes.contains(cl)) {
            throw new Exception("El cliente ya se encuentra registrado");
        }
        this.clientes.add(cl);
    }

    public void addProducto(Producto pd) throws Exception {
        if (productos.contains(pd)) {
            throw new Exception("El producto ya se encuentra registrado");
        }
        this.productos.add(pd);
    }

    public void addTecnico(Tecnico tec) throws Exception {
        if (tecnicos.contains(tec)) {
            throw new Exception("El tecnico ya se encuentra registrado");
        }
        this.tecnicos.add(tec);
    }

    public void addMantenimientoP(Mantenimiento mant) {
        this.mantenimientosP.add(mant);
    }
    
     public void addMantenimientoR(Mantenimiento mant) {
        this.mantenimientosR.add(mant);
    }
     
     public void addVenta(Venta v) {
        this.ventas.add(v);
    }

    public List MantSinTecnicos() {

        List list = new ArrayList();

        for (Mantenimiento mant : this.mantenimientosP) {
            if (mant.getTecnico() == null) {
                list.add(mant);
            }
        }
        return list;
    }

    public List TecnicosDisp() {

        ArrayList listTD = new ArrayList();

        for (Tecnico tec : this.tecnicos) {
            if (tec.getEstado().equals(tec.getEstado().Si)) {
                listTD.add(tec);
            }
        }
        return listTD;
    }

    public Computador FindPc(String Serial) throws Exception {
        for (Computador comp : this.computadores) {
            if (comp.getSerialEquipo().equals(Serial)) {
                return comp;
            }
        }
        throw new Exception("El computador no se encuentra registrado en la base"
                + "de datos");
    }

    public Mantenimiento findMantPend(String serial) throws Exception {

        for (Mantenimiento mant : this.mantenimientosP) {
            if (mant.getComputador().getSerialEquipo().toLowerCase().equals(serial.toLowerCase())) {
                return mant;
            }
        }
        throw new Exception("No se encontro el mantenimiento con el serial del equipo ingresada");
    }

    public Producto findProducto(int codigo) throws Exception {

        for (Producto prod : this.productos) {
            if (prod.getCodigo() == codigo) {
                return prod;
            }
        }
        throw new Exception("No se encontro el producto con el codigo ingresado");
    }
    
    public Cliente findCliente(long ident) throws Exception {
        
        for (Cliente cliente : this.clientes)
            if (cliente.getIdentificacion() == ident) {
                return cliente;
            }
        throw new Exception("No se encontro el cliente");
    }
    
    public void removeMantP(Mantenimiento mant) {
        this.mantenimientosP.remove(mant);
    }
}
