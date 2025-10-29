/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Facades;

import Entidades.Leche;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public interface IFachadaLecheControlador {
    
    
    public Leche obtenerLeche(Integer id) throws Exception;
    
    public void agregarLeche(Leche leche) throws Exception;
    
    public void editarLecha(Leche leche) throws Exception;
    
    public void eliminarLeche(Integer id) throws Exception;
    
    public List<Leche> obtenerTodasLasLeches() throws Exception;
    
}
