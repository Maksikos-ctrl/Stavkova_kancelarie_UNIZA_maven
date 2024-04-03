package com.stavkova.kancelarie;

import java.util.Random;

public class Loterei {

    private final int[][] board;

    public Loterei(int rows, int columns) {
        this.board = generateRandomBoard(rows, columns);
    }

    public int[][] getBoard() {
        return board;
    }

    public int getWinningNumber() {
        return (int) (Math.random() * 100);
    }

    public int[] checkWinningBets(int winningNumber) {
        int count = 0;
        for (int[] row : board) {
            for (int num : row) {
                if (num == winningNumber) {
                    count++;
                }
            }
        }
        int[] winningBets = new int[count];
        count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == winningNumber) {
                    winningBets[count++] = i * board[i].length + j + 1;
                }
            }
        }
        return winningBets;
    }

    private int[][] generateRandomBoard(int rows, int columns) {
        int[][] newBoard = new int[rows][columns];
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                newBoard[i][j] = random.nextInt(100) + 1;
            }
        }
        return newBoard;
    }

    public boolean checkIfInWinningCombination(int numOfSelectedNums) {
        int winningNumber = getWinningNumber();
        int[] winningBets = checkWinningBets(winningNumber);
        for (int i = 0; i < numOfSelectedNums; i++) {
            if (winningBets[i] == 0) {
                return false;
            }
        }
        return true;
    }
}
