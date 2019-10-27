#include "server_MLManager.h"

JNIEXPORT jboolean JNICALL Java_server_MLManager_train(JNIEnv * env, jobject thisObject, jobject dataset, jobject net) {
	jclass datasetClass = env->GetObjectClass(dataset);
	jclass netClass = env->GetObjectClass(net);

	return 1;
}	
