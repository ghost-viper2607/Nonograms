package com.example.nonograms.models;

import com.example.nonograms.utils.Constants;
import java.util.ArrayList;
import java.util.Random;

public class BoardInit {

    private boolean[][] blackSquares;
    private ArrayList<Integer>[] rowHints;
    private ArrayList<Integer>[] columnHints;

    public BoardInit() {
        int numRows = Constants.NUM_ROWS;
        int numColumns = Constants.NUM_COLUMNS;

        blackSquares = new boolean[numRows][numColumns];
        rowHints = new ArrayList[numRows];
        columnHints = new ArrayList[numColumns];

        generateBlackSquares(numRows, numColumns);
        calculateHints(numRows, numColumns);
    }

    private void generateBlackSquares(int numRows, int numColumns) {
        Random random = new Random();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                blackSquares[i][j] = random.nextBoolean();
            }
        }
    }

    private void calculateHints(int numRows, int numColumns) {
        for (int i = 0; i < numRows; i++) {
            rowHints[i] = calculateLineHints(blackSquares[i]);
        }

        for (int j = 0; j < numColumns; j++) {
            boolean[] column = new boolean[numRows];
            for (int i = 0; i < numRows; i++) {
                column[i] = blackSquares[i][j];
            }
            columnHints[j] = calculateLineHints(column);
        }
    }

    private ArrayList<Integer> calculateLineHints(boolean[] line) {
        ArrayList<Integer> hints = new ArrayList<>();
        int count = 0;
        for (boolean cell : line) {
            if (cell) {
                count++;
            } else {
                if (count > 0) {
                    hints.add(count);
                    count = 0;
                }
            }
        }
        if (count > 0) {
            hints.add(count);
        }
        if (hints.isEmpty()) {
            hints.add(0);
        }
        return hints;
    }

    public boolean isBlackSquare(int row, int column) {
        return blackSquares[row][column];
    }

    public ArrayList<Integer> getRowHints(int row) {
        return rowHints[row];
    }

    public ArrayList<Integer> getColumnHints(int column) {
        return columnHints[column];
    }

}
