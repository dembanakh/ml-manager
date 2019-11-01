from keras.applications.mobilenet import MobileNet

def train(model_path):
    model = MobileNet(weights='imagenet')
    model.save(model_path)