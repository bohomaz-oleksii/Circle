package com.project.thor.circle;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;

import com.project.thor.circle.engine.GameEngine;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, Switch.OnCheckedChangeListener {

    private Switch circleSwitch, triangleSwitch, squareSwitch, starSwitch;
    private Button btnTriangle, btnSquare, btnStar;
    private GameEngine engine;
    final String COINS = "coins";
    SharedPreferences sPref;
    private int[] saveBtn = {0, 0, 0};
    private int[] saveSwitch = {1, 0, 0, 0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // установка кнопки назад в тулбар
        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //------


        circleSwitch = (Switch) findViewById(R.id.switchCircle);
        triangleSwitch = (Switch) findViewById(R.id.switchTriangle);
        squareSwitch = (Switch) findViewById(R.id.switchSquare);
        starSwitch = (Switch) findViewById(R.id.switchStar);
        circleSwitch.setOnCheckedChangeListener(this);
        triangleSwitch.setOnCheckedChangeListener(this);
        squareSwitch.setOnCheckedChangeListener(this);
        starSwitch.setOnCheckedChangeListener(this);

        btnTriangle = (Button) findViewById(R.id.triangleBtn);
        btnSquare = (Button) findViewById(R.id.squareBtn);
        btnStar = (Button) findViewById(R.id.starBtn);
        btnTriangle.setOnClickListener(this);
        btnSquare.setOnClickListener(this);
        btnStar.setOnClickListener(this);
        engine = new GameEngine();

        loadData();
        setBtnAndSwitches();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.triangleBtn:
                if(engine.checkBuy(10)){
                    btnTriangle.setVisibility(View.INVISIBLE);
                    triangleSwitch.setVisibility(View.VISIBLE);
                    circleSwitch.setVisibility(View.VISIBLE);
                    saveBtn[0] = 1;
                }else{
                    Toast.makeText(this, "Not enough coins - " + (500-engine.getCoins()) , Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.squareBtn:
                if(engine.checkBuy(20)){
                    btnSquare.setVisibility(View.INVISIBLE);
                    squareSwitch.setVisibility(View.VISIBLE);
                    circleSwitch.setVisibility(View.VISIBLE);
                    saveBtn[1] = 1;
                }else{
                    Toast.makeText(this, "Not enough coins - " + (1000 - engine.getCoins()), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.starBtn:
                if(engine.checkBuy(30)){
                    btnStar.setVisibility(View.INVISIBLE);
                    starSwitch.setVisibility(View.VISIBLE);
                    circleSwitch.setVisibility(View.VISIBLE);
                    saveBtn[2] = 1;
                }else{
                    Toast.makeText(this, "Not enough coins - " + (1500 - engine.getCoins()), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.switchCircle:
                //Toast.makeText(this, "checked: " + isChecked, Toast.LENGTH_SHORT).show();
                if(isChecked){
                    saveSwitch[0] = 1;
                }else{
                    saveSwitch[0] = 0;
                }
                break;
            case R.id.switchTriangle:
                //Toast.makeText(this, "checked: " + isChecked, Toast.LENGTH_SHORT).show();
                if(isChecked){
                    saveSwitch[1] = 2;
                }else{
                    saveSwitch[1] = 0;
                }
                break;
            case R.id.switchSquare:
                //Toast.makeText(this, "checked: " + isChecked, Toast.LENGTH_SHORT).show();
                if(isChecked){
                    saveSwitch[2] = 3;
                }else{
                    saveSwitch[2] = 0;
                }
                break;
            case R.id.switchStar:
                //Toast.makeText(this, "checked: " + isChecked + array[3], Toast.LENGTH_SHORT).show();
                if(isChecked){
                    saveSwitch[3] = 4;
                }else{
                    saveSwitch[3] = 0;
                }
                break;
        }
    }

    public void saveData() {
        String dataBtn = engine.setDataToArray(saveBtn);
        String dataSwitch = engine.setDataToArray(saveSwitch);
        sPref = getSharedPreferences("CIRCLE", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(COINS, engine.getCoins());
        ed.putString("BTN", dataBtn);
        ed.putString("SW", dataSwitch);
        ed.commit();
        Toast.makeText(this, dataBtn + "\n" + dataSwitch, Toast.LENGTH_SHORT).show();
    }

    public void loadData() {
        sPref = getSharedPreferences("CIRCLE", Context.MODE_PRIVATE);
        engine.setCoins(sPref.getInt(COINS, engine.getCoins()));
        saveBtn = engine.getDataToArray(saveBtn, sPref.getString("BTN", null));
        saveSwitch = engine.getDataToArray(saveSwitch, sPref.getString("SW", null));
    }


    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }


    public void setBtnAndSwitches(){
        Switch []array = {circleSwitch, triangleSwitch, squareSwitch, starSwitch};
        for(int i = 0; i< array.length; i++){
            if(saveSwitch[i] != 0){
                array[i].setChecked(true);
            }
        }
        if(saveBtn[0] != 0){
            btnTriangle.setVisibility(View.INVISIBLE);
            triangleSwitch.setVisibility(View.VISIBLE);
            circleSwitch.setVisibility(View.VISIBLE);
        }
        if(saveBtn[1] != 0){
            btnSquare.setVisibility(View.INVISIBLE);
            squareSwitch.setVisibility(View.VISIBLE);
            circleSwitch.setVisibility(View.VISIBLE);
        }
        if(saveBtn[2] != 0){
            btnStar.setVisibility(View.INVISIBLE);
            starSwitch.setVisibility(View.VISIBLE);
            circleSwitch.setVisibility(View.VISIBLE);
        }

    }


}
