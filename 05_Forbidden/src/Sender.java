
public enum Sender {
	BRADLEY('B'),
	GORDON('G');
	
	private final char chara;
	private Sender(char chara) {
		this.chara = chara;
	}
	
	public char getChar() {
		return chara;
	}
	
	public static Sender getSender(char senderChar) {
		
		for(Sender sender : Sender.values()) {
			if(sender.chara == senderChar) {
				return sender;
			}
		}

		return null;
	}
}
