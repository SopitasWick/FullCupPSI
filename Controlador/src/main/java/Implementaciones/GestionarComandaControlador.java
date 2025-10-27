/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;
import Entidades.Comanda;
import Entidades.Detallecomanda;
import Entidades.ExtrasProductos;
import Entidades.Producto;
import Facades.IFachadaComandasControlador;
import Implementaciones.GestionarComandaModel;
import Facades.IFachadaComandasModel;
import java.util.List;
/**
 *
 * @author usuario
 */
public class GestionarComandaControlador implements IFachadaComandasControlador{
IFachadaComandasModel FModel;

    public GestionarComandaControlador() {
        this.FModel = new GestionarComandaModel();
    }

    @Override
    public List<Producto> ObtenerListaProductos() throws Exception {
        return FModel.ObtenerListaProductos();
    }

    @Override
    public List<ExtrasProductos> ObtenerDetallesProductos(Integer idProducto) throws Exception {
           return FModel.ObtenerDetallesProductos(idProducto);
    }

    @Override
    public void GuardarComanda(Comanda comanda) throws Exception {
        FModel.GuardarComanda(comanda);
    }

    @Override
    public List<Comanda> ObtenerListaComandas() throws Exception {
       return FModel.ObtenerListaComandas();
    }

    @Override
    public void comandaCompletada(Integer idComanda) throws Exception {
    FModel.comandaCompletada(idComanda);
    }

    @Override
    public List<Comanda> obtenerComandasActivas() throws Exception {
       return FModel.obtenerComandasActivas();
    }

    @Override
    public List<Comanda> obtenerComandasCompletadas() throws Exception {
     return FModel.obtenerComandasCompletadas();
    }

    @Override
    public void comandaEliminada(Integer idComanda) throws Exception {
         FModel.comandaEliminada(idComanda);
    }

    @Override
    public int totalProductoDetalles() throws Exception {
       return FModel.totalProductoDetalles();
    }

    @Override
    public int totalComandas() throws Exception {
        return FModel.totalComandas();
    }

    @Override
    public void GuardarDetalleComanda(Detallecomanda detComanda) throws Exception {
               FModel.GuardarDetalleComanda(detComanda);
    }

    @Override
    public void EditarComanda(Comanda comanda) throws Exception {
         FModel.EditarComanda(comanda);
    }

    @Override
    public List<Producto> ObtenerListaProductosCategoria(int idCategoria) throws Exception {
     return FModel.ObtenerListaProductosCategoria(idCategoria);
    }
    
}
