package Exercise10;

import java.lang.reflect.Field;

import acme.NetworkService;
/**
 * Found the private field name in Part 1, now hardcode to change the value so that it will work.
 * 
 * @author JW043373
 *
 */
public class Part1A {

	public static void main(String[] args){
		NetworkService ns = new NetworkService();
		
		Field field;
		try {
			field = ns.getClass().getDeclaredField("machineName");
			field.setAccessible(true);
			field.set(ns, "aws.com");
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		ns.connect();
	}
}
