/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Extra;
import Facades.IFachadaExtraModel2;
import JPA.ExtraJPAController;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarExtrasModel2 implements IFachadaExtraModel2{
    
    
    ExtraJPAController extraJPA;
    
    
    public GestionarExtrasModel2(){
        extraJPA = new ExtraJPAController();
    }

    @Override
    public Extra agregarExtra(Extra extra) throws Exception {
        return extraJPA.guardarExtra(extra);
    }

    @Override
    public Extra actualizarExtra(Extra extra) throws Exception {
        return extraJPA.actualizarExtra(extra);

    }

    @Override
    public List<Extra> obtenerTodosLosExtras() throws Exception {
        return extraJPA.obtenerTodosLosExtras();
        
    }
    
    
    
}
