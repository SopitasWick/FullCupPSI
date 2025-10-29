/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.TamanoVaso;
import Facades.IFachadaTamanoVasosModel;
import JPA.TamanoVasoJpaController;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class GestionarTamanoVasosModel implements IFachadaTamanoVasosModel  {
  
    
    TamanoVasoJpaController tamanoVasoJPA;
    
    public GestionarTamanoVasosModel(){
        this.tamanoVasoJPA = new TamanoVasoJpaController();
    }
    
    @Override
    public TamanoVaso obtenerTamanoVaso(Integer id) throws Exception {
        return tamanoVasoJPA.findTamanoVaso(id);
    }
    
    @Override
    public void agregarTamanoVaso(TamanoVaso tamanoVaso) throws Exception {
        tamanoVasoJPA.create(tamanoVaso);
    }
    
    @Override
    public void editarTamanoVaso(TamanoVaso tamanoVaso) throws Exception {
        tamanoVasoJPA.edit(tamanoVaso);
    }
    
    @Override
    public void eliminarTamanoVaso(Integer id) throws Exception {
        tamanoVasoJPA.destroy(id);
    }
    
    @Override
    public List<TamanoVaso> obtenerTodosLosTamanosVasos() throws Exception {
        return tamanoVasoJPA.findTamanoVasoEntities();
    }
    

}
