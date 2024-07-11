package entity;

public class AcessoSistemas {

	private Integer codigo_sistema;
	private String nome_sistema;
	private String descricao_sistema;
	private String datacriacao_sistema;

	public AcessoSistemas() {
		super();		
	}

	public AcessoSistemas(Integer codigo_sistema, String nome_sistema, String descricao_sistema,
			String datacriacao_sistema) {
		super();
		this.codigo_sistema = codigo_sistema;
		this.nome_sistema = nome_sistema;
		this.descricao_sistema = descricao_sistema;
		this.datacriacao_sistema = datacriacao_sistema;
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

	public String getDescricao_sistema() {
		return descricao_sistema;
	}

	public void setDescricao_sistema(String descricao_sistema) {
		this.descricao_sistema = descricao_sistema;
	}

	public String getDatacriacao_sistema() {
		return datacriacao_sistema;
	}

	public void setDatacriacao_sistema(String datacriacao_sistema) {
		this.datacriacao_sistema = datacriacao_sistema;
	}

	@Override
	public String toString() {
		return "AcessoSistemas [codigo_sistema=" + codigo_sistema + ", nome_sistema=" + nome_sistema
				+ ", descricao_sistema=" + descricao_sistema + ", datacriacao_sistema=" + datacriacao_sistema + "]";
	}
}

