/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bancodados;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import modelo.classes.Funcionario;

/**
 *
 * @author Adonias
 */
public class FuncionarioBD extends ConexaoBanco{

    DecimalFormat formatador = new DecimalFormat("00");
   //private ResultSet tabelaRetornada;
    
    /**
     * Método responsável por inserir um Novo Funcionário no banco.O método
     * é chamado pela tela de Cadastro de Funcionários 
     * BANCO DE DADOS(QUICKER.FUNCIONARIOS).
     * @param funcionarioRecebido 
     */
    public void inserirFuncionario(Funcionario funcionarioRecebido)
    {
        try
        {
        /* IDFUNCIONARIO, NOMEFUNCIONARIO, CPFFUNCIONARIO, DATANASCIMENTOFUNCIONARIO,
         PISFUNCIONARIO, TELEFONEFUNCIONARIO, CELULARFUNCIONARIO, CEPFUNCIONARIO, 
         RUAAVNFUNCIONARIO, NUMEROFUNCIONARIO, BAIRROFUNCIONARIO, CIDADEFUNCIONARIO,
         ESTADOFUNCIONARIO, DATAADIMISSAOFUNCIONARIO, SITUACAOFUNCIONARIO,
         DATADEMISSAOFUNCIONARIO, DATAAFASTAMENTOFUNCIONARIO, DATARETORNOFUNCIONARIO, 
         CARGFUNCIONARIO, NIVELPERMISSAOFUNCIONARIO, NOMEUSUARIOFUNCIONARIO, SENHAFUNCIONARIO
        */
            conectaBanco(); // Conectando a base de dados
            
            stm = con.createStatement(); // "Criando o botão de execução de comandos SQL"
            
            sql = "INSERT INTO QUICKER.FUNCIONARIOS (NOMEFUNCIONARIO, CPFFUNCIONARIO, DATANASCIMENTOFUNCIONARIO, PISFUNCIONARIO, TELEFONEFUNCIONARIO, CELULARFUNCIONARIO, CEPFUNCIONARIO, RUAAVNFUNCIONARIO, NUMEROFUNCIONARIO, BAIRROFUNCIONARIO, CIDADEFUNCIONARIO, ESTADOFUNCIONARIO, DATAADIMISSAOFUNCIONARIO, SITUACAOFUNCIONARIO, DATADEMISSAOFUNCIONARIO, DATAAFASTAMENTOFUNCIONARIO, DATARETORNOFUNCIONARIO, CARGOFUNCIONARIO, NIVELPERMISSAOFUNCIONARIO, NOMEUSUARIOFUNCIONARIO, SENHAFUNCIONARIO)" +

                  " VALUES ('"+funcionarioRecebido.getNomeFuncionario()+"', "
                              + "'"+funcionarioRecebido.getCpfFuncionario()+ "', "
                              + "'"+funcionarioRecebido.getDataNascimentoFuncionario().get(Calendar.YEAR)+"-"+formatador.format(funcionarioRecebido.getDataNascimentoFuncionario().get(Calendar.MONTH))+"-"+formatador.format(funcionarioRecebido.getDataNascimentoFuncionario().get(Calendar.DAY_OF_MONTH))+"', "
                              + "'"+funcionarioRecebido.getPisFuncionario()+ "', "
                              + "'"+funcionarioRecebido.getTelefoneFuncionario()+"', "
                              + "'"+funcionarioRecebido.getCelularFuncionario()+"',"
                              +"'"+funcionarioRecebido.getCepFuncionario()+"',"   
                              +"'"+funcionarioRecebido.getRuaAvnFuncionario()+"', "
                              +"'"+funcionarioRecebido.getNumeroFuncionario()+"',"
                              + "'"+funcionarioRecebido.getBairroFuncionario()+ "', "
                              + "'"+funcionarioRecebido.getCidadeFuncionario()+ "', "
                              + "'"+funcionarioRecebido.getEstadoFuncionario()+ "', "
                              + "'"+funcionarioRecebido.getDataAdamissaoFuncionario().get(Calendar.YEAR)+"-"+ formatador.format(funcionarioRecebido.getDataAdamissaoFuncionario().get(Calendar.MONTH))+"-"+formatador.format(funcionarioRecebido.getDataAdamissaoFuncionario().get(Calendar.DAY_OF_MONTH))+"', "
                              +"'"+funcionarioRecebido.getSituacaoFuncionario()+ "', ";
                      
                    // --- condições para as datas 
            
                    if(funcionarioRecebido.getDataDemissaoFuncionario()!= null) {
                        sql += "'"+funcionarioRecebido.getDataDemissaoFuncionario().get(Calendar.YEAR)+"-"+formatador.format( funcionarioRecebido.getDataDemissaoFuncionario().get(Calendar.MONTH))+"-"+formatador.format(funcionarioRecebido.getDataDemissaoFuncionario().get(Calendar.DAY_OF_MONTH))+ "', ";
            } else {
                        sql += "null,";
            }
                    
                    if(funcionarioRecebido.getDataDemissaoFuncionario()!= null) {
                        sql += "'"+funcionarioRecebido.getDataDemissaoFuncionario().get(Calendar.YEAR)+"-"+formatador.format( funcionarioRecebido.getDataDemissaoFuncionario().get(Calendar.MONTH))+"-"+formatador.format(funcionarioRecebido.getDataDemissaoFuncionario().get(Calendar.DAY_OF_MONTH))+ "', ";
            } else {
                        sql += "null,";
            }      
                        
                    if(funcionarioRecebido.getDataRetornoFuncionario()!= null) {
                        sql += "'"+funcionarioRecebido.getDataRetornoFuncionario().get(Calendar.YEAR)+"-"+ formatador.format(funcionarioRecebido.getDataRetornoFuncionario().get(Calendar.MONTH))+"-"+formatador.format(funcionarioRecebido.getDataRetornoFuncionario().get(Calendar.DAY_OF_MONTH))+ "', ";
            } else {
                        sql += "null,";
            }      
                    
                       sql += "'"+funcionarioRecebido.getCargoFuncionario()+"',"
                               +"'"+funcionarioRecebido.getPermissaoFuncionario()+"',"
                               +"'"+funcionarioRecebido.getNomeUsuario()+"',"
                               +"'"+funcionarioRecebido.getSenhaFuncionario()+"')";
                               
                       
            
                        
                        System.out.println("SQL DE INSERÇÃO: "+sql);
            
                        stm.executeUpdate(sql); // Executando o SQL escrito acima
            
            
            JOptionPane.showMessageDialog(null, "FUNCIONARIO CADASTRADO COM SUCESSO!", "Cadastro Realizado", 1);
            
        }catch(Exception e)
            {
                e.printStackTrace();
            }finally
                    {
                        desconectaBanco();
                    }
    }
    
