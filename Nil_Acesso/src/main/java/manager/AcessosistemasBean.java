package manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import entity.AcessoSistemas;
import persistence.AcessoSistemasDao;


@ManagedBean
@ViewScoped
public class AcessosistemasBean {

	private Integer id_acessosis;
	
	private String nome_acessotelas;
	
    private AcessoSistemas acessosistemas;
    private AcessoSistemas acessosistemasalteracao;
    
    private List<AcessoSistemas> acessosistemaslista;
	 
	
	public AcessosistemasBean() {
		acessosistemas = new AcessoSistemas() ;
		acessosistemasalteracao = new AcessoSistemas() ;
		
		acessosistemaslista = new ArrayList<AcessoSistemas>();	
	}

	public AcessoSistemas getAcessosistemasalteracao() {
		return acessosistemasalteracao;
	}

	public void setAcessosistemasalteracao(AcessoSistemas acessosistemasalteracao) {
		this.acessosistemasalteracao = acessosistemasalteracao;
	}

	public Integer getId_acessosis() {
		return id_acessosis;
	}

	public void setId_acessosis(Integer id_acessosis) {
		this.id_acessosis = id_acessosis;
	}

	public String getNome_acessotelas() {
		return nome_acessotelas;
	}

	public void setNome_acessotelas(String nome_acessotelas) {
		this.nome_acessotelas = nome_acessotelas;
	}

	public AcessoSistemas getAcessosistemas() {
		return acessosistemas;
	}

	public void setAcessosistemas(AcessoSistemas acessosistemas) {
		this.acessosistemas = acessosistemas;
	}

	public List<AcessoSistemas> getAcessosistemaslista() {
		return acessosistemaslista;
	}

	public void setAcessosistemaslista(List<AcessoSistemas> acessosistemaslista) {
		this.acessosistemaslista = acessosistemaslista;
	}

	public void gravar() {
		AcessoSistemasDao asd = new AcessoSistemasDao();
		
		// Criticar nomes duplicados
		Boolean camposduplicados = false;
		
		try {
			camposduplicados = asd.testacamposduplicados(acessosistemas);
			
			if (camposduplicados) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Voce informou nome do sistema duplicado", ""));
			} else {
				
				asd.gravar(acessosistemas);
				
				acessosistemas = new AcessoSistemas() ;
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravacao efetuada!", ""));
			}
			
		} catch (Exception e){
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"", ""));

		}
	}	
	
	//Ativar o consultar pra trazer as informações que foram cadastradas no banco
	
	public void buscar() {
		
		AcessoSistemasDao asd = new AcessoSistemasDao();
		
		try {
			
			acessosistemaslista = asd.consultar(acessosistemas);
			
		} catch (Exception e) {
			e.printStackTrace();  
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}
	
	public void abredialogoalterar() {
		
		acessosistemaslista.remove(acessosistemasalteracao);
		
		PrimeFaces.current().executeScript("PF('dialogoalterar').show();");
	}
	
	public void abredialogoexclusao() {
		PrimeFaces.current().executeScript("PF('dialogoexcluir').show();");
	}
	
	public void abrirdialogocadastrar() {
		acessosistemas = new AcessoSistemas();
		
		java.util.Date data = new java.util.Date();
		
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		
		acessosistemas.setDatacriacao_sistema(formatador.format(data));
		
		PrimeFaces.current().executeScript("PF('dialogocadastrar').show();");
	}
	
	public void alterar() {
		AcessoSistemasDao asd = new AcessoSistemasDao();
		
		try {
			
			Integer registrosalterados = asd.alterar(acessosistemasalteracao);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, registrosalterados + " Sistema alterado!", ""));
			
			PrimeFaces.current().executeScript("PF('dialogoalterar').hide();");
			
			acessosistemaslista.add(acessosistemasalteracao);
	
		} catch (Exception e) {
			e.printStackTrace();  
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public void excluir() {
		AcessoSistemasDao asd = new AcessoSistemasDao();

		try {
			
			Integer registrosalterados = asd.excluir(acessosistemasalteracao);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, registrosalterados + " Sistema excluido!", ""));
			
			PrimeFaces.current().executeScript("PF('dialogoexcluir').hide();");
		
			acessosistemaslista.remove(acessosistemas);

		} catch (Exception e) {
			e.printStackTrace();  
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public String imprimir(){
		
//		if ((sistemaslistafiltro.size()==0)) {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum sistema encontrado para imprimir", "")); 
//		} else {
//			
//			try{	
//	
//				DSReportSistemas ds = new DSReportSistemas(sistemaslistafiltro);
//				
//				InputStream arquivo = FacesContext.getCurrentInstance()
//					.getExternalContext().getResourceAsStream("/AcessoSistemas.jasper");	
//	
//				byte[] pdf = JasperRunManager.runReportToPdf(arquivo, null, ds);
//					
//				HttpServletResponse res = (HttpServletResponse) FacesContext
//						.getCurrentInstance().getExternalContext().getResponse();
//					
//				res.setContentType("application/pdf");
//				
//				res.setHeader("Content-Disposition","inline; filename=\"relatoriosistemas.pdf\"");
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