package com.yipeipei.algs;

import java.util.LinkedList;
import java.util.PriorityQueue;

import edu.princeton.cs.introcs.StdOut;

/**
 * The <tt>Bipartitie<tt> class represents an undirected bipartite graph.
 * 
 * @author peipei
 *
 */
public class BipartiteMatrix {
    private final TC tc_mns;
    private final DegreeIndicator[] loutDegreeIndicators;
    private final DegreeIndicator[] linDegreeIndicators;
    private final PriorityQueue<DegreeIndicator> degreeIndicatorsPQ;
    
    public BipartiteMatrix(TC tc){
        this.tc_mns = tc;
        this.loutDegreeIndicators = new DegreeIndicator[tc.getV()];
        this.linDegreeIndicators = new DegreeIndicator[tc.getV()];
        this.degreeIndicatorsPQ = new PriorityQueue<DegreeIndicator>();
    }
    
    public void greedyBicliqueCover(){
        int pay = bicliqueCover();
        
        // output the results
        StdOut.println();
        StdOut.println("V: " + this.tc_mns.getV());
        StdOut.println("E: " + this.tc_mns.getE());
        StdOut.println("P: " + pay);
        StdOut.println("P/E: " + (double)pay/this.tc_mns.getE());   // hope P/E can show us something
        StdOut.println("P/2E: " + (double)pay/(2 * this.tc_mns.getE()));  // P/2E more convincing
    }
    
    private int bicliqueCover(){
        int pay = 0;    // we neeed to add #pay dummies to cover the whole bipartite
        int uncovered = this.tc_mns.getE();
        
        // init the degree indicator array
        for(int i = 0; i < this.tc_mns.getV(); i++){
            this.loutDegreeIndicators[i] = new DegreeIndicator(Partite.Lout, i, 0);
            this.linDegreeIndicators[i] = new DegreeIndicator(Partite.Lin, i, 0);
        }
        
        // scan tc_mns, and calculate the right degree
        for(int i = 0; i < this.tc_mns.getV(); i++){
            for(int j = 0; j < this.tc_mns.getV(); j++){
                if(true == this.tc_mns.matrix[i][j]){
                    this.loutDegreeIndicators[i].inc();
                    this.linDegreeIndicators[j].inc();
                }
            }
        }
        
        // store a greedy biclique cover
        LinkedList<DegreeIndicator> biclique_lout = new LinkedList<DegreeIndicator>();
        LinkedList<DegreeIndicator> biclique_lin = new LinkedList<DegreeIndicator>();
        
        // store next search space
        boolean[] search_lout = new boolean[this.tc_mns.getV()];
        boolean[] search_lin = new boolean[this.tc_mns.getV()];
        
        while(uncovered > 0){
            // init the biclique cover
            biclique_lout.clear();
            biclique_lin.clear();
            
            // init search space to true, aka. available
            for(int i = 0; i < this.tc_mns.getV(); i++){
                search_lout[i] = !this.loutDegreeIndicators[i].zero();
                search_lin[i] = !this.linDegreeIndicators[i].zero();
            }
            
            // find first
            DegreeIndicator di_fir = findMaxOfBoth(this.loutDegreeIndicators, search_lout, this.linDegreeIndicators, search_lin);
            if(null == di_fir){
                throw new RuntimeException("no di in both partites");
            }
            
            // cover first and find second
            if(Partite.Lout == di_fir.getPartite()){
                biclique_lout.add(di_fir);
                search_lout[di_fir.getIndex()] = false;
                update_search_lin(search_lin, di_fir);
                
                int index = findMax(this.linDegreeIndicators, search_lin);
                DegreeIndicator di_sec = this.linDegreeIndicators[index];
                
                biclique_lin.add(di_sec);
                search_lin[di_sec.getIndex()] = false;
                update_search_lout(search_lout, di_sec);
            }else if(Partite.Lin == di_fir.getPartite()){
                biclique_lin.add(di_fir);
                search_lin[di_fir.getIndex()] = false;
                update_search_lout(search_lout, di_fir);
                
                int index = findMax(this.loutDegreeIndicators, search_lout);
                DegreeIndicator di_sec = this.loutDegreeIndicators[index];
                
                biclique_lout.add(di_sec);
                search_lout[di_sec.getIndex()] = false;
                update_search_lin(search_lin, di_sec);
            }
            
            // find the rest
            DegreeIndicator di = findMaxOfBoth(this.loutDegreeIndicators, search_lout, this.linDegreeIndicators, search_lin);
            while(di != null){
                if(Partite.Lout == di.getPartite()){
                    biclique_lout.add(di);
                    search_lout[di.getIndex()] = false;
                    update_search_lin(search_lin, di);
                }else if(Partite.Lin == di.getPartite()){
                    biclique_lin.add(di);
                    search_lin[di.getIndex()] = false;
                    update_search_lout(search_lout, di);
                }
                
                di = findMaxOfBoth(this.loutDegreeIndicators, search_lout, this.linDegreeIndicators, search_lin);
            }
            
            // cover find rest
            
            /*
            // add all degree indicators to priority queue
            for(int i = 0; i < this.tc_mns.getV(); i++){
                if(!this.loutDegreeIndicators[i].zero()){
                    this.degreeIndicatorsPQ.add(this.loutDegreeIndicators[i]);
                }
                
                if(!this.linDegreeIndicators[i].zero()){
                    this.degreeIndicatorsPQ.add(this.linDegreeIndicators[i]);
                }
            }
            
            // find a biclique
            while(!this.degreeIndicatorsPQ.isEmpty()){
                DegreeIndicator di = this.degreeIndicatorsPQ.poll();
                
                if(di.zero()){
                    continue;
                }
                
                // eligible
                if(Partite.Lout == di.getPartite() && true == search_lout[di.getIndex()]){
                    biclique_lout.add(di);  // picked
                    // update the search space for next time
                    for(int i = 0; i < this.tc_mns.getV(); i++){
                        search_lin[i] = search_lin[i] && this.tc_mns.matrix[di.getIndex()][i];
                    }
                }else if(Partite.Lin == di.getPartite() && true == search_lin[di.getIndex()]){
                    biclique_lin.add(di);   // picked
                    for(int i = 0; i < this.tc_mns.getV(); i++){
                        search_lout[i] = search_lout[i] && this.tc_mns.matrix[i][di.getIndex()];
                    }
                }else{
                    // can not add to current bi partite, throw and mark
                    if(Partite.Lout == di.getPartite()){
                        search_lout[di.getIndex()] = false;
                    }else if(Partite.Lin == di.getPartite()){
                        search_lin[di.getIndex()] = false;
                    }
                }
            }
            // when PQ is empty, we have found a maximal biclique
            */
            
            // not form a biclique
            if(biclique_lout.isEmpty() || biclique_lin.isEmpty()){
                throw new RuntimeException("either partite of the biclique is empty");
            }
            
            // cover tc_mns and update degree indicators
            for(DegreeIndicator di_lout : biclique_lout){
                for(DegreeIndicator di_lin : biclique_lin){
                    // cover a edge, di_lout -> di_lin
                    di_lout.dec();
                    di_lin.dec();
                    this.tc_mns.matrix[di_lout.getIndex()][di_lin.getIndex()] = false;
                }
            }
            
            // update uncovered and pay
            uncovered -= biclique_lout.size() * biclique_lin.size();
            pay += biclique_lout.size() + biclique_lin.size();
        }
        
        
        return pay;
    }
    
