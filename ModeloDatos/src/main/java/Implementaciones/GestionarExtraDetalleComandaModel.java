/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Detallecomanda;
import Entidades.ExtrasDetalleComanda;
import Facades.IFachadaExtraDetalleComandaModel;
import JPA.ExtraDetalleComandaJPAController;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarExtraDetalleComandaModel implements IFachadaExtraDetalleComandaModel{
    
    ExtraDetalleComandaJPAController jpaExtraDetalleComanda;

    
    public GestionarExtraDetalleComandaModel(){
        this.jpaExtraDetalleComanda = new ExtraDetalleComandaJPAController();
    }

    @Override
    public ExtrasDetalleComanda guardarExtraDetalleComanda(ExtrasDetalleComanda extraDetalleComanda) {
        return jpaExtraDetalleComanda.guardarExtraDetalleComanda(extraDetalleComanda);
    }

    @Override
    public ExtrasDetalleComanda actualizarExtra(ExtrasDetalleComanda extraDetalleComanda) {
        return jpaExtraDetalleComanda.actualizarExtraDetalleComanda(extraDetalleComanda);
    }
    
    @Override
    public void eliminarExtraDetalleComanda(Detallecomanda detalleComanda) {
        jpaExtraDetalleComanda.eliminarExtrasPorDetalleComanda(detalleComanda);
    }


    @Override
    public List<ExtrasDetalleComanda> obtenerTodosLosExtrasPorDetalleComanda(Detallecomanda idDetalleComanda) {
        return jpaExtraDetalleComanda.obtenerTodosLosExtrasPorDetalleComanda(idDetalleComanda);
    }
    
}
