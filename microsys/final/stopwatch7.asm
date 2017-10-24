

#include <p16F887.inc>
__CONFIG    _CONFIG1, _LVP_OFF & _FCMEN_OFF & _IESO_OFF & _BOR_OFF & _CPD_OFF & _CP_OFF & _MCLRE_OFF & _PWRTE_ON & _WDT_OFF & _INTRC_OSC_NOCLKOUT
__CONFIG    _CONFIG2, _WRT_OFF & _BOR21V

cblock     0x20
    Delay1              ; Define variables to hold delay loop counters
    Delay2              ; Define variables to hold delay loop counters
    Delay3              ; Define variables to hold delay loop counters
    TimerRunningDisplay ; Holds the LED values while the timer is running
    TimeTens
    TimeOnes
    TimeTenths
	TimeHundredths
    LookingFor
endc

; Subroutine Wait10ms
; Loops with fine-tuned counters to approximate 10ms (1/100 a sec)
; as closely as possible. Always returns, leaving 0 in the working register
    org 500h
Wait10ms
    movlw     0xfb      ; Loop counters have been fine-tuned to get
    movwf     Delay1    ; the most accurate wait time possible
    movlw     0x0d
    movwf     Delay2
DelayLoop:
    decfsz    Delay1,f
    goto      DelayLoop ; Inner loop - runs 251 times first round,
    decfsz    Delay2,f  ; 256 times every following round
    goto      DelayLoop ; Outer loop - runs 13 times
    retlw     0x00

; Subroutine IncrementTime
; Increments the time counters by 1/100 sec. Handles conditions where
; the counters "roll-over" - for instance, when the hundredths counter
; reachs 100, it is cleared and the seconds counter is incremented
    org 600h
IncrementTime
    incf      TimeHundredths
    bcf       STATUS,C       ; Test to see if we've reached
    movfw     TimeHundredths ; 10 hundredths by adding
    addlw     0xf6           ; 246 to the hundredths and check
    btfss     STATUS,C       ; for overflow
    retlw     0x00           ; If no rollover, just return
    clrf      TimeHundredths ; If there is rollover, clear
    incf      TimeTenths     ; the hundredths and increment tenths
    bcf       STATUS,C       ; Repeat the rollover check
    movfw     TimeTenths
    addlw     0xf6
    btfss     STATUS,C
    retlw     0x00           ; If no rollover, just return
    clrf      TimeTenths
    incf      TimeOnes       ; Increment ones if the tenths rollover
    bcf       STATUS,C       ; Repeat the rollover check
    movfw     TimeOnes
    addlw     0xf6
    btfss     STATUS,C
    retlw     0x00           ; If no rollover, just return
    clrf      TimeOnes
    incf      TimeTens       ; Increment tens if the ones rollover
    bcf       STATUS,C       ; Repeat the rollover check
    movfw     TimeTens
    addlw     0xf6
    btfss     STATUS,C       ; If there is rollover here,
    retlw     0x00           ; we have a problem...
    movlw     0xff           ; Current solution is to turn on
    movwf     PORTD          ; all the lights and freeze
    goto      $
    


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
WaitingToStart:
movlw 0x00
movwf TimeTens
movlw 0x00
movwf TimeOnes
movlw 0x00
movwf TimeTenths
movlw 0x00
movwf TimeHundredths
movlw 0x01
movwf TimerRunningDisplay
WaitingForButtonWithDelay:
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
movfw TimeTens ; show tens for 5 sec
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

clrf PORTD           ; clear for 1 sec

movlw 0x64
movwf Delay3
AnotherDelay:
call Wait10ms
decfsz Delay3
goto AnotherDelay

movfw TimeOnes        ; show ones for 5 sec
movwf PORTD

movlw 0x64
movwf Delay3
AnotherDelay3:
call Wait10ms
call Wait10ms
call Wait10ms
call Wait10ms
call Wait10ms
decfsz Delay3
goto AnotherDelay3

clrf PORTD             ; clear for 1 sec

movlw 0x64
movwf Delay3
AnotherDelay4:
call Wait10ms
decfsz Delay3
goto AnotherDelay4

                       ; convert ones and tens to BCD and show
rlf TimeTens,f
rlf TimeTens,f
rlf TimeTens,f
rlf TimeTens,f
movfw TimeTens
addwf TimeOnes
movfw TimeOnes
movwf PORTD


movlw 0x64
movwf Delay3
AnotherDelay5:
call Wait10ms
call Wait10ms
call Wait10ms
call Wait10ms
call Wait10ms
decfsz Delay3
goto AnotherDelay5

clrf PORTD             ; clear for 1 sec

movlw 0x64
movwf Delay3
AnotherDelay6:
call Wait10ms
decfsz Delay3
goto AnotherDelay6




goto WaitingToStart

     end
     
