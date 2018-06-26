/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bancodados;

import java.awt.image.RenderedImage;
import static java.lang.System.out;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import modelo.classes.Pizza;
import org.apache.derby.client.am.Blob;

/**
 *
 * @author Adonias
 */
public class PizzaBD extends ConexaoBanco{
    
    private ResultSet tabelaRetornada;
    
    /***
     * METODO PARA INSERIR UM NOVO CLIENTE NO BANCO DE DADOS (QUICKER.CLIENTES)
     * O METODO É CHAMADO PELA 'TELA CLIENTE' ABA 'CADASTRAR CLIENTE'.
     * @param pizzaRecebida 
     */
    public void inserirPizza(Pizza pizzaRecebida)
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
            sql="INSERT INTO QUICKER.PIZZA (NOMEPIZZA, INGREDIENTE1, INGREDIENTE2, INGREDIENTE3, INGREDIENTE4, INGREDIENTE5, INGREDIENTE6, INGREDIENTE7, INGREDIENTE8, INGREDIENTE9, INGREDIENTE10, INGREDIENTE11, INGREDIENTE12, TAMFATIA)  "
            +"VALUES ('"+pizzaRecebida.getNomePizza()+"', "
                    /*RECEBENDO INGREDIENTES */
            +"'"+pizzaRecebida.getIngrediente1()+"', "
            +"'"+pizzaRecebida.getIngrediente2()+"',"
            +"'"+pizzaRecebida.getIngrediente3()+"', "
            +"'"+pizzaRecebida.getIngrediente4()+"', "
            +"'"+pizzaRecebida.getIngrediente5()+"', "
            +"'"+pizzaRecebida.getIngrediente6()+"', "
            +"'"+pizzaRecebida.getIngrediente7()+"', "
            +"'"+pizzaRecebida.getIngrediente8()+"', "
            +"'"+pizzaRecebida.getIngrediente9()+"', "
            +"'"+pizzaRecebida.getIngrediente10()+"', "
            +"'"+pizzaRecebida.getIngrediente11()+"', "
            +"'"+pizzaRecebida.getIngrediente12()+"', "
                    /* RECEBENDO QUANTIDADE POR PORÇÃO */
                    /*
            +"'"+pizzaRecebida.getQuantPorcao1()+"', "
            +"'"+pizzaRecebida.getQuantPorcao2()+"', "
            +"'"+pizzaRecebida.getQuantPorcao3()+"', "
            +"'"+pizzaRecebida.getQuantPorcao4()+"', "
            +"'"+pizzaRecebida.getQuantPorcao5()+"', "
            +"'"+pizzaRecebida.getQuantPorcao6()+"', "
            +"'"+pizzaRecebida.getQuantPorcao7()+"', "
            +"'"+pizzaRecebida.getQuantPorcao8()+"', "
            +"'"+pizzaRecebida.getQuantPorcao9()+"', "
            +"'"+pizzaRecebida.getQuantPorcao10()+"', "
            +"'"+pizzaRecebida.getQuantPorcao11()+"', "
            +"'"+pizzaRecebida.getQuantPorcao12()+"', "
            */
            +"'"+pizzaRecebida.getTamFatia()+"')";
                    
            /*+"'"+pizzaRecebida.getImagem().getBinaryStream()+"')";*/
            /* GAMBIARRA PRA SALVAR IMAGEM */
            /*
            java.sql.Blob blob = con.createBlob();
            ImageIO.write((RenderedImage) pizzaRecebida.getImagem(), "jpeg", out);
            out.close(); 
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Imagens(imagem) VALUES(?)");  
            stmt.setBlob(1, blob);  
            stmt.executeUpdate();  
            stmt.close(); 
            */
            /* FIM DA GAMBIARRA*/
            
