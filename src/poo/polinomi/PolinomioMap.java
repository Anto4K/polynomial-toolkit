package poo.polinomi;

import java.util.*;

public class PolinomioMap extends PolinomioAstratto {
	private Map<Monomio,Monomio> polinomio = new TreeMap<>();
	
	@Override
	public PolinomioMap factory() {
		return new PolinomioMap();
	}
	
	@Override
	public int size() {
		return polinomio.size();
	}
	
	public void add(Monomio m) {
		if(m.getCOEFF() == 0) return;
		Iterator<Monomio> it = this.iterator();
		while(it.hasNext()){
			Monomio key= it.next();
			Monomio value = polinomio.get(key);
			if(value.equals(m)) {
				value = value.add(m);
				if(value.getCOEFF() == 0) {polinomio.remove(key); return;}
				else {
					polinomio.put(key, value);
					return;
				}
			}
		}
		polinomio.put(m,m);
	}//add
	
	@Override
	public Iterator<Monomio> iterator(){
		return polinomio.keySet().iterator();
	}
}
