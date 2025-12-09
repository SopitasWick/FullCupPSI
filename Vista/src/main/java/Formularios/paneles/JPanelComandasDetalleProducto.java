/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Formularios.paneles;

import Entidades.Comanda;
import Entidades.Detallecomanda;
import Entidades.Extra;
import Entidades.ExtrasDetalleComanda;
import Entidades.Producto;
import Facades.IFachadaCategoriaControlador;
import Facades.IFachadaComandasControlador;
import Facades.IFachadaDetallesComandaControlador;
import Facades.IFachadaExtraControlador;
import Facades.IFachadaExtraDetalleComandaControlador;
import Facades.IFachadaProductoControlador;
import Formularios.FrmComandas;
import Formularios.paneles.elementos.JPanelExtra;
import Formularios.paneles.elementos.JPanelExtra1;
import Implementaciones.GestionarCategoriaControlador;
import Implementaciones.GestionarComandaControlador;
import Implementaciones.GestionarDetallesComandaControlador;
import Implementaciones.GestionarExtraControlador;
import Implementaciones.GestionarExtraDetalleComandaControlador;
import Implementaciones.GestionarProductoControlador;
import dto.ExtraCantidadDTO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import utilerias.ConstantesGUI;
import utilerias.HintToTextField;


/**
 *
 * @author Sergio Arturo
 */
public class JPanelComandasDetalleProducto extends javax.swing.JPanel {

        
    private final IFachadaComandasControlador FComandas = new GestionarComandaControlador();
    private final IFachadaCategoriaControlador fCategoria = new GestionarCategoriaControlador();
    private final IFachadaExtraControlador fExtra = new GestionarExtraControlador();
    private final IFachadaDetallesComandaControlador fDC = new GestionarDetallesComandaControlador();
    private final IFachadaProductoControlador fProductos = new GestionarProductoControlador();
    private final IFachadaExtraDetalleComandaControlador fExtraDetalleComanda = new GestionarExtraDetalleComandaControlador();
    
    
    
    ConstantesGUI accion;
    Comanda comanda;
    Producto producto;
    
    
    List <Extra> listaExtras;
    Extra extraVaso = null;
    Extra extraLeche = null;
    
    
    private List<ExtraCantidadDTO> listaEx = new ArrayList<>() ; // ¡DEBES INICIALIZAR ESTA LISTA!
    private List<ExtrasDetalleComanda> listaExtrasSeleccion;
    
    
    Dimension dimension = null;
    
    
    /**
     * Creates new form JPanelComandas
     */
    public JPanelComandasDetalleProducto(ConstantesGUI accion, Comanda comanda, Producto producto) {
        initComponents();
        
        this.accion = accion;
        this.comanda = comanda;
        this.producto = producto;
        
     
        cargarDiseno();
        cargarDatos();
    }
    
    
    private void cargarDiseno(){
        
        jPanelPromociones.setVisible(false);
        
        txtNombreProducto.setOpaque(false);
        txtNombreProducto.setText(producto.getNombreProducto());
        
        txtPrecioBase.setText("Precio: " + producto.getPrecioProducto());
        
        jPanelListaVasos.setBorder(null);
        jPanelListaLeches.setBorder(null);
        jPanelListaExtras.setBorder(null);
        
        HintToTextField.addHint(txtDescripcion, "Notas");
        
    }
    
    
    private void cargarDatos(){
    
        listaExtras = producto.getExtras();
        
        accionesConstanteGUI();
        
        
    }
    
   
    private void accionesConstanteGUI(){
        
        if (accion == ConstantesGUI.NUEVO){
            cargarExtraVasos();
            cargarExtraLeches();
            cargarExtras();

            calcularTotal();
        
        }
        
        if (accion == ConstantesGUI.EDITAR){
            accionesEditar();
        }
        
        
        
        if (accion == ConstantesGUI.ELIMINAR){
            accionesEliminar();
            
        }
    }
    
    
    private void accionesEditar(){
        listaExtrasSeleccion = fExtraDetalleComanda.obtenerTodosLosExtrasPorDetalleComanda(FrmComandas.detalleComanda);
            
            for(int i = 0; i < listaExtrasSeleccion.size(); i++){
                if(listaExtrasSeleccion.get(i).getExtra().getTipoExtra().equalsIgnoreCase("Tipo vaso")){
                    extraVaso = listaExtrasSeleccion.get(i).getExtra();
                }
                
                if(listaExtrasSeleccion.get(i).getExtra().getTipoExtra().equalsIgnoreCase("Tipo leche")){
                    extraLeche = listaExtrasSeleccion.get(i).getExtra();
                }
           
            }
            
            List <ExtrasDetalleComanda> listaTemp = new ArrayList<>();
            
            for(int i = 0; i < listaExtrasSeleccion.size(); i++){
                if(!(listaExtrasSeleccion.get(i).getExtra().getTipoExtra().equalsIgnoreCase("Tipo leche") ||
                   listaExtrasSeleccion.get(i).getExtra().getTipoExtra().equalsIgnoreCase("Tipo vaso"))){
                    listaTemp.add(listaExtrasSeleccion.get(i));
                }
            }
            
            listaEx = agruparExtrasManualmente(listaTemp);
            
            
            cargarExtraVasosEditar();
            cargarExtraLechesEditar();
            cargarExtrasEditar();
            
            txtDescripcion.setText(FrmComandas.detalleComanda.getNotadetalleComanda());
            
            btnGuardarCambios.setText("Editar");
            
            calcularTotal();
    
    }
    
