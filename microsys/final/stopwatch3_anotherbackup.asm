
#include <p16F887.inc>
__CONFIG    _CONFIG1, _LVP_OFF & _FCMEN_OFF & _IESO_OFF & _BOR_OFF & _CPD_OFF & _CP_OFF & _MCLRE_OFF & _PWRTE_ON & _WDT_OFF & _INTRC_OSC_NOCLKOUT
__CONFIG    _CONFIG2, _WRT_OFF & _BOR21V

cblock     0x20
    Delay1                ; Define variables to hold the delay timers
    Delay2                ; Define variables to hold the delay timers
    Delay3                ; Define variables to hold the delay timers
    DelayReturnValue      ; Used to test the return value of
                          ;     a delay subroutine:
                          ;         0 if there was no interrupt
                          ;         during the delay time
                          ;         1 if the delay loop quit because it
                          ;         saw a button push
    TimerWaitingDisplay   ; Holds the LED values while the timer is waiting
    TimerRunningDisplay   ; Holds the LED values while the timer is running
    ButtonStillPressed    ; Is a 1 when the button has already been
                          ; activated and the user's finger is
                          ; still holding it down
    TimerStart            ; The clock value when the timer was started
    TimerStop             ; The clock value when the timer was stopped
    TimerTenSeconds       ; The ten's place of the timer result
    TimerSeconds          ; The one's place of the timer result
    TimerTenthSeconds     ; The tenth's place of the timer result
    TimerHundredthSeconds ; The hundered's place of the timer result
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
    bsf       STATUS,RP0          ; Select Register Bank 1
    movlw     0xFF
    movwf     TRISA               ; Make PortA all input
    movlw     0x01                ; Make RB0 (push button) an input
    movwf     TRISB
    movlw     0x00                ; Make PortD all output
    movwf     TRISD
    bcf       STATUS,RP0          ; Select Register Bank 0
    clrf      PORTD               ; Clear all LEDs initially
    movlw     0xff                ; Initialize waiting display
    movwf     TimerWaitingDisplay
    movlw     0x80                ; Initialize running display
    movwf     TimerRunningDisplay
    movlw     0x00                ; Initialize delay return value
    movwf     DelayReturnValue
    movlw     0x01
    movwf     ButtonStillPressed

;;;;;;;;;;;;;;;;;;button interrupt test code

WaitingForButton:
    btfsc PORTB,0
	goto WaitingForButton
    
	bsf PORTD,0
	goto $


bsf PORTD,1
WaitLoop:
	;call Wait10ms
	btfsc PORTB,0
	goto WaitLoop
bcf PORTD,1
bsf PORTD,0
goto $

;    goto      WaitingLoop         ; The initial position is the waiting loop
;WaitingLoop:
;    movfw     TimerWaitingDisplay ; Display waiting pattern
;    movwf     PORTD
;    call      Wait2Sec
;    movwf     DelayReturnValue
;    btfsc     DelayReturnValue,0
;    goto      RunningLoop
;    clrf      PORTD               ; Clear waiting pattern
;    call      Wait2Sec
;    movwf     DelayReturnValue
;    btfsc     DelayReturnValue,0
;    goto      RunningLoop
;    goto      WaitingLoop
;RunningLoop:
;    bcf       STATUS,C              ; Ensure the carry bit is clear
;    rlf       TimerRunningDisplay,f
;    btfsc     STATUS,C              ; If the bit rotated out of the word
;    bsf       TimerRunningDisplay,0 ;     put it into bit 0
;    movfw     TimerRunningDisplay   ; Display the running pattern
;    movwf     PORTD
;    call      Wait1Sec
;    goto      RunningLoop

end
