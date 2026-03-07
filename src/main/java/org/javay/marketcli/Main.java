package org.javay.marketcli;
    // os imports
import org.javay.marketcli.model.Bebida;
import org.javay.marketcli.model.Comida;
import org.javay.marketcli.model.Produto;
import org.javay.marketcli.service.ProdutoService;

import java.util.Collection;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ProdutoService service = new ProdutoService();

    public static void main(String[] args) {
        boolean rodando = true;

        System.out.println("=====================================");
        System.out.println("   Bem-vindo ao Sistema Forja CLI");
        System.out.println("=====================================");

        while (rodando) { // o switch case com o trycatch do menu
            exibirMenu();
            int opcao = lerInteiro("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1:
                        cadastrarProduto();
                        break;
                    case 2:
                        listarTodos();
                        break;
                    case 3:
                        atualizarProduto();
                        break;
                    case 4:
                        removerProduto();
                        break;
                    case 5:
                        buscarPorFiltro();
                        break;
                    case 6:
                        gerarRelatorioNegocio();
                        break;
                    case 0:
                        System.out.println("Encerrando o sistema!!");
                        rodando = false;
                        break;
                    default:
                        System.out.println("Opção inválida, Tente novamente!!");
                }
            } catch (IllegalArgumentException e) { // os catch de validações e pa
                System.out.println("\n[ERRO DE VALIDAÇÃO]: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n[ERRO INESPERADO]: Ocorreu um erro na operacao.");
            }
        }
        scanner.close();
    }

    private static void exibirMenu() { // o menuzin bonito com as opc
        System.out.println("\n=======================================");
        System.out.println("|           FORJA PRINCIPAL           |");
        System.out.println("=======================================");
        System.out.println("| 1 | Cadastrar Produtos              |");
        System.out.println("| 2 | Listar Todos os Produtos        |");
        System.out.println("| 3 | Atualizar Produtos              |");
        System.out.println("| 4 | Remover Produtos                |");
        System.out.println("| 5 | Buscar Produtos por Setor       |");
        System.out.println("| 6 | Relatório de Estoque Geral      |");
        System.out.println("---------------------------------------");
        System.out.println("| 0 | Sair                            |");
        System.out.println("=======================================");
    }

    // os metodos do crud

    private static void cadastrarProduto() { // usado para cadastrar os prod
        System.out.println("\n--- CADASTRAR PRODUTO ---");
        Produto novoProduto = coletarDadosProduto();
        if (novoProduto != null) {
            service.addProduto(novoProduto);
            System.out.println("Produto '" + novoProduto.getNome() + "' cadastrado com sucesso!");
        }
    }

    private static void listarTodos() { // usado para listar os produtos
        System.out.println("\n--- LISTA DE PRODUTOS ---");
        Collection<Produto> produtos = service.getAllProdutos(); // pega todos os produtos e lista
        imprimirListaProdutos(produtos);
    }

    private static void atualizarProduto() {// usado para atulizar os produtos
        System.out.println("\n--- ATUALIZAR PRODUTO ---");
        String nome = lerString("Digite o nome exato do produto que será atualizado: ");
        if (!service.hasProduto(nome)) {
            System.out.println("Produto não encontrado no sistema."); // verifica se tem prod, se nn fala q nn foi encontraado
            return;
        }

        System.out.println("Insira os novos dados para o produto:");
        Produto produtoAtualizado = coletarDadosProduto(); // atualiza os dados

        if (produtoAtualizado != null) { // se for diferente de nulo ele seta as alterações
            produtoAtualizado.setNome(nome);
            service.updateProduto(nome, produtoAtualizado);
            System.out.println("Produto atualizado com sucesso!");
        }
    }

    private static void removerProduto() { // usado para remover os prod
        System.out.println("\n--- REMOVER PRODUTO ---");
        String nome = lerString("Digite o nome exato do produto para remove-lo: ");

        if (service.hasProduto(nome)) { //puxa o produto e remove se nn encontrar da um erro de prod nn encontrado
            service.removeProduto(nome);
            System.out.println("Produto removido com sucesso!!");
        } else {
            System.out.println("Produto não encontrado!!");
        }
    }

    private static void buscarPorFiltro() { // busca prod por setor
        System.out.println("\n--- BUSCAR POR FILTRO (SETOR) ---");

        String setorBuscado = escolherSetor(); // chamo ali o menuu de escolher o setor

        Collection<Produto> filtrados = service.findProdutosByFilter( // cria uma collection chamada fitlrados, que puxa uma função do service, essa collection vai guardar so os prod q passar pelo filtro
                p -> p.getSetor().equalsIgnoreCase(setorBuscado) // ele pega o p (variavel q o java da uma olhada, so uma variavel btf?) ele pega o setor q vc selecionou(getsetor) e compara com o setor buscado(criada ali em cima), usei o equalsignorecase pq ele ignora maisucula e minuscula
        );

        if (filtrados.isEmpty()) {
            System.out.println("\nNenhum produto encontrado no setor: " + setorBuscado);
        } else {
            System.out.println("\nExibindo produtos do setor: " + setorBuscado);
            imprimirListaProdutos(filtrados);
        }
    }

    private static void gerarRelatorioNegocio() { // faz o relatório
        System.out.println("\n--- RELATÓRIO FORJA ---");
        Collection<Produto> produtos = service.getAllProdutos(); // pega todos os prod

        if (produtos.isEmpty()) { // ve se tem estoque se nn tiver retorna q nn tem dados pra fazer o relatorio
            System.out.println("O estoque está vazio. Não há dados para o relatório.");
            return;
        }

        int totalItensEstoque = 0; // cria umas variaveis zeradas
        double valorTotalEstoque = 0.0;
        int totalBebidas = 0;
        int totalComidas = 0;

        for (Produto p : produtos) { //faz um for pra passar os prod
            totalItensEstoque += p.getQuantidade(); // pega a quantidade que tem, se tem 10 sushi ele soma em totalItensEstoque, ai vem 5 temaki ele soma e fica 15 no total
            valorTotalEstoque += (p.getPreco() * p.getQuantidade()); // msm fita porém calculando o valor, ele pega o preço dos prod e faz * a qntd

            if (p instanceof Bebida) totalBebidas++; // o instanceof verifica se é bebida, se for do tipo bebida jog pra bebida
            if (p instanceof Comida) totalComidas++; // msm fita de cima
        }
        // aqui ele mostra as paradas
        System.out.println("Quantidade de Produtos Únicos Cadastrados: " + produtos.size());
        System.out.println("Total de itens físicos em estoque: " + totalItensEstoque);
        System.out.printf("Valor total do estoque: R$ %.2f\n", valorTotalEstoque);
        System.out.println("Divisão: " + totalComidas + " tipo(s) de Comida e " + totalBebidas + " tipo(s) de Bebida.");
    }

    private static String escolherSetor() { // menu pra tu escolher o setor do q tu quer
        System.out.println("\n=======================================");
        System.out.println("|            SETORES FORJA            |");
        System.out.println("=======================================");
        System.out.println("| 1 | Águas                           |");
        System.out.println("| 2 | Energéticos                     |");
        System.out.println("| 3 | Drinks e Bebidas                |");
        System.out.println("| 4 | Sushis                          |");
        System.out.println("| 5 | Outros                          |");
        System.out.println("=======================================");

        while (true) {
            int opcao = lerInteiro("Escolha o número do setor (1 a 5): "); // chama a variavel ler inteiro para ler apenas inteiros e faz o switchcase sem trycatch
            switch (opcao) {
                case 1: return "Aguas";
                case 2: return "Energeticos";
                case 3: return "Drinks e Bebidas Alcoolicas";
                case 4: return "Sushis";
                case 5: return "Outros";
                default: System.out.println("[ERRO] Opção inválida. Escolha um número de 1 a 5."); // se nn for dentre 1 a 5 delimitado cai nesse erro
            }
        }
    }

    private static Produto coletarDadosProduto() { // coletar os dados dos produtos para adicionar
        int tipo = lerInteiro("Qual tipo de produto deseja inserir? (1 - Comida | 2 - Bebida): "); // joga pra bebida ou comida
        if (tipo != 1 && tipo != 2) { // faz uma verificação
            System.out.println("Inválido!! Operação cancelada!!");
            return null;
        }

        String setor = escolherSetor(); // chama alguns metodos pra facilitar
        String nome = lerString("Nome: ");
        double preco = lerDouble("Preço (ex: 10,50): ");
        int quantidade = lerInteiro("Qtd em estoque: ");

        if (tipo == 1) { // se for comida faz as perguntas
            boolean isVegano = lerBoolean("É vegano? (S/N): ");
            double peso = lerDouble("Peso em gramas: ");
            int caloria = lerInteiro("Calorias: ");
            boolean temLactose = lerBoolean("Tem lactose? (S/N): ");
            boolean isAsiatica = lerBoolean("É comida asiatica? (S/N): ");
            boolean isSobremesa = lerBoolean("É sobremesa? (S/N): ");

            return new Comida(setor, nome, preco, quantidade, isVegano, peso, caloria, temLactose, isAsiatica, isSobremesa); //retorna tudo coletado para comida
        } else { // ja se for bebida
            boolean isAlcoolica = lerBoolean("É alcoolica? (S/N): ");
            int ml = lerInteiro("Volume em ml: ");
            String marca = lerString("Marca: ");

            return new Bebida(setor, nome, preco, quantidade, isAlcoolica, ml, marca); //retorna tudo coletado para bebidas
        }
    }

    private static void imprimirListaProdutos(Collection<Produto> produtos) { //pra mostrar os produtos
        if (produtos.isEmpty()) { // verifica se nn ta vazio
            System.out.println("Nenhum produto cadastrado."); // se tiver ele informa se nn passa
            return;
        }
        for (Produto p : produtos) { // for de produto por produto
            System.out.println("-----------------------------------");
            System.out.printf("Produto: %s | Setor: %s | Preço: R$%.2f | Estoque: %d un.\n", p.getNome(), p.getSetor(), p.getPreco(), p.getQuantidade()); // mostra

            if (p instanceof Bebida bebida) { // se o prod for bebida mostra os detalhes da bebida se for comida mostra os detalhes da comida
                System.out.println("Detalhes Específicos: " + bebida.toString());
            } else if (p instanceof Comida comida) {
                System.out.println("Detalhes Específicos: " + comida.toString() + " | Peso: " + comida.getPeso() + "g | Calorias: " + comida.getCaloria() + "kcal");
            }
        }
        System.out.println("-----------------------------------");
    }

    // fiz umas entradas aqui pra garantir que não vai dar erro, só entra se for do tipo selecionado, se tiver ler int so vai passar se for int btf? (melhor coisa que ja fiz namoral)

    private static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    private static int lerInteiro(String mensagem) {
        while (true) { // loop infinito ate q o usu digite oq pede, aqui é int
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Por favor, digite um número inteiro válido."); // so vai ler se for do tipo int
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) { // aqui pe double
            try {
                System.out.print(mensagem);
                String entrada = scanner.nextLine().trim().replace(",", "."); // aqui é complicado mas é facil, ele pega tudo que o usu digitou, tira espaçamento e troca a , por .
                return Double.parseDouble(entrada); //transofoma em double, caso a pessoa digite 10 apenas sem ser quebrado
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Por favor, digite um número decimal válido (ex: 10.50 ou 10,50).");
            }
        }
    }

    private static boolean lerBoolean(String mensagem) {
        while (true) { // aqui é boolean
            String entrada = lerString(mensagem).toUpperCase(); // taquei um to upper case pra nn fazer torcentas verificações e so tacar tudo pro maiusculo
            if (entrada.equals("S") || entrada.equals("SIM")) return true; // aq verifica se o usu digitar s ou sim passa, se nn não
            if (entrada.equals("N") || entrada.equals("NAO") || entrada.equals("NÃO")) return false; // msm coisa so que com não, e com uma a mais sem o acento
            System.out.println("[ERRO] Digite apenas 'S' para Sim ou 'N' para Não."); //erro caso digite fora disso
        }
    }
}