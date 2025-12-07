/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Formularios.paneles;

import Entidades.Usuario;
import Formularios.paneles.elementos.JPanelUsuario;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import utilerias.ConstantesGUI;
import utilerias.HintToTextField;

/**
 *
 * @author Sergio Arturo
 */
public class JPanelAdminUsuarios extends javax.swing.JPanel {
    
    
    
    Dimension dimension = null;
    
    List <Usuario> listaUsuario;
        

    /**
     * Creates new form JPanelAdminCategorias
     */
    public JPanelAdminUsuarios() {
        initComponents();
        
        
        cargarDiseno();
        
        
        
        
        
    }
    
    
          

    
    private void cargarDiseno(){
    
        HintToTextField.addHint(txtBuscarUsuario, "Buscar Usuario");
        
        
        pruebas();
    
        btnAccion.setText("Guardar");
               
    }
    
    
    
    
    
    
    private void pruebas(){
        



        int alturaProducto = 72; 

        Usuario prueba = new Usuario();
        prueba.setNombreUsuario("Usuario de prueba");
        prueba.setPassword("Contra1234");
            
        JPanelUsuario usuario = new JPanelUsuario();
        usuario.setBounds(0, alturaProducto * 0, 474, 62);
        usuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        usuario.setBackground(Color.decode("#FFFFFF"));
                
                
                
        usuario.getJblNombreUsuario().setText(prueba.getNombreUsuario());
        usuario.getJblNombreRol().setText("Administrador");
                
                
   
        usuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtNombreUsuario.setText(prueba.getNombreUsuario());
                txtContraUsuario.setText(prueba.getPassword());
                
                
                
                txtNombreUsuario.setEditable(true);
                txtContraUsuario.setEditable(true);
                
                jRadioAdmin.setEnabled(true);
                jRadioAtendiente.setEnabled(true);
                
                btnAccion.setText("Editar");
                

            }

            @Override
            public void mouseEntered(MouseEvent e){
                usuario.setBackground(Color.decode("#E0E0E0"));

            }

