/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Authors:
  Nguyen Nhat Minh
  Luu Quoc Nhat
  Tran Ngoc Hong Doanh
  To Gia Hy
  ID:
  S3932112
  S3924942
  s3927023
  S3927539
  Acknowledgement: https://howtodoinjava.com/java/java-security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/?fbclid=IwAR1HtOK_dQ_8WVLWSaB8PxfUJ_F7KJ81Je-gwEky_YL6la8hIfA4Ab_OFjM
*/

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class hashPassword {


    public static String get_SHA_256_SecurePassword(String passwordToHash,
                                                    String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16)
                        .substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }

    public static void main(String[] args) {
        System.out.println(get_SHA_256_SecurePassword("123", "[B@6615435c"));
    }

}

