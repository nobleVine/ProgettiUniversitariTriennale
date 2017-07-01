import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;


public class SAT {
	
	/* Dichiarazione attributi: numVar la massima quantità di letterali presenti in una clausola (senza distinzione tra negati e non);
	 * 							numClaus è il totale numero di clausole della formula;
	 * 							formula[][] è appunto la matrice contenente la formula letta da file;
	 * 							binarie[] è l'array contenente la valutazione generata;
	 * 							valut[] è l'array contenente la stringa binaria che l'algoritmo goloso deve opportunamente modificare e restituire. */
	public int numVar, numClaus;
	public int[][] formula;
	public int[] binarie;
	public boolean[] valut;
 	public int [] infoClausole;

	/* Metodo lettura da file. */
	public boolean processFile (String fileName) {
		try {
			File inFile = new File (fileName);
			BufferedReader br = new BufferedReader (new FileReader (inFile));
			String line = br.readLine(); /* legge la prima riga */
			StringTokenizer lineTokens = new StringTokenizer (line, " "); /* tokenizza la prima riga */
			numVar = Integer.parseInt (lineTokens.nextToken()); /* trasforma il primo token in intero e lo assegna a numVar */
			binarie = new int[numVar]; /* inizializza l'array di binarie */
			numClaus = Integer.parseInt(lineTokens.nextToken()); /* trasforma il Math.abs(clauses[i][2])o token in intero e lo assegna a numClaus */
			formula = new int[numClaus][]; /* assegno alla matrice un numero di righe pari al numero di clausole */
			for (int i = 0; i < numClaus; i++) { /* inizializzo la matrice con un for */
				line = br.readLine(); /* leggo la linea riciclando la variabile line */
				lineTokens = new StringTokenizer (line, " "); /* tokenizzo la linea riciclando la variabile lineTokens */
				int count = lineTokens.countTokens(); /* conta il numero di tokens nella linea ovvero il numero di variabili nella clausola */
			    formula[i] = new int[count]; /* creo un array grande quanto il numero di token e lo assegno alla matrice per definire di volta in volta il numero di colonne */
			    for (int j = 0; j < count; j++) { /* uso un altro for per riempirlo */
				formula[i][j] = Integer.parseInt (lineTokens.nextToken()); /* riempio la matrice */
				}
			}
			br.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/* Metodo generazione esaustiva stringhe binarie (valutazioni). */
	public void generaBinarie() {
		int i = numVar - 1;
		while (i > -1 && binarie[i] == 1) {
			binarie[i] = 0;
			i--;
		}
		if (i > -1) {
			binarie[i] = 1;
		}
	}
	
	/* Metodo risoluzione primo esercizio. */
	public boolean exhsat (String fileName) {
		
		/* Condizione verificata se viene riscontrato un errore nella lettura da file. */
		if (!processFile (fileName)) {
			System.out.println ("Errore nel file!");
			return false;
		} else {
			
			/* Dichiarazione variabili: i, j sono gli indici;
			 * 							cont è il contatore di clausole soddisfatte;
			 * 							lim è il massimo numero di stringhe binarie generabili;
			 * 							contBinarie tiene il conto di quante stringhe binarie sono state generate. */
			int i = 0, j = 0, cont = 0, lim = (int) Math.pow (2.0, numVar), contBinarie = 1;
			
			/* Il blocco si ripete al più affinché non sono state generate tutte le stringhe binarie. */
			while (contBinarie < lim) {
				/* Il blocco si ripete al più fino alla lettura di ogni letterale di ogni clausola. */
				while ((i < formula.length) && (j < formula[i].length)) {
					/* Condizione verificata se il letterale in esame è soddisfatto per la valutazione in uso. */
					if ((binarie[(Math.abs(formula[i][j])) - 1] > 0 && formula[i][j] > 0)
							|| (binarie[(Math.abs(formula[i][j])) - 1] == 0 && formula[i][j] < 0)) {
						
						cont++;
						/* Si passa alla clausola successiva (riga successiva della matrice). */
						i++;
						/* Si torna al primo letterale della clausola (colonna di indice 0 della matrice). */
						j = 0;
					} else {
						/* Si passa al letterale successivo (colonnna successiva della matrice). */
						j++;
					}
				}
				
				/* Condizione verificata se il numero delle clausole sino a qui soddisfatte eguaglia il numero di clausole totale del file. */
				if (cont == numClaus) {
					return true;
				}
				
				/* Condizione verificata se controllati tutti i letterali di una data clausola e questa non risulta soddisfacibile. */
				if (j == formula[i].length){
					cont=0;
					/* Viene generata una nuova stringa binaria. */
					generaBinarie();
					/* Si incrementa il contatore delle stringhe generate. */
					contBinarie++;
					/* Si settano gli indici a 0 per tornare all'esamina del primo letterale della prima clausola. */
					i = 0;
					j = 0;
				}
			}
			
			/* Arrivati a questo punto significa che nessuna valutazione soddisfa ogni clausola della formula. */
			return false;
		}
	}
	
	/* Metodo risoluzione Math.abs(clauses[i][2])o esercizio. */
	public int maxsat (String fileName) {
		
		/* Condizione verificata se viene riscontrato un errore nella lettura da file. */
		if (!processFile (fileName)) {
			System.out.println ("Errore nel file!");
			return -1;
		} else {
			
			/* Dichiarazione variabili: i, j sono gli indici;
			 * 							maxClaus è il massimo numero di clausole soddisfatte;
			 * 							contClaus è il numero di clausole soddisfatte per una data valutazione;
			 * 							totBinarie è il massimo numero di stringhe binarie generabili;
			 * 							contBinarie tiene il conto di quante stringhe binarie sono state generate;
			 * 							satisfied viene settata a true se la clausola in esame è soddisfatta. */
			int i = 0, j = 0, maxClaus = 0, contClaus = 0, totBinarie = (int) Math.pow (2, numVar), contBinarie = 0;
			boolean satisfied = false;
			
			/* Il blocco si ripete al più affinché non sono state generate tutte le stringhe binarie. */
			while (contBinarie < totBinarie) {
				/* Il blocco si ripete fino a che ogni clausola non è stata esaminata. */
				for (i = 0; i < formula.length; i++) {
					/* Ogni volta che si passa alla clausola successiva j viene settato a 0 per tornare al primo letterale. */
					j = 0;
					/* Il blocco si ripete al più fino all'esamina dell'ultimo letterale della clausola o finché questa risulta non soddisfatta. */
					while ((j < formula[i].length) && (!satisfied)) {
						/* Condizione verificata se il letterale in esame è soddisfatto per la valutazione in uso. */
						if ((binarie[(Math.abs(formula[i][j])) - 1] > 0 && formula[i][j] > 0)
								|| (binarie[(Math.abs(formula[i][j])) - 1] == 0 && formula[i][j] < 0)) {
							satisfied = true;
							contClaus++;
						} else {
							/* Si passa al letterale successivo (colonnna successiva della matrice). */
							j++;
						}
					}
					satisfied = false;
				}
				
				/* Viene generata una nuova stringa binaria. */
				generaBinarie();
				/* Si incrementa il contatore delle stringhe generate. */
				contBinarie++;
				
				/* Condizione verificata se il numero fin qui di clausole soddisfatte è maggiore di quello che fino a ora era il massimo numero di clausole soddisfatte. */
				if (contClaus > maxClaus){
					/* Si aggiorna il massimo. */
					maxClaus = contClaus;
				}
				
				/* Nel caso che il massimo numero di clausole soddisfatte sia uguale al numero totale di clausole della formula possiamo terminare. */
				if (maxClaus == numClaus){
					return maxClaus;
				}
				contClaus = 0;
			}
			return maxClaus;
		}
	}
	
	/* Metodo contenente algoritmo goloso per risoluzione terzo esercizio. */
	public void greedyAlgorithm (String fileName) {
			
		/* Dichiarazione variabili e strutture dati: letter[] è l'array rappresentante l'insieme di tutti i letterali;
		 * 											 occorr[] è l'array contenente le varie frequenze di ogni letterale all'interno della formula;
		 * 											 clausole[] è l'array rappresentante l'insieme di tutte le clausole: ogni suo elemento rappresenta la corrispondente clausola nella formula (se clausole[i] = -1, significa che tale clausola è stata cancellata);
		 * 											 i e j sono gli indici per la scansione di array e matrici;
		 * 											 v è un intero utilizzato per l'inizializzazione dell'array variab;
		 * 											 cont è un contatore utilizzato nell'inizializzazione di occorr;
		 * 											 indexO è un intero utilizzato per il salvataggio dell'indice relativo all'occorrenza più frequente;
		 * 											 maxO corrisponde al letterale più frequente all'interno della matrice;
		 * 											 l è il letterale più frequente (con la casistica di più letterali maggiormente frequenti già considerata);
		 * 											 empty è un flag usato per la ripetizione del procedimento in caso ci siano ancora clausole non vuote;
		 * 											 found è un flag usato per migliorare l'efficienza dell'algoritmo all'interno del quale è contenuto. */
		int[] letter = new int[numVar * 2], occorr = new int[numVar * 2], clausole = new int[numClaus];
		int i = 0, j = 0, cont = 0, indexO = 0, maxO = 0, l = 0, v = numVar * -1, maxJ = 0;
		boolean empty = false, found = false, satisfied = false, dead = false;
		
		valut = new boolean[numVar];

		for (i = 0; i < numClaus; i++) {
			for (j = 0; j < formula[i].length; j++) {
				if (j > maxJ) {
					maxJ = j;
				}
			}
		}		
		int[][] copy = new int[numClaus][maxJ + 1];		
		for (i = 0; i < numClaus; i++) {
			for (j = 0; j < formula[i].length; j++) {
				copy[i][j] = formula[i][j];
			}
		}
		
		/* Inizializzazione dell'array delle valutazioni (punto 1 dellesercizio). */
		for (i = 0; i < numVar; i++) {
			valut[i] = true;
		}
		
		/* Inizializzazione dell'array dell'insieme delle clausole. */
		for (i = 0; i < numClaus; i++) {
			clausole[i] = 1;
		}
		
		/* Inizializzazione dell'array dell'insieme dei letterali. */
		for (i = 0; i < numVar * 2; i++) {
			letter[i] = v;
			if (v + 1 != 0) {
				v++;
			} else {
				v += 2;
			}
		}
		
		/* Questo blocco si ripete finché ogni clausola della matrice non risulta vuota (ogni elemento di clausole non è uguale a -1). */
		while (!empty) {
			
			/* Inizializzazione dell'array contenente le varie occorrenze dei diversi letterali (occorr[]). */
			cont = 0;
			while (cont < letter.length) {
				for (i = 0; i < numClaus; i++) {
					if ((clausole[i] != -1) && (clausole[i] != 0)) {
						found = false;
						j = 0;
						while ((j < formula[i].length) && (!found)) {
							if (copy[i][j] == letter[cont]) {
								found = true;
								occorr[cont]++;
							}
							j++;
						}
					}
				}
				cont++;
			}
			
			/* Questo blocco trova il massimo valore all'interno di occorr salvando l'indice del letterale corrispondente. */
			maxO = occorr[0];
			indexO = 0;
			for (i = 1; i < occorr.length; i++) {
				if (occorr[i] > maxO) {
					maxO = occorr[i];
					indexO = i;
				}
			}
			
			/* Questo blocco in caso di più letterali maggiormente presenti nella formula, fa si che venga scelto quello di indice minore e, in caso di opposti, quello positivo. */
			for (i = 0; i < numVar * 2; i++) {
				if (occorr[i] == maxO) {
					if (Math.abs(letter[i]) < Math.abs(letter[indexO])) {
						indexO = i;
					} else {
						if (Math.abs(letter[i]) == Math.abs(letter[indexO])) {
							if (letter[i] > 0) {
								indexO = i;
							}
						}
					}
				}
			}
			
			/* Questa assegnazione fa sì che il letterale più frequente non venga riscelto al ciclo successivo. */
			occorr[indexO] = 0;
			
			l = letter[indexO];
			
			/* Se la condizione è verificata si va a modificare la valutazione relativa nell'array valut[]. */
			if (l < 0) {
				valut[Math.abs(l) - 1] = false;
			}
			
			/* Questo blocco elimina ogni clausola contenente l. */
			for (i = 0; i < numClaus; i++) {
				if ((clausole[i] != -1) && (clausole[i] != 0)) {
					j = 0;
					satisfied = false;
					while ((j < formula[i].length) && (!satisfied)) {
						if (copy[i][j] == l) {
							clausole[i] = -1;
							satisfied = true;
						}
						j++;
					}
				}
			}
			
			/* Questo blocco elimina i letterali opposti ad l (-l). */
			for (i = 0; i < numClaus; i++) {
				if ((clausole[i] != -1) && (clausole[i] != 0)) {
					satisfied = false;
					j = 0;
					while ((j < formula[i].length) && (!satisfied)) {
						if (copy[i][j] * -1 == l) {
							copy[i][j] = 0;
							satisfied = true;
						}
						j++;
					}
				}
			}
			
			for (i = 0; i < numClaus; i++) {
				if ((clausole[i] != -1) && (clausole[i] != 0)) {
					j = 0;
					cont = 0;
					dead = false;
					while ((j < formula[i].length) && (!dead)) {
						if (copy[i][j] == 0) {
							cont++;
						}
						j++;
					}
					if (cont == formula[i].length) {
						clausole[i] = 0;
					}
				}
			}
			
			empty = true;
			
			for (i = 0; i < occorr.length; i++) {
				occorr[i] = 0;
			}
			
			/* Questo blocco fa si che tutta la procedura si ripeta per eliminare le clausole non vuote rimaste in caso, appunto, siano ancora presenti. */
			i = 0;
			while ((i < numClaus) && (empty)) {
				if ((clausole[i] != -1) && (clausole[i] != 0)) {
					empty = false;
				}
				i++;
			}
		}
	}
	
	/* Metodo risoluzione terzo esercizio. */
	public int greedysat (String fileName) {
		
		/* Condizione verificata se viene riscontrato un errore nella lettura da file. */
		if (!processFile (fileName)) {
			System.out.println ("Errore nel file!");
			return -1;
		} else {
			
			/* Dichiarazione variabili: i, j sono gli indici;
			 * 							contClaus è il numero di clausole soddisfatte per una data valutazione;
			 * 							satisfied viene settata a true se la clausola in esame è soddisfatta;
			 * 							goloso[] è l'array corrispondente a valut, con la differenza che invece di essere di tipo boolean è di tipo integer. */
			int i = 0, j = 0, contClaus = 0;
			boolean satisfied = false;
			int[] goloso = new int[numVar];
			
			/* Richiamo procedura algoritmo goloso. */
			greedyAlgorithm (fileName);
			
			/* Inizializzazione array goloso[]. */
			for (i = 0; i < numVar; i++) {
				if (valut[i] == false) {
					goloso[i] = 0;
				} else {
					goloso[i] = 1;
				}
			}
			
			/* Il blocco si ripete fino a che ogni clausola non è stata esaminata. */
			for (i = 0; i < numClaus; i++) {
				/* Ogni volta che si passa alla clausola successiva j viene settato a 0 per tornare al primo letterale. */
				j = 0;
				/* Il blocco si ripete al più fino all'esamina dell'ultimo letterale della clausola o finché questa risulta non soddisfatta. */
				while ((j < formula[i].length) && (!satisfied)) {
					/* Condizione verificata se il letterale in esame è soddisfatto per la valutazione in uso. */
					if (((goloso[(Math.abs(formula[i][j])) - 1] == 1) && (formula[i][j] > 0))
							|| ((goloso[(Math.abs(formula[i][j])) - 1] == 0) && (formula[i][j] < 0))) {
						satisfied = true;
						contClaus++;
					} else {
						/* Si passa al letterale successivo (colonnna successiva della matrice). */
						j++;
					}
				}
				satisfied = false;
			}
			
			return contClaus;
		}
	}

	
	
	/* Metodo lettura da file e creazione tavola di verità. */
	public int[] letturaFNC (String in) {
		try {
			File inFile = new File(in);
			BufferedReader br = new BufferedReader (new FileReader (inFile));
			String line = br.readLine();
			numVar = Integer.parseInt(line);
			int[] tv = new int[(int) Math.pow(2, numVar)];
			for (int i = 0; i < Math.pow(2, numVar); i++) {
				line = br.readLine();
				tv[i] = Integer.parseInt(line);
			}
			br.close();
			return tv;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/* Metodo risoluzione quinto esercizio. */
	public void fnc (String in, String out) {
		try {
			File outfile = new File (out);
			BufferedWriter b = new BufferedWriter (new FileWriter (outfile));
			b.write ("numero di variabili: " + numVar + "\n");
			int [] tv = letturaFNC (in);
			for (int i = 0; i < tv.length; i++) {
				if (tv[i] == 0) {
					numClaus++;
				}
			}
			b.write ("numero di clausole soddisfatte: " + numClaus + "\n");
			binarie = new int[numVar];
			for (int i = 0; i < numVar; i++) {
				binarie[i] = 0;
			}
			for (int i = 0; i < tv.length; i++) {            
				if (tv[i] == 0) {
					for (int j = 0; j < numVar; j++) {
						if (binarie[j] == 0) {
							b.write ((j + 1) + " ; ");
						} else {
							b.write (-(j + 1) + " ; ");
						}
					}
					b.write ("\n");
				}
				generaBinarie();
			}
			b.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		
}