/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import Entidades.Categoria;
import Entidades.Comanda;
import Entidades.Producto;
import Facades.IFachadaCategoriaControlador;
import Facades.IFachadaProductoControlador;
import Implementaciones.GestionarCategoriaControlador;
import Implementaciones.GestionarProductoControlador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import utilerias.HintToTextField;


/**
 *
 * @author Sergio Arturo
 */
public class FrmAdministrarCategorias extends javax.swing.JFrame {

  
    FrmPanelAdministrador admin;
    private final IFachadaCategoriaControlador fCategoria = new GestionarCategoriaControlador();
    private final IFachadaProductoControlador fProducto = new GestionarProductoControlador();
    
    private TableRowSorter<DefaultTableModel> sorter;
    
    Categoria categoriaUtil;
    List <Categoria> listaCategoriaUtil;
    Producto productoUtil;
    
    /**
     * Creates new form FrmDetallesProductos
     */
    public FrmAdministrarCategorias(FrmPanelAdministrador admin) {
        initComponents();
       
        
        this.admin = admin;
        
        
        cargarDiseno();
        cargarTablas();
        agregarListenersTabla();
        radioActivoEditar.setSelected(true);
        
        txtBuscarCategoria.getDocument().addDocumentListener(new DocumentListener() {

    @Override
    public void insertUpdate(DocumentEvent e) {
        filtrarTabla();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        filtrarTabla();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        filtrarTabla();
    }
});

        
    }
    
    private void filtrarTabla() {
    String texto = txtBuscarCategoria.getText().trim();

    if (texto.length() == 0) {
        sorter.setRowFilter(null);  // sin filtro
    } else {
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
    }
}

    
    private void cargarDiseno(){
    
        HintToTextField.addHint(txtBuscarCategoria, "Buscar Categoria");
        HintToTextField.addHint(txtNombreCategoria, "Nombre de la Categoria");
        
        SwingUtilities.invokeLater(() -> {
            jLabel2.requestFocusInWindow(); // El foco se va al label
        });
        
        btnEditarCategoria.setVisible(false);
        btnEliminarCategoria.setVisible(false);
        jSeparator1.setVisible(false);
        jSeparator2.setVisible(false);
        radioActivoEditar.setVisible(false);
        radioInactivoEditar.setVisible(false);
        
        
        
               
        
    }
    
