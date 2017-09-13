package com.common.utils.memcached;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import net.jpountz.lz4.LZ4BlockInputStream;
import net.jpountz.lz4.LZ4BlockOutputStream;
import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

/**
 * Zl4压缩算法.
 *
 *
 */
public class LZ4Compress {

    private static final LZ4Factory factory = LZ4Factory.fastestInstance();

    private static final LZ4Compressor compressor = factory.fastCompressor();

    private static final LZ4FastDecompressor decompresser = factory.fastDecompressor();

    private static final int BLOCK_SIZE = 64 * 1024;

    /**
     * 压缩.
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        try (LZ4BlockOutputStream compressedOutput = new LZ4BlockOutputStream(byteOutput, BLOCK_SIZE, compressor)) {
            compressedOutput.write(data);
        }
        return byteOutput.toByteArray();
    }

    /**
     * 解压.
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static byte[] decompress(byte[] data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(BLOCK_SIZE);
        try (LZ4BlockInputStream lzis = new LZ4BlockInputStream(new ByteArrayInputStream(data), decompresser)) {
            int count;
            byte[] buffer = new byte[BLOCK_SIZE];
            while ((count = lzis.read(buffer)) != -1) {
                baos.write(buffer, 0, count);
            }
        }
        return baos.toByteArray();
    }

}
