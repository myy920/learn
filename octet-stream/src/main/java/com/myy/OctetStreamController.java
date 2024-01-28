package com.myy;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@CrossOrigin
@RestController
@RequestMapping("/octetStream")
public class OctetStreamController {

    @GetMapping("/f1")
    void f1(HttpServletResponse response) {
        response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + "test.zip");
        try (OutputStream out = response.getOutputStream();
             ZipOutputStream zOut = new ZipOutputStream(out)) {
            File file = new File("C:\\Users\\Administrator\\Desktop\\test");
            File[] files = file.listFiles();
            for (File one : files) {
                zOut.putNextEntry(new ZipEntry(one.getName()));
                try (FileInputStream in = new FileInputStream(one)) {
                    int len;
                    byte[] buffer = new byte[1024 * 10];
                    while ((len = in.read(buffer)) != -1) {
                        zOut.write(buffer, 0, len);
                    }
                }
                zOut.flush();
            }
        } catch (Exception e) {

        }
    }

}
