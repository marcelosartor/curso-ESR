package com.algaworks.algafood.infrastructure.service.report;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import com.algaworks.algafood.infrastructure.service.report.exception.ReportException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Service
public class PdfVendaReportService implements VendaReportService {
	
	@Autowired
	private VendaQueryService vendaQueryService; 

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filter, String timeOffSet) {

		
		try {
			var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("REPORT_LOCALE", new Locale("pt","BR"));
			
			var vendasDiarias = vendaQueryService.consultarVendaDiaria(filter, timeOffSet);
			
			var dataSource = new JRBeanCollectionDataSource(vendasDiarias);
			
			var jasperPrint = JasperFillManager.fillReport(inputStream, parameters,dataSource);
			
			var relatorio = JasperExportManager.exportReportToPdf(jasperPrint);
			
			return relatorio;
		
		} catch (Exception e) {
			throw new ReportException("Não foi possivel emitir relatorio de vendas diárias",e);
		}
		
	}

}
