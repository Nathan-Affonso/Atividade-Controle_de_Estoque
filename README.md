# Controle de Estoque

Sistema simples de controle de estoque feito em **Java** com **Java Swing**, criado para fins de estudo.

## O que o sistema faz

A aplicação é dividida em três abas, acessadas pelos botões no menu superior:

### Cadastro
Permite registrar um novo produto, informando:
- Nome
- Preço
- Quantidade
- Categoria (Bebida, Utilitario, Cozinha, Comida ou Roupa)

Ao clicar em "cadastrar", o sistema valida se todos os campos foram preenchidos corretamente e adiciona o produto à lista.

### Editar
Reservado para futuras funcionalidades de edição geral do sistema.

### Ver
Exibe a lista de todos os produtos cadastrados, mostrando id, nome, categoria, quantidade e preço.

Nessa tela existem dois botões em cada produto:
- **Editar** (amarelo): abre uma janela com os dados do produto preenchidos, pronta para receber a lógica de edição (ainda não implementada).
- **Excluir** (vermelho): remove o produto da lista, pedindo confirmação antes de apagar.

Também há um pequeno botão **"Filtro ▾"** no canto superior direito, que abre um menu com checkboxes de categorias. É possível marcar várias categorias ao mesmo tempo para filtrar os produtos exibidos. Se nenhuma categoria estiver marcada, todos os produtos são mostrados.

## Como executar

1. Compile os arquivos:
```bash
javac *.java
```

2. Execute o programa:
```bash
java Main
```

## Estrutura dos arquivos

- `Main.java` - ponto de entrada da aplicação.
- `Estoque.java` - tela principal, com toda a interface gráfica e a lógica do sistema.
- `Produto.java` - classe que representa um produto (id, nome, categoria, quantidade e preço).

## Observação

Os produtos ficam armazenados apenas em memória. Ao fechar o programa, todos os dados cadastrados são perdidos.
