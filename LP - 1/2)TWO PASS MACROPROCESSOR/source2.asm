MACRO
M1 &X, &Y, &A=AREG, &B=
MOVER &A, &X
ADD &A, ='2'
MOVER &B, &Y
ADD &B, ='6'
MEND

MACRO
M2 &P, &Q, &U=CREG, &V=DREG
MOVER &U, &P
MOVER &V, &Q
ADD &U, ='15'
ADD &V, ='10'
MEND

START 100
M1 70, 90, &B=CREG
M2 200, 300, &V=AREG, &U=BREG
END
