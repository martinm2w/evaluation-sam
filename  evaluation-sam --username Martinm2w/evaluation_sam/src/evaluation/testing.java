package evaluation;

import java.io.BufferedReader;
import java.util.HashMap;

import util.ExtractAndBuild;
import util.Filenames;

public class testing {
	
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
    static Filenames filename = new Filenames();
    static ExtractAndBuild enb = new ExtractAndBuild();
    
	private static boolean readerOpened=false;
	private static BufferedReader eia_br;
	
	
	public static void main(String[] args){
		
		String human_annotation = "D:/m2w cs/evaluation_sam/input_files/Dec05_Disagreement" ;
        String auto_annotation = "D:/m2w cs/evaluation_sam/input_files/Dec05_Disagreement_Annotated" ;

        String evaluation_file = "D:/m2w cs/evaluation-m2w/evaluation_sam/output_files/Dec05_Disagreement_Annotated_result_ce";
		
        enb.extractSpeakersAndCategoriesToMap(human_annotation, auto_annotation);
        
       }
		
}
