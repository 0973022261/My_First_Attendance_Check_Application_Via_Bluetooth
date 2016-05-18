package com.example.inhm.project_heatcon;

/**
 * Created by inhm on 2016-05-12.
 */
public class Lecture {
        String lecture_number="";
        String lecture_name="";
        String lecture_week="";
        String lecture_date="";
        String lecture_major="";
        String lecture_minor="";
        String lecture_start_time="";
        public Lecture(String lecture_number,String lecture_name,String lecture_week,String lecture_date,String lecture_major,String lecture_minor,String lecture_start_time) {
            this.lecture_number = lecture_number;
            this.lecture_name = lecture_name;
            this.lecture_week = lecture_week;
            this.lecture_date = lecture_date;
            this.lecture_major = lecture_major;
            this.lecture_minor = lecture_minor;
            this.lecture_start_time = lecture_start_time;
        }
        public Lecture() {}
}
