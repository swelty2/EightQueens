
package eightqueens;
import java.util.*;

/**
 *
 * @author Sarah Welty 800 980 256
 * 
 */

public class EightQueens 
{
    //Identify class attributes 
    private int neighbors = 8;
    private int moves = 0;
    private int restarts = 0;
    private boolean newMap = true;
    final private int [] [] map = new int[8][8];
    final private int [][] testMap = new int[8][8];
    private int queenHeuristic = 0;
    private int queenLocs = 0;
    
    //Constructor that creates obj and initializes map
    public EightQueens() {
        //creates 8x8 map and sets each slot to 0
        for(int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                map[i][k] = 0;
            }
        }//end of for loop
    }
    /*
    generate a random starting state which 
    places a queen in a random row of each column.  
    */
   
    
    public void randomizeMap( ){ //randomizes the map
      Random rand = new Random( );

      
      while(queenLocs < 8){
            for(int i = 0; i < 8; i++){
                //place queen at random row location in each column
                map[rand.nextInt(7)][i] = 1;
                //incrmenet the counter
                queenLocs++;
                }//end for
            }//end of while loop
      queenHeuristic = queenHeuristic(map);
    }//end of randomizeMap
   
     
  //Row check
    public boolean rowCheck(int [][] test, int a){ 
        int count = 0;
        boolean colFound = false;
       
      
        for(int i = 0; i < 8; i++){
            if(test[i][a] == 1){
                count++;
            }
        }
        if(count > 1){
            colFound = true;
        }
        return colFound;
    }
    

    
    //Check column
    public boolean colCheck(int [][] test, int j)
    {
        boolean exFound = false;
        int count = 0;
        for(int i = 0; i < 8; i++){
            if(test[j][i] == 1){
                count++;
            }
        }
        if(count > 1){
          exFound = true;
        }
        return exFound;
    }
    

  //Identify if there are any other queens diagonally from h
    public boolean diagCheck(int [][] test, int a, int b)
    {
        boolean match = false;
      
        for(int i = 1; i < 8; i++){
            //if no diagonal queen indicated
            if(match){
                break;
            }

            if((a+i < 8)&&(b+i < 8)){
                
                if(test[a+i][b+i] == 1){
                    match = true;
                }
            }
            if((a-i >= 0)&&(b-i >= 0)){
                if(test[a-i][b-i] == 1){
                    match = true;
                }
            }
            if((a+i < 8)&&(b-i >= 0)){
                if(test[a+i][b-i] == 1){
                    match = true;
                }
            }  
            if((a-i >= 0)&&(b+i < 8)){
                if(test[a-i][b+i] == 1){
                    match = true;
                }
            }  
        }
        return match;
    }
    /*
    Psuedo code for Hill Climing Random Restart Algorithm
    
    function HILL-CLIMBING(problem) returns a solution state
     inputs: problem, a problem
     static: current, a node
         next, a node
     current <— MAKE-NODE(INITIAL-STATE[problem])
     loop do
         next— a highest-valued successor of current
         if VALUE[next] < VALUE[current] then return current
         current *—next
     end
    */
    //Method to count the number of queens conflicting
    public int queenHeuristic(int [][] test){
    int count = 0;
    boolean rowEx;
    boolean colEx;
    boolean diaEx;
      
        for(int i = 0; i < 8; i++){
            for(int j= 0; j < 8; j++){
                if(test[i][j] == 1){
                    rowEx = rowCheck(test, j);
                    colEx = colCheck(test, i);
                    diaEx = diagCheck(test, i, j);
                  
                    if(rowEx || colEx || diaEx){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    //Method to move queen and evaluate agents/env/perf measures
    public void begin( )
    { 
        int colCount;
        int minCol;
        int minRow;
        int[][] hArray = new int[8][8];
 
        int prevColQueen = 0;
        newMap = false;
      
        while(true){
            colCount = 0;
      
            for(int i = 0; i < 8; i++){
                System.arraycopy(map[i], 0, testMap[i], 0, 8);
            }
            while(colCount < 8){
                for(int i = 0; i < 8;i++){
                    testMap[i][colCount] = 0;
                }
                for(int i = 0; i < 8; i++){
                    if(map[i][colCount] == 1){
                        prevColQueen = i;
                    }
                    testMap[i][colCount] = 1;
                    hArray[i][colCount] = queenHeuristic(testMap);
                    testMap[i][colCount] = 0;
                }
                testMap[prevColQueen][colCount] = 1;
                colCount++;
            }
          
            if(restart(hArray)){
                queenLocs = 0;
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        map[i][j] = 0;
                    }
                }
                randomizeMap( );
                System.out.println("RESTART");
                restarts++;
            }
      
            minCol = findMinCol(hArray);
            minRow = findMinRow(hArray);
      
            for(int i = 0; i < 8; i++){
                map[i][minCol] = 0;
            }
      
            map[minRow][minCol] = 1;
            moves++;
            queenHeuristic = queenHeuristic(map);
          
            if(queenHeuristic(map) == 0){
                System.out.println("\nCurrent State");
                for(int i = 0; i < 8; i++){
                    for(int j = 0; j < 8; j++){
                        System.out.print(map[i][j] + " ");
                    }
                    System.out.print("\n");
                }
            System.out.println("Complete! Solution Found.");
            System.out.println("Num of state changes: " + moves);
            System.out.println("Num of restarts: " + restarts);
            break;
            }

            System.out.println("\n");
            System.out.println("Current h: " + queenHeuristic);
            System.out.println("Current State");
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    System.out.print(map[i][j] + " ");
                }
                System.out.print("\n");
            }
            System.out.println("Neighbors found with lower h: " + neighbors);
            System.out.println("Resetting State");
        }
    }
    
    //Identifies minimum neighbor state column location
    public int findMinCol(int[][] test)
    
    { 
        int minCol = 8;
        int minVal = 8;
        int count = 0;
      
        for(int i = 0; i < 8; i++){
          for(int j = 0; j < 8; j++){
              if(test[i][j] < minVal){
                  minVal = test[i][j];
                  minCol = j;
              }
              if(test[i][j] < queenHeuristic){
                  count++;
              }
          }
        }
        neighbors = count;
        return minCol;
    }
    
    
    
    //finds row of minimum neighbor state
    public int findMinRow(int[][] test)
    { 
        int minRow = 8;
        int minVal = 8;
      
        for(int i = 0; i < 8; i++){
          for(int j = 0; j < 8; j++){
              if(test[i][j] < minVal){
                  minVal = test[i][j];
                  minRow = i;
              }
          }
        }
        return minRow;
    }
    
    
    
    
    //Method to check if a restart is needed
    public boolean restart(int [][] test)
    {
        int minVal = 8;
        boolean restart = false;
      
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(test[i][j] < minVal){
                    minVal = test[i][j];
                }
            }
        }
        if(neighbors == 0){
            restart = true;
        }//end of if
        return restart;
    }//end of restart method
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     EightQueens one = new EightQueens( );
     //initialize state  and iniate the map
     one.randomizeMap();
     one.begin();
    }

    
}
