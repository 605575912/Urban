package com.squareup.lib.utils;

public class CryptMap {
    private static char[] encode_valuemap = new char[64];
    private static byte[] decode_valuemap = new byte[128];
    private static char[] encode_keymap = new char[64];
    private static byte[] decode_keymap = new byte[128];
    static {
	initKeyMap();
	initValueMap();
    }
    
    private static void initKeyMap(){
	int i = 0;
	for (char c = 'H'; c <= 'Z'; c++)
	    encode_keymap[i++] = c;
	for (char c = 'a'; c <= 'p'; c++)
	    encode_keymap[i++] = c;
	for (char c = 'A'; c <= 'G'; c++)
	    encode_keymap[i++] = c;
	for (char c = '0'; c <= '4'; c++)
	    encode_keymap[i++] = c;
	for (char c = 'q'; c <= 'z'; c++)
	    encode_keymap[i++] = c;
	for (char c = '5'; c <= '9'; c++)
	    encode_keymap[i++] = c;
	encode_keymap[i++] = '+';
	encode_keymap[i++] = '/';

	for (i = 0; i < decode_keymap.length; i++)
	    decode_keymap[i] = -1;
	for (i = 0; i < 64; i++)
	    decode_keymap[encode_keymap[i]] = (byte) i;
    }
    
    
    /**
     * 根据种子生成Base64编码表、解码表
     * @param seed
     */
    public static char[] getEncodeMap(String seed){
	int hash = 0;
	int count = seed == null ? 0 : seed.length();
	if (count > 0) {
	    final int end = count;
	    final char[] chars = seed.toCharArray();
	    for (int i = 0; i < end; ++i) {
		hash = 31 * hash + chars[i];
	    }
	}
	return getEncodeMap(hash);
    }
    /**
     * 根据种子生成Base64编码表、解码表
     * @param seed
     */
    public static char[] getEncodeMap(int seed){
	char digits[] = new char[10];
	char	alpha1[] = new char[26];
	char	alpha2[] = new char[26];
	char	encode_map[] = new char[64];
	int	offset = seed %10 ;
	int	digit_count = 0, alpha_count1 = 0, alpha_count2 = 0;
	int	i = 0, index = 0;
	char	c = 0;
	for (i = 0; i < digits.length; i ++){
	    c = (char) ('0'+i + offset);
	    if (c <= '9'){
		digits[digit_count++] = c ;
	    }
	}
	
	offset = seed %26 ;
	for (i = 0; i < alpha1.length ; i ++){
	    c = (char)('a'+offset + i);
	    if (c <= 'z'){
		alpha1[alpha_count1 ++] = c ;
	    }
	}
	
	offset = (seed>>4) %26 ;
	for (i = 0; i < alpha2.length; i ++){
	    c = (char)('A'+offset + i);
	    if (c <= 'Z'){
		alpha2[alpha_count2 ++] = c ;
	    }
	}
	
	for (i = 0; i < digit_count; i ++){
	    encode_map[index ++] = digits[i];
	}
	
	for (i = 0; i < alpha_count1; i++){
	    encode_map[index++] = alpha1[i];
	}
	
	for (i = 0; i < alpha_count2; i ++){
	    encode_map[index++] = alpha2[i];
	}

	switch(seed%6){
	case 0:
		if (digits[0] > '0'){
		    for (c = (char)(digits[0] -1); c >= '0'; c -- ){
			encode_map[index++] = c ;
		    }
		}
		if (alpha2[0] > 'A'){
		    for (c = (char)(alpha2[0] -1); c >= 'A'; c -- ){
			encode_map[index++] = c ;
		    }
		}
		if (alpha1[0] >  'a'){
		    for (c = (char)(alpha1[0] -1); c >= 'a'; c -- ){
			encode_map[index++] = c ;
		    }
		}
	    break;
	case 1:
		if (digits[0] > '0'){
		    for (c = (char)(digits[0] -1); c >= '0'; c -- ){
			encode_map[index++] = c ;
		    }
		}
		if (alpha1[0] >  'a'){
		    for (c = (char)(alpha1[0] -1); c >= 'a'; c -- ){
			encode_map[index++] = c ;
		    }
		}
		if (alpha2[0] > 'A'){
		    for (c = (char)(alpha2[0] -1); c >= 'A'; c -- ){
			encode_map[index++] = c ;
		    }
		}
	    break;
	case 2:
		if (alpha2[0] > 'A'){
		    for (c = (char)(alpha2[0] -1); c >= 'A'; c -- ){
			encode_map[index++] = c ;
		    }
		}
		if (digits[0] > '0'){
		    for (c = (char)(digits[0] -1); c >= '0'; c -- ){
			encode_map[index++] = c ;
		    }
		}
		if (alpha1[0] >  'a'){
		    for (c = (char)(alpha1[0] -1); c >= 'a'; c -- ){
			encode_map[index++] = c ;
		    }
		}
	    break;
	case 3:
		if (alpha2[0] > 'A'){
		    for (c = (char)(alpha2[0] -1); c >= 'A'; c -- ){
			encode_map[index++] = c ;
		    }
		}
		if (alpha1[0] >  'a'){
		    for (c = (char)(alpha1[0] -1); c >= 'a'; c -- ){
			encode_map[index++] = c ;
		    }
		}
		if (digits[0] > '0'){
		    for (c = (char)(digits[0] -1); c >= '0'; c -- ){
			encode_map[index++] = c ;
		    }
		}
	    break;
	case 4:
		if (alpha1[0] >  'a'){
		    for (c = (char)(alpha1[0] -1); c >= 'a'; c -- ){
			encode_map[index++] = c ;
		    }
		}
		if (alpha2[0] > 'A'){
		    for (c = (char)(alpha2[0] -1); c >= 'A'; c -- ){
			encode_map[index++] = c ;
		    }
		}
		if (digits[0] > '0'){
		    for (c = (char)(digits[0] -1); c >= '0'; c -- ){
			encode_map[index++] = c ;
		    }
		}
	    break;
	case 5:
		if (alpha1[0] >  'a'){
		    for (c = (char)(alpha1[0] -1); c >= 'a'; c -- ){
			encode_map[index++] = c ;
		    }
		}
		if (digits[0] > '0'){
		    for (c = (char)(digits[0] -1); c >= '0'; c -- ){
			encode_map[index++] = c ;
		    }
		}
		if (alpha2[0] > 'A'){
		    for (c = (char)(alpha2[0] -1); c >= 'A'; c -- ){
			encode_map[index++] = c ;
		    }
		}
	    break;
	}
	encode_map[index++] = '+';
	encode_map[index++] = '/';
	
	return encode_map;
    }
    
