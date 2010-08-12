package evaluation;


import java.io.*;
//import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
//import evaluation_m2w.Compare_eval;

import util.MatchEval;

/**
 *
 * @author jgh
 */
public class topic_disagreement_sam {




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

    //private static boolean readerOpened=false;
    //private static BufferedReader eia_br;
    //private static BufferedReader backupeia_br;



    public static void main(String[] args){




        String[] annotators = {"ONE"};

        for(int i = 0; i < annotators.length; i++){






//          String human_annotation = "/home/ek2837/develop/NLTEST/DSARMD0100/splited_ymca_topic_disagreement_"+annotators[i]+"_0";
        	  //      String auto_annotation = "/home/ek2837/develop/NLTEST/DSARMD0100/splited_ymca_topic_disagreement_auto_0";

        	    //    String evaluation_file = "/home/ek2837/develop/NLTEST/DSARMD0100/"+annotators[i]+"5.result";





        	         String human_annotation = "C:\\Users\\Ed\\Documents\\nlp\\expressdisagree\\memo\\Mar11_topic_disagreement_lauren";
        	        String auto_annotation = "C:\\Users\\Ed\\Documents\\nlp\\expressdisagree\\memo\\Mar11_topic_disagreement_auto";

        	        String evaluation_file = "C:\\Users\\Ed\\Documents\\nlp\\expressdisagree\\memo\\Mar11_topic_disagreement.result";




            try { //extract names/topics
                BufferedReader br = new BufferedReader(new FileReader(human_annotation));



                String tempstr;

                while((tempstr=br.readLine())!=null){
                    if(tempstr.startsWith("The quintile score of ") && tempstr.matches(".*[A-Za-z]_[A-Za-z].*")){//get speaker names

                        byte[] tempbytes=tempstr.getBytes();

                        int firstsemicolon=0; //find the semi-colon index, this is where the name ends
                        for(int ei=0; ei<tempbytes.length; ei++){

                            if(tempbytes[ei]==':'){
                                firstsemicolon=ei;
                                break;
                            }
                        }


                        String curname=tempstr.substring("The quintile score of ".length(), firstsemicolon);

                        String firsthalf=curname.split("_")[0].toLowerCase();
                        String secondhalf=curname.split("_")[1].toLowerCase();

                        if( (speakers_map.get(firsthalf+"_"+secondhalf)==null) &&  (speakers_map.get(secondhalf+"_"+firsthalf)==null)){ //if speaker not yet in list

                            speakers_map.put(firsthalf+"_"+secondhalf, firsthalf+"_"+secondhalf);

                        }


      //                  System.err.println(tempstr);
    //                    System.err.println(firsthalf);
  //                      System.err.println(secondhalf);
//                        System.err.println(tempstr.substring("The quintile score of ".length(), firstsemicolon));


                    }
                    
                    else if(tempstr.contains("Topic Disagreement on ")){//get topics

                       // System.err.println(tempstr.split("\"")[1]);


                        if( categories_map.get(tempstr.split("\"")[1].toLowerCase())==null){ //if category not yet in list

                            categories_map.put(tempstr.split("\"")[1].toLowerCase(), tempstr.split("\"")[1].toLowerCase());

                        }

                    }

                }

        //        System.err.println(Arrays.toString(speakers_map.keySet().toArray()));

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
                Logger.getLogger(topic_disagreement_sam.class.getName()).log(Level.SEVERE, null, ex);
            }
              catch(IOException e){

                  e.printStackTrace();

            }





        init();

        extract_info_human(evaluation_file, human_annotation,auto_annotation);

//        extract_info_human(human_annotation);
  //      extract_info_auto(auto_annotation);
   //     save_human_quintile_scores();
    //    save_auto_quintile_scores();
     //   save_human_actual_scores();
      //  save_auto_actual_scores();

        //writeToEvaluation(evaluation_file, human_annotation, auto_annotation);
        //System.out.println(human_actual_scores.size() + " " + auto_actual_scores.size());
        }

//        try {
	//	eia_br.close();
	//} catch (IOException e) {
		//e.printStackTrace();
	//}

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
        String topic="";

        boolean isnew=false;

        try {
           br = new BufferedReader(new FileReader(file));
           while ((tempStr = br.readLine()) != null){

               if(tempStr.contains("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")){

                   isnew=true;

               }

               if(tempStr.contains("Topic Disagreement on ")){

                   topic=tempStr.split("\"")[1];

                   for(int i = 0; i < categories.length; i++){
                       if(tempStr.toLowerCase().contains(categories[i])){
                           String qt_thrs = br.readLine();
                           String[] qt_thrs1 = qt_thrs.split("\\s+");
                           String[] qt_thrs_array = new String[qt_thrs1.length-1];
                           for(int j = 0; j < qt_thrs_array.length; j++){
                               qt_thrs_array[j] = qt_thrs1[j+1];
                               //System.out.println(qt_thrs_array[j] + " " + categories[i]);
                           }
                           /* save qt_thrs */
                           human_qt_thrs.put(categories[i], qt_thrs_array);

                           /* save scores */
                           String speaker = "";
                           String[] score_array = new String[speakers.length];
                           while((speaker = br.readLine()) != null &&
                               speaker.contains("The quintile score")) {
                               for(int k = 0; k < speakers.length; k++){
                                   String[] speakers_order = speakers[k].split("_");
                                   if(speaker.toLowerCase().contains(speakers_order[0]+"_"+speakers_order[1]) ||
                                           speaker.toLowerCase().contains(speakers_order[1]+"_"+speakers_order[0])){

                                        score_array[k] = speaker.toLowerCase();
                                        //System.out.println("score_array[" + k + "]: " + score_array[k]);
                                   }
                               }
                           }
                       human_scores.put(categories[i], score_array);
                       }
                   }

                   boolean newsection;
                   newsection=false;

                   if(tempStr.contains("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")){

                       newsection=true;

                   }

                   extract_info_auto(evaluation_file, file, auto_annotation, topic, newsection, isnew);

                   isnew=false;

               }
           }

           //readerOpened=false; //next human annotator
           br.close();

           br.close();
        } catch (FileNotFoundException e) {
           e.printStackTrace();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    public static void extract_info_auto(String evaluation_file, String file,String auto_annotation, String topic, boolean newsection, boolean isnew){
        BufferedReader br;
        String tempStr = "";

//        backupeia_br=eia_br;

        try {
          // if(!readerOpened){

            //       eia_br = new BufferedReader(new FileReader(auto_annotation));
              //     readerOpened=true;
                //   backupeia_br=eia_br;


                //}

        	br=new BufferedReader(new FileReader(auto_annotation));

           while ((tempStr = br.readLine()) != null){

        	   System.err.println(tempStr);
        	   System.err.println("TzOPIC="+topic);


               if(tempStr.toLowerCase().contains("topic disagreement on meso-topic \""+topic.toLowerCase()+"\"")){

            	   System.err.println("GOT++++++++++++++++++++++++++++*************************************************************");

                   for(int i = 0; i < categories.length; i++){
                       if(tempStr.toLowerCase().contains(categories[i])){
                           String qt_thrs = br.readLine();
                           String[] qt_thrs1 = qt_thrs.split("\\s+");
                           String[] qt_thrs_array = new String[qt_thrs1.length-1];
                           for(int j = 0; j < qt_thrs_array.length; j++){
                               qt_thrs_array[j] = qt_thrs1[j+1];
                               System.out.println(qt_thrs_array[j] + " " + categories[i]);
                           }
                           /* save qt_thrs */
                           auto_qt_thrs.put(categories[i], qt_thrs_array);

                           /* save scores */
                           String speaker = "";
                           String[] score_array = new String[speakers.length];
                           while((speaker = br.readLine()) != null &&
                                   speaker.contains("The quintile score")) {
                               for(int k = 0; k < speakers.length; k++){
                                   String[] speakers_order = speakers[k].split("_");
                                   if(speaker.toLowerCase().contains(speakers_order[0]+"_"+speakers_order[1]) ||
                                           speaker.toLowerCase().contains(speakers_order[1]+"_"+speakers_order[0])){
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
                    save_human_actual_scores();
                    save_auto_actual_scores();


                     writeToEvaluation(evaluation_file, file, auto_annotation, topic, isnew);
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
            //System.out.println(scores.length + " length");
            for(int j = 0; j < scores.length; j++){
                //System.out.println(scores[j]);
                for(int k = 0; k < speakers.length; k++){
                    String[] speakers_order = speakers[k].split("_");
                    if(scores[j] == null || scores[j].equals("")){
                        continue;
                    }
                    if(scores[j].toLowerCase().contains(speakers_order[0]+"_"+speakers_order[1]) ||
                            scores[j].toLowerCase().contains(speakers_order[1]+"_"+speakers_order[0])){
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
                for(int k = 0; k < speakers.length; k++){
                    String[] speakers_order = speakers[k].split("_");
                    if(scores[j] == null || scores[j].equals("")){
                        continue;
                    }
                    if(scores[j].toLowerCase().contains(speakers_order[0]+"_"+speakers_order[1]) ||
                            scores[j].toLowerCase().contains(speakers_order[1]+"_"+speakers_order[0])){
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

    public static void save_human_actual_scores(){
        Object[] category = human_scores.keySet().toArray();
        for(int i = 0; i < category.length; i++){
            String[] actual_score_array = new String[speakers.length];
            String[] scores = human_scores.get(category[i].toString());
            for(int j = 0; j < scores.length; j++){
                for(int k = 0; k < speakers.length; k++){
                    String[] speakers_order = speakers[k].split("_");
                    if(scores[j] == null || scores[j].equals("")){
                        continue;
                    }
                    if(scores[j].toLowerCase().contains(speakers_order[0]+"_"+speakers_order[1]) ||
                            scores[j].toLowerCase().contains(speakers_order[1]+"_"+speakers_order[0])){

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

    public static void save_auto_actual_scores(){
        Object[] category = auto_scores.keySet().toArray();
        for(int i = 0; i < category.length; i++){
            String[] actual_score_array = new String[speakers.length];
            String[] scores = auto_scores.get(category[i].toString());
            for(int j = 0; j < scores.length; j++){
                for(int k = 0; k < speakers.length; k++){
                    String[] speakers_order = speakers[k].split("_");
                    if(scores[j] == null || scores[j].equals("")){
                        continue;
                    }
                    if(scores[j].toLowerCase().contains(speakers_order[0]+"_"+speakers_order[1]) ||
                            scores[j].toLowerCase().contains(speakers_order[1]+"_"+speakers_order[0])){
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

    public static void writeToEvaluation(String filePath, String human_annotation, String auto_annotation, String curtopic, boolean isnew){

//        double[] HighestRestMismatch = new double[speakers.length];
//        double[] HighLowMismatch = new double[speakers.length];
//        double[] ExactMatch = new double[speakers.length];
//        double[] PartialMatch = new double[speakers.length];

        try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true));

                if(isnew){ //only print for new section


                	bw.write("---------------- Topic Disagreement Evaluation --------------------- \n");
                	bw.write("Human annotated file: " + human_annotation + "\n");
                	bw.write("Auto annotated file: " + auto_annotation + "\n");
                	bw.write("--------------------------------------------------------------- \n");

                }


                System.err.println("GOT++++++++++++++++++++++++++++*************************************************************");

                for(int i = 0; i < categories.length; i++){

                    if(!categories[i].toLowerCase().equals(curtopic.toLowerCase())){

                        continue;

                    }

                    String category = categories[i];
                    bw.write("-----------------------------" + category + "---------------------------- \n");
//                    bw.write("Speaker \t Auto_annotated \t Human_annotated \t Highest/Rest/Mismatch \t " +
//					"High/Low/Mismatch \t Exact-match \t Partial-match \n");
			
					int[] auto_qscore = auto_quintile_scores.get(category);
					int[] human_qscore = human_quintile_scores.get(category);
					
					//String[] auto_ascore = auto_actual_scores.get(category);
					// String[] human_ascore = human_actual_scores.get(category);
					
					String[] auto_qt = auto_qt_thrs.get(category);
					String[] human_qt = human_qt_thrs.get(category);
					
					int counter = 0;
                    /*old match evaluation method*/
                    MatchEval me = new MatchEval();
                    me.matchEval_tpcdis(bw, auto_qscore, human_qscore, auto_qt, human_qt, speakers, category, human_actual_scores, auto_actual_scores, counter);


                    /*compare_evaluation*/
                    //Compare_eval CpEval = new Compare_eval();
                   // CpEval.compareEval(bw, speakers, auto_qscore, human_qscore);

                }

                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


    }

}



