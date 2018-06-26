/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.tabelas;

import java.util.ArrayList;
import modelo.classes.ProdutoEstoque;
import modelo.classes.ProdutoMateria;

/**
 *
 * @author alunov2
 */
public class ModeloTabelaProdutoMateria extends javax.swing.table.AbstractTableModel{
        
     ArrayList<ProdutoMateria> listaProdutoMateria = new ArrayList();
    
   ProdutoMateria produtoMateria;
    
        public int getRowCount() {
        return listaProdutoMateria.size();
    }
         public int getColumnCount() {
        return 3;
    } 
        public String getColumnName(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0: return "Nome Produto";
            case 1: return "Tipo Produto";
            case 2: return "Unid. Medida";
        }
        return null;
    }
        
        public Object getValueAt(int rowIndex, int columnIndex) {
        produtoMateria = listaProdutoMateria.get(rowIndex);
     
        switch(columnIndex)
        {
            
            case 0: return produtoMateria.getNomeProdutoMateria();
            case 1: return produtoMateria.getTipoProdutoMateria();
            case 2: return produtoMateria.getUnidadeMedidaProdutoMateria();
            
        }
        return null;
        }
    
    /***
         * MÉTODOS PARA ALIMENTAÇÃO DA LISTA DE FUNCIONARIO.
         * @param listaClientes 
         */
        public void inserirlistaProdutoMateria(ArrayList<ProdutoMateria> listaProdutoMateria)
    {
        this.listaProdutoMateria = listaProdutoMateria;
    }
     public ArrayList<ProdutoMateria> retornalistaProdutosMateria()
    {
        return this.listaProdutoMateria;
    }
    
    
    
}
