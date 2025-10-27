/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Facades;

import Entidades.Comanda;
import Entidades.Detallecomanda;
import Entidades.ExtrasProductos;
import Entidades.Producto;
import java.util.List;

/**
 *
 * @author usuario
 */
public interface IFachadaComandasControlador{
    public List<Comanda>ObtenerListaComandas()throws Exception;
    public List<Producto> ObtenerListaProductos() throws Exception;
    public List<ExtrasProductos> ObtenerDetallesProductos(Integer idProducto)throws Exception;
    public List<Producto> ObtenerListaProductosCategoria(int idCategoria) throws Exception;
    public void GuardarComanda(Comanda comanda)throws Exception;
    public void GuardarDetalleComanda(Detallecomanda detComanda) throws Exception;
    public void EditarComanda(Comanda comanda) throws Exception;
    public void comandaCompletada (Integer idComanda)throws Exception;
    public List<Comanda> obtenerComandasActivas()throws Exception;
     public List<Comanda> obtenerComandasCompletadas() throws Exception;
     public void comandaEliminada (Integer idComanda)throws Exception;
     public int totalProductoDetalles () throws Exception;
     public int totalComandas()throws Exception;
}
