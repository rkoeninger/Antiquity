%		ECE349_SecondOrderRLC.m			
% Simulation of a second-order dynamics of a RLC circuit
% Both time domain response and frequency domain response
% I have written the program assuming nonideal inductor, and nonideal capacitor
%   but set the value to ideal values. Change those values if necessary
%
% For time domain simulation, I am using the state space model. 
% The dynamics would be in the
%		State space form		X_dot = A*X + b*u.
%   X = [v_C; i_L], the state vector
% For X_dot, see the function ECE349_SecondOrderRLC_f.m
%
% The capacitor model consists of an ideal capacitor in parallel with the
% leakage resistor R_C_Leakage, and these in series with R_C_SESR.
%
% The inductor model is obtained by a dual of the non-ideal capactor
% leading to a model consisting of an ideal inductor of value L in parallel
% with a resistor of value R_L_Leakage and both of this in series with a
% resistor of value R_L_ESR.
%
% The total circuit thus consists of: v_i (source voltage) in series with
% R_in1 (the resistance of the source) in series with R1 (the load
% resistor) in series with the non-ideal inductor and the non-ideal capacitor
% in series with the
% parallel combination of R_ct (the small resistor used ot measure the
% current) and R_Scope (the input resistance of the scope).
%
% Let R_eq = R_ct parallel R_Scope or R_eq = R_ct if G_scope = 0; otherwise
% R_eq = R_ct*R_Scope/(R_ct + R_Scope)
%
% Let R_Series = R_in1 + R1 + R_L_ESR + R_C_ESR + R_eq
% Let K1 = 1 + R_Series*G_L_Leakage
%
% Then v_L = (v_i - R_Series*i_L - v_C)/K1
% di_L/dt =  (v_i - R_Series*i_L - v_C)/(L*K1)   - First state equation
%
% i_C = i_L + v_L*G_L_Leakage - v_C*G_C_Leakage
%     = i_L + (v_i - R_Series*i_L - v_C)*G_L_Leakage/K1 - v_C*G_C_Leakage
% Or di_C/dt = (i_L*(1-G_L_Leakage*R_Series/K1) - v_C*(G_L_Leakage/K1 +
% G_C_Leakge) + G_L_Leakage*v_i/K1)/C   ---- Second state equation
%
%
% For frequency response also,
% I will write the transfer function based on nonideal inductor, nonideal
% capacitor,
% nonideal scope etc. By setting their coeeficients to the ideal value, we
% can also obtain the ideal frequency response.
%
% One transfer function between the voltage accross the load resistor R1 and 
% the input voltage, T(s) = V_R1(s)/V_i(s) =
% R1(1+L*G_L_Leakage*s)*(G_C_Leakage+C*s)/D(s)
% where D(s) = R_series*(1+L*G_L_Leakage*s)*(G_C_Leakage+C*s) +
% L*s*(G_C_Leakage+C*s) +(1+L*G_L_Leakage*s)
%
%
close all;	% closes all open figures
clear all;	% clears all variables; better to do this
% so that your program results will not be corrupted
% by values in the memory.
clc;
%
global SysOrder Switch DC omega1 omega2 omega3 omega4 L C R_Series G_L_Leakage G_C_Leakage K1
 % to pass on values to other functions; more than one variables
 % are passed by separating them by a space. Here we will use switch as a value
 % to specify if we are calculating zero-input response, DC response etc.
 SysOrder = 2; % We will use this to modify system order
 R = 0.1;  R1 = R; % load resistor
 L = 0.005 % One  milli Henry inductor
 R_L_ESR = 60 % Equivalent series resistance of a inductor
G_L_Leakage = 0 % Leakage resistance of a inductor - comes in parallel
% Remember the ideal value of R_Leakage becomes infinity and hence cannot
% be used as such in calculations in a program
 C = 0.00022 % One  milli Farad capator
 R_C_ESR = 100 % Equivalent series resistance of a inductor
G_C_Leakage = 0 % Leakage resistance of a capacitor
G_Scope = 0
R_in1 = 0 % Input resistance of the source
R_ct = 0;
% We need to be carful while calculating R_eq since intermediate values can
% become infinity. Hence the following complex if statement.
if ((G_Scope == 0) & R_ct == 0)
    R_eq = 0
