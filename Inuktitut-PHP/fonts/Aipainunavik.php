<?php

require_once 'lib/utf8.php';

class Aipainunavik {

private static $unicode_to_legacy_codes = array(
            0x1403 => 'w', # i
            0x1431 => 'W', # pi
            0x144E => 't', # ti
            0x146D => 'r', # ki
            0x148B => 'Q', # gi
            0x14A5 => 'u', # mi
            0x14C2 => 'i', # ni
            0x14EF => 'y', # si
            0x14D5 => 'o', # li
            0x1528 => 'p', # ji
            0x1555 => '[', # vi
            0x1546 => 'E', # ri
            0x157F => 'e', # qi
            0x158F => 'q', # Ni
            0x1671 => 0x2021, # Xi
            0x15A0 => 0x00C3, # &i
            0x1404 => 0x2122, # ii
            0x1432 => 0x201E, # pii
            0x144F => 0x2020, # tii
            0x146E => 0x00Ae, # kii
            0x148C => 0x0152, # gii
            0x14A6 => 0x00FC, # mii
            0x14C3 => 0x00EE, # nii
            0x14F0 => 0x00A5, # sii
            0x14D6 => 0x00F8, # lii
            0x1529 => 0x00BA, # jii
            0x1556 => 0x201C, # vii
            0x1547 => 0x2030, # rii
            0x1580 => 0x00E9, # qii
            0x1590 => 0x0153, # Nii
            0x1672 => 0x00B7, # Xii nngii
            0x15A1 => 0x00ED, # &ii
            0x1405 => 's', # u
            0x1433 => 'S', # pu
            0x1450 => 'g', # tu
            0x146F => 'f', # ku
            0x148D => 'A', # gu
            0x14A7 => 'j', # mu
            0x14C4 => 'k', # nu
            0x14F1 => 'h', # su
            0x14D7 => 'l', # lu
            0x152A => 'J', # ju
            0x1557 => 'K', # vu
            0x1548 => 'D', # ru
            0x1581 => 'd', # qu
            0x1591 => 'a', # Nu
            0x1673 => 0x201A, # Xu
            0x15A2 => 0x00C0, # &u
            0x1406 => 0x00DF, # uu
            0x1434 => 0x00CD, # puu
            0x1451 => 0x00A9, # tuu
            0x1470 => 0x0192, # kuu
            0x148E => 0x00C5, # guu
            0x14A8 => 0x00CB, # muu
            0x14C5 => 0x00AA, # nuu
            0x14F2 => 0x00A7, # suu
            0x14D8 => 0x00AC, # luu
            0x152B => 0x00D4, # juu
            0x1558 => 0x00D3, # vuu
            0x1549 => 0x00CE, # ruu
            0x1582 => 0x00DA, # quu
            0x1592 => 0x00E5, # Nuu
            0x1674 => 0x00C2, # Xuu
            0x15A3 => 0x00EC, # &uu
            0x140A => 'x', # a
            0x1438 => 'X', # pa
            0x1455 => 'b', # ta
            0x1472 => 'v', # ka
            0x1490 => 'Z', # ga
            0x14AA => 'm', # ma
            0x14C7 => 'N', # na
            0x14F4 => 'n', # sa
            0x14DA => 'M', # la
            0x152D => '/', # ja
            0x1559 => '?', # va
            0x154B => 'C', # ra
            0x1583 => 'c', # qa
            0x1593 => 'z', # Na
            0x1675 => 0x00CA, # Xa
            0x15A4 => 0x0178, # &a
            0x1401 => 0x00C9, # ai
            0x142f => 0x00D1, # pai
            0x144c => 0x00D6, # tai
            0x146b => 0x00DC, # kai
            0x1489 => 0x00E1, # gai
            0x14a3 => 0x00E0, # mai
            0x14c0 => 0x00E2, # nai
            0x14ed => 0x00E3, # sai
            0x14d3 => 0x00E4, # lai
            0x1526 => 0x00E8, # jai
            0x1553 => 0x00EB, # vai
            0x1543 => 0x00EA, # rai
            0x1542 => 0x00EA, # rai
            0x166f => 0x00F2, # qai
            0x1670 => 0x00F4, # ngai  
            0x140B => 0x20AC, # aa
            0x1439 => 0x00D9, # paa
            0x1456 => 0x00CC, # taa
            0x1473 => 0x00CF, # kaa
            0x1491 => 0x00DB, # gaa
            0x14AB => 0x00B5, # maa
            0x14C8 => 0x02C6, # naa
            0x14F5 => 0x00F1, # saa
            0x14DB => 0x02DC, # laa
            0x152E => 0x00F7, # jaa
            0x155A => 0x00BF, # vaa
            0x154C => 0x00C7, # raa
            0x1584 => 0x00E7, # qaa
            0x1594 => 0x00AF, # Naa
            0x1676 => 0x00C1, # Xaa
            0x15A5 => 0x00EF, # &aa
            0x1449 => 0x32, # p
            0x1466 => 0x35, # t
            0x1483 => 0x34, # k
            0x14A1 => '=', # g
            0x14BB => 0x37, # m
            0x14D0 => 0x38, # n
            0x1505 => '+', # s
            0x14EA => 0x39, # l
            0x153E => 0x30, # j
            0x155D => '{', # v
            0x1550 => 0x33, # r
            0x1585 => 0x36, # q
            0x1595 => 0x31, # N
            0x1596 => 0x00D2, # X
            0x15A6 => 0x00D5, # &
            0x157C => 0x00FF, # H
 
     		0x157B => 0x22 , # h
   			0x1575 => 'L', # hi
    		0x1576 => 0x00D8, # hii
    		0x1577 => 'O', # ho
    		0x1578 => 0x201D, # hoo
    		0x1578 => 0x0094, # hoo
    		0x1579 => 'P', # ha
    		0x157a => 0x00BB, # haa
    		0x1574 => 0x00A8, # hai
            
            0x31 => '!', # 1
            0x32 => '@', # 2
            0x33 => '#', # 3
            0x34 => '$', # 4
            0x35 => '%', # 5
            0x36 => '^', # 6
            0x37 => '&', # 7
            0x38 => '*', # 8
            0x39 => '(', # 9
            0x30 => ')', # 0
            
    0x27 => '<', # '
    0x22 => '>', # "
	0x00F7 => "B", # signe de division
	0x2C => 0x2C, # ,
	0x2D => 0x2d, # -
	0x2E => 0x2E, # .
	0x2F => 'F', # /
	0x28 => 'G', # (
	0x29 => 'H', # )
	0x24 => 'R', # \$
	0x2B => 'T', # +
	0x3D => 'U', # =
	0x3F => 'V', # ?
	0x00D7 => 'Y', # signe de multiplication
	0x5B => ']', # [
	0x5C => 0x5C, # \
	0x5D => '}', # ]
	0x5F => 0x5F, # _
	0x7C => 0x7C, # |
	0x7E => 0x7E, # ~
    0x2026 => 0x0085, # points de suspension
    0x00AB => 0x2039, # guillemets français d"ouverture
    0x00AB => 0x008B, # guillemets français d"ouverture
    0x201C => 0x2018, # guillemets d"ouverture
    0x201C => 0x0091, # guillemets d"ouverture
    0x201D => 0x2019, # guillemets de fermeture
    0x201D => 0x0092, # guillemets de fermeture
    0x2A => 0x2022, # *
    0x00BB => 0x203A, # guillemets français de fermeture
    0x00BB => 0x009B, # guillemets français de fermeture
	0x21 => 0x00A1, # !
	0x23 => 0x00A3, # #
	0x2022 => 0x00AB, # point gras
	0x26 => 0x00B4, # &
	0x25 => 0x00B6, # %
    0x141E => 0x00B8, # arrêt de la glotte
	0x40 => 0x00C4, # @
	0x2019 => 0x00C6, # apostrophe d"ouverture
	0x2018 => 0x00E6, # apostrophe de fermeture
	0x46 => 0x00F3, # F
	0x00B6 => 0x00F5, # pied de mouche
	0x00A7 => 0x00F6, # paragraphe
	0x00A9 => 0x00F9, # signe de droits réservés
	0x2122 => 0x00FA, # signe de marque de commerce
	0x00AE => 0x00FB, # signe de marque enregistrée
);

static $ais = array(
	0x140A => 0x1401, # ai
	0x1438 => 0x142F, # pai
	0x1455 => 0x144C, # tai
	0x1472 => 0x146B, # kai
	0x1490 => 0x1489, # gai
	0x14AA => 0x14A3, # mai
	0x14C7 => 0x14C0, # nai
	0x14F4 => 0x14ED, # sai
	0x14DA => 0x14D3, # lai
	0x152D => 0x1526, # jai
	0x1559 => 0x1553, # vai
	0x154B => 0x1542, # rai
	0x1583 => 0x166F, # qai
	0x1593 => 0x1670, # ngai
);

static $ais_in_aipainunavik = array(
	0xC9 => array( 'ra' => 'ai', 'noaipaitai' => 'xw',
		'UCaipaitai' => 0x1401, 'UCnoaipaitai' => array(0x140A,0x1403) ), 
    0xD1 => array( 'ra' => 'pai',  'noaipaitai' => 'Xw',
		'UCaipaitai' => 0x142F, 'UCnoaipaitai' => array(0x1438,0x1403) ), 
    0xD6 => array( 'ra' => 'tai',  'noaipaitai' => 'bw',
		'UCaipaitai' => 0x144C, 'UCnoaipaitai' => array(0x1455,0x1403) ),
    0xDC => array( 'ra' => 'kai',  'noaipaitai' => 'vw',
		'UCaipaitai' => 0x146B, 'UCnoaipaitai' => array(0x1472,0x1403) ), 
    0xE1 => array( 'ra' => 'gai',  'noaipaitai' => 'Zw',
		'UCaipaitai' => 0x1489, 'UCnoaipaitai' => array(0x1490,0x1403) ), 
    0xE0 => array( 'ra' => 'mai',  'noaipaitai' => 'mw',
		'UCaipaitai' => 0x14A3, 'UCnoaipaitai' => array(0x14AA,0x1403) ), 
    0xE2 => array( 'ra' => 'nai',  'noaipaitai' => 'Nw',
		'UCaipaitai' => 0x14C0, 'UCnoaipaitai' => array(0x14C7,0x1403) ), 
    0xE4 => array( 'ra' => 'lai',  'noaipaitai' => 'Mw',
		'UCaipaitai' => 0x14D3, 'UCnoaipaitai' => array(0x14DA,0x1403) ), 
    0xE3 => array( 'ra' => 'sai',  'noaipaitai' => 'nw',
		'UCaipaitai' => 0x14ED, 'UCnoaipaitai' => array(0x14F4,0x1403) ),
    0xE8 => array( 'ra' => 'jai',  'noaipaitai' => '/w',
		'UCaipaitai' => 0x1526, 'UCnoaipaitai' => array(0x152D,0x1403) ), 
    0xEA => array( 'ra' => 'rai',  'noaipaitai' => 'Cw',
		'UCaipaitai' => 0x1542, 'UCnoaipaitai' => array(0x154B,0x1403) ), 
    0xEB => array( 'ra' => 'vai',  'noaipaitai' => '?w',
		'UCaipaitai' => 0x1553, 'UCnoaipaitai' => array(0x1559,0x1403) ), 
    0xF2 => array( 'ra' => 'qai',  'noaipaitai' => 'cw',
		'UCaipaitai' => 0x166F, 'UCnoaipaitai' => array(0x1583,0x1403) ), 
    0xF4 => array( 'ra' => 'ngai',  'noaipaitai' => 'zw',
		'UCaipaitai' => 0x1670, 'UCnoaipaitai' => array(0x1593,0x1403) )
);

public static function getNoaipaitai($key) {
	return self::$ais_in_aipainunavik[$key]['noaipaitai'];
}

public static function getUCaipaitai($key) {
	return utf8::numeric_to_utf8(self::$ais_in_aipainunavik[$key]['UCaipaitai']);
}

public static function getUCnoaipaitai($key) {
	$s = '';
	foreach (self::$ais_in_aipainunavik[$key]['UCnoaipaitai'] as $charCode) {
		$s .= utf8::numeric_to_utf8($charCode);
	}
	return $s;
}

public static function highlight_aipaitai_chars ( $text ) {
	$aipaitai_chars = array_map('chr',array_keys(self::$ais_in_aipainunavik));
	$pat = '/(' . implode('|',$aipaitai_chars) . ')/';
	$rep = '<<<$1>>>';
	$highlighted_text = preg_replace($pat,$rep,$text);	
	return $highlighted_text;
}

public static function unicodeToLegacy ($text) {
	$transcodedText = '';
	$cnt = mb_strlen($text);
	$i = 0;
	while ($i < $cnt) {
		$c = mb_substr($text,$i++,1);
		$nc = utf8::utf8_to_numeric($c);
		$lc = NULL;
		if ( $i < $cnt && utf8::utf8_to_numeric(mb_substr($text,$i,1)) == 0x1403
			&& array_key_exists($nc,self::$ais) 
			&& array_key_exists(self::$ais[$nc],self::$unicode_to_legacy_codes)
			) {
			$lc = self::$unicode_to_legacy_codes[self::$ais[$nc]];
			$i++;
		}
		elseif (array_key_exists($nc,self::$unicode_to_legacy_codes)) 
			$lc = self::$unicode_to_legacy_codes[$nc];
		if ( $nc <= 0x20 )
			$transcodedText .= $c;
		elseif ( $lc==NULL )
			$transcodedText .= 'V'; # ?
		elseif ( is_numeric($lc) ) 
			$transcodedText .= utf8::numeric_to_utf8($lc);
		else
			$transcodedText .= $lc;
	}
	return $transcodedText;
}

/*
 * aipaitai
 *   '1' or 1 : all a+i replaced with aipai
 *   '0' or 0 : all aipai replaced with a+i
 *   '-' : no replacement whatsoever
 */
public static function legacyToUnicode ($text, $aipaitai) {
	$cs = array();
    $dot = FALSE;
    $i = 0;
    $l = mb_strlen($text);
    while ($i < $l) {
            $c = mb_substr($text,$i,1);
            $nc = utf8::utf8_to_numeric($c);
            if ( $nc == ord('w') ) { $d = 0x1403; } # i
            elseif ( $nc == ord('W') ) { $d = 0x1431; } # pi
            elseif ( $nc == ord('t') ) { $d = 0x144E; } # ti
            elseif ( $nc == ord('r') ) { # ki
                $d = 0x146D; 
				if (count($cs) > 0 && $cs[count($cs)-1] == 0x1550) {
                    array_pop($cs);
                    $d = 0x157f; # r+ki > qi
                }
            }
            elseif ( $nc == ord('Q') ) { # gi
                $d = 0x148B; 
                if (count($cs) != 0) {
                    $e = $cs[count($cs)-1];
                    if ( $e == 0x1595 ) { # ng+gi
                        array_pop($cs);
                        $d = 0x158f; # ngi
                    }
                    elseif ( $e ==  0x1596 ) { #nng+gi
                        array_pop($cs);
                        $d = 0x1671; # nngi
                    }
                }
            }
            elseif ( $nc == ord('u') ) { $d = 0x14A5; } # mi
            elseif ( $nc == ord('i') ) { $d = 0x14C2; } # ni
            elseif ( $nc == ord('y') ) { $d = 0x14EF; } # si
            elseif ( $nc == ord('o') ) { $d = 0x14D5; } # li
            elseif ( $nc == ord('p') ) { $d = 0x1528; } # ji
            elseif ( $nc == ord('[') ) { $d = 0x1555; } # vi
            elseif ( $nc == ord('E') ) { # ri
                $d = 0x1546;
             }
            elseif ( $nc == ord('e') ) { # qi
                $d = 0x157F;
            }
            elseif ( $nc == ord('q') ) { # ngi
                $d = 0x158F;
                if ($dot) { array_pop($cs); }
                if (count($cs) != 0 && ($cs[count($cs)-1] == 0x14d0 || $cs[count($cs)-1] == 0x1595)) {
                        array_pop($cs);
                        $d = 0x1671; # nngi
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == 0x2021 || $nc == 0x0087 ) { $d = 0x1671; } # nngi
            elseif ( $nc == 0x00C3 ) { $d = 0x15A0; } # &i
            elseif ( $nc == 0x2122 ) { $d = 0x1404; } # ii
            elseif ( $nc == 0x201e || $nc == 0x0084 ) { $d = 0x1432; } # pii
            elseif ( $nc == 0x2020 || $nc == 0x0086 ) { $d = 0x144F; } # tii
            elseif ( $nc == 0x00ae ) { # kii
                $d = 0x146E;
				if (count($cs) > 0 && $cs[count($cs)-1] == 0x1550) {
                    array_pop($cs);
                    $d = 0x1580; # rkii > qii
                }
            }
            elseif ( $nc == 0x0152 || $nc == 0x008c ) { # gii
                $d = 0x148C;
                if (count($cs) != 0) {
                    $e = $cs[count($cs)-1];

                    if ( $e ==  0x1595 ) { # ng+gii
                        array_pop($cs);
                        $d = 0x1590; # ngii
                    }
                    elseif ( $e == 0x1596 ) { # nng+gii
                        array_pop($cs);
                        $d = 0x1672; # nngii
                    }
                }
            } # gii
            elseif ( $nc == 0x00FC ) { $d = 0x14A6; } # mii
            elseif ( $nc == 0x00ee ) { $d = 0x14C3; } # nii
            elseif ( $nc == 0x00a5 ) { $d = 0x14F0; } # sii
            elseif ( $nc == 0x00f8 ) { $d = 0x14D6; } # lii
            elseif ( $nc == 0xba ) { $d = 0x1529; } # jii
            elseif ( $nc == 0x201c ) { $d = 0x1556; } # vii
            elseif ( $nc == 0x2030 ) { # rii
                $d = 0x1547;
            }
            elseif ( $nc == 0x00e9 ) { # qii
                $d = 0x1580;
                }
            elseif ( $nc == ord('1') ) { # ng
                $d = 0x1595;
                if (count($cs) != 0 && ($cs[count($cs)-1] == 0x14d0 || $cs[count($cs)-1] == 0x1595)) {
                    array_pop($cs);
                    $d=0x1596;
                }
                }
            elseif ( $nc == ord('3') ) {  $d = 0x1550; } # r
            elseif ( $nc == 0xd2 ) {  $d = 0x1596;  } # nng
            elseif ( $nc == 0x0153 || $nc == 0x009c ) { # ngii
                $d = 0x1590;
                if (count($cs) != 0) {
                    $e = $cs[count($cs)-1];
                    if ( $e == 0x14d0 || $e == 0x1595 ) {  # n, ng
                        array_pop($cs);
                        $d = 0x1672; # nngii
                    }
                }
            }
            elseif ( $nc == 0xb7 ) { $d = 0x1672; } # nngii
            elseif ( $nc == 0x00ed ) { $d = 0x15A1; } # &ii
            elseif ( $nc == ord('s') ) { $d = 0x1405; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # u
            elseif ( $nc == ord('S') ) { $d = 0x1433; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # pu
            elseif ( $nc == ord('g') ) { $d = 0x1450; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # tu
            elseif ( $nc == ord('f') ) { # ku
                $d = 0x146F;
                if ($dot) { array_pop($cs); }
                if (count($cs) > 0 && $cs[count($cs)-1] == 0x1550) {
                    array_pop($cs);
                    $d = 0x1581; # rku > qu
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('A') ) { # gu
                $d = 0x148D;
                if ($dot) { array_pop($cs); }
                if (count($cs) != 0) {
                    $e = $cs[count($cs)-1];
                    if ( $e == 0x1595 ) { #ng+gu
                        array_pop($cs);
                        $d = 0x1591; # ngu
                    }
                    elseif ( $e == 0x1596 ) { #nng+gu
                        array_pop($cs);
                        $d = 0x1673; # nngu
                    }
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('j') ) { $d = 0x14A7; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # mu
            elseif ( $nc == ord('k') ) { $d = 0x14C4; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # nu
            elseif ( $nc == ord('h') ) { $d = 0x14F1; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # su
            elseif ( $nc == ord('l') ) { $d = 0x14D7; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # lu
            elseif ( $nc == ord('J') ) { $d = 0x152A; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # ju
            elseif ( $nc == ord('K') ) { $d = 0x1557; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # vu
            elseif ( $nc == ord('D') ) { # ru
                $d = 0x1548;
                } 
            elseif ( $nc == ord('d') ) { # qu
                $d = 0x1581;
            } 
            elseif ( $nc == ord('a') ) { # ngu
                $d = 0x1591;
                if ($dot) { array_pop($cs); }
                if (count($cs) != 0 && ($cs[count($cs)-1] == 0x14d0 || $cs[count($cs)-1] == 0x1595)) {
                        array_pop($cs);
                        $d = 0x1673; # nngu
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == 0x201A ) { $d = 0x1673; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # nngu
            elseif ( $nc == 0xC0 ) { $d = 0x15A2; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # &u
            elseif ( $nc == 0x00df ) { $d = 0x1406; } # uu
            elseif ( $nc == 0x00cd ) { $d = 0x1434; } # puu
            elseif ( $nc == 0x00a9 ) { $d = 0x1451; } # tuu
            elseif ( $nc == 0x0192 || $nc == 0x0083 ) { # kuu
                $d = 0x1470;
                if (count($cs) > 0 && $cs[count($cs)-1] == 0x1550) {
                    array_pop($cs);
                    $d = 0x1582; # rkuu > quu
                }
            }
            elseif ( $nc == 0x00c5 ) { # guu
                $d = 0x148E;
                if ($dot) { array_pop($cs); }
                if (count($cs) != 0) {
                    $e = $cs[count($cs)-1];

                    if ( $e == 0x1595 ) { #ng+guu
                        array_pop($cs);
                        $d = 0x1592; # nguu
                    }
                    elseif ( $e == 0x1596 ) { #nng+guu
                        array_pop($cs);
                        $d = 0x1674; # nnguu
                    }
                }
                if ($dot) {$dot=FALSE; $d++;}
            } # guu
            elseif ( $nc == 0xcb ) { $d = 0x14A8; } # muu
            elseif ( $nc == 0xaa ) { $d = 0x14C5; } # nuu
            elseif ( $nc == 0xa7 ) { $d = 0x14F2; } # suu
            elseif ( $nc == 0x00ac ) { $d = 0x14D8; } # luu
            elseif ( $nc == 0x00d4 ) { $d = 0x152B; } # juu
            elseif ( $nc == 0xd3 ) { $d = 0x1558; } # vuu
            elseif ( $nc == 0x00ce ) { # ruu
                $d = 0x1549;
            }
            elseif ( $nc == 0xda ) { # quu
                $d = 0x1582;
            }
            elseif ( $nc == 0x00e5 ) { # nguu
                $d = 0x1592;
                if ($dot) { array_pop($cs); }
                if (count($cs) != 0) {
                    $e = $cs[count($cs)-1];
                    if ( $e == 0x14d0 || $e == 0x1595 ) { #n, ng
                        array_pop($cs);
                        $d = 0x1674; # nnguu
                    }
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == 0x00c2 ) { $d = 0x1674; } # nnguu
            elseif ( $nc == 0x00ec ) { $d = 0x15A3; } # &uu
            elseif ( $nc == ord('x') ) {  # a
                $d = 0x140a;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e ==  ord('w') ) {
                        if ($aipaitai=='1') {
                        	$d = 0x1401;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # ai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('X') ) { # pa
                $d = 0x1438;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                     if ( $e ==  ord('w') ) {
                        if ($aipaitai=='1') {
                        	$d = 0x142F;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # ai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('b') ) { # ta
                $d = 0x1455;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                     if ( $e === 'w' ) {
                        if ($aipaitai=='1') {
                        	$d = 0x144C;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # ai
                        }
                        $i=$j;
                    }
                } 
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('v') ) { # ka
                $d = 0x1472;
                $k2q = FALSE;
                if ($dot) { array_pop($cs); }
                if (count($cs) > 0 && $cs[count($cs)-1] == 0x1550) {
                    array_pop($cs);
                    $d = 0x1583; # rka > qa
                    $k2q = TRUE;
                }
               $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ($e === 'w') {
                        if ($aipaitai=='1') {
                           $d = $k2q? 0x166f : 0x146B; # kai
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # ka+i
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('Z') ) { # ga
                $d = 0x1490;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e === 'w' ) {
                        if ($aipaitai=='1') {
                            if (count($cs) != 0 && $cs[count($cs)-1] == 0x1595) {
                                array_pop($cs);
                                $d = 0x1670; # ngai
                            } else {
                                $d = 0x1489; # gai
                                }
                        } else {
                            if (count($cs) != 0 && $cs[count($cs)-1] == 0x1595) {
                                array_pop($cs);
                                array_push($cs,0x1593); # nga
                            } elseif (count($cs) != 0 && $cs[count($cs)-1] == 0x1596) {
                                array_pop($cs);
                                array_push($cs,0x1675); # nnga
                            } else {
                                array_push($cs,$d); # ga
                            }
                            $d = 0x1403; # +i
                        }
                        $i=$j;
                    }
                } elseif (count($cs) != 0 && $cs[count($cs)-1] == 0x1595) {
                    array_pop($cs);
                   $d = 0x1593; # nga
                } elseif (count($cs) != 0 && $cs[count($cs)-1] == 0x1596) {
                    array_pop($cs);
                    $d = 0x1675; # nnga
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('m') ) { # ma
                $d = 0x14AA;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e === 'w' ) {
                        if ($aipaitai=='1') {
                        	$d = 0x14A3; 
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # mai
                        }
                        $i=$j;
                	}
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('N') ) { # na
                $d = 0x14C7;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e === 'w' ) {
                        if ($aipaitai==='1') {
                        	$d = 0x14C0;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # nai
                        }
                        $i=$j;
                    }
                } 
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('n') ) { # sa
                $d = 0x14F4;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e === 'w' ) {
                        if ($aipaitai=='1') {
                        	$d = 0x14ED;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # sai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('M') ) { # la
                $d = 0x14DA;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e === 'w' ) {
                        if ($aipaitai=='1') {
                        	$d = 0x14D3;
                        } else {
                        	array_push($cs,$d); $d = 0x1403; # lai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('/') ) { # ja
                $d = 0x152d;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e === 'w' ) {
                        if ($aipaitai=='1') {
                        	$d = 0x1526;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # jai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('?') ) { # va
                $d = 0x1559;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ($e === 'w') {
                        if ($aipaitai=='1') {
                        	$d = 0x1553;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # vai
                        }
                       $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('C') ) { # ra
                $d = 0x154b;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e === 'w' ) {
                        if ($aipaitai=='1') {
                        	$d = 0x1542;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # rai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
           elseif ( $nc == ord('c') ) { # qa
               $precr = FALSE;
               $d = 0x1583;
               if ($dot) { array_pop($cs); }
               $j=$i+1;
               if ($j < $l) {
                   $e = mb_substr($text,$j,1);
                   if ($e === 'w') {
                       if ($aipaitai=='1') {
                           if ($precr) {
                               $d = 0x146b; # kai
                           } else {
                               $d = 0x166f; # qai
                           }
                       } else {
                           array_push($cs,$d); 
                           $d = 0x1403; 
                       }
                       $i=$j;
                   }
               }
               if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('z') ) { # nga
                $d = 0x1593;
                if ($dot) { array_pop($cs); }
                $precnng = FALSE;
                if (count($cs) != 0) {
                    $e = $cs[count($cs)-1];
                    if ( $e == 0x14d0 || $e == 0x1595 ) { # n, ng
                        array_pop($cs);
                        $d = 0x1675; # nnga
                        $precnng = TRUE;
                    }
                }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e === 'w' ) {
                        if ($aipaitai=='1') {
                            if ($precnng) {
                                array_push($cs,0x1596);
                                $d = 0x1489;
                            } else {
                                $d = 0x1670; # ngai
                            }
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # ngai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == 0xCA ) { # nnga
                $d = 0x1675;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e === 'w' ) {
                        if ($aipaitai=='1') {
                        	array_push($cs,0x1596); $d = 0x1489;
                        } else {
                        	array_push($cs,0x1675); $d = 0x1403;  # nngai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == 0x178 ) { $d = 0x15A4; } # &a
            elseif ( $nc == 0x20ac ) { $d = 0x140B; } # aa
            elseif ( $nc == 0xd9 ) { $d = 0x1439; } # paa
            elseif ( $nc == 0xCC ) { $d = 0x1456; } # taa
            elseif ( $nc == 0xcf ) { # kaa
                $d = 0x1473;
                if (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1550) {
                    array_pop($cs);
                    array_pop($cs);
                    array_push($cs,0x1585);
                } elseif (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1585) {
                    array_pop($cs);
                } elseif (count($cs) > 0 && $cs[count($cs)-1] == 0x1550) {
                    array_pop($cs);
                    $d = 0x1584;
                }
            }
            elseif ( $nc == 0x00db ) { $d = 0x1491; } # gaa
            elseif ( $nc == 0xb5 ) { $d = 0x14AB; } # maa
            elseif ( $nc == 0x02c6 || $nc == 0x0098 ) { $d = 0x14C8; } # naa
            elseif ( $nc == 0x00f1 ) { $d = 0x14F5; } # saa
            elseif ( $nc == 0x02dc ) { $d = 0x14DB; } # laa
            elseif ( $nc == 0x00f7 ) { $d = 0x152E; } # jaa
            elseif ( $nc == 0x00bf ) { $d = 0x155A; } # vaa
            elseif ( $nc == 0x00c7 ) { # raa
                $d = 0x154C;
            }
            elseif ( $nc == 0x00e7 ) { # qaa
                $d = 0x1584;
                if (count($cs) != 0 && ($cs[count($cs)-1] == 0x1550 || $cs[count($cs)-1] == 0x1585)) { # r+qaa = q+kaa (qqaa)
                    array_pop($cs);
                    array_push($cs,0x1585);
                    $d = 0x1473;
                }
            }
            elseif ( $nc == 0xaf ) { # ngaa
                $d = 0x1594;
                if (count($cs) != 0) {
                    $e = $cs[count($cs)-1];
                    if ($e == 0x14d0 || $e == 0x1595 ) { # n, ng
                        array_pop($cs);
                        $d = 0x1676; # nngaa
                    }
                }
            } # ngaa
            elseif ( $nc == 0x00c1 ) { $d = 0x1676; } # nngaa
            elseif ( $nc == 0xef || $nc == 0x0088 ) { $d = 0x15A5; } # &aa
            elseif ( $nc == ord('2') ) { $d = 0x1449; } # p
            elseif ( $nc == ord('5') ) { $d = 0x1466; } # t
            elseif ( $nc == ord('4') ) { $d = 0x1483; } # k
            elseif ( $nc == ord('=') ) { $d = 0x14A1; } # g
            elseif ( $nc == ord('7') ) { $d = 0x14BB; } # m
            elseif ( $nc == ord('8') ) { $d = 0x14D0; } # n
            elseif ( $nc == ord('+') ) { $d = 0x1505; } # s
            elseif ( $nc == ord('9') ) { $d = 0x14EA; } # l
            elseif ( $nc == ord('0') ) { $d = 0x153E; } # j
            elseif ( $nc == ord('{') ) { $d = 0x155D; } # v
            elseif ( $nc == ord('6') ) { $d = 0x1585; } # q
            elseif ( $nc == ord('P') ) { $d = 0x15A6; } # &
            elseif ( $nc == ord('B') ) { $d = 0x157C; } # H
            elseif ( $nc == ord('!') ) { $d = ord('1'); }
            elseif ( $nc == ord('@') ) { $d = ord('2'); }
            elseif ( $nc == ord('#') ) { $d = ord('3'); }
            elseif ( $nc == ord('$') ) { $d = ord('4'); }
            elseif ( $nc == ord('%') ) { $d = ord('5'); }
            elseif ( $nc == ord('^') ) { $d = ord('6'); }
            elseif ( $nc == ord('&') ) { $d = ord('7'); }
            elseif ( $nc == ord('*') ) { $d = ord('8'); }
            elseif ( $nc == ord('(') ) { $d = ord('9'); }
            elseif ( $nc == ord(')') ) { $d = ord('0'); }
            elseif ( $nc == ord('G') ) { $d = ord('('); }
            elseif ( $nc == ord('H') ) { $d = ord(')'); }
            elseif ( $nc == ord('V') ) { $d = ord('?'); }
            elseif ( $nc == ord('\\') ) { $d = ord('/'); }
            elseif ( $nc == ord('>') ) { $d = ord('"'); }
            elseif ( $nc == 0x00a1 ) { $d = ord('!'); }
            elseif ( $nc == 0x00a2 ) { $d = ord('$'); }
            elseif ( $nc == 0x00a3 ) { $d = ord('#'); }
            elseif ( $nc == 0x00a4 ) { $d = 0x00ae; }
            elseif ( $nc == 0x00a7 ) { $d = ord('*'); }
            elseif ( $nc == 0x00aa ) { $d = ord('['); }
            elseif ( $nc == 0x00b0 ) { $d = 0x00a9; }
            elseif ( $nc == 0x00b6 ) { $d = ord('&'); }
            elseif ( $nc == 0x00b7 ) { $d = 0x00f7; }
            elseif ( $nc == 0x00ba ) { $d = ord(']'); }
            elseif ( $nc == 0x0131 ) { $d = ord('}'); }
            elseif ( $nc == 0x2013 ) { $d = 0x00d7; }
            elseif ( $nc == 0x0096 ) { $d = 0x00d7; }
            elseif ( $nc == 0x201a ) { $d = ord('+'); }
            elseif ( $nc == 0x0082 ) { $d = ord('+'); }
            elseif ( $nc == 0x2021 ) { $d = 0x2154; }
            elseif ( $nc == 0x0087 ) { $d = 0x2154; }
            elseif ( $nc == 0x2039 ) { $d = 0x00bc; }
            elseif ( $nc == 0x008b ) { $d = 0x00bc; }
            elseif ( $nc == 0x203a ) { $d = 0x00bd; }
            elseif ( $nc == 0x009b ) { $d = 0x00bd; }
            elseif ( $nc == 0x2044 ) { $d = 0x00a2; }
            elseif ( $nc == 0x2260 ) { $d = ord('='); }
            elseif ( $nc == 0x25ca ) { $d = ord('{'); }
            elseif ( $nc == 0xf001 ) { $d = 0x00be; }
            elseif ( $nc == 0xf002 ) { $d = 0x2153; }
            elseif ( $nc == 0x0085 ) { $d = 0x2026; }
            elseif ( $nc == 0x0091 ) { $d = 0x2018; }
            elseif ( $nc == 0x0092 ) { $d = 0x2019; }
            elseif ( $nc == 0x0093 ) { $d = 0x201c; }
            elseif ( $nc == 0x0094 ) { $d = 0x201d; }
            elseif ( $nc == 0x0095 ) { $d = 0x2022; }
            elseif ( $nc == 0x0097 ) { $d = 0x2014; }
            elseif ( $nc == 0x0099 ) { $d = 0x2122; }
            
            elseif ( $nc == 0xC9 ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x1401; else {array_push($cs, 0x140A); $d = 0x1403; } } # ai
            elseif ( $nc == 0xD1 ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x142F; else {array_push($cs, 0x1438); $d = 0x1403; } } # pai
            elseif ( $nc == 0xD6 ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x144C; else {array_push($cs, 0x1455); $d = 0x1403; } } # tai
            elseif ( $nc == 0xDC ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x146B; else {array_push($cs, 0x1472); $d = 0x1403; } } # kai
            elseif ( $nc == 0xE1 ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x1489; else {array_push($cs, 0x1490); $d = 0x1403; } } # gai
            elseif ( $nc == 0xE0 ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x14A3; else {array_push($cs, 0x14AA); $d = 0x1403; } } # mai
            elseif ( $nc == 0xE2 ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x14C0; else {array_push($cs, 0x14C7); $d = 0x1403; } } # nai
            elseif ( $nc == 0xE4 ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x14D3; else {array_push($cs, 0x14DA); $d = 0x1403; } } # lai
            elseif ( $nc == 0xE3 ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x14ED; else {array_push($cs, 0x14F4); $d = 0x1403; } } # sai
            elseif ( $nc == 0xE8 ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x1526; else {array_push($cs, 0x152D); $d = 0x1403; } } # jai
            elseif ( $nc == 0xEA ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x1542; else {array_push($cs, 0x154B); $d = 0x1403; } } # rai
            elseif ( $nc == 0xEB ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x1553; else {array_push($cs, 0x1559); $d = 0x1403; } } # vai
            elseif ( $nc == 0xF2 ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x166F; else {array_push($cs, 0x1583); $d = 0x1403; } } # qai
            elseif ( $nc == 0xF4 ) { if ($aipaitai=='1' || $aipaitai=='-') $d = 0x1670; else {array_push($cs, 0x1593); $d = 0x1403; } } # ngai

            else {
                $dot=FALSE;
             	$d=$nc;
            }
            $i++;
            array_push($cs,$d);
        }
	$out = '';
	foreach ($cs as $n) {
		$str = utf8::numeric_to_utf8($n);
		$out .= $str;
	}
	return $out;
}

}
