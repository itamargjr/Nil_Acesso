package persistence;

import java.util.ArrayList;
import java.util.List;
import entity.Ctrl_usuarios;

public class Ctrl_usuariosDao extends Dao {

	public Ctrl_usuarios RetornaUsuario(Integer usu) throws Exception{
		Ctrl_usuarios usuario =  new Ctrl_usuarios();
		
		open();
		
		String statement = "select " +
						   "  id_usu, cpf_usu, login_usu, nome_usu, datanasc_usu, tipo_usu, senha_usu, status_usu " +
						   "FROM " +
						   "  ctrl_usuarios " + 
						   "where "+
                           "  id_usu = " + usu;
		
		stmt = con.prepareStatement(statement);
		
		rs = stmt.executeQuery();
				
		if (rs.next()) {					
			
			usuario = new Ctrl_usuarios(rs.getInt("id_usu"),rs.getString("cpf_usu"),rs.getString("login_usu"),
						 rs.getString("nome_usu"), rs.getString("datanasc_usu"),rs.getString("tipo_usu"), 
						 rs.getString("senha_usu"),	rs.getString("status_usu"));
		}
		
		close();
		
		return usuario;
	}
	
	public List<Ctrl_usuarios> findAll()throws Exception{
		List<Ctrl_usuarios> lista = new ArrayList<Ctrl_usuarios>();
		   
		   open();
		   
		   String sql = "select " +
				   		"  id_usu, cpf_usu, login_usu, nome_usu, datanasc_usu, tipo_usu, senha_usu, status_usu " +
				   		"FROM " +
				   		"  ctrl_usuarios " + 
				   		"where "+
				   		" status_usu = 'A' "+
		   				"order by login_usu asc ";
		   
		   stmt = con.prepareStatement(sql);
		   
		   rs = stmt.executeQuery();
		   		   
		   while (rs.next()) {
			   
			   Ctrl_usuarios aud = new Ctrl_usuarios(rs.getInt("id_usu"),rs.getString("cpf_usu"),rs.getString("login_usu"),
					   								 rs.getString("nome_usu"), rs.getString("datanasc_usu"),rs.getString("tipo_usu"), 
					   								 rs.getString("senha_usu"),	rs.getString("status_usu"));
			   lista.add(aud);
			   
	}
	
	   close();
	   
	   return lista;      
	
	}
	
	public Boolean testacamposduplicados(Ctrl_usuarios usu)throws Exception{                    
		Boolean camposduplicados = false;                                                      
		
		open();																					
		
		String sql = "select count(id_usu) as num from ctrl_usuarios where login_usu = ? or cpf_usu = ? or nome_usu = ?";
	
		stmt = con.prepareStatement(sql);                            
		
		stmt.setString(1, usu.getLogin_usu());						 
		stmt.setString(2, usu.getCpf_usu());
		stmt.setString(3, usu.getNome_usu());
		
		rs = stmt.executeQuery();									 
		
		if(rs.next()){       
			camposduplicados = (rs.getInt("num")> 0);       
		}	
		close();											
		
		return camposduplicados; 							
	}
	
	public void gravar(Ctrl_usuarios usu)throws Exception{  
		open();
		
		String sql = "insert into ctrl_usuarios " +
				     "  (id_usu, cpf_usu, login_usu, nome_usu, datanasc_usu, tipo_usu, senha_usu, status_usu ) " +
			         "values  " +
			         "  (?, ?, ?, ?, ?, ?, ?, ?) ";
		
		stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, 0);
		stmt.setString(2,usu.getCpf_usu());
		stmt.setString(3,usu.getLogin_usu());
		stmt.setString(4,usu.getNome_usu()); 
		stmt.setString(5,usu.getDatanasc_usu());
		stmt.setString(6,usu.getTipo_usu());
		stmt.setString(7,usu.getSenha_usu());		
		stmt.setString(8,usu.getStatus_usu());
		
		stmt.executeUpdate();
		
		close();
	}
	
	public Integer excluir(Ctrl_usuarios usu) throws Exception{
		Integer registrosalterados = 0;
		
		open();
		
		String sql = "delete from ctrl_usuarios where id_usu = ? ";
		
		stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, usu.getId_usu());
		
		registrosalterados = stmt.executeUpdate();
		
		close();
		
		return registrosalterados;
	}
	
	public Integer alterar(Ctrl_usuarios usu) throws Exception { 
		Integer registrosalterados = 0;
		
		open();
		
		String sql = "update " +
		             "  ctrl_usuarios " +
		             "set " +
		             "  cpf_usu = ?, " +
		             "  login_usu = ?, " +
		             "  nome_usu = ?, " +
		             "  datanasc_usu = ?, " +
		             "  tipo_usu = ?, " +
		             "  senha_usu = ?, " +
		             "  status_usu = ? " +
		             "where " +
		             "  id_usu = ? ";
		
		stmt = con.prepareStatement(sql);
				
		stmt.setString(1, usu.getCpf_usu());
		stmt.setString(2, usu.getLogin_usu());
		stmt.setString(3, usu.getNome_usu()); 
		stmt.setString(4,usu.getDatanasc_usu());
		stmt.setString(5, usu.getTipo_usu());
		stmt.setString(6, usu.getSenha_usu());		
		stmt.setString(7,usu.getStatus_usu());
		stmt.setInt(8, usu.getId_usu());
		
		registrosalterados = stmt.executeUpdate();
		
		close();
		
		return registrosalterados;
		
	}
	
	public List<Ctrl_usuarios> consultar(Ctrl_usuarios usu)throws Exception{ 
		List<Ctrl_usuarios> lista = new ArrayList<Ctrl_usuarios>();
		open();
		
		String sql ="select " +
			   		"  id_usu, cpf_usu, login_usu, nome_usu, datanasc_usu, tipo_usu, senha_usu, status_usu " +
			   		"FROM " +
			   		"  ctrl_usuarios " + 
			   		"where 1=1 ";
					
					
		if ((usu.getCpf_usu()!= null) && (!usu.getCpf_usu().equalsIgnoreCase("")) ){		
			sql = sql + " and cpf_usu = '" + usu.getCpf_usu() + "'";			                  
		}
		
		if ((usu.getLogin_usu()!= null) && (!usu.getLogin_usu().equalsIgnoreCase("")) ){
			sql = sql + " and login_usu like '" + usu.getLogin_usu() + "'";
		}
		
		if ((usu.getNome_usu()!= null) &&(!usu.getNome_usu().equalsIgnoreCase(""))) {
			sql = sql + " and nome_usu like '%" + usu.getNome_usu() + "%'";
		}
		
		sql = sql + " order by login_usu";
		
		stmt = con.prepareStatement(sql);
		
		rs = stmt.executeQuery();
		
		while(rs.next()) {
			
			Ctrl_usuarios a = new Ctrl_usuarios(rs.getInt("id_usu"),rs.getString("cpf_usu"),rs.getString("login_usu"),
										        rs.getString("nome_usu"), rs.getString("datanasc_usu"),rs.getString("tipo_usu"), 
										        rs.getString("senha_usu"),	rs.getString("status_usu"));
			lista.add(a);
			
		}
		
		close();
		return lista;
	}
	
	public String RetornaLoginpeloId(Integer id)throws Exception{
		String login = "-";
		
		open();
		
		String sql = "select login_usu from ctrl_usuarios where id_usu = ?";
		
		stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, id);
		
		rs = stmt.executeQuery();
		
		if (rs.next()) {
			login =  rs.getString("login_usu");
		}
		
		close();
		
		return login;
	}
}