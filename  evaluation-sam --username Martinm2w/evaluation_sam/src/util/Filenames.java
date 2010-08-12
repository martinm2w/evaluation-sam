package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author m2w
 *
 */

public class Filenames {
    /*new_file_names*/
    static HashMap<String, String> human_files = new HashMap<String, String>();
    static HashMap<String, String> auto_files = new HashMap<String, String>();
    static ArrayList<String> human_file_list = new ArrayList<String>();
    static ArrayList<String> auto_file_list = new ArrayList<String>();
    static int fileIndex = 0;
    
    
    public void extractFileNames(String human_annotation, String auto_annotation){
    /*start read in file names from 2 files */
    
	try {
	BufferedReader hfbr = new BufferedReader(new FileReader(human_annotation));// human file name buffered reader
    BufferedReader afbr = new BufferedReader(new FileReader(auto_annotation)); // auto file name buffered reader
    
    String hfileStr = "";
    String afileStr = "";
    
    while((hfileStr = hfbr.readLine()) != null){
    	
    	if(hfileStr != null && hfileStr.contains("processing") ){
    		
    		if(human_files.get(hfileStr.split(" ")[1]) == null){
    			
    			human_files.put(hfileStr.split(" ")[1], hfileStr.split(" ")[1]); //tester
    			human_file_list.add(hfileStr.split(" ")[1]);// ordered file names
    			
    		}
    		
    	}
    	
    }
    
    while((afileStr = afbr.readLine()) != null){
    	
    	if(afileStr != null && afileStr.contains("processing") ){
    		
    		if(auto_files.get(afileStr.split(" ")[1]) == null){
    			
    			auto_files.put(afileStr.split(" ")[1], afileStr.split(" ")[1]);
    			auto_file_list.add(afileStr.split(" ")[1]);
    			
    		}
    		
    	}
    	
    	
    }
    
    //System.out.println(Arrays.toString(auto_files.keySet().toArray()));
    //System.out.println(Arrays.toString(human_files.keySet().toArray()));
    
    System.out.println(human_file_list);
    System.out.println(auto_file_list);
    
    
    afbr.close();
    hfbr.close();
    
    
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
    
    
    /*end read in */

	
    }
  
    public void printFileNames(BufferedWriter bw){
    	
    try {
		//bw.write("---------------- Topic Control Evaluation --------------------- \n");
		bw.write("Human annotated file: " + human_file_list.get(fileIndex) + "\n");
		bw.write("Auto annotated file: " + auto_file_list.get(fileIndex) + "\n");
		//bw.write("--------------------------------------------------------------- \n");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    fileIndex ++;
    }
}
