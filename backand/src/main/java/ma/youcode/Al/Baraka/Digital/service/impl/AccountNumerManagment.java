package ma.youcode.Al.Baraka.Digital.service.impl;

import org.springframework.stereotype.Component;

import java.io.*;
import java.security.SecureRandom;

@Component
public class AccountNumerManagment {
    public boolean isContains(String number) {
        File file = new File("./../../../../../blacklistNumerAccount.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (number.equals(line.trim())) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    public void addNumber(String number) {
        File file = new File("blacklistNumerAccount.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(number);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erreur lors de l'ajout du numéro à la blacklist");
        }
    }

    public String geneted() {
        String numero;
        do {
            SecureRandom random = new SecureRandom();
            long number = 1_000_000_000_000_000L
                    + (long) (random.nextDouble() * 9_000_000_000_000_000L);
             numero = Long.toString(number);
        } while (!isContains(numero));
        addNumber(numero);
        return numero;
    }

}
