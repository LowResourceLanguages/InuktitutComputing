package utilities;

/**
 * Simple modification of "FileEncrypor.java".  The
 * previously encrypted "clear.txt.des" is read, decrypted
 * and written back as "clear.txt.dcr"
 */

import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class FileDecryptor
{
   // Password must be at least 8 characters (bytes) long
   private static String password = "inuit0amma1nuna";

   /**
    * Note: All kinds of exceptions can be thrown in main.
    * See the API documentation for each method used.
    */

   public static void main(String[] args) throws Exception
   {
	   // File to decrypt.
	   File iFile = new File(args[0]);
	   String filename = iFile.getName();
	   File iDirectory = iFile.getParentFile();
	   File oFile = new File(iDirectory, filename+".dcr");
	   InputStream inFile = new FileInputStream(iFile);
	   OutputStream outFile = new FileOutputStream(oFile);
	   fileDecrypt(inFile, outFile);
   }
   
   public static void fileDecrypt(InputStream inFile, OutputStream outFile) throws Exception {
      PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
      SecretKeyFactory keyFactory =
          SecretKeyFactory.getInstance("PBEWithMD5AndDES");
      SecretKey passwordKey = keyFactory.generateSecret(keySpec);

      // Read in the previouly stored salt and set the iteration count.

      byte[] salt = new byte[8];
      inFile.read(salt);
      int iterations = 100;

      PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, iterations);

      // Create the cipher and initialize it for decryption.

      Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
      cipher.init(Cipher.DECRYPT_MODE, passwordKey, parameterSpec);


      byte[] input = new byte[64];
      int bytesRead;
      while ((bytesRead = inFile.read(input)) != -1)
      {
         byte[] output = cipher.update(input, 0, bytesRead);
         if (output != null)
            outFile.write(output);
      }

      byte[] output = cipher.doFinal();
      if (output != null)
         outFile.write(output);

      inFile.close();
      outFile.flush();
  }
}