            System.out.println("SQL INSERÇÃO: "+sql);
            stm.executeUpdate(sql);//EXECUTANDO COMANDO ACIMA
            JOptionPane.showMessageDialog(null, "PIZZA "+pizzaRecebida.getNomePizza()+"\nCADASTRADA COM SUCESSO!");
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
    public ArrayList<Pizza> selecionarTodasPizzas()
    {
        ArrayList<Pizza> listaPizza=new ArrayList();
        Pizza pizza=new Pizza();
        
        try{
            conectaBanco();
            
            /* OREDEM DAS VARIAVEIS*/
            /*
            idPizza, nomePizza, ingrediente1, ingrediente2, ingrediente3, ingrediente4,
            ingrediente5, ingrediente6, ingrediente7, ingrediente8, ingrediente9,
            ingrediente10, ingrediente11, ingrediente12, quantPorcao1, quantPorcao2,
            quantPorcao3, quantPorcao4, quantPorcao5, quantPorcao6, quantPorcao7,
            quantPorcao8, quantPorcao9, quantPorcao10, quantPorcao11, quantPorcao12,
            tamFatia, imagem
            */
            
             stm = con.createStatement();
            
             sql = "SELECT * "
                  +"FROM PIZZA "
                  +"ORDER BY NOMEPIZZA";
             
             tabelaRetornada=stm.executeQuery(sql);
             
             while(tabelaRetornada.next())
             {
                 pizza=new Pizza();
                 pizza.setIdPizza(tabelaRetornada.getInt("IDPIZZA"));
                 pizza.setNomePizza(tabelaRetornada.getString("NOMEPIZZA"));
                        /* INGREDIENTES */
                 pizza.setIngrediente1(tabelaRetornada.getString("INGREDIENTE1"));
                 pizza.setIngrediente2(tabelaRetornada.getString("INGREDIENTE2"));
                 pizza.setIngrediente3(tabelaRetornada.getString("INGREDIENTE3"));
                 pizza.setIngrediente4(tabelaRetornada.getString("INGREDIENTE4"));
                 pizza.setIngrediente5(tabelaRetornada.getString("INGREDIENTE5"));
                 pizza.setIngrediente6(tabelaRetornada.getString("INGREDIENTE6"));
                 pizza.setIngrediente7(tabelaRetornada.getString("INGREDIENTE7"));
                 pizza.setIngrediente8(tabelaRetornada.getString("INGREDIENTE8"));
                 pizza.setIngrediente9(tabelaRetornada.getString("INGREDIENTE9"));
                 pizza.setIngrediente10(tabelaRetornada.getString("INGREDIENTE10"));
                 pizza.setIngrediente11(tabelaRetornada.getString("INGREDIENTE11"));
                 pizza.setIngrediente12(tabelaRetornada.getString("INGREDIENTE12")); 
                        /* PORÇOES */
               /*  pizza.setQuantPorcao1(tabelaRetornada.getString("QUANTPORCAO1"));
                 pizza.setQuantPorcao2(tabelaRetornada.getString("QUANTPORCAO1"));
                 pizza.setQuantPorcao3(tabelaRetornada.getString("QUANTPORCAO1"));
                 pizza.setQuantPorcao4(tabelaRetornada.getString("QUANTPORCAO1"));
                 pizza.setQuantPorcao5(tabelaRetornada.getString("QUANTPORCAO1"));
                 pizza.setQuantPorcao6(tabelaRetornada.getString("QUANTPORCAO1"));
                 pizza.setQuantPorcao7(tabelaRetornada.getString("QUANTPORCAO1"));
                 pizza.setQuantPorcao8(tabelaRetornada.getString("QUANTPORCAO1"));
                 pizza.setQuantPorcao9(tabelaRetornada.getString("QUANTPORCAO1"));
                 pizza.setQuantPorcao10(tabelaRetornada.getString("QUANTPORCAO1"));
                 pizza.setQuantPorcao11(tabelaRetornada.getString("QUANTPORCAO1"));
                 pizza.setQuantPorcao11(tabelaRetornada.getString("QUANTPORCAO1")); */
                 pizza.setTamFatia(tabelaRetornada.getString("TAMFATIA"));
//                 pizza.setImagem((Blob) tabelaRetornada.getBlob("IMAGEM"));
                 
                 listaPizza.add(pizza);
             }
            }catch(Exception e)
            {
                e.printStackTrace();
                    System.out.println(e.getMessage());
            }finally{
                desconectaBanco();
            }

          return listaPizza;
        
    }
    
