package pl.lukaszbaczek.ksefClient.cert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

public class CertificationGenerator {

    private static void saveCertToFille(X509Certificate cert) throws IOException, CertificateEncodingException {
        File file = new File("C:\\Users\\bacze\\Desktop\\cert.cer");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(cert.getEncoded());
        }
    }

    public static void main(String[] args) throws Exception, SignatureKSEFException {
        SignatureKSEF signatureKSEF = new SignatureKSEF();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.DECEMBER, 31);
        signatureKSEF.setPeriodDate(calendar.getTime());
        signatureKSEF.setCertName("Certyfikat testowy");
        signatureKSEF.setName("Łukasz");
        signatureKSEF.setSureName("Bączek");
        signatureKSEF.setSerial("85082109030");
        signatureKSEF.setPublicKeyFilePath("C:\\Users\\bacze\\Desktop\\publicKey.info");
        KeyPair keyPair = signatureKSEF.generateRSAKeyPair();
        X509Certificate cert = signatureKSEF.generateV3Certificate(keyPair);
        cert.checkValidity(new Date());
        cert.verify(keyPair.getPublic());
        System.out.println("Wygenerowano");
        saveCertToFille(cert);
        System.out.println("Zapisano do pliku");
    }
}

