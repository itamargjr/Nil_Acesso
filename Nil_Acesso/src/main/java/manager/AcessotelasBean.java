package manager;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import entity.AcessoSistemas;
import entity.AcessoTelas;
import persistence.AcessoSistemasDao;
import persistence.AcessoTelasDao;


@ManagedBean
@ViewScoped
public class AcessotelasBean {
		
	private Integer id_acessosis;
	private Integer id_acessotelas;
	
	private AcessoTelas acessotelas;
	private AcessoTelas acessotelasalteracao;
	
	private List<AcessoSistemas> sistemaslistas;
	private List<AcessoTelas> acessotelaslista;
	
	public AcessotelasBean() {
		acessotelas = new AcessoTelas();
		acessotelasalteracao = new AcessoTelas();
		
		acessotelaslista = new ArrayList<AcessoTelas>();
		
		try {
			
			AcessoSistemasDao sd = new AcessoSistemasDao(); 
			sistemaslistas = sd.findAll();
			
		}catch (Exception e) {
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}
	
	public AcessoTelas getAcessotelasalteracao() {
		return acessotelasalteracao;
	}

	public void setAcessotelasalteracao(AcessoTelas acessotelasalteracao) {
		this.acessotelasalteracao = acessotelasalteracao;
	}

	public List<AcessoTelas> getAcessotelaslista() {
		return acessotelaslista;
	}

	public void setAcessotelaslista(List<AcessoTelas> acessotelaslista) {
		this.acessotelaslista = acessotelaslista;
	}

	public AcessoTelas getAcessotelas() {
		return acessotelas;
	}

	public void setAcessotelas(AcessoTelas acessotelas) {
		this.acessotelas = acessotelas;
	}

	public List<AcessoSistemas> getSistemaslistas() {
		return sistemaslistas;
	}

	public void setSistemaslistas(List<AcessoSistemas> sistemaslistas) {
		this.sistemaslistas = sistemaslistas;
	}

	public Integer getId_acessosis() {
		return id_acessosis;
	}

	public void setId_acessosis(Integer id_acessosis) {
		this.id_acessosis = id_acessosis;
	}
	
	public Integer getId_acessotelas() {
		return id_acessotelas;
	}
	
	public void setId_acessotelas(Integer id_acessotelas) {
		this.id_acessotelas = id_acessotelas;
	}
	
	public void buscar () {
		
		AcessoTelasDao atd = new AcessoTelasDao();
		
		try {
			
			acessotelaslista = atd.buscar(acessotelas);
			
		} catch (Exception e) {
			e.printStackTrace();  
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}
	
	public void abredialogoalterar() {
		
		acessotelaslista.remove(acessotelasalteracao);
		
		PrimeFaces.current().executeScript("PF('dialogoalterar').show();");
	}
	
	public void abredialogoexclusao() {
		PrimeFaces.current().executeScript("PF('dialogoexcluir').show();");
	}
	
	public void abrirdialogocadastrar() {
		acessotelas = new AcessoTelas();
		
		PrimeFaces.current().executeScript("PF('dialogocadastrar').show();");
	}
	
	public void gravar() {
		AcessoTelasDao atd = new AcessoTelasDao();
			
		// Criticar os nomes duplicados		
		Boolean camposduplicados = false;
		
		try {
			camposduplicados = atd.testacamposduplicados(acessotelas);
			
			if (camposduplicados) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Voce informou o nome duplicado", ""));
			} else {
				atd.gravar(acessotelas);
				
				acessotelaslista.add(acessotelas);
				
				acessotelas = new AcessoTelas();
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravacao efetuada", ""));
			}
			
		} catch (Exception e) {
			e.printStackTrace();  
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}
	
	public void alterar() {
		AcessoTelasDao atd = new AcessoTelasDao();
		
		try {
			
			Integer registrosalterados = atd.alterar(acessotelasalteracao);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, registrosalterados + " Tela alterada!", ""));
			
			PrimeFaces.current().executeScript("PF('dialogoalterar').hide();");
			
			acessotelaslista.add(acessotelasalteracao);
			
			acessotelas = new AcessoTelas() ;
			
		} catch (Exception e) {
			e.printStackTrace();  
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
			
	}
	
	public void excluir() {
		AcessoTelasDao atd = new AcessoTelasDao();
		
		try {
			
			Integer registrosalterados = atd.excluir(acessotelasalteracao);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, registrosalterados + " Tela excluida!", ""));
			
			PrimeFaces.current().executeScript("PF('dialogoexcluir').hide();");
			
			acessotelaslista.remove(acessotelasalteracao);
			
		} catch (Exception e) {
			e.printStackTrace();  
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}
	
	public String imprimir(){
		
//		if ((acessotelaslistafiltro.size()==0)) {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhuma tela encontrada para imprimir", "")); 
//		} else {
//			
//			try{	
//	
//				DSReportTelas ds = new DSReportTelas(acessotelaslistafiltro);
//				
//				InputStream arquivo = FacesContext.getCurrentInstance()
//					.getExternalContext().getResourceAsStream("/AcessoTelas.jasper");	
//	
//				byte[] pdf = JasperRunManager.runReportToPdf(arquivo, null, ds);
//					
//				HttpServletResponse res = (HttpServletResponse) FacesContext
//						.getCurrentInstance().getExternalContext().getResponse();
//					
//				res.setContentType("application/pdf");
//				
//				res.setHeader("Content-Disposition","inline; filename=\"relatoriotelas.pdf\"");
//				
//				res.setContentLength(pdf.length);
//					
//				OutputStream out = res.getOutputStream();
//	
//				out.write(pdf, 0, pdf.length);
//	
//				out.flush();	
//				
//				out.close();
//				
//				FacesContext.getCurrentInstance().responseComplete();
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//	
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "")); 
//			}
//		}
		return null;
	}
}