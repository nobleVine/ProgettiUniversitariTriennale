# Marco Vignini, Manuel Ronca e Luigi Favaro.
# 5444851, 5094658, 5251492
# marco.vignini@stud.unifi.it, manuel.ronca@stud.unifi.it, luigi.favaro@stud.unifi.it
# Data Di Consegna: 25/ 05/ 2015 
# A.a 2014 - 2015.
# Esercizio 1: Identificatore di sotto-sequenze.

.data
codificaX: .space 10          									# Array di codifica per la X. Gli space vanno messi in alto per non farli sfalzare a non multipli di 4.
sequenzaDaFile: .space 100										# Posso leggere fino a 100 caratteri da file.
welcome: .asciiz "L'intero che verra' chiesto di inserire dovra essere compreso/uguale tra -511 e 511, in caso contrario verra' richiesto l'inserimento stesso finche' tale condizione non sara' rispettata.\n"
input: .asciiz "\nInserire il valore di X --> "
file: .asciiz "sequenza.txt"
fnt: .asciiz "File non trovato"
indiciMS: .asciiz "\nCaso MS - Elenco indici di inizio occorrenza: "
indiciC1: .asciiz "\nCaso C1 - Elenco indici di inizio occorrenza: "
indiciC2: .asciiz "\nCaso C2 - Elenco indico di inizio occorrenza: "
noOccorrenze: .asciiz "Nessuna occorrenza"
spazio: .asciiz " "
erroreFile2: .asciiz "La sequenza presente nel file risulta di lunghezza minore di 10 o maggiore di 100, quindi modifica il file sequenza.txt"

.text
.globl main

main:
	
	addi $sp, $sp, -32								 # Il main è un chiamato (QtSpim fa jal main) e quindi alloco nello stack i registri preservati che voglio usare e il return address.
	sw $s0, 0($sp)
	sw $s1, 4($sp)
	sw $s2, 8($sp)
	sw $s3, 12($sp)
	sw $s4, 16($sp)
	sw $s5, 20($sp)
	sw $s6, 24($sp)
	sw $ra, 28($sp)
	
	li $s5, 1
	li $s6, 0											# Contatore per la stampa delle occorrenze, servira' in fondo al programma.
	
	li $v0, 13											# Apertura del file( system call ).
	la $a0, file										# Carico il nome del file in questione.
	li $a1, 0											# 0 = lettura file. 1 = scrittura file.
	li $a2, 0											# Lunghezza file (ignorata in questo caso = 0).
	syscall
	
	move $t6, $v0										# Salvo il file per verificarne la correttezza.
	
	blt $v0, 0, errore
	
	li $v0, 14											# Leggo il file.
	move $a0, $t6
	la $a1, sequenzaDaFile								# Indirizzo della sequenza da file.
	li $a2, 100					    					# Ora specifico la lunghezza che mi interessa. La sequenza da file può essere lunga massimo 100 caratteri.
	syscall
	
	controlloLunghezza:

			la $s1, sequenzaDaFile                      # Carico l'indirizzo della sequenza da file.
            move $s3, $s1								# Mi servira' una copia dell'indirizzo della sequenza da File.
            li $s4, 0									# Contatore della lunghezza del sequenzaDaFile.
			
			nextCh: lb $t3,($s1)  						# Leggo un carattere della stringa (pseudoistruzione).
				beqz $t3, controlloErroreLunghezza		# Se è zero ho finito (pseudoistruzione).
				add $s4, $s4, 1  						# Incremento il contatore (lunghezza della stringa).
				add $s1, $s1, 1  						# incremento la posizione sulla stringa(indirizzo).
			j nextCh 
	
	controlloErroreLunghezza:							# La lunghezza della stringa da file deve essere compresa/uguale tra 10 e 100. 
	
	li $t1, 10
	bge $s4, $t1, controlloLunghezza2
	
	j errore2
	
