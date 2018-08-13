/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astar;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.PriorityQueue;
import java.util.stream.Stream;

/**
 *
 * @author A214645
 */
public class AStar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String fName = "map.txt";
        
        fileStream(fName);
    }
    
    private static void fileStream(String fileName) {
        try {
            BufferedReader br = Files.newBufferedReader(Paths.get(fileName));
            try (Stream<String> lines = br.lines().map(str -> str.toUpperCase())) {
                System.out.println("<!-----Read all lines by using BufferedReader-----!>");
                lines.forEach(System.out::println);
            }
        } catch (IOException io){
        }
    }
    
    public static final int V_H_COST = 1;
    
    static class Vertex {
        int heuristicCost = 0;
        int finalCost = 0;
        int i, j;
        Vertex parent;
        
        Vertex(int i, int j) {
            this.i = i;
            this.j = j;
        }
        
        @Override
        public String toString() {
          return "["+this.i+", "+this.j+"]";
        }
    }
    static Vertex [][] grid = new Vertex[10][10];
    
    static PriorityQueue<Vertex> open;
     
    static boolean closed[][];
    static int startI, startJ;
    static int endI, endJ;
            
    public static void setBlocked(int i, int j){
        grid[i][j] = null;
    }
    
    public static void setStartVertex(int i, int j){
        startI = i;
        startJ = j;
    }
    
    public static void setEndVertex(int i, int j){
        endI = i;
        endJ = j; 
    }
    
    static void checkAndUpdateCost(Vertex current, Vertex t, int cost){
        if(t == null || closed[t.i][t.j])return;
        int t_final_cost = t.heuristicCost+cost;
        
        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.finalCost){
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }
    
     public static void AStar(){ 
        
        open.add(grid[startI][startJ]);
        
        Vertex current;
        
        while(true){ 
            current = open.poll();
            if(current==null)break;
            closed[current.i][current.j]=true; 

            if(current.equals(grid[endI][endJ])){
                return; 
            } 

            Vertex t;  
            if(current.i-1>=0){
                t = grid[current.i-1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

                
            } 

            if(current.j-1>=0){
                t = grid[current.i][current.j-1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.j+1<grid[0].length){
                t = grid[current.i][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 
            }

            if(current.i+1<grid.length){
                t = grid[current.i+1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST); 

                
            }
        }
    }
}
