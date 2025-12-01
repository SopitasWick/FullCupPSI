/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Categoria;
import Facades.IFachadaCategoriaModel;
import JPA.CategoriaJpaController;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class GestionarCategoriasModel implements IFachadaCategoriaModel{
       
    CategoriaJpaController categoriaJPA;
    
    public GestionarCategoriasModel() {
        this.categoriaJPA = new CategoriaJpaController();
    }
    
    @Override
    public Categoria obtenerCategoria(Integer id) throws Exception {
        return categoriaJPA.findCategoria(id);
    }
    
    @Override
    public void agregarCategoria(Categoria categoria) throws Exception {
        categoriaJPA.create(categoria);
    }
    
    @Override
    public void editarCategoria(Categoria categoria) throws Exception {
        categoriaJPA.edit(categoria);
    }
    
    @Override
    public void eliminarCategoria(Integer id) throws Exception {
        categoriaJPA.destroy(id);
    }
    
    @Override
    public List<Categoria> obtenerTodasLasCategorias() throws Exception {
        return categoriaJPA.findCategoriaEntities();
    }

    @Override
    public Categoria obtenerCategoriaPorNombre(String nombre) throws Exception {
         return categoriaJPA.findCategoriaByNombre(nombre);
    }

    @Override
    public void editarEstadoCategoria(Integer id,String estado) throws Exception {
       Categoria categoria = categoriaJPA.findCategoria(id);
       categoria.setEstado(estado);
       categoriaJPA.edit(categoria);
    }
}
