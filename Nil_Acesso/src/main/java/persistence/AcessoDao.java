package persistence;

import java.util.ArrayList;
import java.util.List;

import entity.Ctrl_usuarios;

public class AcessoDao extends Dao {
	public Ctrl_usuarios login(Ctrl_usuarios usu)throws Exception{
		
		Ctrl_usuarios u = new Ctrl_usuarios();
		
		open();
		
		stmt = con.prepareStatement("select " +
							   		"  id_usu, cpf_usu, login_usu, nome_usu, datanasc_usu, tipo_usu, senha_usu, status_usu " +
							   		"FROM " +
							   		"  ctrl_usuarios " + 
							   		"where upper(login_usu) = ? and upper(senha_usu) = ?");
		
		stmt.setString(1, usu.getLogin_usu().toUpperCase());
		stmt.setString(2, usu.getSenha_usu().toUpperCase());
		
		rs = stmt.executeQuery();
		
		if (rs.next()) {
			
			u = new Ctrl_usuarios(rs.getInt("id_usu"),rs.getString("cpf_usu"),rs.getString("login_usu"),
						 rs.getString("nome_usu"), rs.getString("datanasc_usu"),rs.getString("tipo_usu"), 
						 rs.getString("senha_usu"),	rs.getString("status_usu"));
			
		}
		
		return u;
		
	}
	
	public String validaItemUsuario (Integer usu, Integer item) throws Exception{
		
		String sql, disable = "true"; // disabled do menu = true. Desabilitado

		open();
		
		sql = "select count(codigo_tela) as acesso"
		    + " from ctrl_usuarios_telas"
		    + " where id_usu = " + usu
		    + " and codigo_tela = " + item;
		
		stmt = con.prepareStatement(sql);

		rs = stmt.executeQuery();
		
		while (rs.next()) { // usuario tem acesso, passo false para o "disabled". Menu fica habilitado
			if (rs.getInt("acesso")== 0) {
				disable = "false";
			}
		};

		close();

		return disable;
	}
	
	public List<Integer> ResgataAcessos(Integer IdUsu, Integer IdSistema) throws Exception {
		List<Integer> acessoslista = new ArrayList<Integer>();
		
		open();
		
		stmt = con.prepareStatement("select " +
		                            "  a.codigo_tela " +
				                    "from " +
		                            "  ctrl_usuarios_telas a, ctrl_telas b " +
				                    "where " +
		                            "  a.codigo_tela = b.codigo_tela and " +
				                    "  b.codigo_sistema = ? and " +
		                            "  a.id_usu = ?");
		
		stmt.setInt(1, IdSistema);
		stmt.setInt(2, IdUsu);
		
		rs = stmt.executeQuery();
		
		while (rs.next()) {
			acessoslista.add(rs.getInt("codigo_tela"));
		}
		
		close();
		
		return acessoslista;
	}
}
