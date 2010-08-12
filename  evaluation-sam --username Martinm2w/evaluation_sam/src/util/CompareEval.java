package util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author m2w
 *
 */


public class CompareEval {
	
	public void compareEval(BufferedWriter bw, String[] speakers, int[] auto_qscore, int[] human_qscore){
		
		ArrayList<String> compListH = new ArrayList<String>();
		ArrayList<String> compListA = new ArrayList<String>();
		int equalCount = 0;
		
		/*checking input*/
		for (int k = 0; k < auto_qscore.length; k ++ ){
			System.out.println("auto qscore : " + auto_qscore[k]);
		}
		
		System.out.println("----------------------------------------");
		
		for (int k = 0; k < human_qscore.length; k ++ ){
			System.out.println("human qscore : " + human_qscore[k]);
		}
		
		System.out.println("========================================");
		
		 /*Relations*/
        try {
        	
			bw.write("||||||||||||||||| Relations ||||||||||||||||||||");
			bw.newLine();
	        bw.write("Auto_file\t\t" + "Human_file");
	        bw.newLine();
			
	        for(int i = 0; i < (speakers.length - 1); i++){
	        
	        	/*1st round check*/
	        	if(auto_qscore == null || human_qscore == null){
	                continue;
	            }
	            if(auto_qscore[i] == 0 && human_qscore[i] == 0){
	                continue;
	            }
	            
	        	for(int j = i+1; j < speakers.length; j++){
	        		
	        		
	        		 /*2 if discard speaker not in subfile*/
	        		if(auto_qscore == null || human_qscore == null){
                        continue;
                    }
                    if(auto_qscore[j] == 0 && human_qscore[j] == 0){ //maybe -1
                        continue;
                    }
                                   
	        		String tempStra = null;
	        		String tempStrh = null;
	        		
	        		/*auto_file logic*/
	        		if(auto_qscore[i] < auto_qscore[j]){
	        			tempStra = speakers[i] + " < " + speakers[j];
	        		}
	        		
	        		if(auto_qscore[i] > auto_qscore[j]){
	        			tempStra = speakers[i] + " > " + speakers[j];
	        		}
	        		
	        		if(auto_qscore[i] == auto_qscore[j]){
	        			tempStra = speakers[i] + " = " + speakers[j];
	        		}
	        		
	        		/*human_file logic*/
	        		if (human_qscore[i] < human_qscore[j]){
	        			tempStrh = speakers[i] + " < " + speakers[j];
	        		
	        		}
	        		if (human_qscore[i] > human_qscore[j]){
	        			tempStrh = speakers[i] + " > " + speakers[j];
	        		}
	        		if (human_qscore[i] == human_qscore[j]){
	        			tempStrh = speakers[i] + " = " + speakers[j];
	        		}
	        		
	        		
	        		compListA.add(tempStra);// use to print result
	        		compListH.add(tempStrh);
	        		
	        		/*calculate equalcount*/
	        		if(tempStra.equals(tempStrh)){
	        			equalCount++;
	        		}
	        		
	        		bw.write(tempStra + "\t\t" + tempStrh);
	        		bw.newLine();
	        	}
	        	
	        } // for all speakers
	        
	        /*result eval line*/
	        bw.write("::Equalcount\t\tTotalcount\t\tAccuracy");
	        bw.newLine();
	        bw.write("::" + equalCount + "\t\t" + compListA.size() + "\t\t" + ((double)equalCount / (double)compListA.size()));
	        bw.newLine();
	        
	        compListA.clear();
	        compListH.clear();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
//	
//	public void compareEval_tsk_ctrl(BufferedWriter bw, String[] speakers, int[] auto_qscore, int[] human_qscore){ArrayList<String> compListH = new ArrayList<String>();
//	ArrayList<String> compListA = new ArrayList<String>();
//	int equalCount = 0;
//	
//	/*checking input*/
//	for (int k = 0; k < auto_qscore.length; k ++ ){
//		System.out.println("auto qscore : " + auto_qscore[k]);
//	}
//	
//	System.out.println("----------------------------------------");
//	
//	for (int k = 0; k < human_qscore.length; k ++ ){
//		System.out.println("human qscore : " + human_qscore[k]);
//	}
//	
//	System.out.println("========================================");
//	
//	 /*Relations*/
//    try {
//    	
//		bw.write("||||||||||||||||| Relations ||||||||||||||||||||");
//		bw.newLine();
//        bw.write("Auto_file\t\t" + "Human_file");
//        bw.newLine();
//		
//        for(int i = 0; i < (speakers.length - 1); i++){
//        	
//        	/*1st round check*/
//        	if(auto_qscore == null || human_qscore == null){
//                continue;
//            }
//            if(auto_qscore[i] == 0 && human_qscore[i] == 0){
//                continue;
//            }
//        
//        	for(int j = i+1; j < speakers.length; j++){
//        		
//        		
//        		 /*2 if discard speaker not in subfile*/
//        		if(auto_qscore == null || human_qscore == null){
//                    continue;
//                }
//                if(auto_qscore[j] == 0 && human_qscore[j] == 0){
//                    continue;
//                }
////        		if(auto_qscore == null || human_qscore == null){
////                    continue;
////                }
////        		
////                if(auto_qscore[j] == 0  && human_qscore[j] == 0){ //maybe -1
////                	System.out.println("here is an zero");
////                    continue;
////                }
//                               
//        		String tempStra = null;
//        		String tempStrh = null;
//        		
//        		/*auto_file logic*/
//        		if(auto_qscore[i] < auto_qscore[j]){
//        			tempStra = speakers[i] + " < " + speakers[j];
//        		}
//        		
//        		if(auto_qscore[i] > auto_qscore[j]){
//        			tempStra = speakers[i] + " > " + speakers[j];
//        		}
//        		
//        		if(auto_qscore[i] == auto_qscore[j]){
//        			tempStra = speakers[i] + " = " + speakers[j];
//        		}
//        		
//        		/*human_file logic*/
//        		if (human_qscore[i] < human_qscore[j]){
//        			tempStrh = speakers[i] + " < " + speakers[j];
//        		
//        		}
//        		if (human_qscore[i] > human_qscore[j]){
//        			tempStrh = speakers[i] + " > " + speakers[j];
//        		}
//        		if (human_qscore[i] == human_qscore[j]){
//        			tempStrh = speakers[i] + " = " + speakers[j];
//        		}
//        		
//        		
//        		compListA.add(tempStra);// use to print result
//        		compListH.add(tempStrh);
//        		
//        		/*calculate equalcount*/
//        		if(tempStra.equals(tempStrh)){
//        			equalCount++;
//        		}
//        		
//        		bw.write(tempStra + "\t\t" + tempStrh);
//        		bw.newLine();
//        	}
//        	
//        } // for all speakers
//        
//        /*result eval line*/
//        bw.write("::Equalcount\t\tAccuracy\t\tTotalcount");
//        bw.newLine();
//        bw.write("::" + equalCount + "\t\t" + ((double)equalCount / (double)compListA.size()) + "\t\t" + compListA.size());
//        bw.newLine();
//        
//        compListA.clear();
//        compListH.clear();
//        
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	}
}