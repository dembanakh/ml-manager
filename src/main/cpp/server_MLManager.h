/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class server_MLManager */

#ifndef _Included_server_MLManager
#define _Included_server_MLManager
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     server_MLManager
 * Method:    train
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_server_MLManager_train
  (JNIEnv *, jobject, jstring, jstring, jstring);

/*
 * Class:     server_MLManager
 * Method:    test
 * Signature: (Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)I
 */
JNIEXPORT jint JNICALL Java_server_MLManager_test__Ljava_lang_String_2Ljava_lang_String_2_3Ljava_lang_Object_2
  (JNIEnv *, jobject, jstring, jstring, jobjectArray);

/*
 * Class:     server_MLManager
 * Method:    test
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)F
 */
JNIEXPORT jfloat JNICALL Java_server_MLManager_test__Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2Ljava_lang_String_2I
  (JNIEnv *, jobject, jstring, jstring, jstring, jstring, jint);

#ifdef __cplusplus
}
#endif
#endif
