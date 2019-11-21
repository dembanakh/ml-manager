
def test(task_name, batch, labels):
    import numpy as np
    import keras
    ROOT_MODELS = '/home/dembanakh/.ml-manager/tasks-weights/'
    print batch.shape
    print labels.shape
    model = keras.models.load_model(ROOT_MODELS + task_name + '.h5')
    loss, accuracy = model.evaluate(batch, labels)
    return int(batch.shape[0] * accuracy)

