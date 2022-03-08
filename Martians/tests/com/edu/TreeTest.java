package com.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeTest {

    @Test
    void getReport() throws Exception {
        var john = new Innovator<String>("John");

        var james = new Innovator<String>("James");
        var jared = new Innovator<String>("Jared");
        james.addChild(jared);

        var johan = new Innovator<String>("Johan");

        var jamil = new Innovator<String>("Jamil");
        var joel = new Innovator<String>("Joel");
        var jack = new Innovator<String>("Jack");

        joel.addChild(jack);
        jamil.addChild(joel);

        john.addChild(james);
        john.addChild(johan);
        john.addChild(jamil);

        Tree<Martian<?>> tr = new Tree<>(john);
        String res = tr.getReport();

        assertEquals(tr, tr.parse(res));
    }

    @Test
    void parse() throws Exception {
        var input =
                "InnovatorMartian (String:John)\n" +
                "    InnovatorMartian (String:James)\n" +
                "        InnovatorMartian (String:Jared)\n" +
                "    InnovatorMartian (String:Johan)\n" +
                "    InnovatorMartian (String:Jamil)\n" +
                "        InnovatorMartian (String:Joël)\n" +
                "            InnovatorMartian (String:Jack)\n";

        Tree<?> tr = new Tree<>(new Innovator<>("dummy"));
        tr.parse(input);

        String output = tr.getReport();
        assertEquals(input, output);
    }
}
