package GraphicsTicTacToe.Logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener {

    private int row;
    private int cell;
    private GameButton button;

    public GameActionListener(int row, int cell, GameButton gButton) {
        this.row = row;
        this.cell = cell;
        this.button = gButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        GameBoard board = button.getBoard();

        if (board.isTurnable(row, cell)) {
            updateByPlayersData(board);

            if (board.isFull()) {
                board.getGame().showMessage("Ничья!");
                board.emptyField();
            }
            else {
                updateByAiData(board);
            }
        }
        else {
            board.getGame().showMessage("Некорректный ход!");
        }
    }

    // Ход человека:
    private void updateByPlayersData(GameBoard board) {
        // обновить матрицу игры:
        board.updateGameField(row, cell);

        // обновить содержимое кнопки:
        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        if (board.checkWin()) {
            button.getBoard().getGame().showMessage("Вы выиграли!");
            board.emptyField();
        }
        else {
            board.getGame().passTurn();
        }
    }

    // Рандомный компьютер:
//    private void updateByAiData(GameBoard board) {
//        // генерация координат хода компьюетра:
//        int x, y;
//        Random rnd = new Random();
//
//        do {
//            x = rnd.nextInt(GameBoard.dimension);
//            y = rnd.nextInt(GameBoard.dimension);
//        } while (!board.isTurnable(x, y));
//
//        // обновить матрицу игры:
//        board.updateGameField(x, y);
//
//        // обновить содержимое кнопки:
//        int cellIndex = GameBoard.dimension * x + y;
//        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));
//
//        // проверить победу:
//        if (board.checkWin()) {
//            button.getBoard().getGame().showMessage("Компьютер победил!");
//            board.emptyField();
//        }
//        else {
//            // передать ход:
//            board.getGame().passTurn();
//        }
//    }


    // "Умный" компьютер:
    private void updateByAiData(GameBoard board) {
        int maxScoreRow = -1;
        int maxScoreCell = -1;
        int maxScore = 0;
        char[][] map = button.getBoard().getGameField();
        int row = -1;
        int cell = -1;
        Random random = new Random();

        for (int i = 0; i < GameBoard.dimension; i++) {
            for (int j = 0; j < GameBoard.dimension; j++) {
                int fieldScore = 0;

                if (map[i][j] == GameBoard.nullSymbol) {
                    // проверяем направление:
                    if (
                            i - 1 >= 0 && j - 1 >= 0 &&
                            map[i-1][j-1] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()
                    )
                    {
                        fieldScore++;
                    }
                    if (
                            i - 1 >= 0 &&
                            map[i-1][j] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()
                    )
                    {
                        fieldScore++;
                    }
                    if (
                            i - 1 >= 0 && j + 1 < GameBoard.dimension &&
                            map[i-1][j+1] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()
                    )
                    {
                        fieldScore++;
                    }
                    if (
                            j + 1 < GameBoard.dimension &&
                            map[i][j+1] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()
                    )
                    {
                        fieldScore++;
                    }
                    if (
                            i + 1 < GameBoard.dimension && j + 1 < GameBoard.dimension &&
                            map[i+1][j+1] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()
                    )
                    {
                        fieldScore++;
                    }
                    if (
                            i + 1 < GameBoard.dimension &&
                            map[i+1][j] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()
                    )
                    {
                        fieldScore++;
                    }
                    if (
                            i + 1 < GameBoard.dimension && j - 1 >= 0 &&
                            map[i+1][j-1] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()
                    )
                    {
                        fieldScore++;
                    }
                    if (
                            j - 1 >= 0 &&
                            map[i][j-1] == button.getBoard().getGame().getCurrentPlayer().getPlayerSign()
                    )
                    {
                        fieldScore++;
                    }
                }
                if (fieldScore > maxScore) {
                    maxScore = fieldScore;
                    maxScoreRow = j;
                    maxScoreCell = i;
                }
            }
        }

        if (maxScoreRow == -1) {
            do {
                row = random.nextInt(GameBoard.dimension);
                cell = random.nextInt(GameBoard.dimension);
            }
            while (!button.getBoard().isTurnable(row, cell));
        }

        if (maxScoreRow != -1) {
            row = maxScoreRow;
            cell = maxScoreCell;
        }

        button.getBoard().updateGameField(row, cell);

        int cellIndex = GameBoard.dimension * row + cell;
        button.getBoard().getButton(cellIndex).setText(
                Character.toString(button.getBoard().getGame().getCurrentPlayer().getPlayerSign())
        );

        if (button.getBoard().checkWin()) {
            button.getBoard().getGame().showMessage("Компьютер победил!");
            button.getBoard().emptyField();
        }
        else {
            button.getBoard().getGame().passTurn();
        }
    }
}
