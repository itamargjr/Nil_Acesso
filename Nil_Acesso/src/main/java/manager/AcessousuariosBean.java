
package manager; 

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import entity.Ctrl_usuarios;
import persistence.Ctrl_usuariosDao;

@ManagedBean                                        
@ViewScoped                                        
public class AcessousuariosBean {
	
	private Ctrl_usuarios acessousuarios;      
	private Ctrl_usuarios acessousuariosalteracao;
	
	
	private List<Ctrl_usuarios> acessousuarioslista;
	private List<Ctrl_usuarios> acessousuariosfindlista;

	public AcessousuariosBean() {                 
		acessousuarios = new Ctrl_usuarios();  
		acessousuariosalteracao = new Ctrl_usuarios();
		
		acessousuarioslista = new ArrayList<Ctrl_usuarios>();
		acessousuariosfindlista = new ArrayList<Ctrl_usuarios>();
		
		try { 			
			Ctrl_usuariosDao aud = new Ctrl_usuariosDao();
			acessousuariosfindlista = aud.findAll();
		} catch (Exception e) {             
			e.printStackTrace();            
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "")); 
		}
	}

	public Ctrl_usuarios getAcessousuariosalteracao() {
		return acessousuariosalteracao;
	}

	public void setAcessousuariosalteracao(Ctrl_usuarios acessousuariosalteracao) {
		this.acessousuariosalteracao = acessousuariosalteracao;
	}

	public List<Ctrl_usuarios> getAcessousuarioslista() {
		return acessousuarioslista;
	}

	public void setAcessousuarioslista(List<Ctrl_usuarios> acessousuarioslista) {
		this.acessousuarioslista = acessousuarioslista;
	}

	public Ctrl_usuarios getAcessousuarios() {
		return acessousuarios;
	}

	public void setAcessousuarios(Ctrl_usuarios acessousuarios) {
		this.acessousuarios = acessousuarios;
	}

	public List<Ctrl_usuarios> getAcessousuariosfindlista() {
		return acessousuariosfindlista;
	}

	public void setAcessousuariosfindlista(List<Ctrl_usuarios> acessousuariosfindlista) {
		this.acessousuariosfindlista = acessousuariosfindlista;
	}

	public void gravar() {
		
		//System.out.println(acessousuarios);
		
		Ctrl_usuariosDao aud = new Ctrl_usuariosDao();         
																
		Boolean camposduplicados = false;                       
		
		try {
			camposduplicados = aud.testacamposduplicados(acessousuarios);
			
			if (camposduplicados) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Voce informou login ou codigo duplicado", ""));
			} else {
				
				aud.gravar(acessousuarios);		
				
				acessousuarios = new Ctrl_usuarios();
				
				PrimeFaces.current().executeScript("PF('dialogocadastrar').hide();");
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Gravacao efetuada", ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}
	
	public void buscar() {
		
		Ctrl_usuariosDao aud = new Ctrl_usuariosDao();
		
		try {
			acessousuarioslista = aud.consultar(acessousuarios);
			
		} catch (Exception e) {
			e.printStackTrace();  
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}
	
	public void abredialogoalterar() {
		
		acessousuarioslista.remove(acessousuariosalteracao);
		
		PrimeFaces.current().executeScript("PF('dialogoalterar').show();");
	}
	
	public void abredialogoexclusao() {
		PrimeFaces.current().executeScript("PF('dialogoexcluir').show();");
	}
	
	public void abrirdialogocadastrar() {
		acessousuarios = new Ctrl_usuarios();  
		
		PrimeFaces.current().executeScript("PF('dialogocadastrar').show();");
	}
	
	public void alterar() {
		Ctrl_usuariosDao aud = new Ctrl_usuariosDao();
		
		try {		
			
			acessousuarioslista.add(acessousuariosalteracao);
						
			Integer registrosalterados = aud.alterar(acessousuariosalteracao);
		
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, registrosalterados + " Usuario alterado!", ""));

			PrimeFaces.current().executeScript("PF('dialogoalterar').hide();");
		
			acessousuarios = new Ctrl_usuarios();
			
		} catch (Exception e) {
			e.printStackTrace();  
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}
	
	public void excluir() {
		Ctrl_usuariosDao aud = new Ctrl_usuariosDao();
		
		try {
			
			Integer registrosalterados = aud.excluir(acessousuariosalteracao);
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, registrosalterados + " Usuario excluido!", ""));
			
			PrimeFaces.current().executeScript("PF('dialogoexcluir').hide();");
			
			acessousuarioslista.remove(acessousuariosalteracao);
			
		} catch (Exception e) {
			e.printStackTrace();  
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
	}
}