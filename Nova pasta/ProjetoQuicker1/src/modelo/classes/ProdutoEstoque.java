/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.classes;

import java.util.Calendar;

/**
 *
 * @author alunov2
 */
public class ProdutoEstoque {
 
 /* ORDEM DOS Get's E Set's
    NOMECLIENTE, CPFCLIENTE, DATANASCIMENTOCLIENTE, TELEFONECLIENTE, CELULARCLIENTE, RUAAVNCLIENTE,
    NUMEROCLIENTE, CEPCLINTE, COMPLEMENTOCLIENTE, BAIRROCLIENTE, CIDADECLIENTE, EMAILCLIENTE;
    */
    
    private int idProdutoEstoque;
    private String nomeProdutoEstoque;
    private String tipoProdutoEstoque;
    private Calendar unidadeMedidaEstoque;
   

    /**
     * @return the idProdutoEstoque
     */
    public int getIdProdutoEstoque() {
        return idProdutoEstoque;
    }

    /**
     * @param idProdutoEstoque the idProdutoEstoque to set
     */
    public void setIdProdutoEstoque(int idProdutoEstoque) {
        this.idProdutoEstoque = idProdutoEstoque;
    }

    /**
     * @return the nomeProdutoEstoque
     */
    public String getNomeProdutoEstoque() {
        return nomeProdutoEstoque;
    }

    /**
     * @param nomeProdutoEstoque the nomeProdutoEstoque to set
     */
    public void setNomeProdutoEstoque(String nomeProdutoEstoque) {
        this.nomeProdutoEstoque = nomeProdutoEstoque;
    }

    /**
     * @return the tipoProdutoEstoque
     */
    public String getTipoProdutoEstoque() {
        return tipoProdutoEstoque;
    }

    /**
     * @param tipoProdutoEstoque the tipoProdutoEstoque to set
     */
    public void setTipoProdutoEstoque(String tipoProdutoEstoque) {
        this.tipoProdutoEstoque = tipoProdutoEstoque;
    }

    /**
     * @return the unidadeMedidaEstoque
     */
    public Calendar getUnidadeMedidaEstoque() {
        return unidadeMedidaEstoque;
    }

    /**
     * @param unidadeMedidaEstoque the unidadeMedidaEstoque to set
     */
    public void setUnidadeMedidaEstoque(Calendar unidadeMedidaEstoque) {
        this.unidadeMedidaEstoque = unidadeMedidaEstoque;
    }
}