package Exercise10;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ProductionCodeTest {

    public static void main(String[] args) {

        ProductionCode testSubject = new ProductionCode();

        int inputValue1 = 3;
        int inputValue2 = 7;
        int expectedResult = 10;

        int actualResult = 0;
		Method method;
		try {
			method = testSubject.getClass().getDeclaredMethod("add", int.class, int.class);
			method.setAccessible(true);
			actualResult =(int)method.invoke(testSubject, inputValue1, inputValue2);
		} catch (SecurityException | IllegalArgumentException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

        if (actualResult == expectedResult)
            System.out.println("ProductionCode test passed.");
        else
            System.out.println("ProductionCode test failed.");
    }
}