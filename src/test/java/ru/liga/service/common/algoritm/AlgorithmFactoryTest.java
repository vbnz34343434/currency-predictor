package ru.liga.service.common.algoritm;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AlgorithmFactoryTest {

    AlgorithmFactory factory = new AlgorithmFactory();

    @Test
    public void getActualAlgorithm() {
        assertEquals(ActualAlgorithm.class, factory.getAlgorithm("actual").getClass());
    }

    @Test
    public void getMoonAlgorithm() {
        assertEquals(MoonAlgorithm.class, factory.getAlgorithm("moon").getClass());
    }

    @Test
    public void getLinearAlgorithm() {
        assertEquals(LinearAlgorithm.class, factory.getAlgorithm("linear").getClass());
    }
}