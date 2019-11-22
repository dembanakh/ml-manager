
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
        model.compile(optimizer='adam', metrics=['accuracy'], loss='sparse_categorical_crossentropy')
        model.save(ROOT_MODELS + task_name + '.h5')
    else:
        import os
        import numpy as np
        import imageio
        samples = [i for i in os.listdir(dataset + '/samples')]
        for sample in samples:
            print sample
            img = imageio.imread(dataset + '/samples/' + sample)
            print img.shape
    return 1
