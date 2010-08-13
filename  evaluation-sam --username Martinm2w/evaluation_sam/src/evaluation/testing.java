package evaluation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import util.*;

public class testing {
	
	static Parameters pr = new Parameters();
    static ExtractInfo ei = new ExtractInfo();
    static SaveInfo si = new SaveInfo();
    static ArrayList<Parameters> PList = new ArrayList<Parameters>(); 
    static BufferedReader br;
    static ArrayList<ArrayList<String>> afList = new ArrayList<ArrayList<String>>();
    static ArrayList<ArrayList<String>> hfList = new ArrayList<ArrayList<String>>();
	
	public static void main(String[] args){
		
		String human_annotation = "D:/m2w cs/evaluation_sam/input_files/Dec05_Involvement_Annotated" ;
        String auto_annotation = "D:/m2w cs/evaluation_sam/input_files/Dec05_Involvement" ;

        String evaluation_file = "D:/m2w cs/evaluation-m2w/evaluation_sam/output_files/Dec05_Involvement_result_ce";
		
        pr.setSpeakers_map(ei.extractSpeakers(human_annotation));
        pr.setCategories_map(ei.extractCategories(human_annotation));
        
        pr.setCategories(si.saveCategories(pr.getCategories_map()));
        pr.setSpeakers(si.saveSpeakers(pr.getSpeakers_map()));
        
        pr.init();
       
        afList = ei.getAutoFileList(auto_annotation);
        
        hfList = ei.getHumanFileList(human_annotation);
        
        /*ei.evenTwoLists(hfList, afList);*/
        
		        ArrayList<Integer> delNum = new ArrayList<Integer>();
				
				for (int i = 0; i < hfList.size(); i++){
					for(int j = 0; j < hfList.get(i).size(); j++){
						
						if (hfList.get(i).get(j).contains("CANNOT")){
							delNum.add(i);
						}
					}
				}
				if (delNum != null){
						for (int i = 0; i < delNum.size(); i++){
							
							hfList.remove(i);
							afList.remove(i);
							
						}
				}
				delNum.clear();
				
				for (int i = 0; i < afList.size(); i++){
					for(int j = 0; j < afList.get(i).size(); j++){
						
						if (afList.get(i).get(j).contains("CANNOT")){
							delNum.add(i);
						}
					}
				}
				
				if (delNum != null){
					for (int i = 0; i < delNum.size(); i++){
								
								hfList.remove(i);
								afList.remove(i);
					}
				}
				delNum.clear();
			
        
        
        for(int index = 0; index < hfList.size(); index ++){
        	
        	System.out.println(afList.get(index));
        	System.err.println(hfList.get(index));
        	
        	pr.setHuman_qt_thrs(ei.extractHumanQtThrs(pr.getCategories(), hfList, index));
        	pr.setHuman_scores(ei.extractHumanScore(pr.getCategories(), pr.getSpeakers(), hfList, index));
        	
        	System.out.println(pr.getHuman_scores().get("tci"));
        	
        	pr.init();
        	
        }
        
	}
}
