package Interfaces;

import Clases.Cliente;
import Clases.Computador;
import Clases.Computador.TipoComputador;
import Clases.Mantenimiento;
import Clases.Negocio;
import Clases.Persona;
import Clases.Servicio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class VentanaMantenimiento extends javax.swing.JInternalFrame {

    private Negocio negocio;
    private Mantenimiento mantenimiento;
    private ArrayList<Servicio> servicios;
    private ArrayList<Servicio> serviciosLista;

    public VentanaMantenimiento(Negocio negocio) {
        this.negocio = negocio;
        this.mantenimiento = mantenimiento;
        this.servicios = negocio.getServicios();
        this.serviciosLista = new ArrayList<>();

        initComponents();

        btnIngresarMantenimiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    Computador computador = negocio.FindPc(txtSerial.getText());
                    if (serviciosLista.size() == 0) {

                    JOptionPane.showMessageDialog(VentanaMantenimiento.this, "No se puede agregar un mantenimiento sin servicios");
                    }else {
                    mantenimiento = new Mantenimiento(computador, null);
                            }
                    mantenimiento.setServicios(serviciosLista);

                    negocio.addMantenimientoP(mantenimiento);

                    JOptionPane.showMessageDialog(VentanaMantenimiento.this, "El mantenimiento fue agregado con exito");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(VentanaMantenimiento.this, ex.getMessage());
                    Logger.getLogger(VentanaMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        listServicios.setModel(new ListModel<Servicio>() {
            @Override
            public int getSize() {
                return serviciosLista.size();
            }

            @Override
            public Servicio getElementAt(int index) {
                return serviciosLista.get(index);
            }

            @Override
            public void addListDataListener(ListDataListener ll) {
            }

            @Override
            public void removeListDataListener(ListDataListener ll) {
            }
        });

        btnAgregarServicio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    Servicio servicio = (Servicio) comboServicios.getSelectedItem();

                    serviciosLista.add(servicio);

                    listServicios.updateUI();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(VentanaMantenimiento.this, ex.getMessage());
                    Logger.getLogger(VentanaMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        comboServicios.setModel(new ComboBoxModel() {

            private Servicio seleted;

            public void setSelectedItem(Object anItem) {
                this.seleted = (Servicio) anItem;
            }

            @Override
            public Object getSelectedItem() {
                return seleted;
            }

            @Override
            public int getSize() {
                return negocio.getServicios().size();
            }

            @Override
            public Servicio getElementAt(int index) {
                return servicios.get(index);
            }

            @Override
            public void addListDataListener(ListDataListener ll) {
            }

            @Override
            public void removeListDataListener(ListDataListener ll) {
            }
        });

        btnRegistrarEquipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    String Serial = txtSerial.getText();
                    String Marca = (String) comboMarca.getSelectedItem();
                    TipoComputador Tipo = (TipoComputador) comboTipoEquipo.getSelectedItem();
                    Long Identificacion = Long.parseLong(txtIdentificacion.getText());
                    String Nombre = txtNombres.getText();
                    String Apellidos = txtApellidos.getText();
                    Long Telefono = Long.parseLong(txtTelefono.getText());
                    Cliente cliente = new Cliente(Identificacion, Nombre, Apellidos, Telefono, null);
                    Computador pc = new Computador(Marca, Serial, Tipo, cliente);
                    negocio.addCliente(cliente);
                    negocio.addPc(pc);

                    JOptionPane.showMessageDialog(VentanaMantenimiento.this, "El equipo fue registrado con exito");

                    txtSerial.enable(false);
                    comboMarca.enable(false);
                    comboTipoEquipo.enable(false);
                    txtIdentificacion.enable(false);
                    txtNombres.enable(false);
                    txtApellidos.enable(false);
                    txtTelefono.enable(false);
                     } catch (NumberFormatException xd ) {
                         JOptionPane.showMessageDialog(VentanaMantenimiento.this, "No puede estar vacia la casilla de identificacion ni telefono ");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(VentanaMantenimiento.this, ex.getMessage());
                    Logger.getLogger(VentanaMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        txtSerial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    String serial = txtSerial.getText();

                    Computador computador = negocio.FindPc(serial);
                    comboMarca.setSelectedItem(computador.getMarca());
                    comboTipoEquipo.setSelectedItem(computador.getTipoComputador());
                    txtIdentificacion.setText(Long.toString(computador.getPropietario().getIdentificacion()));
                    txtNombres.setText(computador.getPropietario().getNombre());
                    txtApellidos.setText(computador.getPropietario().getApellido());
                    txtTelefono.setText(Long.toString(computador.getPropietario().getTelefono()));

                } catch (Exception ex) {
                    Object[] opciones = {"Si, por favor", "No gracias"};
                    int decision = JOptionPane.showOptionDialog(VentanaMantenimiento.this, "El equipo no se encuentra registrado\n¿Desea registrarlo?", "Registro",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, "Si, por favor");

                    if (decision == JOptionPane.OK_OPTION) {
                        comboMarca.enable(true);
                        comboMarca.updateUI();
                        comboTipoEquipo.enable(true);
                        comboTipoEquipo.updateUI();
                        comboServicios.enable(true);
                        comboServicios.updateUI();
                        txtIdentificacion.enable(true);
                        txtNombres.enable(true);
                        txtApellidos.enable(true);
                        txtTelefono.enable(true);
                    } else {
                        JOptionPane.showMessageDialog(VentanaMantenimiento.this, "Operacion abortada");
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textTitulo = new javax.swing.JLabel();
        panelEquipo = new javax.swing.JPanel();
        panelSerial = new javax.swing.JPanel();
        txtSerial = new javax.swing.JTextField();
        panelTipoEquipo = new javax.swing.JPanel();
        comboTipoEquipo = new javax.swing.JComboBox(TipoComputador.values());
        panelMarca = new javax.swing.JPanel();
        comboMarca = new javax.swing.JComboBox<>();
        panelPropietario = new javax.swing.JPanel();
        panelIdentificacion = new javax.swing.JPanel();
        txtIdentificacion = new javax.swing.JTextField();
        panelNombres = new javax.swing.JPanel();
        txtNombres = new javax.swing.JTextField();
        panelApellidos = new javax.swing.JPanel();
        txtApellidos = new javax.swing.JTextField();
        panelTelefono = new javax.swing.JPanel();
        txtTelefono = new javax.swing.JTextField();
        btnRegistrarEquipo = new javax.swing.JButton();
        panelServicios = new javax.swing.JPanel();
        comboServicios = new javax.swing.JComboBox<>();
        scrollServicios = new javax.swing.JScrollPane();
        listServicios = new javax.swing.JList<>();
        btnAgregarServicio = new javax.swing.JButton();
        btnIngresarMantenimiento = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        textTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        textTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textTitulo.setText("Generar Mantenimiento");

        panelEquipo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Equipo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        panelSerial.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Serial"));

        javax.swing.GroupLayout panelSerialLayout = new javax.swing.GroupLayout(panelSerial);
        panelSerial.setLayout(panelSerialLayout);
        panelSerialLayout.setHorizontalGroup(
            panelSerialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSerialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSerial)
                .addContainerGap())
        );
        panelSerialLayout.setVerticalGroup(
            panelSerialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSerialLayout.createSequentialGroup()
                .addComponent(txtSerial)
                .addContainerGap())
        );

        panelTipoEquipo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Tipo de Equipo"));

        comboTipoEquipo.setEnabled(false);

        javax.swing.GroupLayout panelTipoEquipoLayout = new javax.swing.GroupLayout(panelTipoEquipo);
        panelTipoEquipo.setLayout(panelTipoEquipoLayout);
        panelTipoEquipoLayout.setHorizontalGroup(
            panelTipoEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTipoEquipoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboTipoEquipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTipoEquipoLayout.setVerticalGroup(
            panelTipoEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTipoEquipoLayout.createSequentialGroup()
                .addComponent(comboTipoEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        panelMarca.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Marca"));

        comboMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hp", "Samsgung", "Asus", "MsI", "Generico", "Acer", "Lenovo", "Dell", "Sony", "Gi" }));
        comboMarca.setEnabled(false);

        javax.swing.GroupLayout panelMarcaLayout = new javax.swing.GroupLayout(panelMarca);
        panelMarca.setLayout(panelMarcaLayout);
        panelMarcaLayout.setHorizontalGroup(
            panelMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMarcaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboMarca, 0, 268, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMarcaLayout.setVerticalGroup(
            panelMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMarcaLayout.createSequentialGroup()
                .addComponent(comboMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelEquipoLayout = new javax.swing.GroupLayout(panelEquipo);
        panelEquipo.setLayout(panelEquipoLayout);
        panelEquipoLayout.setHorizontalGroup(
            panelEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEquipoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSerial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTipoEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelMarca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelEquipoLayout.setVerticalGroup(
            panelEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEquipoLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(panelSerial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(panelMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelTipoEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelPropietario.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Propietario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        panelIdentificacion.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Identificacion"));

        txtIdentificacion.setEnabled(false);

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

        txtNombres.setEnabled(false);

        javax.swing.GroupLayout panelNombresLayout = new javax.swing.GroupLayout(panelNombres);
        panelNombres.setLayout(panelNombresLayout);
        panelNombresLayout.setHorizontalGroup(
            panelNombresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNombresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNombres, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelNombresLayout.setVerticalGroup(
            panelNombresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNombresLayout.createSequentialGroup()
                .addComponent(txtNombres)
                .addContainerGap())
        );

        panelApellidos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Apellido(s)"));

        txtApellidos.setEnabled(false);

        javax.swing.GroupLayout panelApellidosLayout = new javax.swing.GroupLayout(panelApellidos);
        panelApellidos.setLayout(panelApellidosLayout);
        panelApellidosLayout.setHorizontalGroup(
            panelApellidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelApellidosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelApellidosLayout.setVerticalGroup(
            panelApellidosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelApellidosLayout.createSequentialGroup()
                .addComponent(txtApellidos)
                .addContainerGap())
        );

        panelTelefono.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Teléfono"));

        txtTelefono.setEnabled(false);

        javax.swing.GroupLayout panelTelefonoLayout = new javax.swing.GroupLayout(panelTelefono);
        panelTelefono.setLayout(panelTelefonoLayout);
        panelTelefonoLayout.setHorizontalGroup(
            panelTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTelefonoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTelefonoLayout.setVerticalGroup(
            panelTelefonoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTelefonoLayout.createSequentialGroup()
                .addComponent(txtTelefono)
                .addContainerGap())
        );

        javax.swing.GroupLayout panelPropietarioLayout = new javax.swing.GroupLayout(panelPropietario);
        panelPropietario.setLayout(panelPropietarioLayout);
        panelPropietarioLayout.setHorizontalGroup(
            panelPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropietarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelIdentificacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelNombres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelApellidos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelPropietarioLayout.setVerticalGroup(
            panelPropietarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPropietarioLayout.createSequentialGroup()
                .addComponent(panelIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        btnRegistrarEquipo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRegistrarEquipo.setText("Registrar Equipo");
        btnRegistrarEquipo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        panelServicios.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Servicio(s) Solicitado(s)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        listServicios.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        scrollServicios.setViewportView(listServicios);

        btnAgregarServicio.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAgregarServicio.setText("Agregar");
        btnAgregarServicio.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelServiciosLayout = new javax.swing.GroupLayout(panelServicios);
        panelServicios.setLayout(panelServiciosLayout);
        panelServiciosLayout.setHorizontalGroup(
            panelServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelServiciosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollServicios)
                    .addGroup(panelServiciosLayout.createSequentialGroup()
                        .addComponent(comboServicios, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAgregarServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelServiciosLayout.setVerticalGroup(
            panelServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelServiciosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comboServicios, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(btnAgregarServicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(scrollServicios, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnIngresarMantenimiento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnIngresarMantenimiento.setText("Ingresar");
        btnIngresarMantenimiento.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnCancelar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(panelEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRegistrarEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelServicios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnIngresarMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(textTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelPropietario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegistrarEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelServicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIngresarMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarServicio;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnIngresarMantenimiento;
    private javax.swing.JButton btnRegistrarEquipo;
    private javax.swing.JComboBox<String> comboMarca;
    private javax.swing.JComboBox<String> comboServicios;
    private javax.swing.JComboBox<String> comboTipoEquipo;
    private javax.swing.JList<Servicio> listServicios;
    private javax.swing.JPanel panelApellidos;
    private javax.swing.JPanel panelEquipo;
    private javax.swing.JPanel panelIdentificacion;
    private javax.swing.JPanel panelMarca;
    private javax.swing.JPanel panelNombres;
    private javax.swing.JPanel panelPropietario;
    private javax.swing.JPanel panelSerial;
    private javax.swing.JPanel panelServicios;
    private javax.swing.JPanel panelTelefono;
    private javax.swing.JPanel panelTipoEquipo;
    private javax.swing.JScrollPane scrollServicios;
    private javax.swing.JLabel textTitulo;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtIdentificacion;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtSerial;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
