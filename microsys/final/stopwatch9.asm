
#include <p16F887.inc>
__CONFIG    _CONFIG1, _LVP_OFF & _FCMEN_OFF & _IESO_OFF & _BOR_OFF & _CPD_OFF & _CP_OFF & _MCLRE_OFF & _PWRTE_ON & _WDT_OFF & _INTRC_OSC_NOCLKOUT
__CONFIG    _CONFIG2, _WRT_OFF & _BOR21V

cblock     0x20
    Delay1                   ; Define variables to hold delay loop counters
    Delay2                   ; Define variables to hold delay loop counters
    Delay3                   ; Define variables to hold delay loop counters
    TimerDisplay             ; Holds the LED values while the timer is running
    TimeTens                 ; The timer ten's place
    TimeOnes                 ; The timer one's place
    TimeTenths               ; The timer tenth's place
	TimeHundredths           ; The timer hundredth's place
    LookingFor               ; A marker to keep track of whether a button press
                             ; is "fresh" or if the button is still depressed
                             ; from the last time we checked
    SubReturnValue           ; Since we can't use a btfss/btfsc instruction on
                             ; the working register, here's a variable to store
                             ; the return value and do the check
endc

; Subroutine Wait10ms
; Loops with fine-tuned counters to approximate 10ms (1/100 a sec)
; as closely as possible. Always returns, leaving 0 in the working register
    org 100h
Wait10ms
    movlw   0xfb             ; Loop counters have been fine-tuned to get
    movwf   Delay1           ; the most accurate wait time possible
    movlw   0x0d
    movwf   Delay2
DelayLoop:
    decfsz  Delay1,f
    goto    DelayLoop        ; Inner loop - runs 251 times first round,
    decfsz  Delay2,f         ; 256 times every following round
    goto    DelayLoop        ; Outer loop - runs 13 times
    retlw   0x00

; Subroutine Wait1s
; Calls Wait10ms in a loop 100 times
    org 120h
Wait1s
    movlw   0x64             ; Loop 100 times
    movwf   Delay3
    Loop100:
    call    Wait10ms
    decfsz  Delay3
    goto    Loop100
    retlw   0x00

; Subroutine Wait5s
; Calls Wait10ms in a loop 500 times
    org 140h
Wait5s
    movlw   0x64             ; Loop 100 times,
    movwf   Delay3           ; calling 5 times each loop
    Loop500:
    call    Wait10ms
    call    Wait10ms
    call    Wait10ms
    call    Wait10ms
    call    Wait10ms
    decfsz  Delay3
    goto    Loop500
    retlw   0x00

; Subroutine IncrementTime
; Increments the time counters by 1/100 sec. Handles conditions where
; the counters "roll-over" - for instance, when the hundredths counter
; reachs 100, it is cleared and the seconds counter is incremented.
; Returns 0x01 if the ten's place carries over, 0x00 normally
    org 200h
IncrementTime
    incf    TimeHundredths
    bcf     STATUS,C         ; Test to see if we've reached
    movfw   TimeHundredths   ; 10 hundredths by adding
    addlw   0xf6             ; 246 to the hundredths and check
    btfss   STATUS,C         ; for overflow
    retlw   0x00             ; If no rollover, just return
    clrf    TimeHundredths   ; If there is rollover, clear
    incf    TimeTenths       ; the hundredths and increment tenths
    bcf     STATUS,C         ; Repeat the rollover check
    movfw   TimeTenths
    addlw   0xf6
    btfss   STATUS,C
    retlw   0x00             ; If no rollover, just return
    clrf    TimeTenths
    incf    TimeOnes         ; Increment ones if the tenths rollover
    bcf     STATUS,C         ; Repeat the rollover check
    movfw   TimeOnes
    addlw   0xf6
    btfss   STATUS,C
    retlw   0x00             ; If no rollover, just return
    clrf    TimeOnes
    incf    TimeTens         ; Increment tens if the ones rollover
    bcf     STATUS,C         ; Repeat the rollover check
    movfw   TimeTens
    addlw   0xf6
    btfss   STATUS,C         ; If there is rollover here, we have a problem...
    retlw   0x00
    clrf    TimeTens         ; Time rolls over to 0
    retlw   0x01             ; Report the rollover
 
; Subroutine CheckButton
; Encapsulates a check for the button being pressed and a check that
; the button is not just still pressed from last button push. If the button
; has been freshly pressed, the subroutine returns, leaving a 0x01 in the
; working register.
    org 300h
