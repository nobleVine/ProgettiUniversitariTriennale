# Marco Vignini, Manuel Ronca e Luigi Favaro.
# 5444851, 5094658, 5251492
# marco.vignini@stud.unifi.it, manuel.ronca@stud.unifi.it, luigi.favaro@stud.unifi.it
# Data Di Consegna: 25/ 05/ 2015 
# A.a 2014 - 2015.
# Esercizio 3: Matrici.

.data
jump_table: .word a, .b, c, d, e 
matriceA: .asciiz "\nMatrice A:\n"
matriceB: .asciiz "\nMatrice B:\n"
dimensioneM: .asciiz "\nDimensione matrici:\n"
accapo: .asciiz "\n"
choice: .asciiz "\nInserire la scelta - 0: Inserimento di matrici, 1: Somma di matrici, 2: Sottrazione di matrici, 3: Moltiplicazione di matrici, 4: Uscita\n"
inserimentoValore: .asciiz "\nInserire elemento --> "
spazio: .asciiz " "
risSomma: .asciiz "Risultato A + B: "
risSottrazione: .asciiz "Risultato A - B: "
risProdotto: .asciiz "Risultato A * B:\n "
msgUscita: .asciiz "-- Arrivederci --" 
nessunaMatrice: .asciiz "\nNon sono state inserite matrici. Operazione non svolta.\n"

.text
.globl main

main:

	addi $sp, $sp, -24					# Variabili globali messe in registri preservati: Verranno deallocate solamente quando torneremo al chiamante, in questo caso QtSpim( ultimo caso del menu' di scelta).
	sw $s7, 0($sp)						# Testa di A.						
	sw $s6, 4($sp)						# Testa di B.
	sw $s4, 8($sp)						# Bisogna preservare anche la dimensione delle matrici fino il punto a non verrà richiamato per inserirne una nuova.						
	sw $s0, 12($sp)						# Contatore per capire a che matrice siamo arrivati ad inserire.
	sw $ra, 16($sp)
	sw $s5, 20($sp)						# Indirizzo della jump_table che dovrà essere preservato per tutto il programma.
	
	la $s5, jump_table					# Indirizzo iniziale della jump table.
	
scelta:
	
	move $t0, $s5						# Reinizializzo l'indirzzo della jump_table.
	
scelta2:
	
	li $v0, 4							# Richiesta della scelta.
	la $a0, choice
	syscall

	li $v0, 5							# Inserimento della scelta.
	syscall

	move $t2, $v0						# Copio il contenuto di $v0 in $t2.

	blt $t2, $zero, scelta2				# Verifica se n < 0
	li $t1, 4						
	bgt $t2, $t1, scelta2				# Verifica se n > 4

branch_case:

		add $t2, $t2, $t2				
		add $t2, $t2, $t2
		add $t0, $t0, $t2				# Incremento dell'indirizzo dell'offset calcolato sopra.
		lw $t0, 0($t0)					# Leggo l'indirizzo della jump table. 

		jr $t0							# Salto all'indirizzo in $t0.

