package com.hk.transportProject.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class ApiKeyStore {
    private static final String ANDROID_KEYSTORE = "AndroidKeyStore";
    private static final String ALIAS = "ApiKeyAlias";
    private static final String PREFS_NAME = "ApiKeyPrefs";
    private static final String ENCRYPTED_KEY = "encrypted_api_key";
    private static final String IV = "iv";

    private final Context context;
    private final KeyStore keyStore;

    public ApiKeyStore(Context context) throws Exception {
        this.context = context;
        keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
        keyStore.load(null);
        createKey();
    }

    private void createKey() throws Exception {
        if (!keyStore.containsAlias(ALIAS)) {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE);
            KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(ALIAS,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build();
            keyGenerator.init(keyGenParameterSpec);
            keyGenerator.generateKey();
        }
    }

    public void storeApiKey(String apiKey) throws Exception {
        SecretKey secretKey = (SecretKey) keyStore.getKey(ALIAS, null);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedBytes = cipher.doFinal(apiKey.getBytes(StandardCharsets.UTF_8));
        byte[] iv = cipher.getIV();

        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
            .putString(ENCRYPTED_KEY, Base64.encodeToString(encryptedBytes, Base64.DEFAULT))
            .putString(IV, Base64.encodeToString(iv, Base64.DEFAULT))
            .apply();
    }

    public String getApiKey() throws Exception {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String encryptedKey = prefs.getString(ENCRYPTED_KEY, null);
        String iv = prefs.getString(IV, null);

        if (encryptedKey == null || iv == null) {
            return null;
        }

        SecretKey secretKey = (SecretKey) keyStore.getKey(ALIAS, null);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(128, Base64.decode(iv, Base64.DEFAULT));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);

        byte[] decryptedBytes = cipher.doFinal(Base64.decode(encryptedKey, Base64.DEFAULT));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}