package async1.my.com.matkonim;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView htmlTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        htmlTV= (TextView) findViewById(R.id.matkonimTV);

        DownloadTextFromTheInternetTask downloadTextFromTheInternetTask= new DownloadTextFromTheInternetTask();
        downloadTextFromTheInternetTask.execute("http://www.matkonim.net");


    }

    public class DownloadTextFromTheInternetTask extends AsyncTask<String, Integer, String>
    {


        @Override
        protected void onPreExecute() {
            htmlTV.setText("starting......");
        }



        @Override
        protected String doInBackground(String... params) {

            //start download....

            BufferedReader input = null;
            HttpURLConnection connection = null;
            StringBuilder response = new StringBuilder();
            try {
                //create a url:
                URL url = new URL(params[0]);
                //create a connection and open it:
                connection = (HttpURLConnection) url.openConnection();

                //status check:
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                    //connection not good - return.
                }

                //get a buffer reader to read the data stream as characters(letters)
                //in a buffered way.
                input = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                //go over the input, line by line
                String line="";
                while ((line=input.readLine())!=null){
                    //append it to a StringBuilder to hold the
                    //resulting string
                    response.append(line+"\n");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if (input!=null){
                    try {
                        //must close the reader
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(connection!=null){
                    //must disconnect the connection
                    connection.disconnect();
                }
            }

            return response.toString();
        }


        @Override
        protected void onPostExecute(String HtmlResult) {
            htmlTV.setText(HtmlResult);
        }
    }







}
