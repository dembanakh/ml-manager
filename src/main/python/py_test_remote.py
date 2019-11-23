
def test(task_name, data_path, data_type, batch_size):
    import numpy as np
    import os
    import keras
    from keras.preprocessing import image
    ROOT = '/home/dembanakh/'
    ROOT_MODELS = ROOT + '.ml-manager/tasks-weights/'
    ROOT_DATASETS = ROOT + '.ml-manager/datasets/'
    if data_type == 'IMAGE':
        input_shape = (224, 224, 3)
    else:
        print data_type
        return -2
    try:
        samples = [i for i in os.listdir(data_path)]
        path = data_path
    except OSError:
        try:
            samples = [i for i in os.listdir(ROOT + data_path)]
            path = ROOT + data_path
        except OSError:
            try:
                samples = [i for i in os.listdir(ROOT_DATASETS + data_path)]
                path = ROOT_DATASETS + data_path
            except OSError:
                print 'Cannot find', data_path
                return -1
    try:
        model = keras.models.load_model(ROOT_MODELS + task_name + '.h5')
    except IOError:
        print 'Cannot load the model from', ROOT_MODELS + task_name + '.h5'
        return -1
    X = np.zeros((batch_size, input_shape[0], input_shape[1], input_shape[2]))  # maybe depends on architecture
    y = np.zeros((batch_size, ))
    num_batches = len(samples) / batch_size
    correct = 0
    for b in range(num_batches):
        for i, sample in enumerate(samples[b * batch_size:(b+1) * batch_size]):
            try:
                img = image.load_img(path + '/samples/' + sample, target_size=input_shape)
            except IOError:
                print 'Failed to open file', path + '/samples/' + sample
                return -1
            try:
                f_lbl = open(path + '/labels/' + sample.split('.')[0] + '.txt', 'r')
            except IOError:
                print 'Failed to open file', path + '/labels/' + sample.split('.')[0] + '.txt'
                return -1
            try:
                y[i] = int(f_lbl.read())
            except ValueError:
                print 'File', path + '/labels/' + sample.split('.')[0] + '.txt', 'doesn\'t contain integer'
                return -3
        loss, accuracy = model.evaluate(X, y)
        correct += int(accuracy * batch_size)
    return float(correct) / (num_batches * batch_size)
