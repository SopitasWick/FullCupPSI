/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Formularios.paneles;

import Entidades.Extra;
import Entidades.Usuario;
import Entidades.Valoropcion;
import Facades.IFachadaExtraModel2;
import Facades.IFachadaValorOpcionModel;
import Formularios.paneles.elementos.JPanelExtra;
import Formularios.paneles.elementos.JPanelUsuario;
import Implementaciones.GestionarExtrasModel2;
import Implementaciones.GestionarValorOpcionesModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import utilerias.HintToTextField;

/**
 *
 * @author Sergio Arturo
 */
public class JPanelAdminExtras extends javax.swing.JPanel {
    
    
    private final IFachadaExtraModel2 fExtras = new GestionarExtrasModel2();

    
    Dimension dimension = null;
    
    List <Extra> listaExtras;
    
    
    Color colorSeleccion = Color.decode("#E0E0E0");
    Color colorNoSeleccion = Color.decode("#FFFFFF");
    Color colorHover = Color.decode("#F0F0F0");
    String seleccion = "Tipo vaso";
        
    
    Extra extraEditar;
    

    /**
     * Creates new form JPanelAdminCategorias
     */
    public JPanelAdminExtras() {
        initComponents();
        
        
        cargarDiseno();
        
        
        
        
        
    }
    
    
          

    
    private void cargarDiseno(){
    
        HintToTextField.addHint(txtBuscarUsuario, "Buscar Usuario");
        
        
       
    
        btnAccion.setText("Guardar");
        
        try {
            listaExtras = fExtras.obtenerTodosLosExtras();
            
            cargarExtras();
            
            
        } catch (Exception ex) {
            Logger.getLogger(JPanelAdminExtras.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }
    
    
    
 private void cargarExtras(){
    
    jPanelListaGeneral.removeAll();

    try{
        
        List<Extra> listaProvisional = new ArrayList<>();
        if(!listaProvisional.isEmpty()){
            listaProvisional.clear();
        }
        
        // Filtrar por tipo de extra Y por estado (activo/inactivo)
        for(int i = 0; i < listaExtras.size(); i++){
            Extra extra = listaExtras.get(i);
            
            // Verificar si coincide con el tipo seleccionado
            boolean coincideTipo = extra.getTipoExtra().equalsIgnoreCase(seleccion);
            
            // Verificar el estado según el checkbox
            boolean cumpleEstado;
            if(cbInactivas.isSelected()){
                // Si el checkbox está marcado, mostrar solo inactivos
                cumpleEstado = "inactivo".equalsIgnoreCase(extra.getEstado());
            } else {
                // Si no está marcado, mostrar solo activos
                cumpleEstado = "activo".equalsIgnoreCase(extra.getEstado());
            }
            
            // Agregar a la lista provisional si cumple ambas condiciones
            if(coincideTipo && cumpleEstado){
                listaProvisional.add(extra);
            }
        }
        
        int alturaProducto = 55; 
            
        for(int i = 0; i < listaProvisional.size(); i++){    
            
            JPanelExtra extra = new JPanelExtra();
            
            extra.setBounds(0, alturaProducto * i, 530, 45);

            extra.setAlignmentX(Component.LEFT_ALIGNMENT);
            extra.setBackground(Color.decode("#FFFFFF"));

            extra.getJblNombreExtra().setText(listaProvisional.get(i).getNombreExtra());
            extra.getJblPrecioExtra().setText(String.valueOf(listaProvisional.get(i).getPrecio()));

            int iProvisional = i;        

            extra.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    
                    if(listaProvisional.get(iProvisional).getTipoExtra().equalsIgnoreCase("Tipo vaso")){
                        cbTipoExtras.setSelectedIndex(0);
                    }
                    
                    if(listaProvisional.get(iProvisional).getTipoExtra().equalsIgnoreCase("Tipo leche")){
                        cbTipoExtras.setSelectedIndex(1);
                    }
                    
                    if(listaProvisional.get(iProvisional).getTipoExtra().equalsIgnoreCase("Tipo extra")){
                        cbTipoExtras.setSelectedIndex(2);
                    }
                    
                    txtNombreExtra.setText(listaProvisional.get(iProvisional).getNombreExtra());
                    jSpinnerPrecioExtra.setValue(listaProvisional.get(iProvisional).getPrecio());

                    cbTipoExtras.setEnabled(true);
                    txtNombreExtra.setEditable(true);
                    jSpinnerPrecioExtra.setEnabled(true);

                    // Si estamos viendo inactivos, cambiar el botón a "Reactivar"
                    if(cbInactivas.isSelected()){
                        btnAccion.setBackground(Color.decode("#10B981")); // Verde
                        btnAccion.setText("Reactivar");
                    } else {
                        btnAccion.setBackground(Color.decode("#111827"));
                        btnAccion.setText("Editar");
                    }
                    
                    extraEditar = listaProvisional.get(iProvisional);
                }

                @Override
                public void mouseEntered(MouseEvent e){
                    extra.setBackground(Color.decode("#E0E0E0"));
                }

                @Override
                public void mouseExited(MouseEvent e){
                    extra.setBackground(Color.decode("#FFFFFF"));
                }
            });

            extra.getJblEliminarExtra().addMouseListener(new MouseAdapter(){

                @Override
                public void mouseClicked(MouseEvent e){
                    if(listaProvisional.get(iProvisional).getTipoExtra().equalsIgnoreCase("Tipo vaso")){
                        cbTipoExtras.setSelectedIndex(0);
                    }
                    
                    if(listaProvisional.get(iProvisional).getTipoExtra().equalsIgnoreCase("Tipo leche")){
                        cbTipoExtras.setSelectedIndex(1);
                    }
                    
                    if(listaProvisional.get(iProvisional).getTipoExtra().equalsIgnoreCase("Tipo extra")){
                        cbTipoExtras.setSelectedIndex(2);
                    }
                    txtNombreExtra.setText(listaProvisional.get(iProvisional).getNombreExtra());
                    jSpinnerPrecioExtra.setValue(listaProvisional.get(iProvisional).getPrecio());

                    cbTipoExtras.setEnabled(false);
                    txtNombreExtra.setEditable(false);
                    jSpinnerPrecioExtra.setEnabled(false);
                    
                    btnAccion.setBackground(Color.red);
                    btnAccion.setText("Eliminar");
                    
                    extraEditar = listaProvisional.get(iProvisional);
                }
            });

           if(jPanelListaGeneral.getPreferredSize().height < alturaProducto * i){
                
                if(dimension == null){
                    dimension = new Dimension();
                }
                
                dimension.setSize(jPanelListaGeneral.getPreferredSize().width, alturaProducto * i);
                jPanelListaGeneral.setPreferredSize(dimension);
           }
                
            jPanelListaGeneral.add(extra);
        }
    
        jPanelListaGeneral.revalidate();
        jPanelListaGeneral.repaint();
    }
    catch(NumberFormatException e){
    }
}

