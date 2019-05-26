
public class Message {
	private final String originalMessage;
	private final Sender sender;
	private char lastChar;
	
	public Message(Sender sender, String originalMessage) {
		this.originalMessage = originalMessage;
		this.lastChar = originalMessage.charAt(originalMessage.length()-1);
		this.sender = sender;
	}
	
	public char getLastChar() {
		return lastChar;
	}
	
	public Sender getSender() {
		return sender;
	}
	
	public String decrypt(Offset offset, KeyboardLayout layout) {
		StringBuilder decrypted = new StringBuilder();
		
		for(char cha : originalMessage.toCharArray()) {
			if(cha == ' ') {
				decrypted.append(' ');
			} else {
				char shifted = layout.shift(offset, cha);
				decrypted.append(shifted);
			}
			
		}
		return decrypted.toString();
	}
}
