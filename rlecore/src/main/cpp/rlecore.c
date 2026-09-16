#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <stdlib.h>

JNIEXPORT jbyteArray JNICALL
Java_br_com_lucad_rlecore_RleNative_compressRle(JNIEnv *env, jobject thiz, jbyteArray input) {
    (void) thiz;
    if (input == nullptr) {
        return nullptr;
    }

    jbyte *inputPtr = (*env)->GetByteArrayElements(env, input, nullptr);
    const uint32_t size = (*env)->GetArrayLength(env, input);

    // Pior caso ex: 1A2B3C = size * 2
    uint8_t *outputPtr = (uint8_t *) malloc(size * 2);
    if (outputPtr == nullptr) {
        (*env)->ReleaseByteArrayElements(env, input, inputPtr,
                                         JNI_ABORT); //JNI_ABORT sem modificacao
        return nullptr;
    }

    uint8_t *writerPtr = outputPtr;
    uint8_t count = 1;

    for (size_t i = 0; i < size; ++i) {
        if ((i + 1) < size && inputPtr[i] == inputPtr[i + 1]) {
            if (count == 255) {
                *writerPtr++ = count;
                *writerPtr++ = inputPtr[i];
                count = 1;
            } else {
                count++;
            }
        } else {
            *writerPtr++ = count;
            *writerPtr++ = inputPtr[i];
            count = 1;
        }
    }

    ptrdiff_t compressedSize = writerPtr - outputPtr;
    jbyteArray compressed = (*env)->NewByteArray(env, compressedSize);
    if (compressed == nullptr) {
        free(outputPtr);
        (*env)->ReleaseByteArrayElements(env, input, inputPtr, JNI_ABORT);
        return nullptr;
    }
    (*env)->SetByteArrayRegion(env, compressed, 0, compressedSize,
                               (const jbyte *) outputPtr);

    free(outputPtr);
    (*env)->ReleaseByteArrayElements(env, input, inputPtr, JNI_ABORT);
    return compressed;

}

JNIEXPORT jbyteArray JNICALL
Java_br_com_lucad_rlecore_RleNative_decompressRle(JNIEnv *env, jobject thiz, jbyteArray input) {
    (void) thiz;

    if (input == nullptr) {
        return nullptr;
    }

    jbyte *inputPtr = (*env)->GetByteArrayElements(env, input, nullptr);
    const jsize size = (*env)->GetArrayLength(env, input);

    if (size % 2 != 0) {
        (*env)->ReleaseByteArrayElements(env, input, inputPtr, JNI_ABORT);
        return nullptr;
    }

    size_t sum = 0;

    for (size_t i = 0; i < (size_t) size; i += 2) {
        sum += (uint8_t) inputPtr[i];
    }

    uint8_t *outputPtr = (uint8_t *) malloc(sum);
    if (outputPtr == nullptr) {
        (*env)->ReleaseByteArrayElements(env, input, inputPtr, JNI_ABORT);
        return nullptr;
    }

    uint8_t *writerPtr = outputPtr;
    uint8_t count = 0;
    jbyte value = 0;

    for (size_t i = 0; i < (size_t) size; i += 2) {
        count = (uint8_t) inputPtr[i];
        value = inputPtr[i + 1];
        memset(writerPtr, value, count);
        writerPtr += count;
    }

    jbyteArray decompressed = (*env)->NewByteArray(env, (jsize) sum);
    if (decompressed == nullptr) {
        free(outputPtr);
        (*env)->ReleaseByteArrayElements(env, input, inputPtr, JNI_ABORT);
        return nullptr;
    }

    (*env)->SetByteArrayRegion(env, decompressed, 0, (jsize) sum, (const jbyte *) outputPtr);

    free(outputPtr);
    (*env)->ReleaseByteArrayElements(env, input, inputPtr, JNI_ABORT);
    return decompressed;
}