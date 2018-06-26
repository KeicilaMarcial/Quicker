/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.tabelas;

import java.util.ArrayList;
import modelo.classes.Cliente;

/**
 *
 * @author Adonias
 */
public class ModeloTabelaCliente extends javax.swing.table.AbstractTableModel{
    
     ArrayList<Cliente> listaClientes = new ArrayList();
    
   Cliente cliente;
    
        public int getRowCount() {
        return listaClientes.size();
    }
         public int getColumnCount() {
        return 5;
    } 
        public String getColumnName(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0: return "Nome Completo";
            case 1: return "Telefone";
            case 2: return "Endereço";
            case 3: return "Complemento";
            case 4: return "E-mail";
        }
        return null;
    }
        
        public Object getValueAt(int rowIndex, int columnIndex) {
        cliente = listaClientes.get(rowIndex);
     
        switch(columnIndex)
        {
            
            case 0: return cliente.getNomeCliente();
            case 1: return cliente.getTelefoneCliente()+" / "+cliente.getCelularCliente();
            case 2: return cliente.getRuaAvnCliente()+", "+cliente.getNumeroCliente()+", "+cliente.getBairroCliente()+","+cliente.getCidadeCliente();
            case 3: return cliente.getComplementoCliente();
            case 4: return cliente.getEmailCliente();
        }
        return null;
        }
    
    /***
         * MÉTODOS PARA ALIMENTAÇÃO DA LISTA DE FUNCIONARIO.
         * @param listaClientes 
         */
        public void inserirlistaCliente(ArrayList<Cliente> listaClientes)
    {
        this.listaClientes = listaClientes;
    }
     public ArrayList<Cliente> retornalistaCliente()
    {
        return this.listaClientes;
    }
    
    
    
}

