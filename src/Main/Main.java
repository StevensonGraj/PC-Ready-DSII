package Main;


import Clases.Cliente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Clases.Computador;
import Clases.Consumo;
import Clases.Mantenimiento;
import Clases.Negocio;
import Clases.Persona;
import Clases.Producto;
import Clases.Servicio;
import Clases.Tecnico;
import Interfaces.VentanaPrincipal;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String args[]) {

   

        try {
            Negocio negocio = new Negocio(1020L, "PCREADY");
            
              Cliente cl = new Cliente(11111111, "sebastian", "Jaramillo", 3136433711L, null);
              Cliente cl2 = new Cliente(11111112, "Joan", "Rosero", 3215469854L, null);
              
              Computador pc = new Computador("Hp", "123456A",Computador.TipoComputador.Escritorio, cl);
              Computador pc1 = new Computador("Asus", "123456B",Computador.TipoComputador.Portatil, cl2);
              
              Tecnico t1 = new Tecnico(1234567, "Juanito", "Vicio");
              Tecnico t2 = new Tecnico(1234568, "Camila", "Lariza");
              Tecnico t3 = new Tecnico(1234569, "Guille", "Indu");
              
              Servicio s1 = new Servicio(1111, "Instalacion Windows 10", 50000);
              Servicio s2 = new Servicio(2222, "Instalacion antivirus", 30000);
              Servicio s3 = new Servicio(3333, "Instalacion servicios Office", 30000);
              Servicio s4 = new Servicio(4444, "Mantenimiento general", 90000);
              
              Producto p1 = new Producto(11111, "Windows 10", 100000);
              
              Consumo co1 = new Consumo(1, s1, p1);
              
              Mantenimiento m1 = new Mantenimiento(pc1, null);
              
              negocio.addProducto(p1);
              
              m1.addServicio(s1);
              negocio.addServicio(s2);
              negocio.addServicio(s3);
              negocio.addServicio(s4);
              
              t1.setEstado(Tecnico.Estado.Si);
              t2.setEstado(Tecnico.Estado.Si);
                t3.setEstado(Tecnico.Estado.Si);
              
              negocio.addMantenimientoP(m1);
              
              negocio.addTecnico(t1);
              negocio.addTecnico(t2);
              negocio.addTecnico(t3);
              negocio.addServicio(s1);

              negocio.addCliente(cl);
              negocio.addCliente(cl2);
              negocio.addPc(pc);
              negocio.addPc(pc1);
             
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new VentanaPrincipal(negocio).setVisible(true);
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
