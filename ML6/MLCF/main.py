import numpy as np


def inp():
    return list(map(int, input().split()))


n = int(input())
xs = []
ys = []

for i in range(n):
    x, y = inp()
    xs.append(x)
    ys.append(y)

x, y = np.array(xs), np.array(ys)
rx, ry = x.argsort().argsort(), y.argsort().argsort()

print(1 - (6 * sum((rx - ry) ** 2)) / (n * (n ** 2 - 1)))
