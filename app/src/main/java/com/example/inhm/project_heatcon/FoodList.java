package com.example.inhm.project_heatcon;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodList extends AppCompatActivity {

    ArrayList<Food> arrayList;

    String str;
    TextView textView_food_name;
    TextView textView_food_price;
    String str_str;
    String[] name;
    String[] a;
    String[] price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        textView_food_name = (TextView) findViewById(R.id.food_name);
        textView_food_price = (TextView) findViewById(R.id.food_price);

        Intent intent = getIntent();
        str = (String) intent.getSerializableExtra("food_list");

        Log.d("음식1",""+str);
        arrayList =  new ArrayList<Food>();

        a = str.split("/");
        name = a[0].split(",");
        price = a[1].split(",");

        if(str == null) {
//            textView.setText("학식정보를 가져오지 못했습니다.");
        }else {
            for(int i = 0 ; i <name.length;i++ ){
                arrayList.add(new Food(name[i],price[i]));
//                str_str = str_str + "["+name[i] +"]"+ "-"+price[i]+"원"+"\n";
            }
//            textView.setText(str_str);
        }
        MyAdapterFoodList adapter = new MyAdapterFoodList(getApplicationContext(), R.layout.item_list_food);
        ListView listView = (ListView) findViewById(R.id.list_food);
        listView.setAdapter(adapter);
    }
    class MyAdapterFoodList extends BaseAdapter {
        Context context;
        int layout;
        LayoutInflater inflater;
        String[] absent_date;
        Food food;

        public MyAdapterFoodList(Context context, int layout) {
            this.context = context;
            this.layout = layout;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
                return arrayList.size();
        }

        public Object getItem(int position) {
            return arrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = inflater.inflate(layout, null);

            textView_food_name = (TextView) convertView.findViewById(R.id.food_name);
            textView_food_price = (TextView) convertView.findViewById(R.id.food_price);

            food = arrayList.get(position);

            textView_food_name.setText(food.food_name);
            textView_food_price.setText(food.food_price+"원");

            return convertView;
        }
    }
}
