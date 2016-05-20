package com.example.inhm.project_heatcon;

/**
 * Created by inhm on 2016-05-12.
 */
public class Food {
    /**
     * 음식에 대한 정보를 담고있습니다.
     */
        String food_name="";
        String food_price="";
        public Food(String food_name, String food_price) {
            this.food_name = food_name;
            this.food_price = food_price;
        }
        public Food() {}
}
