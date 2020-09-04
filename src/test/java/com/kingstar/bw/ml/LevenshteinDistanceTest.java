package com.kingstar.bw.ml;

import org.junit.Test;

import static org.junit.Assert.*;

public class LevenshteinDistanceTest {

    @Test
    public void computeLevenshteinDistance_Optimized() {

        LevenshteinDistance.computeLevenshteinDistance_Optimized("test23","test1");
    }
    @Test
    public void computeLevenshteinDistance() {

        LevenshteinDistance.computeLevenshteinDistance("test23","test1");
    }

}