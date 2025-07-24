package poo.polinomi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class PolinomioLL extends PolinomioAstratto {
	private LinkedList<Monomio> polinomio = new LinkedList<>();
	
	@Override
	public PolinomioLL factory() {
		return new PolinomioLL();
	}//factory
	
	public Iterator<Monomio> iterator(){
		return polinomio.iterator();
	}//Iterator
	
	@Override
	public void add(Monomio m) {
		if(m.getCOEFF() == 0) return;
		ListIterator<Monomio> lit = polinomio.listIterator();
		boolean flag = false; //quando diventa true ho sistemato il monomio 
		while(lit.hasNext() && !flag) {
			Monomio n = lit.next();
			if(n.equals(m)) {
				n = n.add(m);
				if(n.getCOEFF() == 0) {
					lit.remove();
				}
				else {
					lit.set(n);
				}
				flag = true;
			}
			else if(n.compareTo(m)>0) {
					lit.previous();
					lit.add(m);
					flag = true;
				}
		}
		if(!flag) lit.add(m);
	}//add
	
}//poo.polinomi.PolinomioLL
