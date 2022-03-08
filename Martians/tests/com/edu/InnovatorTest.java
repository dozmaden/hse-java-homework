package com.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InnovatorTest {

    Innovator<Integer> iv1;
    Innovator<Integer> iv2;
    Innovator<Integer> iv3;

    @Test
    void getParent() {
        Innovator<Integer> iv = new Innovator<>(3);
        var child = new Innovator<>(5);
        iv.addChild(child);
        assertEquals(iv, child.getParent());
    }

    @Test
    void getChildren() {
        iv1.addChild(iv2);
        iv1.addChild(iv3);
        for (var child: iv1.getChildren()
             ) {
            System.out.println("iv1 has child" + child);
        }
    }

    @Test
    void getDescendants() {
        iv2.addChild(iv3);
        iv1.addChild(iv2);
        for (var child: iv1.getDescendants()
        ) {
            System.out.println("iv1 has descendant " + child);
        }
    }

    @Test
    void hasChildWithValue() {
        iv1.addChild(iv2);
        System.out.println(iv1.hasChildWithValue(2));
    }

    @Test
    void hasDescendantWithValue() {
        iv2.addChild(iv3);
        iv1.addChild(iv2);
        System.out.println(iv1.hasDescendantWithValue(3));
    }

    @Test
    void setParent() {
        iv2.setParent(iv3);
        iv1.setParent(iv2);
        System.out.println(iv1.getParent());
    }

    @Test
    void setNewDescendants() {
        Innovator<Integer> iv4 = new Innovator<>(4);
        iv2.addChild(iv3);
        iv2.addChild(iv4);
        iv1.setNewDescendants(iv2.getChildren());
        System.out.println(iv1.getDescendants());
    }

    @Test
    void deleteChild() {
        iv1.addChild(iv2);
        iv1.addChild(iv3);
        iv1.deleteChild(iv3);
        System.out.println(iv1.getChildren());
    }

    @BeforeEach
    void setUp() {
        iv1 = new Innovator<>(1);
        iv2 = new Innovator<>(2);
        iv3 = new Innovator<>(3);
    }

    @Test
    void setGenCode() {
        iv2.setGenCode("new gene code!");
        iv1.addChild(iv2);
        System.out.println(iv1.getChildren().toArray()[0]);
        iv1.addChild(new Innovator<>(null));
    }
}
