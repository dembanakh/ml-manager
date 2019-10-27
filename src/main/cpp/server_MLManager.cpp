#include "server_MLManager.h"

#include <utility>

using tensorflow::Status;

JNIEXPORT jboolean JNICALL Java_server_MLManager_train(JNIEnv * env, jobject thisObject, jstring dataset, jstring weights) {


	return 1;
}

Status loadGraph(const string & path, std::unique_ptr<tensorflow::Session> * session) {

}

