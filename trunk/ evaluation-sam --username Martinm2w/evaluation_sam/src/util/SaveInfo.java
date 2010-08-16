package util;

import java.util.HashMap;

public class SaveInfo {
	 
	public String[] saveSpeakers(HashMap<String, String> speakers_map){

		String[] temp_speakers = null;
		Object[] speakerarray=speakers_map.keySet().toArray();
		    
		temp_speakers=new String[speakerarray.length];
		    
		for(int ei=0;ei<speakerarray.length;ei++){
		
		        temp_speakers[ei]=(String)speakerarray[ei];
		
//		        System.err.println(temp_speakers[ei]);
		
		    }
		return temp_speakers;    
	}

	public String[] saveCategories(HashMap<String, String> categories_map){
		
		String[] temp_categories = null;
		Object[] categoriesarray=categories_map.keySet().toArray();
		
		temp_categories=new String[categoriesarray.length];
		
		for(int ei=0;ei<categoriesarray.length;ei++){
		
		    	temp_categories[ei]=(String)categoriesarray[ei];
		
//		        System.err.println(temp_categories[ei]);
		
		    }
		return temp_categories;
	}
	
	public HashMap<String, int[]> saveHumanQtScores(HashMap<String, String[]> human_scores, String[] speakers){
		HashMap<String, int[]> temp_human_quintile_scores = new HashMap<String, int[]>();
		
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
//                        System.out.println("quintile score for speaker " + speakers[k] + " is " + quintile_score_array[k]);

                    }
                }
            }
            temp_human_quintile_scores.put(category[i].toString(), quintile_score_array);
        }
        return temp_human_quintile_scores;
    }
	
	public HashMap<String, int[]> saveAutoQtScores(HashMap<String, String[]> auto_scores, String[] speakers){
		HashMap<String, int[]> temp_auto_quintile_scores = new HashMap<String, int[]>();
				
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
//                        System.err.println("quintile score for speaker " + speakers[k] + " is " + quintile_score_array[k]);
                    }
                }
            }
            temp_auto_quintile_scores.put(category[i].toString(), quintile_score_array);
        }
        return temp_auto_quintile_scores;
    }
	
	
}
