package com.example.inhm.project_heatcon;

import android.os.Looper;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * Created by inhm on 2016-05-14.
 */
public class ServerThread extends Thread{


    Socket socket;                              //소켓통신을 위한 소켓 객체 변수 생성
    String host = "54.199.244.195";               //소켓 통신을 위한 서버 host 주소
    int port = 8000;                            //소켓 통신을 위한 서버 port 번호

    private int REQUEST_NUMBER;

    String student_name;
    String student_number;
    String lecture_number;
    String check_attendance;
    String student_password;

    String attendance_number;
    String request_lecture_list;


    boolean server_request_login = false;

    ServerThread(){};
    ServerThread(int number){
        this.REQUEST_NUMBER = number;
    };
    ServerThread(int number,String student_number){
        this.student_number = student_number;
        this.REQUEST_NUMBER = number;
    };

    ServerThread(int number,String student_number, String student_password,boolean check){
        this.REQUEST_NUMBER = number;
        this.student_number = student_number;
        this.student_password = student_password;
    };
    ServerThread(int number,String student_number, String lecture_number){
        this.REQUEST_NUMBER = number;
        this.student_number = student_number;
        this.lecture_number = lecture_number;
    };
    ServerThread(int number,String student_number,String lecture_number,String check_attendance){
        this.student_number = student_number;
        this.lecture_number = lecture_number;
        this.check_attendance = check_attendance;
        this.REQUEST_NUMBER = number;
    };

    public Boolean request_attendance(){
        return server_request_login;
    }
    public String request_lecture_list() {
        Log.d("LOGINING",""+request_lecture_list);
        return request_lecture_list;

    }
    public String request_attendance_number() {
        return attendance_number;
    }

    public void run() {
        switch(REQUEST_NUMBER){

            case 1:
                //로그인 정보 확인하기
                try {
                Looper.prepare();

                Log.d("Run", "서버접속");
                socket = new Socket(host, port);

                Log.d("Run", socket.toString());
                String output;
                output = "1," + student_number+","+student_password;
                DataOutputStream outstream = new DataOutputStream(socket.getOutputStream());
                outstream.writeUTF(output);
                outstream.flush();
                DataInputStream instream = new DataInputStream(socket.getInputStream());
                server_request_login = instream.readBoolean();

                instream.close();
                outstream.close();
                socket.close();
                Looper.loop();
            } catch (Exception e) {
                Log.d("Run", e.toString());
            }
                break;
            case 2:
                //여태까지 출석정보 확인하기
                try {
                    Looper.prepare();

                    Log.d("Run", "서버접속");
                    socket = new Socket(host, port);

                    String output;
                    output = "2," + student_number+","+lecture_number;
                    DataOutputStream outstream = new DataOutputStream(socket.getOutputStream());
                    outstream.writeUTF(output);
                    Log.d("Run", outstream.toString());
                    outstream.flush();

                    DataInputStream instream = new DataInputStream(socket.getInputStream());
                    attendance_number = instream.readUTF();

                    instream.close();
                    outstream.close();
                    socket.close();
                    Looper.loop();
                } catch (Exception e) {
                    Log.d("Run", e.toString());
                }
                break;
            case 3:
                //중간고사 점수확인
                try {
                    Looper.prepare();

                    Log.d("Run", "서버접속");
                    socket = new Socket(host, port);

                    Log.d("Run", socket.toString());
                    String output;
                    output = "3," + student_number+","+lecture_number;
                    DataOutputStream outstream = new DataOutputStream(socket.getOutputStream());
                    outstream.writeUTF(output);
                    Log.d("Run", outstream.toString());
                    outstream.flush();

                    DataInputStream instream = new DataInputStream(socket.getInputStream());
//                    input = instream.readUTF();
//                    Log.d("Run", "서버로 부터 받은 데이터" + input.toString());

                    instream.close();
                    outstream.close();
                    socket.close();
                    Looper.loop();
                } catch (Exception e) {
                    Log.d("Run", e.toString());
                }
                break;

            case 4:
                //기말고사 점수 확인
                try {
                    Looper.prepare();

                    Log.d("Run", "서버접속");
                    socket = new Socket(host, port);

                    Log.d("Run", socket.toString());
                    String output;
                    output = "4," + student_number+","+student_name;
                    DataOutputStream outstream = new DataOutputStream(socket.getOutputStream());
                    outstream.writeUTF(output);
                    Log.d("Run", outstream.toString());
                    outstream.flush();

                    DataInputStream instream = new DataInputStream(socket.getInputStream());
//                    input = instream.readUTF();
//                    Log.d("Run", "서버로 부터 받은 데이터" + input.toString());

                    instream.close();
                    outstream.close();
                    socket.close();
                    Looper.loop();
                } catch (Exception e) {
                    Log.d("Run", e.toString());
                }
                break;

            case 5:
                //수강과목 목록 받기
                try {
                    Looper.prepare();

                    Log.d("Run", "서버접속");
                    socket = new Socket(host, port);

                    Log.d("Run", socket.toString());
                    String output;
                    output = "5," + student_number;
                    DataOutputStream outstream = new DataOutputStream(socket.getOutputStream());
                    outstream.writeUTF(output);
                    Log.d("Run", outstream.toString());
                    outstream.flush();

                    DataInputStream instream = new DataInputStream(socket.getInputStream());
                    request_lecture_list = instream.readUTF();
                    Log.d("LOGINING", "서버로 부터 받은 데이터" + request_lecture_list);

                    instream.close();
                    outstream.close();
                    socket.close();
                    Looper.loop();
                } catch (Exception e) {
                    Log.d("Run", e.toString());
                }
                break;

            case 6:
                //갱신된 출석정보 전달하기
                try {
                    Looper.prepare();

                    Log.d("Run", "서버접속");
                    socket = new Socket(host, port);

                    Log.d("Run", socket.toString());
                    String output;
                    output = "6," + student_number+","+lecture_number+","+check_attendance;
                    DataOutputStream outstream = new DataOutputStream(socket.getOutputStream());
                    outstream.writeUTF(output);
                    Log.d("Run", outstream.toString());
                    outstream.flush();

//                    DataInputStream instream = new DataInputStream(socket.getInputStream());
//                    input = instream.readUTF();
//                    Log.d("Run", "서버로 부터 받은 데이터" + input.toString());

//                    instream.close();
                    outstream.close();
                    socket.close();
                    Looper.loop();
                } catch (Exception e) {
                    Log.d("Run", e.toString());
                }
                break;
            default :
                break;
        }
    }
 }