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

        // this is for enabling asynchronous run of server threads in main activity
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            // server connecting to the port
            serverSocket = new ServerSocket(7200, my_backlog);
            Log.d("m", "LISTENING TO PORT ");
            System.out.println("TCP socket listening on port " + 7200);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SecurityException se) {
            se.printStackTrace();
        }

        // the server is ready to accept client sockets
        startServer();
    }


    public void startServer() {

        // when START button is clicked
        btn_server.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // create a thread for each socket
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        while (true) {
                            try {
                                Socket socket = serverSocket.accept(); // accepting the client
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

    // sending messages to the client asynchrounously
    private class SocketServerReplyThread extends AsyncTask<Void, Void, Void> {

        private Socket hostThreadSocket;
        private String msg;

        // server connects to socket to send it the message
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
                printStream.print(msgReply);  // actually sending the message to the client
                printStream.close();

            } catch (IOException e) {
            }
            return null;
        }
    }


    public void moveToKaz(View v) {
        setContentView(R.layout.kazmode);  // set current screen to kazmode
        startKazMode();
    }

    public void moveToRus(View v) {
        setContentView(R.layout.rusmode); // set current screen to rusmode
        startKazMode();
    }

    public void startKazMode() {

        currEmotion = (TextView) findViewById(R.id.currEmotion);

        // Emotions
        happyImgView = (ImageView) findViewById(R.id.happyImgView);
        sadImgView = (ImageView) findViewById(R.id.sadImgView);
        surprisedImgView = (ImageView) findViewById(R.id.surprisedImgView);
        scaredImgView = (ImageView) findViewById(R.id.scaredImgView);
        angryImgView = (ImageView) findViewById(R.id.angryImgView);
        shyImgView = (ImageView) findViewById(R.id.shyImgView);
        interestedImgView = (ImageView) findViewById(R.id.interestedImgView);
        boredImgView = (ImageView) findViewById(R.id.boredImgView);

        // Actions
        followmeImg = (ImageView) findViewById(R.id.followmeImg);
        touchmeImg = (ImageView) findViewById(R.id.touchmeImg);
        standUpImg = (ImageView) findViewById(R.id.standUpImg);
        sitDownImg = (ImageView) findViewById(R.id.sitDownImg);

        // Dances
        macarenaView = (ImageView) findViewById(R.id.macarenaView);
        taichi = (ImageView) findViewById(R.id.taichi);
        chasiki = (ImageView) findViewById(R.id.chasiki);

        // When clicked on images these methods below are called
        happyImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Happy");
                sendText = "1";
                showEmotions(Robot, sendText);
            }
        });

        sadImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Sad");
                sendText = "2";
                showEmotions(Robot, sendText);
            }
        });

        surprisedImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Surprised");
                sendText = "3";
                showEmotions(Robot, sendText);
            }
        });

        scaredImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Scared");
                sendText = "4";
                showEmotions(Robot, sendText);
            }
        });


        angryImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Angry");
                sendText = "5";
                showEmotions(Robot, sendText);
            }
        });

        shyImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Shy");
                sendText = "6";
                showEmotions(Robot, sendText);
            }
        });


        interestedImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Interested");
                sendText = "7";
                showEmotions(Robot, sendText);
            }
        });

        boredImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currEmotion.setText("Bored");
                sendText = "8";
                showEmotions(Robot, sendText);
            }
        });


        followmeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currEmotion.setText("Follow me");
                sendText = "9";
                showEmotions(Robot, sendText);
            }
        });


        touchmeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currEmotion.setText("Touch me");
                sendText = "10";
                showEmotions(Robot, sendText);
            }
        });


        standUpImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currEmotion.setText("Standing");
                sendText = "11";
                showEmotions(Robot, sendText);
            }
        });

        sitDownImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currEmotion.setText("Sitting");
                sendText = "12";
                showEmotions(Robot, sendText);
            }
        });

        macarenaView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                currEmotion.setText("Macarena");
                sendText = "13";
                showEmotions(Robot, sendText);
            }
        });


        taichi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currEmotion.setText("Tai chi");
                sendText = "14";
                showEmotions(Robot, sendText);
            }
        });


        chasiki.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                currEmotion.setText("Chasiki");
                sendText = "15";
                showEmotions(Robot, sendText);
            }
        });

    }

    // ENDING THE GAME AND SAYING BYE
    public void onEndGame(View v) {

        currEmotion.setText("Game ending");
        sendText = "17";
        showEmotions(Robot, sendText);

    }

    // GREETING
    public void onStartGame(View v) {
        currEmotion.setText("Game starting...");
        sendText = "16";
        showEmotions(Robot, sendText);
    }

    // SEND commands to the client based on messages from the server
    public void showEmotions(Socket Robot, CharSequence sendText) {
        sendText = sendText + "\n";
        // if client socket exists send the message
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

    // STOPPING THE SERVER
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
