
public class MathHelper {

	public static EnteroEnorme mcm(EnteroEnorme num1, EnteroEnorme num2) {
		
		try {

		EnteroEnorme mcm = new EnteroEnorme(0);
		EnteroEnorme a = MathHelper.max(num1, num2);
		EnteroEnorme b = MathHelper.min(num1, num2);
		mcm = EnteroEnorme.Multiply(EnteroEnorme.Divide(a, mcd(a, b)),b);
		return mcm;
		}catch(Exception e) {
			return null;
		}
	}
	
	public static EnteroEnorme max(EnteroEnorme num1, EnteroEnorme num2) {
		if(EnteroEnorme.BiggerThan(num1, num2)) {
			return num1;
		}
		return num2;
	}
	
	public static EnteroEnorme min(EnteroEnorme num1, EnteroEnorme num2) {
		if(EnteroEnorme.BiggerThan(num1, num2)) {
			return num2;
		}
		return num1;
	}
	
	public static EnteroEnorme mcd(EnteroEnorme num1, EnteroEnorme num2) {
		
		try {
		EnteroEnorme mcd = new EnteroEnorme(0);
		EnteroEnorme a = MathHelper.max(num1, num2);
		EnteroEnorme b = MathHelper.min(num1, num2);
		do {
			mcd = b;
			b = EnteroEnorme.Modulo(a, b);
			a = mcd;
		} while(!EnteroEnorme.EqualThan(b, new EnteroEnorme("0")));
			return mcd;
		}catch(Exception e) {
			return null;
		}
	}
	
	
}
