import sys, os
import cv2

darknet_root = '/home/rex/code/darknet/'

sys.path.append(os.path.join(darknet_root,'python/'))

import darknet as dn

index = ['63', '82', '88', '143', '102', '124', '154', '177', '132']

dn.set_gpu(0)
net = dn.load_net((darknet_root + 'cfg/yolo.cfg').encode('utf-8'), 'data/yolo.weights'.encode('utf-8'), 0)
meta = dn.load_meta((darknet_root + 'cfg/coco.data').encode('utf-8'))

for x in range(3):
	for y in range(3):
		name = 'data/' + index[x*3 + y] + '.jpg'
		result = dn.detect(net, meta, name.encode('utf-8'), thresh=.35)
		img = cv2.imread(name)
		print(result)
		for item in result:
			coord = item[2]
			if (item[0] == b'car' or item[0]==b'bus'):
				cv2.rectangle(img,(int(coord[0]-coord[2]/2),int(coord[1]-coord[3]/2)),(int(coord[0]+coord[2]),int(coord[1]+coord[3])),(0,255,0),2)
		cv2.imshow('img', img)
		cv2.imwrite('result/' + index[x*3 + y] + '.jpg', img)

cv2.destroyAllWindows()