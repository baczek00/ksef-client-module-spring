package pl.lukaszbaczek.ksefClient.cert;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StampKSEF extends BaseCerificate {
    private static final String FORMAT_PATTERN = "(VATPL).*?(?<number>\\d{10})";
    private final Pattern pattern = Pattern.compile(FORMAT_PATTERN);
    private Matcher matcher;
    private String organizationName;

    public void setOrganizationName(String value) {
        organizationName = value;
        matcher = pattern.matcher(organizationName);
    }

    @Override
    public X500Name subjectCertificate() throws SignatureKSEFException {
        if (!matcher.matches()) {
            throw new SignatureKSEFException("Nazwa organizacji musi miec format: " + FORMAT_PATTERN);
        }
        return new X500NameBuilder(BCStyle.INSTANCE)
                .addRDN(BCStyle.ORGANIZATION_IDENTIFIER, organizationName).build();
    }
}
