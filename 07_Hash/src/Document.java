
public class Document {
	private final String preamble;
	private final String body;
	private String print = "";
	private final static String PRINT_SEPARATOR = "---";
	private Hash hash;
	private final int hashStartsAt;
	
	
	Document(String allDocument){
		String[] parts = allDocument.split(PRINT_SEPARATOR +PRINT_SEPARATOR); 
		preamble = parts[0];
		body = parts[1];
		calculateHash();
		hashStartsAt = (preamble.length() + PRINT_SEPARATOR.length())% Hash.MODULO;
	}
	
	Document(String preamble, String body){
		this.preamble = preamble;
		this.body = body;
		calculateHash();
		hashStartsAt = (preamble.length() + PRINT_SEPARATOR.length())% Hash.MODULO;
	}
	
	public String getText() {
		return preamble + PRINT_SEPARATOR + print + PRINT_SEPARATOR + body;
	}
	
	public void setPrint(String print) {
		this.print = print;
		calculateHash();
	}
	
	public void addToPrint(String print) {
		setPrint(this.print + print);
	}
	
	public Hash getHash() {
		return this.hash;
	}
	
	public String getPrint() {
		return this.print;
	}
	
	private void calculateHash() {
		this.hash = Hash.notSoComplexHash(this);
	}

	public int getHashStartsAt() {
		return hashStartsAt;
	}

}
