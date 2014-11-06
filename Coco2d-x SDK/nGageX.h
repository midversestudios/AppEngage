//
//  nGageX.h


#ifndef __nGageX__
#define __nGageX__



class nGageXDelegate
{
public:
	virtual void onReceivenGageReward(int reward,const char* serverToken);
};

class nGageXInterstitialDelegate
{
public:
	virtual void onReceiveInterstitialInfo(bool displayed,const char* errorCode);
};

class nGageX{
private:
  static nGageXDelegate *delegate_;
  static nGageXInterstitialDelegate *interstitialDelegate_;
public:
  static void startnGageSession(const char* appKey);
  static void setDelegate(nGageXDelegate *delegate);
  static void showAchievementsDialog();
  static void completeAction(const char* actionId);
  static void completeActionValue(const char* actionId,const char* value);
  static void getPendingRewards (); // call on applicationWillEnterForeground
  static void onDestroy (); // call when app quits to avoid Android static caching issues on next launch
  static void onReceiveReward(int reward,const char* serverToken); //callback function


  static void showInterstitial();
  static void showIncentedInterstitial();
  static void showInterstitial(const char* custom_text);
  static void showIncentedInterstitial(const char* custom_text);
  static void setInterstitialDelegate(nGageXInterstitialDelegate *interstitialDelegate);
  static void onReceiveInterstitialInfo(bool displayed,const char* errorCode); //callback function


};

#endif
