package util;

import java.util.HashMap;

public class SaveInfo {
	 
	public String[] saveSpeakers(HashMap<String, String> speakers_map){

		String[] temp_speakers = null;
		Object[] speakerarray=speakers_map.keySet().toArray();
		    
		temp_speakers=new String[speakerarray.length];
		    
		for(int ei=0;ei<speakerarray.length;ei++){
		
		        temp_speakers[ei]=(String)speakerarray[ei];
		
		        System.err.println(temp_speakers[ei]);
		
		    }
		return temp_speakers;    
	}

	public String[] saveCategories(HashMap<String, String> categories_map){
		
		String[] temp_categories = null;
		Object[] categoriesarray=categories_map.keySet().toArray();
		
		temp_categories=new String[categoriesarray.length];
		
		for(int ei=0;ei<categoriesarray.length;ei++){
		
		    	temp_categories[ei]=(String)categoriesarray[ei];
		
		        System.err.println(temp_categories[ei]);
		
		    }
		return temp_categories;
	}
}
