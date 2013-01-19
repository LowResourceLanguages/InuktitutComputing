<?php

require_once 'lib/utf8.php';

class Prosyl {

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
            0x1555 => '=', # vi
            0x1546 => 'E', # ri
            0x157F => 'e', # qi
            0x158F => 'q', # Ni (ngi)
            0x1671 => '8q', # Xi (nngi = n ngi)
            0x15A0 => 'O', # &i
            0x1404 => '`w', # ii
            0x1432 => '`W', # pii
            0x144F => '`t', # tii
            0x146E => '`r', # kii
            0x148C => '`Q', # gii
            0x14A6 => '`u', # mii
            0x14C3 => '~i', # nii
            0x14F0 => '<y', # sii
            0x14D6 => '~o', # lii
            0x1529 => '>p', # jii
            0x1556 => '`=', # vii
            0x1547 => '~E', # rii
            0x1580 => '`e', # qii
            0x1590 => '<q', # Nii
            0x1672 => '8<q', # Xii nngii
            0x15A1 => '~O', # &ii
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
            0x1673 => '8a', # Xu
            0x15A2 => 'L', # &u
            0x1406 => '>s', # uu
            0x1434 => '>S', # puu
            0x1451 => '>g', # tuu
            0x1470 => ']f', # kuu
            0x148E => ']A', # guu
            0x14A8 => '<j', # muu
            0x14C5 => '~k', # nuu
            0x14F2 => '<h', # suu
            0x14D8 => '~l', # luu
            0x152B => '<J', # juu
            0x1558 => '>K', # vuu
            0x1549 => '>D', # ruu
            0x1582 => '<d', # quu
            0x1592 => '<a', # Nuu
            0x1674 => '8<a', # Xuu
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
            0x1675 => '8z', # Xa
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
            0x140B => '<x', # aa
            0x1439 => '<X', # paa
            0x1456 => ']b', # taa
            0x1473 => '>v', # kaa
            0x1491 => '>Z', # gaa
            0x14AB => '>m', # maa
            0x14C8 => '~N', # naa
            0x14F5 => '>n', # saa
            0x14DB => '~M', # laa
            0x152E => '>/', # jaa
            0x155A => '<?', # vaa
            0x154C => '>C', # raa
            0x1584 => '`c', # qaa
            0x1594 => '<z', # Naa
            0x1676 => '8<z', # Xaa
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
            0x155D => '}', # v
            0x1550 => 0x33, # r
            0x1585 => 0x36, # q
            0x1595 => 0x31, # N
            0x1596 => array(0x38,0x31), # X
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
            0x2F => 'F', # /
            0x21 => 'U', # !
            0x24 => 'R', # $
            0x3D => '+', # =
            0x25 => '-', # %
            0x2B => 'T', # +
            0x5F => 'Y', # _
            0x7D => '\\', # }
            0x2D => '_', # -
            0x7B => '|', # {
            0x22 => 0x22, # "
            0x27 => 0x27, # '
            0x2C => 0x2C, # ,
            0x2E => 0x2E, # .
            0x3A => 0x3A, # :
            0x3B => 0x3B, # ;
);

