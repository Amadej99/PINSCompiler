#include <jni.h>

JNIEXPORT jstring JNICALL Java_Main_convertPointerToString(JNIEnv *env, jobject obj, jlong pointer) {
    const char *str = (const char *) pointer;
    return (*env)->NewStringUTF(env, str);
}
