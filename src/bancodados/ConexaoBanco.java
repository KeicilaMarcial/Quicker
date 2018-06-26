/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bancodados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author alunov2
 */
public class ConexaoBanco 
{
    //URL = "jdbc:derby://quicker"
    String driver = "org.apache.derby.jdbc.ClientDriver";
    String url = "jdbc:derby:projetoquicker";
    String usuario = "quicker";
    String senha = "quicker123";
    
    String sql;
    Connection con;
    Statement stm;
    
    // Tabela para retornar dados do SELECT
    ResultSet tabelaRetornada;
    
    public void conectaBanco()
    {
        try
        {
            System.out.println("Checando se o driver de conexão existe...");
            Class.forName(driver);
            System.out.println("DRIVER ENCONTRADO");
            
            System.out.println("Criando a conexão com a base "+url+"...");
            con = DriverManager.getConnection(url, usuario, senha);
            System.out.println("CONECTADO A BASE");
        }catch(Exception e)
            {
                System.err.println("Problema de conexão com a base\n\n"+e.getMessage());
            }
    }
    
    public void desconectaBanco()
    {
         try
         {
             System.out.println("Tentando desconectar da base...");
             con.close();
             System.out.println("DESCONECTADO");
         }catch(Exception e)
            {
                
            }
    }
}
/*
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Adonias
 */
/*
public class ConexaoBanco {

    //RL = "jdbc:derby://quicker"
    String driver = "org.apache.derby.jdbc.ClientDriver";
    String url = "jdbc:derby:projetoquicker";
    String usuario = "quicker";
    String senha = "quicker123";
    
    String sql;
    Connection con;
    Statement stm;
    
    public void conectaBanco()
    {
        try
        {
            System.out.println("Checando se o driver de conexão existe...");
            Class.forName(driver);
            System.out.println("DRIVER ENCONTRADO");
            
            System.out.println("Criando a conexão com a base "+url+"...");
            con = DriverManager.getConnection(url, usuario, senha);
            System.out.println("CONECTADO A BASE");
        }catch(Exception e)
            {
                System.err.println("Problema de conexão com a base\n\n"+e.getMessage());
            }
    }
    
    public void desconectaBanco()
    {
         try
         {
             System.out.println("Tentando desconectar da base...");
             con.close();
             System.out.println("DESCONECTADO");
         }catch(Exception e)
            {
                
            }
    }
}    
*/