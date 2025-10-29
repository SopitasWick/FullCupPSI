/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Categoria;
import Facades.IFachadaCategoriaControlador;
import Facades.IFachadaCategoriaModel;
import java.util.List;

/**
 *
 * @author Orlando Leyva
 */
public class GestionarCategoriaControlador implements IFachadaCategoriaControlador{

  IFachadaCategoriaModel categoriaModel;
    
    public GestionarCategoriaControlador() {
        this.categoriaModel = new GestionarCategoriasModel();
    }
    
    @Override
    public Categoria obtenerCategoria(Integer id) throws Exception {
        return categoriaModel.obtenerCategoria(id);
    }
    
    @Override
    public void agregarCategoria(Categoria categoria) throws Exception {
        categoriaModel.agregarCategoria(categoria);
    }
    
    @Override
    public void editarCategoria(Categoria categoria) throws Exception {
        categoriaModel.editarCategoria(categoria);
    }
    
    @Override
    public void eliminarCategoria(Integer id) throws Exception {
        categoriaModel.eliminarCategoria(id);
    }
    
    @Override
    public List<Categoria> obtenerTodasLasCategorias() throws Exception {
        return categoriaModel.obtenerTodasLasCategorias();
    }
    
}
