package remote;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class RemoteUtil extends AsyncTask<String, Void, String> {
	protected String doInBackground(String... url) {
		try {
			URL url1 = new URL(url[0]);
			// create the new connection
			HttpURLConnection urlConnection = (HttpURLConnection) url1
					.openConnection();

			// set up some things on the connection
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);

			// and connect!
			urlConnection.connect();

			// set the path where we want to save the file
			// in this case, going to save it on the root directory of the
			// sd card.
			String a = Environment.getExternalStorageDirectory().getPath()
					+ "/Download";
			File SDCardRoot = new File(a);
			// create a new file, specifying the path, and the filename
			// which we want to save the file as.
			int length = url[0].length();
			String fname = null;
			if (url[1].equals("Course")) {
				fname = url[0].toString().substring((length - 9));
			} else if (url[1].equals("Users")) {
				fname = url[0].toString().substring((length - 15));
			}
			Log.d("RemoteUtil", fname);
			File file = new File(SDCardRoot, fname);

			// this will be used to write the downloaded data into the file we
			// created
			FileOutputStream fileOutput = new FileOutputStream(file);

			// this will be used in reading the data from the internet
			InputStream inputStream = urlConnection.getInputStream();

			// create a buffer...
			byte[] buffer = new byte[1024];
			int bufferLength = 0; // used to store a temporary size of the
									// buffer

			// now, read through the input buffer and write the contents to the
			// file
			while ((bufferLength = inputStream.read(buffer)) > 0) {
				// add the data in the buffer to the file in the file output
				// stream (the file on the sd card
				fileOutput.write(buffer, 0, bufferLength);
			}
			// close the output stream when done
			fileOutput.close();

			// catch some possible errors...
		} catch (FileNotFoundException e) {
			return "Not Found";
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "Error";
		} catch (IOException e) {
			e.printStackTrace();
			return "Error";
		}
		return "Success";
	}

}
