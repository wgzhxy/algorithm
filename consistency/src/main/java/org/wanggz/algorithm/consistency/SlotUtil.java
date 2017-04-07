package org.wanggz.algorithm.consistency;


import org.wanggz.algorithm.consistency.hash.HashAlgorithms;

/**
 * @author guangzhong.wgz
 */
public class SlotUtil {

    /**
     * 通过antKey产生hashCode
     */
    public static long generateHashCode(String key, String salt) {
        HashAlgorithm func = HashAlgorithm.KETAMA_HASH;
        long hashCode = func.hash(func.computeMd5(key, salt), 0);
        return hashCode;
    }

    /**
     * 通过antKey产生hashCode
     */
    public static long generateMurmurHashCode(String key, String salt) {
        return Math.abs(HashAlgorithms.murmurHash((key + salt).getBytes()));
    }

    public static long getBelongedSlotNum(String key, String salt, int slotSize) {
        long keyHashCode = generateMurmurHashCode(key, salt);
        return (keyHashCode % slotSize);
    }

}
