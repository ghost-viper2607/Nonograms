package com.example.nonograms.controller;

import com.example.nonograms.models.BoardInit;
import com.example.nonograms.models.Cell;
import com.example.nonograms.utils.Constants;

public class Controller {

    private BoardInit boardInit;
    private int numRows;
    private int numColumns;

    public Controller() {
        boardInit = new BoardInit();
        numRows = Constants.NUM_ROWS;
        numColumns = Constants.NUM_COLUMNS;
    }

    public boolean isBlackSquare(int row, int column) {
        return boardInit.isBlackSquare(row, column);
    }

    public java.util.ArrayList<Integer> getRowHints(int row) {
        return boardInit.getRowHints(row);
    }

    public java.util.ArrayList<Integer> getColumnHints(int column) {
        return boardInit.getColumnHints(column);
    }

    public boolean checkWin(Cell[][] cells) {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (isBlackSquare(i, j) && !cells[i][j].isRevealed()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }
}