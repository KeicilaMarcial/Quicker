/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.classes;

import org.apache.derby.client.am.Blob;

/**
 *
 * @author Adonias
 */
public class Pizza {
    /* ORDEM DOS GET'S E SET'S */
    /*
    idPizza, nomePizza, ingrediente1, ingrediente2, ingrediente3, ingrediente4,
    ingrediente5, ingrediente6, ingrediente7, ingrediente8, ingrediente9,
    ingrediente10, ingrediente11, ingrediente12, quantPorcao1, quantPorcao2,
    quantPorcao3, quantPorcao4, quantPorcao5, quantPorcao6, quantPorcao7,
    quantPorcao8, quantPorcao9, quantPorcao10, quantPorcao11, quantPorcao12,
    tamFatia, imagem
    */
    
    private int idPizza;
    private String nomePizza;
    private String ingrediente1;
    private String ingrediente2;
    private String ingrediente3;
    private String ingrediente4;
    private String ingrediente5;
    private String ingrediente6;
    private String ingrediente7;
    private String  ingrediente8;
    private String ingrediente9;
    private String ingrediente10; 
    private String ingrediente11;
    private String ingrediente12;
    private String quantPorcao1;
    private String quantPorcao2;
    private String quantPorcao3;
    private String quantPorcao4; 
    private String quantPorcao5; 
    private String quantPorcao6; 
    private String quantPorcao7;
    private String quantPorcao8; 
    private String quantPorcao9; 
    private String quantPorcao10;
    private String quantPorcao11; 
    private String quantPorcao12;
    private String tamFatia;
    private Blob imagem;

    /**
     * @return the idPizza
     */
    public int getIdPizza() {
        return idPizza;
    }

    /**
     * @param idPizza the idPizza to set
     */
    public void setIdPizza(int idPizza) {
        this.idPizza = idPizza;
    }

    /**
     * @return the nomePizza
     */
    public String getNomePizza() {
        return nomePizza;
    }

    /**
     * @param nomePizza the nomePizza to set
     */
    public void setNomePizza(String nomePizza) {
        this.nomePizza = nomePizza;
    }

    /**
     * @return the ingrediente1
     */
    public String getIngrediente1() {
        return ingrediente1;
    }

    /**
     * @param ingrediente1 the ingrediente1 to set
     */
    public void setIngrediente1(String ingrediente1) {
        this.ingrediente1 = ingrediente1;
    }

    /**
     * @return the ingrediente2
     */
    public String getIngrediente2() {
        return ingrediente2;
    }

    /**
     * @param ingrediente2 the ingrediente2 to set
     */
    public void setIngrediente2(String ingrediente2) {
        this.ingrediente2 = ingrediente2;
    }

    /**
     * @return the ingrediente3
     */
    public String getIngrediente3() {
        return ingrediente3;
    }

    /**
     * @param ingrediente3 the ingrediente3 to set
     */
    public void setIngrediente3(String ingrediente3) {
        this.ingrediente3 = ingrediente3;
    }

    /**
     * @return the ingrediente4
     */
    public String getIngrediente4() {
        return ingrediente4;
    }

    /**
     * @param ingrediente4 the ingrediente4 to set
     */
    public void setIngrediente4(String ingrediente4) {
        this.ingrediente4 = ingrediente4;
    }

    /**
     * @return the ingrediente5
     */
    public String getIngrediente5() {
        return ingrediente5;
    }

    /**
     * @param ingrediente5 the ingrediente5 to set
     */
    public void setIngrediente5(String ingrediente5) {
        this.ingrediente5 = ingrediente5;
    }

    /**
     * @return the ingrediente6
     */
    public String getIngrediente6() {
        return ingrediente6;
    }

    /**
     * @param ingrediente6 the ingrediente6 to set
     */
    public void setIngrediente6(String ingrediente6) {
        this.ingrediente6 = ingrediente6;
    }

    /**
     * @return the ingrediente7
     */
    public String getIngrediente7() {
        return ingrediente7;
    }

    /**
     * @param ingrediente7 the ingrediente7 to set
     */
    public void setIngrediente7(String ingrediente7) {
        this.ingrediente7 = ingrediente7;
    }

    /**
     * @return the ingrediente8
     */
    public String getIngrediente8() {
        return ingrediente8;
    }

    /**
     * @param ingrediente8 the ingrediente8 to set
     */
    public void setIngrediente8(String ingrediente8) {
        this.ingrediente8 = ingrediente8;
    }

    /**
     * @return the ingrediente9
     */
    public String getIngrediente9() {
        return ingrediente9;
    }

    /**
     * @param ingrediente9 the ingrediente9 to set
     */
    public void setIngrediente9(String ingrediente9) {
        this.ingrediente9 = ingrediente9;
    }

    /**
     * @return the ingrediente10
     */
    public String getIngrediente10() {
        return ingrediente10;
    }

    /**
     * @param ingrediente10 the ingrediente10 to set
     */
    public void setIngrediente10(String ingrediente10) {
        this.ingrediente10 = ingrediente10;
    }

