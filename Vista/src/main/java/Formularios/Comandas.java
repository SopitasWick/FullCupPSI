package Formularios;

import Entidades.Comanda;
import Facades.IFachadaComandasControlador;
import Implementaciones.GestionarComandaControlador;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnNuevaComanda = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblComandasActivas = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombreCajero = new javax.swing.JLabel();
        txtCaja = new javax.swing.JLabel();
        txtTurno = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblComandasCompletadas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1440, 1024));

        jPanel1.setBackground(new java.awt.Color(242, 243, 245));
        jPanel1.setMaximumSize(new java.awt.Dimension(1440, 1024));
        jPanel1.setPreferredSize(new java.awt.Dimension(1440, 1024));

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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1440, 57));

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

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(39, 24, 17));
        jLabel1.setText("Café Express POS");
        jLabel1.setAlignmentX(16.0F);
        jLabel1.setAlignmentY(0.0F);

        txtNombreCajero.setText("Nombre");

        txtCaja.setText("Caja");

        txtTurno.setText("Turno");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTurno)
                .addGap(47, 47, 47)
                .addComponent(txtCaja)
                .addGap(60, 60, 60)
                .addComponent(txtNombreCajero)
                .addGap(75, 75, 75))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtNombreCajero)
                        .addComponent(txtCaja)
                        .addComponent(txtTurno))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

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
                .addContainerGap(51, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 226, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(312, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
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

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevaComanda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblComandasActivas;
    private javax.swing.JTable tblComandasCompletadas;
    private javax.swing.JLabel txtCaja;
    private javax.swing.JLabel txtNombreCajero;
    private javax.swing.JLabel txtTurno;
    // End of variables declaration//GEN-END:variables
}
