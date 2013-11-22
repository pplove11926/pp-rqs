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
    private final TC tc;
    private final DegreeIndicator[] L_DegreeIndicators;
    private final DegreeIndicator[] R_DegreeIndicators;
    private final PriorityQueue<DegreeIndicator> degreeIndicatorsPQ;

    public BipartiteMatrix(TC tc) {
        this.tc = tc;
        this.L_DegreeIndicators = new DegreeIndicator[tc.getV()];
        this.R_DegreeIndicators = new DegreeIndicator[tc.getV()];
        this.degreeIndicatorsPQ = new PriorityQueue<DegreeIndicator>();

        // init the degree indicator array
        for (int i = 0; i < this.tc.getV(); i++) {
            this.L_DegreeIndicators[i] = new DegreeIndicator(Partite.L, i, 0);
            this.R_DegreeIndicators[i] = new DegreeIndicator(Partite.R, i, 0);
        }

        // scan tc, and calculate the right degree
        for (int i = 0; i < this.tc.getV(); i++) {
            for (int j = 0; j < this.tc.getV(); j++) {
                if (true == this.tc.matrix[i][j]) {
                    this.L_DegreeIndicators[i].inc();
                    this.R_DegreeIndicators[j].inc();
                }
            }
        }
    }

    public boolean isEmpty() {
        return 0 == this.tc.getE();
    }

    public Biclique findMaximumBiclique() {
        if (0 == this.tc.getE()) {
            return null;
        }

        Biclique biclique = new Biclique();

        // store next search space
        boolean[] search_L = new boolean[this.tc.getV()];
        boolean[] search_R = new boolean[this.tc.getV()];

        // init search space to true, aka. available
        for (int i = 0; i < this.tc.getV(); i++) {
            search_L[i] = !this.L_DegreeIndicators[i].zero();
            search_R[i] = !this.R_DegreeIndicators[i].zero();
        }
        
        // find the rest
        DegreeIndicator di = findMaxOfBoth(this.L_DegreeIndicators,
                search_L, this.R_DegreeIndicators, search_R, Partite.L);
        while (di != null) {
            if (Partite.L == di.getPartite()) {
                int index = di.getIndex();
                
                biclique.L.add(index);
                search_L[index] = false;
                update_search_R(search_R, index);
            } else if (Partite.R == di.getPartite()) {
                int index = di.getIndex();
                
                biclique.R.add(index);
                search_R[index] = false;
                update_search_L(search_L, index);
            }

            di = findMaxOfBoth(this.L_DegreeIndicators, search_L,
                    this.R_DegreeIndicators, search_R, di.getPartite());
        }

        
        /**
        
        // find first
        DegreeIndicator di_fir = findMaxOfBoth(this.L_DegreeIndicators,
                search_L, this.R_DegreeIndicators, search_R);
        if (null == di_fir) {
            throw new RuntimeException("no di in both partites");
        }

        // cover first and find second
        if (Partite.L == di_fir.getPartite()) {
            int index = di_fir.getIndex();
            
            biclique.L.add(index);
            search_L[index] = false;
            update_search_R(search_R, index);
            
            // find second
            index = findMax(this.R_DegreeIndicators, search_R);
            
            biclique.R.add(index);
            search_R[index] = false;
            update_search_L(search_L, index);
        } else if (Partite.R == di_fir.getPartite()) {
            int index = di_fir.getIndex();
            
            biclique.R.add(index);
            search_R[index] = false;
            update_search_L(search_L, index);

            index = findMax(this.L_DegreeIndicators, search_L);

            biclique.L.add(index);
            search_L[index] = false;
            update_search_R(search_R, index);
        }

        // find the rest
        DegreeIndicator di = findMaxOfBoth(this.L_DegreeIndicators,
                search_L, this.R_DegreeIndicators, search_R);
        while (di != null) {
            if (Partite.L == di.getPartite()) {
                int index = di.getIndex();
                
                biclique.L.add(index);
                search_L[index] = false;
                update_search_R(search_R, index);
            } else if (Partite.R == di.getPartite()) {
                int index = di.getIndex();
                
                biclique.R.add(index);
                search_R[index] = false;
                update_search_L(search_L, index);
            }

            di = findMaxOfBoth(this.L_DegreeIndicators, search_L,
                    this.R_DegreeIndicators, search_R);
        }
        **/

        return biclique;
    }

    public void cover(Biclique biclique) {
        // not form a biclique
        if (biclique.isEmpty()) {
            throw new RuntimeException(
                    "either partite of the biclique is empty");
        }

        // cover tc and update degree indicators
        for (int i : biclique.L) {
            for (int j : biclique.R) {
                // cover a edge, i -> j, update degree and tc
                L_DegreeIndicators[i].dec();
                R_DegreeIndicators[j].dec();
                this.tc.flip(i, j);
            }
        }
    }

    public void greedyBicliqueCover() {
        int pay = bicliqueCover();

        // output the results
        StdOut.println();
        StdOut.println("V: " + this.tc.getV());
        StdOut.println("E: " + this.tc.getE());
        StdOut.println("P: " + pay);
        StdOut.println("P/E: " + (double) pay / this.tc.getE()); // hope P/E can
                                                                 // show us
                                                                 // something
        StdOut.println("P/2E: " + (double) pay / (2 * this.tc.getE())); // P/2E
                                                                        // more
                                                                        // convincing
    }

    private int bicliqueCover() {
        int pay = 0; // we neeed to add #pay dummies to cover the whole
                     // bipartite
        int uncovered = this.tc.getE();

        // init the degree indicator array
        for (int i = 0; i < this.tc.getV(); i++) {
            this.L_DegreeIndicators[i] = new DegreeIndicator(Partite.L, i,0);
            this.R_DegreeIndicators[i] = new DegreeIndicator(Partite.R, i, 0);
        }

        // scan tc_mns, and calculate the right degree
        for (int i = 0; i < this.tc.getV(); i++) {
            for (int j = 0; j < this.tc.getV(); j++) {
                if (true == this.tc.matrix[i][j]) {
                    this.L_DegreeIndicators[i].inc();
                    this.R_DegreeIndicators[j].inc();
                }
            }
        }

        // store a greedy biclique cover
        LinkedList<DegreeIndicator> biclique_lout = new LinkedList<DegreeIndicator>();
        LinkedList<DegreeIndicator> biclique_lin = new LinkedList<DegreeIndicator>();

        // store next search space
        boolean[] search_lout = new boolean[this.tc.getV()];
        boolean[] search_lin = new boolean[this.tc.getV()];

        while (uncovered > 0) {
            // init the biclique cover
            biclique_lout.clear();
            biclique_lin.clear();

            // init search space to true, aka. available
            for (int i = 0; i < this.tc.getV(); i++) {
                search_lout[i] = !this.L_DegreeIndicators[i].zero();
                search_lin[i] = !this.R_DegreeIndicators[i].zero();
            }

            // find first
            DegreeIndicator di_fir = findMaxOfBoth(this.L_DegreeIndicators,
                    search_lout, this.R_DegreeIndicators, search_lin, Partite.L);
            if (null == di_fir) {
                throw new RuntimeException("no node in both partites");
            }

            // cover first and find second
            if (Partite.L == di_fir.getPartite()) {
                biclique_lout.add(di_fir);
                search_lout[di_fir.getIndex()] = false;
                update_search_R(search_lin, di_fir.getIndex());

                int index = findMax(this.R_DegreeIndicators, search_lin);
                DegreeIndicator di_sec = this.R_DegreeIndicators[index];

                biclique_lin.add(di_sec);
                search_lin[di_sec.getIndex()] = false;
                update_search_L(search_lout, di_sec.getIndex());
            } else if (Partite.R == di_fir.getPartite()) {
                biclique_lin.add(di_fir);
                search_lin[di_fir.getIndex()] = false;
                update_search_L(search_lout, di_fir.getIndex());

                int index = findMax(this.L_DegreeIndicators, search_lout);
                DegreeIndicator di_sec = this.L_DegreeIndicators[index];

                biclique_lout.add(di_sec);
                search_lout[di_sec.getIndex()] = false;
                update_search_R(search_lin, di_sec.getIndex());
            }

            // find the rest
            DegreeIndicator di = findMaxOfBoth(this.L_DegreeIndicators,
                    search_lout, this.R_DegreeIndicators, search_lin, Partite.L);
            while (di != null) {
                if (Partite.L == di.getPartite()) {
                    biclique_lout.add(di);
                    search_lout[di.getIndex()] = false;
                    update_search_R(search_lin, di.getIndex());
                } else if (Partite.R == di.getPartite()) {
                    biclique_lin.add(di);
                    search_lin[di.getIndex()] = false;
                    update_search_L(search_lout, di.getIndex());
                }

                di = findMaxOfBoth(this.L_DegreeIndicators, search_lout,
                        this.R_DegreeIndicators, search_lin, di.getPartite());
            }

            // not form a biclique
            if (biclique_lout.isEmpty() || biclique_lin.isEmpty()) {
                throw new RuntimeException(
                        "either partite of the biclique is empty");
            }

            // cover tc_mns and update degree indicators
            for (DegreeIndicator di_lout : biclique_lout) {
                for (DegreeIndicator di_lin : biclique_lin) {
                    // cover a edge, di_lout -> di_lin
                    di_lout.dec();
                    di_lin.dec();
                    this.tc.matrix[di_lout.getIndex()][di_lin.getIndex()] = false;
                }
            }

            // update uncovered and pay
            uncovered -= biclique_lout.size() * biclique_lin.size();
            pay += biclique_lout.size() + biclique_lin.size();
        }

        return pay;
    }

    private void update_search_R(boolean[] search_R, int index) {
        for (int i = 0; i < this.tc.getV(); i++) {
            search_R[i] = search_R[i] && this.tc.matrix[index][i];
        }
    }

    private void update_search_L(boolean[] search_L, int index) {
        for (int i = 0; i < this.tc.getV(); i++) {
            search_L[i] = search_L[i] && this.tc.matrix[i][index];
        }
    }

    private DegreeIndicator findMaxOfBoth(DegreeIndicator[] lout_dis,
            boolean[] lout_ss, DegreeIndicator[] lin_dis, boolean[] lin_ss, Partite P) {
        int lout_max = findMax(lout_dis, lout_ss);
        int lin_max = findMax(lin_dis, lin_ss);

        if (-1 == lout_max && -1 == lin_max) {
            return null;
        } else if (-1 == lout_max) {
            return lin_dis[lin_max];
        } else if (-1 == lin_max) {
            return lout_dis[lout_max];
        }
        
        // to jump between two partites
        if(lout_dis[lout_max].compareTo(lin_dis[lin_max]) == 0){
            if(Partite.L == P){
                return lin_dis[lin_max];
            }else{
                return lout_dis[lout_max];
            }
        }

        if (lout_dis[lout_max].compareTo(lin_dis[lin_max]) > 0) {
            return lin_dis[lin_max];
        } else {
            return lout_dis[lout_max];
        }
    }

    /**
     * find row or column with the max degree in the specific search space.
     * 
     * @param dis
     * @param search_space
     * @return
     */
    private int findMax(DegreeIndicator[] dis, boolean[] search_space) {
        if (dis.length != search_space.length) {
            throw new IllegalArgumentException("Degree indicators length does not match the search space");
        }

        int max = 0;

        // find the first eligible one
        while (max < dis.length) {
            if (search_space[max] == true) break;
            max++;
        }

        if (max == dis.length) {
            return -1; // not found first one
        }

        // continue to find the max one
        for (int i = max; i < dis.length; i++) {
            if (search_space[i] == true && dis[max].compareTo(dis[i]) > 0) {
                max = i;
            }
        }

        return max;
    }
}
