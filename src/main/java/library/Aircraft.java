//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package library;

import java.util.Objects;

public class Aircraft {
    private final String model;

    public Aircraft(String model) {
        this.model = model;
    }

    public String getModel() {
        return this.model;
    }

    public String toString() {
        return this.model;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Aircraft aircraft = (Aircraft)o;
            return Objects.equals(this.model, aircraft.model);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.model});
    }
}
