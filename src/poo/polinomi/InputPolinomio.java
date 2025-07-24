package poo.polinomi;

import java.util.Scanner;
import java.util.StringTokenizer;

import poo.polinomi.Monomio;
import poo.polinomi.Polinomio;
import poo.polinomi.PolinomioLL;

public class InputPolinomio {

	public static Monomio primoMonomio( StringTokenizer st ) {
		int segno=1, coeff=0, grado=0;
		String tk=st.nextToken();
		
		if( tk.charAt(0)=='-' ) {
			segno=-1;
			tk=st.nextToken();
		}
		
		if( tk.matches("\\d+") ) {
			coeff=Integer.parseInt(tk);
		}
		else if( tk.matches("x(\\^\\d+)?") ) {
			coeff=1;
			int i=tk.indexOf('^');
			if( i>=0 ) 
				grado=Integer.parseInt( tk.substring(i+1) );
			else
				grado=1;
		}
		else {
			int i=tk.indexOf('x');
			coeff=Integer.parseInt( tk.substring(0,i) );
			i=tk.indexOf('^',i+1);
			if( i>=0 )
				grado=Integer.parseInt( tk.substring(i+1) );
			else
				grado=1;
		}
		coeff=coeff*segno;
		return new Monomio(coeff,grado);
	}//primoMonomio
	
	public static Monomio prossimoMonomio( StringTokenizer st ) {
		int coeff=0, grado=0;
		int segno=st.nextToken().charAt(0)=='+'? 1 : -1;
		String tk=st.nextToken();
		if( tk.matches("\\d+") ) {
			coeff=Integer.parseInt(tk);
		}
		else if( tk.matches("x(\\^\\d+)?") ) {
			coeff=1;
			int i=tk.indexOf('^');
			if( i>=0 ) 
				grado=Integer.parseInt(tk.substring(i+1));
			else
				grado=1;
		}
		else {
			int i=tk.indexOf('x');
			coeff=Integer.parseInt( tk.substring(0,i) );
			i=tk.indexOf('^',i+1);
			if( i>=0 )
				grado=Integer.parseInt( tk.substring(i+1) );
			else
				grado=1;
		}
		coeff=coeff*segno;
		return new Monomio(coeff,grado);
	}//prossimoMonomio
	
	public static void main( String[] args ) {
		
		String MONOMIO="(\\d+|x(\\^\\d+)?|\\d+x(\\^\\d+)?)";
		String SEGNO="[\\+\\-]";
		String POLINOMIO="\\-?"+MONOMIO+"("+SEGNO+MONOMIO+")*";
		
		Scanner sc=new Scanner( System.in );
		System.out.println("Inserire un polinomio dopo ogni > o solo INVIO per terminare");
		for(;;) {
			System.out.print("> ");
			String input=sc.nextLine();
			if( input.length()==0 ) break;
			if( input.matches(POLINOMIO) ) {
				Polinomio p=new PolinomioLL();
				
				StringTokenizer st=new StringTokenizer(input,"+-",true);
				
				p.add( primoMonomio(st) );
				while( st.hasMoreTokens() ) {
					p.add( prossimoMonomio(st) );
				}
				
				System.out.println(p);
			}
			else
				System.out.println(input+" non e' un polinomio.");
		}
		sc.close();
		System.out.println("Bye.");
	}
}//poo.polinomi.InputPolinomio
