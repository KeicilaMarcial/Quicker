/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.classes;

import java.util.Calendar;

/**
 *
 * @author Adonias
 */
public class ProdutoMateria {
    /* ORDEM DOS Get's E Set's
    NOMECLIENTE, CPFCLIENTE, DATANASCIMENTOCLIENTE, TELEFONECLIENTE, CELULARCLIENTE, RUAAVNCLIENTE,
    NUMEROCLIENTE, CEPCLINTE, COMPLEMENTOCLIENTE, BAIRROCLIENTE, CIDADECLIENTE, EMAILCLIENTE;
    */
    
    private int idProdutoMateria;
    private String nomeProdutoMateria;
    private String tipoProdutoMateria;
    private String unidadeMedidaProdutoMateria;
    private String quantidaFardoProdutoMateria;
    /**
     * @return the idProdutoMateria
     */
    public int getIdProdutoMateria() {
        return idProdutoMateria;
    }

    /**
     * @param idProdutoMateria the idProdutoMateria to set
     */
    public void setIdProdutoMateria(int idProdutoMateria) {
        this.idProdutoMateria = idProdutoMateria;
    }

    /**
     * @return the nomeProdutoMateria
     */
    public String getNomeProdutoMateria() {
        return nomeProdutoMateria;
    }

    /**
     * @param nomeProdutoMateria the nomeProdutoMateria to set
     */
    public void setNomeProdutoMateria(String nomeProdutoMateria) {
        this.nomeProdutoMateria = nomeProdutoMateria;
    }

    /**
     * @return the tipoProdutoMateria
     */
    public String getTipoProdutoMateria() {
        return tipoProdutoMateria;
    }

    /**
     * @param tipoProdutoMateria the tipoProdutoMateria to set
     */
    public void setTipoProdutoMateria(String tipoProdutoMateria) {
        this.tipoProdutoMateria = tipoProdutoMateria;
    }

    /**
     * @return the unidadeMedidaProdutoMateria
     */
    public String getUnidadeMedidaProdutoMateria() {
        return unidadeMedidaProdutoMateria;
    }

    /**
     * @param unidadeMedidaProdutoMateria the unidadeMedidaProdutoMateria to set
     */
    public void setUnidadeMedidaProdutoMateria(String unidadeMedidaProdutoMateria) {
        this.unidadeMedidaProdutoMateria = unidadeMedidaProdutoMateria;
    }

    /**
     * @return the quantidaFardoProdutoMateria
     */
    public String getQuantidaFardoProdutoMateria() {
        return quantidaFardoProdutoMateria;
    }

    /**
     * @param quantidaFardoProdutoMateria the quantidaFardoProdutoMateria to set
     */
    public void setQuantidaFardoProdutoMateria(String quantidaFardoProdutoMateria) {
        this.quantidaFardoProdutoMateria = quantidaFardoProdutoMateria;
    }

    /**
     * @return the unidadeMedidaProdutoMateria
     */
    
}