package clue;

// Provides colors for testing module

import java.util.HashMap;
import java.util.Map;

public class Colors {
	private static String[] colors = new String[]{"red", "blue", "green", "yellow", "magenta", "cyan", "white"};
	public static Map<String,String> colorMap = new HashMap<String,String>();

	public static String[] getColorsArray(){
		String[] s = new String[colors.length];
		for(int i = 0;i<colors.length;i++){
			s[i] = colors[i];
		}
		return s;
	}
	
	public static void load(){
		Colors.colorMap.put("red",(char)27+"[31m");
		Colors.colorMap.put("blue",(char)27+"[34m");
		Colors.colorMap.put("green",(char)27+"[32m");
		Colors.colorMap.put("yellow",(char)27+"[33m");
		Colors.colorMap.put("magenta",(char)27+"[35m");
		Colors.colorMap.put("cyan",(char)27+"[36m");
		Colors.colorMap.put("black",(char)27+"[30m");
		Colors.colorMap.put("white",(char)27+"[37m");
	
	}
	
	
	public static String begin(String color){
		return Colors.colorMap.get(color);
	}
	
	public static String end(){
		return (char)27+"[0m";
	}
	
}
