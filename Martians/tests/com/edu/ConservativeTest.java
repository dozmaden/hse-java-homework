package com.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.management.ObjectName;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;

class ConservativeTest {

    Innovator<Integer> iv1;
    Innovator<Integer> iv2;
    Innovator<Integer> iv3;
    Conservative<Integer> con1;
    Conservative<Integer> con2;
    Conservative<Integer> con3;

    @BeforeEach
    void setUp() {
        iv1 = new Innovator<>(1);
        iv2 = new Innovator<>(2);
        iv3 = new Innovator<>(3);
    }

    @Test
    void getParent() {

    }

    @Test
    void getChildren() {
    }

    @Test
    void getDescendants() {
        iv2.addChild(iv3);
        iv1.addChild(iv2);
        con1 = new Conservative<>(iv1);

        System.out.println(con1.getDescendants());
    }

    @Test
    void hasChildWithValue() {
        iv1.addChild(iv2);

        con1 = new Conservative<>(iv1);
        System.out.println(con1.hasChildWithValue(2));
    }

    @Test
    void hasDescendantWithValue() {
        iv2.addChild(iv3);
        iv1.addChild(iv2);

        con1 = new Conservative<>(iv1);
        System.out.println(con1.hasDescendantWithValue(3));
    }
}
