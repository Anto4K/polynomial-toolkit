package poo.polinomi;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class PolinomioSet extends PolinomioAstratto {
	private Set<Monomio> polinomio = new TreeSet<>();
	
	@Override
	public PolinomioSet factory() {
		return new PolinomioSet();
	}
	
	@Override
	public int size() {
		return polinomio.size();
	}
	
	public void add(Monomio m) {
		if(m.getCOEFF() == 0) return;
		Iterator<Monomio> it = polinomio.iterator();
		boolean flag = false;
		while(!flag && it.hasNext()) {
			Monomio n = it.next();
			if(n.equals(m)) {
				it.remove();
				n = n.add(m);
				if(n.getCOEFF() != 0)
					polinomio.add(n);
				flag = true;
			}
		}
		if(!flag)
			polinomio.add(m);
	}//add

	
	@Override
	public Iterator<Monomio> iterator(){
		return polinomio.iterator();
	}
}
