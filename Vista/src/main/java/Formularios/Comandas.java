package Formularios;

import Entidades.Comanda;
import Facades.IFachadaComandasControlador;
import Implementaciones.GestionarComandaControlador;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.table.DefaultTableModel;

public class Comandas extends javax.swing.JFrame {

    private final IFachadaComandasControlador FComandas = new GestionarComandaControlador();
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    
    
    FrmListaProductos listaProductos;
    

    /**
     * Creates new form frmComandas
     */
    public Comandas() {
        initComponents();
        initCustom();
    }
// Método de inicialización personalizado

    public void initCustom() {
        cargarTablas();
        configurarPopupTablaActivas();
        configurarPopupTablaComandasCompletadas();
    }

// Carga los datos en ambas tablas
    private void cargarTablas() {
        cargarComandasActivas();
        cargarComandasCompletadas();
    }

    private void cargarComandasActivas() {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Productos", "Fecha", "Total", "Descripción"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try {
            List<Comanda> activas = FComandas.obtenerComandasActivas();//Cambiar aqui a patron facade
            for (Comanda c : activas) {
                String fecha = c.getFechaHoracomanda() != null ? sdf.format(c.getFechaHoracomanda()) : "";
           
                String productos = c.getDetallecomandaList() != null ? String.valueOf(c.getDetallecomandaList().size()) + " Productos" : "0 Productos";
                
                String descripcion = (c.getDescripcionGeneral() != null) ? c.getDescripcionGeneral() : "";
                modelo.addRow(new Object[]{c.getIdComanda(), productos, fecha, c.getTotalComanda(), descripcion});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error cargando comandas activas: " + ex.getMessage());
        }
        tblComandasActivas.setModel(modelo);
      
        tblComandasActivas.getColumnModel().getColumn(0).setMaxWidth(40);
        tblComandasActivas.getColumnModel().getColumn(3).setMaxWidth(300);
    }

