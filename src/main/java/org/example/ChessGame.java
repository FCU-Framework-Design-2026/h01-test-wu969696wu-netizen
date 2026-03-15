package org.example;

import java.util.*;

public class ChessGame {

    Chess[][] board = new Chess[4][8];

    public void generateChess() {

        String[] pieces = {
                "將","士","士","象","象","馬","馬","車","車",
                "炮","炮","卒","卒","卒","卒","卒",
                "帥","仕","仕","相","相","馬","馬","車","車",
                "炮","炮","兵","兵","兵","兵","兵"
        };

        List<String> list = new ArrayList<>(Arrays.asList(pieces));

        Collections.shuffle(list);

        int index = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {

                String name = list.get(index);

                String side = getSide(name);

                int weight = getWeight(name);

                board[i][j] = new Chess(name, side, weight);

                index++;
            }
        }
    }

    public String getSide(String name) {

        if(name.equals("將") || name.equals("士") || name.equals("象") ||
                name.equals("車") || name.equals("馬") || name.equals("炮") ||
                name.equals("卒"))
            return "black";

        return "red";
    }

    public int getWeight(String name) {

        switch (name) {

            case "將":
            case "帥":
                return 7;

            case "士":
            case "仕":
                return 6;

            case "象":
            case "相":
                return 5;

            case "車":
                return 4;

            case "馬":
                return 3;

            case "炮":
                return 2;

            case "卒":
            case "兵":
                return 1;
        }

        return 0;
    }

    public void showBoard() {

        System.out.println("  A B C D E F G H");

        for (int i = 0; i < 4; i++) {

            System.out.print((i + 1) + " ");

            for (int j = 0; j < 8; j++) {

                if (board[i][j] == null)
                    System.out.print(". ");
                else
                    System.out.print(board[i][j] + " ");

            }

            System.out.println();
        }
    }

    public int[] parse(String pos) {

        if (pos.length() != 2)
            return null;

        char colChar = pos.charAt(0);
        char rowChar = pos.charAt(1);

        if (colChar < 'A' || colChar > 'H')
            return null;

        if (rowChar < '1' || rowChar > '4')
            return null;

        int col = colChar - 'A';
        int row = rowChar - '1';

        return new int[]{row, col};
    }

    public boolean flip(String pos) {

        int[] p = parse(pos);

        if (p == null)
            return false;

        Chess c = board[p[0]][p[1]];

        if (c == null)
            return false;

        if (!c.open) {

            c.open = true;
            return true;

        }

        return false;
    }

    public boolean canEat(Chess a, Chess b) {

        String A = a.name;
        String B = b.name;

        if ((A.equals("兵") || A.equals("卒")) &&
                (B.equals("將") || B.equals("帥")))
            return true;

        if ((A.equals("將") || A.equals("帥")) &&
                (B.equals("兵") || B.equals("卒")))
            return false;

        if ((A.equals("兵") || A.equals("卒")) &&
                B.equals("炮"))
            return false;

        return a.weight >= b.weight;
    }

    public boolean move(String from, String to) {

        int[] p1 = parse(from);
        int[] p2 = parse(to);

        if (p1 == null || p2 == null)
            return false;

        Chess c1 = board[p1[0]][p1[1]];
        Chess c2 = board[p2[0]][p2[1]];

        if (c1 == null)
            return false;

        if (!c1.open)
            return false;

        int dist = Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);

        if (dist != 1)
            return false;

        if (c2 == null) {

            board[p2[0]][p2[1]] = c1;
            board[p1[0]][p1[1]] = null;

            return true;
        }

        if (!c2.open)
            return false;

        if (c1.side.equals(c2.side))
            return false;

        if (canEat(c1, c2)) {

            board[p2[0]][p2[1]] = c1;
            board[p1[0]][p1[1]] = null;

            return true;
        }

        return false;
    }

    public boolean gameOver() {

        boolean red = false;
        boolean black = false;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {

                Chess c = board[i][j];

                if (c == null)
                    continue;

                if (c.side.equals("red"))
                    red = true;

                if (c.side.equals("black"))
                    black = true;
            }
        }

        return !(red && black);
    }
}