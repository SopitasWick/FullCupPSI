/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.Comanda;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public interface IComandaControlador {
     void guardarComanda(Comanda comanda) throws Exception;
    
    void actualizarComanda(Comanda comanda) throws Exception;
    
    void eliminarComanda(Integer id) throws Exception;
    
    Comanda obtenerComanda(Integer id);
    
    List<Comanda> obtenerTodasLasComandas();
    
    List<Comanda> obtenerComandasPaginadas(int maxResults, int firstResult);
    
    int contarComandas();
    
    List<Comanda> obtenerComandasPorUsuario(Integer idUsuario);
}
