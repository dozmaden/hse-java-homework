package com.ozmaden;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @Test
    void runTest() {
        Cart crt = new Cart(420, 69);

        assertDoesNotThrow(() -> {
            Thread[] animals = new Thread[] {
                    new Animal("Bear", 361), // Лебедь
                    new Animal("Fish", 2077), // Рак
                    new Animal("Fox", 1488), // Щука
            };

            Main.AnimalsMoveCart(animals, crt);
        });
    }

    @Test
    void moveCart() {
        Cart crt = new Cart(0, 0);
        Animal anml = new Animal("Wolf", 60);
        anml.MoveCart();
        assertNotEquals(new double[] {0, 0}, new double[] {Cart.getX(), Cart.getY()});
    }
}
