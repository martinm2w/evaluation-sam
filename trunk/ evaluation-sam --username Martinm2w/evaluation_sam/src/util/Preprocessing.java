package util;

import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
//import java.io.OutputStreamWriter;
import java.io.PrintWriter;
//import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * 
 * @author m2w
 *
 */

public class Preprocessing {

	public static void main(String[] args) throws IOException{
		
		Preprocessing p = new Preprocessing();
		
		
		
		p.parseTskCtrl(
				"D:/m2w cs/evaluation-m2w/input_log/older/task_control_5_Lauren_annotated_7",
				"D:/m2w cs/evaluation-m2w/input_log/older/task_control_5_automated_7",
				"D:/m2w cs/evaluation-m2w/src/preprocessed/task_control_5_Lauren_annotated_7_pp",
				"D:/m2w cs/evaluation-m2w/src/preprocessed/task_control_5_automated_7_pp");
//		
		
		
		
//		p.parseExpDisForCompare(
//				"D:/m2w cs/evaluation-m2w/input_log/older/expressive_disagreement_Lauren_3",  
//				"D:/m2w cs/evaluation-m2w/input_log/older/expressive_disagreement_auto_3", 
//				"D:/m2w cs/evaluation-m2w/src/preprocessed/expressive_disagreement_Lauren_3_pp_ce",
//				"D:/m2w cs/evaluation-m2w/src/preprocessed/expressive_disagreement_auto_3_pp_ce");
	
	
	
//		p.parseExpDisForMatch(	
//			"D:/m2w cs/evaluation-m2w/input_log/older/expressive_disagreement_Lauren_3",  
//			"D:/m2w cs/evaluation-m2w/input_log/older/expressive_disagreement_auto_3", 
//			"D:/m2w cs/evaluation-m2w/src/preprocessed/expressive_disagreement_Lauren_3_pp_me",
//			"D:/m2w cs/evaluation-m2w/src/preprocessed/expressive_disagreement_auto_3_pp_me");

}
	public void parseExpDisForMatch (String input1, String input2, String input3, String input4) {
		
		String human_file = input1; // "D:/m2w cs/evaldata/input_log/older/expressive_disagreement_lauren_3"
		String auto_file = input2; // "D:/m2w cs/evaldata/input_log/older/expressive_disagreement_auto_3"
		
		String human_preprocessed = input3; //"D:/m2w cs/evaluation-m2w/src/preprocessed/expressive_disagreement_lauren_3.p"
		String auto_preprocessed = input4; //"D:/m2w cs/evaluation-m2w/src/preprocessed/expressive_disagreement_auto_3.p"
		
		ArrayList<ArrayList<String>> HList = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> AList = new ArrayList<ArrayList<String>>();
		
		ArrayList<Integer> delHList = new ArrayList<Integer>();
		ArrayList<Integer> delAList = new ArrayList<Integer>();
		
		
			
		
		
		try {
			BufferedReader hbr = new BufferedReader(new FileReader(human_file));
			BufferedReader abr = new BufferedReader(new FileReader(auto_file));
			
			PrintWriter hpw = new PrintWriter(human_preprocessed);
			PrintWriter apw = new PrintWriter(auto_preprocessed);
			
			String tempStrh = null; 
			String tempStra = null;
			
			/*1.1 save human file into HList*/  // ok
			while((tempStrh = hbr.readLine()) != null ) {
				//System.out.println("tempStrh : " + tempStrh);
				
				if(tempStrh.contains("processing:")){  ///if it's a new file

					ArrayList<String> templist = new ArrayList<String>();
					templist.add(tempStrh);
					
					do{ 

                        hbr.mark(1000);
                        tempStrh=hbr.readLine();
                        templist.add(tempStrh);

                   }while(tempStrh!=null && !tempStrh.contains("%%%%"));
					
                   hbr.reset();
                   
                   HList.add(templist);
					//System.err.println("tempsh is :" + tempStrh);
				}
				
				
				
				
				if(tempStrh.contains("+++++")){  /// if new calculate block
					
					//String tempsh = null;
					ArrayList<String> templist = new ArrayList<String>();
					templist.add(tempStrh);
					
					do{ 

                        hbr.mark(1000);
                        tempStrh=hbr.readLine();
                        templist.add(tempStrh);

                   }while(tempStrh!=null && !tempStrh.contains("$$$"));
					
                  
					

	                     hbr.reset();
	                     HList.add(templist);
				}
			} //while
			
//			/*1.1save human file into HList*/  // ok
//			while((tempStrh = hbr.readLine()) != null ) {
//				//System.out.println("tempStrh : " + tempStrh);
//				
//				if(tempStrh.contains("processing:")){  ///if it's a new file
//
//					ArrayList<String> templist = new ArrayList<String>();
//					templist.add(tempStrh);
//					
//					do{ 
//
//                        hbr.mark(1000);
//                        tempStrh=hbr.readLine();
//                        templist.add(tempStrh);
//
//                   }while(tempStrh!=null && !tempStrh.contains("%%%%"));
//					
//                   hbr.reset();
//                   
//                   HList.add(templist);
//					//System.err.println("tempsh is :" + tempStrh);
//				}
//				
//				
//				
//				
//				if(tempStrh.contains("+++++")){  /// if new calculate block
//					
//					//String tempsh = null;
//					ArrayList<String> templist = new ArrayList<String>();
//					templist.add(tempStrh);
//					
//					while((tempStrh = hbr.readLine()) != null && !tempStrh.contains("$$$")){
//						
//						 hbr.mark(1000);
//	                     templist.add(tempStrh);
//					}
//	                     hbr.reset();
//	                     HList.add(templist);
//				}	
//			} //while
				
			/*1.2save auto file into AList*/ //
			while((tempStra = abr.readLine()) != null ) {
				
				if(tempStra.contains("processing:")){ /// if is new file
					
					//String tempStra = null;
					ArrayList<String> templist = new ArrayList<String>();
					templist.add(tempStra);
					
					do{ 

                        abr.mark(1000);
                        tempStra=abr.readLine();
                        templist.add(tempStra);

                   }while(tempStra!=null && !tempStra.contains("%%%%%%%%"));

                   abr.reset();
                   
                   AList.add(templist);
					
				}
				
				
				
				if(tempStra.contains("+++")){ /// if is a category in a file
					
					//String tempsa = null;
					ArrayList<String> templist = new ArrayList<String>();
					templist.add(tempStra);
					
//					while((tempStra = abr.readLine()) != null && !tempStra.contains("$$$$")){
//						
//						abr.mark(1000);
//						templist.add(tempStra);
//					}
					do{ 

                        abr.mark(1000);
                        tempStra=abr.readLine();
                        templist.add(tempStra);

                   }while(tempStra!=null && ( !tempStra.contains("$$$")));
				

                   abr.reset();
                   
                   AList.add(templist);
					
				}
				
			} //while
				
			
			System.out.println("Hlist size is :"+ HList.size());
			System.out.println("Alist size is :"+ AList.size());
			
			hbr.close();
			abr.close();
			
			/*2.1parse human, delete -1 */ //ok
			for (int i = 0; i < HList.size(); i ++){
				for(int j = 0 ; j < HList.get(i).size() - 1; j++){
					String tempStr = HList.get(i).get(j).toString(); //nullpointer
					
					System.out.println(tempStr);
					
					if (tempStr.contains("-1")){
						delHList.add(i);
						delAList.add(i);
						break;
					}
				}
			}
			
			for(int i = 0; i < delHList.size(); i ++){
				
				System.out.println("delHList :"+ delHList.get(i));
				System.out.println("delAList :"+ delAList.get(i));
				
				int a = delHList.get(i) - i; // calculate  index after size shrink
				int b = delAList.get(i) - i;
				
				HList.remove(a);
				AList.remove(b);
				
			}
			
			delHList.clear();
			delAList.clear();
			
			
			
			/*2.2parse auto, delete -1*/ //ok
			for (int i = 0; i < AList.size(); i ++){
				for(int j = 0 ; j < AList.get(i).size() - 1; j++){
					
					String tempStr = AList.get(i).get(j).toString();
					
					if (tempStr.contains("-1")){
						delAList.add(i);
						delHList.add(i);
						break;
					}
				}
			}
			
			for(int i = 0; i < delAList.size(); i ++){
				
				System.out.println("delHList 2:"+ delHList.get(i));
				System.out.println("delAList 2:"+ delAList.get(i));
				
				int a = delHList.get(i) - i; 
				int b = delAList.get(i) - i;
				
				HList.remove(a);
				AList.remove(b);
			}
			
			delHList.clear();
			delAList.clear();	
				

			System.out.println("Hlist final size : " + HList.size());
			System.out.println("Alist final size : " + AList.size());
			
			
			/*3.write to files*/  //ok
			for (int i = 0; i < HList.size(); i ++) {
				for (int j = 0 ; j < HList.get(i).size(); j++){
					hpw.println(HList.get(i).get(j));
					//System.out.println(HList.get(i).get(j).toString());
				}
			}
			hpw.close();
			
			for (int i = 0; i < AList.size(); i ++) {
				for (int j = 0 ; j < AList.get(i).size(); j++){
					apw.println(AList.get(i).get(j));
					//System.out.println(AList.get(i).get(j).toString());
				}
			}
			apw.close();
			
			
			
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				
				
		}

			
	public void parseTskCtrl (String input1, String input2, String input3, String input4) { // ok
		String human_file = input1; //"D:/m2w cs/evaluation/src/input_files/task_control_5_Lauren_annotated_7";
		String auto_file = input2; //"D:/m2w cs/evaluation/src/input_files/task_control_5_automated_7";
		
		String human_preprocessed = input3; //"D:/m2w cs/evaluation/src/preprocessed/task_control_5_Lauren_annotated_7.p";
		String auto_preprocessed = input4; //"D:/m2w cs/evaluation/src/preprocessed/task_control_5_automated_7.p";
		
		ArrayList<ArrayList<String>> HList = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> AList = new ArrayList<ArrayList<String>>();
		
		ArrayList<Integer> delHList = new ArrayList<Integer>();
		ArrayList<Integer> delAList = new ArrayList<Integer>();
		
		ArrayList<Integer> addList = new ArrayList<Integer>();
		
				
		try {
			
			BufferedReader hbr = new BufferedReader(new FileReader(human_file));
			BufferedReader abr = new BufferedReader(new FileReader(auto_file));
			
			PrintWriter hpw = new PrintWriter(human_preprocessed);
			PrintWriter apw = new PrintWriter(auto_preprocessed);
			
			String tempStrh = null; 
			String tempStra = null;
			
			/*1.1save human file into HList*/  // ok
			while((tempStrh = hbr.readLine()) != null ) {
			//	System.out.println("tempStrh : " + tempStrh);
				
				if(tempStrh.contains("$$$$")){
					String tempsh = null;
					ArrayList<String> templist = new ArrayList<String>();
					templist.add(tempStrh);
					
					do{ 

                        hbr.mark(1000);
                        tempsh=hbr.readLine();
                        templist.add(tempsh);

                   }while(tempsh!=null && !tempsh.contains("total_ac_oc_:"));

                   hbr.reset();
                   
                   HList.add(templist);
					
				}
				
				if(tempStrh.contains("%%%")){
					String tempsh = null;
					ArrayList<String> templist = new ArrayList<String>();
					templist.add(tempStrh);
					
					do{ 

                        hbr.mark(1000);
                        tempsh=hbr.readLine();
                        templist.add(tempsh);

                   }while(tempsh!=null && ( tempsh.contains("The") || tempsh.contains("+++++") || tempsh.contains("calculate") || tempsh.contains("qt_thrs")));

                   hbr.reset();
                   
                   HList.add(templist);
					
				}
				
			} //while
			
			/*1.2save auto file into AList*/ //ok
			while((tempStra = abr.readLine()) != null ) {
				
				if(tempStra.contains("$$$$")){
					
					String tempsa = null;
					ArrayList<String> templist = new ArrayList<String>();
					templist.add(tempStra);
					
					do{ 

                        abr.mark(1000);
                        tempsa=abr.readLine();
                        templist.add(tempsa);

                   }while(tempsa!=null && !tempsa.contains("total_ac_oc_:"));//header sensor changed to total_ac_oc_:

                   abr.reset();
                   
                   AList.add(templist);
					
				}
				
				
				
				if(tempStra.contains("%%%")){
					
					String tempsa = null;
					ArrayList<String> templist = new ArrayList<String>();
					templist.add(tempStra);
					
					do{ 

                        abr.mark(1000);
                        tempsa=abr.readLine();
                        templist.add(tempsa);

                   }while(tempsa!=null && ( tempsa.contains("The") || tempsa.contains("++++") || tempsa.contains("calculate") || tempsa.contains("qt_thrs") ) );

                   abr.reset();
                   
                   AList.add(templist);
					
				}
				
			} //while
			
			System.out.println("Hlist size is :"+ HList.size());
			System.out.println("Alist size is :"+ AList.size());
			
			
			/*2.1parse human, delete -1 */ //ok
			for (int i = 0; i < HList.size(); i ++){
				for(int j = 0 ; j < HList.get(i).size(); j++){
					String tempStr = HList.get(i).get(j).toString();
					if (tempStr.contains("-1")){
						delHList.add(i);
						delAList.add(i);
						break;
					}
				}
			}
			
			for(int i = 0; i < delHList.size(); i ++){
				
				System.out.println("delHList :"+ delHList.get(i));
				System.out.println("delAList :"+ delAList.get(i));
				
				int a = delHList.get(i) - i; // calculate  index after size shrink
				int b = delAList.get(i) - i;
				
				HList.remove(a);
				AList.remove(b);
				
			}
			
			delHList.clear();
			delAList.clear();
			
			
			/*2.1parse auto, add actual score = 0*/ //ok
			for (int i = 0; i < AList.size(); i ++){
				for(int j = 0 ; j < AList.get(i).size(); j++){
					
					String tempStr = AList.get(i).get(j).toString();
					
					if (tempStr.contains("-1")){
						
						AList.get(i).set(j, tempStr + " --- actual score: 0");
						
						continue;	
					}
									
				}
				
			}
			
//			for(int i = 0; i < delAList.size(); i ++){
//				
//				System.out.println("delHList 2:"+ delHList.get(i));
//				System.out.println("delAList 2:"+ delAList.get(i));
//				
//				int a = delHList.get(i) - i; 
//				int b = delAList.get(i) - i;
//				
//				HList.remove(a);
//				AList.remove(b);
//			}
//			
//			delHList.clear();
//			elAList.clear();
			
			
			System.out.println("Hlist final size : " + HList.size());
			System.out.println("Alist final size : " + AList.size());
					
			/*2.3.1 new, add qt_thrs: 0.0 to -1 categories*/
			for (int i = 0; i < AList.size(); i ++){//get category number needs to be added.
				for(int j = 0 ; j < AList.get(i).size(); j++){
			
					String tempStr = AList.get(i).get(j).toString();
					
					if (tempStr.contains("-1")){
					
						addList.add(i);
						break;
					}
					
				}
				
			}
			
			/*2.3.2 adding*/
			for (int i = 0; i < addList.size(); i ++){
				
				int catNum = addList.get(i);//get category number from add list
				
				for(int j = 0 ; j < AList.get(catNum).size(); j++){
					
					String tempStr = AList.get(catNum).get(j).toString(); // get line from category
					
					if (tempStr.contains("-1")){//add qt_thrs
						
						AList.get(catNum).add(j, "qt_thrs:  0.0 0.0 0.0 0.0 0.0"); // 不是 j - 1 , 是 j, 插入到有-1 的那一行
						break;
						
					}
										
				}
			}
					
			/*3.write to files*/  //ok
			for (int i = 0; i < HList.size(); i ++) {
				for (int j = 0 ; j < HList.get(i).size(); j++){
					hpw.println(HList.get(i).get(j));
					//System.out.println(HList.get(i).get(j).toString());
				}
			}
			hpw.close();
			
			for (int i = 0; i < AList.size(); i ++) {
				for (int j = 0 ; j < AList.get(i).size(); j++){
					apw.println(AList.get(i).get(j));
					//System.out.println(AList.get(i).get(j).toString());
				}
			}
			apw.close();
			
			hbr.close();
			abr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void parseExpDisForCompare (String input1, String input2, String input3, String input4) {
			
			String human_file = input1; // "D:/m2w cs/evaldata/input_log/older/expressive_disagreement_lauren_3"
			String auto_file = input2; // "D:/m2w cs/evaldata/input_log/older/expressive_disagreement_auto_3"
			
			String human_preprocessed = input3; //"D:/m2w cs/evaluation-m2w/src/preprocessed/expressive_disagreement_lauren_3.p"
			String auto_preprocessed = input4; //"D:/m2w cs/evaluation-m2w/src/preprocessed/expressive_disagreement_auto_3.p"
			
			ArrayList<ArrayList<String>> HList = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> AList = new ArrayList<ArrayList<String>>();
			
			ArrayList<Integer> delHList = new ArrayList<Integer>();
			ArrayList<Integer> delAList = new ArrayList<Integer>();
			
			
				
			
			
			try {
				BufferedReader hbr = new BufferedReader(new FileReader(human_file));
				BufferedReader abr = new BufferedReader(new FileReader(auto_file));
				
				PrintWriter hpw = new PrintWriter(human_preprocessed);
				PrintWriter apw = new PrintWriter(auto_preprocessed);
				
				String tempStrh = null; 
				String tempStra = null;
				
				
				/*1.1save human file into HList*/  // ok
				while((tempStrh = hbr.readLine()) != null ) {
					//System.out.println("tempStrh : " + tempStrh);
					
					if(tempStrh.contains("processing: ")){  ///if it's a new file

						ArrayList<String> templist = new ArrayList<String>();
						templist.add(tempStrh);
						
						do{ 

	                        hbr.mark(1000);
	                        tempStrh=hbr.readLine();
	                        templist.add(tempStrh);

	                   }while(tempStrh!=null && !tempStrh.contains("$$$$$$")); //改成了整个block读取
						
	                   hbr.reset();
	                   
	                   HList.add(templist);
						//System.err.println("tempsh is :" + tempStrh);
					}
					
					
					
					
//					if(tempStrh.contains("+++++")){  /// if new calculate block
//						
//						//String tempsh = null;
//						ArrayList<String> templist = new ArrayList<String>();
//						templist.add(tempStrh);
//						
//						do{ 
//
//	                        hbr.mark(1000);
//	                        tempStrh=hbr.readLine();
//	                        templist.add(tempStrh);
//
//	                   }while(tempStrh!=null && !tempStrh.contains("$$$"));
//						
//	                  
//						
//
//		                     hbr.reset();
//		                     HList.add(templist);
//					}
				} //while
					
				
				
				
				
				
				/*1.2save auto file into AList*/ //
				while((tempStra = abr.readLine()) != null ) {
					
					if(tempStra.contains("processing: ")){ /// if is new file
						
						//String tempStra = null;
						ArrayList<String> templist = new ArrayList<String>();
						templist.add(tempStra);
						
						do{ 

	                        abr.mark(1000);
	                        tempStra=abr.readLine();
	                        templist.add(tempStra);

	                   }while(tempStra!=null && !tempStra.contains("$$$$$$"));

	                   abr.reset();
	                   
	                   AList.add(templist);
						
					}
					
					
					
//					if(tempStra.contains("+++")){ /// if is a category in a file
//						
//						//String tempsa = null;
//						ArrayList<String> templist = new ArrayList<String>();
//						templist.add(tempStra);
//						
////						while((tempStra = abr.readLine()) != null && !tempStra.contains("$$$$")){
////							
////							abr.mark(1000);
////							templist.add(tempStra);
////						}
//						do{ 
//
//	                        abr.mark(1000);
//	                        tempStra=abr.readLine();
//	                        templist.add(tempStra);
//
//	                   }while(tempStra!=null && ( !tempStra.contains("$$$")));
//					
//
//	                   abr.reset();
//	                   
//	                   AList.add(templist);
//						
//					}
					
				} //while
					
				
				System.out.println("Hlist size is :"+ HList.size());
				System.out.println("Alist size is :"+ AList.size());
				System.out.println("Alist is :"+ AList);
				System.out.println("Hlist is :"+ HList);
				
				hbr.close();
				abr.close();
				
				/*2.1parse human, delete -1 */ //ok
				for (int i = 0; i < HList.size(); i ++){
					
					System.out.println("H:"+ HList.get(i).size());
					System.out.println("A:"+ AList.get(i).size());
					
					for(int j = 0 ; j < HList.get(i).size() -1; j++){
						
						String tempStr = HList.get(i).get(j); //nullpointer
						
						
						
						if (tempStr.contains("-1")){
							delHList.add(i);
							delAList.add(i);
							break;
						}
					}
				}
				
				for(int i = 0; i < delHList.size(); i ++){
					
					System.out.println("delHList :"+ delHList.get(i));
					System.out.println("delAList :"+ delAList.get(i));
					
					int a = delHList.get(i) - i; // calculate  index after size shrink
					int b = delAList.get(i) - i;
					
					HList.remove(a);
					AList.remove(b);
					
				}
				
				delHList.clear();
				delAList.clear();
				
				
				
				/*2.2parse auto, add actual score: 0 to -1 */ //ok
				for (int i = 0; i < AList.size(); i ++){
					for(int j = 0 ; j < AList.get(i).size() - 1; j++){
						
						String tempStr = AList.get(i).get(j).toString();
						
						if (tempStr.contains("-1")){
							
							AList.get(i).set(j, tempStr + " --- actual score: 0");
							continue;	
						}
					}
				}
//						if (tempStr.contains("-1")){
//							delAList.add(i);
//							delHList.add(i);
//							break;
//						}
//					}
//				}
//				
//				for(int i = 0; i < delAList.size(); i ++){
//					
//					System.out.println("delHList 2:"+ delHList.get(i));
//					System.out.println("delAList 2:"+ delAList.get(i));
//					
//					int a = delHList.get(i) - i; 
//					int b = delAList.get(i) - i;
//					
//					HList.remove(a);
//					AList.remove(b);
//				}
//				
//				delHList.clear();
//				delAList.clear();	
					

				System.out.println("Hlist final size : " + HList.size());
				System.out.println("Alist final size : " + AList.size());
				
				
				/*3.write to files*/  //ok
				for (int i = 0; i < HList.size(); i ++) {
					for (int j = 0 ; j < HList.get(i).size(); j++){
						hpw.println(HList.get(i).get(j));
						//System.out.println(HList.get(i).get(j).toString());
					}
				}
				hpw.close();
				
				for (int i = 0; i < AList.size(); i ++) {
					for (int j = 0 ; j < AList.get(i).size(); j++){
						apw.println(AList.get(i).get(j));
						//System.out.println(AList.get(i).get(j).toString());
					}
				}
				apw.close();
				
				
				
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}	
					
					
			}
		
		
		
	

	}


