<?php

require_once 'lib/utf8.php';

class Nunacom {

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
            0x1555 => 'F', # vi
            0x1546 => 'E', # ri
            0x157F => 'e', # qi
            0x158F => 'q', # Ni
            0x1671 => 'T', # Xi
            0x15A0 => 'O', # &i
            0x1404 => '|w', # ii
            0x1432 => '|W', # pii
            0x144F => '|t', # tii
            0x146E => '}r', # kii
            0x148C => '}Q', # gii
            0x14A6 => '}u', # mii
            0x14C3 => '`i', # nii
            0x14F0 => '+y', # sii
            0x14D6 => '`o', # lii
            0x1529 => ']p', # jii
            0x1556 => '|F', # vii
            0x1547 => '`E', # rii
            0x1580 => '+e', # qii
            0x1590 => '1}Q', # Nii
            0x1672 => 'R}Q', # Xii nngii
            0x15A1 => '`O', # &ii
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
            0x1673 => 'Y', # Xu
            0x15A2 => 'L', # &u
            0x1406 => ']s', # uu
            0x1434 => ']S', # puu
            0x1451 => '}g', # tuu
            0x1470 => '|f', # kuu
            0x148E => '|A', # guu
            0x14A8 => '+j', # muu
            0x14C5 => '~k', # nuu
            0x14F2 => '+h', # suu
            0x14D8 => '~l', # luu
            0x152B => '+J', # juu
            0x1558 => '}K', # vuu
            0x1549 => '}D', # ruu
            0x1582 => '3+f', # quu
            0x1592 => '1+A', # Nuu
            0x1674 => 'R+A', # Xuu
            0x15A3 => '~L', # &uu
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
            0x1675 => 'U', # Xa
            0x15A4 => 'I', # &a
            0x1401 => 'xw', # ai
            0x142f => 'Xw', # pai
            0x144c => 'bw', # tai
            0x146b => 'vw', # kai
            0x1489 => 'Zw', # gai
            0x14a3 => 'mw', # mai
            0x14c0 => 'Nw', # nai
            0x14ed => 'nw', # sai
            0x14d3 => 'Mw', # lai
            0x1526 => '/w', # jai
            0x1553 => '?w', # vai
            0x1543 => 'Cw', # rai
            0x1542 => 'Cw', # rai
            0x166f => 'cw', # qai
            0x1670 => 'zw', # ngai  
            0x140B => '+x', # aa
            0x1439 => '+X', # paa
            0x1456 => '|b', # taa
            0x1473 => ']v', # kaa
            0x1491 => ']Z', # gaa
            0x14AB => ']m', # maa
            0x14C8 => '~N', # naa
            0x14F5 => ']n', # saa
            0x14DB => '~M', # laa
            0x152E => ']/', # jaa
            0x155A => '+?', # vaa
            0x154C => '}C', # raa
            0x1584 => '|c', # qaa
            0x1594 => '1]Z', # Naa
            0x1676 => 'R]Z', # Xaa
            0x15A5 => '~I', # &aa
            0x1449 => 0x32, # p
            0x1466 => 0x35, # t
            0x1483 => 0x34, # k
            0x14A1 => '[', # g
            0x14BB => 0x37, # m
            0x14D0 => 0x38, # n
            0x1505 => '{', # s
            0x14EA => 0x39, # l
            0x153E => 0x30, # j
            0x155D => '=', # v
            0x1550 => 0x33, # r
            0x1585 => 0x36, # q
            0x1595 => 0x31, # N
            0x1596 => 'R', # X
            0x15A6 => 'P', # &
            0x157C => 'B', # H
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
            0x28 => 'G', # (
            0x29 => 'H', # )
            0x3F => 'V', # ?
            0x2F => '\\', # /
            0x22 => 0x22, # "
            0x27 => 0x27, # '
            0x2C => 0x2C, # ,
            0x2E => 0x2E, # .
            0x3A => 0x3A, # :
            0x3B => 0x3B, # ;
            0x3C => 0x3C, # <
            0x3E => 0x3E, # >
            0x5F => 0x5F, # _
            0x21 => 0xa1, # !
            0x24 => 0xa2, # $
            0x23 => 0xa3, # #
            0x00ae => 0xa4, # marque d�pos�e
            0x2a => 0xa7, # *
            0x5b => 0xaa, # [
            0x00a9 => 0xb0, # copyright
            0x26 => 0xb6, # &
            0x00f7 => 0xb7, # division 
            0x5D => 0xba, # ]
            0x7D => 0x131, # }
            0x00d7 => 0x2013, # multiplication
            0x2B => 0x201a, # +
            0x2154 => 0x2021, # 2/3
            0x00bc => 0x2039, # 1/4
            0x00bd => 0x203a, # 1/2
            0x00a2 => 0x2044, # centime
            0x3D => 0x2260, # = 
            0x7B => 0x25ca, # {
            0x00be => 0xf001, # 3/4
            0x2153 => 0xf002, # 1/3
            0x2d => 0x2010, # -
            0x2010 => 0x2010, # -
            0x2026 => 0x2026, # points de suspension
            0x2018 => 0x2018, # guillemet-apostrophe culbut�
            0x2019 => 0x2019, # guillemet-apostrophe
            0x201c => 0x201c, # guillemet-apostrophe double culbut�
            0x201d => 0x201d, # guillemet-apostrophe double
            0x2022 => 0x2022, # puce
            0x2014 => 0x2014, # tiret cadratin
            0x2122 => 0x2122, # marque de commerce
);


