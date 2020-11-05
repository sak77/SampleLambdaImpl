package com.sshriwas.samplelambdaimpl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
    The purpose of this project to try out different Lambda expressions which were introduced in Java 8
 **/
public class MainActivity extends AppCompatActivity {

    private String mAnimal = "Rhino";

    //Functional interface with method get
    /*
    A functional interface is an interface that contains only one abstract method.
    They can have only one functionality to exhibit.
    From Java 8 onwards, lambda expressions can be used to represent the instance of a functional interface.
    A functional interface can have any number of default methods.
    Runnable, ActionListener, Comparable are some of the examples of functional interfaces.

    There’s an annotation introduced- @FunctionalInterface which can be used for compiler level
    errors when the interface you have annotated is not a valid Functional Interface.

    Although functional interface allows only a single abstract method. However, it can still
    declare abstract methods from java.lang.Object class and still be considered a valid
    Functional interface. For eg. below we can add methods such as
    String toString()
    boolean equals(Object o)
    to the interface but still we do not get any Compile error warnings from @FunctionalInterface
     */
    @FunctionalInterface
    interface Circle{
        double get(double radius);  //abstract method
        //Default method uses the default keyword in the method declaration.
        //Also it can ahve its own implementation inside the interface.
        default void doSomeWork(){
            Log.d("", "");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnClick = findViewById(R.id.btnClick);

/**************************************** Example 1 ********************************************************/
        //Handling onClick the old way....using anonymous class
/*
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
*/
        //Using ONLY Lambda expression
/*
        btnClick.setOnClickListener(view -> {
            Toast.makeText(this, "Clicked via Lambda!", Toast.LENGTH_SHORT).show();
        });
*/

        //Implementing interface using Lambda
        //Lambdas can be used with functional interface where they allow to define the method without the code overhead
        MyImplClass.MyButtonClickListener myButtonClickListener = (s) -> {
            //The following statement(s) are implicitly used to define body of method myButtonClicked..
            Toast.makeText(this, "Called Lambda via Interface" + s, Toast.LENGTH_SHORT).show();
            //lambdawithThread();
        };

        //Assigning interface method as onclicklistener
        btnClick.setOnClickListener(view -> myButtonClickListener.myButtonClicked("Saket"));

/**********************************************************************************************/

/*************************************** Example 2 ********************************************/
        /*
        Now we will show how we can pass function body as arguments to another method using lambdas
        In this example we will create a functional interface called Circle which has a method get().
        Now we define 2 implementations of the interface using lambdas which are in turn passed to
        a third method that displays the result. The purpose of this example is to show how easily it can
        be achieved using Lambdas
         */
        //Here we define 2 implementations of the get method of Circle interface
        //Using get to define area of circle
        //Circle circle_area = (r) -> Math.PI * r * r;
        Circle circle_area = radius -> Math.PI * radius * radius;
        //Using get to define circumference of circle
        Circle circle_circumference = (r) -> 2 * Math.PI * r;

        //Now based on type of click call the corresponding implementation of Circle interface
        Button btnArea = findViewById(R.id.btnArea);
        btnArea.setOnClickListener(v -> displayResult(circle_area, 10));

        Button btnCircumference = findViewById(R.id.btnCircumfrence);
        btnCircumference.setOnClickListener(v -> displayResult(circle_circumference, 10));

/****************************************************************************************************/
    }

    private void lambdawithThread(){
/*
        Here we are using lambdas to replace anonymous method RUnnable inside instance of thread.
        Notice how the body of the lambda statement is implicitly used to define the body of the would-be run() inside Runnable
        Another important thing to observe is how we are using 'this' to refer to instance of the MainActivity class
        In case of anonymous methods, this keyword would have referred to the anonymous class itself.
        One more thing, keeping TAG empty in logs will not allow them to be printed in the Logcat
*/
        new Thread(() -> {
            String strLocalAnimal = "Bear";
            Log.v("TESTING","Local animal "+ strLocalAnimal);
            Log.v("TESTING","Foreign animal " + this.mAnimal);
        }).start();
    }

    //The method displays the out put of circle interface implementation of get method using radius as input
    private void displayResult(Circle circle, double radius){
        circle.get(radius);
    }
}

class MyImplClass {
    //Functional interface is an interface with only one abstract method...
    // similar as View.OnClickListener interface. <
    public interface MyButtonClickListener {
        void myButtonClicked(String value);
    }
}