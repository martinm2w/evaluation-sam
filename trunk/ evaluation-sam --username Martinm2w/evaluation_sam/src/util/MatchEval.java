package util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class MatchEval {

	public void matchEval(BufferedWriter bw, int[] auto_qscore,
											 int[] human_qscore,
											 String[] auto_qt,
											 String[] human_qt,
										     String[] speakers,
										     String category,
//										     double[] HighestRestMismatch,
//										     double[] HighLowMismatch,
//										     double[] ExactMatch,
//										     double[] PartialMatch,
										     HashMap<String, String[]> human_actual_scores,
										     HashMap<String, String[]> auto_actual_scores,
										     int counter
										     ){
			
		
		
			 try {
				 double[] HighestRestMismatch = new double[speakers.length];
			        double[] HighLowMismatch = new double[speakers.length];
			        double[] ExactMatch = new double[speakers.length];
			        double[] PartialMatch = new double[speakers.length];
				 bw.write("Speaker \t Auto_annotated \t Human_annotated \t Highest/Rest/Mismatch \t " +
				 "High/Low/Mismatch \t Exact-match \t Partial-match \n");
			 /*Scores*/
			 for(int j = 0; j < speakers.length; j++){
			
			     if(auto_qscore == null || human_qscore == null){
			         continue;
			     }
			     if(auto_qscore[j] == 0 && human_qscore[j] == 0){
			         continue;
			     }
			
			     counter++;
			
			     String speaker = speakers[j];
			     bw.write(speaker + "\t");
			     bw.write(auto_qscore[j] + "\t");
			     bw.write(human_qscore[j] + "\t");
			     
			     
			     
			     
			     /* Highest/Rest/Mismatch */
			     if(auto_qscore[j] == 5 && human_qscore[j] == 5){
			         bw.write("Highest\t");
			         HighestRestMismatch[j] = 1;
			     }
			     else if(auto_qscore[j] == 5 || human_qscore[j] == 5){
			         bw.write("Mismatch\t");
			         HighestRestMismatch[j] = 0;
			     }
			     else{
			         bw.write("Rest\t");
			         HighestRestMismatch[j] = 0.5;
			     }
			
			     /* High/Low/Mismatch */
			     if(auto_qscore[j] >= 4 && human_qscore[j] >=4){
			         bw.write("High\t");
			         HighLowMismatch[j] = 1;
			     }
			     else if(auto_qscore[j] < 4 && human_qscore[j] < 4){
			         bw.write("Low\t");
			         HighLowMismatch[j] = 0.5;
			     }
			     else{
			         bw.write("Mismatch\t");
			         HighLowMismatch[j] = 0;
			     }
			
			     /* Exact-match */
			     if(auto_qscore[j] == human_qscore[j]){
			         bw.write("yes\t");
			         ExactMatch[j] = 1;
			     }
			     else{
			         bw.write("no\t");
			         ExactMatch[j] = 0;
			     }
			
			     /* Partial-match */
			     if(auto_qscore[j] == human_qscore[j]){
			         bw.write("1\t");
			         PartialMatch[j] = 1;
			     }
			     else if(auto_qscore[j]-human_qscore[j] > 1 || auto_qscore[j]-human_qscore[j] < -1){
			         bw.write("0\t");
			         PartialMatch[j] = 0;
			     }
			     else{ // calculate partial score
			         String[] auto_actual_scores_array = auto_actual_scores.get(category);
			
			
			         
			
			         String[] human_actual_scores_array = human_actual_scores.get(category);
			
			         System.err.println(human_actual_scores_array[4]);
			
			         //check if human_actual_scores_array is all null
			
			         boolean notallnull;
			         notallnull=false;
			
			         for(int gh=0; gh<human_actual_scores_array.length;gh++){
			
			             if(human_actual_scores_array[gh]!=null){
			
			                 notallnull=true;
			                 break;
			
			             }
			
			         }
			
			        if(!notallnull){ // human_actual_scores_array is all null
			             bw.write("0\t");
			             PartialMatch[j] = 0;
			
			        }
			        else{
			
			         for(int ei=0; ei<human_actual_scores_array.length;ei++){
			             System.err.println(human_actual_scores_array[ei]+"aaaaaaaaaaaaaaaaa");
			             System.err.println(human_qscore[ei]);
			
			
			         }
			         
			         double human_actual_score = Double.parseDouble(human_actual_scores_array[j]);
			            double auto_actual_score = Double.parseDouble(auto_actual_scores_array[j]);
			
			
			
			
			
			         if(human_qscore[j] - auto_qscore[j]  == 1){
			
			             int mid_qt_thrs_index = 5 - human_qscore[j];
			             double auto_mid_qt_thrs = Double.parseDouble(auto_qt[mid_qt_thrs_index]);
			             double human_mid_qt_thrs = Double.parseDouble(human_qt[mid_qt_thrs_index]);
			             double p = 0.0;
			             double np = 0.0;
			             if(mid_qt_thrs_index != 3){
			                 p = auto_mid_qt_thrs - auto_actual_score;
			                 np = auto_actual_score - Double.parseDouble(auto_qt[mid_qt_thrs_index+1]);
			             }
			             else{
			                 p = human_actual_score - human_mid_qt_thrs;
			                 np = Double.parseDouble(human_qt[mid_qt_thrs_index-1]) - human_actual_score;
			             }
			             System.out.println("p: " + p + "np: "+ np);
			             if(p <= np){
			                 bw.write("0.5\t");
			                 PartialMatch[j] = 0.5;
			             }
			             else{
			                 bw.write("0\t");
			                 PartialMatch[j] = 0;
			             }
			         }
			         else if(human_qscore[j] - auto_qscore[j]  == -1){
			             int mid_qt_thrs_index = 5 - auto_qscore[j];
			             double auto_mid_qt_thrs = Double.parseDouble(auto_qt[mid_qt_thrs_index]);
			             double human_mid_qt_thrs = Double.parseDouble(human_qt[mid_qt_thrs_index]);
			             double p = 0.0;
			             double np = 0.0;
			             if(mid_qt_thrs_index != 3){
			                 p = human_mid_qt_thrs - human_actual_score;
			                 np = human_actual_score - Double.parseDouble(human_qt[mid_qt_thrs_index+1]);
			             }
			             else{
			                 p = auto_actual_score - auto_mid_qt_thrs;
			                 np = Double.parseDouble(auto_qt[mid_qt_thrs_index-1]) - auto_actual_score;
			             }
			             System.out.println("p: " + p + "np: "+ np);
			             if(p <= np){
			                 bw.write("0.5\t");
			                 PartialMatch[j] = 0.5;
			             }
			             else{
			                 bw.write("0\t");
			                 PartialMatch[j] = 0;
			             }
			
			         }
			     }
			
			     }
			
			     bw.write("\n");
			 }
			 
			 /* Precision */
			 bw.write("Precision: \t");
			
			 int HRM = 0, HLM = 0, EXM = 0;
			 double PM = 0;
			 for(int k = 0; k < speakers.length; k++){
			     if(HighestRestMismatch[k] != 0){
			         HRM++;
			     }
			     if(HighLowMismatch[k] != 0){
			         HLM++;
			     }
			     if(ExactMatch[k] != 0){
			         EXM++;
			     }
			     if(PartialMatch[k] != 0){
			         PM += PartialMatch[k];
			     }
			 }
			 double HRM_precision = (double)HRM/(double)counter;
			 bw.write(HRM_precision + "(Highest/Rest/Mismatch)\t");
			 double HLM_precision = (double)HLM/(double)counter;
			 bw.write(HLM_precision + "(High/Low/Mismatch)\t");
			 double EXM_precision = (double)EXM/(double)counter;
			 bw.write(EXM_precision + "(Exact-match)\t");
			 double PM_precision = (double)PM/(double)counter;
			 bw.write(PM_precision + "(Partial-match)\t");
			
			 bw.write("\n");
			 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	public void matchEval_expdis(BufferedWriter bw, int[] auto_qscore,
			 int[] human_qscore,
			 String[] auto_qt,
			 String[] human_qt,
		     String[] speakers,
		     String category,
//		     double[] HighestRestMismatch,
//		     double[] HighLowMismatch,
//		     double[] ExactMatch,
//		     double[] PartialMatch,
		     HashMap<String, String[]> human_actual_scores,
		     HashMap<String, String[]> auto_actual_scores,
		     int counter
		     ){
		        try {
		        	double[] HighestRestMismatch = new double[speakers.length];
			        double[] HighLowMismatch = new double[speakers.length];
			        double[] ExactMatch = new double[speakers.length];
			        double[] PartialMatch = new double[speakers.length];
		        	
		        	bw.write("Speaker \t Auto_annotated \t Human_annotated \t Highest/Rest/Mismatch \t " +
					"High/Low/Mismatch \t Exact-match \t Partial-match \n");
		        	
				for(int j = 0; j < speakers.length; j++){
				    if(auto_qscore[j] == 0 && human_qscore[j] == 0){
				        continue;
				    }
				
				    counter++;
				
				    String speaker = speakers[j];
				    bw.write(speaker + "\t");
				    if(human_qscore == null){
				        human_qscore = new int[speakers.length];
				    }
				    if(auto_qscore == null){
				        auto_qscore = new int[speakers.length];
				    }
				    if(auto_qscore[j] != 0){
				        bw.write(auto_qscore[j] + "\t");
				    }
				    else{
				        bw.write("NA\t");
				    }
				    if(human_qscore[j] != 0){
				        bw.write(human_qscore[j] + "\t");
				    }
				    else{
				        bw.write("NA\t");
				    }
				    /* Highest/Rest/Mismatch */
				    if(auto_qscore[j] == 5 && human_qscore[j] == 5){
				        bw.write("Highest\t");
				        HighestRestMismatch[j] = 1;
				    }
				    else if(auto_qscore[j] == 5 || human_qscore[j] == 5){
				        bw.write("Mismatch\t");
				        HighestRestMismatch[j] = 0;
				    }
				    else if(auto_qscore[j] == 0 || human_qscore[j] == 0){
				        bw.write("NA\t");
				        HighestRestMismatch[j] = 0;
				    }
				    else{
				        bw.write("Rest\t");
				        HighestRestMismatch[j] = 0.5;
				    }
				
				    /* High/Low/Mismatch */
				    if(auto_qscore[j] >= 4 && human_qscore[j] >=4){
				        bw.write("High\t");
				        HighLowMismatch[j] = 1;
				    }
				    else if(auto_qscore[j] == 0 || human_qscore[j] == 0){
				        bw.write("NA\t");
				        HighLowMismatch[j] = 0;
				    }
				    else if(auto_qscore[j] < 4 && human_qscore[j] < 4){
				        bw.write("Low\t");
				        HighLowMismatch[j] = 0.5;
				    }
				    else{
				        bw.write("Mismatch\t");
				        HighLowMismatch[j] = 0;
				    }
				
				    /* Exact-match */
				    if(auto_qscore[j] != 0 && auto_qscore[j] == human_qscore[j]){
				        bw.write("yes\t");
				        ExactMatch[j] = 1;
				    }
				    else if(auto_qscore[j] == 0 || human_qscore[j] == 0){
				        bw.write("NA\t");
				        ExactMatch[j] = 0;
				    }
				    else{
				        bw.write("no\t");
				        ExactMatch[j] = 0;
				    }
				
				    /* Partial-match */
				    if(auto_qscore[j] == 0 || human_qscore[j] == 0){
				        bw.write("NA\t");
				        PartialMatch[j] = 0;
				    }
				    else if(auto_qscore[j] == human_qscore[j]){
				        bw.write("1\t");
				        PartialMatch[j] = 1;
				    }
				    else if(auto_qscore[j]-human_qscore[j] > 1 || auto_qscore[j]-human_qscore[j] < -1){
				        bw.write("0\t");
				        PartialMatch[j] = 0;
				    }
				    else{ // calculate partial score
				        String[] auto_actual_scores_array = auto_actual_scores.get(category);
				        String[] human_actual_scores_array = human_actual_scores.get(category);
				
				        //check if human_actual_scores_array is all null
				
				        boolean notallnull;
				        notallnull=false;
				
				        for(int gh=0; gh<human_actual_scores_array.length;gh++){
				
				            if(human_actual_scores_array[gh]!=null){
				
				                notallnull=true;
				                break;
				
				            }
				
				        }
				
				       if(!notallnull){ // human_actual_scores_array is all null
				            bw.write("0\t");
				            PartialMatch[j] = 0;
				
				       }
				       else{
				
				
				
				        if(auto_actual_scores_array == null){
				            auto_actual_scores_array = new String[speakers.length];
				        }
				        else if(human_actual_scores_array == null){
				            human_actual_scores_array = new String[speakers.length];
				        }
				        double auto_actual_score = 0.0;
				        double human_actual_score = 0.0;
				        if(auto_actual_scores_array[j] != null && !auto_actual_scores_array[j].equals("")
				           && human_actual_scores_array[j] != null && !human_actual_scores_array[j].equals(""))
				        {
				            auto_actual_score = Double.parseDouble(auto_actual_scores_array[j]);
				            human_actual_score = Double.parseDouble(human_actual_scores_array[j]);
				        }
				
				        if(human_qscore[j] - auto_qscore[j]  == 1){
				
				            int mid_qt_thrs_index = 5 - human_qscore[j];
				            if(auto_qt.length > mid_qt_thrs_index && human_qt.length > mid_qt_thrs_index){
				            double auto_mid_qt_thrs = Double.parseDouble(auto_qt[mid_qt_thrs_index]);
				            double human_mid_qt_thrs = Double.parseDouble(human_qt[mid_qt_thrs_index]);
				            double p = 0.0;
				            double np = 0.0;
				            if(mid_qt_thrs_index != 0){
				            if(mid_qt_thrs_index != 3){
				                p = auto_mid_qt_thrs - auto_actual_score;
				                np = auto_actual_score - Double.parseDouble(auto_qt[mid_qt_thrs_index-1]);
				            }
				            else{
				                p = human_actual_score - human_mid_qt_thrs;
				                np = Double.parseDouble(human_qt[mid_qt_thrs_index-1]) - human_actual_score;
				            }
				            }
				            System.out.println("p: " + p + "np: "+ np);
				            if(p <= np){
				                bw.write("0.5\t");
				                PartialMatch[j] = 0.5;
				            }
				            else{
				                bw.write("0\t");
				                PartialMatch[j] = 0;
				            }
				            }
				        }
				        else if(human_qscore[j] - auto_qscore[j]  == -1){
				            int mid_qt_thrs_index = 5 - auto_qscore[j];
				            if(auto_qt.length > mid_qt_thrs_index && human_qt.length > mid_qt_thrs_index){
				            double auto_mid_qt_thrs = Double.parseDouble(auto_qt[mid_qt_thrs_index]);
				            double human_mid_qt_thrs = Double.parseDouble(human_qt[mid_qt_thrs_index]);
				            double p = 0.0;
				            double np = 0.0;
				            if(mid_qt_thrs_index != 3){
				                p = human_mid_qt_thrs - human_actual_score;
				                np = human_actual_score - Double.parseDouble(human_qt[mid_qt_thrs_index-1]);
				            }
				            else{
				                p = auto_actual_score - auto_mid_qt_thrs;
				                np = Double.parseDouble(auto_qt[mid_qt_thrs_index-1]) - auto_actual_score;
				            }
				            System.out.println("p: " + p + "np: "+ np);
				            if(p <= np){
				                bw.write("0.5\t");
				                PartialMatch[j] = 0.5;
				            }
				            else{
				                bw.write("0\t");
				                PartialMatch[j] = 0;
				            }
				
				        }
				        }
				    }
				    }
				
				
				
				    bw.write("\n");
				}
				/* Precision */
				bw.write("Precision: \t");
				
				int HRM = 0, HLM = 0, EXM = 0;
				double PM = 0;
				for(int k = 0; k < speakers.length; k++){
				    if(HighestRestMismatch[k] != 0){
				        HRM++;
				    }
				    if(HighLowMismatch[k] != 0){
				        HLM++;
				    }
				    if(ExactMatch[k] != 0){
				        EXM++;
				    }
				    if(PartialMatch[k] != 0){
				        PM += PartialMatch[k];
				    }
				}
				System.err.println("counter = " + counter);
				//System.err.println("HRM = " + HRM);
				double HRM_precision = (double)HRM/(double)counter;
				bw.write(HRM_precision + "(Highest/Rest/Mismatch)\t");
				double HLM_precision = (double)HLM/(double)counter;
				bw.write(HLM_precision + "(High/Low/Mismatch)\t");
				double EXM_precision = (double)EXM/(double)counter;
				bw.write(EXM_precision + "(Exact-match)\t");
				double PM_precision = (double)PM/(double)counter;
				bw.write(PM_precision + "(Partial-match)\t");
				
				bw.write("\n");

		        } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	public void matchEval_tpcdis(BufferedWriter bw, int[] auto_qscore,
			 int[] human_qscore,
			 String[] auto_qt,
			 String[] human_qt,
		     String[] speakers,
		     String category,
//		     double[] HighestRestMismatch,
//		     double[] HighLowMismatch,
//		     double[] ExactMatch,
//		     double[] PartialMatch,
		     HashMap<String, String[]> human_actual_scores,
		     HashMap<String, String[]> auto_actual_scores,
		     int counter
		     ){
	
				try {
					 double[] HighestRestMismatch = new double[speakers.length];
				        double[] HighLowMismatch = new double[speakers.length];
				        double[] ExactMatch = new double[speakers.length];
				        double[] PartialMatch = new double[speakers.length];
					
					bw.write("Speaker \t Auto_annotated \t Human_annotated \t Highest/Rest/Mismatch \t " +
					"High/Low/Mismatch \t Exact-match \t Partial-match \n");
					
				for(int j = 0; j < speakers.length; j++){
				if(auto_qscore == null || human_qscore == null){
				    continue;
				}
				if(auto_qscore[j] == 0 && human_qscore[j] == 0){
				    continue;
				}
				
				counter++;
				
				String speaker = speakers[j];
				bw.write(speaker + "\t");
				if(human_qscore == null){
				    human_qscore = new int[speakers.length];
				}
				if(auto_qscore == null){
				    auto_qscore = new int[speakers.length];
				}
				if(auto_qscore[j] != 0){
				    bw.write(auto_qscore[j] + "\t");
				}
				else{
				    bw.write("NA\t");
				}
				if(human_qscore[j] != 0){
				    bw.write(human_qscore[j] + "\t");
				}
				else{
				    bw.write("NA\t");
				}
				/* Highest/Rest/Mismatch */
				if(auto_qscore[j] == 5 && human_qscore[j] == 5){
				    bw.write("Highest\t");
				    HighestRestMismatch[j] = 1;
				}
				else if(auto_qscore[j] == 5 || human_qscore[j] == 5){
				    bw.write("Mismatch\t");
				    HighestRestMismatch[j] = 0;
				}
				else if(auto_qscore[j] == 0 || human_qscore[j] == 0){
				    bw.write("NA\t");
				    HighestRestMismatch[j] = 0;
				}
				else{
				    bw.write("Rest\t");
				    HighestRestMismatch[j] = 0.5;
				}
				
				/* High/Low/Mismatch */
				if(auto_qscore[j] >= 4 && human_qscore[j] >=4){
				    bw.write("High\t");
				    HighLowMismatch[j] = 1;
				}
				else if(auto_qscore[j] == 0 || human_qscore[j] == 0){
				    bw.write("NA\t");
				    HighLowMismatch[j] = 0;
				}
				else if(auto_qscore[j] < 4 && human_qscore[j] < 4){
				    bw.write("Low\t");
				    HighLowMismatch[j] = 0.5;
				}
				else{
				    bw.write("Mismatch\t");
				    HighLowMismatch[j] = 0;
				}
				
				/* Exact-match */
				if(auto_qscore[j] == human_qscore[j]){
				    bw.write("yes\t");
				    ExactMatch[j] = 1;
				}
				else if(auto_qscore[j] == 0 || human_qscore[j] == 0){
				    bw.write("NA\t");
				    ExactMatch[j] = 0;
				}
				else{
				    bw.write("no\t");
				    ExactMatch[j] = 0;
				}
				
				/* Partial-match */
				if(auto_qscore[j] == 0 || human_qscore[j] == 0){
				    bw.write("NA\t");
				    PartialMatch[j] = 0;
				}
				else if(auto_qscore[j] == human_qscore[j]){
				    bw.write("1\t");
				    PartialMatch[j] = 1;
				}
				else if(auto_qscore[j]-human_qscore[j] > 1 || auto_qscore[j]-human_qscore[j] < -1){
				    bw.write("0\t");
				    PartialMatch[j] = 0;
				}
				else{ // calculate partial score
				    String[] auto_actual_scores_array = auto_actual_scores.get(category);
				    String[] human_actual_scores_array = human_actual_scores.get(category);
				
				    //check if human_actual_scores_array is all null
				
				    boolean notallnull;
				    notallnull=false;
				
				    for(int gh=0; gh<human_actual_scores_array.length;gh++){
				
				        if(human_actual_scores_array[gh]!=null){
				
				            notallnull=true;
				            break;
				
				        }
				
				    }
				
				   if(!notallnull){ // human_actual_scores_array is all null
				        bw.write("0\t");
				        PartialMatch[j] = 0;
				
				   }
				   else{
				
				
				    if(auto_actual_scores_array == null){
				        auto_actual_scores_array = new String[speakers.length];
				    }
				    if(human_actual_scores_array == null){
				        human_actual_scores_array = new String[speakers.length];
				    }
				    double auto_actual_score = 0.0;
				    double human_actual_score = 0.0;
				    if(auto_actual_scores_array[j] != null && !auto_actual_scores_array[j].equals("")
				       && human_actual_scores_array[j] != null && !human_actual_scores_array[j].equals(""))
				    {
				        auto_actual_score = Double.parseDouble(auto_actual_scores_array[j]);
				        human_actual_score = Double.parseDouble(human_actual_scores_array[j]);
				    }
				
				    if(human_qscore[j] - auto_qscore[j]  == 1){
				
				        int mid_qt_thrs_index = 5 - human_qscore[j];
				        if(auto_qt.length > mid_qt_thrs_index && human_qt.length > mid_qt_thrs_index){
				        double auto_mid_qt_thrs = Double.parseDouble(auto_qt[mid_qt_thrs_index]);
				        double human_mid_qt_thrs = Double.parseDouble(human_qt[mid_qt_thrs_index]);
				        double p = 0.0;
				        double np = 0.0;
				        if(mid_qt_thrs_index != 3){
				            p = auto_mid_qt_thrs - auto_actual_score;
				            np = auto_actual_score - Double.parseDouble(auto_qt[mid_qt_thrs_index+1]);
				        }
				        else{
				            p = human_actual_score - human_mid_qt_thrs;
				            np = Double.parseDouble(human_qt[mid_qt_thrs_index-1]) - human_actual_score;
				        }
				        System.out.println("p: " + p + "np: "+ np);
				        if(p <= np){
				            bw.write("0.5\t");
				            PartialMatch[j] = 0.5;
				        }
				        else{
				            bw.write("0\t");
				            PartialMatch[j] = 0;
				        }
				        }
				    }
				    else if(human_qscore[j] - auto_qscore[j]  == -1){
				        int mid_qt_thrs_index = 5 - auto_qscore[j];
				        if(auto_qt.length > mid_qt_thrs_index && human_qt.length > mid_qt_thrs_index){
				        double auto_mid_qt_thrs = Double.parseDouble(auto_qt[mid_qt_thrs_index]);
				        double human_mid_qt_thrs = Double.parseDouble(human_qt[mid_qt_thrs_index]);
				        double p = 0.0;
				        double np = 0.0;
				        if(mid_qt_thrs_index != 3){
				            p = human_mid_qt_thrs - human_actual_score;
				            np = human_actual_score - Double.parseDouble(human_qt[mid_qt_thrs_index+1]);
				        }
				        else{
				            p = auto_actual_score - auto_mid_qt_thrs;
				            np = Double.parseDouble(auto_qt[mid_qt_thrs_index-1]) - auto_actual_score;
				        }
				        System.out.println("p: " + p + "np: "+ np);
				        if(p <= np){
				            bw.write("0.5\t");
				            PartialMatch[j] = 0.5;
				        }
				        else{
				            bw.write("0\t");
				            PartialMatch[j] = 0;
				        }
				
				    }
				    }
				}
				}
				
				
				bw.write("\n");
				}
				/* Precision */
				bw.write("Precision: \t");
				
				int HRM = 0, HLM = 0, EXM = 0;
				double PM = 0;
				for(int k = 0; k < speakers.length; k++){
				if(HighestRestMismatch[k] != 0){
				    HRM++;
				}
				if(HighLowMismatch[k] != 0){
				    HLM++;
				}
				if(ExactMatch[k] != 0){
				    EXM++;
				}
				if(PartialMatch[k] != 0){
				    PM += PartialMatch[k];
				}
				}
				double HRM_precision = (double)HRM/(double)counter;
				bw.write(HRM_precision + "(Highest/Rest/Mismatch)\t");
				double HLM_precision = (double)HLM/(double)counter;
				bw.write(HLM_precision + "(High/Low/Mismatch)\t");
				double EXM_precision = (double)EXM/(double)counter;
				bw.write(EXM_precision + "(Exact-match)\t");
				System.err.println(PM + " " + counter);
				double PM_precision = (double)PM/(double)counter;
				bw.write(PM_precision + "(Partial-match)\t");
				
				System.err.println("GOT++++++++++++++++++++++++++++*************************************************************");
				bw.write("\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
}
	
	public void matchEval_taskCtl(BufferedWriter bw, int[] auto_qscore,
			 int[] human_qscore,
			 String[] auto_qt,
			 String[] human_qt,
		     String[] speakers,
		     String category,
//		     double[] HighestRestMismatch,
//		     double[] HighLowMismatch,
//		     double[] ExactMatch,
//		     double[] PartialMatch,
		     HashMap<String, String[]> human_actual_scores,
		     HashMap<String, String[]> auto_actual_scores,
		     int counter
		     ){
		
		try {
			
			double[] HighestRestMismatch = new double[speakers.length];
	        double[] HighLowMismatch = new double[speakers.length];
	        double[] ExactMatch = new double[speakers.length];
	        double[] PartialMatch = new double[speakers.length];
			
			bw.write("Speaker \t Auto_annotated \t Human_annotated \t Highest/Rest/Mismatch \t " +
				 "High/Low/Mismatch \t Exact-match \t Partial-match \n");
			
		for(int j = 0; j < speakers.length; j++){

            if(auto_qscore == null || human_qscore == null){
                continue;
            }
            if(auto_qscore[j] == 0 && human_qscore[j] == 0){
                continue;
            }

            counter++;

            String speaker = speakers[j];
            
//            bw.write(speaker + "\t");
//		    if(human_qscore == null){
//		        human_qscore = new int[speakers.length];
//		    }
//		    if(auto_qscore == null){
//		        auto_qscore = new int[speakers.length];
//		    }
//		    if(auto_qscore[j] != 0){
//		        bw.write(auto_qscore[j] + "\t");
//		    }
//		    else{
//		        bw.write("NA\t");
//		    }
//		    if(human_qscore[j] != 0){
//		        bw.write(human_qscore[j] + "\t");
//		    }
//		    else{
//		        bw.write("NA\t");
//		    }
				bw.write(speaker + "\t");
			
            bw.write(auto_qscore[j] + "\t");
            bw.write(human_qscore[j] + "\t");
            /* Highest/Rest/Mismatch */
            if(auto_qscore[j] == 5 && human_qscore[j] == 5){
                bw.write("Highest\t");
                HighestRestMismatch[j] = 1;
            }
            else if(auto_qscore[j] == 5 || human_qscore[j] == 5){
                bw.write("Mismatch\t");
                HighestRestMismatch[j] = 0;
            }
            else{
                bw.write("Rest\t");
                HighestRestMismatch[j] = 0.5;
            }

            /* High/Low/Mismatch */
            if(auto_qscore[j] >= 4 && human_qscore[j] >=4){
                bw.write("High\t");
                HighLowMismatch[j] = 1;
            }
            else if(auto_qscore[j] < 4 && human_qscore[j] < 4){
                bw.write("Low\t");
                HighLowMismatch[j] = 0.5;
            }
            else{
                bw.write("Mismatch\t");
                HighLowMismatch[j] = 0;
            }

            /* Exact-match */
            if(auto_qscore[j] == human_qscore[j]){
                bw.write("yes\t");
                ExactMatch[j] = 1;
            }
            else{
                bw.write("no\t");
                ExactMatch[j] = 0;
            }

            /* Partial-match */
            if(auto_qscore[j] == human_qscore[j]){
                bw.write("1\t");
                PartialMatch[j] = 1;
            }
            else if(auto_qscore[j]-human_qscore[j] > 1 || auto_qscore[j]-human_qscore[j] < -1){
                bw.write("0\t");
                PartialMatch[j] = 0;
            }
            else{ // calculate partial score
                String[] auto_actual_scores_array = auto_actual_scores.get(category);
                String[] human_actual_scores_array = human_actual_scores.get(category);

                System.err.println(Arrays.toString(auto_actual_scores_array));
                System.err.println(Arrays.toString(human_actual_scores_array));

                //check if human_actual_scores_array is all null

                boolean notallnull;
                notallnull=false;

                for(int gh=0; gh<human_actual_scores_array.length;gh++){

                    if(human_actual_scores_array[gh]!=null){

                        notallnull=true;
                        break;

                    }

                }

               if(!notallnull){ // human_actual_scores_array is all null
                    bw.write("0\t");
                    PartialMatch[j] = 0;

               }
               else{



                double auto_actual_score = 0.0;
                double human_actual_score = 0.0;
                if(auto_actual_scores_array[j] != null && !auto_actual_scores_array[j].equals("")
                   && human_actual_scores_array[j] != null && !human_actual_scores_array[j].equals(""))
                {
                    auto_actual_score = Double.parseDouble(auto_actual_scores_array[j]);
                    human_actual_score = Double.parseDouble(human_actual_scores_array[j]);
                }

                if(human_qscore[j] - auto_qscore[j]  == 1){

                    int mid_qt_thrs_index = 5 - human_qscore[j];
                    if(auto_qt.length > mid_qt_thrs_index && human_qt.length > mid_qt_thrs_index){
                        double auto_mid_qt_thrs = Double.parseDouble(auto_qt[mid_qt_thrs_index]);
                        double human_mid_qt_thrs = Double.parseDouble(human_qt[mid_qt_thrs_index]);
                        double p = 0.0;
                        double np = 0.0;
                        if (mid_qt_thrs_index != 4){
                            
                        
                        if(mid_qt_thrs_index != 3){
                            p = auto_mid_qt_thrs - auto_actual_score;
                            np = auto_actual_score - Double.parseDouble(auto_qt[mid_qt_thrs_index+1]);
                        }
                        else{
                            p = human_actual_score - human_mid_qt_thrs;
                            np = Double.parseDouble(human_qt[mid_qt_thrs_index-1]) - human_actual_score;
                        }
                    }
                        System.out.println("p: " + p + "np: "+ np);
                        if(p <= np){
                            bw.write("0.5\t");
                            PartialMatch[j] = 0.5;
                        }
                        else{
                            bw.write("0\t");
                            PartialMatch[j] = 0;
                        }
                    }
                }
                else if(human_qscore[j] - auto_qscore[j]  == -1){

                    System.err.println(auto_qscore[j]);

                    int mid_qt_thrs_index = 5 - auto_qscore[j];

                    System.err.println(mid_qt_thrs_index);

                    if(auto_qt.length > mid_qt_thrs_index && human_qt.length > mid_qt_thrs_index){
                        double auto_mid_qt_thrs = Double.parseDouble(auto_qt[mid_qt_thrs_index]);
                        double human_mid_qt_thrs = Double.parseDouble(human_qt[mid_qt_thrs_index]);
                        double p = 0.0;
                        double np = 0.0;
                        if (mid_qt_thrs_index != 4){
                        
                        if(mid_qt_thrs_index != 3){

                            //if(human_qt[mid_qt_thrs_index+1]

                            p = human_mid_qt_thrs - human_actual_score;

                            System.err.println(Arrays.toString(human_qt));

                            np = human_actual_score - Double.parseDouble(human_qt[mid_qt_thrs_index+1]);
                        }
                        else{
                            System.err.println("%%%%%%%%%%%%%%%%%");
                            p = auto_actual_score - auto_mid_qt_thrs;
                            np = Double.parseDouble(auto_qt[mid_qt_thrs_index-1]) - auto_actual_score;
                        }
                    }
                        System.out.println("p: " + p + "np: "+ np);
                        if(p <= np){
                            bw.write("0.5\t");
                            PartialMatch[j] = 0.5;
                        }
                        else{
                            bw.write("0\t");
                            PartialMatch[j] = 0;
                        }
                    }

                }
            }
            }


            bw.write("\n");
        }
        /* Precision */
        bw.write("Precision: \t");

        int HRM = 0, HLM = 0, EXM = 0;
        double PM = 0;
        for(int k = 0; k < speakers.length; k++){
            if(HighestRestMismatch[k] != 0){
                HRM++;
            }
            if(HighLowMismatch[k] != 0){
                HLM++;
            }
            if(ExactMatch[k] != 0){
                EXM++;
            }
            if(PartialMatch[k] != 0){
                PM += PartialMatch[k];
            }
        }
        double HRM_precision = (double)HRM/(double)counter;
        bw.write(HRM_precision + "(Highest/Rest/Mismatch)\t");
        double HLM_precision = (double)HLM/(double)counter;
        bw.write(HLM_precision + "(High/Low/Mismatch)\t");
        double EXM_precision = (double)EXM/(double)counter;
        bw.write(EXM_precision + "(Exact-match)\t");
        double PM_precision = (double)PM/(double)counter;
        bw.write(PM_precision + "(Partial-match)\t");

        bw.write("\n");
        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
