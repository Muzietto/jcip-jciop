package net.faustinelli.lift.queue;

public class Floor implements Comparable {

    private Integer floorNumber;

    public Floor(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Integer number() {
        return floorNumber;
    }

    @Override
    public int compareTo(Object o) {
        return floorNumber.compareTo(((Floor)o).number());
    }

    @Override
    public boolean equals(Object obj) {
        return floorNumber.equals(((Floor)obj).number());
    }

    @Override
    public int hashCode() {
        return floorNumber.hashCode();
    }
}
