import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class JogoDaVelha {
    
    public static char[][] iniciarTabuleiro() {
        char[][] tabuleiro = new char[3][3];
        for (char[] linha : tabuleiro) Arrays.fill(linha, ' '); // Preenche o tabuleiro com espaços
        return tabuleiro;
    }

    public static void exibirTabuleiro(char[][] tabuleiro) {
        System.out.println("\n====== JOGO DA VELHA ======\n");
        System.out.println("   A   B   C");
        for (int i = 0; i < 3; i++) {
            System.out.print((i + 1) + "  ");
            for (int j = 0; j < 3; j++) {
                if (j < 2) {
                    System.out.print(tabuleiro[i][j] + " | "); // Exibe o conteúdo das células com separadores
                } else {
                    System.out.print(tabuleiro[i][j]); // Exibe o conteúdo da última célula sem separador
                }
            }
            if (i < 2) {
                System.out.println("\n  -----------"); // Exibe uma linha separadora entre as linhas do tabuleiro
            }
        }
        System.out.println();
    }

    // Método para verificar o resultado do jogo
    public static String verificarResultado(char[][] tabuleiro) {
        // Verifica as linhas e colunas para ver se há um vencedor
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][1] == tabuleiro[i][2] && tabuleiro[i][0] != ' ')
                return "O vencedor foi o jogador: " + tabuleiro[i][0] + "!";
            if (tabuleiro[0][i] == tabuleiro[1][i] && tabuleiro[1][i] == tabuleiro[2][i] && tabuleiro[0][i] != ' ')
                return "O vencedor foi o jogador: " + tabuleiro[0][i] + "!";
        }
        // Verifica as diagonais para ver se há um vencedor
        if ((tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2] ||
                tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0]) && tabuleiro[1][1] != ' ')
            return "O vencedor foi o jogador: " + tabuleiro[1][1] + "!";

        // Verifica se há algum espaço vazio no tabuleiro
        for (char[] linha : tabuleiro) {
            for (char c : linha) {
                if (c == ' ') return null; // Se houver espaço vazio, o jogo continua
            }
        }

        return "Essa partida deu empate!"; // Se não houver vencedor e não houver mais espaços, o jogo é empate
    }

    // Método para verificar se a jogada é válida
    public static boolean jogadaValida(String jogada, char[][] tabuleiro) {
        if (jogada.length() != 2 || jogada.charAt(0) < 'A' || jogada.charAt(0) > 'C' || jogada.charAt(1) < '1' || jogada.charAt(1) > '3')
            return false; // Verifica se a entrada está dentro dos limites válidos
        int[] posicao = converterEntrada(jogada);
        return tabuleiro[posicao[0]][posicao[1]] == ' '; // Verifica se a posição está vazia
    }

    // Método para converter a entrada do usuário em índices para o tabuleiro
    public static int[] converterEntrada(String entrada) {
        return new int[]{entrada.charAt(1) - '1', entrada.charAt(0) - 'A'};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean jogarNovamente = true;

        // Laço principal do jogo
        while (jogarNovamente) {
            char[][] tabuleiro = iniciarTabuleiro(); // Inicializa o tabuleiro
            char jogadorAtual = 'X'; // Começa com o jogador X

            // Laço para as jogadas durante o jogo
            while (true) {
                exibirTabuleiro(tabuleiro); // Exibe o tabuleiro
                System.out.println("\nJogador " + jogadorAtual + ", informe sua jogada (Ex: A1):");
                String jogada;

                // Tenta ler a jogada do jogador
                try {
                    jogada = scanner.nextLine().toUpperCase();
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida! Tente novamente.");
                    scanner.next();
                    continue;
                }

                if (!jogadaValida(jogada, tabuleiro)) {
                    System.out.println("Entrada inválida ou posição ocupada! Tente novamente.");
                    continue;
                }

                // Converte a jogada e coloca o símbolo do jogador no tabuleiro
                int[] posicao = converterEntrada(jogada);
                tabuleiro[posicao[0]][posicao[1]] = jogadorAtual;

                String resultado = verificarResultado(tabuleiro);
                if (resultado != null) {
                    exibirTabuleiro(tabuleiro);
                    System.out.println(resultado);
                    break;
                }
                // Troca de jogador
                jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X';
            }

            System.out.println("\nDeseja jogar novamente? (S/N): ");
            String resposta = scanner.nextLine().toUpperCase();
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
