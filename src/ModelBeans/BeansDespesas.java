package ModelBeans;

public class BeansDespesas extends BeansTransacao{

    private int id_transacao;
   private int id_user_despesa;
    private String categoria;
    private String descricao;
    private String forma_pagamento;
    private String status;
    private String pesquisa;

    public int getId_user_despesa() {
        return id_user_despesa;
    }

    public void setId_user_despesa(int id_user_despesa) {
        this.id_user_despesa = id_user_despesa;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }
    

    public int getId_transacao() {
        return id_transacao;
    }

    public void setId_transacao(int id_transacao) {
        this.id_transacao = id_transacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getForma_pagamento() {
        return forma_pagamento;
    }

    public void setForma_pagamento(String forma_pagamento) {
        this.forma_pagamento = forma_pagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}