CheckButton
    btfsc   LookingFor,0
    goto    LookingFor1
LookingFor0:                 ; The switch was not pressed last time
    btfsc   PORTB,0          ; Is the switch pressed now (0)?
    retlw   0x00             ; No  - just move along
    bsf     LookingFor,0     ; Yes - the button has been freshly pressed
    retlw   0x01             ; Report event activation
LookingFor1:                 ; The switch was pressed last time
    btfsc   PORTB,0          ; Is the switch pressed now (0)?
    bcf     LookingFor,0     ; No  - mark that next press (0), is a fresh press
    retlw   0x00             ; Yes - just move along

; Program Body
; 
; 
    org 0
Init:
    bsf     STATUS,RP0       ; Select Register Bank 1
    movlw   0x01
    movwf   TRISB            ; Make RB0 input
    clrf    TRISD            ; Make PortD all output
    bsf     STATUS,RP1       ; Select Register Bank 3
    clrf    ANSELH           ; Make PortB all digitial (RB0 is digital switch)
    bcf     STATUS,RP0       ; Select Register Bank 0
    bcf     STATUS,RP1
    clrf    LookingFor       ; Looking for button press (0) initially
TimerWait:
    clrf    PORTD            ; Initialize timer display for waiting state
TimerWaitingLoop:
    call    Wait10ms         ; Wait a small amount of time
    movlw   0xff             ; Refresh waiting display
    xorwf   PORTD
    call    CheckButton
    movwf   SubReturnValue
    btfss   SubReturnValue,0 ; Skip to TimerStart if button pressed
    goto    TimerWaitingLoop ; Keep waiting for button to be pressed
TimerRun:
    clrf    TimeTens         ; Clear time variables
    clrf    TimeOnes
    clrf    TimeTenths
    clrf    TimeHundredths
    movlw   0x01
    movwf   TimerDisplay     ; Re-initialize timer display
TimerRunningLoop:            ; Loop while counting (loop every 1s)
    movfw   TimerDisplay     ; Refresh the LEDs
    movwf   PORTD
    movlw   0x64             ; Inner loop to run 100 times
    movwf   Delay3
InnerLoop:                   ; Inner loop (loop every 10ms or 1/100 sec)
    call    IncrementTime    ; Increment timers by 1/100th sec
    movwf   SubReturnValue
    btfsc   SubReturnValue,0 ; If the timer has passed 99.9s
    goto    TimerWait        ; Go back to the waiting to start state
    decfsz  Delay3
    goto    WaitAndCheckButton
RotateDisplay:
    bcf     STATUS,C
    rlf     TimerDisplay,f   ; Rotate display word
    btfsc   STATUS,C         ; Check for carry
    bsf     TimerDisplay,0   ; If there was carry, add the bit on the end
    goto    TimerRunningLoop ; Continue looping
WaitAndCheckButton:
    call    Wait10ms         ; Wait 1/100th sec
    call    CheckButton      ; Check for a button push
    movwf   SubReturnValue   ; If the button was pushed,
    btfss   SubReturnValue,0 ; skip to DisplayTime
    goto    InnerLoop        ; If button not pressed, keep looping
DisplayTime:
    rlf     TimeTens,f       ; Rotate the ten's place 4 bits over and...
    rlf     TimeTens,f
    rlf     TimeTens,f
    rlf     TimeTens,f
    movfw   TimeTens
    addwf   TimeOnes         ; combine the ten's and one's place as BCDs
    movfw   TimeOnes
    movwf   PORTD            ; Show the ten's and one's place as BCD
    call    Wait5s           ; Show for 5 sec
    clrf    PORTD            ; Clear display
    call    Wait1s           ; Show blank display for 1 sec
    rlf     TimeTenths,f     ; Rotate the tenth's place 4 bits over and...
    rlf     TimeTenths,f
    rlf     TimeTenths,f
    rlf     TimeTenths,f
    movfw   TimeTenths
    addwf   TimeHundredths   ; combine the tenth's and hundredth's place
    movfw   TimeHundredths
    movwf   PORTD            ; Show the tenth's and hundredth's place as BCD
    call    Wait5s           ; Show for 5 sec
    clrf    PORTD            ; Clear display
    call    Wait1s           ; Show blank display for 1 sec
    goto    TimerWait        ; Go back to waiting state
    end                      ; Program runs forever, end is never reached
