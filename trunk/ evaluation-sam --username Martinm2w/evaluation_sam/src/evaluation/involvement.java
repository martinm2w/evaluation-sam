/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package evaluation;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
//import java.util.logging.Level;
//import java.util.logging.Logger;

import util.CompareEval;
//import util.Filenames;
import util.MatchEval;


/**
 *
 * @author jgh
 */
public class involvement {


   //static String[] categories = {"NPI", "TI", "TCI", "ALLOTP", "ASMI"};
  //  static String[] speakers = {"kerri", "ken", "kara", "chris", "jennifer", "jianhua", "jenny"};


    static String[] categories;

    static String[] speakers;

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


    public static void main(String[] args){

    //   String[] annotators = {"brian", "kerri", "lauren"};

      //  for(int i = 0; i < annotators.length; i++){
    	 String human_annotation = "D:/m2w cs/evaluation_sam/input_files/Dec05_Involvement_Annotated" ;
         String auto_annotation = "D:/m2w cs/evaluation_sam/input_files/Dec05_Involvement" ;

         String evaluation_file = "D:/m2w cs/evaluation_sam/output_files/Dec05_Involvement_result_ce";



            try { //extract names/topics
                BufferedReader br = new BufferedReader(new FileReader(human_annotation));

              
                String tempstr;

                while((tempstr=br.readLine())!=null){
                    if(tempstr.startsWith("The quintile score of ")/*|| tempstr.startsWith("The quintile score ")*/){//get speaker names

                        String name = "";

                        if(tempstr.startsWith("The quintile score of ")){

                            name=tempstr.split(" ")[4];

                        }

                        name=name.toLowerCase();

                        if( (speakers_map.get(name)==null)){ //if speaker not yet in list

                            speakers_map.put(name, name);

                        }



                    }
                    /*merged files*/
                    else if(tempstr.contains("COMPUTING ANNOTATED MERGED")){
                    	
                    	if(categories_map.get("merged") == null){
                    		
                    		categories_map.put("merged", "merged");
                    		
                    	}
                    }
                    
                    /*normal files*/
                    else if(tempstr.contains("COMPUTING FOR")){//get topics

                    	String curCtg = tempstr.split(":")[1].toLowerCase().split(" ")[0];

                        if( categories_map.get(curCtg)==null){ //if category not yet in list

                            categories_map.put(curCtg,curCtg);

                        }

                    }

                }

                System.err.println(Arrays.toString(speakers_map.keySet().toArray()));

                                System.err.println(Arrays.toString(categories_map.keySet().toArray()));


                Object[] speakerarray=speakers_map.keySet().toArray();


                speakers=new String[speakerarray.length];

                for(int ei=0;ei<speakerarray.length;ei++){

                    speakers[ei]=(String)speakerarray[ei];

                    System.err.println(speakers[ei]);

                }


                Object[] categoriesarray=categories_map.keySet().toArray();

                categories=new String[categoriesarray.length];

                for(int ei=0;ei<categoriesarray.length;ei++){

                    categories[ei]=(String)categoriesarray[ei];

                    System.err.println(categories[ei]);

                }


                
                br.close();

            } catch (FileNotFoundException ex) {
            	 ex.printStackTrace();
//                Logger.getLogger(topic_disagreement.class.getName()).log(Level.SEVERE, null, ex);
            }
              catch(IOException e){

            	  e.printStackTrace();

            }



            init();

            extract_info_human(evaluation_file, human_annotation,auto_annotation);

          
        try {
		eia_br.close();
	} catch (IOException e) {
		e.printStackTrace();
	}

    }

