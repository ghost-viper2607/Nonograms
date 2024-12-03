package com.example.nonograms.models;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton; //AppCompatButton 클래스를 상속

import com.example.nonograms.R;

public class Cell extends AppCompatButton {

    private boolean blackSquare;
    private boolean checked;
    private static int numBlackSquares;

    public Cell(@NonNull Context context) {
        super(context);
        this.blackSquare = Math.random() < 0.5; // 50% 확률로 검정 사각형 생성
        if (blackSquare) {
            numBlackSquares++;
        }
        checked = false;
        setBackgroundResource(R.drawable.cell_selector);
    }

    public Cell(Context context, boolean blackSquare) {
        super(context);
        this.blackSquare = blackSquare;
        if (blackSquare) {
            numBlackSquares++;
        }
        checked = false;
        setBackgroundResource(R.drawable.cell_selector);
    }

    public boolean isBlackSquare() {
        return blackSquare;
    }

    public static int getNumBlackSquares() {
        return numBlackSquares;
    }

    public static void resetNumBlackSquares() {
        numBlackSquares = 0;
    }

    public boolean markBlackSquare() {
        if (checked) {
            return true;
        }
        if (blackSquare) {
            setBackgroundColor(getResources().getColor(android.R.color.black));
            setEnabled(false);
            numBlackSquares--;
            return true;
        } else {
            setText("X");
            return false;
        }
    }

    public boolean toggleX() {
        checked = !checked;
        if (checked) {
            setText("X");
        } else {
            setText("");
        }
        return checked;
    }

    public boolean isRevealed() {
        return !isEnabled();
    }
}
