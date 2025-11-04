/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Entidades.Categoria;
import Entidades.Comanda;
import Entidades.Detallecomanda;
import Entidades.Producto;
import Facades.IFachadaCategoriaControlador;
import Facades.IFachadaComandasControlador;
import Facades.IFachadaDetallesComandaControlador;
import Implementaciones.GestionarCategoriaControlador;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sergio Arturo
 */
public class FrmListaProductos extends javax.swing.JFrame {
    
    
    
    private final IFachadaComandasControlador FComandas = new GestionarComandaControlador();
    private final IFachadaDetallesComandaControlador fDC = new GestionarDetallesComandaControlador();
    private final IFachadaCategoriaControlador fC = new GestionarCategoriaControlador();

    List<Producto> todosLosProductos;
    List<Categoria> categorias;
    
    
    Dimension dimension = null;
    String rutaBtnEliminar = "src/main/java/iconos/BotonEliminar.png";

    public static Comanda comanda = new Comanda();
    public static List<Detallecomanda> detalleComanda = new ArrayList<>();

    DetallesProducto detalle;
    
    static Integer idComanda;
    
    
    

    /**
     * Creates new form FrmListaProductos
     */
    public FrmListaProductos() {
        initComponents();
        
        
        
        buscarProductos();
        cargarDiseno();
        
        cargarPanelComanda();
        
    }
    
    
    
    public FrmListaProductos(Comandas comand, Integer idComanda) {
        initComponents();
        
        this.idComanda = idComanda;
        
        cargarDiseno();
        
        

        buscarProductos();
        configurarPopupTablaActivas();
        
        
        cargarPanelComanda();
        
        
        
        cargarDiseno();
        
        cargarCategorias();
    }    
    
    
    
    
    
