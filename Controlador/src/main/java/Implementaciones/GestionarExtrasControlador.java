/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.ExtrasProductos;
import Facades.IFachadaExtraModel;
import Facades.IFachadaExtrasControlador;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarExtrasControlador implements IFachadaExtrasControlador{

    
    
    
    IFachadaExtraModel eModel;
    
    public GestionarExtrasControlador() {
        this.eModel = new GestionarExtrasModel();
    }
    
    @Override
    public ExtrasProductos obtenerExtras(Integer id) throws Exception {
        return eModel.obtenerExtras(id);
    }
    
    @Override
    public void agregarExtrasProductos(ExtrasProductos extra) throws Exception {
        eModel.agregarExtrasProductos(extra);
    }
    
    @Override
    public void editarExtrasProductos(ExtrasProductos extra) throws Exception {
        eModel.editarExtrasProductos(extra);
    }
    
    @Override
    public void eliminarExtrasProductos(Integer id) throws Exception {
        eModel.eliminarExtrasProductos(id);
    }
     @Override
    public List<ExtrasProductos> obtenerTodosLosExtras() throws Exception {
        return eModel.obtenerTodosLosExtras();
    }
    
}
    
    

