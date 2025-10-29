/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Leche;
import Facades.IFachadaLecheModel;
import JPA.LecheJpaController;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public class GestionarLecheModel implements IFachadaLecheModel{
    
    
    LecheJpaController lecheJPA;
    
    
    public GestionarLecheModel(){
        this.lecheJPA = new LecheJpaController();
    }

    
    @Override
    public Leche obtenerLeche(Integer id) throws Exception {
        return lecheJPA.findLeche(id);
    }

    @Override
    public void agregarLeche(Leche leche) throws Exception {
        lecheJPA.create(leche);
    }

    @Override
    public void editarLecha(Leche leche) throws Exception {
        lecheJPA.edit(leche);
    }

    @Override
    public void eliminarLeche(Integer id) throws Exception {
        lecheJPA.destroy(id);
    }

    @Override
    public List<Leche> obtenerTodasLasLeches() throws Exception {
        return lecheJPA.findLecheEntities();
    }
    
}
