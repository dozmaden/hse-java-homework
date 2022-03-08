/**
 * @author <a href="mailto:dozmaden@edu.hse.ru"> Deniz Ozmaden</a>
 */

package com.edu;
import java.util.*;

public class Main {
    /**
     * Our main, nothing too complicated.
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        // defaults
        int height = 6;
        int width = 6;
        int money = 500;

        if(args.length != 3 || args == null){
            System.out.println("Something went wrong!");
            System.out.println("No arguments, but will use default ones.");
        } else if (args.length == 3){
            try{
                height = Integer.parseInt(args[0]);
                width = Integer.parseInt(args[1]);
                money = Integer.parseInt(args[2]);
                if(height < 6 || height > 30 || width < 6 ||
                        width > 30 || money < 500 || money > 15000){
                    throw new Exception();
                }
            } catch (Exception e){
                System.out.println("Error while parsing arguments!");
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }

        // Initialize our main classes
        Table tbl = new Table(height, width);
        User player = new User(money);
        Bot bot = new Bot(money);

        // Dictionary for the future moves on the table.
        Hashtable<Integer, Cell> dict = CollectDictionary(tbl.GetCells());

        try{
            EnterGame(tbl, player, bot, dict);
        } catch(Exception e){
            // if anything wrong happens
            System.out.println(e.getMessage());
        }
    }

    /**
     * All of the game happens in here
     * @param tbl Table class.
     * @param player You
     * @param bot Bot
     * @param dict Dictionary that maps 2d array to int cnt of moves.
     */
    public static void EnterGame(Table tbl, User player, Bot bot, Hashtable<Integer, Cell> dict){

        // determine random turn
        boolean userTurn = MyRand.rndInt(0, 2) == 0;

        // default locations for players
        int plcnt = 0;
        int botcnt = 0;

        System.out.println("Welcome to Monopoly!");
        promptKey();

        // Players arrive at (0, 0 ) in the table.
        dict.get(plcnt).Arrives(player);
        dict.get(botcnt).Arrives(bot);
        tbl.Draw();
        // Important thing to note - we don't see bot stats, that's by design
        GameInfo(player);
        promptKey();

        // check if game should continue
        while(player.getMoney() > 0 && bot.getMoney() > 0){

            // random dice
            int step = MyRand.rndInt(1, 7) + MyRand.rndInt(1, 7);
            if(userTurn){
                // leaves prev cell
                dict.get(plcnt).Leaves(player);

                System.out.printf("User moves by %d cells %n", step);
                plcnt += step;

                //if starting new round
                plcnt = plcnt % dict.size();
                dict.get(plcnt).Arrives(player);

                //gets into a loop if was in a taxi
                plcnt = TaxiBeh(player, plcnt, dict);

                // gives money that he lost on opponents shops back
                bot.setMoney(bot.getMoney() + player.getOwedmoney());
                player.setOwedmoney(0);
                userTurn = false;
            } else{
                // Same logic applies here. Not in separate methods because it will become confusing.
                dict.get(botcnt).Leaves(bot);

                System.out.printf("Bot moves by %d cells %n", step);
                botcnt += step;
                botcnt = botcnt % dict.size();

                dict.get(botcnt).Arrives(bot);
                botcnt = TaxiBeh(bot, botcnt, dict);

                player.setMoney(player.getMoney() + bot.getOwedmoney());
                bot.setOwedmoney(0);
                userTurn = true;
            }
            tbl.Draw();
            GameInfo(player);
            promptKey();
        }

        // Gave over logic.
        if(player.getMoney()<0){
            System.out.println("YOU LOST! GAME OVER");
        } else if (bot.getMoney() < 0){
            System.out.println("YOU WON! GAME OVER");
        }
    }

    /**
     * Prints all neccessary info
     * @param p player - for money info
     */
    public static void GameInfo(Player p){
        System.out.printf("Your money: %.2f %n", p.getMoney());
        System.out.printf("Spent money: %.2f %n", p.getAbsolutemoney());
        System.out.printf("Penalty coeff: %.2f %n", Coeffs.getPenaltyCoeff());
        System.out.printf("Credit coeff: %.2f %n", Coeffs.getCreditCoeff());
        System.out.printf("Debt coeff: %.2f %n", Coeffs.getDebtCoeff());
        System.out.println("_______________________");
    }

    /**
     * Maps every cell to an integer count of the table. Easier to move players this way
     * @param c 2d jaddeg array of cells
     * @return new hashtable
     */
    public static Hashtable<Integer, Cell> CollectDictionary(Cell[][] c) {
        Hashtable<Integer, Cell> dict = new Hashtable<Integer, Cell>();
        int cnt = 0;
        for (Cell[] cells : c) {
            for (Cell cell : cells) {
                dict.put(cnt, cell);
                cnt++;
            }
        }
        return dict;
    }

    /**
     * What to do when you get in a taxi
     * @param p bot or player
     * @param cnt location of that player/bot
     * @param dict to push him forward
     * @return returns count because it can get into a loop and go really far
     */
    public static int TaxiBeh(Player p, int cnt, Hashtable<Integer, Cell> dict){
        while(p.getTaxiDistance() != 0){
            dict.get(cnt).Leaves(p);
            cnt += p.getTaxiDistance();
            cnt = cnt%dict.size();
            p.setTaxiDistance(0);
            dict.get(cnt).Arrives(p);
        }
        return cnt;
    }

    /**
     * To press in console
     */
    public static void promptKey(){
        System.out.println("Press any key to continue!");
        try
        {
            System.in.read();
        }
        catch(Exception e)
        {
            System.out.print(e.getMessage());
        }
    }
}
