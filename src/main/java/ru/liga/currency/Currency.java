package ru.liga.currency;

public abstract class Currency {
    private String name;
    private String rateFileName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRateFileName() {
        return rateFileName;
    }

    public void setRateFileName(String rateFileName) {
        this.rateFileName = rateFileName;
    }

    @Override
    public String toString() {
        return "Currency {" +
                "name='" + name + '\'' +
                ", rateFileName='" + rateFileName + '\'' +
                '}';
    }
}
