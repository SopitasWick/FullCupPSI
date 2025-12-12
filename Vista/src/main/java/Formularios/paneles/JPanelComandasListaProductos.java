/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Formularios.paneles;

import Entidades.Categoria;
import Entidades.Comanda;
import Entidades.Detallecomanda;
import Entidades.Extra;
import Entidades.Producto;
import Facades.IFachadaCategoriaControlador;
import Facades.IFachadaComandasControlador;
import Facades.IFachadaDetallesComandaControlador;
import Facades.IFachadaProductoControlador;
import Formularios.FrmComandas;
import Formularios.BETA.BETAFrmListaProductos;
import Formularios.paneles.elementos.JPanelComandas;
import Formularios.paneles.elementos.JPanelExtra;
import Formularios.paneles.elementos.JPanelProducto;
import Implementaciones.GestionarCategoriaControlador;
import Implementaciones.GestionarComandaControlador;
import Implementaciones.GestionarDetallesComandaControlador;
import Implementaciones.GestionarProductoControlador;
import Impresion.TicketPrinter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterAbortException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import utilerias.ConstantesGUI;
import utilerias.HintToTextField;

/**
 *
 * @author Sergio Arturo
 */
public class JPanelComandasListaProductos extends javax.swing.JPanel {

    
    
    private final IFachadaCategoriaControlador fCategoria = new GestionarCategoriaControlador();
    private final IFachadaProductoControlador fProducto = new GestionarProductoControlador();
    private final IFachadaComandasControlador FComandas = new GestionarComandaControlador();
    private final IFachadaDetallesComandaControlador fDC = new GestionarDetallesComandaControlador();

    
    Dimension dimension = null;
    
    
    List <Producto> listaProductos;
    List <Detallecomanda> detallesComandas;
    List <Categoria> listaCategorias;
    
    ConstantesGUI accion;
    
