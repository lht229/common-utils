package com.common.utils.memcached;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * byte辅助工具.
 *
 *
 */
public final class ByteArrayUtils {

    private ByteArrayUtils() {

    }

    /**
     * 获得分割的段数.
     */
    public static int getMyInt(int a, int b) {
        return (((double) a / (double) b) > (a / b) ? a / b + 1 : a / b);
    }


    /**
     * 分割数据.
     *  注意: 第一块a[0]保存分割的块大小, 如果块>1， 合并时候需要继续读取其他块.
     * @param original  需要切分的数据.
     * @param chunkSize  每一块大小.
     * @return
     */
    public static List<byte[]> split(byte[] original,
            int chunkSize) {
        int dataLen = original.length;
        int num = getMyInt(dataLen, chunkSize);
        if (num > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("问卷太大, 压缩后大小:" + original.length + "bytes");
        }
        List<byte[]> list = new ArrayList<byte[]>(num);
        for (int i = 0; i < num; i++) {
            int start = i * chunkSize;
            int end = (i + 1) * chunkSize;
            if (end > dataLen) {
                end = dataLen;
            }
            if (i == 0) {
                byte[] newChunk = new byte[end - start + 1];
                System.arraycopy(original, start, newChunk, 1,  end - start);
                newChunk[0] = (byte) num;
                list.add(newChunk);
            } else {
                list.add(Arrays.copyOfRange(original, start, end));
            }
        }
        return list;
    }

    /**
     * 合并数据块.
     *
     * @param chunkBytes 数据块.
     * @return
     */
    public static byte[] merge(byte[]... chunkBytes) {
        int totalLength = 0;
        boolean firstChunk = true;
        for (byte[] chunkByte : chunkBytes) {
            if (firstChunk) {
                firstChunk = false;
            }
            totalLength += chunkByte.length;
        }
        // 去掉长度字节
        totalLength -= 1;
        firstChunk = true;
        byte[] mergeChunkByte = new byte[totalLength];
        int offset = 0;
        for (byte[] chunkByte : chunkBytes) {
            int len = chunkByte.length;
            if (firstChunk) {
                firstChunk = false;
                System.arraycopy(chunkByte, 1, mergeChunkByte, 0, len - 1);
                offset += len - 1;
            } else {
                System.arraycopy(chunkByte, 0, mergeChunkByte, offset, len);
                offset += len;
            }

        }
        return mergeChunkByte;
    }
}