    private void update_search_lin(boolean[] search_lin, DegreeIndicator di){
        for(int i = 0; i < this.tc_mns.getV(); i++){
            search_lin[i] = search_lin[i] && this.tc_mns.matrix[di.getIndex()][i];
        }
    }
    
    private void update_search_lout(boolean[] search_lout, DegreeIndicator di){
        for(int i = 0; i < this.tc_mns.getV(); i++){
            search_lout[i] = search_lout[i] && this.tc_mns.matrix[i][di.getIndex()];
        }
    }
    
    private DegreeIndicator findMaxOfBoth(DegreeIndicator[] lout_dis, boolean[] lout_ss, DegreeIndicator[] lin_dis, boolean[] lin_ss){
        int lout_max = findMax(lout_dis, lout_ss);
        int lin_max = findMax(lin_dis, lin_ss);
        
        if(lout_max == -1 && lin_max == -1){
           return null;
        }else if(lout_max == -1){
            return lin_dis[lin_max];
        }else if(lin_max == -1){
            return lout_dis[lout_max];
        }
        
        if(lout_dis[lout_max].compareTo(lin_dis[lin_max]) > 0){
            return lin_dis[lin_max];
        }else{
            return lout_dis[lout_max];
        }
    }
    
    /**
     * find row or column with the max degree in the specific search space.
     * @param dis
     * @param search_space
     * @return
     */
    private int findMax(DegreeIndicator[] dis, boolean[] search_space){
        if(dis.length != search_space.length){
            throw new IllegalArgumentException("Degree indicators length does not match the search space");
        }
        
        int max = 0;
        
        // find the first eligible one
        while(max < dis.length){
            if(search_space[max] == true) break;
            max++;
        }
        
        if(max == dis.length){
            return -1; // not found first one
        }
        
        // continue to find the max one
        for(int i = max; i < dis.length; i++){
            if(search_space[i] == true && dis[max].compareTo(dis[i]) > 0){
                max = i;
            }
        }
        
        return max;
    }
}