else
    if (G_Scope == 0)
        R_eq = R_ct
    elseif (R_ct == 0)
        R_eq = 1/G_Scope
    else 
        R_eq = 1/(G_Scope + 1/R_ct)
    end
end
R_Series = R_in1 + R1 + R_L_ESR + R_C_ESR + R_eq
K1 = 1 + R_Series*G_L_Leakage
 DC = 10;	% a simple assignment statement; Here we use it to represent DC input
 omega1 = 2*pi*1;	% frequency of 1 Hz
 omega2 = 2*pi*10;	% frequency of 10 Hz
 omega3 = 1/sqrt(L*C);	% The natural frequency of oscillation
 omega4 = 2*pi*300;	% frequency of 300 Hz
%
% The ideal RLC circuit with R=1 and L = 1 milli Henry and C = 1 micro Farad
% should lead to a bandpass
% response between V_i and V_R1(I am using uppercase to denote that they are 
% in the frequency or Laplace domain)with the peak response at Omega_Zero = 1000
% rad/sec or f_zero = 159.23 Hz. At that point the phase should be 0 degrees
%
%	Now we are going to define elements needed for the built-in function
%	ODE45 for solving a set of differential (linear as well as nonlinear)
%	differential equations
%   
options = odeset('RelTol',1e-8,'AbsTol',[1e-8]);
t_initial = 0;
t_final = 2;
time_range = [t_initial t_final];
%
%-------------------------------------------------------------------------------------
% First, zero-input response
%
Switch = 1;	% we will use this value for zero-input response
DC = 0;
%	
InitCond = [1 -1]	% a three element row vector; elements are separated by a space
InitCond_Transposed = InitCond' % This transposes the vector
%	Below is the call to ODE; T is the output vector of times where
%	the response was calculated. They are calculated at non-uniform times
%	X is the resulting state vector
%
[T,X] = ode45('ECE349_SecondOrderRLC_f',time_range,InitCond,options);
input = DC*T;
v_L = (input - R_Series*X(:,2) - X(:,1))/K1; % v_L = (v_i - R_Series*i_L - v_C)/K1
v_R1 = R1*(X(:,2) + G_L_Leakage*v_L); % v_R1 = R1*i_Series = R1*(i_L + i_R_L_Leakage) 
                                 %           = R1*(i_L + G_L_Leakage*v_L)
%
y_min = floor(min(min(X)));
y_max = ceil(max(max(X)));
% Now we can plot the results
%
figure;	% establishes a new figure 1
plot(T,X(:,1), T,v_R1,'r' ) % 
grid on;	% places grids
title('Second order RLC circuit transient response');	% titlling
xlabel('time t');					% x-axis labeling
ylabel('Capacitor voltage and Voltage across the load resistor');			% y-axis
legend('Cap Voltage', 'Load Resistor Vol')		% helps to identify plots when you have more than one
axis ([0 0.1 y_min y_max])
hold on								% 
%
%------------------------------------------------------------------------------
%
% Next, zero-state DC response
%
Switch = 2;	% we will use this value for zero-state DC response
DC = 1;		% DC input
InitCond = [0 0];	% zero IC
[T,X] = ode45('ECE349_SecondOrderRLC_f',time_range,InitCond,options);
v_size = size(X);	
input = DC*T;
v_L = (input - R_Series*X(:,2) - X(:,1))/K1; % v_L = (v_i - R_Series*i_L - v_C)/K1
v_R1 = R1*(X(:,2) + G_L_Leakage*v_L); % v_R1 = R1*i_Series = R1*(i_L + i_R_L_Leakage) 
                                 %           = R1*(i_L + G_L_Leakage*v_L)
