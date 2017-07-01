# Marco Vignini, Manuel Ronca e Luigi Favaro.
# 5444851, 5094658, 5251492
# marco.vignini@stud.unifi.it, manuel.ronca@stud.unifi.it, luigi.favaro@stud.unifi.it
# Data Di Consegna: 25/ 05/ 2015 
# A.a 2014 - 2015.
# Esercizio 2: Procedure annidate e ricorsive.

.data
input: .asciiz "Inserire il valore del numero naturale n --> "
ris: .asciiz "\n\nRisultato: G("
ParentesiA: .asciiz "("
parentesiC: .asciiz ")"
soloF: .asciiz "F"
soloG: .asciiz "G"
trattino: .asciiz "-"
freccia: .asciiz "->"
ritorno: .asciiz "return"
traccia: .asciiz "\nTraccia: "
uguale: .asciiz "="

.text
.globl main

main:
		
		addi $sp, $sp, -4		# Salvataggio dell'indirizzo di ritorno al main.
		sw $ra, 0($sp)
		
	inserimento:	
		
		li $t1, 1				# Per il controllo.
		
		li $v0, 4				# Stampa della stringa che richiede l'inserimento di n.
		la $a0, input
		syscall
		
		li $v0, 5				# Inserimento di n.
		syscall
		
		slt $t0, $v0, 0			# Controllo che il numero inserito sia naturale, ovvero >= 0.
		beq $t0, $t1, inserimento
		
		move $t7, $v0
		
		li $v0, 4				# Stampo la scritta traccia.
		la $a0, traccia
		syscall
		
		move $t6, $t7			# Salvo n per la stampa.
		move $a0, $t7			# Passo come parametro n.
		
		addi $sp, $sp, -4		# Salvo $t6 nello stack prima di chiamare G perche' verra' utilizzato al ritorno dalla procedura.
		sw $t6, 0($sp)
		
		jal G					# Chiamata della procedura G.
		
		lw $t6, 0($sp)			# Dealloco dallo stack $t6 (ripristino dei registri caller-saved).
		addi $sp, $sp, 4
		
		move $t5, $v0			# Per non perdere il valore.
		
		li $v0, 4				# Stampa della stringa.
		la $a0, ris
		syscall
		
		li $v0, 1				# Stampa dell'n inserito da tastiera.
		move $a0, $t6
		syscall
		
		li $v0, 4				# Stampa parentesi chiusa.
		la $a0, parentesiC
		syscall
		
		li $v0, 4				# Stampa dell'uguale.
		la $a0, uguale
		syscall
		
		move $a0, $t5			# Metto il valore restituito dalla G in $a0.
		li $v0, 1				# Stampa del risultato.
		syscall
		
		lw $ra, 0($sp)			# Ripristino dello stack frame.
		addi $sp, $sp, 4
		jr $ra
		
G:		
		
		addi $sp, $sp, -4		# Il chiamato si salva il return address.
		sw $ra, 0($sp)
		
		li $t0, 0				# b = 0.
		move $t1, $a0			# $t1 = n.
		li $t2, 0				# Indice del ciclo for.
		
		li $v0, 4				# Stampa di G.
		la $a0, soloG
		syscall
		
		li $v0, 4				# Stampa della parentesi aperta.
		la $a0, ParentesiA
		syscall
		
		li $v0, 1				# Stampa di n.
		move $a0, $t1
		syscall
		
		li $v0, 4				# Stampa parentesi chiusa.
		la $a0, parentesiC
		syscall
		
		li $v0, 4				# Stampa della freccia.
		la $a0, freccia
		syscall
		
		cicloFor:
		
				bgt $t2, $t1, restituzione
				move $a0, $t2						# Passo k alla procedura F.
				
				addi $sp, $sp, -12					# Salvo nello stack i registri non preservati che userò al ritorno della chiamata a procedura.
				sw $t0, 0($sp)
				sw $t1, 4($sp)
				sw $t2, 8($sp)
				
				jal F								# Chiamata della procedura F.
				
				lw $t0, 0($sp)						# Ripristino dei registri caller - saved.
				lw $t1, 4($sp)
				lw $t2, 8($sp)
				addi $sp, $sp, 12
				
				mul $t0, $t0, $t0					# b^2
				add $t0, $t0, $v0					# b^2 + u ( valore restituito da F ).	
				addi $t2, $t2, 1					# Incremento dell'indice del ciclo for ovvero k.
					
		j cicloFor
		
		restituzione:
	
			move $v0, $t0			# return b.
			
			li $v0, 4				# Stampa di G.
			la $a0, soloG
			syscall
		
			li $v0, 4				# Stampa del trattino.
			la $a0, trattino
			syscall
		
			li $v0, 4				# Stampa del ritorno.
			la $a0, ritorno
			syscall
		
			li $v0, 4				# Stampa di parentesi aperta.
			la $a0, ParentesiA
			syscall
			
			move $a0, $t0			# Passo il valore da restituire ad $a0.
			
			li $v0, 1				# Stampa del valore finale.
			syscall
			
			li $v0, 4				# Stampa parentesi chiusa.
			la $a0, parentesiC
			syscall
		
			move $v0, $t0			# Metto il risultato in $v0 per poi tornare al chiamante.
		
			lw $ra, 0($sp)
			addi $sp, $sp, 4
			jr $ra
		
