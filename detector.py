# Stupid python path shit.
# Instead just add darknet.py to somewhere in your python path
# OK actually that might not be a great idea, idk, work in progress
# Use at your own risk. or don't, i don't care

import sys, os

darknet_root = '/home/rex/code/darknet/'

sys.path.append(os.path.join(darknet_root,'python/'))

import darknet as dn

dn.set_gpu(0)
net = dn.load_net((darknet_root + 'cfg/yolo.cfg').encode('utf-8'), 'yolo.weights'.encode('utf-8'), 0)
meta = dn.load_meta((darknet_root + 'cfg/coco.data').encode('utf-8'))

r = dn.detect(net, meta, 'test1.jpeg'.encode('utf-8'), thresh=.25)
print(r)

