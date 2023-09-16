package tableModel;

public class Interest {
	
	public static double simpleCalc(double principal, double interest, int years) {
		return (principal + (principal * (interest / 100) * years));
	}
	
	public static double compoundCalc(double principal, double interest, int years) {
		return (principal * Math.pow((1 + (interest / 100)), years));
	}
}
