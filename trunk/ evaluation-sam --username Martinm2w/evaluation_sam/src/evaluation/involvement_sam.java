package evaluation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import util.*;

public class involvement_sam {
	
	static Parameters pr = new Parameters();
    static ExtractInfo ei = new ExtractInfo();
    static SaveInfo si = new SaveInfo();
    static CompareEval CpEval = new CompareEval();
    
    
    static ArrayList<Parameters> PList = new ArrayList<Parameters>(); 
    static BufferedReader br;
    static ArrayList<ArrayList<String>> afList = new ArrayList<ArrayList<String>>();
    static ArrayList<ArrayList<String>> hfList = new ArrayList<ArrayList<String>>();
	
	public static void main(String[] args){
		
		String human_annotation = "D:/m2w cs/evaluation_sam/input_files/Oct11_Involvement_Annotated" ;
        String auto_annotation = "D:/m2w cs/evaluation_sam/input_files/Oct11_Involvement" ;

        String evaluation_file = "D:/m2w cs/evaluation_sam/output_files/Oct11_Involvement_result_ce";
		
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
                	
                	
                    bw.write("---------------- Involvement Evaluation --------------------- \n");
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
        	
        	
//        	for (int i = 0; i < PList.get(z).getSpeakers().length; i++){
//            	System.out.println("speakers : " + PList.get(z).getSpeakers()[i]);
//            }
//        	System.out.println("plist " + PList.get(i).getAuto_quintile_scores().entrySet());
        	
        
        
	}



//package evaluation;
//
//
//import java.io.*;
//import util.*;
//
////
///**
// *
// * @author m2w
// * 
// */
//public class involvement_sam {
//
//	static ExtractAndBuild enb = new ExtractAndBuild();
//	
//    public static void main(String[] args){
//
//        String human_annotation = "D:/m2w cs/evaluation_sam/input_files/Dec05_Involvement_Annotated" ;
//        String auto_annotation = "D:/m2w cs/evaluation_sam/input_files/Dec05_Involvement" ;
//
//        String evaluation_file = "D:/m2w cs/evaluation_sam/output_files/Dec05_Involvement_result_ce";
//
//        /*extract info*/
//        enb.extractSpknCatToMap_involvement(human_annotation);
//       
//        System.err.println("Speakers : " + enb.getSpeakers());
//        System.err.println("categories : " + enb.getCategories());
//        
//        
//        /*initializing*/
//        enb.init();
//
//        enb.extract_info_human_involment(evaluation_file, human_annotation, auto_annotation);
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
//
////package evaluation;
////
////import java.io.*;
////import java.util.Arrays;
////import java.util.HashMap;
////import java.util.logging.Level;
////import java.util.logging.Logger;
////
////import util.CompareEval;
////import util.Filenames;
////import util.MatchEval;
////
////
/////**
//// *
//// * @author jgh
//// */
////public class involvement_sam {
////
////    static String[] categories;
////    static String[] speakers;
////
////    static HashMap<String, String> categories_map = new HashMap<String, String>();
////    static HashMap<String, String> speakers_map = new HashMap<String, String>();
////
////    static HashMap<String, String[]> human_qt_thrs = new HashMap<String, String[]>(); // key: category; value: qt_thrs array
////    static HashMap<String, String[]> auto_qt_thrs = new HashMap<String, String[]>(); // key: category; value: qt_thrs array
////    static HashMap<String, String[]> human_scores = new HashMap<String, String[]>(); // key: category; value: score, order by speakers
////    static HashMap<String, String[]> auto_scores = new HashMap<String, String[]>(); // key: category; value: score, order by speakers
////    static HashMap<String, int[]> human_quintile_scores = new HashMap<String, int[]>(); // key: category; value: quintile scores, order by speakers
////    static HashMap<String, int[]> auto_quintile_scores = new HashMap<String, int[]>(); // key: category; value: quintile scores, order by speakers
////    static HashMap<String, String[]> human_actual_scores = new HashMap<String, String[]>(); // key: category; value: actual scores, order by speakers
////    static HashMap<String, String[]> auto_actual_scores = new HashMap<String, String[]>(); // key: category; value: actual scores, order by speakers
////
////    private static boolean readerOpened=false;
////    private static BufferedReader eia_br;
////
////
////    public static void main(String[] args){
////
////            String human_annotation = "D:/m2w cs/evaluation-m2w/input_log/2010.08/involvement_6_Lauren_annotated_with_merge_2";
////            String auto_annotation = "D:/m2w cs/evaluation-m2w/input_log/2010.08/involvement_6_automated_with_merge_2";
////
////            String evaluation_file = "D:/m2w cs/evaluation-m2w/src/output_files/involvement_6_Lauren_annotated_with_merge_2_result_me";
////
////
////            try { //extract names/topics
////                BufferedReader br = new BufferedReader(new FileReader(human_annotation));
////
////                String tempstr;
////
////                while((tempstr=br.readLine())!=null){
////                    if(tempstr.startsWith("The quintile score of ")|| tempstr.startsWith("The quintile score ")){//get speaker names
////
////                        String name;
////
////                        if(tempstr.startsWith("The quintile score of ")){
////
////                            name=tempstr.split(" ")[4];
////
////                        }
////                        else{
////                            
////                           name=tempstr.split(" ")[3];
////                            
////                        }
////
////
////                        name=name.toLowerCase();
////
////                        if( (speakers_map.get(name)==null)){ //if speaker not yet in list
////
////                            speakers_map.put(name, name);
////
////                        }
////
////
////      //                  System.err.println(tempstr);
////    //                    System.err.println(firsthalf);
////  //                      System.err.println(secondhalf);
//////                        System.err.println(tempstr.substring("The quintile score of ".length(), firstsemicolon));
////
////
////                    }
////                    /*merged files*/
////                    else if(tempstr.contains("calculate Merged quintile")){
////                    	
////                    	if(categories_map.get("merged quintile") == null){
////                    		
////                    		categories_map.put("merged quintile", "merged quintile");
////                    		
////                    	}
////                    }
////                    /*normal files*/
////                    else if(tempstr.contains("calculate Involvement - ")){//get topics
////
////                       // System.err.println(tempstr.split("\"")[1]);
////
////
////                        if( categories_map.get(tempstr.split(" ")[3].toLowerCase())==null){ //if category not yet in list
////
////                            categories_map.put(tempstr.split(" ")[3].toLowerCase(), tempstr.split(" ")[3].toLowerCase());
////
////                        }
////
////                    }
////
////                }
////
////                System.err.println(Arrays.toString(speakers_map.keySet().toArray()));
////
////                                System.err.println(Arrays.toString(categories_map.keySet().toArray()));
////
////
////                Object[] speakerarray=speakers_map.keySet().toArray();
////
////
////                speakers=new String[speakerarray.length];
////
////                for(int ei=0;ei<speakerarray.length;ei++){
////
////                    speakers[ei]=(String)speakerarray[ei];
////
////                    System.err.println(speakers[ei]);
////
////                }
////
////
////                Object[] categoriesarray=categories_map.keySet().toArray();
////
////                categories=new String[categoriesarray.length];
////
////                for(int ei=0;ei<categoriesarray.length;ei++){
////
////                    categories[ei]=(String)categoriesarray[ei];
////
////                    System.err.println(categories[ei]);
////
////                }
////
////
////                
////                br.close();
////
////            } catch (FileNotFoundException ex) {
////                Logger.getLogger(topic_disagreement_sam.class.getName()).log(Level.SEVERE, null, ex);
////            }
////              catch(IOException e){
////
////                  e.printStackTrace();
////
////            }
////
////
////
////
////
////
////
////            init();
////
////            extract_info_human(evaluation_file, human_annotation,auto_annotation);
////
////           // extract_info_human(human_annotation);
////            //extract_info_auto(auto_annotation);
////            //save_human_quintile_scores();
////            //save_auto_quintile_scores();
////            //save_human_actual_scores();
////            //save_auto_actual_scores();
////
////            //writeToEvaluation(evaluation_file, human_annotation, auto_annotation);
////            //System.out.println(human_actual_scores.size() + " " + auto_actual_scores.size());
////       // }
////
////        try {
////		eia_br.close();
////	} catch (IOException e) {
////		e.printStackTrace();
////	}
////
////    }
////
////    public static void init(){
////
////
////        speakers_map.clear();
////        categories_map.clear();
////        human_scores.clear();
////        auto_scores.clear();
////        human_qt_thrs.clear();
////        auto_qt_thrs.clear();
////        human_quintile_scores.clear();
////        auto_quintile_scores.clear();
////        human_actual_scores.clear();
////        auto_actual_scores.clear();
////    }
////
////    public static void extract_info_human(String evaluation_file, String file,String auto_annotation){
////        BufferedReader br;
////        String tempStr = "";
////
////        String curtopic="";
////
////        boolean isnew=false;
////
////        try {
////           br = new BufferedReader(new FileReader(file));
////           while ((tempStr = br.readLine()) != null){
////
////               if(tempStr.contains("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")){
////
////                   isnew=true;
////
////               }
////
////               if(tempStr.contains("calculate Involvement - ") || tempStr.contains("calculate Merged quintile")){
////                   for(int i = 0; i < categories.length; i++){
////                       if(tempStr.toLowerCase().contains("calculate involvement - "+ categories[i]) || tempStr.toLowerCase().contains("calculate " +categories[i])){
////
////                           curtopic=categories[i];
////
////                           String qt_thrs = br.readLine();
////
////                           //if(qt_thrs==null){
////
////                             //  System.err.println("is null");
////
////                           //}
////
////                           String[] qt_thrs1 = qt_thrs.split("\\s+");
////                           String[] qt_thrs_array = new String[qt_thrs1.length-1];
////                           for(int j = 0; j < qt_thrs_array.length; j++){
////                               qt_thrs_array[j] = qt_thrs1[j+1];
////                               //System.out.println(qt_thrs_array[j] + " " + categories[i]);
////                           }
////                           /* save qt_thrs */
////                           human_qt_thrs.put(categories[i], qt_thrs_array);
////
////                           /* save scores */
////                           String speaker = "";
////                           String[] score_array = new String[speakers.length];
////                           while((speaker = br.readLine()) != null &&
////                               speaker.contains("The quintile score")) {
////
////                               for(int k = 0; k < speakers.length; k++){
////                                   if(speaker.toLowerCase().contains(speakers[k] + " ")){
////                                        score_array[k] = speaker.toLowerCase();
////                                        //System.out.println("score_array[" + k + "]: " + score_array[k]);
////                                   }
////                               }
////                           }
////                       human_scores.put(categories[i], score_array);
////                       }
////                   }
////
////                   extract_info_auto(evaluation_file, file, auto_annotation, curtopic, isnew);
////
////                   isnew=false;
////
////               }
////           }
////
////           readerOpened=false; //next human annotator
////           eia_br.close();
////
////           br.close();
////        } catch (FileNotFoundException e) {
////           e.printStackTrace();
////        } catch (IOException e) {
////           e.printStackTrace();
////        }
////    }
////
////    public static void extract_info_auto(String evaluation_file, String file,String auto_annotation, String curtopic, boolean isnew){
////        //BufferedReader br;
////        String tempStr = "";
////
////        try {
////
////            if(!readerOpened){
////
////                   eia_br = new BufferedReader(new FileReader(auto_annotation));
////                   readerOpened=true;
////
////            }
////
////
////           while ((tempStr = eia_br.readLine()) != null){
////               if(tempStr.contains("calculate Involvement - ") || tempStr.contains("calculate Merged quintile")){
////                   for(int i = 0; i < categories.length; i++){
////                       if(tempStr.toLowerCase().contains("calculate involvement - "+categories[i]) || tempStr.toLowerCase().contains("calculate " +categories[i])){
////                           String qt_thrs = eia_br.readLine();
////                           String[] qt_thrs1 = qt_thrs.split("\\s+");
////                           String[] qt_thrs_array = new String[qt_thrs1.length-1];
////                           for(int j = 0; j < qt_thrs_array.length; j++){
////                               qt_thrs_array[j] = qt_thrs1[j+1];
////                               //System.out.println(qt_thrs_array[j] + " " + categories[i]);
////                           }
////                           /* save qt_thrs */
////                           auto_qt_thrs.put(categories[i], qt_thrs_array);
////
////                           /* save scores */
////                           String speaker = "";
////                           String[] score_array = new String[speakers.length];
////                           while((speaker = eia_br.readLine()) != null &&
////                                   speaker.contains("The quintile score")) {
////                               for(int k = 0; k < speakers.length; k++){
////                                   if(speaker.toLowerCase().contains(speakers[k] + " ")){
////                                        score_array[k] = speaker.toLowerCase();
////                                        //System.out.println("score_array[" + k + "]: " + score_array[k]);
////                                   }
////                               }
////                           }
////                       auto_scores.put(categories[i], score_array);
////                       }
////                   }
////
////                   save_human_quintile_scores();
////                    save_auto_quintile_scores();
////                    save_human_actual_scores();
////                    save_auto_actual_scores();
////
////                     writeToEvaluation(evaluation_file, file, auto_annotation, curtopic, isnew);
////                     System.err.println("Eval*********************");
////                        init();
////                        break;
////
////               }
////           }
////          
////        } catch (FileNotFoundException e) {
////           e.printStackTrace();
////        } catch (IOException e) {
////           e.printStackTrace();
////        }
////    }
////
////    public static void save_human_quintile_scores(){
////        Object[] category = human_scores.keySet().toArray();
////        for(int i = 0; i < category.length; i++){
////            int[] quintile_score_array = new int[speakers.length];
////            String[] scores = human_scores.get(category[i].toString());
////            for(int j = 0; j < scores.length; j++){
////                if(scores[j] == null || scores[j].equals("")){
////                    continue;
////                }
////                for(int k = 0; k < speakers.length; k++){
////                    if(scores[j].contains(speakers[k] + " ")){
////                        String speaker_scores = scores[j];
////                        String[] tmp = speaker_scores.split("---");
////                        String tmp2 = tmp[0].trim();
////                        String[] tmp3 = tmp2.split(":");
////                        String quintile_score = tmp3[1].trim();
////                        quintile_score_array[k] = Integer.parseInt(quintile_score);
////                        //System.out.println("quintile score for speaker " + speakers[k] + " is " + quintile_score_array[k]);
////
////                    }
////                }
////            }
////            human_quintile_scores.put(category[i].toString(), quintile_score_array);
////        }
////    }
////
////    public static void save_auto_quintile_scores(){
////        Object[] category = auto_scores.keySet().toArray();
////        for(int i = 0; i < category.length; i++){
////            int[] quintile_score_array = new int[speakers.length];
////            String[] scores = auto_scores.get(category[i].toString());
////            for(int j = 0; j < scores.length; j++){
////                if(scores[j] == null || scores[j].equals("")){
////                    continue;
////                }
////                for(int k = 0; k < speakers.length; k++){
////                    if(scores[j].contains(speakers[k] + " ")){
////                        String speaker_scores = scores[j];
////                        String[] tmp = speaker_scores.split("---");
////                        String tmp2 = tmp[0].trim();
////                        String[] tmp3 = tmp2.split(":");
////                        String quintile_score = tmp3[1].trim();
////                        quintile_score_array[k] = Integer.parseInt(quintile_score);
////                    }
////                }
////            }
////            auto_quintile_scores.put(category[i].toString(), quintile_score_array);
////        }
////    }
////
////    public static void save_human_actual_scores(){
////        Object[] category = human_scores.keySet().toArray();
////        for(int i = 0; i < category.length; i++){
////            String[] actual_score_array = new String[speakers.length];
////            String[] scores = human_scores.get(category[i].toString());
////            for(int j = 0; j < scores.length; j++){
////                if(scores[j] == null || scores[j].equals("")){
////                    continue;
////                }
////                for(int k = 0; k < speakers.length; k++){
////                    if(scores[j].contains(speakers[k] + " ")){
////                        String speaker_scores = scores[j];
////                        String[] tmp = speaker_scores.split("---");
////                        String tmp2 = tmp[1].trim();
////                        String[] tmp3 = tmp2.split(":");
////                        String actual_score = tmp3[1].trim();
////                        actual_score_array[k] = actual_score;
////                    }
////                }
////            }
////            human_actual_scores.put(category[i].toString(), actual_score_array);
////        }
////    }
////
////    public static void save_auto_actual_scores(){
////        Object[] category = auto_scores.keySet().toArray();
////        for(int i = 0; i < category.length; i++){
////            String[] actual_score_array = new String[speakers.length];
////            String[] scores = auto_scores.get(category[i].toString());
////            for(int j = 0; j < scores.length; j++){
////                if(scores[j] == null || scores[j].equals("")){
////                    continue;
////                }
////                for(int k = 0; k < speakers.length; k++){
////                    if(scores[j].contains(speakers[k] + " ")){
////                        String speaker_scores = scores[j];
////                        String[] tmp = speaker_scores.split("---");
////                        String tmp2 = tmp[1].trim();
////                        String[] tmp3 = tmp2.split(":");
////                        String actual_score = tmp3[1].trim();
////                        actual_score_array[k] = actual_score;
////                    }
////                }
////            }
////            auto_actual_scores.put(category[i].toString(), actual_score_array);
////        }
////    }
////
////    public static void writeToEvaluation(String filePath, String human_annotation, String auto_annotation, String curtopic, boolean isnew){
////
//////        double[] HighestRestMismatch = new double[speakers.length];
//////        double[] HighLowMismatch = new double[speakers.length];
//////        double[] ExactMatch = new double[speakers.length];
//////        double[] PartialMatch = new double[speakers.length];
////
////        try {
////                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));
////
////                if(isnew){ //only print for new section
////                	
////                	
////                    bw.write("---------------- Involvement Evaluation --------------------- \n");
////                    
////                    /*print file names*/
//////	                filename.printFileNames(bw);
////	                
////                    
//////                    bw.write("Human annotated file: " + human_annotation + "\n");
//////                    bw.write("Auto annotated file: " + auto_annotation + "\n");
////                    bw.write("--------------------------------------------------------------- \n");
////
////                }
////
////                for(int i = 0; i < categories.length; i++){
////
////                    if(!categories[i].equals(curtopic)){
////
////                        continue;
////
////                    }
////
////                    String category = categories[i];
////                    bw.write("+++++++++++++++++++++++++ " + category + " ++++++++++++++++++++++++++++++ \n");
//////                    bw.write("Speaker \t Auto_annotated \t Human_annotated \t Highest/Rest/Mismatch \t " +
//////	   				 "High/Low/Mismatch \t Exact-match \t Partial-match \n");
////	   			
////		   			 int[] auto_qscore = auto_quintile_scores.get(category);
////		   			 int[] human_qscore = human_quintile_scores.get(category);
////		   			
////		   			 //String[] auto_ascore = auto_actual_scores.get(category);
////		   			 //String[] human_ascore = human_actual_scores.get(category);
////		   			
////		   			 String[] auto_qt = auto_qt_thrs.get(category);
////		   			 String[] human_qt = human_qt_thrs.get(category);
////		   			
////		   			 int counter = 0;
////		   			 
////                    /*old match evaluation method*/
////                    MatchEval me = new MatchEval();
////                    me.matchEval(bw, auto_qscore, human_qscore, auto_qt, human_qt, speakers, category, human_actual_scores, auto_actual_scores, counter);
////                   
////
////                    /*compare_evaluation*/
//////                    CompareEval CpEval = new CompareEval();
//////                    CpEval.compareEval(bw, speakers, auto_qscore, human_qscore);
////
////                }
////
////                bw.close();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////
////
////    }
////
////}
////
////
////
