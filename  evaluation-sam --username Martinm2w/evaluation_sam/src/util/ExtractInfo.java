package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExtractInfo {

	
	
	public ArrayList<ArrayList<String>> getHumanFileList(String human_annotation){
		
		ArrayList<ArrayList<String>> temp_hfList = new ArrayList<ArrayList<String>>();
		String tempStr = "";
				
		try {
			BufferedReader br = new BufferedReader(new FileReader(human_annotation));
			
			while((tempStr = br.readLine()) != null){
				
				if (tempStr.contains("CANNOT")){
					ArrayList<String> tempList = new ArrayList<String>();
					tempList.add(tempStr);
					temp_hfList.add(tempList);
					continue;
				}
				
				if (tempStr.contains("COMPUTING") || tempStr.contains("COMPUTIGN")){
					
					ArrayList<String> tempList = new ArrayList<String>();
					tempList.add(tempStr);
					tempStr=br.readLine();
					
					while(tempStr!=null && (tempStr.contains("The quintile score") || tempStr.contains("qt_thrs:"))){ 

						tempList.add(tempStr);
                        br.mark(1000);
                        tempStr=br.readLine();
                    
                   }
					
					br.reset();
				                    
					temp_hfList.add(tempList);
					
				}
			}
				
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return temp_hfList;
	}

	public ArrayList<ArrayList<String>> getAutoFileList(String auto_annotation){
		
		ArrayList<ArrayList<String>> temp_afList = new ArrayList<ArrayList<String>>();
		String tempStr = "";
				
		try {
			BufferedReader br = new BufferedReader(new FileReader(auto_annotation));
			
			while((tempStr = br.readLine()) != null){
				
				if (tempStr.contains("CANNOT")){
					ArrayList<String> tempList = new ArrayList<String>();
					tempList.add(tempStr);
					temp_afList.add(tempList);
					continue;
				}
				
				if (tempStr.contains("COMPUTING") || tempStr.contains("COMPUTIGN")){
					
					ArrayList<String> tempList = new ArrayList<String>();
					tempList.add(tempStr);
					tempStr=br.readLine();
					
					while(tempStr!=null && (tempStr.contains("The quintile score") || tempStr.contains("qt_thrs:"))){ 

						tempList.add(tempStr);
                        br.mark(1000);
                        tempStr=br.readLine();
                    
                   }
					
					br.reset();
				                    
					temp_afList.add(tempList);
					
				}
			}
				
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return temp_afList;
	}
	
	public void evenTwoLists(ArrayList<ArrayList<String>> hfList, ArrayList<ArrayList<String>> afList){
		 
		ArrayList<Integer> delNum = new ArrayList<Integer>();
		
		for (int i = 0; i < hfList.size(); i++){
			for(int j = 0; j < hfList.get(i).size(); j++){
				
				if (hfList.get(i).get(j).contains("CANNOT")){
					delNum.add(i);
				}
			}
		}
		if (delNum != null){
				for (int i = 0; i < delNum.size(); i++){
					
					hfList.remove(i);
					afList.remove(i);
					
				}
		}
		delNum.clear();
		
		for (int i = 0; i < afList.size(); i++){
			for(int j = 0; j < afList.get(i).size(); j++){
				
				if (afList.get(i).get(j).contains("CANNOT")){
					delNum.add(i);
				}
			}
		}
		
		if (delNum != null){
			for (int i = 0; i < delNum.size(); i++){
						
						hfList.remove(i);
						afList.remove(i);
			}
		}
		delNum.clear();
	}
	
	public HashMap<String, String> extractSpeakers(String human_annotation){//extract names
			
		HashMap<String, String> temp_speakers_map = new HashMap<String, String>();
        
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(human_annotation));
		
        String tempstr;

        while((tempstr=br.readLine())!=null){
            if(tempstr.startsWith("The quintile score ")){//get speaker names

                String name;

                name=tempstr.split(" ")[4].toLowerCase();

                if( (temp_speakers_map.get(name)==null)){ //if speaker not yet in list

                	temp_speakers_map.put(name, name);

                }
             }
        }
		
        br.close();
        
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return temp_speakers_map;
		
	}
	
	public HashMap<String, String> extractCategories(String human_annotation){
		
		HashMap<String, String> temp_categories_map = new HashMap<String, String>();
		
		try {
								
				BufferedReader br = new BufferedReader(new FileReader(human_annotation));
				
		        String tempstr;
		
		        while((tempstr=br.readLine())!=null){
		        	
					/*merged files*/
			        if(tempstr.contains("COMPUTING MERGED")){
			        	
			        	if(temp_categories_map.get("merged") == null){
			        		
			        		temp_categories_map.put("merged", "merged");
			        		
			        	}
			        }
			        /*normal files*/
			        else if(tempstr.contains("COMPUTIGN FOR")){
			
			            if( temp_categories_map.get(tempstr.split(":")[1].toLowerCase().split(" ")[0])==null){ //if category not yet in list
			
			                temp_categories_map.put(tempstr.split(":")[1].toLowerCase().split(" ")[0], tempstr.split(":")[1].toLowerCase().split(" ")[0]);
			
			            }
			        }
			   }
		        
		        br.close();
		        
			} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
			} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
			}
			
			return temp_categories_map;
			
	}
	
	public HashMap<String, String[]> extractHumanQtThrs(String[] categories, ArrayList<ArrayList<String>> hfList, int index){
	        
		HashMap<String, String[]> temp_human_qt_thrs = new HashMap<String, String[]>();
		String firstLine = hfList.get(index).get(0);
		String qt_thrs = hfList.get(index).get(1); 
		
		for(int i = 0; i < categories.length; i++){
			
			if(firstLine.contains(categories[i])){
		
		    String[] qt_thrs1 = qt_thrs.split("\\s+");
	        String[] qt_thrs_array = new String[qt_thrs1.length-1];
	        for(int j = 0; j < qt_thrs_array.length; j++){
	            qt_thrs_array[j] = qt_thrs1[j+1];
	        }
			
			
	        /* save qt_thrs */
	        temp_human_qt_thrs.put(categories[i], qt_thrs_array);
			}
		}
		return temp_human_qt_thrs;
	}
		

	public HashMap<String, String[]> extractHumanScore(String[] categories, String[] speakers, ArrayList<ArrayList<String>> hfList, int index){
		
		HashMap<String, String[]> temp_human_scores = new HashMap<String, String[]>();
		
		String speaker = "";
        String[] score_array = new String[speakers.length];

        for(int i = 0; i < categories.length; i++){
            for(int j = 2; j < hfList.get(index).size(); j ++){
            	
	        	speaker = hfList.get(index).get(j);
	            for(int k = 0; k < speakers.length; k++){
	                if(speaker.toLowerCase().contains(speakers[k] + " ")){
	                     score_array[k] = speaker.toLowerCase();
	                }
	            }
	        }
            temp_human_scores.put(categories[i], score_array);
        }
              
        return temp_human_scores;
    }
	
	


	
	
	
	
	
	
	
	

}