
def train(dataset, architecture, task_name):
    ROOT_MODELS = '/home/dembanakh/.ml-manager/tasks-weights/'
    ROOT_DATASETS = '/home/dembanakh/.ml-manager/datasets/'
    #print 'Python received', dataset
    #print 'Python received', architecture
    if dataset == 'IMAGENET':
        if architecture == 'VGG16':
            from keras.applications.vgg16 import VGG16
            model = VGG16(weights='imagenet')
        elif architecture == 'VGG19':
            from keras.applications.vgg19 import VGG19
            model = VGG19(weights='imagenet')
        elif architecture == 'MobileNet':
            from keras.applications.mobilenet import MobileNet
            model = MobileNet(weights='imagenet')
        else:
            return 0
        model.save(ROOT_MODELS + task_name + '.h5')
    else:
        # loop through all samples in ROOT_DATASETS+dataset
        pass
    return 1
