import sys, os
import cv2
import numpy as np

darknet_root = '/home/rex/code/darknet/'

sys.path.append(os.path.join(darknet_root,'python/'))

import darknet as dn

cap = cv2.VideoCapture('vid.avi')

dn.set_gpu(0)
net = dn.load_net(('data/car.cfg').encode('utf-8'), 'data/car.weights'.encode('utf-8'), 0)
meta = dn.load_meta(('data/car.data').encode('utf-8'))

print(cap.isOpened())
while(cap.isOpened()):
	_, img = cap.read()

	cv2.imwrite('tmp.jpeg', img)
	result = dn.detect(net, meta, 'tmp.jpeg'.encode('utf-8'), thresh=.25)
	print(result)

	for item in result:
		coord = item[2]
		cv2.rectangle(img,(int(coord[0]-coord[2]/2),int(coord[1]-coord[3]/2)),(int(coord[0]+coord[2]),int(coord[1]+coord[3])),(0,255,0),2)

	cv2.imshow('img', img)
	if cv2.waitKey(1) & 0xFF == ord('q'):
		break

cap.release()
cv2.destroyAllWindows()