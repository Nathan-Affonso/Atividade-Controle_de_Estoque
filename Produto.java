public class Produto {
    int id,quant;
    String nome,categoria,carga;
    double preco;
    Produto(int id, String nome, String categoria,String carga, int quant, double preco){
        this.id=id;
        this.nome=nome;
        this.categoria=categoria;
        this.quant=quant;
        this.preco=preco;
        this.carga=carga;
    }
}