    private void cargarComandasCompletadas() {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Productos", "Fecha", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        try {
            List<Comanda> completadas = FComandas.obtenerComandasCompletadas();
            for (Comanda c : completadas) {
                String fecha = c.getFechaHoracomanda() != null ? sdf.format(c.getFechaHoracomanda()) : "";
                String productos = c.getDetallecomandaList() != null ? String.valueOf(c.getDetallecomandaList().size()) + " Productos" : "0 Productos";
                modelo.addRow(new Object[]{c.getIdComanda(), productos, fecha, c.getTotalComanda()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error cargando comandas completadas: " + ex.getMessage());
        }
        tblComandasCompletadas.setModel(modelo);
        tblComandasCompletadas.getColumnModel().getColumn(0).setMaxWidth(80);
    }

    private void configurarPopupTablaActivas() {
        JPopupMenu popup = new JPopupMenu();

        JMenuItem itemCompletar = new JMenuItem("Marcar como completada");
        JMenuItem itemEliminar = new JMenuItem("Eliminar comanda");        
        JMenuItem itemEditar = new JMenuItem("Editar comanda");

        popup.add(itemCompletar);
        popup.add(itemEliminar);
        popup.add(itemEditar);

        // Listener para completar
        itemCompletar.addActionListener(e -> {
            Integer id = obtenerIdSeleccionado(tblComandasActivas);
            if (id != null) {
                try {
                   FComandas.comandaCompletada(id);
                    cargarTablas();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al completar: " + ex.getMessage());
                }
            }
        });

         // Listener para eliminar
        itemEliminar.addActionListener(e -> {
            Integer id = obtenerIdSeleccionado(tblComandasActivas);
            if (id != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "¿Seguro que deseas eliminar esta comanda?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        FComandas.comandaEliminada(id);
                        cargarTablas();

                        // Mensaje de confirmación
                        JOptionPane.showMessageDialog(this,
                                "La comanda fue eliminada correctamente.",
                                "Eliminación exitosa",
                                JOptionPane.INFORMATION_MESSAGE);

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,
                                "Error al eliminar la comanda: " + ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        
        //Listener para editar
        itemEditar.addActionListener(e -> {
            Integer id = obtenerIdSeleccionado(tblComandasActivas);
            if (id != null) {
                try {
                    
                   
                   listaProductos = new FrmListaProductos(this, id, false);
                   listaProductos.setVisible(true);
                   Comandas.this.setVisible(false);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al completar: " + ex.getMessage());
                }
            }
        });
        

        // Mostrar popup al hacer clic derecho
        tblComandasActivas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mostrarPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mostrarPopup(e);
            }

            private void mostrarPopup(MouseEvent e) {
                if (e.isPopupTrigger() && tblComandasActivas.getSelectedRow() >= 0) {
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
    
    
    
    private void configurarPopupTablaComandasCompletadas() {
        JPopupMenu popup = new JPopupMenu();
       
        JMenuItem itemVer = new JMenuItem("Ver comanda");

        popup.add(itemVer);

        
        
        //Listener para ver
        itemVer.addActionListener(e -> {
            Integer id = obtenerIdSeleccionado(tblComandasCompletadas);
            if (id != null) {
                try {
                    
                   
                   listaProductos = new FrmListaProductos(this, id, true);
                   listaProductos.setVisible(true);
                   Comandas.this.setVisible(false);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al completar: " + ex.getMessage());
                }
            }
        });
        

        // Mostrar popup al hacer clic derecho
        tblComandasCompletadas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mostrarPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mostrarPopup(e);
            }

            private void mostrarPopup(MouseEvent e) {
                if (e.isPopupTrigger() && tblComandasCompletadas.getSelectedRow() >= 0) {
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    /**
     * Obtiene el ID (columna 0) de la fila seleccionada en una tabla
     */
    private Integer obtenerIdSeleccionado(javax.swing.JTable tabla) {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila primero.");
            return null;
        }
        Object valor = tabla.getModel().getValueAt(fila, 0);
        if (valor instanceof Integer) {
            return (Integer) valor;
        }
        return Integer.valueOf(valor.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelFondo = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnNuevaComanda = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblComandasActivas = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblComandasCompletadas = new javax.swing.JTable();
        jPanelNavegacion = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanelEncabezado = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Comandas");
        setPreferredSize(new java.awt.Dimension(1440, 755));
        setResizable(false);
        setSize(new java.awt.Dimension(1440, 775));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanelFondo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFondo.setBorder(new javax.swing.border.MatteBorder(null));
        jPanelFondo.setLayout(null);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setAutoscrolls(true);
        jPanel4.setPreferredSize(new java.awt.Dimension(500, 592));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(55, 65, 81));
        jLabel2.setText("Comandas activas");

        btnNuevaComanda.setBackground(new java.awt.Color(17, 24, 39));
        btnNuevaComanda.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        btnNuevaComanda.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaComanda.setText("Nueva Comanda +");
        btnNuevaComanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaComandaActionPerformed(evt);
            }
        });

        tblComandasActivas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Comanda", "Productos", "Fecha", "Total", "Descripcion"
            }
        ));
        tblComandasActivas.setGridColor(new java.awt.Color(255, 255, 255));
        tblComandasActivas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblComandasActivasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblComandasActivas);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(144, 144, 144)
                        .addComponent(btnNuevaComanda))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnNuevaComanda))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelFondo.add(jPanel4);
        jPanel4.setBounds(132, 164, 500, 550);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setAutoscrolls(true);
        jPanel5.setPreferredSize(new java.awt.Dimension(500, 592));

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(55, 65, 81));
        jLabel3.setText("Comandas completadas");

        tblComandasCompletadas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Comanda", "Productos", "Fecha", "Total"
            }
        ));
        tblComandasCompletadas.setGridColor(new java.awt.Color(255, 255, 255));
        tblComandasCompletadas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblComandasCompletadasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblComandasCompletadas);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        jPanelFondo.add(jPanel5);
        jPanel5.setBounds(858, 164, 500, 550);

        jPanelNavegacion.setBackground(new java.awt.Color(255, 255, 255));
        jPanelNavegacion.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 1, new java.awt.Color(0, 0, 0)));
        jPanelNavegacion.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setText("Comandas");
        jPanelNavegacion.add(jLabel4);
        jLabel4.setBounds(29, 15, 60, 27);
        jPanelNavegacion.add(jSeparator1);
        jSeparator1.setBounds(28, 45, 1370, 10);

        jPanelFondo.add(jPanelNavegacion);
        jPanelNavegacion.setBounds(0, 50, 1436, 60);

        jPanelEncabezado.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEncabezado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelEncabezado.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(31, 41, 55));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setPreferredSize(new java.awt.Dimension(32, 32));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );

        jPanelEncabezado.add(jPanel3);
        jPanel3.setBounds(10, 10, 32, 32);

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(39, 24, 17));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Salir");
        jLabel7.setAlignmentX(16.0F);
        jLabel7.setAlignmentY(0.0F);
        jPanelEncabezado.add(jLabel7);
        jLabel7.setBounds(1330, 15, 80, 24);

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(39, 24, 17));
        jLabel8.setText("Full Cup");
        jLabel8.setAlignmentX(16.0F);
        jLabel8.setAlignmentY(0.0F);
        jPanelEncabezado.add(jLabel8);
        jLabel8.setBounds(50, 15, 310, 24);

        jPanelFondo.add(jPanelEncabezado);
        jPanelEncabezado.setBounds(0, 0, 1436, 50);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 1436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblComandasActivasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblComandasActivasMouseClicked
        // TODO add your handling code here:
        //Buscar elemento de la comanda y mostrar una ventana emergente (modificar/eliminar)
    }//GEN-LAST:event_tblComandasActivasMouseClicked

    private void tblComandasCompletadasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblComandasCompletadasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblComandasCompletadasMouseClicked

    private void btnNuevaComandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaComandaActionPerformed
        // TODO add your handling code here:
         // Limpiar la sesión anterior
    FrmListaProductos.comanda = new Comanda();
    FrmListaProductos.detalleComanda.clear();
    FrmListaProductos.idComanda = null;

    // Abrir nueva ventana
    listaProductos = new FrmListaProductos(this, null, false);
    listaProductos.setVisible(true);
    this.setVisible(false);
        
    }//GEN-LAST:event_btnNuevaComandaActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        initCustom();
    }//GEN-LAST:event_formComponentShown

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevaComanda;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelEncabezado;
    private javax.swing.JPanel jPanelFondo;
    private javax.swing.JPanel jPanelNavegacion;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblComandasActivas;
    private javax.swing.JTable tblComandasCompletadas;
    // End of variables declaration//GEN-END:variables
}