a:		

		li $s0, 0						# $s0 = 0 --> Sto facendo A, altrimenti B.
	
	insDim:
	
		li $v0, 4						# Richiesta della dimensione delle matrici.
		la $a0, dimensioneM
		syscall

		li $v0, 5						# Inserimento della dimensione delle matrici.
		syscall
		
		li $s4, 0
		
		move $s4, $v0					# Quindi il valore inserito da tastiera lo sposto in $s4 che e' la nostra variabile globale per la dimensione delle matrici.
		
		ble $s4, $zero, insDim			# Controllo che la dimensione inserita sia un valore maggiore di 0.
		
		move $a0, $s4					# Passo come parametro alla procedura.
		move $a1, $s7					# Passo la testa della matrice A.
		move $a2, $s6                   # Passo la testa della matrice B.
		
		jal inserimentoMatrici
		
		j scelta						# Si ritorna al menu' di scelta.
		
	inserimentoMatrici:
	
		addi $sp, $sp, -4
		sw $s0, 0($sp)
	
		move $t3, $a0	   	 			# Salvo n.
		bgtz $s0, InsB
		move $t1, $a1					# $t1 = testa di A.
		
		li $v0, 4					    # Stampa della scritta MatriceA
		la $a0, matriceA
		syscall
		
		j inserimentoListaPrinc
		
	InsB:	
	
		move $t1, $a2                    # $t1 = testa di B.
				
		addi $s0, $s0, 1
		
		li $v0, 4
		la $a0, matriceB
		syscall
		
		inserimentoListaPrinc:
		
			bne $t1, $zero, inizializzazionePuntatori
		        
		primoInserimento:
		
			li $v0, 9							# Creazione del primo record.
			li $a0, 8
			syscall
		
			move $t1, $v0						# Puntatore di inizio matrice.
			move $s6, $t1
			
		inizializzazionePuntatori:
		
			move $t4, $t1						# t4 scorre la lista principale.
			li $t8, 0							# Contatore 
			
		cicloEsterno:	
			
			li $t7, 0							# Contatore riga.
			move $t6, $t4						# t6 scorre la lista secondaria.
		
		cicloInterno:
			
			beq $t7, $t3, fineCicloInterno		# Se indice = n devo andare alla riga successiva.
		
			lw $t5, 4($t6)						# Prendo l'indirizzo del record successivo.
			
			bne $t5, $zero, recordSuccessivo	# Se l'indirizzo del record successivo e' diverso da zero devo sovrascrivere.
				
			li $v0, 9							# Se arrivo qui significa che e' 0, quindi devo creare un nuovo record.
			li $a0, 8
			syscall	
			
			sw $v0, 4($t6)						# Faccio il puntatore al record successivo.

		recordSuccessivo:	
			
			lw $t6, 4($t6)						# Incremento del puntatore all'elemento successivo.
			
			li $v0, 4							# Viene richiesto l'inserimento del valore da tastiera.
			la $a0, inserimentoValore
			syscall
			
			li $v0, 5							# Quindi inserisco il valore da tastiera.
			syscall
			
			sw $v0, 0($t6)						# Scrivo l'elemento messo in memoria.
			
			addi $t7, $t7, 1					# Incremento l'indice che mi fa arrivare a n(fine della riga).
			
		j cicloInterno	

		fineCicloInterno:

				addi $t8, $t8, 1				# Indica a quale riga sono arrivato, cioè scorre la lista principale.
				
				beq $t8, $t3, print
				
				lw $t5, 0($t4)					# Scorro di riga, cioè vado all'elemento successivo della lista principale.
				
				bne $t5, $zero, rigaSuccessiva	# Stesso discorso di prima, se e' già stato scritto il record successivo della lista principale lo sovrascrivo.
				
				li $v0, 9						# Creo nuova memoria dinamica se il puntatore e' a 0.
				li $a0, 8
				syscall	
					
				sw $v0, 0($t4)					# Scrivo il nuovo puntatore nel record creato.
				
			rigaSuccessiva:
			
				lw $t4, 0($t4)					# Vado alla riga successiva.	
				
		j cicloEsterno
			
		print:
		
			bgtz $s0, diSotto
		    move $s7, $t1						# Salvo la testa di A in $s7.
		        
		diSotto:	
		
			move $t4, $t1						# Uso $t4 per scorrere la lista principale.
			move $t6, $t1						# Uso $t6 per scorrere la lista secondaria.
			li $t7, 0							# Contatori di riga e di colonna.
			li $t8, 0
			
			printCicloEsterno:	
			
			li $t7, 0							# Contatore riga.
			move $t6, $t4						# t6 scorre la lista secondaria.
			
			li $v0, 4							# Vado a capo
			la $a0, accapo
			syscall
			
			loopPrintInterno:
			
				beq $t7, $t3, finePrintInterno		# Se indice = n devo andare alla riga successiva.
				
				lw $t6, 4($t6)						# Prendo l'indirizzo del record in cui c'è l'elemento da stampare.
							
				lw $a0, 0($t6)						# Prendo l'elemento da stampare.
				
				li $v0, 1							# Stampo l'elemento.
				syscall
				
				li $v0, 4							# Spazio.
				la $a0,spazio
				syscall
				
				addi $t7, $t7, 1					# Incremento il contatore di riga.
				
			j loopPrintInterno
			
			finePrintInterno:
			
				addi $t8, $t8, 1					# Incremento il contatore della lista principale.
				bnez $s0, diSotto2
				beq $t8, $t3, InsB
				
			diSotto2:
			
		        beq $t8, $t3, deallocazione
				lw $t4, 0($t4)						# Vado alla riga successiva.
                                			
			j printCicloEsterno
				
		deallocazione:
			
			move $v0, $s7			    # Testa di A.
			move $v1, $s6			    # Testa di B.
			
			lw $s0, 0($sp)
			addi $sp, $sp, 4
			jr $ra
			
