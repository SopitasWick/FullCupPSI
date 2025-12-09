/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Formularios.paneles;

import Entidades.Comanda;
import Entidades.Extra;
import Facades.IFachadaComandasControlador;
import Formularios.FrmComandas;
import Formularios.paneles.elementos.JPanelComandas;
import Formularios.paneles.elementos.JPanelExtra;
import Implementaciones.GestionarComandaControlador;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilerias.ConstantesGUI;

/**
 *
 * @author Sergio Arturo
 */
public class JPanelComandasGeneral extends javax.swing.JPanel {

    
    private final IFachadaComandasControlador FComandas = new GestionarComandaControlador();

    
    
    
    Dimension dimensionComActivas = null;
    
    JPanelComandasListaProductos panelListaProductos;
    
    List <Comanda> listaComandas;
    
    
    /**
     * Creates new form JPanelComandas
     */
    public JPanelComandasGeneral() {
        initComponents();
        
        
        cargarDiseno();
        
        cargarDatos();
        
        
       // pruebas();
        
    }
    
    
    private void cargarDiseno(){
        jScrollPaneComandasActivas.setOpaque(false);
        jScrollPaneComandasActivas.setBorder(null);
        
        jPanelListaComandasActivas.setBorder(null);
    }
    
    
    
    private void cargarDatos(){
        try {
            
            listaComandas = FComandas.obtenerComandasActivas();
            System.out.println(listaComandas.size());
            
            cargarComandasActivas();
        }
        catch (Exception ex) {
            Logger.getLogger(JPanelComandasGeneral.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    private void pruebas(){
        
        
        
        
        
        
        
        
        List <Comanda> listaProvisional = new ArrayList<>();
        
        
        int alturaProducto = 110; 
                
            for(int i = 0; i < 10; i++){
                
                
                Comanda com1 = new Comanda();
                com1.setFechaHoracomanda(new Date());
                com1.setTotalComanda(20.00f);
                
                listaProvisional.add(com1);
                
                
                
                JPanelComandas panelComanda = new JPanelComandas();
                
                panelComanda.setBounds(0, alturaProducto * i, 350, 90);

                panelComanda.setAlignmentX(Component.LEFT_ALIGNMENT);
                panelComanda.setBackground(Color.decode("#FFFFFF"));



                panelComanda.getJblNombreComanda().setText("Comanda: " + i + " - " + listaProvisional.get(i).getFechaHoracomanda());
                panelComanda.getJblNumProductos().setText("2 Productos");
                panelComanda.getJblPrecioProducto().setText("$ " + listaProvisional.get(i).getTotalComanda());

                int iProvisional = i;        

                panelComanda.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        


                    }

                    @Override
                    public void mouseEntered(MouseEvent e){
                        panelComanda.setBackground(Color.decode("#E0E0E0"));

                    }

                    @Override
                    public void mouseExited(MouseEvent e){
                        panelComanda.setBackground(Color.decode("#FFFFFF"));
                    }

                });


                panelComanda.getJblEliminarComanda().addMouseListener(new MouseAdapter(){

                    @Override
                    public void mouseClicked(MouseEvent e){


                    }

                });

            jPanelListaComandasActivas.add(panelComanda);
               
            int numeroExtras = listaProvisional.size();
             
             if(numeroExtras > 0){
                 int nuevaAltura = alturaProducto * numeroExtras;
                 
                 if(dimensionComActivas == null){
                     dimensionComActivas = new Dimension();
                 }
                 
                 int anchoActual = jPanelListaComandasActivas.getPreferredSize().width;
                 
                 dimensionComActivas.setSize(anchoActual, nuevaAltura);
                 jPanelListaComandasActivas.setPreferredSize(dimensionComActivas);
                 
             }
             
             else{
                 jPanelListaComandasActivas.setPreferredSize(new Dimension(jPanelListaComandasActivas.getPreferredSize().width,90));
             }

                
            }
        
        
            jPanelListaComandasActivas.revalidate();
            jPanelListaComandasActivas.repaint();
        
        
        
        
        
    
    
    }
    
    
    
