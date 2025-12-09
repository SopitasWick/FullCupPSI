/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Implementaciones;

import Entidades.Comanda;
import Entidades.Detallecomanda;
import Entidades.ExtrasProductos;
import Entidades.Producto;
import Facades.IFachadaComandasModel;
import JPA.ComandaJpaController;
import JPA.DetallecomandaJpaController;
import JPA.ExtraProductosJpaController;
import JPA.ProductoJpaController;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author usuario
 */
public class GestionarComandaModel implements IFachadaComandasModel {

    ProductoJpaController jpaProducto;
    ExtraProductosJpaController jpaExtras;
    ComandaJpaController jpaComanda;
    DetallecomandaJpaController jpaDetalleComanda;

    public GestionarComandaModel() {
        this.jpaProducto = new ProductoJpaController();
        this.jpaExtras = new ExtraProductosJpaController();
        this.jpaComanda = new ComandaJpaController();
        this.jpaDetalleComanda = new DetallecomandaJpaController();
    }

    @Override
    public List<Comanda> ObtenerListaComandas() throws Exception {
        return jpaComanda.findComandaEntities();
    }

    @Override
    public List<Producto> ObtenerListaProductos() throws Exception {
        return jpaProducto.findProductoEntities();
    }

    @Override
    public List<ExtrasProductos> ObtenerDetallesProductos(Integer idProducto) throws Exception {
        return jpaExtras.findExtrasByProducto(idProducto);
    }

    @Override
    public Comanda GuardarComanda(Comanda comanda) throws Exception {
        return jpaComanda.create(comanda);
    }

    @Override
    public void comandaCompletada(Integer idComanda) throws Exception {
        if (idComanda == null || idComanda <= 0) {
            throw new Exception("ID de comanda inválido");
        }
        Comanda c = jpaComanda.findComanda(idComanda);
        if (c == null) {
            throw new Exception("Comanda no encontrada");
        }
        c.setEstadoComanda("Cerrada");
        jpaComanda.edit(c);
    }

    @Override
    public Comanda obtenerComanda(Integer idComanda) throws Exception {
        return jpaComanda.findComanda(idComanda);
    }

    @Override
    public List<Comanda> obtenerComandasActivas() throws Exception {
        return jpaComanda.findComandaEntities().stream()
                .filter(c -> c.getEstadoComanda() != null && c.getEstadoComanda().equalsIgnoreCase("Abierta"))
                .collect(Collectors.toList());
    }

    @Override
    public List<Comanda> obtenerComandasCompletadas() throws Exception {
        return jpaComanda.findComandaEntities().stream()
                .filter(c -> c.getEstadoComanda() != null && c.getEstadoComanda().equalsIgnoreCase("Cerrada"))
                .collect(Collectors.toList());
    }

    @Override
    public void comandaEliminada(Integer idComanda) throws Exception {
        if (idComanda == null || idComanda <= 0) {
            throw new Exception("ID de comanda inválido");
        }
        Comanda c = jpaComanda.findComanda(idComanda);
        if (c == null) {
            throw new Exception("Comanda no encontrada");
        }
        c.setEstadoComanda("Eliminado");
        jpaComanda.edit(c);
    }

    @Override
    public int totalProductoDetalles() throws Exception {
        return jpaDetalleComanda.getDetallecomandaCount();
    }

    @Override
    public int totalComandas() throws Exception {
        return jpaComanda.getComandaCount();
    }

    @Override
    public void GuardarDetalleComanda(Detallecomanda detComanda) throws Exception {
        jpaDetalleComanda.create(detComanda);
    }

    @Override
    public Comanda EditarComanda(Comanda comanda) throws Exception {
        return jpaComanda.edit(comanda);
    }

    @Override
    public List<Producto> ObtenerListaProductosCategoria(int idCategoria) throws Exception {
        return jpaProducto.findProductosByIdCategoria(idCategoria);
    }

    @Override
    public void EditarTotalComanda(Integer idComanda, float nuevoTotal) throws Exception {
        jpaComanda.actualizarTotalComanda(idComanda, nuevoTotal);
    }

    @Override
    public void EditarDescripcionComanda(Integer idComanda, String descripcion) throws Exception {
        if (idComanda == null || idComanda <= 0) {
            throw new Exception("ID de comanda inválido");
        }

        Comanda comanda = jpaComanda.findComanda(idComanda);

        if (comanda == null) {
            throw new Exception("Comanda no encontrada con ID: " + idComanda);
        }

        // Actualizar la descripción
        comanda.setDescripcionGeneral(descripcion);

        // Guardar los cambios en la base de datos
        jpaComanda.edit(comanda);
    }

}
