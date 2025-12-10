package Formularios;

import Entidades.Categoria;
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
import Implementaciones.GestionarCategoriaControlador;
import Implementaciones.GestionarComandaControlador;
import Implementaciones.GestionarDetallesComandaControlador;
import Implementaciones.GestionarExtrasControlador;
import Implementaciones.GestionarLecheControlador;
import Implementaciones.GestionarProductoControlador;
import Implementaciones.GestionarProductoModel;
import Implementaciones.GestionarTamanoVasosControlador;
import JPA.ComandaJpaController;
import JPA.DetallecomandaJpaController;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

public class DetallesProducto extends javax.swing.JFrame {

    private final IFachadaComandasControlador FComandas = new GestionarComandaControlador();
    private final IFachadaLecheControlador fLeche = new GestionarLecheControlador();
    private final IFachadaExtrasControlador fExtras = new GestionarExtrasControlador();
    private final IFachadaCategoriaControlador fCategoria = new GestionarCategoriaControlador();
    private final IFachadaTamanoVasosControlador fVasos = new GestionarTamanoVasosControlador();
    private final IFachadaDetallesComandaControlador fDC = new GestionarDetallesComandaControlador();
    private final IFachadaProductoControlador fProductos = new GestionarProductoControlador();

    private javax.swing.JRadioButton radioConLeche;
    private javax.swing.JRadioButton radioSinLeche;

    Producto producto;
    Comanda comandaEditar;
    Detallecomanda detalle;
    FrmListaProductos listaProductos;
    public static List<Detallecomanda> listaDetalleComandas = new ArrayList<>();

    float precioTamano;
    float precioExtra;
    float precioLeche;
    float subTotal;
    float total;
    int cantidad;
    String nota;
    String tipoLeche;

    /**
     * Creates new form DetallesProducto
     */
    public DetallesProducto(FrmListaProductos listaProducto, Producto producto, Comanda comandaEditar, Detallecomanda detalle) {
        initComponents();

        ButtonGroup grupoTamaño = new ButtonGroup();
        grupoTamaño.add(jrbTamañoSi);
        grupoTamaño.add(jrbTamañoNo);

        ButtonGroup grupoLeche = new ButtonGroup();
        grupoLeche.add(jrbLecheSi);
        grupoLeche.add(jrbLecheNo);

        ButtonGroup grupoExtras = new ButtonGroup();
        grupoExtras.add(jrbExtrasSi);
        grupoExtras.add(jrbExtrasNo);
        this.producto = producto;
        this.listaProductos = listaProducto;
        this.comandaEditar = comandaEditar;
        this.detalle = detalle;

        this.cargarDatos();

        if (detalle != null) {
            cargarDatosEditar();
        }
        this.agregarValoresTxtSpinners();
        mostrarOpcionesSegunCategoria(producto);
    }

    private void cargarDatos() {
        precioTamano = 0;
        txtNombreProducto.setText(producto.getNombreProducto());
        txtPrecioBase.setText(producto.getPrecioProducto().toString());

        txtPrecioLeche.setText(String.valueOf(0));
        txtPrecioExtra.setText(String.valueOf(0));
        txtPrecioProductoResumen.setText(producto.getPrecioProducto().toString());

        if (txtProductoResumen.getText().isEmpty()) {
            txtProductoResumen.setText(producto.getNombreProducto());
        }

       

        recalcularTotal();
//
//        total = ((producto.getPrecioProducto() + precioTamano) * cantidad) + precioLeche + precioExtra;z
//
//        txtSubtotal.setText(String.valueOf(total));
//        txtTotal.setText(String.valueOf(total));
    }

    public void cargarDatosEditar() {
        if (detalle != null) {
            System.out.println("--- MODO EDICIÓN DETECTADO ---");

            // Cargar cantidad y nota
            
            txtDescripcion.setText(detalle.getNotadetalleComanda());

            // Resetear spinners a 0 por si no hay extras
            spinnerBoba.setValue(0);
            spinnerShotExpreso.setValue(0);
            spinnerLecheAlmendras.setValue(0);
            spinnerLecheDeCoco.setValue(0);

            // Resetear radios
            jrbExtrasSi.setSelected(false);
            jrbExtrasNo.setSelected(true);

            jrbLecheSi.setSelected(false);
            jrbLecheNo.setSelected(true);

            jrbTamañoSi.setSelected(false);
            jrbTamañoNo.setSelected(true);

            // Iterar sobre los extras guardados
            List<ExtrasProductos> extras = null;
            if (extras != null && !extras.isEmpty()) {
                for (ExtrasProductos extra : extras) {

                    // Cargar cantidades según el nombre del extra
                    if (extra.getNombreExtra() != null) {
                        switch (extra.getNombreExtra().toLowerCase()) {
                            case "boba":
                                spinnerBoba.setValue(extra.getCantidad());
                                break;
                            case "shot expreso":
                                spinnerShotExpreso.setValue(extra.getCantidad());
                                break;
                            case "leche de almendras":
                                spinnerLecheAlmendras.setValue(extra.getCantidad());
                                jrbLecheSi.setSelected(true);
                                jrbLecheNo.setSelected(false);
                                break;
                            case "leche de coco":
                                spinnerLecheDeCoco.setValue(extra.getCantidad());
                                jrbLecheSi.setSelected(true);
                                jrbLecheNo.setSelected(false);
                                break;
                            case "mediano":
                                radioMediano2.setSelected(true);
                                jrbTamañoSi.setSelected(true);
                                jrbTamañoNo.setSelected(false);

                                precioTamano = 10;
                                recalcularTotal();
                                break;
                            default:

                                jrbExtrasSi.setSelected(true);
                                jrbExtrasNo.setSelected(false);
                                break;
                        }
                    }
                }
            }
        }
    }