.b: 

	beqz $s7, noMatrici		# Controllo per stabilire se quando richiamo questa operazione ho inserito almeno una volta le matrici.
	
	move $a2, $s4			# Passo alla procedura n.
	move $a0, $s7			# Passo alla procedura la testa di A.
	move $a1, $s6			# Passo alla procedura la testa di B.
	
	jal sommaMatrici		# Procedura che fa la somma tra le matrici A e B.
	
	j scelta				# Si ritorna al menu' di scelta.
	
	noMatrici:				# Caso in cui vengano richiamate le operazioni sulle matrici senza averle inserite.
	
		li $v0, 4
		la $a0, nessunaMatrice
		syscall
		
	j scelta
	
	sommaMatrici:
		
		addi $sp, $sp, -4		# Il chiamato si salva i registri preservati che usa.
		sw $s3, 0($sp)
			
		move $t9, $a2			# $t9 = n ( dimensione della matrice ).
		move $t3, $a0			# Testa di A come indirizzo iniziale della matrice A. $t3 = per scorrere le liste secondarie di A.
		move $t5, $a0			# $t5 = per scorrere la lista principale di A.
		move $t4, $a1 			# Testa di B come indirizzo iniziale della matrice B. $t4 = per scorrere le liste secondarie di B.
		move $t6, $a1			# $t6 = per scorrere la lista principale di B.
		li $t7, 0				# Contatore delle liste principali.
		li $t8, 0				# Contatore delle liste secondarie.
						
		li $v0, 4				# Risultato A + B.
		la $a0, risSomma
		syscall
		
	cicloEsternoSomma:
		
		li $t8, 0				# Quando scorro nella lista principale, reinizializzo gli indici della lista secondaria.				
		
		addi $t7, $t7, 1		# Incremento il contatore delle due matrici, per la lista principale ( scorro di riga ).
		
		move $t3, $t5			# Metto il puntatore che scorre la lista principale, in quello che scorre la lista secondaria.
		move $t4, $t6
		
		li $v0, 4				# Vado a capo
		la $a0, accapo
		syscall
		
		loopInternoSomma:
		
			beq $t8, $t9, fineLoopInternoSomma
		
			lw $t3, 4($t3)		# Prendo l'indirizzo dell'elemento dalla matrice A che andra' sommato.
			lw $t1, 0($t3)		# Prendo l'elemento dalla matrice A.
					
			lw $t4, 4($t4)		# Prendo l'indirizzo dell'elemento dalla matrice B che andra' sommato.
			lw $t2, 0($t4)		# Prendo l'elemento dalla matrice B.
			
			add $s3, $t1, $t2	# Sommo a[i,j] + b[i,j].

			move $a0, $s3		# Prendo in argomento di stampa il risultato della somma.
			
			li $v0, 1			# Stampo il risultato.
			syscall
		
	        li $v0, 4           # Stampo uno spazio
	        la $a0, spazio
	        syscall	
		
			li $s3, 0			# Riazzero il registro che tiene memoria del risultato della somma.
			
			addi $t8, $t8, 1	# Incremento gli indici della liste secondarie delle rispettive matrici. 
						
		j loopInternoSomma

		fineLoopInternoSomma:
		
			beq $t7, $t9, deallocazione2
			lw $t5, 0($t5)						# Vado alla riga successiva nella matrice A (scorro la lista principale).
			lw $t6, 0($t6)						# Vado alla riga successiva nella matrice B (scorro la lista principale). 
			
		j cicloEsternoSomma
			
	deallocazione2:
			
			lw $s3, 0($sp)						# Ripristino i registri caller - saved.
			addi $sp, $sp, 4
			jr $ra

