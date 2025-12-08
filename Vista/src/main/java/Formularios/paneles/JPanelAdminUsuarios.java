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
import javax.swing.SwingUtilities;
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
        cargarUsuario("Todos", ""); // ← ahora se cargan desde BD al iniciar
        cargarDiseno();

    }

    private void cargarDiseno() {

        HintToTextField.addHint(txtBuscarUsuario, "Buscar Usuario");

        pruebas();

        btnAccion.setText("Guardar");

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
                txtContraUsuario.setText(prueba.getPassword());

                txtNombreUsuario.setEditable(true);
                txtContraUsuario.setEditable(true);

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

            for (int i = 0; i < listaProvicional.size(); i++) {

                Usuario u = listaProvicional.get(i);

                JPanelUsuario usuarioPanel = new JPanelUsuario();
                usuarioPanel.setBounds(0, alturaProducto * i, 772, 62);
                usuarioPanel.setBackground(Color.decode("#FFFFFF"));

                usuarioPanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if (e.getSource() == usuarioPanel.getJblEliminarUsuario()) {
                            return;
                        }

                        usuarioSeleccionadoId = u.getIdUsuario();

                        txtNombreUsuario.setText(u.getNombreUsuario());
                        txtContraUsuario.setText(u.getPassword());

                        txtNombreUsuario.setEditable(true);
                        txtContraUsuario.setEditable(true);
                        jRadioAdmin.setEnabled(true);
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
                                // ELIMINAR ROL ASOCIADO
                                for (Rol r : u.getRolList()) {
                                    ctrlRol.destroy(r.getIdRol());
                                }

                                // ELIMINAR USUARIO
                                ctrlUsuario.destroy(u.getIdUsuario());

                                javax.swing.JOptionPane.showMessageDialog(
                                        null,
                                        "El usuario \"" + u.getNombreUsuario() + "\" ha sido eliminado correctamente.",
                                        "Eliminado",
                                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                                );

                                // LIMPIAR CAMPOS DEL PANEL DERECHA
                                txtNombreUsuario.setText("");
                                txtContraUsuario.setText("");

                                txtNombreUsuario.setEditable(true);
                                txtContraUsuario.setEditable(true);
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
                        usuarioPanel.setBackground(Color.decode("#F8D7DA")); // hover rojo suave
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        usuarioPanel.setBackground(Color.decode("#FFFFFF"));
                    }

                });

                jPanelListaUsuarios.add(usuarioPanel);
            }

            jPanelListaUsuarios.revalidate();
            jPanelListaUsuarios.repaint();

            jblRolUsuario.setText("Usuarios: " + listaProvicional.size());

        } catch (Exception ex) {
            ex.printStackTrace();
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
        String pass = txtContraUsuario.getText().trim();
        String rol = jRadioAdmin.isSelected() ? "Administrador" : "Atendiente";

        if (nombre.isEmpty() || pass.isEmpty()) {
            System.out.println("Campos vacíos.");
            return;
        }

        try {
            Usuario u = new Usuario();
            u.setIdUsuario(generarIdUsuario());
            u.setNombreUsuario(nombre);
            u.setPassword(pass);

            ctrlUsuario.create(u);

            Rol r = new Rol();
            r.setIdRol(generarIdRol());
            r.setNombre(rol);
            r.setIdUsuario(u);

            ctrlRol.create(r);

            cargarUsuario("Todos", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editarUsuario() {

        if (usuarioSeleccionadoId == -1) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Selecciona un usuario de la lista.",
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {

            Usuario u = ctrlUsuario.findUsuario(usuarioSeleccionadoId);

            // DATOS ANTES
            String nombreAntes = u.getNombreUsuario();
            String passAntes = u.getPassword();
            String rolAntes = u.getRolList().isEmpty() ? "Sin rol" : u.getRolList().get(0).getNombre();

            // DATOS NUEVOS
            String nombreNuevo = txtNombreUsuario.getText().trim();
            String passNuevo = txtContraUsuario.getText().trim();
            String rolNuevo = jRadioAdmin.isSelected() ? "Administrador" : "Atendiente";

            // Confirmación
            int confirm = javax.swing.JOptionPane.showConfirmDialog(
                    null,
                    "¿Deseas EDITAR este usuario?\n\n"
                    + "ANTES:\n"
                    + "• Nombre: " + nombreAntes + "\n"
                    + "• Contraseña: " + passAntes + "\n"
                    + "• Rol: " + rolAntes + "\n\n"
                    + "DESPUÉS:\n"
                    + "• Nombre: " + nombreNuevo + "\n"
                    + "• Contraseña: " + passNuevo + "\n"
                    + "• Rol: " + rolNuevo,
                    "Confirmar edición",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE
            );

            if (confirm != javax.swing.JOptionPane.YES_OPTION) {
                return;
            }

            // EDITAR USUARIO
            u.setNombreUsuario(nombreNuevo);
            u.setPassword(passNuevo);
            ctrlUsuario.edit(u);

            // EDITAR ROL
            Rol r = u.getRolList().get(0);
            r.setNombre(rolNuevo);
            ctrlRol.edit(r);

            javax.swing.JOptionPane.showMessageDialog(null,
                    "Usuario editado correctamente.",
                    "Completado",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            // RESET
            usuarioSeleccionadoId = -1;
            btnAccion.setText("Guardar");
            txtNombreUsuario.setText("");
            txtContraUsuario.setText("");

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

            // Eliminar roles primero
            for (Rol r : u.getRolList()) {
                ctrlRol.destroy(r.getIdRol());
            }

            // Ahora eliminar usuario
            ctrlUsuario.destroy(u.getIdUsuario());

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

        String texto = txtBuscarUsuario.getText().trim();

        SwingUtilities.invokeLater(() -> {
            cargarUsuario("Todos", texto);
        });

        if (evt.getKeyChar() == '\n') {
            cargarUsuario("Todos", texto);
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

        if (btnAccion.getText().equalsIgnoreCase("Guardar")) {
            guardarUsuario();
        }

        if (btnAccion.getText().equalsIgnoreCase("Editar")) {
            editarUsuario();
        }

        if (btnAccion.getText().equalsIgnoreCase("Eliminar")) {
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
