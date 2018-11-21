package com.aidana.inao;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.net.*;
import java.io.*;
import java.net.ServerSocket;
import android.os.StrictMode;

public class MainActivity extends AppCompatActivity {

    private PrintWriter out;
    private Button btn_server, btn_stop, btn_listener;
    private CharSequence sendText;

    protected int my_backlog = 5;
    protected ServerSocket serverSocket;
    Socket Robot = null;


    TextView currEmotion, naoText;
    EditText inputText;
    ImageView happyImgView, sadImgView, surprisedImgView, scaredImgView, interestedImgView,
                angryImgView, boredImgView, shyImgView, followmeImg, standUpImg,
    sitDownImg, macarenaView, touchmeImg, taichi, chasiki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_server = (Button) findViewById(R.id.btn_server);
        btn_stop = (Button) findViewById(R.id.btn_stop);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            serverSocket = new ServerSocket(7200, my_backlog);
            Log.d("m", "LISTENING TO PORT ");
            System.out.println("TCP socket listening on port " + 7200);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SecurityException se) {
            se.printStackTrace();
        }

        startServer();
    }


    public void startServer() {

        btn_server.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        while (true) {
                            try {
                                Socket socket = serverSocket.accept();
                                Robot = socket;
                                System.out.println("CLIENT: " +Robot);

                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            } catch (SecurityException se) {
                                se.printStackTrace();
                            }
                        }

                    }
                }).start();

            }
        });

    }

    private class SocketServerReplyThread extends AsyncTask<Void, Void, Void> {

        private Socket hostThreadSocket;
        private String msg;

        SocketServerReplyThread(Socket socket, CharSequence sendText) {
            hostThreadSocket = socket;
            msg = sendText.toString();
        }

        protected Void doInBackground(Void... voids) {
            OutputStream outputStream;
            String msgReply = msg;

            try {
                outputStream = hostThreadSocket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(msgReply);
                printStream.close();

            } catch (IOException e) {
            }
            return null;
        }
    }


    public void moveToKaz(View v) {
        setContentView(R.layout.kazmode);
        startKazMode();
    }

    public void moveToRus(View v) {
        setContentView(R.layout.rusmode);
        startKazMode();
    }

    public void startKazMode() {

        currEmotion = (TextView) findViewById(R.id.currEmotion);

        happyImgView = (ImageView) findViewById(R.id.happyImgView);
        sadImgView = (ImageView) findViewById(R.id.sadImgView);
        surprisedImgView = (ImageView) findViewById(R.id.surprisedImgView);
        scaredImgView = (ImageView) findViewById(R.id.scaredImgView);
        angryImgView = (ImageView) findViewById(R.id.angryImgView);
        shyImgView = (ImageView) findViewById(R.id.shyImgView);
        interestedImgView = (ImageView) findViewById(R.id.interestedImgView);
        boredImgView = (ImageView) findViewById(R.id.boredImgView);

        followmeImg = (ImageView) findViewById(R.id.followmeImg);
        touchmeImg = (ImageView) findViewById(R.id.touchmeImg);
        standUpImg = (ImageView) findViewById(R.id.standUpImg);
        sitDownImg = (ImageView) findViewById(R.id.sitDownImg);
        macarenaView = (ImageView) findViewById(R.id.macarenaView);
        taichi = (ImageView) findViewById(R.id.taichi);
        chasiki = (ImageView) findViewById(R.id.chasiki);


        happyImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Happy");
                sendText = "1";
                showEmtions(Robot, sendText);
                //Toast.makeText(MainActivity.this, "CLICKED!", Toast.LENGTH_LONG).show();
            }
        });

        sadImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Sad");
                sendText = "2";
                showEmtions(Robot, sendText);
            }
        });

        surprisedImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Surprised");
                sendText = "3";
                showEmtions(Robot, sendText);
            }
        });

        scaredImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Scared");
                sendText = "4";
                showEmtions(Robot, sendText);
               // Toast.makeText(MainActivity.this, "You clicked on SCARED", Toast.LENGTH_LONG).show();
            }
        });


        angryImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Angry");
                sendText = "5";
                showEmtions(Robot, sendText);
            }
        });

        shyImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Shy");
                sendText = "6";
                showEmtions(Robot, sendText);
            }
        });


        interestedImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Interested");
                sendText = "7";
                showEmtions(Robot, sendText);
            }
        });

        boredImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Bored");
                sendText = "8";
                showEmtions(Robot, sendText);
            }
        });


        followmeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currEmotion.setText("Follow me");
                sendText = "9";
                showEmtions(Robot, sendText);
            }
        });


        touchmeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currEmotion.setText("Touch me");
                sendText = "10";
                showEmtions(Robot, sendText);
            }
        });


        standUpImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currEmotion.setText("Standing");
                sendText = "11";
                showEmtions(Robot, sendText);
            }
        });

        sitDownImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currEmotion.setText("Sitting");
                sendText = "12";
                showEmtions(Robot, sendText);
            }
        });

        macarenaView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                currEmotion.setText("Macarena");
                sendText = "13";
                showEmtions(Robot, sendText);
            }
        });


        taichi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currEmotion.setText("Tai chi");
                sendText = "14";
                showEmtions(Robot, sendText);
            }
        });


        chasiki.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                currEmotion.setText("Chasiki");
                sendText = "15";
                showEmtions(Robot, sendText);
            }
        });




    }


    public void onEndGame(View v) {

        currEmotion.setText("Game ending");
        sendText = "17";
        showEmtions(Robot, sendText);

    }

    public void onStartGame(View v) {
        currEmotion.setText("Game starting...");
        sendText = "16";
        showEmtions(Robot, sendText);
    }

    public void showEmtions(Socket Robot, CharSequence sendText) {
        sendText = sendText + "\n";
        if (Robot != null) {
            System.out.print("MESSAGE: " + sendText);
            SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(Robot, sendText);
            socketServerReplyThread.doInBackground();
        }
    }

    public void naoSays(View v) {
        naoText.setText(inputText.getText());
        sendText = inputText.getText();
    }

    public void onRusMode(View v) {
        setContentView(R.layout.rusmode);
    }

    public void onKazMode(View v) {
        setContentView(R.layout.kazmode);
    }

    public void stopServer(View v) {
        try {
            stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onStopServer(View v) {
        try {
            stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws IOException {
        out.close();
        Robot.close();
    }


}
