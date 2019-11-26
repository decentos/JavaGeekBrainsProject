package GraphicsTicTacToe.Logic;

import javax.swing.*;

public class Game {

    private GameBoard board;
    private GamePlayer[] gamePlayers = new GamePlayer[2];
    private int playersTurn = 0;

    public Game() {
        this.board = new GameBoard(this);
    }

    public void initGame(){
        gamePlayers[0] = new GamePlayer(true, 'X');
        gamePlayers[1] = new GamePlayer(false, 'O');
    }

    // Метод передачи хода:
    void passTurn() {
        if (playersTurn == 0)
            playersTurn = 1;
        else
            playersTurn = 0;
    }

    // Получение объкта текущего игрока:
    GamePlayer getCurrentPlayer() {
        return gamePlayers[playersTurn];
    }

    // Метод показа сообщения:
    void showMessage(String messageText) {
        JOptionPane.showMessageDialog(board, messageText);
    }
}
