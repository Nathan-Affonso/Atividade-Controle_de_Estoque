import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Estoque extends JFrame implements ActionListener {
    JPanel header, body, form,fNome,fQuantidade,fPreco,fCategoria,meio, pCategoria ,pEdit,pCarga;
    JButton cadastro,editar, ver,sub, edit,limp;
    JTextField nome, quant,preco;
    JLabel lNome,lQuant,lPreco,lCategoria;
    JRadioButton pacote,unidade;
    ButtonGroup gr;
    JComboBox<String> categoria;
    JComboBox<Integer> verIds;
    int idEscolhido;
    String[] categorias={"Bebida","Utilitario","Cozinha","Comida","Roupa"};
    String cat=categorias[0];
    ArrayList<Produto> produtos= new ArrayList<>();
    int id=0;
    String nomeAtual;
    double precoAtual;
    int quantAtual;
    String categoriaAtual;
    String carga;
    // checkboxes de filtro por categoria (usados no menu dropdown)
    JCheckBoxMenuItem[] filtros = new JCheckBoxMenuItem[5];
    // quais categorias estao marcadas no filtro (nenhuma marcada = mostra tudo)
    boolean[] filtroAtivo = {false, false, false, false, false};
    Estoque(){
        
        unidade=new JRadioButton("Unidade");
        pacote=new JRadioButton("Pacote");
        unidade.addActionListener(this);
        pacote.addActionListener(this);
        gr = new ButtonGroup();
        gr.add(unidade);
        gr.add(pacote);
        pCarga=new Linha();
        pCarga.add(unidade);
        pCarga.add(pacote);

        cadastro = new Botao("Cadastro");
        cadastro.addActionListener(this);
        editar = new Botao("Editar");
        editar.addActionListener(this);
        ver = new Botao("Ver");
        ver.addActionListener(this);

        edit= new JButton("Editar");
        edit.setForeground(Color.BLACK);
        edit.setBackground(new Color(0x3772FF));
        edit.addActionListener(this);


        sub= new JButton("Cadastrar");
        sub.setForeground(Color.BLACK);
        sub.setBackground(new Color(0x3772FF));
        sub.addActionListener(this);
        
        verIds = new JComboBox<>();
        verIds.addActionListener(this);

        pEdit =new JPanel();
        pEdit.setLayout(new GridLayout(1,2));
        pEdit.add(edit);
        pEdit.add(verIds);

        lNome = new Label("Nome");
        lQuant=new Label("Quantidade");
        lPreco=new Label("Preço");
        lCategoria=new Label("Categoria");

        nome=new Campotext();
        fNome=new Linha();
        fNome.add(lNome);
        fNome.add(nome);

        preco=new Campotext();
        fPreco=new Linha();
        fPreco.add(lPreco);
        fPreco.add(preco);

        quant=new Campotext();
        fQuantidade=new Linha();
        fQuantidade.add(lQuant);
        fQuantidade.add(quant);


        limp= new JButton("Limpar");
        limp.addActionListener(this);

        categoria = new JComboBox<>(categorias);
        categoria.addActionListener(this);
        fCategoria=new Linha();
        fCategoria.add(lCategoria);
        fCategoria.add(categoria);
        fCategoria.add(limp);

        fNome.setBackground(Color.WHITE);
        fPreco.setBackground(Color.WHITE);
        fQuantidade.setBackground(Color.WHITE);
        fCategoria.setBackground(Color.WHITE);
        pCarga.setBackground(Color.WHITE);

        form = new JPanel();
        form.setLayout(new GridLayout(6,1));
        form.add(fNome);
        form.add(fPreco);
        form.add(fQuantidade);
        form.add(fCategoria);
        form.add(pCarga);
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
        this.setLayout(new BorderLayout(20,20));
        this.setTitle("Estoque");
        this.add(header,BorderLayout.NORTH);
        this.add(body,BorderLayout.CENTER);
        this.getContentPane().setBackground(Color.WHITE);
        this.setVisible(true);
    }

        // monta e exibe a lista de produtos com botao de excluir e filtro dropdown
    void mostrarProdutos() {
        body.removeAll();

        // botao pequeno estilo dropdown no canto superior direito para escolher os filtros
        JButton botaoFiltro = new JButton("Filtro ▾");
        botaoFiltro.setFont(new Font(null, Font.PLAIN, 12));
        botaoFiltro.setBackground(new Color(0x3772FF));

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
                    atualizarComboIds();
                    mostrarProdutos();
                     // atualiza a tela apos excluir
                }
            });

            JButton editarBtn = new JButton("Editar");
            editarBtn.setBackground(Color.YELLOW);
            editarBtn.setForeground(Color.BLACK);
            editarBtn.setFont(new Font(null, Font.BOLD, 12));
            editarBtn.addActionListener(ev -> {
            nome.setText(p.nome);
            preco.setText(String.valueOf(p.preco));
            quant.setText(String.valueOf(p.quant));

            categoria.setSelectedItem(p.categoria);

            carga = p.carga; 

            if (p.carga.equals("Unidade")) {
                unidade.setSelected(true);
            } else {
                pacote.setSelected(true);
            }

   
            idEscolhido = p.id;
            verIds.addItem(p.id);
   
             body.removeAll();
            if (sub != null) {
                form.remove(sub);
            }
            form.add(pEdit); 
            body.add(form);

            body.revalidate();
            body.repaint();

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
    void atualizarComboIds() {
        verIds.removeAllItems();
        for (Produto p : produtos) {
            verIds.addItem(p.id);
        }
    }
    void limpar(){
        nome.setText("");
        preco.setText("");
        gr.clearSelection();
        quant.setText("");
        categoria.setSelectedIndex(0);
        cat = categoria.getSelectedItem().toString();
        carga="";
        verIds.addItem(0);
        atualizarComboIds();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==cadastro){
            body.removeAll();
            if (pEdit != null) {
                form.remove(pEdit);
            }
            limpar();
            form.add(sub);
            body.add(form,BorderLayout.CENTER);
            body.revalidate();
            body.repaint();
        }
        if(e.getSource()==categoria){
            cat = categoria.getSelectedItem().toString();
        }
        if (e.getSource() == unidade) {
            carga = "Unidade";
        }

        if (e.getSource() == pacote) {
            carga = "Pacote";
        }
        if(e.getSource()==sub){
            String name = nome.getText();
            String dou = preco.getText();
            String qua =quant.getText();

            int podeIr =0;
            int quanti=0;
            double price=0;
            if(qua.equals("") || dou.equals("")|| name.equals("")|| carga.equals("")){
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
                nome.setText("");
                preco.setText("");
                gr.clearSelection();
                quant.setText("");
                categoria.setSelectedIndex(0);
                produtos.add(new Produto(id,name,cat,carga,quanti,price));
                cat = categoria.getSelectedItem().toString();
                carga="";
                id+=1;
                atualizarComboIds();
            }
        }

        if(e.getSource()==edit){
            String name = nome.getText();
            String dou = preco.getText();
            String qua =quant.getText();

            int podeIr =0;
            int quanti=0;
            double price=0;
            if(qua.equals("") || dou.equals("")|| name.equals("")|| carga.equals("")){
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
                nome.setText("");
                preco.setText("");
                gr.clearSelection();
                quant.setText("");
                categoria.setSelectedIndex(0);
                idEscolhido = (Integer) verIds.getSelectedItem();
                for (Produto p : produtos) {
                    if (p.id == idEscolhido) {
                       p.nome = name;
                       p.preco = price;
                       p.quant = quanti;
                       p.categoria = cat;
                       p.carga = carga;
                    }
                }
                cat = categoria.getSelectedItem().toString();
                carga="";
            }
        }if (e.getSource()==limp){
            limpar();
        }
        

        if (e.getSource()==editar){
            body.removeAll();
            if (sub != null) {
                form.remove(sub);
            }
            limpar();
            form.add(pEdit);
            body.add(form,BorderLayout.CENTER);
            body.revalidate();
            body.repaint();
        }
        if (e.getSource()==ver){
            mostrarProdutos();
        }
    }

}
