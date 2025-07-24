package poo.polinomi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class PolinomioAL extends PolinomioAstratto {
	private ArrayList<Monomio> polinomio = new ArrayList<>();
	
	@Override
	public PolinomioAL factory() {
		return new PolinomioAL();
	}
	
	@Override
	public int size() {return polinomio.size();}
	
	@Override
	public void add(Monomio m) {
		if(m.getCOEFF() == 0) return;
		ListIterator<Monomio> lit = polinomio.listIterator();
		boolean flag = false;
		while(lit.hasNext() && !flag) {
			Monomio n = lit.next();
			if(n.equals(m)) {
				n = n.add(m);
				if(n.getCOEFF() == 0) {lit.remove(); flag = true;}
				else { lit.set(n); flag=true;}
			}if(n.compareTo(m)>0) {
				lit.previous();
				lit.add(m);
				flag = true;
			}
		}
		if(!flag) lit.add(m);
	} 
	
	@Override
	public Iterator<Monomio> iterator(){
		return polinomio.iterator();
	}
}
