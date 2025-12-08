/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Facades;

import Entidades.Extra;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public interface IFachadaExtraControlador {
    
    
    public Extra agregarExtra(Extra extra) throws Exception;
    
    public Extra actualizarExtra(Extra extra) throws Exception;
    
    public List<Extra> obtenerTodosLosExtras() throws Exception;
    
}
