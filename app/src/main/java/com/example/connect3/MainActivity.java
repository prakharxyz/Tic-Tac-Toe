package com.example.connect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //empty=0, cross=1, zero=2//
    int[] gameState= {0,0,0,0,0,0,0,0,0}; //array gamestate declared which shows the state of all 9 counters whether it is empty, x or o//
    int[][] winningPoss= {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}}; //2D array which has all possible winning positions
    int player= 1; //initially player x will be playing first
    boolean gameActive= true; //game is ON

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn(View view) { //when a counter is tapped inside grid
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString()); //variable dec to get tag of counter
        if (gameState[tappedCounter]==0 && gameActive) { //when state of tapped counter is empty and game is active then execute this code
            gameState[tappedCounter] = player; //update gamestate of tappedcounter with player
            counter.setTranslationX(-1000); //now we want x & o to translate from left(out of screen) to their assigned position so we set counter to left
            if(player==1) //if player1 has his chance
            {
                counter.setImageResource(R.drawable.x); //set x in counter
                player=2; //now it will be player2's chance so set player=2
            }
            else //if player2 has his chance
            {
                counter.setImageResource(R.drawable.o); //set o in counter
                player=1; //now it will be player1's chance
            }
            counter.animate().translationXBy(1000).setDuration(1000); //translate counter from leftmost to its assigned place in 1sec
            for(int[] winningPos : winningPoss) //get a 1D array winningPos from 2D array winningPoss and loop it through
            {
                if(gameState[winningPos[0]]==gameState[winningPos[1]] && gameState[winningPos[1]]==gameState[winningPos[2]] && gameState[winningPos[0]]!=0)
                //if all 3 counter tags of any subarray winningPos have same states and state is NOT empty that means someone has won
                {
                    gameActive = false; //when someone has won that means game is over and so user now cannot do anything
                    String winner;
                    if(player==2) //if last active player was player2(o) ,ie, if it was player2(o)'s chance
                    {
                        winner="x"; //then winner was player1(x), ie, player1(x) has won this game in his previous chance
                    }
                    else //if last active player was player1(x) ,ie, if it was player1(x)'s chance
                    {
                        winner="o"; //then winner was player2(o), ie, player2(o) has won this game in his previous chance
                    }
                    Button playAgain = (Button) findViewById(R.id.button); //get access to playAgain button
                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
                    winnerTextView.setText(winner+" has won!!"); //winner's name on textview
                    playAgain.setVisibility(View.VISIBLE); //set button visible when game is over which was initially invisible
                    winnerTextView.setVisibility(View.VISIBLE); //set textview visible when game is over which was initially invisible
                }
            }
        }
    }
    public void playAgain(View view)
    {
        try {
            Button playAgain = (Button) findViewById(R.id.button);
            TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);
            playAgain.setVisibility(View.INVISIBLE);
            winnerTextView.setVisibility(View.INVISIBLE);
            GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                ImageView counter = (ImageView) gridLayout.getChildAt(i);
                counter.setImageDrawable(null);
            }
            for (int i = 0; i < gameState.length; i++) {
                gameState[i] = 0;
            }
            player = 1;
            gameActive = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}