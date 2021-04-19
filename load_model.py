#import tensorflow as tf
import keras.models as md
from keras.preprocessing.image import load_img
from keras.preprocessing.image import img_to_array
import sys
from prepareImg import imageprepare

#@tf.autograph.experimental.do_not_convert
def load_model(image_path, model_path, drawen= False):
	model = md.load_model(model_path)

	if drawen:
		img = imageprepare(image_path)
	else:
		img = load_img(image_path,color_mode='grayscale',target_size=(28,28))
		img = img_to_array(img)
		img = img.reshape(1,28,28,1)
		img = img.astype('float32')
		img = img/255.0

	result = model.predict_classes(img)
	print(result[0])

load_model(sys.argv[1],sys.argv[2],sys.argv[3])