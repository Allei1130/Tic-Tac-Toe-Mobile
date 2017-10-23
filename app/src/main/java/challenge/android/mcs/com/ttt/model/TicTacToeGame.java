package challenge.android.mcs.com.ttt.model;

import java.util.Random;

public class TicTacToeGame {
    private char mBoard[];
    private final static int BOARD_SIZE = 9;

    public static char HUMAN_PLAYER;
    public static char AI_PLAYER;
    private static final char EMPTY_SPACE = ' ';

    private Random mRand;

    public static int getBoardSize(){
        return BOARD_SIZE;
    }

    public TicTacToeGame(){
        mBoard = new char[BOARD_SIZE];

        for(int i=0;i<BOARD_SIZE; i++){
            mBoard[i] = EMPTY_SPACE;
        }

        mRand = new Random();
    }

    public void clearBoard(){
        for (int i=0; i<BOARD_SIZE; i++){
            mBoard[i] = EMPTY_SPACE;
        }
    }

    public void setMove(char player, int location){
        mBoard[location] = player;
    }

    public int getAIMove(){
        int move;
        //logic for AI going for the win
        for(int i=0; i<getBoardSize(); i++){
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != AI_PLAYER){
                char curr = mBoard[i];
                mBoard[i] = AI_PLAYER;
                if (checkForWinner() == 3){
                    setMove(AI_PLAYER, i);
                    return i;
                }
                else{
                    mBoard[i] = curr;
                }
            }
        }
        //logic for AI blocking the player
        for (int i=0; i<getBoardSize(); i++){
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != AI_PLAYER){
                char curr = mBoard[i];
                mBoard[i] = HUMAN_PLAYER;
                if (checkForWinner() == 2){
                    setMove(AI_PLAYER, i);
                    return i;
                }
                else{
                    mBoard[i] = curr;
                }
            }
        }
        //logic for AI making a random move. This needs to change.
        do{
            move = mRand.nextInt(getBoardSize());
        }
        while (mBoard[move] == HUMAN_PLAYER || mBoard[move] == AI_PLAYER);{
            setMove(AI_PLAYER, move);
        }
        return move;
    }

    public int checkForWinner(){
        for (int i=0; i<=6; i+=3){
            //horizontal human win
            if(mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+1] == HUMAN_PLAYER &&
                    mBoard[i+2] == HUMAN_PLAYER)
                return 2;
            //horizontal ai win
            if(mBoard[i] == AI_PLAYER &&
                    mBoard[i+1] == AI_PLAYER &&
                    mBoard[i+2] == AI_PLAYER)
                return 3;
        }

        for (int i=0; i<=2; i++){
            //vertical human win
            if (mBoard[i] == HUMAN_PLAYER &&
                    mBoard[i+3] == HUMAN_PLAYER &&
                    mBoard[i+6] == HUMAN_PLAYER)
                return 2;
            //horizontal ai win
            if (mBoard[i] == AI_PLAYER &&
                    mBoard[i+3] == AI_PLAYER &&
                    mBoard[i+6] == AI_PLAYER)
                return 3;
        }

        //diagonal human win
        if (mBoard[0] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[8] == HUMAN_PLAYER ||
                mBoard[2] == HUMAN_PLAYER &&
                mBoard[4] == HUMAN_PLAYER &&
                mBoard[6] == HUMAN_PLAYER){
            return 2;
        }
        //diagonal ai win
        if (mBoard[0] == AI_PLAYER &&
                mBoard[4] == AI_PLAYER &&
                mBoard[8] == AI_PLAYER ||
                mBoard[2] == AI_PLAYER &&
                mBoard[4] == AI_PLAYER &&
                mBoard[6] == AI_PLAYER) {
            return 3;
        }

        //tie game
        for (int i=0; i<getBoardSize(); i++){
            if (mBoard[i] != HUMAN_PLAYER && mBoard[i] != AI_PLAYER){
                return 0;
            }
        }
        //game is still active
        return 1;
    }
}