
def train(dataset, architecture):
    from keras.applications.vgg16 import VGG16
    from keras.applications.vgg19 import VGG19
    ROOT = '/home/dembanakh/.ml-manager/tasks-weights/'
    #print 'Python received', dataset
    #print 'Python received', architecture
    if dataset == 'IMAGENET':
        if architecture == 'VGG16':
            model = VGG16(weights='imagenet')
        elif architecture == 'VGG19':
            model = VGG19(weights='imagenet')
        else:
            return 0
        model.save(ROOT + 'test.h5')
    else:
        pass
    return 1