   /***
    * METODO RESPONSAVEL POR SELECIONAR TODOS OS FUNCIONARIOS
    * DO BANCO DADOS (QUICKER.FUNCIONARIOS) E INSERI-LOS EM 
    * UMA LISTA DE FUNCIONARIOS, QUE SERÁ INSERIDA NO 
    * MODELO DE TABELA (modeloTabelaFuncionarios).
    * @return 
    */ 
   public ArrayList<Funcionario> selecionarTodosFuncionarios()
    {
        ArrayList<Funcionario> listaFuncionarios = new ArrayList();
        Funcionario funcionario = new Funcionario();
        try
        {
             conectaBanco();
            
          
            
            stm = con.createStatement();
            
            sql = "SELECT * "
                 +"FROM FUNCIONARIOS ";
                
            
            tabelaRetornada = stm.executeQuery(sql);
            
             while(tabelaRetornada.next()) // Enquanto houverem linhas na tabela para serem lidas
            {
                  funcionario=new Funcionario();
                
                funcionario.setIdFuncionario(tabelaRetornada.getInt("IDFUNCIONARIO"));
                funcionario.setPisFuncionario(tabelaRetornada.getString("PISFUNCIONARIO"));
                funcionario.setNomeFuncionario(tabelaRetornada.getString("NOMEFUNCIONARIO"));
                funcionario.setCpfFuncionario(tabelaRetornada.getString("CPFFUNCIONARIO")); 
                funcionario.setTelefoneFuncionario(tabelaRetornada.getString("TELEFONEFUNCIONARIO"));
                funcionario.setCelularFuncionario(tabelaRetornada.getString("CELULARFUNCIONARIO"));
                funcionario.setRuaAvnFuncionario(tabelaRetornada.getString("RUAAVNFUNCIONARIO"));
                funcionario.setNumeroFuncionario(tabelaRetornada.getString("NUMEROFUNCIONARIO")); 
                funcionario.setBairroFuncionario(tabelaRetornada.getString("BAIRROFUNCIONARIO")); 
                funcionario.setCidadeFuncionario(tabelaRetornada.getString("CIDADEFUNCIONARIO")); 
                funcionario.setCargoFuncionario(tabelaRetornada.getString("CARGOFUNCIONARIO"));
                funcionario.setSituacaoFuncionario(tabelaRetornada.getString("SITUACAOFUNCIONARIO"));
                funcionario.setEstadoFuncionario(tabelaRetornada.getString("ESTADOFUNCIONARIO"));
                funcionario.setCepFuncionario(tabelaRetornada.getString("CEPFUNCIONARIO"));
                funcionario.setPermissaoFuncionario("NIVELPERMISSAOFUNCIONARIO");
                funcionario.setNomeUsuario("NOMEUSUARIOFUNCIONARIO");
                funcionario.setSenhaFuncionario("SENHAFUNCIONARIO");
                
                Calendar cal=Calendar.getInstance();
                cal.setTime(tabelaRetornada.getDate("DATAADIMISSAOFUNCIONARIO"));
                funcionario.setDataAdamissaoFuncionario(cal);
                
                Calendar calDemissao=Calendar.getInstance();
                if(tabelaRetornada.getDate("DATADEMISSAOFUNCIONARIO") != null)
                {
                    calDemissao.setTime(tabelaRetornada.getDate("DATADEMISSAOFUNCIONARIO"));
                }
                funcionario.setDataDemissaoFuncionario(calDemissao);
                
                Calendar calNascimentno=Calendar.getInstance();
                calNascimentno.setTime(tabelaRetornada.getDate("DATANASCIMENTOFUNCIONARIO"));
                funcionario.setDataNascimentoFuncionario(calNascimentno);
               /*
                Calendar calRetorno=Calendar.getInstance();
                calRetorno.setTime(tabelaRetornada.getDate("DATARETORNOFUNCIONARIO"));
                funcionario.setDataRetornoFuncionario(calRetorno);
                
                Calendar calAfastamento=Calendar.getInstance();
                calAfastamento.setTime(tabelaRetornada.getDate("DATAAFASTAMENTOFUNCIONARIO"));
                funcionario.setDataAfastamento(calAfastamento);
                */
                
                   
                
                listaFuncionarios.add(funcionario);
               
                
            }
        }catch(Exception e)
            {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }  finally
                {
                  desconectaBanco();
                }
        return  listaFuncionarios  ;
    }
    
    
    /***
     * METODO RESPONSAVEL POR 'PEGAR' O FUNCIONARIO SELECIONADO E ALETAR UM OU
     * TODOS OS DADOS QUE SÃO EXIBIDOS NA TELA DE CADASTRO DE FUNCIONARIO,
     * E REINSERI-LO NO BANCO DE DADOS (QUICKER.FUNCIONARIOS) COM AS ALTERAÇÕES
     * REALIZAS.
     * @param idAntigoFuncionario
     * @param funcionarioNovo 
     */
    public void atualizarFuncionario(Funcionario funcionarioNovo, Funcionario funcionarioAntigo)
    {
        try
        {
           conectaBanco();
            
            stm = con.createStatement();
           
            
            //ADICIONAR TODOS OS CAMPOS NA SEGUNDA LINHA.
            sql = "UPDATE FUNCIONARIOS " +
                  "SET NOMEFUNCIONARIO='"+funcionarioNovo.getNomeFuncionario()+"' "
                   +", TELEFONEFUNCIONARIO ='"+funcionarioNovo.getTelefoneFuncionario()+"' "
                   +", CELULARFUNCIONARIO ='"+funcionarioNovo.getCelularFuncionario()+"' "
                   + ", PISFUNCIONARIO='"+funcionarioNovo.getPisFuncionario()+"' "
                   +", CIDADEFUNCIONARIO='"+funcionarioNovo.getCidadeFuncionario()+"' "
                   +", ESTADOFUNCIONARIO='"+funcionarioNovo.getEstadoFuncionario()+"' "
                   +", BAIRROFUNCIONARIO='"+funcionarioNovo.getBairroFuncionario()+"' "
                   +", NUMEROFUNCIONARIO="+funcionarioNovo.getNumeroFuncionario()
                   +",DATAADIMISSAOFUNCIONARIO='"+funcionarioNovo.getDataAdamissaoFuncionario().get(Calendar.YEAR)+"-"+funcionarioNovo.getDataAdamissaoFuncionario().get(Calendar.MONTH)+"-"+funcionarioNovo.getDataAdamissaoFuncionario().get(Calendar.DAY_OF_MONTH)+"' "
                   +", DATADEMISSAOFUNCIONARIO='"+funcionarioNovo.getDataDemissaoFuncionario().get(Calendar.YEAR)+"-"+funcionarioNovo.getDataDemissaoFuncionario().get(Calendar.MONTH)+"-"+funcionarioNovo.getDataDemissaoFuncionario().get(Calendar.DAY_OF_MONTH)+"' "
                   +",SITUACAOFUNCIONARIO= '"+funcionarioNovo.getSituacaoFuncionario()+"' "
                   +",DATARETORNOFUNCIONARIO='"+funcionarioNovo.getDataRetornoFuncionario().get(Calendar.YEAR)+"-"+funcionarioNovo.getDataRetornoFuncionario().get(Calendar.MONTH)+"-"+funcionarioNovo.getDataRetornoFuncionario().get(Calendar.DAY_OF_MONTH)+"' "
                   +",DATANASCIMENTOFUNCIONARIO='"+funcionarioNovo.getDataNascimentoFuncionario().get(Calendar.YEAR)+"-"+funcionarioNovo.getDataNascimentoFuncionario().get(Calendar.MONTH)+"-"+funcionarioNovo.getDataNascimentoFuncionario().get(Calendar.DAY_OF_MONTH)+"' "
                   +", DATAAFASTAMENTOFUNCIONARIO='"+funcionarioNovo.getDataDemissaoFuncionario().get(Calendar.YEAR)+"-"+funcionarioNovo.getDataDemissaoFuncionario().get(Calendar.MONTH)+"-"+funcionarioNovo.getDataDemissaoFuncionario().get(Calendar.DAY_OF_MONTH)+"' "
                    +",CPFFUNCIONARIO='"+funcionarioNovo.getCpfFuncionario()+"' "
                   +",RUAAVNFUNCIONARIO='"+funcionarioNovo.getRuaAvnFuncionario()+"' "
                   +",CARGOFUNCIONARIO='"+funcionarioNovo.getCargoFuncionario()+"' "
                   +",CEPFUNCIONARIO='"+funcionarioNovo.getCepFuncionario()+"' "
                   +",NIVELPERMISSAOFUNCIONARIO='"+funcionarioNovo.getPermissaoFuncionario()+"' "
                   +",NOMEUSUARIOFUNCIONARIO='"+funcionarioNovo.getNomeUsuario()+"' "
                   +",SENHAFUNCIONARIO='"+funcionarioNovo.getSenhaFuncionario()+"'"+
                   "WHERE IDFUNCIONARIO = "+funcionarioAntigo.getIdFuncionario();
             
            System.out.println(sql);
            
            stm.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(null, "Funcionario foi alterado com sucesso!", "Aviso", 3);                   
            
        }catch(Exception e)
            {
               e.printStackTrace();
            }finally
                {
                     desconectaBanco();
                }
    }
    
    
    /***
     * METODO RESPONSAVEL POR REMOVER O FUNCIONARIO SELECINADO DA LISTA
     * DE FUNCIONARIOS EXIBIDA NA TABELA E DO BANCO DE DADOS (QUICKER.FUNCINARIOS).
     * @param idFuncionario
     * @param NomeFuncionario 
     */
    public void deletarFuncionario(int idFuncionario ,String NomeFuncionario)
    { 
        try
        {
           conectaBanco();
            
            stm = con.createStatement();
            
            sql = "DELETE FROM QUICKER.FUNCIONARIOS WHERE IDFUNCIONARIO="+idFuncionario;
            System.out.println(sql);
            System.out.println("ID do funcionario = "+idFuncionario);
             stm.executeUpdate(sql);
            
            
           // JOptionPane.showMessageDialog(null, "Funionario "+NomeFuncionario+" foi deletado com sucesso!", "Aviso", 3); 
            JOptionPane.showMessageDialog(null, "Funcionario "+NomeFuncionario+" foi deletado com sucesso!", "Aviso", 3); 
            
            
        }catch(Exception e)
            {
               e.printStackTrace();
            }finally
                {
                     desconectaBanco();
                }
    }

    
 
   
}