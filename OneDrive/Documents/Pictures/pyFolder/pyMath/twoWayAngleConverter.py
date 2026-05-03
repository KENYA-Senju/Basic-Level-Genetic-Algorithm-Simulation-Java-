# #1: taking decimal into times 
def spacing(message): 
    print(message)
    print("")


def main(): 
    Direction = input("Input in CLT 'a' to convert decimal format into angle or 'b' to convert " \
    "angle format into decimal format ")
    print()
    if Direction == "a": 
        finda()
    elif Direction == "b": 
        findb()
    else: 
        spacing("Sorry, Couldn't Recognize inserted chracter. Please try again")
        main()
def finda():
    n = float(input("Decimalized Number? ")) #this will be an input soon 
    print()
    theta = [int(n//1),0,0 ]
    rem = n - theta[0]
    newRem= rem*60 
    theta[1] = int(newRem//1)
    rem = newRem  - theta[1] 
    theta[2] = (rem * 60) 
    spacing(f"Angle: {theta[0]}, Minutes: {theta[1]}, Seconds: {round(theta[2],3)}")
def findb(): 
    spacing("Below, insert all three values for the angle, minutes, and seconds")
    angle = [float(v) for v in input().split()]
    number = angle[0] + angle[1]/60 +  angle[2]/3600
    spacing(f"Number: {str(round(number,3))}")
main()






#2: get the signatures and return it to decimal format 
