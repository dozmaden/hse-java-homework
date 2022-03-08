package com.edu;

import java.util.*;

public class Tree<T extends Martian<?>> {

    private final String lineSeparator = System.lineSeparator();
    private T mainNode;

    private ArrayDeque<String> result;

    /**
     * constructor
     * @param mainNode
     */
    public Tree(T mainNode) {
        this.mainNode = mainNode;
    }

    /**
     * get text report from tree
     * @return text string
     * @throws Exception
     */
    public String getReport() throws Exception {
        result = new ArrayDeque<>();

        Collection<T> mainchildren = (Collection<T>) mainNode.getChildren();

        FilterResult(mainNode, 0);
        traverseTree(mainchildren, 4);

        String ress = "";
        for (var s : result) {
            ress += s + "\n";
        }
        return ress;
    }

    /**
     * get tree fromt text
     * @param report text report
     * @return Tree
     * @throws Exception
     */
    public Tree<?> parse(String report) throws Exception {
        var lines = report.split("\n");

        ArrayList<Innovator<?>> nodes = new ArrayList<>();
        ArrayList<Integer> spaces = new ArrayList<>();

        for (var line : lines
        ) {
            nodes.add(filterTree(line));
            spaces.add(line.length() - line.replace(" ", "").length());
        }

        for (int i = 1; i < nodes.size(); i++) {
            if (spaces.get(i) > spaces.get(i - 1)) {
                nodes.get(i - 1).addChild(nodes.get(i));
            } else {
                int j = i;
                while (j > -1) {
                    if (spaces.get(i) > spaces.get(j)) {
                        nodes.get(j).addChild(nodes.get(i));
                    }
                    j--;
                }
                nodes.get(0).addChild(nodes.get(i));
            }
        }

        T res;
        if (lines[0].contains("Conservative")) {
            Conservative<?> con = new Conservative<>(nodes.get(0));
            res = (T) con;
        } else {
            res = (T) nodes.get(0);
        }

        mainNode = res;
        return this;
    }

    /**
     * filtering line to understand which type
     * @param line
     * @return Innovator
     * @throws Exception
     */
    public Innovator<?> filterTree(String line) throws Exception {
        if (line.contains("String")) {
            return new Innovator<String>(findGenCode(line));
        } else if (line.contains("Integer")) {
            return new Innovator<Integer>(Integer.parseInt(findGenCode(line)));
        } else if (line.contains("Double")) {
            return new Innovator<Double>(Double.parseDouble(findGenCode(line)));
        } else {
            throw new Exception("No needed type!");
        }
    }

    /**
     * finding name of Matrian in string
     * @param line
     * @return
     */
    public String findGenCode(String line) {
        return line.split(":")[1].replace(")", "");
    }

    /**
     * recursive algorithm to build a text report
     * @param children
     * @param space
     * @throws Exception
     */
    public void traverseTree(Collection<T> children, int space) throws Exception {
        for (var c : children) {
            if (!c.getChildren().isEmpty()) {
                FilterResult(c, space);
                space += 4;
                Collection<T> newchildren = (Collection<T>) c.getChildren();
                traverseTree(newchildren, space);
                space -= 4;
            } else {
                FilterResult(c, space);
            }
        }
    }

    /**
     * filtering a child for text report
     * @param child
     * @param space
     */
    public void FilterResult(T child, int space) {
        String martianType = null;
        if (child instanceof Innovator) {
            martianType = "InnovatorMartian";
        } else if (child instanceof Conservative) {
            martianType = "ConservativeMartian";
        }

        String objectType = null;
        if (child.getGenCode() instanceof String) {
            objectType = "String";
        } else if (child.getGenCode() instanceof Integer) {
            objectType = "Integer";
        } else if (child.getGenCode() instanceof Double) {
            objectType = "Double";
        }


        result.add(" ".repeat(space) +
                String.format("%s (%s:%s)", martianType, objectType, child.getGenCode()));
    }
}
