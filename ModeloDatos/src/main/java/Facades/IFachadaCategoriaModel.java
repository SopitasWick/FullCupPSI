/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Facades;

import Entidades.Categoria;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public interface IFachadaCategoriaModel {
     public Categoria obtenerCategoria(Integer id) throws Exception;
    
    public void agregarCategoria(Categoria categoria) throws Exception;
    
    public void editarCategoria(Categoria categoria) throws Exception;
    
    public void eliminarCategoria(Integer id) throws Exception;
    
    public List<Categoria> obtenerTodasLasCategorias() throws Exception;
    
    public Categoria obtenerCategoriaPorNombre(String nombre) throws Exception;
    
    public void editarEstadoCategoria(Integer id,String estado) throws Exception;
    
}

