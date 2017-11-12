package com.example.alejandrotorresruiz.hilos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button button;
    private Button button2;
    private Button button3;
    private ProgressBar progressBar;
    private EjemploAsyncTask ejemploAsyncTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);

    }


    private void UnSegundo(){
        try{
            Thread.sleep(1000);
        }catch (InterruptedException e){

        }
    }

    private void hilos(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i<=10; i++){
                    UnSegundo();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"TAREA LARGA REALIZADA",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                for (int i = 0;i<=10; i++){
                    UnSegundo();
                }
                break;
            case R.id.button2:
                ejemploAsyncTask.cancel(true);
                break;
            case R.id.button3:
                ejemploAsyncTask = new EjemploAsyncTask();
                ejemploAsyncTask.execute();
                break;
            default:
                break;
        }
    }

    private class EjemploAsyncTask extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressBar.setMax(100);
            progressBar.setProgress(0);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            for (int i = 0;i<=10; i++){
                UnSegundo();
                publishProgress(i*10);
                if(isCancelled())
                    break;
            }
            return true;
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            progressBar.setProgress(values[0].intValue());

        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            super.onPostExecute(resultado);
            if(resultado){
                Toast.makeText(getBaseContext(),"Tarea larga finalizada en async",Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected void onCancelled() {
            Toast.makeText(getBaseContext(),"Tarea larga ha sido cancelada",Toast.LENGTH_LONG).show();

        }
    }
}
