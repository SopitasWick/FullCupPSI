/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Formularios.paneles;

import Entidades.Categoria;
import Entidades.Producto;
import Facades.IFachadaCategoriaControlador;
import Facades.IFachadaProductoControlador;
import Formularios.FrmPanelAdministrador;
import Formularios.paneles.elementos.JPanelProducto;
import Implementaciones.GestionarCategoriaControlador;
import Implementaciones.GestionarProductoControlador;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;
import utilerias.ConstantesGUI;
import utilerias.HintToTextField;

/**
 *
 * @author Sergio Arturo
 */
public class JPanelAdminProductos extends javax.swing.JPanel {
    
    
    private final IFachadaCategoriaControlador fCategoria = new GestionarCategoriaControlador();
    private final IFachadaProductoControlador fProducto = new GestionarProductoControlador();
    
    Dimension dimension = null;
    
    List <Producto> listaProductos;
    List <Categoria> listaCategorias;
    
    
    
    JPanelAdminProductosCRUD productosCRUD;
    

    /**
     * Creates new form JPanelAdminCategorias
     */
    public JPanelAdminProductos() {
        initComponents();
        
        
        cargarDiseno();
        
        
        
        
        
    }
    
    
          

    
    private void cargarDiseno(){
    
        HintToTextField.addHint(txtBuscarProducto, "Buscar Producto");
        
        SwingUtilities.invokeLater(() -> {
            jLabelCategorias.requestFocusInWindow(); // El foco se va al label
        });
        
        
        
        //Estblecer para la lista de productos en jpanel
//        jScrollPaneProductos.setViewportView(jPanelListaProductos);
//        
//       // jPanelListaProductos.setLayout(new BoxLayout(jPanelListaProductos, BoxLayout.Y_AXIS));
//        
//        jPanelListaProductos.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
//
//        jPanelListaProductos.setAutoscrolls(true);

        
       // pruebas();
    
       
        cargarCategorias();
       
        cargarProductos("Todos", "");
        
    }
    
    
    private void cargarCategorias() {

        List<String> listaCategoriasString = new ArrayList<>();
        listaCategoriasString.add("Todos");

        try {
            listaCategorias = fCategoria.obtenerTodasLasCategorias();

            for (int i = 0; i < listaCategorias.size(); i++) {
                listaCategoriasString.add(listaCategorias.get(i).getNombre());
            }

            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();

            for (String nombre : listaCategoriasString) {
                modelo.addElement(nombre);
            }

            cbCategorias.setModel(modelo);

        } catch (Exception ex) {
            Logger.getLogger(JPanelAdminProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
    private void pruebas(){
        



        JPanelProducto producto1 = new JPanelProducto();
        producto1.setPreferredSize(new Dimension(820, 62));
        producto1.setMaximumSize(new Dimension(820, 62));
        producto1.setMinimumSize(new Dimension(820, 62));
        producto1.setAlignmentX(Component.LEFT_ALIGNMENT);

        
        JPanelProducto producto2 = new JPanelProducto();
        producto2.setPreferredSize(new Dimension(820, 62));
        producto2.setMaximumSize(new Dimension(820, 62));
        producto2.setMinimumSize(new Dimension(820, 62));
        producto2.setAlignmentX(Component.LEFT_ALIGNMENT);

        
        JPanelProducto producto3 = new JPanelProducto();
        producto3.setPreferredSize(new Dimension(820, 62));
        producto3.setMaximumSize(new Dimension(820, 62));
        producto3.setMinimumSize(new Dimension(820, 62));
        producto3.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanelProducto producto4 = new JPanelProducto();
        producto4.setPreferredSize(new Dimension(820, 62));
        producto4.setMaximumSize(new Dimension(820, 62));
        producto4.setMinimumSize(new Dimension(820, 62));
        producto4.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanelProducto producto5 = new JPanelProducto();
        producto5.setPreferredSize(new Dimension(820, 62));
        producto5.setMaximumSize(new Dimension(820, 62));
        producto5.setMinimumSize(new Dimension(820, 62));
        producto5.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanelProducto producto6 = new JPanelProducto();
        producto6.setPreferredSize(new Dimension(820, 62));
        producto6.setMaximumSize(new Dimension(820, 62));
        producto6.setMinimumSize(new Dimension(820, 62));
        producto6.setAlignmentX(Component.LEFT_ALIGNMENT);

        
        JPanelProducto producto7 = new JPanelProducto();
        producto7.setPreferredSize(new Dimension(820, 62));
        producto7.setMaximumSize(new Dimension(820, 62));
        producto7.setMinimumSize(new Dimension(820, 62));
        producto7.setAlignmentX(Component.LEFT_ALIGNMENT);

        
        JPanelProducto producto8 = new JPanelProducto();
        producto8.setPreferredSize(new Dimension(820, 62));
        producto8.setMaximumSize(new Dimension(820, 62));
        producto8.setMinimumSize(new Dimension(820, 62));
        producto8.setAlignmentX(Component.LEFT_ALIGNMENT);



        
        jPanelListaProductos.add(producto1);
        jPanelListaProductos.add(producto2);
        jPanelListaProductos.add(producto3);
        jPanelListaProductos.add(producto4);
        jPanelListaProductos.add(producto5);
        jPanelListaProductos.add(producto6);
        jPanelListaProductos.add(producto7);
        jPanelListaProductos.add(producto8);
        jPanelListaProductos.revalidate();
        jPanelListaProductos.repaint();
        
    
    }
    
    
    
    private void cargarProductos(String categoria, String textoBuscador){
        
        
        jPanelListaProductos.removeAll();
        
        try {
            
            if(listaProductos != null){
                listaProductos.clear();
            }
            listaProductos = fProducto.obtenerTodosLosProductos();
            
            List<Producto> listaProvicional = new ArrayList<>();
            
            
            
            
            //Aqui se filtra por categoria
            if(!categoria.equalsIgnoreCase("Todos")){
                listaProvicional.clear();
                
                for(int i = 0; i < listaProductos.size(); i++){
                    if(listaProductos.get(i).getIdCategoria().getNombre().equalsIgnoreCase(categoria)){
                        listaProvicional.add(listaProductos.get(i));
                    }
                }
                
            }
            
            else{
                listaProvicional = listaProductos;
            }
            
            


            if (!textoBuscador.trim().equalsIgnoreCase("")) {

                String txt = textoBuscador.toLowerCase();
                List<Producto> listaFiltradaBuscador = new ArrayList<>();

                for (Producto p : listaProvicional) {
                    if (p.getNombreProducto().toLowerCase().contains(txt)) {
                        listaFiltradaBuscador.add(p);
                    }
                }

                // Cambiar la lista provisional al resultado del filtro
                listaProvicional = listaFiltradaBuscador;
            }            
            
            
            
            int alturaProducto = 72; 

            
            for(int i = 0; i < listaProvicional.size(); i++){
                JPanelProducto producto1 = new JPanelProducto();
                producto1.setBounds(0, alturaProducto * i, 772, 62);
                producto1.setAlignmentX(Component.LEFT_ALIGNMENT);
                producto1.setBackground(Color.decode("#FFFFFF"));
                
                producto1.getJblNombreProducto().setText(listaProvicional.get(i).getNombreProducto());
                producto1.getJblNombreCategoria().setText(listaProvicional.get(i).getIdCategoria().getNombre());
                producto1.getJblPrecioProducto().setText(String.valueOf(listaProvicional.get(i).getPrecioProducto()));
                
                
   
                producto1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        
                      FrmPanelAdministrador.jPanelSeccion.removeAll();
  
                        
                      productosCRUD = new JPanelAdminProductosCRUD(ConstantesGUI.EDITAR);
                      productosCRUD.setBounds(0, 0, FrmPanelAdministrador.jPanelSeccion.getWidth(), FrmPanelAdministrador.jPanelSeccion.getHeight());
                            FrmPanelAdministrador.jPanelSeccion.add(productosCRUD);

                            FrmPanelAdministrador.jPanelSeccion.revalidate();
                            FrmPanelAdministrador.jPanelSeccion.repaint();

                    }

                    @Override
                    public void mouseEntered(MouseEvent e){
                        producto1.setBackground(Color.decode("#E0E0E0"));

                    }

                    @Override
                    public void mouseExited(MouseEvent e){
                        producto1.setBackground(Color.decode("#FFFFFF"));
                    }

                });
                
                
                producto1.getJblEliminarProducto().addMouseListener(new MouseAdapter(){
                
                    @Override
                    public void mouseClicked(MouseEvent e){
                        
                        FrmPanelAdministrador.jPanelSeccion.removeAll();

                        productosCRUD = new JPanelAdminProductosCRUD(ConstantesGUI.ELIMINAR);
                        productosCRUD.setBounds(0, 0, FrmPanelAdministrador.jPanelSeccion.getWidth(), FrmPanelAdministrador.jPanelSeccion.getHeight());
                        FrmPanelAdministrador.jPanelSeccion.add(productosCRUD);

                        FrmPanelAdministrador.jPanelSeccion.revalidate();
                        FrmPanelAdministrador.jPanelSeccion.repaint();
                    }
                
                });
                
                
               
                 if(jPanelListaProductos.getPreferredSize().height < alturaProducto * i){
                    
                    if(dimension == null){
                        dimension = new Dimension();
                    }
                    
                    dimension.setSize(jPanelListaProductos.getPreferredSize().width, alturaProducto * i);
                    jPanelListaProductos.setPreferredSize(dimension);

                }       
                
                jPanelListaProductos.add(producto1);

            }
         
            
           

        jPanelListaProductos.revalidate();
        jPanelListaProductos.repaint();
            
            
        jblCantidadProductos.setText("Productos: " + String.valueOf(listaProvicional.size()));
            
        } catch (Exception ex) {
            Logger.getLogger(JPanelAdminProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscarProducto = new javax.swing.JTextField();
        cbInactivas = new javax.swing.JCheckBox();
        jLabelCategorias = new javax.swing.JLabel();
        cbCategorias = new javax.swing.JComboBox<>();
        btnNuevoProducto = new javax.swing.JButton();
        jScrollPaneProductos = new javax.swing.JScrollPane();
        jPanelListaProductos = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jblCantidadProductos = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(965, 620));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(null);

        txtBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyTyped(evt);
            }
        });
        add(txtBuscarProducto);
        txtBuscarProducto.setBounds(20, 30, 630, 42);

        cbInactivas.setBackground(new java.awt.Color(255, 255, 255));
        cbInactivas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cbInactivas.setText("Inactivas");
        cbInactivas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbInactivasActionPerformed(evt);
            }
        });
        add(cbInactivas);
        cbInactivas.setBounds(720, 30, 170, 40);

        jLabelCategorias.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabelCategorias.setForeground(new java.awt.Color(39, 24, 17));
        jLabelCategorias.setText("Categorias");
        jLabelCategorias.setAlignmentX(16.0F);
        jLabelCategorias.setAlignmentY(0.0F);
        add(jLabelCategorias);
        jLabelCategorias.setBounds(20, 90, 90, 20);

        cbCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Deshabilitado" }));
        cbCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoriasActionPerformed(evt);
            }
        });
        add(cbCategorias);
        cbCategorias.setBounds(110, 90, 150, 22);

        btnNuevoProducto.setBackground(new java.awt.Color(17, 24, 39));
        btnNuevoProducto.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        btnNuevoProducto.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoProducto.setText("Nuevo Producto");
        btnNuevoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProductoActionPerformed(evt);
            }
        });
        add(btnNuevoProducto);
        btnNuevoProducto.setBounds(720, 90, 170, 28);

        jScrollPaneProductos.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneProductos.setBorder(null);

        jPanelListaProductos.setBackground(new java.awt.Color(255, 255, 255));
        jPanelListaProductos.setMinimumSize(new java.awt.Dimension(300, 200));
        jPanelListaProductos.setPreferredSize(new java.awt.Dimension(500, 200));
        jPanelListaProductos.setLayout(null);
        jScrollPaneProductos.setViewportView(jPanelListaProductos);

        add(jScrollPaneProductos);
        jScrollPaneProductos.setBounds(50, 160, 820, 430);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel1.setLayout(null);

        jblCantidadProductos.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jblCantidadProductos.setForeground(new java.awt.Color(39, 24, 17));
        jblCantidadProductos.setText("Productos (0)");
        jblCantidadProductos.setAlignmentX(16.0F);
        jblCantidadProductos.setAlignmentY(0.0F);
        jPanel1.add(jblCantidadProductos);
        jblCantidadProductos.setBounds(35, 10, 120, 20);

        add(jPanel1);
        jPanel1.setBounds(20, 124, 870, 480);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProductoKeyTyped
        // TODO add your handling code here:
        
        if (txtBuscarProducto.isFocusOwner() && evt.getKeyChar() == '\n') {
            
            String seleccion = cbCategorias.getSelectedItem() != null
                ? cbCategorias.getSelectedItem().toString()
                : "";

            try {
                cargarProductos(seleccion, txtBuscarProducto.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }//GEN-LAST:event_txtBuscarProductoKeyTyped

    private void cbInactivasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbInactivasActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbInactivasActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        
        
        
    }//GEN-LAST:event_formMouseClicked

    private void cbCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoriasActionPerformed
        // TODO add your handling code here:}
        
        String seleccion = cbCategorias.getSelectedItem() != null
                ? cbCategorias.getSelectedItem().toString()
                : "";
        
        
        
        cargarProductos(seleccion, txtBuscarProducto.getText());

    }//GEN-LAST:event_cbCategoriasActionPerformed

    private void btnNuevoProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProductoActionPerformed
        // TODO add your handling code here:
        FrmPanelAdministrador.jPanelSeccion.removeAll();
        
        productosCRUD = new JPanelAdminProductosCRUD(ConstantesGUI.NUEVO);
        productosCRUD.setBounds(0, 0, FrmPanelAdministrador.jPanelSeccion.getWidth(), FrmPanelAdministrador.jPanelSeccion.getHeight());
            FrmPanelAdministrador.jPanelSeccion.add(productosCRUD);

            FrmPanelAdministrador.jPanelSeccion.revalidate();
            FrmPanelAdministrador.jPanelSeccion.repaint();
                

    }//GEN-LAST:event_btnNuevoProductoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevoProducto;
    private javax.swing.JComboBox<String> cbCategorias;
    private javax.swing.JCheckBox cbInactivas;
    private javax.swing.JLabel jLabelCategorias;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelListaProductos;
    private javax.swing.JScrollPane jScrollPaneProductos;
    private javax.swing.JLabel jblCantidadProductos;
    private javax.swing.JTextField txtBuscarProducto;
    // End of variables declaration//GEN-END:variables
}
