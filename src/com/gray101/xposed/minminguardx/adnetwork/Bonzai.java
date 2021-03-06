package com.gray101.xposed.minminguardx.adnetwork;

import com.gray101.xposed.minminguardx.Main;
import com.gray101.xposed.minminguardx.Util;

import android.view.View;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XposedHelpers.ClassNotFoundError;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Bonzai {
    public static boolean handleLoadPackage(final String packageName, LoadPackageParam lpparam, final boolean test) {
        try {
            Class<?> adView = XposedHelpers.findClass("com.bonzai.view.BonzaiAdView", lpparam.classLoader);
            XposedBridge.hookAllMethods(adView, "update", new XC_MethodHook() {
                
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Util.log(packageName, "Detect BonzaiAdView update in " + packageName);
                    
                    if(!test) {
                        param.setResult(new Object());
                        Main.removeAdView((View) param.thisObject, packageName, true);
                    }
                }
                
            });
            Util.log(packageName, packageName + " uses Bonzai");
        }
        catch(ClassNotFoundError e) {
            return false;
        }
        return true;
    }
}
