/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.edu;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Table class for drawing, storing cells
 */
public class Table{

    /**
     * Tanble constructor
     * @param height height of the table
     * @param width width of the table
     */
    public Table(int height, int width) {
        // Each cell has 4 lines => height * 4
        SetTabletop(new String[height*4][width]);
        //Setting 4 lines, always true
        SetCells();
        //going by those lines and filling them
        for (int i = 0; i < 4; i++) {
            int len = i%2 == 0? width : height;
            GetCells()[i] = FillLine(len - 1);
        }
        //Enumarating those cells for coordinates for console
        EnumCells(GetCells());
    }

    // string array for drawing table
    private String[][] tabletop;

    // 2d jagged array of cells
    private Cell[][] Cells;

    /**
     * Getter for tabletop
     * @return table array
     */
    public String[][] GetTabletop(){
        return tabletop;
    }

    /**
     * Setting new table and filling it with default stuff
     * @param table table array
     */
    public void SetTabletop(String[][] table){
        tabletop = table;
        FillTabletop();
    }

    /**
     * Filling table array of non-dynamic cells
     * Others will change over time
     */
    public void FillTabletop(){
        for (int i = 0; i < GetTabletop().length;){
            Arrays.fill(GetTabletop()[i], "+---+");
            i += 4;
        }

        for (int i = 3; i < GetTabletop().length;){
            Arrays.fill(GetTabletop()[i], "+---+");
            i += 4;
        }

        for (int i = 4; i < GetTabletop().length - 4; i++){
            for (int j = 1; j < GetTabletop()[i].length - 1; j++){
                GetTabletop()[i][j] = "     ";
            }
        }
    }

    /**
     * Getter for cells array
     * @return
     */
    public Cell[][] GetCells(){
        return Cells;
    }

    /**
     * Setter for cells array, always 4
     */
    public void SetCells(){
        Cells = new Cell[4][];
    }

    /**
     * Filling new line of cells (excl last member)
     * @param len len of line
     * @return return line of 2d jagged array (one of four)
     */
    Cell[] FillLine(int len){
        // maximum allowable taxi/pencell
        LinkedList<Cell> stack = new LinkedList<>(){{
            push(new Taxi());
            push(new Taxi());
            push(new PenaltyCell());
            push(new PenaltyCell());
        }};
        // for true randomness
        Collections.shuffle(stack);
        LinkedList<Cell> cells = new LinkedList<>(){{
            // this always needs to be true
            push(new Bank());
        }};
        // Adding shops and taxis/pencells randomly
        for (int i = 0; i < len - 2; i++) {
            int rnum = MyRand.rndInt(0, 4);
            if (rnum == 3 || stack.isEmpty()) {
                cells.add(new Shop());
            } else {
                cells.add(stack.pop());
            }
        }
        //Shuffle the result
        Collections.shuffle(cells);
        //Put empty cell in the beggining
        cells.addFirst(new EmptyCell());
        //Convert to array
        return(cells.toArray(new Cell[len]));
    }

    /**
     * Setting each cell a coordinate
     * @param cells all cells
     */
    void EnumCells(Cell[][] cells){
        int x = 0, y = 0;
        for (int i = 0; i < GetCells().length; i++){
            for(int j = 0; j < GetCells()[i].length; j++){
                GetCells()[i][j].setXY(x, y);
                if (i == 0) {
                    x++;
                } else if (i == 1) {
                    y--;
                } else if (i == 2){
                    x--;
                } else if (i == 3){
                    y++;
                }
            }
        }
    }

    /**
     * Dynamically changing the points of table array
     * New positions, names and so on
     */
    public void UpdateTabletop(){
        LinkedList<String> names = new LinkedList<>();
        LinkedList<String> locs = new LinkedList<>();

        for (int i = 0; i < GetCells().length; i++){
            for(int j = 0; j < GetCells()[i].length; j++){
                names.add(GetCells()[i][j].getName());
                locs.add(GetCells()[i][j].getPos());
            }
        }

        for(int i = 0; i < GetTabletop()[0].length; i++){
            GetTabletop()[1][i] = "| " + names.pop() + " |";
            GetTabletop()[2][i] = "|" + locs.pop() + "|";
        }

        for(int i = 5; i < GetTabletop().length;){
            GetTabletop()[i][GetTabletop()[0].length - 1] = "| " + names.pop() + " |";
            GetTabletop()[i+1][GetTabletop()[0].length - 1] = "|" + locs.pop() + "|";
            i += 4;
        }

        for(int i = GetTabletop()[0].length - 2; i >= 0; i--){
            GetTabletop()[GetTabletop().length - 3][i] ="| " + names.pop() + " |";
            GetTabletop()[GetTabletop().length - 2][i] = "|" + locs.pop() + "|";
        }

        for(int i = GetTabletop().length - 6; i > 2;){
            GetTabletop()[i-1][0] = "| " + names.pop() + " |";
            GetTabletop()[i][0] = "|" + locs.pop() + "|";
            i -= 4;
        }
    }

    /**
     * Draw the entire string table array
     */
    public void Draw(){
        UpdateTabletop();
        for (int i = 0; i < GetTabletop().length; i++){
            for (int j = 0; j < GetTabletop()[i].length; j++){
                System.out.print(GetTabletop()[i][j]);
            }
            System.out.printf("%n");
        }
    }
}