c:
	
	beqz $s7, noMatrici		# Controllo per stabilire se quando richiamo questa operazione ho inserito almeno una volta le matrici.
	
	move $a2, $s4			# Passo alla procedura n.
	move $a0, $s7			# Passo alla procedura la testa di A.
	move $a1, $s6			# Passo alla procedura la testa di B.
	
	jal differenzaMatrici		# Procedura che fa la somma tra le matrici A e B.
	
	j scelta					# PC + 4 al ritorno dalla procedura differenzaMatrici, per tornare al menu' di scelta.
	
	differenzaMatrici:
	
		addi $sp, $sp, -4		# Il chiamato si salva i registri preservati che usa.
		sw $s3, 0($sp)
			
		move $t9, $a2			# $t9 = n ( dimensione della matrice ).
		move $t3, $a0			# Testa di A come indirizzo iniziale della matrice A. $t3 = per scorrere le liste secondarie di A.
		move $t5, $a0			# $t5 = per scorrere la lista principale di A.
		move $t4, $a1 			# Testa di B come indirizzo iniziale della matrice B. $t4 = per scorrere le liste secondarie di B.
		move $t6, $a1			# $t6 = per scorrere la lista principale di B.
		li $t7, 0				# Contatore delle liste principali.
		li $t8, 0				# Contatore delle liste secondarie.
						
		li $v0, 4				# Risultato A - B
		la $a0, risSottrazione
		syscall
		
	cicloEsternoSottrazione:
		
		li $t8, 0				# Quando scorro nella lista principale, reinizializzo gli indici della lista secondaria.				
		
		addi $t7, $t7, 1		# Incremento il contatore delle due matrici, per la lista principale ( scorro di riga ).
		
		move $t3, $t5			# Metto il puntatore che scorre la lista principale, in quello che scorre la lista secondaria.
		move $t4, $t6
		
		li $v0, 4				# Vado a capo
		la $a0, accapo
		syscall
		
		loopInternoSottrazione:
		
			beq $t8, $t9, fineLoopInternoSottrazione
		
			lw $t3, 4($t3)		# Prendo l'indirizzo dell'elemento dalla matrice A che andra' sottratto.
			lw $t1, 0($t3)		# Prendo l'elemento dalla matrice A.
					
			lw $t4, 4($t4)		# Prendo l'indirizzo dell'elemento dalla matrice B che andra' sottratto.
			lw $t2, 0($t4)		# Prendo l'elemento dalla matrice B.
			
			sub $s3, $t1, $t2	# Faccio a[i,j] - b[i,j].

			move $a0, $s3		# Prendo in argomento di stampa il risultato della differenza.
			
			li $v0, 1			# Stampo il risultato.
			syscall
			
			li $v0, 4           # Spazio.
	        la $a0, spazio
	        syscall	
		
			li $s3, 0			# Riazzero il registro che tiene memoria del risultato della somma.
			
			addi $t8, $t8, 1	# Incremento gli indici della liste secondarie delle rispettive matrici. 
						
		j loopInternoSottrazione

		fineLoopInternoSottrazione:
		
			beq $t7, $t9, deallocazione3
			lw $t5, 0($t5)						# Vado alla riga successiva nella matrice A (scorro la lista principale).
			lw $t6, 0($t6)						# Vado alla riga successiva nella matrice B (scorro la lista principale). 
			
		j cicloEsternoSottrazione
			
	deallocazione3:
			
			lw $s3, 0($sp)						# Ripristino i registri caller - saved.
			addi $sp, $sp, 4
			jr $ra			
	
