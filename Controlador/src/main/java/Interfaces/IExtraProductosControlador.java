/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import Entidades.ExtrasProductos;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public interface IExtraProductosControlador {
  void create(ExtrasProductos extraProducto) throws PreexistingEntityException, Exception;
    
    void edit(ExtrasProductos extraProducto) throws NonexistentEntityException, Exception;
    
    void destroy(Integer id) throws NonexistentEntityException;
    
    ExtrasProductos findExtraProducto(Integer id);
    
    List<ExtrasProductos> findExtraProductoEntities();
    
    List<ExtrasProductos> findExtraProductoEntities(int maxResults, int firstResult);
    
    int getExtraProductoCount();
    
    List<ExtrasProductos> findExtrasByProducto(Integer idProducto);
  
}
