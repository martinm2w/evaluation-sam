package evaluation;


import java.io.*;
import util.*;

//
/**
 *
 * @author m2w
 * 
 */
public class disagreement_sam {

	static ExtractAndBuild enb = new ExtractAndBuild();
	
    public static void main(String[] args){

        String human_annotation = "D:/m2w cs/evaluation_sam/input_files/Oct11_Disagreement_Annotated" ;
        String auto_annotation = "D:/m2w cs/evaluation_sam/input_files/Oct11_Disagreement" ;

        String evaluation_file = "D:/m2w cs/evaluation_sam/output_files/Oct11_Disagreement_Annotated_result_ce";

        /*extract info*/
        enb.extractSpeakersAndCategoriesToMap(human_annotation);
       
        System.err.println("Speakers : " + enb.getSpeakers());
        System.err.println("categories : " + enb.getCategories());
        
        
        /*initializing*/
        enb.init();

        enb.extract_info_human(evaluation_file, human_annotation, auto_annotation);
        
		try {
			enb.getEia_br().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    }

}



