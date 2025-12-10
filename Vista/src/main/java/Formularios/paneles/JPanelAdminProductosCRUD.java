/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Formularios.paneles;

import Entidades.Categoria;
import Entidades.Extra;
import Entidades.Producto;
import Facades.IFachadaCategoriaControlador;
import Facades.IFachadaExtraModel2;
import Facades.IFachadaProductoControlador;
import Facades.IFachadaProductoOpcionModel;
import Facades.IFachadaValorOpcionModel;
import Formularios.FrmPanelAdministrador;
import Formularios.paneles.elementos.JPanelExtra;
import Implementaciones.GestionarCategoriaControlador;
import Implementaciones.GestionarExtrasModel2;
import Implementaciones.GestionarProductoControlador;
import Implementaciones.GestionarProductoOpcionModel;
import Implementaciones.GestionarValorOpcionesModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import utilerias.ConstantesGUI;
import utilerias.NumberDecimalDocumentFilter;

/**
 *
 * @author Sergio Arturo
 */
public class JPanelAdminProductosCRUD extends javax.swing.JPanel {
    
    
    private final IFachadaCategoriaControlador fCategoria = new GestionarCategoriaControlador();
    private final IFachadaProductoControlador fProducto = new GestionarProductoControlador();
    private final IFachadaValorOpcionModel fExtras = new GestionarValorOpcionesModel();
    private final IFachadaProductoOpcionModel fProductoOpcion = new GestionarProductoOpcionModel();
    private final IFachadaExtraModel2 fex2 = new GestionarExtrasModel2();

    
    
    ConstantesGUI accion;
    Producto producto;
    
    Color colorSeleccion = Color.decode("#E0E0E0");
    Color colorNoSeleccion = Color.decode("#FFFFFF");
    Color colorHover = Color.decode("#F0F0F0");
    String seleccion = "Tipo vaso";
    
    
    Dimension dimension = null;
    
    List <Producto> listaProductos;
    List <Extra> listaExtras;
    List <Categoria> listaCategorias;
    
    List <Extra> extrasSeleccionados = new ArrayList<>();
    
    private boolean extraExpandido = false;
    
    private Object ultimoValorValido;
    boolean cargarExtras = false;
    
    

