package com.ilnas.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuizzActivity extends AppCompatActivity implements View.OnClickListener {

    TextView TotalQuestions;
    TextView QuestionText;
    Button AnsA, AnsB, AnsC,AnsD;
    Button Submit;

    List<Integer> indexes;

    int Score = 0;
    int QuestionsCount = QuestionAnswer.Questions.length;
    int CurrentQuestionIndex = 0;
    String SelectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizz_activity);

        TotalQuestions = findViewById(R.id.TotalQuestions);
        QuestionText = findViewById(R.id.Question);
        AnsA = findViewById(R.id.AnsA);
        AnsB = findViewById(R.id.AnsB);
        AnsC = findViewById(R.id.AnsC);
        AnsD = findViewById(R.id.AnsD);
        Submit = findViewById(R.id.SubmitButton);

        AnsA.setOnClickListener(this);
        AnsB.setOnClickListener(this);
        AnsC.setOnClickListener(this);
        AnsD.setOnClickListener(this);
        Submit.setOnClickListener(this);

        TotalQuestions.setText("TOTAL QUESTIONS : "+ QuestionsCount );

        FillIndexArray();

        LoadNewQuestion();
    }

    @Override
    public void onClick(View view) {

        AnsA.setBackgroundColor(Color.WHITE);
        AnsB.setBackgroundColor(Color.WHITE);
        AnsC.setBackgroundColor(Color.WHITE);
        AnsD.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;
        if(clickedButton.getId() == R.id.SubmitButton){

            if(SelectedAnswer.equals(QuestionAnswer.CorrectAnswers[CurrentQuestionIndex]))
                Score++;


            LoadNewQuestion();

        }else{
            SelectedAnswer = clickedButton.getText().toString();
            clickedButton.setBackgroundColor(Color.MAGENTA);
        }
    }

    void FillIndexArray()
    {
        int max = QuestionsCount;
        indexes = new ArrayList<Integer>(max);
        for(int c = 0; c < max; c++)
        {
            indexes.add(c);
        }
    }

    void LoadNewQuestion()
    {
        /*
         if(CurrentQuestionIndex == QuestionsCount){
             FinishQuiz();
             return;
         }*/
        if(indexes.isEmpty()){
            FinishQuiz();
            return;
        }

        int randomIndex = (int)((double)indexes.size() * Math.random());
        indexes.remove(randomIndex);
        CurrentQuestionIndex = randomIndex;
        QuestionText.setText(QuestionAnswer.Questions[CurrentQuestionIndex]);
        AnsA.setText(QuestionAnswer.Choices[CurrentQuestionIndex][0]);
        AnsB.setText(QuestionAnswer.Choices[CurrentQuestionIndex][1]);
        AnsC.setText(QuestionAnswer.Choices[CurrentQuestionIndex][2]);
        AnsD.setText(QuestionAnswer.Choices[CurrentQuestionIndex][3]);
    }

    void FinishQuiz(){
        String passStatus = "";

        if(Score>QuestionsCount*0.60)
            passStatus = "Passed";
        else
            passStatus = "failed";

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is "+ Score+" out of "+QuestionsCount)
                .setNegativeButton("RESTART",(dialogInterface, i) -> RestartQuiz() )
                .setCancelable(false)
                .show();
    }

    void RestartQuiz(){
        Score = 0;
        CurrentQuestionIndex = 0;
        FillIndexArray();
        LoadNewQuestion();
    }
}