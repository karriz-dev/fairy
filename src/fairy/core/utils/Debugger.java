package fairy.core.utils;

public class Debugger {
	public static boolean isDebugging = true;
	public static final int level = 0;
	
	public static void Log(Object o, String msg)
	{	
		if(isDebugging) {
			System.out.println("[DEBUG] " + o.getClass().getName() +": " + msg);
		}
	}
	
	public static void Log(Object o, String msg, int lv)
	{	
		if(level == lv)
		{
			if(isDebugging) {
				System.out.println("[DEBUG] " + o.getClass().getName() +": " + msg);
			}
		}
	}
	
	public static void Log(Object o, Exception e)
	{	
		if(isDebugging)
			System.out.println("[Error] " + o.getClass().getName() +": " + e.getMessage() +"(cause: " + e.getCause() +")");
	}
}