    private void cargarComandasActivas(){
        
        jPanelComandasCompletadasContenedor.removeAll();

        
        try{
            
            List<Comanda> listaProvisional = new ArrayList<>();
            if(!listaProvisional.isEmpty()){
                listaProvisional.clear();
            }

            listaProvisional = listaComandas;
            
            int alturaProducto = 95; 
                
            for(int i = 0; i < listaProvisional.size(); i++){    
                
                JPanelComandas panelComanda = new JPanelComandas();
                
                panelComanda.setBounds(0, alturaProducto * i, 350, 80);

                panelComanda.setAlignmentX(Component.LEFT_ALIGNMENT);
                panelComanda.setBackground(Color.decode("#FFFFFF"));



                panelComanda.getJblNombreComanda().setText("Comanda: " + i + " - " + listaProvisional.get(i).getFechaHoracomanda());
                panelComanda.getJblNumProductos().setText("Productos: " + String.valueOf(listaProvisional.get(i).getDetallecomandaList().size()));
                panelComanda.getJblPrecioProducto().setText("$ " + listaProvisional.get(i).getTotalComanda());

                int iProvisional = i;
                Comanda co = listaProvisional.get(i);

                panelComanda.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        
                        FrmComandas.comanda = co;
                        panelListaProductos = new JPanelComandasListaProductos(ConstantesGUI.EDITAR);
                        
                        FrmComandas.jPanelSeccion.removeAll();
        

                        panelListaProductos.setBounds(0, 0, FrmComandas.jPanelSeccion.getWidth(), FrmComandas.jPanelSeccion.getHeight());
                        FrmComandas.jPanelSeccion.add(panelListaProductos);

                        FrmComandas.jPanelSeccion.revalidate();
                        FrmComandas.jPanelSeccion.repaint();


                    }

                    @Override
                    public void mouseEntered(MouseEvent e){
                        panelComanda.setBackground(Color.decode("#E0E0E0"));

                    }

                    @Override
                    public void mouseExited(MouseEvent e){
                        panelComanda.setBackground(Color.decode("#FFFFFF"));
                    }

                });


                panelComanda.getJblEliminarComanda().addMouseListener(new MouseAdapter(){

                    @Override
                    public void mouseClicked(MouseEvent e){


                    }

                });
                
                
               // panelComanda.setRadiusPanelVerde();
                

              
               
               if(jPanelListaComandasActivas.getPreferredSize().height < alturaProducto * i){
                    
                    if(dimensionComActivas == null){
                        dimensionComActivas = new Dimension();
                    }
                    
                    dimensionComActivas.setSize(jPanelListaComandasActivas.getPreferredSize().width, alturaProducto * i);
                    jPanelListaComandasActivas.setPreferredSize(dimensionComActivas);
               }
                    
