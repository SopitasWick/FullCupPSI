/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Comanda;
import Entidades.Detallecomanda;
import Facades.IFachadaDetalleComandasModel;
import Facades.IFachadaDetallesComandaControlador;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarDetallesComandaControlador implements IFachadaDetallesComandaControlador{
    
    
    IFachadaDetalleComandasModel dCModel;
    
    
    public GestionarDetallesComandaControlador(){
        this.dCModel = new GestionarDetallesComandaModel();
    
    }

    
    @Override
    public Detallecomanda agregarDetallesComandas(Detallecomanda detallecomanda) throws Exception {
        return dCModel.agregarDetallesComandas(detallecomanda);
    }

    @Override
    public void editarDetallesComandas(Detallecomanda detallecomanda) throws Exception {
        dCModel.editarDetallesComandas(detallecomanda);
    }

    @Override
    public void eliminarDetallesComandas(Integer id) throws Exception {
        dCModel.eliminarDetallesComandas(id);
    }
    
    
    
    @Override
    public List<Detallecomanda> obtenerDetallesComandasPorComanda(Comanda comanda) throws Exception {
        return dCModel.obtenerDetallesComandasPorComanda(comanda);
    }

    
}
