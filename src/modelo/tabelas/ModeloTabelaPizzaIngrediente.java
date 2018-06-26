/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.tabelas;

import java.util.ArrayList;
import modelo.classes.ProdutoMateria;

/**
 *
 * @author Adonias
 */
public class ModeloTabelaPizzaIngrediente extends javax.swing.table.AbstractTableModel{
        
     ArrayList<ProdutoMateria> listaProdutoMateriaingrediente = new ArrayList();
    
   ProdutoMateria produtoMateria;
    
        public int getRowCount() {
        return listaProdutoMateriaingrediente.size();
    }
         public int getColumnCount() {
        return 1;
    } 
        public String getColumnName(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0: return "NOME INGREDIENTE";
        }
        return null;
    }
        
        public Object getValueAt(int rowIndex, int columnIndex) {
        produtoMateria = listaProdutoMateriaingrediente.get(rowIndex);
     
        switch(columnIndex)
        {
            case 0: return produtoMateria.getNomeProdutoMateria();
            
        }
        return null;
        }
    
    /***
         * MÉTODOS PARA ALIMENTAÇÃO DA LISTA DE FUNCIONARIO.
         * @param listaClientes 
         */
        public void inserirlistaPizzaIngrediente(ArrayList<ProdutoMateria> listaProdutoMateria)
    {
        this.listaProdutoMateriaingrediente = listaProdutoMateria;
    }
     public ArrayList<ProdutoMateria> retornalistaProdutoMateriaingrediente()
    {
        return this.listaProdutoMateriaingrediente;
    }
}