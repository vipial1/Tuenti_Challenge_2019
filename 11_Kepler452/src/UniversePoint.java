import java.math.BigDecimal;

public class UniversePoint {
	private final BigDecimal x;
	private final BigDecimal y;
	
	public UniversePoint(BigDecimal x, BigDecimal y) {
		this.x = x;
		this.y = y;
	}
	
	public UniversePoint() {
		this(BigDecimal.ZERO, BigDecimal.ZERO);
	}

	public BigDecimal getX() {
		return x;
	}

	public BigDecimal getY() {
		return y;
	}


}
