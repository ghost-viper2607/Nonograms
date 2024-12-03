//Pixel 2 API 35
package com.example.nonograms;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toast;
import android.view.Gravity;
import android.view.View;

import com.example.nonograms.controller.Controller;
import com.example.nonograms.models.Cell;
import com.example.nonograms.utils.Constants;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView[][] textViews;
    private TextView[][] columnTextViews;
    private Cell[][] buttons;
    private int lives;
    private TextView livesTextView;
    private ToggleButton modeToggleButton;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new Controller();

        int numRows = Constants.NUM_ROWS;
        int numColumns = Constants.NUM_COLUMNS;

        lives = Constants.TOTAL_LIVES;

        textViews = new TextView[numRows][3];
        columnTextViews = new TextView[3][numColumns];
        buttons = new Cell[numRows][numColumns];

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        livesTextView = findViewById(R.id.livesTextView);
        modeToggleButton = findViewById(R.id.modeToggleButton);

        livesTextView.setText("Lives: " + lives);

        // LayoutParams
        //TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(150, 150);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(Constants.CELL_X_SIZE_PX, Constants.CELL_Y_SIZE_PX); //Button 크기 조절
        layoutParams.setMargins(0, 0, 0, 0); //LayoutParams의 setMargins() 함수로 Margin 조절

        //TableRow 8개
        int totalRows = numRows + 3;

        for(int i = 0; i < totalRows; i++) {
            TableRow tableRow = new TableRow(this);

            if (i < 3) {
                // TextView 3개 추가
                for (int j = 0; j < 3; j++) {
                    TextView emptyTextView = new TextView(this);
                    emptyTextView.setLayoutParams(layoutParams);
                    tableRow.addView(emptyTextView);
                }
                // TextView => col
                for (int j = 0; j < numColumns; j++) {
                    columnTextViews[i][j] = new TextView(this);
                    columnTextViews[i][j].setLayoutParams(layoutParams);
                    columnTextViews[i][j].setGravity(Gravity.CENTER);
                    columnTextViews[i][j].setTextSize(18);
                    columnTextViews[i][j].setText("");
                    tableRow.addView(columnTextViews[i][j]); //TableRow의 addView() 함수 호출
                }
            } else {
                int rowIndex = i - 3;
                // TextView => row
                for(int j = 0 ; j < 3; j++) {
                    textViews[rowIndex][j] = new TextView(this);
                    textViews[rowIndex][j].setLayoutParams(layoutParams);
                    textViews[rowIndex][j].setGravity(Gravity.CENTER);
                    textViews[rowIndex][j].setTextSize(18);
                    textViews[rowIndex][j].setText("");
                    tableRow.addView(textViews[rowIndex][j]); //TableRow의 addView() 함수 호출
                }

                // Thêm các Button (Cell)

                /*for(int j = 0 ; j < 5; j++) {
                    buttons[i][j] = new Button(this);
                    tableRow.addView(buttons[i][j]);
                }*/

                for(int j = 0 ; j < numColumns; j++) {
                    boolean isBlack = controller.isBlackSquare(rowIndex, j);
                    buttons[rowIndex][j] = new Cell(this, isBlack); // Sử dụng Cell thay cho Button
                    buttons[rowIndex][j].setLayoutParams(layoutParams);
                    tableRow.addView(buttons[rowIndex][j]);

                    // 클릭 이벤트 테스 - markBlackSquare() 메서드 동작 테스트
                    buttons[rowIndex][j].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Cell cell = (Cell) v;
                            if (!cell.isEnabled()) {
                                return;
                            }
                            if (modeToggleButton.isChecked()) {
                                cell.toggleX();
                            } else {
                                boolean result = cell.markBlackSquare();
                                if (!result) {
                                    lives--;   // 생명력 뻬기
                                    livesTextView.setText("Lives: " + lives);
                                    if (lives == 0) { // 생명력이 0이 된 경우
                                        gameOver(false); // 게임 종료 => Game Over
                                    }
                                } else {
                                    //  모든 검정 사각형을 찾은 경우
                                    if (controller.checkWin(buttons)) {
                                        gameOver(true); // 게임 종료 => You Win!
                                    }
                                }
                            }
                        }
                    });
                }
            }
            //TableLayout의 addView() 함수 사용
            tableLayout.addView(tableRow);
        }

        for (int i = 0; i < numRows; i++) {
            ArrayList<Integer> rowHint = controller.getRowHints(i);
            int hintSize = rowHint.size();
            int startIndex = 3 - hintSize;

            for (int j = 0; j < 3; j++) {
                if (j >= startIndex) {
                    int hintIndex = j - startIndex;
                    textViews[i][j].setText(String.valueOf(rowHint.get(hintIndex)));
                } else {
                    textViews[i][j].setText("");
                }
            }
        }

        for (int j = 0; j < numColumns; j++) {
            ArrayList<Integer> columnHint = controller.getColumnHints(j);
            int hintSize = columnHint.size();
            int startIndex = 3 - hintSize;

            for (int i = 0; i < 3; i++) {
                if (i >= startIndex) {
                    int hintIndex = i - startIndex;
                    columnTextViews[i][j].setText(String.valueOf(columnHint.get(hintIndex)));
                } else {
                    columnTextViews[i][j].setText("");
                }
            }
        }
    }

    private void gameOver(boolean win) {
        if (win) {
            Toast.makeText(this, "You Win!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Game Over!", Toast.LENGTH_LONG).show();
        }
        int numRows = controller.getNumRows();
        int numColumns = controller.getNumColumns();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }
}