d:
		
	beqz $s7, noMatrici		# Controllo per stabilire se quando richiamo questa operazione ho inserito almeno una volta le matrici.
	
	move $a2, $s4			# Passo alla procedura n.
	move $a0, $s7			# Passo alla procedura la testa di A.
	move $a1, $s6			# Passo alla procedura la testa di B.
	
	jal prodottoMatrici		# Chiamata della proceduta prodottoMatrici.
	
	j scelta				# PC + 4 al ritorno dalla procedura prodottoMatrici, per tornare al menu' di scelta.
	
	prodottoMatrici:
	
	addi $sp, $sp, -20		# Chiamato che salva nello stack i registri $s che vuole alterare e $ra.
	sw $s0, 0($sp)
	sw $s2, 4($sp)
	sw $s3, 8($sp)
	sw $s4, 12($sp)
	sw $ra, 16($sp)
	
	li $s4, 0				# Riazzero $s4 perche' in questa procedura ci si arriva sempre con la dimensione delle matrici. Riacquisirà il suo valore una volta usciti dalla procedura prodottoMatrici.
	
	move $t9, $a2			# $t9 = n ( dimensione della matrice ).
	move $t3, $a0			# Testa di A come indirizzo iniziale della matrice A. $t3 = per scorrere le liste secondarie di A.
	move $t4, $a1 			# Testa di B come indirizzo iniziale della matrice B. $t4 = per scorrere le liste secondarie di B.
	
	li $t7, 1				# Contatore della lista principale di A.
	li $t8, 1				# Contatore delle liste secondarie di A.
	li $t1, 1				# Contatore della lista principale di B.
	li $t2, 1				# Contatore della lista secondaria di B.
			
	li $v0, 4				# Risultato A * B
	la $a0, risProdotto
	syscall		
			
	cicloEsternoPrelievo:
	
		move $a0, $t3		# Passo la testa di A.
		move $a1, $t7		# Passo l'indice della lista principale.
		move $a2, $t8		# Passo l'indice della lista secondaria.
		
		addi $sp, $sp, -12	# Salvo nello stack i registri non preservati di cui voglio conservare il valore a ritorno dalla procedura.
		sw $t3, 0($sp)
		sw $t7, 4($sp)
		sw $t8, 8($sp)
		
		jal prendiElemento	# Chiamata della procedura prendiElemento.
		
		lw $t3, 0($sp)		# Ripristino i registri non preservati di cui voglio conservare il valore a ritorno dalla procedura.
		lw $t7, 4($sp)
		lw $t8, 8($sp)
		addi $sp, $sp, 12
		
		move $s0, $v0  		# Passo in $s0 il valore di A[i,j].
		
		move $a0, $t4		# Passo la testa di B.
		move $a1, $t1		# Passo l'indice della lista principale.
		move $a2, $t2		# Passo l'indice della lista secondaria.
		
		addi $sp, $sp, -12	# Salvo nello stack i registri non preservati di cui voglio conservare il valore a ritorno dalla procedura.
		sw $t4, 0($sp)
		sw $t1, 4($sp)
		sw $t2, 8($sp)
	
		jal prendiElemento 	# Sistemare i temporanei.
		
		lw $t4, 0($sp)		# Ripristino i registri non preservati di cui voglio conservare il valore a ritorno dalla procedura.
		lw $t1, 4($sp)
		lw $t2, 8($sp)
		addi $sp, $sp, 12
		
		move $s2, $v0			# Passo in $s2 il valore di B[i,j].
		
		mul $s3, $s2, $s0		# Moltiplico i due elementi ottenuti con la procedura prendiElemento.
		add $s4, $s3, $s4		# $s4 = sommatoria righe per colonne corrente.
		
		beq $t8, $t9, stampa	# Alla fine di ogni riga devo stampare la sommatoria accumulata con le moltiplicazioni.
		
		addi $t8, $t8, 1		# Incremento indice lista secondaria di A.
		addi $t1, $t1, 1		# Incremento indice lista principale di B.
		
		j cicloEsternoPrelievo
		
	stampa:
	
	        li $v0, 4							# Spazio.
			la $a0, spazio
			syscall
	
			move $a0, $s4
			
			li $v0, 1
			syscall
			
			bne $t2, $t9, ok					# Controllo per stampare i risultati in forma di matrice.
			
			li $v0, 4							# Vado a capo.
			la $a0, accapo
			syscall
			
			li $v0, 4							# Spazio.
			la $a0, spazio
			syscall
			
			ok:
			
				li $s4, 0						# Reinizializzo il risultato della sommatoria.
						
				beq $t7, $t9, controlloFinale	# Caso in cui sono arrivato a fine delle matrici.
							
				j avanti
			
			controlloFinale:
			
				beq $t2, $t9, deallocazione4
			
			avanti:																		
																						
				beq $t2, $t9, incrementPrincA	# Se sono arrivato alla fine delle colonne di B devo cambiare riga in A.
			
				addi $t2, $t2, 1				# Altrimenti vado alla colonna successiva di B.
			
				li $t8, 1						# Riporto l'indice della lista secondaria di A all'inizio.
				li $t1, 1						# Riporto l'indice della lista principale di B all'inizio.
			
			j cicloEsternoPrelievo
			
			incrementPrincA:
			
				addi $t7, $t7, 1
				li $t8, 1
				li $t2, 1					# B dovrà ripartire dalla prima posizione.
				li $t1, 1					
		
			j cicloEsternoPrelievo
	
	prendiElemento:
	
			addi $sp, $sp, -16					
			sw $s1, 0($sp)
			sw $s5, 4($sp)
			sw $s6, 8($sp)
			sw $s7, 12($sp)
						
			move $t5, $a0						# $t5 = puntatore della lista principale.  
			move $s1, $a1						# Salvo l'indice della lista principale(limite alla quale bisogna arrivare nella lista principale).
			move $t0, $a2						# Salvo l'indice della lista secondaria(limite alla quale bisogna arrivare nella lista secondaria).
			
			li $s5, 1							# Indice della lista secondaria (contatore).
			li $s6, 1							# Indice della lista principale (contatore).
			
			cicloEsternoScansione:
			
				beq $s6, $s1, cicloInternoScansione
				
				addi $s6, $s6, 1				# Incremento fino ad arrivare al punto giusto della lista principale.
				
				lw $t5, 0($t5)					# Scorro la lista principale.
				
			j cicloEsternoScansione
				
			cicloInternoScansione:
				
				lw $t5, 4($t5)
				lw $s7, 0($t5)					# Prendo l'elemento nella posizione cercata.
				
				beq $s5, $t0, restituzione		# Quando si e' arrivati all'indice passato come parametro alla procedura si può restituire l'elemento corrispondente. 
				
				addi $s5, $s5, 1				# Incremento il contatore della lista secondaria perche' devo arrivare all'elemento chiesto.
				
			j cicloInternoScansione 				
					
	restituzione:				
		
			move $v0, $s7						# Restituisco l'elemento cercato e ripristino i registri caller - saved.
			lw $s1, 0($sp)
			lw $s5, 4($sp)
			lw $s6, 8($sp)
			lw $s7, 12($sp)
			addi $sp, $sp, 16
			jr $ra		
						
	deallocazione4:
			
			lw $s0, 0($sp)						# Ripristino i registri caller - saved e ritorno al chiamante.
			lw $s2, 4($sp)
			lw $s3, 8($sp)
			lw $s4, 12($sp)
			lw $ra, 16($sp)
			addi $sp, $sp, 20
			jr $ra	
	
e:
	
		li $v0, 4								# Messaggio d'uscita.
		la $a0, msgUscita
		syscall
		
		lw $s7, 0($sp)							# Exception Handler (ritorno a QtSpim ), dopo aver ripristinato i registri 
		lw $s6, 4($sp)
		lw $s4, 8($sp)
		lw $s0, 12($sp)
		lw $ra, 16($sp)
		lw $s5, 20($sp)
		addi $sp, $sp, 24	
		jr $ra