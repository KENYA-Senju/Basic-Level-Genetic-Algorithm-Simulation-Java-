f = 10
a,b = 0,0
AVERAGE,cnt =  0,0
LAST_VAL = 0
d = {}

print() 
print('ROOTS: ')
#THIS CODE IS UI VARIED
# Note: if you were evaluating for 1 to 2, please select 10 to 20 (x,y == x*10,y*10) :)
l,r = -9,9
def graphSOLVE(j):
    #THIS CODE IS UI VARIED
    # PLEASE FIX THIS EQUATION! THIS IS NOT COMPLETELY CORRECT AT THE MOMENT 
    return pow(j,3)-1*pow(j,2)+4
def call(a,b): 
    k = round((a + b)/2,2)
    x = round(graphSOLVE((a + b)/2),3)
    
    print(f"ESTIMATED VALUE: {k}, ESTIMATED ANSWER: {x}")
    print()
    print("SIGN CHANGES:")
    print(f'{a}: {'+' if graphSOLVE(a) > 0 else '-'}')
    print(f'{k}: {'+' if x> 0 else '-'}')
    print(f'{b}: {'+' if graphSOLVE(b) > 0 else '-'}')
    print()
    return 100 - abs(x*100)
    
for i in range(l*10,r*10):
    j = i/f
    d[j] = graphSOLVE(j)

num = d[next(iter(d))]
for i in d.keys():
    # we can use a return value to consider if the value is half
    if ((d[i] > num) and (d[i] > 0 and num < 0)) or((d[i] < num) and (d[i] < 0 and num >0)) or min(num,d[i]) ==0:
        b,a = i,LAST_VAL
        AVERAGE += call(a,b)
        cnt += 1
    LAST_VAL = i
    num = d[i]
print()
print(f"Average Accuracy: {round(AVERAGE/cnt,2)}%")

print()
print("TESTED DATA:")
print(d)

# next, we will calculate the test case accuracy 
#to turn decimals into percents, simply multipliy the number by 100, then we will find the average of all the percents 
# and return the average accuracy of the compiler 
# this code assumes that you have found the correct range, otherwise, there will be an error in the
# calculation

'''
step2: after sorting the list, we will find the closest number to 0 that is positive,
similarly with negatives.  (CHECK)

step3: find the range of a and b ((a + b)/2) and graphSOLVE((a + b)/2), this is your answer (SO FAR)
(CHECK)

step4: compare the sign changes of all the numbers, and record the data for reevalutation :)
(CHECK)

step5: clean up the code (PLEASE)
'''

'''
0.8 -
0.85
0.9 +
CAN YOU OPTIMIZE THIS CODE EVEN FURTHER?
'''