package com.trieudq194388.simple_calculator_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String NUMBER = "number";
    private static final String EQUAL_BUTTON = "equal_button";
    private static final String OPERATOR = "operator";
    private static final String BACKSPACE = "backspace";
    private static final String CANNOT_DIVIDE_BY_ZERO = "cannot divide by zero";
    private static final String OUT_OF_RANGE = "Out of range";
    private TextView inputText, operationText;
    private String previousBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.input_text);
        operationText = findViewById(R.id.operation_text);
    }


    public void ceBtnOnClick(View view) {

        if(previousBtn.equals(EQUAL_BUTTON)){
            inputText.setText(R.string.default_string);
            operationText.setText(R.string.default_operation);
        }
        else{
            inputText.setText(R.string.default_string);
        }
    }

    public void cBtnOnClick(View view) {
        inputText.setText(R.string.default_string);
        operationText.setText(R.string.default_operation);
    }

    public void bsBtnOnClick(View view) {
        DBZandOORCheck();

        if(!previousBtn.equals(OPERATOR)){
            if(previousBtn.equals(EQUAL_BUTTON))
                operationText.setText(R.string.default_operation);
            else bsBtnAction();

            previousBtn = BACKSPACE;
        }
    }
    private void bsBtnAction(){
        String input = inputText.getText().toString();
        int size = input.length();
        if((size == 1) || (size == 2 && input.contains("-"))) inputText.setText(R.string.default_string);
        else {
            input = input.substring(0, input.length() - 1);
            inputText.setText(input);
        }
    }


    public void divBtnOnClick(View view) {
        operatorAction('/');
    }
    public void mulBtnOnClick(View view) {
        operatorAction('x');
    }
    public void addBtnOnClick(View view) {
        operatorAction('+');
    }
    public void minusBtnOnClick(View view) {
        operatorAction('-');
    }
    @SuppressLint("SetTextI18n")
    private void operatorAction(char operator){
        if(previousBtn.equals(OPERATOR)){
            String currOperation = operationText.getText().toString();
            int size = currOperation.length();
            currOperation = currOperation.substring(0, size - 2);
            currOperation = currOperation + operator + " ";
            operationText.setText(currOperation);
        }
        else if(previousBtn.equals(EQUAL_BUTTON)){
            String res = inputText.getText().toString();
            calculate(res);
            String input = inputText.getText().toString();
            operationText.setText(input + " " + operator + " ");
        }
        else{
            String currOperation = operationText.getText().toString();
            currOperation += inputText.getText().toString();
            calculate(currOperation);

            String input = inputText.getText().toString();
            if(input.equals(CANNOT_DIVIDE_BY_ZERO)) operationText.setText(currOperation + " " + operator + " ");
            else operationText.setText(input + " " + operator + " ");
        }

        previousBtn = OPERATOR;
    }

    @SuppressLint("SetTextI18n")
    public void equalBtnOnClick(View view) {
        DBZandOORCheck();

        if(previousBtn != null && !previousBtn.equals(EQUAL_BUTTON)){
            String currOperation = operationText.getText().toString();
            currOperation += inputText.getText().toString();
            calculate(currOperation);

            currOperation += " = ";
            operationText.setText(currOperation);
        }

        previousBtn = EQUAL_BUTTON;
    }
    @SuppressLint("SetTextI18n")
    private void calculate(String currOperation){

        if(currOperation.split(" \\+ ").length == 2){
            String[] number = currOperation.split(" \\+ ");
            try{
                long add = Long.parseLong(number[0]) +  Long.parseLong(number[1]);
                inputText.setText(add+"");
            }catch (Exception e){
                Log.e("ERROR", e+"");
                inputText.setText(OUT_OF_RANGE);
                disableOperatorBtn();
            }
        }
        else if(currOperation.split(" - ").length == 2){
            String[] number = currOperation.split(" - ");
            try{
                long minus = Long.parseLong(number[0]) -  Long.parseLong(number[1]);
                inputText.setText(minus+"");
            }catch (Exception e){
                Log.e("ERROR", e+"");
                inputText.setText(OUT_OF_RANGE);
                disableOperatorBtn();
            }
        }
        else if(currOperation.split(" x ").length == 2){
            String[] number = currOperation.split(" x ");
            try{
                long mul = Long.parseLong(number[0]) *  Long.parseLong(number[1]);
                inputText.setText(mul+"");
            }catch (Exception e){
                Log.e("ERROR", e+"");
                inputText.setText(OUT_OF_RANGE);
                disableOperatorBtn();
            }
        }
        else if(currOperation.split(" / ").length == 2){
            String[] number = currOperation.split(" / ");
            try{
                if(Long.parseLong(number[1]) == 0){
                    inputText.setText(CANNOT_DIVIDE_BY_ZERO);
                    disableOperatorBtn();
                }
                else{
                    long div = Long.parseLong(number[0]) /  Long.parseLong(number[1]);
                    inputText.setText(div+"");
                }

            }catch (Exception e){
                Log.e("ERROR", e+"");
                inputText.setText(OUT_OF_RANGE);
                disableOperatorBtn();
            }
        }
    }

    private void disableOperatorBtn(){
        findViewById(R.id.add_button).setEnabled(false);
        findViewById(R.id.minus_button).setEnabled(false);
        findViewById(R.id.mul_button).setEnabled(false);
        findViewById(R.id.div_button).setEnabled(false);
        findViewById(R.id.opp_button).setEnabled(false);
        findViewById(R.id.dot_button).setEnabled(false);
    }
    private void enableOperatorBtn(){
        findViewById(R.id.add_button).setEnabled(true);
        findViewById(R.id.minus_button).setEnabled(true);
        findViewById(R.id.mul_button).setEnabled(true);
        findViewById(R.id.div_button).setEnabled(true);
        findViewById(R.id.opp_button).setEnabled(true);
        findViewById(R.id.dot_button).setEnabled(true);
    }


    @SuppressLint("SetTextI18n")
    public void oppBtnOnClick(View view) {
        String input = inputText.getText().toString();
        try{
            long inputInt = Long.parseLong(input);
            if(previousBtn.equals(EQUAL_BUTTON)){
                operationText.setText(R.string.default_operation);
                inputInt *= -1;
                inputText.setText(inputInt + "");
            }
            else if(previousBtn.equals(OPERATOR))
                inputText.setText(R.string.default_string);
            else{
                inputInt *= -1;
                inputText.setText(inputInt + "");
            }
        }catch(Exception e){
            Log.e("ERROR", e+"");
            inputText.setText("Out of range");
            disableOperatorBtn();
        }

    }

    @SuppressLint("SetTextI18n")
    public void zeroBtnOnClick(View view) {
        numberAction('0');
    }
    @SuppressLint("SetTextI18n")
    public void oneBtnOnClick(View view) {
        numberAction('1');
    }
    @SuppressLint("SetTextI18n")
    public void twoBtnOnClick(View view) {
        numberAction('2');
    }
    @SuppressLint("SetTextI18n")
    public void threeBtnOnClick(View view) {
        numberAction('3');
    }
    @SuppressLint("SetTextI18n")
    public void fourBtnOnClick(View view) {
        numberAction('4');
    }
    @SuppressLint("SetTextI18n")
    public void fiveBtnOnClick(View view) {
        numberAction('5');
    }
    @SuppressLint("SetTextI18n")
    public void sixBtnOnClick(View view) {
        numberAction('6');
    }
    @SuppressLint("SetTextI18n")
    public void sevenBtnOnClick(View view) {
        numberAction('7');
    }
    @SuppressLint("SetTextI18n")
    public void eightBtnOnClick(View view) {
        numberAction('8');
    }
    @SuppressLint("SetTextI18n")
    public void nineBtnOnClick(View view) {
        numberAction('9');
    }

    @SuppressLint("SetTextI18n")
    private void numberAction(char number){
        enableOperatorBtn();

        String input = inputText.getText().toString();
//        Log.i("num", input);

        if(previousBtn != null && (previousBtn.equals(EQUAL_BUTTON) || input.equals(CANNOT_DIVIDE_BY_ZERO) || input.equals(OUT_OF_RANGE))){
            inputText.setText(number+"");
            operationText.setText(R.string.default_operation);
        }
        else if(input.equals("0") || previousBtn.equals(OPERATOR))
            inputText.setText(number+"");
        else inputText.setText(input + number);
        previousBtn = NUMBER;
    }
    private void DBZandOORCheck(){
        if(inputText.getText().toString().equals(CANNOT_DIVIDE_BY_ZERO) || inputText.getText().toString().equals(OUT_OF_RANGE)){
            cBtnOnClick(findViewById(androidx.appcompat.R.id.content));
            enableOperatorBtn();
        }
    }

}