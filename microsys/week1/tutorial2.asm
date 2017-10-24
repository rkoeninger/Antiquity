;**********************************************************************
;                                                                     *
;    Filename:	    tutorial2.asm                                     *
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

cblock     0x20
    Delay1
    Delay2
    Delay3
endc

     org 0
Start:

    bsf  STATUS,RP0     ; select Register Bank 1
    bcf  TRISD, 0       ; Make LED 0 an output
    bcf  STATUS,RP0     ; back to Register Bank 0
     
Main:

	bsf  PORTD, 0       ; Light LED

    clrf Delay1
    clrf Delay2
    clrf Delay3

LoopWhileOn:

    decfsz Delay1,f     ; Pause to let light shine
    goto   LoopWhileOn
    decfsz Delay2,f
    goto   LoopWhileOn
    decfsz Delay3,f
    goto   LoopWhileOn

	bcf  PORTD, 0       ; Clear LED

    clrf Delay1
    clrf Delay2
    clrf Delay3

LoopWhileOff:

    decfsz Delay1,f     ; Pause before relighting LED
    goto   LoopWhileOff
    decfsz Delay2,f
    goto   LoopWhileOff
    decfsz Delay3,f
    goto   LoopWhileOff
    
    goto   Main
 
    end
     