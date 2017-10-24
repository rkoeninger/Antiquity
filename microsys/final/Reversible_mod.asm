
#include <p16F887.inc>
__CONFIG    _CONFIG1, _LVP_OFF & _FCMEN_OFF & _IESO_OFF & _BOR_OFF & _CPD_OFF & _CP_OFF & _MCLRE_OFF & _PWRTE_ON & _WDT_OFF & _INTRC_OSC_NOCLKOUT
__CONFIG    _CONFIG2, _WRT_OFF & _BOR21V

cblock     0x20
    Delay1              ; Define variables to hold the delay timers
    Delay2              ; Define variables to hold the delay timers
    TimerRunningDisplay ; Holds the LED values while the timer is running
    LookingFor
endc

     
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
MainLoop:
     movf      TimerRunningDisplay,w      ; Copy the display to the LEDs
     movwf     PORTD
     nop                      ; wait 5uS for A2D amp to settle and capacitor to charge.
     nop                      ; wait 1uS
     nop                      ; wait 1uS
     nop                      ; wait 1uS
     nop                      ; wait 1uS
     bsf       ADCON0,GO_DONE ; start conversion
     btfss     ADCON0,GO_DONE ; this bit will change to zero when the conversion is complete
     goto      $-1
     movf      ADRESH,w       ; Copy the display to the LEDs
     movwf     Delay2

A2DDelayLoop:
     incfsz    Delay1,f       ; Waste time.  
     goto      A2DDelayLoop   ; The Inner loop takes 3 instructions per loop * 256 loopss = 768 instructions
     incfsz    Delay2,f       ; The outer loop takes and additional 3 instructions per lap * 256 loops
     goto      A2DDelayLoop   ; (768+3) * 256 = 197376 instructions / 1M instructions per second = 0.197 sec.
                              ; call it a two-tenths of a second.

     movlw     .13            ; Delay another 10mS plus whatever was above
     movwf     Delay2
TenmSdelay:
     decfsz    Delay1,f
     goto      TenmSdelay
     decfsz    Delay2,f
     goto      TenmSdelay

     btfsc     LookingFor,0
     goto      LookingFor1
LookingFor0:
     btfsc     PORTB,0        ; is the switch pressed (0)
     goto      Rotate
     bsf       LookingFor,0   ; yes  Next we'll be looking for a 1
     movlw     0xFF           ; load the W register incase we need it
     goto      Rotate

LookingFor1:
     btfsc     PORTB,0        ; is the switch pressed (0)
     bcf       LookingFor,0
     
Rotate:
     bcf       STATUS,C       ; ensure the carry bit is clear
;     goto      RotateLeft
;RotateRight:
;     rrf       Display,f
;     btfsc     STATUS,C       ; Did the bit rotate into the carry?
;     bsf       Display,7      ; yes, put it into bit 7.

;     goto      MainLoop
;RotateLeft:
     rlf       TimerRunningDisplay,f
     btfsc     STATUS,C                   ; did it rotate out of the display
     bsf       TimerRunningDisplay,0      ; yes, put it into bit 0
     goto      MainLoop
     
     end
     
