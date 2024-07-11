package entity;

	public class AcessoTelas {
		
		private Integer codigo_tela;
		private Integer codigo_sistema;
		private String nome_tela;
		private String status_tela;		
		
		private String nome_sistema;
		
		public AcessoTelas () {
			super();		
		}

		public AcessoTelas(Integer codigo_tela, Integer codigo_sistema, String nome_tela, String status_tela,
				String nome_sistema) {
			super();
			this.codigo_tela = codigo_tela;
			this.codigo_sistema = codigo_sistema;
			this.nome_tela = nome_tela;
			this.status_tela = status_tela;
			this.nome_sistema = nome_sistema;
		}

		public Integer getCodigo_tela() {
			return codigo_tela;
		}

		public void setCodigo_tela(Integer codigo_tela) {
			this.codigo_tela = codigo_tela;
		}

		public Integer getCodigo_sistema() {
			return codigo_sistema;
		}

		public void setCodigo_sistema(Integer codigo_sistema) {
			this.codigo_sistema = codigo_sistema;
		}

		public String getNome_tela() {
			return nome_tela;
		}

		public void setNome_tela(String nome_tela) {
			this.nome_tela = nome_tela;
		}

		public String getStatus_tela() {
			return status_tela;
		}

		public void setStatus_tela(String status_tela) {
			this.status_tela = status_tela;
		}

		public String getNome_sistema() {
			return nome_sistema;
		}

		public void setNome_sistema(String nome_sistema) {
			this.nome_sistema = nome_sistema;
		}

		@Override
		public String toString() {
			return "AcessoTelas [codigo_tela=" + codigo_tela + ", codigo_sistema=" + codigo_sistema + ", nome_tela="
					+ nome_tela + ", status_tela=" + status_tela + ", nome_sistema=" + nome_sistema + "]";
		}
}