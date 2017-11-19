import sys, os

import cv2
import numpy as np

index = ['63', '82', '88', '143', '102', '124', '154', '177', '132']

img = []
for x in range(3):
	img_x = []
	for y in range(3):
		img_x.append(cv2.imread('result/' + index[x*3 + y] + '.jpg'))

	row_img = np.hstack((img_x[0], img_x[1], img_x[2]))
	img.append(row_img)

whole_img = np.vstack((img[0], img[1], img[2]))
cv2.imshow('carDetector', whole_img)
cv2.imwrite('result/whole.jpeg', whole_img)

cv2.destroyAllWindows()

