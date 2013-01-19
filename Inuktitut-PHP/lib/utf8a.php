<?php
/*
 * Created on 2010-05-14
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 class utf8a {
 
  public static function utf8_to_numeric ($c) {
 	if (mb_detect_encoding($c) === 'UTF-8') {
 		$cs = str_split($c);
 		if (strlen($c)==4)
 			$n = ((ord($cs[0]) & 0x07) << 18) | ((ord($cs[1]) & 0x3F) << 12) | ((ord($cs[2]) & 0x3F) << 6) | (ord($cs[2]) & 0x3F);
 		elseif (strlen($c)==3)
 			$n = ((ord($cs[0]) & 0x0F) << 12) | ((ord($cs[1]) & 0x3F) << 6) | (ord($cs[2]) & 0x3F);
 		elseif (strlen($c)==2)
 			$n = ((ord($cs[0]) & 0x1F) << 6) | (ord($cs[1]) & 0x3F);
 		else
 			$n = ord($c);
 		return $n;
 	}
    else
      return ord($c);
 }
 
 public static function numeric_to_utf8 ($n) {
 	if ($n < 0x80) 
 		$byte = array($n);
 	elseif ($n >= 0x80 && $n <= 0x7FF) {
 		$byte = array(0,0);
 		$byte[1] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[0] = ($n & 0x1F) | 0xC0;
 	}
 	elseif ($n >= 0x800 && $n <= 0xFFFF) {
 		$byte = array(0,0,0);
 		$byte[2] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[1] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[0] = ($n & 0x1F) | 0xE0;
 	}
 	elseif ($n >= 0x10000 && $n <= 0x1FFFFF) {
 		$byte = array(0,0,0,0);
 		$byte[3] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[2] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[1] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[0] = ($n & 0x1F) | 0xF0;
 	}
 	elseif ($n >= 0x200000 && $n <= 0x3FFFFFF) {
 		$byte = array(0,0,0,0,0);
 		$byte[4] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[3] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[2] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[1] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[0] = ($n & 0x1F) | 0xF8;
 	}
 	elseif ($n >= 0x4000000 && $n <= 0x7FFFFFFF) {
 		$byte = array(0,0,0,0,0);
 		$byte[5] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[4] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[3] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[2] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[1] = ($n & 0x3F) | 0x80;
 		$n = $n >> 6;
 		$byte[0] = ($n & 0x1F) | 0xFC;
 	}
 	return implode('',array_map('chr',$byte));
 }
 
}
 
?>