controlloLunghezza2:

	blt $s4, 100, chiusuraFile							# Dopo aver fatto anche il secondo controllo chiudo il file.
	
	j errore2

	chiusuraFile:
	
		li	$v0, 16										# Chiusura del file.
		move $a0, $t6				
		syscall
		
		j inizio

	errore:
	
			li	$v0, 4									# File non trovato ( stampa la stringa che se ne occupa ).
			la	$a0, fnt	
			syscall
			
			j exit
			
	errore2:
	
			li $v0, 4
			la $a0, erroreFile2							# Stringa che avverte che il tipo di errore e' quello riguardante la lunghezza della stringa contenuta nel file.
			syscall
	
			li	$v0, 16									# Chiusura del file.
			move $a0, $t6				
			syscall
		
			j exit
	
	inizio:	
	
			li $v0, 4									# Istruzioni per l'uso.
			la $a0, welcome
			syscall
	
	inserimento:
			
			li $v0, 4									# Richiesta dell'inserimento dell'input.
			la $a0, input
			syscall
	
			li $v0, 5									# Lettura di X.
			syscall
			
			
			slti $t0, $v0, -511							# Se $v0 < -511 metto $t0 = 1. Controllo sul valore inserito(deve essere compreso tra -511 e 511). Principio di progetto: uso direttamente le costanti per incrementare la velocità d'esecuzione.
			li $t1, 1									# Mi serve il controllo con la branch if equal.
			beq $t0, $t1, inserimento					# Input sbagliato, faccio reinserire all'utente l'intero. Perchè se $t0 = 1 --> $v0 < -511.
			li $t2, 511									# Riuso $t1 per risparmiare registri.
			slt $t0, $t2, $v0							# Controllo che l'intero sia <= di 511.
			beq $t0, $t1, inserimento					# Input sbagliato, faccio reinserire all'utente l'intero. Perchè se $t0 = 1 --> $v0 > 511.
	
			move $t0, $a1								# Mi serve l'indirizzo del sequenzaDaFile per scorrerlo.
			move $t1, $v0								# X.
			la $t2, codificaX							# Indirizzo dell'array codificaX.
			move $s2, $t2

	Start:
			
			addi $s4, $s4, -9                           # Lunghezza = Lunghezza - 9, dato che l'ultima sotto-sequenza di lunghezza 10 puo' cominciare da LunghezzaSequenza - 9.
			li $t3, 2									# Si usa nella divisione per 2.
			li $t7, 9									# Fine dell'array.
			move $s0, $t1								# Lo salvo perche' nel corso del programma mi riservira'.
			
			bne $t1, $zero, NonZero
			
			j inizAlgoritmoZeri							# 0 --> Ha due codifiche in C1 ed in MS quindi va trattato a parte.
					
			NonZero:									# Tutti gli altri casi.
			
			codificaMS:							   		# Metodo delle divisioni ripetute.
			
						div $t1, $t3					# Divido X per 2.
						mflo $t1						# Metto il quoziente in $t1 per accumularlo.
						mfhi $t4						# Metto il resto in $t4.
						bne $t4, $zero, codifica1  	
						li $t8, 48						# Codifiche ascii dello 0 e dell'1.
						sb $t8, ($t2)					# Scrittura nell'array codificaX.
						addi $t9, $t9, 1				# Contatore per arrivare alla fine della stringa.
						addi $t2, $t2, 1				# Increcremento dell'indirizzo.
						beq $t9, $t7, codificaSegno		# Fine delle divisioni ripetute perche' il quoziente e' zero.
						j codificaMS
						
		        codifica1:
				 
						li $t8, 49
						sb $t8, ($t2)					# Scrittura nell'array codificaX.
						addi $t9,$t9, 1					# Contatore per arrivare alla fine della stringa.
						addi $t2, $t2, 1				# Increcremento dell'indirizzo.
						beq $t9, $t7, codificaSegno		# Fine delle divisioni ripetute perche' il quoziente e' zero.
						j codificaMS
						
			codificaSegno:
			
						blt $s0, $zero, segnoArrayRestiNeg		# Segno del numero.
						li $t8, 48								# Per il caso positivo.
						sb $t8, ($t2)							# Segno positivo.
						
						li $v0, 4							    # Caso MS - Elenco indici di inizio occorrenza.
                        la $a0, indiciMS
                        syscall
						
						j reinizializzazionePerRicercaIndici	# Vado a ricercare gli indici.
						
			segnoArrayRestiNeg:
						
			            li $t8, 49								# Corrispondente di 1 in ascii. 
				        sb $t8, ($t2)							# Segno negativo.
						
						li $v0, 4							    # Caso MS - Elenco indici di inizio occorrenza.
                        la $a0, indiciMS
                        syscall
						
						j reinizializzazionePerRicercaIndici	# Vado a ricercare gli indici.
                        
			
			reinizializzazioneC2:						# Per fare il complemento a due
						
						addi $s6, $s6, 1				# $s6 = 1 --> Sto facendo il C2.
						
						li $v0, 4						# Caso C2 - Elenco indici di inizio occorrenza.
                        la $a0, indiciC2
                        syscall
						
						move $t1, $s0					# X.
						li $t3, 2
						move $t2, $s2	
						li $t9, 1						# Contatore per arrivare a fine della stringa.
						li $t0, 0						# Contatore che mi dice se trovo ho trovato l'1 oppure no.
						li $t7, 10
												
						blt $t1, $zero, C2negativo		# Controllo sul segno.

				codificaC2:
				
						div $t1, $t3					# Divido X per 2.
						mflo $t1						# Metto il quoziente in $t1 per accumularlo.
						mfhi $t4						# Metto il resto in $t4.
						bne $t4, $zero, codifica2C2  	
						li $t8, 48						# Codifiche ascii dello 0 e dell'1.
						sb $t8, ($t2)					# Scrittura nell'array di codificaX(codifica).
						addi $t9, $t9, 1				# Contatore per arrivare alla fine della stringa.
						addi $t2, $t2, 1				# Decremento dell'indirizzo, devo scorrere la parola al contrario(metodo 3 per il C2).
						beq $t9, $t7, reinizializzazionePerRicercaIndici				# Fine delle divisioni ripetute perche' il quoziente e' zero.
						
				j codificaC2 
				
				codifica2C2:							# Scrive 1 nel caso positivo.
				
						li $t8, 49
						sb $t8, ($t2)					# Scrittura nell'array di codificaX(codifica).
						addi $t9, $t9, 1				# Contatore per arrivare alla fine della stringa.
						addi $t2, $t2, 1				# Decremento dell'indirizzo.
						beq $t9, $t7, reinizializzazionePerRicercaIndici		        # Fine delle divisioni ripetute perche' il quoziente e' zero.
						
				j codificaC2
						
				C2negativo:								# Caso Negativo del C2
						
						div $t1, $t3					# Divido X per 2.
						mflo $t1						# Metto il quoziente in $t1 per accumularlo.
						mfhi $t4						# Metto il resto in $t4.
						beq $t4, $zero, controllo0
						j controllo1
						
				scriviUno:
						
						li $t8, 49						# Codifiche ascii dello 0 e dell'1.
						sb $t8, ($t2)					# Scrittura nell'array di codificaX(codifica).
						addi $t0, $t0, 1				# Segno di aver trovato almeno un 1.
						addi $t9,$t9, 1					# Contatore per arrivare alla fine della stringa.
						addi $t2, $t2, 1				# Incremento dell'indirizzo.
						beq $t9, $t7, reinizializzazionePerRicercaIndici              # Fine delle divisioni ripetute perche' il quoziente e' zero.
						
				j C2negativo
				
				scriviZero:
						
						li $t8, 48
						sb $t8, ($t2)					# Scrittura nell'array di codificaX(codifica).
						addi $t9,$t9, 1					# Contatore per arrivare alla fine della stringa.
						addi $t2, $t2, 1				# Decremento dell'indirizzo.
						beq $t9, $t7, reinizializzazionePerRicercaIndici				# Fine delle divisioni ripetute perche' il quoziente e' zero.
										
				j C2negativo
				
				controllo0:
							beq $t0, $zero, scriviZero	# $t0 = 0 --> Non ho ancora trovato un 1 quindi scrivo i bit normali. 
							j scriviUno					# Complemento i bit.
							
				controllo1:
							beq $t0, $zero, scriviUno	# $t0 = 0 --> Non ho ancora trovato degli 1 quindiu scrivo i bit normali.
							j scriviZero				# Complemento i bit.
			
			reinizializzazioneC1:
								
						addi $s6, $s6, 1				# $s6 = 2 --> Sto facendo il C1.
																
						li $v0, 4						# Caso C1 - Elenco indici di inizio occorrenza.
						la $a0, indiciC1
						syscall
								
						li $t7, 10
						move $t1, $s0
						li $t3, 2
						move $t2, $s2
						li $t9, 1	
		      
						blt $t1, $zero, C1negativo

			codificaC1:
			
						div $t1, $t3									# Divido X per 2.
						mflo $t1										# Metto il quoziente in $t1 per accumularlo.
						mfhi $t4										# Metto il resto in $t4.
						bne $t4, $zero,codifica1C1  	
						li $t8, 48										# Codifiche ascii dello 0 e dell'1.
						sb $t8, ($t2)									# Scrittura nell'array di codificaX(codifica).
						addi $t9,$t9, 1									# Contatore per arrivare alla fine della stringa.
						addi $t2, $t2, 1								# Incremento dell'indirizzo.
						beq $t9, $t7, reinizializzazionePerRicercaIndici				# Fine delle divisioni ripetute perche' il quoziente e' zero.
						j codificaC1 
									
			codifica1C1:
			
						li $t8, 49
						sb $t8, ($t2)									# Scrittura nell'array di codificaX(codifica).
						addi $t9,$t9, 1									# Contatore per arrivare alla fine della stringa.
						addi $t2, $t2, 1								# Incremento dell'indirizzo.
						beq $t9, $t7, reinizializzazionePerRicercaIndici		        # Fine delle divisioni ripetute perche' il quoziente e' zero.
						j codificaC1

			C1negativo:							   						# Metodo delle divisioni ripetute.
			
						div $t1, $t3									# Divido X per 2.
						mflo $t1										# Metto il quoziente in $t1 per accumularlo.
						mfhi $t4										# Metto il resto in $t4.
						bne $t4, $zero, codifica0 	
						li $t8, 49										# Codifiche ascii dello 0 e dell'1.
						sb $t8, ($t2)									# Scrittura nell'array di codificaX(codifica).
						addi $t9, $t9, 1								# Contatore per arrivare alla fine della stringa.
						addi $t2, $t2, 1								# Incremento dell'indirizzo.
						beq $t9, $t7, reinizializzazionePerRicercaIndici              # Fine delle divisioni ripetute perche' il quoziente e' zero.
						j C1negativo
		         
				 codifica0:
				 
						li $t8, 48
						sb $t8, ($t2)									   # Scrittura nell'array di codificaX(codifica).
						addi $t9, $t9, 1								   # Contatore per arrivare alla fine della stringa.
						addi $t2, $t2, 1								   # Incremento dell'indirizzo.
						beq $t9, $t7, reinizializzazionePerRicercaIndici   # Fine delle divisioni ripetute perche' il quoziente e' zero.
						j C1negativo			
						
				
				reinizializzazionePerRicercaIndici:
                                                                                                                                                                
                            move $s1, $s3                        			# Riporto sequenzaDaFile al suo indirizzo originale.              
                            li $t3, 1                            			# Indice di partenza sequenzaDaFile.
                            li $t4, 10										# Fine sotto-sequenza.
							li $t7, 0										# Contatore che serve ad indicare se ci sono state occorrenze.
																												
						w1:
                            
							bgt $t3, $s4, nessunaOccorrenza					 # Fine scorrimento SequenzaDaFile.
                            li $t1, 0                                        # $t1 = contatoregiusti.
						                                                 
						w2:                    
                           
						   beq $t1, $t4, stampaOccorrenze
                           lb  $t5, ($t2)                                    # Prende l'ultimo carattere poichè la codifica è memorizzata al contrario.                        
                           lb  $t6, ($s1)                                    # Primo carattere della sequenza da file.
                           bne $t5, $t6, AumentaIndiceOcc                    # Se non sono uguali vado ad aumentare l'indice.
                           addi $t1, $t1, 1                                  # Aumento del contatoreGiusti.
                           addi $t2, $t2, -1                                 # Decremento l'indice della sotto-sequenza.
                           addi $s1, $s1, 1                                  # Punto al carattere successivo della sequenza da file
						   
                        j w2
                         
                        AumentaIndiceOcc:
                            
							sub  $s1, $s1, $t1								 # Aggiornamento indirizzo del sequenzaDaFile.
                            addi $s1, $s1, 1					
						    addi $t3, $t3, 1								 # Incremento l'indice della sequenza da file.
							move $t2, $s2
                            addi $t2, $t2, 9
                            
						j w1
                        
						stampaOccorrenze:
                                
								addi $t7, $t7, 1                            # N° occorrenze, per capire se stampare la stringa che indica che non ci sono occorrenze.
                                  
                                move $a0, $t3                               # Stampo l' occorrenza corrente.
                                li $v0, 1
                                syscall
                                                
								li $v0, 4									# Spazio.
                                la $a0, spazio
                                syscall

                                addi $t3, $t3, 1							# reinizializzazionePerRicercaIndici indici e indirizzi.
                                
								addi $t2, $s2, 9
                                sub $s1, $s1, $t1	
                                addi $s1, $s1, 1							# Scorro la sequenza da file.
                               
						j w1											    # Continua a cercare occorrenze.

						nessunaOccorrenza:
								
								beq $t7, $zero, controlloStampaOcc			# Questo controllo va fatto altrimenti in caso di C2 senza occorrenza non stampa la mancanza della occorrenza appunto.
								beq $s6, $zero, reinizializzazioneC2		# Da MS a C2.		 			  
								beq $s6, $s5, reinizializzazioneC1		    # Da C2 a C1.
								bgt $s6, $s5, exit							# Ho gia' fatto C1.
								
						controlloStampaOcc:
								
								li $v0, 4									# Nessuna occorrenza
								la $a0, noOccorrenze
								syscall
						
								# $s6 = 0 --> MS
								# $s6 = 1 --> C2
								# $s6 = 2 --> C1
								
								beq $s6, $zero, reinizializzazioneC2
								beq $s6, $s5, reinizializzazioneC1
								
								# Per C1 non ho bisogno di indicare il salto perche' devo finire.
								
				exit:	
						
						lw $s0, 0($sp)			 # Il chiamato ripristina i registri caller - saved che ha utilizzato e torna al chiamante(QtSpim).
						lw $s1, 4($sp)
						lw $s2, 8($sp)
						lw $s3, 12($sp)
						lw $s4, 16($sp)
						lw $s5, 20($sp)
						lw $s6, 24($sp)
						lw $ra, 28($sp)
						addi $sp, $sp, 32
						jr $ra
			
			inizAlgoritmoZeri:
			
					li $s5, 0
					li $s6, 0
					li $t1, 0						# Contatore per nessuna occorrenza.
								
			algoritmoZeri:
					
					li $s6, 2
					li $t2, 1						# Contatore fisso.
					li $t3, 0						# Contatore dei Giusti.
					li $t6, 10
					move $s1, $s3					# Riporto l'indirizzo della sequenzaDaFile all'inizio.
					li $t7, 48						# Zero in Ascii.
					
					beq $s5, $s6, stampaC2occorrenze
					bgt $s5, $t2, stampaC1occorrenze
					
						li $v0, 4					# Indici Occorrenze MS.
						la $a0, indiciMS
						syscall
						
						j controllo
					
					stampaC2occorrenze:
					
						li $v0, 4
						la $a0, indiciC2			# Indici Occorrenze C2.
						syscall
						
						j controllo
					
					stampaC1occorrenze:
					
						li $v0, 4
						la $a0, indiciC1			# Indici Occorrenze C1.
						syscall
					
					controllo:
					
						bgt $t2, $s4, controlloAlg
					
					loopZeroMS:
						
						beq $t3, $t6, stampaIndiceCercato
						lb $t5, ($s1)				# Prendo il byte successivo dalla sequenza da File.
						bne $t5, $t7, riazzera
						addi $t3, $t3, 1			# Aumento il contatore dei giusti.
						addi $s1, $s1, 1			# Incremento l'indirizzo della sequenzaDaFile.
						
					j loopZeroMS 
					
					riazzera:
						
						addi $t2, $t2, 1			# Incremento l'indice fisso.
						sub $s1, $s1, $t3			# Riporto indietro l'indirizzo.
						li $t3, 0					# Reinizializzo il contatore dei giusti.
						addi $s1, $s1, 1			# Incremento l'indirizzo della SequenzaDaFile.
					
					j controllo
					
					stampaIndiceCercato:
					
						addi $t1, $t1, 1			# Controllo se ho stampato occorrenze oppure no, per restituire su consolle "Nessuna Occorrenza" se necessario.
					
						move $a0, $t2
					
						li $v0, 1					# Stampa occorrenza.
						syscall
						
						li $v0, 4					# Spazio.
						la $a0, spazio
						syscall
						
						addi $s1, $s1, -9			# Ricomincio a scansionare dall'indice fisso successivo a quello stampato per cercare una nuova sottosequenza.
						li $t3, 0					# Riazzero il contatore dei giusti.
						addi $t2, $t2, 1			# Incremento l'indice fisso.
					
					j controllo
					
					controlloAlg:
					
					li $t4, 3
					li $t2, 1
					li $t3, 2
					beq $s5, $zero, reinizializzazioneLoopZeroNegativoMS		# Da MS a MSNegativo.
					beq $s5, $t2, reinizializzazioneZeroC2						# Da MSNegativo a C2.
					beq $s5, $t3, reinizializzazioneLoopZeroPosC1				# Da C2 a C1positivo.
					beq $s5, $t4, reinizializzazioneLoopZeroNegC1				# Da C1positivo a C1negativo.
					
					reinizializzazioneLoopZeroNegativoMS:
					
					addi $s5, $s5, 1
					
					li $t2, 1						# Contatore fisso.
					li $t3, 0						# Contatore dei Giusti.
					move $s1, $s3					# Riporto l'indirizzo della sequenzaDaFile all'inizio.
					li $t7, 48						# Zero in Ascii.
					li $t8, 49						# Uno in Ascii.
					li $t9, 9
										
					primoControllo:					# Perche' se la sequenza non comincia con un 1, non potra' mai essere uno zero negativo nel MS.

							lb $t5, ($s1)			# Prelevo il carattere da file.	
							beq $t5, $t8, loopZeroNegativoMS
							addi $s1, $s1, 1		# Esito di ricerca dell'1 negativo --> Incremento l'indirizzo e il contatore fisso e riazzero il contatore dei giusti.
							addi $t2, $t2, 1
							li $t3, 0
							
					j primoControllo
					
					loopZeroNegativoMS:
					
						bgt $t2, $s4, controlloAlg	# Controllo che serve per quando sono arrivato in fondo a k - 9 della sequenza da file.
						beq $t3, $t9, stampaOccorrenzeZeroNegativoMS
						addi $s1, $s1, 1			# Incremento dell'indirizzo.
						lb $t5, ($s1)				# Leggo dalla sequenza da file.
						bne $t5, $t7, riazzera2		
						addi $t3, $t3, 1			# Incremento il contatore dei giusti.
					
					j loopZeroNegativoMS
					
					riazzera2:
					
						addi $t2, $t2, 1			# Incremento l'indice fisso.
						sub $s1, $s1, $t3			# Riporto indietro l'indirizzo.
						li $t3, 0					# Reinizializzo il contatore dei giusti.
											
					j primoControllo
					
					stampaOccorrenzeZeroNegativoMS:
						
						addi $t1, $t1, 1			# Controllo se ho stampato occorrenze oppure no, per restituire su consolle "Nessuna Occorrenza" se necessario.
						
						move $a0, $t2
					
						li $v0, 1					# Stampa occorrenza.
						syscall
						
						li $v0, 4
						la $a0, spazio
						syscall
						
						addi $s1, $s1, -8			# Ricomincio a scansionare dall'indice fisso successivo a quello stampato.
						li $t3, 0					# Riazzero il contatore dei giusti.
						addi $t2, $t2, 1			# Incremento l'indice fisso.
					
					j primoControllo
					
					reinizializzazioneZeroC2:
					
						addi $s5, $s5, 1 			# ---> Sto facendo C2
						
						bne $t1, $zero, dopo1
						
							li $v0, 4				# Nel caso in cui non siano state trovate occorrenze nel caso precedente.
							la $a0, noOccorrenze
							syscall
						
						dopo1:
						
						li $t1, 0					# Riazzero il contatore che mi serve ad indicare se sono state stampare occorrenze oppure no.
						
						j algoritmoZeri
					
					reinizializzazioneLoopZeroPosC1:
					
					addi $s5, $s5, 1				# $s5 = 3 --> Sto facendo il C1.
					
					bne $t1, $zero, dopo2
						
						li $v0, 4					# Nel caso in cui non siano state trovate occorrenze nel caso precedente.
						la $a0, noOccorrenze
						syscall
						
					dopo2:
					
						li $t1, 0					# Riazzero il contatore che mi serve ad indicare se sono state stampare occorrenze oppure no.
					
					j algoritmoZeri
					
					reinizializzazioneLoopZeroNegC1:
					
					li $t2, 1						# Contatore fisso.
					li $t3, 0						# Contatore dei Giusti.
					li $t6, 10
					move $s1, $s3					# Riporto l'indirizzo della sequenzaDaFile all'inizio.
					li $t7, 49						# Zero in Ascii.
					
					controlloC1Neg:
					
						bgt $t2, $s4, controlloFinale		# Per sapere se non ci sono occorrenze in C1 negativo.
					
					loopZeroC1Neg:
						
						beq $t3, $t6, stampaIndiceCercatoC1Neg
						lb $t5, ($s1)
						bne $t5, $t7, riazzeraC1Neg
						addi $t3, $t3, 1			# Aumento il contatore dei giusti.
						addi $s1, $s1, 1			# Incremento l'indirizzo della sequenzaDaFile.
						
					j loopZeroC1Neg 
					
					riazzeraC1Neg:
						
						addi $t2, $t2, 1			# Incremento l'indice fisso.
						sub $s1, $s1, $t3			# Riporto indietro l'indirizzo.
						li $t3, 0					# Reinizializzo il contatore dei giusti.
						addi $s1, $s1, 1			# Incremento l'indirizzo della SequenzaDaFile.
					
					j controlloC1Neg
					
					stampaIndiceCercatoC1Neg:
						
						addi $t1, $t1, 1			# Controllo se ho stampato occorrenze oppure no, per restituire su consolle "Nessuna Occorrenza" se necessario.
						
						addi $s5, $s5, 1			# --> Sto facendo il C1Negativo.
					
						move $a0, $t2
					
						li $v0, 1					# Stampa occorrenza.
						syscall
						
						li $v0, 4					# Spazio.
						la $a0, spazio
						syscall
						
						addi $s1, $s1, -9			# Ricomincio a scansionare dall'indice fisso successivo a quello stampato.
						li $t3, 0					# Reinizializzo il contatore dei giusti.
						addi $t2, $t2, 1			# Incremento l'indice fisso.
					
					j controlloC1Neg					
					
					controlloFinale:				# Per capire se devo stampare "Nessuna occorrenza" prima di uscire dal programma oppure no.
					
						bne $t1, $zero, exit
						
							li $v0, 4
							la $a0, noOccorrenze
							syscall
						
						j exit	
						
					
					
					
					

						
						
						
						
						
						
		