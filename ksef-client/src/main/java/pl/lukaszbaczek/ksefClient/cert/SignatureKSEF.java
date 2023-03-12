package pl.lukaszbaczek.ksefClient.cert;

import lombok.Setter;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import pl.lukaszbaczek.ksefClient.validaror.SerialValidator;

@Setter
public class SignatureKSEF extends BaseCerificate {
    private String certName;
    private String name;
    private String sureName;
    private String serial;

    @Override
    public X500Name subjectCertificate() throws SignatureKSEFException {
        if (certName == null) {
            throw new SignatureKSEFException("Brak nazwy certyfikatu");
        }
        if (name == null) {
            throw new SignatureKSEFException("Brak imienia osoby na którą jest wystawiony certyfikat");
        }
        if (sureName == null) {
            throw new SignatureKSEFException("Brak nazwiska osoby na którą jest wystawiony certyfikat");
        }
        if (serial == null) {
            throw new SignatureKSEFException("Nie podano numeru PESEL lub NIP");
        }
        SerialValidator serialValidator = new SerialValidator(serial);
        boolean serialValidatorResult = serialValidator.validate();
        if (!serialValidatorResult) {
            throw new SignatureKSEFException("Numer musi być PESELEM lup NIPEM");
        }
        return new X500NameBuilder(BCStyle.INSTANCE)
                .addRDN(BCStyle.CN, certName)
                .addRDN(BCStyle.SURNAME, sureName)
                .addRDN(BCStyle.GIVENNAME, name)
                .addRDN(BCStyle.SERIALNUMBER, serial)
                .build();
    }
}
