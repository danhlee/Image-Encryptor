package encryption;
import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.KeyGenerator;
import java.security.InvalidKeyException;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.*;
import java.io.*;



public class AES {
	
	
	public static void encrypt(File imageFile) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
     FileNotFoundException, IOException, IllegalBlockSizeException, BadPaddingException {
		String fileName = imageFile.getAbsolutePath();
		int dotIndex = fileName.lastIndexOf('.');
		String imageExtension = fileName.substring(dotIndex + 1);
		
		Cipher cipher = Cipher.getInstance("AES");
	    KeyGenerator keyGen = KeyGenerator.getInstance("AES");         
	    //initialize keygen with int keysize parameter
	    keyGen.init(128);
	    SecretKey key = keyGen.generateKey();
	    cipher.init(Cipher.ENCRYPT_MODE, key);
	    
	    /* copy data to byte[] b */
        FileInputStream fileInput = new FileInputStream(imageFile);
        
        // File.length returns long, but byte array parameter needs integer
        byte[] b = new byte[(int)imageFile.length()];
        
        // reads b.length bytes of data FROM file TO byte[] b, puts bytes into the array 
        fileInput.read(b);
        fileInput.close();
      
        /* encode byte[] b and output TO encryptedData.txt */ 
        byte[] encodedByteArr = cipher.doFinal(b);
        FileOutputStream fileOutput = new FileOutputStream("encryptedData." + imageExtension);
        
        /* write TO encryptedData.text FROM encodedByteArr */
        fileOutput.write(encodedByteArr);
        fileOutput.close();
        
        /* encode secret key and put in byte[] encodedKey and write to key.txt */
        byte[] encodedKey = key.getEncoded();
        FileOutputStream keyFileOutput = new FileOutputStream("key.txt");
        keyFileOutput.write(encodedKey);
        keyFileOutput.close();
        
	}

	public static void decrypt(File keyFile, File fileToDecrypt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
	 FileNotFoundException, IOException, IllegalBlockSizeException, BadPaddingException {
		String fileName = fileToDecrypt.getAbsolutePath();
		int dotIndex = fileName.lastIndexOf('.');
		String imageExtension = fileName.substring(dotIndex + 1);		
				
		/* get key.txt file and set up for reading via input stream */
	    File encryptedKeyFile = keyFile;        
	    FileInputStream keyInput = new FileInputStream(encryptedKeyFile);
	    
	    /* create new byte[] to store encrypted key in bytes */
	    // size of the byte array = File.length()
	    byte[] keyBytes = new byte[(int)encryptedKeyFile.length()];
	    
	    /* read FROM input stream and put into byte[] "keyBytes" */     
	    keyInput.read(keyBytes);
	    keyInput.close();
	    
	    /* construct new secret key using encoded key byte[] keyBytes set to decrypt using AES */   
	    SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
	    
	    //create new cipher object instance to decrypt, using AES param      
	    Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    
	    // create input stream for encrypted image file
	    File encryptedData = fileToDecrypt;
	    FileInputStream fileInput = new FileInputStream(encryptedData);
	    
	    // create byte[] to store encrypted image file data
	    byte[] encryptedByte = new byte[(int)encryptedData.length()];
	    
	    // read FROM encryptedData and write to encryptedByte array
	    fileInput.read(encryptedByte);
	    fileInput.close();
	      
	    // decrypt byte[] into new byte[] with decrypted data and write to decryptedImage
        byte[] decryptedByte = cipher.doFinal(encryptedByte);
        FileOutputStream fileOuput = new FileOutputStream("decryptedImage." + imageExtension);
        fileOuput.write(decryptedByte);
        fileOuput.close();
	}
}