                jPanelListaComandasActivas.add(panelComanda);

            

                
            }
        
        
            jPanelListaComandasActivas.revalidate();
            jPanelListaComandasActivas.repaint();
        }
        
        
        
        catch(NumberFormatException e){
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

        jPanelComandasCompletadasContenedor = new javax.swing.JPanel();
        jblComandasCompletadas = new javax.swing.JLabel();
        jPanelListaComandasCompletadas = new javax.swing.JPanel();
        jPanelComandasActivasContenedor = new javax.swing.JPanel();
        jblComandasActivas = new javax.swing.JLabel();
        btnNuevaComanda = new javax.swing.JButton();
        jScrollPaneComandasActivas = new javax.swing.JScrollPane();
        jPanelListaComandasActivas = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setMinimumSize(new java.awt.Dimension(1210, 560));
        setPreferredSize(new java.awt.Dimension(1210, 560));
        setLayout(null);

        jPanelComandasCompletadasContenedor.setBackground(new java.awt.Color(255, 255, 255));
        jPanelComandasCompletadasContenedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelComandasCompletadasContenedor.setLayout(null);

        jblComandasCompletadas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jblComandasCompletadas.setText("Comandas Completadas");
        jPanelComandasCompletadasContenedor.add(jblComandasCompletadas);
        jblComandasCompletadas.setBounds(10, 15, 170, 20);

        jPanelListaComandasCompletadas.setBackground(new java.awt.Color(255, 255, 255));
        jPanelListaComandasCompletadas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelListaComandasCompletadas.setMinimumSize(new java.awt.Dimension(300, 20));
        jPanelListaComandasCompletadas.setPreferredSize(new java.awt.Dimension(490, 380));
        jPanelListaComandasCompletadas.setLayout(null);
        jPanelComandasCompletadasContenedor.add(jPanelListaComandasCompletadas);
        jPanelListaComandasCompletadas.setBounds(10, 60, 380, 340);

        add(jPanelComandasCompletadasContenedor);
        jPanelComandasCompletadasContenedor.setBounds(720, 70, 400, 430);

        jPanelComandasActivasContenedor.setBackground(new java.awt.Color(255, 255, 255));
        jPanelComandasActivasContenedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelComandasActivasContenedor.setLayout(null);

        jblComandasActivas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jblComandasActivas.setText("Comandas activas");
        jPanelComandasActivasContenedor.add(jblComandasActivas);
        jblComandasActivas.setBounds(10, 10, 140, 20);

        btnNuevaComanda.setBackground(new java.awt.Color(17, 24, 39));
        btnNuevaComanda.setFont(new java.awt.Font("Helvetica Neue", 1, 12)); // NOI18N
        btnNuevaComanda.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevaComanda.setText("Nueva Comanda +");
        btnNuevaComanda.setBorder(null);
        btnNuevaComanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaComandaActionPerformed(evt);
            }
        });
        jPanelComandasActivasContenedor.add(btnNuevaComanda);
        btnNuevaComanda.setBounds(240, 10, 150, 30);

        jPanelListaComandasActivas.setBackground(new java.awt.Color(255, 255, 255));
        jPanelListaComandasActivas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanelListaComandasActivas.setMinimumSize(new java.awt.Dimension(300, 20));
        jPanelListaComandasActivas.setPreferredSize(new java.awt.Dimension(360, 200));
        jPanelListaComandasActivas.setLayout(null);
        jScrollPaneComandasActivas.setViewportView(jPanelListaComandasActivas);

        jPanelComandasActivasContenedor.add(jScrollPaneComandasActivas);
        jScrollPaneComandasActivas.setBounds(10, 50, 380, 370);

        add(jPanelComandasActivasContenedor);
        jPanelComandasActivasContenedor.setBounds(130, 70, 400, 430);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevaComandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaComandaActionPerformed
        // TODO add your handling code here:
        
        FrmComandas.jPanelSeccion.removeAll();
        
        panelListaProductos = new JPanelComandasListaProductos(ConstantesGUI.NUEVO);
        
        panelListaProductos.setBounds(0, 0, FrmComandas.jPanelSeccion.getWidth(), FrmComandas.jPanelSeccion.getHeight());
        FrmComandas.jPanelSeccion.add(panelListaProductos);

        FrmComandas.jPanelSeccion.revalidate();
        FrmComandas.jPanelSeccion.repaint();
        
        FrmComandas.comanda = null;
        

    }//GEN-LAST:event_btnNuevaComandaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevaComanda;
    private javax.swing.JPanel jPanelComandasActivasContenedor;
    private javax.swing.JPanel jPanelComandasCompletadasContenedor;
    private javax.swing.JPanel jPanelListaComandasActivas;
    private javax.swing.JPanel jPanelListaComandasCompletadas;
    private javax.swing.JScrollPane jScrollPaneComandasActivas;
    private javax.swing.JLabel jblComandasActivas;
    private javax.swing.JLabel jblComandasCompletadas;
    // End of variables declaration//GEN-END:variables
}
