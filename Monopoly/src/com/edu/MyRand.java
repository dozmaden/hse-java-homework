/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.edu;

import java.util.Random;

/*
My class for random methods
 */
class MyRand{
    /**
     * Random Int method
     * @param min minimum int
     * @param max maximum int
     * @return random integer between the two
     */
    public static int rndInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    /**
     * Random Double method
     * @param min minimum int
     * @param max maximum int
     * @return random double
     */
    public static double rndDouble(double min, double max) {
        return(min + (max - min) * Math.random());
    }
}
