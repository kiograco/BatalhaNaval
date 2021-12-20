package com.company.EstudoJava.BatalhaNaval;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;


public class Main {
    static final int VAZIO = 0;
    static final int NAVIO = 1;
    static final int ERROU = 2;
    static final int ACERTOU = 3;
    static final int POSICAO_X = 0;
    static final int POSICAO_Y = 1;

    static int naviosJogador1, naviosJogador2;
    static String player1, player2;
    static int sideX , sideY, quantidadeNavios, limiteNavios;
    static int[][] mapaJogador1 = distribuidorDeNaviosJogadores(),mapaJogador2;
    static Scanner sc = new Scanner(System.in);


    public static void tamanhoMapa() {

        for(;;) {
            boolean informacoesCorretas = false;
            try {
                sc = new Scanner(System.in);
                System.out.println("Digite a largura do tabuleiro: ");
                sideX = sc.nextInt();
                System.out.println("Digite a altura do tabuleiro: ");
                sideY = sc.nextInt();
                informacoesCorretas = true;

            } catch (InputMismatchException erro) {
                System.out.println("digite um valor numerico");
            }
            if(informacoesCorretas){

                break;
            }
        }

    }
    public static void calcularLimiteNavios(){
        limiteNavios = ( sideX * sideY ) / 3;
    }
    public static  int[][] iniciarMapaVazio(){
        return  new int[sideX][sideY];
    }
    public static void iniciandoMapas(){
        mapaJogador1 = iniciarMapaVazio();
        mapaJogador2 = iniciarMapaVazio();
    }

    public static void obterQuantidadeDeNavios(){
        System.out.println("Digite a quantida de navios: ");
        System.out.println("limite maximo de navios e " + limiteNavios);
        quantidadeNavios = sc.nextInt();
        while (quantidadeNavios > limiteNavios || quantidadeNavios < 1){
            System.out.println("Voce ultrapassou o limite de "+ limiteNavios +" ou digitou menor que 1, digite novamente");
            quantidadeNavios = sc.nextInt();
        }
    }
    public static int[][] distribuidorDeNaviosJogadores(){
        int[][] novoMapa = iniciarMapaVazio();
        int quantidadeRestanteDeNavios = quantidadeNavios;
        int x= 0, y =0;
        Random rand = new Random();
        do{
            x = 0;
            y = 0;
            for (int[] linha : novoMapa) {
                for(int coluna : linha){
                    if(rand.nextInt(100) <= 10 ){
                        if(coluna == VAZIO){
                            novoMapa [x] [y] = NAVIO;
                            quantidadeRestanteDeNavios --;
                            break;
                        }
                        if(quantidadeRestanteDeNavios <= 0){
                            break;
                        }
                    }
                    y++;
                }
                y = 0;
                x++;
                if(quantidadeRestanteDeNavios <= 0){
                    break;
                }
            }

    }while (quantidadeRestanteDeNavios > 0);
        return novoMapa;
}
    public static void inserirNaviosNosMapaDosJogadores(){
        mapaJogador1 = distribuidorDeNaviosJogadores();
        mapaJogador2 = distribuidorDeNaviosJogadores();
    }

    public static void obterNomeJogadores(){
        System.out.println("Digite o nome do jogador 1");
        player1 = sc.next();
        System.out.println("Digite o nome do jogador 2");
        player2 = sc.next();
    }

    public static void montarTabuleiro(){
        exibirTabuleiro(player1,mapaJogador1, true);
        exibirTabuleiro(player2, mapaJogador2, false);
    }

    public static void exibirNumerosDasColunasDosTabuleiros(){
        int numeroDaColuna = 1;
        String numerosDoTabuleiro = "   ";
        for (int i = 0; i < sideY ; i++) {
            numerosDoTabuleiro += numeroDaColuna++ + " ";

        }
        System.out.println(numerosDoTabuleiro);
    }
    public static void exibirTabuleiro(String nomeDoJogador, int[][] tabuleiro, boolean seuTabuleiro) {
        System.out.println("|----- " + nomeDoJogador + " -----|");
        exibirNumerosDasColunasDosTabuleiros();
        String linhaDoTabuleiro = "";
        char letraDaLinha = 65;
        for(int[] linha : tabuleiro) {
            linhaDoTabuleiro = (letraDaLinha++) + " |";

            for (int coluna : linha) {
                switch(coluna) {
                    case VAZIO:
                        linhaDoTabuleiro += " |";
                        break;
                    case NAVIO:
                        if (seuTabuleiro) {
                            linhaDoTabuleiro += "N|";
                            break;
                        } else {
                            linhaDoTabuleiro += " |";
                            break;
                        }
                    case ERROU:
                        linhaDoTabuleiro += "X|";
                        break;

                    case ACERTOU:
                        linhaDoTabuleiro += "D|";
                        break;
                }
            }
            System.out.println(linhaDoTabuleiro);
        }
    }
    public static String receberValorDigitadoPeloJogador(){
        System.out.println("Digite a posição do seu tiro:");
        return sc.next();
    }
    public static  boolean validarTiroPeloJogador(String tiroDoJogador){
        int quantidadeDeNumeros = (sideY > 10) ? 2 : 1;
        String expressaoDeVerificacao = "^[A-Za-z]{1}[0-9]{"
                + quantidadeDeNumeros + "}$";
        return tiroDoJogador.matches(expressaoDeVerificacao);
    };

