/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.tabelas;

import java.util.ArrayList;
import modelo.classes.ProdutoEstoque;

/**
 *
 * @author alunov2
 */
public class ModeloTabelaProdutoEstoque extends javax.swing.table.AbstractTableModel{
        
     ArrayList<ProdutoEstoque> listaProdutosEstoque = new ArrayList();
    
   ProdutoEstoque produtoEstoque;
    
        public int getRowCount() {
        return listaProdutosEstoque.size();
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
        produtoEstoque = listaProdutosEstoque.get(rowIndex);
     
        switch(columnIndex)
        {
            
            case 0: return produtoEstoque.getNomeProdutoEstoque();
            case 1: return produtoEstoque.getTipoProdutoEstoque();
            case 2: return produtoEstoque.getUnidadeMedidaEstoque();
            
        }
        return null;
        }
    
    /***
         * MÉTODOS PARA ALIMENTAÇÃO DA LISTA DE FUNCIONARIO.
         * @param listaClientes 
         */
        public void inserirlistaProdutoEstoque(ArrayList<ProdutoEstoque> listaProdutosEstoque)
    {
        this.listaProdutosEstoque = listaProdutosEstoque;
    }
     public ArrayList<ProdutoEstoque> retornalistaProdutosEstoque()
    {
        return this.listaProdutosEstoque;
    }
    
    
    
}
