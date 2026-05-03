r = int(input('ROWS? '))
c = int(input('COLUMNS? '))
m = [] 
for i in range(r): 
    m.extend([[int(v) for v in input().split()]])
print()
print('Input the multiplier matrix, already scaled if necessary')
n =[]
for i in range(c): 
    n.extend([[int(v) for v in input().split()]])
ans = [[0 for _ in range(len(n[0]))] for _ in range(r)]
for i in range(r):
    for y in range(len(n[0])): 
        g=0
        for j in range(c): 
            g += m[i][j] * n[j][y]
        ans[i][j] += g 
print()
print()
for q in range(r):
    print(*ans[q])
        