    private void accionesEliminar(){
        listaExtrasSeleccion = fExtraDetalleComanda.obtenerTodosLosExtrasPorDetalleComanda(FrmComandas.detalleComanda);
            
            for(int i = 0; i < listaExtrasSeleccion.size(); i++){
                if(listaExtrasSeleccion.get(i).getExtra().getTipoExtra().equalsIgnoreCase("Tipo vaso")){
                    extraVaso = listaExtrasSeleccion.get(i).getExtra();
                }
                
                if(listaExtrasSeleccion.get(i).getExtra().getTipoExtra().equalsIgnoreCase("Tipo leche")){
                    extraLeche = listaExtrasSeleccion.get(i).getExtra();
                }
           
            }
            
            List <ExtrasDetalleComanda> listaTemp = new ArrayList<>();
            
            for(int i = 0; i < listaExtrasSeleccion.size(); i++){
                if(!(listaExtrasSeleccion.get(i).getExtra().getTipoExtra().equalsIgnoreCase("Tipo leche") ||
                   listaExtrasSeleccion.get(i).getExtra().getTipoExtra().equalsIgnoreCase("Tipo vaso"))){
                    listaTemp.add(listaExtrasSeleccion.get(i));
                }
            }
            
            listaEx = agruparExtrasManualmente(listaTemp);
            
            
            cargarExtraVasosEditar();
            cargarExtraLechesEditar();
            cargarExtrasEditar();
            
            txtDescripcion.setText(FrmComandas.detalleComanda.getNotadetalleComanda());
            
            btnGuardarCambios.setText("Eliminar");
            btnGuardarCambios.setBackground(Color.red);
            
            calcularTotal();
            
    }
    
    
    public List<ExtraCantidadDTO> agruparExtrasManualmente(List<ExtrasDetalleComanda> listaExtrasUnidades) {
    
    // Usamos un mapa temporal para almacenar los DTOs agrupados (clave: Nombre del Extra)
    Map<String, ExtraCantidadDTO> mapaExtras = new HashMap<>();

    for (ExtrasDetalleComanda extraUnitario : listaExtrasUnidades) {
        String nombre = extraUnitario.getExtra().getNombreExtra();
        
        if (mapaExtras.containsKey(nombre)) {
            // Caso 1: El extra ya existe en el mapa (repetido)
            ExtraCantidadDTO dtoExistente = mapaExtras.get(nombre);
            
            // Sumar 1 a la cantidad existente
            dtoExistente.setCantidad(dtoExistente.getCantidad() + 1);
            
        } else {
            // Caso 2: El extra es nuevo, se crea el DTO y se añade al mapa
            // Se asume que getPrecio() es un método de la clase Extra
            ExtraCantidadDTO nuevoDto = new ExtraCantidadDTO(
                nombre, 
                extraUnitario.getExtra().getPrecio(), 
                1 // Cantidad inicial 1
            );
            mapaExtras.put(nombre, nuevoDto);
        }
    }
    
    // Convertir los valores del mapa (los DTOs agrupados) a una lista
    return new ArrayList<>(mapaExtras.values());
}
    
    
    
    
    private void cargarExtraVasos(){
        
        jPanelListaVasos.removeAll();
 
        try{
            
            List<Extra> listaProvisional = new ArrayList<>();
            if(!listaProvisional.isEmpty()){
                listaProvisional.clear();
            }
            
            for(int i = 0; i < listaExtras.size(); i++){
                if(listaExtras.get(i).getTipoExtra().equalsIgnoreCase("Tipo vaso")){
                    listaProvisional.add(listaExtras.get(i));
                }
            }
                 
            int alturaProducto = 55; 
                
            for(int i = 0; i < listaProvisional.size(); i++){    
                
                JPanelExtra extra = new JPanelExtra();
                
                extra.setBounds(0, alturaProducto * i, 260, 45);

                extra.setAlignmentX(Component.LEFT_ALIGNMENT);
                extra.setBackground(Color.decode("#FFFFFF"));

                extra.getJblNombreExtra().setText(listaProvisional.get(i).getNombreExtra());
                
                extra.getJblPrecioExtra().setText(String.valueOf(listaProvisional.get(i).getPrecio()));
                extra.getJblPrecioExtra().setLocation(extra.getJblNombreExtra().getBounds().x + 120, extra.getJblPrecioExtra().getBounds().y);

                int iProvisional = i;        

                extra.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        
                        if(extraVaso != null ){
                            if(extraVaso.getIdExtra().toString().equalsIgnoreCase(listaProvisional.get(iProvisional).getIdExtra().toString())){
                                extraVaso = null;
                                extra.setSelecionado(false);
                                System.out.println("vaso null");
                            }
                            else{
                                extraVaso = listaProvisional.get(iProvisional);
                                System.out.println("vaso no null");
                                extra.setSelecionado(true);
                            }
                        
                        }
                        
                        else{
                            extraVaso = listaProvisional.get(iProvisional);
                            extra.setSelecionado(true);
                            extra.setBackground(Color.decode("#E0E0E0"));
                        }

                        actualizarNota();
                        calcularTotal();
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


                 extra.getJblEliminarExtra().setCursor(new Cursor(1));
                 extra.getJblEliminarExtra().setVisible(false);

                      
               if(jPanelListaVasos.getPreferredSize().height < alturaProducto * i){
                    
                    if(dimension == null){
                        dimension = new Dimension();
                    }
                    
                    dimension.setSize(jPanelListaVasos.getPreferredSize().width, alturaProducto * i);
                    jPanelListaVasos.setPreferredSize(dimension);
               }
                    
                jPanelListaVasos.add(extra);
  
            }
        
        
            jPanelListaVasos.revalidate();
            jPanelListaVasos.repaint();
        }
        
        
        
        catch(NumberFormatException e){
        }
    
    
    }
    
    
    private void cargarExtraLeches(){

            jPanelListaLeches.removeAll();

            try{

                List<Extra> listaProvisional = new ArrayList<>();
                if(!listaProvisional.isEmpty()){
                    listaProvisional.clear();
                }

                for(int i = 0; i < listaExtras.size(); i++){
                    if(listaExtras.get(i).getTipoExtra().equalsIgnoreCase("Tipo leche")){
                        listaProvisional.add(listaExtras.get(i));
                    }
                }

                int alturaProducto = 55; 

                for(int i = 0; i < listaProvisional.size(); i++){    

                    JPanelExtra extra = new JPanelExtra();

                    extra.setBounds(0, alturaProducto * i, 260, 45);

                    extra.setAlignmentX(Component.LEFT_ALIGNMENT);
                    extra.setBackground(Color.decode("#FFFFFF"));

                    extra.getJblNombreExtra().setText(listaProvisional.get(i).getNombreExtra());

                    extra.getJblPrecioExtra().setText(String.valueOf(listaProvisional.get(i).getPrecio()));
                    extra.getJblPrecioExtra().setLocation(extra.getJblNombreExtra().getBounds().x + 120, extra.getJblPrecioExtra().getBounds().y);

                    int iProvisional = i;        

                    extra.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {

                            if(extraLeche != null ){
                                if(extraLeche.getIdExtra().toString().equalsIgnoreCase(listaProvisional.get(iProvisional).getIdExtra().toString())){
                                    extraLeche = null;
                                    extra.setSelecionado(false);
                                    System.out.println("vaso null");
                                }
                                else{
                                    extraLeche = listaProvisional.get(iProvisional);
                                    System.out.println("vaso no null");
                                    extra.setSelecionado(true);
                                }

                            }

                            else{
                                extraLeche = listaProvisional.get(iProvisional);
                                extra.setSelecionado(true);
                                extra.setBackground(Color.decode("#E0E0E0"));
                            }
                            
                            actualizarNota();
                            calcularTotal();

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


                     extra.getJblEliminarExtra().setCursor(new Cursor(1));
                     extra.getJblEliminarExtra().setVisible(false);


                   if(jPanelListaLeches.getPreferredSize().height < alturaProducto * i){

                        if(dimension == null){
                            dimension = new Dimension();
                        }

                        dimension.setSize(jPanelListaLeches.getPreferredSize().width, alturaProducto * i);
                        jPanelListaLeches.setPreferredSize(dimension);
                   }

                    jPanelListaLeches.add(extra);

                }


                jPanelListaLeches.revalidate();
                jPanelListaLeches.repaint();
            }



            catch(NumberFormatException e){
            }


        }


    private void cargarExtras(){

            jPanelListaExtras.removeAll();

            try{

                List<Extra> listaProvisional = new ArrayList<>();
                if(!listaProvisional.isEmpty()){
                    listaProvisional.clear();
                }

                for(int i = 0; i < listaExtras.size(); i++){
                    if(listaExtras.get(i).getTipoExtra().equalsIgnoreCase("Tipo extra")){
                        listaProvisional.add(listaExtras.get(i));
                    }
                }

                int alturaProducto = 55; 

                for(int i = 0; i < listaProvisional.size(); i++){    

                    JPanelExtra1 extra = new JPanelExtra1();

                    extra.setBounds(0, alturaProducto * i, 260, 45);

                    extra.setAlignmentX(Component.LEFT_ALIGNMENT);
                    extra.setBackground(Color.decode("#FFFFFF"));

                    extra.getJblNombreExtra().setText(listaProvisional.get(i).getNombreExtra());

                    extra.getJblPrecioExtra().setText(String.valueOf(listaProvisional.get(i).getPrecio()));
                    extra.getJblPrecioExtra().setLocation(extra.getJblNombreExtra().getBounds().x + 40, extra.getJblPrecioExtra().getBounds().y);
                    extra.getJblSpinnerCantidad().setLocation(180, 15);
                    
                    // Configurar el JSpinner
                    SpinnerModel model = new SpinnerNumberModel(0, 0, 100, 1);
                    extra.getJblSpinnerCantidad().setModel(model);
                    
                    final ExtraCantidadDTO extraCantidadDTO = new ExtraCantidadDTO(listaProvisional.get(i).getNombreExtra(), listaProvisional.get(i).getPrecio(), 0);
                    
                    
                    int iProvisional = i;        

                    extra.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {


                            actualizarNota();

                        }

                    });
                    
                    
                    extra.getJblSpinnerCantidad().addChangeListener(new ChangeListener() {
                        @Override
                        public void stateChanged(ChangeEvent e) {
                            int nuevaCantidad = (int) extra.getJblSpinnerCantidad().getValue();
                            actualizarItemEnComanda(extraCantidadDTO, nuevaCantidad);
                            
                            
                            
                            actualizarNota();
                            calcularTotal();

 
                        }
                        
                    });
                
                    
                    



                   if(jPanelListaExtras.getPreferredSize().height < alturaProducto * i){

                        if(dimension == null){
                            dimension = new Dimension();
                        }

                        dimension.setSize(jPanelListaExtras.getPreferredSize().width, alturaProducto * i);
                        jPanelListaExtras.setPreferredSize(dimension);
                   }

                    jPanelListaExtras.add(extra);

                }


                jPanelListaExtras.revalidate();
                jPanelListaExtras.repaint();
            }



