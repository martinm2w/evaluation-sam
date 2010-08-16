package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.*;

import evaluation.topic_disagreement_sam;

public class ExtractAndBuild {

	private static String[] categories;

    private static String[] speakers;

    static HashMap<String, String> categories_map = new HashMap<String, String>();
    static HashMap<String, String> speakers_map = new HashMap<String, String>();

    static HashMap<String, String[]> human_qt_thrs = new HashMap<String, String[]>(); // key: category; value: qt_thrs array
    static HashMap<String, String[]> auto_qt_thrs = new HashMap<String, String[]>(); // key: category; value: qt_thrs array
    static HashMap<String, String[]> human_scores = new HashMap<String, String[]>(); // key: category; value: score, order by speakers
    static HashMap<String, String[]> auto_scores = new HashMap<String, String[]>(); // key: category; value: score, order by speakers
    static HashMap<String, int[]> human_quintile_scores = new HashMap<String, int[]>(); // key: category; value: quintile scores, order by speakers
    static HashMap<String, int[]> auto_quintile_scores = new HashMap<String, int[]>(); // key: category; value: quintile scores, order by speakers
    static HashMap<String, String[]> human_actual_scores = new HashMap<String, String[]>(); // key: category; value: actual scores, order by speakers
    static HashMap<String, String[]> auto_actual_scores = new HashMap<String, String[]>(); // key: category; value: actual scores, order by speakers

    /*new_file_names*/
//    static Filenames filename = new Filenames();
        
	private static boolean readerOpened=false;
	private static BufferedReader eia_br;
	
	public void init(){

        speakers_map.clear();
        categories_map.clear();
        human_scores.clear();
        auto_scores.clear();
        human_qt_thrs.clear();
        auto_qt_thrs.clear();
        human_quintile_scores.clear();
        auto_quintile_scores.clear();
        human_actual_scores.clear();
        auto_actual_scores.clear();
    }
	
	public void extractSpeakersAndCategoriesToMap(String human_annotation){
		
		try { //extract names/topics
            BufferedReader br = new BufferedReader(new FileReader(human_annotation));

            /*read in file name*/
//            filename.extractFileNames(human_annotation, auto_annotation);
            /*end*/

            String tempstr;

            while((tempstr=br.readLine())!=null){
                if(tempstr.startsWith("The quintile score of")) {
                	/* new */
                	String curname = tempstr.split(" ")[4].toLowerCase(); //get current name
                    
                    if( (speakers_map.get(curname)==null)){ //if speaker not yet in list
                        speakers_map.put(curname, curname); //put the name in corresponding index
                    }
                	
                	

                }else if(tempstr.contains("COMPUTING FOR")){//get topics
                	
                	/* get current category */
                	String curCtg = tempstr.split(":")[1].toLowerCase().split(" ")[0]; // get current topic

					if( categories_map.get(curCtg)==null){ //if category not yet in list
					    categories_map.put(curCtg, curCtg);
                    }
                 }
            } 
            
            Object[] speakerarray=speakers_map.keySet().toArray();


            setSpeakers(new String[speakerarray.length]);

            for(int ei=0;ei<speakerarray.length;ei++){
            	
                getSpeakers()[ei]=(String)speakerarray[ei];

//                System.err.println("Speakers : " + speakers[ei]);

            }


            Object[] categoriesarray=categories_map.keySet().toArray();

            setCategories(new String[categoriesarray.length]);

            for(int ei=0;ei<categoriesarray.length;ei++){

                getCategories()[ei]=(String)categoriesarray[ei];

//                System.err.println("categories : " + categories[ei]);

            }


            br.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(topic_disagreement_sam.class.getName()).log(Level.SEVERE, null, ex);
        }
          catch(IOException e){

              e.printStackTrace();

        }
		
	}
	
