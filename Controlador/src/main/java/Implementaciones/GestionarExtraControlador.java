/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Extra;
import Facades.IFachadaExtraControlador;
import Facades.IFachadaExtraModel2;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarExtraControlador implements IFachadaExtraControlador{
    
    
    IFachadaExtraModel2 eModel;
    
    
    public GestionarExtraControlador() {
        this.eModel = new GestionarExtrasModel2();
    }

    
    @Override
    public Extra agregarExtra(Extra extra) throws Exception {
        return eModel.agregarExtra(extra);
    }

    @Override
    public Extra actualizarExtra(Extra extra) throws Exception {
        return eModel.actualizarExtra(extra);
    }

    @Override
    public List<Extra> obtenerTodosLosExtras() throws Exception {
        return eModel.obtenerTodosLosExtras();
    }
    
    
}
