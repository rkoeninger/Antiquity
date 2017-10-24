
#include <p16F887.inc>
__CONFIG    _CONFIG1, _LVP_OFF & _FCMEN_OFF & _IESO_OFF & _BOR_OFF & _CPD_OFF & _CP_OFF & _MCLRE_OFF & _PWRTE_ON & _WDT_OFF & _INTRC_OSC_NOCLKOUT
__CONFIG    _CONFIG2, _WRT_OFF & _BOR21V

cblock     0x20
    Delay1              ; Define variables to hold the delay timers
    Delay2              ; Define variables to hold the delay timers
    TimerRunningDisplay ; Holds the LED values while the timer is running
    LookingFor
endc
    org 500h
Wait10ms
    movlw     0xed
    movwf     Delay1
    movlw     0x41
    movwf     Delay2
DelayLoop:
    decfsz    Delay1,f
    goto      DelayLoop
    decfsz    Delay2,f
    goto      DelayLoop
    retlw     0x00
     
     org 0
Start:
     bsf       STATUS,RP0     ; select Register Bank 1
     movlw     0xFF
     movwf     TRISA          ; Make PortA all input
     movlw     0x01
     movwf     TRISB          ; Make RB0 input
     clrf      TRISD          ; Make PortD all output
     movlw     0x00           ; Left Justified, Vdd-Vss referenced
     movwf     ADCON1
     bsf       STATUS,RP1     ; select Register Bank 3
     movlw     0xFF           ; we want all Port A pins Analog
     movwf     ANSEL
     movlw     0x00
     movwf     ANSELH         ; PortB pins are digitial (important as RB0 is switch)
     bcf       STATUS,RP0     ; back to Register Bank 0
     bcf       STATUS,RP1
     
     movlw     0x41
     movwf     ADCON0         ; configure A2D for Fosc/8, Channel 0 (RA0), and turn on the A2D module
     movlw     0x80
     movwf     TimerRunningDisplay
     clrf      LookingFor     ; Looking for a 0 on the button
	clrf PORTD

WaitingForButtonWithDelay:
    call Wait10ms
    btfsc PORTB,0
	goto WaitingForButtonWithDelay
    
	bsf PORTD,0
	goto $

;WaitingForButton:
;    btfsc PORTB,0
;	goto WaitingForButton
    
;	bsf PORTD,0
;	goto $

;LoopForever:
;btfss PORTB,0
;goto Light1
;goto Light2
;Light1:
;bcf PORTD,1
;bsf PORTD,0
;goto LoopForever
;Light2:
;bsf PORTD,1
;bcf PORTD,0
;goto LoopForever

     end
     
