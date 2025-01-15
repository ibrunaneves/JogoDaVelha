import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JogoDaVelha {

    public static char[][] iniciarTabuleiro() {
        char[][] tabuleiro = new char[3][3]; // Cria uma matriz 3x3 para o tabuleiro
        for (char[] linha : tabuleiro) Arrays.fill(linha, ' '); // Preenche a matriz com espaços em branco
        return tabuleiro; // Retorna o tabuleiro inicializado
    }

    public static void exibirTabuleiro(char[][] tabuleiro) {
        System.out.println("\n====== JOGO DA VELHA ======\n");
        System.out.println("   A   B   C");
        for (int i = 0; i < 3; i++) { // Laço para percorrer as linhas do tabuleiro
            System.out.print((i + 1) + "  "); // Exibe o número da linha
            for (int j = 0; j < 3; j++) { // Laço para percorrer as colunas de cada linha
                if (j < 2) {
                    System.out.print(tabuleiro[i][j] + " | "); // Exibe os valores da linha e coloca um separador
                } else {
                    System.out.print(tabuleiro[i][j]); // Exibe o valor da última coluna sem o separador
                }
            }
            if (i < 2) { // Coloca uma linha de separação entre as linhas do tabuleiro
                System.out.println("\n  -----------");
            }
        }
        System.out.println();
    }

    // Função para verificar se há um vencedor ou se o jogo deu empate
    public static String verificarResultado(char[][] tabuleiro) {
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][1] == tabuleiro[i][2] && tabuleiro[i][0] != ' ')
                return "O vencedor foi o jogador: " + tabuleiro[i][0] + "!"; // Verifica se há vencedor nas linhas
            if (tabuleiro[0][i] == tabuleiro[1][i] && tabuleiro[1][i] == tabuleiro[2][i] && tabuleiro[0][i] != ' ')
                return "O vencedor foi o jogador: " + tabuleiro[0][i] + "!"; // Verifica se há vencedor nas colunas
        }
        if ((tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2] ||
                tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0]) && tabuleiro[1][1] != ' ')
            return "O vencedor foi o jogador: " + tabuleiro[1][1] + "!"; // Verifica as diagonais

        for (char[] linha : tabuleiro) // Verifica se ainda há espaços vazios no tabuleiro
            for (char c : linha)
                if (c == ' ') return null;

        return "Essa partida deu empate!"; // Caso não haja vencedor e o tabuleiro esteja cheio
    }

    // Função para validar a jogada feita pelo jogador
    public static boolean jogadaValida(String jogada, char[][] tabuleiro) {
        if (jogada.length() != 2 || jogada.charAt(0) < 'A' || jogada.charAt(0) > 'C' || jogada.charAt(1) < '1' || jogada.charAt(1) > '3')
            return false; // Verifica se a jogada está no formato correto
        int[] posicao = converterEntrada(jogada); // Converte a entrada para índices de linha e coluna
        return tabuleiro[posicao[0]][posicao[1]] == ' '; // Verifica se a posição está livre
    }

    // Função para converter a entrada de jogada (ex: A1) para índices de linha e coluna
    public static int[] converterEntrada(String entrada) {
        return new int[]{entrada.charAt(1) - '1', entrada.charAt(0) - 'A'}; // Converte para índices de linha e coluna
    }

    // Função principal que controla o fluxo do jogo
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean jogarNovamente = true;

        while (jogarNovamente) { // Loop principal do jogo, permitindo jogar novamente
            char[][] tabuleiro = iniciarTabuleiro(); // Inicializa o tabuleiro
            char jogadorAtual = 'X'; // Define o jogador inicial

            while (true) { // Loop para as jogadas do jogo
                exibirTabuleiro(tabuleiro); // Exibe o tabuleiro atualizado
                System.out.println("\nJogador " + jogadorAtual + ", informe sua jogada (Ex: A1):");
                String jogada;

                try {
                    jogada = scanner.nextLine().toUpperCase(); // Lê a jogada do jogador e converte para maiúsculas
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida! Tente novamente."); // Trata exceções de entrada inválida
                    scanner.next();
                    continue;
                }

                if (!jogadaValida(jogada, tabuleiro)) {
                    System.out.println("Entrada inválida ou posição ocupada! Tente novamente."); // Informa jogada inválida
                    continue;
                }

                int[] posicao = converterEntrada(jogada); // Converte a jogada para posição no tabuleiro
                tabuleiro[posicao[0]][posicao[1]] = jogadorAtual; // Marca a posição no tabuleiro com o símbolo do jogador

                String resultado = verificarResultado(tabuleiro); // Verifica se o jogo terminou
                if (resultado != null) {
                    exibirTabuleiro(tabuleiro); // Exibe o tabuleiro final
                    System.out.println(resultado); // Exibe o resultado do jogo (vencedor ou empate)
                    break;
                }
                jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X'; // Alterna entre os jogadores
            }

            System.out.println("\nDeseja jogar novamente? (S/N): ");
            String resposta = scanner.nextLine().toUpperCase(); // Pergunta se o jogador quer jogar novamente
            while (!resposta.equals("S") && !resposta.equals("N")) {
                System.out.println("Resposta inválida! Digite 'S' para Sim ou 'N' para Não.");
                resposta = scanner.nextLine().toUpperCase();
            }
            jogarNovamente = resposta.equals("S");
        }
        System.out.println("Obrigada por jogar!");
        scanner.close();
    }
}