    /**
     * @return the ingrediente11
     */
    public String getIngrediente11() {
        return ingrediente11;
    }

    /**
     * @param ingrediente11 the ingrediente11 to set
     */
    public void setIngrediente11(String ingrediente11) {
        this.ingrediente11 = ingrediente11;
    }

    /**
     * @return the ingrediente12
     */
    public String getIngrediente12() {
        return ingrediente12;
    }

    /**
     * @param ingrediente12 the ingrediente12 to set
     */
    public void setIngrediente12(String ingrediente12) {
        this.ingrediente12 = ingrediente12;
    }

    /**
     * @return the quantPorcao1
     */
    public String getQuantPorcao1() {
        return quantPorcao1;
    }

    /**
     * @param quantPorcao1 the quantPorcao1 to set
     */
    public void setQuantPorcao1(String quantPorcao1) {
        this.quantPorcao1 = quantPorcao1;
    }

    /**
     * @return the quantPorcao2
     */
    public String getQuantPorcao2() {
        return quantPorcao2;
    }

    /**
     * @param quantPorcao2 the quantPorcao2 to set
     */
    public void setQuantPorcao2(String quantPorcao2) {
        this.quantPorcao2 = quantPorcao2;
    }

    /**
     * @return the quantPorcao3
     */
    public String getQuantPorcao3() {
        return quantPorcao3;
    }

    /**
     * @param quantPorcao3 the quantPorcao3 to set
     */
    public void setQuantPorcao3(String quantPorcao3) {
        this.quantPorcao3 = quantPorcao3;
    }

    /**
     * @return the quantPorcao4
     */
    public String getQuantPorcao4() {
        return quantPorcao4;
    }

    /**
     * @param quantPorcao4 the quantPorcao4 to set
     */
    public void setQuantPorcao4(String quantPorcao4) {
        this.quantPorcao4 = quantPorcao4;
    }

    /**
     * @return the quantPorcao5
     */
    public String getQuantPorcao5() {
        return quantPorcao5;
    }

    /**
     * @param quantPorcao5 the quantPorcao5 to set
     */
    public void setQuantPorcao5(String quantPorcao5) {
        this.quantPorcao5 = quantPorcao5;
    }

    /**
     * @return the quantPorcao6
     */
    public String getQuantPorcao6() {
        return quantPorcao6;
    }

    /**
     * @param quantPorcao6 the quantPorcao6 to set
     */
    public void setQuantPorcao6(String quantPorcao6) {
        this.quantPorcao6 = quantPorcao6;
    }

    /**
     * @return the quantPorcao7
     */
    public String getQuantPorcao7() {
        return quantPorcao7;
    }

    /**
     * @param quantPorcao7 the quantPorcao7 to set
     */
    public void setQuantPorcao7(String quantPorcao7) {
        this.quantPorcao7 = quantPorcao7;
    }

    /**
     * @return the quantPorcao8
     */
    public String getQuantPorcao8() {
        return quantPorcao8;
    }

    /**
     * @param quantPorcao8 the quantPorcao8 to set
     */
    public void setQuantPorcao8(String quantPorcao8) {
        this.quantPorcao8 = quantPorcao8;
    }

    /**
     * @return the quantPorcao9
     */
    public String getQuantPorcao9() {
        return quantPorcao9;
    }

    /**
     * @param quantPorcao9 the quantPorcao9 to set
     */
    public void setQuantPorcao9(String quantPorcao9) {
        this.quantPorcao9 = quantPorcao9;
    }

    /**
     * @return the quantPorcao10
     */
    public String getQuantPorcao10() {
        return quantPorcao10;
    }

    /**
     * @param quantPorcao10 the quantPorcao10 to set
     */
    public void setQuantPorcao10(String quantPorcao10) {
        this.quantPorcao10 = quantPorcao10;
    }

    /**
     * @return the quantPorcao11
     */
    public String getQuantPorcao11() {
        return quantPorcao11;
    }

    /**
     * @param quantPorcao11 the quantPorcao11 to set
     */
    public void setQuantPorcao11(String quantPorcao11) {
        this.quantPorcao11 = quantPorcao11;
    }

    /**
     * @return the quantPorcao12
     */
    public String getQuantPorcao12() {
        return quantPorcao12;
    }

    /**
     * @param quantPorcao12 the quantPorcao12 to set
     */
    public void setQuantPorcao12(String quantPorcao12) {
        this.quantPorcao12 = quantPorcao12;
    }

    /**
     * @return the tamFatia
     */
    public String getTamFatia() {
        return tamFatia;
    }

    /**
     * @param tamFatia the tamFatia to set
     */
    public void setTamFatia(String tamFatia) {
        this.tamFatia = tamFatia;
    }

    /**
     * @return the imagem
     */
    public Blob getImagem() {
        return imagem;
    }

    /**
     * @param imagem the imagem to set
     */
    public void setImagem(Blob imagem) {
        this.imagem = imagem;
    }
}