    public  static int[] retornarPosicoesDigitadasPeloJogador(String tiroDoJogador) {
        String tiro = tiroDoJogador.toLowerCase();
        int[] retorno = new int[2];
        retorno[POSICAO_X] = tiro.charAt(0) - 97;
        retorno[POSICAO_Y] = Integer.parseInt(tiro.substring(1)) - 1;
        return  retorno;
    }

    public static void inserirValoresDaAcaoNoTabuleiro(int[] posicoes, int numeroDoJogador){
        if(numeroDoJogador == 1) {
            if(mapaJogador2[posicoes[POSICAO_X]] [posicoes[POSICAO_Y]] == NAVIO){
                mapaJogador2[posicoes[POSICAO_X]] [posicoes[POSICAO_Y]] = ACERTOU;
                System.out.println("voce acertou um navio");
                naviosJogador2--;

            }else {
                mapaJogador2[posicoes[POSICAO_X]] [posicoes[POSICAO_Y]] = ERROU;
                System.out.println("voce errou  ");
            }

        }else{
            if(mapaJogador1[posicoes[POSICAO_X]] [posicoes[POSICAO_Y]] == NAVIO){
                mapaJogador1[posicoes[POSICAO_X]] [posicoes[POSICAO_Y]] = ACERTOU;
                System.out.println("voce acertou um navio");
                naviosJogador1--;

            }else {
                mapaJogador1[posicoes[POSICAO_X]] [posicoes[POSICAO_Y]] = ERROU;
                System.out.println("voce errou  ");
            }
        }
    }

    public static boolean acaoDoJogador() {
        boolean acaoValida = true;
        String tiroDoJogador = receberValorDigitadoPeloJogador();
        if (validarTiroPeloJogador(tiroDoJogador)) {
            int[] posicoes = retornarPosicoesDigitadasPeloJogador(tiroDoJogador);
            if(validarPosicaoInseridasPeloJogador(posicoes)){
                inserirValoresDaAcaoNoTabuleiro(posicoes, 1);
            }else {
                acaoValida = false;

            }
        }else {
            System.out.println("Posição invalida");
            acaoValida = false;
        }
        return acaoValida;
    }
    public static boolean validarPosicaoInseridasPeloJogador(int[] posicoes) {
        boolean retorno = true;
        if (posicoes[0] > sideX -1) {
            retorno = false;
            System.out.println("A posicao das letras não pode ser maior que " + (char)(sideX + 64));
        }

        if (posicoes[1] > sideY) {
            retorno = false;
            System.out.println("A posicao numérica não pode ser maior que " + sideY);
        }

        return retorno;
    }
    public static void instanciarContadoresDeNavios(){
        naviosJogador1 = quantidadeNavios;
        naviosJogador2 = quantidadeNavios;
    }

    public static void acaoDoComputador(){

        int[] posicoes = retornarJogadaDoComputador();
        inserirValoresDaAcaoNoTabuleiro(posicoes,2);

    }
    public static int[] retornarJogadaDoComputador(){
        int[] posicoes = new int[2];
        posicoes[POSICAO_X] = retornarJogadaAleatoriaDoComputador(sideX);
        posicoes[POSICAO_Y] = retornarJogadaAleatoriaDoComputador(sideY);

        return posicoes;
    }
    public static int retornarJogadaAleatoriaDoComputador(int limite){
        Random jogadaDoComputador = new Random();
        int numeroGerado = jogadaDoComputador.nextInt(limite);
        return (numeroGerado == limite) ? --numeroGerado : numeroGerado;
    }
    public static void main(String[] args) {
        obterNomeJogadores();
        tamanhoMapa();
        iniciandoMapas();
        calcularLimiteNavios();
        obterQuantidadeDeNavios();
        instanciarContadoresDeNavios();
        inserirNaviosNosMapaDosJogadores();
        boolean jogoAtivo = true;
        do {
            montarTabuleiro();
            if(acaoDoJogador()){
                if(naviosJogador2 <= 0){
                    System.out.println(player1+ " Venceu o jogo");
                    break;
                }
                acaoDoComputador();
                if(naviosJogador1 <= 0){
                    System.out.println(player2 + " Venceu o jogo");
                    jogoAtivo = false;
                }
            }

        }while (jogoAtivo);

        //sc.close();



    }
}
