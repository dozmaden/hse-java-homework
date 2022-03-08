/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.edu;

/**
 * Cell class
 */
abstract class Cell{
    /**
     * Cell constructor
     * Sets spaces in string for table
     */
    public Cell(){
        setPos("   ");
    }

    // x, y for console
    private int x, y;

    /**
     * United setter
     * @param xn new x
     * @param yn new y
     */
    public void setXY(int xn, int yn){
        x = xn;
        y = yn;
    }

    /**
     * Getting X
     * @return x
     */
    public int getX(){
        return x;
    }

    /**
     * Getting Y
     * @return y
     */
    public int getY(){
        return y;
    }

    // name of cell
    private String name;

    /**
     * Getter for cell name
     * @return name
     */
    public String getName() { return name; }

    /**
     * Setter for new cell name
     * @param n new name
     */
    public void setName(String n){ name = n; }

    /**
     * String for table drawing, position of player
     */
    private String pos;

    /**
     * Getter for player position
     * @return position
     */
    public String getPos() {
        return pos;
    }

    /**
     * Setter for player position
     * @param pos position
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     * Player/bot arriving in cell
     * @param p player/bot
     */
    public void Arrives(Player p){
        // if someone else is here, you are together now
        setPos(getPos().equals("   ")?
                p.getUname(): "Y/B");

        // logging for console
        System.out.printf("%s arrived at %s (x: %d, y: %d). %n",
                p.getUname(),
                getClass().getSimpleName(),
                getX(), getY());
    }

    /**
     * Player/bot leaving a cell
     * @param p player/bot
     */
    public void Leaves(Player p){
        // leaving someone else
        if(getPos().equals("Y/B")){
            setPos(p instanceof User? "BOT": "YOU");
        } else{
            //leaving nobody
            setPos("   ");
        }

        //logging for console
        System.out.printf("%s left the %s (x: %d, y: %d) %n",
                p.getUname(),
                this.getClass().getSimpleName(),
                getX(), getY());
    }
}
