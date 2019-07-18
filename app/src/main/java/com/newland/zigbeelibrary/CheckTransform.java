//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.newland.zigbeelibrary;

public class CheckTransform {
    private final int polynomial = 40961;
    private int[] table = new int[256];

    public int ComputeChecksum(byte[] bytes) {
        int crc = 65535;

        for(int i = 0; i < bytes.length; ++i) {
            int index = (byte)(crc ^ bytes[i]);
            if (index < 0) {
                index += 256;
            }

            crc = crc >>> 8 ^ this.table[index];
        }

        return crc;
    }

    public int ComputeChecksum(char[] bytes) {
        int crc = 65535;

        for(int i = 0; i < bytes.length; ++i) {
            int index = (byte)(crc ^ bytes[i]);
            if (index < 0) {
                index += 256;
            }

            crc = crc >>> 8 ^ this.table[index];
        }

        return crc;
    }

    public static char getLRC(char[] buzzer) {
        byte lrc = 0;
        int a = buzzer.length - 2;

        for(int i = 0; i < a; ++i) {
            lrc = (byte)(lrc ^ buzzer[i + 1]);
        }

        return (char)lrc;
    }

    public static char getHFLRC(char[] buzzer) {
        byte lrc = 0;
        int a = buzzer.length - 5;

        for(int i = 0; i < a; ++i) {
            lrc = (byte)(lrc ^ buzzer[i + 3]);
        }

        return (char)lrc;
    }

    public CheckTransform() {
        for(int i = 0; i < this.table.length; ++i) {
            int value = 0;
            int temp = i;

            for(byte j = 0; j < 8; ++j) {
                if (((value ^ temp) & 1) != 0) {
                    value = value >> 1 ^ 'ꀁ';
                } else {
                    value >>>= 1;
                }

                temp >>>= 1;
            }

            this.table[i] = value;
        }

    }
}
