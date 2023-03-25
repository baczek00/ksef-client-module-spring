package pl.lukaszbaczek.ksefClient.cert;


import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;

public abstract class BaseCerificate {
    private Date periodTo;
    private String publicKeyFilePath;

    public BaseCerificate() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public X509Certificate generateV3Certificate(KeyPair keyPair) throws Exception, SignatureKSEFException {

        // Atrybuty certyfikatu
        X500Name issuer = new X500Name("CN=Test Certificate Authority");
        BigInteger serialNumber = BigInteger.valueOf(System.currentTimeMillis());

        // Okres ważności certyfikatu
        Date startDate = new Date(System.currentTimeMillis());
        Date endDate = periodTo;

        if (endDate == null) {
            throw new SignatureKSEFException("Nie podano daty końcowej certyfikatu");
        }
        if (endDate.before(startDate)) {
            throw new SignatureKSEFException("Data końcowa nie może być przed aktualną datą");
        }
        SubjectPublicKeyInfo subjectPublicKeyInfo = null;
        if (publicKeyFilePath != null) {
            try (PEMParser pemParser = new PEMParser(new FileReader(publicKeyFilePath))) {
                Object object = pemParser.readObject();
                if (object instanceof SubjectPublicKeyInfo) {
                    subjectPublicKeyInfo = (SubjectPublicKeyInfo) object;
                } else {
                    throw new IllegalArgumentException("Niewłaściwy format pliku.");
                }
            } catch (IOException e) {
                System.out.println("Nie udało się odczytać pliku z kluczem publicznym: Powód błędu to: " + e);
            }
        } else {
            subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
        }
        // Tworzymy certyfikat
        X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(issuer, serialNumber, startDate, endDate, subjectCertificate(), subjectPublicKeyInfo);

        // Podpisanie kluczem prywantym
        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSAEncryption").build(keyPair.getPrivate());
        X509CertificateHolder certHolder = certBuilder.build(contentSigner);

        // Konwersja certyfikatu na X509Certificate
        X509Certificate cert = new  JcaX509CertificateConverter().getCertificate(certHolder);
        return cert;
    }

    public KeyPair generateRSAKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(2048, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    public abstract X500Name subjectCertificate() throws SignatureKSEFException;

    public ByteArrayOutputStream saveCertToStream(X509Certificate cert) throws CertificateEncodingException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            outputStream.write(cert.getEncoded());
        } catch (IOException e) {
            // handle exception
        }
        return outputStream;
    }

    public void setPublicKeyFilePath(String publicKeyFilePath) {
        this.publicKeyFilePath = publicKeyFilePath;
    }

    public void setPeriodDate(Date date) {
        periodTo = date;
    }
}
