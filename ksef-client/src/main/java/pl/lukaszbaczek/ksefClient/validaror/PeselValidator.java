package pl.lukaszbaczek.ksefClient.validaror;

import java.time.LocalDate;

class PeselValidator {
    private String pesel;
    private int[] weights = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

    public PeselValidator(String pesel) {
        this.pesel = pesel;
    }

    public boolean validate() {
        if (pesel == null || pesel.length() != 11) {
            return false;
        }

        int sum = calculateSum();

        int checksum = (10 - (sum % 10)) % 10;
        int lastDigit = Character.digit(pesel.charAt(10), 10);

        if (checksum != lastDigit) {
            return false;
        }

        LocalDate dateOfBirth = calculateDateOfBirth();

        return dateOfBirth != null;
    }

    private int calculateSum() {
        int sum = 0;

        for (int i = 0; i < 10; i++) {
            int digit = Character.digit(pesel.charAt(i), 10);
            sum += digit * weights[i];
        }

        return sum;
    }

    private LocalDate calculateDateOfBirth() {
        int year = Integer.parseInt(pesel.substring(0, 2));
        int month = Integer.parseInt(pesel.substring(2, 4));
        int day = Integer.parseInt(pesel.substring(4, 6));

        if (month > 80) {
            year += 1800;
            month -= 80;
        } else if (month > 60) {
            year += 2200;
            month -= 60;
        } else if (month > 40) {
            year += 2100;
            month -= 40;
        } else if (month > 20) {
            year += 2000;
            month -= 20;
        } else {
            year += 1900;
        }

        try {
            LocalDate dateOfBirth = LocalDate.of(year, month, day);
            return dateOfBirth;
        } catch (Exception e) {
            return null;
        }
    }
}
