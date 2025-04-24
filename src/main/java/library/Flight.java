//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package library;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Flight implements Comparable<Flight> {
    private static final ZoneId zoneId = ZoneId.systemDefault();
    private static final DateTimeFormatter HOUR_FORMAT;
    private final String code;
    private final Type type;
    private final Instant date;
    private final Aircraft aircraft;

    public Flight(String code, Type type, Instant date, Aircraft aircraft) {
        this.code = code;
        this.type = type;
        this.date = date;
        this.aircraft = aircraft;
    }

    public String getCode() {
        return this.code;
    }

    public Type getType() {
        return this.type;
    }

    public Instant getDate() {
        return this.date;
    }

    public Aircraft getAircraft() {
        return this.aircraft;
    }

    public String toString() {
        String dateFormatted = HOUR_FORMAT.format(this.date);
        return dateFormatted + " / " + this.code + " / " + this.type;
    }

    static {
        HOUR_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").withZone(zoneId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(code, flight.code) && type == flight.type && Objects.equals(date, flight.date) && Objects.equals(aircraft, flight.aircraft);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, type, date, aircraft);
    }

    @Override
    public int compareTo(Flight o) {
        return this.getDate().compareTo(o.getDate());
    }

    public static enum Type {
        ARRIVAL,
        DEPARTURE;

        private Type() {
        }
    }
}
