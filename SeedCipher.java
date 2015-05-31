package com.gifticon.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Seed 암복호화 처리
 * 
 * @author chooty
 * @since 20131230
 */
public class SeedCipher {

	private final static int[] pdwRoundKey = new int[32];
	private final byte[] pbUserKey;
	private final static Seedx seedx = new Seedx();

	public SeedCipher(String companyCode) {
		this.pbUserKey = companyCode.getBytes();
		this.seedx.SeedRoundKey(this.pdwRoundKey, this.pbUserKey);
	}

	public static byte[] encrypt(String plainText) {
		List<Byte> byteList = new ArrayList<Byte>();

		byte[] temp = plainText.getBytes();
		int count = temp.length / 16;
		int remainder = temp.length % 16;

		byte[] pbCipher = new byte[16];
		byte[] target = null;
		int startPos = 0;

		for (int i = 0; i < count; i++) {
			target = new byte[16];
			System.arraycopy(temp, startPos, target, 0, 16);
			seedx.SeedEncrypt(target, pdwRoundKey, pbCipher);
			addBytes(byteList, pbCipher);
			startPos += 16;
		}

		if (remainder > 0) {
			target = new byte[16];
			System.arraycopy(temp, startPos, target, 0, remainder);
			seedx.SeedEncrypt(target, pdwRoundKey, pbCipher);
			addBytes(byteList, pbCipher);
		}
		return listToByteArray(byteList);
	}

	public static byte[] decrypt(byte[] cipherText) {
		byte[] temp = cipherText;

		List<Byte> byteList = new ArrayList<Byte>();

		int count = temp.length / 16;
		int remainder = temp.length % 16;

		byte[] plainText = new byte[16];
		byte[] target = null;
		int startPos = 0;

		for (int i = 0; i < count; i++) {
			target = new byte[16];
			System.arraycopy(temp, startPos, target, 0, 16);
			seedx.SeedDecrypt(target, pdwRoundKey, plainText);
			addBytes(byteList, plainText);
			startPos += 16;
		}

		if (remainder > 0) {
			target = new byte[16];
			System.arraycopy(temp, startPos, target, 0, remainder);
			seedx.SeedDecrypt(target, pdwRoundKey, plainText);
			addBytes(byteList, plainText);
		}

		return removePadding(listToByteArray(byteList));
	}

	private static void addBytes(List<Byte> byteList, byte[] byteArray) {
		for (int i = 0; i < byteArray.length; i++) {
			byteList.add(byteArray[i]);
		}
	}

	private static byte[] listToByteArray(List<Byte> byteList) {
		byte[] temp = new byte[byteList.size()];
		for (int i = 0; i < byteList.size(); i++) {
			temp[i] = byteList.get(i);
		}
		return temp;
	}

	private static byte[] removePadding(byte[] value) {
		byte[] removePadding = null;
		int index = 0;
		for (int i = value.length - 1; i < value.length; i--) {
			if (value[i] == 0)
				continue;
			else {
				index = i + 1;
				break;
			}
		}
		removePadding = new byte[index];
		for (int i = 0; i < index; i++) {
			removePadding[i] = value[i];
		}

		return removePadding;
	}
}
