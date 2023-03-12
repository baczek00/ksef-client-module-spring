package pl.lukaszbaczek.ksefClient.validaror;

class NipValidator {

    private String nip;

    public NipValidator(String nip) {
        this.nip = nip;
    }

    public boolean validate() {
        if (nip == null || nip.length() != 10) {
            return false;
        }

        int[] weights = {6, 5, 7, 2, 3, 4, 5, 6, 7};
        int sum = 0;

        for (int i = 0; i < 9; i++) {
            int digit = Character.digit(nip.charAt(i), 10);
            sum += digit * weights[i];
        }

        int checksum = sum % 11;
        if (checksum == 10) {
            checksum = 0;
        }

        int lastDigit = Character.digit(nip.charAt(9), 10);

        return checksum == lastDigit;
    }
}
