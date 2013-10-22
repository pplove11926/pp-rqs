package com.yipeipei.pprqs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.yipeipei.algs.TarjanSCC;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.StdOut;

public class DataVerify {
    private final Digraph G;
    private static final String CHECK_PREFIX = "check";
    
    public DataVerify(Digraph G){
        this.G = G;
    }
    
    /**
     * Invoke all check methods (method name start with "check") in this class.
     * For each method, print its class and method name, then invoke it. 
     * @return
     */
    public boolean verifyALL(){
        Method methods[] = this.getClass().getDeclaredMethods();
        for(Method m : methods){
            if (isCheckMethod(m.getName())) {
                StdOut.println(this.getClass() + "\n\tmethod " + m.getName() + "()");
                try {
                    m.invoke(this, null);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
    
    private static final boolean isCheckMethod(String methodName) {
        return methodName.startsWith(CHECK_PREFIX);
    }
    
    /**
     * Check if every edge <v, w> of given digraph <tt>G</tt> satisfy that v < w.
     * If not, print such edges and return false.
     * @param G
     * @return if v < w for all edge <v, w>, return true; else return false. 
     */
    private boolean checkSmall2Big(){
        for(int v = 0; v < G.V(); v++){
            for(int w : G.adj(v)){
                if(v > w){
                    StdOut.println(v + " " + w);
                }
            }
        }
        return true;
    }
    
    private boolean checkSCC(){
        TarjanSCC scc = new TarjanSCC(G);
        
        return false;
    }
    
    public static void main(String[] argv) {
        DataVerify dv = new DataVerify(new Digraph(100));
        dv.verifyALL();
    }
}
