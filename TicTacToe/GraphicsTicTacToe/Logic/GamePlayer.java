package GraphicsTicTacToe.Logic;

public class GamePlayer {

    private char playerSign;
    private boolean realPlayer;

    public GamePlayer(boolean isRealPlayer, char playerSign) {
        this.realPlayer = isRealPlayer;
        this.playerSign = playerSign;
    }

    public boolean isRealPlayer() {
        return realPlayer;
    }

    public char getPlayerSign() {
        return playerSign;
    }
}
