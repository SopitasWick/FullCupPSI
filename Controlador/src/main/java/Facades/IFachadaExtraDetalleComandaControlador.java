/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Facades;

import Entidades.Detallecomanda;
import Entidades.ExtrasDetalleComanda;
import java.util.List;

/**
 *
 * @author Sergio Arturo
 */
public interface IFachadaExtraDetalleComandaControlador {
    
    
    public ExtrasDetalleComanda guardarExtraDetalleComanda(ExtrasDetalleComanda extraDetalleComanda);
    
    public ExtrasDetalleComanda actualizarExtra(ExtrasDetalleComanda extraDetalleComanda);
    
    public void eliminarExtraDetalleComanda(Detallecomanda detalleComanda);

    
    public List <ExtrasDetalleComanda> obtenerTodosLosExtrasPorDetalleComanda(Detallecomanda idDetalleComanda);
    
}
