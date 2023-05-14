package models;

import java.math.BigInteger;

public class Condensation {
    private String name;
    private BigInteger size;
    private BigInteger initSize;
    private BigInteger finishSize;

    public Condensation(String name, BigInteger size, BigInteger initSize, BigInteger finishSize) {
        this.name = name;
        this.size = size;
        this.initSize = initSize;
        this.finishSize = finishSize;
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

    public BigInteger getInitSize() {
        return initSize;
    }

    public void setInitSize(BigInteger initSize) {
        this.initSize = initSize;
    }

    public BigInteger getFinishSize() {
        return finishSize;
    }

    public void setFinishSize(BigInteger finishSize) {
        this.finishSize = finishSize;
    }

    @Override
    public String toString() {
        return "Condensation{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", initSize=" + initSize +
                ", finishSize=" + finishSize +
                '}' + "\n";
    }
}
