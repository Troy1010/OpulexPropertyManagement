package com.example.opulexpropertymanagement.util;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class JavaUtil {
    public static void copyIt(Context context, String s) {
        ClipboardManager clipboard=(ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip= ClipData.newPlainText("label",s);
        clipboard.setPrimaryClip(clip);
    }
}
