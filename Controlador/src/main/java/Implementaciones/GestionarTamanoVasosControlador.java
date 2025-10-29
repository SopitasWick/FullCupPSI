/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.TamanoVaso;
import Facades.IFachadaTamanoVasosControlador;
import Facades.IFachadaTamanoVasosModel;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class GestionarTamanoVasosControlador implements IFachadaTamanoVasosControlador{
    IFachadaTamanoVasosModel tvModel;
    
    public GestionarTamanoVasosControlador(){
        this.tvModel = new GestionarTamanoVasosModel();
    }
    
    @Override
    public TamanoVaso obtenerTamanoVaso(Integer id) throws Exception {
        return tvModel.obtenerTamanoVaso(id);
    }
    
    @Override
    public void agregarTamanoVaso(TamanoVaso tamanoVaso) throws Exception {
        tvModel.agregarTamanoVaso(tamanoVaso);
    }
    
    public void editarTamanoVaso(TamanoVaso tamanoVaso) throws Exception {
        tvModel.editarTamanoVaso(tamanoVaso);
    }
    
    @Override
    public void eliminarTamanoVaso(Integer id) throws Exception {
        tvModel.eliminarTamanoVaso(id);
    }
    
    @Override
    public List<TamanoVaso> obtenerTodosLosTamanosVasos() throws Exception {
        return tvModel.obtenerTodosLosTamanosVasos();
    }
}