    public static void init(){


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

    public static void extract_info_human(String evaluation_file, String file,String auto_annotation){
        BufferedReader br;
        String tempStr = "";

        String curtopic="";


        try {
           br = new BufferedReader(new FileReader(file));
           while ((tempStr = br.readLine()) != null){


               if(tempStr.startsWith("COMPUTING")){
                   for(int i = 0; i < categories.length; i++){
                       if(tempStr.toLowerCase().contains(categories[i])) {
//                    		   || tempStr.toLowerCase().contains("computing annotated" +categories[i])){

                           curtopic=categories[i];
                           String qt_thrs = br.readLine();

//                           String[] qt_thrs1 = qt_thrs.split("\\s+");
//                           String[] qt_thrs_array = new String[qt_thrs1.length-1];
//                           for(int j = 0; j < qt_thrs_array.length; j++){
//                               qt_thrs_array[j] = qt_thrs1[j+1];
//                               //System.out.println(qt_thrs_array[j] + " " + categories[i]);
//                           }
//                           /* save qt_thrs */
//                           human_qt_thrs.put(categories[i], qt_thrs_array);

                           /* save scores */
                           String speaker = "";
                           String[] score_array = new String[speakers.length];
                           while((speaker = br.readLine()) != null &&
                               speaker.contains("The quintile score")) {

                               for(int k = 0; k < speakers.length; k++){
                                   if(speaker.toLowerCase().contains(speakers[k] + " ")){
                                        score_array[k] = speaker.toLowerCase();
//                                        System.out.println("score_array[" + k + "]: " + score_array[k]);
                                   }
                               }
                           }
                       human_scores.put(categories[i], score_array);
                       }
                   }

                   extract_info_auto(evaluation_file, file, auto_annotation, curtopic/*, isnew*/);

//                   isnew=false;

               }
           }

           readerOpened=false; //next human annotator
           eia_br.close();

           br.close();
        } catch (FileNotFoundException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    public static void extract_info_auto(String evaluation_file, String file,String auto_annotation, String curtopic/*, boolean isnew*/){
        //BufferedReader br;
        String tempStr = "";

        try {

            if(!readerOpened){

                   eia_br = new BufferedReader(new FileReader(auto_annotation));
                   readerOpened=true;

            }


           while ((tempStr = eia_br.readLine()) != null){
               if(tempStr.contains("COMPUTING")/* || tempStr.contains("COMPUTING MERGED")*/){
                   for(int i = 0; i < categories.length; i++){
                       if(tempStr.toLowerCase().contains(categories[i])){
                           String qt_thrs = eia_br.readLine();
//                           String[] qt_thrs1 = qt_thrs.split("\\s+");
//                           String[] qt_thrs_array = new String[qt_thrs1.length-1];
//                           for(int j = 0; j < qt_thrs_array.length; j++){
//                               qt_thrs_array[j] = qt_thrs1[j+1];
//                               //System.out.println(qt_thrs_array[j] + " " + categories[i]);
//                           }
//                           /* save qt_thrs */
//                           auto_qt_thrs.put(categories[i], qt_thrs_array);

                           /* save scores */
                           String speaker = "";
                           String[] score_array = new String[speakers.length];
                           while((speaker = eia_br.readLine()) != null &&
                                   speaker.contains("The quintile score")) {
                               for(int k = 0; k < speakers.length; k++){
                                   if(speaker.toLowerCase().contains(speakers[k] + " ")){
                                        score_array[k] = speaker.toLowerCase();
                                        //System.out.println("score_array[" + k + "]: " + score_array[k]);
                                   }
                               }
                           }
                       auto_scores.put(categories[i], score_array);
                       }
                   }

                   save_human_quintile_scores();
                    save_auto_quintile_scores();
//                    save_human_actual_scores();
//                    save_auto_actual_scores();

                     writeToEvaluation(evaluation_file, file, auto_annotation, curtopic/*, isnew*/);
                     System.err.println("Eval*********************");
                        init();
                        break;

               }
           }
          
        } catch (FileNotFoundException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    public static void save_human_quintile_scores(){
        Object[] category = human_scores.keySet().toArray();
        for(int i = 0; i < category.length; i++){
            int[] quintile_score_array = new int[speakers.length];
            String[] scores = human_scores.get(category[i].toString());
            for(int j = 0; j < scores.length; j++){
                if(scores[j] == null || scores[j].equals("")){
                    continue;
                }
                for(int k = 0; k < speakers.length; k++){
                    if(scores[j].contains(speakers[k] + " ")){
                        String speaker_scores = scores[j];
                        String[] tmp = speaker_scores.split("---");
                        String tmp2 = tmp[0].trim();
                        String[] tmp3 = tmp2.split(":");
                        String quintile_score = tmp3[1].trim();
                        quintile_score_array[k] = Integer.parseInt(quintile_score);
                        //System.out.println("quintile score for speaker " + speakers[k] + " is " + quintile_score_array[k]);

                    }
                }
            }
            human_quintile_scores.put(category[i].toString(), quintile_score_array);
        }
    }

    public static void save_auto_quintile_scores(){
        Object[] category = auto_scores.keySet().toArray();
        for(int i = 0; i < category.length; i++){
            int[] quintile_score_array = new int[speakers.length];
            String[] scores = auto_scores.get(category[i].toString());
            for(int j = 0; j < scores.length; j++){
                if(scores[j] == null || scores[j].equals("")){
                    continue;
                }
                for(int k = 0; k < speakers.length; k++){
                    if(scores[j].contains(speakers[k] + " ")){
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

//    public static void save_human_actual_scores(){
//        Object[] category = human_scores.keySet().toArray();
//        for(int i = 0; i < category.length; i++){
//            String[] actual_score_array = new String[speakers.length];
//            String[] scores = human_scores.get(category[i].toString());
//            for(int j = 0; j < scores.length; j++){
//                if(scores[j] == null || scores[j].equals("")){
//                    continue;
//                }
//                for(int k = 0; k < speakers.length; k++){
//                    if(scores[j].contains(speakers[k] + " ")){
//                        String speaker_scores = scores[j];
//                        String[] tmp = speaker_scores.split("---");
//                        String tmp2 = tmp[1].trim();
//                        String[] tmp3 = tmp2.split(":");
//                        String actual_score = tmp3[1].trim();
//                        actual_score_array[k] = actual_score;
//                    }
//                }
//            }
//            human_actual_scores.put(category[i].toString(), actual_score_array);
//        }
//    }

//    public static void save_auto_actual_scores(){
//        Object[] category = auto_scores.keySet().toArray();
//        for(int i = 0; i < category.length; i++){
//            String[] actual_score_array = new String[speakers.length];
//            String[] scores = auto_scores.get(category[i].toString());
//            for(int j = 0; j < scores.length; j++){
//                if(scores[j] == null || scores[j].equals("")){
//                    continue;
//                }
//                for(int k = 0; k < speakers.length; k++){
//                    if(scores[j].contains(speakers[k] + " ")){
//                        String speaker_scores = scores[j];
//                        String[] tmp = speaker_scores.split("---");
//                        String tmp2 = tmp[1].trim();
//                        String[] tmp3 = tmp2.split(":");
//                        String actual_score = tmp3[1].trim();
//                        actual_score_array[k] = actual_score;
//                    }
//                }
//            }
//            auto_actual_scores.put(category[i].toString(), actual_score_array);
//        }
//    }

    public static void writeToEvaluation(String filePath, String human_annotation, String auto_annotation, String curtopic/*, boolean isnew*/){

//        double[] HighestRestMismatch = new double[speakers.length];
//        double[] HighLowMismatch = new double[speakers.length];
//        double[] ExactMatch = new double[speakers.length];
//        double[] PartialMatch = new double[speakers.length];

        try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));

//                if(isnew){ //only print for new section
                	
                	
                    bw.write("---------------- Involvement Evaluation --------------------- \n");
                    
                    /*print file names*/
//	                filename.printFileNames(bw);
	                
                    
                    bw.write("Human annotated file: " + human_annotation + "\n");
                    bw.write("Auto annotated file: " + auto_annotation + "\n");
                    bw.write("--------------------------------------------------------------- \n");

//                }

                for(int i = 0; i < categories.length; i++){

                    if(!categories[i].equals(curtopic)){

                        continue;

                    }

                    String category = categories[i];
                    bw.write("+++++++++++++++++++++++++ " + category + " ++++++++++++++++++++++++++++++ \n");
//                    bw.write("Speaker \t Auto_annotated \t Human_annotated \t Highest/Rest/Mismatch \t " +
//	   				 "High/Low/Mismatch \t Exact-match \t Partial-match \n");
	   			
		   			 int[] auto_qscore = auto_quintile_scores.get(category);
		   			 int[] human_qscore = human_quintile_scores.get(category);
		   			
		   			 //String[] auto_ascore = auto_actual_scores.get(category);
		   			 //String[] human_ascore = human_actual_scores.get(category);
		   			
//		   			 String[] auto_qt = auto_qt_thrs.get(category);
//		   			 String[] human_qt = human_qt_thrs.get(category);
		   			
//		   			 int counter = 0;
		   			 
                    /*old match evaluation method*/
//                    MatchEval me = new MatchEval();
//                    me.matchEval(bw, auto_qscore, human_qscore, auto_qt, human_qt, speakers, category, human_actual_scores, auto_actual_scores, counter);
                   

                    /*compare_evaluation*/
                    CompareEval CpEval = new CompareEval();
                    CpEval.compareEval(bw, speakers, auto_qscore, human_qscore);

                }

                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


    }

}