            catch(NumberFormatException e){
            }


        }
    
    
    private void cargarExtraVasosEditar(){
        
        jPanelListaVasos.removeAll();
 
        try{
            
            List<Extra> listaProvisional = new ArrayList<>();
            if(!listaProvisional.isEmpty()){
                listaProvisional.clear();
            }
            
            for(int i = 0; i < listaExtras.size(); i++){
                if(listaExtras.get(i).getTipoExtra().equalsIgnoreCase("Tipo vaso")){
                    listaProvisional.add(listaExtras.get(i));
                }
            }
                 
            int alturaProducto = 55; 
                
            for(int i = 0; i < listaProvisional.size(); i++){    
                
                JPanelExtra extra = new JPanelExtra();
                
                extra.setBounds(0, alturaProducto * i, 260, 45);

                extra.setAlignmentX(Component.LEFT_ALIGNMENT);
                extra.setBackground(Color.decode("#FFFFFF"));

                extra.getJblNombreExtra().setText(listaProvisional.get(i).getNombreExtra());
                
                extra.getJblPrecioExtra().setText(String.valueOf(listaProvisional.get(i).getPrecio()));
                extra.getJblPrecioExtra().setLocation(extra.getJblNombreExtra().getBounds().x + 120, extra.getJblPrecioExtra().getBounds().y);

                int iProvisional = i;        

                
                if(extraVaso != null){
                    extra.setBackground(Color.decode("#E0E0E0"));
                }
                
                
                if(!(accion == ConstantesGUI.ELIMINAR)){
                    extra.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {

                            if(extraVaso != null ){
                                if(extraVaso.getIdExtra().toString().equalsIgnoreCase(listaProvisional.get(iProvisional).getIdExtra().toString())){
                                    extraVaso = null;
                                    extra.setSelecionado(false);
                                    System.out.println("vaso null");
                                }
                                else{
                                    extraVaso = listaProvisional.get(iProvisional);
                                    System.out.println("vaso no null");
                                    extra.setSelecionado(true);
                                }

                            }

                            else{
                                extraVaso = listaProvisional.get(iProvisional);
                                extra.setSelecionado(true);
                                extra.setBackground(Color.decode("#E0E0E0"));
                            }

                            actualizarNota();
                            calcularTotal();
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


                 extra.getJblEliminarExtra().setCursor(new Cursor(1));
                 extra.getJblEliminarExtra().setVisible(false);

                      
               if(jPanelListaVasos.getPreferredSize().height < alturaProducto * i){
                    
                    if(dimension == null){
                        dimension = new Dimension();
                    }
                    
                    dimension.setSize(jPanelListaVasos.getPreferredSize().width, alturaProducto * i);
                    jPanelListaVasos.setPreferredSize(dimension);
               }
                    
                jPanelListaVasos.add(extra);
  
            }
        
        
            jPanelListaVasos.revalidate();
            jPanelListaVasos.repaint();
        }
        
        
        
        catch(NumberFormatException e){
        }
    
    
    }
    
    
    private void cargarExtraLechesEditar(){

            jPanelListaLeches.removeAll();

            try{

                List<Extra> listaProvisional = new ArrayList<>();
                if(!listaProvisional.isEmpty()){
                    listaProvisional.clear();
                }

                for(int i = 0; i < listaExtras.size(); i++){
                    if(listaExtras.get(i).getTipoExtra().equalsIgnoreCase("Tipo leche")){
                        listaProvisional.add(listaExtras.get(i));
                    }
                }

                int alturaProducto = 55; 

                for(int i = 0; i < listaProvisional.size(); i++){    

                    JPanelExtra extra = new JPanelExtra();

                    extra.setBounds(0, alturaProducto * i, 260, 45);

                    extra.setAlignmentX(Component.LEFT_ALIGNMENT);
                    extra.setBackground(Color.decode("#FFFFFF"));

                    extra.getJblNombreExtra().setText(listaProvisional.get(i).getNombreExtra());

                    extra.getJblPrecioExtra().setText(String.valueOf(listaProvisional.get(i).getPrecio()));
                    extra.getJblPrecioExtra().setLocation(extra.getJblNombreExtra().getBounds().x + 120, extra.getJblPrecioExtra().getBounds().y);

                    int iProvisional = i;   
                    
                    if(extraLeche != null){
                        extra.setBackground(Color.decode("#E0E0E0"));
                    }
                    
                    
                    if(!(accion == ConstantesGUI.ELIMINAR)){
                        extra.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {

                                if(extraLeche != null ){
                                    if(extraLeche.getIdExtra().toString().equalsIgnoreCase(listaProvisional.get(iProvisional).getIdExtra().toString())){
                                        extraLeche = null;
                                        extra.setSelecionado(false);
                                        System.out.println("vaso null");
                                    }
                                    else{
                                        extraLeche = listaProvisional.get(iProvisional);
                                        System.out.println("vaso no null");
                                        extra.setSelecionado(true);
                                    }

                                }

                                else{
                                    extraLeche = listaProvisional.get(iProvisional);
                                    extra.setSelecionado(true);
                                    extra.setBackground(Color.decode("#E0E0E0"));
                                }

                                actualizarNota();
                                calcularTotal();

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


                     extra.getJblEliminarExtra().setCursor(new Cursor(1));
                     extra.getJblEliminarExtra().setVisible(false);


                   if(jPanelListaLeches.getPreferredSize().height < alturaProducto * i){

                        if(dimension == null){
                            dimension = new Dimension();
                        }

                        dimension.setSize(jPanelListaLeches.getPreferredSize().width, alturaProducto * i);
                        jPanelListaLeches.setPreferredSize(dimension);
                   }

                    jPanelListaLeches.add(extra);

                }


                jPanelListaLeches.revalidate();
                jPanelListaLeches.repaint();
            }



            catch(NumberFormatException e){
            }


        }

    
    private void cargarExtrasEditar(){

            jPanelListaExtras.removeAll();

            try{

                List<Extra> listaProvisional = new ArrayList<>();
                if(!listaProvisional.isEmpty()){
                    listaProvisional.clear();
                }

                for(int i = 0; i < listaExtras.size(); i++){
                    if(listaExtras.get(i).getTipoExtra().equalsIgnoreCase("Tipo extra")){
                        listaProvisional.add(listaExtras.get(i));
                    }
                }

                int alturaProducto = 55; 

                for(int i = 0; i < listaProvisional.size(); i++){    

                    JPanelExtra1 extra = new JPanelExtra1();

                    extra.setBounds(0, alturaProducto * i, 260, 45);

                    extra.setAlignmentX(Component.LEFT_ALIGNMENT);
                    extra.setBackground(Color.decode("#FFFFFF"));

                    extra.getJblNombreExtra().setText(listaProvisional.get(i).getNombreExtra());

                    extra.getJblPrecioExtra().setText(String.valueOf(listaProvisional.get(i).getPrecio()));
                    extra.getJblPrecioExtra().setLocation(extra.getJblNombreExtra().getBounds().x + 40, extra.getJblPrecioExtra().getBounds().y);
                    extra.getJblSpinnerCantidad().setLocation(180, 15);
                    
                    // Configurar el JSpinner
                    SpinnerModel model = new SpinnerNumberModel(0, 0, 100, 1);
                    extra.getJblSpinnerCantidad().setModel(model);
                    
                    
                    int cantidadActual = 0;
                    for(int j = 0; j < listaEx.size(); j++){
                        if(listaEx.get(j).getNombre().equalsIgnoreCase(extra.getJblNombreExtra().getText())){
                            cantidadActual = listaEx.get(j).getCantidad();
                            extra.getJblSpinnerCantidad().setValue(cantidadActual);
                        }
                        
                    }
                    
                    
                    final ExtraCantidadDTO extraCantidadDTO = new ExtraCantidadDTO(listaProvisional.get(i).getNombreExtra(), listaProvisional.get(i).getPrecio(), cantidadActual);

                    
                    int iProvisional = i;   
                    
                    if(accion == ConstantesGUI.ELIMINAR){
                        extra.getJblSpinnerCantidad().setEnabled(false);
                    }

                    if(accion != ConstantesGUI.ELIMINAR){
                        extra.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {


                                actualizarNota();

                            }

                        });


                        extra.getJblSpinnerCantidad().addChangeListener(new ChangeListener() {
                            @Override
                            public void stateChanged(ChangeEvent e) {
                                int nuevaCantidad = (int) extra.getJblSpinnerCantidad().getValue();
                                actualizarItemEnComanda(extraCantidadDTO, nuevaCantidad);

                                actualizarNota();
                                calcularTotal();


                            }

                        });
                
                    }
                    
                    



                   if(jPanelListaExtras.getPreferredSize().height < alturaProducto * i){

                        if(dimension == null){
                            dimension = new Dimension();
                        }

                        dimension.setSize(jPanelListaExtras.getPreferredSize().width, alturaProducto * i);
                        jPanelListaExtras.setPreferredSize(dimension);
                   }

                    jPanelListaExtras.add(extra);

                }


                jPanelListaExtras.revalidate();
                jPanelListaExtras.repaint();
            }



            catch(NumberFormatException e){
            }


        }
    
    
    private void actualizarItemEnComanda(ExtraCantidadDTO extraCantidadDTO, int nuevaCantidad) {
        String nombreBuscado = extraCantidadDTO.getNombre();
        boolean encontrado = false;
        boolean necesitaRecarga = false; // <-- Nueva Bandera

        // 1. Recorrer la lista para encontrar una coincidencia
        // (Usar un iterador es más seguro al eliminar dentro del bucle)
        Iterator<ExtraCantidadDTO> iter = listaEx.iterator();
        while (iter.hasNext()) {
            ExtraCantidadDTO itemExistente = iter.next();
            if (itemExistente.getNombre().equalsIgnoreCase(nombreBuscado)) {
                encontrado = true;
                itemExistente.setCantidad(nuevaCantidad);

                // 2. ELIMINAR si la cantidad es 0
                if (nuevaCantidad == 0) {
                    iter.remove(); // Elimina de forma segura usando el iterador
                    necesitaRecarga = true; // Necesitamos recargar la GUI (eliminamos un componente)
                }
                break; 
            }
        }

        // 3. AÑADIR si no se encontró y la cantidad es > 0
        if (!encontrado && nuevaCantidad > 0) {
            extraCantidadDTO.setCantidad(nuevaCantidad); 
            listaEx.add(extraCantidadDTO);
            necesitaRecarga = true; // Necesitamos recargar la GUI (añadimos un componente)
        }

        // 4. Recargar la GUI SOLO si la estructura de la lista cambió
//        if (necesitaRecarga) {
//            if(accion == ConstantesGUI.NUEVO){
//                cargarExtras();
//            } else {
//                cargarExtrasEditar();
//            }
//        }

        // 5. Recalcular y actualizar notas SIEMPRE
        actualizarNota();
        calcularTotal();
    }
    
    
    private void eliminarItemDeComanda(JSpinner spiner, ExtraCantidadDTO extraCantidadDTO) {
        // Elimina el ítem de la lista global y resetea el spinner local
        listaEx.remove(extraCantidadDTO);
        spiner.setValue(0);
    }
    
    
    private void calcularTotal(){
        
        
        float precioVaso = 0;
        float precioLeche = 0;
        float precioExtra = 0;
        
        if(extraVaso != null ){
            precioVaso = extraVaso.getPrecio();
            txtPrecioProductoResumen.setText(String.valueOf(producto.getPrecioProducto() + precioVaso));
        }
        
        if(extraLeche != null){
            precioLeche = extraLeche.getPrecio();
            txtLeche.setText(extraLeche.getNombreExtra());
            txtPrecioLeche.setText(String.valueOf(precioLeche));
        }
        
        if(!listaEx.isEmpty()){
            for(int i = 0; i < listaEx.size(); i++){
                if(listaEx.get(i).getCantidad() > 0){
                    precioExtra = precioExtra + (listaEx.get(i).getCantidad() * listaEx.get(i).getPrecioUnitario());
                }
            }
        }
        
        System.out.println(listaEx.size());
        txtPrecioExtra.setText(String.valueOf(precioExtra));
        
        txtPrecioProductoResumen.setText(String.valueOf(producto.getPrecioProducto()));
        txtTotal.setText(String.valueOf(precioVaso + precioLeche + producto.getPrecioProducto() + precioExtra));
        txtSubtotal.setText(txtTotal.getText());
        
        
    }
    
    
    
    
    
    
    
    private void actualizarNota(){
        
        String nota;
        StringBuilder sb = new StringBuilder();
        
        // Tipo de Vaso
        if (extraVaso != null) {
            sb.append("Vaso: ").append(extraVaso.getNombreExtra()).append(". ");
        }
        
        
        // Tipo de leche
        if (extraLeche != null) {
            sb.append("Leche: ").append(extraLeche.getNombreExtra()).append(". ");
        }
        
        
        
        if (!listaEx.isEmpty()) {
            for(int i = 0; i < listaEx.size(); i++){
                if(listaEx.get(i).getCantidad() > 0){
                    sb.append(listaEx.get(i).getNombre()).append(" x").append(listaEx.get(i).getCantidad()).append(". ");
                }
            }
        }
//            for (Map.Entry<Valoropcion, Integer> entry : extrasSeleccionados.entrySet()) {
//                Valoropcion extra = entry.getKey();
//                int cantidad = entry.getValue();
//
//                if (cantidad > 0) {
//                    sb.append(extra.getNombreValor())
//                      .append(" x").append(cantidad)
//                      .append(". ");
//
//                    extras += cantidad;
//                }
//            }
//        }
        

       // sb.append("Cantidad de extras: ").append(extras).append(". ");

        nota = sb.toString();
        txtDescripcion.setText(nota);
        
        
    }
    
        
        
    private void volverSeccionAnterior(ConstantesGUI acci){
        
        FrmComandas.jPanelSeccion.removeAll();
        
        JPanelComandasListaProductos panelListaProductos = new JPanelComandasListaProductos(acci);
        
        panelListaProductos.setBounds(0, 0, FrmComandas.jPanelSeccion.getWidth(), FrmComandas.jPanelSeccion.getHeight());
        FrmComandas.jPanelSeccion.add(panelListaProductos);

        FrmComandas.jPanelSeccion.revalidate();
        FrmComandas.jPanelSeccion.repaint();
        
    
    }    
    
    
    private void generarNuevaComanda(){
        try{
            FrmComandas.comanda = new Comanda();
            FrmComandas.comanda.setEstadoComanda("Abierta");
            FrmComandas.comanda.setFechaHoracomanda(new Date());
            //comanda.setIdUsuario(idUsuario);
            FrmComandas.comanda.setTotalComanda(Float.valueOf(txtTotal.getText()));
            
            FrmComandas.comanda = FComandas.GuardarComanda(FrmComandas.comanda);
            System.out.println(FrmComandas.comanda);
            
        }
        catch(Exception e){
        
        }
        
    }
    
    
    private void guardarDetalleComanda(){
        
        
        if(FrmComandas.comanda == null){
            generarNuevaComanda();
        }
        
        
        try{
        Detallecomanda detalle = new Detallecomanda();
        detalle.setCaintidaddetalleComanda(1);
        detalle.setIdComanda(FrmComandas.comanda);
        detalle.setIdProducto(producto);
        detalle.setNotadetalleComanda(txtDescripcion.getText());
        detalle.setSubTotaldetalleComanda(Float.valueOf(txtTotal.getText()));
        
        Detallecomanda nuevaComanda = fDC.agregarDetallesComandas(detalle);
        FrmComandas.detalleComanda = nuevaComanda;
        
        List <Detallecomanda> ll = new ArrayList<>();
        
        if(FrmComandas.comanda.getDetallecomandaList() == null || FrmComandas.comanda.getDetallecomandaList().isEmpty() ){
        
            ll.add(detalle);
        }
        else{
            ll = FrmComandas.comanda.getDetallecomandaList();
            ll.add(detalle);
        }
        
        FrmComandas.comanda.setDetallecomandaList(ll);
        
        FComandas.EditarComanda(FrmComandas.comanda);
        
        List <ExtrasDetalleComanda> e = new ArrayList<>();
        
        
        // aqui se guardan los extras
        if(!listaEx.isEmpty() && !listaExtras.isEmpty()){
            
            for (int i = 0; i < listaEx.size(); i++) {
                // 1. Obtener el extra agrupado actual
                ExtraCantidadDTO extraAgrupado = listaEx.get(i);
                String nombreExtraAgrupado = extraAgrupado.getNombre();

                // 2. Condición: Si NO es "Tipo vaso" Y NO es "Tipo leche"
                if (!(nombreExtraAgrupado.equalsIgnoreCase("Tipo vaso") ||
                      nombreExtraAgrupado.equalsIgnoreCase("Tipo leche"))) {

                    // 3. Bucle de Cantidad: Iterar N veces, donde N es la cantidad del extra
                    // Usamos la cantidad del ítem actual (extraAgrupado.getCantidad())
                    for (int j = 0; j < extraAgrupado.getCantidad(); j++) {

                        // 4. Búsqueda: Iterar la lista maestra de Extras (listaExtras)
                        for (int k = 0; k < listaExtras.size(); k++) {

                            // 5. Comparación: Si el nombre del extra maestro coincide con el nombre del extra agrupado
                            if (listaExtras.get(k).getNombreExtra().equalsIgnoreCase(nombreExtraAgrupado)) {

                                // 6. Persistencia
                                ExtrasDetalleComanda es = new ExtrasDetalleComanda();
                                es.setDetalleComanda(nuevaComanda);
                                es.setExtra(listaExtras.get(k)); // El objeto Extra real

                                // Guardar la entidad (una por cada unidad)
                                fExtraDetalleComanda.guardarExtraDetalleComanda(es);

                                // 7. Optimización: Una vez que encontramos la coincidencia, podemos romper el bucle 'k'
                                break; 
                            }
                        }
                    }
                }
            }

        }
        
        
        if(extraLeche != null){
            ExtrasDetalleComanda es = new ExtrasDetalleComanda();
            es.setDetalleComanda(nuevaComanda);
            es.setExtra(extraLeche);
            fExtraDetalleComanda.guardarExtraDetalleComanda(es);
        }
        
        if(extraVaso != null){
            ExtrasDetalleComanda es = new ExtrasDetalleComanda();
            es.setDetalleComanda(nuevaComanda);
            es.setExtra(extraVaso);
            fExtraDetalleComanda.guardarExtraDetalleComanda(es);
        }
        
        
            JOptionPane.showMessageDialog(this, "Producto agregado a la comanda");
            
            volverSeccionAnterior(ConstantesGUI.EDITAR);
        
        
        
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
    
    
    private void editarDetalleComanda(){
        
        
        try{
            
        FrmComandas.detalleComanda.setCaintidaddetalleComanda(1);
        FrmComandas.detalleComanda.setIdComanda(FrmComandas.comanda);
        FrmComandas.detalleComanda.setIdProducto(producto);
        FrmComandas.detalleComanda.setNotadetalleComanda(txtDescripcion.getText());
        FrmComandas.detalleComanda.setSubTotaldetalleComanda(Float.valueOf(txtSubtotal.getText()));
        
        List <Detallecomanda> listeTemp = fDC.obtenerDetallesComandasPorComanda(FrmComandas.comanda);
                float precioTotal = 0;
                for(int i = 0; i < listeTemp.size(); i++){
                    precioTotal = precioTotal + listeTemp.get(i).getSubTotaldetalleComanda();
                }
                                
                float iva = precioTotal * 0.16f;
                
        FrmComandas.comanda.setTotalComanda(precioTotal + iva);
                
        
        Detallecomanda nuevaComanda = fDC.editarDetallesComandas(FrmComandas.detalleComanda);
        
        List <Detallecomanda> ll = new ArrayList<>();
        
        if(FrmComandas.comanda.getDetallecomandaList() == null || FrmComandas.comanda.getDetallecomandaList().isEmpty() ){
        
            ll.add(FrmComandas.detalleComanda);
        }
        else{
            ll = FrmComandas.comanda.getDetallecomandaList();
            ll.add(FrmComandas.detalleComanda);
        }
        
        FrmComandas.comanda.setDetallecomandaList(ll);
        
        FComandas.EditarComanda(FrmComandas.comanda);
        
        List <ExtrasDetalleComanda> e = new ArrayList<>();
        
        
        
        if(listaEx.isEmpty()){
            fExtraDetalleComanda.eliminarExtraDetalleComanda(nuevaComanda);
        }
        
        
        // aqui se guardan los extras
        if(!listaEx.isEmpty() && !listaExtras.isEmpty()){
            
            fExtraDetalleComanda.eliminarExtraDetalleComanda(FrmComandas.detalleComanda);
            
            List<Extra> extTemp = fExtra.obtenerTodosLosExtras();
                       
            
            for (int i = 0; i < listaEx.size(); i++) {
                // 1. Obtener el extra agrupado actual
                ExtraCantidadDTO extraAgrupado = listaEx.get(i);
                String nombreExtraAgrupado = extraAgrupado.getNombre();

                // 2. Condición: Si NO es "Tipo vaso" Y NO es "Tipo leche"
                if (!(nombreExtraAgrupado.equalsIgnoreCase("Tipo vaso") ||
                      nombreExtraAgrupado.equalsIgnoreCase("Tipo leche"))) {

                    // 3. Bucle de Cantidad: Iterar N veces, donde N es la cantidad del extra
                    // Usamos la cantidad del ítem actual (extraAgrupado.getCantidad())
                    for (int j = 0; j < extraAgrupado.getCantidad(); j++) {

                        // 4. Búsqueda: Iterar la lista maestra de Extras (listaExtras)
                        for (int k = 0; k < listaExtras.size(); k++) {

                            // 5. Comparación: Si el nombre del extra maestro coincide con el nombre del extra agrupado
                            if (listaExtras.get(k).getNombreExtra().equalsIgnoreCase(nombreExtraAgrupado)) {

                                // 6. Persistencia
                                ExtrasDetalleComanda es = new ExtrasDetalleComanda();
                                es.setDetalleComanda(nuevaComanda);
                                es.setExtra(listaExtras.get(k)); // El objeto Extra real

                                // Guardar la entidad (una por cada unidad)
                                fExtraDetalleComanda.guardarExtraDetalleComanda(es);

                                // 7. Optimización: Una vez que encontramos la coincidencia, podemos romper el bucle 'k'
                                break; 
                            }
                        }
                    }
                }
            }
        }
        
        
        if(extraLeche != null){
            ExtrasDetalleComanda es = new ExtrasDetalleComanda();
            es.setDetalleComanda(nuevaComanda);
            es.setExtra(extraLeche);
            fExtraDetalleComanda.guardarExtraDetalleComanda(es);
        }
        
        if(extraVaso != null){
            ExtrasDetalleComanda es = new ExtrasDetalleComanda();
            es.setDetalleComanda(nuevaComanda);
            es.setExtra(extraVaso);
            fExtraDetalleComanda.guardarExtraDetalleComanda(es);
        }
        
        
            JOptionPane.showMessageDialog(this, "Detalle del producto editado");
            
            volverSeccionAnterior(ConstantesGUI.EDITAR);
        
        
        
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
    
    
    
    private void eliminarDetalleComanda(){
        try{
            
            int opcion = JOptionPane.showConfirmDialog(
            null, // Componente padre: null si es una ventana independiente
            "¿Estás seguro que deseas eliminar este elemento?", // Mensaje
            "Confirmar Eliminación", // Título de la ventana
            JOptionPane.YES_NO_OPTION, // Tipo de opciones (Sí/No)
            JOptionPane.QUESTION_MESSAGE // Icono (Signo de interrogación)
        );

        if (opcion == JOptionPane.YES_OPTION) {
            
                fExtraDetalleComanda.eliminarExtraDetalleComanda(FrmComandas.detalleComanda);

                fDC.eliminarDetallesComandas(FrmComandas.detalleComanda.getIdDetalleComanda());
                FrmComandas.detalleComanda = null;

                System.out.println("Elemento eliminado exitosamente.");
                JOptionPane.showMessageDialog(null, "El elemento ha sido eliminado.");
                
                
                if (FrmComandas.comanda != null) {
                FrmComandas.comanda = FComandas.obtenerComanda(FrmComandas.comanda.getIdComanda()); // Reemplaza fComanda por tu DAO/Service de Comanda
                }
                
                List<Detallecomanda> listTemp = fDC.obtenerDetallesComandasPorComanda(FrmComandas.comanda);
                if(listTemp == null || listTemp.isEmpty()){
                    volverSeccionAnterior(ConstantesGUI.NUEVO);
                }
                else{
                    volverSeccionAnterior(ConstantesGUI.EDITAR);
                }
                                
            
        }
        else if (opcion == JOptionPane.NO_OPTION) {

            
        } 
        
        else if (opcion == JOptionPane.CLOSED_OPTION) {
            
        }
            
            
            
            
        }
        
        catch(Exception e){
            
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

        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        txtPrecioBase = new javax.swing.JLabel();
        jPanelExtras = new javax.swing.JPanel();
        jPanelListaExtras = new javax.swing.JPanel();
        jPanelListaVasos = new javax.swing.JPanel();
        jPanelListaLeches = new javax.swing.JPanel();
        jblLeches = new javax.swing.JLabel();
        jblExtras = new javax.swing.JLabel();
        jblVaso = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jPanelPromociones = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
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

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMinimumSize(new java.awt.Dimension(1210, 560));
        setPreferredSize(new java.awt.Dimension(1210, 560));
        setLayout(null);

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

        txtPrecioBase.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        txtPrecioBase.setForeground(new java.awt.Color(17, 24, 39));
        txtPrecioBase.setText("Precio");
        jPanel4.add(txtPrecioBase);
        txtPrecioBase.setBounds(20, 90, 210, 30);

        jPanelExtras.setBackground(new java.awt.Color(255, 255, 255));
        jPanelExtras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelExtras.setLayout(null);

        jPanelListaExtras.setBackground(new java.awt.Color(255, 255, 255));
        jPanelListaExtras.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelListaExtras.setMinimumSize(new java.awt.Dimension(300, 20));
        jPanelListaExtras.setPreferredSize(new java.awt.Dimension(490, 380));
        jPanelListaExtras.setLayout(null);
        jPanelExtras.add(jPanelListaExtras);
        jPanelListaExtras.setBounds(580, 50, 260, 339);

        jPanelListaVasos.setBackground(new java.awt.Color(255, 255, 255));
        jPanelListaVasos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelListaVasos.setMinimumSize(new java.awt.Dimension(300, 20));
        jPanelListaVasos.setPreferredSize(new java.awt.Dimension(490, 380));
        jPanelListaVasos.setLayout(null);
        jPanelExtras.add(jPanelListaVasos);
        jPanelListaVasos.setBounds(10, 50, 260, 339);

        jPanelListaLeches.setBackground(new java.awt.Color(255, 255, 255));
        jPanelListaLeches.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelListaLeches.setMinimumSize(new java.awt.Dimension(300, 20));
        jPanelListaLeches.setPreferredSize(new java.awt.Dimension(490, 380));
        jPanelListaLeches.setLayout(null);
        jPanelExtras.add(jPanelListaLeches);
        jPanelListaLeches.setBounds(295, 50, 260, 339);

        jblLeches.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jblLeches.setForeground(new java.awt.Color(17, 24, 39));
        jblLeches.setText("Tipo de leche");
        jPanelExtras.add(jblLeches);
        jblLeches.setBounds(295, 20, 130, 20);

        jblExtras.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jblExtras.setForeground(new java.awt.Color(17, 24, 39));
        jblExtras.setText("Extras");
        jPanelExtras.add(jblExtras);
        jblExtras.setBounds(580, 20, 80, 20);

        jblVaso.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        jblVaso.setForeground(new java.awt.Color(17, 24, 39));
        jblVaso.setText("Vaso");
        jPanelExtras.add(jblVaso);
        jblVaso.setBounds(10, 20, 110, 20);

        jPanel4.add(jPanelExtras);
        jPanelExtras.setBounds(10, 130, 850, 400);
        jPanel4.add(txtDescripcion);
        txtDescripcion.setBounds(10, 50, 850, 40);

        add(jPanel4);
        jPanel4.setBounds(10, 10, 880, 540);

        jPanelPromociones.setBackground(new java.awt.Color(255, 255, 255));
        jPanelPromociones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(229, 231, 225)));
        jPanelPromociones.setLayout(null);

        jLabel8.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(17, 24, 39));
        jLabel8.setText("Promociones");
        jLabel8.setAlignmentX(16.0F);
        jLabel8.setAlignmentY(0.0F);
        jPanelPromociones.add(jLabel8);
        jLabel8.setBounds(90, 10, 120, 24);

        lblDescripcion.setText("Sin promociones.");
        jPanelPromociones.add(lblDescripcion);
        lblDescripcion.setBounds(20, 40, 250, 60);

        add(jPanelPromociones);
        jPanelPromociones.setBounds(910, 10, 280, 110);

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

        add(jPanel12);
        jPanel12.setBounds(910, 140, 280, 410);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreProductoActionPerformed

    private void btnGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCambiosActionPerformed

        if(accion == ConstantesGUI.NUEVO){
            guardarDetalleComanda();
        }
        
        if(accion == ConstantesGUI.EDITAR){
            editarDetalleComanda();
        }
        
        if(accion == ConstantesGUI.ELIMINAR){
            eliminarDetalleComanda();
        }
        
        

    }//GEN-LAST:event_btnGuardarCambiosActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        volverSeccionAnterior(accion);
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardarCambios;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelExtras;
    private javax.swing.JPanel jPanelListaExtras;
    private javax.swing.JPanel jPanelListaLeches;
    private javax.swing.JPanel jPanelListaVasos;
    private javax.swing.JPanel jPanelPromociones;
    private javax.swing.JLabel jblExtras;
    private javax.swing.JLabel jblLeches;
    private javax.swing.JLabel jblVaso;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JTextField txtDescripcion;
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
