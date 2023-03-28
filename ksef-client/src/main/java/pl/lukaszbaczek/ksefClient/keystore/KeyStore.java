package pl.lukaszbaczek.ksefClient.keystore;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
public class KeyStore {

    private java.security.KeyStore keyStore;
    private String password;

    public KeyStore(String path, String type, String password) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        this.keyStore = java.security.KeyStore.getInstance(type);
        this.password = password;
        try (FileInputStream stream = new FileInputStream(path)) {
            keyStore.load(stream, this.password.toCharArray());
        }
    }

    public PrivateKey getPrivateKey(String alias) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        return (PrivateKey) keyStore.getKey(alias, password.toCharArray());
    }

    public PublicKey getPublicKey(String alias) throws KeyStoreException {
        return keyStore.getCertificate(alias).getPublicKey();
    }

    public Certificate getCertificate(String allias) throws KeyStoreException {
       return keyStore.getCertificate(allias);
    }
}
