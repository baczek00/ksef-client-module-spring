package pl.lukaszbaczek.ksefClient.keystore;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

public class KeyStoreGeneratorFile {
    private final String keystoreFile;
    private final String aliasKey;
    public KeyStoreGeneratorFile(String keystoreFile, String aliasKey) {
        this.keystoreFile = keystoreFile;
        this.aliasKey = aliasKey;
    }
    private KeyStore generateKeyStore(InputStream certStream, char[] keystorePwd) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Certificate certificate = certificateFactory.generateCertificate(certStream);

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, keystorePwd);
        keyStore.setKeyEntry(aliasKey, keyPair.getPrivate(), keystorePwd, new Certificate[]{certificate,});
        return keyStore;
    }

    private KeyStore generateKeyStore(String certFile, String password) throws Exception {
        try (InputStream certStream = new FileInputStream(certFile)) {
            return generateKeyStore(certStream, password.toCharArray());
        }
    }

    public void saveP12File(Certificate certificate, String password) throws Exception {
        // Zapis certyfikatu do strumienia
        InputStream certStream = new ByteArrayInputStream(certificate.getEncoded());
        // Tworzymy instancję KeyStore
        KeyStore keyStore = generateKeyStore(certStream, password.toCharArray());

        // Zapisujemy keystore do pliku
        try (FileOutputStream outputStream = new FileOutputStream(keystoreFile)) {
            keyStore.store(outputStream, password.toCharArray());
        }
    }
}
