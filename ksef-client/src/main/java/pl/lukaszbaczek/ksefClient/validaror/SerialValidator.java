package pl.lukaszbaczek.ksefClient.validaror;

public class SerialValidator {
    private final String serialNumber;

    public SerialValidator(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean validate() {
        if (serialNumber == null || serialNumber.isEmpty()) {
            return false;
        }

        if (serialNumber.length() == 11) {
            PeselValidator peselValidator = new PeselValidator(serialNumber);
            return peselValidator.validate();
        } else if (serialNumber.length() == 10) {
            NipValidator nipValidator = new NipValidator(serialNumber);
            return nipValidator.validate();
        }
        return false;
    }
}
