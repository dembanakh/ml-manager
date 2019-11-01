#include "server_MLManager.h"

#include <utility>
#include <Python.h>

JNIEXPORT jboolean JNICALL Java_server_MLManager_train(JNIEnv * env, jobject thisObject, jstring dataset, jstring weights) {
    PyObject *pName, *pModule, *pDict, *pFunc, *pArgs, *pValue;

    Py_Initialize();
    PyRun_SimpleString("import sys");
    PyRun_SimpleString("import os");
    PyRun_SimpleString("sys.path.append(os.getcwd())");

    pName = PyString_FromString("train");
    pModule = PyImport_Import(pName);
    pDict = PyModule_GetDict(pModule);
    pFunc = PyDict_GetItemString(pDict, "train");

    if (PyCallable_Check(pFunc)) {
        pArgs = PyTuple_New(1);
        pValue = PyString_FromString("~/test.model");
        PyTuple_SetItem(pArgs, 0, pValue);

        PyObject_CallObject(pFunc, pArgs);
        Py_DECREF(pArgs);
        Py_DECREF(pValue);
    } else {
        PyErr_Print();
    }

    Py_DECREF(pModule);
    Py_DECREF(pName);

    Py_Finalize();

	return 1;
}

