package hs.karlsruhe.wgfinder.Entity;

enum Geschlecht {
    M,
    W

}

public class GeschlechtClass {
    public static void main(String[] args) {
        Geschlecht myVar = Geschlecht.M;

        switch(myVar) {
            case M:
                System.out.println("männlich");
                break;
            case W:
                System.out.println("weiblich");
                break;

        }
    }
}