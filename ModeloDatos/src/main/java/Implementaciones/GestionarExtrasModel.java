/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.ExtrasProductos;
import Entidades.Producto;
import Facades.IFachadaExtraModel;
import JPA.ExtraProductosJpaController;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarExtrasModel implements IFachadaExtraModel{
    
    
    ExtraProductosJpaController extraJPA;
    
    
    public GestionarExtrasModel(){
        extraJPA = new ExtraProductosJpaController();
    }

    @Override
    public ExtrasProductos obtenerExtras(Integer id) throws Exception {
        return extraJPA.findExtraProducto(id);
    }

    @Override
    public void agregarExtrasProductos(ExtrasProductos extra) throws Exception {
        extraJPA.create(extra);
    }

    @Override
    public void editarExtrasProductos(ExtrasProductos extra) throws Exception {
        extraJPA.edit(extra);
    }

    @Override
    public void eliminarExtrasProductos(Integer id) throws Exception {
        extraJPA.destroy(id);
    }

    @Override
    public List<ExtrasProductos> obtenerTodosLosExtrasPorProducto(Producto producto) throws Exception {
        return extraJPA.findExtrasByProducto(producto.getIdProducto());
    }
    
}
