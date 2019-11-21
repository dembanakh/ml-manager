
def test(architecture, task_name, batch):
    import numpy as np
    ROOT_MODELS = '/home/dembanakh/.ml-manager/tasks-weights/'
    print batch.shape
    if architecture == 'VGG16':
        #from keras.applications.vgg16 import VGG16
        pass
    elif architecture == 'VGG19':
        #from keras.applications.vgg19 import VGG19
        pass
    elif architecture == 'MobileNet':
        from keras.applications.mobilenet import MobileNet
        model = keras.models.load_model(ROOT_MODELS + task_name + '.h5')
        loss, accuracy = model.evaluate(batch, np.zeros(batch.shape[0]))
        return int(batch.shape[0] * accuracy)
    else:
        return -1
    return 1