F:		
                     
			addi $sp, $sp, -4		# Il chiamato si salva il return address.	
			sw $ra, 0($sp)
			
			move $t8, $a0			# Mi salvo k per stamparlo.
			
			li $v0, 4				# Stampa di F.
			la $a0, soloF
			syscall
		
			li $v0, 4				# Stampa della parentesi aperta.
			la $a0, ParentesiA
			syscall
		
			li $v0, 1				# Stampa di k.
			move $a0, $t8
			syscall
		
			li $v0, 4				# Stampa parentesi chiusa.
			la $a0, parentesiC
			syscall
		
			li $v0, 4				# Stampa della freccia.
			la $a0, freccia
			syscall
			
			move $a0, $t8			# Rimetto k in $a0.
			
			beq $a0, $zero, restZero
			
			move $a1, $a0			# Salvo n per sommarlo dopo...
			addi $a0, $a0, -1		# n - 1
			
			addi $sp, $sp, -8		# Salvo nello stack i registri non preservati che intendo usare al ritorno della chiamata a procedura.
			sw $a0, 0($sp)
			sw $a1, 4($sp)
			
			jal F					# La procedura F richiama se stessa ( funzione ricorsiva ).
			
			lw $a0, 0($sp)			# Ripristino dei registri caller - saved.
			lw $a1, 4($sp)
			addi $sp, $sp, 8
			
			li $t3, 2
			mul $v0, $v0, $t3		# 2 * F ( n - 1 )
			add $v0, $v0, $a1		# 2 * F ( n - 1 ) + n
			
			move $t9, $v0
			
			li $v0, 4				# Stampa di F.
			la $a0, soloF
			syscall
		
			li $v0, 4				# Stampa del trattino.
			la $a0, trattino
			syscall
		
			li $v0, 4				# Stampa della scritta return.
			la $a0, ritorno
			syscall
			
			li $v0, 4				# Stampa della parentesi aperta.
			la $a0, ParentesiA
			syscall
			
			move $a0, $t9
			
			li $v0, 1				# Stampa del valore restituito.
			syscall
			
			li $v0, 4				# Stampa della parentesi chiusa.
			la $a0, parentesiC
			syscall
			
			li $v0, 4				# Stampa della freccia.
			la $a0, freccia
			syscall
			
			move $v0, $t9

			lw $ra, 0($sp)			# Il chiamato ripristina i registri caller - saved e torna la chiamante.
			addi $sp, $sp, 4
			jr $ra
			
		restZero:					# Caso base...
		
			li $v0, 1				# In questo caso devo restituire 1.
			
			move $t9, $v0
			
			li $v0, 4				# Stampa di F.
			la $a0, soloF
			syscall
		
			li $v0, 4				# Stampa del trattino.
			la $a0, trattino
			syscall
		
			li $v0, 4				# Stampa della scritta return.
			la $a0, ritorno
			syscall
			
			li $v0, 4				# Stampa della parentesi aperta.
			la $a0, ParentesiA
			syscall
			
			move $a0, $t9			# Rimetto il valore da stampare in $a0.
			
			li $v0, 1				# Stampa del valore restituito.
			syscall
			
			li $v0, 4				# Stampa della parentesi chiusa.
			la $a0, parentesiC
			syscall
			
			li $v0, 4				# Stampa della freccia.
			la $a0, freccia
			syscall
			
			li $v0, 1				# Valore che la procedura deve restituire.
						
			lw $ra, 0($sp)			# Il chiamato ripristina i registri caller - saved e torna al chiamante.
			addi $sp, $sp, 4
			jr $ra
		

