package com.ozmaden;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void mainTestWithTwoArguments() {
        String x = "-420.04";
        String y = "20.02";

        assertDoesNotThrow(() -> {
            Main.main(new String[] {x, y});
        });
    }

    @Test
    void mainTestWithOneArgument() {
        String x = "13812.5670";

        assertDoesNotThrow(() -> {
            Main.main(new String[] {x});
        });
    }

    @Test
    void mainTestWithNoArguments() {
        assertDoesNotThrow(() -> {
            Main.main(null);
        });
    }

    @Test
    void mainTestWithIncorrectArgs() {
        assertDoesNotThrow(() -> {
            Main.main(new String[] {"blablabla", "c# is better change my mind"});
        });
    }
}
