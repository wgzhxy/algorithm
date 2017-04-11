package org.wanggz.algorithm.consistency;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by guangzhong.wgz on 17/4/11.
 */
public class BitSetTest {

    @Test
    public void bitExpressLong() {

        System.out.println("Integer.MAX_VALUE: " + Integer.MAX_VALUE);
        System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝Long.MAX_VALUE : " + Long.MAX_VALUE);

        long randomNum = Double.doubleToLongBits(Math.random() * Long.MAX_VALUE);

        long radio = randomNum / (Integer.MAX_VALUE);
        long mod = Long.valueOf(randomNum % Integer.MAX_VALUE);

        if (radio >= Integer.MAX_VALUE) {
            System.out.println("\n true \n");
        }
        radio = radio - Integer.MAX_VALUE;

        System.out.println("radio: " + radio);
        System.out.println("mode : " + mod);

        System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝");
        long radioMod = (radio + Integer.MAX_VALUE) * Integer.MAX_VALUE + mod;

        System.out.println(randomNum + " == " + radioMod);
        Assert.assertTrue(randomNum == radioMod);
    }

    @Test
    public void IntegerToBitset() {

       /* for (int i = 0; i < 10000; i++) {
            long randomNum = Double.doubleToLongBits(Math.random() * Long.MAX_VALUE);

            int radio = Long.valueOf(randomNum / Integer.MAX_VALUE).intValue();
            int mod = Long.valueOf(randomNum % Integer.MAX_VALUE).intValue();
            radio -= Integer.MAX_VALUE;

            radioSet.set(radio, true);
            modSet.set(mod, true);
        }
        System.out.println(radioSet.cardinality());
        System.out.println(radioSet.cardinality());
        for(long obj : modSet.toByteArray()) {
            System.out.println(obj);
        } */

    }
}
