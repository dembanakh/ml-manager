
def train(model_path):
    print 'hello from python'
    from keras.applications.mobilenet import MobileNet
    model = MobileNet(weights='imagenet')
    model.save(model_path)
