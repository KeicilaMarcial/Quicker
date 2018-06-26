/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo.tabelas;

import java.util.ArrayList;
import modelo.classes.Funcionario;

/**
 *
 * @author Adonias
 */
public class ModeloTabelaFuncionario extends javax.swing.table.AbstractTableModel
{
     ArrayList<Funcionario> listaFuncionarios = new ArrayList();
    
    Funcionario funcionario;
    
        public int getRowCount() {
        return listaFuncionarios.size();
    }
        
        public int getColumnCount() {
        return 5;
    }
    
      public String getColumnName(int columnIndex)
    {
        switch(columnIndex)
        {
            case 0: return "Nome";
            case 1: return "CPF";
            case 2: return "Telefones";
            case 3: return "Endereço";
            case 4: return "Cargo";
        }
        return null;
    }
       public Object getValueAt(int rowIndex, int columnIndex) {
        funcionario = listaFuncionarios.get(rowIndex);
        
        switch(columnIndex)
        {
            
            case 0: return funcionario.getNomeFuncionario();
            case 1: return funcionario.getCpfFuncionario();
            case 2: return funcionario.getTelefoneFuncionario() +" | "+funcionario.getCelularFuncionario();
            case 3: return funcionario.getRuaAvnFuncionario()+" , "+funcionario.getNumeroFuncionario()+" , "+funcionario.getBairroFuncionario()+" , "+funcionario.getCidadeFuncionario();
            case 4: return funcionario.getCargoFuncionario();
        }
        return null;
    }
        /***
         * MÉTODOS PARA ALIMENTAÇÃO DA LISTA DE FUNCIONARIO.
         * @param listaFuncionarios 
         */
        public void inserirlistaFuncionarios(ArrayList<Funcionario> listaFuncionarios)
    {
        this.listaFuncionarios = listaFuncionarios;
    }
    
    public ArrayList<Funcionario> retornalistaFuncioarios()
    {
        return this.listaFuncionarios;
    }
    
    
    
}
