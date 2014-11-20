package com.testplus.dzikovski.ivan.testserver;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.testplus.dzikovski.ivan.test.model.Answer;
import com.testplus.dzikovski.ivan.test.model.Question;

import java.util.ArrayList;


public class ChoseQuestionActivity extends Activity {

    ListView lvQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_question);

        lvQuestions=(ListView) findViewById(R.id.lvQuestions);

        //Adding dummy questions

        ArrayList<Question> questions=new ArrayList<Question>();


        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(new Answer("2, 2, 1, 1, 2", true));
        answers.add(new Answer("2, 2, 1, 2, 2", false));
        answers.add(new Answer("2, 1, 2, 2, 1", false));
        answers.add(new Answer("2, 1, 2, 2, 2", false));
        Question question = new Question("If the sequence of operations - push(1), push(2), pop, push(1), push(2), pop, pop, pop, push(2), pop are performed on a stack, the sequence of popped out values are ?", answers);

        questions.add(question);

        ArrayList<Answer> answers1 = new ArrayList<Answer>();
        answers1.add(new Answer("radix sort", true));
        answers1.add(new Answer("quick sort", false));
        answers1.add(new Answer("recursion", false));
        answers1.add(new Answer("depth first search", false));
        Question question1 = new Question("Queue can be used to implement ?", answers);

        questions.add(question1);

        ArrayList<Answer> answers2 = new ArrayList<Answer>();
        answers2.add(new Answer("400 names", true));
        answers2.add(new Answer("800 names", false));
        answers2.add(new Answer("750 names", false));
        answers2.add(new Answer("900 names", false));
        Question question2 = new Question("A machine took 200 sec to sort 200 names, using bubble sort. In 800 sec, it can approximately sort ?", answers);

        questions.add(question2);

        //ArrayAdapter<Question> arrayAdapter=new ArrayAdapter<Question>(this,android.R.layout.simple_list_item_1,questions);
        QuestionListAdapter arrayAdapter=new QuestionListAdapter(this,questions);
        lvQuestions.setAdapter(arrayAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chose_question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