y_min = floor(min(min(X)));
y_max = ceil(max(max(X)));
%
%
figure;	% establishes figure 2
plot(T,X(:,1), T,v_R1,'r' ) % 
grid on;	% places grids
title('Second order RLC circuit DC response - Omega = 0');	% titlling
xlabel('time t');					% x-axis labeling
ylabel('Capacitor voltage and Voltage across the load resistor');			% y-axis
legend('Cap Voltage', 'Load Resistor Vol')	
axis ([0 0.1 y_min y_max])
hold on
%
%-----------------------------------------------------------------------------
%
% Next, zero-state Sinusoidal response - for one sinusoid
%
Switch = 3;	% we will use this value for zero-state sinusoidal response
% We won't define the input here since we don't know the time during
% which the state is calculated. Instead, let us pass the frequency alone
% to the function that calculates X_dot at a particular time. We will omega1
% for this call
%
InitCond = [0 0];	% zero IC
[T,X] = ode45('ECE349_SecondOrderRLC_f',time_range,InitCond,options);
v_size = size(X);	
%
input = sin(omega1*T);	% 
%
v_L = (input - R_Series*X(:,2) - X(:,1))/K1; % v_L = (v_i - R_Series*i_L - v_C)/K1
v_R1 = R1*(X(:,2) + G_L_Leakage*v_L); % v_R1 = R1*i_Series = R1*(i_L + i_R_L_Leakage) 
                                 %           = R1*(i_L + G_L_Leakage*v_L)
%
y_min = floor(min(min(X)));
y_max = ceil(max(max(X)));
figure;	% establishes a figure 3
plot(T,X(:,1), T,v_R1,'r' ) % 
grid on;	% places grids
title('Second order RLC circuit Sinusoidal response - Omega = 2*PI');	% titlling
xlabel('time t');					% x-axis labeling
ylabel('Capacitor voltage and Voltage across the load resistor');			% y-axis
legend('Cap Voltage', 'Load Resistor Vol')	
axis ([0 t_final*2*pi/omega1 y_min y_max]) %
hold on
%--------------------------------------------------------------------
%
% Next, zero-state Sinusoidal response - for second sinusoid
%
Switch = 4;	% we will use this value for zero-state sinusoidal response
%
InitCond = [0 0];	% zero IC
%
[T,X] = ode45('ECE349_SecondOrderRLC_f',time_range,InitCond,options);
v_size = size(X);	
input = sin(omega2*T);	% I have used vector notations to calculate the
v_L = (input - R_Series*X(:,2) - X(:,1))/K1; % v_L = (v_i - R_Series*i_L - v_C)/K1
v_R1 = R1*(X(:,2) + G_L_Leakage*v_L); % v_R1 = R1*i_Series = R1*(i_L + i_R_L_Leakage) 
                                 %           = R1*(i_L + G_L_Leakage*v_L)
%
y_min = floor(min(min(X)));
y_max = ceil(max(max(X)));
figure;	% establishes a  figure 4
plot(T,X(:,1), T,v_R1,'r' ) % 
grid on;	% places grids
title('Second order RLC circuit Sinusoidal response - Omega = 20*PI');	% titlling
xlabel('time t');					% x-axis labeling
ylabel('Capacitor voltage and Voltage across the load resistor');			% y-axis
legend('Cap Voltage', 'Load Resistor Vol')	
axis ([0 t_final*8*pi/omega2 y_min y_max])
hold on					% 
%
%--------------------------------------------------------------------------------------------

% Next, zero-state Sinusoidal response - for third sinusoid
%
Switch = 5;	% we will use this value for zero-state sinusoidal response
InitCond = [0 0];	% zero IC
[T,X] = ode45('ECE349_SecondOrderRLC_f',time_range,InitCond,options);
input = sin(omega3*T);	% I have used vector notations to calculate the
% input vector in one statement
%
v_L = (input - R_Series*X(:,2) - X(:,1))/K1; % v_L = (v_i - R_Series*i_L - v_C)/K1
v_R1 = R1*(X(:,2) + G_L_Leakage*v_L); % v_R1 = R1*i_Series = R1*(i_L + i_R_L_Leakage) 
                                 %           = R1*(i_L + G_L_Leakage*v_L)
%
%
% Now we can plot the input and the results
%
y_min = floor(min(min(X)));
y_max = ceil(max(max(X)));
figure;	% establishes a figure 5
plot(T,X(:,1), T,v_R1,'r' ) % 
grid on;	% places grids
title('Second order RLC circuit Sinusoidal response - Omega = Natural frequency');	% titlling
xlabel('time t');					% x-axis labeling
ylabel('Capacitor voltage and Voltage across the load resistor');			% y-axis
legend('Cap Voltage', 'Load Resistor Vol')	
axis ([0 t_final*16*pi/omega3 y_min y_max])
hold on				% 
%
%---------------------------------------------------------------------------------------
%
% Next, zero-state Sinusoidal response - for fourth sinusoid
%
Switch = 6;	% we will use this value for zero-state sinusoidal response
%
InitCond = [0 0];	% zero IC
%
[T,X] = ode45('ECE349_SecondOrderRLC_f',time_range,InitCond,options);
input = sin(omega4*T);	% 
v_L = (input - R_Series*X(:,2) - X(:,1))/K1; % v_L = (v_i - R_Series*i_L - v_C)/K1
v_R1 = R1*(X(:,2) + G_L_Leakage*v_L); % v_R1 = R1*i_Series = R1*(i_L + i_R_L_Leakage) 
                                 %           = R1*(i_L + G_L_Leakage*v_L)
