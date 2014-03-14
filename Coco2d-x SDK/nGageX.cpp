//
//  nGageX.cpp
 
 

#include "nGageX.h"
#include <jni.h>
#include "platform/android/jni/JniHelper.h"
#include <android/log.h>
#include "cocos2d.h"

using namespace cocos2d;

nGageXDelegate *nGageX::delegate_ = NULL; 
nGageXInterstitialDelegate *nGageX::interstitialDelegate_ = NULL;

void nGageX::startnGageSession(const char* appKey){
  JniMethodInfo methodInfo;
  if (JniHelper::getStaticMethodInfo(methodInfo, "com/tinidream/ngagecoco/nGageHelper", "startSession", "(Ljava/lang/String;)V")) {
    jstring jappKey_ = methodInfo.env->NewStringUTF(appKey);
    
    methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID, jappKey_);
    methodInfo.env->DeleteLocalRef(jappKey_);
    methodInfo.env->DeleteLocalRef(methodInfo.classID);
  }
}

void nGageX::showAchievementsDialog(){
      JniMethodInfo methodInfo;
      if (JniHelper::getStaticMethodInfo(methodInfo, "com/tinidream/ngagecoco/nGageHelper", "showAchievementsDialog", "()V")) {
        methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID);
        methodInfo.env->DeleteLocalRef(methodInfo.classID);
      }
}


void nGageX::completeAction(const char* actionId){

  JniMethodInfo methodInfo;
  if (JniHelper::getStaticMethodInfo(methodInfo, "com/tinidream/ngagecoco/nGageHelper", "completeAction", "(Ljava/lang/String;)V")) {
    jstring jactionId_ = methodInfo.env->NewStringUTF(actionId);
    
    methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID, jactionId_);
    methodInfo.env->DeleteLocalRef(jactionId_);
    methodInfo.env->DeleteLocalRef(methodInfo.classID);
  }
}

void nGageX::completeActionValue(const char* actionId,const char* value){

  JniMethodInfo methodInfo;
  if (JniHelper::getStaticMethodInfo(methodInfo, "com/tinidream/ngagecoco/nGageHelper", "completeActionValue", "(Ljava/lang/String;Ljava/lang/String;)V")) {
    jstring jactionId_ = methodInfo.env->NewStringUTF(actionId);
    jstring jvalue_ = methodInfo.env->NewStringUTF(value);
    
    methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID, jactionId_,jvalue_);
    methodInfo.env->DeleteLocalRef(jactionId_);
    methodInfo.env->DeleteLocalRef(jvalue_);
    methodInfo.env->DeleteLocalRef(methodInfo.classID);
  }
}

void nGageX::setDelegate(nGageXDelegate *delegate){
  delegate_ = delegate;
}


void nGageX::getPendingRewards(){
	//call when your app resumes
	  JniMethodInfo methodInfo;
	  if (JniHelper::getStaticMethodInfo(methodInfo, "com/tinidream/ngagecoco/nGageHelper", "getPendingRewards", "()V")) {
		methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID);
		methodInfo.env->DeleteLocalRef(methodInfo.classID);
	  }
}

void nGageX::onDestroy(){
	//call when app is closing
	  JniMethodInfo methodInfo;
	  if (JniHelper::getStaticMethodInfo(methodInfo, "com/tinidream/ngagecoco/nGageHelper", "onDestroy", "()V")) {
		methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID);
		methodInfo.env->DeleteLocalRef(methodInfo.classID);
	  }
}

void nGageX::onReceiveReward(int reward,const char* serverToken){
	//makes callback to coco delegate
	if (delegate_!=NULL) {
		delegate_->onReceivenGageReward(reward,serverToken);
	}
}



extern "C" JNIEXPORT void JNICALL Java_com_tinidream_ngagecoco_nGageHelper_nGageRewardNative(JNIEnv* env, jobject thiz, jint reward,jstring serverTokenjava) {
	//Reward Responce method from java
	const jbyte* argvv =  (jbyte*)env->GetStringUTFChars(serverTokenjava, NULL);
	const char* serverToken =(char *) argvv;

	nGageX::onReceiveReward((int)reward,serverToken);

}


//Intersticials

void nGageX::showInterstitial(){
	  JniMethodInfo methodInfo;
	  if (JniHelper::getStaticMethodInfo(methodInfo, "com/tinidream/ngagecoco/nGageHelper", "showInterstitial", "()V")) {
		methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID);
		methodInfo.env->DeleteLocalRef(methodInfo.classID);
	  }
}

void nGageX::showIncentedInterstitial(){
	 JniMethodInfo methodInfo;
	 if (JniHelper::getStaticMethodInfo(methodInfo, "com/tinidream/ngagecoco/nGageHelper", "showIncentedInterstitial", "()V")) {
		methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID);
		methodInfo.env->DeleteLocalRef(methodInfo.classID);
	 }
}

void nGageX::showInterstitial(const char* custom_text){
	JniMethodInfo methodInfo;
	if (JniHelper::getStaticMethodInfo(methodInfo, "com/tinidream/ngagecoco/nGageHelper", "showInterstitial", "(Ljava/lang/String;)V")) {

		jstring jcustom_text = methodInfo.env->NewStringUTF(custom_text);
		methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID, jcustom_text);
		methodInfo.env->DeleteLocalRef(jcustom_text);
		methodInfo.env->DeleteLocalRef(methodInfo.classID);
	 }
}



void nGageX::setInterstitialDelegate(nGageXInterstitialDelegate *interstitialDelegate){
	interstitialDelegate_ = interstitialDelegate;
}

void nGageX::onReceiveInterstitialInfo(bool displayed,const char* errorCode){ //callback function
	//makes callback to coco delegate
	if (interstitialDelegate_!=NULL) {
		interstitialDelegate_->onReceiveInterstitialInfo(displayed,errorCode);
	}
}


extern "C" JNIEXPORT void JNICALL Java_com_tinidream_ngagecoco_nGageHelper_nGageInterstitialNative(JNIEnv* env, jobject thiz, jboolean displayed, jstring errorCodeJava) {
	//Reward Responce method from java
	const jbyte* argvv =  (jbyte*)env->GetStringUTFChars(errorCodeJava, NULL);
	const char* errorCode =(char *) argvv;

	nGageX::onReceiveInterstitialInfo(displayed,errorCode);


}

