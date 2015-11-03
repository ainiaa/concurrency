package concurrency.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpPostUtils {

    public static String httpPost(String urlAddress, Map<String, String> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap();
        }
        String[] params = new String[paramMap.size()];
        int i = 0;
        String cookie = "";
        if (paramMap.containsKey("cookie")) {
            cookie = paramMap.get("cookie");
            paramMap.remove("cookie");
        }
        for (String paramKey : paramMap.keySet()) {
            String param = paramKey + "=" + paramMap.get(paramKey);
            params[i] = param;
            i++;
        }
        return httpPost(urlAddress, params, cookie);
    }

    public static String httpPost(String urlAddress, List<String> paramList, String cookie) {
        if (paramList == null) {
            paramList = new ArrayList();
        }
        return httpPost(urlAddress, paramList.toArray(new String[0]), cookie);
    }

    public static String httpPost(String urlAddress, String[] params, String cookie) {
        URL url = null;
        HttpURLConnection con = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            url = new URL(urlAddress);
            con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");//放置因为没有user-agent导致访问不正常
            con.setConnectTimeout(0);//不超时 要不然 30s之后就会超时了。
            con.setReadTimeout(0);//不超时
            con.setRequestProperty("Connection", "Keep-Alive");// 设置长连接
            con.setRequestProperty("Cache-Control", "no-cache");//设置 不使用cache
            if (!cookie.isEmpty()) {
                con.setRequestProperty("Cookie", cookie);//设置 cookie    
            }

            String paramsTemp = HttpPostUtils.getParamStringFromParamArray(params);
            byte[] b = paramsTemp.getBytes();
            con.getOutputStream().write(b, 0, b.length);
            con.getOutputStream().flush();
            con.getOutputStream().close();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                } else {
                    result.append(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            String fileName = "E:/recoverUserFailure.txt";
            String content = HttpPostUtils.getParamStringFromParamArray(params);
            //按方法A追加文件
            FileUtils.appendMethodA(fileName, content + "\r\n");

        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (con != null) {
                    con.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String httpGet(String urlAddress) {
        return httpGet(urlAddress, new String[0], "", false);
    }
    
    public static String httpGet(String urlAddress, boolean getHeader) {
        return httpGet(urlAddress, new String[0], "", getHeader);
    }
    
    public static String httpGet(String urlAddress, String cookie, boolean getHeader) {
        return httpGet(urlAddress, new String[0], cookie, getHeader);
    }
    
    public static String httpGet(String urlAddress, List<String> paramList, boolean getHeader) {
        if (paramList == null) {
            paramList = new ArrayList();
        }
        return httpGet(urlAddress, paramList.toArray(new String[0]), "", getHeader);
    }
    
    public static String httpGet(String urlAddress, List<String> paramList, String cookie, boolean getHeader) {
        if (paramList == null) {
            paramList = new ArrayList();
        }
        return httpGet(urlAddress, paramList.toArray(new String[0]), cookie, getHeader);
    }
    
    public static String httpGet(String urlAddress, String[] params, String cookie, boolean getHeader) {
        URL url;
        HttpURLConnection con = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            String paramsTemp = HttpPostUtils.getParamStringFromParamArray(params);
            if (!paramsTemp.isEmpty()) {
                urlAddress += "?" + paramsTemp;
            }
            url = new URL(urlAddress);
            con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setRequestMethod("GET");

            con.setConnectTimeout(0);//不超时 要不然 30s之后就会超时了。
            con.setReadTimeout(0);//不超时
            con.setRequestProperty("accept", "*/*");// 设置长连接
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:31.0) Gecko/20100101 Firefox/31.0");//放置因为没有user-agent导致访问不正常
            con.setRequestProperty("Connection", "Keep-Alive");// 设置长连接
            con.setRequestProperty("Cache-Control", "no-cache");//设置 不使用cache
            if (!cookie.isEmpty()) {
                con.setRequestProperty("Cookie", cookie);//设置 cookie    
            }

            if (getHeader) {
                // 获取所有响应头字段
                Map<String, List<String>> map = con.getHeaderFields();
                // 遍历所有的响应头字段
                result.append("header end=======\r\n");
                map.keySet().stream().forEach((key) -> {
                    result.append(key + ":" + map.get(key) + "\r\n");
                });
                result.append("header end=======\r\n");
            }

            //定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            String fileName = "E:/recoverUserFailure.txt";
            String content = HttpPostUtils.getParamStringFromParamArray(params);
            //按方法A追加文件
            FileUtils.appendMethodA(fileName, content + "\r\n");

        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (con != null) {
                    con.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * @author Jeff Liu<jeff.liu.guo@mail.com>
     * @param params
     * @return
     */
    public static String getParamStringFromParamArray(String[] params) {
        String paramsTemp = "";
        for (String param : params) {
            if (param != null && !"".equals(param.trim())) {
                paramsTemp += "&" + param;
            }
        }
        return paramsTemp;
    }

}