	public void extractSpknCatToMap_involvement(String human_annotation){
		
		try { //extract names/topics
            BufferedReader br = new BufferedReader(new FileReader(human_annotation));

            /*read in file name*/
//            filename.extractFileNames(human_annotation, auto_annotation);
            /*end*/

            String tempstr;

            while((tempstr=br.readLine())!=null){
                if(tempstr.startsWith("The quintile score of")) {
                	/* new */
                	String curname = tempstr.split(" ")[4].toLowerCase(); //get current name
                    
                    if( (speakers_map.get(curname)==null)){ //if speaker not yet in list
                        speakers_map.put(curname, curname); //put the name in corresponding index
                    }
                	
                	

                }
                
                if(tempstr.contains("COMPUTING FOR")){//get topics
                	
                	/* get current category */
                	String curCtg = tempstr.split(":")[1].toLowerCase().split(" ")[0]; // get current topic

					if( categories_map.get(curCtg)==null){ //if category not yet in list
					    categories_map.put(curCtg, curCtg);
                    }
                 }
                
                if(tempstr.contains("COMPUTING MERGED")){
                	
                	if( categories_map.get("merged")==null){ //if category not yet in list
					    categories_map.put("merged", "merged");
                    }
                	
                }
            } 
            
            Object[] speakerarray=speakers_map.keySet().toArray();


            setSpeakers(new String[speakerarray.length]);

            for(int ei=0;ei<speakerarray.length;ei++){
            	
                getSpeakers()[ei]=(String)speakerarray[ei];

//                System.err.println("Speakers : " + speakers[ei]);

            }


            Object[] categoriesarray=categories_map.keySet().toArray();

            setCategories(new String[categoriesarray.length]);

            for(int ei=0;ei<categoriesarray.length;ei++){

                getCategories()[ei]=(String)categoriesarray[ei];

            System.err.println("categories : " + categories[ei]);

            }


            br.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(topic_disagreement_sam.class.getName()).log(Level.SEVERE, null, ex);
        }
          catch(IOException e){

              e.printStackTrace();

        }
		
	}
	
