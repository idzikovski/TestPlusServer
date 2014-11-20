package com.testplus.dzikovski.ivan.testserver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.testplus.dzikovski.ivan.test.model.Question;

import java.util.ArrayList;

/**
 * Created by Ivan on 18.11.2014.
 */
public class QuestionListAdapter extends ArrayAdapter<Question> {
    private final Context context;
    private final ArrayList<Question> values;

    public QuestionListAdapter(Context context, ArrayList<Question> values) {
        super(context, R.layout.question_list_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.question_list_item, parent, false);
        TextView tv=(TextView) rowView.findViewById(R.id.text1);
        tv.setText(values.get(position).getQuestion());
        return rowView;
    }
}
