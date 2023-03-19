package pl.lukaszbaczek.ksefClient.keystore;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
public class KeyStore {

    private java.security.KeyStore keyStore;

    public KeyStore(String path, String password) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, IOException {
        keyStore = java.security.KeyStore.getInstance(java.security.KeyStore.getDefaultType());
        try (FileInputStream stream = new FileInputStream(path)) {
            keyStore.load(stream, password.toCharArray());
        }
    }

    public PrivateKey getPrivateKey(String alias, String password) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        return (PrivateKey) keyStore.getKey(alias, password.toCharArray());
    }

    public PublicKey getPublicKey(String alias) throws KeyStoreException {
        return keyStore.getCertificate(alias).getPublicKey();
    }
}
