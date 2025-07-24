package poo.polinomi;

public class Monomio implements Comparable<Monomio>{
	private final int COEFF, GRADO;
	
	public Monomio(final int C, final int G) {
		if(G <0) throw new IllegalArgumentException();
		this.COEFF = C;
		this.GRADO = G;
	}
	
	public Monomio(Monomio m) {
		COEFF = m.COEFF; GRADO= m.GRADO;
	}
	
	public int getCOEFF() {
		return COEFF;
	}
	
	public int getGRADO() {
		return GRADO;
	}
	
	public boolean equals(Object x) {
		if(!(x instanceof Monomio)) return false;
		if(x == this) return true;
		Monomio m = (Monomio)x;
		return this.GRADO == m.GRADO;
	}//equals
	
	public int hashCode() {
		return GRADO;
	}//hashCode
	
	public int compareTo(Monomio m) {
		if(this.GRADO > m.GRADO) return -1;
		if(this.GRADO < m.GRADO) return 1;
		return 0;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(50);
		if(COEFF == 0) sb.append(0);
		else {
			if(COEFF < 0) sb.append('-');
			if(Math.abs(COEFF) != 1 || GRADO == 0)
				sb.append(Math.abs(COEFF));
			if(GRADO >0) sb.append('x');
			if(GRADO >1) { 
				sb.append('^');
				sb.append(GRADO);
			}
		}
		return sb.toString();
	}//toString
	
	public Monomio add(Monomio m) {
		if(!this.equals(m)) throw new RuntimeException("Monomi non simili");
		return new Monomio(this.COEFF+m.COEFF, this.GRADO);
	}//add
	
	public Monomio mul(int s) {
		return new Monomio(COEFF*s, GRADO);
	}//mul
	
	public Monomio mul(Monomio m) {
		return new Monomio(this.COEFF*m.COEFF, this.GRADO+m.GRADO);
	}//mul
	
}//poo.polinomi.Monomio
