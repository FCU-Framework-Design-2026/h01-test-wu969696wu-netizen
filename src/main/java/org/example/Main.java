package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ChessGame game = new ChessGame();

        game.generateChess();

        Scanner sc = new Scanner(System.in);

        while (true) {

            game.showBoard();

            if (game.gameOver()) {
                System.out.println("Game Over!");
                break;
            }

            System.out.print("choose position: ");

            String pos = sc.next().toUpperCase();

            if (game.flip(pos))
                continue;

            System.out.print("move to: ");

            String dest = sc.next().toUpperCase();

            if (!game.move(pos, dest))
                System.out.println("invalid move");
        }
    }
}