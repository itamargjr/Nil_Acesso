package persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import entity.AcessoSistemas;
import entity.AcessoUsuTelas;

public class AcessoUsuTelasDao extends Dao{
	
	public void gravaralteracoes(List<AcessoUsuTelas> acessousutelaslista, Integer idusu, Integer idsis) throws Exception{   //Criamos a função gravar alteração que tras uma lista de acessoUsuTelas
		open();		
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		Integer usulog = (Integer) session.getAttribute("idusulogado");
		
		java.util.Date data = new java.util.Date();
		
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		
		String dataS = formatador.format(data);
		
		// 1� Excluir os acessos do usuario no sistema
		
		String sql = "delete from "+
				 	 "  ctrl_usuarios_telas "+
				 	 "where "+
				 	 "  id_usu = ? "+
				 	 "and codigo_tela "+
				 	 "	in (select codigo_tela from ctrl_telas where codigo_sistema = ?)";
		
		stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, idusu);
		stmt.setInt(2, idsis);
		
		stmt.executeUpdate();
		
		// 2� Incluir os acessos vindos da lista
		
		for (AcessoUsuTelas acessoUsuTelas : acessousutelaslista) {
			sql = "insert into "+
					  "	ctrl_usuarios_telas "+
					  "	 (id_usutelas, id_usu, codigo_tela, id_usuautorizador, data_acesso) "+
					  "values " +
					  "  (0, ?, ?, ?, ?) ";
				
				stmt = con.prepareStatement(sql);
				
				stmt.setInt(1, idusu);
				stmt.setInt(2, acessoUsuTelas.getCodigo_tela());
				stmt.setInt(3, usulog);
				stmt.setString(4, dataS);
				
				stmt.executeUpdate();
		}
		
		close();
	}
	
	public List<AcessoSistemas> findAll() throws Exception{
		   List<AcessoSistemas> lista = new ArrayList<AcessoSistemas>();
		   
		   open();
		   
		   String sql = "select "+
		   				"	codigo_sistema, nome_sistema, descricao_sistema, datacriacao_sistema "+
		   		        "from "+
		   				"	ctrl_sistemas "+
				        "order by "+
				        " nome_sistema";
		   
		   //System.out.println(sql);
		   
		   stmt = con.prepareStatement(sql);
		   
		   rs = stmt.executeQuery();
		   
		   while (rs.next()) {
			   AcessoSistemas cad = new AcessoSistemas(rs.getInt("codigo_sistema"), rs.getString("nome_sistema"),
					                                   rs.getString("descricao_sistema"), rs.getString("datacriacao_sistema"));
			   
			   lista.add(cad);
		   }
		   close();
		   
		   return lista;      
		
	}
	
	public List<AcessoUsuTelas> consultar(AcessoUsuTelas aut)throws Exception{       //Instanciamos o consultar de uma lista de acessoUsuTelas para chamar ele na tela para fazer a busca, jogando as exceçõe para fora        
		List<AcessoUsuTelas> lista = new ArrayList<AcessoUsuTelas>();               // Instanciamos a lista de AcessoUsoTelas trazendo toda a informação do banco de dados 
		open();
		
		String sql = "select "+                                                     //Criamos uma sql para trazer toda informação que vamos precisar para jogar na tela para o usuário e fazer a ligação.
					 "	a.id_usutelas, a.id_usu, a.codigo_tela, " +
					 "  a.id_usuautorizador, a.data_acesso, " +
					 "  e.login_usu as login_usuautorizador, " +
					 "	b.nome_tela, c.login_usu, d.codigo_sistema, "+
					 "	d.nome_sistema "+ 
					 "from "+
					 "	ctrl_usuarios_telas a, ctrl_telas b, "+
					 "	ctrl_usuarios c, ctrl_sistemas d, ctrl_usuarios e "+
					 "where "+
					 "	a.codigo_tela = b.codigo_tela and "+
					 "  a.id_usu = c.id_usu and "+
					 "  a.id_usuautorizador = e.id_usu and "+
					 "	b.codigo_sistema = d.codigo_sistema ";
			
		if ((aut.getCodigo_sistema()!= null)&&(aut.getCodigo_sistema()>0)) {			  //Fizemos um filtro que se  o id_acessosis for diferente de nulo e maior que zero
			sql = sql + " and d.codigo_sistema = " + aut.getCodigo_sistema();			  // vai passar a informação pedida
		}
		
		if ((aut.getId_usu()!= null)&&(aut.getId_usu()>0)) {						  //Fizemos um filtro que se  o id_usu for diferente de nulo e maior que zero
			sql = sql + " and a.id_usu = " + aut.getId_usu();			              // vai passar a informação pedida    		
		}
		
		if ((aut.getId_usuautorizador()!= null)&&(aut.getId_usuautorizador()>0)) {	  //Fizemos um filtro que se  o id_usu for diferente de nulo e maior que zero
			sql = sql + " and a.id_usuautorizador = " + aut.getId_usuautorizador();	  // vai passar a informação pedida    		
		}
		
		if ((aut.getCodigo_tela()!= null)&&(aut.getCodigo_tela()>0)) {		  //Fizemos um filtro que se  o id_usu for diferente de nulo e maior que zero
			sql = sql + " and a.codigo_tela = " + aut.getCodigo_tela();		  // vai passar a informação pedida    		
		}
		
		if ((aut.getNome_sistema()!= null) &&(!aut.getNome_sistema().equalsIgnoreCase(""))){
			sql = sql + " and d.nome_sistema like '%" + aut.getNome_sistema() + "%'";				
		}
		
		if ((aut.getLogin_usu()!= null) &&(!aut.getLogin_usu().equalsIgnoreCase(""))){
			sql = sql + " and c.login_usu like '%" + aut.getLogin_usu() + "%'";
		}
		
		sql = sql + " order by b.nome_tela";
		
		//System.out.println(sql);
		
		stmt = con.prepareStatement(sql);
		
		rs = stmt.executeQuery();
		
		while(rs.next()) {   
			
			AcessoUsuTelas a = new AcessoUsuTelas(rs.getInt("id_usutelas"), rs.getInt("id_usu"), rs.getInt("codigo_tela"), 
												  rs.getInt("id_usuautorizador"), rs.getString("data_acesso"),
												  rs.getString("login_usuautorizador"), rs.getString("nome_tela"),
												  rs.getString("login_usu"), rs.getInt("codigo_sistema"), 
												  rs.getString("nome_sistema"));
		
			lista.add(a);
		}	

		close();
		return lista;
		
	}
}	