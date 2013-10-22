package com.yipeipei.pprqs;

import java.io.File;
import java.util.ArrayList;

import edu.princeton.cs.introcs.StdOut;

public class Data {
    public static final String DATA_ROOT = "data/";
    public static final String DATA_ORIGIN = DATA_ROOT + "origin/";
    public static final String DATA_UNIFIED = DATA_ROOT + "unified/";
    
    /**
     * return all the original data set, some maybe in an adjacency-lists representation.
     * @return
     */
    public static File[] getOrigin(){
        return getFiles(DATA_ORIGIN, null);
    }
    
    /**
     * return all the unified data set, using names o through V-1 for the vertices in a V-vertex graph.
     * @return
     */
    public static File[] getUnified(){
        return getFiles(DATA_UNIFIED, null);
    }
    
    /**
     * List all files and sub directories under the given directory while match the filter.
     * Files and directories start with . will be ignored. e.g. .DS_Store
     * @param directory
     * @param filter
     * @return
     */
    public static File[] getFiles(String directory, String filter){
        String fileNames[] = new File(directory).list();
        if(fileNames == null) return null;
        ArrayList<File> matchList = new ArrayList<File>();
        for(String f : fileNames){
            if(!f.startsWith(".") && ((filter == null) || f.endsWith(filter))){
                matchList.add(new File(directory, f)); 
            }
        }
        return (File[])(matchList.toArray(new File[matchList.size()]));
    }
    
    public static void main(String[] argv) {
        for(File f : getOrigin()){
            StdOut.println(f.getAbsolutePath());
        }
        for(File f : getUnified()){
            StdOut.println(f.getAbsolutePath());
        }
        for(File f : getFiles(DATA_ORIGIN, ".net")){
            StdOut.println(f.getAbsolutePath());
        }
    }
}
