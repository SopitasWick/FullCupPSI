/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Comanda;
import Entidades.Detallecomanda;
import Facades.IFachadaDetalleComandasModel;
import JPA.DetallecomandaJpaController;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarDetallesComandaModel implements IFachadaDetalleComandasModel{
    
    DetallecomandaJpaController jpaDetalleComanda;

    
    public GestionarDetallesComandaModel(){
        this.jpaDetalleComanda = new DetallecomandaJpaController();
    }
    
    
    @Override
    public Detallecomanda agregarDetallesComandas(Detallecomanda detallecomanda) throws Exception {
        return jpaDetalleComanda.create(detallecomanda);
    }

    @Override
    public Detallecomanda editarDetallesComandas(Detallecomanda detallecomanda) throws Exception {
        return jpaDetalleComanda.edit(detallecomanda);
    }

    @Override
    public void eliminarDetallesComandas(Integer id) throws Exception {
        jpaDetalleComanda.destroy(id);
    }

    @Override
    public List<Detallecomanda> obtenerDetallesComandasPorComanda(Comanda comanda) throws Exception {
        return jpaDetalleComanda.findByDetalleComandaByComanda(comanda);
    }


    
}
