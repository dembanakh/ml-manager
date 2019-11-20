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

int *** convertToIntTensor(JNIEnv * env, jobjectArray data) {
    int data_len = env->GetArrayLength(data);
    std::cout << data_len << std::endl;
    if (data_len > 0) {
        int img_width = env->GetArrayLength((jobjectArray) env->GetObjectArrayElement(data, 0));
        std::cout << img_width << std::endl;
    }
    return nullptr;
}

JNIEXPORT jint JNICALL Java_server_MLManager_testLocal
  (JNIEnv * env, jobject thisObject, jstring architecture, jstring taskName, jobjectArray data) {
    PyObject *pName, *pModule, *pDict, *pFunc, *pArgs, *pValue;

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
        int *** test_set = convertToIntTensor(env, data);

        pValue = PyObject_CallObject(pFunc, pArgs);
        returnValue = (int) PyInt_AsLong(pValue);
        Py_DECREF(pArgs);
        Py_DECREF(pValue);
    } else {
        PyErr_Print();
    }

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

