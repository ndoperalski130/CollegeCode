package com.jblearning.encryptionv3;

import android.util.Log;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class RSAEncryption {
  public static String ALGORITHM = "RSA";
  private Cipher cipher;
  private Key privateKey;
  private Key publicKey;

  public RSAEncryption( ) {
  try {
    cipher = Cipher.getInstance( ALGORITHM );
    KeyPairGenerator generator = KeyPairGenerator.getInstance( ALGORITHM );
    generator.initialize( 256 );
    KeyPair keyPair = generator.genKeyPair( );

    privateKey = keyPair.getPrivate( );
    publicKey = keyPair.getPublic( );
    Log.i("mcvey: public key", publicKey.toString());
    Log.i("mcvey: private key",privateKey.toString());
    Log.i("mcvey: publickeyformat",publicKey.getFormat());
    Log.i("mcvey: privatekeyformat",privateKey.getFormat());

    byte [ ] publicKeyBytes = getPublicKeyBytes( );
    // distribute keyBytes to a user
    String publicKeyString = Arrays.toString( publicKeyBytes );
    Log.i( "mcvey", "public key: " + publicKeyBytes + ": " + publicKeyString );
    byte [ ] privateKeyBytes = getPrivateKeyBytes( );
    // distribute keyBytes to a user  .. well, you really wouldn't distribute these
    String privatekeyString = Arrays.toString( privateKeyBytes );
    Log.i( "mcvey", "private key: " + privateKeyBytes + ": " + privatekeyString );

    // having received keyBytes, reconstruct the key
    Key reconstructedKey = new SecretKeySpec( publicKeyBytes, "RSA" );
    // check that the reconstructed key is the same as the original key
    byte [ ] bytesFromReconstructedKey = reconstructedKey.getEncoded( );
    String stringFromReconstructedKey =
            Arrays.toString( bytesFromReconstructedKey );
    Log.w( "mcvey", "reconstructed key: "
            + bytesFromReconstructedKey + ": " + stringFromReconstructedKey );
  } catch( GeneralSecurityException gse ) {
  }
}

  public Key getPrivateKey( ) {
    return privateKey;
  }

  public Key getPublicKey( ) {
    return publicKey;
  }

  public byte [ ] getPrivateKeyBytes( ) {
    return privateKey.getEncoded( );
  }

  public byte [ ] getPublicKeyBytes( ) {
    return publicKey.getEncoded( );
  }

  public String crypt( int opMode, String message, Key key ) {
    try {
      cipher.init( opMode, key );
      byte [ ] messageBytes = message.getBytes( "ISO-8859-1" );
      byte [ ] encryptedBytes = cipher.doFinal( messageBytes );
      String encrypted = new String( encryptedBytes, "ISO-8859-1" );
      return encrypted;
    } catch( Exception e ) {
      return null;
    }
  }
}