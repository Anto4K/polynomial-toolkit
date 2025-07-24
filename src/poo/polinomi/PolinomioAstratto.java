package poo.polinomi;

import java.util.Iterator;

public abstract class PolinomioAstratto implements Polinomio{
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		boolean flag = true;
		for(Monomio m : this) {
			if(flag) flag = !flag;
			else {
				if(m.getCOEFF()>0) sb.append('+');
			}
			sb.append(m);
		}
		return sb.toString();
	}//toString
	
	public boolean equals(Object x) {
		if(!(x instanceof Polinomio)) return false;
		if(x== this) return true;
		Polinomio p = (Polinomio) x;
		if(this.size() != p.size()) return false;
		Iterator<Monomio> itThis = this.iterator();
		Iterator<Monomio> itP = p.iterator();
		while(itThis.hasNext()) {
			Monomio m1 = itThis.next();
			Monomio m2 = itP.next();
			if(m1.getCOEFF() != m2.getCOEFF() || m1.getGRADO() != m2.getGRADO()) return false;
		}
		return true;
	}//equals
	
	public int hashCode() {
		final int M = 43;
		int h = 0;
		for(Monomio m: this) {
			h = h*M + (String.valueOf(m.getCOEFF())+String.valueOf(m.getGRADO())).hashCode();
		}
		return h;
	}//hashCode
	
}//poo.polinomi.PolinomioAstratto
