package com.jblearning.encryptionv3;

import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

import javax.crypto.Cipher;

public class MainActivity extends Activity {
  public static final String MA = "MainActivity";
  private AESEncryption aes;
  private RSAEncryption rsa;
  private EditText etOriginal;
  private TextView tvEncrypted;
  private TextView tvDecrypted;

  @Override
  protected void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    aes = new AESEncryption( );
    rsa = new RSAEncryption( );
    setContentView( R.layout.activity_main );
    etOriginal = ( EditText ) findViewById( R.id.string_original );
    tvEncrypted = ( TextView ) findViewById( R.id.string_encrypted );
    tvDecrypted = ( TextView ) findViewById( R.id.string_decrypted );
  }

  public void encryptAndDecryptAES( View v ) {
    EditText et = ( EditText ) findViewById( R.id.string_original );
    String original = et.getText( ).toString( );
    String encrypted =
        aes.crypt( Cipher.ENCRYPT_MODE, original, aes.getKey( ) );
    TextView tvEncrypted =
        ( TextView ) findViewById( R.id.string_encrypted );
    tvEncrypted.setText( encrypted );
    TextView tvDecrypted =
        ( TextView ) findViewById( R.id.string_decrypted );
    String decrypted =
        aes.crypt( Cipher.DECRYPT_MODE, encrypted, aes.getKey( ) );
    tvDecrypted.setText( decrypted );
  }

  public void encryptAndDecryptRSA1( View v ) {
    String original = etOriginal.getText( ).toString( );
    String encrypted =
        rsa.crypt( Cipher.ENCRYPT_MODE, original, rsa.getPrivateKey( ) );

    tvEncrypted.setText( encrypted );
    String decrypted =
        rsa.crypt( Cipher.DECRYPT_MODE, encrypted, rsa.getPublicKey( ) );
    tvDecrypted.setText( decrypted );
  }

  public void encryptAndDecryptRSA2( View v ) {
    String original = etOriginal.getText( ).toString( );
    String encrypted =
        rsa.crypt( Cipher.ENCRYPT_MODE, original, rsa.getPublicKey( ) );
    tvEncrypted.setText( encrypted );
    String decrypted =
        rsa.crypt( Cipher.DECRYPT_MODE, encrypted, rsa.getPrivateKey( ) );
    tvDecrypted.setText( decrypted );
  }
}