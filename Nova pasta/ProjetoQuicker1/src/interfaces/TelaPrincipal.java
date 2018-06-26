/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import bancodados.ClienteBD;
import bancodados.FornecedorBD;
import bancodados.FuncionarioBD;
import bancodados.ProdutoBD;
import bancodados.ProdutoMateriaBD;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import modelo.classes.Cliente;
import modelo.classes.Fornecedor;
import modelo.classes.ProdutoMateria;
import modelo.classes.Funcionario;
import modelo.classes.LerManual;
import modelo.tabelas.ModeloTabelaCliente;
import modelo.tabelas.ModeloTabelaFornecedor;
import modelo.tabelas.ModeloTabelaFuncionario;
import modelo.tabelas.ModeloTabelaProdutoEstoque;
import modelo.tabelas.ModeloTabelaProdutoMateria;
import org.jsoup.Jsoup;
import net.sf.nachocalendar.CalendarFactory;
import net.sf.nachocalendar.components.DateField;
/**
 *
 * @author DTECNET
 */
public class TelaPrincipal extends javax.swing.JFrame {

    /* criando formatador*/
   DecimalFormat  formatador= new  DecimalFormat("00");
    
     /* USADO NO  METODO DE buscaCep */
     Map<String, String> query = new HashMap<String, String>();
     /* CONECTANDO BANCO DE DADOS A TABELA */
     ClienteBD conexaoTabelaCliente=new ClienteBD();
     ProdutoMateriaBD conexaoTabelaProdutoMateria=new ProdutoMateriaBD();
     FuncionarioBD conexaoTabelaFuncionario=new FuncionarioBD();
     FornecedorBD conexaoTabelaFornecedor=new FornecedorBD();
     ProdutoBD conexaoTabelaProduto=new ProdutoBD(); // PRODUTOS DO ESTOQUE
     
     /* MODELO DE TABELAS */
     ModeloTabelaCliente modeloTabelaCliente=new ModeloTabelaCliente();
     ModeloTabelaProdutoEstoque modeloTabelaProdutoEstoque=new ModeloTabelaProdutoEstoque();
     ModeloTabelaFuncionario modeloTabelaFuncionario=new ModeloTabelaFuncionario();
     ModeloTabelaFornecedor modeloTabelaFornecedor=new ModeloTabelaFornecedor();
     ModeloTabelaProdutoMateria modeloTabelaProdutoMateria=new ModeloTabelaProdutoMateria();

     // Criando um novo campo de data (calendário) 
    DateField dataNascimentoCliente = CalendarFactory.createDateField();
    DateField dataNascimentoFuncionario = CalendarFactory.createDateField();
    DateField dataCompraProduto = CalendarFactory.createDateField();
    DateField dataValidadeProduto = CalendarFactory.createDateField();
    DateField dataAdmissaoFuncionario  = CalendarFactory.createDateField();
    DateField dataDemissaoFuncionario  = CalendarFactory.createDateField();
    DateField dataRetornoFuncionario  = CalendarFactory.createDateField();
    private Calendar cal;
    
    // Criando uma lista de produtos/matéria cadastrados
    ArrayList<ProdutoMateria> listaProdutosMateria = new ArrayList();
    // Criando uma lista de clientes
    ArrayList<Cliente> listaClientes=new ArrayList();
    // Criando uma lista de funcionarios
    ArrayList<Funcionario> listaFuncionarios=new ArrayList();
    
    // Criando uma lista de funcionarios
    ArrayList<Fornecedor> listaFornecedores=new ArrayList();
    
    // LOCKS para comboBox
    boolean lock_cbNomeProduto = true;
    
    /* ADICIONANDO ICONE AO PROGRAMA */
    ImageIcon icone = new ImageIcon(getClass().getResource("/imagens/logomarcaatualizada.png"));
    
    /* VARIAVEL PARA ATUALIZAR FORNECEDOR - FUNCIONARIO - CLIENTE - PRODUTOESTOQUE
    - PRODUTOMATERIA */
    Funcionario funcionarioNovo = null;
    Funcionario funcionarioAntigo = null;
    Fornecedor fornecedorAntigo=null;
    Fornecedor fornecedorNovo=null;
    Cliente clienteAntigo=null;
    Cliente clienteNovo=null;
    