    public static byte[] getDecodeMap(char [] encode_map){
	byte decode_map[] = new byte[128];
	for (int i = 0; i < 64; i++){
	    decode_map[encode_map[i]] = (byte) i;
	}
	return decode_map ;
    }
    
    private static void initValueMap(){
	int i = 0;
	for (char c = '0'; c <= '4'; c++)
	    encode_valuemap[i++] = c;
	for (char c = 'a'; c <= 'p'; c++)
	    encode_valuemap[i++] = c;
	for (char c = 'A'; c <= 'G'; c++)
	    encode_valuemap[i++] = c;
	for (char c = 'q'; c <= 'z'; c++)
	    encode_valuemap[i++] = c;
	for (char c = 'H'; c <= 'Z'; c++)
	    encode_valuemap[i++] = c;
	for (char c = '5'; c <= '9'; c++)
	    encode_valuemap[i++] = c;
	encode_valuemap[i++] = '+';
	encode_valuemap[i++] = '/';

	for (i = 0; i < decode_valuemap.length; i++)
	    decode_valuemap[i] = -1;
	for (i = 0; i < 64; i++)
	    decode_valuemap[encode_valuemap[i]] = (byte) i;
    }

    public static char[] getEncodeKeyMap() {
	return encode_keymap;
    }

    public static byte[] getDecodeKeyMap() {
	return decode_keymap;
    }

    public static char[] getEncodeValueMap() {
	return encode_valuemap;
    }

    public static byte[] getDecodeValueMap() {
	return decode_valuemap;
    }
}
