package Interfaces;

import Clases.Cliente;
import Clases.Consumo;
import Clases.DetalleVenta;
import Clases.Mantenimiento;
import Clases.Negocio;
import Clases.Persona;
import Clases.Producto;
import Clases.Servicio;
import Clases.Venta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Shadow
 */
public class VentanaFacturacion extends javax.swing.JInternalFrame {

    private Negocio negocio;
    private ArrayList servicios = new ArrayList();
    private ArrayList consumos = new ArrayList();
    private ArrayList<DetalleVenta> detalleVentas = new ArrayList();
    private Cliente cliente = null;
    int total = 0;
    int tipoFact = 0;

    public void CompVenta(boolean b) {
        txtIdentificacion.setEditable(b);
        txtNombres.setEditable(b);
        txtApellidos.setEditable(b);
        txtTelefono.setEditable(b);
        txtCodigoProducto.setEditable(b);
        txtCantidad.setEditable(b);
        btnBuscarCliente.setEnabled(b);
        btnRegistrarCliente.setEnabled(b);
        btnAgregarProducto.setEnabled(b);
        btnDevolver.setEnabled(b);
        listProductos.setEnabled(b);
    }

    public void CompMant(boolean b) {
        txtSerial.setEditable(b);
        listServicios.setEnabled(b);
        listConsumos.setEnabled(b);
    }

