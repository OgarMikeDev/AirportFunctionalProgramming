import library.Aircraft;
import library.Airport;
import library.Flight;
import library.Terminal;

import java.time.Instant;
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
        return airport.getAllAircrafts().stream()
                .map(Aircraft::getModel)
                .filter(currentModel -> currentModel.contains(model))
                .count();
    }

    public static List<Flight> findFlightsLeavingInTheNextHours(Airport airport, int hours) {
        //TODO Метод должен вернуть список отправляющихся рейсов в ближайшее количество часов.
        long second = hours * 60 * 60;
        Instant instantNow = Instant.now();
        return airport.getTerminals().stream()
                .map(Terminal::getFlights)
                .flatMap(List::stream)
                .filter(flight -> {
                    return flight.getType().equals(Flight.Type.DEPARTURE)
                            && flight.getDate().isAfter(instantNow)
                            && flight.getDate().isBefore(instantNow.plusSeconds(second));
                })
                .collect(Collectors.toList());
    }

    public static Map<String, Integer> findMapCountParkedAircraftByTerminalName(Airport airport) {
        //TODO Метод должен вернуть словарь с количеством припаркованных самолетов в каждом терминале.
        return airport.getTerminals().stream()
                .collect(Collectors.toMap(Terminal::getName,
                        terminal -> terminal.getParkedAircrafts().size()));
    }


    public static Optional<Flight> findFirstFlightArriveToTerminal(Airport airport, String terminalName) {
        //TODO Найти ближайший прилет в указанный терминал.
        Instant instantNow = Instant.now();

        Terminal firstTerminal =
                airport.getTerminals().stream()
                        .filter(currentTerminal -> currentTerminal.getName().equals(terminalName))
                        .findFirst()
                        .orElse(null);

        if (firstTerminal == null) {
            return Optional.empty();
        }

        return firstTerminal.getFlights().stream()
                .filter(currentFlight -> currentFlight.getType().equals(Flight.Type.ARRIVAL)
                        && currentFlight.getDate().isAfter(instantNow))
                .min(Comparator.comparing(flight -> flight.getDate()));
    }
}
