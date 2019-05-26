import java.math.BigDecimal;
import java.math.RoundingMode;

public class Moon implements Comparable<Moon>{

	private final int id;
	private final BigDecimal distanceToPlanet;
	private final BigDecimal initialRadius;
	private BigDecimal currentRadius;
	private final BigDecimal radiusStep;
	private final BigDecimal period;
	private final int unobtanium;
	private UniversePoint positionInMKm = new UniversePoint(BigDecimal.ZERO, BigDecimal.ZERO);
	
	public Moon(int id, BigDecimal distanceInMKm, BigDecimal initialRadius, BigDecimal period, int unobtanium) {
		this.id = id;
		this.distanceToPlanet = distanceInMKm;
		this.initialRadius = initialRadius;
		this.currentRadius = this.initialRadius;
		this.period = period;
		this.radiusStep = BigDecimal.valueOf(2).multiply(PhysicsHelper.PI).multiply(BigDecimal.valueOf(6)).divide(period,10, RoundingMode.HALF_UP) ;
		this.unobtanium = unobtanium;
		calculatePosition();
	}
	
	public Moon(int id, String distanceInKm, String initialRadius, String period, String unobtanium) {
		this(id, new BigDecimal(distanceInKm),new BigDecimal(initialRadius),new BigDecimal(period),Integer.parseInt(unobtanium));
	}
	
	private void calculatePosition() {
		
		BigDecimal x = this.distanceToPlanet.multiply(new BigDecimal(Math.cos(currentRadius.doubleValue())));
		BigDecimal y = this.distanceToPlanet.multiply(new BigDecimal(Math.sin(currentRadius.doubleValue())));

		positionInMKm =  new UniversePoint(x,y);
		
	}
	
	public UniversePoint getPosition() {
		return positionInMKm;
	}
	
	public int getId() {
		return id;
	}
	
	
	public void moveOneStep() {
		this.currentRadius = this.currentRadius.add(radiusStep);
		calculatePosition();
	}
	
	public void moveOneStepBack() {
		this.currentRadius = this.currentRadius.subtract(radiusStep);
		calculatePosition();
	}

	public BigDecimal getDistance2PlanetInMKm() {
		return distanceToPlanet;
	}
	
	public BigDecimal getCurrentRadius() {
		return currentRadius;
	}

	public BigDecimal getInitialRadius() {
		return initialRadius;
	}

	public BigDecimal getPeriod() {
		return period;
	}

	public int getUnobtanium() {
		return unobtanium;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Moon [id=");
		builder.append(id);
		builder.append(", distanceToPlanet=");
		builder.append(distanceToPlanet);
		builder.append(", initialRadius=");
		builder.append(initialRadius);
		builder.append(", currentRadius=");
		builder.append(currentRadius);
		builder.append(", unobtanium=");
		builder.append(unobtanium);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Moon other = (Moon) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compareTo(Moon o) {
		return this.getUnobtanium() - o.getUnobtanium();
	}
}
