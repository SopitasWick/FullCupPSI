/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Leche;
import Facades.IFachadaLecheControlador;
import Facades.IFachadaLecheModel;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarLecheControlador implements IFachadaLecheControlador{
    
    
    IFachadaLecheModel lModel;
    
    
    public GestionarLecheControlador(){
        this.lModel = new GestionarLecheModel();
    }
    

    @Override
    public Leche obtenerLeche(Integer id) throws Exception {
        return lModel.obtenerLeche(id);
    }

    @Override
    public void agregarLeche(Leche leche) throws Exception {
        lModel.agregarLeche(leche);
    }

    @Override
    public void editarLecha(Leche leche) throws Exception {
        lModel.editarLecha(leche);
    }

    @Override
    public void eliminarLeche(Integer id) throws Exception {
        lModel.eliminarLeche(id);
    }

    @Override
    public List<Leche> obtenerTodasLasLeches() throws Exception {
        return lModel.obtenerTodasLasLeches();
    }
    
}
