/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reports;

import Bean.ServicoOrcamentoItensBean;
import View.servicos.CotacaoServico;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Marcos Filho
 */
public class JasperOrcamentoServico {

    public void gerar(String layout) throws JRException, SQLException, ClassNotFoundException {
        
        int rc = CotacaoServico.tableitens.getRowCount();
        
        //gerando o jasper design
        InputStream is = JRLoader.getResourceInputStream(layout);
        JasperDesign desenho = JRXmlLoader.load(is);

        //compila o relatório
        JasperReport relatorio = JasperCompileManager.compileReport(desenho);

        //Colocar os itens do orçamento na table
        //List para colocar os itens
        List<ServicoOrcamentoItensBean> li = new ArrayList<>();
        
        //Criar itens
//        li.add(CotacaoServico.tableitens.getValueAt(0, 0).toString());
        ServicoOrcamentoItensBean item = new ServicoOrcamentoItensBean();
        item.setCodigo(CotacaoServico.tableitens.getValueAt(0, 2).toString());
        li.add(item);
        
        //Converte List para JRBeanCollectionDataSource
        JRBeanCollectionDataSource jrbeanlist = new JRBeanCollectionDataSource(li);

        //executa o relatório
        Map parametros = new HashMap();
        parametros.put("cliente", CotacaoServico.txtnomecliente.getText());
        parametros.put("vendedor", CotacaoServico.txtvendedor.getText());
        parametros.put("representante", CotacaoServico.txtrepresentante.getText());
        parametros.put("condicao", CotacaoServico.txtcondicao.getText());
        parametros.put("itens", jrbeanlist);
        parametros.put("obs", CotacaoServico.txtnotes.getText());

        JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros);

        //exibe o resultado
        JasperViewer viewer = new JasperViewer(impressao, true);
        viewer.show();
//        InputStream caminhorelatorio = this.getClass().getClassLoader().getResourceAsStream("Reports/orcamento_servico.jasper");
//        JasperPrint print = JasperFillManager.fillReport(caminhorelatorio, new HashMap());
//        JasperPrintManager.printReport(print, false);
//        JasperViewer viewer = new JasperViewer(print, false);
//        viewer.show();
    }
}
