#include <jni.h>
#include <stdio.h>
#include <string.h>

JNIEXPORT jintArray JNICALL
Java_br_com_lucad_rlecore_RleNative_parseLog(
        JNIEnv *env,
        jobject thiz,
        jstring text,
        jstring keyword) {
    (void)thiz;
    if (text == NULL || keyword == NULL) return NULL;

    const char *textLocal = (*env)->GetStringUTFChars(env, text, NULL);
    if (textLocal == NULL) return NULL;

    const char *keyWordLocal = (*env)->GetStringUTFChars(env, keyword, NULL);
    if (keyWordLocal == NULL) {
        (*env)->ReleaseStringUTFChars(env, text, textLocal);
        return NULL;
    }

    // Process C buffer...

    // Always release UTF chars
    (*env)->ReleaseStringUTFChars(env, text, textLocal);
    (*env)->ReleaseStringUTFChars(env, keyword, keyWordLocal);

    // Return jintArray or result
    jintArray result = (*env)->NewIntArray(env, 3);
    // ...
    return result;
}
