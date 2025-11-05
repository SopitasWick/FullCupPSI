package Formularios;

import Entidades.Comanda;
import Entidades.Detallecomanda;
import Entidades.Producto;
import Facades.IFachadaComandasControlador;
import Facades.IFachadaDetallesComandaControlador;
import Implementaciones.GestionarComandaControlador;
import Implementaciones.GestionarDetallesComandaControlador;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class ListaProductosBeta extends javax.swing.JFrame {

    
    private final IFachadaComandasControlador FComandas = new GestionarComandaControlador();
    private final IFachadaDetallesComandaControlador fDC = new GestionarDetallesComandaControlador();

    List<Producto> todosLosProductos;
    
    
    Dimension dimension = null;
    String rutaBtnEliminar = "src/main/java/iconos/BotonEliminar.png";

    public static Comanda comanda = new Comanda();
    public static List<Detallecomanda> detalleComanda = new ArrayList<>();

    DetallesProducto detalle;
    
    static Integer idComanda;


    /**
     * Creates new form frmListaProductos
     * @param comandaEditar utilizada para editar, null en caso de ser nueva comanda
     */
    public ListaProductosBeta(Comandas comand, Integer idComanda) throws Exception {
        initComponents();

        //this.comand = comand;
        this.idComanda = idComanda;

        buscarProductos();
        configurarPopupTablaActivas();
        
        
        cargarPanelComanda();
        
    }

    
    
    public void cargarPanelComanda(){
        
        jPanelItems.removeAll();
        
        try {
            detalleComanda = fDC.obtenerDetallesComandasPorComanda(comanda);
        } catch (Exception ex) {
            Logger.getLogger(ListaProductosBeta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int margenY = 70;
        
        for (int i = 0; i < detalleComanda.size(); i++){
            
            //SubPanel
            JPanel subPanel = new JPanel();
            subPanel.setBackground(Color.WHITE);
            subPanel.setOpaque(false);
            subPanel.setBorder(new LineBorder(Color.BLACK));
            subPanel.setBounds(0, margenY * i, jPanelItems.getWidth()-10, 70);
            subPanel.setLayout(null);
            subPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            
            
            //Nombre del producto
            JLabel jlabelNombreProducto = new JLabel();
            jlabelNombreProducto.setBounds(10, 8, 300, 20);
            jlabelNombreProducto.setFont(new java.awt.Font("Segoe UI", 1, 16));
            jlabelNombreProducto.setText(detalleComanda.get(i).getIdProducto().getNombreProducto());
            subPanel.add(jlabelNombreProducto);
            
            
            
            //Detalles del producto
            JLabel jlabelDetalleProducto = new JLabel();
            jlabelDetalleProducto.setBounds(10, 30, 300, 20);
            jlabelDetalleProducto.setFont(new java.awt.Font("Segoe UI", 0, 10));
            jlabelDetalleProducto.setText(detalleComanda.get(i).getNotadetalleComanda());
            subPanel.add(jlabelDetalleProducto);
            
            
            //boton Eliminar
            JLabel jlabelBotonEliminar = new JLabel();
            jlabelBotonEliminar.setBounds(subPanel.getWidth()-30, 8, 20, 20);
           // jlabelBotonEliminar.setBorder(new LineBorder(Color.BLACK));
            setImagenLabel(jlabelBotonEliminar, rutaBtnEliminar);
            subPanel.add(jlabelBotonEliminar);
            
            int id = i;
            
             jlabelBotonEliminar.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            int confirm = JOptionPane.showConfirmDialog(ListaProductosBeta.this, "¿Seguro que deseas eliminar este item?",
                                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            try {
                                fDC.eliminarDetallesComandas(detalleComanda.get(id).getIdDetalleComanda());
                                cargarPanelComanda();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(ListaProductosBeta.this, "Error al eliminar: " + ex.getMessage());
                                ex.printStackTrace();
                            }
                        }
                            
                        }
                        
             });
            
            
            subPanel.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    JOptionPane.showMessageDialog(ListaProductosBeta.this, "No programado aun, pero seria editar cuando se haga");
                }
            
            
            });
            
            
            
            if (jPanelItems.getPreferredSize().height < margenY * (i + 1)) {
                if (dimension == null) {
                    dimension = new Dimension();
                }
                dimension.setSize(jPanelItems.getPreferredSize().width, margenY * (i + 1));
                jPanelItems.setPreferredSize(dimension);
            }
            
            
            
            jPanelItems.add(subPanel);
            jPanelItems.revalidate();
            jPanelItems.repaint();
            
            
        }
        
    }

    
    public void setImagenLabel(JLabel label, String rutaImagen) {
        try {
            ImageIcon icon = new ImageIcon(rutaImagen);

            Image img = icon.getImage().getScaledInstance(
                label.getWidth(), 
                label.getHeight(), 
                Image.SCALE_SMOOTH
            );

            label.setIcon(new ImageIcon(img));
            label.setText(null); 

        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
        }   
    }
    
    
    
    
    public void buscarProductos() {


        try {
            todosLosProductos = FComandas.ObtenerListaProductos();
            
            if(idComanda != null){
                comanda = FComandas.obtenerComanda(idComanda);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ListaProductosBeta.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Se encontraron " + todosLosProductos.size() + " productos");

        llenarTablaProductos();

    }
    
    public void buscarProductosPorCategoria(int idCategoria)throws Exception{
         todosLosProductos = FComandas.ObtenerListaProductosCategoria(idCategoria);
         System.out.println("Se encontraron " + todosLosProductos.size() + " productos");
         llenarTablaProductos();
    }

    public void llenarTablaProductos() {

        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();

        modelo.setRowCount(0);

        for (Producto p : todosLosProductos) {
            Object[] fila = {
                p.getNombreProducto(),          // o p.getNombreProducto()
                "No Programado",     // o p.getDescripcionProducto() AQUI VACIAR LO QUE SE TENGA DE LA
                p.getPrecioProducto()// o p.getPrecioProducto()
            };
            modelo.addRow(fila);
        }

        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    JTable target = (JTable) evt.getSource();
                    int filaSeleccionada = target.getSelectedRow();

                    if (filaSeleccionada != -1) {
                        String nombre = target.getValueAt(filaSeleccionada, 0).toString();
                        String descripcion = target.getValueAt(filaSeleccionada, 1).toString();
                        float precio = Float.parseFloat(target.getValueAt(filaSeleccionada, 2).toString());

                        Producto producto = new Producto();

                        for (Producto p : todosLosProductos) {
                            if (p.getNombreProducto().equals(nombre) && p.getPrecioProducto() == precio) {
                                producto = p;
                            }
                        }
                        //RIFARSELA AQUI PARA QUE ANTES DE QUE HABRA LA PANTALLA DETALLES
                        //YA SE CARGUE TODO TAL CUAL DE ESE PRODUCTO HACERLO DESDE EL CONSTRUCTOR
                        //YA HAY UN METODO QUE TE TRAE LOS EXTRAS POR ID DE PRODUCTO
//                        
//                        if (idComanda != null){
//                        
//                            detalle = new DetallesProducto(ListaProductos.this, producto, comanda, null);
//                        }
//                        else{
//                            detalle = new DetallesProducto(ListaProductos.this, producto, null, null);
//                        }
//                        
//
//                        detalle.setVisible(true);
//                        
//                        ListaProductos.this.setVisible(false);

                    }
                }
            }
        });

        
        llenarItemsComanda();
        
    }

    public void llenarItemsComanda() {

       // DefaultTableModel modelo = (DefaultTableModel) tblitems.getModel();

      //  modelo.setRowCount(0);
        
        
        try {
            detalleComanda = fDC.obtenerDetallesComandasPorComanda(comanda);
        } catch (Exception ex) {
            Logger.getLogger(ListaProductosBeta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        if(idComanda != 0){
//            
//            detalleComanda = comandaEditar.getDetallecomandaList();
//        }
//
//        else{
//            System.out.println("Es nula");
//        }
        
        
            for (int i = 0; i < detalleComanda.size(); i++) {

                Object[] fila = {
                    detalleComanda.get(i).getIdProducto().getNombreProducto(),
                    detalleComanda.get(i).getNotadetalleComanda(), // o p.getDescripcionProducto()
                    detalleComanda.get(i).getCaintidaddetalleComanda(),
                    detalleComanda.get(i).getSubTotaldetalleComanda()
                };
            //    modelo.addRow(fila);

            }
        
        

       // calcularTotal();

    }

//    private void calcularTotal() {
//        float total = 0;
//       // DefaultTableModel modelo = (DefaultTableModel) tblitems.getModel();
//
//        for (int i = 0; i < modelo.getRowCount(); i++) {
//            Object cantidadObj = modelo.getValueAt(i, 2);
//            Object precioObj = modelo.getValueAt(i, 3);
//
//            if (cantidadObj == null || precioObj == null) {
//                System.out.println("Fila " + i + " tiene valores nulos: cantidad=" + cantidadObj + ", precio=" + precioObj);
//                continue;
//            }
//
//            int cantidad;
//            float precio;
//
//            if (cantidadObj instanceof Integer) {
//                cantidad = (Integer) cantidadObj;
//            } else {
//                cantidad = Integer.parseInt(cantidadObj.toString());
//            }
//
//            if (precioObj instanceof Float) {
//                precio = (Float) precioObj;
//            } else {
//                precio = Float.parseFloat(precioObj.toString());
//            }
//
//            total += cantidad * precio;
//        }
//
//        txtSubtotal.setText(String.valueOf(total));
//        txtTotal.setText(String.valueOf(total));
//    }

    
    
    private void configurarPopupTablaActivas() {
        JPopupMenu popup = new JPopupMenu();

        JMenuItem itemEliminar = new JMenuItem("Eliminar item");        
        JMenuItem itemEditar = new JMenuItem("Editar item");

        popup.add(itemEditar);
        popup.add(itemEliminar);
        



        // Listener para eliminar
//        itemEliminar.addActionListener(e -> {
//            Integer id = obtenerIdSeleccionado(tblitems);
//            if (id != null) {
//                int confirm = JOptionPane.showConfirmDialog(this,
//                        "¿Seguro que deseas eliminar este item?",
//                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
//                if (confirm == JOptionPane.YES_OPTION) {
//                    try {
//                        fDC.eliminarDetallesComandas(detalleComanda.get(id).getIdDetalleComanda());
//                        llenarItemsComanda();
//                    } catch (Exception ex) {
//                        JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
//                    }
//                }
//            }
//        });
        
        
//      //  Listener para editar
//        itemEditar.addActionListener(e -> {
//            Integer id = obtenerIdSeleccionado(tblitems);
//            if (id != null) {
//                try {
//                    
//                   Comanda comandaEditar = FComandas.obtenerComanda(idComanda);
//                   
//                   System.out.println("idd" + id);
//                    System.out.println("detalle size" + detalleComanda.size());
//                   detalle = new DetallesProducto(this, detalleComanda.get(id).getIdProducto(), comandaEditar, detalleComanda.get(1));
//                   detalle.setVisible(true);
//                   System.out.println("idd" + id);
//                   ListaProductos.this.setVisible(false);
//
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(this, "Error al completar: " + ex.getMessage());
//                }
//            }
//        });
//        
//
//        // Mostrar popup al hacer clic derecho
//        tblitems.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                mostrarPopup(e);
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//                mostrarPopup(e);
//            }
//
//            private void mostrarPopup(MouseEvent e) {
//                if (e.isPopupTrigger() && tblitems.getSelectedRow() >= 0) {
//                    popup.show(e.getComponent(), e.getX(), e.getY());
//                }
//            }
//        });
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
        return fila;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtBuscarProductos = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanelItems = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        btnParaLlevar = new javax.swing.JButton();
        btnMesa = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JLabel();
        txtimpuestos = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        btnRegistrarVenta = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGuardar1 = new javax.swing.JButton();
        toggleTodos = new javax.swing.JToggleButton();
        toggleBCalientes = new javax.swing.JToggleButton();
        toggleTonic = new javax.swing.JToggleButton();
        toggleFrappeRocas = new javax.swing.JToggleButton();
        toggleEspeciales = new javax.swing.JToggleButton();
        toggleSmothies = new javax.swing.JToggleButton();
        toggleCrepasDulces = new javax.swing.JToggleButton();
        toggleEmparedados = new javax.swing.JToggleButton();
        toggleCrepasSaladas = new javax.swing.JToggleButton();
        btnProductosUnitarios = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(1440, 1024));
        jPanel2.setPreferredSize(new java.awt.Dimension(1440, 1024));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1440, 54));
        jPanel1.setPreferredSize(new java.awt.Dimension(1440, 54));

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(39, 24, 17));
        jLabel1.setText("Café Express POS");
        jLabel1.setAlignmentX(16.0F);
        jLabel1.setAlignmentY(0.0F);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addContainerGap(1213, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(5, 5, 5)))
                .addContainerGap())
        );

        txtBuscarProductos.setText("Buscar productos...");
        txtBuscarProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarProductosActionPerformed(evt);
            }
        });

        tblProductos.setBackground(new java.awt.Color(242, 243, 245));
        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Producto", "Descripción", "Precio"
            }
        ));
        jScrollPane1.setViewportView(tblProductos);

        jPanel4.setBackground(new java.awt.Color(242, 243, 245));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        jPanel4.setMaximumSize(new java.awt.Dimension(430, 375));
        jPanel4.setPreferredSize(new java.awt.Dimension(430, 375));
        jPanel4.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(17, 24, 39));
        jLabel4.setText("Items de la Comanda");
        jLabel4.setAlignmentX(16.0F);
        jLabel4.setAlignmentY(0.0F);
        jPanel4.add(jLabel4);
        jLabel4.setBounds(10, 10, 184, 24);

        jPanelItems.setPreferredSize(new java.awt.Dimension(390, 318));

        javax.swing.GroupLayout jPanelItemsLayout = new javax.swing.GroupLayout(jPanelItems);
        jPanelItems.setLayout(jPanelItemsLayout);
        jPanelItemsLayout.setHorizontalGroup(
            jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 408, Short.MAX_VALUE)
        );
        jPanelItemsLayout.setVerticalGroup(
            jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 328, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanelItems);

        jPanel4.add(jScrollPane2);
        jScrollPane2.setBounds(10, 40, 410, 330);

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(39, 24, 17));
        jLabel2.setText("Nueva Comanda");
        jLabel2.setAlignmentX(16.0F);
        jLabel2.setAlignmentY(0.0F);

        jLabel3.setForeground(new java.awt.Color(107, 114, 128));
        jLabel3.setText("Cliente");

        jTextField1.setText("Nombre del cliente");
        jTextField1.setMaximumSize(new java.awt.Dimension(430, 40));
        jTextField1.setMinimumSize(new java.awt.Dimension(0, 0));
        jTextField1.setPreferredSize(new java.awt.Dimension(430, 40));

        btnParaLlevar.setBackground(new java.awt.Color(31, 41, 55));
        btnParaLlevar.setForeground(new java.awt.Color(255, 255, 255));
        btnParaLlevar.setText("Para Llevar");

        btnMesa.setBackground(new java.awt.Color(242, 243, 245));
        btnMesa.setForeground(new java.awt.Color(55, 65, 81));
        btnMesa.setText("Mesa");
        btnMesa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        jPanel5.setMaximumSize(new java.awt.Dimension(430, 234));
        jPanel5.setPreferredSize(new java.awt.Dimension(430, 234));

        jLabel5.setForeground(new java.awt.Color(75, 85, 99));
        jLabel5.setText("Subtotal:");

        jLabel6.setForeground(new java.awt.Color(75, 85, 99));
        jLabel6.setText("Impuestos:");

        txtSubtotal.setText("$7.75");

        txtimpuestos.setText("$0.00");

        jLabel7.setForeground(new java.awt.Color(229, 231, 225));
        jLabel7.setText("________________________________________________________________");

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 17)); // NOI18N
        jLabel8.setText("Total:");

        txtTotal.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        txtTotal.setText("$8.53");

        btnRegistrarVenta.setBackground(new java.awt.Color(31, 41, 55));
        btnRegistrarVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarVenta.setText("Registrar Venta");
        btnRegistrarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarVentaActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(242, 243, 245));
        btnCancelar.setForeground(new java.awt.Color(55, 65, 81));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        btnGuardar1.setBackground(new java.awt.Color(242, 243, 245));
        btnGuardar1.setForeground(new java.awt.Color(55, 65, 81));
        btnGuardar1.setText("Guardar");
        btnGuardar1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnGuardar1.setMaximumSize(new java.awt.Dimension(180, 40));
        btnGuardar1.setPreferredSize(new java.awt.Dimension(180, 40));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSubtotal)
                            .addComponent(txtimpuestos))
                        .addGap(14, 14, 14))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTotal))
                            .addComponent(jLabel7))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnRegistrarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(68, 68, 68))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSubtotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtimpuestos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegistrarVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        toggleTodos.setBackground(new java.awt.Color(242, 243, 245));
        buttonGroup1.add(toggleTodos);
        toggleTodos.setText("Todos");
        toggleTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleTodosActionPerformed(evt);
            }
        });

        toggleBCalientes.setBackground(new java.awt.Color(242, 243, 245));
        buttonGroup1.add(toggleBCalientes);
        toggleBCalientes.setText("Bebidas Calientes");
        toggleBCalientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleBCalientesActionPerformed(evt);
            }
        });

        toggleTonic.setBackground(new java.awt.Color(242, 243, 245));
        buttonGroup1.add(toggleTonic);
        toggleTonic.setText("Tonic");
        toggleTonic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleTonicActionPerformed(evt);
            }
        });

        toggleFrappeRocas.setBackground(new java.awt.Color(242, 243, 245));
        buttonGroup1.add(toggleFrappeRocas);
        toggleFrappeRocas.setText("Frappe o Rocas");
        toggleFrappeRocas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleFrappeRocasActionPerformed(evt);
            }
        });

        toggleEspeciales.setBackground(new java.awt.Color(242, 243, 245));
        buttonGroup1.add(toggleEspeciales);
        toggleEspeciales.setText("Bebidas Especiales");
        toggleEspeciales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleEspecialesActionPerformed(evt);
            }
        });

        toggleSmothies.setBackground(new java.awt.Color(242, 243, 245));
        buttonGroup1.add(toggleSmothies);
        toggleSmothies.setText("Smothies");
        toggleSmothies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleSmothiesActionPerformed(evt);
            }
        });

        toggleCrepasDulces.setBackground(new java.awt.Color(242, 243, 245));
        toggleCrepasDulces.setText("Crepas D");
        toggleCrepasDulces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleCrepasDulcesActionPerformed(evt);
            }
        });

        toggleEmparedados.setBackground(new java.awt.Color(242, 243, 245));
        toggleEmparedados.setText("Emparedados");
        toggleEmparedados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleEmparedadosActionPerformed(evt);
            }
        });

        toggleCrepasSaladas.setBackground(new java.awt.Color(242, 243, 245));
        toggleCrepasSaladas.setText("Crepas S");
        toggleCrepasSaladas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleCrepasSaladasActionPerformed(evt);
            }
        });

        btnProductosUnitarios.setBackground(new java.awt.Color(242, 243, 245));
        btnProductosUnitarios.setText("Productos Unitarios");
        btnProductosUnitarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosUnitariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtBuscarProductos)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(toggleTodos)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(toggleBCalientes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(toggleTonic)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(toggleFrappeRocas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(toggleEspeciales)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(toggleSmothies)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(toggleCrepasDulces)
                                .addGap(18, 18, 18)
                                .addComponent(toggleCrepasSaladas)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(toggleEmparedados, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(321, 321, 321)
                                .addComponent(btnProductosUnitarios, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnParaLlevar, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscarProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(toggleTodos)
                            .addComponent(toggleBCalientes)
                            .addComponent(toggleTonic)
                            .addComponent(toggleFrappeRocas)
                            .addComponent(toggleEspeciales)
                            .addComponent(toggleSmothies)
                            .addComponent(toggleCrepasDulces)
                            .addComponent(toggleCrepasSaladas)
                            .addComponent(toggleEmparedados))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProductosUnitarios)))
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnParaLlevar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnMesa, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProductosUnitariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosUnitariosActionPerformed
        try {
            this.buscarProductosPorCategoria(9);
        } catch (Exception ex) {
            Logger.getLogger(ListaProductosBeta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnProductosUnitariosActionPerformed

    private void toggleCrepasSaladasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleCrepasSaladasActionPerformed
        try {
            this.buscarProductosPorCategoria(7);
        } catch (Exception ex) {
            Logger.getLogger(ListaProductosBeta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toggleCrepasSaladasActionPerformed

    private void toggleEmparedadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleEmparedadosActionPerformed
        try {
            this.buscarProductosPorCategoria(8);
        } catch (Exception ex) {
            Logger.getLogger(ListaProductosBeta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toggleEmparedadosActionPerformed

    private void toggleCrepasDulcesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleCrepasDulcesActionPerformed
        try {
            this.buscarProductosPorCategoria(6);
        } catch (Exception ex) {
            Logger.getLogger(ListaProductosBeta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toggleCrepasDulcesActionPerformed

    private void toggleSmothiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleSmothiesActionPerformed
        try {
            this.buscarProductosPorCategoria(5);
        } catch (Exception ex) {
            Logger.getLogger(ListaProductosBeta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toggleSmothiesActionPerformed

    private void toggleEspecialesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleEspecialesActionPerformed
        try {
            this.buscarProductosPorCategoria(4);
        } catch (Exception ex) {
            Logger.getLogger(ListaProductosBeta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toggleEspecialesActionPerformed

    private void toggleFrappeRocasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleFrappeRocasActionPerformed
        try {
            this.buscarProductosPorCategoria(3);
        } catch (Exception ex) {
            Logger.getLogger(ListaProductosBeta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toggleFrappeRocasActionPerformed

    private void toggleTonicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleTonicActionPerformed
        try {
            this.buscarProductosPorCategoria(2);
        } catch (Exception ex) {
            Logger.getLogger(ListaProductosBeta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toggleTonicActionPerformed

    private void toggleBCalientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleBCalientesActionPerformed
        try {
            this.buscarProductosPorCategoria(1);
        } catch (Exception ex) {
            Logger.getLogger(ListaProductosBeta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toggleBCalientesActionPerformed

    private void toggleTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleTodosActionPerformed
        try {
            // TODO add your handling code here:
            this.buscarProductos();
        } catch (Exception ex) {
            Logger.getLogger(ListaProductosBeta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_toggleTodosActionPerformed

    private void btnRegistrarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarVentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegistrarVentaActionPerformed

    private void txtBuscarProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarProductosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarProductosActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JButton btnMesa;
    private javax.swing.JButton btnParaLlevar;
    private javax.swing.JButton btnProductosUnitarios;
    private javax.swing.JButton btnRegistrarVenta;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanelItems;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblProductos;
    private javax.swing.JToggleButton toggleBCalientes;
    private javax.swing.JToggleButton toggleCrepasDulces;
    private javax.swing.JToggleButton toggleCrepasSaladas;
    private javax.swing.JToggleButton toggleEmparedados;
    private javax.swing.JToggleButton toggleEspeciales;
    private javax.swing.JToggleButton toggleFrappeRocas;
    private javax.swing.JToggleButton toggleSmothies;
    private javax.swing.JToggleButton toggleTodos;
    private javax.swing.JToggleButton toggleTonic;
    private javax.swing.JTextField txtBuscarProductos;
    private javax.swing.JLabel txtSubtotal;
    private javax.swing.JLabel txtTotal;
    private javax.swing.JLabel txtimpuestos;
    // End of variables declaration//GEN-END:variables
}