	public void extract_info_human(String evaluation_file, 
											String human_annotation, 
											String auto_annotation){
		
        BufferedReader brh;
        String tempStr = "";
        
        String curtopic="";

        try {
        	
           brh = new BufferedReader(new FileReader(human_annotation));
           
           while ((tempStr = brh.readLine()) != null){
        	   
        	   System.out.println(" cat length" + getCategories().length );
        	   
               if(tempStr.contains("COMPUTING FOR ")){
            	  
            	   System.out.println(" cat length" + getCategories().length );
            	   
                   for(int i = 0; i < getCategories().length; i++){
                       if(tempStr.toLowerCase().contains(getCategories()[i])){
                          
                    	   curtopic=getCategories()[i];
                    	   
                    	   String qt_thrs = brh.readLine();
                           String[] qt_thrs1 = qt_thrs.split("\\s+");
                           String[] qt_thrs_array = new String[qt_thrs1.length-1];
                           for(int j = 0; j < qt_thrs_array.length; j++){
                               qt_thrs_array[j] = qt_thrs1[j+1];
                               //System.out.println(qt_thrs_array[j] + " " + categories[i]);
                           }
                           /* save qt_thrs */
                           human_qt_thrs.put(getCategories()[i], qt_thrs_array);

                           /* save scores */
                           String speaker = "";
                           String[] score_array = new String[getSpeakers().length];


//                           do{ //read out extra lines
//
//                                br.mark(1000);
//                                speaker=br.readLine();
//
//
//                           }while(speaker!=null && !speaker.contains("The quintile score") && !speaker.contains("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"));
//
//                           br.reset();



                           while((speaker = brh.readLine()) != null &&
                               speaker.contains("The quintile score")) {
//                               System.out.println("aaaaaaaaa");
                               for(int k = 0; k < getSpeakers().length; k++){
                            	   /* new */
                            	   if(speaker.toLowerCase().contains(getSpeakers()[k] + " ")){
                                       score_array[k] = speaker.toLowerCase();
                            	   
                                   /*String[] speakers_order = speakers[k].split("_");
                                   if(speaker.toLowerCase().contains(speakers_order[0]+"_"+speakers_order[1]) ||
                                           speaker.toLowerCase().contains(speakers_order[1]+"_"+speakers_order[0])){

                                        score_array[k] = speaker.toLowerCase();*/
                                        System.out.println("score_array[" + k + "]: " + score_array[k]);
                                   }
                               }
                           }
                       human_scores.put(getCategories()[i], score_array);
                       
                       }
                   } /*for(int i = 0; i < categories.length; i++)*/

//                   System.err.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

			extract_info_auto(evaluation_file, human_annotation, auto_annotation, curtopic);
					
//			isnew=false;
                /*if contains qt_thrs*/
               } /*if(tempStr.contains("calculate Expressive Disagreement -")*/
               

           } /*while ((tempStr = br.readLine()) != null)*/



           readerOpened=false; //next human annotator
           getEia_br().close(); //new file

           brh.close();


        } catch (FileNotFoundException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

	public void extract_info_human_involment(String evaluation_file, 
			String human_annotation, 
			String auto_annotation){

		BufferedReader brh;
        String tempStr = "";
        
        String curtopic="";

        try {
        	
           brh = new BufferedReader(new FileReader(human_annotation));
           
           while ((tempStr = brh.readLine()) != null){
        	   
        	   System.out.println(" cat length" + getCategories().length );
        	   
               if(tempStr.contains("COMPUTING FOR ") || tempStr.contains("COMPUTING MERGED")){
            	  
            	   System.out.println(" cat length" + getCategories().length );
            	   
                   for(int i = 0; i < getCategories().length; i++){
                       if(tempStr.toLowerCase().contains(getCategories()[i])){
                          
                    	   curtopic=getCategories()[i];
                    	   
                    	   String qt_thrs = brh.readLine();
                           String[] qt_thrs1 = qt_thrs.split("\\s+");
                           String[] qt_thrs_array = new String[qt_thrs1.length-1];
                           for(int j = 0; j < qt_thrs_array.length; j++){
                               qt_thrs_array[j] = qt_thrs1[j+1];
                               //System.out.println(qt_thrs_array[j] + " " + categories[i]);
                           }
                           /* save qt_thrs */
                           human_qt_thrs.put(getCategories()[i], qt_thrs_array);

                           /* save scores */
                           String speaker = "";
                           String[] score_array = new String[getSpeakers().length];


                           while((speaker = brh.readLine()) != null &&
                               speaker.contains("The quintile score")) {
//                               System.out.println("aaaaaaaaa");
                               for(int k = 0; k < getSpeakers().length; k++){
                            	   /* new */
                            	   if(speaker.toLowerCase().contains(getSpeakers()[k] + " ")){
                                       score_array[k] = speaker.toLowerCase();
                            	   
                                   /*String[] speakers_order = speakers[k].split("_");
                                   if(speaker.toLowerCase().contains(speakers_order[0]+"_"+speakers_order[1]) ||
                                           speaker.toLowerCase().contains(speakers_order[1]+"_"+speakers_order[0])){

                                        score_array[k] = speaker.toLowerCase();*/
                                        System.out.println("score_array[" + k + "]: " + score_array[k]);
                                   }
                               }
                           }
                       human_scores.put(getCategories()[i], score_array);
                       
                       }
                   } /*for(int i = 0; i < categories.length; i++)*/

//                   System.err.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

			extract_info_auto_involvement(evaluation_file, human_annotation, auto_annotation, curtopic);
					
//			isnew=false;
                /*if contains qt_thrs*/
               } /*if(tempStr.contains("calculate Expressive Disagreement -")*/
               

           } /*while ((tempStr = br.readLine()) != null)*/



           readerOpened=false; //next human annotator
           getEia_br().close(); //new file

           brh.close();


        } catch (FileNotFoundException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }
}
	
	public void extract_info_auto(String evaluation_file, 
									String human_annotation, 
									String auto_annotation,
									String curtopic){
        
        String tempStr = "";


        try {


        if(!readerOpened){

				setEia_br(new BufferedReader(new FileReader(auto_annotation)));
				readerOpened=true;

		}


           while ((tempStr = getEia_br().readLine()) != null){
               if(tempStr.contains("COMPUTING FOR ")){
            	   
            	   /* new*/
            	   /*eia_br.mark(1000);
            	   String checkLineAuto = eia_br.readLine();
            	   eia_br.reset();
            	   
            	   if(checkLineAuto.equals("")){
            		   continue;   
            	   }
            	   if(checkLineAuto.contains("qt_thrs")){
            		   */
            		  for(int i = 0; i < getCategories().length; i++){
                       if(tempStr.toLowerCase().contains(getCategories()[i])){
                           String qt_thrs = getEia_br().readLine();
                           String[] qt_thrs1 = qt_thrs.split("\\s+");
                           String[] qt_thrs_array = new String[qt_thrs1.length-1];
                           for(int j = 0; j < qt_thrs_array.length; j++){
                               qt_thrs_array[j] = qt_thrs1[j+1];
                               //System.out.println(qt_thrs_array[j] + " " + categories[i]);
                           }
                           /* save qt_thrs */
                           auto_qt_thrs.put(getCategories()[i], qt_thrs_array);

                           /* save scores */
                           String speaker = "";
                           String[] score_array = new String[getSpeakers().length];


					//eia_br.readLine();eia_br.readLine();eia_br.readLine();

//
//                           do{ //read out extra lines
//
//                                eia_br.mark(1000);
//                                speaker=eia_br.readLine();
//
//
//                           }while(speaker!=null && !speaker.contains("The quintile score") && !speaker.contains("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"));
//
//                           eia_br.reset();

                           while((speaker = getEia_br().readLine())!=null && speaker.contains("The quintile score")) {
                               for(int k = 0; k < getSpeakers().length; k++){
                            	   /* new */
                            	   if(speaker.toLowerCase().contains(getSpeakers()[k] + " ")){
                                       score_array[k] = speaker.toLowerCase();
                                  
                                   }
                               }
                           }
                       auto_scores.put(getCategories()[i], score_array);
                       }
                   }

                save_auto_actual_scores();
                save_auto_quintile_scores();
                save_human_actual_scores();
                save_human_quintile_scores();

 	            writeToEvaluation(evaluation_file, human_annotation, auto_annotation, curtopic);
 	            init();
			break;

                    /*if contains qt_thrs*/
               } /* if(tempStr.contains("calculate Expressive Disagreement -"))*/
           } /*while ((tempStr = eia_br.readLine()) != null)*/

        } catch (FileNotFoundException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

	public void extract_info_auto_involvement(String evaluation_file, 
			String human_annotation, 
			String auto_annotation,
			String curtopic){
		 String tempStr = "";


	        try {


	        if(!readerOpened){

					setEia_br(new BufferedReader(new FileReader(auto_annotation)));
					readerOpened=true;

			}


	           while ((tempStr = getEia_br().readLine()) != null){
	               if(tempStr.contains("COMPUTING FOR ") || tempStr.contains("COMPUTING MERGED")){
	             
	            		  for(int i = 0; i < getCategories().length; i++){
	                       if(tempStr.toLowerCase().contains(getCategories()[i])){
	                           String qt_thrs = getEia_br().readLine();
	                           String[] qt_thrs1 = qt_thrs.split("\\s+");
	                           String[] qt_thrs_array = new String[qt_thrs1.length-1];
	                           for(int j = 0; j < qt_thrs_array.length; j++){
	                               qt_thrs_array[j] = qt_thrs1[j+1];
	                               //System.out.println(qt_thrs_array[j] + " " + categories[i]);
	                           }
	                           /* save qt_thrs */
	                           auto_qt_thrs.put(getCategories()[i], qt_thrs_array);

	                           /* save scores */
	                           String speaker = "";
	                           String[] score_array = new String[getSpeakers().length];

	                           while((speaker = getEia_br().readLine())!=null && speaker.contains("The quintile score")) {
	                               for(int k = 0; k < getSpeakers().length; k++){
	                            	   /* new */
	                            	   if(speaker.toLowerCase().contains(getSpeakers()[k] + " ")){
	                                       score_array[k] = speaker.toLowerCase();
	                                  
	                                   }
	                               }
	                           }
	                       auto_scores.put(getCategories()[i], score_array);
	                       }
	                   }

	                save_auto_actual_scores();
	                save_auto_quintile_scores();
	                save_human_actual_scores();
	                save_human_quintile_scores();

	 	            writeToEvaluation(evaluation_file, human_annotation, auto_annotation, curtopic);
	 	            init();
				break;

	                    /*if contains qt_thrs*/
	               } /* if(tempStr.contains("calculate Expressive Disagreement -"))*/
	           } /*while ((tempStr = eia_br.readLine()) != null)*/

	        } catch (FileNotFoundException e) {
	           e.printStackTrace();
	        } catch (IOException e) {
	           e.printStackTrace();
	        }
	}
	
	
    public void save_human_quintile_scores(){
        Object[] category = human_scores.keySet().toArray();
        for(int i = 0; i < category.length; i++){
            int[] quintile_score_array = new int[getSpeakers().length];
            String[] scores = human_scores.get(category[i].toString());
           
            for(int j = 0; j < scores.length; j++){
              
                for(int k = 0; k < getSpeakers().length; k++){
                  
                    if(scores[j] == null || scores[j].equals("")){
                        continue;
                    }
                    /* new */
                    if(scores[j].contains(getSpeakers()[k])){
                 
                        String speaker_scores = scores[j];
                        String[] tmp = speaker_scores.split("---");
                        String tmp2 = tmp[0].trim();
                        String[] tmp3 = tmp2.split(":");
                        String quintile_score = tmp3[1].trim();
                        quintile_score_array[k] = Integer.parseInt(quintile_score);
                      

                    }
                }
            }
            human_quintile_scores.put(category[i].toString(), quintile_score_array);
        }
    }

    public void save_auto_quintile_scores(){
    	
        Object[] category = auto_scores.keySet().toArray();
        for(int i = 0; i < category.length; i++){
            int[] quintile_score_array = new int[getSpeakers().length];
            String[] scores = auto_scores.get(category[i].toString());
            for(int j = 0; j < scores.length; j++){
                for(int k = 0; k < getSpeakers().length; k++){
                   /* String[] speakers_order = speakers[k].split("_");*/
                    if(scores[j] == null || scores[j].equals("")){
                        continue;
                    }
                    /* new */
                    if(scores[j].contains(getSpeakers()[k])){
                    /*if(scores[j].contains(speakers_order[0]+"_"+speakers_order[1]) ||
                            scores[j].contains(speakers_order[1]+"_"+speakers_order[0])){*/
                        String speaker_scores = scores[j];
                        String[] tmp = speaker_scores.split("---");
                        String tmp2 = tmp[0].trim();
                        String[] tmp3 = tmp2.split(":");
                        String quintile_score = tmp3[1].trim();
                        quintile_score_array[k] = Integer.parseInt(quintile_score);
                    }
                }
            }
            auto_quintile_scores.put(category[i].toString(), quintile_score_array);
        }
    }

    public void save_human_actual_scores(){
    	
        Object[] category = human_scores.keySet().toArray();
        for(int i = 0; i < category.length; i++){
            String[] actual_score_array = new String[getSpeakers().length];
            String[] scores = human_scores.get(category[i].toString());
            for(int j = 0; j < scores.length; j++){
                for(int k = 0; k < getSpeakers().length; k++){
                    /*String[] speakers_order = speakers[k].split("_");*/
                    if(scores[j] == null || scores[j].equals("")){
                        continue;
                    }
                    /* new */
                    if(scores[j].contains(getSpeakers()[k])){
                    /*if(scores[j].contains(speakers_order[0]+"_"+speakers_order[1]) ||
                            scores[j].contains(speakers_order[1]+"_"+speakers_order[0])){
*/
                        String speaker_scores = scores[j];
                        String[] tmp = speaker_scores.split("---");
                        String tmp2 = tmp[1].trim();
                        String[] tmp3 = tmp2.split(":");
                        String actual_score = tmp3[1].trim();
                        actual_score_array[k] = actual_score;
                    }
                }
            }
            human_actual_scores.put(category[i].toString(), actual_score_array);
        }
    }

    public void save_auto_actual_scores(){
    	
        Object[] category = auto_scores.keySet().toArray();
        for(int i = 0; i < category.length; i++){
            String[] actual_score_array = new String[getSpeakers().length];
            String[] scores = auto_scores.get(category[i].toString());
            for(int j = 0; j < scores.length; j++){
                for(int k = 0; k < getSpeakers().length; k++){
                    /*String[] speakers_order = speakers[k].split("_");*/
                    if(scores[j] == null || scores[j].equals("")){
                        continue;
                    }
                    /* new */
                    if(scores[j].contains(getSpeakers()[k])){
                    /*if(scores[j].contains(speakers_order[0]+"_"+speakers_order[1]) ||
                            scores[j].contains(speakers_order[1]+"_"+speakers_order[0])){*/
                        String speaker_scores = scores[j];
                        String[] tmp = speaker_scores.split("---");
                        String tmp2 = tmp[1].trim();
                        String[] tmp3 = tmp2.split(":");
                        String actual_score = tmp3[1].trim();
                        actual_score_array[k] = actual_score;
                    }
                }
            }
            auto_actual_scores.put(category[i].toString(), actual_score_array);
        }
    }
	
    public void writeToEvaluation(String evaluation_file, 
			String human_annotation, 
			String auto_annotation,
			String curtopic){

        try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(evaluation_file, true));
                
//                if(isnew){ //only print for new section
		                bw.write("---------------------- Evaluation --------------------- \n");
		                
		                bw.write("Human File: " + human_annotation +"\n");
		                bw.write("Auto File: " + auto_annotation + "\n");
		                
		                bw.write("--------------------------------------------------------------- \n");
//                }
                
                for(int i = 0; i < getCategories().length; i++){
                	
                	if(!getCategories()[i].equals(curtopic)){

                        continue;

                    }
                	
                    String category = getCategories()[i];
                    bw.write("-----------------------------" + category + "---------------------------- \n");
//                    bw.write("Speaker \t Auto_annotated \t Human_annotated \t Highest/Rest/Mismatch \t " +
//					"High/Low/Mismatch \t Exact-match \t Partial-match \n");
				
					int[] auto_qscore = auto_quintile_scores.get(category);
					int[] human_qscore = human_quintile_scores.get(category);
					
					for (int k = 0; k < auto_qscore.length; k ++){
						
						System.err.println("auto q score"+ auto_qscore[k]);
						
					}
					
					for (int l = 0; l < auto_qscore.length; l ++){
						
						System.err.println("human q score"+ human_qscore[l]);
						
					}
					
					

					String[] auto_qt = auto_qt_thrs.get(category);
					String[] human_qt = human_qt_thrs.get(category);
					
					int counter = 0;
                    /*old match evaluation method*/
//                    MatchEval me = new MatchEval();
//                    me.matchEval_expdis(bw, auto_qscore, human_qscore, auto_qt, human_qt, speakers, category, human_actual_scores, auto_actual_scores, counter);

                    /*compare_evaluation*/
                    CompareEval CpEval = new CompareEval();
                    CpEval.compareEval(bw, getSpeakers(), auto_qscore, human_qscore);

                }

                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


    }

	public static void setSpeakers(String[] speakers) {
		ExtractAndBuild.speakers = speakers;
	}

	public static String[] getSpeakers() {
		return speakers;
	}

	public static void setCategories(String[] categories) {
		ExtractAndBuild.categories = categories;
	}

	public static String[] getCategories() {
		return categories;
	}

	public static void setEia_br(BufferedReader eia_br) {
		ExtractAndBuild.eia_br = eia_br;
	}

	public static BufferedReader getEia_br() {
		return eia_br;
	}
}
