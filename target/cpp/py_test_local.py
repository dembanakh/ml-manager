
def test(architecture, task_name, testset):
    ROOT_MODELS = '/home/dembanakh/.ml-manager/tasks-weights/'
    if architecture == 'VGG16':
        #from keras.applications.vgg16 import VGG16

    elif architecture == 'VGG19':
        #from keras.applications.vgg19 import VGG19

    elif architecture == 'MobileNet':
        #from keras.applications.mobilenet import MobileNet

    else:
        return -1
    return 1