    private void cargarDiseno(){
        
        
        //PlaceHolderToJTextField hintBuscar = new PlaceHolderToJTextField("Buscar producto...", txtBuscar);
        
        
    
        
    
    }
    
    
    private void cargarCategorias(){
    
        List<String> listaCate = new ArrayList<>();
        listaCate.add("Todos");
        
        try {
            categorias = fC.obtenerTodasLasCategorias();
            
            for (int i = 0; i < categorias.size(); i++){
                listaCate.add(categorias.get(i).getNombre());
            }
            
            DefaultComboBoxModel<String> modelo = new DefaultComboBoxModel<>();
            
            for (String nombre : listaCate) {
                modelo.addElement(nombre);
            }
            

            cbCategorias.setModel(modelo);
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(FrmListaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        
    }
    
    
    
    
    public void cargarPanelComanda(){
        
        jPanelItems.removeAll();
        
        try {
            detalleComanda = fDC.obtenerDetallesComandasPorComanda(comanda);
        } catch (Exception ex) {
            Logger.getLogger(ListaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int margenY = 68;
        float subTota = 0;
        
        for (int i = 0; i < detalleComanda.size(); i++){
            
            subTota = subTota + detalleComanda.get(i).getSubTotaldetalleComanda();
            
            //SubPanel
            JPanel subPanel = new JPanel();
            subPanel.setBackground(Color.WHITE);
            subPanel.setOpaque(false);
            subPanel.setBorder(new LineBorder(Color.GRAY));
            subPanel.setBounds(0, margenY * i, jPanelItems.getWidth()-5, 68);
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
            jlabelDetalleProducto.setBounds(10, 45, 300, 20);
            jlabelDetalleProducto.setFont(new java.awt.Font("Segoe UI", 0, 10));
            jlabelDetalleProducto.setText(detalleComanda.get(i).getNotadetalleComanda());
            subPanel.add(jlabelDetalleProducto);
            
            
            //Precio del producto
            JLabel jlabelPrecio = new JLabel();
            jlabelPrecio.setBounds(subPanel.getWidth()-130, 8, 80, 20);
            jlabelPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
            jlabelPrecio.setText(detalleComanda.get(i).getSubTotaldetalleComanda().toString());
            subPanel.add(jlabelPrecio);
            
            
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
                            int confirm = JOptionPane.showConfirmDialog(FrmListaProductos.this, "¿Seguro que deseas eliminar este item?",
                                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                        if (confirm == JOptionPane.YES_OPTION) {
                            try {
                                fDC.eliminarDetallesComandas(detalleComanda.get(id).getIdDetalleComanda());
                                cargarPanelComanda();
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(FrmListaProductos.this, "Error al eliminar: " + ex.getMessage());
                                ex.printStackTrace();
                            }
                        }
                            
                        }
                        
             });
            
            
            subPanel.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    JOptionPane.showMessageDialog(FrmListaProductos.this, "No programado aun, pero seria editar cuando se haga");
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
        
        txtSubtotal.setText(String.valueOf(subTota));
        txtTotal.setText(String.valueOf(subTota));
        
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
            Logger.getLogger(ListaProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Se encontraron " + todosLosProductos.size() + " productos");

        llenarTablaProductos();

    }
    
    public void buscarProductosPorCategoria(int idCategoria)throws Exception{
        
        if(todosLosProductos != null){
                todosLosProductos.clear();
            }
        
        if(idCategoria == 0){
            buscarProductos();
        }
        else{
            todosLosProductos = FComandas.ObtenerListaProductosCategoria(idCategoria);
            System.out.println("idCategoria" + idCategoria);
            System.out.println("Se encontraron " + todosLosProductos.size() + " productos");
            llenarTablaProductos();
        }
    }
    
    
    public void llenarTablaProductos() {

        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();

        modelo.setRowCount(0);

        for (Producto p : todosLosProductos) {
            Object[] fila = {
                p.getNombreProducto(),          // o p.getNombreProducto()
                p.getPrecioProducto()// o p.getPrecioProducto()
            };
            modelo.addRow(fila);
        }
        
        
//        if(tblProductos.getMouseListeners().length > 0){
//            for (MouseListener ml : tblProductos.getMouseListeners()) {
//                tblProductos.removeMouseListener(ml);
//            }
//        }
        
        

        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    JTable target = (JTable) evt.getSource();
                    int filaSeleccionada = target.getSelectedRow();

                    if (filaSeleccionada != -1) {
                        String nombre = target.getValueAt(filaSeleccionada, 0).toString();
                        float precio = Float.parseFloat(target.getValueAt(filaSeleccionada, 1).toString());

                        Producto producto = new Producto();

                        for (Producto p : todosLosProductos) {
                            if (p.getNombreProducto().equals(nombre) && p.getPrecioProducto() == precio) {
                                producto = p;
                            }
                        }
                        //RIFARSELA AQUI PARA QUE ANTES DE QUE HABRA LA PANTALLA DETALLES
                        //YA SE CARGUE TODO TAL CUAL DE ESE PRODUCTO HACERLO DESDE EL CONSTRUCTOR
                        //YA HAY UN METODO QUE TE TRAE LOS EXTRAS POR ID DE PRODUCTO
                        
                        if (idComanda != null){
                        
                            detalle = new DetallesProducto(FrmListaProductos.this, producto, comanda, null);
                        }
                        else{
                            detalle = new DetallesProducto(FrmListaProductos.this, producto, null, null);
                        }
                        

                        detalle.setVisible(true);
                        
                        FrmListaProductos.this.setVisible(false);

                    }
                }
            }
        });

        
        llenarItemsComanda();
                // 🔹 Forzar refresco
    modelo.fireTableDataChanged();
    tblProductos.revalidate();
    tblProductos.repaint();

    // 🔹 Asegurar que es visible
    tblProductos.setVisible(true);
    tblProductos.setForeground(Color.BLACK);
    tblProductos.setBackground(Color.WHITE);
        
    }
    

//    public void llenarTablaProductos() {
//
//        DefaultTableModel modelo = (DefaultTableModel) tblProductos.getModel();
//
//        modelo.setRowCount(0);
//
//        for (Producto p : todosLosProductos) {
//            Object[] fila = {
//                p.getNombreProducto(),          // o p.getNombreProducto()
//                p.getPrecioProducto()// o p.getPrecioProducto()
//            };
//            modelo.addRow(fila);
//        }
//        
//        
//        for (MouseListener ml : tblProductos.getMouseListeners()) {
//            tblProductos.removeMouseListener(ml);
//        }
//        
//
//        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
//
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                if (evt.getClickCount() == 1) {
//                    JTable target = (JTable) evt.getSource();
//                    int filaSeleccionada = target.getSelectedRow();
//
//                    if (filaSeleccionada != -1) {
//                        String nombre = target.getValueAt(filaSeleccionada, 0).toString();
//                        float precio = Float.parseFloat(target.getValueAt(filaSeleccionada, 1).toString());
//
//                        Producto producto = new Producto();
//
//                        for (Producto p : todosLosProductos) {
//                            if (p.getNombreProducto().equals(nombre) && p.getPrecioProducto() == precio) {
//                                producto = p;
//                            }
//                        }
//                        //RIFARSELA AQUI PARA QUE ANTES DE QUE HABRA LA PANTALLA DETALLES
//                        //YA SE CARGUE TODO TAL CUAL DE ESE PRODUCTO HACERLO DESDE EL CONSTRUCTOR
//                        //YA HAY UN METODO QUE TE TRAE LOS EXTRAS POR ID DE PRODUCTO
//                        
////                        if (idComanda != null){
////                        
////                            detalle = new DetallesProducto(FrmListaProductos.this, producto, comanda, null);
////                        }
////                        else{
////                            detalle = new DetallesProducto(ListaProductos.this, producto, null, null);
////                        }
////                        
////
////                        detalle.setVisible(true);
////                        
////                        ListaProductos.this.setVisible(false);
//
//                    }
//                }
//            }
//        });
//
//        
//        llenarItemsComanda();
//        
//    }

    public void llenarItemsComanda() {

       // DefaultTableModel modelo = (DefaultTableModel) tblitems.getModel();

      //  modelo.setRowCount(0);
        
        
        try {
            detalleComanda = fDC.obtenerDetallesComandasPorComanda(comanda);
        } catch (Exception ex) {
            Logger.getLogger(ListaProductos.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanelFondo = new javax.swing.JPanel();
        jPanelEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanelResumenComanda = new javax.swing.JPanel();
        jPaneParaLlevar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jPanelItemComanda = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanelItems = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanelTotal = new javax.swing.JPanel();
        btnRegistrarVenta = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtimpuestos = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JLabel();
        btnGuardar2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanelBuscador = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jLabelCategorias = new javax.swing.JLabel();
        cbCategorias = new javax.swing.JComboBox<>();
        jPanelListaProductos = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Comanda : Lista de Productos");
        setMinimumSize(new java.awt.Dimension(1230, 620));
        setSize(new java.awt.Dimension(1230, 620));

        jPanelFondo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFondo.setLayout(null);

        jPanelEncabezado.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEncabezado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelEncabezado.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(39, 24, 17));
        jLabel1.setText("Café Express POS");
        jLabel1.setAlignmentX(16.0F);
        jLabel1.setAlignmentY(0.0F);
        jPanelEncabezado.add(jLabel1);
        jLabel1.setBounds(50, 15, 160, 24);

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

        jPanelFondo.add(jPanelEncabezado);
        jPanelEncabezado.setBounds(0, 0, 1230, 50);

        jPanelResumenComanda.setBackground(new java.awt.Color(255, 255, 255));
        jPanelResumenComanda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelResumenComanda.setLayout(null);

        jPaneParaLlevar.setBackground(new java.awt.Color(255, 255, 255));
        jPaneParaLlevar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPaneParaLlevar.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(39, 24, 17));
        jLabel2.setText("Nueva Comanda");
        jLabel2.setAlignmentX(16.0F);
        jLabel2.setAlignmentY(0.0F);
        jPaneParaLlevar.add(jLabel2);
        jLabel2.setBounds(20, 10, 130, 20);

        jLabel3.setForeground(new java.awt.Color(107, 114, 128));
        jLabel3.setText("Cliente");
        jPaneParaLlevar.add(jLabel3);
        jLabel3.setBounds(20, 35, 120, 16);

        jTextField1.setText("Nombre del cliente");
        jTextField1.setMaximumSize(new java.awt.Dimension(430, 40));
        jTextField1.setMinimumSize(new java.awt.Dimension(0, 0));
        jTextField1.setPreferredSize(new java.awt.Dimension(430, 40));
        jPaneParaLlevar.add(jTextField1);
        jTextField1.setBounds(20, 60, 220, 30);

        jRadioButton1.setBackground(new java.awt.Color(255, 255, 255));
        jRadioButton1.setText("Para Llevar");
        jRadioButton1.setOpaque(true);
        jPaneParaLlevar.add(jRadioButton1);
        jRadioButton1.setBounds(290, 65, 98, 21);

        jPanelResumenComanda.add(jPaneParaLlevar);
        jPaneParaLlevar.setBounds(0, 0, 410, 100);

        jPanelItemComanda.setBackground(new java.awt.Color(255, 255, 255));
        jPanelItemComanda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelItemComanda.setLayout(null);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanelItems.setBackground(new java.awt.Color(255, 255, 255));
        jPanelItems.setPreferredSize(new java.awt.Dimension(385, 245));

        javax.swing.GroupLayout jPanelItemsLayout = new javax.swing.GroupLayout(jPanelItems);
        jPanelItems.setLayout(jPanelItemsLayout);
        jPanelItemsLayout.setHorizontalGroup(
            jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        jPanelItemsLayout.setVerticalGroup(
            jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 273, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanelItems);

        jPanelItemComanda.add(jScrollPane2);
        jScrollPane2.setBounds(10, 40, 390, 275);

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(17, 24, 39));
        jLabel4.setText("Items de la Comanda");
        jLabel4.setAlignmentX(16.0F);
        jLabel4.setAlignmentY(0.0F);
        jPanelItemComanda.add(jLabel4);
        jLabel4.setBounds(10, 10, 184, 24);

        jPanelResumenComanda.add(jPanelItemComanda);
        jPanelItemComanda.setBounds(0, 100, 410, 320);

        jPanelTotal.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTotal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelTotal.setLayout(null);

        btnRegistrarVenta.setBackground(new java.awt.Color(31, 41, 55));
        btnRegistrarVenta.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarVenta.setText("Registrar Venta");
        btnRegistrarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarVentaActionPerformed(evt);
            }
        });
        jPanelTotal.add(btnRegistrarVenta);
        btnRegistrarVenta.setBounds(30, 140, 340, 38);

        btnCancelar.setBackground(new java.awt.Color(242, 243, 245));
        btnCancelar.setForeground(new java.awt.Color(55, 65, 81));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnCancelar.setMaximumSize(new java.awt.Dimension(180, 40));
        btnCancelar.setPreferredSize(new java.awt.Dimension(180, 40));
        jPanelTotal.add(btnCancelar);
        btnCancelar.setBounds(220, 190, 150, 40);

        jLabel5.setForeground(new java.awt.Color(75, 85, 99));
        jLabel5.setText("Subtotal:");
        jPanelTotal.add(jLabel5);
        jLabel5.setBounds(30, 50, 80, 16);

        jLabel6.setForeground(new java.awt.Color(75, 85, 99));
        jLabel6.setText("Impuestos:");
        jPanelTotal.add(jLabel6);
        jLabel6.setBounds(30, 20, 90, 16);

        txtimpuestos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtimpuestos.setText("$0.00");
        jPanelTotal.add(txtimpuestos);
        txtimpuestos.setBounds(300, 30, 60, 16);

        txtSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtSubtotal.setText("$7.75");
        jPanelTotal.add(txtSubtotal);
        txtSubtotal.setBounds(300, 50, 60, 16);

        btnGuardar2.setBackground(new java.awt.Color(242, 243, 245));
        btnGuardar2.setForeground(new java.awt.Color(55, 65, 81));
        btnGuardar2.setText("Guardar");
        btnGuardar2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnGuardar2.setMaximumSize(new java.awt.Dimension(180, 40));
        btnGuardar2.setPreferredSize(new java.awt.Dimension(180, 40));
        jPanelTotal.add(btnGuardar2);
        btnGuardar2.setBounds(30, 190, 150, 40);

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 17)); // NOI18N
        jLabel8.setText("Total:");
        jPanelTotal.add(jLabel8);
        jLabel8.setBounds(30, 100, 100, 23);

        txtTotal.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotal.setText("$8.53");
        jPanelTotal.add(txtTotal);
        txtTotal.setBounds(280, 100, 90, 20);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanelTotal.add(jPanel1);
        jPanel1.setBounds(30, 80, 340, 3);

        jPanelResumenComanda.add(jPanelTotal);
        jPanelTotal.setBounds(0, 420, 410, 250);

        jPanelFondo.add(jPanelResumenComanda);
        jPanelResumenComanda.setBounds(820, 50, 410, 670);

        jPanelBuscador.setBackground(new java.awt.Color(255, 255, 255));
        jPanelBuscador.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelBuscador.setLayout(null);

        txtBuscar.setText("Buscar Producto...");
        jPanelBuscador.add(txtBuscar);
        txtBuscar.setBounds(18, 16, 785, 42);

        jLabelCategorias.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabelCategorias.setForeground(new java.awt.Color(39, 24, 17));
        jLabelCategorias.setText("Categorias");
        jLabelCategorias.setAlignmentX(16.0F);
        jLabelCategorias.setAlignmentY(0.0F);
        jPanelBuscador.add(jLabelCategorias);
        jLabelCategorias.setBounds(20, 70, 90, 20);

        cbCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoriasActionPerformed(evt);
            }
        });
        jPanelBuscador.add(cbCategorias);
        cbCategorias.setBounds(110, 70, 150, 22);

        jPanelFondo.add(jPanelBuscador);
        jPanelBuscador.setBounds(0, 50, 820, 100);

        jPanelListaProductos.setBackground(new java.awt.Color(255, 255, 255));
        jPanelListaProductos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblProductos.setForeground(new java.awt.Color(255, 255, 255));
        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Producto", "Descripcion"
            }
        ));
        jScrollPane1.setViewportView(tblProductos);

        javax.swing.GroupLayout jPanelListaProductosLayout = new javax.swing.GroupLayout(jPanelListaProductos);
        jPanelListaProductos.setLayout(jPanelListaProductosLayout);
        jPanelListaProductosLayout.setHorizontalGroup(
            jPanelListaProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelListaProductosLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 786, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanelListaProductosLayout.setVerticalGroup(
            jPanelListaProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListaProductosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelFondo.add(jPanelListaProductos);
        jPanelListaProductos.setBounds(0, 150, 820, 570);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelFondo, javax.swing.GroupLayout.DEFAULT_SIZE, 1230, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelFondo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarVentaActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_btnRegistrarVentaActionPerformed

    private void cbCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoriasActionPerformed
        // TODO add your handling code here:}
        
        if (cbCategorias.getSelectedIndex() != -1) { 
            
            int idCategoria = 0;
            
            try{
                String seleccion = cbCategorias.getSelectedItem().toString();
            
                if(seleccion.equalsIgnoreCase("Todos")){
                    buscarProductosPorCategoria(idCategoria);
                }
                else{
                    

                    for (int i = 0; i < todosLosProductos.size(); i++){
                        if(todosLosProductos.get(i).getIdCategoria().getNombre().equalsIgnoreCase(seleccion)){
                            idCategoria = todosLosProductos.get(i).getIdCategoria().getIdCategoria();
                        }
                    }


                    buscarProductosPorCategoria(idCategoria);

                }
            
            }
            
            catch(Exception e){
                e.printStackTrace();
            }
        }
        
        
    }//GEN-LAST:event_cbCategoriasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmListaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmListaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmListaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmListaProductos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmListaProductos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar2;
    private javax.swing.JButton btnRegistrarVenta;
    private javax.swing.JComboBox<String> cbCategorias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelCategorias;
    private javax.swing.JPanel jPaneParaLlevar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelBuscador;
    private javax.swing.JPanel jPanelEncabezado;
    private javax.swing.JPanel jPanelFondo;
    private javax.swing.JPanel jPanelItemComanda;
    private javax.swing.JPanel jPanelItems;
    private javax.swing.JPanel jPanelListaProductos;
    private javax.swing.JPanel jPanelResumenComanda;
    private javax.swing.JPanel jPanelTotal;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JLabel txtSubtotal;
    private javax.swing.JLabel txtTotal;
    private javax.swing.JLabel txtimpuestos;
    // End of variables declaration//GEN-END:variables
}
