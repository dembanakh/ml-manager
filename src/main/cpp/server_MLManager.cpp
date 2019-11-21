#include "server_MLManager.h"

#include <iostream>
#include "Python.h"
#include "numpy/arrayobject.h"

JNIEXPORT jboolean JNICALL Java_server_MLManager_train(JNIEnv * env, jobject thisObject, jstring dataset, jstring architecture, jstring taskName) {
    PyObject *pName, *pModule, *pDict, *pFunc, *pArgs, *pValue;

    Py_Initialize();
    //std::cout << "Initialized" << std::endl;
    PyRun_SimpleString("import sys");
    PyRun_SimpleString("import os");
    PyRun_SimpleString("os.chdir('target/cpp')");
    PyRun_SimpleString("sys.path.append(os.getcwd())");

    pName = PyString_FromString("py_train");
    pModule = PyImport_Import(pName);
    //std::cout << "got pModule = " << pModule << std::endl;
    if (pModule == NULL) return 0;
    pDict = PyModule_GetDict(pModule);
    //std::cout << "got pDict = " << pDict << std::endl;
    pFunc = PyDict_GetItemString(pDict, "train");
    //std::cout << "got pFunc = " << pFunc << std::endl;

    int returnValue = 0;

    if (PyCallable_Check(pFunc)) {
        pArgs = PyTuple_New(3);
        pValue = PyString_FromString(env->GetStringUTFChars(dataset, NULL));
        PyTuple_SetItem(pArgs, 0, pValue);
        pValue = PyString_FromString(env->GetStringUTFChars(architecture, NULL));
        PyTuple_SetItem(pArgs, 1, pValue);
        pValue = PyString_FromString(env->GetStringUTFChars(taskName, NULL));
        PyTuple_SetItem(pArgs, 2, pValue);

        pValue = PyObject_CallObject(pFunc, pArgs);
	    returnValue = (int) PyInt_AsLong(pValue);
        Py_DECREF(pArgs);
        Py_DECREF(pValue);
    } else {
	    //std::cout << "Error: python function is invalid" << std::endl;
        PyErr_Print();
    }

    Py_DECREF(pDict);
    Py_DECREF(pModule);
    Py_DECREF(pName);

    Py_Finalize();

	return returnValue;
}

int *** convertToIntTensor(JNIEnv * env, jobjectArray data, int * batchSize, int * imgHeight, int * imgWidth) {
    int batch_size = env->GetArrayLength(data);
    //std::cout << batch_size << std::endl;
    if (batch_size <= 0) return nullptr;
    *batchSize = batch_size;
    int *** batch = new (int**)[batch_size];
    for (int i = 0; i < batch_size; ++i) {
        jobjectArray image = (jobjectArray) env->GetObjectArrayElement(data, i);
        int img_height = env->GetArrayLength(image);
        if (img_height <= 0) {
            delete[] batch;
            return nullptr;
        }
        *imgHeight = img_height;
        batch[i] = new (int*)[img_height];
        for (int j = 0; j < img_height; ++j) {
            jintArray img_row = (jintArray) env->GetObjectArrayElement(image, j);
            jint * pixels = env->GetIntArrayElements(img_row, 0);
            int img_width = env->GetArrayLength(img_row);
            if (img_width <= 0) {
                delete[] batch[i]; delete[] batch; return nullptr;
            }
            *imgWidth = img_width;
            batch[i][j] = new int[img_width];
            for (int k = 0; k < img_width; ++k) {
                batch[i][j][k] = pixels[k];
            }
            env->ReleaseIntArrayElements(img_row, pixels, 0);
            env->DeleteLocalRef(img_row);
        }
        env->DeleteLocalRef(image);
    }
    return batch;
}

void freeMemory(int *** batch, int & batch_size, int & img_height) {
    for (int i = 0; i < batch_size; ++i) {
        for (int j = 0; j < img_height; ++j) {
            delete[] batch[i][j];
        }
        delete[] batch[i];
    }
    delete[] batch;
}

JNIEXPORT jint JNICALL Java_server_MLManager_testLocal
  (JNIEnv * env, jobject thisObject, jstring architecture, jstring taskName, jobjectArray data) {
    PyObject *pName, *pModule, *pDict, *pFunc, *pArgs, *pValue;
    PyArrayObject *np_arg;

    Py_Initialize();
    //std::cout << "Initialized" << std::endl;
    PyRun_SimpleString("import sys");
    PyRun_SimpleString("import os");
    PyRun_SimpleString("os.chdir('target/cpp')");
    PyRun_SimpleString("sys.path.append(os.getcwd())");

    pName = PyString_FromString("py_test_local");
    pModule = PyImport_Import(pName);
    if (pModule == NULL) return 0;
    pDict = PyModule_GetDict(pModule);
    pFunc = PyDict_GetItemString(pDict, "test");

    int returnValue = 0;

    if (PyCallable_Check(pFunc)) {
        pArgs = PyTuple_New(3);
        pValue = PyString_FromString(env->GetStringUTFChars(architecture, NULL));
        PyTuple_SetItem(pArgs, 0, pValue);
        pValue = PyString_FromString(env->GetStringUTFChars(taskName, NULL));
        PyTuple_SetItem(pArgs, 1, pValue);

        int batch_size, img_width, img_height;
        int *** batch = convertToIntTensor(env, data, &batch_size, &img_height, &img_width);  // new
        npy_intp dims[3] {batch_size, img_width, img_height};
        np_arg = reinterpret_cast<PyArrayObject*>(PyArray_SimpleNewFromData(3, dims, NPY_INT, reinterpret_cast<void*>(batch)));
        PyTuple_SetItem(pArgs, 2, reinterpret_cast<PyObject*>(np_arg));

        pValue = PyObject_CallObject(pFunc, pArgs);
        returnValue = (int) PyInt_AsLong(pValue);
        Py_DECREF(pArgs);
        Py_DECREF(pValue);
        Py_DECREF(np_arg);
        freeMemory(batch, batch_size, img_height);  // delete
    } else {
        PyErr_Print();
    }

    Py_DECREF(pFunc);
    Py_DECREF(pDict);
    Py_DECREF(pModule);
    Py_DECREF(pName);

    Py_Finalize();

    return returnValue;
}

JNIEXPORT jfloat JNICALL Java_server_MLManager_testRemote
(JNIEnv * env, jobject thisObject, jstring architecture, jstring taskName, jstring dataPath, jstring dataType, jint batchSize) {
    std::cout << architecture << " " << taskName << " " << dataPath << " " << dataType << " " << batchSize << std::endl;
    return 0.0f;
}

