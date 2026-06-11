import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Estoque extends JFrame implements ActionListener {
    JPanel header, body, form,fNome,fQuantidade,fPreco,fCategoria,meio, pCategoria ,pSub;
    JButton cadastro,editar, ver,sub;
    JTextField nome, quant,preco;
    JLabel lNome,lQuant,lPreco,lCategoria;
    JComboBox<String> categoria;
    String cat="";
    String[] categorias={"Bebida","Utilitario","Cozinha","Comida","Roupa"};
    ArrayList<Produto> produtos= new ArrayList<>();
    int id=0;

    // checkboxes de filtro por categoria (usados no menu dropdown)
    JCheckBoxMenuItem[] filtros = new JCheckBoxMenuItem[5];
    // quais categorias estao marcadas no filtro (nenhuma marcada = mostra tudo)
    boolean[] filtroAtivo = {false, false, false, false, false};

    Estoque(){

        cadastro = new JButton("Cadastro");
        cadastro.setFont(new Font(null,Font.BOLD,20));
        cadastro.setForeground(Color.BLACK);
        cadastro.addActionListener(this);
        cadastro.setBorderPainted(false);
        cadastro.setContentAreaFilled(false);
        cadastro.setFocusPainted(false);
        cadastro.setOpaque(false);
        cadastro.setPreferredSize(new Dimension(0,50));

        editar = new JButton("Editar");
        editar.setFont(new Font(null,Font.BOLD,20));
        editar.setForeground(Color.BLACK);
        editar.addActionListener(this);
        editar.setBorderPainted(false);
        editar.setContentAreaFilled(false);
        editar.setFocusPainted(false);
        editar.setOpaque(false);
        editar.setPreferredSize(new Dimension(0,50));


        ver = new JButton("Ver");
        ver.setFont(new Font(null,Font.BOLD,20));
        ver.setForeground(Color.BLACK);
        ver.addActionListener(this);
        ver.setBorderPainted(false);
        ver.setContentAreaFilled(false);
        ver.setFocusPainted(false);
        ver.setOpaque(false);
        ver.setPreferredSize(new Dimension(0,50));

        sub= new JButton("cadastrar");
        sub.setForeground(Color.BLACK);
        sub.setBackground(new Color(0x97EC86));
        sub.addActionListener(this);


        lNome=new JLabel("Nome");
        lNome.setFont(new Font(null,Font.PLAIN,16));
        lNome.setForeground(Color.BLACK);
        lQuant=new JLabel("Quantidade");
        lQuant.setFont(new Font(null,Font.PLAIN,16));
        lQuant.setForeground(Color.BLACK);
        lPreco=new JLabel("Preço");
        lPreco.setFont(new Font(null,Font.PLAIN,16));
        lPreco.setForeground(Color.BLACK);
        lCategoria=new JLabel("Categoria");
        lCategoria.setFont(new Font(null,Font.PLAIN,16));
        lCategoria.setForeground(Color.BLACK);

        nome=new JTextField();
        nome.setPreferredSize(new Dimension(200,30));
        nome.setFont(new Font(null,Font.PLAIN,20));
        fNome=new JPanel();
        fNome.setLayout(new FlowLayout(FlowLayout.LEFT,50,0));
        fNome.add(lNome);
        fNome.add(nome);

        preco=new JTextField();
        preco.setPreferredSize(new Dimension(200,30));
        preco.setFont(new Font(null,Font.PLAIN,20));
        fPreco=new JPanel();
        fPreco.setLayout(new FlowLayout(FlowLayout.LEFT,50,0));
        fPreco.add(lPreco);
        fPreco.add(preco);

        quant=new JTextField();
        quant.setPreferredSize(new Dimension(200,30));
        quant.setFont(new Font(null,Font.PLAIN,20));
        fQuantidade=new JPanel();
        fQuantidade.setLayout(new FlowLayout(FlowLayout.LEFT,50,0));
        fQuantidade.add(lQuant);
        fQuantidade.add(quant);


        categoria = new JComboBox<>(categorias);
        categoria.addActionListener(this);
        fCategoria=new JPanel();
        fCategoria.setLayout(new FlowLayout(FlowLayout.LEFT,50,0));
        fCategoria.add(lCategoria);
        fCategoria.add(categoria);

        form = new JPanel();
        form.setLayout(new GridLayout(5,1));
        form.add(fNome);
        form.add(fPreco);
        form.add(fQuantidade);
        form.add(fCategoria);
        form.add(sub);
        form.setPreferredSize(new Dimension(300,600));


        header = new JPanel();
        header.setPreferredSize(new Dimension(0,70));
        header.setBackground(Color.BLUE);
        header.setLayout(new GridLayout(1,3));
        header.add(cadastro);
        header.add(editar);
        header.add(ver);


        body = new JPanel();
        body.setBackground(Color.WHITE);
        body.setLayout(new BorderLayout());


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,600);
        this.setLayout(new BorderLayout(10,10));
        this.setTitle("Estoque");
        this.add(header,BorderLayout.NORTH);
        this.add(body,BorderLayout.CENTER);

        this.setVisible(true);
    }

    // monta e exibe a lista de produtos com botao de excluir e filtro dropdown
    void mostrarProdutos() {
        body.removeAll();

        // botao pequeno estilo dropdown no canto superior direito para escolher os filtros
        JButton botaoFiltro = new JButton("Filtro ▾");
        botaoFiltro.setFont(new Font(null, Font.PLAIN, 12));
        botaoFiltro.setBackground(new Color(0x97EC86));

        JPopupMenu menuFiltro = new JPopupMenu();
        for (int i = 0; i < categorias.length; i++) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem(categorias[i]);
            item.setSelected(filtroAtivo[i]);
            final int indiceCat = i;
            item.addActionListener(ev -> {
                filtroAtivo[indiceCat] = item.isSelected();
                mostrarProdutos();
            });
            filtros[i] = item;
            menuFiltro.add(item);
        }
        botaoFiltro.addActionListener(ev -> menuFiltro.show(botaoFiltro, 0, botaoFiltro.getHeight()));

        JPanel topo = new JPanel();
        topo.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topo.setBackground(Color.WHITE);
        topo.add(botaoFiltro);
        body.add(topo, BorderLayout.NORTH);

        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBackground(Color.WHITE);

        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);

            // se nenhum filtro estiver marcado, exibe todos os produtos
            boolean algumFiltroAtivo = false;
            for (int j = 0; j < filtroAtivo.length; j++) {
                if (filtroAtivo[j]) algumFiltroAtivo = true;
            }

            boolean exibir = !algumFiltroAtivo;
            if (algumFiltroAtivo) {
                for (int j = 0; j < categorias.length; j++) {
                    if (categorias[j].equals(p.categoria) && filtroAtivo[j]) {
                        exibir = true;
                        break;
                    }
                }
            }
            if (!exibir) continue;

            final int indice = i;

            JPanel linha = new JPanel();
            linha.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
            linha.setBackground(Color.WHITE);
            linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

            JLabel info = new JLabel(p.id + " | " + p.nome + " | " + p.categoria + " | Qtd: " + p.quant + " | R$" + String.format("%.2f", p.preco));
            info.setFont(new Font(null, Font.PLAIN, 14));
            info.setForeground(Color.BLACK);

            JButton excluir = new JButton("Excluir");
            excluir.setBackground(new Color(0xE05555));
            excluir.setForeground(Color.WHITE);
            excluir.setFont(new Font(null, Font.BOLD, 12));
            excluir.addActionListener(ev -> {
                int confirmacao = JOptionPane.showConfirmDialog(null,
                    "Excluir o produto '" + p.nome + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) {
                    produtos.remove(indice);
                    mostrarProdutos(); // atualiza a tela apos excluir
                }
            });

            JButton editarBtn = new JButton("Editar");
            editarBtn.setBackground(Color.YELLOW);
            editarBtn.setForeground(Color.BLACK);
            editarBtn.setFont(new Font(null, Font.BOLD, 12));
            editarBtn.addActionListener(ev -> {
                JTextField campoNome = new JTextField(p.nome);
                JTextField campoPreco = new JTextField(String.valueOf(p.preco));
                JTextField campoQuant = new JTextField(String.valueOf(p.quant));
                JComboBox<String> campoCategoria = new JComboBox<>(categorias);
                campoCategoria.setSelectedItem(p.categoria);

                JPanel painel = new JPanel(new GridLayout(4, 2, 5, 5));
                painel.add(new JLabel("Nome:"));
                painel.add(campoNome);
                painel.add(new JLabel("Preço:"));
                painel.add(campoPreco);
                painel.add(new JLabel("Quantidade:"));
                painel.add(campoQuant);
                painel.add(new JLabel("Categoria:"));
                painel.add(campoCategoria);

                JOptionPane.showConfirmDialog(null, painel, "Editar Produto", JOptionPane.OK_CANCEL_OPTION);

                // TODO: implementar a logica de salvar as alteracoes
            });

            linha.add(info);
            linha.add(editarBtn);
            linha.add(excluir);
            lista.add(linha);
        }

        if (lista.getComponentCount() == 0) {
            JLabel vazio = new JLabel("  Nenhum produto encontrado.");
            vazio.setFont(new Font(null, Font.PLAIN, 16));
            vazio.setForeground(Color.BLACK);
            lista.add(vazio);
        }

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBackground(Color.WHITE);
        scroll.getViewport().setBackground(Color.WHITE);

        body.add(scroll, BorderLayout.CENTER);
        body.revalidate();
        body.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==cadastro){
            body.removeAll();
            body.add(form,BorderLayout.CENTER);
            body.revalidate();
            body.repaint();
        }
        if(e.getSource()==categoria){
            cat = categoria.getSelectedItem().toString();
        }
        if(e.getSource()==sub){
            String name = nome.getText();
            String dou = preco.getText();
            String qua =quant.getText();

            int podeIr =0;
            int quanti=0;
            double price=0;
            if(qua.equals("") || dou.equals("")|| name.equals("")|| cat.equals("")){
                JOptionPane.showMessageDialog(null,"Campos vazios","Incompleto",JOptionPane.ERROR_MESSAGE);
            }
            else{
            try {
                quanti= Integer.parseInt(qua);
                podeIr+=1;
            } catch (Exception ex) {
                quant.setText("");
                JOptionPane.showMessageDialog(null,"digite um numero inteiro no campo quantidade","tipo errado",JOptionPane.ERROR_MESSAGE);
            }
            try {
                dou = dou.replace(',','.');
                price =Double.parseDouble(dou);
                podeIr+=1;
            } catch (Exception ex) {
                preco.setText("");
                JOptionPane.showMessageDialog(null,"digite um numero flutuante no campo preço","tipo errado",JOptionPane.ERROR_MESSAGE);
            }}

            if (podeIr==2){
                String catSalva = cat;
                nome.setText("");
                preco.setText("");
                quant.setText("");
                cat="";
                produtos.add(new Produto(id,name,catSalva,quanti,price));
                id+=1;
            }
        }
        if (e.getSource()==ver){
            mostrarProdutos();
        }
    }

}
