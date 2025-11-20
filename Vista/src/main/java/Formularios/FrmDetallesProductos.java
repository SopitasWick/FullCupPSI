/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Formularios;

import Entidades.Comanda;
import Entidades.Detallecomanda;
import Entidades.ExtrasProductos;
import Entidades.Leche;
import Entidades.Producto;
import Entidades.TamanoVaso;
import Entidades.Valoropcion;
import Facades.IFachadaCategoriaControlador;
import Facades.IFachadaComandasControlador;
import Facades.IFachadaDetallesComandaControlador;
import Facades.IFachadaExtrasControlador;
import Facades.IFachadaLecheControlador;
import Facades.IFachadaProductoControlador;
import Facades.IFachadaTamanoVasosControlador;
import static Formularios.FrmListaProductos.comanda;
import static Formularios.FrmListaProductos.detalleComanda;
import Implementaciones.GestionarCategoriaControlador;
import Implementaciones.GestionarComandaControlador;
import Implementaciones.GestionarDetallesComandaControlador;
import Implementaciones.GestionarExtrasControlador;
import Implementaciones.GestionarLecheControlador;
import Implementaciones.GestionarProductoControlador;
import Implementaciones.GestionarTamanoVasosControlador;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author Sergio Arturo
 */
public class FrmDetallesProductos extends javax.swing.JFrame {

    
    
    private final IFachadaProductoControlador fProductos = new GestionarProductoControlador();
    private final IFachadaTamanoVasosControlador fVaso = new GestionarTamanoVasosControlador();
    private final IFachadaLecheControlador fLeche = new GestionarLecheControlador();
    private final IFachadaComandasControlador FComandas = new GestionarComandaControlador();
    private final IFachadaExtrasControlador fExtras = new GestionarExtrasControlador();
    private final IFachadaCategoriaControlador fCategoria = new GestionarCategoriaControlador();
    private final IFachadaTamanoVasosControlador fVasos = new GestionarTamanoVasosControlador();
    private final IFachadaDetallesComandaControlador fDC = new GestionarDetallesComandaControlador();

    
    
    Dimension dimension = null;

    
    
    private Map<Valoropcion, Integer> extrasSeleccionados = new HashMap<>();

    
    List<ExtrasProductos> extras = new ArrayList<>();
    
    
    FrmListaProductos pantallaProductos;
    
    
    Detallecomanda detalle;
    Producto producto;
    Comanda comanda;
    boolean soloLectura;
    
    
    TamanoVaso vaso = null;
    Leche leche = null;
    
    
    
    /**
     * Creates new form FrmDetallesProductos
     */
    public FrmDetallesProductos() {
        initComponents();
    }
    
    
    public FrmDetallesProductos(FrmListaProductos pantallaProductos, Detallecomanda detalle, Producto producto, boolean soloLectura) {
        initComponents();
        
        
        this.pantallaProductos = pantallaProductos;
        this.detalle = detalle;
        this.producto = producto;
        this.soloLectura = soloLectura;
        
        jPanel13.setVisible(false); // visualizar jpanelpromociones---------------------------------------------------------
        jblSoloLectura.setVisible(false);
        if(soloLectura){
            soloLectura();
        }
        
        if(detalle != null){
            System.out.println("detalle no es nulo");
            try {
                comanda = FComandas.obtenerComanda(this.detalle.getIdComanda().getIdComanda());
            } catch (Exception ex) {
                Logger.getLogger(FrmDetallesProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
            cargarDatosEditar();
        }
        else{
            System.out.println("detalle es nulo");
            cargarDatos();
        }
        
        calcularTotal();
        
    }
    
    
    public FrmDetallesProductos(FrmListaProductos pantallaProductos, Comanda comanda, Detallecomanda detalle, Producto producto, boolean soloLectura) {
        initComponents();
        
        
        this.pantallaProductos = pantallaProductos;
        this.detalle = detalle;
        this.producto = producto;
        this.comanda = comanda;
        this.soloLectura = soloLectura;
        
        
        
        
        jblSoloLectura.setVisible(false);
        if(soloLectura){
           soloLectura();
        }
        
        
        
        if(detalle != null){
            System.out.println("detalle no es nulo");
            cargarDatosEditar();
        }
        else{
            System.out.println("detalle es nulo");
            cargarDatos();
        }
        calcularTotal();
        jPanel13.setVisible(false);
        
    }
    
    
    
    private void soloLectura(){
        btnGuardarCambios.setEnabled(false);
        btnGuardarCambios.setVisible(false);
        
        txtDescripcion.setEditable(false);
        
        jblSoloLectura.setVisible(true);
        
        
        
    }
    
    
    
    private void cargarDatos() {

        txtNombreProducto.setText(producto.getNombreProducto());
        txtPrecioBase.setText("Precio: " + producto.getPrecioProducto());
        txtProductoResumen.setText(producto.getNombreProducto());

        try {
            List<Valoropcion> opciones = fProductos.obtenerDetallesPorProducto(producto.getIdProducto());
            jPanelExtras.removeAll();

            // ====== Verificar qué opciones requiere el producto ======
            boolean requiereVaso = false;
            boolean requiereLeche = false;
            boolean requieresBebidas = false; //extras

            for (Valoropcion vo : opciones) {
                String nombreOpcion = vo.getIdOpcionProducto().getNombreOpcion().toLowerCase();
                System.out.println(nombreOpcion);
                if (nombreOpcion.contains("tipo vaso")) requiereVaso = true;
                if (nombreOpcion.contains("tipo leche")) requiereLeche = true;
                if (nombreOpcion.contains("extra bebidas")) requieresBebidas = true;
            }

            // ====== Subpaneles ======
            JPanel subPanelVaso = null;
            JPanel subPanelLeche = null;
           // JPanel subPanelExtras = null;

            // ====== Panel de tipo vaso ======
            if (requiereVaso) {
                subPanelVaso = new JPanel();
                subPanelVaso.setName("Tipo vaso");
                subPanelVaso.setBackground(Color.WHITE);
                subPanelVaso.setOpaque(false);
                subPanelVaso.setBorder(new LineBorder(Color.GRAY));
                subPanelVaso.setBounds(0, 0, 480, 160); // <- TUS bounds originales
                subPanelVaso.setLayout(null);

                JLabel jlabelTituloVaso = new JLabel();
                jlabelTituloVaso.setBounds(subPanelVaso.getX(), 8, subPanelVaso.getWidth(), 20);
                jlabelTituloVaso.setHorizontalAlignment(SwingConstants.CENTER);
                jlabelTituloVaso.setFont(new java.awt.Font("Segoe UI", 1, 18));
                jlabelTituloVaso.setText("Tipo de vaso");
                subPanelVaso.add(jlabelTituloVaso);

                JPanel jPanelElementosVasos = new JPanel();
                jPanelElementosVasos.setBounds(10, 42, subPanelVaso.getWidth() - 20, subPanelVaso.getHeight() - 52);
                jPanelElementosVasos.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

                List<TamanoVaso> vasos = fVaso.obtenerTodosLosTamanosVasos();
                List<JPanel> listaSubPanelesVasos = new ArrayList<>();

                for (int j = 0; j < vasos.size(); j++) {
                    int idVaso = j;

                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                    panel.setBackground(Color.WHITE);
                    panel.setPreferredSize(new Dimension(120, 80));
                    panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    JLabel lbl1 = new JLabel(vasos.get(j).getNombre(), SwingConstants.CENTER);
                    JLabel lbl3 = new JLabel("$" + vasos.get(j).getPrecio(), SwingConstants.CENTER);

                    lbl1.setAlignmentX(Component.CENTER_ALIGNMENT);
                    lbl3.setAlignmentX(Component.CENTER_ALIGNMENT);

                    panel.add(Box.createVerticalGlue());
                    panel.add(lbl1);
                    panel.add(lbl3);
                    panel.add(Box.createVerticalGlue());

                    panel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            for (JPanel p : listaSubPanelesVasos) {
                                p.setBackground(Color.WHITE);
                                p.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                            }
                            panel.setBackground(new Color(173, 216, 230));
                            panel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                            vaso = vasos.get(idVaso);
                            calcularTotal();
                            actualizarNota();
                        }
                    });

                    listaSubPanelesVasos.add(panel);
                    jPanelElementosVasos.add(panel);
                }

                subPanelVaso.add(jPanelElementosVasos);
                jPanelExtras.add(subPanelVaso);
            }

            // ====== Panel de tipo leche ======
            if (requiereLeche) {
                subPanelLeche = new JPanel();
                subPanelLeche.setName("Tipo leche");
                subPanelLeche.setBackground(Color.WHITE);
                subPanelLeche.setOpaque(false);
                subPanelLeche.setBorder(new LineBorder(Color.GRAY));

                if (subPanelVaso != null) {
                    subPanelLeche.setBounds(0, subPanelVaso.getHeight() + 20, 480, subPanelVaso.getHeight() + 20);
                } else {
                    subPanelLeche.setBounds(0, 0, 480, 180);
                }
                subPanelLeche.setLayout(null);

                JLabel jlabelTituloLeche = new JLabel();
                jlabelTituloLeche.setBounds(subPanelLeche.getX(), 8, subPanelLeche.getWidth(), 20);
                jlabelTituloLeche.setHorizontalAlignment(SwingConstants.CENTER);
                jlabelTituloLeche.setFont(new java.awt.Font("Segoe UI", 1, 18));
                jlabelTituloLeche.setText("Tipo de leche");
                subPanelLeche.add(jlabelTituloLeche);

                JPanel jPanelElementosLeches = new JPanel();
                jPanelElementosLeches.setBounds(10, 42, subPanelLeche.getWidth() - 20, subPanelLeche.getHeight() - 52);
                jPanelElementosLeches.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

                List<Leche> leches = fLeche.obtenerTodasLasLeches();
                List<JPanel> listaSubPanelesLeches = new ArrayList<>();

                for (int j = 0; j < leches.size(); j++) {
                    int idLeche = j;

                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                    panel.setBackground(Color.WHITE);
                    panel.setPreferredSize(new Dimension(120, 40));
                    panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    JLabel lbl1 = new JLabel(leches.get(j).getNombre(), SwingConstants.CENTER);
                    JLabel lbl3 = new JLabel("$" + leches.get(j).getPrecio(), SwingConstants.CENTER);

                    lbl1.setAlignmentX(Component.CENTER_ALIGNMENT);
                    lbl3.setAlignmentX(Component.CENTER_ALIGNMENT);

                    panel.add(Box.createVerticalGlue());
                    panel.add(lbl1);
                    panel.add(lbl3);
                    panel.add(Box.createVerticalGlue());

                    panel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            for (JPanel p : listaSubPanelesLeches) {
                                p.setBackground(Color.WHITE);
                                p.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                            }
                            panel.setBackground(new Color(173, 216, 230));
                            panel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                            leche = leches.get(idLeche);
                            calcularTotal();
                            actualizarNota();
                        }
                    });

