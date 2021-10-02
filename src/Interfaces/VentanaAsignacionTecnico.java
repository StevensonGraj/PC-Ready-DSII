package Interfaces;

import Clases.Mantenimiento;
import Clases.Negocio;
import Clases.Persona;
import Clases.Tecnico;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Shadow
 */
public class VentanaAsignacionTecnico extends javax.swing.JInternalFrame {

    private Negocio negocio;
    private ArrayList tecnicos;

    public VentanaAsignacionTecnico(Negocio negocio) {
        this.negocio = negocio;
        this.tecnicos = (ArrayList) negocio.TecnicosDisp();

        initComponents();

        tablaMantenimientos.updateUI();
        comboTecnicos.updateUI();

        tablaMantenimientos.setModel(new TableModel() {

            public int getRowCount() {
                return negocio.MantSinTecnicos().size();
            }

            @Override
            public int getColumnCount() {
                return 2;
            }

            @Override
            public String getColumnName(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return "Computador";
                    case 1:
                        return "Servicio(s)";
                    default:
                        return "";
                }
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    default:
                        return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {

                Mantenimiento mant = (Mantenimiento) negocio.MantSinTecnicos().get(rowIndex);

                switch (columnIndex) {
                    case 0:
                        return mant.getComputador().toString();
                    case 1:
                        return mant.Servicios();
                    default:
                        return "";
                }
            }

            @Override
            public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            }

            @Override
            public void addTableModelListener(TableModelListener l) {
            }

            @Override
            public void removeTableModelListener(TableModelListener l) {
            }
        });

        comboTecnicos.setModel(new ComboBoxModel<Persona>() {

            Persona tecnico;

            @Override
            public void setSelectedItem(Object anItem) {
                this.tecnico = (Persona) anItem;
            }

            @Override
            public Object getSelectedItem() {
                return this.tecnico;
            }

            @Override
            public int getSize() {
                return tecnicos.size();
            }

            @Override
            public Persona getElementAt(int index) {
                return (Persona) tecnicos.get(index);
            }

            @Override
            public void addListDataListener(ListDataListener l) {
            }

            @Override
            public void removeListDataListener(ListDataListener l) {
            }
        });

        btnAsignarTecnico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Tecnico tec = (Tecnico) comboTecnicos.getSelectedItem();

                if (tec == null) {
                    try {
                        throw new Exception("Debe seleccionar el tecnico que realizara el ");
                    } catch (Exception ex) {
                        Logger.getLogger(VentanaAsignacionTecnico.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                tec.setEstado(Tecnico.Estado.No);

                Mantenimiento mant = (Mantenimiento) negocio.MantSinTecnicos().get(tablaMantenimientos.getSelectedRow());

                mant.setTecnico(tec);

                negocio.setMantPend(mant);
                negocio.setTecnico(tec);

                tecnicos = (ArrayList) negocio.TecnicosDisp();
                tablaMantenimientos.updateUI();
                comboTecnicos.setSelectedIndex(-1);
                comboTecnicos.updateUI();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActualizarTab();
                ActualizarTec();
            }
        });
    }

    public void ActualizarTab() {
        tablaMantenimientos.updateUI();
    }

    public void ActualizarTec() {
        comboTecnicos.updateUI();
        tecnicos = (ArrayList) negocio.TecnicosDisp();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textTitulo = new javax.swing.JLabel();
        panelListaMantenimientos = new javax.swing.JPanel();
        scrollMantenimientos = new javax.swing.JScrollPane();
        tablaMantenimientos = new javax.swing.JTable();
        panelTecnicos = new javax.swing.JPanel();
        comboTecnicos = new javax.swing.JComboBox<>();
        btnActualizar = new javax.swing.JButton();
        btnAsignarTecnico = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        textTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        textTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        textTitulo.setText("Asignacion de Tecnico");

        panelListaMantenimientos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Mantenimientos Sin Tecnico", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        tablaMantenimientos.setModel(new javax.swing.table.DefaultTableModel(
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
        scrollMantenimientos.setViewportView(tablaMantenimientos);

        javax.swing.GroupLayout panelListaMantenimientosLayout = new javax.swing.GroupLayout(panelListaMantenimientos);
        panelListaMantenimientos.setLayout(panelListaMantenimientosLayout);
        panelListaMantenimientosLayout.setHorizontalGroup(
            panelListaMantenimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListaMantenimientosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollMantenimientos, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelListaMantenimientosLayout.setVerticalGroup(
            panelListaMantenimientosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelListaMantenimientosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollMantenimientos, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTecnicos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Tecnicos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N

        javax.swing.GroupLayout panelTecnicosLayout = new javax.swing.GroupLayout(panelTecnicos);
        panelTecnicos.setLayout(panelTecnicosLayout);
        panelTecnicosLayout.setHorizontalGroup(
            panelTecnicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTecnicosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboTecnicos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTecnicosLayout.setVerticalGroup(
            panelTecnicosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTecnicosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(comboTecnicos, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnActualizar.setText("Actualizar Lista");

        btnAsignarTecnico.setText("Asignar Tecnico");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTecnicos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelListaMantenimientos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAsignarTecnico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(textTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelListaMantenimientos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTecnicos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(btnAsignarTecnico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAsignarTecnico;
    private javax.swing.JComboBox<Persona> comboTecnicos;
    private javax.swing.JPanel panelListaMantenimientos;
    private javax.swing.JPanel panelTecnicos;
    private javax.swing.JScrollPane scrollMantenimientos;
    private javax.swing.JTable tablaMantenimientos;
    private javax.swing.JLabel textTitulo;
    // End of variables declaration//GEN-END:variables
}
