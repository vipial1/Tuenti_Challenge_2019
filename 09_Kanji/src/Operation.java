
public class Operation {
	private static char SEPARATOR = ' ';
	private String number1str, number2str, resultstr;
	private WesternNumber number1, number2, result;
	private int correctNumber1, correctNumber2, correctResult;
	private Operator correctOperator;

	Operation(String line){
		 NextToRead next2read = NextToRead.NUMBER1;
		 StringBuilder sb = new StringBuilder();
		 char[] charArray = line.toCharArray();
		 for(int i=0; i<charArray.length; i++) {
			 char c = charArray[i];
			 if(c != SEPARATOR && i<charArray.length -1) {
				 sb.append(c);
			 } else {
				 switch(next2read) {
				case EQUALS:
					next2read = NextToRead.RESULT;
					break;
				case NUMBER1:
					number1str = sb.toString();
					next2read = NextToRead.OPERATOR;
					break;
				case NUMBER2:
					number2str = sb.toString();
					next2read = NextToRead.EQUALS;
					break;
				case OPERATOR:
					next2read = NextToRead.NUMBER2;
					break;
				case RESULT:
					sb.append(c);
					resultstr = sb.toString();
					break;
				default:
					break;
				 
				 }
				 sb = new StringBuilder();
			 }
		 }
		 number1 = new WesternNumber(number1str);
		 number2 = new WesternNumber(number2str);
		 result = new WesternNumber(resultstr);

		 
	}

	public void validate() {
		int results=0;;
		for(Operator oper : Operator.values()) {
			for(int num1 : number1.getPossibleNumbers()) {
				for(int num2 : number2.getPossibleNumbers()) {
					int partResult = 0;
					switch(oper) {
						case ADD:
							partResult = num1 + num2;
							break;
						case MULTIPLY:
							partResult = num1 * num2;
							break;
						case SUBSTRACT:
							partResult = num1 - num2;
							break;
						default:
							break;
					}
					if(result.getPossibleNumbers().contains(partResult)) {
						correctNumber1 = num1;
						correctNumber2 = num2;
						correctResult = partResult;
						correctOperator = oper;
						results++;
						//return;
					}
				}
			}
		}
		
		//System.out.println(results);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(correctNumber1);
		builder.append(" ");
		builder.append(correctOperator.getOperator());
		builder.append(" ");
		builder.append(correctNumber2);
		builder.append(" = ");
		builder.append(correctResult);
		return builder.toString();
	}
}
