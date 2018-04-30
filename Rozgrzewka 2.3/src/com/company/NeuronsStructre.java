package com.company;

public class NeuronsStructre implements Comparable {

    private int index;
    private double distance;

    public NeuronsStructre(int index, double distance) {
        this.index = index;
        this.distance = distance;
    }

    @Override
    public int compareTo(Object o) {
        NeuronsStructre structre = (NeuronsStructre) o;
        return Double.compare(distance, structre.distance);
    }

    @Override
    public String toString(){
        return index+" "+distance;
    }

}

