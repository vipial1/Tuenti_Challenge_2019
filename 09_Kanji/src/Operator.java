
public enum Operator {
	ADD('+'),
	SUBSTRACT('-'),
	MULTIPLY('*');
	
	private char sign;
	
	private Operator(char sign){
		this.sign = sign;
	}
	
	public char getOperator() {
		return sign;
	}
}
