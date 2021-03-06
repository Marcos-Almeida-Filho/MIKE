/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Geral;

import DAO.RastreamentoDocumentosObsDAO;
import DAO.RastreamentoDocumentosDAO;
import DAO.RastreamentoDocumentosDocDAO;
import Methods.Dates;
import Methods.ReadXMLDocumento;
import Methods.SendEmail;
import Methods.Valores;
import View.financeiro.AdicionarContasAPagar;
import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Marcos Filho
 */
public class ProcurarRastreamentoDocumento extends javax.swing.JInternalFrame {

    /**
     * Creates new form ProcurarRastreamentoDocumento
     */
    public String origem;

    //File para arquivo xml
    File xml;

    //DAOs para pesquisa
    static RastreamentoDocumentosDAO rdd = new RastreamentoDocumentosDAO();
    RastreamentoDocumentosDocDAO rddd = new RastreamentoDocumentosDocDAO();
    RastreamentoDocumentosObsDAO rdod = new RastreamentoDocumentosObsDAO();

    public ProcurarRastreamentoDocumento(String origin) {
        initComponents();
        readtablevendedores();
        origem = origin;
    }

    public static void readtablevendedores() {
        //DefaultTable para modificar tabledocs
        DefaultTableModel model = (DefaultTableModel) tabledocs.getModel();

        rdd.readtableaprovado().forEach((rdb) -> {
            model.addRow(new Object[]{
                rdb.getId(),
                rdb.getEmitente(),
                rdb.getNumero()
            });
        });
    }

    public static void filtertablevendedores() {
        //DefaultTable para modificar tabledocs
        DefaultTableModel model = (DefaultTableModel) tabledocs.getModel();
        model.setRowCount(0);

        rdd.pesquisa(txtpesquisa.getText()).forEach((rdb) -> {
            model.addRow(new Object[]{
                rdb.getId(),
                rdb.getEmitente(),
                rdb.getNumero()
            });
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabledocs = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtpesquisa = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Procurar Documento Lançado");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tabledocs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Fornecedor", "Número"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabledocs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabledocsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabledocs);
        if (tabledocs.getColumnModel().getColumnCount() > 0) {
            tabledocs.getColumnModel().getColumn(0).setMinWidth(40);
            tabledocs.getColumnModel().getColumn(0).setPreferredWidth(40);
            tabledocs.getColumnModel().getColumn(0).setMaxWidth(40);
            tabledocs.getColumnModel().getColumn(1).setResizable(false);
            tabledocs.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Vendedor"));

        jLabel1.setText("Pesquisa");

        txtpesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtpesquisaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpesquisa)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtpesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabledocsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabledocsMouseClicked
        if (evt.getClickCount() == 2) {
            switch (origem) {
                case "CAP":
                    //ID em int
                    int id = Integer.parseInt(tabledocs.getValueAt(tabledocs.getSelectedRow(), 0).toString());

                    //Pegar dados do documento
                    rdd.click(id).forEach(rdb -> {
                        AdicionarContasAPagar.txtemitente.setText(rdb.getEmitente());
                        AdicionarContasAPagar.txtnumero.setText(rdb.getNumero());
                        Dates.SetarDataJDateChooser(AdicionarContasAPagar.dateemissao, rdb.getEmissao());
                        AdicionarContasAPagar.idrastreamento = id;
                        if (rdb.getXml() != null) {
                            xml = new File(rdb.getXml());

                            //Pegar parcelas
                            try {
                                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                                DocumentBuilder dBuilder = null;
                                try {
                                    dBuilder = dbFactory.newDocumentBuilder();
                                } catch (ParserConfigurationException ex) {
                                    Logger.getLogger(ReadXMLDocumento.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                Document doc = null;
                                try {
                                    doc = dBuilder.parse(xml);
                                } catch (SAXException | IOException ex) {
                                    Logger.getLogger(ReadXMLDocumento.class.getName()).log(Level.SEVERE, null, ex);
                                    JOptionPane.showMessageDialog(null, "Erro ao ler XML!\n" + ex);
                                }

                                //optional, but recommended
                                //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
                                doc.getDocumentElement().normalize();

                                //Buscar Duplicatas
                                NodeList duplist = doc.getElementsByTagName("dup");
                                int qtd = duplist.getLength();

                                //Buscar vencimentos
                                NodeList vencimentolist = doc.getElementsByTagName("dVenc");

                                //Buscar valores das parcelas
                                NodeList valorlist = doc.getElementsByTagName("vDup");

                                //Buscar valor total da nota
                                NodeList totallist = doc.getElementsByTagName("vLiq");
                                Node totalnode = totallist.item(0);
                                if (totallist.getLength() > 0) {
                                    AdicionarContasAPagar.txttotal.setText(Valores.TransformarValorFloatEmDinheiro(totalnode.getTextContent()));
                                }

                                DefaultTableModel modelparcelas = (DefaultTableModel) AdicionarContasAPagar.tableparcelas.getModel();

                                for (int i = 0; i < qtd; i++) {
                                    Node vencimentonode = vencimentolist.item(i);
                                    Node valornode = valorlist.item(i);
                                    String parcela = i + 1 + "/" + qtd;

                                    modelparcelas.addRow(new Object[]{
                                        parcela,
                                        Dates.TransformarDataCurtaDoDB(vencimentonode.getTextContent()),
                                        Valores.TransformarValorFloatEmDinheiro(valornode.getTextContent())
                                    });
                                }
                            } catch (DOMException e) {
                                JOptionPane.showMessageDialog(null, "Erro ao ler arquivo!\n" + e);
                                try {
                                    SendEmail.EnviarErro("Erro ao ler arquivo!\n" + e.toString());
                                } catch (AWTException | IOException ex) {
                                    Logger.getLogger(RastreamentoDocumentosDAO.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    });

                    //Pegar documentos do Documento
                    rddd.read(id).forEach(rddb -> {
                        DefaultTableModel modeldoc = (DefaultTableModel) AdicionarContasAPagar.tabledocumentos.getModel();
                        modeldoc.setNumRows(0);

                        modeldoc.addRow(new Object[]{
                            "",
                            false,
                            rddb.getDescricao(),
                            "",
                            rddb.getLocal()
                        });
                    });

                    //Pegar observações do Documento
                    rdod.read(id).forEach(rdob -> {
                        DefaultTableModel modelobs = (DefaultTableModel) AdicionarContasAPagar.tableobs.getModel();
                        
                        modelobs.addRow(new Object[]{
                            rdob.getUsuario(),
                            Dates.TransformarDataCurtaDoDB(rdob.getData()),
                            rdob.getObs()
                        });
                    });
                    
                    dispose();
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Deu ruim.");
                    break;
            }
        }
    }//GEN-LAST:event_tabledocsMouseClicked

    private void txtpesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtpesquisaKeyReleased
        filtertablevendedores();
    }//GEN-LAST:event_txtpesquisaKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tabledocs;
    public static javax.swing.JTextField txtpesquisa;
    // End of variables declaration//GEN-END:variables
}
