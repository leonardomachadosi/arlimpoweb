package br.ufma.lsdi.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SynchronousEncryption implements Serializable {

    private static final String IV = "AAAAAAAAAAAAAAAA";

    public static String returnEncryptedString(String string) throws Exception {
        return new ObjectMapper()
                .writeValueAsString(
                        encrypt(string, createToken())
                );
    }

    public static String returnDecryptedString(String string) throws Exception {
        byte[] byteBody = new ObjectMapper()
                .readValue(string, byte[].class);
        return decrypt(byteBody, createToken());
    }

    private static byte[] encrypt(String textopuro, String chaveencriptacao) throws Exception {
        Cipher encripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        encripta.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return encripta.doFinal(textopuro.getBytes("UTF-8"));
    }

    private static String decrypt(byte[] textoencriptado, String chaveencriptacao) throws Exception {
        Cipher decripta = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(chaveencriptacao.getBytes("UTF-8"), "AES");
        decripta.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(decripta.doFinal(textoencriptado), "UTF-8");
    }

    private static String createToken() {

        Date data = Calendar.getInstance().getTime();
        String preToken = new SimpleDateFormat("HH").format(data);
        String developer_01 = "EDUREGONERB";
        String developer_02 = "ROINUJOCSICNARF";
        String developer_03 = "SOMELODRANOEL";
        String postToken = new SimpleDateFormat("mm").format(data);

        StringBuilder builder = new StringBuilder();
        builder
                .append(preToken)
                .append(developer_01)
                .append(developer_02)
                .append(developer_03)
                .append(postToken);
        return EncryptionMethods.SHA256(builder.toString()).substring(0, 16);
    }
}

