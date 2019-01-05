package com.work.app.ztea.http;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by qiyunfeng on 2017/8/21.
 */

public class MyRequestParams extends RequestParams {

    @Override
    public String toString() {
        String buff = "";
        ConcurrentHashMap<String, String> tmpMap = urlParams;
        try {
            List<ConcurrentHashMap.Entry<String, String>> infoIds = new ArrayList<ConcurrentHashMap.Entry<String, String>>(tmpMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<ConcurrentHashMap.Entry<String, String>>() {

                @Override
                public int compare(ConcurrentHashMap.Entry<String, String> o1, ConcurrentHashMap.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

            StringBuilder result = new StringBuilder();
            for (ConcurrentHashMap.Entry<String, String> entry : infoIds) {
//                if (!entry.getValue().isEmpty()) {
                if (result.length() > 0)
                    result.append("&");

                result.append(entry.getKey());
                result.append("=");
                result.append(entry.getValue());
//                }
            }

            for (ConcurrentHashMap.Entry<String, StreamWrapper> entry : streamParams.entrySet()) {
                if (result.length() > 0)
                    result.append("&");

                result.append(entry.getKey());
                result.append("=");
                result.append("STREAM");
            }

            for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
                if (result.length() > 0)
                    result.append("&");

                result.append(entry.getKey());
                result.append("=");
                result.append("FILE");
            }

            for (ConcurrentHashMap.Entry<String, List<FileWrapper>> entry : fileArrayParams.entrySet()) {
                if (result.length() > 0)
                    result.append("&");

                result.append(entry.getKey());
                result.append("=");
                result.append("FILES(SIZE=").append(entry.getValue().size()).append(")");
            }

            List<BasicNameValuePair> params = getParamsList(null, urlParamsWithObjects);
            for (BasicNameValuePair kv : params) {
                if (result.length() > 0)
                    result.append("&");

                result.append(kv.getName());
                result.append("=");
                result.append(kv.getValue());
            }


            buff = result.toString();
//            if (buff.isEmpty() == false) {
//                buff = buff.substring(0, buff.length() - 1);
//            }

        } catch (Exception e) {
            return null;
        }

        return buff;
    }


    private List<BasicNameValuePair> getParamsList(String key, Object value) {
        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
        if (value instanceof Map) {
            Map map = (Map) value;
            List list = new ArrayList<Object>(map.keySet());
            // Ensure consistent ordering in query string
            if (list.size() > 0 && list.get(0) instanceof Comparable) {
                Collections.sort(list);
            }
            for (Object nestedKey : list) {
                if (nestedKey instanceof String) {
                    Object nestedValue = map.get(nestedKey);
                    if (nestedValue != null) {
                        params.addAll(getParamsList(key == null ? (String) nestedKey : String.format(Locale.US, "%s[%s]", key, nestedKey),
                                nestedValue));
                    }
                }
            }
        } else if (value instanceof List) {
            List list = (List) value;
            int listSize = list.size();
            for (int nestedValueIndex = 0; nestedValueIndex < listSize; nestedValueIndex++) {
                params.addAll(getParamsList(String.format(Locale.US, "%s[%d]", key, nestedValueIndex), list.get(nestedValueIndex)));
            }
        } else if (value instanceof Object[]) {
            Object[] array = (Object[]) value;
            int arrayLength = array.length;
            for (int nestedValueIndex = 0; nestedValueIndex < arrayLength; nestedValueIndex++) {
                params.addAll(getParamsList(String.format(Locale.US, "%s[%d]", key, nestedValueIndex), array[nestedValueIndex]));
            }
        } else if (value instanceof Set) {
            Set set = (Set) value;
            for (Object nestedValue : set) {
                params.addAll(getParamsList(key, nestedValue));
            }
        } else {
            params.add(new BasicNameValuePair(key, value.toString()));
        }
        return params;
    }


}
