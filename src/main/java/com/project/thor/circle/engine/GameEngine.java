package com.project.thor.circle.engine;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.project.thor.circle.enums.GameState;
import com.project.thor.circle.views.FigureView;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class GameEngine {


    private GameState state = GameState.RUN;
    private Random random = new Random();
    private FigureView gameView;
    private int Score = 0;
    private int BestScore = 0;
    private int Coins = 0;


    private int[] arraySwitch = {1, 0, 0, 0};

    private TextView scoreView, bestView, coinsView;

    public GameEngine(){}

    public GameEngine(FigureView view, TextView scoreView, TextView bestView, TextView coinsView) {
        this.gameView = view;
        this.scoreView = scoreView;
        this.bestView = bestView;
        this.coinsView = coinsView;
        update();
    }

    public void update(){
        gameView.setState(state);
        setData();
    }

    public void checkUpdateLevel(){
        if(gameView.getRadius() >= gameView.getRadiusSecond() && gameView.getRadius() <= gameView.getRadiusFirst()){
            state = GameState.WIN;
            Score ++;
            Coins += 1;
        } else {
            if(Score > BestScore){
                BestScore = Score;
            }
            state = GameState.LOST;
            Score = 0;

        }
        setData();
        gameView.setRadius(0);
        int k = 0;
        int[] arr = {0, 0, 0, 0};
        for(int i = 0; i < arraySwitch.length; i++){
            if(arraySwitch[i] != 0){
                arr[k] = arraySwitch[i];
                k++;
            }
        }
        gameView.setIdFigure(arr[random.nextInt(k)]);
        //gameView.setIdFigure(1 + random.nextInt(4));
        gameView.setState(state);
        //Log.d("TAG", "Score = " + Score);
    }

    public void setData(){
        scoreView.setText("Score: " + Score);
        bestView.setText("Best: " + BestScore);
        coinsView.setText("Coins: " + Coins);
    }


    public boolean checkBuy(int price){
        if(price > Coins){
            return false;
        }else{
            Coins = Coins - price;
            return true;
        }
    }


    public int[] getDataToArray(int[] array, String data){
        int k = 0;
        if(data != null){
            int dat = Integer.parseInt(data);
            for (int i = array.length-1; i >= 0; i--){
                array[i] = dat % 10;
                dat /= 10;
                if(array[i] == 0){
                    k++;
                }
            }
        }
        if(k == 4){
            array[0] = 1;
        }
        return array;
    }



    public String setDataToArray(int[] array){
        String data = "";
        for(int i =0; i<array.length; i++){
            data += String.valueOf(array[i]);
        }
        return data;
    }


    public int getBestScore() { return BestScore; }
    public void setBestScore(int bestScore) { BestScore = bestScore; }
    public int getScore() { return Score; }
    public GameState getState() { return state; }
    public void setState(GameState state) { this.state = state; }
    public int getCoins() { return Coins; }
    public void setCoins(int coins) { Coins = coins; }
    public void setArray(int[] array) { this.arraySwitch = array; }
}
