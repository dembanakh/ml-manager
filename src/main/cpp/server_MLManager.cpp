#include "server_MLManager.h"

#include <iostream>
#include <Python.h>

JNIEXPORT jboolean JNICALL Java_server_MLManager_train(JNIEnv * env, jobject thisObject, jstring dataset, jstring architecture) {
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

    if (PyCallable_Check(pFunc)) {
        pArgs = PyTuple_New(2);
        pValue = PyString_FromString(env->GetStringUTFChars(env, dataset, NULL));
        PyTuple_SetItem(pArgs, 0, pValue);
        pValue = PyString_FromString(env->GetStringUTFChars(env, architecture, NULL));
        PyTuple_SetItem(pArgs, 1, pValue);

        PyObject_CallObject(pFunc, pArgs);
	    //if (pValue != NULL) std::cout << "Control check: return value = " << PyInt_AsLong(pValue) << std::endl;
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

	return 1;
}