public static function unicodeToLegacy ($text) {
	$logger = Logger::getLogger('Prosyl.unicodeToLegacy');
	$transcodedText = '';
	$cnt = mb_strlen($text);
	$logger->debug("\$cnt= '$cnt'");
	$chars = str_split($text);
	$i = 0;
	while ($i < $cnt) {
		$c = mb_substr($text,$i++,1);
		$logger->debug("\$c= '$c'");
		$nc = utf8::utf8_to_numeric($c);
		$lc = self::$unicode_to_legacy_codes[$nc];
		if ( $nc <= 0x20 )
			$transcodedText .= $c;
		elseif ( $lc==NULL )
			$transcodedText .= 'V'; # ?
		elseif ( is_numeric($lc) ) 
			$transcodedText .= utf8::numeric_to_utf8($lc);
		elseif ( is_array($lc) ) 
			$transcodedText .= implode('',array_map('utf8::numeric_to_utf8',$lc));
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
                if ($dot) {$dot=FALSE; $d++;}
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
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('u') ) { $d = 0x14A5; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # mi
            elseif ( $nc == ord('i') ) { $d = 0x14C2; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # ni
            elseif ( $nc == ord('y') ) { $d = 0x14EF; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # si
            elseif ( $nc == ord('o') ) { $d = 0x14D5; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # li
            elseif ( $nc == ord('p') ) { $d = 0x1528; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # ji
            elseif ( $nc == ord('=') ) { $d = 0x1555; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # vi
            elseif ( $nc == ord('E') ) { # ri
                $d = 0x1546;
                if ($dot) {$dot=FALSE; array_pop($cs); $d++;} 
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
                if ($dot) {$dot=FALSE; $d++;}
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
                if ($dot) {$dot=FALSE; $d++;}
                }
            elseif ( $nc == ord('O') ) { $d = 0x15A0; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # &i
            
                        
            elseif ( $nc == ord('s') ) { $d = 0x1405; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # u
            elseif ( $nc == ord('S') ) { $d = 0x1433; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # pu
            elseif ( $nc == ord('g') ) { $d = 0x1450; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # tu
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
                if ($dot) {$dot=FALSE; array_pop($cs); $d++;}; # ruu
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
                if ($dot) {$dot=FALSE; $d++;}
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
            elseif ( $nc == ord('L') ) { $d = 0x15A2; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # &u
            
            
            elseif ( $nc == ord('x') ) {  # a
                $d = 0x140a;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ( $e ==  'w' ) {
                        if ($aipaitai) {
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
                     if ( $e ==  'w' ) {
                        if ($aipaitai) {
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
                     if ( $e ==  'w' ) {
                        if ($aipaitai) {
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
                if (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1550) {
                    array_pop($cs);
                    array_pop($cs);
                    array_push($cs,0x1585); # rrka > qqa
                } elseif (count($cs) > 1 && $cs[count($cs)-1] == 0x1550 && $cs[count($cs)-2] == 0x1585) {
                    array_pop($cs); # rqka > rka (???)
                } elseif (count($cs) > 0 && $cs[count($cs)-1] == 0x1550) {
                    array_pop($cs);
                    $d = 0x1583; # rka > qa
                    $k2q = TRUE;
                }
               $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ($e == ord('w')) {
                        if ($aipaitai) {
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
                if ($dot) {$dot=FALSE; $d++;}
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
                if ($dot) {$dot=FALSE; $d++;}
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
                if ($dot) {$dot=FALSE; $d++;}
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
                if ($dot) {$dot=FALSE; $d++;}
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
                if ($dot) {$dot=FALSE; $d++;}
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
                if ($dot) {$dot=FALSE; $d++;}
            }
            elseif ( $nc == ord('?') ) { # va
                $d = 0x1559;
                if ($dot) { array_pop($cs); }
                $j=$i+1;
                if ($j < $l) {
                    $e = mb_substr($text,$j,1);
                    if ($e == ord('w')) {
                        if ($aipaitai) {
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
                if ($dot) {$dot=FALSE; $d++;}
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
                   if ($e == ord('w')) {
                       if ($aipaitai) {
                           if ($precr) {
                               $d = 0x146b; # kai
                           } else {
                               $d = 0x166f ;# qai
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
                    if ( $e ==  'w' ) {
                        if ($aipaitai) {
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
            elseif ( $nc == ord('I') ) { $d = 0x15A4; if ($dot) {$dot=FALSE; array_pop($cs); $d++;} } # &a
            

            elseif ( $nc == ord('2') ) { $d = 0x1449; } # p
            elseif ( $nc == ord('5') ) { $d = 0x1466; } # t
            elseif ( $nc == ord('4') ) { $d = 0x1483; } # k
            elseif ( $nc == ord('[') ) { $d = 0x14A1; } # g
            elseif ( $nc == ord('7') ) { $d = 0x14BB; } # m
            elseif ( $nc == ord('8') ) { $d = 0x14D0; } # n
            elseif ( $nc == ord('{') ) { $d = 0x1505; } # s
            elseif ( $nc == ord('9') ) { $d = 0x14EA; } # l
            elseif ( $nc == ord('0') ) { $d = 0x153E; } # j
            elseif ( $nc == ord('=') ) { $d = 0x155D; } # v
            elseif ( $nc == ord('3') ) {  $d = 0x1550; } # r
            elseif ( $nc == ord('6') ) { $d = 0x1585; } # q
            elseif ( $nc == ord('1') ) { # ng
                $d = 0x1595;
                if (count($cs) != 0 && ($cs[count($cs)-1] == 0x14d0 || $cs[count($cs)-1] == 0x1595)) {
                    array_pop($cs);
                    $d=0x1596; # n ou ng + ng > nng 
                }
            }
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
            elseif ( $nc == ord('+') ) { $d = ord('='); }
            elseif ( $nc == ord('-') ) { $d = ord('%'); }
            elseif ( $nc == ord('F') ) { $d = ord('/'); }
            elseif ( $nc == ord('G') ) { $d = ord('('); }
            elseif ( $nc == ord('H') ) { $d = ord(')'); }
            elseif ( $nc == ord('R') ) { $d = ord('$'); }
            elseif ( $nc == ord('T') ) { $d = ord('+'); }
            elseif ( $nc == ord('U') ) { $d = ord('!'); }
            elseif ( $nc == ord('V') ) { $d = ord('?'); }
            elseif ( $nc == ord('Y') ) { $d = ord('_'); }
            elseif ( $nc == ord('\\') ) { $d = ord('}'); }
            elseif ( $nc == ord('_') ) { $d = ord('-'); }
            elseif ( $nc == ord('|') ) { $d = ord('{'); }
            elseif ( $nc == 0x2018 ) { $d = ord('\''); }
            elseif ( $nc == 0x2019 ) { $d = ord('\''); }
            elseif ( $nc == 0x201C ) { $d = ord('"'); }
            elseif ( $nc == 0x201D ) { $d = ord('"'); }
            elseif ( $nc == ord('<') || $nc == ord('>') || $nc == ord('`') || $nc == ord(']') || $nc == ord('~') ) {
                $d=ord($c); 
                $dot = TRUE;
            }
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
