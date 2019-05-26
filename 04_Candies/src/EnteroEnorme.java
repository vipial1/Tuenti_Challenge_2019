/****************************
 * Vicent Picornell
 * vicent.de.oliva@gmail.com
 * java + eclipse
 ****************************/

import java.text.ParseException;



public class EnteroEnorme {
	private final static int MAX_DIGITS = 4000;
	private int[] digits;
	private boolean isNegative;
	private boolean overflow;
	private boolean Error = false;
	private boolean isZero = false;
	private int Multiplier10First = 0;
	private int Multiplier10Zeros = 0;
	
	
	//CONSTRUCTOR
	EnteroEnorme(String number_as_str) throws ParseException{
		this.Parse(number_as_str);
	}
	
	EnteroEnorme(int number_as_int) throws ParseException{
		this.Parse(String.valueOf(number_as_int));
	}
	
	private void Parse(String number_as_str) throws ParseException{
		this.Parse(number_as_str.toCharArray());
	}
	
	
	private void Parse(char[] number_as_array) throws ParseException{
		if(number_as_array == null 
		  || number_as_array.length==0){
		}
		
		this.isNegative = (number_as_array[0] == '-');
		
		int FirstParseable = this.isNegative?1:0;
		
		while(FirstParseable  < number_as_array.length
			&& number_as_array[FirstParseable] == '0'){
			FirstParseable++;
		}
		if(FirstParseable >= number_as_array.length){
			FirstParseable--;
		}
		
		this.digits	= new int[Math.min(number_as_array.length-FirstParseable, MAX_DIGITS)];
		
		int offset = Math.max(number_as_array.length-FirstParseable- MAX_DIGITS,0);
		boolean Multiplier10 = true;
		
		for(int index=number_as_array.length-1 - offset, digit_index=0;
				index>=FirstParseable;
				index--,digit_index++){
			
			if(Character.isDigit(number_as_array[index])){
				this.digits[digit_index] = Character.getNumericValue(number_as_array[index]);
			}
			else{
				this.digits = null;
				this.Error = true;
				throw new ParseException("Error parsing number!!", index);
			}
			if((Multiplier10 && index != number_as_array.length-1 && this.digits[digit_index] !=0) || this.digits.length == 1){
				Multiplier10 = false;
			}
		}
		if(this.digits.length==1 && this.digits[0]==0){
			this.isNegative = false;
			this.isZero	= true;
			return;
		}
		
		if(number_as_array.length - FirstParseable > MAX_DIGITS){
			this.overflow = true;
		}
		
		if(Multiplier10){
			this.Multiplier10First = this.digitAt(this.digits.length-1);
			this.Multiplier10Zeros = this.digits.length-1;
		}
	}
	
	public int getLength(){
		return this.Error?0:this.digits.length;
	}
	
	@Override
	public String toString() {
		if(this.Error){return "";}
		
		StringBuilder result = new StringBuilder(this.getLength()+4);
		
		if(this.overflow){
			result.append("...");
		}
		for(int i=0;i<this.digits.length;i++){
			result.append(this.digits[i]);
		}
		
		if(this.isNegative){
			result.append('-');
		}
		
		return result.reverse().toString();
	}
	
	private String toStringAbsolute(){
		if(this.Error){return "";}
		
		StringBuilder result = new StringBuilder(this.getLength());
		for(int i=0;i<this.digits.length;i++){
			result.append(this.digits[i]);
		}
		return result.reverse().toString();

	}
	
	private int digitAt(int index){
		if(index >= this.digits.length){
			return -1;
		}
		return this.digits[index];
	}
	private int RealDigitAt(int index){
		if(index >= this.digits.length){
			return -1;
		}
		return this.digits[this.digits.length - index - 1];
	}
	