    public VentanaFacturacion(Negocio negocio) {
        this.negocio = negocio;

        initComponents();

        CompMant(true);
        CompVenta(false);

        txtTotal.setEnabled(false);
        txtSubtotal.setEnabled(false);
        txtIVA.setEnabled(false);

        comboEleccion.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                tipoFact = comboEleccion.getSelectedIndex();

                if (tipoFact == 0) {
                    CompMant(true);
                    CompVenta(false);
                    txtSerial.setText("");
                    txtMarca.setText("");
                    txtTipoEquipo.setText("");
                    txtTotal.setText("");
                    txtSubtotal.setText("");
                    txtIVA.setText("");
                    servicios = new ArrayList();
                    consumos = new ArrayList();
                    total = 0;
                } else {
                    CompMant(false);
                    CompVenta(true);
                    txtIdentificacion.setText("");
                    txtNombres.setText("");
                    txtApellidos.setText("");
                    txtTelefono.setText("");
                    txtCodigoProducto.setText("");
                    txtCantidad.setText("");
                    txtNombreProducto.setText("");
                    txtCostoProducto.setText("");
                    txtTotal.setText("");
                    txtSubtotal.setText("");
                    txtIVA.setText("");
                    detalleVentas = new ArrayList();
                    cliente = null;
                    total = 0;
                }
            }
        });

        txtSerial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    String serial = txtSerial.getText();
                    Mantenimiento mant = negocio.findMantPend(serial);
                    txtMarca.setText(mant.getComputador().getMarca());
                    txtTipoEquipo.setText(mant.getComputador().getTipoComputador().name());

                    servicios = mant.getServicios();
                    consumos = mant.getConsumos();

                    if (mant.getTecnico() == null) {
                        txtIdentificacion.setText("");
                        txtNombres.setText("");
                        txtApellidos.setText("");
                        txtTelefono.setText("");
                        txtCodigoProducto.setText("");
                        txtCantidad.setText("");
                        txtSerial.setText("");
                        txtNombreProducto.setText("");
                        txtCostoProducto.setText("");
                        txtMarca.setText("");
                        txtTipoEquipo.setText("");
                        txtTotal.setText("");
                        txtSubtotal.setText("");
                        txtIVA.setText("");
                        servicios = new ArrayList();
                        consumos = new ArrayList();
                        detalleVentas = new ArrayList();
                        cliente = null;
                        total = 0;
                        listConsumos.updateUI();
                        listProductos.updateUI();
                        listServicios.updateUI();
                        
                        throw new Exception("No se puede facturar un mantenimiento sin tecnico");
                    }
                    listServicios.updateUI();
                    listConsumos.updateUI();

                    txtTotal.setText(Integer.toString(mant.getCostoTotalMant()));
                    txtIVA.setText(Integer.toString((int) (mant.getCostoTotalMant() * 0.19)));
                    txtSubtotal.setText(Integer.toString((int) ((mant.getCostoTotalMant() - (mant.getCostoTotalMant() * 0.19)))));

                } catch (Exception ex) {
                    Logger.getLogger(VentanaFacturacion.class
                            .getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(VentanaFacturacion.this, ex.getMessage());
                }
            }
        });

        listServicios.setModel(new AbstractListModel<Servicio>() {

            public int getSize() {
                return servicios.size();
            }

            public Servicio getElementAt(int index) {
                return (Servicio) servicios.get(index);
            }
        });

        listConsumos.setModel(new AbstractListModel<Consumo>() {

            public int getSize() {
                return consumos.size();
            }

            @Override
            public Consumo getElementAt(int index) {
                return (Consumo) consumos.get(index);
            }
        });

        btnBuscarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                long Ident = Long.parseLong(txtIdentificacion.getText());
                try {
                    cliente = negocio.findCliente(Ident);

                    txtNombres.setText(cliente.getNombre());
                    txtApellidos.setText(cliente.getApellido());
                    txtTelefono.setText(Long.toString(cliente.getTelefono()));

                } catch (Exception ex) {
                    Logger.getLogger(VentanaFacturacion.class
                            .getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(VentanaFacturacion.this, ex.getMessage());
                }
            }
        });

        btnRegistrarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    long ident = Long.parseLong(txtIdentificacion.getText());
                    String nombre = txtNombres.getText();
                    String apellido = txtApellidos.getText();
                    Long telefono = Long.parseLong(txtTelefono.getText());

                    cliente = new Cliente(ident, nombre, apellido, telefono, null);

                    negocio.addCliente(cliente);

                    throw new Exception("Se registro el cliente");

                } catch (Exception ex) {
                    Logger.getLogger(VentanaFacturacion.class
                            .getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(VentanaFacturacion.this, ex.getMessage());
                }
            }
        });

        txtCodigoProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    int cod = Integer.parseInt(txtCodigoProducto.getText());

                    Producto prod = negocio.findProducto(cod);

                    txtNombreProducto.setText(prod.getNombre());
                    txtCostoProducto.setText(Float.toString(prod.getCosto()));

                } catch (Exception ex) {
                    Logger.getLogger(VentanaFacturacion.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnAgregarProducto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    int cod = Integer.parseInt(txtCodigoProducto.getText());
                    int cant = Integer.parseInt(txtCantidad.getText());

                    Producto prod = negocio.findProducto(cod);

                    DetalleVenta dV = new DetalleVenta(cant, prod);

                    detalleVentas.add(dV);

                    total = 0;
                    for (DetalleVenta dv : detalleVentas) {
                        total += dv.getCostoTotal();
                    }

                    txtTotal.setText(Integer.toString(total));
                    txtIVA.setText(Integer.toString((int) (total * 0.19)));
                    txtSubtotal.setText(Integer.toString((int) (total - (total * 0.19))));

                    listProductos.updateUI();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(VentanaFacturacion.this, "Debe ingresar valores numericos");
                    Logger.getLogger(VentanaFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(VentanaFacturacion.class
                            .getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(VentanaFacturacion.this, ex.getMessage());
                }
            }
        });

        listProductos.setModel(new AbstractListModel<DetalleVenta>() {

            public int getSize() {
                return detalleVentas.size();
            }

            public DetalleVenta getElementAt(int index) {
                return (DetalleVenta) detalleVentas.get(index);
            }
        });

        btnDevolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    int i = listProductos.getSelectedIndex();

                    detalleVentas.remove(i);

                    total = 0;
                    for (DetalleVenta dv : detalleVentas) {
                        total += dv.getCostoTotal();
                    }

                    txtTotal.setText(Integer.toString(total));
                    txtIVA.setText(Integer.toString((int) (total * 0.19)));
                    txtSubtotal.setText(Integer.toString((int) (total - (total * 0.19))));

                    listProductos.updateUI();

                } catch (Exception ex) {
                    Logger.getLogger(VentanaFacturacion.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnFacturar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    if (tipoFact == 0) {
                        String serial = txtSerial.getText();
                        Mantenimiento mant = negocio.findMantPend(serial);

                        negocio.removeMantP(mant);
                        negocio.addMantenimientoR(mant);

                        JOptionPane.showMessageDialog(VentanaFacturacion.this, "Se realizo la facturacion del mantenimiento");
                    } else {
                        Venta venta = new Venta(cliente);
                        venta.setDetalleVentas(detalleVentas);

                        negocio.addVenta(venta);

                        JOptionPane.showMessageDialog(VentanaFacturacion.this, "Se realizo la facturacion de la venta");
                    }
                    txtIdentificacion.setText("");
                    txtNombres.setText("");
                    txtApellidos.setText("");
                    txtTelefono.setText("");
                    txtCodigoProducto.setText("");
                    txtCantidad.setText("");
                    txtSerial.setText("");
                    txtNombreProducto.setText("");
                    txtCostoProducto.setText("");
                    txtMarca.setText("");
                    txtTipoEquipo.setText("");
                    txtTotal.setText("");
                    txtSubtotal.setText("");
                    txtIVA.setText("");
                    servicios = new ArrayList();
                    consumos = new ArrayList();
                    detalleVentas = new ArrayList();
                    cliente = null;
                    total = 0;
                    listConsumos.updateUI();
                    listProductos.updateUI();
                    listServicios.updateUI();
                } catch (Exception ex) {
                    Logger.getLogger(VentanaFacturacion.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                txtIdentificacion.setText("");
                txtNombres.setText("");
                txtApellidos.setText("");
                txtTelefono.setText("");
                txtCodigoProducto.setText("");
                txtCantidad.setText("");
                txtSerial.setText("");
                txtNombreProducto.setText("");
                txtCostoProducto.setText("");
                txtMarca.setText("");
                txtTipoEquipo.setText("");
                txtTotal.setText("");
                txtSubtotal.setText("");
                txtIVA.setText("");
                servicios = new ArrayList();
                consumos = new ArrayList();
                detalleVentas = new ArrayList();
                cliente = null;
                total = 0;
                listConsumos.updateUI();
                listProductos.updateUI();
                listServicios.updateUI();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textTitulo = new javax.swing.JLabel();
        panelEleccion = new javax.swing.JPanel();
        comboEleccion = new javax.swing.JComboBox<>();
        panelMantenimiento = new javax.swing.JPanel();
        panelEquipo = new javax.swing.JPanel();
        panelSerial = new javax.swing.JPanel();
        txtSerial = new javax.swing.JTextField();
        panelTipoEquipo = new javax.swing.JPanel();
        txtTipoEquipo = new javax.swing.JTextField();
        panelMarca = new javax.swing.JPanel();
        txtMarca = new javax.swing.JTextField();
        panelServicios = new javax.swing.JPanel();
        scrollServicios = new javax.swing.JScrollPane();
        listServicios = new javax.swing.JList<>();
        panelConsumos = new javax.swing.JPanel();
        scrollServicios1 = new javax.swing.JScrollPane();
        listConsumos = new javax.swing.JList<>();
        panelVenta = new javax.swing.JPanel();
        panelPropietario = new javax.swing.JPanel();
        panelIdentificacion = new javax.swing.JPanel();
        txtIdentificacion = new javax.swing.JTextField();
        panelNombres = new javax.swing.JPanel();
        txtNombres = new javax.swing.JTextField();
        panelApellidos = new javax.swing.JPanel();
        txtApellidos = new javax.swing.JTextField();
        panelTelefono = new javax.swing.JPanel();
        txtTelefono = new javax.swing.JTextField();
        btnBuscarCliente = new javax.swing.JButton();
        btnRegistrarCliente = new javax.swing.JButton();
        panelRegistroConsumo = new javax.swing.JPanel();
        panelCodigoInsumo = new javax.swing.JPanel();
        txtCodigoProducto = new javax.swing.JTextField();
        panelNombreInsumo = new javax.swing.JPanel();
        txtNombreProducto = new javax.swing.JTextField();
        panelCostoInsumo = new javax.swing.JPanel();
        txtCostoProducto = new javax.swing.JTextField();
        panelCantidadInsumo = new javax.swing.JPanel();
        txtCantidad = new javax.swing.JTextField();
        btnAgregarProducto = new javax.swing.JButton();
        btnDevolver = new javax.swing.JButton();
        scrollServicios2 = new javax.swing.JScrollPane();
        listProductos = new javax.swing.JList<>();
        panelCostos = new javax.swing.JPanel();
        panelSubtotal = new javax.swing.JPanel();
        txtSubtotal = new javax.swing.JTextField();
        panelIVA = new javax.swing.JPanel();
        txtIVA = new javax.swing.JTextField();
        panelTotal = new javax.swing.JPanel();
        txtTotal = new javax.swing.JTextField();
        btnFacturar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        textTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        textTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textTitulo.setText("Facturacion");

        panelEleccion.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Que Desea Facturar", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        panelEleccion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        comboEleccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mantenimiento", "Venta" }));

        javax.swing.GroupLayout panelEleccionLayout = new javax.swing.GroupLayout(panelEleccion);
        panelEleccion.setLayout(panelEleccionLayout);
        panelEleccionLayout.setHorizontalGroup(
            panelEleccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEleccionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboEleccion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelEleccionLayout.setVerticalGroup(
            panelEleccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEleccionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboEleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelMantenimiento.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Mantenimiento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        panelEquipo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Equipo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        panelSerial.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Serial"));

        javax.swing.GroupLayout panelSerialLayout = new javax.swing.GroupLayout(panelSerial);
        panelSerial.setLayout(panelSerialLayout);
        panelSerialLayout.setHorizontalGroup(
            panelSerialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSerialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSerial, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelSerialLayout.setVerticalGroup(
            panelSerialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSerialLayout.createSequentialGroup()
                .addComponent(txtSerial)
                .addContainerGap())
        );

        panelTipoEquipo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Tipo de Equipo"));

        txtTipoEquipo.setEditable(false);
        txtTipoEquipo.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelTipoEquipoLayout = new javax.swing.GroupLayout(panelTipoEquipo);
        panelTipoEquipo.setLayout(panelTipoEquipoLayout);
        panelTipoEquipoLayout.setHorizontalGroup(
            panelTipoEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTipoEquipoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTipoEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTipoEquipoLayout.setVerticalGroup(
            panelTipoEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTipoEquipoLayout.createSequentialGroup()
                .addComponent(txtTipoEquipo)
                .addContainerGap())
        );

        panelMarca.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Marca"));

        txtMarca.setEditable(false);
        txtMarca.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelMarcaLayout = new javax.swing.GroupLayout(panelMarca);
        panelMarca.setLayout(panelMarcaLayout);
        panelMarcaLayout.setHorizontalGroup(
            panelMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMarcaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMarca)
                .addContainerGap())
        );
        panelMarcaLayout.setVerticalGroup(
            panelMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMarcaLayout.createSequentialGroup()
                .addComponent(txtMarca)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelEquipoLayout = new javax.swing.GroupLayout(panelEquipo);
        panelEquipo.setLayout(panelEquipoLayout);
        panelEquipoLayout.setHorizontalGroup(
            panelEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEquipoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelSerial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMarca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelTipoEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelEquipoLayout.setVerticalGroup(
            panelEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEquipoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSerial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(panelMarca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelTipoEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelServicios.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Servicio(s) Solicitado(s)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        listServicios.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        scrollServicios.setViewportView(listServicios);

        javax.swing.GroupLayout panelServiciosLayout = new javax.swing.GroupLayout(panelServicios);
        panelServicios.setLayout(panelServiciosLayout);
        panelServiciosLayout.setHorizontalGroup(
            panelServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelServiciosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollServicios)
                .addContainerGap())
        );
        panelServiciosLayout.setVerticalGroup(
            panelServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelServiciosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollServicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        panelConsumos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Consumos Registrados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        listConsumos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        scrollServicios1.setViewportView(listConsumos);

        javax.swing.GroupLayout panelConsumosLayout = new javax.swing.GroupLayout(panelConsumos);
        panelConsumos.setLayout(panelConsumosLayout);
        panelConsumosLayout.setHorizontalGroup(
            panelConsumosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsumosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollServicios1)
                .addContainerGap())
        );
        panelConsumosLayout.setVerticalGroup(
            panelConsumosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsumosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollServicios1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelMantenimientoLayout = new javax.swing.GroupLayout(panelMantenimiento);
        panelMantenimiento.setLayout(panelMantenimientoLayout);
        panelMantenimientoLayout.setHorizontalGroup(
            panelMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMantenimientoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelServicios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelConsumos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelMantenimientoLayout.setVerticalGroup(
            panelMantenimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMantenimientoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelServicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelConsumos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelVenta.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Venta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        panelPropietario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        panelIdentificacion.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Identificacion"));

        txtIdentificacion.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelIdentificacionLayout = new javax.swing.GroupLayout(panelIdentificacion);
        panelIdentificacion.setLayout(panelIdentificacionLayout);
        panelIdentificacionLayout.setHorizontalGroup(
            panelIdentificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIdentificacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtIdentificacion)
                .addContainerGap())
        );
        panelIdentificacionLayout.setVerticalGroup(
            panelIdentificacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIdentificacionLayout.createSequentialGroup()
                .addComponent(txtIdentificacion)
                .addContainerGap())
        );

        panelNombres.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Nombre(s)"));

        txtNombres.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelNombresLayout = new javax.swing.GroupLayout(panelNombres);
        panelNombres.setLayout(panelNombresLayout);
        panelNombresLayout.setHorizontalGroup(
            panelNombresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNombresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNombres, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelNombresLayout.setVerticalGroup(
            panelNombresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNombresLayout.createSequentialGroup()
                .addComponent(txtNombres)
                .addContainerGap())
        );

        panelApellidos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Apellido(s)"));

        txtApellidos.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelApellidosLayout = new javax.swing.GroupLayout(panelApellidos);
        panelApellidos.setLayout(panelApellidosLayout);
        panelApellidosLayout.setHorizontalGroup(
            panelApellidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelApellidosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelApellidosLayout.setVerticalGroup(
            panelApellidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelApellidosLayout.createSequentialGroup()
                .addComponent(txtApellidos)
                .addContainerGap())
        );

        panelTelefono.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Teléfono"));

        txtTelefono.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelTelefonoLayout = new javax.swing.GroupLayout(panelTelefono);
        panelTelefono.setLayout(panelTelefonoLayout);
        panelTelefonoLayout.setHorizontalGroup(
            panelTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTelefonoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTelefonoLayout.setVerticalGroup(
            panelTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTelefonoLayout.createSequentialGroup()
                .addComponent(txtTelefono)
                .addContainerGap())
        );

        btnBuscarCliente.setText("Buscar Cliente");

        btnRegistrarCliente.setText("Registrar Cliente");

        javax.swing.GroupLayout panelPropietarioLayout = new javax.swing.GroupLayout(panelPropietario);
        panelPropietario.setLayout(panelPropietarioLayout);
        panelPropietarioLayout.setHorizontalGroup(
            panelPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropietarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelIdentificacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPropietarioLayout.createSequentialGroup()
                        .addComponent(panelNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelPropietarioLayout.createSequentialGroup()
                        .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRegistrarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(4, 4, 4))
        );
        panelPropietarioLayout.setVerticalGroup(
            panelPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPropietarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPropietarioLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(129, 129, 129))
                    .addGroup(panelPropietarioLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panelPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRegistrarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        panelRegistroConsumo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Producto a Registrar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        panelCodigoInsumo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Codigo"));

        javax.swing.GroupLayout panelCodigoInsumoLayout = new javax.swing.GroupLayout(panelCodigoInsumo);
        panelCodigoInsumo.setLayout(panelCodigoInsumoLayout);
        panelCodigoInsumoLayout.setHorizontalGroup(
            panelCodigoInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCodigoInsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCodigoProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelCodigoInsumoLayout.setVerticalGroup(
            panelCodigoInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCodigoInsumoLayout.createSequentialGroup()
                .addComponent(txtCodigoProducto)
                .addContainerGap())
        );

        panelNombreInsumo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Nombre"));

        txtNombreProducto.setEditable(false);
        txtNombreProducto.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelNombreInsumoLayout = new javax.swing.GroupLayout(panelNombreInsumo);
        panelNombreInsumo.setLayout(panelNombreInsumoLayout);
        panelNombreInsumoLayout.setHorizontalGroup(
            panelNombreInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNombreInsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNombreProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelNombreInsumoLayout.setVerticalGroup(
            panelNombreInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNombreInsumoLayout.createSequentialGroup()
                .addComponent(txtNombreProducto)
                .addContainerGap())
        );

        panelCostoInsumo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Costo\n"));

        txtCostoProducto.setEditable(false);
        txtCostoProducto.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelCostoInsumoLayout = new javax.swing.GroupLayout(panelCostoInsumo);
        panelCostoInsumo.setLayout(panelCostoInsumoLayout);
        panelCostoInsumoLayout.setHorizontalGroup(
            panelCostoInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCostoInsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCostoProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelCostoInsumoLayout.setVerticalGroup(
            panelCostoInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCostoInsumoLayout.createSequentialGroup()
                .addComponent(txtCostoProducto)
                .addContainerGap())
        );

        panelCantidadInsumo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Cantidad"));

        txtCantidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelCantidadInsumoLayout = new javax.swing.GroupLayout(panelCantidadInsumo);
        panelCantidadInsumo.setLayout(panelCantidadInsumoLayout);
        panelCantidadInsumoLayout.setHorizontalGroup(
            panelCantidadInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCantidadInsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelCantidadInsumoLayout.setVerticalGroup(
            panelCantidadInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCantidadInsumoLayout.createSequentialGroup()
                .addComponent(txtCantidad)
                .addContainerGap())
        );

        btnAgregarProducto.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAgregarProducto.setText("Agregar Producto");
        btnAgregarProducto.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelRegistroConsumoLayout = new javax.swing.GroupLayout(panelRegistroConsumo);
        panelRegistroConsumo.setLayout(panelRegistroConsumoLayout);
        panelRegistroConsumoLayout.setHorizontalGroup(
            panelRegistroConsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistroConsumoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelRegistroConsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelRegistroConsumoLayout.createSequentialGroup()
                        .addComponent(panelCodigoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelNombreInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelCostoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelCantidadInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAgregarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelRegistroConsumoLayout.setVerticalGroup(
            panelRegistroConsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistroConsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRegistroConsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCantidadInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCostoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelNombreInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCodigoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAgregarProducto, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnDevolver.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDevolver.setText("Devolver");
        btnDevolver.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        listProductos.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        scrollServicios2.setViewportView(listProductos);

        javax.swing.GroupLayout panelVentaLayout = new javax.swing.GroupLayout(panelVenta);
        panelVenta.setLayout(panelVentaLayout);
        panelVentaLayout.setHorizontalGroup(
            panelVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDevolver, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelPropietario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRegistroConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, 535, Short.MAX_VALUE)
                    .addComponent(scrollServicios2))
                .addContainerGap())
        );
        panelVentaLayout.setVerticalGroup(
            panelVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVentaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRegistroConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollServicios2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(btnDevolver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        panelCostos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Costos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        panelSubtotal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Subtotal"));

        txtSubtotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelSubtotalLayout = new javax.swing.GroupLayout(panelSubtotal);
        panelSubtotal.setLayout(panelSubtotalLayout);
        panelSubtotalLayout.setHorizontalGroup(
            panelSubtotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSubtotalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelSubtotalLayout.setVerticalGroup(
            panelSubtotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSubtotalLayout.createSequentialGroup()
                .addComponent(txtSubtotal)
                .addContainerGap())
        );

        panelIVA.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "IVA"));

        txtIVA.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelIVALayout = new javax.swing.GroupLayout(panelIVA);
        panelIVA.setLayout(panelIVALayout);
        panelIVALayout.setHorizontalGroup(
            panelIVALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIVALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtIVA, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelIVALayout.setVerticalGroup(
            panelIVALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIVALayout.createSequentialGroup()
                .addComponent(txtIVA)
                .addContainerGap())
        );

        panelTotal.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Total"));

        txtTotal.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelTotalLayout = new javax.swing.GroupLayout(panelTotal);
        panelTotal.setLayout(panelTotalLayout);
        panelTotalLayout.setHorizontalGroup(
            panelTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTotalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTotalLayout.setVerticalGroup(
            panelTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTotalLayout.createSequentialGroup()
                .addComponent(txtTotal)
                .addContainerGap())
        );

        btnFacturar.setText("Facturar");

        btnCancelar.setText("Cancelar");

        javax.swing.GroupLayout panelCostosLayout = new javax.swing.GroupLayout(panelCostos);
        panelCostos.setLayout(panelCostosLayout);
        panelCostosLayout.setHorizontalGroup(
            panelCostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCostosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelIVA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelCostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(btnFacturar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCostosLayout.setVerticalGroup(
            panelCostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCostosLayout.createSequentialGroup()
                .addGroup(panelCostosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCostosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(panelSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelIVA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCostosLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(btnFacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelEleccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelMantenimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(259, 259, 259)
                .addComponent(panelCostos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(textTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(panelEleccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelMantenimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelVenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCostos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarProducto;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDevolver;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JButton btnRegistrarCliente;
    private javax.swing.JComboBox<String> comboEleccion;
    private javax.swing.JList<Consumo> listConsumos;
    private javax.swing.JList<DetalleVenta> listProductos;
    private javax.swing.JList<Servicio> listServicios;
    private javax.swing.JPanel panelApellidos;
    private javax.swing.JPanel panelCantidadInsumo;
    private javax.swing.JPanel panelCodigoInsumo;
    private javax.swing.JPanel panelConsumos;
    private javax.swing.JPanel panelCostoInsumo;
    private javax.swing.JPanel panelCostos;
    private javax.swing.JPanel panelEleccion;
    private javax.swing.JPanel panelEquipo;
    private javax.swing.JPanel panelIVA;
    private javax.swing.JPanel panelIdentificacion;
    private javax.swing.JPanel panelMantenimiento;
    private javax.swing.JPanel panelMarca;
    private javax.swing.JPanel panelNombreInsumo;
    private javax.swing.JPanel panelNombres;
    private javax.swing.JPanel panelPropietario;
    private javax.swing.JPanel panelRegistroConsumo;
    private javax.swing.JPanel panelSerial;
    private javax.swing.JPanel panelServicios;
    private javax.swing.JPanel panelSubtotal;
    private javax.swing.JPanel panelTelefono;
    private javax.swing.JPanel panelTipoEquipo;
    private javax.swing.JPanel panelTotal;
    private javax.swing.JPanel panelVenta;
    private javax.swing.JScrollPane scrollServicios;
    private javax.swing.JScrollPane scrollServicios1;
    private javax.swing.JScrollPane scrollServicios2;
    private javax.swing.JLabel textTitulo;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigoProducto;
    private javax.swing.JTextField txtCostoProducto;
    private javax.swing.JTextField txtIVA;
    private javax.swing.JTextField txtIdentificacion;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtSerial;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTipoEquipo;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
