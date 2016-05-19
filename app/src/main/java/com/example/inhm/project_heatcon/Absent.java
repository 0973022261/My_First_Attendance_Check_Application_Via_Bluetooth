package com.example.inhm.project_heatcon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Absent extends AppCompatActivity {

    TextView textView;
    TextView textView_name;
    String[] strr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent);

        textView = (TextView) findViewById(R.id.text_absent);
        textView_name = (TextView) findViewById(R.id.textView_name);
        Intent intent = getIntent();
        textView_name.setText(intent.getStringExtra("Lecture_name"));

        String str = (String) intent.getSerializableExtra("request_absent");
        if (str == null) {
            textView.setText("결석한 적이 없습니다.!");
        } else {
            String st = "";
            strr = str.split(",");
        }

        MyAdapterAbsent adapter = new MyAdapterAbsent(getApplicationContext(), R.layout.item_list_absent, strr);

        ListView listView = (ListView) findViewById(R.id.absent_list);
        listView.setAdapter(adapter);
    }

    class MyAdapterAbsent extends BaseAdapter {
        Context context;
        TextView textView_date;
        int layout;
        LayoutInflater inflater;
        String[] absent_date;

        public MyAdapterAbsent(Context context, int layout, String[] strr) {
            this.context = context;
            this.layout = layout;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.absent_date=strr;
        }

        public int getCount() {
            if (absent_date != null) {
                return absent_date.length;
            } else {
                return 0;
            }

        }

        public Object getItem(int position) {
            return absent_date[position];
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = inflater.inflate(layout, null);

            textView_date = (TextView) convertView.findViewById(R.id.absent_date);

            String str_absent = strr[position];

            textView_date.setText(str_absent);

            return convertView;
        }
    }
}
