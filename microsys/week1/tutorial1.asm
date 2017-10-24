;**********************************************************************
;                                                                     *
;    Filename:	    tutorial1.asm                                     *
;    Date:          April 6, 2009                                     *
;    File Version:                                                    *
;                                                                     *
;    Author:        Robert Koeninger                                  *
;    Company:                                                         *
;                                                                     *
;                                                                     *
;**********************************************************************
;                                                                     *
;    Files Required: P16F887.INC                                      *
;                                                                     *
;**********************************************************************

list		p=16f887	    ; list directive to define processor
#include	<p16f887.inc>	; processor specific variable definitions

; '__CONFIG' directives were copied from tutorial
__CONFIG _CONFIG1, _LVP_OFF & _FCMEN_OFF & _IESO_OFF & _BOR_OFF & _CPD_OFF & _CP_OFF & _MCLRE_OFF & _PWRTE_ON & _WDT_OFF & _INTRC_OSC_NOCLKOUT
__CONFIG _CONFIG2, _WRT_OFF & _BOR21V

     org 0
Start:

    bsf  STATUS,RP0     ; select Register Bank 1
    bcf  TRISD, 0       ; Make all PortD output
    bcf  TRISD, 1
    bcf  TRISD, 2
    bcf  TRISD, 3
    bcf  TRISD, 4
    bcf  TRISD, 5
    bcf  TRISD, 6
    bcf  TRISD, 7
    bcf  STATUS,RP0     ; back to Register Bank 0
     
    ; Student ID = 02477822
    ; ID mod 200 = 22
    ; 22 => 16 + 4 + 2
    ; 22 => 00010110 (led0 = least significant bit)

	bcf  PORTD, 0         ; Set LEDs to bit pattern
	bsf  PORTD, 1
	bsf  PORTD, 2
	bcf  PORTD, 3
	bsf  PORTD, 4
	bcf  PORTD, 5
	bcf  PORTD, 6
	bcf  PORTD, 7

	goto Start      ; loop infinitely
     
    end
     