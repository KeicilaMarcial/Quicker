/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.tabelas;

import java.util.ArrayList;
import modelo.classes.Pizza;

/**
 *
 * @author Adonias
 */
public class ModeloTabelaPizza extends javax.swing.table.AbstractTableModel{
    
     ArrayList<Pizza> listaPizza = new ArrayList();
    
   Pizza pizza;
    
        public int getRowCount() {
        return listaPizza.size();
    }
         public int getColumnCount() {
        return 2;
    } 
        public String getColumnName(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0: return "Nome Pizza";
            case 1: return "Tamanho/Fatias";
            //case 2: return "Imagem";
        }
        return null;
    }
        
        public Object getValueAt(int rowIndex, int columnIndex) {
        pizza = listaPizza.get(rowIndex);
     
        switch(columnIndex)
        {
            
            case 0: return pizza.getNomePizza();
            case 1: return pizza.getTamFatia();
            //case 2: return pizza.getImagem();
        }
        return null;
        }
    
    /***
         * MÉTODOS PARA ALIMENTAÇÃO DA LISTA DE PIZZAS
         * @param listaPizza 
         */
        public void inserirlistaPizza(ArrayList<Pizza> listaPizza)
    {
        this.listaPizza = listaPizza;
    }
     public ArrayList<Pizza> retornalistaPizzas()
    {
        return this.listaPizza;
    }

}