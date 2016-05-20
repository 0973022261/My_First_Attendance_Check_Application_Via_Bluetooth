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

public class Late extends AppCompatActivity {
    TextView textView_name;
    TextView textView;
    String[] strr;
    Intent intent_get;
    String st;
    String str_absent;
    MyAdapterLate adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_late);

        /**
         * 메모리에 올려진 텍스트를 변수에 저장합니다.
         */
        textView = (TextView)findViewById(R.id.text_late);
        textView_name = (TextView)findViewById(R.id.textView_name);

        /**
         * 인텐트를 받아옵니다.
         */
        intent_get = getIntent();

        /**
         * 받아온 인텐트를 텍스트뷰의 텍스트로 설정합니다.
         */
        textView_name.setText(intent_get.getStringExtra("Lecture_name"));
        String str = (String) intent_get.getSerializableExtra("request_late");

        if(str == null){
            textView.setText("지각한 적이 없습니다.");
        }else {
            st = "";
            strr = str.split(",");
            adapter = new MyAdapterLate(getApplicationContext(), R.layout.item_list_absent, strr);
            listView = (ListView) findViewById(R.id.late_list);
            listView.setAdapter(adapter);
        }
    }

    class MyAdapterLate extends BaseAdapter {
        Context context;
        TextView textView_date;
        int layout;
        LayoutInflater inflater;
        String[] absent_date;

        public MyAdapterLate(Context context, int layout, String[] strr) {
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
            str_absent = strr[position];
            textView_date.setText(str_absent);
            return convertView;
        }
    }
}
