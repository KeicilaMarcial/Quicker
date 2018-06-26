/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bancodados;

import java.beans.Statement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import modelo.classes.Cliente;

/**
 *
 * @author Adonias
 */
public class ClienteBD extends ConexaoBanco{
    
    DecimalFormat formatador=new DecimalFormat("00");
    private ResultSet tabelaRetornada;
    
    /***
     * METODO PARA INSERIR UM NOVO CLIENTE NO BANCO DE DADOS (QUICKER.CLIENTES)
     * O METODO É CHAMADO PELA 'TELA CLIENTE' ABA 'CADASTRAR CLIENTE'.
     * @param clienteRecebido 
     */
    public void inserirCliente(Cliente clienteRecebido)
    {
        try{
            conectaBanco();
            
            /* OREDEM DAS VARIAVEIS*/
            /*
            NOMECLIENTE, CPFCLIENTE, DATANASCIMENTOCLIENTE, TELEFONECLIENTE, 
            CELULARCLIENTE, CEPCLIENTE, RUAAVNCLIENTE, NUMEROCLIENTE, 
            BAIRROCLIENTE, CIDADECLIENTE, COMPLEMENTOCLIENTE, EMAILCLIENTE
            */
            
            stm= con.createStatement();
            sql="INSERT INTO QUICKER.CLIENTES (NOMECLIENTE, CPFCLIENTE, DATANASCIMENTOCLIENTE, TELEFONECLIENTE, CELULARCLIENTE, CEPCLIENTE, RUAAVNCLIENTE, NUMEROCLIENTE, BAIRROCLIENTE, CIDADECLIENTE, COMPLEMENTOCLIENTE, EMAILCLIENTE) "
            +"VALUES ('"+clienteRecebido.getNomeCliente()+"', "
            +"'"+clienteRecebido.getCpfCliente()+"', "
            +"'"+clienteRecebido.getDataNascimentoCliente().get(Calendar.YEAR)+"-"+formatador.format(clienteRecebido.getDataNascimentoCliente().get(Calendar.MONTH))+"-"+formatador.format(clienteRecebido.getDataNascimentoCliente().get(Calendar.DAY_OF_MONTH))+"',"
            +"'"+clienteRecebido.getTelefoneCliente()+"', "
            +"'"+clienteRecebido.getCelularCliente()+"', "
            +"'"+clienteRecebido.getCepCliente()+"', "
            +"'"+clienteRecebido.getRuaAvnCliente()+"', "
            +"'"+clienteRecebido.getNumeroCliente()+"', "
            +"'"+clienteRecebido.getBairroCliente()+"', "
            +"'"+clienteRecebido.getCidadeCliente()+"', "
            +"'"+clienteRecebido.getComplementoCliente()+"', "
            +"'"+clienteRecebido.getEmailCliente()+"')";
            
            System.out.println("SQL INSERÇÃO: "+sql);
            stm.executeUpdate(sql);//EXECUTANDO COMANDO ACIMA
            JOptionPane.showMessageDialog(null, "Cliente "+clienteRecebido.getNomeCliente()+"\nCadastrado Com Sucesso!");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally{
            desconectaBanco();
        }
    }
    
    /***
     * METODO PARA SELEÇÃO DE TODOS OS CLIENTES CADASTRADOS, PARA EXIBI-LOS
     * NA TABELA DE GERENCIA NA ABA 'GERENCIAR CLIENTES'.
     * BANCO DE DADOS>QUICKER.CLIENTE
     * @return 
     */
    public ArrayList<Cliente> selecionarTodosClientes()
    {
        ArrayList<Cliente> listaCliente=new ArrayList();
        Cliente cliente=new Cliente();
        
        try{
            conectaBanco();
            
            /* OREDEM DAS VARIAVEIS*/
            /*
            NOMECLIENTE, CPFCLIENTE, DATANASCIMENTOCLIENTE, TELEFONECLIENTE, 
            CELULARCLIENTE, CEPCLIENTE, RUAAVNCLIENTE, NUMEROCLIENTE, 
            BAIRROCLIENTE, CIDADECLIENTE, COMPLEMENTOCLIENTE, EMAILCLIENTE
            */
            
             stm = con.createStatement();
            
             sql = "SELECT * "
                  +"FROM CLIENTES";
             
             tabelaRetornada=stm.executeQuery(sql);
             
             while(tabelaRetornada.next())
             {
                 cliente=new Cliente();
                 cliente.setIdCliente(tabelaRetornada.getInt("IDCLIENTE"));
                 cliente.setNomeCliente(tabelaRetornada.getString("NOMECLIENTE"));
                 cliente.setCpfCliente(tabelaRetornada.getString("CPFCLIENTE"));
                 /*  CONVERTENDO DATA DE NASCIMENTO DO CLIENTE*/
                 Calendar calNascimento=Calendar.getInstance();
                 calNascimento.setTime(tabelaRetornada.getDate("DATANASCIMENTOCLIENTE"));
                 cliente.setDataNascimentoCliente(calNascimento);
                 /* PROSSEGUINCO COM A COLETA DE DADOS*/
                 cliente.setTelefoneCliente(tabelaRetornada.getString("TELEFONECLIENTE"));
                 cliente.setCelularCliente(tabelaRetornada.getString("CELULARCLIENTE"));
                 cliente.setCepCliente(tabelaRetornada.getString("CEPCLIENTE"));
                 cliente.setRuaAvnCliente(tabelaRetornada.getString("RUAAVNCLIENTE"));
                 cliente.setNumeroCliente(tabelaRetornada.getString("NUMEROCLIENTE"));
                 cliente.setBairroCliente(tabelaRetornada.getString("BAIRROCLIENTE"));
                 cliente.setCidadeCliente(tabelaRetornada.getString("CIDADECLIENTE"));
                 cliente.setComplementoCliente(tabelaRetornada.getString("COMPLEMENTOCLIENTE"));
                 cliente.setEmailCliente(tabelaRetornada.getString("EMAILCLIENTE"));
                 
                 listaCliente.add(cliente);
             }
            }catch(Exception e)
            {
                e.printStackTrace();
                    System.out.println(e.getMessage());
            }finally{
                desconectaBanco();
            }

          return listaCliente;
        
    }
    
     /***
     * METODO RESPONSAVEL POR 'PEGAR' O FORNECEDOR SELECIONADO E ALTERAR UM OU
     * TODOS OS DADOS QUE SÃO EXIBIDOS NA TELA DE CADASTRO DE FORNECEDORES,
     * E REINSERI-LO NO BANCO DE DADOS (QUICKER.FORNECEDORES) COM AS ALTERAÇÕES
     * REALIZAS.
     * @param idAntigoFornecedor
     * @param fornecedorNovo 
     */
    public void atualizarCliente(Cliente clienteNovo, Cliente clienteAntigo)
    {
        
        try
        {
           conectaBanco();
            
            stm = con.createStatement();
            
            sql = "UPDATE QUICKER.CLIENTES " +
                  "SET  NOMECLIENTE='"+clienteNovo.getNomeCliente()+"' "
                     +",CPFCLIENTE='"+clienteNovo.getCpfCliente()+"' "
                    +",DATANASCIMENTOCLIENTE='"+clienteNovo.getDataNascimentoCliente().get(Calendar.YEAR)+"-"+formatador.format(clienteNovo.getDataNascimentoCliente().get(Calendar.MONTH))+"-"+formatador.format(clienteNovo.getDataNascimentoCliente().get(Calendar.DAY_OF_MONTH))+"' " 
                    +",TELEFONECLIENTE='"+clienteNovo.getTelefoneCliente()+"' "
                    +",CELULARCLIENTE='"+clienteNovo.getCelularCliente()+"' "
                    +",CEPCLIENTE='"+clienteNovo.getCepCliente()+"' "
                    +",RUAAVNCLIENTE='"+clienteNovo.getRuaAvnCliente()+"' "
                    +",NUMEROCLIENTE='"+clienteNovo.getNumeroCliente()+"' "
                    +",BAIRROCLIENTE='"+clienteNovo.getBairroCliente()+"' "
                    +",CIDADECLIENTE='"+clienteNovo.getCidadeCliente()+"' "
                    +",COMPLEMENTOCLIENTE='"+clienteNovo.getComplementoCliente()+"' "
                    +",EMAILCLIENTE='"+clienteNovo.getEmailCliente()+"'"
                   +"WHERE IDCLIENTE = "+clienteAntigo.getIdCliente();
                   
                   System.out.println(sql);
            
                   stm.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(null, "INFORMAÇÕES ATUALIZADAS COM SUCESSO!", "FINALIZADO", 3);                     
            
        } catch(Exception e)
            {
               e.printStackTrace();
            }finally
                {
                     desconectaBanco();
                }   
}
     /***
     * METODO RESPONSAVEL POR REMOVER O ClIENTE SELECINADO DA LISTA
     * DE ClIENTES EXIBIDA NA TABELA E DO BANCO DE DADOS (QUICKER.ClIENTES).
     * @param idCliente
     * @param  
     */
     public void deletarCliente(int idCliente, String toString) {
        
         try
        {
           conectaBanco();
            
            stm = con.createStatement();
            
            sql = "DELETE FROM QUICKER.CLIENTES WHERE IDClIENTE="+idCliente;
            System.out.println(sql);
            System.out.println("ID do ClIENTE = "+idCliente);
             stm.executeUpdate(sql);
            
            
           // JOptionPane.showMessageDialog(null, "Funionario "+NomeFuncionario+" foi deletado com sucesso!", "Aviso", 3); 
            JOptionPane.showMessageDialog(null, "Cliente foi deletado com sucesso!", "Aviso", 3); 
            
            
        }catch(Exception e)
            {
               e.printStackTrace();
            }finally
                {
                     desconectaBanco();
                }
    }
}
