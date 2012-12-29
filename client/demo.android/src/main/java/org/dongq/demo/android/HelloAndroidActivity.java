package org.dongq.demo.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HelloAndroidActivity extends Activity implements OnClickListener {

	private static String TAG = "demo.android.HelloAndroidActivity";

	Button requestUrlButton;
	
	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
		setContentView(R.layout.main);
		
		requestUrlButton = (Button) findViewById(R.id.requestUrl);
		requestUrlButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		requestUrl();
	}
	
	private void requestUrl() {
		BufferedReader in = null;
		String uri = "http://192.168.1.88:8080/demo.server/";
		try {
			
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(uri);
			HttpResponse response = client.execute(request);
			
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			
			in.close();
			
			String page = sb.toString();
			Log.d(TAG, page);
			
			Toast.makeText(this, page, Toast.LENGTH_LONG).show();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
