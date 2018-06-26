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
public class Cliente {
     /* ORDEM DOS Get's E Set's
    NOMECLIENTE, CPFCLIENTE, DATANASCIMENTOCLIENTE, TELEFONECLIENTE, CELULARCLIENTE, RUAAVNCLIENTE,
    NUMEROCLIENTE, CEPCLINTE, COMPLEMENTOCLIENTE, BAIRROCLIENTE, CIDADECLIENTE, EMAILCLIENTE;
    */
    
    private int idCliente;
    private String nomeCliente;
    private String cpfCliente;
    private Calendar dataNascimentoCliente;
    private String telefoneCliente;
    private String celularCliente;
    private String cepCliente;
    private String ruaAvnCliente;
    private String numeroCliente; // CASA DO CLIENTE (ENDEREÇO).
    private String bairroCliente;
    private String cidadeCliente;
    private String complementoCliente;
    private String emailCliente;

    /**
     * @return the idCliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * @return the nomeCliente
     */
    public String getNomeCliente() {
        return nomeCliente;
    }

    /**
     * @param nomeCliente the nomeCliente to set
     */
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    /**
     * @return the cpfCliente
     */
    public String getCpfCliente() {
        return cpfCliente;
    }

    /**
     * @param cpfCliente the cpfCliente to set
     */
    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    /**
     * @return the dataNascimentoCliente
     */
    public Calendar getDataNascimentoCliente() {
        return dataNascimentoCliente;
    }

    /**
     * @param dataNascimentoCliente the dataNascimentoCliente to set
     */
    public void setDataNascimentoCliente(Calendar dataNascimentoCliente) {
        this.dataNascimentoCliente = dataNascimentoCliente;
    }

    /**
     * @return the telefoneCliente
     */
    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    /**
     * @param telefoneCliente the telefoneCliente to set
     */
    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }

    /**
     * @return the celularCliente
     */
    public String getCelularCliente() {
        return celularCliente;
    }

    /**
     * @param celularCliente the celularCliente to set
     */
    public void setCelularCliente(String celularCliente) {
        this.celularCliente = celularCliente;
    }

    /**
     * @return the cepCliente
     */
    public String getCepCliente() {
        return cepCliente;
    }

    /**
     * @param cepCliente the cepCliente to set
     */
    public void setCepCliente(String cepCliente) {
        this.cepCliente = cepCliente;
    }

    /**
     * @return the ruaAvnCliente
     */
    public String getRuaAvnCliente() {
        return ruaAvnCliente;
    }

    /**
     * @param ruaAvnCliente the ruaAvnCliente to set
     */
    public void setRuaAvnCliente(String ruaAvnCliente) {
        this.ruaAvnCliente = ruaAvnCliente;
    }

    /**
     * @return the numeroCliente
     */
    public String getNumeroCliente() {
        return numeroCliente;
    }

    /**
     * @param numeroCliente the numeroCliente to set
     */
    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    /**
     * @return the bairroCliente
     */
    public String getBairroCliente() {
        return bairroCliente;
    }

    /**
     * @param bairroCliente the bairroCliente to set
     */
    public void setBairroCliente(String bairroCliente) {
        this.bairroCliente = bairroCliente;
    }

    /**
     * @return the cidadeCliente
     */
    public String getCidadeCliente() {
        return cidadeCliente;
    }

    /**
     * @param cidadeCliente the cidadeCliente to set
     */
    public void setCidadeCliente(String cidadeCliente) {
        this.cidadeCliente = cidadeCliente;
    }

    /**
     * @return the complementoCliente
     */
    public String getComplementoCliente() {
        return complementoCliente;
    }

    /**
     * @param complementoCliente the complementoCliente to set
     */
    public void setComplementoCliente(String complementoCliente) {
        this.complementoCliente = complementoCliente;
    }

    /**
     * @return the emailCliente
     */
    public String getEmailCliente() {
        return emailCliente;
    }

    /**
     * @param emailCliente the emailCliente to set
     */
    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }
}