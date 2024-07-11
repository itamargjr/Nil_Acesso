package manager;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import entity.AcessoSistemas;
import entity.AcessoTelas;
import entity.AcessoUsuTelas;
import entity.Ctrl_usuarios;
import persistence.Ctrl_usuariosDao;
import persistence.AcessoTelasDao;
import persistence.AcessoUsuTelasDao;

@ManagedBean
@ViewScoped
public class AcessousutelasBean {
	
	private AcessoUsuTelas acessousutelas; 
	
	private List<AcessoUsuTelas> acessousutelaslista;
	private List<AcessoUsuTelas> acessousutelasselecionadaslista;
	private List<AcessoSistemas> sistemaslista;
	private List<Ctrl_usuarios> acessousuarioslista;
	private List<AcessoTelas> acessotelaslista;
	
	public AcessousutelasBean() {
		acessousutelas = new AcessoUsuTelas();
		acessousutelaslista =  new ArrayList<AcessoUsuTelas>();	
		acessousutelasselecionadaslista =  new ArrayList<AcessoUsuTelas>();
		acessotelaslista = new ArrayList<AcessoTelas>();
		
		try {
			
			AcessoUsuTelasDao autd = new AcessoUsuTelasDao(); 	
			sistemaslista = autd.findAll();
			
			Ctrl_usuariosDao aud = new Ctrl_usuariosDao();
			acessousuarioslista = aud.findAll();
			
			//System.out.println(sistemaslistas);

		} catch (Exception e) {
			e.printStackTrace();
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));			
		}	
	}

	public List<AcessoTelas> getAcessotelaslista() {
		return acessotelaslista;
	}

	public void setAcessotelaslista(List<AcessoTelas> acessotelaslista) {
		this.acessotelaslista = acessotelaslista;
	}

	public List<AcessoUsuTelas> getAcessousutelasselecionadaslista() {
		return acessousutelasselecionadaslista;
	}

	public void setAcessousutelasselecionadaslista(List<AcessoUsuTelas> acessousutelasselecionadaslista) {
		this.acessousutelasselecionadaslista = acessousutelasselecionadaslista;
	}

	public AcessoUsuTelas getAcessousutelas() {
		return acessousutelas;
	}

	public void setAcessousutelas(AcessoUsuTelas acessousutelas) {
		this.acessousutelas = acessousutelas;
	}

	public List<AcessoUsuTelas> getAcessousutelaslista() {
		return acessousutelaslista;
	}

	public void setAcessousutelaslista(List<AcessoUsuTelas> acessousutelaslista) {
		this.acessousutelaslista = acessousutelaslista;
	}

	public List<AcessoSistemas> getSistemaslista() {
		return sistemaslista;
	}

	public void setSistemaslista(List<AcessoSistemas> sistemaslista) {
		this.sistemaslista = sistemaslista;
	}

	public List<Ctrl_usuarios> getAcessousuarioslista() {
		return acessousuarioslista;
	}

	public void setAcessousuarioslista(List<Ctrl_usuarios> acessousuarioslista) {
		this.acessousuarioslista = acessousuarioslista;
	}
	
	public void consultar() {
		
		acessousutelasselecionadaslista = new ArrayList<AcessoUsuTelas>();
		acessousutelaslista = new ArrayList<AcessoUsuTelas>();
		
		try {
			
			AcessoTelasDao atd = new AcessoTelasDao();
			
			acessotelaslista = atd.listartelasdosistema(acessousutelas.getCodigo_sistema());
			
			for (AcessoTelas acessoTelas : acessotelaslista) {
				AcessoUsuTelas a = new AcessoUsuTelas(acessoTelas.getCodigo_tela(), acessoTelas.getNome_tela(), acessousutelas.getCodigo_sistema());
				
				acessousutelaslista.add(a);
			}
			
			AcessoUsuTelasDao autd = new AcessoUsuTelasDao();
			
			acessousutelasselecionadaslista = autd.consultar(acessousutelas);
			
			//System.out.println(acessousutelaslista);
			//System.out.println(acessousutelasselecionadaslista);
			
			
		} catch (Exception e) {
			e.printStackTrace();  
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}

	public void gravaralteracoes() {
		
		try {
			
			AcessoUsuTelasDao aud = new AcessoUsuTelasDao();  			//Chamamos o Dao e demos uma variavel para ele 
			
			if ((acessousutelas.getId_usu()==null)||(acessousutelas.getId_usu().equals(0))) { 		//Criamos um filtro que se o id do usuario for igual a null ou igual a zero retorne a mensagem para o usuario. 
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário selecionado incorretamente", ""));
			} else {
				if ((acessousutelas.getCodigo_sistema()==null)||(acessousutelas.getCodigo_sistema().equals(0))) {    //Criamos um filtro que se o id do sistema for igual a null ou igual a zero retorne a mensagem para o usuario
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sistema selecionado incorretamente", ""));					
				} else {
					aud.gravaralteracoes(acessousutelasselecionadaslista, acessousutelas.getId_usu(), acessousutelas.getCodigo_sistema());  // Se passar pelo filtro sem encontrar nenhum erro, vai gravar as informações pedidas.
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravação alterada com sucesso!", ""));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();  
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		
	}
	
	public String imprimir(){
		
//		try {
//			
//			AcessoUsuariosDao ad = new AcessoUsuariosDao();
//			
//			String loginusu = ad.RetornaLoginpeloId(acessousutelas.getId_usu());
//			
//			AcessoSistemasDao ns = new AcessoSistemasDao();
//			
//			String nomesistema = ns.RetornanomesistemapeloId(acessousutelas.getId_acessosis());		
//		
//		if ((acessousutelasselecionadaslista.size()==0)) {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nenhum relatório encontrado para imprimir", "")); 
//		} else {
//	
//				DSReportAcessousuTelas ds = new DSReportAcessousuTelas(acessousutelasselecionadaslista , nomesistema, loginusu);
//				
//				InputStream arquivo = FacesContext.getCurrentInstance()
//					.getExternalContext().getResourceAsStream("/AcessoUsuTelas.jasper");	
//	
//				byte[] pdf = JasperRunManager.runReportToPdf(arquivo, null, ds);
//					
//				HttpServletResponse res = (HttpServletResponse) FacesContext
//						.getCurrentInstance().getExternalContext().getResponse();
//					
//				res.setContentType("application/pdf");
//				
//				res.setHeader("Content-Disposition","inline; filename=\"relatorioacessousutelas.pdf\"");
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
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//	
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "")); 
//			}
		return null;
	}
}
	
	
