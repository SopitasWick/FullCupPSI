/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Valoropcion;
import Facades.IFachadaValorOpcionModel;
import JPA.ValoropcionJpaController;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarValorOpcionesModel implements IFachadaValorOpcionModel{

    
    ValoropcionJpaController jpaValorOpcion;
    
    public GestionarValorOpcionesModel() {
        this.jpaValorOpcion = new ValoropcionJpaController();
    }
    
    
    @Override
    public List<Valoropcion> ObtenerTodosValorOpciones() throws Exception {
        return jpaValorOpcion.findValoropcionEntities();
    }
    
}
