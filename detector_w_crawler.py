import sys, os
import cv2
import numpy as np
from datetime import datetime

darknet_root = '/home/rex/code/darknet/'
img_path = "data/img/"

sys.path.append(os.path.join(darknet_root,'python/'))

import darknet as dn

last_time = datetime.now()

# Init the net
dn.set_gpu(0)
net = dn.load_net((darknet_root + 'cfg/yolo-car.2.0.cfg').encode('utf-8'), 'data/yolo-car_1500.weights'.encode('utf-8'), 0)
meta = dn.load_meta((darknet_root + 'cfg/car.data').encode('utf-8'))

images = [None] * 7
img_pointer = 0
dis_array = [None] * 4
dis_pointer = 0
rep_pointer = 0
first = True
while(True):

	curr_time = datetime.now()
	diff_time = curr_time - last_time
	if diff_time.seconds < 2:
		cv2.waitKey(10)
		continue
	last_time = curr_time

	# Check the lock
	if not os.path.isfile(img_path + 'lock'):
		if os.path.isfile(img_path + 'signal'):
			print("Signal found.")
			os.remove(img_path + 'signal')
			for img in os.listdir(img_path):
				print(img)
				r = dn.detect(net, meta, os.path.join(img_path, img).encode('utf-8'), thresh=.5)
				print(r)
				raw = cv2.imread(os.path.join(img_path, img))
				for item in r:
					c = item[2]
					cv2.rectangle(raw,(int(c[0]-c[2]/2),int(c[1]-c[3]/2)),(int(c[0]+c[2]),int(c[1]+c[3])),(0,255,0),1)
				raw = cv2.resize(raw, (0,0), fx=1.5, fy=1.5) 
				images[img_pointer] = raw
				img_pointer = (img_pointer + 1) % 7
	else:
		print("Lock here")

	if first:
		for i in range(4):
			dis_array[i] = images[i]
		first = False

	dis_array[rep_pointer] = images[dis_pointer]
	rep_pointer = (rep_pointer + 1) % 4
	dis_pointer = (dis_pointer + 1) % 7

	top_img = np.hstack((dis_array[0], dis_array[1]))
	btm_img = np.hstack((dis_array[2], dis_array[3]))

	whole_img = np.vstack((top_img, btm_img))

	cv2.imshow('detection', whole_img)
	if cv2.waitKey(200) & 0xFF == ord('q'):
		break

cv2.destroyAllWindows()