                    listaSubPanelesLeches.add(panel);
                    jPanelElementosLeches.add(panel);
                }

                subPanelLeche.add(jPanelElementosLeches);
                jPanelExtras.add(subPanelLeche);
            }
            
            
            
           // ====== Panel de tipo extras ======
            if (requieresBebidas) {
                JPanel subPanelExtras = new JPanel();
                subPanelExtras.setName("Tipo extra");
                subPanelExtras.setBackground(Color.WHITE);
                subPanelExtras.setOpaque(false);
                subPanelExtras.setBorder(new LineBorder(Color.GRAY));
                subPanelExtras.setLayout(null);
                subPanelExtras.setBounds(500, 0, 350, jPanelExtras.getHeight() - 42);

                // --- Título ---
                JLabel jlabelTituloExtra = new JLabel("Tipo de extra", SwingConstants.CENTER);
                jlabelTituloExtra.setBounds(0, 8, subPanelExtras.getWidth(), 20);
                jlabelTituloExtra.setFont(new Font("Segoe UI", Font.BOLD, 18));
                subPanelExtras.add(jlabelTituloExtra);

                // --- Panel interno con BoxLayout vertical ---
                JPanel contenedorExtras = new JPanel();
                contenedorExtras.setLayout(new BoxLayout(contenedorExtras, BoxLayout.Y_AXIS));
                contenedorExtras.setBackground(Color.WHITE);

                // --- Scroll ---
                JScrollPane scrollExtras = new JScrollPane(contenedorExtras);
                scrollExtras.setBounds(10, 40, subPanelExtras.getWidth() - 20, subPanelExtras.getHeight() - 50);
                scrollExtras.setBorder(null);
                scrollExtras.getVerticalScrollBar().setUnitIncrement(16);
                scrollExtras.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scrollExtras.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

                subPanelExtras.add(scrollExtras);

                List<JPanel> listaSubPanelesExtras = new ArrayList<>();

                for (int j = 0; j < opciones.size(); j++) {

                    if (opciones.get(j).getIdOpcionProducto().getNombreOpcion().equalsIgnoreCase("Extra bebidas")) {

                        Valoropcion extra = opciones.get(j);

                        // PANEL CONTENEDOR PRINCIPAL
                        JPanel panel = new JPanel();
                        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                        panel.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                                BorderFactory.createEmptyBorder(8, 8, 8, 8)
                        ));
                        panel.setBackground(Color.WHITE);
                        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

                        // ---------- Fila 1: Nombre + Precio ----------
                        JPanel fila1 = new JPanel(new BorderLayout());
                        fila1.setOpaque(false);

                        JLabel lblNombre = new JLabel(extra.getNombreValor());
                        JLabel lblPrecio = new JLabel("$" + extra.getCosteAdicional());

                        fila1.add(lblNombre, BorderLayout.WEST);
                        fila1.add(lblPrecio, BorderLayout.EAST);

                        panel.add(fila1);
                        panel.add(Box.createVerticalStrut(6));

                        // ---------- Fila 2: Cantidad y Spinner ----------
                        JPanel fila2 = new JPanel(new BorderLayout());
                        fila2.setOpaque(false);

                        JLabel lblCantidad = new JLabel("Cantidad:");

                        JSpinner spiner = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
                        spiner.setPreferredSize(new Dimension(60, 26));
                        spiner.setMaximumSize(new Dimension(60, 26));

                        fila2.add(lblCantidad, BorderLayout.WEST);
                        fila2.add(spiner, BorderLayout.EAST);

                        panel.add(fila2);
                        panel.add(Box.createVerticalStrut(4));


                        if (!soloLectura) {
                            spiner.addChangeListener(e -> {
                                int cantidad = (int) spiner.getValue();

                                if (cantidad > 0) {
                                    extrasSeleccionados.put(extra, cantidad);
                                } else {
                                    extrasSeleccionados.remove(extra);
                                }

                                calcularTotal();
                                actualizarNota();
                            });
                        } else {
                            spiner.setEnabled(false);
                        }

                        listaSubPanelesExtras.add(panel);
                        contenedorExtras.add(panel);
                        contenedorExtras.add(Box.createVerticalStrut(10));

                        // AJUSTAR ALTURA DEL CONTENEDOR
                        int alturaTotal = (listaSubPanelesExtras.size() * 90) + (listaSubPanelesExtras.size() * 10);

                        contenedorExtras.setPreferredSize(new Dimension(
                                scrollExtras.getWidth() - 20,
                                Math.max(alturaTotal, scrollExtras.getHeight())
                        ));

                        contenedorExtras.revalidate();
                        contenedorExtras.repaint();
                    }
                }

                if (soloLectura) subPanelExtras.setEnabled(false);

                jPanelExtras.add(subPanelExtras);
            }else{
                JPanel subPanelExtras = new JPanel();
                subPanelExtras.setName("Tipo extra");
                subPanelExtras.setBackground(Color.WHITE);
                subPanelExtras.setOpaque(false);
                subPanelExtras.setBorder(new LineBorder(Color.GRAY));
                subPanelExtras.setLayout(null);
                subPanelExtras.setBounds(500, 0, 350, jPanelExtras.getHeight() - 42);

                // --- Título ---
                JLabel jlabelTituloExtra = new JLabel("Tipo de extra", SwingConstants.CENTER);
                jlabelTituloExtra.setBounds(0, 8, subPanelExtras.getWidth(), 20);
                jlabelTituloExtra.setFont(new Font("Segoe UI", Font.BOLD, 18));
                subPanelExtras.add(jlabelTituloExtra);

                // --- Panel interno con BoxLayout vertical ---
                JPanel contenedorExtras = new JPanel();
                contenedorExtras.setLayout(new BoxLayout(contenedorExtras, BoxLayout.Y_AXIS));
                contenedorExtras.setBackground(Color.WHITE);

                // --- Scroll ---
                JScrollPane scrollExtras = new JScrollPane(contenedorExtras);
                scrollExtras.setBounds(10, 40, subPanelExtras.getWidth() - 20, subPanelExtras.getHeight() - 50);
                scrollExtras.setBorder(null);
                scrollExtras.getVerticalScrollBar().setUnitIncrement(16);
                scrollExtras.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scrollExtras.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

                subPanelExtras.add(scrollExtras);

                List<JPanel> listaSubPanelesExtras = new ArrayList<>();

                for (int j = 0; j < opciones.size(); j++) {

             String nombreOpcion = opciones.get(j).getIdOpcionProducto().getNombreOpcion().toLowerCase();

           if (nombreOpcion.contains("extra")) {

             Valoropcion extra = opciones.get(j);

                        // PANEL CONTENEDOR PRINCIPAL
                        JPanel panel = new JPanel();
                        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                        panel.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                                BorderFactory.createEmptyBorder(8, 8, 8, 8)
                        ));
                        panel.setBackground(Color.WHITE);
                        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

                        // ---------- Fila 1: Nombre + Precio ----------
                        JPanel fila1 = new JPanel(new BorderLayout());
                        fila1.setOpaque(false);

                        JLabel lblNombre = new JLabel(extra.getNombreValor());
                        JLabel lblPrecio = new JLabel("$" + extra.getCosteAdicional());

                        fila1.add(lblNombre, BorderLayout.WEST);
                        fila1.add(lblPrecio, BorderLayout.EAST);

                        panel.add(fila1);
                        panel.add(Box.createVerticalStrut(6));

                        // ---------- Fila 2: Cantidad y Spinner ----------
                        JPanel fila2 = new JPanel(new BorderLayout());
                        fila2.setOpaque(false);

                        JLabel lblCantidad = new JLabel("Cantidad:");

                        JSpinner spiner = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
                        spiner.setPreferredSize(new Dimension(60, 26));
                        spiner.setMaximumSize(new Dimension(60, 26));

                        fila2.add(lblCantidad, BorderLayout.WEST);
                        fila2.add(spiner, BorderLayout.EAST);

                        panel.add(fila2);
                        panel.add(Box.createVerticalStrut(4));


                        if (!soloLectura) {
                            spiner.addChangeListener(e -> {
                                int cantidad = (int) spiner.getValue();

                                if (cantidad > 0) {
                                    extrasSeleccionados.put(extra, cantidad);
                                } else {
                                    extrasSeleccionados.remove(extra);
                                }

                                calcularTotal();
                                actualizarNota();
                            });
                        } else {
                            spiner.setEnabled(false);
                        }

                        listaSubPanelesExtras.add(panel);
                        contenedorExtras.add(panel);
                        contenedorExtras.add(Box.createVerticalStrut(10));

                        // AJUSTAR ALTURA DEL CONTENEDOR
                        int alturaTotal = (listaSubPanelesExtras.size() * 90) + (listaSubPanelesExtras.size() * 10);

                        contenedorExtras.setPreferredSize(new Dimension(
                                scrollExtras.getWidth() - 20,
                                Math.max(alturaTotal, scrollExtras.getHeight())
                        ));

                        contenedorExtras.revalidate();
                        contenedorExtras.repaint();
                    }
                }

                if (soloLectura) subPanelExtras.setEnabled(false);

                jPanelExtras.add(subPanelExtras);
            }   
     
            // ====== Refrescar vista ======
            jPanelExtras.revalidate();
            jPanelExtras.repaint();

        } catch (Exception ex) {
            Logger.getLogger(FrmDetallesProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    
    private void cargarDatosEditar() {

        txtNombreProducto.setText(producto.getNombreProducto());
        txtPrecioBase.setText("Precio: " + producto.getPrecioProducto());
        txtProductoResumen.setText(producto.getNombreProducto());
        txtDescripcion.setText(detalle.getNotadetalleComanda());
        
        List <ExtrasProductos> extrasEncontrados = detalle.getExtrasProductosList();//CEHCAR ESTA TABLA Y AGREGAR LO DE LAS CREPAS PARA QUE APAREZCA
        System.out.println("extras en" + extrasEncontrados.size());
        
        
        extrasSeleccionados.clear(); // Limpia antes de cargar

        if (detalle != null && extrasEncontrados != null && !extrasEncontrados.isEmpty()) {
            // Obtener todas las opciones del producto una sola vez
            List<Valoropcion> opciones = null;
            try {
                opciones = fProductos.obtenerDetallesPorProducto(producto.getIdProducto());
            } catch (Exception ex) {
                Logger.getLogger(FrmDetallesProductos.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (ExtrasProductos extra : extrasEncontrados) {
                // Solo procesar los que sean "Extra bebidas"
                for (Valoropcion vo : opciones) {
                    if (vo.getIdOpcionProducto().getNombreOpcion().equalsIgnoreCase("Extra bebidas") &&
                        vo.getNombreValor().equalsIgnoreCase(extra.getNombreExtra())) {

                        extrasSeleccionados.put(vo, extra.getCantidad());
                        break;
                    }
                }
            }
    }
        
        
        
        if(extrasEncontrados != null && !extrasEncontrados.isEmpty()){
            for(int i = 0; i < extrasEncontrados.size(); i++){
                if(extrasEncontrados.get(i).getIdTamanoVaso() != null){
                    vaso = extrasEncontrados.get(i).getIdTamanoVaso();
                }
                
                if(extrasEncontrados.get(i).getIdLeche() != null){
                    leche = extrasEncontrados.get(i).getIdLeche();
                }
                
                
                
            }
        }
        
        
        try {
            List<Valoropcion> opciones = fProductos.obtenerDetallesPorProducto(producto.getIdProducto());
            jPanelExtras.removeAll();

            // ====== Verificar qué opciones requiere el producto ======
            boolean requiereVaso = false;
            boolean requiereLeche = false;
            boolean requieresBebidas = false; //extras
            
            
            
            
            

            for (Valoropcion vo : opciones) {
                String nombreOpcion = vo.getIdOpcionProducto().getNombreOpcion().toLowerCase();
                if (nombreOpcion.contains("tipo vaso")) requiereVaso = true;
                if (nombreOpcion.contains("tipo leche")) requiereLeche = true;
                if (nombreOpcion.contains("extra bebidas")) requieresBebidas = true;
            }

            // ====== Subpaneles ======
            JPanel subPanelVaso = null;
            JPanel subPanelLeche = null;
           // JPanel subPanelExtras = null;

            // ====== Panel de tipo vaso ======
            if (requiereVaso) {
                subPanelVaso = new JPanel();
                subPanelVaso.setName("Tipo vaso");
                subPanelVaso.setBackground(Color.WHITE);
                subPanelVaso.setOpaque(false);
                subPanelVaso.setBorder(new LineBorder(Color.GRAY));
                subPanelVaso.setBounds(0, 0, 480, 160); // <- TUS bounds originales
                subPanelVaso.setLayout(null);

                JLabel jlabelTituloVaso = new JLabel();
                jlabelTituloVaso.setBounds(subPanelVaso.getX(), 8, subPanelVaso.getWidth(), 20);
                jlabelTituloVaso.setHorizontalAlignment(SwingConstants.CENTER);
                jlabelTituloVaso.setFont(new java.awt.Font("Segoe UI", 1, 18));
                jlabelTituloVaso.setText("Tipo de vaso");
                subPanelVaso.add(jlabelTituloVaso);

                JPanel jPanelElementosVasos = new JPanel();
                jPanelElementosVasos.setBounds(10, 42, subPanelVaso.getWidth() - 20, subPanelVaso.getHeight() - 52);
                jPanelElementosVasos.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

                List<TamanoVaso> vasos = fVaso.obtenerTodosLosTamanosVasos();
                List<JPanel> listaSubPanelesVasos = new ArrayList<>();

                
                for (int j = 0; j < vasos.size(); j++) {
                    int idVaso = j;

                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                    panel.setBackground(Color.WHITE);
                    panel.setPreferredSize(new Dimension(120, 80));

                    JLabel lbl1 = new JLabel(vasos.get(j).getNombre(), SwingConstants.CENTER);
                    JLabel lbl3 = new JLabel("$" + vasos.get(j).getPrecio(), SwingConstants.CENTER);

                    lbl1.setAlignmentX(Component.CENTER_ALIGNMENT);
                    lbl3.setAlignmentX(Component.CENTER_ALIGNMENT);

                    panel.add(Box.createVerticalGlue());
                    panel.add(lbl1);
                    panel.add(lbl3);
                    panel.add(Box.createVerticalGlue());
                    
                    
                    if(vaso != null && vaso.getNombre().equalsIgnoreCase(vasos.get(j).getNombre())){
                        panel.setBackground(new Color(173, 216, 230));
                        panel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                        vaso = vasos.get(idVaso);
                        calcularTotal();
                    }
                    
                    if(!soloLectura){
                        
                        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        panel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                for (JPanel p : listaSubPanelesVasos) {
                                    p.setBackground(Color.WHITE);
                                    p.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                                }
                                panel.setBackground(new Color(173, 216, 230));
                                panel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                                vaso = vasos.get(idVaso);
                                System.out.println("Vaso: " + vaso.getNombre());
                                calcularTotal();
                                actualizarNota();
                            }
                        });
                }

                    listaSubPanelesVasos.add(panel);
                    jPanelElementosVasos.add(panel);
                }

                
                subPanelVaso.add(jPanelElementosVasos);
                jPanelExtras.add(subPanelVaso);
            }

            // ====== Panel de tipo leche ======
            if (requiereLeche) {
                subPanelLeche = new JPanel();
                subPanelLeche.setName("Tipo leche");
                subPanelLeche.setBackground(Color.WHITE);
                subPanelLeche.setOpaque(false);
                subPanelLeche.setBorder(new LineBorder(Color.GRAY));

                if (subPanelVaso != null) {
                    subPanelLeche.setBounds(0, subPanelVaso.getHeight() + 20, 480, subPanelVaso.getHeight() + 20);
                } else {
                    subPanelLeche.setBounds(0, 0, 480, 180);
                }
                subPanelLeche.setLayout(null);

                JLabel jlabelTituloLeche = new JLabel();
                jlabelTituloLeche.setBounds(subPanelLeche.getX(), 8, subPanelLeche.getWidth(), 20);
                jlabelTituloLeche.setHorizontalAlignment(SwingConstants.CENTER);
                jlabelTituloLeche.setFont(new java.awt.Font("Segoe UI", 1, 18));
                jlabelTituloLeche.setText("Tipo de leche");
                subPanelLeche.add(jlabelTituloLeche);

                JPanel jPanelElementosLeches = new JPanel();
                jPanelElementosLeches.setBounds(10, 42, subPanelLeche.getWidth() - 20, subPanelLeche.getHeight() - 52);
                jPanelElementosLeches.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

                List<Leche> leches = fLeche.obtenerTodasLasLeches();
                List<JPanel> listaSubPanelesLeches = new ArrayList<>();

                for (int j = 0; j < leches.size(); j++) {
                    int idLeche = j;

                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                    panel.setBackground(Color.WHITE);
                    panel.setPreferredSize(new Dimension(120, 40));
                    

                    JLabel lbl1 = new JLabel(leches.get(j).getNombre(), SwingConstants.CENTER);
                    JLabel lbl3 = new JLabel("$" + leches.get(j).getPrecio(), SwingConstants.CENTER);

                    lbl1.setAlignmentX(Component.CENTER_ALIGNMENT);
                    lbl3.setAlignmentX(Component.CENTER_ALIGNMENT);

                    panel.add(Box.createVerticalGlue());
                    panel.add(lbl1);
                    panel.add(lbl3);
                    panel.add(Box.createVerticalGlue());
                    
                    
                    if(leche != null && leche.getNombre().equalsIgnoreCase(leches.get(j).getNombre())){
                        panel.setBackground(new Color(173, 216, 230));
                        panel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                        leche = leches.get(idLeche);
                        calcularTotal();
                    }
                    
                    
                    if(!soloLectura){
                        
                        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        
                        panel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                for (JPanel p : listaSubPanelesLeches) {
                                    p.setBackground(Color.WHITE);
                                    p.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
                                }
                                panel.setBackground(new Color(173, 216, 230));
                                panel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
                                leche = leches.get(idLeche);

                                calcularTotal();
                                actualizarNota();
                            }
                        });
                    }
                    

                    listaSubPanelesLeches.add(panel);
                    jPanelElementosLeches.add(panel);
                }

                subPanelLeche.add(jPanelElementosLeches);
                
                if(soloLectura){
                subPanelLeche.setEnabled(false);
                }
                
                jPanelExtras.add(subPanelLeche);
            }
            
            
            
           // ====== Panel de tipo extras ======
            if (requieresBebidas) {
                JPanel subPanelExtras = new JPanel();
                subPanelExtras.setName("Tipo extra");
                subPanelExtras.setBackground(Color.WHITE);
                subPanelExtras.setOpaque(false);
                subPanelExtras.setBorder(new LineBorder(Color.GRAY));
                subPanelExtras.setLayout(null);
                subPanelExtras.setBounds(500, 0, 350, jPanelExtras.getHeight() - 42);

                // --- Título ---
                JLabel jlabelTituloExtra = new JLabel("Tipo de extra", SwingConstants.CENTER);
                jlabelTituloExtra.setBounds(0, 8, subPanelExtras.getWidth(), 20);
                jlabelTituloExtra.setFont(new Font("Segoe UI", Font.BOLD, 18));
                subPanelExtras.add(jlabelTituloExtra);

                // --- Panel interno con BoxLayout vertical ---
                JPanel contenedorExtras = new JPanel();
                contenedorExtras.setLayout(new BoxLayout(contenedorExtras, BoxLayout.Y_AXIS));
                contenedorExtras.setBackground(Color.WHITE);

                // --- Scroll ---
                JScrollPane scrollExtras = new JScrollPane(contenedorExtras);
                scrollExtras.setBounds(10, 40, subPanelExtras.getWidth() - 20, subPanelExtras.getHeight() - 50);
                scrollExtras.setBorder(null);
                scrollExtras.getVerticalScrollBar().setUnitIncrement(16);
                scrollExtras.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scrollExtras.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

                subPanelExtras.add(scrollExtras);

                List<JPanel> listaSubPanelesExtras = new ArrayList<>();

                for (int j = 0; j < opciones.size(); j++) {//**************************************************************************************** 

                    if (opciones.get(j).getIdOpcionProducto().getNombreOpcion().equalsIgnoreCase("Extra bebidas")) {

                        Valoropcion extra = opciones.get(j);

                        // PANEL CONTENEDOR PRINCIPAL
                        JPanel panel = new JPanel();
                        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                        panel.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                                BorderFactory.createEmptyBorder(8, 8, 8, 8)
                        ));
                        panel.setBackground(Color.WHITE);
                        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

                        // ---------- Fila 1: Nombre + Precio ----------
                        JPanel fila1 = new JPanel(new BorderLayout());
                        fila1.setOpaque(false);

                        JLabel lblNombre = new JLabel(extra.getNombreValor());
                        JLabel lblPrecio = new JLabel("$" + extra.getCosteAdicional());

                        fila1.add(lblNombre, BorderLayout.WEST);
                        fila1.add(lblPrecio, BorderLayout.EAST);

                        panel.add(fila1);
                        panel.add(Box.createVerticalStrut(6));

                        // ---------- Fila 2: Cantidad y Spinner ----------
                        JPanel fila2 = new JPanel(new BorderLayout());
                        fila2.setOpaque(false);

                        JLabel lblCantidad = new JLabel("Cantidad:");

                        JSpinner spiner = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
                        spiner.setPreferredSize(new Dimension(60, 26));
                        spiner.setMaximumSize(new Dimension(60, 26));

                        fila2.add(lblCantidad, BorderLayout.WEST);
                        fila2.add(spiner, BorderLayout.EAST);

                        panel.add(fila2);
                        panel.add(Box.createVerticalStrut(4));

                        // SI EXISTEN EXTRAS PRE-SELECCIONADOS
                        for (int i = 0; i < extrasEncontrados.size(); i++) {
                            if (extra.getNombreValor().equalsIgnoreCase(extrasEncontrados.get(i).getNombreExtra())) {
                                spiner.setValue(extrasEncontrados.get(i).getCantidad());
                                calcularTotal();
                            }
                        }

                        if (!soloLectura) {
                            spiner.addChangeListener(e -> {
                                int cantidad = (int) spiner.getValue();

                                if (cantidad > 0) {
                                    extrasSeleccionados.put(extra, cantidad);
                                } else {
                                    extrasSeleccionados.remove(extra);
                                }

                                calcularTotal();
                                actualizarNota();
                            });
                        } else {
                            spiner.setEnabled(false);
                        }

                        listaSubPanelesExtras.add(panel);
                        contenedorExtras.add(panel);
                        contenedorExtras.add(Box.createVerticalStrut(10));

                        // AJUSTAR ALTURA DEL CONTENEDOR
                        int alturaTotal = (listaSubPanelesExtras.size() * 90) + (listaSubPanelesExtras.size() * 10);

                        contenedorExtras.setPreferredSize(new Dimension(
                                scrollExtras.getWidth() - 20,
                                Math.max(alturaTotal, scrollExtras.getHeight())
                        ));

                        contenedorExtras.revalidate();
                        contenedorExtras.repaint();
                    }else{//***************************************************************************************
                        Valoropcion extra = opciones.get(j);

                        // PANEL CONTENEDOR PRINCIPAL
                        JPanel panel = new JPanel();
                        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                        panel.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                                BorderFactory.createEmptyBorder(8, 8, 8, 8)
                        ));
                        panel.setBackground(Color.WHITE);
                        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

                        // ---------- Fila 1: Nombre + Precio ----------
                        JPanel fila1 = new JPanel(new BorderLayout());
                        fila1.setOpaque(false);

                        JLabel lblNombre = new JLabel(extra.getNombreValor());
                        JLabel lblPrecio = new JLabel("$" + extra.getCosteAdicional());

                        fila1.add(lblNombre, BorderLayout.WEST);
                        fila1.add(lblPrecio, BorderLayout.EAST);

                        panel.add(fila1);
                        panel.add(Box.createVerticalStrut(6));

                        // ---------- Fila 2: Cantidad y Spinner ----------
                        JPanel fila2 = new JPanel(new BorderLayout());
                        fila2.setOpaque(false);

                        JLabel lblCantidad = new JLabel("Cantidad:");

                        JSpinner spiner = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
                        spiner.setPreferredSize(new Dimension(60, 26));
                        spiner.setMaximumSize(new Dimension(60, 26));

                        fila2.add(lblCantidad, BorderLayout.WEST);
                        fila2.add(spiner, BorderLayout.EAST);

                        panel.add(fila2);
                        panel.add(Box.createVerticalStrut(4));

                        // SI EXISTEN EXTRAS PRE-SELECCIONADOS
                        for (int i = 0; i < extrasEncontrados.size(); i++) {
                            if (extra.getNombreValor().equalsIgnoreCase(extrasEncontrados.get(i).getNombreExtra())) {
                                spiner.setValue(extrasEncontrados.get(i).getCantidad());
                                calcularTotal();
                            }
                        }

                        if (!soloLectura) {
                            spiner.addChangeListener(e -> {
                                int cantidad = (int) spiner.getValue();

                                if (cantidad > 0) {
                                    extrasSeleccionados.put(extra, cantidad);
                                } else {
                                    extrasSeleccionados.remove(extra);
                                }

                                calcularTotal();
                                actualizarNota();
                            });
                        } else {
                            spiner.setEnabled(false);
                        }

                        listaSubPanelesExtras.add(panel);
                        contenedorExtras.add(panel);
                        contenedorExtras.add(Box.createVerticalStrut(10));

                        // AJUSTAR ALTURA DEL CONTENEDOR
                        int alturaTotal = (listaSubPanelesExtras.size() * 90) + (listaSubPanelesExtras.size() * 10);

                        contenedorExtras.setPreferredSize(new Dimension(
                                scrollExtras.getWidth() - 20,
                                Math.max(alturaTotal, scrollExtras.getHeight())
                        ));

                        contenedorExtras.revalidate();
                        contenedorExtras.repaint();
                    }
                }

                if (soloLectura) subPanelExtras.setEnabled(false);

                jPanelExtras.add(subPanelExtras);
            }



  
            
            
            

            // ====== Refrescar vista ======
            jPanelExtras.revalidate();
            jPanelExtras.repaint();
            

        } catch (Exception ex) {
            Logger.getLogger(FrmDetallesProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    private void calcularTotal(){
        
        
        float precioVaso = 0;
        float precioLeche = 0;
        float totalExtras = 0;
        
        if(vaso != null ){
            precioVaso = vaso.getPrecio();
            txtPrecioProductoResumen.setText(String.valueOf(producto.getPrecioProducto() + precioVaso));
        }
        
        if(leche != null){
            precioLeche = leche.getPrecio();
            txtLeche.setText(leche.getNombre());
            txtPrecioLeche.setText(String.valueOf(precioLeche));
        }
        
        for (Map.Entry<Valoropcion, Integer> entry : extrasSeleccionados.entrySet()) {
            Valoropcion extra = entry.getKey();
            int cantidad = entry.getValue();
            totalExtras += extra.getCosteAdicional() * cantidad;
        }
        
        
        

        
        txtPrecioExtra.setText(String.valueOf(totalExtras));
        
        txtPrecioProductoResumen.setText(String.valueOf(producto.getPrecioProducto() + precioVaso + totalExtras));
        txtTotal.setText(String.valueOf(precioVaso + precioLeche + producto.getPrecioProducto() + totalExtras));
        txtSubtotal.setText(txtTotal.getText());
        
        
    }
    
    
    
    
    
    
    
    
    
    
    private void guardarDetalleComanda(){
        
        try{
                
            FrmListaProductos.comanda.setEstadoComanda("Abierta");
            FrmListaProductos.comanda.setFechaHoracomanda(new Date());
            FrmListaProductos.comanda.setTotalComanda(Float.valueOf(txtTotal.getText()));    
                    
            FComandas.GuardarComanda(FrmListaProductos.comanda);
                    
            FrmListaProductos.idComanda = FrmListaProductos.comanda.getIdComanda(); 
            
            comanda = FrmListaProductos.comanda;
            
        }
              
        catch(Exception e){
            e.printStackTrace();
        }
        
    
    }
    
    private void editarDetallaComanda(){
        
        
        //Si es la primera vez
        Detallecomanda detalleCo = new Detallecomanda();
        
        detalleCo.setCaintidaddetalleComanda(1);
        detalleCo.setIdComanda(comanda);
        detalleCo.setIdProducto(producto);
        detalleCo.setNotadetalleComanda(txtDescripcion.getText());
        detalleCo.setSubTotaldetalleComanda(Float.valueOf(txtTotal.getText()));
        
        try {
            Detallecomanda detalleCoEditada = new Detallecomanda();
            
            //Editar
            if(detalle != null){
                detalle.setCaintidaddetalleComanda(1);
                detalle.setIdComanda(comanda);
                detalle.setIdProducto(producto);
                detalle.setNotadetalleComanda(txtDescripcion.getText());
                detalle.setSubTotaldetalleComanda(Float.valueOf(txtTotal.getText()));
                System.out.println("detalle" + detalle.toString());
                detalleCoEditada = fDC.editarDetallesComandas(detalle);
            }
            //Guardar
            else{
                detalleCoEditada = fDC.agregarDetallesComandas(detalleCo);
            }
            
            System.out.println("Se guardo el detalle o se edito");
            List<ExtrasProductos> listaExtr = new ArrayList<>();

        // 🧩 1. Agregar los extras seleccionados (si existen)
        if (extrasSeleccionados != null && !extrasSeleccionados.isEmpty()) {
            for (Map.Entry<Valoropcion, Integer> entry : extrasSeleccionados.entrySet()) {
                Valoropcion valor = entry.getKey();
                int cantidad = entry.getValue();

                ExtrasProductos extra = new ExtrasProductos();
                extra.setIdDetalleComanda(detalleCoEditada);
                extra.setIdProducto(producto);
                extra.setNombreExtra(valor.getNombreValor());
                extra.setCantidad(cantidad);

                if (vaso != null) {
                    extra.setIdTamanoVaso(vaso);
                }
                if (leche != null) {
                    extra.setIdLeche(leche);
                }

                listaExtr.add(extra);
            }
        }

        // 🧩 2. Si no hubo extras seleccionados, pero sí vaso o leche, crear un registro base
        if (listaExtr.isEmpty() && (vaso != null || leche != null)) {
            ExtrasProductos extraSimple = new ExtrasProductos();
            extraSimple.setIdDetalleComanda(detalleCoEditada);
            extraSimple.setIdProducto(producto);

            if (vaso != null) {
                extraSimple.setIdTamanoVaso(vaso);
            }
            if (leche != null) {
                extraSimple.setIdLeche(leche);
            }

            listaExtr.add(extraSimple);
        }

        // 🧩 3. Si no hay nada en absoluto, agrega uno "vacío" (si es necesario)
        if (listaExtr.isEmpty()) {
            ExtrasProductos extraVacio = new ExtrasProductos();
            extraVacio.setIdDetalleComanda(detalleCoEditada);
            extraVacio.setIdProducto(producto);
            listaExtr.add(extraVacio);
        }
                  
            if (vaso != null || leche != null || (extrasSeleccionados != null && !extrasSeleccionados.isEmpty())) {
                
                //Editar: Se eliminan y se agregan los nuevos
                if (detalle != null) {
                    List<ExtrasProductos> extrasViejos = fExtras.obtenerTodosLosExtrasPorComandas(detalleCoEditada);
                    for (ExtrasProductos e : extrasViejos) {
                        fExtras.eliminarExtrasProductos(e.getIdExtraProducto());
                    }
                }
                
                
                for (ExtrasProductos e : listaExtr) {
                        fExtras.agregarExtrasProductos(e);
                }  
                
                
                List<ExtrasProductos> nuevaListaExtra = fExtras.obtenerTodosLosExtrasPorComandas(detalleCoEditada);
                
                detalleCoEditada.setExtrasProductosList(nuevaListaExtra);
                
                fDC.editarDetallesComandas(detalleCoEditada);
                
                float total = 0;
                
                List<Detallecomanda> listaTotal = new ArrayList<>();
                
                if(detalle != null){
                  listaTotal = fDC.obtenerDetallesComandasPorComanda(comanda);
                }
                else{
                  listaTotal = fDC.obtenerDetallesComandasPorComanda(detalleCoEditada.getIdComanda());
                }
                
                for (int i = 0; i < listaTotal.size(); i++){
                    total = total + listaTotal.get(i).getSubTotaldetalleComanda();
                }
                
                detalleCoEditada.getIdComanda().setTotalComanda(total);
                FComandas.EditarComanda(detalleCoEditada.getIdComanda());
                
            }

            FrmListaProductos.comanda = detalleCoEditada.getIdComanda();
            pantallaProductos.cargarPanelComanda();
            pantallaProductos.setVisible(true);
            this.dispose();
            
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(FrmDetallesProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    
    }
    
    
    
    private void actualizarNota(){
        
        String nota;
        StringBuilder sb = new StringBuilder();
        
        // Tipo de Vaso
        if (vaso != null) {
            sb.append("Vaso: ").append(vaso.getNombre()).append(". ");
        }
        
        
        // Tipo de leche
        if (leche != null) {
            sb.append("Leche: ").append(leche.getNombre()).append(". ");
        }
        
        
        // Extras
        int extras = 0;
        
        if (!extrasSeleccionados.isEmpty()) {
            for (Map.Entry<Valoropcion, Integer> entry : extrasSeleccionados.entrySet()) {
                Valoropcion extra = entry.getKey();
                int cantidad = entry.getValue();

                if (cantidad > 0) {
                    sb.append(extra.getNombreValor())
                      .append(" x").append(cantidad)
                      .append(". ");

                    extras += cantidad;
                }
            }
        }
        

        sb.append("Cantidad de extras: ").append(extras).append(". ");

        nota = sb.toString();
        txtDescripcion.setText(nota);
        
        
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
        jblSoloLectura = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        txtPrecioBase = new javax.swing.JLabel();
        jPanelExtras = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtProductoResumen = new javax.swing.JLabel();
        txtLeche = new javax.swing.JLabel();
        txtExtra = new javax.swing.JLabel();
        txtPrecioLeche = new javax.swing.JLabel();
        txtPrecioProductoResumen = new javax.swing.JLabel();
        txtPrecioExtra = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        btnGuardarCambios = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Comanda: Detalles del Producto");
        setMinimumSize(new java.awt.Dimension(1230, 620));
        setPreferredSize(new java.awt.Dimension(1230, 720));
        setResizable(false);
        setSize(new java.awt.Dimension(1230, 620));
        getContentPane().setLayout(null);

        jPanelFondo.setBackground(new java.awt.Color(255, 255, 255));
        jPanelFondo.setLayout(null);

        jPanelEncabezado.setBackground(new java.awt.Color(255, 255, 255));
        jPanelEncabezado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanelEncabezado.setLayout(null);

        jblSoloLectura.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jblSoloLectura.setForeground(new java.awt.Color(39, 24, 17));
        jblSoloLectura.setText("Solo Visualizacion");
        jblSoloLectura.setAlignmentX(16.0F);
        jblSoloLectura.setAlignmentY(0.0F);
        jPanelEncabezado.add(jblSoloLectura);
        jblSoloLectura.setBounds(300, 15, 230, 24);

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

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(39, 24, 17));
        jLabel2.setText("Café Express POS");
        jLabel2.setAlignmentX(16.0F);
        jLabel2.setAlignmentY(0.0F);
        jPanelEncabezado.add(jLabel2);
        jLabel2.setBounds(50, 15, 160, 24);

        jPanelFondo.add(jPanelEncabezado);
        jPanelEncabezado.setBounds(0, 0, 1230, 50);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        jPanel4.setPreferredSize(new java.awt.Dimension(500, 200));
        jPanel4.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(17, 24, 39));
        jLabel4.setText("Producto:");
        jLabel4.setAlignmentX(16.0F);
        jLabel4.setAlignmentY(0.0F);
        jPanel4.add(jLabel4);
        jLabel4.setBounds(13, 17, 100, 24);

        txtNombreProducto.setEditable(false);
        txtNombreProducto.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        txtNombreProducto.setForeground(new java.awt.Color(17, 24, 39));
        txtNombreProducto.setText("Capuchino");
        txtNombreProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProductoActionPerformed(evt);
            }
        });
        jPanel4.add(txtNombreProducto);
        txtNombreProducto.setBounds(110, 15, 350, 26);

        txtDescripcion.setColumns(20);
        txtDescripcion.setForeground(new java.awt.Color(75, 85, 99));
        txtDescripcion.setRows(2);
        txtDescripcion.setBorder(null);
        jScrollPane1.setViewportView(txtDescripcion);

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(13, 48, 850, 40);

        txtPrecioBase.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtPrecioBase.setForeground(new java.awt.Color(17, 24, 39));
        txtPrecioBase.setText("Precio");
        jPanel4.add(txtPrecioBase);
        txtPrecioBase.setBounds(20, 90, 210, 30);

        javax.swing.GroupLayout jPanelExtrasLayout = new javax.swing.GroupLayout(jPanelExtras);
        jPanelExtras.setLayout(jPanelExtrasLayout);
        jPanelExtrasLayout.setHorizontalGroup(
            jPanelExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );
        jPanelExtrasLayout.setVerticalGroup(
            jPanelExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        jPanel4.add(jPanelExtras);
        jPanelExtras.setBounds(10, 130, 850, 400);

        jPanelFondo.add(jPanel4);
        jPanel4.setBounds(20, 70, 880, 540);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        jPanel12.setLayout(null);

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(17, 24, 39));
        jLabel9.setText("Resumen");
        jLabel9.setAlignmentX(16.0F);
        jLabel9.setAlignmentY(0.0F);
        jPanel12.add(jLabel9);
        jLabel9.setBounds(18, 17, 84, 24);

        txtProductoResumen.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txtProductoResumen.setText("Producto");
        jPanel12.add(txtProductoResumen);
        txtProductoResumen.setBounds(30, 60, 140, 19);

        txtLeche.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txtLeche.setText("Leche");
        jPanel12.add(txtLeche);
        txtLeche.setBounds(30, 100, 140, 19);

        txtExtra.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txtExtra.setText("Extra");
        jPanel12.add(txtExtra);
        txtExtra.setBounds(30, 140, 140, 19);

        txtPrecioLeche.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtPrecioLeche.setText("0");
        jPanel12.add(txtPrecioLeche);
        txtPrecioLeche.setBounds(200, 100, 50, 19);

        txtPrecioProductoResumen.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtPrecioProductoResumen.setText("0");
        jPanel12.add(txtPrecioProductoResumen);
        txtPrecioProductoResumen.setBounds(200, 60, 50, 19);

        txtPrecioExtra.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtPrecioExtra.setText("0");
        jPanel12.add(txtPrecioExtra);
        txtPrecioExtra.setBounds(200, 140, 60, 19);

        jLabel10.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(17, 24, 39));
        jLabel10.setText("Subtotal");
        jLabel10.setAlignmentX(16.0F);
        jLabel10.setAlignmentY(0.0F);
        jPanel12.add(jLabel10);
        jLabel10.setBounds(30, 220, 58, 19);

        txtSubtotal.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtSubtotal.setForeground(new java.awt.Color(17, 24, 39));
        txtSubtotal.setText("$00.00");
        txtSubtotal.setAlignmentX(16.0F);
        txtSubtotal.setAlignmentY(0.0F);
        jPanel12.add(txtSubtotal);
        txtSubtotal.setBounds(200, 220, 44, 19);

        jLabel11.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(17, 24, 39));
        jLabel11.setText("Total");
        jLabel11.setAlignmentX(16.0F);
        jLabel11.setAlignmentY(0.0F);
        jPanel12.add(jLabel11);
        jLabel11.setBounds(30, 270, 34, 19);

        txtTotal.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(17, 24, 39));
        txtTotal.setText("$00.00");
        txtTotal.setAlignmentX(16.0F);
        txtTotal.setAlignmentY(0.0F);
        jPanel12.add(txtTotal);
        txtTotal.setBounds(200, 270, 44, 19);

        btnGuardarCambios.setBackground(new java.awt.Color(31, 41, 55));
        btnGuardarCambios.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        btnGuardarCambios.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarCambios.setText("Guardar Cambios");
        btnGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCambiosActionPerformed(evt);
            }
        });
        jPanel12.add(btnGuardarCambios);
        btnGuardarCambios.setBounds(30, 320, 211, 28);

        btnCancelar.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(31, 41, 55));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel12.add(btnCancelar);
        btnCancelar.setBounds(30, 360, 211, 23);

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 3));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 205, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel12.add(jPanel1);
        jPanel1.setBounds(30, 250, 211, 3);

        jPanelFondo.add(jPanel12);
        jPanel12.setBounds(930, 200, 280, 410);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        jPanel13.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(17, 24, 39));
        jLabel8.setText("Promociones");
        jLabel8.setAlignmentX(16.0F);
        jLabel8.setAlignmentY(0.0F);
        jPanel13.add(jLabel8);
        jLabel8.setBounds(90, 10, 120, 24);

        lblDescripcion.setText("Sin promociones.");
        jPanel13.add(lblDescripcion);
        lblDescripcion.setBounds(20, 40, 250, 60);

        jPanelFondo.add(jPanel13);
        jPanel13.setBounds(930, 70, 280, 110);

        getContentPane().add(jPanelFondo);
        jPanelFondo.setBounds(0, 0, 1230, 720);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProductoActionPerformed

    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambiosActionPerformed
  
        //Nueva comanda
        if(comanda == null){
            
            System.out.println("Se guarda una nueva comanda");
            guardarDetalleComanda();
            
        }
        
        editarDetallaComanda();
        
        
        
        
  
    }//GEN-LAST:event_btnGuardarCambiosActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        pantallaProductos.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardarCambios;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelEncabezado;
    private javax.swing.JPanel jPanelExtras;
    private javax.swing.JPanel jPanelFondo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jblSoloLectura;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JLabel txtExtra;
    private javax.swing.JLabel txtLeche;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JLabel txtPrecioBase;
    private javax.swing.JLabel txtPrecioExtra;
    private javax.swing.JLabel txtPrecioLeche;
    private javax.swing.JLabel txtPrecioProductoResumen;
    private javax.swing.JLabel txtProductoResumen;
    private javax.swing.JLabel txtSubtotal;
    private javax.swing.JLabel txtTotal;
    // End of variables declaration//GEN-END:variables
}
