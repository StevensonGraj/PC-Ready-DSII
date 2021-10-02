/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Clases.Computador;
import Clases.Consumo;
import Clases.Mantenimiento;
import Clases.Negocio;
import Clases.Producto;
import Clases.Servicio;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Shadow
 */
public class VentanaAsignacionConsumos extends javax.swing.JInternalFrame {

    private Negocio negocio;
    private Mantenimiento mantenimiento;
    private ArrayList<Servicio> serviciosLista;
    private ArrayList<Consumo> consumos;

    /**
     * Creates new form VentanaAsignacaion
     */
    public VentanaAsignacionConsumos(Negocio negocio) {
        this.negocio = negocio;
        this.mantenimiento = mantenimiento;
        this.serviciosLista = new ArrayList<>();
        this.consumos = new ArrayList<>();

        initComponents();

        btnAgregarInsumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    int cantidad = Integer.parseInt(txtCantidad.getText());
                    int codigo = Integer.parseInt(txtCodigoInsumo.getText());
                    Producto producto = negocio.findProducto(codigo);
                    Servicio servicio = listServicios.getSelectedValue();

                    Consumo consumo = new Consumo(cantidad, servicio, producto);

                    mantenimiento.addConsumo(consumo);

                    JOptionPane.showMessageDialog(VentanaAsignacionConsumos.this, "El consumo fue agregado con exito");
                    tablaConsumos.updateUI();

                } catch (Exception ex) {
                    Logger.getLogger(VentanaAsignacionConsumos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        tablaConsumos.setModel(new TableModel() {
            @Override
            public int getRowCount() {
                return consumos.size();
            }

            @Override
            public int getColumnCount() {
                return 5;
            }

            @Override
            public String getColumnName(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return "Codigo";
                    case 1:
                        return "Nombre";
                    case 2:
                        return "Vlr. Unitario";
                    case 3:
                        return "Cantidad";
                    case 4:
                        return "Costo";
                    default:
                        return "";
                }
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return String.class;
                    case 2:
                        return Integer.class;
                    case 3:
                        return Integer.class;
                    case 4:
                        return Double.class;
                    default:
                        return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex == 3;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return consumos.get(rowIndex).getProducto().getCodigo();
                    case 1:
                        return consumos.get(rowIndex).getProducto().getNombre();
                    case 2:
                        return consumos.get(rowIndex).getProducto().getCosto();
                    case 3:
                        return consumos.get(rowIndex).getCantidad();
                    case 4:
                        return consumos.get(rowIndex).getCantidad() * consumos.get(rowIndex).getProducto().getCosto();
                    default:
                        return "";
                }
            }

            @Override
            public void setValueAt(Object o, int rowIndex, int columnIndex) {
            }

            @Override
            public void addTableModelListener(TableModelListener l) {
            }

            @Override
            public void removeTableModelListener(TableModelListener l) {
            }
        });

