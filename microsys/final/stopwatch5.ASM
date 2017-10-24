
#include <p16F887.inc>
__CONFIG    _CONFIG1, _LVP_OFF & _FCMEN_OFF & _IESO_OFF & _BOR_OFF & _CPD_OFF & _CP_OFF & _MCLRE_OFF & _PWRTE_ON & _WDT_OFF & _INTRC_OSC_NOCLKOUT
__CONFIG    _CONFIG2, _WRT_OFF & _BOR21V

cblock     0x20
    Delay1              ; Define variables to hold the delay timers
    Delay2              ; Define variables to hold the delay timers
    Delay3
    TimerRunningDisplay ; Holds the LED values while the timer is running
	TimeSeconds
	TimeHundredths
	AuxTimeSeconds
    LookingFor
endc

    org 500h
Wait10ms
;    movlw     0xed
    movlw     0xfb
    movwf     Delay1
;    movlw     0x41
    movlw     0x0d
    movwf     Delay2
DelayLoop:
    decfsz    Delay1,f
    goto      DelayLoop
    decfsz    Delay2,f
    goto      DelayLoop
    retlw     0x00

    org 600h
IncrementTime
    incf      TimeHundredths
    bcf       STATUS,C       ; Test to see if we've reached
    movfw     TimeHundredths ; 100 hundredths by adding
    addlw     0x9c           ; 156 to the hundredths and check
    btfss     STATUS,C       ; for overflow
    retlw     0x00
    clrf      TimeHundredths ; if there is rollover, clear
    incf      TimeSeconds    ; the hundredths and increment secs
    retlw     0x00
     
     org 0
Start:
     bsf       STATUS,RP0     ; select Register Bank 1
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
    clrf TimeHundredths
    clrf TimeSeconds

movlw 0x00
movwf TimeSeconds
movlw 0x00
movwf TimeHundredths
movlw 0x00
movwf AuxTimeSeconds
movlw 0x01
movwf TimerRunningDisplay
WaitingForButtonWithDelay:
incf AuxTimeSeconds
movfw TimerRunningDisplay
movwf PORTD
movlw 0x64
movwf Delay3
InnerDelay:
    call IncrementTime
	decfsz Delay3
	goto DelayHelper
	bcf STATUS,C
	rlf TimerRunningDisplay,f
	btfsc STATUS,C
	bsf TimerRunningDisplay,0
	goto WaitingForButtonWithDelay
DelayHelper:
    call Wait10ms
    btfss PORTB,0
	goto NextState
	goto InnerDelay

NextState:
;movfw AuxTimeSeconds
movfw TimeSeconds
movwf PORTD



movlw 0x64
movwf Delay3
AnotherDelay2:
call Wait10ms
call Wait10ms
call Wait10ms
call Wait10ms
call Wait10ms
decfsz Delay3
goto AnotherDelay2

clrf PORTD

movlw 0x64
movwf Delay3
AnotherDelay:
call Wait10ms
decfsz Delay3
goto AnotherDelay

movfw TimeHundredths
movwf PORTD



movlw 0x64
movwf Delay3
AnotherDelay2:
call Wait10ms
call Wait10ms
call Wait10ms
call Wait10ms
call Wait10ms
decfsz Delay3
goto AnotherDelay2



     end
     
