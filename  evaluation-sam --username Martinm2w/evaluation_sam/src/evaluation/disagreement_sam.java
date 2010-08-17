//package evaluation;
//
//
//import java.io.*;
//import util.*;
package evaluation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import util.*;

public class disagreement_sam {
	
	static Parameters pr = new Parameters();
    static ExtractInfo ei = new ExtractInfo();
    static SaveInfo si = new SaveInfo();
    static CompareEval CpEval = new CompareEval();
    
    
    static ArrayList<Parameters> PList = new ArrayList<Parameters>(); 
    static BufferedReader br;
    static ArrayList<ArrayList<String>> afList = new ArrayList<ArrayList<String>>();
    static ArrayList<ArrayList<String>> hfList = new ArrayList<ArrayList<String>>();
	
	public static void main(String[] args){
		
		String human_annotation = "D:/m2w cs/evaluation_sam/input_files/Oct11_Disagreement_Annotated" ;
        String auto_annotation = "D:/m2w cs/evaluation_sam/input_files/Oct11_Disagreement" ;

        String evaluation_file = "D:/m2w cs/evaluation_sam/output_files/Oct11_Disagreement_result_ce";
		
        pr.setSpeakers_map(ei.extractSpeakers(human_annotation));
        pr.setCategories_map(ei.extractCategories(human_annotation));
        
        pr.setCategories(si.saveCategories(pr.getCategories_map()));
        pr.setSpeakers(si.saveSpeakers(pr.getSpeakers_map()));
        
        pr.init();
       
        /*get two lists of paragraph*/
        afList = ei.getAutoFileList(auto_annotation);
        hfList = ei.getHumanFileList(human_annotation);
        

        /*set human qt thrs and scores */
        for(int index = 0; index < hfList.size(); index ++){

        	String curtopic = ei.extractCurTopic(pr.getCategories(), hfList, index);
        	
        	System.out.println("curtopic : " + curtopic);
        	
        	pr.setHuman_scores(ei.extractHumanScoreStrings(pr.getCategories(), pr.getSpeakers(), hfList, index));
        	pr.setAuto_scores(ei.extractAutoScoreStrings(pr.getCategories(), pr.getSpeakers(), afList, index));

        	
        	pr.setHuman_quintile_scores(si.saveHumanQtScores(pr.getHuman_scores(), pr.getSpeakers()));
        	pr.setAuto_quintile_scores(si.saveAutoQtScores(pr.getAuto_scores(), pr.getSpeakers()));
        	
        	        	
        	try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(evaluation_file, true));
                	
                	
                    bw.write("---------------- Disagreement Evaluation --------------------- \n");
                    bw.write("Human annotated file: " + human_annotation + "\n");
                    bw.write("Auto annotated file: " + auto_annotation + "\n");
                    bw.write("--------------------------------------------------------------- \n");
                    
                    for(int i = 0; i < pr.getCategories().length; i++){

                        String category = pr.getCategories()[i];
                        
                        if(!pr.getCategories()[i].equals(curtopic)){

                            continue;

                        }
                        
                        bw.write("+++++++++++++++++++++++++ " + category + " ++++++++++++++++++++++++++++++ \n");
                        
                        int[] auto_qscore = pr.getAuto_quintile_scores().get(category);
    		   			int[] human_qscore = pr.getHuman_quintile_scores().get(category);

    		   			
    		   			CpEval.compareEval(bw, pr.getSpeakers(), auto_qscore, human_qscore);
                    }
                                        
                    bw.close();
                                        
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        	pr.init();
        	
        }
                
    }
        	
}
//
////
///**
// *
// * @author m2w
// * 
// */
//public class disagreement_sam {
//
//	static ExtractAndBuild enb = new ExtractAndBuild();
//	
//    public static void main(String[] args){
//
//        String human_annotation = "D:/m2w cs/evaluation_sam/input_files/Oct11_Disagreement_Annotated" ;
//        String auto_annotation = "D:/m2w cs/evaluation_sam/input_files/Oct11_Disagreement" ;
//
//        String evaluation_file = "D:/m2w cs/evaluation_sam/output_files/Oct11_Disagreement_Annotated_result_ce";
//
//        /*extract info*/
//        enb.extractSpeakersAndCategoriesToMap(human_annotation);
//       
//        System.err.println("Speakers : " + enb.getSpeakers());
//        System.err.println("categories : " + enb.getCategories());
//        
//        
//        /*initializing*/
//        enb.init();
//
//        enb.extract_info_human(evaluation_file, human_annotation, auto_annotation);
//        
//		try {
//			enb.getEia_br().close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//    }
//
//}
//
//
//