        txtCodigoInsumo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                try {
                    int codigo = Integer.parseInt(txtCodigoInsumo.getText());

                    Producto producto = negocio.findProducto(codigo);

                    txtNombreInsumo.setText(producto.getNombre());
                    txtCostoInsumo.setText(Float.toString(producto.getCosto()));
                } catch (Exception ex) {
                    Logger.getLogger(VentanaAsignacionConsumos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        listServicios.setModel(new ListModel<Servicio>() {
            @Override
            public int getSize() {
                return serviciosLista.size();
            }

            @Override
            public Servicio getElementAt(int i) {
                return serviciosLista.get(i);
            }

            @Override
            public void addListDataListener(ListDataListener l) {
            }

            @Override
            public void removeListDataListener(ListDataListener l) {
            }
        });

        txtSerial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    String serial = txtSerial.getText();

                    mantenimiento = negocio.findMantPend(serial);
                    serviciosLista = mantenimiento.getServicios();
                    consumos = mantenimiento.getConsumos();
                    listServicios.updateUI();

                    txtCodigoInsumo.enable(true);
                    txtCantidad.enable(true);

                    txtMarca.setText(mantenimiento.getComputador().getMarca());
                    txtTipoEquipo.setText(mantenimiento.getComputador().getSerialEquipo());
                    txtTecnico.setText(mantenimiento.getTecnico().toString());

                } catch (Exception ex) {
                    Logger.getLogger(VentanaMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textTitulo = new javax.swing.JLabel();
        panelEquipo = new javax.swing.JPanel();
        panelSerial = new javax.swing.JPanel();
        txtSerial = new javax.swing.JTextField();
        panelTipoEquipo = new javax.swing.JPanel();
        txtTipoEquipo = new javax.swing.JTextField();
        panelMarca = new javax.swing.JPanel();
        txtMarca = new javax.swing.JTextField();
        panelTecnico = new javax.swing.JPanel();
        txtTecnico = new javax.swing.JTextField();
        panelServicios = new javax.swing.JPanel();
        scrollServicios = new javax.swing.JScrollPane();
        listServicios = new javax.swing.JList<>();
        panelRegistroConsumo = new javax.swing.JPanel();
        panelCodigoInsumo = new javax.swing.JPanel();
        txtCodigoInsumo = new javax.swing.JTextField();
        panelNombreInsumo = new javax.swing.JPanel();
        txtNombreInsumo = new javax.swing.JTextField();
        panelCostoInsumo = new javax.swing.JPanel();
        txtCostoInsumo = new javax.swing.JTextField();
        panelCantidadInsumo = new javax.swing.JPanel();
        txtCantidad = new javax.swing.JTextField();
        btnAgregarInsumo = new javax.swing.JButton();
        panelConsumos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaConsumos = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        textTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        textTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textTitulo.setText("Registro de Productos Utilizados");

        panelEquipo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Equipo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        panelSerial.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Serial"));

        javax.swing.GroupLayout panelSerialLayout = new javax.swing.GroupLayout(panelSerial);
        panelSerial.setLayout(panelSerialLayout);
        panelSerialLayout.setHorizontalGroup(
            panelSerialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSerialLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSerial, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
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
        txtTipoEquipo.setEnabled(false);

        javax.swing.GroupLayout panelTipoEquipoLayout = new javax.swing.GroupLayout(panelTipoEquipo);
        panelTipoEquipo.setLayout(panelTipoEquipoLayout);
        panelTipoEquipoLayout.setHorizontalGroup(
            panelTipoEquipoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTipoEquipoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTipoEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
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
        txtMarca.setEnabled(false);

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

        panelTecnico.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tecnico Asignado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        txtTecnico.setEditable(false);
        txtTecnico.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTecnico.setEnabled(false);

        javax.swing.GroupLayout panelTecnicoLayout = new javax.swing.GroupLayout(panelTecnico);
        panelTecnico.setLayout(panelTecnicoLayout);
        panelTecnicoLayout.setHorizontalGroup(
            panelTecnicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTecnicoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTecnico)
                .addContainerGap())
        );
        panelTecnicoLayout.setVerticalGroup(
            panelTecnicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTecnicoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelServicios.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Servicio(s)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

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
                .addComponent(scrollServicios, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelRegistroConsumo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Producto a Registrar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        panelCodigoInsumo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Codigo"));

        txtCodigoInsumo.setEnabled(false);

        javax.swing.GroupLayout panelCodigoInsumoLayout = new javax.swing.GroupLayout(panelCodigoInsumo);
        panelCodigoInsumo.setLayout(panelCodigoInsumoLayout);
        panelCodigoInsumoLayout.setHorizontalGroup(
            panelCodigoInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCodigoInsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCodigoInsumo, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelCodigoInsumoLayout.setVerticalGroup(
            panelCodigoInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCodigoInsumoLayout.createSequentialGroup()
                .addComponent(txtCodigoInsumo)
                .addContainerGap())
        );

        panelNombreInsumo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Nombre"));

        txtNombreInsumo.setEditable(false);
        txtNombreInsumo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNombreInsumo.setEnabled(false);

        javax.swing.GroupLayout panelNombreInsumoLayout = new javax.swing.GroupLayout(panelNombreInsumo);
        panelNombreInsumo.setLayout(panelNombreInsumoLayout);
        panelNombreInsumoLayout.setHorizontalGroup(
            panelNombreInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNombreInsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNombreInsumo, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelNombreInsumoLayout.setVerticalGroup(
            panelNombreInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNombreInsumoLayout.createSequentialGroup()
                .addComponent(txtNombreInsumo)
                .addContainerGap())
        );

        panelCostoInsumo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Costo\n"));

        txtCostoInsumo.setEditable(false);
        txtCostoInsumo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCostoInsumo.setEnabled(false);

        javax.swing.GroupLayout panelCostoInsumoLayout = new javax.swing.GroupLayout(panelCostoInsumo);
        panelCostoInsumo.setLayout(panelCostoInsumoLayout);
        panelCostoInsumoLayout.setHorizontalGroup(
            panelCostoInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCostoInsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCostoInsumo, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelCostoInsumoLayout.setVerticalGroup(
            panelCostoInsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCostoInsumoLayout.createSequentialGroup()
                .addComponent(txtCostoInsumo)
                .addContainerGap())
        );

        panelCantidadInsumo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Cantidad"));

        txtCantidad.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtCantidad.setEnabled(false);

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

        btnAgregarInsumo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAgregarInsumo.setText("Agregar Consumo");
        btnAgregarInsumo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout panelRegistroConsumoLayout = new javax.swing.GroupLayout(panelRegistroConsumo);
        panelRegistroConsumo.setLayout(panelRegistroConsumoLayout);
        panelRegistroConsumoLayout.setHorizontalGroup(
            panelRegistroConsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRegistroConsumoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRegistroConsumoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelRegistroConsumoLayout.createSequentialGroup()
                        .addComponent(panelCodigoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelNombreInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelCostoInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelCantidadInsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAgregarInsumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(btnAgregarInsumo, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelConsumos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Lista de Consumos\n", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        tablaConsumos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaConsumos);

        javax.swing.GroupLayout panelConsumosLayout = new javax.swing.GroupLayout(panelConsumos);
        panelConsumos.setLayout(panelConsumosLayout);
        panelConsumosLayout.setHorizontalGroup(
            panelConsumosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsumosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        panelConsumosLayout.setVerticalGroup(
            panelConsumosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelConsumosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelConsumos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelRegistroConsumo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelServicios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelEquipo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTecnico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(textTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelEquipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelServicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRegistroConsumo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelConsumos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarInsumo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<Servicio> listServicios;
    private javax.swing.JPanel panelCantidadInsumo;
    private javax.swing.JPanel panelCodigoInsumo;
    private javax.swing.JPanel panelConsumos;
    private javax.swing.JPanel panelCostoInsumo;
    private javax.swing.JPanel panelEquipo;
    private javax.swing.JPanel panelMarca;
    private javax.swing.JPanel panelNombreInsumo;
    private javax.swing.JPanel panelRegistroConsumo;
    private javax.swing.JPanel panelSerial;
    private javax.swing.JPanel panelServicios;
    private javax.swing.JPanel panelTecnico;
    private javax.swing.JPanel panelTipoEquipo;
    private javax.swing.JScrollPane scrollServicios;
    private javax.swing.JTable tablaConsumos;
    private javax.swing.JLabel textTitulo;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigoInsumo;
    private javax.swing.JTextField txtCostoInsumo;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtNombreInsumo;
    private javax.swing.JTextField txtSerial;
    private javax.swing.JTextField txtTecnico;
    private javax.swing.JTextField txtTipoEquipo;
    // End of variables declaration//GEN-END:variables
}
