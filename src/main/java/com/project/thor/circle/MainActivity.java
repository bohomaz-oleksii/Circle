package com.project.thor.circle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.thor.circle.engine.GameEngine;
import com.project.thor.circle.enums.GameState;
import com.project.thor.circle.views.FigureView;



public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private TextView scoreView, coinsView, bestView, helpView;
    private ImageView settingsView;
    private final Handler handler = new Handler();
    private long updateDelay = 25;
    private GameEngine engine;
    private FigureView gameView;
    SharedPreferences sPref;
    final String BEST_SCORE = "best_score";
    final String COINS = "coins";
    private int[] saveSwitch = {1, 0, 0, 0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coinsView = (TextView) findViewById(R.id.coins);
        bestView = (TextView) findViewById(R.id.bestText);
        settingsView = (ImageView) findViewById(R.id.imageSettings);
        helpView = (TextView) findViewById(R.id.helpView);
        gameView = (FigureView) findViewById(R.id.figureView);
        gameView.setOnTouchListener(this);
        scoreView = (TextView) findViewById(R.id.text);
        engine = new GameEngine(gameView, scoreView, bestView, coinsView);
        loadData();
        gameView.setState(engine.getState());
        startUpdateHandler();
        settingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                engine.setState(GameState.LOST);
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });


    }

    private void startUpdateHandler() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(engine.getState() == GameState.RUN){
                    engine.update();
                    handler.postDelayed(this, updateDelay);
                    gameView.invalidate();
                }
            }
        }, updateDelay);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                engine.setState(GameState.RUN);
                startUpdateHandler();
                helpView.setVisibility(View.INVISIBLE);
                break;
            case MotionEvent.ACTION_UP:
                engine.setState(GameState.LOST);
                handler.removeCallbacksAndMessages(null);
                engine.checkUpdateLevel();
                break;
        }
        return true;
    }

    public void saveData() {
        //sPref = getPreferences(MODE_PRIVATE);
        sPref = getSharedPreferences("CIRCLE", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(BEST_SCORE, engine.getBestScore());
        ed.putInt(COINS, engine.getCoins());
        ed.commit();
    }

    public void loadData() {
        //sPref = getPreferences(MODE_PRIVATE);
        sPref = getSharedPreferences("CIRCLE", Context.MODE_PRIVATE);
        engine.setBestScore(sPref.getInt(BEST_SCORE, engine.getScore()));
        engine.setCoins(sPref.getInt(COINS, engine.getCoins()));
        saveSwitch = engine.getDataToArray(saveSwitch, sPref.getString("SW", null));
        engine.setArray(saveSwitch);
    }


    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }
}
