package persistence;

import java.util.ArrayList;
import java.util.List;

import entity.AcessoTelas;


public class AcessoTelasDao extends Dao{
	
	public List<AcessoTelas> buscar(AcessoTelas tela) throws Exception {
		List<AcessoTelas> lista = new ArrayList<AcessoTelas>();
		
		open();
		
		String sql = "SELECT " +
				 	 "	a.codigo_tela, a.codigo_sistema, a.nome_tela, a.status_tela, b.nome_sistema " +
				 	 "FROM " +
				 	 "	ctrl_telas a, ctrl_sistemas b " +
				 	 "WHERE " +
				 	 "	a.codigo_sistema = b.codigo_sistema";
		
		if (tela.getCodigo_tela()!= null) {
			sql = sql + " and a.codigo_tela = " + tela.getCodigo_tela();
		}
		
		if (tela.getCodigo_sistema()!= null) {
			sql = sql + " and a.codigo_sistema = " + tela.getCodigo_sistema();
		}
		
		if ((tela.getNome_tela()!= null) && (!tela.getNome_tela().equalsIgnoreCase(""))){
			sql = sql + " and a.nome_tela like '%" + tela.getNome_tela() + "%'";				
		}
		
		if ((tela.getStatus_tela()!= null) && (!tela.getStatus_tela().equalsIgnoreCase(""))){
			sql = sql + " and a.status_tela = '" + tela.getStatus_tela() + "'";				
		}
		
		sql = sql + " order by a.codigo_sistema, a.nome_tela";
		
		
		stmt = con.prepareStatement(sql);
		rs = stmt.executeQuery();
	
		while(rs.next()) {
			AcessoTelas a = new AcessoTelas(rs.getInt("codigo_tela"), rs.getInt("codigo_sistema"), 
											rs.getString("nome_tela"), rs.getString("status_tela"),
											rs.getString("nome_sistema"));
		
			lista.add(a);
		}
			
		rs.close();
		close();
		
		return lista;
	}
	
	public List<AcessoTelas> listartelasdosistema(Integer codigosistema) throws Exception {
		List<AcessoTelas> lista = new ArrayList<AcessoTelas>();
		
		open();
		
		String sql = "SELECT " +
				 	 "	a.codigo_tela, a.codigo_sistema, a.nome_tela, a.status_tela, b.nome_sistema " +
				 	 "FROM " +
				 	 "	ctrl_telas a, ctrl_sistemas b " +
				 	 "WHERE " +
				 	 "	a.codigo_sistema = b.codigo_sistema and b.codigo_sistema = " + codigosistema;
		
		sql = sql + " order by a.codigo_sistema, a.nome_tela";		
		
		stmt = con.prepareStatement(sql);
		rs = stmt.executeQuery();
	
		while(rs.next()) {
			AcessoTelas a = new AcessoTelas(rs.getInt("codigo_tela"), rs.getInt("codigo_sistema"), 
											rs.getString("nome_tela"), rs.getString("status_tela"),
											rs.getString("nome_sistema"));
		
			lista.add(a);
		}
			
		rs.close();
		close();
		
		return lista;
	}
	
	public Boolean testacamposduplicados(AcessoTelas at) throws Exception{ 
		Boolean camposduplicados = false; 
		
		open();
		
		String sql = "select count(codigo_tela) as Total from ctrl_telas where nome_tela = ? and codigo_sistema = ? ";
	
		stmt = con.prepareStatement(sql);
		
		stmt.setString(1, at.getNome_tela());
		stmt.setInt(2, at.getCodigo_sistema());
		
		rs = stmt.executeQuery();
		
		if(rs.next()){
			camposduplicados = (rs.getInt("Total")> 0);
		}
		close();
		return camposduplicados;
	}

	public void gravar (AcessoTelas at) throws Exception{
		open();
		
		String sql = "insert into ctrl_telas "+
				     "	(codigo_tela, codigo_sistema, nome_tela, status_tela) " +
				     "values "+
				 	 " (?,?,?,?) ";
	 
		stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, 0);
		stmt.setInt(2, at.getCodigo_sistema());
		stmt.setString(3, at.getNome_tela());
		stmt.setString(4, at.getStatus_tela());
		
		stmt.executeUpdate();
		
		close();		
	}
	

	public Integer excluir(AcessoTelas at) throws Exception{
		Integer registrosalterados = 0;
		
		open();
		
		String sql = "delete from ctrl_telas where codigo_tela = ? ";
		
		stmt = con.prepareStatement(sql);
		
		stmt.setInt(1, at.getCodigo_tela());
		
		registrosalterados = stmt.executeUpdate();
		
		close();
		
		return registrosalterados;
	}
	
	public Integer alterar(AcessoTelas at) throws Exception { 
		Integer registrosalterados = 0;
		
		open();
		
		String sql = "update " +
					 "  ctrl_telas "+
				  	 "set "+
				     "  nome_tela = ?, "+
				     "  status_tela = ? "+
				     "where "+
				     "  codigo_tela = ? ";    
				               
		stmt = con.prepareStatement(sql);
		
		stmt.setString(1, at.getNome_tela());
		stmt.setString(2, at.getStatus_tela());
		stmt.setInt(3, at.getCodigo_tela());
	
		registrosalterados = stmt.executeUpdate();
		
		close();
		
		return registrosalterados;
	}
}