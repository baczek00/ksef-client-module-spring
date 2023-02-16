package pl.lukaszbaczek.ksefClient.xml;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import pl.gov.mf.ksef.schema.gtw.svc.online.auth.request._2021._10._01._0001.InitSessionSignedRequest;
import pl.gov.mf.ksef.schema.gtw.svc.online.types._2021._10._01._0001.AuthorisationContextSignedType;
import pl.gov.mf.ksef.schema.gtw.svc.types._2021._10._01._0001.*;

import java.io.ByteArrayOutputStream;

public class XMLSessionSignedRequest {
    public static final String SYSTEM_CODE = "FA (1)";
    public static final String SCHEMA_VERSION = "1-0E";
    public static final String TARGET_NAMESPACE = "http://crd.gov.pl/wzor/2021/11/29/11089/";
    public static final String FORM_CODE_TYPE_VALUE = "FA";

    public byte[] noEncriptedSessionSignedRequest(String challenge, String identifier) throws JAXBException {
        InitSessionSignedRequest initSessionSignedRequest = getInitSessionSignedRequest(challenge, identifier);
      return saveToByte(initSessionSignedRequest);
    }

    private InitSessionSignedRequest getInitSessionSignedRequest(String challenge, String identifier) {
        InitSessionSignedRequest initSessionSignedRequest = new InitSessionSignedRequest();
        AuthorisationContextSignedType context = new AuthorisationContextSignedType();
        context.setChallenge(challenge);
        SubjectIdentifierByType subjectIdentifierByType = new SubjectIdentifierByCompanyType();
        subjectIdentifierByType.setIdentifier(identifier);
        context.setIdentifier(subjectIdentifierByType);

        FormCodeType formCodeType = new FormCodeType();
        formCodeType.setSystemCode(SYSTEM_CODE);
        formCodeType.setSchemaVersion(SCHEMA_VERSION);
        formCodeType.setTargetNamespace(TARGET_NAMESPACE);
        formCodeType.setValue(FORM_CODE_TYPE_VALUE);

        DocumentTypeType documentTypeType = new DocumentTypeType();
        documentTypeType.setService(ServiceType.K_SE_F);
        documentTypeType.setFormCode(formCodeType);
        context.setDocumentType(documentTypeType);
        context.setType(AuthorisationTypeType.SERIAL_NUMBER);

        initSessionSignedRequest.setContext(context);
        return initSessionSignedRequest;
    }

    private void saveToConsol(InitSessionSignedRequest initSessionSignedRequest) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(InitSessionSignedRequest.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(initSessionSignedRequest, System.out);
    }

    private byte[] saveToByte(InitSessionSignedRequest initSessionSignedRequest) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(InitSessionSignedRequest.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        marshaller.marshal(initSessionSignedRequest, outputStream);
        byte[] requestBytes = outputStream.toByteArray();
        return requestBytes;
    }


}
