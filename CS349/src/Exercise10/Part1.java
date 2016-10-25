package Exercise10;

import java.lang.reflect.Field;

import acme.NetworkService;

/**
 * Run the unaltered program, to show that it errors.
 * Then search for the field names and will use that in Part1A.
 * 
 * @author Jonathan Whipple
 *
 */
public class Part1 {
	public static void main(String[] args) {
        runOriginalProgram();
        System.out.println("");
		findAttribute();
    }
	
	public static void runOriginalProgram(){
		NetworkService ns = new NetworkService();
        ns.connect();
	}
	
	public static void findAttribute(){
		NetworkService ns = new NetworkService();
		Field[] fields = ns.getClass().getDeclaredFields();
		for(Field field : fields){
			System.out.println("Field Name: " + field.getName() + " | " + field.toGenericString());
		}
	}
}