    private void cargarTablas() {
        cargarCategoriasActivas();
    }
    private void cargarCategoriasActivas() {
    // Definir columnas
    String[] columnas = {"Id_Categoria", "Nombre_Categoria", "Estado_Categoria"};

    // Crear modelo no editable
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    try {
        // Obtener categorías
        List<Categoria> categorias = fCategoria.obtenerTodasLasCategorias();

        for (Categoria c : categorias) {
            if (c.getEstado() != null && c.getEstado().equalsIgnoreCase("activo")) {
                modelo.addRow(new Object[]{
                    c.getIdCategoria(),
                    c.getNombre(),
                    c.getEstado()
                });
            }
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
            "Error cargando categorías: " + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    // Asignar modelo a la tabla
    tableCategorias.setModel(modelo);

    // Ajustar ancho de columnas
    TableColumnModel columnModel = tableCategorias.getColumnModel();
    columnModel.getColumn(0).setPreferredWidth(80);   // Id_Categoria
    columnModel.getColumn(1).setPreferredWidth(300);  // Nombre_Categoria
    columnModel.getColumn(2).setPreferredWidth(300); //Estado_Categoria

    // Evitar reordenamiento de columnas
    tableCategorias.getTableHeader().setReorderingAllowed(false);

    // Selección de solo una fila
    tableCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    sorter = new TableRowSorter<>((DefaultTableModel) tableCategorias.getModel());
    tableCategorias.setRowSorter(sorter);

}
    
    private void cargarCategoriasInactivas() {
    // Definir columnas
    String[] columnas = {"Id_Categoria", "Nombre_Categoria", "Estado_Categoria"};

    // Crear modelo no editable
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    try {
        // Obtener todas las categorías
        List<Categoria> categorias = fCategoria.obtenerTodasLasCategorias();

        // Filtrar SOLO las inactivas
        for (Categoria c : categorias) {

            if (c.getEstado() != null && c.getEstado().equalsIgnoreCase("inactivo")) {
                modelo.addRow(new Object[]{
                    c.getIdCategoria(),
                    c.getNombre(),
                    c.getEstado()
                });
            }
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
            "Error cargando categorías inactivas: " + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }

    // Asignar modelo a la tabla
    tableCategorias.setModel(modelo);

    // Ajustar ancho de columnas
    TableColumnModel columnModel = tableCategorias.getColumnModel();
    columnModel.getColumn(0).setPreferredWidth(80);
    columnModel.getColumn(1).setPreferredWidth(300);
    columnModel.getColumn(2).setPreferredWidth(300);

    // Evitar reordenamiento
    tableCategorias.getTableHeader().setReorderingAllowed(false);

    // Selección de solo una fila
    tableCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    
    sorter = new TableRowSorter<>((DefaultTableModel) tableCategorias.getModel());
    tableCategorias.setRowSorter(sorter);
    
    
}

    
    private void agregarListenersTabla() {
    tableCategorias.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        int fila = tableCategorias.getSelectedRow();
        if (fila != -1) {
            String nombre = tableCategorias.getValueAt(fila, 1).toString();
            String estado = tableCategorias.getValueAt(fila, 2).toString();
            txtNombreCategoria.setText(nombre);  
            
            if(estado.equalsIgnoreCase("activo")){
                radioActivoEditar.setSelected(true);
                radioInactivoEditar.setSelected(false);
            }
            else{
                radioActivoEditar.setSelected(false);
                radioInactivoEditar.setSelected(true);
            }
            

            if(cbInactivas.isSelected()){
                btnEliminarCategoria.setVisible(false);
                btnEditarCategoria.setVisible(true);
                radioActivoEditar.setVisible(true);
                radioInactivoEditar.setVisible(true);
            }
            else{

                btnEditarCategoria.setVisible(true);
                btnEliminarCategoria.setVisible(true);
                jSeparator1.setVisible(true);
                jSeparator2.setVisible(true);
//                radioActivoEditar.setVisible(true);
//                radioInactivoEditar.setVisible(true);

                btnNuevaCategoria.setVisible(false);
            }

        }
        else {
            btnEditarCategoria.setVisible(false);
            btnEliminarCategoria.setVisible(false);
            jSeparator1.setVisible(false);
            jSeparator2.setVisible(false);
            radioActivoEditar.setVisible(false);
            radioInactivoEditar.setVisible(false);
            
            btnNuevaCategoria.setVisible(true);
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

        jPanelFondo = new javax.swing.JPanel();
        jPanelEncabezado = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanelListaCategorias = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCategorias = new javax.swing.JTable();
        txtBuscarCategoria = new javax.swing.JTextField();
        cbInactivas = new javax.swing.JCheckBox();
        jPanelListaCategorias1 = new javax.swing.JPanel();
        txtNombreCategoria = new javax.swing.JTextField();
        btnEliminarCategoria = new javax.swing.JButton();
        btnNuevaCategoria = new javax.swing.JButton();
        btnEditarCategoria = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        radioInactivoEditar = new javax.swing.JRadioButton();
        radioActivoEditar = new javax.swing.JRadioButton();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Administrar Categoria");
        setMaximumSize(new java.awt.Dimension(1245, 700));
        setMinimumSize(new java.awt.Dimension(1244, 657));
        setPreferredSize(new java.awt.Dimension(1244, 700));
        setSize(new java.awt.Dimension(1244, 700));
        getContentPane().setLayout(null);

        jPanelFondo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFondo.setMinimumSize(new java.awt.Dimension(1230, 670));
        jPanelFondo.setPreferredSize(new java.awt.Dimension(1230, 670));
        jPanelFondo.setLayout(null);

        jPanelEncabezado.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEncabezado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelEncabezado.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(31, 41, 55));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setPreferredSize(new java.awt.Dimension(32, 32));
        jPanel3.setLayout(null);
        jPanelEncabezado.add(jPanel3);
        jPanel3.setBounds(10, 10, 32, 32);

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(39, 24, 17));
        jLabel2.setText("Café Express POS: Panel Administrador - Administrar categoria");
        jLabel2.setAlignmentX(16.0F);
        jLabel2.setAlignmentY(0.0F);
        jPanelEncabezado.add(jLabel2);
        jLabel2.setBounds(50, 15, 830, 24);

        jPanelFondo.add(jPanelEncabezado);
        jPanelEncabezado.setBounds(0, 0, 1230, 50);

        jPanelListaCategorias.setBackground(new java.awt.Color(255, 255, 255));
        jPanelListaCategorias.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        jPanelListaCategorias.setPreferredSize(new java.awt.Dimension(500, 200));
        jPanelListaCategorias.setLayout(null);

        tableCategorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Id_Categoria", "Nombre_Categoria"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableCategorias);

        jPanelListaCategorias.add(jScrollPane1);
        jScrollPane1.setBounds(10, 10, 800, 402);

        jPanelFondo.add(jPanelListaCategorias);
        jPanelListaCategorias.setBounds(20, 150, 820, 420);

        txtBuscarCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarCategoriaKeyTyped(evt);
            }
        });
        jPanelFondo.add(txtBuscarCategoria);
        txtBuscarCategoria.setBounds(20, 80, 740, 42);

