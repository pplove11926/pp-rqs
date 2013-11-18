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
    }
    
    private int bicliqueCover(){
        int pay = 0;    // we need to add #pay dummies to cover the whole bipartite
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
        
        // if not a biclique, PQ is not random when two item has same degree
        int skip = 0;
        
        while(uncovered > 0){
            // init the biclique cover
            biclique_lout.clear();
            biclique_lin.clear();
            
            // init search space to true, aka. available
            for(int i = 0; i < this.tc_mns.getV(); i++){
                search_lout[i] = true;
                search_lin[i] = true;
            }
            
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
                int s = skip;
                while(s > 0){
                    this.degreeIndicatorsPQ.poll();
                    s--;
                }
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
                }
                
                if(Partite.Lin == di.getPartite() && true == search_lin[di.getIndex()]){
                    biclique_lin.add(di);   // picked
                    for(int i = 0; i < this.tc_mns.getV(); i++){
                        search_lout[i] = search_lout[i] && this.tc_mns.matrix[i][di.getIndex()];
                    }
                }
            }
            // when PQ is empty, we have found a maximal biclique
            
            // not form a biclique
            if(biclique_lout.isEmpty() || biclique_lin.isEmpty()){
                skip++;
                continue;
            }else{
                skip = 0;   // reset skip
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
    
    
}
