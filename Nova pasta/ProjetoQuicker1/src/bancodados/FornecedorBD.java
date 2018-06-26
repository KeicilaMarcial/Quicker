/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bancodados;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.classes.Fornecedor;

/**
 *
 * @author Adonias
 */
public class FornecedorBD extends ConexaoBanco{
    
    
    /**
     * Método responsável por inserir um Novo fornecedor no banco.O método
     * é chamado pela tela de Cadastro de fornecedores
     * BANCO DE DADOS(QUICKER.FORNECEDORES).
     * @param fornecedorRecebido 
     */
    public void inserirFornecedor(Fornecedor fornecedorRecebido)
    {
         try
        {
            conectaBanco(); // Conectando a base de dados
            
            stm = con.createStatement(); // "Criando o botão de execução de comandos SQL"
            
         /*  IDFORNECEDOR, RAZAOSOCIALFORNECEDOR, NOMEFANTASIAFORNECEDOR, CNPJFORNECEDOR,
            TELEFONEFORNECEDOR, CEPFORNECEDOR, RUAAVNFORNECEDOR, NUMEROFORNECEDOR,
            BAIRROFORNECEDOR, CIDADEFORNECEDOR, ESTADOFORNECEDOR, REPRESENTANTEFORNECEDOR, 
            TELEFONEREPRESENTANTE, CELULARREPRESENTANTE, EMAILREPRESENTATNTE*/

            sql = "INSERT INTO QUICKER.FORNECEDORES(RAZAOSOCIALFORNECEDOR, NOMEFANTASIAFORNECEDOR, CNPJFORNECEDOR, TELEFONEFORNECEDOR, CEPFORNECEDOR, RUAAVNFORNECEDOR, NUMEROFORNECEDOR, BAIRROFORNECEDOR, CIDADEFORNECEDOR, ESTADOFORNECEDOR, REPRESENTANTEFORNECEDOR, TELEFONEREPRESENTANTE, CELULARREPRESENTANTE, EMAILREPRESENTATNTE)"+
                    "VALUES ('"+fornecedorRecebido.getRazaoSocialFornecedor()+"',"
                                +"'"+fornecedorRecebido.getNomeFantasiaFornecedor()+"',"
                                +"'"+fornecedorRecebido.getCnpjFornecedor()+"',"
                                +"'"+fornecedorRecebido.getTelefoneFornecedor()+"',"
                                +"'"+fornecedorRecebido.getCepFornecedor()+"',"
                                +"'"+fornecedorRecebido.getRuaAvnFornecedor()+"',"
                                +"'"+fornecedorRecebido.getNumeroFornecedor()+"',"
                                +"'"+fornecedorRecebido.getBairroFornecedor()+"',"
                                +"'"+fornecedorRecebido.getCidadeFornecedor()+"',"
                                +"'"+fornecedorRecebido.getEstadoFornecedor()+"',"
                                +"'"+fornecedorRecebido.getRepresentanteFornecedor()+"',"
                                +"'"+fornecedorRecebido.getTelefoneRepresentanteFornecedor()+"',"
                                +"'"+fornecedorRecebido.getCelularRepresentanteFornecedor()+"',"
                                +"'"+fornecedorRecebido.getEmailRepresentanteFornecedor()+"')";
            
                        System.out.println("SQL DE INSERÇÃO: "+sql);
            
                        stm.executeUpdate(sql); // Executando o SQL escrito acima
            
            
            JOptionPane.showMessageDialog(null, "FORNECEDOR CADASTRADO COM SUCESSO", "FINALIZADO", 1);                     
        }catch(Exception e)
            {
                e.printStackTrace();
            }finally
                    {
                        desconectaBanco();
                    }
      
    }
         /***
    * METODO RESPONSAVEL POR SELECIONAR TODOS OS FORNECEDORES
    * DO BANCO DADOS (QUICKER.FORNECEDORES) E INSERI-LOS EM 
    * UMA LISTA DE FORNECEDORES, QUE SERÁ INSERIDA NO 
    * MODELO DE TABELA (modeloTabelafornecedores).
    * @return 
    */ 
   public ArrayList<Fornecedor> selecionarTodosFornecedores()
    {   
        ArrayList<Fornecedor> listaFornecedores = new ArrayList();
        Fornecedor fornecedor = new Fornecedor();
        try
        {
             conectaBanco();
            
          
            
            stm = con.createStatement();
            
            sql = "SELECT * "
                 +"FROM QUICKER.FORNECEDORES ";
            
            tabelaRetornada = stm.executeQuery(sql);
            
             while(tabelaRetornada.next()) // Enquanto houverem linhas na tabela para serem lidas
            {
                  fornecedor=new Fornecedor();
                
                  fornecedor.setIdFornecedor(tabelaRetornada.getInt("IDFORNECEDOR"));
                  fornecedor.setRazaoSocialFornecedor(tabelaRetornada.getString("RAZAOSOCIALFORNECEDOR"));
                  fornecedor.setNomeFantasiaFornecedor(tabelaRetornada.getString("NOMEFANTASIAFORNECEDOR"));
                  fornecedor.setCnpjFornecedor(tabelaRetornada.getString("CNPJFORNECEDOR"));
                  fornecedor.setRuaAvnFornecedor(tabelaRetornada.getString("RUAAVNFORNECEDOR"));
                  fornecedor.setNumeroFornecedor(tabelaRetornada.getString("NUMEROFORNECEDOR"));
                  fornecedor.setBairroFornecedor(tabelaRetornada.getString("BAIRROFORNECEDOR"));
                  fornecedor.setCidadeFornecedor(tabelaRetornada.getString("CIDADEFORNECEDOR"));
                  fornecedor.setEstadoFornecedor(tabelaRetornada.getString("ESTADOFORNECEDOR"));
                  fornecedor.setRepresentanteFornecedor(tabelaRetornada.getString("REPRESENTANTEFORNECEDOR"));
                  fornecedor.setTelefoneRepresentanteFornecedor(tabelaRetornada.getString("TELEFONEREPRESENTANTE"));
                  fornecedor.setTelefoneFornecedor(tabelaRetornada.getString("TELEFONEFORNECEDOR"));
                  fornecedor.setCelularRepresentanteFornecedor(tabelaRetornada.getString("CELULARREPRESENTANTE"));
                  fornecedor.setEmailRepresentanteFornecedor(tabelaRetornada.getString("EMAILREPRESENTATNTE"));
                  fornecedor.setCepFornecedor(tabelaRetornada.getString("CEPFORNECEDOR"));
                  
                  listaFornecedores.add(fornecedor);
    
            }
        }catch(Exception e)
           {
               e.printStackTrace();
               System.out.println(e.getMessage());
           }  finally
               {
                 desconectaBanco();
               }
                     return  listaFornecedores  ;
    
    }  
         /***
     * METODO RESPONSAVEL POR 'PEGAR' O FORNECEDOR SELECIONADO E ALTERAR UM OU
     * TODOS OS DADOS QUE SÃO EXIBIDOS NA TELA DE CADASTRO DE FORNECEDORES,
     * E REINSERI-LO NO BANCO DE DADOS (QUICKER.FORNECEDORES) COM AS ALTERAÇÕES
     * REALIZAS.
     * @param idAntigoFornecedor
     * @param fornecedorNovo 
     */
    public void atualizarFornecedor(Fornecedor fornecedorNovo, Fornecedor fornecedorAntigo)
    {
        try
        {
           conectaBanco();
            
            stm = con.createStatement();
            
            sql = "UPDATE QUICKER.FORNECEDORES " +
                  "SET RAZAOSOCIALFORNECEDOR='"+fornecedorNovo.getRazaoSocialFornecedor()+"' "
                   +", NOMEFANTASIAFORNECEDOR='"+fornecedorNovo.getNomeFantasiaFornecedor()+"' "
                   +",CNPJFORNECEDOR='"+fornecedorNovo.getCnpjFornecedor()+"' "
                   +", TELEFONEFORNECEDOR='"+fornecedorNovo.getTelefoneFornecedor()+"'"
                   +", CEPFORNECEDOR='"+fornecedorNovo.getCepFornecedor()+"' "
                   +",RUAAVNFORNECEDOR='"+fornecedorNovo.getRuaAvnFornecedor()+"' "
                   +", NUMEROFORNECEDOR='"+fornecedorNovo.getNumeroFornecedor()+"'"
                   +", BAIRROFORNECEDOR='"+fornecedorNovo.getBairroFornecedor()+"' "
                   +",CIDADEFORNECEDOR='"+fornecedorNovo.getCidadeFornecedor()+"' "
                   +", ESTADOFORNECEDOR='"+fornecedorNovo.getEstadoFornecedor()+"' "
                   +", REPRESENTANTEFORNECEDOR='"+fornecedorNovo.getRepresentanteFornecedor()+"' "
                   +", TELEFONEREPRESENTANTE='"+fornecedorNovo.getTelefoneRepresentanteFornecedor()+"' "
                   +", CELULARREPRESENTANTE='"+fornecedorNovo.getCelularRepresentanteFornecedor()+"' "
                   +", EMAILREPRESENTATNTE='"+fornecedorNovo.getEmailRepresentanteFornecedor()+"'"
                   +"WHERE IDFORNECEDOR = "+fornecedorAntigo.getIdFornecedor();
                   
                   System.out.println(sql);
            
                   stm.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(null, "FORNECEDOR ALTERADO COM SUCESSO!", "FINALIZADO", 3);                     
            
        } catch(Exception e)
            {
               e.printStackTrace();
            }finally
                {
                     desconectaBanco();
                }   
    }  
        /***
     * METODO RESPONSAVEL POR REMOVER O FORNECEDOR SELECINADO DA LISTA
     * DE FORNECEDORES EXIBIDA NA TABELA E DO BANCO DE DADOS (QUICKER.FORNECEDORES).
     * @param idFornecedor
     * @param  
     */
   
