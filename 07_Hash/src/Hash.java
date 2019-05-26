import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Hash {
	
	public final static int MODULO = 16;
	public final static byte maxHASHChange = 'z' - '0';
	
	private final byte[] hash;
	
	private Hash(byte[] hash){
		this.hash = hash;
	}
	
	public byte getByteAt(int index) {
		return hash[index];
	}
	
	public static Hash notSoComplexHash(Document inputText) {
		return notSoComplexHash(inputText.getText());
	}
	
	public static Hash notSoComplexHash(String inputText) {
	    byte[] hash = new byte[MODULO];
	    Arrays.fill(hash, (byte) 0x00);
	    byte[] textBytes = inputText.getBytes(StandardCharsets.ISO_8859_1);
	    for (int i = 0; i < textBytes.length; i++) {
	        hash[i % MODULO] = (byte) (hash[i % MODULO] + textBytes[i]);
	    }
	    return new Hash(hash);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(byte b : hash) {
			builder.append(b);
			builder.append(" ");
		}
		return builder.toString();
	}
}
