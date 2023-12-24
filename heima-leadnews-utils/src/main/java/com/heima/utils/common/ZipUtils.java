package com.heima.utils.common;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.*;

public class ZipUtils {

    /**
     * 使用gzip进行压缩
     */
    public static String gzip(String primStr) {
        if (primStr == null || primStr.length() == 0) {
            return primStr;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
            gzip.write(primStr.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(out.toByteArray());
    }

    /**
     * 使用gzip进行解压缩
     */
    public static String gunzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (ByteArrayInputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(compressedStr));
             GZIPInputStream ginzip = new GZIPInputStream(in)) {

            byte[] buffer = new byte[1024];
            int offset = -1;
            while ((offset = ginzip.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用zip进行压缩
     */
    public static String zip(String str) {
        if (str == null)
            return null;

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ZipOutputStream zout = new ZipOutputStream(out)) {

            zout.putNextEntry(new ZipEntry("0"));
            zout.write(str.getBytes());
            zout.closeEntry();

            byte[] compressed = out.toByteArray();
            return Base64.getEncoder().encodeToString(compressed);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用zip进行解压缩
     */
    public static String unzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        }

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ByteArrayInputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(compressedStr));
             ZipInputStream zin = new ZipInputStream(in)) {

            zin.getNextEntry();
            byte[] buffer = new byte[1024];
            int offset;
            while ((offset = zin.read(buffer)) != -1) {
                out.write(buffer, 0, offset);
            }
            return out.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}