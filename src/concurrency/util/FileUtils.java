/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrency.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Administrator
 */
public class FileUtils {

    /**
     * A方法追加文件：使用RandomAccessFile
     * @param fileName
     * @param content
     */
    public static void appendMethodA(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            //将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            byte[] bytes = content.getBytes("gb2312");
            randomFile.write(bytes);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * B方法追加文件：使用FileWriter
     * @param fileName
     * @param content
     */
    public static void appendMethodB(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
         String fileName = "E:/newTemp.dat";
         String content = "new append!";
         //按方法A追加文件
         FileUtils.appendMethodA(fileName, content);
         FileUtils.appendMethodA(fileName, "append end.");
         //显示文件内容
//         ReadFromFile.readFileByBytes(fileName);//.readFileByLines(fileName);
        /* //按方法B追加文件
         AppendToFile.appendMethodB(fileName, content);
         AppendToFile.appendMethodB(fileName, "append end. \n");
         //显示文件内容
         ReadFromFile.readFileByBytes(fileName);*/
        // ReadFromFile.readFileByLines(fileName);
     }

}