    float total = 0;
    
  
    
    
    /**
     * Creates new form JPanelComandas
     */
    public JPanelComandasListaProductos(ConstantesGUI accion) {
        initComponents();
        
        this.accion = accion;
        
        cargarDiseno();
        
        
    }
    
    
    private void cargarDiseno(){
    
        HintToTextField.addHint(txtBuscarProducto, "Buscar Producto");
        
        SwingUtilities.invokeLater(() -> {
            jLabelCategorias.requestFocusInWindow(); // El foco se va al label
        });
       
        
        inicializarDatos();
        
        cargarCategorias();
       
        
        if(FrmComandas.comanda != null){
            cargarPanelComanda();
            System.out.println("no nula");
        }

        
        jScrollPane2.setBorder(null);
        
        calcularTotal();
        
    }
    
    
    private void inicializarDatos(){
        
        try{
            if(detallesComandas != null){
                detallesComandas.clear();
            }
            detallesComandas = fDC.obtenerDetallesComandasPorComanda(FrmComandas.comanda);
        }
        
        catch(Exception e){
            e.printStackTrace();
        }
        
        accionesConstantesGUI();
        
        
    }
    
    
    private void accionesConstantesGUI(){
        
        if(accion == ConstantesGUI.SOLOLECTURA){
            cbCategorias.setEnabled(false);
            btnRegistrarVenta.setVisible(false);
            
            txtNombreCliente.setEditable(false);
            txtBuscarProducto.setEditable(false);
            rbParaLlevar.setEnabled(false);
            
        }
        
        else{        
            cargarProductos("Todos", "");       
        }
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
    
        
        
        
        
    private void cargarProductos(String categoria, String textoBuscador){


            jPanelListaProductos.removeAll();

            try {

                if(listaProductos != null){
                    listaProductos.clear();
                }
                listaProductos = fProducto.obtenerTodosLosProductos();

                List<Producto> listaProvicional = new ArrayList<>();
                List<Producto> listaProvicionalEstado = new ArrayList<>();



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


                for(int i = 0; i < listaProvicional.size(); i++){
                    listaProvicionalEstado.add(listaProvicional.get(i));
                }

                listaProvicional.clear();
                    for(int i = 0; i < listaProvicionalEstado.size(); i++){

                        if(listaProvicionalEstado.get(i).getEstado().equalsIgnoreCase("activo")){
                            listaProvicional.add(listaProvicionalEstado.get(i));
                        }
                    }
                
  



                final List<Producto> listaProductosFinal = listaProvicional;

                int alturaProducto = 72; 


                for(int i = 0; i < listaProvicional.size(); i++){

                    final int indiceFinal = i;

                    JPanelProducto producto1 = new JPanelProducto();
                    producto1.setBounds(0, alturaProducto * i, 730, 62);
                    producto1.setAlignmentX(Component.LEFT_ALIGNMENT);
                    producto1.setBackground(Color.decode("#FFFFFF"));

                    producto1.getJblNombreProducto().setText(listaProvicional.get(i).getNombreProducto());
                    producto1.getJblNombreCategoria().setText(listaProvicional.get(i).getIdCategoria().getNombre());
                    producto1.getJblPrecioProducto().setText(String.valueOf(listaProvicional.get(i).getPrecioProducto()));



                    producto1.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            
                            JPanelComandasDetalleProducto panelComandaDetalle;
                            FrmComandas.jPanelSeccion.removeAll();
                            if(FrmComandas.comanda == null){

                                panelComandaDetalle = new JPanelComandasDetalleProducto(ConstantesGUI.NUEVO, null, listaProductosFinal.get(indiceFinal));
                            }
                            else{
                                panelComandaDetalle = new JPanelComandasDetalleProducto(ConstantesGUI.NUEVO, FrmComandas.comanda, listaProductosFinal.get(indiceFinal));
                                
                            }
                            
                            panelComandaDetalle.setBounds(0, 0, FrmComandas.jPanelSeccion.getWidth(), FrmComandas.jPanelSeccion.getHeight());

                            FrmComandas.jPanelSeccion.add(panelComandaDetalle);
                            
                            FrmComandas.jPanelSeccion.revalidate();
                            FrmComandas.jPanelSeccion.repaint();


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


                    producto1.getJblEliminarProducto().setVisible(false);
                     producto1.getJblEliminarProducto().setCursor(new Cursor(1));



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



            } catch (Exception ex) {
                Logger.getLogger(JPanelAdminProductos.class.getName()).log(Level.SEVERE, null, ex);
            }

        }        
 
    
    public void cargarPanelComanda() {

        jPanelItems.removeAll();

        try {
            
        int margenY = 62;
        float subTota = 0;

        for (int i = 0; i < detallesComandas.size(); i++) {

            //subTota = subTota + detallesComandas.get(i).getSubTotaldetalleComanda();

            //SubPanel
            JPanelProducto subPanel = new JPanelProducto();
            subPanel.setBackground(Color.WHITE);
            subPanel.setOpaque(false);
            subPanel.setBounds(0, margenY * i, 380, 58);
            subPanel.setLayout(null);
            subPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            //Nombre del producto
            JLabel jlabelNombreProducto = new JLabel();
            jlabelNombreProducto.setBounds(10, 8, 300, 20);
            jlabelNombreProducto.setFont(new java.awt.Font("Segoe UI", 1, 16));
            jlabelNombreProducto.setText(detallesComandas.get(i).getIdProducto().getNombreProducto());
            subPanel.add(jlabelNombreProducto);

            //Detalles del producto
            JLabel jlabelDetalleProducto = new JLabel();
            jlabelDetalleProducto.setBounds(10, 32, 300, 20);
            jlabelDetalleProducto.setFont(new java.awt.Font("Segoe UI", 0, 10));
            jlabelDetalleProducto.setText(detallesComandas.get(i).getNotadetalleComanda());
            subPanel.add(jlabelDetalleProducto);

            //Precio del producto
            JLabel jlabelPrecio = new JLabel();
            jlabelPrecio.setBounds(subPanel.getWidth() - 130, 8, 80, 20);
            jlabelPrecio.setHorizontalAlignment(SwingConstants.RIGHT);
            jlabelPrecio.setText(detallesComandas.get(i).getSubTotaldetalleComanda().toString());
            subPanel.add(jlabelPrecio);

            //boton Eliminar
            subPanel.getJblEliminarProducto().setBounds(340, 8, 35, 35);

            
            if(accion == ConstantesGUI.SOLOLECTURA){
                subPanel.getJblEliminarProducto().setVisible(false);
            }
            
            subPanel.getJblNombreProducto().setVisible(false);
            subPanel.getJblNombreCategoria().setVisible(false);
            subPanel.getJblPrecioProducto().setVisible(false);

            int id = i;
            Detallecomanda detalle = detallesComandas.get(i);

                if(accion != ConstantesGUI.SOLOLECTURA){
                    subPanel.getJblEliminarProducto().addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {


                            FrmComandas.detalleComanda = detalle;

                            JPanelComandasDetalleProducto panelComandaDetalle;
                            FrmComandas.jPanelSeccion.removeAll();

                            panelComandaDetalle = new JPanelComandasDetalleProducto(ConstantesGUI.ELIMINAR, FrmComandas.comanda, detalle.getIdProducto());

                            panelComandaDetalle.setLectura(true);
                            
                            panelComandaDetalle.setBounds(0, 0, FrmComandas.jPanelSeccion.getWidth(), FrmComandas.jPanelSeccion.getHeight());

                            FrmComandas.jPanelSeccion.add(panelComandaDetalle);

                            FrmComandas.jPanelSeccion.revalidate();
                            FrmComandas.jPanelSeccion.repaint();


                        }

                    });
                }
            

            subPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                        
                    FrmComandas.detalleComanda = detalle;
                        
                        JPanelComandasDetalleProducto panelComandaDetalle;
                        FrmComandas.jPanelSeccion.removeAll();

                        if(accion == ConstantesGUI.SOLOLECTURA){
                            panelComandaDetalle = new JPanelComandasDetalleProducto(ConstantesGUI.SOLOLECTURA, FrmComandas.comanda, detalle.getIdProducto());
                        }
                        else{
                            panelComandaDetalle = new JPanelComandasDetalleProducto(ConstantesGUI.EDITAR, FrmComandas.comanda, detalle.getIdProducto());

                        }
                        
                        panelComandaDetalle.setBounds(0, 0, FrmComandas.jPanelSeccion.getWidth(), FrmComandas.jPanelSeccion.getHeight());

                        FrmComandas.jPanelSeccion.add(panelComandaDetalle);
                            
                        FrmComandas.jPanelSeccion.revalidate();
                        FrmComandas.jPanelSeccion.repaint();
                    

                }
                
                @Override
                public void mouseEntered(MouseEvent e){
                    subPanel.setBackground(Color.decode("#E0E0E0"));

                }
                
                @Override
                public void mouseExited(MouseEvent e){
                    subPanel.setBackground(Color.decode("#FFFFFF"));
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

        }

        jPanelItems.revalidate();
        jPanelItems.repaint();

        // Calcular IVA (16%)
        float iva = subTota * 0.16f;

        // Calcular Total
        float total = subTota + iva;

        // Asignar valores a campos
        txtSubtotal.setText(String.valueOf(subTota));
        txtimpuestos.setText(String.valueOf(iva));
        txtTotal.setText(String.valueOf(total));
        
        } catch (Exception ex) {
            Logger.getLogger(FrmComandas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    private void calcularTotal(){
        if(FrmComandas.comanda != null){
            try {
                
                List <Detallecomanda> listeTemp = fDC.obtenerDetallesComandasPorComanda(FrmComandas.comanda);
                float precioTotal = 0;
                for(int i = 0; i < listeTemp.size(); i++){
                    precioTotal = precioTotal + listeTemp.get(i).getSubTotaldetalleComanda();
                }
                
                txtSubtotal.setText(String.valueOf(precioTotal));
                
                float iva = precioTotal * 0.16f;
                
                txtimpuestos.setText(String.valueOf(iva));
                
                txtTotal.setText(String.valueOf(precioTotal + iva));
                
                
                
            } catch (Exception ex) {
                Logger.getLogger(JPanelComandasListaProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    
    
   private void volverSeccionAnterior(){
        
        FrmComandas.comanda = null;
        FrmComandas.detalleComanda = null;
        
        FrmComandas.jPanelSeccion.removeAll();
        
        JPanelComandasGeneral panelGeneral = new JPanelComandasGeneral();
        
        panelGeneral.setBounds(0, 0, FrmComandas.jPanelSeccion.getWidth(), FrmComandas.jPanelSeccion.getHeight());
        FrmComandas.jPanelSeccion.add(panelGeneral);

        FrmComandas.jPanelSeccion.revalidate();
        FrmComandas.jPanelSeccion.repaint();
        
    
    }  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPaneParaLlevar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        rbParaLlevar = new javax.swing.JRadioButton();
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
        jLabel8 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabelCategorias = new javax.swing.JLabel();
        cbCategorias = new javax.swing.JComboBox<>();
        txtBuscarProducto = new javax.swing.JTextField();
        jScrollPaneProductos = new javax.swing.JScrollPane();
        jPanelListaProductos = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMinimumSize(new java.awt.Dimension(1210, 560));
        setPreferredSize(new java.awt.Dimension(1210, 560));
        setLayout(null);

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

        txtNombreCliente.setMaximumSize(new java.awt.Dimension(430, 40));
        txtNombreCliente.setMinimumSize(new java.awt.Dimension(0, 0));
        txtNombreCliente.setPreferredSize(new java.awt.Dimension(430, 40));
        jPaneParaLlevar.add(txtNombreCliente);
        txtNombreCliente.setBounds(20, 60, 220, 30);

        rbParaLlevar.setBackground(new java.awt.Color(255, 255, 255));
        rbParaLlevar.setText("Para Llevar");
        rbParaLlevar.setOpaque(true);
        jPaneParaLlevar.add(rbParaLlevar);
        rbParaLlevar.setBounds(290, 65, 98, 21);

        add(jPaneParaLlevar);
        jPaneParaLlevar.setBounds(800, 0, 410, 100);

        jPanelItemComanda.setBackground(new java.awt.Color(255, 255, 255));
        jPanelItemComanda.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelItemComanda.setLayout(null);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanelItems.setBackground(new java.awt.Color(255, 255, 255));
        jPanelItems.setPreferredSize(new java.awt.Dimension(325, 190));

        javax.swing.GroupLayout jPanelItemsLayout = new javax.swing.GroupLayout(jPanelItems);
        jPanelItems.setLayout(jPanelItemsLayout);
        jPanelItemsLayout.setHorizontalGroup(
            jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
        );
        jPanelItemsLayout.setVerticalGroup(
            jPanelItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanelItems);

        jPanelItemComanda.add(jScrollPane2);
        jScrollPane2.setBounds(10, 40, 390, 200);

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(17, 24, 39));
        jLabel4.setText("Items de la Comanda");
        jLabel4.setAlignmentX(16.0F);
        jLabel4.setAlignmentY(0.0F);
        jPanelItemComanda.add(jLabel4);
        jLabel4.setBounds(10, 10, 184, 24);

        add(jPanelItemComanda);
        jPanelItemComanda.setBounds(800, 100, 410, 250);

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
        btnRegistrarVenta.setBounds(30, 100, 340, 38);

        btnCancelar.setBackground(new java.awt.Color(242, 243, 245));
        btnCancelar.setForeground(new java.awt.Color(55, 65, 81));
        btnCancelar.setText("Volver");
        btnCancelar.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnCancelar.setMaximumSize(new java.awt.Dimension(180, 40));
        btnCancelar.setPreferredSize(new java.awt.Dimension(180, 40));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanelTotal.add(btnCancelar);
        btnCancelar.setBounds(30, 150, 150, 40);

        jLabel5.setForeground(new java.awt.Color(75, 85, 99));
        jLabel5.setText("Subtotal:");
        jPanelTotal.add(jLabel5);
        jLabel5.setBounds(30, 30, 80, 16);

        jLabel6.setForeground(new java.awt.Color(75, 85, 99));
        jLabel6.setText("Impuestos:");
        jPanelTotal.add(jLabel6);
        jLabel6.setBounds(30, 10, 90, 16);

        txtimpuestos.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtimpuestos.setText("$0.00");
        jPanelTotal.add(txtimpuestos);
        txtimpuestos.setBounds(300, 10, 60, 16);

        txtSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtSubtotal.setText("$0.00");
        jPanelTotal.add(txtSubtotal);
        txtSubtotal.setBounds(300, 30, 60, 16);

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 17)); // NOI18N
        jLabel8.setText("Total:");
        jPanelTotal.add(jLabel8);
        jLabel8.setBounds(30, 70, 100, 23);

        txtTotal.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtTotal.setText("$0.00");
        jPanelTotal.add(txtTotal);
        txtTotal.setBounds(280, 70, 90, 20);

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
        jPanel1.setBounds(30, 50, 340, 3);

        add(jPanelTotal);
        jPanelTotal.setBounds(800, 350, 410, 210);

        jLabelCategorias.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabelCategorias.setForeground(new java.awt.Color(39, 24, 17));
        jLabelCategorias.setText("Categorias");
        jLabelCategorias.setAlignmentX(16.0F);
        jLabelCategorias.setAlignmentY(0.0F);
        add(jLabelCategorias);
        jLabelCategorias.setBounds(20, 65, 90, 20);

        cbCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Deshabilitado" }));
        cbCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoriasActionPerformed(evt);
            }
        });
        add(cbCategorias);
        cbCategorias.setBounds(110, 65, 150, 22);

        txtBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyTyped(evt);
            }
        });
        add(txtBuscarProducto);
        txtBuscarProducto.setBounds(20, 10, 630, 42);

        jScrollPaneProductos.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneProductos.setBorder(null);

        jPanelListaProductos.setBackground(new java.awt.Color(255, 255, 255));
        jPanelListaProductos.setMinimumSize(new java.awt.Dimension(300, 200));
        jPanelListaProductos.setPreferredSize(new java.awt.Dimension(500, 200));
        jPanelListaProductos.setLayout(null);
        jScrollPaneProductos.setViewportView(jPanelListaProductos);

        add(jScrollPaneProductos);
        jScrollPaneProductos.setBounds(20, 110, 760, 430);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarVentaActionPerformed
        // TODO add your handling code here:
        if (FrmComandas.comanda != null && !detallesComandas.isEmpty()) {
            try {
                // Reiniciar el total para evitar acumulación en múltiples clics
                this.total = 0f;

                // Calcular el subtotal sumando los subtotales de cada detalle
                for (Detallecomanda detalle2 : detallesComandas) {
                    if (detalle2.getSubTotaldetalleComanda() != null) {
                        this.total += detalle2.getSubTotaldetalleComanda();
                    }
                }

                // ---- APLICAR IVA (16%) ----
                float iva = this.total * 0.16f;
                float totalConIVA = this.total + iva;

                // Guardar el total con IVA en la variable total
                this.total = totalConIVA;

                // Guardar nombre del cliente y si es para llevar
                String nombre = txtNombreCliente.getText().trim();
                if (nombre.equals("Nombre del cliente") || nombre.isEmpty()) {
                    nombre = "Cliente General"; // Asignar un valor por defecto
                }

                // El texto debe ser "Para llevar: Si"
                String paraLlevar = rbParaLlevar.isSelected() ? "Llevar: Si" : "Llevar: No";

                // El texto debe incluir "Cliente: "
                String descripcion = nombre + ", " + paraLlevar;

                FrmComandas.comanda.setDescripcionGeneral(descripcion);

                // Actualizar el total EN LA BASE DE DATOS (ya trae IVA)
                FComandas.EditarTotalComanda(FrmComandas.comanda.getIdComanda(), this.total);

                if (FrmComandas.comanda != null) {

                    // --- MODO EDITAR ---
                    // La comanda ya existe, solo actualizamos la descripción.
                    System.out.println("Guardando descripción en comanda existente ID: " + FrmComandas.comanda.getIdComanda());
                    FComandas.EditarDescripcionComanda(FrmComandas.comanda.getIdComanda(), descripcion);
                }

                // IMPRIMIR **********************************************
                // Imprimir ticket
                FrmComandas.comanda.setDetallecomandaList(
                    fDC.obtenerDetallesComandasPorComanda(FrmComandas.comanda)
                );

                System.out.println("Imprimir lista detalles comanda: " + detallesComandas);

                PrintService defaultPrinter = PrintServiceLookup.lookupDefaultPrintService();
                if (defaultPrinter != null) {
                    PrinterJob job = PrinterJob.getPrinterJob();
                    job.setPrintService(defaultPrinter);
                    job.setPrintable(new TicketPrinter(FrmComandas.comanda));
                    try {
                        job.print();
                    }
                    catch (PrinterAbortException pae) {
                        System.out.println("La impresión fue cancelada por el usuario.");
                        // Aquí NO debe tronar. Simplemente continúas.
                    }

                } else {
                    System.err.println("⚠️ No se encontró una impresora predeterminada.");
                }

                
                FComandas.comandaCompletada(FrmComandas.comanda.getIdComanda());
                
                // Cerrar ventana actual y volver a la lista de comandas
                volverSeccionAnterior();

            } catch (Exception ex) {
                Logger.getLogger(BETAFrmListaProductos.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this,
                    "Error al guardar los cambios en la comanda: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "Debe seleccionar al menos un producto",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
        

    }//GEN-LAST:event_btnRegistrarVentaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        volverSeccionAnterior();
      
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void cbCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoriasActionPerformed
        // TODO add your handling code here:}

        String seleccion = cbCategorias.getSelectedItem() != null
        ? cbCategorias.getSelectedItem().toString()
        : "";

        cargarProductos(seleccion, txtBuscarProducto.getText());
    }//GEN-LAST:event_cbCategoriasActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegistrarVenta;
    private javax.swing.JComboBox<String> cbCategorias;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelCategorias;
    private javax.swing.JPanel jPaneParaLlevar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelItemComanda;
    private javax.swing.JPanel jPanelItems;
    private javax.swing.JPanel jPanelListaProductos;
    private javax.swing.JPanel jPanelTotal;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneProductos;
    private javax.swing.JRadioButton rbParaLlevar;
    private javax.swing.JTextField txtBuscarProducto;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JLabel txtSubtotal;
    private javax.swing.JLabel txtTotal;
    private javax.swing.JLabel txtimpuestos;
    // End of variables declaration//GEN-END:variables
}
