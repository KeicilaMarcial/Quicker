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
public class Funcionario {
    /* ORDEM DOS Get's E Set's
    ;*/
    private int IdFuncionario;
    private String nomeFuncionario;
    private String cpfFuncionario;
    private Calendar dataNascimentoFuncionario;
    private String pisFuncionario;
    private String telefoneFuncionario;
    private String celularFuncionario;
    private String cepFuncionario;
    private String ruaAvnFuncionario;
    private String numeroFuncionario; //NUMERO DA CASA (ENDEREÇO).
    private String bairroFuncionario;
    private String cidadeFuncionario;
    private String estadoFuncionario;;
    private Calendar dataAdamissaoFuncionario;
    private String situacaoFuncionario;
    private Calendar dataDemissaoFuncionario;
    private Calendar dataRetornoFuncionario;
    private String cargoFuncionario;
    private String permissaoFuncionario;
    private String nomeUsuario;
    private String senhaFuncionario;
    
    /**
     * @return the nomeFuncionario
     */
    public String getNomeFuncionario() {
        return nomeFuncionario;
    }

    /**
     * @param nomeFuncionario the nomeFuncionario to set
     */
    public void setNomeFuncionario(String nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    /**
     * @return the cpfFuncionario
     */
    public String getCpfFuncionario() {
        return cpfFuncionario;
    }

    /**
     * @param cpfFuncionario the cpfFuncionario to set
     */
    public void setCpfFuncionario(String cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }

    /**
     * @return the pisFuncionario
     */
    public String getPisFuncionario() {
        return pisFuncionario;
    }

    /**
     * @param pisFuncionario the pisFuncionario to set
     */
    public void setPisFuncionario(String pisFuncionario) {
        this.pisFuncionario = pisFuncionario;
    }

    /**
     * @return the telefoneFuncionario
     */
    public String getTelefoneFuncionario() {
        return telefoneFuncionario;
    }

    /**
     * @param telefoneFuncionario the telefoneFuncionario to set
     */
    public void setTelefoneFuncionario(String telefoneFuncionario) {
        this.telefoneFuncionario = telefoneFuncionario;
    }

    /**
     * @return the celularFuncionario
     */
    public String getCelularFuncionario() {
        return celularFuncionario;
    }

    /**
     * @param celularFuncionario the celularFuncionario to set
     */
    public void setCelularFuncionario(String celularFuncionario) {
        this.celularFuncionario = celularFuncionario;
    }

    /**
     * @return the ruaAvnFuncionario
     */
    public String getRuaAvnFuncionario() {
        return ruaAvnFuncionario;
    }

    /**
     * @param ruaAvnFuncionario the ruaAvnFuncionario to set
     */
    public void setRuaAvnFuncionario(String ruaAvnFuncionario) {
        this.ruaAvnFuncionario = ruaAvnFuncionario;
    }

    /**
     * @return the numeroFuncionario
     */
    public String getNumeroFuncionario() {
        return numeroFuncionario;
    }

    /**
     * @param numeroFuncionario the numeroFuncionario to set
     */
    public void setNumeroFuncionario(String numeroFuncionario) {
        this.numeroFuncionario = numeroFuncionario;
    }

    /**
     * @return the bairroFuncionario
     */
    public String getBairroFuncionario() {
        return bairroFuncionario;
    }

    /**
     * @param bairroFuncionario the bairroFuncionario to set
     */
    public void setBairroFuncionario(String bairroFuncionario) {
        this.bairroFuncionario = bairroFuncionario;
    }

    /**
     * @return the cidadeFuncionario
     */
    public String getCidadeFuncionario() {
        return cidadeFuncionario;
    }

    /**
     * @param cidadeFuncionario the cidadeFuncionario to set
     */
    public void setCidadeFuncionario(String cidadeFuncionario) {
        this.cidadeFuncionario = cidadeFuncionario;
    }

    /**
     * @return the estadoFuncionario
     */
    public String getEstadoFuncionario() {
        return estadoFuncionario;
    }

    /**
     * @param estadoFuncionario the estadoFuncionario to set
     */
    public void setEstadoFuncionario(String estadoFuncionario) {
        this.estadoFuncionario = estadoFuncionario;
    }

    /**
     * @return the dataAdamissaoFuncionario
     */
    public Calendar getDataAdamissaoFuncionario() {
        return dataAdamissaoFuncionario;
    }

    /**
     * @param dataAdamissaoFuncionario the dataAdamissaoFuncionario to set
     */
    public void setDataAdamissaoFuncionario(Calendar dataAdamissaoFuncionario) {
        this.dataAdamissaoFuncionario = dataAdamissaoFuncionario;
    }

    /**
     * @return the situacaoFuncionario
     */
    public String getSituacaoFuncionario() {
        return situacaoFuncionario;
    }

    /**
     * @param situacaoFuncionario the situacaoFuncionario to set
     */
    public void setSituacaoFuncionario(String situacaoFuncionario) {
        this.situacaoFuncionario = situacaoFuncionario;
    }

    /**
     * @return the dataDemissaoFuncionario
     */
    public Calendar getDataDemissaoFuncionario() {
        return dataDemissaoFuncionario;
    }

    /**
     * @param dataDemissaoFuncionario the dataDemissaoFuncionario to set
     */
    public void setDataDemissaoFuncionario(Calendar dataDemissaoFuncionario) {
        this.dataDemissaoFuncionario = dataDemissaoFuncionario;
    }

    /**
     * @return the dataRetornoFuncionario
     */
    public Calendar getDataRetornoFuncionario() {
        return dataRetornoFuncionario;
    }

    /**
     * @param dataRetornoFuncionario the dataRetornoFuncionario to set
     */
    public void setDataRetornoFuncionario(Calendar dataRetornoFuncionario) {
        this.dataRetornoFuncionario = dataRetornoFuncionario;
    }

    /**
     * @return the dataNascimentoFuncionario
     */
    public Calendar getDataNascimentoFuncionario() {
        return dataNascimentoFuncionario;
    }

    /**
     * @param dataNascimentoFuncionario the dataNascimentoFuncionario to set
     */
    public void setDataNascimentoFuncionario(Calendar dataNascimentoFuncionario) {
        this.dataNascimentoFuncionario = dataNascimentoFuncionario;
    }

    /**
     * @return the cargoFuncionario
     */
    public String getCargoFuncionario() {
        return cargoFuncionario;
    }

    /**
     * @param cargoFuncionario the cargoFuncionario to set
     */
    public void setCargoFuncionario(String cargoFuncionario) {
        this.cargoFuncionario = cargoFuncionario;
    }

    /**
     * @return the nomeUsuario
     */
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     * @param nomeUsuario the nomeUsuario to set
     */
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    /**
     * @return the cepFuncionario
     */
    public String getCepFuncionario() {
        return cepFuncionario;
    }

    /**
     * @param cepFuncionario the cepFuncionario to set
     */
    public void setCepFuncionario(String cepFuncionario) {
        this.cepFuncionario = cepFuncionario;
    }

    /**
     * @return the senhaFuncionario
     */
    public String getSenhaFuncionario() {
        return senhaFuncionario;
    }

    /**
     * @param senhaFuncionario the senhaFuncionario to set
     */
    public void setSenhaFuncionario(String senhaFuncionario) {
        this.senhaFuncionario = senhaFuncionario;
    }

    /**
     * @return the permissaoFuncionario
     */
    public String getPermissaoFuncionario() {
        return permissaoFuncionario;
    }

    /**
     * @param permissaoFuncionario the permissaoFuncionario to set
     */
    public void setPermissaoFuncionario(String permissaoFuncionario) {
        this.permissaoFuncionario = permissaoFuncionario;
    }

    /**
     * @return the IdFuncionario
     */
    public int getIdFuncionario() {
        return IdFuncionario;
    }

    /**
     * @param IdFuncionario the IdFuncionario to set
     */
    public void setIdFuncionario(int IdFuncionario) {
        this.IdFuncionario = IdFuncionario;
    }
}
