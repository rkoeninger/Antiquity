%	ECE349_SecondOrderRLC_f.m
%	A function that calculates the derivative of the dynamics, that is,
%	X_dot = A*X + b*u
%	for use fo the ODE45 solver
%
function dx = ECE349_SecondOrderRLC_f(t,x)	% Note the way the function is defined
                                    % and the file name used
                                    % For details, help ode45
global SysOrder Switch DC omega1 omega2 omega3 omega4 L C R_Series G_L_Leakage G_C_Leakage K1
% values for DC, frequncy etc.
%
if Switch == 1
   u = 0;
elseif Switch == 2
   u = DC;
elseif Switch == 3
   u = sin(omega1*t);
elseif Switch == 4
   u = sin(omega2*t);
elseif Switch == 5
   u = sin(omega3*t);
elseif Switch == 6
   u = sin(omega4*t);
elseif Switch == 7
   u = sin(omega1*t)+ sin(omega2*t)+ sin(omega3*t) + sin(omega4*t) + DC   ;
end
%
v_i = u;
v_C = x(1); % First state variable, the capacitor voltage
i_L = x(2); % Second state variable, the inductor current
%
dx1 = (i_L*(1-G_L_Leakage*R_Series/K1) - v_C*(G_L_Leakage/K1 +  ...
G_C_Leakage) + G_L_Leakage*v_i/K1)/C;  % First state equation
%
dx2 =  (v_i - R_Series*i_L - v_C)/(L*K1); %   - First state equation
%
dx = [dx1;dx2];
%

