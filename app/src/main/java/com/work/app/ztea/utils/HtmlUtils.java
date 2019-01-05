package com.work.app.ztea.utils;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.work.app.ztea.widget.MxgsaTagHandler;
import com.work.app.ztea.widget.URLImageParser;

public class HtmlUtils {

    public static Spanned getHtml(Context context, TextView textView, String string,URLImageParser.OnImageLoadSuccessImp successImp) {
//        textView.setMovementMethod(ScrollingMovementMethod.getInstance());// 滚动
        textView.setMovementMethod(LinkMovementMethod.getInstance());//设置超链接可以打开网页//click must
        return Html.fromHtml(string, new URLImageParser(textView, context,successImp), new MxgsaTagHandler(context));
    }
}
