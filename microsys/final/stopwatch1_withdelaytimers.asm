
#include <p16F887.inc>
__CONFIG    _CONFIG1, _LVP_OFF & _FCMEN_OFF & _IESO_OFF & _BOR_OFF & _CPD_OFF & _CP_OFF & _MCLRE_OFF & _PWRTE_ON & _WDT_OFF & _INTRC_OSC_NOCLKOUT
__CONFIG    _CONFIG2, _WRT_OFF & _BOR21V

cblock     0x20
    Delay1                ; Define variables to hold the delay timers
    Delay2                ; Define variables to hold the delay timers
    Delay3                ; Define variables to hold the delay timers
    TimerWaitingDisplay   ; Holds the LED values while the timer is
                          ; waiting to start
    TimerRunningDisplay   ; Holds the LED values while the timer is running
    LookingFor            ; Holds button up/down state so we know
                          ; when it has been pressed and released
    TimerStart            ; The clock value when the timer was started
    TimerStop             ; The clock value when the timer was stopped
    TimerTenSeconds       ; The ten's place of the timer result
    TimerSeconds          ; The one's place of the timer result
    TimerTenthSeconds     ; The tenth's place of the timer result
    TimerHundredthSeconds ; The hundered's place of the timer result
endc

    org 500h
Wait1Sec
    clrf      Delay1
    movlw     0x63
    movwf     Delay2
    movlw     0x06
    movwf     Delay3
DelayLoop:
    decfsz    Delay1,f
    goto      DelayLoop
    decfsz    Delay2,f
    goto      DelayLoop
    movlw     0xf0      ; Set Delay 2 to the appropriate amount, not 0x00
    movwf     Delay2
    decfsz    Delay3,f
    goto      DelayLoop
    retlw 0x00


    org 600h
Wait2Sec
    call Wait1Sec
    call Wait1Sec
    retlw 0x00


    org 700h
Wait10Sec
    call Wait1Sec
    call Wait1Sec
    call Wait1Sec
    call Wait1Sec
    call Wait1Sec
    call Wait1Sec
    call Wait1Sec
    call Wait1Sec
    call Wait1Sec
    call Wait1Sec
    retlw 0x00


    org 0
Start:
    bsf       STATUS,RP0          ; Select Register Bank 1
;    movlw     0x01                ; Make RB0 (push button) an input
;    movwf     TRISB
    clrf      TRISD               ; Make PortD all output
    bcf       STATUS,RP0          ; Select Register Bank 0
    clrf      PORTD               ; Clear all LEDs initially
;    movlw     0x80                ; Initialize waiting display
;    movwf     TimerWaitingDisplay
;    movlw     0x80                ; Initialize running display
;    movwf     TimerRunningDisplay
;    clrf      LookingFor          ; Looking for a 0 (down) on the button
BlinkLoop:
    bsf  PORTD, 0
    call Wait2Sec
    bcf  PORTD, 0
    call Wait2Sec
    goto BlinkLoop

;WaitingLoop:
    ; check for button press (if LookingFor and RBO are unequal)
    ; if button is released, just reset LookingFor and continue
    ; if button is pressed, change stopwatch state and proceed to next
    ; operation

;RunningLoop:


end