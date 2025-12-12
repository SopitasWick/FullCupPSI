/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Formularios.paneles;

import Entidades.Rol;
import Entidades.Usuario;
import Formularios.paneles.elementos.JPanelUsuario;
import JPA.RolJpaController;
import JPA.UsuarioJpaController;
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
import javax.swing.SwingUtilities;
import org.mindrot.jbcrypt.BCrypt;
import utilerias.ConstantesGUI;
import utilerias.HintToTextField;

/**
 *
 * @author Sergio Arturo
 */
public class JPanelAdminUsuarios extends javax.swing.JPanel {

    UsuarioJpaController ctrlUsuario = new UsuarioJpaController();
    RolJpaController ctrlRol = new RolJpaController();
    private int usuarioSeleccionadoId = -1;

    Dimension dimension = null;

    List<Usuario> listaUsuario;

    /**
     * Creates new form JPanelAdminCategorias
     */
    public JPanelAdminUsuarios() {
        initComponents();
        cargarUsuario("Todos", "");
        cargarDiseno();

    }

    private void cargarDiseno() {

        HintToTextField.addHint(txtBuscarUsuario, "Buscar Usuario");

        //pruebas();

        btnAccion.setText("Guardar");
        
        cargarUsuario("", "");

    }

    private void pruebas() {

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
                pwdContra.setText(prueba.getPassword());

                txtNombreUsuario.setEditable(true);
                pwdContra.setEditable(true);

                jRadioAdmin.setEnabled(true);
                jRadioAtendiente.setEnabled(true);

                btnAccion.setText("Editar");

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                usuario.setBackground(Color.decode("#E0E0E0"));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                usuario.setBackground(Color.decode("#FFFFFF"));
            }

        });

        usuario.getJblEliminarUsuario().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                txtNombreUsuario.setText(prueba.getNombreUsuario());
                pwdContra.setText(prueba.getPassword());

                txtNombreUsuario.setEditable(false);
                pwdContra.setEditable(false);

                jRadioAdmin.setEnabled(false);
                jRadioAtendiente.setEnabled(false);

                btnAccion.setText("Eliminar");

            }

        });

        jPanelListaUsuarios.add(usuario);

        jPanelListaUsuarios.revalidate();
        jPanelListaUsuarios.repaint();

    }

    private void cargarUsuario(String rol, String textoBuscador) {

        jPanelListaUsuarios.removeAll();

        try {

            // TRAER TODOS LOS USUARIOS DE BD
            List<Usuario> listaProvicional = ctrlUsuario.findUsuarioEntities();

            // FILTRO DEL BUSCADOR
            if (!textoBuscador.trim().equalsIgnoreCase("")) {
                String txt = textoBuscador.toLowerCase();
                List<Usuario> listaFiltradaBuscador = new ArrayList<>();

                for (Usuario u : listaProvicional) {
                    if (u.getNombreUsuario().toLowerCase().contains(txt)) {
                        listaFiltradaBuscador.add(u);
                    }
                }

                listaProvicional = listaFiltradaBuscador;
            }

            int alturaProducto = 72;

            // FILTRO ACTIVOS / INACTIVOS
            List<Usuario> listaFiltradaEstado = new ArrayList<>();

            boolean mostrarInactivos = cbInactivas.isSelected();

            for (Usuario u : listaProvicional) {
                if (mostrarInactivos) {
                    if (u.getEstado() != null && u.getEstado() == 0) {
                        listaFiltradaEstado.add(u);
                    }
                } else {
                    if (u.getEstado() == null || u.getEstado() == 1) {
                        listaFiltradaEstado.add(u);
                    }
                }
            }

            listaProvicional = listaFiltradaEstado;

            for (int i = 0; i < listaProvicional.size(); i++) {

                Usuario u = listaProvicional.get(i);

                JPanelUsuario usuarioPanel = new JPanelUsuario();
                usuarioPanel.setBounds(0, alturaProducto * i, 472, 62);
                usuarioPanel.setBackground(Color.decode("#FFFFFF"));

                if (u.getEstado() != null && u.getEstado() == 0) {

                    // Fondo gris claro
                    usuarioPanel.setBackground(Color.decode("#E0E0E0"));

                    // Cambiar color del texto
                    usuarioPanel.getJblNombreUsuario().setForeground(Color.decode("#555555"));
                    usuarioPanel.getJblNombreRol().setForeground(Color.decode("#555555"));
                }

                usuarioPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if (e.getSource() == usuarioPanel.getJblEliminarUsuario()) {
                            return;
                        }
                        if (u.getEstado() != null && u.getEstado() == 0) {
                            btnAccion.setText("Activar");
                        } else {
                            btnAccion.setText("Editar");
                        }

                        usuarioSeleccionadoId = u.getIdUsuario();

                        txtNombreUsuario.setText(u.getNombreUsuario());
                        //pwdContra.setText(u.getPassword());

                        txtNombreUsuario.setEditable(true);
                        pwdContra.setEditable(true);
                        pwdContra.setEnabled(true);
                        jRadioAtendiente.setEnabled(true);

                        if (!u.getRolList().isEmpty()) {
                            String r = u.getRolList().get(0).getNombre();
                            if (r.equalsIgnoreCase("Administrador")) {
                                jRadioAdmin.setSelected(true);
                            } else {
                                jRadioAtendiente.setSelected(true);
                            }
                        }

                        btnAccion.setText("Editar");
                    }
                });

                // MOSTRAR NOMBRE
                usuarioPanel.getJblNombreUsuario().setText(u.getNombreUsuario());

                // MOSTRAR ROL DEL USUARIO
                if (!u.getRolList().isEmpty()) {
                    usuarioPanel.getJblNombreRol().setText(u.getRolList().get(0).getNombre());
                } else {
                    usuarioPanel.getJblNombreRol().setText("Sin rol");
                }

                usuarioPanel.getJblEliminarUsuario().addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        // PANEL DE CONFIRMACIÓN
                        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                                null,
                                "¿Seguro que deseas eliminar al usuario:\n\n  ➤ " + u.getNombreUsuario() + " ?",
                                "Confirmar eliminación",
                                javax.swing.JOptionPane.YES_NO_OPTION,
                                javax.swing.JOptionPane.WARNING_MESSAGE
                        );

                        if (confirm == javax.swing.JOptionPane.YES_OPTION) {

                            try {
                                // Eliminación lógica
                                u.setEstado(0);
                                ctrlUsuario.edit(u);

                                javax.swing.JOptionPane.showMessageDialog(
                                        null,
                                        "El usuario \"" + u.getNombreUsuario() + "\" ha sido eliminado correctamente.",
                                        "Eliminado",
                                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                                );

                                // LIMPIAR CAMPOS DEL PANEL DERECHA
                                txtNombreUsuario.setText("");
                                pwdContra.setText("");

                                txtNombreUsuario.setEditable(true);
                                pwdContra.setEditable(true);
                                jRadioAdmin.setEnabled(true);
                                jRadioAtendiente.setEnabled(true);

                                btnAccion.setText("Guardar");

                                // RECARGAR LISTA LADO IZQUIERDO
                                cargarUsuario("Todos", "");

                            } catch (Exception ex) {
                                javax.swing.JOptionPane.showMessageDialog(
                                        null,
                                        "Error al eliminar el usuario.",
                                        "Error",
                                        javax.swing.JOptionPane.ERROR_MESSAGE
                                );
                            }
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (u.getEstado() != null && u.getEstado() == 0) {
                            usuarioPanel.setBackground(Color.decode("#D6D6D6")); // gris más fuerte
                        } else {
                            usuarioPanel.setBackground(Color.decode("#E0E0E0"));
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (u.getEstado() != null && u.getEstado() == 0) {
                            usuarioPanel.setBackground(Color.decode("#E0E0E0")); // gris normal para inactivo
                        } else {
                            usuarioPanel.setBackground(Color.decode("#FFFFFF"));
                        }
                    }

                });

                jPanelListaUsuarios.add(usuarioPanel);

                jPanelListaUsuarios.setPreferredSize(new Dimension(
                        jPanelListaUsuarios.getWidth(),
                        listaProvicional.size() * 72
                ));
            }

            jPanelListaUsuarios.revalidate();
            jPanelListaUsuarios.repaint();

            jblRolUsuario.setText("Usuarios: " + listaProvicional.size());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void activarUsuario() {
        try {
            String nombre = txtNombreUsuario.getText().trim();

            Usuario u = ctrlUsuario.findUsuarioByNombre(nombre);

            if (u == null) {
                System.out.println("Usuario no encontrado");
                return;
            }

            u.setEstado(1);
            ctrlUsuario.edit(u);

            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "Usuario reactivado correctamente.",
                    "Reactivado",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            cargarUsuario("Todos", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int generarIdUsuario() {
        List<Usuario> lista = ctrlUsuario.findUsuarioEntities();
        if (lista.isEmpty()) {
            return 1;
        }
        return lista.stream()
                .mapToInt(Usuario::getIdUsuario)
                .max()
                .getAsInt() + 1;
    }

    private int generarIdRol() {
        List<Rol> lista = ctrlRol.findRolEntities();
        if (lista.isEmpty()) {
            return 1;
        }
        return lista.stream()
                .mapToInt(Rol::getIdRol)
                .max()
                .getAsInt() + 1;
    }

    private void guardarUsuario() {

        String nombre = txtNombreUsuario.getText().trim();
        char[] passwordChars = pwdContra.getPassword();
        String pass = new String(passwordChars);
        String rol = jRadioAdmin.isSelected() ? "Administrador" : "Atendiente";

        if (nombre.isEmpty() || pass.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Completa todos los campos.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // CONFIRMACIÓN
        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                null,
                "¿Deseas GUARDAR este usuario?\n\n"
                + "• Nombre: " + nombre + "\n"
                + "• Contraseña: " + pass + "\n"
                + "• Rol: " + rol,
                "Confirmar registro",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );

        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Usuario u = new Usuario();
            u.setIdUsuario(generarIdUsuario());
            u.setNombreUsuario(nombre);
            u.setPassword(pass);
            u.setEstado(1);
            
            if(!ctrlUsuario.existeUsuarioPorNombre(nombre)){

                ctrlUsuario.create(u);

                Rol r = new Rol();
                r.setIdRol(generarIdRol());
                r.setNombre(rol);
                r.setIdUsuario(u);

                ctrlRol.create(r);

                javax.swing.JOptionPane.showMessageDialog(null,
                        "Usuario registrado correctamente.",
                        "Completado",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);

                cargarUsuario("Todos", "");
            }
            else{
                JOptionPane.showMessageDialog(this, "El nombre de usuario ya esta en uso");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private void editarUsuario() {

        try {
            Usuario u = ctrlUsuario.findUsuario(usuarioSeleccionadoId);

            String nombreAntes = u.getNombreUsuario();
            String nombreNuevo = txtNombreUsuario.getText().trim();
            String password = new String(pwdContra.getPassword()).trim(); // contraseña nueva

            // --------------------------
            // Verificar si el nuevo nombre ya existe en otros usuarios
            // --------------------------
            boolean nombreDuplicado = false;
            List<Usuario> usuarios = ctrlUsuario.findUsuarioEntities();
            for (Usuario usuario : usuarios) {
                if (!usuario.getIdUsuario().equals(u.getIdUsuario()) // ignorar el mismo usuario
                        && usuario.getNombreUsuario().equalsIgnoreCase(nombreNuevo)) {
                    nombreDuplicado = true;
                    break;
                }
            }

            if (nombreDuplicado) {
                JOptionPane.showMessageDialog(this, "El nombre de usuario ya está en uso");
                return;
            }

            // --------------------------
            // Confirmación
            // --------------------------
            int confirm = javax.swing.JOptionPane.showConfirmDialog(
                    null,
                    "¿Deseas EDITAR este usuario?\n\n"
                    + "ANTES:\n"
                    + "• Nombre: " + nombreAntes + "\n\n"
                    + "DESPUÉS:\n"
                    + "• Nombre: " + nombreNuevo + "\n"
                    + "• Contraseña: " + (password.isEmpty() ? "(sin cambios)" : "******"),
                    "Confirmar edición",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE
            );

            if (confirm != javax.swing.JOptionPane.YES_OPTION) {
                return;
            }

            // --------------------------
            // Editar usuario
            // --------------------------
            u.setNombreUsuario(nombreNuevo);
            u.setPassword(password.isEmpty() ? null : password); // null indica sin cambios
            ctrlUsuario.edit(u);

            javax.swing.JOptionPane.showMessageDialog(null,
                    "Usuario editado correctamente.",
                    "Completado",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            // Reset UI
            usuarioSeleccionadoId = -1;
            btnAccion.setText("Guardar");
            txtNombreUsuario.setText("");
            pwdContra.setText("");

            cargarUsuario("Todos", "");

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Error al editar el usuario.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }


    private void eliminarUsuario() {

        try {
            String nombre = txtNombreUsuario.getText().trim();

            Usuario u = ctrlUsuario.findUsuarioByNombre(nombre);

            if (u == null) {
                System.out.println("Usuario no encontrado");
                return;
            }

            // Marcar como inactivo
            u.setEstado(0);

            ctrlUsuario.edit(u);

            javax.swing.JOptionPane.showMessageDialog(
                    null,
                    "El usuario fue marcado como INACTIVO.",
                    "Actualizado",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            cargarUsuario("Todos", "");

        } catch (Exception e) {
            e.printStackTrace();
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPaneProductos = new javax.swing.JScrollPane();
        jPanelListaUsuarios = new javax.swing.JPanel();
        jPanelDatosUsuario = new javax.swing.JPanel();
        txtNombreUsuario = new javax.swing.JTextField();
        jblRolUsuario = new javax.swing.JLabel();
        jblNombreUsuario = new javax.swing.JLabel();
        jblContrasenaUsuario = new javax.swing.JLabel();
        jRadioAtendiente = new javax.swing.JRadioButton();
        jRadioAdmin = new javax.swing.JRadioButton();
        btnAccion = new javax.swing.JButton();
        pwdContra = new javax.swing.JPasswordField();
        cbMostrarContra = new javax.swing.JCheckBox();

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
        jPanelDatosUsuario.add(pwdContra);
        pwdContra.setBounds(20, 150, 310, 30);

        cbMostrarContra.setText("Mostrar contraseña");
        cbMostrarContra.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cbMostrarContra.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        cbMostrarContra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMostrarContraActionPerformed(evt);
            }
        });
        jPanelDatosUsuario.add(cbMostrarContra);
        cbMostrarContra.setBounds(179, 125, 150, 20);

        add(jPanelDatosUsuario);
        jPanelDatosUsuario.setBounds(590, 94, 350, 380);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarUsuarioKeyTyped

        String texto = txtBuscarUsuario.getText().trim();

        SwingUtilities.invokeLater(() -> {
            cargarUsuario("Todos", texto);
        });

        if (evt.getKeyChar() == '\n') {
            cargarUsuario("Todos", texto);
        }

    }//GEN-LAST:event_txtBuscarUsuarioKeyTyped

    private void cbInactivasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbInactivasActionPerformed
        cargarUsuario("Todos", txtBuscarUsuario.getText().trim());

    }//GEN-LAST:event_cbInactivasActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_formMouseClicked

    private void btnAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccionActionPerformed
        // TODO add your handling code here:

        if (btnAccion.getText().equalsIgnoreCase("Guardar")) {
            guardarUsuario();
        }

        if (btnAccion.getText().equalsIgnoreCase("Editar")) {
            editarUsuario();
        }

        if (btnAccion.getText().equalsIgnoreCase("Eliminar")) {
            eliminarUsuario();
        }
        if (btnAccion.getText().equalsIgnoreCase("Activar")) {
            activarUsuario();
        }
    }//GEN-LAST:event_btnAccionActionPerformed

    private void cbMostrarContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMostrarContraActionPerformed
        // TODO add your handling code here:
        if(cbMostrarContra.isSelected()){
            pwdContra.setEchoChar((char)0);
        }
        else{
            pwdContra.setEchoChar('*');
        }
        
    }//GEN-LAST:event_cbMostrarContraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccion;
    private javax.swing.JCheckBox cbInactivas;
    private javax.swing.JCheckBox cbMostrarContra;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelDatosUsuario;
    private javax.swing.JPanel jPanelListaUsuarios;
    private javax.swing.JRadioButton jRadioAdmin;
    private javax.swing.JRadioButton jRadioAtendiente;
    private javax.swing.JScrollPane jScrollPaneProductos;
    private javax.swing.JLabel jblContrasenaUsuario;
    private javax.swing.JLabel jblNombreUsuario;
    private javax.swing.JLabel jblRolUsuario;
    private javax.swing.JPasswordField pwdContra;
    private javax.swing.JTextField txtBuscarUsuario;
    private javax.swing.JTextField txtNombreUsuario;
    // End of variables declaration//GEN-END:variables
}
