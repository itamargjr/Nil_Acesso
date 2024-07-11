package entity;

public class AcessoUsuTelas {
	private Integer id_usutelas;
	private Integer id_usu;
	private Integer codigo_tela;	
	private Integer id_usuautorizador;
	private String data_acesso;
	
	private String login_usuautorizador;
	
	private String nome_tela;
	private String login_usu;
	private Integer codigo_sistema;
	private String nome_sistema;
	
	public AcessoUsuTelas() {
		super();
	}

	public AcessoUsuTelas(Integer id_usutelas, Integer id_usu, Integer codigo_tela, Integer id_usuautorizador,
			String data_acesso, String login_usuautorizador, String nome_tela, String login_usu, Integer codigo_sistema,
			String nome_sistema) {
		super();
		this.id_usutelas = id_usutelas;
		this.id_usu = id_usu;
		this.codigo_tela = codigo_tela;
		this.id_usuautorizador = id_usuautorizador;
		this.data_acesso = data_acesso;
		this.login_usuautorizador = login_usuautorizador;
		this.nome_tela = nome_tela;
		this.login_usu = login_usu;
		this.codigo_sistema = codigo_sistema;
		this.nome_sistema = nome_sistema;
	}

	public AcessoUsuTelas(Integer codigo_tela, String nome_tela, Integer codigo_sistema) {
		super();
		this.codigo_tela = codigo_tela;
		this.nome_tela = nome_tela;
		this.codigo_sistema = codigo_sistema;
	}

	public String getLogin_usuautorizador() {
		return login_usuautorizador;
	}

	public void setLogin_usuautorizador(String login_usuautorizador) {
		this.login_usuautorizador = login_usuautorizador;
	}

	public Integer getId_usutelas() {
		return id_usutelas;
	}

	public void setId_usutelas(Integer id_usutelas) {
		this.id_usutelas = id_usutelas;
	}

	public Integer getId_usu() {
		return id_usu;
	}

	public void setId_usu(Integer id_usu) {
		this.id_usu = id_usu;
	}

	public Integer getCodigo_tela() {
		return codigo_tela;
	}

	public void setCodigo_tela(Integer codigo_tela) {
		this.codigo_tela = codigo_tela;
	}

	public Integer getId_usuautorizador() {
		return id_usuautorizador;
	}

	public void setId_usuautorizador(Integer id_usuautorizador) {
		this.id_usuautorizador = id_usuautorizador;
	}

	public String getData_acesso() {
		return data_acesso;
	}

	public void setData_acesso(String data_acesso) {
		this.data_acesso = data_acesso;
	}

	public String getNome_tela() {
		return nome_tela;
	}

	public void setNome_tela(String nome_tela) {
		this.nome_tela = nome_tela;
	}

	public String getLogin_usu() {
		return login_usu;
	}

	public void setLogin_usu(String login_usu) {
		this.login_usu = login_usu;
	}

	public Integer getCodigo_sistema() {
		return codigo_sistema;
	}

	public void setCodigo_sistema(Integer codigo_sistema) {
		this.codigo_sistema = codigo_sistema;
	}

	public String getNome_sistema() {
		return nome_sistema;
	}

	public void setNome_sistema(String nome_sistema) {
		this.nome_sistema = nome_sistema;
	}

	@Override
	public String toString() {
		return "AcessoUsuTelas [id_usutelas=" + id_usutelas + ", id_usu=" + id_usu + ", codigo_tela=" + codigo_tela
				+ ", id_usuautorizador=" + id_usuautorizador + ", data_acesso=" + data_acesso
				+ ", login_usuautorizador=" + login_usuautorizador + ", nome_tela=" + nome_tela + ", login_usu="
				+ login_usu + ", codigo_sistema=" + codigo_sistema + ", nome_sistema=" + nome_sistema + "]";
	}
}