    public void removerFornecedor(int idFornecedor, String toString) {
        
         try
        {
           conectaBanco();
            
            stm = con.createStatement();
            
            sql = "DELETE FROM QUICKER.FORNECEDORES WHERE IDFORNECEDOR="+idFornecedor;
            System.out.println(sql);
            System.out.println("ID do FORNECEDOR = "+idFornecedor);
             stm.executeUpdate(sql);
            
            
           // JOptionPane.showMessageDialog(null, "Funionario "+NomeFuncionario+" foi deletado com sucesso!", "Aviso", 3); 
            JOptionPane.showMessageDialog(null, "FORNECEDOR REMOVIDO COM SUCESSO!", "FINALIZADO", 3); 
            
            
        }catch(Exception e)
            {
               e.printStackTrace();
            }finally
                {
                     desconectaBanco();
                }
    }
    /*
    public ArrayList<Fornecedor> mostrarRelatoriofornecedor()
    {
     JOptionPane.showMessageDialog(null, "<HTML>"
            + "<TABLE border='1'> "
                    + "<TR> <TD> <b>DADOS ANTIGOS </b></TD> <TD><b> DADOS NOVOS </TD> </b></TR>"
                    + "<TR> <TD> NOME FANTASIA: "+fornecedorAntigo.getNomeFantasiaFornecedor()+" </TD> <TD> NOME FANTASIA: "+fornecedorNovo.getNomeFantasiaFornecedor()+" </TD> </TR>"
                    + "<TR> <TD> RAZÃO SOCIAL:"+fornecedorAntigo.getRazaoSocialFornecedor()+"</TD> <TD> RAZÃO SOCIAL: "+fornecedorNovo.getRazaoSocialFornecedor()+" </TD> </TR>"
                    +"<TR> <TD>  CNPJ:"+fornecedorAntigo.getCnpjFornecedor()+"</TD> <TD> CNPJ:"+fornecedorNovo.getCnpjFornecedor()+"</TD> </TR>"
                    +"<TR> <TD>  TELEFONE:"+fornecedorAntigo.getTelefoneFonecedor()+"</TD> <TD> TELEFONE:"+fornecedorNovo.getTelefoneFonecedor()+"</TD> </TR>"
                    +"<TR> <TD>  CEP:"+fornecedorAntigo.getCepFornecedor()+"</TD> <TD> CEP:"+fornecedorNovo.getCepFornecedor()+"</TD> </TR>"
                    +"<TR> <TD>  LOGRADOURO: "+fornecedorAntigo.getRuaAvnFornecedor()+"</TD> <TD> LOGRADOURO"+fornecedorNovo.getRuaAvnFornecedor()+"</TD> </TR>"
                    +"<TR> <TD>  NÚMERO:"+fornecedorAntigo.getNumeroFornecedor()+"</TD> <TD> NÚMERO:"+fornecedorNovo.getNumeroFornecedor()+"</TD> </TR>"
                    +"<TR> <TD>  BAIRRO:"+fornecedorAntigo.getBairroFornecedor()+"</TD> <TD> BAIRRO:"+fornecedorNovo.getBairroFornecedor()+"</TD> </TR>"
                    +"<TR> <TD>  CIDADE:"+fornecedorAntigo.getCidadeFornecedor()+"</TD> <TD> CIDADE:"+fornecedorNovo.getCidadeFornecedor()+"</TD> </TR> "
                    +"<TR> <TD>  ESTADO:"+fornecedorAntigo.getEstadoFornecedor()+"</TD> <TD> ESTADO:"+fornecedorNovo.getEstadoFornecedor()+"</TD> </TR> "
                    +"<TR> <TD>  NOME/REPRESENTANTE:"+fornecedorAntigo.getRepresentanteFornecedor()+"</TD> <TD> NOME/REPRESENTANTE:"+fornecedorNovo.getRepresentanteFornecedor()+"</TD> </TR>"
                    +"<TR> <TD>  TELEFONE/COMERCIAL:"+fornecedorAntigo.getTelefoneRepresentante()+"</TD> <TD> TELEFONE/COMERCIAL:"+fornecedorNovo.getTelefoneRepresentante()+"</TD> </TR>"
                    +"<TR> <TD>  CELULAR:"+fornecedorAntigo.getCelularRepresentanteFornecedor()+"</TD> <TD> CELULAR: "+fornecedorNovo.getCelularRepresentanteFornecedor()+"</TD> </TR>"
                    + "<TR> <TD> EMAIL: "+fornecedorAntigo.getEmailRepresentanteFornecedor()+" </TD> <TD> EMAIL: "+fornecedorNovo.getEmailRepresentanteFornecedor()+" </TD> </TR>"
                    + "</TABLE>"
                    + "</HTML>", "RELATORIO FORNECEDOR", WIDTH, null);   
    }
    */
}