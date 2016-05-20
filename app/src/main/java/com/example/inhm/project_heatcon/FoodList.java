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
    String[] food_list_split_by_slash;
    String[] name;
    String[] price;
    Intent intent_get;
    MyAdapterFoodList adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        /**
         * 메모리에 올린 텍스트뷰를 변수에 저장합니다.
         */
        textView_food_name = (TextView) findViewById(R.id.food_name);
        textView_food_price = (TextView) findViewById(R.id.food_price);

        /**
         * 인텐트를 받아옵니다.
         */
        intent_get = getIntent();

        /**
         * 받아온 인텐트를 변수에 저장합니다.
         */
        str = (String) intent_get.getSerializableExtra("food_list");


        /**
         * 음식에대한 리스트 데이터를 token(" / "," , ")로 분류합니다.
         * 분류는 음식 이름, 음식 가격 으로 나누게 됩니다.
         */
        food_list_split_by_slash = str.split("/");
        name = food_list_split_by_slash[0].split(",");
        price = food_list_split_by_slash[1].split(",");

        /**
         * 음식을 담고있는 ArrayList를 가져옵니다.
         */
        arrayList =  new ArrayList<Food>();

        if(str == null) {
        }else {
            /**
             * 음식 에대한 정보를 arrayList에 각각 저장합니다.
             */
            for(int i = 0 ; i <name.length;i++ ){
                arrayList.add(new Food(name[i],price[i]));
            }
        }

        /**
         * 설정한 어댑터에 음식정보를 붙여 띄어줍니다.
         */
        adapter = new MyAdapterFoodList(getApplicationContext(), R.layout.item_list_food);
        listView = (ListView) findViewById(R.id.list_food);
        listView.setAdapter(adapter);
    }
    class MyAdapterFoodList extends BaseAdapter {
        Context context;
        int layout;
        LayoutInflater inflater;
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
