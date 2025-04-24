import library.Aircraft;
import library.Airport;
import library.Flight;
import library.Terminal;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        String model = "Boeing";
        Airport airport = Airport.getInstance();
        System.out.println("Count all aircraft witch found model \"" + model + "\": " +
                findCountAircraftWithModelAirbus(airport, model));

        System.out.println("\nList flights, which through two hours: ");
        for (Flight currentFlight : findFlightsLeavingInTheNextHours(airport, 2)) {
            System.out.println("\"" + currentFlight + "\"");
        }

        System.out.println("\nThe number of parked planes in each terminal: " +
                findMapCountParkedAircraftByTerminalName(airport));

        System.out.println("\nFind the nearest flight to the specified terminal - A: " +
                findFirstFlightArriveToTerminal(airport, "A"));
    }

    public static long findCountAircraftWithModelAirbus(Airport airport, String model) {
        //TODO Метод должен вернуть количество самолетов указанной модели.
        // подходят те самолеты, у которых name начинается со строки model
        int count = 0;
        for (Aircraft currentAircraft : airport.getAllAircrafts()) {
            if (currentAircraft.toString().contains(model)) {
                count++;
            }
        }
        return count;
    }

    public static List<Flight> findFlightsLeavingInTheNextHours(Airport airport, int hours) {
        //TODO Метод должен вернуть список отправляющихся рейсов в ближайшее количество часов.
        ZonedDateTime timeNow = Instant.now().atZone(ZoneId.of("Europe/Moscow"));
        ZonedDateTime timeSpecifiedPeriod = timeNow.plusSeconds(3600 * hours);

        System.out.println("Наст. вр: " + timeNow);
        System.out.println("Конец вр: " + timeSpecifiedPeriod);
        List<Flight> flightsLeavingInTheNextHours = new ArrayList<>();

        for (int i = 0; i < airport.getTerminals().size(); i++) {
            Terminal currentTerminal = airport.getTerminals().get(i);
            for (Flight currentFlight : currentTerminal.getFlights()) {
                ZonedDateTime timeCurrentFlight = currentFlight.getDate().atZone(ZoneId.of("Europe/Moscow"));
                if ((timeCurrentFlight.isAfter(timeNow) || timeCurrentFlight.isEqual(timeNow))
                        &&
                        (timeCurrentFlight.isBefore(timeSpecifiedPeriod)) || timeCurrentFlight.isEqual(timeNow)) {
                    flightsLeavingInTheNextHours.add(currentFlight);
                }
            }
        }

        return flightsLeavingInTheNextHours;
    }

    public static Map<String, Integer> findMapCountParkedAircraftByTerminalName(Airport airport) {
        //TODO Метод должен вернуть словарь с количеством припаркованных самолетов в каждом терминале.
        Map<String, Integer> mapCountParkedAircraftByTerminalName = new HashMap<>();

        for (Terminal currentTerminal : airport.getTerminals()) {
            String currentNameTerminal = currentTerminal.getName();
            int countParkedAircrafts = currentTerminal.getParkedAircrafts().size();
            mapCountParkedAircraftByTerminalName.put(currentNameTerminal, countParkedAircrafts);
        }

        return mapCountParkedAircraftByTerminalName;
    }


    public static Flight findFirstFlightArriveToTerminal(Airport airport, String terminalName) {
        //TODO Найти ближайший прилет в указанный терминал.
        ZonedDateTime zonedTimeNow = Instant.now().atZone(ZoneId.of("Europe/Moscow"));

        List<Terminal> listTerminals = airport.getTerminals();
        Set<Flight> sortedFlights = new TreeSet<>();
        for (Terminal currentTerminal : listTerminals) {
            if (currentTerminal.getName().equals(terminalName)) {
                for (Flight currentFlight : currentTerminal.getFlights()) {
                    ZonedDateTime zonedTimeCurrentFlight = currentFlight.getDate().atZone(ZoneId.of("Europe/Moscow"));
                    if (currentFlight.getType().equals(Flight.Type.ARRIVAL) &&
                            zonedTimeCurrentFlight.isAfter(zonedTimeNow)
                    ) {
                       sortedFlights.add(currentFlight);
                    }
                }
            }
        }

        for (Flight currentFlightArrival : sortedFlights) {
            return currentFlightArrival;
        }
        return null;
    }
}
