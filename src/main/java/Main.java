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

//        System.out.println("\nList flights, which through two hours: ");
//
//        for (Flight currentFlight : findFlightsLeavingInTheNextHours(airport, 2)) {
//            System.out.println("\"" + currentFlight + "\"");
//        }

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

//    public static List<Flight> findFlightsLeavingInTheNextHours(Airport airport, int hours) {
//        //TODO Метод должен вернуть список отправляющихся рейсов в ближайшее количество часов.
//
//    }

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


    public static ZonedDateTime findFirstFlightArriveToTerminal(Airport airport, String terminalName) {
        //TODO Найти ближайший прилет в указанный терминал.
        Terminal specifiedTerminal = null;
        Instant specifiedInstant = Instant.now().atZone(ZoneId.of("Europe/Moscow")).toInstant();
        List<Flight> listFlightsArrival = new ArrayList<>();


        for (Terminal currentTerminal : airport.getTerminals()) {
            if (currentTerminal.getName().equals(terminalName)) {
                specifiedTerminal = currentTerminal;
                break;
            }
        }

        for (Flight currentFlight : specifiedTerminal.getFlights()) {
            if ((currentFlight.getType().equals(Flight.Type.ARRIVAL)) &&
                    (currentFlight.getDate().isAfter(specifiedInstant))) {
                listFlightsArrival.add(currentFlight);
            }
        }

        Set<Instant> setDates = new TreeSet<>();
        for (Flight currentFlight : listFlightsArrival) {
            setDates.add(currentFlight.getDate());
        }

        for (Instant currentDate : setDates) {
            return currentDate.atZone(ZoneId.of("Europe/Moscow"));
        }

        return Instant.now().atZone(ZoneId.of("Europe/Moscow"));
    }
}
