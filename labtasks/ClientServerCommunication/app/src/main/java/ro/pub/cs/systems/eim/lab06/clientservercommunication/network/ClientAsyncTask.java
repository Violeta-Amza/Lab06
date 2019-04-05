package ro.pub.cs.systems.eim.lab06.clientservercommunication.network;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import ro.pub.cs.systems.eim.lab06.clientservercommunication.general.Constants;
import ro.pub.cs.systems.eim.lab06.clientservercommunication.general.Utilities;

public class ClientAsyncTask extends AsyncTask<String, String, Void> {

    private TextView serverMessageTextView;
    Socket socket = null;
    public ClientAsyncTask(TextView serverMessageTextView) {
        this.serverMessageTextView = serverMessageTextView;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {

            // TODO exercise 6b
            // - get the connection parameters (serverAddress and serverPort from parameters - on positions 0 and 1)
            // - open a socket to the server
            // - get the BufferedReader in order to read from the socket (use Utilities.getReader())
            // - while the line that has read is not null (EOF was not sent), append the content to serverMessageTextView
            // by publishing the progress - with the publishProgress(...) method - to the UI thread
            // - close the socket to the server

            int serverPort = Integer.parseInt(params[1]);
            socket = new Socket(params[0], 2017);
            Log.v(Constants.TAG, "Connected to: addr:" + socket.getInetAddress() + ":port:" + socket.getLocalPort());
            BufferedReader bufferedReader = Utilities.getReader(socket);
            String line = bufferedReader.readLine();
            Log.v(Constants.TAG, "received: " + line);
            publishProgress(line);
        } catch (UnknownHostException unknownHostException) {
            Log.d(Constants.TAG, unknownHostException.getMessage());
            if (Constants.DEBUG) {
                unknownHostException.printStackTrace();
            }
        } catch (IOException ioException) {
            Log.d(Constants.TAG, ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ioException) {
                Log.d(Constants.TAG, ioException.getMessage());
                if (Constants.DEBUG) {
                    ioException.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        // TODO exercise 6b
        // - reset the content of the serverMessageTextView
        serverMessageTextView.setText("");
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        // TODO exercise 6b
        // - append the content to serverMessageTextView
        serverMessageTextView.append(progress[0] + '\n');
    }

    @Override
    protected void onPostExecute(Void result) {}

}
