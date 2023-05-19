package models;

import java.math.BigInteger;

public class Compaction {
    private String name;
    private BigInteger size;

    public Compaction(String name, BigInteger size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }



    @Override
    public String toString() {
        return "Condensation{" +
                "name='" + name + '\'' +
                ", size=" + size +
                '}' + "\n";
    }
}
