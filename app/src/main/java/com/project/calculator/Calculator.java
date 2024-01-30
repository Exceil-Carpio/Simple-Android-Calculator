package com.project.calculator;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Calculator extends AppCompatActivity{

    public static int numOfOpenParen, numOfCloseParen, operandNum;
    public static List<String> infixExpression = new ArrayList<>();
    public static List<String> postfixExpression = new ArrayList<>();
    public static boolean solved;

    TextView txtPrevEquation, txtResult;
    Button btnClearAll, btnOpenParenthesis, btnCloseParenthesis, btnExponent, btnEquals, btnDecimal;
    Button btnDivision, btnMultiplication, btnAddition, btnSubtraction;
    Button btnNum1, btnNum2, btnNum3, btnNum4, btnNum5, btnNum6, btnNum7, btnNum8, btnNum9, btnNum0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        txtPrevEquation = findViewById(R.id.txtPrevEquation);
        txtResult = findViewById(R.id.txtResult);
        btnClearAll = findViewById(R.id.btnClear);
        btnOpenParenthesis = findViewById(R.id.btnOpenParenthesis);
        btnCloseParenthesis = findViewById(R.id.btnClosingParenthesis);
        btnExponent = findViewById(R.id.btnExponent);
        btnDecimal = findViewById(R.id.btnDecimal);
        btnEquals = findViewById(R.id.btnEquals);
        btnDivision = findViewById(R.id.btnDivision);
        btnMultiplication = findViewById(R.id.btnMultiplication);
        btnSubtraction = findViewById(R.id.btnSubtraction);
        btnAddition = findViewById(R.id.btnAddition);
        btnNum1 = findViewById(R.id.btnNum1);
        btnNum2 = findViewById(R.id.btnNum2);
        btnNum3 = findViewById(R.id.btnNum3);
        btnNum4 = findViewById(R.id.btnNum4);
        btnNum5 = findViewById(R.id.btnNum5);
        btnNum6 = findViewById(R.id.btnNum6);
        btnNum7 = findViewById(R.id.btnNum7);
        btnNum8 = findViewById(R.id.btnNum8);
        btnNum9 = findViewById(R.id.btnNum9);
        btnNum0 = findViewById(R.id.btnNum0);

        txtResult.setText("0");

        //Set up Click Events on all buttons

        btnClearAll.setOnClickListener(view -> {
            //Reset all variables, list, and labels to their initial values
            txtPrevEquation.setText("");
            txtResult.setText("0");
            infixExpression.clear();
            postfixExpression.clear();
            operandNum = 0;
            numOfCloseParen = 0;
            numOfOpenParen = 0;
            solved = false;
        });

        btnDivision.setOnClickListener(view ->
                txtResult.setText(addCharEquation("/", txtResult.getText().toString())));

        btnAddition.setOnClickListener(view ->
                txtResult.setText(addCharEquation("+", txtResult.getText().toString())));

        btnSubtraction.setOnClickListener(view ->
                txtResult.setText(addCharEquation("-", txtResult.getText().toString())));

        btnMultiplication.setOnClickListener(view ->
                txtResult.setText(addCharEquation("x", txtResult.getText().toString())));

        btnExponent.setOnClickListener(view ->
                txtResult.setText(addCharEquation("^", txtResult.getText().toString())));

        btnNum1.setOnClickListener(view ->
                txtResult.setText(addCharEquation("1", txtResult.getText().toString())));

        btnNum2.setOnClickListener(view ->
                txtResult.setText(addCharEquation("2", txtResult.getText().toString())));

        btnNum3.setOnClickListener(view ->
                txtResult.setText(addCharEquation("3", txtResult.getText().toString())));

        btnNum4.setOnClickListener(view ->
                txtResult.setText(addCharEquation("4", txtResult.getText().toString())));

        btnNum5.setOnClickListener(view ->
                txtResult.setText(addCharEquation("5", txtResult.getText().toString())));

        btnNum6.setOnClickListener(view ->
                txtResult.setText(addCharEquation("6", txtResult.getText().toString())));

        btnNum7.setOnClickListener(view ->
                txtResult.setText(addCharEquation("7", txtResult.getText().toString())));

        btnNum8.setOnClickListener(view ->
                txtResult.setText(addCharEquation("8", txtResult.getText().toString())));

        btnNum9.setOnClickListener(view ->
                txtResult.setText(addCharEquation("9", txtResult.getText().toString())));

        btnNum0.setOnClickListener(view ->
                txtResult.setText(addCharEquation("0", txtResult.getText().toString())));

        btnOpenParenthesis.setOnClickListener(view ->
                txtResult.setText(addCharEquation("(", txtResult.getText().toString())));

        btnCloseParenthesis.setOnClickListener(view ->
                txtResult.setText(addCharEquation(")", txtResult.getText().toString())));

        btnDecimal.setOnClickListener(view ->
                txtResult.setText(addCharEquation(".", txtResult.getText().toString())));

        btnEquals.setOnClickListener(view -> {
            String temp = txtResult.getText().toString();

            //Adds missing closing parenthesis to equation
            while(numOfCloseParen < numOfOpenParen){
                infixExpression.add(")");
                temp = temp + ")";
                numOfCloseParen++;
            }

            //Show previous equation to the txtPrevEquation textview and show the result to the result text view
            txtPrevEquation.setText(temp);
            txtResult.setText(getResult());

            //reset all variables and set solved to true
            operandNum = 0;
            numOfCloseParen = 0;
            numOfOpenParen = 0;
            solved = true;
        });
    }

    //A method that concatenates the operator/number/parenthesis that the user inputted through the buttons to the equation
    public static String addCharEquation(String text, String equation){

        //Check if user has already used the calculator to solve an equation
        //If yes, then check if the input is a number
        //if its a number, replace it with the input
        if(solved && isNum(text)){
            infixExpression.remove(0);
            equation = "0";
        }

        solved = false;

        //Get the last character of the equation
        String lastChar = equation.substring(equation.length()-1);

        //Check if user is in the initial character of the equation
        if(equation.equals("0")){

            //If character to be inputted is either an operator or a closing parenthesis, return the equation as is
            if(text.equals(")") || isOperator(text)){

                //If character to be inputted is an opening parenthesis, return the opening parenthesis
                if(!(text.equals("("))){
                    return equation;
                }else{
                    numOfOpenParen++;
                    operandNum++;
                }
            }

            //If first input is a dot, add a 0 at the start before it before concatenating it
            if(text.equals(".")){
                equation = "0" + text;
            }else if(text.equals("0")){
                //if its a zero, return the equation as is
                return equation;
            }else{
                //if its a any number, concatenate the number to the operand
                equation = "" + text;
            }

            infixExpression.add(equation);
            return equation;
        }

        //Prevent user from inputting succeeding operators
        if(isOperator(text)){
            if(isOperator(lastChar) && !(text.equals("("))) {
                return equation;
            }
        }

        //checks if input is a number or a closing braces
        //if any of the condition is true, automatically concatenate a "x" to the equation before the opening parenthesis
        if(text.equals("(")){
            numOfOpenParen++;
            if(isNum(lastChar) || lastChar.equals(")")){
                equation = equation + "x";
                infixExpression.add("x");
                operandNum++;
            }
        }

        //checks if the number of closing parenthesis exceeds the number of opening parenthesis
        //checks if the last character in the equation is an operator
        //if any of the condition is true, return the equation as is
        if(text.equals(")")){
            if(isOperator(lastChar) || numOfCloseParen >= numOfOpenParen){
                return equation;
            }

            //if its a dot, concatenate a 0 to the start of the operand
            if(lastChar.equals(".")){
                equation += 0;
                infixExpression.set(operandNum, infixExpression.get(operandNum) + "0");
            }
            numOfCloseParen++;
        }

        //Concatenates a "x" to the equation if the last character of the equation is a closing brace and input is a number
        if(lastChar.equals(")") && (isNum(text) || text.equals("."))){
            equation = equation + "x";
            infixExpression.add("x");
            operandNum++;
        }

        //If input is a dot, check if the last character is a number/dot, and if its a operator/closing brace

        //if number/dot, check if the operand already have a dot, and if not concatenate it and return the expression
        if(text.equals(".") && (isNum(lastChar) || lastChar.equals("."))){
            if(!infixExpression.get(operandNum).contains(".")){
                String temp = infixExpression.get(operandNum) + text;
                infixExpression.set(operandNum, temp);
                temp = equation + text;
                return temp;
            }else{
                return equation;
            }
        }else if(text.equals(".") && (isOperator(lastChar) || lastChar.equals(")"))){
            //if operator/closing brace, concatenate a 0 to the start of the operand
            infixExpression.add("0");
            equation += "0";
        }

        //Concatenates the inputted number if previous char and inputted characters are both numbers
        //Or if the previous char is a dot
        if(isNum(text) || text.equals(".")){
            if(operandNum >= infixExpression.size()){
                infixExpression.add(text);
            }else{
                String cont = infixExpression.get(operandNum) + text;
                infixExpression.set(operandNum, cont);
            }
        }else{

            //add the operator into the infix expression array list
            infixExpression.add(text);

            //sets which index of the infix array list is the current operand on in the next input
            if((isOperator(lastChar) && text.equals("(")) || (isOperator(text) && lastChar.equals(")"))){
                operandNum++;
            }else{
                operandNum += 2;
            }
        }

        //Concatenate the character to the equation and return it
        return equation+text;
    }

    //Function to check if a specific character is an operator
    public static boolean isOperator(String operator){
        switch(operator){
            case "(":
            case "^":
            case "+":
            case "-":
            case "x":
            case "/":
                return true;
        }
        return false;
    }

    //A function to check if a specific character is a number
    public static boolean isNum(String num){
        try{
            Float.parseFloat(String.valueOf(num));
        }catch(Exception e){
            return false;
        }
        return true;
    }

    //Calculates a mathematical problem of two operands based on passed operator
    public static float calculate(String operand1, String operator, String operand2){
        float oper1 = Float.parseFloat(operand1);
        float oper2 = Float.parseFloat(operand2);
        switch(operator){
            case "+":
                return oper1 + oper2;
            case "-":
                return oper1 - oper2;
            case "/":
                return oper1 / oper2;
            case "x":
                return oper1 * oper2;
            case "^":
                return (int) Math.pow(oper1, oper2);
        }
        return 0;
    }

    //Converts infix expression to postfix to establish operator precedence during calculations.
    public static void infixToPostfix() {
        String symbol;
        Stack<String> operators = new Stack<>();

        //Loops through all elements in the infix expression array list
        for (int i = 0; i < infixExpression.size(); ++i) {
            symbol = infixExpression.get(i);

            //if its an operand, add the operand to the postfix expression array list
            if (isNum(symbol)){
                postfixExpression.add(symbol);
            }else if (symbol.equals("(")){

                //if it is an open parenthesis, push it to the operator stack
                operators.push(symbol);
            } else if (symbol.equals(")")){

                //if it is a close parenthesis, pop all elements in the stack until an open parenthesis is found
                while (!operators.peek().equals("(")) {
                    postfixExpression.add(operators.pop());
                }

                //Remove the open parenthesis from stack
                operators.pop();
            } else {

                //If the operator in the stack has lower precedence than the one in the symbol variable,
                //pop if out of the stack and add the operator to the postfix expression array.
                while (!operators.isEmpty() && !(operators.peek().equals("(")) && getPrecedence(symbol) <= getPrecedence(operators.peek())){
                    postfixExpression.add(operators.pop());
                }

                //Push current operator to the stack
                operators.push(symbol);
            }
        }

        //pop out all remaining operators left in the stack
        while (!operators.isEmpty()){
            postfixExpression.add(operators.pop());
        }
    }

    //A function to calculate overall expression using postfix to infix logic
    public static String getResult(){
        float result;
        infixToPostfix();
        Stack<String> operands = new Stack<>();

        //Loops through all elements of the postfix expression array list
        for (int i = 0; i < postfixExpression.size(); i++)
        {
            String operator = postfixExpression.get(i);
            //If it is an operator, pop the previous two operands from stack
            if (isOperator(operator))
            {
                String operand2 = operands.pop();
                String operand1 = operands.pop();

                //Pass both operands and operator to calculate function and push result back to the stack
                result = calculate(operand1, operator, operand2);
                operands.push(Float.toString(result));
            }else{

                //if it is an operand, push it back to the stack
                operands.push(operator);
            }
        }

        //Clear both array lists and add the result to the infix expression to get ready for the next calculation
        infixExpression.clear();
        postfixExpression.clear();
        infixExpression.add(operands.peek());

        //Get the final result from the stack
        result = Float.parseFloat(operands.pop());

        //Return result as String
        return Float.toString(result);
    }

    //A function that determines the order of precedence of a specific operator
    static int getPrecedence(String operator)
    {
        switch (operator) {
            case "-":
                return 1;
            case "+":
                return 2;
            case "/":
                return 3;
            case "x":
                return 4;
            case "^":
                return 5;
        }
        return 0;
    }
}