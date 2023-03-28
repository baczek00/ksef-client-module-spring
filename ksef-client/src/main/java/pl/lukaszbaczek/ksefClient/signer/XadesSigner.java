package pl.lukaszbaczek.ksefClient.signer;

import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;

import java.io.FileInputStream;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class XadesSigner {

    static final String KEYSTORE_FILE = "C:\\Users\\bacze\\Desktop\\keystore.jks";
    static final String KEYSTORE_INSTANCE = "JKS";
    static final String KEYSTORE_PWD = "ABC";
    static final String KEYSTORE_ALIAS = "KSEF";

    public static void main(String[] args) throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        if (Security.getProvider("BC") == null) {
            System.out.println("Bouncy Castle provider not found");
        } else {
            System.out.println("Bouncy Castle provider found");
        }

        System.out.println("Supported algorithms:");
        for (Provider.Service service : Security.getProvider("BC").getServices()) {
            System.out.println(service.getAlgorithm());
        }


        String text = "This is a message";



        pl.lukaszbaczek.ksefClient.keystore.KeyStore keyStore = new pl.lukaszbaczek.ksefClient.keystore.KeyStore(KEYSTORE_FILE, KEYSTORE_INSTANCE, KEYSTORE_PWD);


        //Sign
        PrivateKey privKey = keyStore.getPrivateKey(KEYSTORE_ALIAS);
        Signature signature = Signature.getInstance("SHA1WithRSA", "BC");
        signature.initSign(privKey);
        signature.update(text.getBytes());

        //Build CMS
        X509Certificate cert = (X509Certificate) keyStore.getCertificate(KEYSTORE_ALIAS);
        List certList = new ArrayList();
        certList.add(cert);
        Store certs = new JcaCertStore(certList);
        CMSSignedDataGenerator gen = new CMSSignedDataGenerator();
        ContentSigner sha1Signer = new JcaContentSignerBuilder("SHA1withRSA").setProvider("BC").build(privKey);
        gen.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().setProvider("BC").build()).build(sha1Signer, cert));
        gen.addCertificates(certs);

        byte[] signatureBytes = signature.sign();
        CMSProcessableByteArray msg = new CMSProcessableByteArray(signatureBytes);
        CMSSignedData sigData = gen.generate(msg, false);

        // encode signed content to base64
        String signedContentBase64 = Base64.getEncoder().encodeToString(signatureBytes);
        System.out.println("Signed content (base64): " + signedContentBase64 + "\n");

        // encode entire CMS to base64
        byte[] cmsBytes = sigData.getEncoded();
        String envelopedData = Base64.getEncoder().encodeToString(cmsBytes);
        System.out.println("Enveloped data (base64): " + envelopedData);
    }
}