    private void mostrarOpcionesSegunCategoria(Producto producto) {
        List<Valoropcion> vopcion = new ArrayList<>();
        if (producto == null || producto.getIdCategoria() == null) {
            System.out.println("No se pudo determinar la categoría del producto.");
            return;
        }

        //AQUI VA A PASAR LA MAGIA
        //VAMOS A INVOCAR EL METODO ObtenerDetallesPorProducto, y le vamos a pasar el id del producto
        //del parametro
        // Ocultamos todos los paneles por defecto
        panelTamano.setVisible(false);
        panelLeche.setVisible(false);
        panelExtras.setVisible(false);

        try {
            //Lista que tienen los detalles por producto
            vopcion = fProductos.obtenerDetallesPorProducto(producto.getIdProducto());
        } catch (Exception ex) {
            Logger.getLogger(DetallesProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Buscamos los detalles del producto
        for (Valoropcion opciones : vopcion) {
            System.out.println("Detalle encontrado: " + opciones.getNombreValor());
            int idOpcion = opciones.getIdOpcionProducto().getIdOpcionProducto();
            // Mostramos los paneles correspondientes según la categoría
            switch (idOpcion) {// UN PRODUCTO PUEDE TENER MAS DE UNA OPCION
                //TODAS LAS BEBIDAS LOS CONTIENEN******************
                case 1 -> { //Café tiene los tres
                    panelTamano.setVisible(true);
                }
                case 2 -> {
                    panelLeche.setVisible(true);
                }
                case 3 -> {
                    panelExtras.setVisible(true);
                }
                //TODAS LAS BEBIDAS LOS CONTIENEN******************
                //DE AQUI PARA ABAJO YA SE VUELVE INDIVIDUAL EXCEPTO EL CASE 5 QUE CORRESPONDE
                //A LOS PRODUCTOS QUE SE VENDEN POR UNIDAD
                case 4 -> { //Crepa de jamon extras de sus mismos ingredientes
                    panelExtras.setVisible(true);
                    //Modificar contenido del panel en base al producto -- Faltan mejoras
                    this.txtExtraShot.setText("Jamon");
                    //Modificar valor spinner
                    this.spinnerLecheDeCoco.setToolTipText("Jamon");
                }
                case 5 -> {
                    System.out.println("Producto unitario - sin detalles");
                }
                default -> {
                }
            }
            // Una coca - paneles ya ocultos por defecto
        }

        Fondo.revalidate();
        Fondo.repaint();

        //System.out.println("Categoría detectada: " + idCategoria);
    }

    private void modificarPanelDetallePorProducto() {
    }

    ;//Si no se usa directo en el switch
    private void agregarValoresTxtSpinners() {
        this.spinnerBoba.setToolTipText("Boba");
        this.spinnerLecheAlmendras.setToolTipText("Leche almendras");
        this.spinnerLecheDeCoco.setToolTipText("Leche coco");
        this.spinnerShotExpreso.setToolTipText("Shot expreso");
    }

    private void actualizarResumen() {
        // Nombre del producto
        if (producto != null) {
            txtProductoResumen.setText(producto.getNombreProducto());
        }

//        // Tipo de leche
        System.out.println("NoeditarComanda");
        if (tipoLeche != null && !tipoLeche.isEmpty()) {
            txtLeche.setText(tipoLeche);
        } else {
            txtLeche.setText("Ninguna");
        }

        // Extras AQUI VAN LOS TXT
        StringBuilder extras = new StringBuilder();
        if ((int) spinnerLecheDeCoco.getValue() > 0) {
            extras.append(this.spinnerLecheDeCoco.getToolTipText()).append(" x").append(spinnerLecheDeCoco.getValue()).append("  ");
        }
        if ((int) spinnerLecheAlmendras.getValue() > 0) {
            extras.append(this.spinnerLecheAlmendras.getToolTipText()).append(" x").append(spinnerLecheAlmendras.getValue()).append("  ");
        }
        if ((int) spinnerShotExpreso.getValue() > 0) {
            extras.append(this.spinnerShotExpreso.getToolTipText()).append(" x").append(spinnerShotExpreso.getValue()).append("  ");
        }
        if ((int) spinnerBoba.getValue() > 0) {
            extras.append(this.spinnerBoba.getToolTipText()).append(" x").append(spinnerBoba.getValue()).append("  ");
        }

        if (extras.length() == 0) {
            txtExtra.setText("Ninguno");
        } else {
            txtExtra.setText(extras.toString());
        }

        // --- CAMBIOS ---
        // Actualiza precios multiplicados por la cantidad
        txtPrecioLeche.setText(String.valueOf(precioLeche * cantidad));
        txtPrecioExtra.setText(String.valueOf(precioExtra * cantidad));
        txtPrecioProductoResumen.setText(String.valueOf(producto.getPrecioProducto() * cantidad));

        // Subtotal y total
        txtSubtotal.setText(String.valueOf(total));
        txtTotal.setText(String.valueOf(total));
    }

//    private void cargarDatosDetalles(){
//   if(this.producto.getIdProducto()<=5 && this.producto.getIdProducto()>=1){
//       //TAMAÑO
//       this.radioMediano2.setEnabled(true);
//       this.radioMediano3.setEnabled(true);
//       //TIPO LECHE
//       this.toggleAlmendras.setEnabled(true);
//       this.toggleAvena.setEnabled(true);
//       this.toggleDeslactosada.setEnabled(true);
//       this.toggleEntera.setEnabled(true);
//       //EXTRAS
//       this.spinnerBoba.setEnabled(true);
//       this.spinnerLecheAlmendras.setEnabled(true);
//       this.spinnerLecheDeCoco.setEnabled(true);
//       this.spinnerBoba.setEnabled(true);
//   }else{
//        //TAMAÑO
//       this.radioMediano2.setEnabled(false);
//       this.radioMediano3.setEnabled(false);
//       //TIPO LECHE
//       this.toggleAlmendras.setEnabled(false);
//       this.toggleAvena.setEnabled(false);
//       this.toggleDeslactosada.setEnabled(false);
//       this.toggleEntera.setEnabled(false);
//       //EXTRAS
//       this.spinnerBoba.setEnabled(false);
//       this.spinnerLecheAlmendras.setEnabled(false);
//       this.spinnerLecheDeCoco.setEnabled(false);
//       this.spinnerBoba.setEnabled(false);
//   }
//}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        btnTipoLeche = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        Fondo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel = new javax.swing.JLabel();
        txtNumMesa = new javax.swing.JLabel();
        txtNombreCajero = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNumComanda = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        txtPrecioBase = new javax.swing.JLabel();
        panelTamano = new javax.swing.JPanel();
        radioMediano2 = new javax.swing.JRadioButton();
        radioMediano3 = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jrbTamañoNo = new javax.swing.JRadioButton();
        jrbTamañoSi = new javax.swing.JRadioButton();
        panelLeche = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        toggleEntera = new javax.swing.JToggleButton();
        toggleAlmendras = new javax.swing.JToggleButton();
        toggleDeslactosada = new javax.swing.JToggleButton();
        toggleAvena = new javax.swing.JToggleButton();
        jrbLecheSi = new javax.swing.JRadioButton();
        jrbLecheNo = new javax.swing.JRadioButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtProductoResumen = new javax.swing.JLabel();
        txtLeche = new javax.swing.JLabel();
        txtExtra = new javax.swing.JLabel();
        txtPrecioProductoResumen = new javax.swing.JLabel();
        txtPrecioLeche = new javax.swing.JLabel();
        txtPrecioExtra = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JLabel();
        btnGuardarCambios = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        panelExtras = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtExtraShot = new javax.swing.JLabel();
        spinnerLecheDeCoco = new javax.swing.JSpinner();
        jPanel9 = new javax.swing.JPanel();
        txtCremaExtra = new javax.swing.JLabel();
        spinnerLecheAlmendras = new javax.swing.JSpinner();
        jPanel10 = new javax.swing.JPanel();
        txtVainillaExtra = new javax.swing.JLabel();
        spinnerShotExpreso = new javax.swing.JSpinner();
        jPanel11 = new javax.swing.JPanel();
        txtCarameloExtra = new javax.swing.JLabel();
        spinnerBoba = new javax.swing.JSpinner();
        jrbExtrasNo = new javax.swing.JRadioButton();
        jrbExtrasSi = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(0, 0));
        setMaximumSize(new java.awt.Dimension(1440, 1024));
        setResizable(false);

        Fondo.setBackground(new java.awt.Color(242, 243, 245));
        Fondo.setMaximumSize(new java.awt.Dimension(1440, 1024));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1440, 57));

        jLabel.setText("Mesa:");

        txtNumMesa.setText("Numero");

        txtNombreCajero.setText("Comanda:");

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

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(39, 24, 17));
        jLabel1.setText("Modificar Producto");
        jLabel1.setAlignmentX(16.0F);
        jLabel1.setAlignmentY(0.0F);

        txtNumComanda.setText("Numero");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNumMesa)
                .addGap(66, 66, 66)
                .addComponent(txtNombreCajero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNumComanda)
                .addGap(254, 254, 254))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel)
                            .addComponent(txtNombreCajero)
                            .addComponent(txtNumMesa)
                            .addComponent(jLabel1)
                            .addComponent(txtNumComanda))
                        .addGap(5, 5, 5)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        jPanel4.setPreferredSize(new java.awt.Dimension(500, 200));

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(17, 24, 39));
        jLabel4.setText("Producto:");
        jLabel4.setAlignmentX(16.0F);
        jLabel4.setAlignmentY(0.0F);

        txtNombreProducto.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        txtNombreProducto.setForeground(new java.awt.Color(17, 24, 39));
        txtNombreProducto.setText("Capuchino");
        txtNombreProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNombreProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreProductoActionPerformed(evt);
            }
        });

        txtDescripcion.setColumns(20);
        txtDescripcion.setForeground(new java.awt.Color(75, 85, 99));
        txtDescripcion.setRows(2);
        txtDescripcion.setBorder(null);
        jScrollPane1.setViewportView(txtDescripcion);

        txtPrecioBase.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtPrecioBase.setForeground(new java.awt.Color(17, 24, 39));
        txtPrecioBase.setText("Precio");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecioBase)
                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNombreProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPrecioBase)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelTamano.setBackground(new java.awt.Color(255, 255, 255));
        panelTamano.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        panelTamano.setPreferredSize(new java.awt.Dimension(500, 200));

        buttonGroup1.add(radioMediano2);
        radioMediano2.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        radioMediano2.setForeground(new java.awt.Color(17, 24, 39));
        radioMediano2.setText("Mediano (16 oz)");
        radioMediano2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMediano2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioMediano3);
        radioMediano3.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        radioMediano3.setForeground(new java.awt.Color(17, 24, 39));
        radioMediano3.setText("Grande (24 oz)");
        radioMediano3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioMediano3ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(17, 24, 39));
        jLabel5.setText("Tamaño");
        jLabel5.setAlignmentX(16.0F);
        jLabel5.setAlignmentY(0.0F);

        jrbTamañoNo.setText("No");
        jrbTamañoNo.setEnabled(false);
        jrbTamañoNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbTamañoNoActionPerformed(evt);
            }
        });

        jrbTamañoSi.setText("Si");
        jrbTamañoSi.setEnabled(false);
        jrbTamañoSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbTamañoSiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelTamanoLayout = new javax.swing.GroupLayout(panelTamano);
        panelTamano.setLayout(panelTamanoLayout);
        panelTamanoLayout.setHorizontalGroup(
            panelTamanoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTamanoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panelTamanoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTamanoLayout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(radioMediano2))
                    .addComponent(jLabel5))
                .addGap(36, 36, 36)
                .addGroup(panelTamanoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTamanoLayout.createSequentialGroup()
                        .addComponent(radioMediano3)
                        .addContainerGap(88, Short.MAX_VALUE))
                    .addGroup(panelTamanoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jrbTamañoNo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jrbTamañoSi)
                        .addGap(51, 51, 51))))
        );
        panelTamanoLayout.setVerticalGroup(
            panelTamanoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTamanoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelTamanoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jrbTamañoNo)
                    .addComponent(jrbTamañoSi))
                .addGap(18, 18, 18)
                .addGroup(panelTamanoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioMediano2)
                    .addComponent(radioMediano3))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        panelLeche.setBackground(new java.awt.Color(255, 255, 255));
        panelLeche.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));

        jLabel6.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(17, 24, 39));
        jLabel6.setText("Tipo de Leche");
        jLabel6.setAlignmentX(16.0F);
        jLabel6.setAlignmentY(0.0F);

        toggleEntera.setBackground(new java.awt.Color(242, 243, 245));
        btnTipoLeche.add(toggleEntera);
        toggleEntera.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        toggleEntera.setForeground(new java.awt.Color(17, 24, 39));
        toggleEntera.setText("Leche Entera");
        toggleEntera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleEnteraActionPerformed(evt);
            }
        });

        toggleAlmendras.setBackground(new java.awt.Color(242, 243, 245));
        btnTipoLeche.add(toggleAlmendras);
        toggleAlmendras.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        toggleAlmendras.setForeground(new java.awt.Color(17, 24, 39));
        toggleAlmendras.setText("Leche Almendras");
        toggleAlmendras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleAlmendrasActionPerformed(evt);
            }
        });

        toggleDeslactosada.setBackground(new java.awt.Color(242, 243, 245));
        btnTipoLeche.add(toggleDeslactosada);
        toggleDeslactosada.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        toggleDeslactosada.setForeground(new java.awt.Color(17, 24, 39));
        toggleDeslactosada.setText("Deslactosada");
        toggleDeslactosada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleDeslactosadaActionPerformed(evt);
            }
        });

        toggleAvena.setBackground(new java.awt.Color(242, 243, 245));
        btnTipoLeche.add(toggleAvena);
        toggleAvena.setFont(new java.awt.Font("Helvetica Neue", 1, 13)); // NOI18N
        toggleAvena.setForeground(new java.awt.Color(17, 24, 39));
        toggleAvena.setText("Leche de Avena");
        toggleAvena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleAvenaActionPerformed(evt);
            }
        });

        jrbLecheSi.setText("Si");
        jrbLecheSi.setEnabled(false);
        jrbLecheSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbLecheSiActionPerformed(evt);
            }
        });

        jrbLecheNo.setText("No");
        jrbLecheNo.setEnabled(false);
        jrbLecheNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbLecheNoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLecheLayout = new javax.swing.GroupLayout(panelLeche);
        panelLeche.setLayout(panelLecheLayout);
        panelLecheLayout.setHorizontalGroup(
            panelLecheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLecheLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jrbLecheNo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jrbLecheSi)
                .addGap(51, 51, 51))
            .addGroup(panelLecheLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(panelLecheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLecheLayout.createSequentialGroup()
                        .addComponent(toggleEntera, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(toggleDeslactosada, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLecheLayout.createSequentialGroup()
                        .addComponent(toggleAlmendras, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(toggleAvena, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        panelLecheLayout.setVerticalGroup(
            panelLecheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLecheLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelLecheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(panelLecheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jrbLecheSi)
                        .addComponent(jrbLecheNo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLecheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toggleEntera, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toggleDeslactosada, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLecheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toggleAlmendras, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(toggleAvena, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(455, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(17, 24, 39));
        jLabel9.setText("Resumen");
        jLabel9.setAlignmentX(16.0F);
        jLabel9.setAlignmentY(0.0F);

        txtProductoResumen.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txtProductoResumen.setText("Producto");

        txtLeche.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txtLeche.setText("Leche");

        txtExtra.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        txtExtra.setText("Extra");

        txtPrecioProductoResumen.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtPrecioProductoResumen.setText("Precio");

        txtPrecioLeche.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtPrecioLeche.setText("Precio");

        txtPrecioExtra.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtPrecioExtra.setText("Precio");

        jLabel2.setText("_______________________________");

        jLabel10.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(17, 24, 39));
        jLabel10.setText("Subtotal");
        jLabel10.setAlignmentX(16.0F);
        jLabel10.setAlignmentY(0.0F);

        txtSubtotal.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtSubtotal.setForeground(new java.awt.Color(17, 24, 39));
        txtSubtotal.setText("$00.00");
        txtSubtotal.setAlignmentX(16.0F);
        txtSubtotal.setAlignmentY(0.0F);

        jLabel3.setText("_______________________________");

        jLabel11.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(17, 24, 39));
        jLabel11.setText("Total");
        jLabel11.setAlignmentX(16.0F);
        jLabel11.setAlignmentY(0.0F);

        txtTotal.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(17, 24, 39));
        txtTotal.setText("$00.00");
        txtTotal.setAlignmentX(16.0F);
        txtTotal.setAlignmentY(0.0F);

        btnGuardarCambios.setBackground(new java.awt.Color(31, 41, 55));
        btnGuardarCambios.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        btnGuardarCambios.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarCambios.setText("Guardar Cambios");
        btnGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCambiosActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(31, 41, 55));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGuardarCambios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTotal))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSubtotal))
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel12Layout.createSequentialGroup()
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtProductoResumen)
                                    .addComponent(txtLeche)
                                    .addComponent(txtExtra))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPrecioExtra)
                                    .addComponent(txtPrecioLeche)
                                    .addComponent(txtPrecioProductoResumen)))
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(50, 50, 50))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtProductoResumen)
                    .addComponent(txtPrecioProductoResumen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLeche)
                    .addComponent(txtPrecioLeche))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExtra)
                    .addComponent(txtPrecioExtra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtSubtotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardarCambios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(17, 24, 39));
        jLabel8.setText("Promociones");
        jLabel8.setAlignmentX(16.0F);
        jLabel8.setAlignmentY(0.0F);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 112, Short.MAX_VALUE))
        );

        panelExtras.setBackground(new java.awt.Color(255, 255, 255));
        panelExtras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        panelExtras.setMaximumSize(new java.awt.Dimension(472, 377));

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(17, 24, 39));
        jLabel7.setText("Extras");
        jLabel7.setAlignmentX(16.0F);
        jLabel7.setAlignmentY(0.0F);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));

        txtExtraShot.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtExtraShot.setForeground(new java.awt.Color(17, 24, 39));
        txtExtraShot.setText("Leche de coco");

        spinnerLecheDeCoco.setModel(new javax.swing.SpinnerNumberModel(0, 0, 5, 1));
        spinnerLecheDeCoco.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerShotExtraStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtExtraShot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spinnerLecheDeCoco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExtraShot)
                    .addComponent(spinnerLecheDeCoco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));

        txtCremaExtra.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtCremaExtra.setForeground(new java.awt.Color(17, 24, 39));
        txtCremaExtra.setText("Leche de almendras");

        spinnerLecheAlmendras.setModel(new javax.swing.SpinnerNumberModel(0, 0, 5, 1));
        spinnerLecheAlmendras.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerCremaExtraStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCremaExtra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addComponent(spinnerLecheAlmendras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCremaExtra)
                    .addComponent(spinnerLecheAlmendras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));

        txtVainillaExtra.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtVainillaExtra.setForeground(new java.awt.Color(17, 24, 39));
        txtVainillaExtra.setText("Shot expreso");

        spinnerShotExpreso.setModel(new javax.swing.SpinnerNumberModel(0, 0, 5, 1));
        spinnerShotExpreso.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerVainillaStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtVainillaExtra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spinnerShotExpreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtVainillaExtra)
                    .addComponent(spinnerShotExpreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        jPanel11.setMaximumSize(new java.awt.Dimension(137, 59));

        txtCarameloExtra.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtCarameloExtra.setForeground(new java.awt.Color(17, 24, 39));
        txtCarameloExtra.setText("Boba");

        spinnerBoba.setModel(new javax.swing.SpinnerNumberModel(0, 0, 5, 1));
        spinnerBoba.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerCarameloStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCarameloExtra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(spinnerBoba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCarameloExtra)
                    .addComponent(spinnerBoba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jrbExtrasNo.setText("No");
        jrbExtrasNo.setEnabled(false);
        jrbExtrasNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbExtrasNoActionPerformed(evt);
            }
        });

        jrbExtrasSi.setText("Si");
        jrbExtrasSi.setEnabled(false);
        jrbExtrasSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbExtrasSiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelExtrasLayout = new javax.swing.GroupLayout(panelExtras);
        panelExtras.setLayout(panelExtrasLayout);
        panelExtrasLayout.setHorizontalGroup(
            panelExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelExtrasLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(panelExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelExtrasLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jrbExtrasNo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jrbExtrasSi))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelExtrasLayout.setVerticalGroup(
            panelExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelExtrasLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelExtrasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jrbExtrasNo)
                    .addComponent(jrbExtrasSi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1619, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(panelExtras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, FondoLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panelTamano, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panelLeche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(285, 285, 285))
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(FondoLayout.createSequentialGroup()
                                .addComponent(panelTamano, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(panelLeche, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(FondoLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(panelExtras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 503, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(Fondo);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 78, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1024, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProductoActionPerformed

    private void radioConLecheActionPerformed(java.awt.event.ActionEvent evt) {
        toggleEntera.setEnabled(true);
        toggleDeslactosada.setEnabled(true);
        toggleAvena.setEnabled(true);
        toggleAlmendras.setEnabled(true);
    }

    private void radioSinLecheActionPerformed(java.awt.event.ActionEvent evt) {
        toggleEntera.setEnabled(false);
        toggleDeslactosada.setEnabled(false);
        toggleAvena.setEnabled(false);
        toggleAlmendras.setEnabled(false);
        tipoLeche = "";
        txtLeche.setText("");
        actualizarNota();

    }

    private void recalcularTotal() {
        
        total = ((producto.getPrecioProducto() + precioTamano) * cantidad) + (precioLeche * cantidad) + (precioExtra * cantidad);
        txtSubtotal.setText(String.valueOf(total));
        txtTotal.setText(String.valueOf(total));
        actualizarNota();
        actualizarResumen();
    }

    private void calcularExtrasYTotal() {
        // Calcula el precio de los extras según la cantidad de cada spinner
        precioExtra = ((int) spinnerLecheDeCoco.getValue() * 5)
                + ((int) spinnerLecheAlmendras.getValue() * 5)
                + ((int) spinnerShotExpreso.getValue() * 10)
                + ((int) spinnerBoba.getValue() * 7);

        // Actualiza el label de precio de extras
        txtPrecioExtra.setText(String.valueOf(precioExtra));

        // --- CORRECCIÓN ---
        recalcularTotal();

        // Actualiza los labels
//        txtSubtotal.setText(String.valueOf(total));
//        txtTotal.setText(String.valueOf(total));
        // Actualiza el resumen y la nota descriptiva
//        actualizarNota();
//        actualizarResumen();
    }

    private void actualizarNota() {
        StringBuilder sb = new StringBuilder();

        // Tamaño del vaso
        if (precioTamano == 10) {
            sb.append("Tamaño: Mediano. ");
        } else if (precioTamano == 20) {
            sb.append("Tamaño: Grande. ");
        }

        // Tipo de leche
        if (tipoLeche != null && !tipoLeche.isEmpty()) {
            sb.append("Leche: ").append(tipoLeche).append(". ");
        }

        // Extras ACTUALIZAR ESTO COMO ARRIBA
        int extras = 0;
        if ((int) spinnerLecheDeCoco.getValue() > 0) {
            sb.append(spinnerLecheDeCoco.getToolTipText()).append(" x").append(spinnerLecheDeCoco.getValue()).append(". ");
            extras += (int) spinnerLecheDeCoco.getValue();
        }
        if ((int) spinnerLecheAlmendras.getValue() > 0) {
            sb.append(spinnerLecheAlmendras.getToolTipText()).append(" x").append(spinnerLecheAlmendras.getValue()).append(". ");
            extras += (int) spinnerLecheAlmendras.getValue();
        }
        if ((int) spinnerShotExpreso.getValue() > 0) {
            sb.append(spinnerShotExpreso.getToolTipText()).append(" x").append(spinnerShotExpreso.getValue()).append(". ");
            extras += (int) spinnerShotExpreso.getValue();
        }
        if ((int) spinnerBoba.getValue() > 0) {
            sb.append(spinnerBoba.getToolTipText()).append(" x").append(spinnerBoba.getValue()).append(". ");
            extras += (int) spinnerBoba.getValue();
        }

        sb.append("Cantidad de extras: ").append(extras).append(". ");

        nota = sb.toString();
        txtDescripcion.setText(nota);
    }


    private void radioChico1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioChico1ActionPerformed
        // TODO add your handling code here:

        precioTamano = 0;

        recalcularTotal();

    }//GEN-LAST:event_radioChico1ActionPerformed

    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambiosActionPerformed

        // TODO add your handling code here:
//        Detallecomanda detalleCo = new Detallecomanda();
//
//        detalleCo.setIdProducto(producto);
//        detalleCo.setNotadetalleComanda(nota);
//        detalleCo.setSubTotaldetalleComanda(total);
        //aqui vaser 
        //     detalleCo.setCaintidaddetalleComanda((Integer) spinnerCantidadProducto.getValue());
        // (Tu lógica para crear la comanda si es nueva está bien)
        Detallecomanda detalleCo = new Detallecomanda();

        if (comandaEditar == null) { // Es una comanda nueva
            if (FrmListaProductos.comanda == null || FrmListaProductos.comanda.getIdComanda() == null) {
                try {
                    FrmListaProductos.comanda.setEstadoComanda("Abierta");
                    FrmListaProductos.comanda.setFechaHoracomanda(new Date());
                    FrmListaProductos.comanda.setTotalComanda(total);
                    FComandas.GuardarComanda(FrmListaProductos.comanda);
                    FrmListaProductos.idComanda = FrmListaProductos.comanda.getIdComanda();
                    comandaEditar = FrmListaProductos.comanda;
                } catch (Exception ex) {
                    Logger.getLogger(DetallesProducto.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
            } else {
                comandaEditar = FrmListaProductos.comanda;
            }
        }

        if (comandaEditar.getDetallecomandaList() == null) {
            comandaEditar.setDetallecomandaList(new ArrayList<>());
        }

        // --- LÓGICA DE EDICIÓN (CORREGIDA) ---
        Detallecomanda detalleParaGuardar; // Objeto a guardar (sea nuevo o editado)

        if (this.detalle != null) {
            System.out.println("Modo Editar: Actualizando ID: " + this.detalle.getIdDetalleComanda());
            detalleParaGuardar = this.detalle; // Usar detalle existente

            try {
                // 1. Borrar solo los extras ANTIGUOS (hijos)
//                if (detalleParaGuardar.getExtrasProductosList() != null && !detalleParaGuardar.getExtrasProductosList().isEmpty()) {
//                    System.out.println("Eliminando extras antiguos...");
//                    // Crear una copia para evitar errores de modificación concurrente
//                    List<ExtrasProductos> extrasAntiguos = new ArrayList<>(detalleParaGuardar.getExtrasProductosList());
//
//                    for (ExtrasProductos extraAntiguo : extrasAntiguos) {
//                        fExtras.eliminarExtrasProductos(extraAntiguo.getIdExtraProducto());
//                    }
//                }

                // 2. Actualizar los campos del padre (Detallecomanda)
                detalleParaGuardar.setNotadetalleComanda(nota);
                detalleParaGuardar.setSubTotaldetalleComanda(total);

                // 3. Editar
                fDC.editarDetallesComandas(detalleParaGuardar);

            } catch (Exception ex) {
                Logger.getLogger(DetallesProducto.class.getName()).log(Level.SEVERE, "Error al ACTUALIZAR el detalle", ex);
                return; // Salir si hay un error
            }

        } else {
            // --- MODO NUEVO: USAR EL MÉTODO GuardarDetalleComanda ---
            System.out.println("Modo Nuevo: Creando nuevo detalle");
            detalleParaGuardar = new Detallecomanda(); // Creamos un detalle nuevo

            detalleParaGuardar.setIdProducto(producto);
            detalleParaGuardar.setNotadetalleComanda(nota);
            detalleParaGuardar.setSubTotaldetalleComanda(total);
            
            detalleParaGuardar.setIdComanda(comandaEditar);

            try {
                // Guardar el nuevo detalle en la BD (usando la fachada de Comandas, como lo tenías)
                FComandas.GuardarDetalleComanda(detalleParaGuardar);
            } catch (Exception ex) {
                Logger.getLogger(DetallesProducto.class.getName()).log(Level.SEVERE, "Error al GUARDAR nuevo detalle", ex);
                return;
            }
        }

        // --- LÓGICA DE EXTRAS (Se ejecuta para ambos modos) ---
        // Ahora creamos los nuevos extras y los asociamos al 'detalleParaGuardar'
        ExtrasProductos extra = new ExtrasProductos();
        Leche leche = null;
        boolean hayExtras = false; // Bandera para saber si guardar

        if (jrbLecheSi.isSelected()) {
            hayExtras = true;
            try {
                if (tipoLeche.equalsIgnoreCase("Leche entera")) {
                    leche = fLeche.obtenerLeche(1);
                } else if (tipoLeche.equalsIgnoreCase("Deslactosada")) {
                    leche = fLeche.obtenerLeche(2);
                } else if (tipoLeche.equalsIgnoreCase("Leche de avena")) {
                    leche = fLeche.obtenerLeche(3);
                } else if (tipoLeche.equalsIgnoreCase("Leche de almendras")) {
                    leche = fLeche.obtenerLeche(4);
                }
                extra.setIdLeche(leche);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (jrbTamañoSi.isSelected()) {
            hayExtras = true;
            try {
                TamanoVaso vaso;
                if (radioMediano2.isSelected()) {
                    vaso = fVasos.obtenerTamanoVaso(1);
                } else {
                    vaso = fVasos.obtenerTamanoVaso(2);
                }
                extra.setIdTamanoVaso(vaso);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (hayExtras) {
            try {
                extra.setIdProducto(producto);
                extra.setIdDetalleComanda(detalleParaGuardar); // Se asocia al detalle (nuevo O editado)

                System.out.println("Guardando extras...");
                fExtras.agregarExtrasProductos(extra); //
            } catch (Exception ex) {
                Logger.getLogger(DetallesProducto.class.getName()).log(Level.SEVERE, "Error al guardar extras", ex);
            }
        }

        // Volver a la lista de productos
        listaProductos.setVisible(true);
        listaProductos.cargarPanelComanda();
        this.dispose();
    }//GEN-LAST:event_btnGuardarCambiosActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        listaProductos.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void radioMediano2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioMediano2ActionPerformed
        // TODO add your handling code here:

        precioTamano = 10;

        recalcularTotal();
    }//GEN-LAST:event_radioMediano2ActionPerformed

    private void radioMediano3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioMediano3ActionPerformed
        // TODO add your handling code here:
        precioTamano = 20;

        recalcularTotal();
    }//GEN-LAST:event_radioMediano3ActionPerformed

    private void toggleEnteraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleEnteraActionPerformed
        tipoLeche = "Leche entera";
        precioLeche = 0; // Sin costo adicional
        txtPrecioLeche.setText(String.valueOf(precioLeche));
        recalcularTotal();

    }//GEN-LAST:event_toggleEnteraActionPerformed

    private void toggleDeslactosadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleDeslactosadaActionPerformed
        tipoLeche = "Deslactosada";
        precioLeche = 5; // Costo adicional ejemplo
        txtPrecioLeche.setText(String.valueOf(precioLeche));
        recalcularTotal();
    }//GEN-LAST:event_toggleDeslactosadaActionPerformed

    private void toggleAvenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleAvenaActionPerformed
        tipoLeche = "Leche de avena";
        precioLeche = 7; // Costo adicional ejemplo
        txtPrecioLeche.setText(String.valueOf(precioLeche));
        recalcularTotal();
    }//GEN-LAST:event_toggleAvenaActionPerformed

    private void spinnerShotExtraStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerShotExtraStateChanged
        calcularExtrasYTotal();
    }//GEN-LAST:event_spinnerShotExtraStateChanged

    private void spinnerCremaExtraStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerCremaExtraStateChanged
        calcularExtrasYTotal();
    }//GEN-LAST:event_spinnerCremaExtraStateChanged

    private void spinnerVainillaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerVainillaStateChanged
        calcularExtrasYTotal();
    }//GEN-LAST:event_spinnerVainillaStateChanged

    private void spinnerCarameloStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerCarameloStateChanged
        calcularExtrasYTotal();
    }//GEN-LAST:event_spinnerCarameloStateChanged

    private void toggleAlmendrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleAlmendrasActionPerformed
        tipoLeche = "Leche de almendras";
        precioLeche = 8; // Costo adicional ejemplo
        txtPrecioLeche.setText(String.valueOf(precioLeche));
        recalcularTotal();
    }//GEN-LAST:event_toggleAlmendrasActionPerformed

    private void jrbTamañoNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbTamañoNoActionPerformed

        radioMediano2.setEnabled(false);
        radioMediano3.setEnabled(false);
        precioTamano = 0;
        recalcularTotal();
    }//GEN-LAST:event_jrbTamañoNoActionPerformed

    private void jrbTamañoSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbTamañoSiActionPerformed
        radioMediano2.setEnabled(true);
        radioMediano3.setEnabled(true);
    }//GEN-LAST:event_jrbTamañoSiActionPerformed

    private void jrbLecheSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbLecheSiActionPerformed
        toggleEntera.setEnabled(true);
        toggleDeslactosada.setEnabled(true);
        toggleAvena.setEnabled(true);
        toggleAlmendras.setEnabled(true);
    }//GEN-LAST:event_jrbLecheSiActionPerformed

    private void jrbLecheNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbLecheNoActionPerformed
        toggleEntera.setEnabled(false);
        toggleDeslactosada.setEnabled(false);
        toggleAvena.setEnabled(false);
        toggleAlmendras.setEnabled(false);
        tipoLeche = "";
        precioLeche = 0;
        recalcularTotal();
    }//GEN-LAST:event_jrbLecheNoActionPerformed

    private void jrbExtrasSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbExtrasSiActionPerformed
        spinnerLecheDeCoco.setEnabled(true);
        spinnerLecheAlmendras.setEnabled(true);
        spinnerShotExpreso.setEnabled(true);
        spinnerBoba.setEnabled(true);
    }//GEN-LAST:event_jrbExtrasSiActionPerformed

    private void jrbExtrasNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbExtrasNoActionPerformed
        spinnerLecheDeCoco.setEnabled(false);
        spinnerLecheAlmendras.setEnabled(false);
        spinnerShotExpreso.setEnabled(false);
        spinnerBoba.setEnabled(false);
        precioExtra = 0;
        recalcularTotal();
    }//GEN-LAST:event_jrbExtrasNoActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Fondo;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardarCambios;
    private javax.swing.ButtonGroup btnTipoLeche;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton jrbExtrasNo;
    private javax.swing.JRadioButton jrbExtrasSi;
    private javax.swing.JRadioButton jrbLecheNo;
    private javax.swing.JRadioButton jrbLecheSi;
    private javax.swing.JRadioButton jrbTamañoNo;
    private javax.swing.JRadioButton jrbTamañoSi;
    private javax.swing.JPanel panelExtras;
    private javax.swing.JPanel panelLeche;
    private javax.swing.JPanel panelTamano;
    private javax.swing.JRadioButton radioMediano2;
    private javax.swing.JRadioButton radioMediano3;
    private javax.swing.JSpinner spinnerBoba;
    private javax.swing.JSpinner spinnerLecheAlmendras;
    private javax.swing.JSpinner spinnerLecheDeCoco;
    private javax.swing.JSpinner spinnerShotExpreso;
    private javax.swing.JToggleButton toggleAlmendras;
    private javax.swing.JToggleButton toggleAvena;
    private javax.swing.JToggleButton toggleDeslactosada;
    private javax.swing.JToggleButton toggleEntera;
    private javax.swing.JLabel txtCarameloExtra;
    private javax.swing.JLabel txtCremaExtra;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JLabel txtExtra;
    private javax.swing.JLabel txtExtraShot;
    private javax.swing.JLabel txtLeche;
    private javax.swing.JLabel txtNombreCajero;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JLabel txtNumComanda;
    private javax.swing.JLabel txtNumMesa;
    private javax.swing.JLabel txtPrecioBase;
    private javax.swing.JLabel txtPrecioExtra;
    private javax.swing.JLabel txtPrecioLeche;
    private javax.swing.JLabel txtPrecioProductoResumen;
    private javax.swing.JLabel txtProductoResumen;
    private javax.swing.JLabel txtSubtotal;
    private javax.swing.JLabel txtTotal;
    private javax.swing.JLabel txtVainillaExtra;
    // End of variables declaration//GEN-END:variables
}
