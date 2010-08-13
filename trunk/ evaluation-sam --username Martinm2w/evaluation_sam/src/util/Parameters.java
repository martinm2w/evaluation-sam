package util;

import java.io.BufferedReader;
import java.util.HashMap;

public class Parameters {

	
	private String[] categories;
	private String[] speakers;

	private HashMap<String, String> categories_map = new HashMap<String, String>();
	private HashMap<String, String> speakers_map = new HashMap<String, String>();

	private HashMap<String, String[]> human_qt_thrs = new HashMap<String, String[]>(); // key: category; value: qt_thrs array
	private HashMap<String, String[]> auto_qt_thrs = new HashMap<String, String[]>(); // key: category; value: qt_thrs array
	private HashMap<String, String[]> human_scores = new HashMap<String, String[]>(); // key: category; value: score, order by speakers
	private HashMap<String, String[]> auto_scores = new HashMap<String, String[]>(); // key: category; value: score, order by speakers
	private HashMap<String, int[]> human_quintile_scores = new HashMap<String, int[]>(); // key: category; value: quintile scores, order by speakers
	private HashMap<String, int[]> auto_quintile_scores = new HashMap<String, int[]>(); // key: category; value: quintile scores, order by speakers 
	private HashMap<String, String[]> human_actual_scores = new HashMap<String, String[]>(); // key: category; value: actual scores, order by speakers
	private HashMap<String, String[]> auto_actual_scores = new HashMap<String, String[]>(); // key: category; value: actual scores, order by speakers

    private boolean readerOpened=false;
    private BufferedReader eia_br;
    
    
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
    
    /*===========================getters and setters==============================*/
    
	public String[] getCategories() {
		return categories;
	}
	public void setCategories(String[] categories) {
		this.categories = categories;
	}
	public String[] getSpeakers() {
		return speakers;
	}
	public void setSpeakers(String[] speakers) {
		this.speakers = speakers;
	}
	public HashMap<String, String> getCategories_map() {
		return categories_map;
	}
	public void setCategories_map(HashMap<String, String> categories_map) {
		this.categories_map = categories_map;
	}
	public HashMap<String, String> getSpeakers_map() {
		return speakers_map;
	}
	public void setSpeakers_map(HashMap<String, String> speakers_map) {
		this.speakers_map = speakers_map;
	}
	public HashMap<String, String[]> getHuman_qt_thrs() {
		return human_qt_thrs;
	}
	public void setHuman_qt_thrs(HashMap<String, String[]> human_qt_thrs) {
		this.human_qt_thrs = human_qt_thrs;
	}
	public HashMap<String, String[]> getAuto_qt_thrs() {
		return auto_qt_thrs;
	}
	public void setAuto_qt_thrs(HashMap<String, String[]> auto_qt_thrs) {
		this.auto_qt_thrs = auto_qt_thrs;
	}
	public HashMap<String, String[]> getHuman_scores() {
		return human_scores;
	}
	public void setHuman_scores(HashMap<String, String[]> human_scores) {
		this.human_scores = human_scores;
	}
	public HashMap<String, String[]> getAuto_scores() {
		return auto_scores;
	}
	public void setAuto_scores(HashMap<String, String[]> auto_scores) {
		this.auto_scores = auto_scores;
	}
	public HashMap<String, int[]> getHuman_quintile_scores() {
		return human_quintile_scores;
	}
	public void setHuman_quintile_scores(
			HashMap<String, int[]> human_quintile_scores) {
		this.human_quintile_scores = human_quintile_scores;
	}
	public HashMap<String, int[]> getAuto_quintile_scores() {
		return auto_quintile_scores;
	}
	public void setAuto_quintile_scores(HashMap<String, int[]> auto_quintile_scores) {
		this.auto_quintile_scores = auto_quintile_scores;
	}
	public HashMap<String, String[]> getHuman_actual_scores() {
		return human_actual_scores;
	}
	public void setHuman_actual_scores(HashMap<String, String[]> human_actual_scores) {
		this.human_actual_scores = human_actual_scores;
	}
	public HashMap<String, String[]> getAuto_actual_scores() {
		return auto_actual_scores;
	}
	public void setAuto_actual_scores(HashMap<String, String[]> auto_actual_scores) {
		this.auto_actual_scores = auto_actual_scores;
	}
	public boolean isReaderOpened() {
		return readerOpened;
	}
	public void setReaderOpened(boolean readerOpened) {
		this.readerOpened = readerOpened;
	}
	public BufferedReader getEia_br() {
		return eia_br;
	}
	public void setEia_br(BufferedReader eia_br) {
		this.eia_br = eia_br;
	}
}