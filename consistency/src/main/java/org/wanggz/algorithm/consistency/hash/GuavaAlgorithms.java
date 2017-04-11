package org.wanggz.algorithm.consistency.hash;

import com.google.common.collect.Maps;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.util.Map;

public class GuavaAlgorithms {

    private static Map<String, HashFunction> hashFunc = Maps.newHashMap();

    public static long murMurHash32(String key) {

        String seedKey = String.valueOf(32);
        if (hashFunc.get(seedKey) == null) {
            hashFunc.put(seedKey, Hashing.murmur3_32());
        }
        return hashFunc.get(seedKey).hashBytes(md5(key)).asLong();
    }

    public static long murMurHash32(byte[] digest) {
        String seedKey = String.valueOf(32);
        if (hashFunc.get(seedKey) == null) {
            hashFunc.put(seedKey, Hashing.murmur3_32());
        }
        return hashFunc.get(seedKey).hashBytes(digest).asInt();
    }

    public static long murMurHash32(byte[] digest, int seed) {
        String seedKey = String.valueOf(seed);
        if (hashFunc.get(seedKey) == null) {
            hashFunc.put(seedKey, Hashing.murmur3_32(seed));
        }
        return hashFunc.get(seedKey).hashBytes(digest).asInt();
    }

    /**
     * MurMurHash算法，是非加密HASH算法，性能很高，
     * 比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
     * 等HASH算法要快很多，而且据说这个算法的碰撞率很低.
     * http://murmurhash.googlepages.com/
     */
    private static Long murMurHash(String key) {

        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();
            k *= m;
            k ^= k >>> r;
            k *= m;
            h ^= k;
            h *= m;
        }
        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(
                    ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }
        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;
        buf.order(byteOrder);
        return h;
    }


    /**
     * Get the md5 of the given key.
     */
    public static byte[] md5(String k) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();

            byte[] keyBytes = (k).getBytes("UTF-8");
            md5.update(keyBytes);

            return md5.digest();
        } catch (Throwable e) {
            throw new RuntimeException("generate md5 error", e);
        }
    }
}

