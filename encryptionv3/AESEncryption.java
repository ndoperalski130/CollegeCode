package com.jblearning.encryptionv3;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AESEncryption {
  public static String ALGORITHM = "AES";
  private SecretKey secretKey;
  private Cipher cipher;
  private SecureRandom rand;

  public AESEncryption( ) {
    try {
      cipher = Cipher.getInstance( ALGORITHM );
      rand = new SecureRandom( );
      KeyGenerator keyGen = KeyGenerator.getInstance( ALGORITHM );
      keyGen.init( 256 );
      secretKey = keyGen.generateKey( );
    } catch ( GeneralSecurityException gse ) {
    }
  }

  public String crypt( int opMode, String message, SecretKey key ) {
    try {
      cipher.init( opMode, key, rand );
      byte [ ] messageBytes = message.getBytes( "ISO-8859-1" );
      byte [ ] encodedBytes = cipher.doFinal( messageBytes );
      String encoded = new String( encodedBytes, "ISO-8859-1" );
      return encoded;
    } catch( Exception e ) {
      return null;
    }
  }

  public SecretKey getKey( ) {
    return secretKey;
  }

  public byte [ ] getKeyBytes( ) {
    return secretKey.getEncoded( );
  }
}
