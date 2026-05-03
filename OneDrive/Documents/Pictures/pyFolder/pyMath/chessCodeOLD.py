import random
import string
print('WELCOME TO THE CHESSBOARD COLOR CALCULATOR')
rounds = int(input('How many rounds? '))
white = ['b1','d1', 'f1', 'h1','a2','c2','e2','g2','b3','d3','f3','h3'
'a4','c4','e4','g4','b5','d5','f5','h5','a6','c6','e6','g6','b7','d7','f7','h7',
'a8','c8','e8','g8']
black = ['a1','c1', 'e1', 'g1','b2','d2','f2','h2','a3','c3','e3','g3'
'b4','d4','f4','h4','a5','c5','e5','g5','b6','d6','f6','h6','a7','c7','e7','g7',
'b8','d8','f8','h8']
score = 0
for _ in range(rounds):
    letters = string.ascii_lowercase[:8]
    p = random.choice(letters)
    n = random.randint(1,8)
    point = p + str(n)
    print(point)
    ans = input()
    for i in range(0,32):
        # add i + 1 because the index is from 0 to n - 1
        if point == white[i - 1]:
            a = 'white'
            if ans == a:
                print('RIGHT')
                score += 1
                break
            else:
                print('WRONG')
        elif point == black[i-1 ]:
            a = 'black'
            if ans == a:
                print('RIGHT')
                score += 1
                break
            else:
                print('WRONG')
print('score = ',score/rounds * 100,'%')