    /**
     * Creates new form JPanelAdminCategorias
     */
    public JPanelAdminProductosCRUD(ConstantesGUI accion, Producto producto) {
        initComponents();
        
        this.accion = accion;
        this.producto = producto;
        
        
        cargarDiseno();
        
        
        
        
        
    }
    
    
          

    
    private void cargarDiseno(){
           
        cargarCategorias();
      
        
        cargarExtras();
        
        
//        jPanelExtrasProductos.setPreferredSize(new Dimension(605, 312));
//        jPanelNavegacion.setVisible(false);
//        jPanelListaGeneral.setVisible(false);
//        
//        jPanelMostrarExtras.setOpaque(false);
        jPanelListaGeneral.setBorder(null);
        
        jScrollPaneExtras.setBorder(null);
        
        
        jblResumenCategoria.setVisible(true);
        jblResumenPrecio.setVisible(false);
        jblResumenNombre.setVisible(false);
        
        jblNumExtra.setVisible(false);
        jblNumExtraLeches.setVisible(false);
        jblNumExtraVaso.setVisible(false);
        
        if(!listaCategorias.isEmpty()){
            jblResumenCategoria.setText(listaCategorias.get(0).getNombre());
        }
        
        
        accionesConstantesGUI();
        
        
    }
    
    
    private void cargarCategorias() {

        
        List<String> listaCategoriasString = new ArrayList<>();

        try {
            
            listaExtras = fex2.obtenerTodosLosExtras();
            
                        
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
            Logger.getLogger(JPanelAdminProductosCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
    private void accionesConstantesGUI(){
        
        
        if(accion == ConstantesGUI.NUEVO){
            btnAccion.setText("Guardar");
        }
        
        if(accion == ConstantesGUI.EDITAR){
            accionesEditar();
        }
        
        if(accion == ConstantesGUI.ELIMINAR){
            System.out.println("producto extras: " + producto.getExtras().size());
            accionesEliminar();
        }
        
        
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
                
            case "Extra bebidas" -> {
                jPanelSeccionExtras.setBackground(colorSeleccion);
                
                jPanelSeccionLeches.setBackground(colorNoSeleccion);
                jPanelSeccionVasos.setBackground(colorNoSeleccion);
            }      
                
            
        }
        
     }
     
     
     
     
     
    private void cargarExtras(){
  
        jPanelListaGeneral.removeAll();

        
        try{
            
            List<Extra> listaProvisional = new ArrayList<>();
            if(!listaProvisional.isEmpty()){
                listaProvisional.clear();
            }
            
            for(int i = 0; i < listaExtras.size(); i++){
                    if(listaExtras.get(i).getTipoExtra().equalsIgnoreCase(seleccion)){
                        listaProvisional.add(listaExtras.get(i));
                    }
            }
            
            if(accion == ConstantesGUI.NUEVO){
                
            }
            
            if(accion == ConstantesGUI.EDITAR || accion == ConstantesGUI.ELIMINAR){
                if(cargarExtras == false){
                    for (int i = 0; i < producto.getExtras().size(); i++){
                        extrasSeleccionados.add(producto.getExtras().get(i));
                    }
                    cargarExtras = true;
                }
                               
            }
            
            System.out.println("ex " + extrasSeleccionados.size());
            
            int alturaProducto = 55; 
                
            for(int i = 0; i < listaProvisional.size(); i++){    
                
                JPanelExtra extra = new JPanelExtra();
                
                extra.setBounds(10, alturaProducto * i, 530, 45);

                extra.setAlignmentX(Component.LEFT_ALIGNMENT);
                extra.setBackground(Color.decode("#FFFFFF"));



                extra.getJblNombreExtra().setText(listaProvisional.get(i).getNombreExtra());
                extra.getJblPrecioExtra().setText(String.valueOf(listaProvisional.get(i).getPrecio()));

                
                Extra opcionActual = listaProvisional.get(i);

                for(int j = 0; j < extrasSeleccionados.size(); j++){
                    if(extrasSeleccionados.get(j).getNombreExtra().equalsIgnoreCase(opcionActual.getNombreExtra())){
                        extra.setBackground(colorSeleccion);
                        extra.setSelecionado(true);
                    }
                }
                
                if(!(accion == ConstantesGUI.ELIMINAR)){
                    extra.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {                            
                            if (extra.isSelecionado()) {
                                for (int i = 0; i < extrasSeleccionados.size(); i++) {
                                    if (extrasSeleccionados.get(i).getIdExtra().equals(opcionActual.getIdExtra())) {

                                        extrasSeleccionados.remove(i); 
                                        break;
                                    }
                                }
                                extra.setBackground(colorNoSeleccion);
                                extra.setSelecionado(false);
                                

                            }
                            else{
                                extra.setSelecionado(true);
                                extrasSeleccionados.add(opcionActual);
                                
                                extra.setBackground(colorSeleccion);
                                
                                System.out.println("nolo contiene");
                            }
                            establecerResumenExtras();
                            System.out.println("extraselect " + extrasSeleccionados.size() );
                        }

                        @Override
                        public void mouseEntered(MouseEvent e){
                            if(!extra.isSelecionado()){
                                extra.setBackground(Color.decode("#E0E0E0"));
                            }

                        }

                        @Override
                        public void mouseExited(MouseEvent e){
                            if(!extra.isSelecionado()){
                                extra.setBackground(Color.decode("#FFFFFF"));
                            }
                        }

                    });
                }

                

                extra.getJblEliminarExtra().setVisible(false);
                extra.getJblEliminarExtra().setCursor(new Cursor(1));
                

              
//               
//               if(jPanelListaGeneral.getPreferredSize().height < alturaProducto * i){
//                    
//                    if(dimension == null){
//                        dimension = new Dimension();
//                    }
//                    
//                    dimension.setSize(jPanelListaGeneral.getPreferredSize().width, alturaProducto * i);
//                    jPanelListaGeneral.setPreferredSize(dimension);
//               }
//                    
                jPanelListaGeneral.add(extra);
                
                
                
                
             int numeroExtras = listaProvisional.size();
             
             if(numeroExtras > 0){
                 int nuevaAltura = alturaProducto * numeroExtras;
                 
                 if(dimension == null){
                     dimension = new Dimension();
                 }
                 
                 int anchoActual = jPanelListaGeneral.getPreferredSize().width;
                 
                 dimension.setSize(anchoActual, nuevaAltura);
                 jPanelListaGeneral.setPreferredSize(dimension);
                 
             }
             
             else{
                 jPanelListaGeneral.setPreferredSize(new Dimension(jPanelListaGeneral.getPreferredSize().width,10));
             }

                
            }
        
        
            jPanelListaGeneral.revalidate();
            jPanelListaGeneral.repaint();
            
            
          establecerResumenExtras();
            
        }
        
        
        
        catch(NumberFormatException e){
        } catch (Exception ex) {
            Logger.getLogger(JPanelAdminProductosCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
    
     
    
    private void accionesEditar(){
        
        btnAccion.setText("EDITAR");
        
         txtNombreProducto1.setText(producto.getNombreProducto());
       // jblDescripcionProducto1.setText(producto.ge);
        jSpinnerPrecioProducto.setValue(producto.getPrecioProducto());
        cbCategorias.setSelectedItem((String)producto.getIdCategoria().getNombre());
        
        jblResumenNombre.setText(producto.getNombreProducto());
        jblResumenNombre.setVisible(true);
        
        jblResumenPrecio.setText(String.valueOf(producto.getPrecioProducto()));
        jblResumenPrecio.setVisible(true);
        
        
        establecerResumenExtras();
    }
    
     
    private void accionesEliminar(){
        btnAccion.setText("ELIMINAR");
        btnAccion.setBackground(Color.red);
        
        txtNombreProducto1.setEditable(false);
        jSpinnerPrecioProducto.setEnabled(false);
        cbCategorias.setEnabled(false);
        
        txtNombreProducto1.setText(producto.getNombreProducto());
       // jblDescripcionProducto1.setText(producto.ge);
        jSpinnerPrecioProducto.setValue(producto.getPrecioProducto());
        cbCategorias.setSelectedItem((String)producto.getIdCategoria().getNombre());
        
        jblResumenNombre.setText(producto.getNombreProducto());
        jblResumenNombre.setVisible(true);
        
        jblResumenPrecio.setText(String.valueOf(producto.getPrecioProducto()));
        jblResumenPrecio.setVisible(true);
        
        
        establecerResumenExtras();
        
        
    } 
     
     
    /**
 * Valida que el nombre del producto no esté vacío
 * @return true si el nombre es válido, false en caso contrario
 */
private boolean validarNombreProducto() {
    String nombre = txtNombreProducto1.getText().trim();
    
    if (nombre.isEmpty()) {
        JOptionPane.showMessageDialog(
            null,
            "El nombre del producto es obligatorio",
            "Error de validación",
            JOptionPane.ERROR_MESSAGE
        );
        txtNombreProducto1.requestFocus();
        return false;
    }
    
    return true;
}

private void agregarProducto(){
    
    try{
        
        // Validación del nombre
        if (!validarNombreProducto()) {
            return;
        }
        
        String nombre = txtNombreProducto1.getText();
        float precio = ((Number)jSpinnerPrecioProducto.getValue()).floatValue();
        Categoria categoria = fCategoria.obtenerCategoriaPorNombre((String)cbCategorias.getSelectedItem());
        
        //Validaciones
        
        
        Producto producto = new Producto();
        producto.setNombreProducto(nombre);
        producto.setIdCategoria(categoria);
        producto.setPrecioProducto(precio);
        producto.setEstado("activo");
        
        //Aqui se guardan los extras
        if(!extrasSeleccionados.isEmpty()){
            
           List <Extra> listaExtrasGuardar = new ArrayList<>();
           
           for(int i = 0; i < extrasSeleccionados.size(); i++){ 
               Extra extraGuardar = new Extra();
               extraGuardar.setIdExtra(extrasSeleccionados.get(i).getIdExtra());
               extraGuardar.setEstado("activo");
               extraGuardar.setNombreExtra(extrasSeleccionados.get(i).getNombreExtra());
               extraGuardar.setPrecio(extrasSeleccionados.get(i).getPrecio());
               extraGuardar.setTipoExtra(extrasSeleccionados.get(i).getTipoExtra());
               
               listaExtrasGuardar.add(extraGuardar);
           }
           
            producto.setExtras(listaExtrasGuardar);
   
        }
        
        Producto nuevoProducto = fProducto.agregarProducto(producto);
        
        
        JOptionPane.showMessageDialog(null, "Producto agregado");
        
        volverSecccionAnterior();
    }
    
    catch(Exception e){
        e.printStackTrace();
    }
    
    
}
    
    private void editarProducto(){
        
        
        try{
        String nombre = txtNombreProducto1.getText();
        float precio = ((Number)jSpinnerPrecioProducto.getValue()).floatValue();
        Categoria categoria = fCategoria.obtenerCategoriaPorNombre((String)cbCategorias.getSelectedItem());
            
        //Validaciones
     
        
        
        producto.setNombreProducto(nombre);
        producto.setIdCategoria(categoria);
        producto.setPrecioProducto(precio);
        producto.setEstado("activo");
            
        
        List <Extra> listaExtrasGuardar = new ArrayList<>();
            if(!extrasSeleccionados.isEmpty()){    
               for(int i = 0; i < extrasSeleccionados.size(); i++){ 
                   Extra extraGuardar = new Extra();
                   extraGuardar.setIdExtra(extrasSeleccionados.get(i).getIdExtra());
                   extraGuardar.setEstado("activo");
                   extraGuardar.setNombreExtra(extrasSeleccionados.get(i).getNombreExtra());
                   extraGuardar.setPrecio(extrasSeleccionados.get(i).getPrecio());
                   extraGuardar.setTipoExtra(extrasSeleccionados.get(i).getTipoExtra());
                   
                   listaExtrasGuardar.add(extraGuardar);
               }
               
            }
            
            producto.setExtras(listaExtrasGuardar);
            
            fProducto.editarProducto(producto);
            
            
            JOptionPane.showMessageDialog(this, "Producto Editado");
            
            volverSecccionAnterior();
            
        }
        
        catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    private void eliminarProducto(){
        
        int opcion = JOptionPane.showConfirmDialog(
            null, // Componente padre: null si es una ventana independiente
            "¿Estás seguro que deseas eliminar este elemento?", // Mensaje
            "Confirmar Eliminación", // Título de la ventana
            JOptionPane.YES_NO_OPTION, // Tipo de opciones (Sí/No)
            JOptionPane.QUESTION_MESSAGE // Icono (Signo de interrogación)
        );

        if (opcion == JOptionPane.YES_OPTION) {
            
            producto.setEstado("inactivo");
            try {
                fProducto.editarProducto(producto);
                System.out.println("Elemento eliminado exitosamente.");
                JOptionPane.showMessageDialog(null, "El elemento ha sido eliminado.");
                
                volverSecccionAnterior();
                
            } catch (Exception ex) {
                Logger.getLogger(JPanelAdminProductosCRUD.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
        }
        else if (opcion == JOptionPane.NO_OPTION) {

            
        } 
        
        else if (opcion == JOptionPane.CLOSED_OPTION) {
            
        }
    
    }
    
    
    private void volverSecccionAnterior(){
        FrmPanelAdministrador.jPanelSeccion.removeAll();
            
        JPanelAdminProductos panelProductos = new JPanelAdminProductos();
            
        panelProductos.setBounds(0, 0, FrmPanelAdministrador.jPanelSeccion.getWidth(), FrmPanelAdministrador.jPanelSeccion.getHeight());
        FrmPanelAdministrador.jPanelSeccion.add(panelProductos);
        
        FrmPanelAdministrador.jPanelSeccion.revalidate();
        FrmPanelAdministrador.jPanelSeccion.repaint();
    
    }
    
    
    
    
    private void configurarEditorDecimal(JSpinner spinner) {
    JComponent editor = spinner.getEditor();
    
    if (editor instanceof JSpinner.DefaultEditor) {
        JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
        
        // 1. Crear el formato decimal explícito (con punto decimal)
        DecimalFormat decimalFormat = new DecimalFormat("0.00"); // Acepta 2 decimales
        
        // 2. Crear el formateador con el formato decimal
        NumberFormatter formatter = new NumberFormatter(decimalFormat);
        
        // 3. Configurar para que acepte decimales (Float o Double)
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(true); // Permitir entrada temporalmente inválida (ej: solo un punto)
        formatter.setCommitsOnValidEdit(true); // Actualizar el modelo solo si es válido
        
        // 4. Aplicar el formateador al campo de texto
        DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);
        textField.setFormatterFactory(factory);

        // Opcional: Centrar el texto
        textField.setHorizontalAlignment(JTextField.RIGHT);
    }
}
    
    
    
    private void aplicarValidadorASpinner(JSpinner spinner) {
    
        JComponent editor = spinner.getEditor();
        JFormattedTextField textField = null;

        // Paso 1: Intentar obtener el JFormattedTextField del editor
        if (editor instanceof JSpinner.DefaultEditor) {
            textField = ((JSpinner.DefaultEditor) editor).getTextField();
        } 

        if (textField != null) {
            // Paso 2: Crear el filtro
            NumberDecimalDocumentFilter decimalFilter = new NumberDecimalDocumentFilter();

            // Paso 3: Aplicar el filtro al Documento
            ((AbstractDocument) textField.getDocument()).setDocumentFilter(decimalFilter);

            // Opcional: Asegurarse de que el spinner solo permita editar texto
            textField.setEditable(true); 
        } else {
            System.err.println("Advertencia: No se pudo encontrar el campo de texto interno del JSpinner.");
        }
}
    
    
    private void configurarSpinnerConReversion(JSpinner spinner) {
        
      //  configurarEditorDecimal(spinner);    

        // 1. Inicializar el último valor válido
        ultimoValorValido = spinner.getValue();

        // 2. Agregar el ChangeListener al modelo
        spinner.getModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    // El método getValue() del SpinnerModel intenta devolver el valor,
                    // PERO puede lanzar una excepción si el texto del campo es inválido
                    // o si excede los límites definidos por el modelo.

                    Object nuevoValor = spinner.getValue();

                    // Si llegamos aquí, el valor es válido (dentro de los límites del modelo).
                    // Guardamos este nuevo valor para usarlo si el próximo intento falla.
                    ultimoValorValido = nuevoValor;

                } catch (Exception ex) {
                    // 3. Si hay una excepción (valor inválido o fuera de límites):

                    // Revertir el JSpinner al último valor conocido y válido
                    spinner.setValue(ultimoValorValido);

                    // Opcional: Mostrar un mensaje o loguear el error.
                    System.out.println("Valor inválido detectado. Revertiendo a: " + ultimoValorValido);
                }
            }
        });

        // Opcional: Si además quieres asegurar que solo se puedan escribir números,
        // asegúrate de llamar a tu método de aplicación del DocumentFilter aquí:
         aplicarValidadorASpinner(spinner);
}
    
    
    private void establecerResumenExtras(){
        if(extrasSeleccionados != null){
            int leche = 0;
            int vaso = 0;
            int otros = 0;
            
            for(int i = 0; i < extrasSeleccionados.size(); i++){
                if(extrasSeleccionados.get(i).getTipoExtra().equalsIgnoreCase("Tipo vaso")){
                    vaso++;
                }
                
                if(extrasSeleccionados.get(i).getTipoExtra().equalsIgnoreCase("Tipo leche")){
                    leche++;
                }
                
                if(extrasSeleccionados.get(i).getTipoExtra().equalsIgnoreCase("Tipo Extra")){
                    otros++;
                }
                
            }
            
            jblNumExtraLeches.setText("Leches (" + leche + ")" );
            jblNumExtraVaso.setText("Vasos (" + vaso + ")" );
            jblNumExtra.setText("Otros (" + otros + ")" );
            
            
            // Código mejorado usando el operador ternario:
            jblNumExtraLeches.setVisible(leche != 0);

            // Código original:
            // if(vaso == 0){ jblNumExtraVaso.setVisible(false); } else { jblNumExtraVaso.setVisible(true); }

            // Código mejorado usando el operador ternario:
            jblNumExtraVaso.setVisible(vaso != 0);
            System.out.println("lec" + jblNumExtraVaso.getText());

            // Código original:
            // if(otros == 0){ jblNumExtra.setVisible(false); } else { jblNumExtra.setVisible(true); }

            // Código mejorado usando el operador ternario:
            jblNumExtra.setVisible(otros != 0);
            
            
            jblNumExtra1.setText("Extras (" + extrasSeleccionados.size() + ")");
            
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

        jPanel1 = new javax.swing.JPanel();
        jblAdminProductos = new javax.swing.JLabel();
        jScrollPaneProductos = new javax.swing.JScrollPane();
        jPanelExtrasProductos = new javax.swing.JPanel();
        txtNombreProducto1 = new javax.swing.JTextField();
        jblCategoriaProducto = new javax.swing.JLabel();
        jblNombreProducto = new javax.swing.JLabel();
        jSpinnerPrecioProducto = new javax.swing.JSpinner();
        cbCategorias = new javax.swing.JComboBox<>();
        jblPrecioProducto = new javax.swing.JLabel();
        jPanelNavegacion = new javax.swing.JPanel();
        jPanelSeccionVasos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelSeccionLeches = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanelSeccionExtras = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPaneExtras = new javax.swing.JScrollPane();
        jPanelListaGeneral = new javax.swing.JPanel();
        jPanelResumenProducto = new javax.swing.JPanel();
        jblNumExtra = new javax.swing.JLabel();
        jblResumenPrecio = new javax.swing.JLabel();
        jblResumenNombre = new javax.swing.JLabel();
        jblResumenCategoria = new javax.swing.JLabel();
        jblResumenNombre1 = new javax.swing.JLabel();
        jblResumenPrecio1 = new javax.swing.JLabel();
        jblResumenCategoria1 = new javax.swing.JLabel();
        jblNumExtra1 = new javax.swing.JLabel();
        jblNumExtraVaso = new javax.swing.JLabel();
        jblNumExtraLeches = new javax.swing.JLabel();
        btnAccion = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(965, 620));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel1.setLayout(null);

        jblAdminProductos.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jblAdminProductos.setText("Agregar Producto");
        jPanel1.add(jblAdminProductos);
        jblAdminProductos.setBounds(20, 30, 170, 22);

        jScrollPaneProductos.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPaneProductos.setBorder(null);
        jScrollPaneProductos.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPaneProductos.setViewportView(jPanelExtrasProductos);

        jPanelExtrasProductos.setBackground(new java.awt.Color(255, 255, 255));
        jPanelExtrasProductos.setMinimumSize(new java.awt.Dimension(603, 482));
        jPanelExtrasProductos.setPreferredSize(new java.awt.Dimension(603, 482));
        jPanelExtrasProductos.setLayout(null);

        txtNombreProducto1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNombreProducto1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProducto1KeyTyped(evt);
            }
        });
        jPanelExtrasProductos.add(txtNombreProducto1);
        txtNombreProducto1.setBounds(10, 40, 530, 42);

        jblCategoriaProducto.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jblCategoriaProducto.setText("Categoria");
        jPanelExtrasProductos.add(jblCategoriaProducto);
        jblCategoriaProducto.setBounds(230, 100, 150, 22);

        jblNombreProducto.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jblNombreProducto.setText("Nombre del producto");
        jPanelExtrasProductos.add(jblNombreProducto);
        jblNombreProducto.setBounds(10, 10, 170, 22);

        jSpinnerPrecioProducto.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerPrecioProductoStateChanged(evt);
            }
        });
        jSpinnerPrecioProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jSpinnerPrecioProductoKeyTyped(evt);
            }
        });
        jPanelExtrasProductos.add(jSpinnerPrecioProducto);
        jSpinnerPrecioProducto.setBounds(10, 130, 150, 40);

        cbCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Deshabilitado" }));
        cbCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCategoriasActionPerformed(evt);
            }
        });
        jPanelExtrasProductos.add(cbCategorias);
        cbCategorias.setBounds(230, 130, 150, 40);

        jblPrecioProducto.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jblPrecioProducto.setText("Precio");
        jPanelExtrasProductos.add(jblPrecioProducto);
        jblPrecioProducto.setBounds(10, 100, 130, 22);

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

        jPanelSeccionLeches.setBackground(new java.awt.Color(255, 255, 255));
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

        jPanelSeccionExtras.setBackground(new java.awt.Color(255, 255, 255));
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

        jPanelExtrasProductos.add(jPanelNavegacion);
        jPanelNavegacion.setBounds(10, 250, 560, 50);

        jPanelListaGeneral.setBackground(new java.awt.Color(255, 255, 255));
        jPanelListaGeneral.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelListaGeneral.setMinimumSize(new java.awt.Dimension(300, 20));
        jPanelListaGeneral.setPreferredSize(new java.awt.Dimension(550, 200));
        jPanelListaGeneral.setLayout(null);
        jScrollPaneExtras.setViewportView(jPanelListaGeneral);

        jPanelExtrasProductos.add(jScrollPaneExtras);
        jScrollPaneExtras.setBounds(10, 320, 570, 160);

        jScrollPaneProductos.setViewportView(jPanelExtrasProductos);

        jPanel1.add(jScrollPaneProductos);
        jScrollPaneProductos.setBounds(20, 80, 600, 490);

        jPanelResumenProducto.setBackground(new java.awt.Color(255, 255, 255));
        jPanelResumenProducto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelResumenProducto.setLayout(null);

        jblNumExtra.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jblNumExtra.setText("Otros");
        jPanelResumenProducto.add(jblNumExtra);
        jblNumExtra.setBounds(110, 320, 110, 30);

        jblResumenPrecio.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jblResumenPrecio.setText("Precio");
        jPanelResumenProducto.add(jblResumenPrecio);
        jblResumenPrecio.setBounds(110, 90, 130, 30);

        jblResumenNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jblResumenNombre.setText("Nombre");
        jPanelResumenProducto.add(jblResumenNombre);
        jblResumenNombre.setBounds(110, 40, 140, 30);

        jblResumenCategoria.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jblResumenCategoria.setText("Categoria");
        jPanelResumenProducto.add(jblResumenCategoria);
        jblResumenCategoria.setBounds(110, 140, 70, 30);

        jblResumenNombre1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jblResumenNombre1.setText("Nombre");
        jPanelResumenProducto.add(jblResumenNombre1);
        jblResumenNombre1.setBounds(20, 40, 60, 30);

        jblResumenPrecio1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jblResumenPrecio1.setText("Precio");
        jPanelResumenProducto.add(jblResumenPrecio1);
        jblResumenPrecio1.setBounds(20, 90, 80, 30);

        jblResumenCategoria1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jblResumenCategoria1.setText("Categoria");
        jPanelResumenProducto.add(jblResumenCategoria1);
        jblResumenCategoria1.setBounds(20, 140, 70, 30);

        jblNumExtra1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jblNumExtra1.setText("Extras: 0");
        jPanelResumenProducto.add(jblNumExtra1);
        jblNumExtra1.setBounds(20, 200, 70, 30);

        jblNumExtraVaso.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jblNumExtraVaso.setText("Vasos (0)");
        jPanelResumenProducto.add(jblNumExtraVaso);
        jblNumExtraVaso.setBounds(110, 240, 120, 30);

        jblNumExtraLeches.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jblNumExtraLeches.setText("Leches (0)");
        jPanelResumenProducto.add(jblNumExtraLeches);
        jblNumExtraLeches.setBounds(110, 280, 120, 30);

        jPanel1.add(jPanelResumenProducto);
        jPanelResumenProducto.setBounds(640, 80, 260, 360);

        btnAccion.setBackground(new java.awt.Color(17, 24, 39));
        btnAccion.setFont(new java.awt.Font("Helvetica Neue", 1, 16)); // NOI18N
        btnAccion.setForeground(new java.awt.Color(255, 255, 255));
        btnAccion.setText("Accion");
        btnAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccionActionPerformed(evt);
            }
        });
        jPanel1.add(btnAccion);
        btnAccion.setBounds(730, 30, 170, 30);

        add(jPanel1);
        jPanel1.setBounds(20, 14, 920, 590);
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        
        
        
    }//GEN-LAST:event_formMouseClicked

    private void txtNombreProducto1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProducto1KeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtNombreProducto1KeyTyped

    private void cbCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCategoriasActionPerformed
        // TODO add your handling code here:}
        jblResumenCategoria.setVisible(true);
        jblResumenCategoria.setText((String)cbCategorias.getSelectedItem());
    }//GEN-LAST:event_cbCategoriasActionPerformed

    private void btnAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccionActionPerformed
        // TODO add your handling code here:

        if(accion == ConstantesGUI.NUEVO){
            System.out.println("guardar");
            agregarProducto();
        }
        
        if(accion == ConstantesGUI.EDITAR){
            editarProducto();
        }
        
        if(accion == ConstantesGUI.ELIMINAR){
            eliminarProducto();
        }

    }//GEN-LAST:event_btnAccionActionPerformed

    private void jPanelSeccionVasosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionVasosMouseClicked
        // TODO add your handling code here:

        seleccion = "Tipo vaso";

        comprobarSeleccion();

        dimension = null;
        cargarExtras();

    }//GEN-LAST:event_jPanelSeccionVasosMouseClicked

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

    private void jPanelSeccionLechesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionLechesMouseClicked
        // TODO add your handling code here:

        seleccion = "Tipo leche";

        comprobarSeleccion();

        dimension = null;
        cargarExtras();
    }//GEN-LAST:event_jPanelSeccionLechesMouseClicked

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

    private void jPanelSeccionExtrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionExtrasMouseClicked
        // TODO add your handling code here:

        seleccion = "Tipo extra";

        comprobarSeleccion();

        dimension = null;
        cargarExtras();
    }//GEN-LAST:event_jPanelSeccionExtrasMouseClicked

    private void jPanelSeccionExtrasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionExtrasMouseEntered
        // TODO add your handling code here:
        if(!seleccion.equalsIgnoreCase("Tipo extra")){
            jPanelSeccionExtras.setBackground(colorHover);
        }

    }//GEN-LAST:event_jPanelSeccionExtrasMouseEntered

    private void jPanelSeccionExtrasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanelSeccionExtrasMouseExited
        // TODO add your handling code here:
        if(!seleccion.equalsIgnoreCase("Tipo extra")){
            jPanelSeccionExtras.setBackground(colorNoSeleccion);
        }
    }//GEN-LAST:event_jPanelSeccionExtrasMouseExited

    private void jSpinnerPrecioProductoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerPrecioProductoStateChanged
        // TODO add your handling code here:
        configurarSpinnerConReversion(jSpinnerPrecioProducto);
        
        jblResumenPrecio.setVisible(true);
        jblResumenPrecio.setText(String.valueOf(ultimoValorValido));
        
    }//GEN-LAST:event_jSpinnerPrecioProductoStateChanged

    private void jSpinnerPrecioProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSpinnerPrecioProductoKeyTyped
        // TODO add your handling code here:
        configurarSpinnerConReversion(jSpinnerPrecioProducto);
    }//GEN-LAST:event_jSpinnerPrecioProductoKeyTyped

    private void txtNombreProducto1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProducto1KeyReleased
        // TODO add your handling code here:
        jblResumenNombre.setVisible(true);
        jblResumenNombre.setText(txtNombreProducto1.getText());
    }//GEN-LAST:event_txtNombreProducto1KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccion;
    private javax.swing.JComboBox<String> cbCategorias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelExtrasProductos;
    private javax.swing.JPanel jPanelListaGeneral;
    private javax.swing.JPanel jPanelNavegacion;
    private javax.swing.JPanel jPanelResumenProducto;
    private javax.swing.JPanel jPanelSeccionExtras;
    private javax.swing.JPanel jPanelSeccionLeches;
    private javax.swing.JPanel jPanelSeccionVasos;
    private javax.swing.JScrollPane jScrollPaneExtras;
    private javax.swing.JScrollPane jScrollPaneProductos;
    private javax.swing.JSpinner jSpinnerPrecioProducto;
    private javax.swing.JLabel jblAdminProductos;
    private javax.swing.JLabel jblCategoriaProducto;
    private javax.swing.JLabel jblNombreProducto;
    private javax.swing.JLabel jblNumExtra;
    private javax.swing.JLabel jblNumExtra1;
    private javax.swing.JLabel jblNumExtraLeches;
    private javax.swing.JLabel jblNumExtraVaso;
    private javax.swing.JLabel jblPrecioProducto;
    private javax.swing.JLabel jblResumenCategoria;
    private javax.swing.JLabel jblResumenCategoria1;
    private javax.swing.JLabel jblResumenNombre;
    private javax.swing.JLabel jblResumenNombre1;
    private javax.swing.JLabel jblResumenPrecio;
    private javax.swing.JLabel jblResumenPrecio1;
    private javax.swing.JTextField txtNombreProducto1;
    // End of variables declaration//GEN-END:variables
}
