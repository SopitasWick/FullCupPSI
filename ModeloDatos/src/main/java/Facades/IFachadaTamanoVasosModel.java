/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Facades;

import Entidades.TamanoVaso;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public interface IFachadaTamanoVasosModel {
       public TamanoVaso obtenerTamanoVaso(Integer id) throws Exception;
    
    public void agregarTamanoVaso(TamanoVaso tamanoVaso) throws Exception;
    
    public void editarTamanoVaso(TamanoVaso tamanoVaso) throws Exception;
    
    public void eliminarTamanoVaso(Integer id) throws Exception;
    
    public List<TamanoVaso> obtenerTodosLosTamanosVasos() throws Exception;
}
