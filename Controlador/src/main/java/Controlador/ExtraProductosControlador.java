/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Entidades.ExtrasProductos;
import Interfaces.IExtraProductosControlador;
import JPA.ExtraProductosJpaController;
import JPA.exceptions.NonexistentEntityException;
import JPA.exceptions.PreexistingEntityException;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class ExtraProductosControlador implements  IExtraProductosControlador{
    private final ExtraProductosJpaController extraProductosController;
    
    public ExtraProductosControlador() {
        this.extraProductosController = new ExtraProductosJpaController();
    }
    
    @Override
    public void create(ExtrasProductos extraProducto) throws PreexistingEntityException, Exception {
        if (extraProducto == null) {
            throw new Exception("El extra producto no puede ser nulo");
        }
        
        if (extraProducto.getIdExtraProducto() == null) {
            List<ExtrasProductos> todos = extraProductosController.findExtraProductoEntities();
            int maxId = todos.stream()
                    .mapToInt(e -> e.getIdExtraProducto() != null ? e.getIdExtraProducto() : 0)
                    .max()
                    .orElse(0);
            extraProducto.setIdExtraProducto(maxId + 1);
        }
        
        if (extraProducto.getNombreExtra() == null || extraProducto.getNombreExtra().trim().isEmpty()) {
            throw new Exception("El nombre del extra no puede estar vacío");
        }
        
        if (extraProducto.getPrecioExtra() == null) {
            extraProducto.setPrecioExtra(0.0f);
        }
        
        if (extraProducto.getPrecioExtra() < 0) {
            throw new Exception("El precio del extra debe ser mayor o igual a 0");
        }
        
        if (extraProducto.getCantidad() < 0) {
            throw new Exception("La cantidad debe ser mayor o igual a 0");
        }
        
        extraProductosController.create(extraProducto);
    }
    
    @Override
    public void edit(ExtrasProductos extraProducto) throws NonexistentEntityException, Exception {
        if (extraProducto == null) {
            throw new Exception("El extra producto no puede ser nulo");
        }
        
        if (extraProducto.getIdExtraProducto() == null) {
            throw new Exception("El ID del extra producto no puede ser nulo");
        }
        
        if (extraProducto.getNombreExtra() == null || extraProducto.getNombreExtra().trim().isEmpty()) {
            throw new Exception("El nombre del extra no puede estar vacío");
        }
        
        if (extraProducto.getPrecioExtra() == null || extraProducto.getPrecioExtra() < 0) {
            throw new Exception("El precio del extra debe ser mayor o igual a 0");
        }
        
        if (extraProducto.getCantidad() < 0) {
            throw new Exception("La cantidad debe ser mayor o igual a 0");
        }
        
        extraProductosController.edit(extraProducto);
    }
    
    @Override
    public void destroy(Integer id) throws NonexistentEntityException {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo");
        }
        extraProductosController.destroy(id);
    }
    
    @Override
    public ExtrasProductos findExtraProducto(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        return extraProductosController.findExtraProducto(id);
    }
    
    @Override
    public List<ExtrasProductos> findExtraProductoEntities() {
        return extraProductosController.findExtraProductoEntities();
    }
    
    @Override
    public List<ExtrasProductos> findExtraProductoEntities(int maxResults, int firstResult) {
        if (maxResults <= 0) {
            throw new IllegalArgumentException("El número máximo de resultados debe ser mayor a 0");
        }
        if (firstResult < 0) {
            throw new IllegalArgumentException("El primer resultado no puede ser negativo");
        }
        return extraProductosController.findExtraProductoEntities(maxResults, firstResult);
    }
    
    @Override
    public int getExtraProductoCount() {
        return extraProductosController.getExtraProductoCount();
    }
    
    @Override
    public List<ExtrasProductos> findExtrasByProducto(Integer idProducto) {
        if (idProducto == null || idProducto <= 0) {
            throw new IllegalArgumentException("El ID del producto debe ser un número positivo");
        }
        return extraProductosController.findExtrasByProducto(idProducto);
    }
    
    public float calcularPrecioTotalExtras(List<ExtrasProductos> extras) {
        if (extras == null || extras.isEmpty()) {
            return 0.0f;
        }
        float total = 0.0f;
        for (ExtrasProductos extra : extras) {
            if (extra.getPrecioExtra() != null) {
                total += extra.getPrecioExtra() * extra.getCantidad();
            }
        }
        return total;
    }
    
    public boolean verificarDisponibilidad(Integer idExtra) {
        ExtrasProductos extra = findExtraProducto(idExtra);
        return extra != null && extra.getCantidad() > 0;
    } 
}
