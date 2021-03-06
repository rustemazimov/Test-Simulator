package models.encryption;

import models.Meta;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

    /**
     * Generates hash values of given values with a given value
     *
     */
    public class Function {

        private static Function instance;

        private Function() {}

        public static Function getInstance() {
            if (instance == null)
            {
                instance = new Function();
            }
                    return instance;
        }

        public String hash(String text){
            for (String hashMethod : Meta.getInstance().getHashMethods()) {
                text = hashText(text, hashMethod);
            }
            return text;
        }
        private String hashText(String text, String methodName){
            switch(methodName){
                case "MD5": return convertToMD5(text);
                case "SHA1": return convertToSHA1(text);
                case "SHA256": return convertToSHA256(text);
                case "SHA512": return convertToSHA512(text);
                default: return "You need support\nContact with us";
            }
        }


        /*Create a method for hashing with MD5*/
        private String convertToMD5(String text){
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] messageDigest = new byte[0];
                try {
                    messageDigest = md.digest(text.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                md.reset();
                BigInteger number = new BigInteger(1, messageDigest);
                String hashedText = number.toString(16);
                while(hashedText.length() < 32){
                    hashedText = "0" + hashedText;
                }
                return hashedText;
            } catch (NoSuchAlgorithmException ex) {
                System.out.println("No such Algorithm in MD5");
            }
        /*If te program executes the following statement
                it means that Exception happened*/
            return "You need support\nContact with us";
        }

        private String convertToSHA1(String text)
        {
            String hashedText = "";
            try
            {
                MessageDigest crypt = MessageDigest.getInstance("SHA-1");
                crypt.digest(text.getBytes("UTF-8"));
                crypt.reset();
                hashedText = convertByteToHex(crypt.digest());//Convert from byte to HexaDecimal then Initialize to ^hashedText^
            }
            catch(NoSuchAlgorithmException | UnsupportedEncodingException e)
            {
                //If the jvp is here  Your program is already broken :)))
            }
            return hashedText;
        }

        private String convertToSHA256(String base) {
            try{
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(base.getBytes("UTF-8"));
                digest.reset();
                StringBuilder hexString = new StringBuilder();

                for (int i = 0; i < hash.length; i++) {
                    String hex = Integer.toHexString(0xff & hash[i]);
                    if(hex.length() == 1) hexString.append('0');
                    hexString.append(hex);
                }

                return hexString.toString();
            } catch(UnsupportedEncodingException | NoSuchAlgorithmException ex){
                //If the jvp is here  Your program is already broken :)))
            }
            return "You need support\nContact with us";
        }

        private String convertToSHA512(String textToHash)
        {
            try {
                final MessageDigest sha512 = MessageDigest.getInstance("SHA-512");
                try {
                    sha512.digest(textToHash.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                sha512.reset();
                return convertByteToHex(sha512.digest());
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Function.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "You need support\nContact with us";
        }
        private  String convertByteToHex(byte data[])
        {
            StringBuilder hexData = new StringBuilder();
            for (int byteIndex = 0; byteIndex < data.length; byteIndex++)
                hexData.append(Integer.toString((data[byteIndex] & 0xff) + 0x100, 16).substring(1));

            return hexData.toString();
        }
    }
