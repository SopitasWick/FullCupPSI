/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Detallecomanda;
import Entidades.ExtrasDetalleComanda;
import Facades.IFachadaExtraDetalleComandaControlador;
import Facades.IFachadaExtraDetalleComandaModel;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarExtraDetalleComandaControlador implements IFachadaExtraDetalleComandaControlador{
    
    
    IFachadaExtraDetalleComandaModel eModel;
    
    
    public GestionarExtraDetalleComandaControlador() {
        this.eModel = new GestionarExtraDetalleComandaModel();
    }
    

    @Override
    public ExtrasDetalleComanda guardarExtraDetalleComanda(ExtrasDetalleComanda extraDetalleComanda) {
        return eModel.actualizarExtra(extraDetalleComanda);
    }

    @Override
    public ExtrasDetalleComanda actualizarExtra(ExtrasDetalleComanda extraDetalleComanda) {
        return eModel.actualizarExtra(extraDetalleComanda);
    }
    
    @Override
    public void eliminarExtraDetalleComanda(Detallecomanda detalleComanda) {
        eModel.eliminarExtraDetalleComanda(detalleComanda);
    }


    @Override
    public List<ExtrasDetalleComanda> obtenerTodosLosExtrasPorDetalleComanda(Detallecomanda idDetalleComanda) {
        return eModel.obtenerTodosLosExtrasPorDetalleComanda(idDetalleComanda);
    }
    
}
