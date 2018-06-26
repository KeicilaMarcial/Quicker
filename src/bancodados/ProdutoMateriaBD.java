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
import modelo.classes.ProdutoMateria;

/**
 *
 * @author Adonias
 */
public class ProdutoMateriaBD extends ConexaoBanco{
 
    DecimalFormat formatador=new DecimalFormat("00");
    private ResultSet tabelaRetornada;
    
    /***
     * METODO PARA INSERIR UM NOVO CLIENTE NO BANCO DE DADOS (QUICKER.CLIENTES)
     * O METODO É CHAMADO PELA 'TELA CLIENTE' ABA 'CADASTRAR CLIENTE'.
     * @param produtoMateriaRecebido 
     */
    public void inserirProdutoMateria(ProdutoMateria produtoMateriaRecebido)
    {
        try{
            conectaBanco();
            
            /* OREDEM DAS VARIAVEIS*/
            /*
            NOMEPRODUTO, UNIDADEMEDIDAPRODUTO, 
            TIPOPRODUTO, QUANTIDADEPRODUTOFARDO>[NÃO SERÁ NECESSARIO]
            */
            
            stm= con.createStatement();
            sql="INSERT INTO QUICKER.PRODUTOSMATERIA (NOMEPRODUTO, UNIDADEMEDIDAPRODUTO, TIPOPRODUTO) "
            +"VALUES ('"+produtoMateriaRecebido.getNomeProdutoMateria()+"', "
            +"'"+produtoMateriaRecebido.getUnidadeMedidaProdutoMateria()+"', "
            +"'"+produtoMateriaRecebido.getTipoProdutoMateria()+"')";
            System.out.println("SQL INSERÇÃO: "+sql);
            stm.executeUpdate(sql);//EXECUTANDO COMANDO ACIMA
           // JOptionPane.showMessageDialog(null, "Cliente "+produtoMateriaRecebido.getNomeProdutoMateria()+"\nCadastrado Com Sucesso!");
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
    public ArrayList<ProdutoMateria> selecionarTodosProdutosMateria()
    {
        ArrayList<ProdutoMateria> listaProdutoMateria=new ArrayList();
        ProdutoMateria produtoMateria=new ProdutoMateria();
        
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
                  +"FROM PRODUTOSMATERIA "
                  +"ORDER BY NOMEPRODUTO";
             
             tabelaRetornada=stm.executeQuery(sql);
             
             while(tabelaRetornada.next())
             {
                 produtoMateria=new ProdutoMateria();
                 produtoMateria.setIdProdutoMateria(tabelaRetornada.getInt("IDPRODUTO"));
                 produtoMateria.setNomeProdutoMateria(tabelaRetornada.getString("NOMEPRODUTO"));
                 produtoMateria.setTipoProdutoMateria(tabelaRetornada.getString("TIPOPRODUTO"));
                 produtoMateria.setUnidadeMedidaProdutoMateria(tabelaRetornada.getString("UNIDADEMEDIDAPRODUTO"));
                 
                 listaProdutoMateria.add(produtoMateria);
             }
            }catch(Exception e)
            {
                e.printStackTrace();
                    System.out.println(e.getMessage());
            }finally{
                desconectaBanco();
            }

          return listaProdutoMateria;
        
    }
    
    /***
     * REMOVER PRODUTO [MATERIA PRIMA(TRIGO, MUSSARELA...) OU NÃO (COCA-COLA...)]
     * DO BANCO DE DADOS (QUICKER.PRODUTOSMATERIA).
     * @param idProdutoMateria
     * @param NomeProdutoMateria 
     */
    public void removerProdutoMateria(int idProdutoMateria ,String NomeProdutoMateria)
    { 
        try
        {
           conectaBanco();
            
            stm = con.createStatement();
            
            sql = "DELETE FROM QUICKER.PRODUTOSMATERIA WHERE IDPRODUTO="+idProdutoMateria;
            System.out.println(sql);
            System.out.println("ID do PRODUTO = "+idProdutoMateria);
            
               System.out.println(sql);
            
             stm.executeUpdate(sql);
            
            
           // JOptionPane.showMessageDialog(null, "Funionario "+NomeFuncionario+" foi deletado com sucesso!", "Aviso", 3); 
            JOptionPane.showMessageDialog(null, "PRODUTO "+NomeProdutoMateria.toUpperCase()+" FOI REMOVIDO COM SUCESSO!", "AVISO", 3); 
            
            
        }catch(Exception e)
            {
               e.printStackTrace();
            }finally
                {
                     desconectaBanco();
                }
    }
    
    public void atualizarProdutoMateria(ProdutoMateria produtoMateriaNovo, ProdutoMateria produtoMateriaAntigo)
    {
            try
        {
           conectaBanco();
            
            stm = con.createStatement();
            
            sql = "UPDATE QUICKER.PRODUTOSMATERIA " +
                  "SET NOMEPRODUTO='"+produtoMateriaNovo.getNomeProdutoMateria()+"' "
                   +", TIPOPRODUTO='"+produtoMateriaNovo.getTipoProdutoMateria()+"' "
                   +",UNIDADEMEDIDAPRODUTO='"+produtoMateriaNovo.getUnidadeMedidaProdutoMateria()+"' "
                   +"WHERE IDPRODUTO = "+produtoMateriaAntigo.getIdProdutoMateria();
                   
                   System.out.println(sql);
            
                   stm.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(null, "PRODUTO ALTERADO COM SUCESSO!", "FINALIZADO", 3);                     
            
        } catch(Exception e)
            {
               e.printStackTrace();
            }finally
                {
                     desconectaBanco();
                }
    }
}
