package solo.sololand.external;

public class ArrayUtil{
	
	private ArrayUtil(){
		
	}
	
	public static String implode(String p, String[] array){
		StringBuilder sb = new StringBuilder();
		boolean f = true;
		for(String a : array){
			if(f){
				f = false;
			}else{
				sb.append(p);
			}
			sb.append(a);
		}
		if(f){
			return sb.toString();
		}
		return "";
	}
}