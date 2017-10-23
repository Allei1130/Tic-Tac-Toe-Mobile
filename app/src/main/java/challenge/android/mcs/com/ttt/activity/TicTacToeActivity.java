package challenge.android.mcs.com.ttt.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import challenge.android.mcs.com.ttt.R;
import challenge.android.mcs.com.ttt.model.TicTacToeGame;

public class TicTacToeActivity extends AppCompatActivity {
    private TicTacToeGame mTicTacToeGame;

    private Button mBoardButtons[];

    private TextView mStatTextView;
    private TextView mPlayerCount;
    private TextView mTieCount;
    private TextView mAICount;

    private ImageButton oButton;
    private ImageButton xButton;

    private int mPlayerCounter = 0;
    private int mTieCounter = 0;
    private int mAICounter = 0;

    private boolean mGameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBoardButtons = new Button[TicTacToeGame.getBoardSize()];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        mStatTextView = (TextView) findViewById(R.id.stats);
        mPlayerCount = (TextView) findViewById(R.id.human_count);
        mTieCount = (TextView) findViewById(R.id.tie_count);
        mAICount = (TextView) findViewById(R.id.ai_count);

        mTicTacToeGame = new TicTacToeGame();

        //dialog setup
        AlertDialog.Builder builder = new AlertDialog.Builder(TicTacToeActivity.this);
        LayoutInflater factory = LayoutInflater.from(TicTacToeActivity.this);
        View view = factory.inflate(R.layout.dialog, null);
        oButton = view.findViewById(R.id.oButton);
        xButton = view.findViewById(R.id.xButton);

        builder.setView(view);
        final AlertDialog alert = builder.create();

        oButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TicTacToeGame.HUMAN_PLAYER = 'O';
                TicTacToeGame.AI_PLAYER = 'X';
                Toast.makeText(TicTacToeActivity.this, "Player is now O.", Toast.LENGTH_SHORT).show();
                startNewGame();
                alert.dismiss();
            }
        });
        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TicTacToeGame.HUMAN_PLAYER = 'X';
                TicTacToeGame.AI_PLAYER = 'O';
                Toast.makeText(TicTacToeActivity.this, "Player is now X.", Toast.LENGTH_SHORT).show();
                startNewGame();
                alert.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.newGame:
                AlertDialog.Builder builder = new AlertDialog.Builder(TicTacToeActivity.this);
                LayoutInflater factory = LayoutInflater.from(TicTacToeActivity.this);
                View view = factory.inflate(R.layout.dialog, null);
                oButton = view.findViewById(R.id.oButton);
                xButton = view.findViewById(R.id.xButton);

                builder.setView(view);
                final AlertDialog alert = builder.create();

                oButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TicTacToeGame.HUMAN_PLAYER = 'O';
                        TicTacToeGame.AI_PLAYER = 'X';
                        Toast.makeText(TicTacToeActivity.this, "Player is now O.", Toast.LENGTH_SHORT).show();
                        startNewGame();
                        alert.dismiss();
                    }
                });
                xButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TicTacToeGame.HUMAN_PLAYER = 'X';
                        TicTacToeGame.AI_PLAYER = 'O';
                        Toast.makeText(TicTacToeActivity.this, "Player is now X.", Toast.LENGTH_SHORT).show();
                        startNewGame();
                        alert.dismiss();
                    }
                });
                alert.show();
                break;
            case R.id.exitGame:
                TicTacToeActivity.this.finish();
        }
        return true;
    }

    private void startNewGame(){
        mTicTacToeGame.clearBoard();

        for (int i=0; i<mBoardButtons.length; i++){
//            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
            mBoardButtons[i].setBackgroundDrawable(getResources().getDrawable(R.drawable.blank));
        }
        mStatTextView.setText(R.string.first_human);
        mGameOver = false;
    }

    private class ButtonClickListener implements View.OnClickListener{
        int location;

        ButtonClickListener(int location){
            this.location = location;
        }

        @Override
        public void onClick(View view) {
            if (!mGameOver){
                if (mBoardButtons[location].isEnabled()){
                    setMove(TicTacToeGame.HUMAN_PLAYER, location);

                    int winner = mTicTacToeGame.checkForWinner();

                    if (winner == 0){
                        mStatTextView.setText(R.string.turn_AI);
                        int move = mTicTacToeGame.getAIMove();
                        setMove(TicTacToeGame.AI_PLAYER, move);
                        winner = mTicTacToeGame.checkForWinner();
                    }

                    if (winner == 0){
                        mStatTextView.setText(R.string.turn_human);
                    }
                    else if (winner == 1){
                        mStatTextView.setText(R.string.result_tie);
                        mTieCounter++;
                        mTieCount.setText(Integer.toString(mTieCounter));
                        mGameOver = true;
                    }
                    else if (winner == 2){
                        mStatTextView.setText(R.string.result_human_wins);
                        mPlayerCounter++;
                        mPlayerCount.setText(Integer.toString(mPlayerCounter));
                        mGameOver = true;
                    }
                    else{
                        mStatTextView.setText(R.string.result_ai_wins);
                        mAICounter++;
                        mAICount.setText(Integer.toString(mAICounter));
                        mGameOver = true;
                    }
                }
            }
        }
    }

    private void setMove(char player, int location){
        mTicTacToeGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        if (player == TicTacToeGame.HUMAN_PLAYER && TicTacToeGame.HUMAN_PLAYER == 'O'){
            mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.o));
        }
        else if (player == TicTacToeGame.HUMAN_PLAYER && TicTacToeGame.HUMAN_PLAYER == 'X'){
            mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.x));
        }
        else if (player == TicTacToeGame.AI_PLAYER && TicTacToeGame.AI_PLAYER == 'O'){
            mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.o));
        }
        else{
            mBoardButtons[location].setBackgroundDrawable(getResources().getDrawable(R.drawable.x));
        }
    }
}