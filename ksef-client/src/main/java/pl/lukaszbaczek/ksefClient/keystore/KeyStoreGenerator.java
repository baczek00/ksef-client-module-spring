package pl.lukaszbaczek.ksefClient.keystore;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

public class KeyStoreGenerator {

    private final String keystoreFile;
    private final String keystoreInstance;
    private final char[] keystorePwd;
    public KeyStoreGenerator(String keystoreFile, String keystoreInstance, char[] keystorePwd) {
        this.keystoreFile = keystoreFile;
        this.keystoreInstance = keystoreInstance;
        this.keystorePwd = keystorePwd;
    }
    public KeyStore generateKeyStore(InputStream certStream, PrivateKey privateKey) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(certStream);

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, keystorePwd);
        keyStore.setKeyEntry(keystoreInstance, keyPair.getPrivate(), keystorePwd, new Certificate[]{certificate,});
        return keyStore;
    }

    public KeyStore generateKeyStore(String certFile, PrivateKey privateKey) throws Exception {
        try (InputStream certStream = new FileInputStream(certFile)) {
            return generateKeyStore(certStream, privateKey);
        }
    }
}
