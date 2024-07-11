package persistence;

import java.util.ArrayList;

import java.util.List;

import entity.AcessoSistemas;

	public class AcessoSistemasDao extends Dao {
	
	public Boolean testacamposduplicados(AcessoSistemas as)throws Exception{
		Boolean camposduplicados = false;
		
		open();
		
		String sql = "select count(codigo_sistema) as Total from ctrl_sistemas where nome_sistema = ?";
	
		stmt = con.prepareStatement(sql);
		
		stmt.setString(1, as.getNome_sistema());
		
		rs = stmt.executeQuery();
		
		if(rs.next()){
			camposduplicados = (rs.getInt("Total")> 0);		
		}
		close();
	
		return camposduplicados;
	}

	
   public List<AcessoSistemas> findAll() throws Exception{
	   List<AcessoSistemas> lista = new ArrayList<AcessoSistemas>();
	   
	   open();
	   
	   String sql = " select codigo_sistema, nome_sistema, descricao_sistema, datacriacao_sistema "+
	   		        " from ctrl_sistemas "+
			        " order by nome_sistema ";
	   
	   stmt = con.prepareStatement(sql);
	   
	   rs = stmt.executeQuery();
	   
	   while (rs.next()) {
		   AcessoSistemas as = new AcessoSistemas(rs.getInt("codigo_sistema"), rs.getString("nome_sistema"), 
				   								  rs.getString("descricao_sistema"), rs.getString("datacriacao_sistema"));
		   
		   lista.add(as);
	   }
	   close();
	   
	   return lista;      
	}
   
   	public void gravar(AcessoSistemas as) throws Exception{
   
   	open();
   	
   	String sql = " insert into ctrl_sistemas "+ 
   				 "	(codigo_sistema, nome_sistema, descricao_sistema, datacriacao_sistema ) "+
   				 "values "+
   				 " 	(?, ?, ?, ?)";
   	
   	stmt = con.prepareStatement(sql);
   	
   	stmt.setInt(1, 0);
   	stmt.setString(2, as.getNome_sistema());
   	stmt.setString(3, as.getDescricao_sistema());
   	stmt.setString(4, as.getDatacriacao_sistema());
   	stmt.executeUpdate();
	
	close();
   	
   	}   
   	
   	public Integer excluir(AcessoSistemas as) throws Exception{
		Integer registrosalterados = 0;
		
		open();
		
		String sql = "delete from ctrl_sistemas where codigo_sistema = ? ";
		
		stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, as.getCodigo_sistema());
		
		registrosalterados = stmt.executeUpdate();
		
		close();
		
		return registrosalterados;
   	}
   	
   	public Integer alterar(AcessoSistemas as) throws Exception { 
		Integer registrosalterados = 0;
		
		open();
		
		String sql = "update "+
				 	 "  ctrl_sistemas "+
					 "set "+
					 "  nome_sistema = ?, "+
					 "  descricao_sistema = ? "+
					 "where "+
					 "  codigo_sistema = ? ";
		
		stmt = con.prepareStatement(sql);
		
		stmt.setString(1, as.getNome_sistema());
		stmt.setString(2, as.getDescricao_sistema());
		stmt.setInt(3, as.getCodigo_sistema());
		
		registrosalterados = stmt.executeUpdate();
		
		close();
		
		return registrosalterados;
   	}
   	
   	public List<AcessoSistemas> consultar(AcessoSistemas as)throws Exception{               
		List<AcessoSistemas> lista = new ArrayList<AcessoSistemas>();
		
		open();
		
		String sql = "select codigo_sistema, nome_sistema, descricao_sistema, datacriacao_sistema " +                                   
			         "from ctrl_sistemas where 1 = 1 ";					 
		
		if (as.getCodigo_sistema()!= null) {			
			sql = sql + " and codigo_sistema = " + as.getCodigo_sistema();			          
		} else if ((as.getNome_sistema()!= null) && (!as.getNome_sistema().equalsIgnoreCase(""))){
			sql = sql + " and nome_sistema like '%" + as.getNome_sistema() + "%'";
		}
			
		sql = sql + "order by nome_sistema";
		
		stmt = con.prepareStatement(sql);
		
		rs = stmt.executeQuery();
		
		while(rs.next()) {																	
			
			AcessoSistemas a = new AcessoSistemas(rs.getInt("codigo_sistema"), rs.getString("nome_sistema"), 
						  						  rs.getString("descricao_sistema"), rs.getString("datacriacao_sistema"));     
			
			lista.add(a);
		}
		
		close();
		
		return lista;	
	}
   	
   	public String RetornanomesistemapeloId(Integer id)throws Exception{
		String nomesis = "";
		
		open();
		
		String sql = "select nome_sistema from ctrl_sistemas where codigo_sistema = ?";
		
		stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, id);
		
		rs = stmt.executeQuery();
		
		if (rs.next()) {
			nomesis =  rs.getString("nome_sistema");
		}
		
		close();
		
		return nomesis;
	}
}	
	
