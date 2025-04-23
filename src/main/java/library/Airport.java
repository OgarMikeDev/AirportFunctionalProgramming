//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package library;

import library.Flight.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Airport {
    private final Random random = new Random(1L);
    private final List<Terminal> terminals = new ArrayList();

    private Airport() {
    }

    public static Airport getInstance() {
        return (new Airport()).initializeAirport();
    }

    public List<Aircraft> getAllAircrafts() {
        Stream<Aircraft> usedAircraft = this.terminals.stream().map(Terminal::getFlights).flatMap(Collection::stream).map(Flight::getAircraft);
        Stream<Aircraft> parkedAircraft = this.terminals.stream().map(Terminal::getParkedAircrafts).flatMap(Collection::stream);
        return (List)Stream.concat(usedAircraft, parkedAircraft).collect(Collectors.toList());
    }

    public List<Terminal> getTerminals() {
        return this.terminals;
    }

    private Airport initializeAirport() {
        String[] terminalNames = new String[]{"A", "B", "C", "D"};
        int aircraftsCount = 125 + (int) ((Math.random() - (double) 0.5F) * (double) 50.0F);

        for (String terminalName : terminalNames) {
            Terminal terminal = new Terminal(terminalName);

            for(int i = 0; i < aircraftsCount; ++i) {
                double type = Math.random();
                if (type <= 0.33) {
                    terminal.addFlight(this.generateFlight(Type.DEPARTURE));
                } else if (type <= 0.8) {
                    terminal.addFlight(this.generateFlight(Type.ARRIVAL));
                } else {
                    terminal.addParkingAircraft(this.generateAircraft());
                }
            }

            this.terminals.add(terminal);
        }

        return this;
    }

    private Flight generateFlight(Flight.Type type) {
        return new Flight(this.generateFlightName(), type, this.generateRandomDate(), this.generateAircraft());
    }

    private Instant generateRandomDate() {
        Instant currentTime = Instant.now();
        return currentTime.plusMillis((long)((this.random.nextDouble() - (double)0.5F) * (double)2.0F * (double)8.64E7F));
    }

    private String generateFlightName() {
        String[] companyCodes = new String[]{"SU", "AA", "AR", "AF", "B2", "FV"};
        String companyPrefix = companyCodes[this.random.nextInt(companyCodes.length)];
        int routeNumber = this.random.nextInt(9999) + 1;
        return companyPrefix + " " + routeNumber;
    }

    private Aircraft generateAircraft() {
        String[] models = new String[]{"Boeing 737-200", "Boeing 737-800", "Boeing 777-200", "Airbus A-320", "Airbus A-319", "Airbus A-321"};
        String randomModel = models[this.random.nextInt(models.length)];
        return new Aircraft(randomModel);
    }
}
