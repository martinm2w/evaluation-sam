package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ParseResult {

	public static void main(String[] args){
		
		ParseResult pr = new ParseResult();
		pr.parseMatchResult("D:/m2w cs/evaluation-m2w/src/output_files/topic_control_6_Lauren_annotated_with_merge_3_mr", 
					   "D:/m2w cs/evaluation-m2w/src/output_files/topic_control_6_Lauren_annotated_with_merge_3_mr_PR");
//		
//		pr.parseCompareResult("D:/m2w cs/evaluation-m2w/src/output_files/topic_control_6_Lauren_annotated_with_merge_3_ce", 
//				   "D:/m2w cs/evaluation-m2w/src/output_files/topic_control_6_Lauren_annotated_with_merge_3_PR");
	}
	
	public void parseMatchResult(String result_file, String parse_output){
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(result_file));
			PrintWriter pw = new PrintWriter(parse_output);
			
			String tempStr = null;
			
			while ((tempStr = br.readLine()) != null  ){
				
				if(!(tempStr.contains("---") ||
					 tempStr.contains("Human annotated file:") ||
					 tempStr.contains("Auto annotated file:") ||
					 tempStr.contains("Precision: ")
//					 tempStr.contains("*******") ||
//					 tempStr.contains("|||")
					 )){
				continue;
					}
				
				pw.println(tempStr);	
			}
			br.close();
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	public void parseCompareResult(String result_file, String parse_output){
		try {
			BufferedReader br = new BufferedReader(new FileReader(result_file));
			PrintWriter pw = new PrintWriter(parse_output);
			
			String tempStr = null;
			
			while ((tempStr = br.readLine()) != null  ){
				
				if(!(tempStr.contains("---") ||
					 tempStr.contains("Human annotated file:") ||
					 tempStr.contains("Auto annotated file:") ||
					 tempStr.contains("::")
					 )){
				continue;
					}
				
				pw.println(tempStr);	
			}
			br.close();
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
}
