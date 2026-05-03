'''
the focus is a point that is k inside the vertex of the parabola 

the ditronix is a line that is situated k outside the vertex of a parabola 

0.5: switch data function, in order to switch two sets of data based on the argumented Y or N parameter
1. inputting values:  (rounded to two digits in every case please)
    UI will need to manually input the COEFFICIENTS of the formula (coefficient, coefficient, contsant)
    we will start with only a 2nd degree polynomial 
    UI must include whether the equation is (set to Y) or (set to X). In the case of X, we will need to switch all the data
    before outputting values
2. -b/2a will find us the vertex Y/X, and we can plug into a preset equation to find X/Y
    output this vertex 
2.5: c = 1/4a
3. if (set to y), we will situate the focus at (vertexX,vertexY + c)
4. if (set to y), we will situate the directrix at y = vertexY - c
    a = 1/4c, 

START CODING 
'''
def Q(lst,val):
    # we will start with a 2 degree polynomial 
    return  lst[0] * pow(val,2) + lst[1] * val + lst[2]
def switch(X,Y,con):
    if con.lower() == 'true': 
        return X,Y 
    return Y,X
    
A,B,C = list(map(int,input().split()))
EQ = [A,B,C]
CHECK = input("Equation set to Y? ")
V = [-B/(2*A),Q(EQ,-B/(2*A))]
print()
print(f'VERTEX: {switch(V[0],V[1],CHECK)}') 

C1 = 1/(4*A)
F,D = [V[0],V[1] + C1], V[1] - C1 
ans = switch(F,D,CHECK)
ans = ans if CHECK.lower() == 'true' else ans[::-1]
print()
print()
print(f"Focus: {ans[0]}")
print()
print(f'Direconix: {'y' if CHECK.lower() == 'true' else 'x'} = {ans[1]}')

# deal with rounding later 
#!! call switch function every time before you output values 