// También agrega este método para reactivar extras
private void reactivarExtra(){
    if(extraEditar != null){
        int opcion = JOptionPane.showConfirmDialog(
            null,
            "¿Deseas reactivar este elemento?",
            "Confirmar Reactivación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                extraEditar.setEstado("activo");
                fExtras.actualizarExtra(extraEditar);
                
                JOptionPane.showMessageDialog(null, "Extra reactivado exitosamente");
                
                listaExtras = fExtras.obtenerTodosLosExtras();
                cargarExtras();
                
                // AGREGAR ESTA LÍNEA PARA LIMPIAR DESPUÉS DE REACTIVAR:
                limpiarFormulario();
                
            } catch (Exception ex) {
                Logger.getLogger(JPanelAdminExtras.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}


    
    
 /**
 * Valida que el nombre del extra no esté vacío
 * @return true si el nombre es válido, false en caso contrario
 */
private boolean validarNombreExtra() {
    String nombre = txtNombreExtra.getText().trim();
    
    if (nombre.isEmpty()) {
        JOptionPane.showMessageDialog(
            null,
            "El nombre del extra es obligatorio",
            "Error de validación",
            JOptionPane.ERROR_MESSAGE
        );
        txtNombreExtra.requestFocus();
        return false;
    }
    
    return true;
}

private void guardarExtra(){
    
    // Validación del nombre
    if (!validarNombreExtra()) {
        return;
    }
    
    Extra extra = new Extra();
    extra.setEstado("activo");
    extra.setNombreExtra(txtNombreExtra.getText());
    extra.setPrecio(((Number)jSpinnerPrecioExtra.getValue()).floatValue());
    extra.setTipoExtra(String.valueOf(cbTipoExtras.getSelectedItem()));
    
    try{
        fExtras.agregarExtra(extra);
        
        JOptionPane.showMessageDialog(null, "Extra agregado");
        
        listaExtras = fExtras.obtenerTodosLosExtras();
        cargarExtras();
        
        // AGREGAR ESTAS LÍNEAS PARA LIMPIAR DESPUÉS DE GUARDAR:
        limpiarFormulario();
    }
    catch(Exception e){
        e.printStackTrace();
    }
}

    
    private void editarExtra(){
    
    if(extraEditar != null){
        extraEditar.setNombreExtra(txtNombreExtra.getText());
        extraEditar.setPrecio(((Number)jSpinnerPrecioExtra.getValue()).floatValue());
        extraEditar.setTipoExtra(String.valueOf(cbTipoExtras.getSelectedItem()));
        
        try{
            fExtras.actualizarExtra(extraEditar);
            JOptionPane.showMessageDialog(this, "Extra editado");
            
            listaExtras = fExtras.obtenerTodosLosExtras();
            cargarExtras();
            
            // AGREGAR ESTA LÍNEA PARA LIMPIAR DESPUÉS DE EDITAR:
            limpiarFormulario();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
    
    private void eliminarExtra(){
    
    try{
        extraEditar.setEstado("inactivo");
        
        int opcion = JOptionPane.showConfirmDialog(
            null,
            "¿Estás seguro que deseas eliminar este elemento?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            try {
                extraEditar.setEstado("inactivo");
                
                fExtras.actualizarExtra(extraEditar);
                
                System.out.println("Elemento eliminado exitosamente.");
                JOptionPane.showMessageDialog(null, "El elemento ha sido eliminado.");
                
                listaExtras = fExtras.obtenerTodosLosExtras();
                cargarExtras();
                
                // AGREGAR ESTA LÍNEA PARA LIMPIAR DESPUÉS DE ELIMINAR:
                limpiarFormulario();

            } catch (Exception ex) {
                Logger.getLogger(JPanelAdminProductosCRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    catch(Exception e){
        e.printStackTrace();
    }
}
    private void limpiarFormulario(){
    // Habilitar todos los campos
    cbTipoExtras.setEnabled(true);
    txtNombreExtra.setEditable(true);
    jSpinnerPrecioExtra.setEnabled(true);
    
    // Limpiar los campos
    txtNombreExtra.setText("");
    jSpinnerPrecioExtra.setValue(0);
    cbTipoExtras.setSelectedIndex(0);
    
    // Configurar el botón para guardar
    btnAccion.setText("Guardar");
    btnAccion.setBackground(Color.decode("#111827"));
    
    // Limpiar la selección
    extraEditar = null;
}
    
    
    
    private void comprobarSeleccion(){
        
        switch (seleccion) {
            case "Tipo Vaso" -> {
                jPanelSeccionVasos.setBackground(colorSeleccion);
                
                jPanelSeccionExtras.setBackground(colorNoSeleccion);
                jPanelSeccionLeches.setBackground(colorNoSeleccion);
                
            }
            
            case "Tipo leche" -> {
                jPanelSeccionLeches.setBackground(colorSeleccion);
                
                jPanelSeccionExtras.setBackground(colorNoSeleccion);
                jPanelSeccionVasos.setBackground(colorNoSeleccion);
                
            }
                
            case "Tipo extra" -> {
                jPanelSeccionExtras.setBackground(colorSeleccion);
                
                jPanelSeccionLeches.setBackground(colorNoSeleccion);
                jPanelSeccionVasos.setBackground(colorNoSeleccion);
            }      
                
            
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

        txtBuscarUsuario = new javax.swing.JTextField();
        cbInactivas = new javax.swing.JCheckBox();
        jScrollPaneGeneral = new javax.swing.JScrollPane();
        jPanelListaGeneral = new javax.swing.JPanel();
        jPanelDatosUsuario = new javax.swing.JPanel();
        txtNombreExtra = new javax.swing.JTextField();
        jblRolUsuario = new javax.swing.JLabel();
        jblNombreUsuario = new javax.swing.JLabel();
        jblContrasenaUsuario = new javax.swing.JLabel();
        btnAccion = new javax.swing.JButton();
        jSpinnerPrecioExtra = new javax.swing.JSpinner();
        cbTipoExtras = new javax.swing.JComboBox<>();
        jPanelNavegacion = new javax.swing.JPanel();
        jPanelSeccionVasos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelSeccionLeches = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanelSeccionExtras = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnAccionNuevo = new javax.swing.JButton();

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
        txtBuscarUsuario.setBounds(20, 30, 560, 42);

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

        jScrollPaneGeneral.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneGeneral.setBorder(null);

        jPanelListaGeneral.setBackground(new java.awt.Color(255, 255, 255));
        jPanelListaGeneral.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelListaGeneral.setMinimumSize(new java.awt.Dimension(300, 20));
        jPanelListaGeneral.setPreferredSize(new java.awt.Dimension(490, 380));
        jPanelListaGeneral.setLayout(null);
        jScrollPaneGeneral.setViewportView(jPanelListaGeneral);

        add(jScrollPaneGeneral);
        jScrollPaneGeneral.setBounds(20, 140, 560, 430);

        jPanelDatosUsuario.setBackground(new java.awt.Color(255, 255, 255));
        jPanelDatosUsuario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelDatosUsuario.setLayout(null);
        jPanelDatosUsuario.add(txtNombreExtra);
        txtNombreExtra.setBounds(20, 140, 310, 30);

        jblRolUsuario.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jblRolUsuario.setForeground(new java.awt.Color(39, 24, 17));
        jblRolUsuario.setText("Precio");
        jblRolUsuario.setAlignmentX(16.0F);
        jblRolUsuario.setAlignmentY(0.0F);
        jPanelDatosUsuario.add(jblRolUsuario);
        jblRolUsuario.setBounds(20, 210, 70, 20);

        jblNombreUsuario.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jblNombreUsuario.setForeground(new java.awt.Color(39, 24, 17));
        jblNombreUsuario.setText("Tipo de extra");
        jblNombreUsuario.setAlignmentX(16.0F);
        jblNombreUsuario.setAlignmentY(0.0F);
        jPanelDatosUsuario.add(jblNombreUsuario);
        jblNombreUsuario.setBounds(20, 25, 170, 20);

        jblContrasenaUsuario.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jblContrasenaUsuario.setForeground(new java.awt.Color(39, 24, 17));
        jblContrasenaUsuario.setText("Nombre");
        jblContrasenaUsuario.setAlignmentX(16.0F);
        jblContrasenaUsuario.setAlignmentY(0.0F);
        jPanelDatosUsuario.add(jblContrasenaUsuario);
        jblContrasenaUsuario.setBounds(20, 115, 170, 20);

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
        btnAccion.setBounds(20, 270, 300, 40);
        jPanelDatosUsuario.add(jSpinnerPrecioExtra);
        jSpinnerPrecioExtra.setBounds(80, 205, 90, 30);

        cbTipoExtras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tipo vaso", "Tipo leche", "Tipo extra" }));
        jPanelDatosUsuario.add(cbTipoExtras);
        cbTipoExtras.setBounds(20, 50, 310, 30);

        add(jPanelDatosUsuario);
        jPanelDatosUsuario.setBounds(590, 140, 350, 330);

        jPanelNavegacion.setBackground(new java.awt.Color(255, 255, 255));
        jPanelNavegacion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelNavegacion.setLayout(new javax.swing.BoxLayout(jPanelNavegacion, javax.swing.BoxLayout.LINE_AXIS));

        jPanelSeccionVasos.setBackground(new java.awt.Color(224, 224, 224));
        jPanelSeccionVasos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelSeccionVasosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelSeccionVasosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelSeccionVasosMouseExited(evt);
            }
        });
        jPanelSeccionVasos.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Vasos");
        jPanelSeccionVasos.add(jLabel1);

        jPanelNavegacion.add(jPanelSeccionVasos);

        jPanelSeccionLeches.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelSeccionLeches.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelSeccionLechesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelSeccionLechesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelSeccionLechesMouseExited(evt);
            }
        });
        jPanelSeccionLeches.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Leches");
        jPanelSeccionLeches.add(jLabel2);

        jPanelNavegacion.add(jPanelSeccionLeches);

        jPanelSeccionExtras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelSeccionExtras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanelSeccionExtrasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanelSeccionExtrasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanelSeccionExtrasMouseExited(evt);
            }
        });
        jPanelSeccionExtras.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Extras");
        jPanelSeccionExtras.add(jLabel3);

        jPanelNavegacion.add(jPanelSeccionExtras);

        add(jPanelNavegacion);
        jPanelNavegacion.setBounds(20, 80, 560, 50);

        btnAccionNuevo.setBackground(new java.awt.Color(17, 24, 39));
        btnAccionNuevo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAccionNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnAccionNuevo.setText("Nuevo Extra");
        btnAccionNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccionNuevoActionPerformed(evt);
            }
        });
        add(btnAccionNuevo);
        btnAccionNuevo.setBounds(800, 90, 140, 40);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarUsuarioKeyTyped
        // TODO add your handling code here:
        
        if (txtBuscarUsuario.isFocusOwner() && evt.getKeyChar() == '\n') {
            
        }
        
    }//GEN-LAST:event_txtBuscarUsuarioKeyTyped

    private void cbInactivasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbInactivasActionPerformed

            // Recargar la lista de extras cuando se cambia el estado del checkbox
    try {
        listaExtras = fExtras.obtenerTodosLosExtras();
        dimension = null;
        cargarExtras();
    } catch (Exception ex) {
        Logger.getLogger(JPanelAdminExtras.class.getName()).log(Level.SEVERE, null, ex);
    }

        // TODO add your handling code here:

    }//GEN-LAST:event_cbInactivasActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        
        
        
    }//GEN-LAST:event_formMouseClicked

    private void btnAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccionActionPerformed
        // TODO add your handling code here:
        
      if(btnAccion.getText().equalsIgnoreCase("Guardar")){
        guardarExtra();
    }
    
    if(btnAccion.getText().equalsIgnoreCase("Editar")){
        editarExtra();
    }
    
    if(btnAccion.getText().equalsIgnoreCase("Eliminar")){
        eliminarExtra();
    }
    
    // AGREGAR ESTA NUEVA CONDICIÓN:
    if(btnAccion.getText().equalsIgnoreCase("Reactivar")){
        reactivarExtra();
    }
        
    }//GEN-LAST:event_btnAccionActionPerformed

    private void jPanelSeccionVasosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionVasosMouseClicked
        // TODO add your handling code here:
        
        seleccion = "Tipo vaso";
        
        comprobarSeleccion();
        
        dimension = null;
        cargarExtras();
        
    }//GEN-LAST:event_jPanelSeccionVasosMouseClicked

    private void jPanelSeccionLechesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionLechesMouseClicked
        // TODO add your handling code here:
        
        seleccion = "Tipo leche";
        
        comprobarSeleccion();
        
        dimension = null;
        cargarExtras();
    }//GEN-LAST:event_jPanelSeccionLechesMouseClicked

    private void jPanelSeccionExtrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionExtrasMouseClicked
        // TODO add your handling code here:
        
        seleccion = "Tipo extra";
        
        comprobarSeleccion();
        
        dimension = null;
        cargarExtras();
    }//GEN-LAST:event_jPanelSeccionExtrasMouseClicked

    private void jPanelSeccionVasosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionVasosMouseEntered
        // TODO add your handling code here:
        if(!seleccion.equalsIgnoreCase("Tipo vaso")){
            jPanelSeccionVasos.setBackground(colorHover);
        }
    }//GEN-LAST:event_jPanelSeccionVasosMouseEntered

    private void jPanelSeccionVasosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionVasosMouseExited
        // TODO add your handling code here:
        if(!seleccion.equalsIgnoreCase("Tipo vaso")){
            jPanelSeccionVasos.setBackground(colorNoSeleccion);
        }
    }//GEN-LAST:event_jPanelSeccionVasosMouseExited

    private void jPanelSeccionLechesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionLechesMouseEntered
        // TODO add your handling code here:
        if(!seleccion.equalsIgnoreCase("Tipo leche")){
            jPanelSeccionLeches.setBackground(colorHover);
        }
    }//GEN-LAST:event_jPanelSeccionLechesMouseEntered

    private void jPanelSeccionLechesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionLechesMouseExited
        // TODO add your handling code here:
        if(!seleccion.equalsIgnoreCase("Tipo leche")){
            jPanelSeccionLeches.setBackground(colorNoSeleccion);
        }
    }//GEN-LAST:event_jPanelSeccionLechesMouseExited

    private void jPanelSeccionExtrasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionExtrasMouseEntered
        // TODO add your handling code here:
        if(!seleccion.equalsIgnoreCase("Extra bebidas")){
            jPanelSeccionExtras.setBackground(colorHover);
        }
        
    }//GEN-LAST:event_jPanelSeccionExtrasMouseEntered

    private void jPanelSeccionExtrasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionExtrasMouseExited
        // TODO add your handling code here:
        if(!seleccion.equalsIgnoreCase("Extra bebidas")){
            jPanelSeccionExtras.setBackground(colorNoSeleccion);
        }
    }//GEN-LAST:event_jPanelSeccionExtrasMouseExited

    private void btnAccionNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccionNuevoActionPerformed
        // TODO add your handling code here:
        
        btnAccion.setText("Guardar");
        btnAccion.setBackground(Color.decode("#111827"));
        
        txtNombreExtra.setText("");
        
    }//GEN-LAST:event_btnAccionNuevoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccion;
    private javax.swing.JButton btnAccionNuevo;
    private javax.swing.JCheckBox cbInactivas;
    private javax.swing.JComboBox<String> cbTipoExtras;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanelDatosUsuario;
    private javax.swing.JPanel jPanelListaGeneral;
    private javax.swing.JPanel jPanelNavegacion;
    private javax.swing.JPanel jPanelSeccionExtras;
    private javax.swing.JPanel jPanelSeccionLeches;
    private javax.swing.JPanel jPanelSeccionVasos;
    private javax.swing.JScrollPane jScrollPaneGeneral;
    private javax.swing.JSpinner jSpinnerPrecioExtra;
    private javax.swing.JLabel jblContrasenaUsuario;
    private javax.swing.JLabel jblNombreUsuario;
    private javax.swing.JLabel jblRolUsuario;
    private javax.swing.JTextField txtBuscarUsuario;
    private javax.swing.JTextField txtNombreExtra;
    // End of variables declaration//GEN-END:variables
}
