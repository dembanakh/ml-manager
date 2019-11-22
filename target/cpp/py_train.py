
def train(dataset, architecture, task_name):
    ROOT_MODELS = '/home/dembanakh/.ml-manager/tasks-weights/'
    ROOT_DATASETS = '/home/dembanakh/.ml-manager/datasets/'
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
        input_shape = (224, 224, 3)
        import os
        import numpy as np
        from keras.preprocessing import image
        try:
            samples = [i for i in os.listdir(dataset + '/samples')]
        except OSError:
            print 'There is no such directory', ROOT_DATASETS + dataset + '/samples'
            return 0
        X = np.zeros((len(samples), input_shape[0], input_shape[1], input_shape[2]))  # maybe depends on architecture
        y = np.zeros((len(samples), ))
        for i, sample in enumerate(samples):
            try:
                img = image.load_img(dataset + '/samples/' + sample, target_size=input_shape)
            except IOError:
                print 'Failed to open file', ROOT_DATASETS + dataset + '/samples/' + sample
                return 0
            try:
                f_lbl = open(dataset + '/labels/' + sample.split('.')[0] + '.txt', 'r')
            except IOError:
                print 'Failed to open file', ROOT_DATASETS + dataset + '/labels/' + sample.split('.')[0] + '.txt'
                return 0
            try:
                y[i] = int(f_lbl.read())
            except ValueError:
                print 'File', ROOT_DATASETS + dataset + '/labels/' + sample.split('.')[0] + '.txt', 'doesn\'t contain integer'
        if architecture == 'VGG16':
            from keras.applications.vgg16 import VGG16, preprocess_input
            model = VGG16()
            for i in range(X.shape[0]):
                X[i] = preprocess_input(X[i])
        elif architecture == 'VGG19':
            from keras.applications.vgg19 import VGG19, preprocess_input
            model = VGG19()
            for i in range(X.shape[0]):
                X[i] = preprocess_input(X[i])
        elif architecture == 'MobileNet':
            from keras.applications.mobilenet import MobileNet, preprocess_input
            model = MobileNet()
            for i in range(X.shape[0]):
                X[i] = preprocess_input(X[i])
        else:
            return 0
        model.compile(optimizer='adam', metrics=['accuracy'], loss='sparse_categorical_crossentropy')
        model.fit(X, y)
        model.save(ROOT_MODELS + task_name + '.h5')
    return 1