public static function unicodeToLegacy ($text) {
	$transcodedText = '';
	$cnt = mb_strlen($text);
	$i = 0;
	while ($i < $cnt) {
		$c = mb_substr($text,$i++,1);
		$nc = utf8::utf8_to_numeric($c);
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
 *   '1' or 1 or TRUE : all a+i replaced with aipai
 *   '0' or 0 or FALSE : all aipai replaced with a+i
 */
public static function legacyToUnicode ($text, $aipaitai) {
	$cs = array();
    $dot = FALSE;
    $i = 0;
    $l = mb_strlen($text);
    $text_transcode = '';
        while ($i < $l) {
            $c = mb_substr($text,$i,1);
            $nc = utf8::utf8_to_numeric($c);
            if ( $nc == ord('w') ) { $d = 0x1403; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # i
            elseif ( $nc == ord('W') ) { $d = 0x1431; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # pi
            elseif ( $nc == ord('t') ) { $d = 0x144E; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # ti
            elseif ( $nc == ord('r') ) { # ki
                $d = 0x146D; 
                if ($dot) { array_pop($cs); }
                if (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1550) {
                    array_pop($cs);
                    array_pop($cs);
                    array_push($cs,0x1585);
                } elseif (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1585) {
                     array_pop($cs);
                } elseif (count($cs) > 0 && $cs[count($cs)-1] == 0x1550) {
                    array_pop($cs);
                    $d = 0x157f;
                }
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('Q') ) { # gi
                $d = 0x148B; 
                if ($dot) { array_pop($cs); }
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
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('u') ) { $d = 0x14A5; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # mi
            elseif ( $nc == ord('i') ) { $d = 0x14C2; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # ni
            elseif ( $nc == ord('y') ) { $d = 0x14EF; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # si
            elseif ( $nc == ord('o') ) { $d = 0x14D5; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # li
            elseif ( $nc == ord('p') ) { $d = 0x1528; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # ji
            elseif ( $nc == ord('F') ) { $d = 0x1555; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # vi
            elseif ( $nc == ord('E') ) { # ri
                $d = 0x1546;
                if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} 
                if (count($cs) != 0 && $cs[count($cs)-1] == 0x1585) { # q+ri = r+ri (rri)
                    array_pop($cs); 
                    array_push($cs, 0x1550);
                }
            }
            elseif ( $nc == ord('e') ) { # qi
                $d = 0x157F;
                if ($dot) { array_pop($cs); }
                if (count($cs) != 0 && ($cs[count($cs)-1] == 0x1550 || $cs[count($cs)-1] == 0x1585)) { # r+qi = q+ki (qqi)
                    array_pop($cs);
                    array_push($cs,0x1585);
                    $d = 0x146d;
                    }
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('q') ) { # ngi
                $d = 0x158F;
                if ($dot) { array_pop($cs); }
                if (count($cs) != 0) {
                    $e = $cs[count($cs)-1];
                    if ( $e == 0x14d0 || $e == 0x1595 ) { # n, ng
                        array_pop($cs);
                        $d = 0x1671; # nngi
                    }
                }
                if ($dot) {$dot=FALSE;; $d++;}
                }
            elseif ( $nc == ord('T') ) { $d = 0x1671; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # nngi
            elseif ( $nc == ord('O') ) { $d = 0x15A0; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # &i
            elseif ( $nc == 0x2211 ) { $d = 0x1404; } # ii
            elseif ( $nc == 0x201e || $nc == 0x0084 ) { $d = 0x1432; } # pii
            elseif ( $nc == 0x2020 || $nc == 0x0086 ) { $d = 0x144F; } # tii
            elseif ( $nc == 0x00ae ) { # kii
                $d = 0x146E;
                if (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1550) {
                    array_pop($cs);
                    array_pop($cs);
                    array_push($cs,0x1585);
                } elseif (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1585) {
                    array_pop($cs);
                } elseif (count($cs) > 0 && $cs[count($cs)-1] == 0x1550) {
                    array_pop($cs);
                    $d = 0x1580;
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
            elseif ( $nc == 0x00a8 ) { $d = 0x14A6; } # mii
            elseif ( $nc == 0x00ee ) { $d = 0x14C3; } # nii
            elseif ( $nc == 0x00a5 ) { $d = 0x14F0; } # sii
            elseif ( $nc == 0x00f8 ) { $d = 0x14D6; } # lii
            elseif ( $nc == 0x03c0 ) { $d = 0x1529; } # jii
            elseif ( $nc == 0x00cf ) { $d = 0x1556; } # vii
            elseif ( $nc == 0x00b4 ) { # rii
                $d = 0x1547;
                if (count($cs) != 0 && $cs[count($cs)-1] == 0x1585) { # q+rii = r+rii (rrii)
                    array_pop($cs);
                    array_push($cs,0x1550);
                }
            } # rii
            elseif ( $nc == 0x00e9 ) { # qii
                $d = 0x1580;
                if (count($cs) != 0 && ($cs[count($cs)-1] == 0x1550 || $cs[count($cs)-1] == 0x1585)) { # r+qii = q+kii (qqii)
                    array_pop($cs);
                    array_push($cs,0x1585);
                    $d = 0x146e;
                    }
                }
            elseif ( $nc == ord('1') ) { # ng
                $d = 0x1595;
                if (count($cs) != 0 && ($cs[count($cs)-1] == 0x14d0 || $cs[count($cs)-1] == 0x1595)) {
                    array_pop($cs);
                    $d=0x1596;
                }
                }
            elseif ( $nc == ord('3') ) {  $d = 0x1550; } # r
            elseif ( $nc == ord('R') ) {  $d = 0x1596;  } # nng
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
            elseif ( $nc == 0x02c7 ) { $d = 0x1672; } # nngii
            elseif ( $nc == 0x00d8 ) { $d = 0x15A1; } # &ii
            elseif ( $nc == ord('s') ) { $d = 0x1405; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # u
            elseif ( $nc == ord('S') ) { $d = 0x1433; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # pu
            elseif ( $nc == ord('g') ) { $d = 0x1450; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # tu
            elseif ( $nc == ord('f') ) { # ku
                $d = 0x146F;
                if ($dot) { array_pop($cs); }
                if (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1550) {
                    array_pop($cs);
                    array_pop($cs);
                    array_push($cs,0x1585);
                } elseif (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1585) {
                    array_pop($cs);
                } elseif (count($cs) > 0 && $cs[count($cs)-1] == 0x1550) {
                    array_pop($cs);
                    $d = 0x1581;
                }
                if ($dot) {$dot=FALSE;; $d++;}
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
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('j') ) { $d = 0x14A7; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # mu
            elseif ( $nc == ord('k') ) { $d = 0x14C4; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # nu
            elseif ( $nc == ord('h') ) { $d = 0x14F1; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # su
            elseif ( $nc == ord('l') ) { $d = 0x14D7; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # lu
            elseif ( $nc == ord('J') ) { $d = 0x152A; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # ju
            elseif ( $nc == ord('K') ) { $d = 0x1557; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # vu
            elseif ( $nc == ord('D') ) { # ru
                $d = 0x1548;
                if ($dot) {$dot=FALSE;; array_pop($cs); $d++;}; # ruu
                if (count($cs) != 0 && $cs[count($cs)-1] == 0x1585) { # q+ru = r+ru (rru)
                    array_pop($cs); 
                    array_push($cs,0x1550);
                }
                } 
            elseif ( $nc == ord('d') ) { # qu
                $d = 0x1581;
                if ($dot) { array_pop($cs); }
                if (count($cs) != 0 && ($cs[count($cs)-1] == 0x1550 || $cs[count($cs)-1] == 0x1585)) { # r+qu = q+ku (qqu)
                    array_pop($cs);
                    array_push($cs,0x1585);
                    $d = 0x146f;
                    }
                if ($dot) {$dot=FALSE;; $d++;}
            } 
            elseif ( $nc == ord('a') ) { # ngu
                $d = 0x1591;
                if ($dot) { array_pop($cs); }
                if (count($cs) != 0 && ($cs[count($cs)-1] == 0x14d0 || $cs[count($cs)-1] == 0x1595)) {
                        array_pop($cs);
                        $d = 0x1673; # nngu
                }
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('Y') ) { $d = 0x1673; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # nngu
            elseif ( $nc == ord('L') ) { $d = 0x15A2; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # &u
            elseif ( $nc == 0x00df ) { $d = 0x1406; } # uu
            elseif ( $nc == 0x00cd ) { $d = 0x1434; } # puu
            elseif ( $nc == 0x00a9 ) { $d = 0x1451; } # tuu
            elseif ( $nc == 0x0192 || $nc == 0x0083 ) { # kuu
                $d = 0x1470;
                if (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1550) {
                    array_pop($cs);
                    array_pop($cs);
                    array_push($cs,0x1585);
                } elseif (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1585) {
                    array_pop($cs);
                } elseif (count($cs) > 0 && $cs[count($cs)-1] == 0x1550) {
                    array_pop($cs);
                    $d = 0x1582;
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
                if ($dot) {$dot=FALSE;; $d++;}
            } # guu
            elseif ( $nc == 0x0394 ) { $d = 0x14A8; } # muu
            elseif ( $nc == 0x02da ) { $d = 0x14C5; } # nuu
            elseif ( $nc == 0x02d9 ) { $d = 0x14F2; } # suu
            elseif ( $nc == 0x00ac ) { $d = 0x14D8; } # luu
            elseif ( $nc == 0x00d4 ) { $d = 0x152B; } # juu
            elseif ( $nc == 0xf000 ) { $d = 0x1558; } # vuu
            elseif ( $nc == 0x00ce ) { # ruu
                $d = 0x1549;
                if (count($cs) != 0 && $cs[count($cs)-1] == 0x1585) { # q+ruu = r+ruu (rruu)
                    array_pop($cs);
                    array_push($cs,0x1550);
                }
            }
            elseif ( $nc == 0x2202 ) { # quu
                $d = 0x1582;
                if (count($cs) != 0 && ($cs[count($cs)-1] == 0x1550 || $cs[count($cs)-1] == 0x1585)) { # r+quu = q+kuu (qquu)
                    array_pop($cs);
                    array_push($cs,0x1585);
                    $d = 0x1470;
                }
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
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == 0x00c1 ) { $d = 0x1674; } # nnguu
            elseif ( $nc == 0x00d2 ) { $d = 0x15A3; } # &uu
            elseif ( $nc == ord('x') ) {  # a
                $d = 0x140a;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e ==  ord('w') ) {
                        if ($aipaitai) {
                        	$d = 0x1401;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # ai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('X') ) { # pa
                $d = 0x1438;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                     if ( $e ==  ord('w') ) {
                        if ($aipaitai) {
                        	$d = 0x142F;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # ai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('b') ) { # ta
                $d = 0x1455;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                     if ( $e ==  ord('w') ) {
                        if ($aipaitai) {
                        	$d = 0x144C;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # ai
                        }
                        $i=$j;
                    }
                } 
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('v') ) { # ka
                $d = 0x1472;
                $k2q = FALSE;
                if ($dot) { array_pop($cs); }
                if (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1550) {
                    array_pop($cs);
                    array_pop($cs);
                    array_push($cs,0x1585);
                } elseif (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1585) {
                    array_pop($cs);
                } elseif (count($cs) > 0 && $cs[count($cs)-1] == 0x1550) {
                    array_pop($cs);
                    $d = 0x1583;
                    $k2q = TRUE;
                }
               $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ($e == ord('w') ) {
                        if ($aipaitai) {
                           $d = $k2q? 0x166f : 0x146B; # kai
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # ka+i
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('Z') ) { # ga
                $d = 0x1490;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e == ord('w') ) {
                        if ($aipaitai) {
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
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('m') ) { # ma
                $d = 0x14AA;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e == ord('w') ) {
                        if ($aipaitai) {
                        	$d = 0x14A3; 
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # mai
                        }
                        $i=$j;
                	}
                }
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('N') ) { # na
                $d = 0x14C7;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e == ord('w') ) {
                        if ($aipaitai) {
                        	$d = 0x14C0;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # nai
                        }
                        $i=$j;
                    }
                } 
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('n') ) { # sa
                $d = 0x14F4;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e == ord('w') ) {
                        if ($aipaitai) {
                        	$d = 0x14ED;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # sai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('M') ) { # la
                $d = 0x14DA;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e == ord('w') ) {
                        if ($aipaitai) {
                        	$d = 0x14D3;
                        } else {
                        	array_push($cs,$d); $d = 0x1403; # lai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('/') ) { # ja
                $d = 0x152d;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e == ord('w') ) {
                        if ($aipaitai) {
                        	$d = 0x1526;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # jai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('?') ) { # va
                $d = 0x1559;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ($e == ord('w') ) {
                        if ($aipaitai) {
                        	$d = 0x1553;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # vai
                        }
                       $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('C') ) { # ra
                $d = 0x154b;
                if ($dot) { array_pop($cs); }
                if (count($cs) != 0 && $cs[count($cs)-1] == 0x1585) { # q+r_ = r+r_ (rr_)
                    array_pop($cs);
                    array_push($cs,0x1550);
                }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e == ord('w') ) {
                        if ($aipaitai) {
                        	$d = 0x1542;
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # rai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE;; $d++;}
            }
           elseif ( $nc == ord('c') ) { # qa
               $precr = FALSE;
               $d = 0x1583;
               if ($dot) { array_pop($cs); }
               if (count($cs) != 0 && ($cs[count($cs)-1] == 0x1550 || $cs[count($cs)-1] == 0x1585)) { # r+qa = q+ka (qqa)
                   array_pop($cs);
                   array_push($cs,0x1585);
                   $d = 0x1472;
                   $precr = TRUE;
                   }
               $j=$i+1;
               if ($j < $l) {
                   $e = mb_substr($text,$j,1);
                   if ($e == 'w') {
                       if ($aipaitai) {
                           if ($precr) {
                               $d = 0x146b; # kai
                           } else {
                               $d = 0x166f; # qai
                           }
                       } else {
                           array_push($cs,$d); 
                           $d = 0x1403 ;
                       }
                       $i=$j;
                   }
               }
               if ($dot) {$dot=FALSE;; $d++;}
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
                    if ( $e ==  ord('w') ) {
                        if ($aipaitai) {
                            if ($precnng) {
                                array_push($cs,0x1596);
                                $d = 0x1489;
                            } else {
                                $d = 0x1670;# ngai
                            }
                        } else {
                        	array_push($cs,$d); $d = 0x1403;  # ngai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('U') ) { # nnga
                $d = 0x1675;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e == ord('w') ) {
                        if ($aipaitai) {
                        	array_push($cs,0x1596); $d = 0x1489;
                        } else {
                        	array_push($cs,0x1675); $d = 0x1403;  # nngai
                        }
                        $i=$j;
                    }
                }
                if ($dot) {$dot=FALSE;; $d++;}
            }
            elseif ( $nc == ord('I') ) { $d = 0x15A4; if ($dot) {$dot=FALSE;; array_pop($cs); $d++;} } # &a
            elseif ( $nc == 0x02db ) { $d = 0x1439; } # paa
            elseif ( $nc == 0x222b ) { $d = 0x1456; } # taa
            elseif ( $nc == 0x221a ) { # kaa
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
            elseif ( $nc == 0x00b8 ) { $d = 0x1491; } # gaa
            elseif ( $nc == 0x03bc ) { $d = 0x14AB; } # maa
            elseif ( $nc == 0x02dc || $nc == 0x0098 ) { $d = 0x14C8; } # naa
            elseif ( $nc == 0x00f1 ) { $d = 0x14F5; } # saa
            elseif ( $nc == 0x00c2 ) { $d = 0x14DB; } # laa
            elseif ( $nc == 0x00f7 ) { $d = 0x152E; } # jaa
            elseif ( $nc == 0x00bf ) { $d = 0x155A; } # vaa
            elseif ( $nc == 0x00c7 ) { # raa
                $d = 0x154C;
                if (count($cs) != 0 && $cs[count($cs)-1] == 0x1585) { # q+rii = r+rii (rrii)
                    array_pop($cs);
                    array_push($cs,0x1550);
                }
            }
            elseif ( $nc == 0x00e7 ) { # qaa
                $d = 0x1584;
                if (count($cs) != 0 && ($cs[count($cs)-1] == 0x1550 || $cs[count($cs)-1] == 0x1585)) { # r+qaa = q+kaa (qqaa)
                    array_pop($cs);
                    array_push($cs,0x1585);
                    $d = 0x1473;
                }
            }
            elseif ( $nc == 0x03a9 ) { # ngaa
                $d = 0x1594;
                if (count($cs) != 0) {
                    $e = $cs[count($cs)-1];
                    if ($e == 0x14d0 || $e == 0x1595 ) { # n, ng
                        array_pop($cs);
                        $d = 0x1676; # nngaa
                    }
                }
            } # ngaa
            elseif ( $nc == 0x00dc ) { $d = 0x1676; } # nngaa
            elseif ( $nc == 0x02c6 || $nc == 0x0088 ) { $d = 0x15A5; } # &aa
            elseif ( $nc == ord('2') ) { $d = 0x1449; }
            elseif ( $nc == ord('5') ) { $d = 0x1466; }
            elseif ( $nc == ord('4') ) { $d = 0x1483; }
            elseif ( $nc == ord('[') ) { $d = 0x14A1; }
            elseif ( $nc == ord('7') ) { $d = 0x14BB; }
            elseif ( $nc == ord('8') ) { $d = 0x14D0; }
            elseif ( $nc == ord('{') ) { $d = 0x1505; }
            elseif ( $nc == ord('9') ) { $d = 0x14EA; }
            elseif ( $nc == ord('0') ) { $d = 0x153E; }
            elseif ( $nc == ord('=') ) { $d = 0x155D; }
            elseif ( $nc == ord('6') ) { $d = 0x1585; }
            elseif ( $nc == ord('P') ) { $d = 0x15A6; }
            elseif ( $nc == ord('B') ) { $d = 0x157C; }
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
            elseif ( $nc == ord('|') || $nc == ord('}') || $nc == ord('`') || $nc == ord('+') || $nc == ord(']') || $nc == ord('~') ) {
                $d=ord($c); 
                $dot = TRUE;
            }
            else {
                $dot=FALSE;;
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
