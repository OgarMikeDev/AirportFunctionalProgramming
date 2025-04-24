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
            if (currentAircraft.getModel().contains(model)) {
                count++;
            }
        }

        return count;
    }

    public static List<Flight> findFlightsLeavingInTheNextHours(Airport airport, int hours) {
        //TODO Метод должен вернуть список отправляющихся рейсов в ближайшее количество часов.
        ZonedDateTime zonedTimeNow = Instant.now().atZone(ZoneId.of("Europe/Moscow"));
        ZonedDateTime zonedTimePlusHours = zonedTimeNow.plusHours(hours);

        List<Flight> listFlightsLeavingInTheNextHours = new ArrayList<>();

        for (Terminal currentTerminal : airport.getTerminals()) {
            for (Flight currentFlight : currentTerminal.getFlights()) {
                ZonedDateTime zonedTimeCurrentFlight = currentFlight.getDate()
                        .atZone(ZoneId.of("Europe/Moscow"));
                if (currentFlight.getType().equals(Flight.Type.DEPARTURE) &&
                        (zonedTimeCurrentFlight.isEqual(zonedTimeNow) ||
                                zonedTimeCurrentFlight.isAfter(zonedTimeNow)) &&
                        (zonedTimeCurrentFlight.isEqual(zonedTimePlusHours) ||
                                zonedTimeCurrentFlight.isBefore(zonedTimePlusHours))
                ) {
                    listFlightsLeavingInTheNextHours.add(currentFlight);
                }
            }
        }

        return listFlightsLeavingInTheNextHours;
    }


    public static Map<String, Integer> findMapCountParkedAircraftByTerminalName(Airport airport) {
        //TODO Метод должен вернуть словарь с количеством припаркованных самолетов в каждом терминале.
        Map<String, Integer> mapCountParkedAircraftByTerminalName = new HashMap<>();

        for (Terminal currentTerminal : airport.getTerminals()) {
           mapCountParkedAircraftByTerminalName.put(
                   currentTerminal.getName(), currentTerminal.getParkedAircrafts().size());
        }

        return mapCountParkedAircraftByTerminalName;
    }


    public static Flight findFirstFlightArriveToTerminal(Airport airport, String terminalName) {
        //TODO Найти ближайший прилет в указанный терминал.
        ZonedDateTime zonedDateTimeNow = Instant.now().atZone(ZoneId.of("Europe/Moscow"));

        Set<Flight> sortedFlights = new TreeSet<>();

        for (Terminal currentTerminal : airport.getTerminals()) {
            if (currentTerminal.getName().equals(terminalName)) {
                for (Flight currentFlight : currentTerminal.getFlights()) {
                    ZonedDateTime zonedTimeFlight = currentFlight.getDate()
                            .atZone(ZoneId.of("Europe/Moscow"));
                    if (currentFlight.getType().equals(Flight.Type.ARRIVAL) &&
                            zonedTimeFlight.isAfter(zonedDateTimeNow)) {
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