     /***
     * METODO RESPONSAVEL POR 'PEGAR' O FORNECEDOR SELECIONADO E ALTERAR UM OU
     * TODOS OS DADOS QUE SÃO EXIBIDOS NA TELA DE CADASTRO DE FORNECEDORES,
     * E REINSERI-LO NO BANCO DE DADOS (QUICKER.FORNECEDORES) COM AS ALTERAÇÕES
     * REALIZAS.
     * @param pizzaAntiga
     * @param pizzaNova 
     */
    public void atualizarPizza(Pizza pizzaNova, Pizza pizzaAntiga)
    {
        
        try
        {
           conectaBanco();
            
            stm = con.createStatement();
            
            sql = "UPDATE QUICKER.PIZZA " +
                  "SET  NOMEPIZZA='"+pizzaNova.getNomePizza()+"' "
                            /* INGREDIENTES */ 
                    +",INGREDIENTE1='"+pizzaNova.getIngrediente1()+"' "
                    +",INGREDIENTE2='"+pizzaNova.getIngrediente2()+"' "
                    +",INGREDIENTE3='"+pizzaNova.getIngrediente3()+"' "
                    +",INGREDIENTE4='"+pizzaNova.getIngrediente4()+"' "
                    +",INGREDIENTE5='"+pizzaNova.getIngrediente5()+"' "
                    +",INGREDIENTE6='"+pizzaNova.getIngrediente6()+"' "
                    +",INGREDIENTE7='"+pizzaNova.getIngrediente7()+"' "
                    +",INGREDIENTE8='"+pizzaNova.getIngrediente8()+"' "
                    +",INGREDIENTE9='"+pizzaNova.getIngrediente9()+"' "
                    +",INGREDIENTE10='"+pizzaNova.getIngrediente10()+"' "
                    +",INGREDIENTE11='"+pizzaNova.getIngrediente11()+"' "
                    +",INGREDIENTE12='"+pizzaNova.getIngrediente12()+"' "
                            /* PORÇÃO */
                    +",QUANTPORCAO1='"+pizzaNova.getQuantPorcao1()+"' "
                    +",QUANTPORCAO2='"+pizzaNova.getQuantPorcao2()+"' "
                    +",QUANTPORCAO3='"+pizzaNova.getQuantPorcao3()+"' "
                    +",QUANTPORCAO4='"+pizzaNova.getQuantPorcao4()+"' "
                    +",QUANTPORCAO5='"+pizzaNova.getQuantPorcao5()+"' "
                    +",QUANTPORCAO6='"+pizzaNova.getQuantPorcao6()+"' "
                    +",QUANTPORCAO7='"+pizzaNova.getQuantPorcao7()+"' "
                    +",QUANTPORCAO8='"+pizzaNova.getQuantPorcao8()+"' "
                    +",QUANTPORCAO9='"+pizzaNova.getQuantPorcao9()+"' "
                    +",QUANTPORCAO10='"+pizzaNova.getQuantPorcao10()+"' "
                    +",QUANTPORCAO11='"+pizzaNova.getQuantPorcao11()+"' "
                    +",QUANTPORCAO12='"+pizzaNova.getQuantPorcao12()+"' "
                    +",TAMFATIA='"+pizzaNova.getTamFatia()+"' "
                    +",IMAGEM='"+pizzaNova.getImagem()+"' "
                    +"WHERE IDPIZZA = "+pizzaAntiga.getIdPizza();
                   
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
     * @param idPizza
     * @param  
     */
     public void deletarPizza(int idPizza, String toString) {
        
         try
        {
           conectaBanco();
            
            stm = con.createStatement();
            
            sql = "DELETE FROM QUICKER.PIZZA WHERE IDPIZZA="+idPizza;
            System.out.println(sql);
            System.out.println("ID do ClIENTE = "+idPizza);
             stm.executeUpdate(sql);
            
            
           // JOptionPane.showMessageDialog(null, "Funionario "+NomeFuncionario+" foi deletado com sucesso!", "Aviso", 3); 
            JOptionPane.showMessageDialog(null, "PIZZA REMOVIDA COM SUCESSO!", "OPERAÇÃO FINALIZADA", 3); 
            
            
        }catch(Exception e)
            {
               e.printStackTrace();
            }finally
                {
                     desconectaBanco();
                }
    }
}
