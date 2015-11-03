/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency;

import concurrency.util.FileUtils;
import concurrency.util.HttpPostUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author kira
 */
public class Concurrency implements Runnable {

    @Override
    public void run() {
        doPost();
    }

    public void doPost() {
        String urlAddress = "http://test.wangcaio2o.com/index.php?g=Label&m=ShareBatch&a=returnCj"; //"http://test.wangcaio2o.com/index.php?g=Label&m=Cj&a=submit";
        Map<String, String> paramMap = new HashMap();
        paramMap.put("id", "11723");
//        paramMap.put("cj_check_flag", "0");
        paramMap.put("full_id", "11723");
        paramMap.put("pay_token", "");
//        paramMap.put("mobile", "18221965265");
        //paramMap.put("verify", "9530");
        paramMap.put("cookie",
                "PHPSESSID=r4024iikfvhqa6r56q5ivoke51; SERVER_ID=app4; Hm_lvt_ab5c1948317f7d13b46c8f6fa373c3ee=1442380865; Hm_lpvt_ab5c1948317f7d13b46c8f6fa373c3ee=1442388084; Hm_lvt_d63513a907e602a2cc2cb6970d7765b8=1442380865; Hm_lpvt_d63513a907e602a2cc2cb6970d7765b8=1442392713; cookieId=dd96e2c856f6d680299356a939b33325; visitId=756258a410aa1e06aed1690dad3354b7; thinkphp_show_page_trace=0|0; emailCookie=wangpan%40imageco.com.cn");
        String response = HttpPostUtils.httpPost(urlAddress, paramMap);
        System.out.println();
        System.out.println(response);
        
//        FileUtils.appendMethodA("d:/response.html", response + "\r\n===========================\r\n\r\n");
    }

    public void doGet(String url) {
        String response = HttpPostUtils.httpGet(url);
        System.out.println();
        System.out.println(response);
        
//        FileUtils.appendMethodA("d:/response.html", response + "\r\n===========================\r\n\r\n");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int concurrencyCount = 10;
        for (int i = 0; i < concurrencyCount; i++) {
            new Concurrency().doGet("http://local.imageco.me/index.php?g=Tools&m=TestDb&a=getGoodsId");
        }
    }
}
