;**********************************************************************
;                                                                     *
;    Filename:	    tutorial3.asm                                     *
;    Date:          April 17, 2009                                    *
;    File Version:                                                    *
;                                                                     *
;    Author:        Robert Koeninger                                  *
;    Company:                                                         *
;                                                                     *
;                                                                     *
;**********************************************************************

	list		p=16f887	; list directive to define processor
	#include	<p16f887.inc>	; processor specific variable definitions

__CONFIG _CONFIG1, _LVP_OFF & _FCMEN_OFF & _IESO_OFF & _BOR_OFF & _CPD_OFF & _CP_OFF & _MCLRE_OFF & _PWRTE_ON & _WDT_OFF & _INTRC_OSC_NOCLKOUT
__CONFIG _CONFIG2, _WRT_OFF & _BOR21V

cblock     0x20
    Delay1
    Delay2
    Display
endc

;subroutine init - configures portd and initializes display word
org 0ffh

Init

bsf     STATUS,RP0
clrf    TRISD      ; make PortD all outputs
bcf     STATUS,RP0
movlw   0xe0       ; the initial display values
                   ; modified to have 3 adjacent LEDs
                   ; light up and have that pattern rotate
movwf   Display
retlw   0x00

org 0

Start:

call Init

MainLoop:

movf    Display,w  ; Copy the display to the LEDs
movwf   PORTD

clrf Delay1
clrf Delay2

Loop:

decfsz  Delay1,f
goto    Loop
decfsz  Delay2,f
goto    Loop

bcf     STATUS,C   ; clear carry bit so we can check it
rrf     Display,f  ; rotate display
btfsc   STATUS,C   ; if carry is set
bsf     Display,7  ;     then put it on the end of the display word

goto    MainLoop

end
