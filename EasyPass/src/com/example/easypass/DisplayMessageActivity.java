package com.example.easypass;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayMessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	
		
		//get message from intent
		Intent intent =getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
		//create text view
		
		TextView textView = new TextView(this);
		TextView textView2 = new TextView(this);
		textView.setTextSize(40);
		textView2.setTextSize(40);
		textView2.setText(message);
		textView.setText("Password: " + message +"\n");

	//Generate Key
		
		
		/*SecretKey secretKey;
	    String stringKey;

	    try {secretKey = KeyGenerator.getInstance("AES").generateKey();
	    
	    
	    if (secretKey != null) {stringKey = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
	    textView.setText("Encrpyted Key: " + stringKey + "\n");
	    
	    
	    byte[] encodedKey     = Base64.decode(stringKey, Base64.DEFAULT);
	    SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES"); 
	  
	    textView.append("Decoded Key: " + originalKey.getEncoded());
	    
	    
	    }
	    
	    }
	    catch (NoSuchAlgorithmException e) {e.printStackTrace();}

	  */
	    
	    
	    /*
		
		try {
			SecretKey MyKey = generateKey();
			if (MyKey != null) {String MyStringKey = Base64.encodeToString(MyKey.getEncoded(),Base64.DEFAULT);
			textView.setText(MyStringKey);
			}
		
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		
		
		
		
		
		
		try {
			byte[] salt = SecureRandom.getSeed(8);
			SecretKey MMYKey = generateKey(message.toCharArray(),salt);
		    if (MMYKey != null) {String SMMYKey = Base64.encodeToString(MMYKey.getEncoded(), Base64.DEFAULT);
		    textView.append("Key Format: " + MMYKey.getFormat() + "\n");
		    textView.append("Key Algo: " + MMYKey.getAlgorithm() + "\n");
		    textView.append("Key: " + MMYKey + "\n");
		    String text = new String(salt, 0, salt.length, "ASCII");
		    textView.append("Salt: " + text + "\n");
		   
		    textView.append("Encoded Key: " + SMMYKey + "\n");
		    
			
		    byte[] encodedKey     = Base64.decode(SMMYKey, Base64.DEFAULT);
		    SecretKey originalKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "PBKDF2WithHmacSHA1"); 
		  
		    textView.append("Decoded Key: " + originalKey);}
			
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	
		
		setContentView(textView);
		
		
	}
	
	
	
	public static SecretKey generateKey(char[] passphraseOrPin, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
	    // Number of PBKDF2 hardening rounds to use. Larger values increase
	    // computation time. You should select a value that causes computation
	    // to take >100ms.
	    final int iterations = 1000; 

	    // Generate a 256-bit key
	    final int outputKeyLength = 256;

	    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
	    KeySpec keySpec = new PBEKeySpec(passphraseOrPin, salt, iterations, outputKeyLength);
	    SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
	    return secretKey;
	}
	
	
	public static SecretKey generateKey() throws NoSuchAlgorithmException {
	    // Generate a 256-bit key
	    final int outputKeyLength = 256;

	    SecureRandom secureRandom = new SecureRandom();
	    // Do *not* seed secureRandom! Automatically seeded from system entropy.
	    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
	    keyGenerator.init(outputKeyLength, secureRandom);
	    SecretKey key = keyGenerator.generateKey();
	    return key;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_display_message,
					container, false);
			return rootView;
		}
	}

}