            @Override
            public void mouseExited(MouseEvent e){
                usuario.setBackground(Color.decode("#FFFFFF"));
            }

        });
                
                
        usuario.getJblEliminarUsuario().addMouseListener(new MouseAdapter(){
                
            @Override
            public void mouseClicked(MouseEvent e){
                txtNombreUsuario.setText(prueba.getNombreUsuario());
                txtContraUsuario.setText(prueba.getPassword());
                
                txtNombreUsuario.setEditable(false);
                txtContraUsuario.setEditable(false);
                
                jRadioAdmin.setEnabled(false);
                jRadioAtendiente.setEnabled(false);
                
                btnAccion.setText("Eliminar");
                        
            }
                
        });
              
        
                
        



        
        jPanelListaUsuarios.add(usuario);

        jPanelListaUsuarios.revalidate();
        jPanelListaUsuarios.repaint();
        
    
    }
    
    
    
    private void cargarUsuario(String rol, String textoBuscador){
        
        
        jPanelListaUsuarios.removeAll();
        
        try {
            
            if(listaUsuario != null){
                listaUsuario.clear();
            }
            
            //aqui codigo para obtener todos los usuarios
           // listaUsuario = fUsuario.obtenerTodosLosUsuarios();
            
            List<Usuario> listaProvicional = new ArrayList<>();
            
            
            
            
            //Aqui se filtra por rol
//            if(!categoria.equalsIgnoreCase("Todos")){
//                listaProvicional.clear();
//                
//                for(int i = 0; i < listaProductos.size(); i++){
//                    if(listaProductos.get(i).getIdCategoria().getNombre().equalsIgnoreCase(categoria)){
//                        listaProvicional.add(listaProductos.get(i));
//                    }
//                }
//                
//            }
//            
//            else{
//                listaProvicional = listaProductos;
//            }
            
            


            if (!textoBuscador.trim().equalsIgnoreCase("")) {

                String txt = textoBuscador.toLowerCase();
                List<Usuario> listaFiltradaBuscador = new ArrayList<>();

                for (Usuario u : listaProvicional) {
                    if (u.getNombreUsuario().toLowerCase().contains(txt)) {
                        listaFiltradaBuscador.add(u);
                    }
                }

                // Cambiar la lista provisional al resultado del filtro
                listaProvicional = listaFiltradaBuscador;
            }            
            
            
            
            int alturaProducto = 72; 

            
            for(int i = 0; i < listaProvicional.size(); i++){
                JPanelUsuario usuario = new JPanelUsuario();
                usuario.setBounds(0, alturaProducto * i, 772, 62);
                usuario.setAlignmentX(Component.LEFT_ALIGNMENT);
                usuario.setBackground(Color.decode("#FFFFFF"));
                
                
                
               // usuario.getJblNombreUsuario().setText(listaProvicional.get(i).getNombreUsuario());
               // usuario.getJblNombreRol().setText(listaProvicional.get(i).get.getNombreRol());
                
                
   
                usuario.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                       

                    }

                    @Override
                    public void mouseEntered(MouseEvent e){
                        usuario.setBackground(Color.decode("#E0E0E0"));

                    }

                    @Override
                    public void mouseExited(MouseEvent e){
                        usuario.setBackground(Color.decode("#FFFFFF"));
                    }

                });
                
                
                usuario.getJblNombreUsuario().addMouseListener(new MouseAdapter(){
                
                    @Override
                    public void mouseClicked(MouseEvent e){
                        
                        
                    }
                
                });
                
                
               
                 if(jPanelListaUsuarios.getPreferredSize().height < alturaProducto * i){
                    
                    if(dimension == null){
                        dimension = new Dimension();
                    }
                    
                    dimension.setSize(jPanelListaUsuarios.getPreferredSize().width, alturaProducto * i);
                    jPanelListaUsuarios.setPreferredSize(dimension);

                }       
                
                jPanelListaUsuarios.add(usuario);

            }
         
            
           

        jPanelListaUsuarios.revalidate();
        jPanelListaUsuarios.repaint();
            
            
        jblRolUsuario.setText("Productos: " + String.valueOf(listaProvicional.size()));
            
        } catch (Exception ex) {
            Logger.getLogger(JPanelAdminUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
    private void guardarUsuario(){
    
    }
    
    private void editarUsuario(){
    
    }
    
    private void eliminarUsuario(){
    
        jPanelListaUsuarios.removeAll();
        
        jPanelListaUsuarios.revalidate();
        jPanelListaUsuarios.repaint();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtBuscarUsuario = new javax.swing.JTextField();
        cbInactivas = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jScrollPaneProductos = new javax.swing.JScrollPane();
        jPanelListaUsuarios = new javax.swing.JPanel();
        jPanelDatosUsuario = new javax.swing.JPanel();
        txtContraUsuario = new javax.swing.JTextField();
        txtNombreUsuario = new javax.swing.JTextField();
        jblRolUsuario = new javax.swing.JLabel();
        jblNombreUsuario = new javax.swing.JLabel();
        jblContrasenaUsuario = new javax.swing.JLabel();
        jRadioAtendiente = new javax.swing.JRadioButton();
        jRadioAdmin = new javax.swing.JRadioButton();
        btnAccion = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(965, 620));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(null);

        txtBuscarUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarUsuarioKeyTyped(evt);
            }
        });
        add(txtBuscarUsuario);
        txtBuscarUsuario.setBounds(20, 30, 540, 42);

        cbInactivas.setBackground(new java.awt.Color(255, 255, 255));
        cbInactivas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cbInactivas.setText("Inactivos");
        cbInactivas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbInactivasActionPerformed(evt);
            }
        });
        add(cbInactivas);
        cbInactivas.setBounds(590, 40, 170, 40);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel1.setLayout(null);

        jScrollPaneProductos.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneProductos.setBorder(null);

        jPanelListaUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        jPanelListaUsuarios.setMinimumSize(new java.awt.Dimension(300, 200));
        jPanelListaUsuarios.setPreferredSize(new java.awt.Dimension(500, 200));
        jPanelListaUsuarios.setLayout(null);
        jScrollPaneProductos.setViewportView(jPanelListaUsuarios);

        jPanel1.add(jScrollPaneProductos);
        jScrollPaneProductos.setBounds(20, 20, 500, 470);

        add(jPanel1);
        jPanel1.setBounds(20, 94, 540, 510);

        jPanelDatosUsuario.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDatosUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelDatosUsuario.setLayout(null);
        jPanelDatosUsuario.add(txtContraUsuario);
        txtContraUsuario.setBounds(20, 140, 310, 30);
        jPanelDatosUsuario.add(txtNombreUsuario);
        txtNombreUsuario.setBounds(20, 50, 310, 30);

        jblRolUsuario.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jblRolUsuario.setForeground(new java.awt.Color(39, 24, 17));
        jblRolUsuario.setText("Rol");
        jblRolUsuario.setAlignmentX(16.0F);
        jblRolUsuario.setAlignmentY(0.0F);
        jPanelDatosUsuario.add(jblRolUsuario);
        jblRolUsuario.setBounds(20, 210, 170, 20);

        jblNombreUsuario.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jblNombreUsuario.setForeground(new java.awt.Color(39, 24, 17));
        jblNombreUsuario.setText("Nombre de usuario");
        jblNombreUsuario.setAlignmentX(16.0F);
        jblNombreUsuario.setAlignmentY(0.0F);
        jPanelDatosUsuario.add(jblNombreUsuario);
        jblNombreUsuario.setBounds(20, 25, 170, 20);

        jblContrasenaUsuario.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jblContrasenaUsuario.setForeground(new java.awt.Color(39, 24, 17));
        jblContrasenaUsuario.setText("Contraseña");
        jblContrasenaUsuario.setAlignmentX(16.0F);
        jblContrasenaUsuario.setAlignmentY(0.0F);
        jPanelDatosUsuario.add(jblContrasenaUsuario);
        jblContrasenaUsuario.setBounds(20, 115, 170, 20);

        jRadioAtendiente.setText("Atendiente");
        jPanelDatosUsuario.add(jRadioAtendiente);
        jRadioAtendiente.setBounds(190, 240, 110, 21);

        jRadioAdmin.setText("Administrador");
        jPanelDatosUsuario.add(jRadioAdmin);
        jRadioAdmin.setBounds(40, 240, 110, 21);

        btnAccion.setBackground(new java.awt.Color(17, 24, 39));
        btnAccion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAccion.setForeground(new java.awt.Color(255, 255, 255));
        btnAccion.setText("Accion");
        btnAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccionActionPerformed(evt);
            }
        });
        jPanelDatosUsuario.add(btnAccion);
        btnAccion.setBounds(25, 320, 300, 40);

        add(jPanelDatosUsuario);
        jPanelDatosUsuario.setBounds(590, 94, 350, 380);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarUsuarioKeyTyped
        // TODO add your handling code here:
        
        if (txtBuscarUsuario.isFocusOwner() && evt.getKeyChar() == '\n') {
            
        }
        
    }//GEN-LAST:event_txtBuscarUsuarioKeyTyped

    private void cbInactivasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbInactivasActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cbInactivasActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        
        
        
    }//GEN-LAST:event_formMouseClicked

    private void btnAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccionActionPerformed
        // TODO add your handling code here:
        
        if(btnAccion.getText().equalsIgnoreCase("Guardar")){
            guardarUsuario();
        }
        
        if(btnAccion.getText().equalsIgnoreCase("Editar")){
            editarUsuario();
        }
        
        if(btnAccion.getText().equalsIgnoreCase("Eliminar")){
            eliminarUsuario();
        }
        
    }//GEN-LAST:event_btnAccionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccion;
    private javax.swing.JCheckBox cbInactivas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelDatosUsuario;
    private javax.swing.JPanel jPanelListaUsuarios;
    private javax.swing.JRadioButton jRadioAdmin;
    private javax.swing.JRadioButton jRadioAtendiente;
    private javax.swing.JScrollPane jScrollPaneProductos;
    private javax.swing.JLabel jblContrasenaUsuario;
    private javax.swing.JLabel jblNombreUsuario;
    private javax.swing.JLabel jblRolUsuario;
    private javax.swing.JTextField txtBuscarUsuario;
    private javax.swing.JTextField txtContraUsuario;
    private javax.swing.JTextField txtNombreUsuario;
    // End of variables declaration//GEN-END:variables
}
