
public class HashHelper {

	
	public static int calculatePrintLenght(Document original, Document modified) {
		
		while(!hashReachable(modified.getHashStartsAt(),modified.getPrint().length(), original.getHash(), modified.getHash())) {
			modified.addToPrint("0");
		}
		return modified.getPrint().length();
	}
	
	public static boolean hashReachable(int offset, int length, Hash original, Hash modified) {
		
		int[] need = new int[16];
		for(int i=0; i<Hash.MODULO;i++) {
			need[i] = ((original.getByteAt(i) - modified.getByteAt(i)) + 256) % 256;
		}
		
		for(int i = 0; i<length; i++) {
			int index = (i+offset)%Hash.MODULO;
			need[index] -= Hash.maxHASHChange;
		}
		
		for(int i=0; i<Hash.MODULO;i++) {
			if(need[i] > 0) {
				return false;
			}
		}
		return true;
	}

	public static void calculatePrint(Document originalDoc, Document modifiedDoc) {
		
		Hash originalHash =originalDoc.getHash();
		Hash modifiedHash =modifiedDoc.getHash();
		char[] needChar = new char[modifiedDoc.getPrint().length()];
		int needIndex = modifiedDoc.getHashStartsAt();
		
		int[] need = new int[16];
		for(int i=0; i<Hash.MODULO;i++) {
			need[i] = ((originalHash.getByteAt(i) - modifiedHash.getByteAt(i)) + 256) % 256;
		}
		
		for(int printIndex = 0; 
				printIndex < Hash.MODULO && printIndex < needChar.length; 
				printIndex++) {
			
			int needed = need[needIndex];
			int summands = ((needChar.length - printIndex -1) / Hash.MODULO)+1;
			
			char[] chars = getCharsToAchieveNeed(needed, summands);
			
			for(int i=0;i<chars.length;i++) {
				needChar[printIndex + (i*Hash.MODULO)] = chars[i];
			}
			needIndex = (needIndex +1)%Hash.MODULO;
			
		}
		
		modifiedDoc.setPrint(new String(needChar));
	}
	
	private static char[] getCharsToAchieveNeed(int need, int summands) {
		char[] result = new char[summands];
		for(int i=0;i<result.length;i++) {
			result[i]='0';
		}
		charsFor: for(int i=result.length -1; i>=0;i--) {
			while(!needAchieved(result, need)) {
				result[i]++;
				if(result[i] > 'z') {
					result[i] = 'z';
					continue charsFor;
				}
			}
			break;
		}
		return result;
	}
	
	private static boolean needAchieved(char[] chars, int need) {
		int sum=0;
		for(int i=0;i<chars.length;i++) {
			sum += (chars[i] - '0');
		}
		return sum==need;
	}
}
