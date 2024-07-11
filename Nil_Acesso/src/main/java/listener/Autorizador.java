package listener;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import manager.AcessoBean;

/**
 * Classe que "escuta" o 
 * ciclo do JSF e implementa
 * o evento que captura a navegacao entre 
 * paginas do projeto para impedir um
 * usuario nao logado de acessar o
 * sistema 
 * 
 * @author itamar
 *
 */

public class Autorizador implements PhaseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7165802048370933572L;

	@Override
	public void afterPhase(PhaseEvent event) {
		
		FacesContext context = event.getFacesContext();
		
		/**
		 * Se a pagina a ser acessada e o index.xhtml
		 * nada e feito, ja que esta e a pagina de login
		 */
		
		if ("/index.xhtml".equals(context.getViewRoot().getViewId())) {
			return;
		}
		
		/**
		 * Instancializo o acessobean pq la esta a 
		 * referencia se o usuario ja esta logado
		 * ou nao.
		 * 
		 * Isto e registrado no metodo booleano
		 * isLogado()
		 */
		
		AcessoBean acessoBean = context.getApplication().evaluateExpressionGet(context, "#{acessoBean}", AcessoBean.class);
		
		/**
		 * Se nao estiver logado, volta pro index.xhtml
		 */
		
		if (!acessoBean.isLogado()) {
			NavigationHandler handler = context.getApplication().getNavigationHandler();
			
			handler.handleNavigation(context, null, "index?faces-redirect=true");
			
			context.renderResponse();
		}		
	}

	@Override
	public void beforePhase(PhaseEvent arg0) {
		
	}

	@Override
	public PhaseId getPhaseId() {
		
		return PhaseId.RESTORE_VIEW;
	}
}
