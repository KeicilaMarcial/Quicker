/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.tabelas;

import java.util.ArrayList;
import modelo.classes.Fornecedor;

/**
 *
 * @author Adonias
 */
public class ModeloTabelaFornecedor extends javax.swing.table.AbstractTableModel{
    
  ArrayList<Fornecedor> listaFornecedores = new ArrayList();
    
    Fornecedor fornecedor;
    
        public int getRowCount() {
        return listaFornecedores.size();
    } 
          public int getColumnCount() {
        return 5;
    }
    
      public String getColumnName(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0: return "Nome Fantasia";
            case 1: return "Telefone Fornecedor";
            case 2: return "Representante";
            case 3: return "Contatos do representante";
            case 4: return "Cidade";
          
        }
        return null;
    }
      public Object getValueAt(int rowIndex, int columnIndex) {
        fornecedor = listaFornecedores.get(rowIndex);
        
        switch(columnIndex)
        {
            
            case 0: return fornecedor.getNomeFantasiaFornecedor();
            case 1: return fornecedor.getTelefoneFornecedor();
            case 2: return fornecedor.getRepresentanteFornecedor();
            case 3: return fornecedor.getTelefoneRepresentanteFornecedor()+" | "+fornecedor.getCelularRepresentanteFornecedor();
            case 4: return fornecedor.getCidadeFornecedor();
        }
        return null;
    }
      /***
         * MÉTODOS PARA ALIMENTAÇÃO DA LISTA DE FORNECEDORES.
         * @param listaFornecedores 
         */
        public void inserirlistaFornecedores(ArrayList<Fornecedor> listaFornecedores)
    {
        this.listaFornecedores = listaFornecedores;
    }
    
    public ArrayList<Fornecedor> retornalistaFornecedores()
    {
        return this.listaFornecedores;
    }
    
}
