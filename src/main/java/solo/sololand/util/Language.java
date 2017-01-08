package solo.sololand.util;

import java.util.HashMap;

public class Language{
	
	private Language(){
		
	}
	
	public static HashMap<String, String> lang = new HashMap<String, String>();
	
	public static String systemLanguage = "kor";
	
	public static String get(String key){
		if(lang.containsKey(key)){
			return lang.get(key);
		}
		return "Invalid key";
	}
	
	public static String get(String key, String... replace){
		String ret = get(key);
		for(int i = 0; i < replace.length; i++){
			ret.replace("{%" + Integer.toString(i) + "}", replace[i]);
		}
		return ret;
	}
}