        cbInactivas.setBackground(new java.awt.Color(255, 255, 255));
        cbInactivas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        cbInactivas.setText("Inactivas");
        cbInactivas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbInactivasActionPerformed(evt);
            }
        });
        jPanelFondo.add(cbInactivas);
        cbInactivas.setBounds(780, 80, 140, 40);

        jPanelListaCategorias1.setBackground(new java.awt.Color(255, 255, 255));
        jPanelListaCategorias1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        jPanelListaCategorias1.setPreferredSize(new java.awt.Dimension(500, 200));
        jPanelListaCategorias1.setLayout(null);

        txtNombreCategoria.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreCategoriaKeyTyped(evt);
            }
        });
        jPanelListaCategorias1.add(txtNombreCategoria);
        txtNombreCategoria.setBounds(20, 70, 280, 42);

        btnEliminarCategoria.setBackground(new java.awt.Color(224, 223, 223));
        btnEliminarCategoria.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        btnEliminarCategoria.setForeground(new java.awt.Color(102, 102, 102));
        btnEliminarCategoria.setText("Eliminar");
        btnEliminarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCategoriaActionPerformed(evt);
            }
        });
        jPanelListaCategorias1.add(btnEliminarCategoria);
        btnEliminarCategoria.setBounds(20, 230, 280, 50);

        btnNuevaCategoria.setBackground(new java.awt.Color(17, 24, 39));
        btnNuevaCategoria.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        btnNuevaCategoria.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaCategoria.setText("Nueva Categoria");
        btnNuevaCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaCategoriaActionPerformed(evt);
            }
        });
        jPanelListaCategorias1.add(btnNuevaCategoria);
        btnNuevaCategoria.setBounds(20, 150, 280, 60);

        btnEditarCategoria.setBackground(new java.awt.Color(17, 24, 39));
        btnEditarCategoria.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        btnEditarCategoria.setForeground(new java.awt.Color(255, 255, 255));
        btnEditarCategoria.setText("Editar");
        btnEditarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarCategoriaActionPerformed(evt);
            }
        });
        jPanelListaCategorias1.add(btnEditarCategoria);
        btnEditarCategoria.setBounds(20, 310, 280, 50);

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(39, 24, 17));
        jLabel3.setText("Categorias");
        jLabel3.setAlignmentX(16.0F);
        jLabel3.setAlignmentY(0.0F);
        jPanelListaCategorias1.add(jLabel3);
        jLabel3.setBounds(20, 20, 270, 24);
        jPanelListaCategorias1.add(jSeparator1);
        jSeparator1.setBounds(0, 220, 320, 3);
        jPanelListaCategorias1.add(jSeparator2);
        jSeparator2.setBounds(0, 290, 320, 3);

        radioInactivoEditar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        radioInactivoEditar.setText("Inactivo");
        radioInactivoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioInactivoEditarActionPerformed(evt);
            }
        });
        jPanelListaCategorias1.add(radioInactivoEditar);
        radioInactivoEditar.setBounds(50, 380, 80, 25);

        radioActivoEditar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        radioActivoEditar.setText("Activo");
        radioActivoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioActivoEditarActionPerformed(evt);
            }
        });
        jPanelListaCategorias1.add(radioActivoEditar);
        radioActivoEditar.setBounds(190, 380, 70, 25);

        jPanelFondo.add(jPanelListaCategorias1);
        jPanelListaCategorias1.setBounds(880, 150, 320, 420);

        btnRegresar.setBackground(new java.awt.Color(224, 223, 223));
        btnRegresar.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(102, 102, 102));
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jPanelFondo.add(btnRegresar);
        btnRegresar.setBounds(20, 590, 110, 50);

        getContentPane().add(jPanelFondo);
        jPanelFondo.setBounds(0, 0, 1230, 670);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarCategoriaKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtBuscarCategoriaKeyTyped

    private void txtNombreCategoriaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreCategoriaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreCategoriaKeyTyped

    private void btnEliminarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCategoriaActionPerformed
        // TODO add your handling code here:
       int fila = tableCategorias.getSelectedRow();

    // VALIDAR QUE SE HAYA SELECCIONADO UNA FILA
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione una categoría de la tabla.");
        return;
    }
    int id = (int) tableCategorias.getValueAt(fila, 0);
 
    try{
    Categoria categoria = fCategoria.obtenerCategoria(id);
     if("inactivo".equals(categoria.getEstado())){
         JOptionPane.showMessageDialog(this, "No se puede eliminar - categoria ya inactiva");
         return;
     }   
        
     fCategoria.editarEstadoCategoria(id, "inactivo");
     fProducto.cambiarEstadoProductosByCategoria(id, "inactivo");
     //LIMPIAR Y ACTUALIZAR TABLA
                DefaultTableModel modelo = (DefaultTableModel) tableCategorias.getModel();
                modelo.setRowCount(0); // LIMPIAR
                JOptionPane.showMessageDialog(this, "Se elimino con exito la categoria y sus productos");
                cargarCategoriasActivas();    // CARGAR DE NUEVO
    }catch (Exception ex) {
                Logger.getLogger(FrmAdministrarCategorias.class.getName()).log(Level.SEVERE, null, ex);
            }
    
    //VALIDACIONES
    }//GEN-LAST:event_btnEliminarCategoriaActionPerformed

    private void btnNuevaCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaCategoriaActionPerformed
        // TODO add your handling code here:
        
        //VALIDACIONES
        if (txtNombreCategoria.getText().trim().isEmpty()) {
            txtNombreCategoria.setBorder(BorderFactory.createLineBorder(Color.RED));
        JOptionPane.showMessageDialog(this, "Campo vacio");
         } else {
          txtNombreCategoria.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
            try {
            String nombre = txtNombreCategoria.getText().trim();

            // Buscar categoría por nombre
            Categoria cat = fCategoria.obtenerCategoriaPorNombre(nombre);

            if (cat != null) {
            JOptionPane.showMessageDialog(this, "La categoría ya existe.");
            
            }else{
                //CREAR OBJETO A GUARDAR
                listaCategoriaUtil = fCategoria.obtenerTodasLasCategorias();
                categoriaUtil = new Categoria(listaCategoriaUtil.size()+1, "activo", txtNombreCategoria.getText());
                //GUARDAR OBJETO EN LA DB
                fCategoria.agregarCategoria(categoriaUtil);
                //LIMPIAR Y ACTUALIZAR TABLA
                DefaultTableModel modelo = (DefaultTableModel) tableCategorias.getModel();
                modelo.setRowCount(0); // LIMPIAR
                JOptionPane.showMessageDialog(this, "Se agrego con exito la categoria");
                cargarCategoriasActivas();    // CARGAR DE NUEVO
            }
                 
            } catch (Exception ex) {
                Logger.getLogger(FrmAdministrarCategorias.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnNuevaCategoriaActionPerformed

    private void btnEditarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarCategoriaActionPerformed
        // TODO add your handling code here:
         int fila = tableCategorias.getSelectedRow();

    // VALIDAR QUE SE HAYA SELECCIONADO UNA FILA
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione una categoría de la tabla.");
        return;
    }
    

    // VALIDAR TEXTFIELD VACÍO
    String nuevoNombre = txtNombreCategoria.getText().trim();
    if (nuevoNombre.isEmpty()) {
        txtNombreCategoria.setBorder(BorderFactory.createLineBorder(Color.RED));
        JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
        return;
    }

    txtNombreCategoria.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));

    // OBTENER ID DE LA TABLA
    int id = (int) tableCategorias.getValueAt(fila, 0);

    try {
        // BUSCAR CATEGORÍA EN BD
        Categoria categoria = fCategoria.obtenerCategoria(id);

        if (categoria == null) {
            JOptionPane.showMessageDialog(this, "La categoría ya no existe.");
            return;
        }

        // VALIDAR QUE NO EXISTA OTRA CON EL MISMO NOMBRE
        Categoria duplicado = fCategoria.obtenerCategoriaPorNombre(nuevoNombre);
        if (duplicado != null && duplicado.getIdCategoria() != id) {
            JOptionPane.showMessageDialog(this, "Ya existe una categoría con ese nombre.");
            return;
        }

              // ACTUALIZAR VALOR
                categoria.setNombre(nuevoNombre);
                if(radioActivoEditar.isSelected()){
                    categoria.setEstado("activo");
                }
                else{
                    categoria.setEstado("inactivo");
                }
                
                fCategoria.editarCategoria(categoria);
                
//                if("inactivo".equals(categoria.getEstado())&& radioInactivoEditar.isSelected()){
//                      JOptionPane.showMessageDialog(this, "No se puede editar - Elegir otra opcion");
//                       return;
//                }
//                
//                if("activo".equals(categoria.getEstado())&& radioActivoEditar.isSelected()){
//                      JOptionPane.showMessageDialog(this, "No se puede editar - Elegir otra opcion");
//                       return;
//                }
//                
//                if("activo".equals(categoria.getEstado()) && radioInactivoEditar.isSelected()){
//                    fCategoria.editarEstadoCategoria(id, "inactivo");
//                    fProducto.cambiarEstadoProductosByCategoria(id, "inactivo");
//                }
//                if("inactivo".equals(categoria.getEstado()) && radioActivoEditar.isSelected()){
//                    fCategoria.editarEstadoCategoria(id, "activo");
//                    fProducto.cambiarEstadoProductosByCategoria(id, "activo");
//                }
                    
 
                DefaultTableModel modelo = (DefaultTableModel) tableCategorias.getModel();
                modelo.setRowCount(0); // LIMPIAR
                JOptionPane.showMessageDialog(this, "Categoria actualizada con exito");
                cargarCategoriasActivas();    // CARGAR DE NUEVO   
                cbInactivas.setSelected(false);
            

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al editar categoría: " + ex.getMessage());
    }
    }//GEN-LAST:event_btnEditarCategoriaActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        admin.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void radioInactivoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioInactivoEditarActionPerformed
        // TODO add your handling code here:
        radioActivoEditar.setSelected(false);
    }//GEN-LAST:event_radioInactivoEditarActionPerformed

    private void radioActivoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioActivoEditarActionPerformed
        // TODO add your handling code here:
        radioInactivoEditar.setSelected(false);
    }//GEN-LAST:event_radioActivoEditarActionPerformed

    private void cbInactivasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbInactivasActionPerformed
        // TODO add your handling code here:
         if (cbInactivas.isSelected()) {
            // CHECK ACTIVADO
            cargarCategoriasInactivas();
            txtNombreCategoria.setEditable(false);
            btnEliminarCategoria.setVisible(false);
            btnNuevaCategoria.setVisible(false);
            
            
            //radioActivoEditar.setVisible(true);
            //radioInactivoEditar.setVisible(true);
            
            
        } else {
            // CHECK DESACTIVADO
            cargarCategoriasActivas();
            txtNombreCategoria.setEditable(true);
            btnEliminarCategoria.setVisible(true);
            
            radioActivoEditar.setVisible(false);
            radioInactivoEditar.setVisible(false);
            
        }
    }//GEN-LAST:event_cbInactivasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditarCategoria;
    private javax.swing.JButton btnEliminarCategoria;
    private javax.swing.JButton btnNuevaCategoria;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JCheckBox cbInactivas;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelEncabezado;
    private javax.swing.JPanel jPanelFondo;
    private javax.swing.JPanel jPanelListaCategorias;
    private javax.swing.JPanel jPanelListaCategorias1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JRadioButton radioActivoEditar;
    private javax.swing.JRadioButton radioInactivoEditar;
    private javax.swing.JTable tableCategorias;
    private javax.swing.JTextField txtBuscarCategoria;
    private javax.swing.JTextField txtNombreCategoria;
    // End of variables declaration//GEN-END:variables
}
