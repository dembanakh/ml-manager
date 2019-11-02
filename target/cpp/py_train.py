
def train(dataset, architecture):
    ROOT = '/home/dembanakh/.ml-manager/tasks-weights/'
    print 'Python received', dataset
    print 'Python received', architecture
    if dataset == 'IMAGENET':
        if architecture == 'VGG16':
            model = vgg16.VGG16(weights='imagenet')
        elif architecture == 'VGG19':
            model = vgg19.VGG19(weights='imagenet')
        else:
            return 0
        model.save(ROOT + 'test.h5')
    else:
        pass
    return 1
