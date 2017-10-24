
#include <p16F887.inc>
__CONFIG    _CONFIG1, _LVP_OFF & _FCMEN_OFF & _IESO_OFF & _BOR_OFF & _CPD_OFF & _CP_OFF & _MCLRE_OFF & _PWRTE_ON & _WDT_OFF & _INTRC_OSC_NOCLKOUT
__CONFIG    _CONFIG2, _WRT_OFF & _BOR21V

cblock     0x20
    Delay1              ; Define variables to hold delay loop counters
    Delay2              ; Define variables to hold delay loop counters
    Delay3              ; Define variables to hold delay loop counters
    TimerRunningDisplay ; Holds the LED values while the timer is running
    TimeTens            ; The timer ten's place
    TimeOnes            ; The timer one's place
    TimeTenths          ; The timer tenth's place
	TimeHundredths      ; The timer hundredth's place
    LookingFor          ; A marker to keep track of whether a button press
                        ; is "fresh" or if the button is still depressed
                        ; from the last time we checked
    SubReturnValue      ; Since we can't use a btfss/btfsc instruction on
                        ; the working register, here's a variable to store
                        ; the return value and do the check
endc

; Subroutine Wait10ms
; Loops with fine-tuned counters to approximate 10ms (1/100 a sec)
; as closely as possible. Always returns, leaving 0 in the working register
    org 100h
Wait10ms
    movlw   0xfb      ; Loop counters have been fine-tuned to get
    movwf   Delay1    ; the most accurate wait time possible
    movlw   0x0d
    movwf   Delay2
DelayLoop:
    decfsz  Delay1,f
    goto    DelayLoop ; Inner loop - runs 251 times first round,
    decfsz  Delay2,f  ; 256 times every following round
    goto    DelayLoop ; Outer loop - runs 13 times
    retlw   0x00

; Subroutine IncrementTime
; Increments the time counters by 1/100 sec. Handles conditions where
; the counters "roll-over" - for instance, when the hundredths counter
; reachs 100, it is cleared and the seconds counter is incremented
    org 200h
IncrementTime
    incf    TimeHundredths
    bcf     STATUS,C       ; Test to see if we've reached
    movfw   TimeHundredths ; 10 hundredths by adding
    addlw   0xf6           ; 246 to the hundredths and check
    btfss   STATUS,C       ; for overflow
    retlw   0x00           ; If no rollover, just return
    clrf    TimeHundredths ; If there is rollover, clear
    incf    TimeTenths     ; the hundredths and increment tenths
    bcf     STATUS,C       ; Repeat the rollover check
    movfw   TimeTenths
    addlw   0xf6
    btfss   STATUS,C
    retlw   0x00           ; If no rollover, just return
    clrf    TimeTenths
    incf    TimeOnes       ; Increment ones if the tenths rollover
    bcf     STATUS,C       ; Repeat the rollover check
    movfw   TimeOnes
    addlw   0xf6
    btfss   STATUS,C
    retlw   0x00           ; If no rollover, just return
    clrf    TimeOnes
    incf    TimeTens       ; Increment tens if the ones rollover
    bcf     STATUS,C       ; Repeat the rollover check
    movfw   TimeTens
    addlw   0xf6
    btfss   STATUS,C       ; If there is rollover here,
    retlw   0x00           ; we have a problem...
    movlw   0xff           ; Current solution is to turn on
    movwf   PORTD          ; all the lights and freeze
    goto    $
 
; Subroutine CheckButtonActivation
; Encapsulates a check for the button being pressed and a check that
; the button is not just still pressed from last button push. If the button
; has been freshly pressed, the subroutine returns, leaving a 0x01 in the
; working register.
    org 300h
CheckButtonActivation
    btfsc   LookingFor,0
    goto    LookingFor1
LookingFor0:             ; The switch was not pressed last time
    btfsc   PORTB,0      ; Is the switch pressed now (0)?
    retlw   0x00         ; No  - just move along
    bsf     LookingFor,0 ; Yes - the button has been freshly pressed
    retlw   0x01         ; Report event activation
LookingFor1:             ; The switch was pressed last time
    btfsc   PORTB,0      ; Is the switch pressed now (0)?
    bcf     LookingFor,0 ; No  - mark that next press (0), is a fresh press
    retlw   0x00         ; Yes - just move along

; Program Body
; 
; 
    org 0
Init:
    bsf     STATUS,RP0 ; Select Register Bank 1
    movlw   0x01
    movwf   TRISB      ; Make RB0 input
    clrf    TRISD      ; Make PortD all output
    bsf     STATUS,RP1 ; Select Register Bank 3
    clrf    ANSELH     ; Make PortB pins are digitial (RB0 is switch)
    bcf     STATUS,RP0 ; Select Register Bank 0
    bcf     STATUS,RP1
    clrf    LookingFor ; We assume the button is not pressed initially

TimerWait:
    call    CheckButtonActivation
    movwf   SubReturnValue
    btfss   SubReturnValue,0
    goto    TimerWait

TimerStart:
    clrf    TimeTens            ; Clear time variables
    clrf    TimeOnes
    clrf    TimeTenths
    clrf    TimeHundredths
    movlw   0x01
    movwf   TimerRunningDisplay ; Re-initialize timer display


WaitingForButtonWithDelay:
    movfw   TimerRunningDisplay
    movwf   PORTD
    movlw   0x64
    movwf   Delay3
InnerDelay:
    call    IncrementTime
    decfsz  Delay3
    goto    DelayHelper
    bcf     STATUS,C
    rlf     TimerRunningDisplay,f
    btfsc   STATUS,C
    bsf     TimerRunningDisplay,0
    goto    WaitingForButtonWithDelay
DelayHelper:
    call    Wait10ms
    call    CheckButtonActivation
    movwf   SubReturnValue
    btfss   SubReturnValue,0
    goto    InnerDelay

NextState:
    rlf TimeTens,f        ; convert ones and tens to BCD and show
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

rlf TimeTenths,f        ; convert hundredths and tenths to BCD and show
rlf TimeTenths,f
rlf TimeTenths,f
rlf TimeTenths,f
movfw TimeTenths
addwf TimeHundredths
movfw TimeHundredths
movwf PORTD

movlw 0x64
movwf Delay3
AnotherDelay7:
call Wait10ms
call Wait10ms
call Wait10ms
call Wait10ms
call Wait10ms
decfsz Delay3
goto AnotherDelay7

clrf PORTD             ; clear for 1 sec

movlw 0x64
movwf Delay3
AnotherDelay8:
call Wait10ms
decfsz Delay3
goto AnotherDelay8

goto TimerWait

     end
     
