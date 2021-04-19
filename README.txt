# digit-recognition-application

## Hello everyone,

  This is simple application to recognise digit from images.
  It uses _Convolutional Neural Network (ConvNet/CNN)_ in backend to recognise digit given in image.
  Steps to run application is given below.
  
  You can
    - open any png or jpg image or
    - draw any digit
  in this application.
  
  **Descrption of each file is as follows:**
    1. digitRecogAppl.java
            *GUI implementation for application using swing
            *used
              - swing
              - awt
              - awt.event
              - io
    2. paint.py
             *It provides gui and saves drawn image at current directory with name test.png
             *The paint object have
                - pen to draw of size 15 to 20
                - eraser to size 15 to 20
                - canvas to draw
                - done button to save image 
              *used 
                - tkinter for gui
                - win32gui to get content of drawn on canvas 
                - pillow and to save image
    3. preapreImg.py
            *As CNN model cannot operate direct on image - we need to convert it.
            *so that, this module takes the filepath that you opened or drawn as input 
            *and returns the image in numpy array like mnist handwritten digit image sample.
            *used
              - pillow
              - numpy
    4. load_model.py
            *As its name suggests, it loads the CNN model into programs memory space and prepares it for prediction.
            *It takes input
              - path of image that you selected,
              - model path and
              - a flag value which determines whether image is drawn or opened.
            *used
              - keras
    5. CNN_model.h5
            *This is our pre-trained CNN model.
            *Built with tensorflow.keras and trained with mnist dataset of handwritten digits.
            1. tensorflow.keras
                - keras is high level library build on the top of tensorflow.
                - keras provides numerous amount of classes and functions to work on deep learning algorithms.
            2. mnist dataset of handwritten digits
                - This mnist dataset is collection of 60000 28x28 pixels images of digits 0 to 9 for training.
                - Also 10000 images for testing.
            *The model's configuration is as follows
                - Layers structure
                    1. Convolutional layer of 32 filters of size 3x3 with relu activation.
                    2. Max pooling layer of 2x2,
                    3. Flatten layer to make serial the output of max pooling layerand forward to dense layer,
                    4. Dense layer of 16 neurons with relu activation,
                    5. Dense layer of 10 neurons with softmax activation.
                - Loss function used
                    1. Categorical crossentropy
                - Optimizer 
                    1. Stochastic gradient descend with learning rate 0.03 and momentum 0.9
                - Training
                    1. batch size 32
                    2. epochs 12
                    3. test split size 10000 images
                - Performance evaluation metrics
                    1. Precision score
                    2. Recall score
                    3. Categorical accuracy 
           
  **Requirements to run this application**
    1. jdk version >=7 (if not, download from here (https://www.java.com/en/download/manual.jsp))
    2. python version >=3.7.0 (if not, download from here (https://www.python.org/downloads/))
    3. tensorflow version >=2.0.0   (if not, refer this (https://www.tensorflow.org/install))
    4. pillow version >= 8.0.0  (if not, refer this (https://pypi.org/project/Pillow/))
    5. win32gui package (if not, refer this (https://stackoverflow.com/questions/20113456/installing-win32gui-python-module))
    
  **Steps to run application.**
    1. clone extract this repo at your end
    2. open cmd at location and run 
          - javac digitRecogAppl.java
          - java digitRecogAppl
    3. open or draw image
    4. click recognize
    5. and see results (The prediction error may occur due to not having 100% of accuracy of trained model)

I hope you liked it.
