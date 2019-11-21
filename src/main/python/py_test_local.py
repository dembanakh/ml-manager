
def test(architecture, task_name, batch):
    import numpy as np
    ROOT_MODELS = '/home/dembanakh/.ml-manager/tasks-weights/'
    print type(batch)
    if architecture == 'VGG16':
        #from keras.applications.vgg16 import VGG16
        pass
    elif architecture == 'VGG19':
        #from keras.applications.vgg19 import VGG19
        pass
    elif architecture == 'MobileNet':
        #from keras.applications.mobilenet import MobileNet
        pass
    else:
        return -1
    return 1
