package constructor;

public class Car {

	private Car() {
	}

	private static Car instance = new Car();
	
	public static Car getInstance() {
		return instance;
	}
	
	public boolean isCar = false;
}
