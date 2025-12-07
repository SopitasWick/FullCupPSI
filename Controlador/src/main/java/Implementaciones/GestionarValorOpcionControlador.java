/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Valoropcion;
import Facades.IFachadaValorOpcionControlador;
import Facades.IFachadaValorOpcionModel;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarValorOpcionControlador implements IFachadaValorOpcionControlador{

    
    IFachadaValorOpcionModel FModel;
    
    
    public GestionarValorOpcionControlador() {
        this.FModel = new GestionarValorOpcionesModel();
    }
    
    @Override
    public List<Valoropcion> ObtenerTodosValorOpciones() throws Exception {
        return FModel.ObtenerTodosValorOpciones();
    }
    
    
}