	public static EnteroEnorme Add(EnteroEnorme A, EnteroEnorme B){
		if(B.getLength() > A.getLength()){
			EnteroEnorme C = A;
			A = B; B = C;
		}
		//we are sure than A has more or equal digits than B
		StringBuilder result = new StringBuilder(A.getLength() +1);
		
		int carrier = 0;
		int partial = 0;
		for(int i=0;i<A.getLength();i++){
			if(i>=B.getLength() && carrier == 0){
				result.append(A.digitAt(i));
			}
			else{
				partial = A.digitAt(i) + ((i>=B.getLength())?0:B.digitAt(i)) + carrier;
				carrier = partial / 10;
				partial %= 10;
				result.append(partial);
			}
		}
		if(carrier > 0){
			result.append(carrier);
		}
		
		try {
			return new EnteroEnorme(result.reverse().toString());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
public static EnteroEnorme Substract(EnteroEnorme A, EnteroEnorme B){		
		try{
			//7-7 = 0
			if(EqualThan(A,B)){return new EnteroEnorme("0");}
			//8 - -7 = 8 + 7
			if(!A.isNegative && B.isNegative){return EnteroEnorme.Add(A, B);}
			//-8 - 7 = -(|-8| + 7)
			if(A.isNegative && !B.isNegative){return ChangeSign(Add(Absolute(A),B));}
			//-8 - -7 = |-7| - |-8|
			if(A.isNegative && B.isNegative){return Substract(Absolute(B),Absolute(A));}
			//8-7... 7-8
			if(!A.isNegative && !B.isNegative){
				//7 - 8 = -(8-7)
				if(BiggerThan(B,A)){return ChangeSign(Substract(B,A));}
				//8 - 7
				//we are sure than result has, as maximum A length
				StringBuilder result = new StringBuilder(A.getLength());
				int carrier=0;
				for(int i = 0;i < A.getLength();i++){
					int Adigit=A.digitAt(i);
					if(i>B.getLength() && carrier == 0){
						result.append(Adigit);
					}
					else{		
						int Bdigit= carrier + (i>=B.getLength()?0:B.digitAt(i));
						carrier=0;
						while(Bdigit > Adigit){
							carrier++;
							Adigit+=10;
						}
						result.append(Adigit-Bdigit);
					}
				}
				return new EnteroEnorme(result.reverse().toString());
			}
		}catch(ParseException e){
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public static EnteroEnorme Multiply(EnteroEnorme A,EnteroEnorme B){
		if(B.getLength() > A.getLength()
		 || (A.Multiplier10First >0 && B.Multiplier10First==0)){
			EnteroEnorme C = A;
			A = B; B = C;
		}
		try{
			if(A.isZero || B.isZero){
				return new EnteroEnorme("0");
			}
			if(B.Multiplier10First > 0){
				boolean NegativeResult = (A.isNegative && !B.isNegative) || (!A.isNegative && B.isNegative);
				EnteroEnorme result = EnteroEnorme.x10exp(Multiply(A,
																  new EnteroEnorme(Integer.toString(B.Multiplier10First))),
														  B.Multiplier10Zeros);
				return NegativeResult?ChangeSign(result):result;
			}
			StringBuilder result = new StringBuilder(A.getLength() + B.getLength());
			
			int pivot = -(B.getLength() -1);
			int carrier= 0;
			
			for(int Aindex=pivot;Aindex<A.getLength();Aindex++){
				int ResultDigit = carrier;
				carrier=0;
				for(int Bindex=B.getLength()-1,i=0;Bindex>=0;Bindex--,i++){
					int AindexMod = Aindex+i;
					ResultDigit += B.digitAt(Bindex) * ((AindexMod<0 || AindexMod >=A.getLength())?0:A.digitAt(AindexMod));
				}
				while(ResultDigit >= 10){
					carrier++;
					ResultDigit-=10;
				}
				result.append(ResultDigit);
			}
			if(carrier > 0){
				result.append(carrier);
			}
			boolean NegativeResult = (A.isNegative && !B.isNegative) || (!A.isNegative && B.isNegative);
			return new EnteroEnorme((NegativeResult?"-":"") + result.reverse().toString());
			
		}catch(ParseException e){
			e.printStackTrace();
			return null;
		}
	}
	public static EnteroEnorme Divide(EnteroEnorme A,EnteroEnorme B){
		
		try{
				if(SmallerThan(A,B)){return new EnteroEnorme("0");}
				if(B.isZero){return null;}
				if(B.Multiplier10First>0){
					return x10exp(Divide(A,new EnteroEnorme(Integer.toString(B.Multiplier10First))),B.Multiplier10Zeros * -1);
				}
				StringBuilder result = new StringBuilder(A.getLength());
				EnteroEnorme Rest = null;
				int NextDigit = B.getLength();
				
				EnteroEnorme Zero = new EnteroEnorme("0");
				do{
					EnteroEnorme Dividend = Rest==null?new EnteroEnorme(A.toStringAbsolute().substring(0, B.getLength()))
														:
														new EnteroEnorme(Rest.toStringAbsolute() +A.RealDigitAt(NextDigit++));
					while(SmallerThan(Dividend,B)){
						if(NextDigit >= A.getLength()){
							result.append("0");
							boolean NegativeResult = (A.isNegative && !B.isNegative) || (!A.isNegative && B.isNegative);
							return new EnteroEnorme((NegativeResult?"-":"") +result.toString());
						}
						if(Rest!=null){result.append("0");}
						Dividend = new EnteroEnorme(Dividend.toStringAbsolute()+A.RealDigitAt(NextDigit++));
					}
					EnteroEnorme PartialResult = null;
					EnteroEnorme NewPartialResult = Zero;
		
					int ResultDigit = 1;
					
					for(ResultDigit = 1;ResultDigit<11 && SmallerOrEqualThan(NewPartialResult,Dividend);ResultDigit++){
						PartialResult = NewPartialResult;
						NewPartialResult = Multiply(B,new EnteroEnorme(Integer.toString(ResultDigit)));
					}
					ResultDigit-=2;
					result.append(Integer.toString(ResultDigit));
					Rest = Substract(Dividend,PartialResult);
				}while(NextDigit < A.getLength());
				boolean NegativeResult = (A.isNegative && !B.isNegative) || (!A.isNegative && B.isNegative);
				return new EnteroEnorme((NegativeResult?"-":"") +result.toString());
			}catch(ParseException e){
			e.printStackTrace();
			return null;
		}
	}
	
	
public static EnteroEnorme Modulo(EnteroEnorme A,EnteroEnorme B){
		
		try{
				if(SmallerThan(A,B)){return new EnteroEnorme("0");}
				if(B.isZero){return null;}
				if(B.Multiplier10First>0){
					return x10exp(Divide(A,new EnteroEnorme(Integer.toString(B.Multiplier10First))),B.Multiplier10Zeros * -1);
				}
				StringBuilder result = new StringBuilder(A.getLength());
				EnteroEnorme Rest = null;
				int NextDigit = B.getLength();
				
				EnteroEnorme Zero = new EnteroEnorme("0");
				do{
					EnteroEnorme Dividend = Rest==null?new EnteroEnorme(A.toStringAbsolute().substring(0, B.getLength()))
														:
														new EnteroEnorme(Rest.toStringAbsolute() +A.RealDigitAt(NextDigit++));
					while(SmallerThan(Dividend,B)){
						if(NextDigit >= A.getLength()){
							result.append("0");
							return EnteroEnorme.Substract(A, EnteroEnorme.Multiply(new EnteroEnorme(result.toString()), B));
						}
						if(Rest!=null){result.append("0");}
						Dividend = new EnteroEnorme(Dividend.toStringAbsolute()+A.RealDigitAt(NextDigit++));
					}
					EnteroEnorme PartialResult = null;
					EnteroEnorme NewPartialResult = Zero;
		
					int ResultDigit = 1;
					
					for(ResultDigit = 1;ResultDigit<11 && SmallerOrEqualThan(NewPartialResult,Dividend);ResultDigit++){
						PartialResult = NewPartialResult;
						NewPartialResult = Multiply(B,new EnteroEnorme(Integer.toString(ResultDigit)));
					}
					ResultDigit-=2;
					result.append(Integer.toString(ResultDigit));
					Rest = Substract(Dividend,PartialResult);
				}while(NextDigit < A.getLength());
				return Rest;
			}catch(ParseException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static EnteroEnorme Raise(EnteroEnorme A, EnteroEnorme B){
		
		try{
			if(B.isZero){
				return new EnteroEnorme("1");
			}
			if(B.isNegative){
				return new EnteroEnorme("1");
			}
			EnteroEnorme counter = B;
			EnteroEnorme result = A;
			EnteroEnorme one = new EnteroEnorme("1");
			while(BiggerThan(counter,one)){
				result = Multiply(result,A);
				counter= Substract(counter,one);
			}
			return result;
		}catch(ParseException e){
			e.printStackTrace();
			return null;
		}

	}

	
	public static EnteroEnorme Factorial(EnteroEnorme A){
		try {
			if(A.isNegative || A.isZero || A.isNegative){
				return null;
			}
			
			EnteroEnorme one = new EnteroEnorme("1");

			EnteroEnorme counter = Substract(A,one);
			EnteroEnorme result = A;
			while(BiggerThan(counter,one)){
				result = Multiply(result,counter);
				counter= Substract(counter,one);
			}
			return result;
			
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static EnteroEnorme x10exp(EnteroEnorme A,int exp){
		try{
			if(exp==0){return new EnteroEnorme("1");}
			if(exp>0){
				StringBuilder zeros = new StringBuilder(exp);
				for(int i=0;i<exp;i++){
					zeros.append('0');
				}
				return new EnteroEnorme(A.toString() + zeros);}
			if(exp<0){
				System.out.println(A.getLength());
				if(A.getLength() <= exp*-1){
					return new EnteroEnorme("0");
				}
				return new EnteroEnorme(A.toString().substring(0, A.getLength()+exp));}
		}catch(ParseException e){
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	private static EnteroEnorme ChangeSign(EnteroEnorme A){
		
		try {
			if(A.isNegative){
				return new EnteroEnorme(A.toStringAbsolute());
			}
			else{
				return new EnteroEnorme("-"+A.toString());
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	private static EnteroEnorme Absolute(EnteroEnorme A){
		
		try {
			return new EnteroEnorme(A.toStringAbsolute());

		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static boolean EqualThan(EnteroEnorme A, EnteroEnorme B){
		if(A.isNegative != B.isNegative){return false;}
		if(A.getLength() != B.getLength()){return false;}
		if(A.overflow != B.overflow){return false;}

		//Same sign and same length
		for(int i=A.getLength();i>=0;i--){
			if(A.digitAt(i) != B.digitAt(i)){return false;}
		}
		return true;
		
	}
	public static boolean BiggerOrEqualThan(EnteroEnorme A, EnteroEnorme B){
		return EnteroEnorme.EqualThan(A, B) || EnteroEnorme.BiggerThan(A, B);
	}
	
	public static boolean SmallerThan(EnteroEnorme A, EnteroEnorme B){
		return !EnteroEnorme.EqualThan(A, B) && !EnteroEnorme.BiggerThan(A, B);
	}
	
	public static boolean SmallerOrEqualThan(EnteroEnorme A, EnteroEnorme B){
		return EnteroEnorme.EqualThan(A, B) || EnteroEnorme.SmallerThan(A, B);
	}
	
	public static boolean BiggerThan(EnteroEnorme A, EnteroEnorme B){
		// -6 < 7
		if(A.isNegative && !B.isNegative){return false;} 
		// 6 > -7
		if(!A.isNegative && B.isNegative){return true;}
		// 6 < 7 ... 7 > 6... 6 = 6
		if(!A.isNegative && !B.isNegative){
			// 12 > 7
			if(A.getLength() > B.getLength()){return true;}
			// 6 < 13
			if(A.getLength() < B.getLength()){return false;}
			//Both positive, and same length.
			for(int i=A.getLength();i>=0;i--){
				if(A.digitAt(i) > B.digitAt(i)){return true;}
				if(A.digitAt(i) < B.digitAt(i)){return false;}
			}
			return false;
		}
		// -6 > -7 ... -7 > -6... -6 = -6	
		if(A.isNegative && B.isNegative){
			// -12 < -7
			if(A.getLength() > B.getLength()){return false;}
			// -6 > -13
			if(A.getLength() < B.getLength()){return true;}
			//Both negative, and same length.
			for(int i=A.getLength();i>=0;i--){
				if(A.digitAt(i) > B.digitAt(i)){return false;}
				if(A.digitAt(i) < B.digitAt(i)){return true;}
			}
			return false;
		}
		return false;
	}
}