%
y_min = floor(min(min(X)));
y_max = ceil(max(max(X)));
figure;	% establishes a figure 6
plot(T,X(:,1), T,v_R1,'r' ) % 
grid on;	% places grids
title('Second order RLC circuit Sinusoidal response - Omega = 600*PI');	% titlling
xlabel('time t');					% x-axis labeling
ylabel('Capacitor voltage and Voltage across the load resistor');			% y-axis
legend('Cap Voltage', 'Load Resistor Vol')	 
axis ([0 t_final*32*pi/omega4 y_min y_max])
hold on					% 
%
%---------------------------------------------------------------------------------
%
% Finally, zero-state Sinusoidal response - input is a sum of four sinusoids plus DC
%
Switch = 7;	% we will use this value for zero-state sinusoidal response
% We won't define the input here since we don't know the time during
% which the state is calculated. Instead, let us pass the frequency alone
% to the function that calculates X_dot at a particular time. We will omega1
% for this call
%
InitCond = [0 0];	% zero IC
[T,X] = ode45('ECE349_SecondOrderRLC_f',time_range,InitCond,options);
input = sin(omega1*T) + sin(omega2*T) + sin(omega3*T) + sin(omega4*T) + DC;
v_L = (input - R_Series*X(:,2) - X(:,1))/K1; % v_L = (v_i - R_Series*i_L - v_C)/K1
v_R1 = R1*(X(:,2) + G_L_Leakage*v_L); % v_R1 = R1*i_Series = R1*(i_L + i_R_L_Leakage) 
                                 %           = R1*(i_L + G_L_Leakage*v_L)
%
y_min = floor(min(min(X)));
y_max = ceil(max(max(X)));
figure;	% establishes a figure 7
plot(T,X(:,1), T,v_R1,'r' ) % 
grid on;	% places grids
title('Second order RLC circuit response to weighted sum of five sinusoids');	% titlling
xlabel('time t');					% x-axis labeling
ylabel('Capacitor voltage and Voltage across the load resistor');			% y-axis
legend('Cap Voltage', 'Load Resistor Vol')	
axis ([0 t_final/8 y_min y_max])
hold on					
%
%---------------------------
%
% Frequency response calculation. I will do it from the transfer function.
% Note the oscilloscope uses an entirely different method.
%
%
% Now calculate the freq response by varying Omega
% 
for k = 1:301 ; 
    Frequency(k) = (k-1); % The real frequency in Hz
    Omega(k) = 2*pi*Frequency(k); % The real frequency variable in radians per second
    s = 0 + j*Omega(k) ; % The complex frequency variable along the imaginary axis
    TransferFunction(k) = R1*(1+L*G_L_Leakage*s)*(G_C_Leakage+C*s)/ ...
    (R_Series*(1+L*G_L_Leakage*s)*(G_C_Leakage+C*s)+L*s*(G_C_Leakage+C*s)+1+L*G_L_Leakage*s);
    TransferFunctionMagnitude(k) = abs(TransferFunction(k));
    TransferFuctionPhase(k) = angle(TransferFunction(k));
end
figure;	
plot(Frequency,TransferFunctionMagnitude) 
grid on;	
title('Second order RLC circuit Magnitude Response');	% titlling
xlabel('Frequency in Hz');					% x-axis labeling
ylabel('Magnitude Response from V_i to V_R1');			% y-axis
%	
hold on;	
figure;	
plot(Frequency, TransferFuctionPhase*180/pi) 
grid on;	
title('Second order RLC circuit Phase Response');	% titlling
xlabel('Frequency in Hz');					% x-axis labeling
ylabel('Phase Response from V_i to V_R1 in degrees');			% y-axis
% legend('Magnitude response', 'Phase Response')		
hold on	

