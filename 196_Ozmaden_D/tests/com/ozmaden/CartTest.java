package com.ozmaden;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Timer;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {


    @BeforeEach
    void setUp() {
        new Cart(10, 10);
    }

    @Test
    void getX() {
        assertEquals(10, Cart.getX());
    }

    @Test
    void setX() {
        Cart.setX(0);
        assertEquals(0, Cart.getX());
    }

    @Test
    void getY() {
        assertEquals(10, Cart.getY());
    }

    @Test
    void setY() {
        Cart.setY(0);
        assertEquals(0, Cart.getY());
    }

    @Test
    void run() {
        Timer timer = new Timer();

        assertDoesNotThrow(() -> {
            timer.schedule(new Cart(100, 111), 0, 2000);
            Thread.sleep(10000);
            timer.cancel();
        });
    }
}
