#include "server_MLManager.h"

#include <iostream>
#include <Python.h>

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

JNIEXPORT jfloat JNICALL Java_server_MLManager_test_Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2I
(JNIEnv * env, jobject thisObject, jstring architecture, jstring taskName, jstring dataPath, jstring dataType, jint batchSize) {
    std::cout << architecture << " " << taskName << " " << dataPath << " " << dataType << " " << batchSize << std::endl;
    return 0.0f;
}

JNIEXPORT jint JNICALL Java_server_MLManager_test__Ljava_lang_String_2Ljava_lang_String_2_3Ljava_lang_Object_2
  (JNIEnv * env, jobject thisObject, jstring architecture, jstring taskName, jobjectArray data) {
      std::cout << architecture << " " << taskName << " " << data << std::endl;
      return 0;
}

