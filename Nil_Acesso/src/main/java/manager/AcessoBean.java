package manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import entity.Ctrl_usuarios;
import persistence.AcessoDao;
import persistence.Dao;

/**
 * Bean de controle de acesso.
 * Todas as funcoes relativas
 * ao acesso do sistema
 * 
 * @author itamar
 *
 */

@ManagedBean
@SessionScoped
public class AcessoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8268179366139876532L;

	public Ctrl_usuarios usuarios; 
	
	private Boolean renderizarBotaocadastrarUsuario;
	private Boolean renderizarBotaoAtualizarUsuario;
	private Boolean renderizarBotaoExcluirUsuario;
	
	private Integer id_escola = 0;
	
	private List<Ctrl_usuarios> usuarioslista;
	private List<String> acessosmenulista;
	
	/* Funcao que mostra em qual banco a aplicacao esta conectada e retorna a String */
	public String banco(){
		
		String cam;
		Dao db = new Dao();
		
		cam = db.caminho(); 

		return cam.substring(cam.length()-18, cam.length());
	}
	
	/* Funcao que mostra o usuario logado */
	public String usuariologado(){
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true); 
		
		if (session.getAttribute("loginusulogado")==null) {
			return "";
		} else {
			return session.getAttribute("loginusulogado").toString();

		}
		
	}
	
	public AcessoBean() {
		usuarios = new Ctrl_usuarios();
		
		usuarioslista = new ArrayList<Ctrl_usuarios>();
		acessosmenulista = new ArrayList<String>();
		
		renderizarBotaocadastrarUsuario = false;
		renderizarBotaoAtualizarUsuario = false;
		renderizarBotaoExcluirUsuario = false;
	}

	public Integer getId_escola() {
		return id_escola;
	}

	public void setId_escola(Integer id_escola) {
		this.id_escola = id_escola;
	}

	public List<String> getAcessosmenulista() {
		return acessosmenulista;
	}

	public void setAcessosmenulista(List<String> acessosmenulista) {
		this.acessosmenulista = acessosmenulista;
	}

	public Boolean getRenderizarBotaocadastrarUsuario() {
		return renderizarBotaocadastrarUsuario;
	}

	public void setRenderizarBotaocadastrarUsuario(Boolean renderizarBotaocadastrarUsuario) {
		this.renderizarBotaocadastrarUsuario = renderizarBotaocadastrarUsuario;
	}

	public Boolean getRenderizarBotaoAtualizarUsuario() {
		return renderizarBotaoAtualizarUsuario;
	}

	public void setRenderizarBotaoAtualizarUsuario(Boolean renderizarBotaoAtualizarUsuario) {
		this.renderizarBotaoAtualizarUsuario = renderizarBotaoAtualizarUsuario;
	}

	public Boolean getRenderizarBotaoExcluirUsuario() {
		return renderizarBotaoExcluirUsuario;
	}

	public void setRenderizarBotaoExcluirUsuario(Boolean renderizarBotaoExcluirUsuario) {
		this.renderizarBotaoExcluirUsuario = renderizarBotaoExcluirUsuario;
	}

	public List<Ctrl_usuarios> getUsuarioslista() {
		return usuarioslista;
	}

	public void setUsuarioslista(List<Ctrl_usuarios> usuarioslista) {
		this.usuarioslista = usuarioslista;
	}

	public Ctrl_usuarios getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Ctrl_usuarios usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * Funcao de acesso ao sistema.
	 * recebe login e senha do 
	 * usuario e critica a 
	 * existencia
	 * 
	 */

	public String login(){
		
		AcessoDao ad = new AcessoDao();
		
		List<Integer> acessoslista = new ArrayList<Integer>();
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		try {
			Ctrl_usuarios usuariologado = ad.login(usuarios);
			
			if (usuariologado.getLogin_usu()==null) {						
				FacesMessage msg = new FacesMessage("Login nao efetuado", "Usuario inexistente, ou senha invalida!");
				
				FacesContext.getCurrentInstance().addMessage(null, msg);
				
				session.setAttribute("idusulogado", 0);
				session.setAttribute("acessoslista", acessoslista);
				
				return null;
			}else {
				
				usuarios = new Ctrl_usuarios();
				
				session.setAttribute("loginusulogado", usuariologado.getLogin_usu());
				session.setAttribute("idusulogado", usuariologado.getId_usu());
				
				acessoslista = ad.ResgataAcessos(usuariologado.getId_usu(), 3); // 3 e o ID do CENTRO DE TERAPIAS na tabela ctrl_sistemas
				
				if (usuariologado.getTipo_usu().equalsIgnoreCase("A")) {
					session.setAttribute("usuadm", "S");
				}
				
				return "principal.xhtml?faces-redirect=true";
			}
			
		} catch (Exception e) {
			e.printStackTrace();

			FacesMessage msg = new FacesMessage("Erro ao efetuar login",e.getMessage());
				
			FacesContext.getCurrentInstance().addMessage(null, msg);			
					
			session.setAttribute("idusulogado", 0);
			
			return null;
		}
		
	}
	
	/* Funcao que mostra nome da escola escolhida no login */
	public String Nomeescola(){

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true); 
		
		if (session.getAttribute("nomeescola")==null) {
			return "";
		} else {
			return session.getAttribute("nomeescola").toString();

		}
	}
	
	/* Funcao que mostra ID da escola escolhida no login */
	public String Idescola(){

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true); 
		
		if (session.getAttribute("idescola")==null) {
			return "";
		} else {
			return session.getAttribute("idescola").toString();

		}
	}
	
	/**
	 * Funcao de saida do sistema
	 * que limpa os dados da sessao
	 * e redireciona o usuario para a 
	 * tela de login
	 * 
	 */

	public String logout(){
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		session.setAttribute("idusulogado", 0);
		
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "index.xhtml?faces-redirect=true";
		
	}
	
	/**
	 * Funcao para confirmar se
	 * existe algum usuario logado
	 * 
	 * A logica e: se o atributo
	 * login_usu da classe acessousuarios
	 * nao esta nulo, entao ele esta
	 * logado 
	 * 
	 */
	
	public boolean isLogado(){
		//return usuarios.getLogin_usu() != null;
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Integer usulog = (Integer) session.getAttribute("idusulogado");
		
		//System.out.println(usulog);
		
		//System.out.println("usulog:" + usulog);
		
		if (usulog !=null) {
			if (usulog ==0) {
				usulog = null;
			}
		}
		
		//System.out.println(usulog);
		
		return usulog !=null;
	}
	
	
	public String validausu(Integer item) throws Exception{
		// alterar o AcessoBean para guardar tambe�m a variavel de sessao usuadm - login()   
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Integer usulog = (Integer) session.getAttribute("idusulogado");
		String adm = (String) session.getAttribute("usuadm");
		
		String desabilita;
		
		if (item==0) {
			desabilita = "false"; // Falta implementar/nao tem controle de acesso no XHTML - habilita tudo
		} else if ((adm != null) && (adm.equals("S"))) 		 		
			desabilita = "false"; // Administrador - habilita tudo
		else{			
			AcessoDao valida = new AcessoDao();
			desabilita = valida.validaItemUsuario(usulog, item);
		}

		return desabilita;	
			
	}

}