    /** Creates new form TelaPrincipal */
    public TelaPrincipal() {
        initComponents();
        this.setIconImage(icone.getImage());
        /* CONFIGURAÇÕES INICIAS DA TELA PRINCIPAL*/
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.isResizable();
        //this.setIconImages(view_refresh.png);
//        this.setSize(800, 600);
        
        this.requestFocusInWindow();
       // FlashWindow(Application.Handle, true);
        
        /* CHAMANDO A TELA DE LOGIN */

        //TelaLogin telaLogin=new TelaLogin(this, true);
        //telaLogin.setVisible(true);
        //telaLogin.requestFocusInWindow();
        
        /* BUSCANDO CLIENTES CADASTRARDOS E INSERINDO NA TABELA - CONFIGURANDO QUAL
        MODELO DE TABELA SERÁ UTILIZADO */
        modeloTabelaCliente.inserirlistaCliente(conexaoTabelaCliente.selecionarTodosClientes());
        tbListaClientes.setModel(modeloTabelaCliente);
        tbListaClientes.updateUI();
        
        /* BUSCANDO PRODUTOSESTOQUE CADASTRARDOS E INSERINDO NA TABELA - CONFIGURANDO QUAL
        MODELO DE TABELA SERÁ UTILIZADO */
        
        tbListaProdutosEstoque.setModel(modeloTabelaProdutoEstoque);
        tbListaProdutosEstoque.updateUI();
        
        /* BUSCANDO FORNECEDORES CADASTRARDOS E INSERINDO NA TABELA - CONFIGURANDO QUAL
        MODELO DE TABELA SERÁ UTILIZADO */
        modeloTabelaFornecedor.inserirlistaFornecedores(conexaoTabelaFornecedor.selecionarTodosFornecedores());
        tbListaFornecedores.setModel(modeloTabelaFornecedor);
        tbListaFornecedores.updateUI();
        
        /* BUSCANDO FORNECEDORES CADASTRARDOS E INSERINDO NA TABELA - CONFIGURANDO QUAL
        MODELO DE TABELA SERÁ UTILIZADO */
        modeloTabelaFuncionario.inserirlistaFuncionarios(conexaoTabelaFuncionario.selecionarTodosFuncionarios());
        tbListaFuncionarios.setModel(modeloTabelaFuncionario);
        tbListaFuncionarios.updateUI();
        
        /* OCULTANDO TELAS/ BOTÕES/ INSERINDO FUNCIONALIDADES */
        ifTelaFornecedor.setVisible(false);
        ifTelaFornecedor.setSize(580, 610);
        ifTelaFuncionario.setVisible(false);
        ifTelaFuncionario.setSize(580, 610);
        ifTelaProdutoEstoque.setVisible(false);
        ifTelaProdutoEstoque.setSize(580, 610);
        ifTelaPdv.setVisible(false);
        ifTelaCliente.setVisible(false);
        ifTelaCliente.setSize(580, 610);
        btSalvarAtualizarCliente.setVisible(false);
        btCancelarAtualizarCliente.setVisible(false);
        btSalvarAtualizarFuncionario.setVisible(false);
        btCancelarAtualizarFuncionario.setVisible(false);
        btSalvarAtualizarFornecedor.setVisible(false);
        btCancelarAtualizarFornecedor.setVisible(false);
        btSalvarAtualizarProdutoEstoque.setVisible(false);
        btCancelarProdutoEstoque.setVisible(false);
        lbDataDemissaoFuncionario.setVisible(false);
        ftfDataDemissaoFuncionario.setVisible(false);
        lbDataRetornoFuncionario.setVisible(false);
        ftfDataRetornoFuncionario.setVisible(false);
        cbEstadoFuncionario.setSelectedItem("ES");
       
        //  TRAZENDO O CALENDARIO DO WINDOWS PARA DENTRO DO CAMPO DATA DE NASCIMENTO DO CLIENTE
        Calendar calCliente = Calendar.getInstance();
        dataNascimentoCliente.setBaseDate(calCliente.getTime());
        ftfDataNascimentoCliente.add(dataNascimentoCliente);
        // Definindo o botão DateField para seleção de uma data e atribuindo uma ação de mudança à ele.
        dataNascimentoCliente.setSize((ftfDataNascimentoCliente.getWidth()), (ftfDataNascimentoCliente.getHeight()));
        
        //  TRAZENDO O CALENDARIO DO WINDOWS PARA DENTRO DO CAMPO DATA DE NASCIMENTO DO FUNCIONARIO
        Calendar calNascimento = Calendar.getInstance();
        dataNascimentoFuncionario.setBaseDate(calNascimento.getTime());
        ftfDataNascimentoFuncionario.add(dataNascimentoFuncionario);
        // Definindo o botão DateField para seleção de uma data e atribuindo uma ação de mudança à ele.
        dataNascimentoFuncionario.setSize((ftfDataNascimentoFuncionario.getWidth()), (ftfDataNascimentoFuncionario.getHeight()));
        
        //  TRAZENDO O CALENDARIO DO WINDOWS PARA DENTRO DO CAMPO DATA DE ADMISSAO DO FUNCIONARIO
        Calendar calAdmissao = Calendar.getInstance();
        dataAdmissaoFuncionario.setBaseDate(calAdmissao.getTime());
        ftfDataAdmissaoFuncionario.add(dataAdmissaoFuncionario);
        // Definindo o botão DateField para seleção de uma data e atribuindo uma ação de mudança à ele.
        dataAdmissaoFuncionario.setSize((ftfDataAdmissaoFuncionario.getWidth()), (ftfDataAdmissaoFuncionario.getHeight()));
        
        //  TRAZENDO O CALENDARIO DO WINDOWS PARA DENTRO DO CAMPO DATA DE DEMISSÃO DO FUNCIONARIO
        Calendar calDemissao = Calendar.getInstance();
        dataDemissaoFuncionario.setBaseDate(calAdmissao.getTime());
        ftfDataDemissaoFuncionario.add(dataDemissaoFuncionario);
        // Definindo o botão DateField para seleção de uma data e atribuindo uma ação de mudança à ele.
        dataDemissaoFuncionario.setSize((ftfDataDemissaoFuncionario.getWidth()), (ftfDataDemissaoFuncionario.getHeight()));
        
        //  TRAZENDO O CALENDARIO DO WINDOWS PARA DENTRO DO CAMPO DATA DE AFASTAMENTO DO FUNCIONARIO
        Calendar calAfastamento = Calendar.getInstance();
        dataDemissaoFuncionario.setBaseDate(calAfastamento.getTime());
        ftfDataDemissaoFuncionario.add(dataDemissaoFuncionario);
        // Definindo o botão DateField para seleção de uma data e atribuindo uma ação de mudança à ele.
        dataDemissaoFuncionario.setSize((ftfDataDemissaoFuncionario.getWidth()), (ftfDataDemissaoFuncionario.getHeight()));

        //  TRAZENDO O CALENDARIO DO WINDOWS PARA DENTRO DO CAMPO DATA DE RETORNO DO FUNCIONARIO
        Calendar calRetorno = Calendar.getInstance();
        dataRetornoFuncionario.setBaseDate(calRetorno.getTime());
        ftfDataRetornoFuncionario.add(dataRetornoFuncionario);
        // Definindo o botão DateField para seleção de uma data e atribuindo uma ação de mudança à ele.
        dataRetornoFuncionario.setSize((ftfDataRetornoFuncionario.getWidth()), (ftfDataRetornoFuncionario.getHeight()));
        
        //  TRAZENDO O CALENDARIO DO WINDOWS PARA DENTRO DO CAMPO DATA DE COMPRA DO PRODUTO
        Calendar calCompraProduto = Calendar.getInstance();
        dataNascimentoCliente.setBaseDate(calCompraProduto.getTime());
        ftfDataCompraProduto.add(dataCompraProduto);
        // Definindo o botão DateField para seleção de uma data e atribuindo uma ação de mudança à ele.
        dataCompraProduto.setSize((ftfDataCompraProduto.getWidth()), (ftfDataCompraProduto.getHeight()));
        
        //  TRAZENDO O CALENDARIO DO WINDOWS PARA DENTRO DO CAMPO DATA DE NASCIMENTO DO CLIENTE
        Calendar calValidadeProduto = Calendar.getInstance();
        dataNascimentoCliente.setBaseDate(calValidadeProduto.getTime());
        ftfDataValidadeProduto.add(dataValidadeProduto);
        // Definindo o botão DateField para seleção de uma data e atribuindo uma ação de mudança à ele.
        dataValidadeProduto.setSize((ftfDataValidadeProduto.getWidth()), (ftfDataValidadeProduto.getHeight()));
        
        /* LISTANDO OS PRODUTOS CADASTRADOS NO cbNOMEPRODUTO*/
        tbListaProdutosEstoque.setModel(modeloTabelaProdutoEstoque);
        
        preencherComboBoxProdutoMateria();
        
        // Destrancando os comboBoxes
        lock_cbNomeProduto = false;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dpAreaTrabalho = new javax.swing.JDesktopPane()
        {
            private Image image;
            {
                try {
                    // ImageIcon icon = new ImageIcon("Penguins.jpg");
                    // image = icon.getImage();
                    // image = ImageIO.read(new URL("http://www.hdbackgroundspoint.com/wp-content/uploads/2013/12/16/345t34.jpeg"));
                    image = ImageIO.read(new File(".\\imagens\\pizza_3.jpg"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        }
        ;
        ifTelaFuncionario = new javax.swing.JInternalFrame();
        tpnAbasFuncionario = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfIdFuncionario = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tfNomeCompletoFuncionario = new javax.swing.JTextField();
        ftfCpfFuncionario = new javax.swing.JFormattedTextField();
        ftfDataNascimentoFuncionario = new javax.swing.JFormattedTextField(Mascara("##/##/####"));
        ftfPisFuncionario = new javax.swing.JFormattedTextField(Mascara("###.#####.##-#"));
        ftfTelefoneFuncionario = new javax.swing.JFormattedTextField(Mascara("(##) ####-####"));
        ftfCelularFuncionario = new javax.swing.JFormattedTextField(Mascara("(##) ####-####"));
        tfLogradouroFuncionario = new javax.swing.JTextField();
        tfNumeroFuncionario = new javax.swing.JTextField();
        tfBairroFuncionario = new javax.swing.JTextField();
        tfCidadeFuncionario = new javax.swing.JTextField();
        cbEstadoFuncionario = new javax.swing.JComboBox();
        jLabel21 = new javax.swing.JLabel();
        ftfCepFuncionario = new javax.swing.JFormattedTextField(Mascara("#####-###"));
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbDataDemissaoFuncionario = new javax.swing.JLabel();
        lbDataRetornoFuncionario = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        ftfDataAdmissaoFuncionario = new javax.swing.JFormattedTextField();
        cbSituacaoFuncionario = new javax.swing.JComboBox();
        ftfDataDemissaoFuncionario = new javax.swing.JFormattedTextField();
        ftfDataRetornoFuncionario = new javax.swing.JFormattedTextField();
        cbCargoFuncionario = new javax.swing.JComboBox();
        cbNivelPermissaoFuncionario = new javax.swing.JComboBox();
        tfSenhaFuncionario = new javax.swing.JTextField();
        tfNomeUsuarioFuncionario = new javax.swing.JTextField();
        btLimparCamposFuncionario = new javax.swing.JButton();
        btSalvarFuncionario = new javax.swing.JButton();
        btSalvarAtualizarFuncionario = new javax.swing.JButton();
        btCancelarAtualizarFuncionario = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        tfBuscaFuncionario = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbListaFuncionarios = new javax.swing.JTable();
        btRelatorioFuncionario = new javax.swing.JButton();
        btAtualizarFuncionario = new javax.swing.JButton();
        btRemoverFuncionario = new javax.swing.JButton();
        ifTelaFornecedor = new javax.swing.JInternalFrame();
        tpnAbasFornecedor = new javax.swing.JTabbedPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel9 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        tfIdFornecedor = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        tfRazaoSocialFornecedor = new javax.swing.JTextField();
        tfNomeFantasiaFornecedor = new javax.swing.JTextField();
        ftfCnpjFornecedor = new javax.swing.JFormattedTextField(Mascara("##.###.###/####-##"));
        ftfTelefoneFornecedor = new javax.swing.JFormattedTextField(Mascara("(##) ####-####"));
        ftfCepFornecedor = new javax.swing.JFormattedTextField();
        tfLogradouroFornecedor = new javax.swing.JTextField();
        tfNumeroFornecedor = new javax.swing.JTextField();
        tfBairroFornecedor = new javax.swing.JTextField();
        tfCidadeFornecedor = new javax.swing.JTextField();
        cbEstadoFornecedor = new javax.swing.JComboBox();
        btLimparCamposFornecedor = new javax.swing.JButton();
        btSalvarFornecedor = new javax.swing.JButton();
        btSalvarAtualizarFornecedor = new javax.swing.JButton();
        btCancelarAtualizarFornecedor = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        tfNomeRepresentanteFornecedor = new javax.swing.JTextField();
        tfEmailRepresentanteFornecedor = new javax.swing.JTextField();
        ftfTelefoneRepresentnteFornecedor = new javax.swing.JFormattedTextField(Mascara("(##) ####-####"));
        ftfCelularRepresentanteFornecedor = new javax.swing.JFormattedTextField(Mascara("(##) ####-####"));
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        tfBuscaFornecedor = new javax.swing.JTextField();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbListaFornecedores = new javax.swing.JTable();
        btRelatorioFornecedor = new javax.swing.JButton();
        btAtualizarFornecedor = new javax.swing.JButton();
        btRemoverFornecedor = new javax.swing.JButton();
        ifTelaProdutoEstoque = new javax.swing.JInternalFrame();
        tpnAbasProdutoEstoque = new javax.swing.JTabbedPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        jPanel18 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        tfIdProduto = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        cbNomeProduto = new javax.swing.JComboBox();
        btNovoProduto = new javax.swing.JButton();
        tfTipoProduto = new javax.swing.JTextField();
        tfUnidMedidaProduto = new javax.swing.JTextField();
        tfQuantCompraProduto = new javax.swing.JTextField();
        tfNotaFiscalProduto = new javax.swing.JTextField();
        ftfDataCompraProduto = new javax.swing.JFormattedTextField();
        ftfDataValidadeProduto = new javax.swing.JFormattedTextField();
        btLimparCamposCliente1 = new javax.swing.JButton();
        btSalvarCliente1 = new javax.swing.JButton();
        btSalvarAtualizarProdutoEstoque = new javax.swing.JButton();
        btCancelarProdutoEstoque = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbListaProdutosEstoque = new javax.swing.JTable();
        jPanel21 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        tfBuscaProdutoEstoque = new javax.swing.JTextField();
        btRelatorioProduto = new javax.swing.JButton();
        btAtualizarProdutoEstoque = new javax.swing.JButton();
        btRemoverProduto = new javax.swing.JButton();
        ifTelaCliente = new javax.swing.JInternalFrame();
        tpnAbasCliente = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        tfIdCliente = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        tfNomeCompletoCliente = new javax.swing.JTextField();
        ftfCpfCliente = new javax.swing.JFormattedTextField();
        ftfDataNascimentoCliente = new javax.swing.JFormattedTextField(Mascara("##/##/####"));
        ftfTelefoneCliente = new javax.swing.JFormattedTextField(Mascara("(##) ####-####"));
        ftfCelularCliente = new javax.swing.JFormattedTextField(Mascara("(##) ####-####"));
        tfLogradouroCliente = new javax.swing.JTextField();
        tfNumeroCliente = new javax.swing.JTextField();
        tfBairroCliente = new javax.swing.JTextField();
        tfCidadeCliente = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        tfComplementoCliente = new javax.swing.JTextField();
        ftfCepCliente = new javax.swing.JFormattedTextField(Mascara("#####-###"));
        jLabel29 = new javax.swing.JLabel();
        tfEmailCliente = new javax.swing.JTextField();
        btLimparCamposCliente = new javax.swing.JButton();
        btSalvarCliente = new javax.swing.JButton();
        btSalvarAtualizarCliente = new javax.swing.JButton();
        btCancelarAtualizarCliente = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        tfBuscaCliente = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbListaClientes = new javax.swing.JTable();
        btRelatorioCliente = new javax.swing.JButton();
        btAtualizarCliente = new javax.swing.JButton();
        btRemoverCliente = new javax.swing.JButton();
        ifTelaPdv = new javax.swing.JInternalFrame();
        tpnAbasPdv = new javax.swing.JTabbedPane();
        jPanel23 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        tfAtendente = new javax.swing.JTextField();
        jLabel53 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbListaProdutosVenda = new javax.swing.JTable();
        jLabel63 = new javax.swing.JLabel();
        lbDinheiro = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        ftfValorTotal = new javax.swing.JFormattedTextField();
        ftfValorRecebido = new javax.swing.JFormattedTextField();
        ftfValorTroco = new javax.swing.JFormattedTextField();
        jPanel24 = new javax.swing.JPanel();
        lbLogoff = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        lbCliente = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        lbFuncionario = new javax.swing.JLabel();
        lbPdv = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnArquivo = new javax.swing.JMenu();
        mnGerencia = new javax.swing.JMenu();
        smnClientes = new javax.swing.JMenu();
        miCadastrarCliente = new javax.swing.JMenuItem();
        miGerenciarCliente = new javax.swing.JMenuItem();
        smnFuncionarios = new javax.swing.JMenu();
        miCadastrarFuncionario = new javax.swing.JMenuItem();
        miGerenciarFuncionario = new javax.swing.JMenuItem();
        smnFornecedores = new javax.swing.JMenu();
        miCadastrarFornecedor = new javax.swing.JMenuItem();
        miGerenciarFornecedor = new javax.swing.JMenuItem();
        smnProdutos = new javax.swing.JMenu();
        miCadastrarProduto = new javax.swing.JMenuItem();
        miGerenciarProduto = new javax.swing.JMenuItem();
        miPDV = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        smnPizzas = new javax.swing.JMenu();
        mnSobre = new javax.swing.JMenu();
        miSobre = new javax.swing.JMenuItem();
        miManualUsuario = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SISTEMA QUICKER - GERENCIADOR DE PEDIDOS");
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        ifTelaFuncionario.setClosable(true);
        ifTelaFuncionario.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        ifTelaFuncionario.setIconifiable(true);
        ifTelaFuncionario.setTitle("TELA FUNCIONARIO");
        ifTelaFuncionario.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/funcionario.png"))); // NOI18N
        ifTelaFuncionario.setVisible(true);

        jLabel1.setText("Número Funcional");

        tfIdFuncionario.setEditable(false);
        tfIdFuncionario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfIdFuncionario.setText("[AUTO]");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("DADOS PESSOAIS"));

        jLabel9.setText("Bairro");

        jLabel8.setText("Número");

        jLabel7.setText("Tel Celular");

        jLabel10.setText("CEP");

        jLabel2.setText("Nome Completo");

        jLabel12.setText("Estado");

        jLabel11.setText("Cidade");

        jLabel6.setText("Tel. Fixo");

        jLabel5.setText("PIS");

        jLabel4.setText("Data Nascimento");

        jLabel3.setText("CPF");

        tfNomeCompletoFuncionario.setToolTipText("OBRIGATORIO");
        tfNomeCompletoFuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfNomeCompletoFuncionarioKeyReleased(evt);
            }
        });

        try {
            ftfCpfFuncionario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfCpfFuncionario.setToolTipText("OBRIGATORIO");
        ftfCpfFuncionario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ftfCpfFuncionarioFocusLost(evt);
            }
        });

        ftfDataNascimentoFuncionario.setToolTipText("DIA/MÊS/ANO - OBRIGATORIO");

        ftfPisFuncionario.setToolTipText("OBRIGATORIO");

        ftfTelefoneFuncionario.setToolTipText("Número De Telefone Da Residencia Do Funcionario.");

        ftfCelularFuncionario.setToolTipText("OBRIGATORIO");

        tfLogradouroFuncionario.setToolTipText("NOME DA RUA/AVENIDA - OBRIGATORIO");
        tfLogradouroFuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfLogradouroFuncionarioKeyReleased(evt);
            }
        });

        tfNumeroFuncionario.setToolTipText("NÚMERO DO DOMICILIO - OBRIGATORIO");

        tfBairroFuncionario.setToolTipText("OBRIGATORIO");
        tfBairroFuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfBairroFuncionarioKeyReleased(evt);
            }
        });

        tfCidadeFuncionario.setToolTipText("OBRIGATORIO");
        tfCidadeFuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfCidadeFuncionarioKeyReleased(evt);
            }
        });

        cbEstadoFuncionario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP" }));
        cbEstadoFuncionario.setToolTipText("Nome Do Estado [UF] Do Funcionario. (OBRIGATORIO)");

        jLabel21.setText("Logradouro");

        ftfCepFuncionario.setToolTipText("OBRIGATORIO");
        ftfCepFuncionario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ftfCepFuncionarioFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfNumeroFuncionario, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfCidadeFuncionario, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ftfCepFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(tfNomeCompletoFuncionario)
                    .addComponent(ftfDataNascimentoFuncionario)
                    .addComponent(ftfTelefoneFuncionario))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel12)
                            .addComponent(jLabel21))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfBairroFuncionario, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cbEstadoFuncionario, javax.swing.GroupLayout.Alignment.TRAILING, 0, 142, Short.MAX_VALUE)
                            .addComponent(tfLogradouroFuncionario, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ftfCelularFuncionario)
                            .addComponent(ftfPisFuncionario)
                            .addComponent(ftfCpfFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))))
                .addGap(10, 54, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(tfNomeCompletoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfCpfFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(ftfDataNascimentoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfPisFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(ftfTelefoneFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfCelularFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tfLogradouroFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(ftfCepFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNumeroFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfBairroFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfCidadeFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbEstadoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("DADOS FUNCIONAIS"));

        jLabel13.setText("Data Admissão");

        jLabel14.setText("Situação");

        lbDataDemissaoFuncionario.setText("Data Demissão");

        lbDataRetornoFuncionario.setText("Data Retorno");

        jLabel17.setText("Cargo");

        jLabel18.setText("Permissão");

        jLabel19.setText("Nome Usuario");

        jLabel20.setText("Senha");

        try {
            ftfDataAdmissaoFuncionario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfDataAdmissaoFuncionario.setToolTipText("DIA/MÊS/ANO - OBRIGATORIO");

        cbSituacaoFuncionario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Contratado", "Demitido", "Afastado", "Ferias" }));
        cbSituacaoFuncionario.setToolTipText("SITUAÇÃO DO FUNCIONARIO - OBRIGATORIO");
        cbSituacaoFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSituacaoFuncionarioActionPerformed(evt);
            }
        });

        try {
            ftfDataDemissaoFuncionario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfDataDemissaoFuncionario.setToolTipText("DIA/MÊS/ANO - OBRIGATORIO");

        try {
            ftfDataRetornoFuncionario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfDataRetornoFuncionario.setToolTipText("DIA/MÊS/ANO - OBRIGATORIO");

        cbCargoFuncionario.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        cbCargoFuncionario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Auxiliar de cozinha", "Caixa", "Garçom", "Gerente", "Pizzaiolo", "Serviços gerais" }));
        cbCargoFuncionario.setToolTipText("FUNÇÃO ATUAL - OBRIGATORIO");

        cbNivelPermissaoFuncionario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Usuario", "Administrador" }));
        cbNivelPermissaoFuncionario.setToolTipText("NIVEL DE PERMISSÃO - OBRIGATORIO");

        tfSenhaFuncionario.setToolTipText("SENHA PARA ACESSAR AO SISTEMA - 8 DIGITOS - OBRIGATORIO");
        tfSenhaFuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfSenhaFuncionarioKeyPressed(evt);
            }
        });

        tfNomeUsuarioFuncionario.setToolTipText("NOME PARA ACESAR AO SISTEMA");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(27, 27, 27)
                        .addComponent(ftfDataAdmissaoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel19)
                            .addComponent(lbDataDemissaoFuncionario))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ftfDataDemissaoFuncionario)
                            .addComponent(cbCargoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfNomeUsuarioFuncionario))))
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbDataRetornoFuncionario)
                    .addComponent(jLabel14)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(cbSituacaoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ftfDataRetornoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbNivelPermissaoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfSenhaFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(ftfDataAdmissaoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbSituacaoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbDataDemissaoFuncionario)
                    .addComponent(lbDataRetornoFuncionario)
                    .addComponent(ftfDataDemissaoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfDataRetornoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbCargoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)
                        .addComponent(jLabel17))
                    .addComponent(cbNivelPermissaoFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfNomeUsuarioFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19)
                        .addComponent(jLabel20))
                    .addComponent(tfSenhaFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        btLimparCamposFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/Eraser.png"))); // NOI18N
        btLimparCamposFuncionario.setText("Limpar Campos");
        btLimparCamposFuncionario.setToolTipText("LIMPAR TODOS OS CAMPOS.");
        btLimparCamposFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparCamposFuncionarioActionPerformed(evt);
            }
        });

        btSalvarFuncionario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btSalvarFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/save.png"))); // NOI18N
        btSalvarFuncionario.setText("Salvar");
        btSalvarFuncionario.setToolTipText("SALVAR OS DADOS INFORMADOS.");
        btSalvarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarFuncionarioActionPerformed(evt);
            }
        });

        btSalvarAtualizarFuncionario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btSalvarAtualizarFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/view_refresh.png"))); // NOI18N
        btSalvarAtualizarFuncionario.setText("Atualizar");
        btSalvarAtualizarFuncionario.setToolTipText("SALVAR OS DADOS INFORMADOS.");
        btSalvarAtualizarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarAtualizarFuncionarioActionPerformed(evt);
            }
        });

        btCancelarAtualizarFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Cancel.png"))); // NOI18N
        btCancelarAtualizarFuncionario.setText("Cancelar");
        btCancelarAtualizarFuncionario.setToolTipText("Limpar Todos Os Campos.");
        btCancelarAtualizarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarAtualizarFuncionarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(31, 31, 31)
                        .addComponent(tfIdFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btCancelarAtualizarFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btLimparCamposFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(272, 272, 272)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btSalvarAtualizarFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btSalvarFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfIdFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btSalvarFuncionario)
                    .addComponent(btLimparCamposFuncionario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btSalvarAtualizarFuncionario)
                    .addComponent(btCancelarAtualizarFuncionario))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        tpnAbasFuncionario.addTab("Cadastrar Funcionario", jScrollPane1);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO DE BUSCA"));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/search.PNG"))); // NOI18N
        jLabel22.setText("Palavra Chave");

        tfBuscaFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfBuscaFuncionarioActionPerformed(evt);
            }
        });
        tfBuscaFuncionario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfBuscaFuncionarioKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addComponent(tfBuscaFuncionario)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(tfBuscaFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("FUNCIONARIOS CADASTRADOS"));

        tbListaFuncionarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tbListaFuncionarios);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btRelatorioFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/report.png"))); // NOI18N
        btRelatorioFuncionario.setText("Mostrar Relatorio");
        btRelatorioFuncionario.setToolTipText("Selecione O Funcionario Que Deseja Visualizar Dados Completos.");
        btRelatorioFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRelatorioFuncionarioActionPerformed(evt);
            }
        });

        btAtualizarFuncionario.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btAtualizarFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/view_refresh.png"))); // NOI18N
        btAtualizarFuncionario.setText("Atualizar Dados");
        btAtualizarFuncionario.setToolTipText("Selecione O Funcionario Que Deseja Atualizar.");
        btAtualizarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAtualizarFuncionarioActionPerformed(evt);
            }
        });

        btRemoverFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/delete.png"))); // NOI18N
        btRemoverFuncionario.setText("Remover");
        btRemoverFuncionario.setToolTipText("Selecione O Funcionario Que Deseja Excluir.");
        btRemoverFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverFuncionarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btRelatorioFuncionario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btRemoverFuncionario)
                .addGap(18, 18, 18)
                .addComponent(btAtualizarFuncionario)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btRelatorioFuncionario)
                    .addComponent(btRemoverFuncionario)
                    .addComponent(btAtualizarFuncionario))
                .addGap(0, 37, Short.MAX_VALUE))
        );

        tpnAbasFuncionario.addTab("Gerenciar Funcionarios", jPanel7);

        javax.swing.GroupLayout ifTelaFuncionarioLayout = new javax.swing.GroupLayout(ifTelaFuncionario.getContentPane());
        ifTelaFuncionario.getContentPane().setLayout(ifTelaFuncionarioLayout);
        ifTelaFuncionarioLayout.setHorizontalGroup(
            ifTelaFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnAbasFuncionario)
        );
        ifTelaFuncionarioLayout.setVerticalGroup(
            ifTelaFuncionarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnAbasFuncionario, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
        );

        dpAreaTrabalho.add(ifTelaFuncionario);
        ifTelaFuncionario.setBounds(600, 20, 570, 580);

        ifTelaFornecedor.setClosable(true);
        ifTelaFornecedor.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        ifTelaFornecedor.setIconifiable(true);
        ifTelaFornecedor.setResizable(true);
        ifTelaFornecedor.setTitle("TELA FORNECEDOR");
        ifTelaFornecedor.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/partnership.png"))); // NOI18N
        ifTelaFornecedor.setVisible(true);

        jLabel32.setText("Codigo Fornecedor");

        tfIdFornecedor.setEditable(false);
        tfIdFornecedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfIdFornecedor.setText("[AUTO]");

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("DADOS FORNECEDOR"));

        jLabel36.setText("Bairro");

        jLabel37.setText("Número");

        jLabel38.setText("Logradouro");

        jLabel39.setText("CEP");

        jLabel40.setText("Razão Social");

        jLabel41.setText("Cidade");

        jLabel42.setText("Telefone");

        jLabel43.setText("CNPJ");

        jLabel46.setText("Nome Fantasia");

        jLabel48.setText("Estado");

        tfRazaoSocialFornecedor.setToolTipText("RAZÃO SOCIAL - OBRIGATORIO");
        tfRazaoSocialFornecedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfRazaoSocialFornecedorKeyReleased(evt);
            }
        });

        tfNomeFantasiaFornecedor.setToolTipText("NOME FANTASIA - OBRIGATORIO");
        tfNomeFantasiaFornecedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfNomeFantasiaFornecedorKeyReleased(evt);
            }
        });

        ftfCnpjFornecedor.setToolTipText("NÚMERO DO CADASTRO NACIONAL DE PESSOA JURIDICA | CNPJ - OBRIGATORIO");

        ftfTelefoneFornecedor.setToolTipText("OBRIGATORIO");

        try {
            ftfCepFornecedor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfCepFornecedor.setToolTipText("CODIGO DE ENDEREÇAMENTO POSTAL | CEP - OBRIGATORIO");
        ftfCepFornecedor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ftfCepFornecedorFocusLost(evt);
            }
        });

        tfLogradouroFornecedor.setToolTipText("NOME DA RUA/AVENIDA - OBRIGATORIO");

        tfNumeroFornecedor.setToolTipText("NÚMERO DO ENDEREÇO - OBRIGATORIO");

        tfBairroFornecedor.setToolTipText("OBRIGATORIO");

        tfCidadeFornecedor.setToolTipText("OBRIGATORIO");

        cbEstadoFornecedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP" }));
        cbEstadoFornecedor.setToolTipText("AUTO SELEÇÃO");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40)
                    .addComponent(jLabel43)
                    .addComponent(jLabel39)
                    .addComponent(jLabel37)
                    .addComponent(jLabel41))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfNumeroFornecedor)
                    .addComponent(tfCidadeFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(tfRazaoSocialFornecedor)
                    .addComponent(ftfCnpjFornecedor)
                    .addComponent(ftfCepFornecedor))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46)
                    .addComponent(jLabel42)
                    .addComponent(jLabel38)
                    .addComponent(jLabel36)
                    .addComponent(jLabel48))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tfBairroFornecedor, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfLogradouroFornecedor, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ftfTelefoneFornecedor, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tfNomeFantasiaFornecedor, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbEstadoFornecedor, 0, 158, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jLabel46)
                    .addComponent(tfRazaoSocialFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNomeFantasiaFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(jLabel42)
                    .addComponent(ftfCnpjFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfTelefoneFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(jLabel39)
                    .addComponent(ftfCepFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfLogradouroFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNumeroFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfBairroFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel41)
                        .addComponent(tfCidadeFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel48)
                        .addComponent(cbEstadoFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );

        btLimparCamposFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/Eraser.png"))); // NOI18N
        btLimparCamposFornecedor.setText("Limpar Campos");
        btLimparCamposFornecedor.setToolTipText("LIMPAR TODOS OS CAMPOS.");
        btLimparCamposFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparCamposFornecedorActionPerformed(evt);
            }
        });

        btSalvarFornecedor.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btSalvarFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/save.png"))); // NOI18N
        btSalvarFornecedor.setText("Salvar");
        btSalvarFornecedor.setToolTipText("SALVAR OS DADOS INFORMADOS.");
        btSalvarFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarFornecedorActionPerformed(evt);
            }
        });

        btSalvarAtualizarFornecedor.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btSalvarAtualizarFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/view_refresh.png"))); // NOI18N
        btSalvarAtualizarFornecedor.setText("Atualizar");
        btSalvarAtualizarFornecedor.setToolTipText("SALVAR OS DADOS INFORMADOS.");
        btSalvarAtualizarFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarAtualizarFornecedorActionPerformed(evt);
            }
        });

        btCancelarAtualizarFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Cancel.png"))); // NOI18N
        btCancelarAtualizarFornecedor.setText("Cancelar");
        btCancelarAtualizarFornecedor.setToolTipText("Limpar Todos Os Campos.");
        btCancelarAtualizarFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarAtualizarFornecedorActionPerformed(evt);
            }
        });

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("DADOS REPRESENTANTE"));

        jLabel47.setText("Nome");

        jLabel15.setText("Tel. Comercial");

        jLabel16.setText("Tel. Celular");

        jLabel51.setText("E-mail");

        tfNomeRepresentanteFornecedor.setToolTipText("NOME DO REPRESENTANTE - OBRIGATORIO");
        tfNomeRepresentanteFornecedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfNomeRepresentanteFornecedorKeyReleased(evt);
            }
        });

        tfEmailRepresentanteFornecedor.setToolTipText("E-MAIL DO REPRESENTANTE - OBRIGATORIO");
        tfEmailRepresentanteFornecedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfEmailRepresentanteFornecedorKeyReleased(evt);
            }
        });

        ftfTelefoneRepresentnteFornecedor.setToolTipText("TELEFONE COMERCIAL DO REPRESENTANTE");

        ftfCelularRepresentanteFornecedor.setToolTipText("NÚMERO DE TELEFONE CELULAR DO REPRESENTANTE - OBRIGATORIO");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel47)
                    .addComponent(jLabel16))
                .addGap(22, 22, 22)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfNomeRepresentanteFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(ftfCelularRepresentanteFornecedor))
                .addGap(32, 32, 32)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51)
                    .addComponent(jLabel15))
                .addGap(27, 27, 27)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfEmailRepresentanteFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(ftfTelefoneRepresentnteFornecedor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(jLabel15)
                    .addComponent(tfNomeRepresentanteFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfTelefoneRepresentnteFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel51)
                    .addComponent(tfEmailRepresentanteFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfCelularRepresentanteFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(84, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addGap(31, 31, 31)
                        .addComponent(tfIdFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btLimparCamposFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btCancelarAtualizarFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(272, 272, 272)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btSalvarFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btSalvarAtualizarFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(tfIdFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSalvarAtualizarFornecedor)
                    .addComponent(btCancelarAtualizarFornecedor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btSalvarFornecedor)
                    .addComponent(btLimparCamposFornecedor))
                .addContainerGap(626, Short.MAX_VALUE))
        );

        jScrollPane5.setViewportView(jPanel9);

        tpnAbasFornecedor.addTab("Cadastrar Fornecedor", jScrollPane5);

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO DE BUSCA"));

        jLabel50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/search.PNG"))); // NOI18N
        jLabel50.setText("Palavra Chave");

        tfBuscaFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfBuscaFornecedorActionPerformed(evt);
            }
        });
        tfBuscaFornecedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfBuscaFornecedorKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel50)
                .addGap(18, 18, 18)
                .addComponent(tfBuscaFornecedor)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(tfBuscaFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("FORNECEDORES CADASTRADOS"));

        tbListaFornecedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tbListaFornecedores);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btRelatorioFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/report.png"))); // NOI18N
        btRelatorioFornecedor.setText("Mostrar Relatorio");
        btRelatorioFornecedor.setToolTipText("Selecione O Funcionario Que Deseja Visualizar Dados Completos.");
        btRelatorioFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRelatorioFornecedorActionPerformed(evt);
            }
        });

        btAtualizarFornecedor.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btAtualizarFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/view_refresh.png"))); // NOI18N
        btAtualizarFornecedor.setText("Atualizar Dados");
        btAtualizarFornecedor.setToolTipText("Selecione O Funcionario Que Deseja Atualizar.");
        btAtualizarFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAtualizarFornecedorActionPerformed(evt);
            }
        });

        btRemoverFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/delete.png"))); // NOI18N
        btRemoverFornecedor.setText("Remover");
        btRemoverFornecedor.setToolTipText("Selecione O Funcionario Que Deseja Excluir.");
        btRemoverFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverFornecedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btRelatorioFornecedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btRemoverFornecedor)
                .addGap(18, 18, 18)
                .addComponent(btAtualizarFornecedor)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btRelatorioFornecedor)
                    .addComponent(btAtualizarFornecedor)
                    .addComponent(btRemoverFornecedor))
                .addGap(0, 32, Short.MAX_VALUE))
        );

        tpnAbasFornecedor.addTab("Gerenciar Fornecedor", jPanel14);

        javax.swing.GroupLayout ifTelaFornecedorLayout = new javax.swing.GroupLayout(ifTelaFornecedor.getContentPane());
        ifTelaFornecedor.getContentPane().setLayout(ifTelaFornecedorLayout);
        ifTelaFornecedorLayout.setHorizontalGroup(
            ifTelaFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnAbasFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
        );
        ifTelaFornecedorLayout.setVerticalGroup(
            ifTelaFornecedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnAbasFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
        );

        dpAreaTrabalho.add(ifTelaFornecedor);
        ifTelaFornecedor.setBounds(10, 20, 570, 580);

        ifTelaProdutoEstoque.setClosable(true);
        ifTelaProdutoEstoque.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        ifTelaProdutoEstoque.setIconifiable(true);
        ifTelaProdutoEstoque.setTitle("TELA PRODUTO");
        ifTelaProdutoEstoque.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/codebar.png"))); // NOI18N
        ifTelaProdutoEstoque.setVisible(true);

        jLabel49.setText("Codigo Produto");

        tfIdProduto.setEditable(false);
        tfIdProduto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfIdProduto.setText("[AUTO]");

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("DADOS PRODUTO"));

        jLabel54.setText("Quant. Comprada");

        jLabel55.setText("Data Compra");

        jLabel56.setText("Nome");

        jLabel58.setText("Núm. Nota Fiscal");

        jLabel59.setText("Tipo");

        jLabel60.setText("Unid. Medida");

        jLabel61.setText("Data Validade");

        cbNomeProduto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbNomeProduto.setToolTipText("SELECIONE UM PRODUTO - OBRIGATORIO");
        cbNomeProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNomeProdutoActionPerformed(evt);
            }
        });

        btNovoProduto.setText("Novo");
        btNovoProduto.setToolTipText("ADICIONAR/EDITAR/REMOVER PRODUTO");
        btNovoProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoProdutoActionPerformed(evt);
            }
        });

        tfTipoProduto.setEditable(false);
        tfTipoProduto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfTipoProduto.setText("[AUTO]");

        tfUnidMedidaProduto.setEditable(false);
        tfUnidMedidaProduto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfUnidMedidaProduto.setText("[AUTO]");

        ftfDataCompraProduto.setToolTipText("DIA/MÊS/ANO - OBRIGATORIO");

        ftfDataValidadeProduto.setToolTipText("DIA/MÊS/ANO - OBRIGATORIO");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                            .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel54)
                                .addComponent(jLabel55))
                            .addGap(22, 22, 22))
                        .addGroup(jPanel19Layout.createSequentialGroup()
                            .addComponent(jLabel59)
                            .addGap(88, 88, 88)))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel56)
                        .addGap(81, 81, 81)))
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfTipoProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                            .addComponent(tfQuantCompraProduto)
                            .addComponent(ftfDataCompraProduto))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel61)
                            .addComponent(jLabel58)
                            .addComponent(jLabel60)))
                    .addComponent(cbNomeProduto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfUnidMedidaProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                            .addComponent(tfNotaFiscalProduto)
                            .addComponent(ftfDataValidadeProduto))
                        .addGap(0, 44, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btNovoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86))))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(cbNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btNovoProduto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(jLabel60)
                    .addComponent(tfTipoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfUnidMedidaProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(jLabel58)
                    .addComponent(tfQuantCompraProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfNotaFiscalProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(jLabel61)
                    .addComponent(ftfDataCompraProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfDataValidadeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btLimparCamposCliente1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/Eraser.png"))); // NOI18N
        btLimparCamposCliente1.setText("Limpar Campos");
        btLimparCamposCliente1.setToolTipText("LIMPAR TODOS OS CAMPOS.");

        btSalvarCliente1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btSalvarCliente1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/save.png"))); // NOI18N
        btSalvarCliente1.setText("Salvar");
        btSalvarCliente1.setToolTipText("SALVAR OS DADOS INFORMADOS.");

        btSalvarAtualizarProdutoEstoque.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btSalvarAtualizarProdutoEstoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/view_refresh.png"))); // NOI18N
        btSalvarAtualizarProdutoEstoque.setText("Atualizar");
        btSalvarAtualizarProdutoEstoque.setToolTipText("SALVAR OS DADOS INFORMADOS.");

        btCancelarProdutoEstoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Cancel.png"))); // NOI18N
        btCancelarProdutoEstoque.setText("Cancelar");
        btCancelarProdutoEstoque.setToolTipText("Limpar Todos Os Campos.");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jLabel49)
                        .addGap(31, 31, 31)
                        .addComponent(tfIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btLimparCamposCliente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btCancelarProdutoEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(272, 272, 272)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btSalvarCliente1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btSalvarAtualizarProdutoEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(tfIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(212, 212, 212)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSalvarAtualizarProdutoEstoque)
                    .addComponent(btCancelarProdutoEstoque))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btSalvarCliente1)
                    .addComponent(btLimparCamposCliente1))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        jScrollPane7.setViewportView(jPanel18);

        tpnAbasProdutoEstoque.addTab("Cadastrar Produto", jScrollPane7);

        jPanel20.setEnabled(false);

        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder("PRODUTOS CADASTRADOS"));

        tbListaProdutosEstoque.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tbListaProdutosEstoque);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
        );

        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO DE BUSCA"));

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/search.PNG"))); // NOI18N
        jLabel64.setText("Palavra Chave");

        tfBuscaProdutoEstoque.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfBuscaProdutoEstoqueActionPerformed(evt);
            }
        });
        tfBuscaProdutoEstoque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfBuscaProdutoEstoqueKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel64)
                .addGap(18, 18, 18)
                .addComponent(tfBuscaProdutoEstoque)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(tfBuscaProdutoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btRelatorioProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/report.png"))); // NOI18N
        btRelatorioProduto.setText("Mostrar Relatorio");
        btRelatorioProduto.setToolTipText("Selecione O Funcionario Que Deseja Visualizar Dados Completos.");
        btRelatorioProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRelatorioProdutoActionPerformed(evt);
            }
        });

        btAtualizarProdutoEstoque.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btAtualizarProdutoEstoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/view_refresh.png"))); // NOI18N
        btAtualizarProdutoEstoque.setText("Atualizar Dados");
        btAtualizarProdutoEstoque.setToolTipText("Selecione O Funcionario Que Deseja Atualizar.");

        btRemoverProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/delete.png"))); // NOI18N
        btRemoverProduto.setText("Remover");
        btRemoverProduto.setToolTipText("Selecione O Funcionario Que Deseja Excluir.");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btRelatorioProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                .addComponent(btRemoverProduto)
                .addGap(18, 18, 18)
                .addComponent(btAtualizarProdutoEstoque)
                .addContainerGap())
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btRelatorioProduto)
                    .addComponent(btAtualizarProdutoEstoque)
                    .addComponent(btRemoverProduto))
                .addGap(0, 52, Short.MAX_VALUE))
        );

        tpnAbasProdutoEstoque.addTab("Gerenciar Produtos", jPanel20);

        javax.swing.GroupLayout ifTelaProdutoEstoqueLayout = new javax.swing.GroupLayout(ifTelaProdutoEstoque.getContentPane());
        ifTelaProdutoEstoque.getContentPane().setLayout(ifTelaProdutoEstoqueLayout);
        ifTelaProdutoEstoqueLayout.setHorizontalGroup(
            ifTelaProdutoEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnAbasProdutoEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
        );
        ifTelaProdutoEstoqueLayout.setVerticalGroup(
            ifTelaProdutoEstoqueLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnAbasProdutoEstoque, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
        );

        dpAreaTrabalho.add(ifTelaProdutoEstoque);
        ifTelaProdutoEstoque.setBounds(670, 620, 570, 600);

        ifTelaCliente.setClosable(true);
        ifTelaCliente.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        ifTelaCliente.setIconifiable(true);
        ifTelaCliente.setTitle("TELA CLIENTE");
        ifTelaCliente.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/user.png"))); // NOI18N
        ifTelaCliente.setVisible(true);

        jLabel23.setText("Codigo Cliente");

        tfIdCliente.setEditable(false);
        tfIdCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfIdCliente.setText("[AUTO]");

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("DADOS PESSOAIS"));

        jLabel24.setText("Bairro");

        jLabel25.setText("Número");

        jLabel26.setText("Tel Celular");

        jLabel27.setText("CEP");

        jLabel28.setText("Nome Completo");

        jLabel30.setText("Cidade");

        jLabel31.setText("Tel. Fixo");

        jLabel33.setText("Data Nascimento");

        jLabel34.setText("CPF");

        tfNomeCompletoCliente.setToolTipText("OBRIGATORIO");
        tfNomeCompletoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfNomeCompletoClienteKeyReleased(evt);
            }
        });

        try {
            ftfCpfCliente.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        ftfCpfCliente.setToolTipText("OBRIGATORIO");

        ftfDataNascimentoCliente.setToolTipText("DIA/MÊS/ANO - OBRIGATORIO");

        ftfTelefoneCliente.setToolTipText("Número De Telefone Da Residencia Do Funcionario.");

        ftfCelularCliente.setToolTipText("OBRIGATORIO");

        tfLogradouroCliente.setToolTipText("NOME RUA/AVENIDA - OBRIGATORIO");
        tfLogradouroCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfLogradouroClienteKeyReleased(evt);
            }
        });

        tfNumeroCliente.setToolTipText("NÚMERO DO DOMICILIO - OBRIGATORIO");

        tfBairroCliente.setToolTipText("OBRIGATORIO");
        tfBairroCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfBairroClienteKeyReleased(evt);
            }
        });

        tfCidadeCliente.setToolTipText("OBRIGATORIO");
        tfCidadeCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfCidadeClienteKeyReleased(evt);
            }
        });

        jLabel35.setText("Logradouro");

        jLabel45.setText("Ponto Ref.");

        tfComplementoCliente.setToolTipText("PONTO DE REFERENCIA - OBRIGATORIO");
        tfComplementoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfComplementoClienteKeyReleased(evt);
            }
        });

        ftfCepCliente.setToolTipText("OBRIGATORIO");
        ftfCepCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ftfCepClienteFocusLost(evt);
            }
        });

        jLabel29.setText("E-mail");

        tfEmailCliente.setToolTipText("OBRIGATORIO");
        tfEmailCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfEmailClienteKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel33)
                    .addComponent(jLabel26)
                    .addComponent(jLabel35)
                    .addComponent(jLabel24)
                    .addComponent(jLabel45))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tfComplementoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                    .addComponent(ftfCelularCliente)
                    .addComponent(ftfDataNascimentoCliente)
                    .addComponent(tfNomeCompletoCliente, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tfLogradouroCliente)
                    .addComponent(tfBairroCliente))
                .addGap(43, 43, 43)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30)
                    .addComponent(jLabel25)
                    .addComponent(jLabel27)
                    .addComponent(jLabel34)
                    .addComponent(jLabel31)
                    .addComponent(jLabel29))
                .addGap(70, 70, 70)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ftfCpfCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                    .addComponent(ftfTelefoneCliente)
                    .addComponent(ftfCepCliente)
                    .addComponent(tfNumeroCliente)
                    .addComponent(tfCidadeCliente)
                    .addComponent(tfEmailCliente))
                .addGap(10, 10, 10))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jLabel34)
                    .addComponent(tfNomeCompletoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ftfCpfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(ftfDataNascimentoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(ftfTelefoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(ftfCelularCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(ftfCepCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfLogradouroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(jLabel25)
                    .addComponent(tfNumeroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfBairroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(tfCidadeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(tfComplementoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(tfEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btLimparCamposCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/Eraser.png"))); // NOI18N
        btLimparCamposCliente.setText("Limpar Campos");
        btLimparCamposCliente.setToolTipText("LIMPAR TODOS OS CAMPOS.");
        btLimparCamposCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLimparCamposClienteActionPerformed(evt);
            }
        });

        btSalvarCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btSalvarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/save.png"))); // NOI18N
        btSalvarCliente.setText("Salvar");
        btSalvarCliente.setToolTipText("SALVAR OS DADOS INFORMADOS.");
        btSalvarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarClienteActionPerformed(evt);
            }
        });

        btSalvarAtualizarCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btSalvarAtualizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/view_refresh.png"))); // NOI18N
        btSalvarAtualizarCliente.setText("Atualizar");
        btSalvarAtualizarCliente.setToolTipText("SALVAR OS DADOS INFORMADOS.");
        btSalvarAtualizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarAtualizarClienteActionPerformed(evt);
            }
        });

        btCancelarAtualizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/Cancel.png"))); // NOI18N
        btCancelarAtualizarCliente.setText("Cancelar");
        btCancelarAtualizarCliente.setToolTipText("Limpar Todos Os Campos.");
        btCancelarAtualizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarAtualizarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGap(31, 31, 31)
                        .addComponent(tfIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btLimparCamposCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btCancelarAtualizarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(272, 272, 272)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btSalvarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btSalvarAtualizarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 0, 0))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(tfIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSalvarAtualizarCliente)
                    .addComponent(btCancelarAtualizarCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btSalvarCliente)
                    .addComponent(btLimparCamposCliente))
                .addGap(1, 1, 1))
        );

        jScrollPane3.setViewportView(jPanel6);

        tpnAbasCliente.addTab("Cadastrar Cliente", jScrollPane3);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("FILTRO DE BUSCA"));

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/search.PNG"))); // NOI18N
        jLabel44.setText("Palavra Chave");

        tfBuscaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfBuscaClienteActionPerformed(evt);
            }
        });
        tfBuscaCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tfBuscaClienteKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel44)
                .addGap(18, 18, 18)
                .addComponent(tfBuscaCliente)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(tfBuscaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("CLIENTES CADASTRADOS"));

        tbListaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tbListaClientes);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        btRelatorioCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/report.png"))); // NOI18N
        btRelatorioCliente.setText("Mostrar Relatorio");
        btRelatorioCliente.setToolTipText("Selecione O Funcionario Que Deseja Visualizar Dados Completos.");
        btRelatorioCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRelatorioClienteActionPerformed(evt);
            }
        });

        btAtualizarCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btAtualizarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/view_refresh.png"))); // NOI18N
        btAtualizarCliente.setText("Atualizar Dados");
        btAtualizarCliente.setToolTipText("Selecione O Funcionario Que Deseja Atualizar.");
        btAtualizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAtualizarClienteActionPerformed(evt);
            }
        });

        btRemoverCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/delete.png"))); // NOI18N
        btRemoverCliente.setText("Remover");
        btRemoverCliente.setToolTipText("Selecione O Funcionario Que Deseja Excluir.");
        btRemoverCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btRelatorioCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btRemoverCliente)
                .addGap(18, 18, 18)
                .addComponent(btAtualizarCliente)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btRelatorioCliente)
                    .addComponent(btAtualizarCliente)
                    .addComponent(btRemoverCliente))
                .addGap(0, 52, Short.MAX_VALUE))
        );

        tpnAbasCliente.addTab("Gerenciar Clientes", jPanel10);

        javax.swing.GroupLayout ifTelaClienteLayout = new javax.swing.GroupLayout(ifTelaCliente.getContentPane());
        ifTelaCliente.getContentPane().setLayout(ifTelaClienteLayout);
        ifTelaClienteLayout.setHorizontalGroup(
            ifTelaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnAbasCliente)
        );
        ifTelaClienteLayout.setVerticalGroup(
            ifTelaClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnAbasCliente)
        );

        dpAreaTrabalho.add(ifTelaCliente);
        ifTelaCliente.setBounds(10, 620, 570, 600);

        ifTelaPdv.setClosable(true);
        ifTelaPdv.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        ifTelaPdv.setTitle("TELA PDV");
        ifTelaPdv.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/pdv.png"))); // NOI18N
        ifTelaPdv.setVisible(true);

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INFORMAÇÕES", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jLabel52.setText("Atendente");

        tfAtendente.setEditable(false);
        tfAtendente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfAtendenteActionPerformed(evt);
            }
        });

        jLabel53.setText("Data");

        jLabel57.setText("Hora");

        jLabel62.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("<html> <span style=\"color: rgb(255, 0, 0);\"><marquee behavior=scroll>VOLTE SEMPRE!</marquee></span> </html>");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel62)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(jLabel52)
                                .addGap(18, 18, 18)
                                .addComponent(tfAtendente, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(jLabel53)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel57)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(tfAtendente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(jLabel57))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder("LOGO BUZZIOS AQUI"));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 167, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tbListaProdutosVenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(tbListaProdutosVenda);

        jLabel63.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel63.setText("Valor Total");

        lbDinheiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/money.png"))); // NOI18N
        lbDinheiro.setToolTipText("VENDA EM DINHEIRO");
        lbDinheiro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbDinheiroMouseClicked(evt);
            }
        });

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/credit_card.png"))); // NOI18N
        jLabel68.setToolTipText("VENDA EM CARTÃO - CREDITO/DEBITO");

        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/agreement.png"))); // NOI18N
        jLabel69.setToolTipText("VENDA EM CONVENIO");

        jLabel70.setText("R$");

        ftfValorTotal.setEditable(false);
        ftfValorTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        ftfValorTotal.setText("326,48");

        ftfValorRecebido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ftfValorRecebidoActionPerformed(evt);
            }
        });
        ftfValorRecebido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ftfValorRecebidoFocusLost(evt);
            }
        });

        ftfValorTroco.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("¤#,##0.00"))));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel70))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(lbDinheiro)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel68)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel69)))
                .addGap(9, 9, 9)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ftfValorTroco)
                    .addComponent(ftfValorRecebido)
                    .addComponent(ftfValorTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel70)
                                .addComponent(ftfValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(ftfValorRecebido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ftfValorTroco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbDinheiro)
                            .addComponent(jLabel68)
                            .addComponent(jLabel69))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tpnAbasPdv.addTab("PDV", jPanel23);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 949, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 593, Short.MAX_VALUE)
        );

        tpnAbasPdv.addTab("Gerenciar PDV", jPanel24);

        javax.swing.GroupLayout ifTelaPdvLayout = new javax.swing.GroupLayout(ifTelaPdv.getContentPane());
        ifTelaPdv.getContentPane().setLayout(ifTelaPdvLayout);
        ifTelaPdvLayout.setHorizontalGroup(
            ifTelaPdvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnAbasPdv)
        );
        ifTelaPdvLayout.setVerticalGroup(
            ifTelaPdvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tpnAbasPdv)
        );

        dpAreaTrabalho.add(ifTelaPdv);
        ifTelaPdv.setBounds(10, 1230, 970, 650);

        lbLogoff.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lbLogoff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/logout.png"))); // NOI18N
        lbLogoff.setToolTipText("TROCAR USUARIO/SAIR");
        lbLogoff.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbLogoffMouseClicked(evt);
            }
        });
        dpAreaTrabalho.add(lbLogoff);
        lbLogoff.setBounds(1290, 80, 40, 40);

        jToolBar1.setRollover(true);
        jToolBar1.setBorderPainted(false);

        lbCliente.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/user.png"))); // NOI18N
        lbCliente.setToolTipText("CADASTRAR CLIENTE");
        lbCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbClienteMouseClicked(evt);
            }
        });
        jToolBar1.add(lbCliente);

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/partnership.png"))); // NOI18N
        jLabel65.setToolTipText("CADASTAR FORNECEDOR (CTRL+5)");
        jLabel65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel65MouseClicked(evt);
            }
        });
        jToolBar1.add(jLabel65);

        lbFuncionario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/funcionario.png"))); // NOI18N
        lbFuncionario.setToolTipText("CADASTRAR FUNCIONARIO");
        lbFuncionario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbFuncionarioMouseClicked(evt);
            }
        });
        jToolBar1.add(lbFuncionario);

        lbPdv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/pdv.png"))); // NOI18N
        lbPdv.setToolTipText("PONTO DE VENDA - PDV (CTRL+9)");
        lbPdv.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbPdvMouseClicked(evt);
            }
        });
        jToolBar1.add(lbPdv);

        dpAreaTrabalho.add(jToolBar1);
        jToolBar1.setBounds(1180, 10, 160, 60);

        mnArquivo.setText("Arquivo");
        jMenuBar1.add(mnArquivo);

        mnGerencia.setText("Gerencia");
        mnGerencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnGerenciaActionPerformed(evt);
            }
        });

        smnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/user.png"))); // NOI18N
        smnClientes.setText("Clientes");

        miCadastrarCliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        miCadastrarCliente.setText("Cadastrar Cliente");
        miCadastrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCadastrarClienteActionPerformed(evt);
            }
        });
        smnClientes.add(miCadastrarCliente);

        miGerenciarCliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_MASK));
        miGerenciarCliente.setText("Gerenciar Clientes");
        miGerenciarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miGerenciarClienteActionPerformed(evt);
            }
        });
        smnClientes.add(miGerenciarCliente);

        mnGerencia.add(smnClientes);

        smnFuncionarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/funcionario.png"))); // NOI18N
        smnFuncionarios.setText("Funcionarios");

        miCadastrarFuncionario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_MASK));
        miCadastrarFuncionario.setText("Cadastrar Funcionario");
        miCadastrarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCadastrarFuncionarioActionPerformed(evt);
            }
        });
        smnFuncionarios.add(miCadastrarFuncionario);

        miGerenciarFuncionario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.CTRL_MASK));
        miGerenciarFuncionario.setText("Gerenciar Funcionarios");
        miGerenciarFuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miGerenciarFuncionarioActionPerformed(evt);
            }
        });
        smnFuncionarios.add(miGerenciarFuncionario);

        mnGerencia.add(smnFuncionarios);

        smnFornecedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/partnership.png"))); // NOI18N
        smnFornecedores.setText("Fornecedores");

        miCadastrarFornecedor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_5, java.awt.event.InputEvent.CTRL_MASK));
        miCadastrarFornecedor.setText("Cadastrar Fornecedor");
        miCadastrarFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCadastrarFornecedorActionPerformed(evt);
            }
        });
        smnFornecedores.add(miCadastrarFornecedor);

        miGerenciarFornecedor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_6, java.awt.event.InputEvent.CTRL_MASK));
        miGerenciarFornecedor.setText("Gerenciar fornecedores");
        miGerenciarFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miGerenciarFornecedorActionPerformed(evt);
            }
        });
        smnFornecedores.add(miGerenciarFornecedor);

        mnGerencia.add(smnFornecedores);

        smnProdutos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/codebar.png"))); // NOI18N
        smnProdutos.setText("Produtos");

        miCadastrarProduto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_7, java.awt.event.InputEvent.CTRL_MASK));
        miCadastrarProduto.setText("Cadastrar Produto");
        miCadastrarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miCadastrarProdutoActionPerformed(evt);
            }
        });
        smnProdutos.add(miCadastrarProduto);

        miGerenciarProduto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_8, java.awt.event.InputEvent.CTRL_MASK));
        miGerenciarProduto.setText("Gerenciar Produtos");
        miGerenciarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miGerenciarProdutoActionPerformed(evt);
            }
        });
        smnProdutos.add(miGerenciarProduto);

        mnGerencia.add(smnProdutos);

        miPDV.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_9, java.awt.event.InputEvent.CTRL_MASK));
        miPDV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/pdv.png"))); // NOI18N
        miPDV.setText("PDV");
        miPDV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miPDVActionPerformed(evt);
            }
        });
        mnGerencia.add(miPDV);
        mnGerencia.add(jSeparator1);

        smnPizzas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/pizza.png"))); // NOI18N
        smnPizzas.setText("Pizzas");
        smnPizzas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                smnPizzasActionPerformed(evt);
            }
        });
        mnGerencia.add(smnPizzas);

        jMenuBar1.add(mnGerencia);

        mnSobre.setText("Sobre");

        miSobre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        miSobre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/Help-icon.png"))); // NOI18N
        miSobre.setText("Sobre Quicker");
        miSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSobreActionPerformed(evt);
            }
        });
        mnSobre.add(miSobre);

        miManualUsuario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        miManualUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icones-pequenos-3/pdf-icon.png"))); // NOI18N
        miManualUsuario.setText("Manual do Usuario");
        miManualUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miManualUsuarioActionPerformed(evt);
            }
        });
        mnSobre.add(miManualUsuario);

        jMenuBar1.add(mnSobre);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dpAreaTrabalho, javax.swing.GroupLayout.DEFAULT_SIZE, 1342, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dpAreaTrabalho, javax.swing.GroupLayout.DEFAULT_SIZE, 1894, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void miSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSobreActionPerformed
        // TODO add your handling code here:
        /* CHAMANDO A TELA DE SOBRE */
        TelaSobre telaSobre=new TelaSobre(this, true);
        telaSobre.setTitle("Sobre Quicker");
        telaSobre.setVisible(true);
    }//GEN-LAST:event_miSobreActionPerformed

    private void mnGerenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnGerenciaActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_mnGerenciaActionPerformed

    private void miCadastrarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCadastrarFuncionarioActionPerformed
        // TODO add your handling code here:
        /* EXIBINDO O CODIGO DO FUNCIONARIO */
        listaFuncionarios = conexaoTabelaFuncionario.selecionarTodosFuncionarios();
        for(int i=0; i<listaFuncionarios.size(); i++)
        {
            if(listaFuncionarios.size()<1)
            {
                tfIdFuncionario.setText("0"+listaFuncionarios.size()+1);
            }else
            {
                tfIdFuncionario.setText(listaFuncionarios.size()+1+" ");
            }
                
        }
        /* ABRINDO TELA DE CADASTRO DE NOVO FUNCIONARIO */
        tpnAbasFuncionario.setSelectedIndex(0);
        ifTelaFuncionario.setVisible(true);
        ifTelaFuncionario.setTitle("CADASTRAR FUNCIONARIO");
        ifTelaFuncionario.setLocation(40, 40);
        /* SOBREPOR A TELA, CASO TENHA OUTRAS ABERTAS*/
        try{
            ifTelaFuncionario.setSelected(true);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_miCadastrarFuncionarioActionPerformed

    private void miCadastrarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCadastrarProdutoActionPerformed
        // TODO add your handling code here:
        preencherComboBoxProdutoMateria(); // APOS FECHAR O NOVO PRODUTO ELE PREEENCHE MAS NÃO MOSTRAR - TIPO NEM UNIDADE MEDIDA
        tpnAbasProdutoEstoque.setSelectedIndex(0);
        ifTelaProdutoEstoque.setVisible(true);
        ifTelaProdutoEstoque.setTitle("NOVO PRODUTO - ESTOQUE");
        ifTelaProdutoEstoque.setLocation(120, 120);
        /* SOBREPOR A TELA, CASO TENHA OUTRAS ABERTAS*/
        try{
            ifTelaProdutoEstoque.setSelected(true);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_miCadastrarProdutoActionPerformed

    private void miCadastrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCadastrarClienteActionPerformed
        // TODO add your handling code here:
        /* EXIBINDO O CODIGO DO CLIENTE */
        listaClientes = conexaoTabelaCliente.selecionarTodosClientes();
        for(int i=0; i<listaClientes.size(); i++)
        {
                tfIdCliente.setText(listaProdutosMateria.size()+1+" ");
        }
        /* ABRINDO TELA DE CADASTRO DE NOVO CLIENTE */
        tpnAbasCliente.setSelectedIndex(0);
        ifTelaCliente.setVisible(true);
        ifTelaCliente.setTitle("CADASTRAR NOVO CLIENTE");
        ifTelaCliente.setLocation(80, 80);
        tfNomeCompletoCliente.requestFocus();
        /* SOBREPOR A TELA, CASO TENHA OUTRAS ABERTAS*/
        try
        {
        ifTelaCliente.setSelected(true); 
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_miCadastrarClienteActionPerformed

    /***
     * TORNANDO VISIVEL A TELA CLIENTES - ABA GERENCIAR CLIENTES.
     * @param evt 
     */
    private void miGerenciarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miGerenciarClienteActionPerformed
        // TODO add your handling code here:
        /* EXIBINDO O CODIGO DO CLIENTE*/
        tpnAbasCliente.setSelectedIndex(1);
        ifTelaCliente.setVisible(true);
        ifTelaCliente.setTitle("GERENCIAR CLIENTES");
        ifTelaCliente.setLocation(80, 80);
        /* SOBREPOR A TELA, CASO TENHA OUTRAS ABERTAS*/
        try{
            ifTelaCliente.setSelected(true);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_miGerenciarClienteActionPerformed

    /***
     * MOSTRANDO A TELA DE FUNCIONARIOS - ABA GERENCIAR FUNCIONARIO.
     * @param evt 
     */
    private void miGerenciarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miGerenciarFuncionarioActionPerformed
        // TODO add your handling code here:
        tpnAbasFuncionario.setSelectedIndex(1);
        ifTelaFuncionario.setVisible(true);
        ifTelaFuncionario.setTitle("GERENCIAR FUNCIONARIOS");
        ifTelaFuncionario.setLocation(40, 40);
        /* SOBREPOR A TELA, CASO TENHA OUTRAS ABERTAS*/
        try{
            ifTelaFuncionario.setSelected(true);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_miGerenciarFuncionarioActionPerformed

    private void btSalvarAtualizarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarAtualizarFuncionarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btSalvarAtualizarFuncionarioActionPerformed

    /***
 * VENDO QUAL A ITUAÇÃO DO FUNCIONARIO, E HABILITANDO ALGUMAS FUNÇÕES ESPECIFICAS.
 * @param evt 
 */
    private void cbSituacaoFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSituacaoFuncionarioActionPerformed
        // TODO add your handling code here:
        if(cbSituacaoFuncionario.getSelectedItem().toString().equalsIgnoreCase("Contratado"))
        {
            lbDataDemissaoFuncionario.setVisible(false);
            ftfDataDemissaoFuncionario.setVisible(false);
            lbDataRetornoFuncionario.setVisible(false);
            ftfDataRetornoFuncionario.setVisible(false);
        }else
            if(cbSituacaoFuncionario.getSelectedItem().toString().equalsIgnoreCase("Demitido"))
            {
                lbDataDemissaoFuncionario.setVisible(true);
                ftfDataDemissaoFuncionario.setVisible(true);
                lbDataRetornoFuncionario.setVisible(false);
                ftfDataRetornoFuncionario.setVisible(false);
            }else
                if(cbSituacaoFuncionario.getSelectedItem().toString().equalsIgnoreCase("Afastado"))
                {
                    lbDataDemissaoFuncionario.setVisible(true);
                    lbDataDemissaoFuncionario.setText("Data Afastamento");
                    ftfDataDemissaoFuncionario.setVisible(true);
                    lbDataRetornoFuncionario.setVisible(true);
                    ftfDataRetornoFuncionario.setVisible(true);
                }else
                    if(cbSituacaoFuncionario.getSelectedItem().toString().equalsIgnoreCase("Ferias"))
                    {
                        lbDataDemissaoFuncionario.setVisible(true);
                        lbDataDemissaoFuncionario.setText("Data Afastamento");
                        ftfDataDemissaoFuncionario.setVisible(true);
                        lbDataRetornoFuncionario.setVisible(true);
                        ftfDataRetornoFuncionario.setVisible(true);
                    }
    }//GEN-LAST:event_cbSituacaoFuncionarioActionPerformed

    private void miCadastrarFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miCadastrarFornecedorActionPerformed
        // TODO add your handling code here:
        tpnAbasFornecedor.setSelectedIndex(0);
        ifTelaFornecedor.setTitle("CADASTRAR NOVO FORNECEDOR");
        ifTelaFornecedor.setVisible(true);
        ifTelaFornecedor.setLocation(0, 0);
        /* SOBREPOR A TELA, CASO TENHA OUTRAS ABERTAS*/
        try{
            ifTelaFornecedor.setSelected(true);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_miCadastrarFornecedorActionPerformed

    private void miGerenciarFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miGerenciarFornecedorActionPerformed
        // TODO add your handling code here:
        tpnAbasFornecedor.setSelectedIndex(1);
        ifTelaFornecedor.setTitle("GERENCIAR FORNECEDORES");
        ifTelaFornecedor.setVisible(true);
        ifTelaFornecedor.setLocation(0, 0);
        /* SOBREPOR A TELA, CASO TENHA OUTRAS ABERTAS*/
        try{
            ifTelaFornecedor.setSelected(true);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_miGerenciarFornecedorActionPerformed
/***
 * TORNANDO A TELA DE CADASTRO DE NOVA MATERIA PRIMA - PRODUTO ESTOQUE - VISIVEL
 * @param evt 
 */
    private void btNovoProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoProdutoActionPerformed
        // TODO add your handling code here:
        TelaCadastrarProduto telaNovoProduto=new TelaCadastrarProduto(this, true);
        telaNovoProduto.setTitle("CADASTRAR NOVO PRODUTO");
        telaNovoProduto.setVisible(true);
        preencherComboBoxProdutoMateria();
    }//GEN-LAST:event_btNovoProdutoActionPerformed
/***
 * TORNANDO A TELA DE VENDAS VISIVEL.
 * @param evt 
 */
    private void miPDVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miPDVActionPerformed
        // TODO add your handling code here:
        ifTelaPdv.setLocation(150, 100);
        ifTelaPdv.setSize(949, 593);
        tpnAbasPdv.setSelectedIndex(0);
        ifTelaPdv.setTitle("PONTO DE VENDA - PDV");
        ifTelaPdv.setVisible(true);
        /* SOBREPOR A TELA, CASO TENHA OUTRAS ABERTAS*/
        try{
            ifTelaPdv.setSelected(true);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_miPDVActionPerformed

    private void ftfValorRecebidoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ftfValorRecebidoFocusLost
        // TODO add your handling code here:
        System.out.println(ftfValorTotal.getText());
        System.out.println(ftfValorRecebido.getText());
//        double total=Double.parseDouble(ftfValorTotal.getText().replace(",", "."));
//        double recebido=Double.parseDouble(ftfValorRecebido.getText().replace(",", "."));
//        System.out.println(total+" --- "+recebido);
//        double troco=(total-recebido);
//        ftfValorTroco.setValue(troco+"");
    }//GEN-LAST:event_ftfValorRecebidoFocusLost

    private void tfAtendenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfAtendenteActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_tfAtendenteActionPerformed

    private void lbDinheiroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbDinheiroMouseClicked
        // TODO add your handling code here:
        TelaVendaDinheiro vendaDinnheiro=new TelaVendaDinheiro(this, true);
        vendaDinnheiro.setVisible(true);
    }//GEN-LAST:event_lbDinheiroMouseClicked

    private void ftfValorRecebidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ftfValorRecebidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ftfValorRecebidoActionPerformed

    private void ftfCepFornecedorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ftfCepFornecedorFocusLost
        // TODO add your handling code here:
        buscaCep("Fornecedor");
    }//GEN-LAST:event_ftfCepFornecedorFocusLost

    private void ftfCepFuncionarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ftfCepFuncionarioFocusLost
        // TODO add your handling code here:
        buscaCep("Funcionario");
    }//GEN-LAST:event_ftfCepFuncionarioFocusLost

    private void ftfCepClienteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ftfCepClienteFocusLost
        // TODO add your handling code here:
        buscaCep("Cliente");
    }//GEN-LAST:event_ftfCepClienteFocusLost

    private void btSalvarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarClienteActionPerformed
        // TODO add your handling code here:
        
        if(preenchimentoCorretoCliente())
        {
            // ADICIONAR CLIENTE AO BANCO DE DADOS (QUICKER.CLIENTE)
            conexaoTabelaCliente.inserirCliente(retornarDadosPreenchidosCliente());
            modeloTabelaCliente.inserirlistaCliente(conexaoTabelaCliente.selecionarTodosClientes());
            tbListaClientes.updateUI();
            limparCamposCliente();
        }
       
    }//GEN-LAST:event_btSalvarClienteActionPerformed

    private void tfNomeCompletoClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNomeCompletoClienteKeyReleased
        // TODO add your handling code here:
        tfNomeCompletoCliente.setText(tfNomeCompletoCliente.getText().toUpperCase());
    }//GEN-LAST:event_tfNomeCompletoClienteKeyReleased

    private void tfLogradouroClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfLogradouroClienteKeyReleased
        // TODO add your handling code here:
        tfLogradouroCliente.setText(tfLogradouroCliente.getText().toUpperCase());
    }//GEN-LAST:event_tfLogradouroClienteKeyReleased

    private void tfBairroClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfBairroClienteKeyReleased
        // TODO add your handling code here:
        tfBairroCliente.setText(tfBairroCliente.getText().toUpperCase());
    }//GEN-LAST:event_tfBairroClienteKeyReleased

    private void tfCidadeClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCidadeClienteKeyReleased
        // TODO add your handling code here:
        tfCidadeCliente.setText(tfCidadeCliente.getText().toUpperCase());
    }//GEN-LAST:event_tfCidadeClienteKeyReleased

    private void tfComplementoClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfComplementoClienteKeyReleased
        // TODO add your handling code here:
        tfComplementoCliente.setText(tfComplementoCliente.getText().toUpperCase());
    }//GEN-LAST:event_tfComplementoClienteKeyReleased

    private void tfEmailClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfEmailClienteKeyReleased
        // TODO add your handling code here:
        tfEmailCliente.setText(tfEmailCliente.getText().toLowerCase());
    }//GEN-LAST:event_tfEmailClienteKeyReleased

    private void btLimparCamposClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparCamposClienteActionPerformed
        // TODO add your handling code here:
       limparCamposCliente();
    }//GEN-LAST:event_btLimparCamposClienteActionPerformed

    private void lbLogoffMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbLogoffMouseClicked
        // TODO add your handling code here:
        fazerLogoff();
    }//GEN-LAST:event_lbLogoffMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        
        if((( JOptionPane.showConfirmDialog(rootPane, "Deseja fazer logoff?", "Logoff", 0)) == 0))
        {
            fazerLogoff();
        }
        
    }//GEN-LAST:event_formWindowClosing

    private void btSalvarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarFuncionarioActionPerformed
        // TODO add your handling code here:
        if(preenchimentoCorretoFuncionario())
        {
            // ADICIONAR FUNCIONARIO AO BANCO DE DADOS (QUICKER.FUNCIONARIOS)
            conexaoTabelaFuncionario.inserirFuncionario(retornarDadosPreenchidosFuncionario());
            modeloTabelaFuncionario.inserirlistaFuncionarios(conexaoTabelaFuncionario.selecionarTodosFuncionarios());
            tbListaFuncionarios.updateUI();
            limparCamposFuncionario();
        }
    }//GEN-LAST:event_btSalvarFuncionarioActionPerformed

    private void btCancelarAtualizarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarAtualizarFuncionarioActionPerformed
        // TODO add your handling code here:
        limparCamposFuncionario();
    }//GEN-LAST:event_btCancelarAtualizarFuncionarioActionPerformed

    private void btLimparCamposFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparCamposFuncionarioActionPerformed
        // TODO add your handling code here:
           limparCamposFuncionario();
    }//GEN-LAST:event_btLimparCamposFuncionarioActionPerformed

    private void tfNomeCompletoFuncionarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNomeCompletoFuncionarioKeyReleased
        // TODO add your handling code here:
        /* COLOCANDO O NOME DO FUNCIONARIO EM CAIXA ALTA - MAIUSCULO */
        tfNomeCompletoFuncionario.setText(tfNomeCompletoFuncionario.getText().toUpperCase());
        /* INSERINDO NOME DE USUARIO - PARA ACESSAR AO SISTEMA */
        tfNomeUsuarioFuncionario.setText(tfNomeCompletoFuncionario.getText().replace(" ", "-"));
        tfNomeUsuarioFuncionario.setText(tfNomeUsuarioFuncionario.getText().toLowerCase());
    }//GEN-LAST:event_tfNomeCompletoFuncionarioKeyReleased

    private void tfLogradouroFuncionarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfLogradouroFuncionarioKeyReleased
        // TODO add your handling code here:
        tfLogradouroFuncionario.setText(tfLogradouroFuncionario.getText().toUpperCase());
    }//GEN-LAST:event_tfLogradouroFuncionarioKeyReleased

    private void tfBairroFuncionarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfBairroFuncionarioKeyReleased
        // TODO add your handling code here:
        tfBairroFuncionario.setText(tfBairroFuncionario.getText().toUpperCase());
    }//GEN-LAST:event_tfBairroFuncionarioKeyReleased

    private void tfCidadeFuncionarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCidadeFuncionarioKeyReleased
        // TODO add your handling code here:
        tfCidadeFuncionario.setText(tfCidadeFuncionario.getText().toUpperCase());
    }//GEN-LAST:event_tfCidadeFuncionarioKeyReleased

    private void cbNomeProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNomeProdutoActionPerformed

        if(!lock_cbNomeProduto) // Se não estiver trancado, faça... (o comboBox é destrancado após a inicialização dos componentes)
        {
           tfTipoProduto.setText(listaProdutosMateria.get(cbNomeProduto.getSelectedIndex()).getUnidadeMedidaProdutoMateria());   
               
           tfUnidMedidaProduto.setText(listaProdutosMateria.get(cbNomeProduto.getSelectedIndex()).getUnidadeMedidaProdutoMateria()); 
        }     
    }//GEN-LAST:event_cbNomeProdutoActionPerformed

    private void btSalvarFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarFornecedorActionPerformed
        // TODO add your handling code here:
        if(preenchimentoCorretoFornecedor())
        {
            conexaoTabelaFornecedor.inserirFornecedor(retornarDadosPreenchidosFornecedor());
            // Limpar Campos
            modeloTabelaFornecedor.inserirlistaFornecedores(conexaoTabelaFornecedor.selecionarTodosFornecedores());
            tbListaFornecedores.updateUI();
        }
    }//GEN-LAST:event_btSalvarFornecedorActionPerformed

    private void tfRazaoSocialFornecedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfRazaoSocialFornecedorKeyReleased
        // TODO add your handling code here:
        tfRazaoSocialFornecedor.setText(tfRazaoSocialFornecedor.getText().toUpperCase());
    }//GEN-LAST:event_tfRazaoSocialFornecedorKeyReleased

    private void tfNomeFantasiaFornecedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNomeFantasiaFornecedorKeyReleased
        // TODO add your handling code here:
        tfNomeFantasiaFornecedor.setText(tfNomeFantasiaFornecedor.getText().toUpperCase());
    }//GEN-LAST:event_tfNomeFantasiaFornecedorKeyReleased

    private void tfNomeRepresentanteFornecedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfNomeRepresentanteFornecedorKeyReleased
        // TODO add your handling code here:
        tfNomeRepresentanteFornecedor.setText(tfNomeRepresentanteFornecedor.getText().toUpperCase());
    }//GEN-LAST:event_tfNomeRepresentanteFornecedorKeyReleased

    private void tfEmailRepresentanteFornecedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfEmailRepresentanteFornecedorKeyReleased
        // TODO add your handling code here:
        tfEmailRepresentanteFornecedor.setText(tfEmailRepresentanteFornecedor.getText().toLowerCase());
    }//GEN-LAST:event_tfEmailRepresentanteFornecedorKeyReleased

    private void miGerenciarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miGerenciarProdutoActionPerformed
        // TODO add your handling code here:
         tpnAbasProdutoEstoque.setSelectedIndex(1);
         ifTelaProdutoEstoque.setVisible(true);
         ifTelaProdutoEstoque.setTitle("GERENCIAR PRODUTOS");
         ifTelaProdutoEstoque.setLocation(40, 40);
         /* SOBREPOR A TELA, CASO TENHA OUTRAS ABERTAS*/
         try{
             ifTelaProdutoEstoque.setSelected(true);
         }catch(Exception e)
         {
             e.printStackTrace();
         }
    }//GEN-LAST:event_miGerenciarProdutoActionPerformed

    private void btRemoverFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverFornecedorActionPerformed
        // TODO add your handling code here:
        if(tbListaFornecedores.getSelectedRow()!=-1) // Se existe alguma linha selecionada, faça...
        {
            conexaoTabelaFornecedor.removerFornecedor(modeloTabelaFornecedor.retornalistaFornecedores().get(tbListaFornecedores.getSelectedRow()).getIdFornecedor(),tbListaFornecedores.getValueAt(tbListaFornecedores.getSelectedRow(), 0).toString());
            
            modeloTabelaFornecedor.inserirlistaFornecedores(conexaoTabelaFornecedor.selecionarTodosFornecedores());
            tbListaFornecedores.updateUI();
        }
    }//GEN-LAST:event_btRemoverFornecedorActionPerformed

    private void btRemoverFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverFuncionarioActionPerformed
        // TODO add your handling code here:
        if(tbListaFuncionarios.getSelectedRow()!=-1) // Se existe alguma linha selecionada, faça...
        {
          // comando gigante para excluir funcionarioAntigo
            
            
            conexaoTabelaFuncionario.deletarFuncionario(modeloTabelaFuncionario.retornalistaFuncioarios().get(tbListaFuncionarios.getSelectedRow()).getIdFuncionario(),tbListaFuncionarios.getValueAt(tbListaFuncionarios.getSelectedRow(), 0).toString());
            
            modeloTabelaFuncionario.inserirlistaFuncionarios(conexaoTabelaFuncionario.selecionarTodosFuncionarios());
            tbListaFuncionarios.updateUI();
        }
    }//GEN-LAST:event_btRemoverFuncionarioActionPerformed

    private void btRemoverClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverClienteActionPerformed
        // TODO add your handling code here:
        if(tbListaClientes.getSelectedRow()!=-1) // Se existe alguma linha selecionada, faça...
        {
          // comando gigante para excluir funcionarioAntigo
            
            
            conexaoTabelaCliente.deletarCliente(modeloTabelaCliente.retornalistaCliente().get(tbListaClientes.getSelectedRow()).getIdCliente(),tbListaClientes.getValueAt(tbListaClientes.getSelectedRow(), 0).toString());
            
            modeloTabelaCliente.inserirlistaCliente(conexaoTabelaCliente.selecionarTodosClientes());
            tbListaFornecedores.updateUI();
        }
    }//GEN-LAST:event_btRemoverClienteActionPerformed

    private void btRelatorioFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRelatorioFornecedorActionPerformed
        // TODO add your handling code here:
        //relatorioAqui
        relatorio("fornecedor");
    }//GEN-LAST:event_btRelatorioFornecedorActionPerformed

    /***
     * MOSTRANDO O RELATORIO DE 'CLIENTE' - 'FORNECEDOR' - 'FUNCIONARIO'
     * @param tipoRelatorio 
     */
    public void relatorio(String tipoRelatorio)
    {
        int posicaoSelecionada;
        switch(tipoRelatorio)
        {
            case "cliente": 
                listaClientes = conexaoTabelaCliente.selecionarTodosClientes();
                 posicaoSelecionada = listaClientes.size();
                if(tbListaClientes.getSelectedRow()!=-1) // Se existe alguma linha selecionada, faça...
                {
                    // CLIENTE SELECIONADO
                       JOptionPane.showMessageDialog(null, "<HTML><CENTER> -- DADOS DO CLIENTE --</CENTER></HTML>\nNOME COMPLETO: "+listaClientes.get(posicaoSelecionada).getNomeCliente()
                       +"\nTELEFONE FIXO: "+listaClientes.get(posicaoSelecionada).getTelefoneCliente()
                       +"\nTELEFONE CELULAR: "+listaClientes.get(posicaoSelecionada).getCelularCliente()
                       +"\nCEP: "+listaClientes.get(posicaoSelecionada).getCepCliente()
                       +"\nLOGRADOURO: "+listaClientes.get(posicaoSelecionada).getRuaAvnCliente()
                       +"\nBAIRRO: "+listaClientes.get(posicaoSelecionada).getBairroCliente()
                       +"\nCIDADE: "+listaClientes.get(posicaoSelecionada).getCidadeCliente()
                       +"\nCOMPLEMENTO: "+listaClientes.get(posicaoSelecionada).getComplementoCliente()
                       +"\nE-MAIL: "+listaClientes.get(posicaoSelecionada).getEmailCliente());
                   
                }
                break;
                
            case "fornecedor":
                listaFornecedores = conexaoTabelaFornecedor.selecionarTodosFornecedores();
                posicaoSelecionada = listaFornecedores.size();
                
                if(tbListaFornecedores.getSelectedRow()!=-1) // Se existe alguma linha selecionada, faça...
                {
                    // FORNECEDOR SELECIONADO
                       JOptionPane.showMessageDialog(null, "<HTML><CENTER> -- DADOS DA EMPRESA --</CENTER></HTML>\nNOME FANTASIA: "+listaFornecedores.get(posicaoSelecionada).getNomeFantasiaFornecedor()
                       +"\nRAZÃO SOCIAL: "+listaFornecedores.get(posicaoSelecionada).getRazaoSocialFornecedor()
                       +"\nTELFONE: "+listaFornecedores.get(posicaoSelecionada).getTelefoneFornecedor()
                       +"\nLOGRADOURO: "+listaFornecedores.get(posicaoSelecionada).getRuaAvnFornecedor()
                       +"\nBAIRRO: "+listaFornecedores.get(posicaoSelecionada).getBairroFornecedor()
                       +"\nCIDADE: "+listaFornecedores.get(posicaoSelecionada).getCidadeFornecedor()
                       +"\nESTADO: "+listaFornecedores.get(posicaoSelecionada).getEstadoFornecedor()
                       +"\n-- DADOS DO REPRESENTANTE --"
                       +"\nREPRESENTANTE: "+listaFornecedores.get(posicaoSelecionada).getRepresentanteFornecedor()
                       +"\nTELEFONE: "+listaFornecedores.get(posicaoSelecionada).getTelefoneRepresentanteFornecedor()
                       +"\nCELULAR: "+listaFornecedores.get(posicaoSelecionada).getCelularRepresentanteFornecedor()
                       +"\nE-MAIL: "+listaFornecedores.get(posicaoSelecionada).getEmailRepresentanteFornecedor());
                   
                }
            
                break;
                
            case "funcionario":
                 listaFuncionarios = conexaoTabelaFuncionario.selecionarTodosFuncionarios();
                 posicaoSelecionada = listaFuncionarios.size();
                if(tbListaFuncionarios.getSelectedRow()!=-1) // Se existe alguma linha selecionada, faça...
                {
                    // FORNECEDOR SELECIONADO
                       JOptionPane.showMessageDialog(null, "<HTML> <TABLE>"
                       +"<th><CENTER> -- DADOS DO FUNCIONARIO -- </CENTER></th>"
                       +"<td>NOME COMPLETO :"+listaFuncionarios.get(posicaoSelecionada).getNomeFuncionario()+"</td>"
                       +"<td>DATA NASCIMENTO: "+listaFuncionarios.get(posicaoSelecionada).getDataNascimentoFuncionario()+"</td>"
                       +"<td>NÚMERO PIS: "+listaFuncionarios.get(posicaoSelecionada).getPisFuncionario()+"</td>"
                       +"<td>TELEFONE FIXO: "+listaFuncionarios.get(posicaoSelecionada).getTelefoneFuncionario()+"</td>"
                       +"<td>TELEFONE CELULAR: "+listaFuncionarios.get(posicaoSelecionada).getCelularFuncionario()+"</td>"
                       +"<td>CEP: "+listaFuncionarios.get(posicaoSelecionada).getCepFuncionario()+"</td>"
                       +"<td>BAIRRO: "+listaFuncionarios.get(posicaoSelecionada).getBairroFuncionario()+"</td>"
                       +"<td>CIDADE: "+listaFuncionarios.get(posicaoSelecionada).getCidadeFuncionario()+"</td>"
                       +"<td>ESTADO: "+listaFuncionarios.get(posicaoSelecionada).getEstadoFuncionario()+"</td>"
                       +"<th><CENTER> -- DADOS FUNCIONAIS -- </CENTER></th>"
                      // +"DATA ADMISSÃO: "+listaFuncionarios.get(i).getDataAdamissaoFuncionario()+"</td>"
                      // +"DATA DEMISSÃO: "+listaFuncionarios.get(i).getDataDemissaoFuncionario()+"</td>"
                      // +"DATA AFASTAMENTO: "+listaFuncionarios.get(i).getDataDemissaoFuncionario()+"</td>"
                      // +"DATA RETORNO: "+listaFuncionarios.get(i).getDataRetornoFuncionario()+"</td>"
                       +"<td>CARGO ATUAL: "+listaFuncionarios.get(posicaoSelecionada).getCargoFuncionario()+"</td>"
                       +"<td>NIVEL PERMISSÃO: "+listaFuncionarios.get(posicaoSelecionada).getPermissaoFuncionario()+"</td>"
                       +"<td>NOME USUARIO: "+listaFuncionarios.get(posicaoSelecionada).getNomeUsuario()+"</td>"
                       +"<td>SENHA: "+listaFuncionarios.get(posicaoSelecionada).getSenhaFuncionario()+"</td>"
                       +"</table></html>");
             
                }
                break;
        }
    }
    private void lbFuncionarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbFuncionarioMouseClicked
        // TODO add your handling code here:
        miCadastrarFuncionarioActionPerformed(null);
    }//GEN-LAST:event_lbFuncionarioMouseClicked

    private void lbClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbClienteMouseClicked
        // TODO add your handling code here:
        miCadastrarClienteActionPerformed(null);
    }//GEN-LAST:event_lbClienteMouseClicked

    private void jLabel65MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel65MouseClicked
        // TODO add your handling code here:
        miCadastrarFornecedorActionPerformed(null);
    }//GEN-LAST:event_jLabel65MouseClicked

    private void lbPdvMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbPdvMouseClicked
        // TODO add your handling code here:
        miPDVActionPerformed(null);
    }//GEN-LAST:event_lbPdvMouseClicked

    private void miManualUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miManualUsuarioActionPerformed
        // TODO add your handling code here:
        try{

            PdfReader reader = new PdfReader("/manual/AVISOS-LIED.pdf");
            //PdfReader arquivo = reader;
            int n = reader.getNumberOfPages();
            System.out.println("Total de páginas para este pdf: "+n);
            
            //java.awt.Desktop.getDesktop().open( new File( "arquivo" ) );  
            
            Process pro = Runtime.getRuntime().exec("cmd.exe /c   C:/Users/Adonias/Documents/NetBeansProjects/ProjetoQuicker/src/manual/AVISOS-LIED.pdf");  
            pro.waitFor();  
            
            /*
            //extraindo o conteúdo de uma página específica
            String str=PdfTextExtractor.getTextFromPage(reader, 1);
            System.out.println("Conteudo: "+str);
            for(n=0;n<reader.getNumberOfPages();n++)
            {
                
            }
            */

        }catch(Exception x){
            x.printStackTrace();
        }
    }//GEN-LAST:event_miManualUsuarioActionPerformed

    private void tfSenhaFuncionarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfSenhaFuncionarioKeyPressed
        // TODO add your handling code here:
        if(tfSenhaFuncionario.getCaretPosition()<=3)
        {
            tfSenhaFuncionario.setBackground(Color.RED);
        }else
            if(tfSenhaFuncionario.getCaretPosition()>=4 && tfSenhaFuncionario.getCaretPosition()<=7)
            {
                tfSenhaFuncionario.setBackground(Color.YELLOW);
            }else
                if(tfSenhaFuncionario.getCaretPosition()==9)
                {
                    tfSenhaFuncionario.setBackground(Color.GREEN);
                }
    }//GEN-LAST:event_tfSenhaFuncionarioKeyPressed

    private void btRelatorioFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRelatorioFuncionarioActionPerformed
        // TODO add your handling code here:
        relatorio("funcionario");
    }//GEN-LAST:event_btRelatorioFuncionarioActionPerformed

    private void btRelatorioClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRelatorioClienteActionPerformed
        // TODO add your handling code here:
        relatorio("cliente");
    }//GEN-LAST:event_btRelatorioClienteActionPerformed

    private void btRelatorioProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRelatorioProdutoActionPerformed
        // TODO add your handling code here:
        relatorio("produto");
    }//GEN-LAST:event_btRelatorioProdutoActionPerformed

    private void btAtualizarFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAtualizarFornecedorActionPerformed
        // TODO add your handling code here:
        if(tbListaFornecedores.getSelectedRow()!=-1) // SE o usuário selecionou uma linha da tabela...
        {
             tpnAbasFornecedor.setTitleAt(0, "ATUALIZAR DADOS");
             tpnAbasFornecedor.setEnabledAt(1, false);
             btSalvarAtualizarFornecedor.setVisible(true); // Tornando visível o botão ATUALIZAR
             btSalvarFornecedor.setVisible(false);// Tornando INVISIVEL o botão SalvarFornecedor
             btCancelarAtualizarFornecedor.setVisible(true);// TORNA VISIVEL O BOTÃO CANCELAR
             btLimparCamposFornecedor.setVisible(false);// Torna o bt invisivel
            
        fornecedorAntigo= modeloTabelaFornecedor.retornalistaFornecedores().get(tbListaFornecedores.getSelectedRow());
        
        tfRazaoSocialFornecedor.setText(fornecedorAntigo.getRazaoSocialFornecedor());
        tfNomeFantasiaFornecedor.setText(fornecedorAntigo.getNomeFantasiaFornecedor());
        ftfCnpjFornecedor.setValue(fornecedorAntigo.getCnpjFornecedor());
        ftfTelefoneFornecedor.setValue(fornecedorAntigo.getTelefoneFornecedor());
        ftfCepFornecedor.setValue(fornecedorAntigo.getCepFornecedor());        
        tfLogradouroFornecedor.setText(fornecedorAntigo.getRuaAvnFornecedor());
        tfNumeroFornecedor.setText(fornecedorAntigo.getNumeroFornecedor());
        tfBairroFornecedor.setText(fornecedorAntigo.getBairroFornecedor());
        tfCidadeFornecedor.setText(fornecedorAntigo.getCidadeFornecedor());
        cbEstadoFornecedor.setSelectedItem(fornecedorAntigo.getEstadoFornecedor());
        tfNomeRepresentanteFornecedor.setText(fornecedorAntigo.getRepresentanteFornecedor());
        ftfTelefoneRepresentnteFornecedor.setValue(fornecedorAntigo.getTelefoneRepresentanteFornecedor());
        ftfCelularRepresentanteFornecedor.setValue(fornecedorAntigo.getCelularRepresentanteFornecedor()); 
        tfEmailRepresentanteFornecedor.setText(fornecedorAntigo.getEmailRepresentanteFornecedor());
        /*ABRINDO ABA DE CADASTRO - ONDE OS DADOS SERAM ATUALZADOS */
                tpnAbasFornecedor.setSelectedIndex(0);//Mudando para a segunda aba(aba de cadastro)
                tpnAbasFuncionario.setTitleAt(0, "ATUALIZAR DADOS");
                tpnAbasFuncionario.setEnabledAt(1, false);
         }
    }//GEN-LAST:event_btAtualizarFornecedorActionPerformed

    private void btSalvarAtualizarFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarAtualizarFornecedorActionPerformed
        // TODO add your handling code here:
        if(preenchimentoCorretoFornecedor())
        {
            fornecedorNovo = retornarDadosPreenchidosFornecedor();
             // Confirmando se o usuário REALMENTE deseja alterar
        // Confirmando se o usuário REALMENTE deseja alterar
            int resp = JOptionPane.showConfirmDialog(rootPane, 
                                                                "<HTML>"
                                                                + "<TABLE border='1'> "
                                                                        + "<TR> <TD> <b>DADOS ANTIGOS </b></TD> <TD><b> DADOS NOVOS </TD> </b></TR>"
                                                                        + "<TR> <TD> NOME FANTASIA: "+fornecedorAntigo.getNomeFantasiaFornecedor()+" </TD> <TD> NOME FANTASIA: "+fornecedorNovo.getNomeFantasiaFornecedor()+" </TD> </TR>"
                                                                        + "<TR> <TD> RAZÃO SOCIAL:"+fornecedorAntigo.getRazaoSocialFornecedor()+"</TD> <TD> RAZÃO SOCIAL: "+fornecedorNovo.getRazaoSocialFornecedor()+" </TD> </TR>"
                                                                        +"<TR> <TD>  CNPJ:"+fornecedorAntigo.getCnpjFornecedor()+"</TD> <TD> CNPJ:"+fornecedorNovo.getCnpjFornecedor()+"</TD> </TR>"
                                                                        +"<TR> <TD>  TELEFONE:"+fornecedorAntigo.getTelefoneFornecedor()+"</TD> <TD> TELEFONE:"+fornecedorNovo.getTelefoneFornecedor()+"</TD> </TR>"
                                                                        +"<TR> <TD>  CEP:"+fornecedorAntigo.getCepFornecedor()+"</TD> <TD> CEP:"+fornecedorNovo.getCepFornecedor()+"</TD> </TR>"
                                                                        +"<TR> <TD>  LOGRADOURO: "+fornecedorAntigo.getRuaAvnFornecedor()+"</TD> <TD> LOGRADOURO"+fornecedorNovo.getRuaAvnFornecedor()+"</TD> </TR>"
                                                                        +"<TR> <TD>  NÚMERO:"+fornecedorAntigo.getNumeroFornecedor()+"</TD> <TD> NÚMERO:"+fornecedorNovo.getNumeroFornecedor()+"</TD> </TR>"
                                                                        +"<TR> <TD>  BAIRRO:"+fornecedorAntigo.getBairroFornecedor()+"</TD> <TD> BAIRRO:"+fornecedorNovo.getBairroFornecedor()+"</TD> </TR>"
                                                                        +"<TR> <TD>  CIDADE:"+fornecedorAntigo.getCidadeFornecedor()+"</TD> <TD> CIDADE:"+fornecedorNovo.getCidadeFornecedor()+"</TD> </TR> "
                                                                        +"<TR> <TD>  ESTADO:"+fornecedorAntigo.getEstadoFornecedor()+"</TD> <TD> ESTADO:"+fornecedorNovo.getEstadoFornecedor()+"</TD> </TR> "
                                                                        +"<TR> <TD>  NOME/REPRESENTANTE:"+fornecedorAntigo.getRepresentanteFornecedor()+"</TD> <TD> NOME/REPRESENTANTE:"+fornecedorNovo.getRepresentanteFornecedor()+"</TD> </TR>"
                                                                        +"<TR> <TD>  TELEFONE/COMERCIAL:"+fornecedorAntigo.getTelefoneRepresentanteFornecedor()+"</TD> <TD> TELEFONE/COMERCIAL:"+fornecedorNovo.getTelefoneRepresentanteFornecedor()+"</TD> </TR>"
                                                                        +"<TR> <TD>  CELULAR:"+fornecedorAntigo.getCelularRepresentanteFornecedor()+"</TD> <TD> CELULAR: "+fornecedorNovo.getCelularRepresentanteFornecedor()+"</TD> </TR>"
                                                                        + "<TR> <TD> EMAIL: "+fornecedorAntigo.getEmailRepresentanteFornecedor()+" </TD> <TD> EMAIL: "+fornecedorNovo.getEmailRepresentanteFornecedor()+" </TD> </TR>"
                                                                        + "</TABLE>"
                                                                        + "</HTML>", 
                                                    "ALTERAÇÃO DE DADOS EMINENTE",     1);
             // Se o usuário pressionar SIM, atualizará os dados
        if( resp == 0)
        {
                conexaoTabelaFornecedor.atualizarFornecedor(retornarDadosPreenchidosFornecedor(), fornecedorAntigo);
                
                // Limpando os campos de cadastro
                limparCamposFornecedor();
                
                modeloTabelaFornecedor.inserirlistaFornecedores(conexaoTabelaFornecedor.selecionarTodosFornecedores()); 
                tbListaFornecedores.updateUI();
                tpnAbasFornecedor.setSelectedIndex(0);
                tpnAbasFornecedor.setTitleAt(0, "CADASTRAR FORNECEDOR");
                tpnAbasFornecedor.setEnabledAt(0, true); 
                tpnAbasFornecedor.setEnabledAt(1, true); 
               
                btSalvarAtualizarFornecedor.setVisible(false);
                btCancelarAtualizarFornecedor.setVisible(false);  
                btSalvarFornecedor.setVisible(true);
                btLimparCamposFornecedor.setVisible(true);
                
        }else
            if(resp==1)// se precionar NÃO, manter a tela  preenchida
            {
                        //tpnAbasFornecedor.setTitleAt(0, "ATUALIZAR");
                        tpnAbasFornecedor.setEnabledAt(1, false); 
                        tpnAbasFornecedor.setEnabledAt(0, true); 
                        btAtualizarFornecedor.setVisible(true);
                        btCancelarAtualizarFornecedor.setVisible(true);
                        btSalvarFornecedor.setVisible(false);
                        btLimparCamposFornecedor.setVisible(false); 
            } else
              {   
            if(resp==2)// se precionar CANCELAR, limpar campos e voltar para aba original
                {  
                // Voltar para a aba original
                tpnAbasFornecedor.setTitleAt(0, "GERÊNCIAR FORNECEDOR");
                tpnAbasFornecedor.setEnabledAt(0, true); 
                tpnAbasFornecedor.setEnabledAt(1, true); 
                tpnAbasFornecedor.setTitleAt(1,"CADASTRAR FORNECEDOR");
                btSalvarAtualizarFornecedor.setVisible(false);
                btSalvarFornecedor.setVisible(true);
                btCancelarAtualizarFornecedor.setVisible(false);
                btLimparCamposFornecedor.setVisible(true);
                              
                // Limpando os campos de cadastro
                limparCamposFornecedor();
        
            tpnAbasFornecedor.setSelectedIndex(0);
                }
            }   
        }
          
        
    }//GEN-LAST:event_btSalvarAtualizarFornecedorActionPerformed

    private void btAtualizarFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAtualizarFuncionarioActionPerformed
        // TODO add your handling code here:
        if(tbListaFuncionarios.getSelectedRow()!=-1) // SE o usuário selecionou uma linha da tabela...
        {
             tpnAbasFuncionario.setTitleAt(0, "ATUALIZAR");
             tpnAbasFuncionario.setEnabledAt(1, false);
             btSalvarAtualizarFuncionario.setVisible(true); // Tornando visível o botão ATUALIZAR
             btCancelarAtualizarFuncionario.setVisible(true);
             btSalvarFuncionario.setVisible(false);// Tornando INVISIVEL o botão SalvarFornecedor
             btLimparCamposFuncionario.setVisible(false);// TORNA VISIVEL O BOTÃO CANCELAR
             

             funcionarioAntigo= modeloTabelaFuncionario.retornalistaFuncioarios().get(tbListaFuncionarios.getSelectedRow());

             tfNomeCompletoFuncionario.setText(funcionarioAntigo.getNomeFuncionario());
             ftfCpfFuncionario.setText(funcionarioAntigo.getCpfFuncionario());
             ftfTelefoneFuncionario.setValue(funcionarioAntigo.getTelefoneFuncionario());
             ftfCelularFuncionario.setValue(funcionarioAntigo.getCelularFuncionario());  
             ftfPisFuncionario.setValue(funcionarioAntigo.getPisFuncionario());
             ftfCepFuncionario.setValue(funcionarioAntigo.getCepFuncionario());
            
             tfLogradouroFuncionario.setText(funcionarioAntigo.getRuaAvnFuncionario());
             tfNumeroFuncionario.setText(funcionarioAntigo.getNumeroFuncionario());
             tfBairroFuncionario.setText(funcionarioAntigo.getBairroFuncionario());
             tfCidadeFuncionario.setText(funcionarioAntigo.getCidadeFuncionario()); 
             
             cbEstadoFuncionario.setSelectedItem(funcionarioAntigo.getEstadoFuncionario());
             cbSituacaoFuncionario.setSelectedItem(funcionarioAntigo.getSituacaoFuncionario());
             cbCargoFuncionario.setSelectedItem(funcionarioAntigo.getCargoFuncionario());
             cbNivelPermissaoFuncionario.setSelectedItem(funcionarioAntigo.getPermissaoFuncionario());
             
            tfNomeUsuarioFuncionario.setText(funcionarioAntigo.getNomeUsuario());
             tfSenhaFuncionario.setText(funcionarioAntigo.getSenhaFuncionario());
             
             /* MÉTODO PARA PEGAR A DATA DO BANCO*/
             Calendar dataNascimentoFuncionario= funcionarioAntigo.getDataNascimentoFuncionario();
             ftfDataNascimentoFuncionario.setValue(formatador.format(dataNascimentoFuncionario.get(Calendar.DAY_OF_MONTH))+"/"+formatador.format(dataNascimentoFuncionario.get(Calendar.MONTH))+"/"+dataNascimentoFuncionario.get(Calendar.YEAR));
            
             Calendar dataAdimissaoFuncionario= funcionarioAntigo.getDataAdamissaoFuncionario();
             ftfDataAdmissaoFuncionario.setValue(formatador.format(dataAdimissaoFuncionario.get(Calendar.DAY_OF_MONTH))+"/"+formatador.format(dataAdimissaoFuncionario.get(Calendar.MONTH))+"/"+dataAdimissaoFuncionario.get(Calendar.YEAR));
            
             Calendar dataDemissaoFuncionario= funcionarioAntigo.getDataDemissaoFuncionario();
             ftfDataDemissaoFuncionario.setValue(formatador.format(dataDemissaoFuncionario.get(Calendar.DAY_OF_MONTH))+"/"+formatador.format(dataDemissaoFuncionario.get(Calendar.MONTH))+"/"+dataDemissaoFuncionario.get(Calendar.YEAR));
            
             Calendar dataAfastamentoFuncionario= funcionarioAntigo.getDataDemissaoFuncionario();
             ftfDataDemissaoFuncionario.setValue(formatador.format(dataAfastamentoFuncionario.get(Calendar.DAY_OF_MONTH))+"/"+formatador.format(dataAfastamentoFuncionario.get(Calendar.MONTH))+"/"+dataAfastamentoFuncionario.get(Calendar.YEAR));
            
             Calendar dataRetornoFuncionario= funcionarioAntigo.getDataRetornoFuncionario();
             ftfDataRetornoFuncionario.setValue(formatador.format(dataRetornoFuncionario.get(Calendar.DAY_OF_MONTH))+"/"+formatador.format(dataRetornoFuncionario.get(Calendar.MONTH))+"/"+dataRetornoFuncionario.get(Calendar.YEAR));
            /* MÉTODO ACABADO*/
             
             /*ABRINDO ABA DE CADASTRO - ONDE OS DADOS SERAM ATUALZADOS */
             tpnAbasFuncionario.setSelectedIndex(0);//Mudando para a segunda aba(aba de cadastro)
             tpnAbasFuncionario.setTitleAt(0, "ATUALIZAR DADOS");
             tpnAbasFuncionario.setEnabledAt(1, false);
         }
    }//GEN-LAST:event_btAtualizarFuncionarioActionPerformed

    private void btAtualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAtualizarClienteActionPerformed
        // TODO add your handling code here:
        if(tbListaClientes.getSelectedRow()!=-1) // SE o usuário selecionou uma linha da tabela...
        {
             tpnAbasCliente.setTitleAt(0, "ATUALIZAR");
             tpnAbasCliente.setEnabledAt(1, false);
             btSalvarAtualizarCliente.setVisible(true); // Tornando visível o botão ATUALIZAR
             btSalvarCliente.setVisible(false);// Tornando INVISIVEL o botão SalvarFornecedor
             btCancelarAtualizarCliente.setVisible(true);// TORNA VISIVEL O BOTÃO CANCELAR
             btLimparCamposCliente.setVisible(false);// Torna o bt invisivel

             clienteAntigo= modeloTabelaCliente.retornalistaCliente().get(tbListaClientes.getSelectedRow());

             tfNomeCompletoCliente.setText(clienteAntigo.getNomeCliente());
             ftfCpfCliente.setText(clienteAntigo.getCpfCliente());
            // ftfDataNascimentoCliente.setValue(clienteAntigo.getDataNascimentoCliente());
             ftfTelefoneCliente.setValue(clienteAntigo.getTelefoneCliente());
             ftfCelularCliente.setValue(clienteAntigo.getCelularCliente());  
             ftfCepCliente.setValue(clienteAntigo.getCepCliente());
             tfLogradouroCliente.setText(clienteAntigo.getRuaAvnCliente());
             tfNumeroCliente.setText(clienteAntigo.getNumeroCliente());
             tfBairroCliente.setText(clienteAntigo.getBairroCliente());
             tfCidadeCliente.setText(clienteAntigo.getCidadeCliente()); 
             tfEmailCliente.setText(clienteAntigo.getEmailCliente());
             
             /* MÉTODO PARA PEGAR A DATA DO BANCO*/
             Calendar dataNascimentoCliente= clienteAntigo.getDataNascimentoCliente();
             ftfDataNascimentoCliente.setValue(formatador.format(dataNascimentoCliente.get(Calendar.DAY_OF_MONTH))+"/"+formatador.format(dataNascimentoCliente.get(Calendar.MONTH))+"/"+dataNascimentoCliente.get(Calendar.YEAR));
            
             /*ABRINDO ABA DE CADASTRO - ONDE OS DADOS SERAM ATUALZADOS */
             tpnAbasCliente.setSelectedIndex(0);//Mudando para a segunda aba(aba de cadastro)
             tpnAbasCliente.setTitleAt(0, "ATUALIZAR DADOS");
             tpnAbasCliente.setEnabledAt(1, false);
         }
    }//GEN-LAST:event_btAtualizarClienteActionPerformed

    private void tfBuscaFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfBuscaFornecedorActionPerformed
        // TODO add your handling code here:
        conexaoTabelaFornecedor.selecionarTodosFornecedores();
    }//GEN-LAST:event_tfBuscaFornecedorActionPerformed

    private void tfBuscaFornecedorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfBuscaFornecedorKeyReleased
        // TODO add your handling code here:
        ArrayList<Fornecedor> listaTemporariaFornecedor = new ArrayList();
        ArrayList<Fornecedor> listaOriginal = conexaoTabelaFornecedor.selecionarTodosFornecedores();
        
        for(int i=0; i<listaOriginal.size(); i++)
        {
            if(listaOriginal.get(i).getNomeFantasiaFornecedor().toLowerCase().contains(tfBuscaFornecedor.getText().toLowerCase())||listaOriginal.get(i).getRepresentanteFornecedor().toLowerCase().contains(tfBuscaFornecedor.getText().toLowerCase()))
            {
                listaTemporariaFornecedor.add(listaOriginal.get(i));
            }
            
        }
        modeloTabelaFornecedor.inserirlistaFornecedores(listaTemporariaFornecedor); 
        tbListaFornecedores.updateUI();
    }//GEN-LAST:event_tfBuscaFornecedorKeyReleased

    private void tfBuscaFuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfBuscaFuncionarioActionPerformed
        conexaoTabelaFuncionario.selecionarTodosFuncionarios();
    }//GEN-LAST:event_tfBuscaFuncionarioActionPerformed

    private void tfBuscaFuncionarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfBuscaFuncionarioKeyReleased
        // esse metodo é para buscar o funcionario no array quando o usuario estiver fazendo uma pesquisa
        ArrayList<Funcionario> listaTemporariaFuncionario = new ArrayList();
        ArrayList<Funcionario> listaOriginal = conexaoTabelaFuncionario.selecionarTodosFuncionarios();
        
        for(int i=0; i<listaOriginal.size(); i++)
        {
            if(listaOriginal.get(i).getNomeFuncionario().toLowerCase().contains(tfBuscaFuncionario.getText().toLowerCase()))
            {
                listaTemporariaFuncionario.add(listaOriginal.get(i));
            }
        }
        modeloTabelaFuncionario.inserirlistaFuncionarios(listaTemporariaFuncionario); 
        tbListaFuncionarios.updateUI();
    }//GEN-LAST:event_tfBuscaFuncionarioKeyReleased

    private void tfBuscaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfBuscaClienteActionPerformed
        conexaoTabelaCliente.selecionarTodosClientes();
    }//GEN-LAST:event_tfBuscaClienteActionPerformed

    private void tfBuscaClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfBuscaClienteKeyReleased
         ArrayList<Cliente> listaTemporariaCliente = new ArrayList();
         ArrayList<Cliente> listaOriginal = conexaoTabelaCliente.selecionarTodosClientes();
        
         for(int i=0; i<listaOriginal.size(); i++)
         {
             if(listaOriginal.get(i).getNomeCliente().toLowerCase().contains(tfBuscaCliente.getText().toLowerCase()))
             {
                 listaTemporariaCliente.add(listaOriginal.get(i));
             }
            
         }
        modeloTabelaCliente.inserirlistaCliente(listaTemporariaCliente); 
        tbListaClientes.updateUI();
    }//GEN-LAST:event_tfBuscaClienteKeyReleased

    private void tfBuscaProdutoEstoqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfBuscaProdutoEstoqueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfBuscaProdutoEstoqueActionPerformed

    private void tfBuscaProdutoEstoqueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfBuscaProdutoEstoqueKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tfBuscaProdutoEstoqueKeyReleased

    private void ftfCpfFuncionarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ftfCpfFuncionarioFocusLost
        // TODO add your handling code here:
        listaFuncionarios = conexaoTabelaFuncionario.selecionarTodosFuncionarios();
        
        for(int i=0; i<listaFuncionarios.size();i++)
        {
            if(ftfCpfFuncionario.getText().equals(listaFuncionarios.get(i).getCpfFuncionario()))
            {
                ftfCpfCliente.requestFocus();
                JOptionPane.showMessageDialog(ifTelaCliente, "CPF EXISTENTE!");
            }
        }
    }//GEN-LAST:event_ftfCpfFuncionarioFocusLost

    private void smnPizzasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_smnPizzasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_smnPizzasActionPerformed

    private void btCancelarAtualizarFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarAtualizarFornecedorActionPerformed
        // TODO add your handling code here:
        tpnAbasCliente.setTitleAt(1, "GERÊNCIAR FORNECEDOR");
                tpnAbasFornecedor.setEnabledAt(0, true); 
                tpnAbasFornecedor.setEnabledAt(1, true); 
                tpnAbasFornecedor.setTitleAt(0,"CADASTRAR FORNECEDOR");
                btSalvarAtualizarFornecedor.setVisible(false);
                btCancelarAtualizarFornecedor.setVisible(false);
                btLimparCamposFornecedor.setVisible(true);
                btSalvarFornecedor.setVisible(true);
               
                
                // Limpando os campos de cadastro
                limparCamposFornecedor();
        
            tpnAbasFornecedor.setSelectedIndex(0);
        
    }//GEN-LAST:event_btCancelarAtualizarFornecedorActionPerformed

    private void btLimparCamposFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLimparCamposFornecedorActionPerformed
      // limpar campoS
    }//GEN-LAST:event_btLimparCamposFornecedorActionPerformed

    private void btSalvarAtualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarAtualizarClienteActionPerformed
        // TODO add your handling code here:
        if(preenchimentoCorretoCliente())
        {
            clienteNovo = retornarDadosPreenchidosCliente();
             // Confirmando se o usuário REALMENTE deseja alterar
        // Confirmando se o usuário REALMENTE deseja alterar
            int resp = JOptionPane.showConfirmDialog(rootPane, 
                                                                "<HTML>"
                                                                + "<TABLE border='1'> "
                                                                        + "<TR> <TD> <b>DADOS ANTIGOS </b></TD> <TD><b> DADOS NOVOS </TD> </b></TR>"
                                                                        + "<TR> <TD> NOME: "+clienteAntigo.getNomeCliente()+" </TD> <TD> NOME FANTASIA: "+clienteNovo.getNomeCliente()+" </TD> </TR>"
                                                                        +"<TR> <TD>  CPF:"+clienteAntigo.getCpfCliente()+"</TD> <TD> CNPJ:"+clienteNovo.getCpfCliente()+"</TD> </TR>"
                                                                        +"<TR> <TD>  TELEFONE:"+clienteAntigo.getTelefoneCliente()+"</TD> <TD> TELEFONE:"+clienteNovo.getTelefoneCliente()+"</TD> </TR>"
                                                                        +"<TR> <TD>  CELULAR:"+clienteAntigo.getCelularCliente()+"</TD> <TD> CELULAR: "+clienteNovo.getCelularCliente()+"</TD> </TR>"
                                                                        +"<TR> <TD>  CEP:"+clienteAntigo.getCepCliente()+"</TD> <TD> CEP:"+clienteNovo.getCepCliente()+"</TD> </TR>"
                                                                        +"<TR> <TD>  LOGRADOURO: "+clienteAntigo.getRuaAvnCliente()+"</TD> <TD> LOGRADOURO"+clienteNovo.getRuaAvnCliente()+"</TD> </TR>"
                                                                        +"<TR> <TD>  NÚMERO:"+clienteAntigo.getNumeroCliente()+"</TD> <TD> NÚMERO:"+clienteNovo.getNumeroCliente()+"</TD> </TR>"
                                                                        +"<TR> <TD>  BAIRRO:"+clienteAntigo.getBairroCliente()+"</TD> <TD> BAIRRO:"+clienteNovo.getBairroCliente()+"</TD> </TR>"
                                                                        +"<TR> <TD>  CIDADE:"+clienteAntigo.getCidadeCliente()+"</TD> <TD> CIDADE:"+clienteNovo.getCidadeCliente()+"</TD> </TR> "
                                                                        + "<TR> <TD> EMAIL: "+clienteAntigo.getEmailCliente()+" </TD> <TD> EMAIL: "+clienteNovo.getEmailCliente()+" </TD> </TR>"
                                                                        + "<TR> <TD> DATA DE NASCIMENTO: "+formatador.format(clienteAntigo.getDataNascimentoCliente().get(Calendar.DAY_OF_MONTH))+"/"
                                                                        +formatador.format(clienteAntigo.getDataNascimentoCliente().get(Calendar.MONTH))+"/"+
                                                                        clienteAntigo.getDataNascimentoCliente().get(Calendar.YEAR)+
                                                                        " </TD> <TD>  DATA DE NASCIMENTO:: "+formatador.format(clienteNovo.getDataNascimentoCliente().get(Calendar.DAY_OF_MONTH))+"/"+
                                                                         formatador.format(clienteNovo.getDataNascimentoCliente().get(Calendar.MONTH))+"/"
                                                                        + clienteNovo.getDataNascimentoCliente().get(Calendar.YEAR)+
                                                                        " </TD> </TR> "
                                                                        + "</TABLE>"
                                                                        + "</HTML>", 
                                                    "ALTERAÇÃO DE DADOS EMINENTE",     1);
            // Se o usuário pressionar SIM, atualizará os dados
        if( resp == 0)
        {
                conexaoTabelaCliente.atualizarCliente(retornarDadosPreenchidosCliente(), clienteAntigo);
                
                // Limpando os campos de cadastro
                limparCamposCliente();
                
                modeloTabelaCliente.inserirlistaCliente(conexaoTabelaCliente.selecionarTodosClientes()); 
                tbListaClientes.updateUI();
                tpnAbasCliente.setSelectedIndex(0);
                tpnAbasCliente.setTitleAt(0, "CADASTRAR CLIENTE");
                tpnAbasCliente.setEnabledAt(0, true); 
                tpnAbasCliente.setEnabledAt(1, true); 
               
                btSalvarAtualizarCliente.setVisible(false);
                btCancelarAtualizarCliente.setVisible(false);  
                btSalvarCliente.setVisible(true);
                btLimparCamposCliente.setVisible(true);
                
        }else
            if(resp==1)// se precionar NÃO, manter a tela  preenchida
                {
                            //tpnAbasFornecedor.setTitleAt(0, "ATUALIZAR");
                            tpnAbasCliente.setEnabledAt(1, false); 
                            tpnAbasCliente.setEnabledAt(0, true); 
                            btAtualizarCliente.setVisible(true);
                            btCancelarAtualizarCliente.setVisible(true);
                            btSalvarCliente.setVisible(false);
                            btLimparCamposCliente.setVisible(false); 
                } else
                  {
                    if(resp==2)// se precionar CANCELAR, limpar campos e voltar para aba original
                    {  
                    // Voltar para a aba original
                    tpnAbasCliente.setTitleAt(0, "GERÊNCIAR FORNECEDOR");
                    tpnAbasCliente.setEnabledAt(0, true); 
                    tpnAbasCliente.setEnabledAt(1, true); 
                    tpnAbasCliente.setTitleAt(1,"CADASTRAR FORNECEDOR");
                    btSalvarAtualizarCliente.setVisible(false);
                    btSalvarCliente.setVisible(true);
                    btCancelarAtualizarCliente.setVisible(false);
                    btLimparCamposCliente.setVisible(true);

                    // Limpando os campos de cadastro
                    limparCamposCliente();

                tpnAbasCliente.setSelectedIndex(0);
                    }
                }   
        }
    }//GEN-LAST:event_btSalvarAtualizarClienteActionPerformed

    private void btCancelarAtualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarAtualizarClienteActionPerformed
        // TODO add your handling code here:
            tpnAbasCliente.setTitleAt(1, "GERÊNCIAR CLIENTE");
            tpnAbasCliente.setEnabledAt(0, true); 
            tpnAbasCliente.setEnabledAt(1, true); 
            tpnAbasCliente.setTitleAt(0,"CADASTRAR CLIENTE");
            btSalvarAtualizarCliente.setVisible(false);
            btCancelarAtualizarCliente.setVisible(false);
            btLimparCamposCliente.setVisible(true);
            btSalvarCliente.setVisible(true);

            // Limpando os campos de cadastro
            limparCamposCliente();

            tpnAbasCliente.setSelectedIndex(0);
        
    }//GEN-LAST:event_btCancelarAtualizarClienteActionPerformed

    /***
     * METODO PARA FAZER LOGOOF
     */
    public void fazerLogoff()
    {
        /* FECHANDO JANELAS INTERNAS ABERTAS */
        ifTelaCliente.hide();
        ifTelaFornecedor.hide();
        ifTelaFuncionario.hide();
        ifTelaPdv.hide();
        ifTelaProdutoEstoque.hide();
        
        /* MOSTRANDO TELA DE LOGIN*/
        TelaLogin login=new TelaLogin(this, true);
        login.setVisible(true);
        this.setVisible(true);
    }
    
    /***
     * VERIFICANDO SE TODOS OS DADOS - OBRIGATORIOS - DO CLIENTE FORAM
     * PREENCHIDOS CORRETAMENTE.
     * @return 
     */
    private boolean preenchimentoCorretoCliente()
    {
        if(tfNomeCompletoCliente.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(ifTelaCliente, "Preencha o Nome do Cliente", "Dados Incompletos", WIDTH);
            tfNomeCompletoCliente.requestFocus();
            return false;
        }else
            if(ftfCpfCliente.getValue()==null)
            {
                JOptionPane.showMessageDialog(ifTelaCliente, "Preencha o CPF do Cliente", "Dados Incompletos", WIDTH);
                ftfCpfCliente.requestFocus();
                return false;
            }else
                if(ftfCelularCliente.getValue()==null)
                {
                    JOptionPane.showMessageDialog(ifTelaCliente, "Preencha o Número de\nCelular do Cliente", "Dados Incompletos", WIDTH);
                    ftfCelularCliente.requestFocus();
                    return false;
                }else
                    if(ftfCepCliente.getValue()==null)
                    {
                        JOptionPane.showMessageDialog(ifTelaCliente, "Preencha o CEP do Cliente", "Dados Incompletos", WIDTH);
                        ftfCepCliente.requestFocus();
                        return false;
                    }else
                        if(tfLogradouroCliente.getText().isEmpty())
                        {
                            JOptionPane.showMessageDialog(ifTelaCliente, "Preencha o Nome da\nRua ou Avenida do Cliente", "Dados Incompletos", WIDTH);
                            tfLogradouroCliente.requestFocus();
                            return false;
                        }else
                            if(tfNumeroCliente.getText().isEmpty())
                            {
                                JOptionPane.showMessageDialog(ifTelaCliente, "Preencha o Número da\nCasa do Cliente", "Dados Incompletos", WIDTH);
                                tfNumeroCliente.requestFocus();
                                return false;
                            }else
                                if(tfBairroCliente.getText().isEmpty())
                                {
                                    JOptionPane.showMessageDialog(ifTelaCliente, "Preencha o Nome do\nBairro do Cliente", "Dados Incompletos", WIDTH);
                                    tfBairroCliente.requestFocus();
                                    return false;
                                }else
                                    if(tfCidadeCliente.getText().isEmpty())
                                    {
                                        JOptionPane.showMessageDialog(ifTelaCliente, "Preencha o Nome da\nCidade do Cliente", "Dados Incompletos", WIDTH);
                                        tfCidadeCliente.requestFocus();
                                        return false;
                                    }else
                                        if(tfNomeCompletoCliente.getText().isEmpty())
                                        {
                                            JOptionPane.showMessageDialog(ifTelaCliente, "Preencha o Ponto de\nReferencia da Residencia\nDeste Cliente", "Dados Incompletos", WIDTH);
                                            tfNomeCompletoCliente.requestFocus();
                                            return false;
                                        }else
                                        {
                                             return true;
                                        }
    }
    
    public Cliente retornarDadosPreenchidosCliente()
    {
        Cliente novoCliente=new Cliente();
        // DADOS DO CLIENTE
        novoCliente.setNomeCliente(tfNomeCompletoCliente.getText());
        novoCliente.setCpfCliente(ftfCpfCliente.getText());
        
        /* CONVERTENDO DATA */
        Calendar cal=Calendar.getInstance();
        cal.setTime((Date) dataNascimentoCliente.getValue());
        novoCliente.setDataNascimentoCliente(cal);
        
        /* VERIFICAR SE O CAMPO DE TELEFONE  NÃO FOI PREENCHIDO */
        if(ftfTelefoneCliente.getValue()==null)
        {
            novoCliente.setTelefoneCliente(null); // O BANCO RECEBERÁ VALOR NULO [null]
        }else
            if(ftfTelefoneCliente.getValue()!=null)
            {
            novoCliente.setTelefoneCliente((String) ftfTelefoneCliente.getValue()); // SE FOR TIVER SIDO PREENCHIDO - ARMAZENAR NO BANCO DE DADOS
            }
        
        /* VERIFICAR SE O CAMPO CELULAR  NÃO FOI PREENCHIDO */
        if(ftfCelularCliente.getValue()==null)
        {
            novoCliente.setCelularCliente(null); // O BANCO RECEBERÁ VALOR NULO [null]
        }else
            if(ftfCelularCliente.getValue()!=null)
            {
            novoCliente.setCelularCliente((String) ftfCelularCliente.getValue()); // SE FOR TIVER SIDO PREENCHIDO - ARMAZENAR NO BANCO DE DADOS
            }
        
        /* PROSSEGUNINDO COM O CADASTRO DO CLIENTE */
        novoCliente.setCepCliente((String) ftfCepCliente.getValue());
        novoCliente.setRuaAvnCliente(tfLogradouroCliente.getText());
        novoCliente.setNumeroCliente(tfNumeroCliente.getText());
        novoCliente.setBairroCliente(tfBairroCliente.getText());
        novoCliente.setCidadeCliente(tfCidadeCliente.getText());
        novoCliente.setComplementoCliente(tfComplementoCliente.getText());
        
        /* VERIFICAR SE O CAMPO E-MAIL NÃO FOI PREENCHIDO */
        if(tfEmailCliente.getText().isEmpty())
        {
            novoCliente.setEmailCliente(null); // O BANCO RECEBERÁ VALOR NULO [null]
        }else
            {
            novoCliente.setEmailCliente(tfEmailCliente.getText()); // SE FOR TIVER SIDO PREENCHIDO - ARMAZENAR NO BANCO DE DADOS
            }
        
         return novoCliente;
        
    }
    
    /***
     * ESTE METODO SERÁ RESPONSAVEL POR BUSCAR NO SISTEMA DE BUSCA CEP DOS
     * CORREIOS O LOGRADOURO (RUA/AVN/...), CIDADA (MUNICIPIO), ESTADO. E 
     * INSERINDO NOS SEUS RESPECIVOS CAMPOS.
     */
    public void buscaCep(String tipoBusca)
    {
        switch(tipoBusca)
        {
            case "Fornecedor": 
                 query.put("cepEntrada", ftfCepFornecedor.getText().toString()); //RECEBE O CEP INFORMADO NO CADASTRO DO FORNECEDOR
                
                 try {
           //FAZ A BUSCA PELO SITE DO CORREIO 
           query.put("tipoCep", "");  
           query.put("cepTemp", "");  
           query.put("metodo", "buscarCep");  

           org.jsoup.nodes.Document doc = Jsoup.connect("http://m.correios.com.br/movel/buscaCepConfirma.do") 
                                            .data(query)  
                                            .post();  

                        org.jsoup.select.Elements elemetos = doc.select(".respostadestaque");
                        
                  
                        
                        if (elemetos.size() == 4) 
                        {  
                            //RETORNA O ENDEREÇO ENCONTRADO                            
                            tfLogradouroFornecedor.setText(elemetos.get(0).text().toUpperCase()); 
                            tfLogradouroFornecedor.setCaretPosition(0);//RETORNA O CURSOR PARA A PRIMEIRA LETRA 
                            tfBairroFornecedor.setText(elemetos.get(1).text().toUpperCase());
                            int x = elemetos.get(2).text().lastIndexOf("/");
                            tfBairroFornecedor.setCaretPosition(0);
                            tfCidadeFornecedor.setText(elemetos.get(2).text().substring(0,x).toUpperCase());
                            tfCidadeFornecedor.setCaretPosition(0);
                            cbEstadoFornecedor.setSelectedItem(elemetos.get(2).text().substring(x+1).toString());
                        }else if (elemetos.size() == 2) 
                              {
                                  int x = elemetos.get(0).text().lastIndexOf("/");
                                  tfCidadeFornecedor.setText(elemetos.get(0).text().substring(0,x).toUpperCase());
                                  tfCidadeFornecedor.setCaretPosition(0);
                                  cbEstadoFornecedor.setSelectedItem(elemetos.get(0).text().substring(x+1).toString());
                              }
                    } catch (IOException ex) 
                      {
                        System.err.println(ex.getMessage());
                      } 
                 break;
                
            case "Cliente":
                 query.put("cepEntrada", ftfCepCliente.getText().toString()); //RECEBE O CEP INFORMADO NO CADASTRO DO CLIENTE
                 
                 try {
           //FAZ A BUSCA PELO SITE DO CORREIO 
           query.put("tipoCep", "");  
           query.put("cepTemp", "");  
           query.put("metodo", "buscarCep");  

           org.jsoup.nodes.Document doc = Jsoup.connect("http://m.correios.com.br/movel/buscaCepConfirma.do") 
                                            .data(query)  
                                            .post();  

                        org.jsoup.select.Elements elemetos = doc.select(".respostadestaque");
                        
                  
                        
                        if (elemetos.size() == 4) 
                        {  
                            //RETORNA O ENDEREÇO ENCONTRADO                            
                            tfLogradouroCliente.setText(elemetos.get(0).text().toUpperCase()); 
                            tfLogradouroCliente.setCaretPosition(0); 
                            tfBairroCliente.setText(elemetos.get(1).text().toUpperCase());
                            int x = elemetos.get(2).text().lastIndexOf("/");
                            tfBairroCliente.setCaretPosition(0);
                            tfCidadeCliente.setText(elemetos.get(2).text().substring(0,x).toUpperCase());
                            tfCidadeCliente.setCaretPosition(0);
                            //cbEstadoFornecedor.setSelectedItem(elemetos.get(2).text().substring(x+1).toString());
                        }else if (elemetos.size() == 2) 
                              {
                                  int x = elemetos.get(0).text().lastIndexOf("/");
                                  tfCidadeCliente.setText(elemetos.get(0).text().substring(0,x).toUpperCase());
                                  tfCidadeCliente.setCaretPosition(0);
                                //  cbEstadoFornecedor.setSelectedItem(elemetos.get(0).text().substring(x+1).toString());
                              }
                    } catch (IOException ex) 
                      {
                        System.err.println(ex.getMessage());
                      } 
                 break;
                
            case "Funcionario":
                 query.put("cepEntrada", ftfCepFuncionario.getText().toString()); //RECEBE O CEP INFORMADO NO CADASTRO DO FUNCIONARIO
                
                 try {
           //FAZ A BUSCA PELO SITE DO CORREIO 
           query.put("tipoCep", "");  
           query.put("cepTemp", "");  
           query.put("metodo", "buscarCep");  

           org.jsoup.nodes.Document doc = Jsoup.connect("http://m.correios.com.br/movel/buscaCepConfirma.do") 
                                            .data(query)  
                                            .post();  

                        org.jsoup.select.Elements elemetos = doc.select(".respostadestaque");
                        
                  
                        
                        if (elemetos.size() == 4) 
                        {  
                            //RETORNA O ENDEREÇO ENCONTRADO                            
                            tfLogradouroFuncionario.setText(elemetos.get(0).text().toUpperCase()); 
                            tfLogradouroFuncionario.setCaretPosition(0); 
                            tfBairroFuncionario.setText(elemetos.get(1).text().toUpperCase());
                            int x = elemetos.get(2).text().lastIndexOf("/");
                            tfBairroFuncionario.setCaretPosition(0);
                            tfCidadeFuncionario.setText(elemetos.get(2).text().substring(0,x).toUpperCase());
                            tfCidadeFuncionario.setCaretPosition(0);
                            cbEstadoFuncionario.setSelectedItem(elemetos.get(2).text().substring(x+1).toString());
                        }else if (elemetos.size() == 2) 
                              {
                                  int x = elemetos.get(0).text().lastIndexOf("/");
                                  tfCidadeFuncionario.setText(elemetos.get(0).text().substring(0,x).toUpperCase());
                                  tfCidadeFuncionario.setCaretPosition(0);
                                  cbEstadoFuncionario.setSelectedItem(elemetos.get(0).text().substring(x+1).toString());
                              }
                    } catch (IOException ex) 
                      {
                        System.err.println(ex.getMessage());
                      } 
                 break;}
    }
    
    /***
     * VERIFICANDO SE OS NUMEROS INSERIDOS NOS CAMPOS DE CPF SÃO REPETIDOS - CASO
     * SEJAM - INFORMAR AO USUARIO QUE O CPF E INVALIDO.
     * @return 
     */
    public boolean validacaoCpf()
    {
        /* NUMEROS QUE NÃO PODERAM REPETIR-SE */
        String repete1="111.111.111-11",repete2="222.222.222-22",repete3="333.333.333-33";
        String repete4="444.444.444-44",repete5="555.555.555-55",repete6="666.666.666-66";
        String repete7="777.777.777-77",repete8="888.888.888-88",repete9="999.999.999-99";
        String repete0="000.000.000-00";
        /* VERIFIANDO O CAMPO CPF - CLIENTE */
        if((ftfCpfCliente.getValue()==repete1 || ftfCpfCliente.getText().equals(repete1)) || ftfCpfCliente.getValue()== repete2 || ftfCpfCliente.getValue()== repete3 || ftfCpfCliente.getValue()== repete4 || ftfCpfCliente.getValue()== repete5 || ftfCpfCliente.getValue()== repete6 || ftfCpfCliente.getValue()== repete7 || ftfCpfCliente.getValue()== repete8 || ftfCpfCliente.getValue()== repete9 || ftfCpfCliente.getValue()== repete0)
        {
            JOptionPane.showMessageDialog(rootPane, "CPF Invalido", "Dados Incompletos", WIDTH);
            ftfCpfCliente.requestFocus();
            return false;
        }else
        {
            return true;
        }
    }
    
    
    public void limparCamposCliente()
    {
        tfIdCliente.setText("[AUTO]");
        tfNomeCompletoCliente.setText("");
        ftfCpfCliente.setValue(null);
        ftfDataNascimentoCliente.setValue(null);
        ftfTelefoneCliente.setValue(null);
        ftfCelularCliente.setValue(null);
        ftfCepCliente.setValue(null);
        tfLogradouroCliente.setText("");
        tfNumeroCliente.setText("");
        tfBairroCliente.setText("");
        tfCidadeCliente.setText("");
        tfComplementoCliente.setText("");
        tfEmailCliente.setText("");
    }
    
    /***
     * VERIFICANDO SE TODOS OS CAMPOS OBRIGATORIOS DO FUNCIONARIO FORAM 
     * PREENCHIDOS CORRETAMENTE.
     * @return 
     */
    public boolean preenchimentoCorretoFuncionario()
    {
        if(tfNomeCompletoFuncionario.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha o Nome do Funcionario", "Dados Incompletos", WIDTH);
            tfNomeCompletoFuncionario.requestFocus();
            return false;
        }else
            if(ftfCpfFuncionario.getValue()==null)
            {
            JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha o CPF do Funcionario", "Dados Incompletos", WIDTH);
            ftfCpfFuncionario.requestFocus();
             return false;
            }else
                if(ftfPisFuncionario.getValue()==null)
                   {
                       JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha o PIS do Funcionario", "Dados Incompletos", WIDTH);
                       ftfPisFuncionario.requestFocus();
                        return false;
                   }else
                        if(tfLogradouroFuncionario.getText().isEmpty())
                        {
                            JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha a Rua/Av. do Funcionario", "Dados Incompletos", WIDTH);
                            tfLogradouroFuncionario.requestFocus(true);
                             return false;
                        }else
                            if(tfNumeroFuncionario.getText().isEmpty())
                            {
                                 JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha o Número do domiciclio\nDo Funcionario", "Dados Incompletos", WIDTH);
                                tfNumeroFuncionario.requestFocus(true);
                             return false;
                            }else
                                if(tfBairroFuncionario.getText().isEmpty())
                                {
                                   JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha o Bairro do Funcionario", "Dados Incompletos", WIDTH);
                                    tfBairroFuncionario.requestFocus();
                                     return false;
                                }else
                                    if(tfCidadeFuncionario.getText().isEmpty())
                                    {
                                        JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha a Cidade do Funcionario", "Dados Incompletos", WIDTH);
                                        tfCidadeFuncionario.requestFocus();
                                         return false;
                                    }else
                                        //VERIFICAR SE O FUNCIONARIO FOI DEMITIDO, CASO SEJA, SE A DATA FOI PREENCHIDA
                                        /*
                                       if(cbSituacaoFuncionario.getSelectedItem().toString().equalsIgnoreCase("demitido"))
                                       {
                                           if(ftfDataDemissaoFuncionario.getValue()==null)
                                           {
                                            JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha a Data de Demissão\nDo Funcionario", "Dados Incompletos", WIDTH);
                                            ftfDataDemissaoFuncionario.requestFocus();   
                                           }
                                            return false;
                                       }else
                                           //VERIFICAR SE O FUNCIONARIO FOI AFASTADO, CASO POSITIVO, VERIFICAR SE A DATA FOI PREENCHIDA
                                            if(cbSituacaoFuncionario.getSelectedItem().toString().equalsIgnoreCase("Afastado") && ftfDataAdmissaoFuncionario.getValue()==null)
                                            {
                                                JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha a Data de Afastamento\nDo Funcionario", "Dados Incompletos", WIDTH);
                                                ftfDataDemissaoFuncionario.requestFocus(); 
                                                 return false;
                                            }else
                                                //VERIFICAR SE O FUNCIONARIO ESTA DE FERIAS, CASO POSITIVO, VERIFICAR SE A DATA FOI PREENCHIDA
                                                if(cbSituacaoFuncionario.getSelectedItem().toString().equalsIgnoreCase("ferias") && ftfDataDemissaoFuncionario.getValue()==null)
                                                {
                                                    JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha a Data de Inicio Das\nFerias do Funcionario", "Dados Incompletos", WIDTH);
                                                    ftfDataDemissaoFuncionario.requestFocus(); 
                                                     return false;
                                                 }else
                                                    if(cbSituacaoFuncionario.getSelectedItem().toString().equalsIgnoreCase("ferias") && ftfDataRetornoFuncionario.getValue()==null)
                                                    {
                                                        JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha a Data de Retorno\nDo Funcionario", "Dados Incompletos", WIDTH);
                                                        ftfDataRetornoFuncionario.requestFocus(); 
                                                         return false;
                                                     }else
                                                        //VERIFICAR SE O FUNCIONARIO ESTA CONTRATADO , CASO POSITIVO, VERIFICAR SE A DATA FOI PREENCHIDA
                                                       if(cbSituacaoFuncionario.getSelectedItem().toString().equalsIgnoreCase("contratado")&&ftfDataAdmissaoFuncionario.getValue()==null)
                                                       {
                                                           JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha a Data de Contratração\nDo Funcionario", "Dados Incompletos", WIDTH);
                                                           ftfDataAdmissaoFuncionario.requestFocus(); 
                                                            return false;
                                                       }else
                                        */
                                                            if(tfNomeUsuarioFuncionario.getText().isEmpty())
                                                            { 
                                                                JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha o Nome de Usuario\nPara o Funcionario", "Dados Incompletos", WIDTH);
                                                                tfNomeUsuarioFuncionario.requestFocus(); 
                                                                return false;
                                                            }else
                                                                if(tfSenhaFuncionario.getText().isEmpty())
                                                                {
                                                                    JOptionPane.showMessageDialog(ifTelaFuncionario, "Preencha a Senha de Usuario\nPara o Funcionario", "Dados Incompletos", WIDTH);
                                                                    tfNomeUsuarioFuncionario.requestFocus(); 
                                                                    return false;
                                                                }
                                                                    {
                                                                         return true;
                                                                    }
    }
    
    /***
     * ENVIA OS DADOS COLETADOS NA TELA DE CADASTRO DO FUNCIONARIO PARA
     * O BANCO DE DADOS (QUICKER.FUNCIONARIOS).
     * @return 
     */
    public Funcionario retornarDadosPreenchidosFuncionario()
    {
        Funcionario novoFuncionario = new Funcionario();

            novoFuncionario.setNomeFuncionario(tfNomeCompletoFuncionario.getText()); 
            novoFuncionario.setTelefoneFuncionario(ftfTelefoneFuncionario.getText());
            novoFuncionario.setCelularFuncionario(ftfCelularFuncionario.getText());
            novoFuncionario.setCpfFuncionario(ftfCpfFuncionario.getText());
            novoFuncionario.setPisFuncionario(ftfPisFuncionario.getText());
            novoFuncionario.setRuaAvnFuncionario(tfLogradouroFuncionario.getText());
            novoFuncionario.setCidadeFuncionario(tfCidadeFuncionario.getText());
            novoFuncionario.setEstadoFuncionario(cbEstadoFuncionario.getSelectedItem().toString());
            novoFuncionario.setBairroFuncionario(tfBairroFuncionario.getText());
            novoFuncionario.setNumeroFuncionario(tfNumeroFuncionario.getText());        
            
            //<!-- INICIO DAS DATAS --->
            /* CONVERTENDO DATA */
            Calendar calNascimentoFuncionario=Calendar.getInstance();
            calNascimentoFuncionario.setTime((Date) dataNascimentoFuncionario.getValue());
            novoFuncionario.setDataNascimentoFuncionario(calNascimentoFuncionario);
            /* CONVERTENDO DATA */
            Calendar calAdmissaoFuncionario=Calendar.getInstance();
            calAdmissaoFuncionario.setTime((Date) dataAdmissaoFuncionario.getValue());
            novoFuncionario.setDataAdamissaoFuncionario(calAdmissaoFuncionario);
            /* CONVERTENDO DATA */
            Calendar calDemissaoFuncionario=Calendar.getInstance();
            calDemissaoFuncionario.setTime((Date) dataDemissaoFuncionario.getValue());
            novoFuncionario.setDataDemissaoFuncionario(calDemissaoFuncionario);
            /* CONVERTENDO DATA */
            Calendar calRetornoFuncionario=Calendar.getInstance();
            calRetornoFuncionario.setTime((Date) dataRetornoFuncionario.getValue());
            novoFuncionario.setDataRetornoFuncionario(calRetornoFuncionario);
            //<!-- FIM DAS DATAS -->
           
            novoFuncionario.setSituacaoFuncionario(cbSituacaoFuncionario.getSelectedItem().toString().toUpperCase());
            novoFuncionario.setCargoFuncionario(cbCargoFuncionario.getSelectedItem().toString().toUpperCase());
            novoFuncionario.setNomeUsuario(tfNomeUsuarioFuncionario.getText());
            novoFuncionario.setSenhaFuncionario(tfSenhaFuncionario.getText());
            novoFuncionario.setPermissaoFuncionario(cbNivelPermissaoFuncionario.getSelectedItem().toString().toUpperCase());
            novoFuncionario.setCepFuncionario(ftfCelularFuncionario.getValue().toString());
            return novoFuncionario;
    }
    
    /***
     * APOS ENVIAR AS INFORMAÇÕES COLETADAS PARA O BANCO DE DADOS OS CAMPOS 
     * SERÃO LIMPOS - TELA CADASTRAR FUNCIONARIO - OU NO ATO CLICAR EM 
     * 'LIMPAR CAMPOS' OU AINDA 'CANCELAR'-TELA ATUALIZAR DADOS DO FUNCIONARIO.
     */
    public void limparCamposFuncionario()
    {
        tfIdFuncionario.setText("[AUTO]");
        tfNomeCompletoFuncionario.setText("");
        ftfCpfFuncionario.setValue(null);
        ftfDataNascimentoFuncionario.setValue(null);
        ftfTelefoneFuncionario.setValue(null);
        ftfCelularFuncionario.setValue(null);
        ftfCepFuncionario.setValue(null);
        tfLogradouroFuncionario.setText("");
        tfNumeroFuncionario.setText("");
        tfBairroFuncionario.setText("");
        tfCidadeFuncionario.setText("");
        cbEstadoFuncionario.setSelectedIndex(7); // ESTADO PADRÃO ESPIRITO SANTO
        ftfDataAdmissaoFuncionario.setValue(null);
        cbSituacaoFuncionario.setSelectedIndex(0); // SITUAÇÃO PADRÃO CONTRATADO
        ftfDataDemissaoFuncionario.setValue(null); // DATA DE DEMISSÃO OU DE AFASTAMENTO
        ftfDataRetornoFuncionario.setValue(null); // 
        cbCargoFuncionario.setSelectedIndex(2);
        cbNivelPermissaoFuncionario.setSelectedIndex(0);
        tfNomeUsuarioFuncionario.setText("");
        tfSenhaFuncionario.setText("");
    }
    
    /***
     * VERIFICANDO SE TODOS OS CAMPOS OBRIGATORIOS FORAM PREEENCHIDOS CORRETAMENTE
     * @return 
     */
    public boolean preenchimentoCorretoFornecedor()
    {
        if(tfRazaoSocialFornecedor.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(ifTelaFornecedor, "PREENCHA A RAZÃO SOCIAL\nDO FORNECEDOR", "DADOS INCOMPLETOS", 2);
            tfRazaoSocialFornecedor.requestFocus();
            return false;
        }else
            if(ftfCnpjFornecedor.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(ifTelaFornecedor, "PREENCHA O CNPJ DO FORNECEDOR", "DADOS INCOMPLETOS", 2);
                ftfCnpjFornecedor.requestFocus();
                return false;
            }else
                if(ftfTelefoneFornecedor.getValue()==null)
                {
                JOptionPane.showMessageDialog(ifTelaFornecedor, "PREENCHA O NÚMERO DE\nTELEFONE DO FORNECEDOR", "DADOS INCOMPLETOS", 2);
                ftfTelefoneFornecedor.requestFocus();
                 return false;
                }else                   
                    if(ftfCepFornecedor.getValue()==null)
                    {
                       JOptionPane.showMessageDialog(ifTelaFornecedor, "PREENCHA O CEP DO FORNECEDOR", "DADOS INCOMPLETOS", 2);
                       ftfCepFornecedor.requestFocus(true); 
                        return false;
                    }else
                        if(tfLogradouroFornecedor.getText().isEmpty())
                           {
                              JOptionPane.showMessageDialog(ifTelaFornecedor, "PREENCHA O ENDEREÇO DO FORNECEDOR", "DADOS INCOMPLETOS", 2);
                              tfLogradouroFornecedor.requestFocus(true); 
                               return false;
                           }else
                                if(tfNumeroFornecedor.getText().isEmpty())
                                {
                                    JOptionPane.showMessageDialog(ifTelaFornecedor, "PREENCHA O NÚMERO DO ENDEREÇO\nDO FORNECEDOR", "DADOS INCOMPLETOS", 2);
                                    tfNumeroFornecedor.requestFocus(true);
                                     return false;
                                }else
                                    if(tfBairroFornecedor.getText().isEmpty())
                                    {
                                        JOptionPane.showMessageDialog(ifTelaFornecedor, "PREENCHA O BAIRRO FORNECEDOR", "DADOS INCOMPLETOS", 2);
                                        tfBairroFornecedor.requestFocus(true);
                                        return false;
                                    }else
                                        if(tfCidadeFornecedor.getText().isEmpty())
                                        {
                                            JOptionPane.showMessageDialog(ifTelaFornecedor, "PREENCHA A CIDADE DO FORNECEDOR", "DADOS INCOMPLETOS", 2);
                                            tfCidadeFornecedor.requestFocus();
                                            return false;
                                        }else
                                            if(tfNomeRepresentanteFornecedor.getText().isEmpty())
                                            {
                                                JOptionPane.showMessageDialog(ifTelaFornecedor, "PREENCHA O NOME DO REPRESENTANTE\nDO FORNECEDOR", "DADOS INCOMPLETOS", 2);
                                                tfNomeRepresentanteFornecedor.requestFocus();
                                                return false;
                                            }else
                                                if(ftfCelularRepresentanteFornecedor.getValue()==null)
                                                {
                                                    JOptionPane.showMessageDialog(ifTelaFornecedor, "PREENCHA O NÚMERO DE TELEFONE CELULAR\nDO REPRESENTANTE DO FORNECEDOR", "DADOS INCOMPLETOS", 2);
                                                    ftfCelularRepresentanteFornecedor.requestFocus();
                                                    return false;
                                                }else
                                                    if(!tfEmailRepresentanteFornecedor.getText().contains("@"))
                                                    {
                                                        JOptionPane.showMessageDialog(ifTelaFornecedor, "PREENCHA O E-MAIL DO REPRESENTANTE\nDO FORNECEDOR", "DADOS INCOMPLETOS", 2);
                                                        tfEmailRepresentanteFornecedor.requestFocus();
                                                        return false;
                                                    }else
                                                        {
                                                             return true;
                                                        }
    }
    
    /***
     * METODO PARA ENVIAR AO BANCO DE DADOS (QUICKER.FORNECEDORES) AS INFORMAÇÕES
     * RECOLHIDAS NA TELA FORNECEDORES - ABA CADASTRAR FORNECEDOR. ESTE METODO
     * SERÁ CAHAMADO PELO BOTÃO SALVAR.
     * @return 
     */
    public Fornecedor retornarDadosPreenchidosFornecedor()
    {
        Fornecedor novoFornecedor=new Fornecedor();
        novoFornecedor.setRazaoSocialFornecedor(tfRazaoSocialFornecedor.getText());
        novoFornecedor.setNomeFantasiaFornecedor(tfNomeFantasiaFornecedor.getText());
        novoFornecedor.setCnpjFornecedor(ftfCnpjFornecedor.getValue().toString());
        novoFornecedor.setTelefoneFornecedor(ftfTelefoneFornecedor.getValue().toString());
        novoFornecedor.setCepFornecedor(ftfCepFornecedor.getValue().toString());
        novoFornecedor.setRuaAvnFornecedor(tfLogradouroFornecedor.getText());
        novoFornecedor.setNumeroFornecedor(tfNumeroFornecedor.getText());
        novoFornecedor.setBairroFornecedor(tfBairroFornecedor.getText());
        novoFornecedor.setCidadeFornecedor(tfCidadeFornecedor.getText());
        novoFornecedor.setEstadoFornecedor(cbEstadoFornecedor.getSelectedItem().toString());
        novoFornecedor.setRepresentanteFornecedor(tfNomeRepresentanteFornecedor.getText());
        /* SE O TELEFONE DO REPRESENTANTE ESTIVER VAZIO */
        if(ftfTelefoneRepresentnteFornecedor.getValue()==null)
        {
            novoFornecedor.setTelefoneRepresentanteFornecedor(null);
        }else
            /* SE ESTIVER PREENCHIDO ENVIAR O DADO PARA O BANCO */
            if(ftfTelefoneRepresentnteFornecedor.getValue()!=null)
            {
                novoFornecedor.setTelefoneRepresentanteFornecedor(ftfTelefoneRepresentnteFornecedor.getValue().toString());
            }
        /* PROSSEGUINDO COM O RECOLHIMENTO DE DADOS */
        novoFornecedor.setCelularRepresentanteFornecedor(ftfCelularRepresentanteFornecedor.getValue().toString());
        novoFornecedor.setEmailRepresentanteFornecedor(tfEmailRepresentanteFornecedor.getText());
        
         return novoFornecedor;       
    }
    
    public void limparCamposFornecedor(){
            tfNomeFantasiaFornecedor.setText("");
            ftfTelefoneFornecedor.setText(null);
            ftfTelefoneRepresentnteFornecedor.setText(null);
            ftfCelularRepresentanteFornecedor.setText(null);
            ftfCnpjFornecedor.setText(null);
            tfBairroFornecedor.setText("");
            tfNumeroFornecedor.setText("");
            tfCidadeFornecedor.setText("");
            tfRazaoSocialFornecedor.setText("");
            cbEstadoFornecedor.setSelectedIndex(0);
            ftfCepFornecedor.setValue(null);  
            tfLogradouroFornecedor.setText("");
            tfEmailRepresentanteFornecedor.setText("");
            tfNomeRepresentanteFornecedor.setText(" ");
    }
    
    /***
     * FORMATANDO A DATA DE NASCIMENTO DO CLIENTE.
     * @param dataCampo
     * @return 
     */
    public Calendar retornarNascimentoCliente(String dataCampo)
    {
        cal = Calendar.getInstance();
                             
        String data = ftfDataNascimentoCliente.getText();
                             
       cal.set(
                Integer.parseInt(data.substring(data.lastIndexOf("/")+1, data.length())), // ANO
                Integer.parseInt(data.substring(data.indexOf("/")+1, data.lastIndexOf("/"))), // MÊS
                Integer.parseInt(data.substring(0, data.indexOf("/"))) // DIA
              );
       
       return cal;
    }
    
    /***
     * FORMATANDO A DATA DE NASCIMENTO DO FUNCIONARIO. 
     * @param dataCampo
     * @return 
     */
    public Calendar retornarNascimentoFuncionario(String dataCampo)
    {
        cal=Calendar.getInstance();
        String data=ftfDataNascimentoFuncionario.getText();
        cal.set(
                Integer.parseInt(data.substring(data.lastIndexOf("/")+1, data.length())), // ANO
                Integer.parseInt(data.substring(data.indexOf("/")+1, data.lastIndexOf("/"))), // MÊS
                Integer.parseInt(data.substring(0, data.indexOf("/"))) // DIA
        );
        return  cal;
    }
    
    /***
     * FORMATANDO A DATA DE DEMISSÃO DO FUNCIOANRIO.
     * @param dataCampo
     * @return 
     */
    public Calendar retornarDemissaoFuncionario(String dataCampo)
    {
        cal = Calendar.getInstance();
                             
        String data = ftfDataDemissaoFuncionario.getText();
                             
       cal.set(
                Integer.parseInt(data.substring(data.lastIndexOf("/")+1, data.length())), // ANO
                Integer.parseInt(data.substring(data.indexOf("/")+1, data.lastIndexOf("/"))), // MÊS
                Integer.parseInt(data.substring(0, data.indexOf("/"))) // DIA
              );
       
       return cal;
    }
    
    /***
     * FORMATANDO A DATA DE ADMISSÃO DO FUNCIOANRIO.
     * @param dataCampo
     * @return 
     */
    public Calendar retornarAdmissaoFuncionario(String dataCampo)
    {
        cal = Calendar.getInstance();
                             
        String data = ftfDataAdmissaoFuncionario.getText();
                             
       cal.set(
                Integer.parseInt(data.substring(data.lastIndexOf("/")+1, data.length())), // ANO
                Integer.parseInt(data.substring(data.indexOf("/")+1, data.lastIndexOf("/"))), // MÊS
                Integer.parseInt(data.substring(0, data.indexOf("/"))) // DIA
              );
       
       return cal;
    }
    
    /***
     * FORMATANDO A DATA DE RETORNO DO FUNCIOANRIO.
     * @param dataCampo
     * @return 
     */
    public Calendar retornarRetornoFuncionario(String dataCampo)
    {
        cal = Calendar.getInstance();
                             
        String data = ftfDataRetornoFuncionario.getText();
                             
       cal.set(
                Integer.parseInt(data.substring(data.lastIndexOf("/")+1, data.length())), // ANO
                Integer.parseInt(data.substring(data.indexOf("/")+1, data.lastIndexOf("/"))), // MÊS
                Integer.parseInt(data.substring(0, data.indexOf("/"))) // DIA
              );
       
       return cal;
    }
    
    /***
     * METODO PARA DEFINIR O FORMATO DOS CAMPOS DE TEXTO FORMATADO.
     * @param Mascara
     * @return 
     */
    public MaskFormatter Mascara(String Mascara){  
         
       MaskFormatter F_Mascara = new MaskFormatter();  
       try{  
           F_Mascara.setMask(Mascara); //Atribui a mascara  
           F_Mascara.setPlaceholderCharacter(' '); //Caracter para preencimento   
       }  
       catch (Exception excecao) {  
       excecao.printStackTrace();  
       }   
       return F_Mascara;  
}   
    
    /***
     * INSERINDO PRODUTOSMATERIA NO 'CBNOMEPRODUTO'.
     */
    public void preencherComboBoxProdutoMateria()
    {
        cbNomeProduto.removeAllItems();
        int i; // produto selecionado
        listaProdutosMateria = conexaoTabelaProdutoMateria.selecionarTodosProdutosMateria();
        for(i=0; i<listaProdutosMateria.size(); i++)
        {
            cbNomeProduto.addItem(listaProdutosMateria.get(i).getNomeProdutoMateria()); 
        }
    }
    
    /***
     * INSERINDO O TIPO DO PRODUTO SELECIONADO-NO 'CBNOMEPRODUTO'
     */
    public void preecherTipoProduto()
    {
        listaProdutosMateria = conexaoTabelaProdutoMateria.selecionarTodosProdutosMateria();
        tfTipoProduto.setText(listaProdutosMateria.get(cbNomeProduto.getSelectedIndex()).getTipoProdutoMateria());
    }
    
    /***
     * INSERINDO O TIPO DO PRODUTO SELECIONADO-NO 'CBNOMEPRODUTO'
     */
    public void preecherUnidadeMedidaProduto()
    {
                tfUnidMedidaProduto.setText(listaProdutosMateria.get(cbNomeProduto.getSelectedIndex()).getUnidadeMedidaProdutoMateria());
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAtualizarCliente;
    private javax.swing.JButton btAtualizarFornecedor;
    private javax.swing.JButton btAtualizarFuncionario;
    private javax.swing.JButton btAtualizarProdutoEstoque;
    private javax.swing.JButton btCancelarAtualizarCliente;
    private javax.swing.JButton btCancelarAtualizarFornecedor;
    private javax.swing.JButton btCancelarAtualizarFuncionario;
    private javax.swing.JButton btCancelarProdutoEstoque;
    private javax.swing.JButton btLimparCamposCliente;
    private javax.swing.JButton btLimparCamposCliente1;
    private javax.swing.JButton btLimparCamposFornecedor;
    private javax.swing.JButton btLimparCamposFuncionario;
    private javax.swing.JButton btNovoProduto;
    private javax.swing.JButton btRelatorioCliente;
    private javax.swing.JButton btRelatorioFornecedor;
    private javax.swing.JButton btRelatorioFuncionario;
    private javax.swing.JButton btRelatorioProduto;
    private javax.swing.JButton btRemoverCliente;
    private javax.swing.JButton btRemoverFornecedor;
    private javax.swing.JButton btRemoverFuncionario;
    private javax.swing.JButton btRemoverProduto;
    private javax.swing.JButton btSalvarAtualizarCliente;
    private javax.swing.JButton btSalvarAtualizarFornecedor;
    private javax.swing.JButton btSalvarAtualizarFuncionario;
    private javax.swing.JButton btSalvarAtualizarProdutoEstoque;
    private javax.swing.JButton btSalvarCliente;
    private javax.swing.JButton btSalvarCliente1;
    private javax.swing.JButton btSalvarFornecedor;
    private javax.swing.JButton btSalvarFuncionario;
    private javax.swing.JComboBox cbCargoFuncionario;
    private javax.swing.JComboBox cbEstadoFornecedor;
    private javax.swing.JComboBox cbEstadoFuncionario;
    private javax.swing.JComboBox cbNivelPermissaoFuncionario;
    private javax.swing.JComboBox cbNomeProduto;
    private javax.swing.JComboBox cbSituacaoFuncionario;
    private javax.swing.JDesktopPane dpAreaTrabalho;
    private javax.swing.JFormattedTextField ftfCelularCliente;
    private javax.swing.JFormattedTextField ftfCelularFuncionario;
    private javax.swing.JFormattedTextField ftfCelularRepresentanteFornecedor;
    private javax.swing.JFormattedTextField ftfCepCliente;
    private javax.swing.JFormattedTextField ftfCepFornecedor;
    private javax.swing.JFormattedTextField ftfCepFuncionario;
    private javax.swing.JFormattedTextField ftfCnpjFornecedor;
    private javax.swing.JFormattedTextField ftfCpfCliente;
    private javax.swing.JFormattedTextField ftfCpfFuncionario;
    private javax.swing.JFormattedTextField ftfDataAdmissaoFuncionario;
    private javax.swing.JFormattedTextField ftfDataCompraProduto;
    private javax.swing.JFormattedTextField ftfDataDemissaoFuncionario;
    private javax.swing.JFormattedTextField ftfDataNascimentoCliente;
    private javax.swing.JFormattedTextField ftfDataNascimentoFuncionario;
    private javax.swing.JFormattedTextField ftfDataRetornoFuncionario;
    private javax.swing.JFormattedTextField ftfDataValidadeProduto;
    private javax.swing.JFormattedTextField ftfPisFuncionario;
    private javax.swing.JFormattedTextField ftfTelefoneCliente;
    private javax.swing.JFormattedTextField ftfTelefoneFornecedor;
    private javax.swing.JFormattedTextField ftfTelefoneFuncionario;
    private javax.swing.JFormattedTextField ftfTelefoneRepresentnteFornecedor;
    private javax.swing.JFormattedTextField ftfValorRecebido;
    private javax.swing.JFormattedTextField ftfValorTotal;
    private javax.swing.JFormattedTextField ftfValorTroco;
    private javax.swing.JInternalFrame ifTelaCliente;
    private javax.swing.JInternalFrame ifTelaFornecedor;
    private javax.swing.JInternalFrame ifTelaFuncionario;
    private javax.swing.JInternalFrame ifTelaPdv;
    private javax.swing.JInternalFrame ifTelaProdutoEstoque;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbCliente;
    private javax.swing.JLabel lbDataDemissaoFuncionario;
    private javax.swing.JLabel lbDataRetornoFuncionario;
    private javax.swing.JLabel lbDinheiro;
    private javax.swing.JLabel lbFuncionario;
    private javax.swing.JLabel lbLogoff;
    private javax.swing.JLabel lbPdv;
    private javax.swing.JMenuItem miCadastrarCliente;
    private javax.swing.JMenuItem miCadastrarFornecedor;
    private javax.swing.JMenuItem miCadastrarFuncionario;
    private javax.swing.JMenuItem miCadastrarProduto;
    private javax.swing.JMenuItem miGerenciarCliente;
    private javax.swing.JMenuItem miGerenciarFornecedor;
    private javax.swing.JMenuItem miGerenciarFuncionario;
    private javax.swing.JMenuItem miGerenciarProduto;
    private javax.swing.JMenuItem miManualUsuario;
    private javax.swing.JMenuItem miPDV;
    private javax.swing.JMenuItem miSobre;
    private javax.swing.JMenu mnArquivo;
    private javax.swing.JMenu mnGerencia;
    private javax.swing.JMenu mnSobre;
    private javax.swing.JMenu smnClientes;
    private javax.swing.JMenu smnFornecedores;
    private javax.swing.JMenu smnFuncionarios;
    private javax.swing.JMenu smnPizzas;
    private javax.swing.JMenu smnProdutos;
    private javax.swing.JTable tbListaClientes;
    private javax.swing.JTable tbListaFornecedores;
    private javax.swing.JTable tbListaFuncionarios;
    private javax.swing.JTable tbListaProdutosEstoque;
    private javax.swing.JTable tbListaProdutosVenda;
    private javax.swing.JTextField tfAtendente;
    private javax.swing.JTextField tfBairroCliente;
    private javax.swing.JTextField tfBairroFornecedor;
    private javax.swing.JTextField tfBairroFuncionario;
    private javax.swing.JTextField tfBuscaCliente;
    private javax.swing.JTextField tfBuscaFornecedor;
    private javax.swing.JTextField tfBuscaFuncionario;
    private javax.swing.JTextField tfBuscaProdutoEstoque;
    private javax.swing.JTextField tfCidadeCliente;
    private javax.swing.JTextField tfCidadeFornecedor;
    private javax.swing.JTextField tfCidadeFuncionario;
    private javax.swing.JTextField tfComplementoCliente;
    private javax.swing.JTextField tfEmailCliente;
    private javax.swing.JTextField tfEmailRepresentanteFornecedor;
    private javax.swing.JTextField tfIdCliente;
    private javax.swing.JTextField tfIdFornecedor;
    private javax.swing.JTextField tfIdFuncionario;
    private javax.swing.JTextField tfIdProduto;
    private javax.swing.JTextField tfLogradouroCliente;
    private javax.swing.JTextField tfLogradouroFornecedor;
    private javax.swing.JTextField tfLogradouroFuncionario;
    private javax.swing.JTextField tfNomeCompletoCliente;
    private javax.swing.JTextField tfNomeCompletoFuncionario;
    private javax.swing.JTextField tfNomeFantasiaFornecedor;
    private javax.swing.JTextField tfNomeRepresentanteFornecedor;
    private javax.swing.JTextField tfNomeUsuarioFuncionario;
    private javax.swing.JTextField tfNotaFiscalProduto;
    private javax.swing.JTextField tfNumeroCliente;
    private javax.swing.JTextField tfNumeroFornecedor;
    private javax.swing.JTextField tfNumeroFuncionario;
    private javax.swing.JTextField tfQuantCompraProduto;
    private javax.swing.JTextField tfRazaoSocialFornecedor;
    private javax.swing.JTextField tfSenhaFuncionario;
    private javax.swing.JTextField tfTipoProduto;
    private javax.swing.JTextField tfUnidMedidaProduto;
    private javax.swing.JTabbedPane tpnAbasCliente;
    private javax.swing.JTabbedPane tpnAbasFornecedor;
    private javax.swing.JTabbedPane tpnAbasFuncionario;
    private javax.swing.JTabbedPane tpnAbasPdv;
    private javax.swing.JTabbedPane tpnAbasProdutoEstoque;
    // End of variables declaration//GEN-END:variables

}
