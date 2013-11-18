package com.yipeipei.algs;

/**
 * 
 * @author peipei
 *
 */
public class DegreeIndicator implements Comparable<DegreeIndicator>{
    private final Partite partite;
    private final int index;
    private int degree; // negative when picked, zero when all covered
    
    public Partite getPartite() {
        return partite;
    }
    
    public int getIndex() {
        return index;
    }
    
    public DegreeIndicator(Partite partite, int index, int degree){
        this.partite = partite;
        this.index = index;
        this.degree = degree;
    }

    private void pick(){
        this.degree = -this.degree; // set negative when picked
    }
    
    private void cover(){
        this.degree = 0;    // set zero when covered
    }
    
    public void inc(){
        this.degree++;
    }
    
    public void dec(){
        if(this.zero()){
            throw new IllegalArgumentException("degree can not decreased to negative");
        }else{
            this.degree--;
        }
    }
    
    public boolean zero(){
        return 0 == this.degree;
    }
    
    @Override
    public int compareTo(DegreeIndicator o) {
        return -(this.degree - o.degree);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEWLINE = System.getProperty("line.separator");
        
        sb.append(NEWLINE);
        sb.append("Partite: ");
        sb.append(this.partite.name());
        sb.append("\t");
        sb.append("Index: ");
        sb.append(this.index);
        sb.append("\t");
        sb.append("Degree: ");
        sb.append(this.degree);
        
        return sb.toString();
    }
    
}

/**
 * 
 * @author peipei
 *
 */
enum Partite{
    Lout,
    Lin,
}