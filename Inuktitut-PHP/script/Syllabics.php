<?php

require_once 'lib/utf8.php';
require_once "lib/log4php/Logger.php";

class Syllabics {

/*
 * aipaitaiMode
 *   '1' : all a+i replaced with aipai
 *   '0' : all aipai replaced with a+i
 */
public static function latinAlphabetToUnicode ( $text, $aipaitaiMode ) {
	if ($aipaitaiMode=='1') {
		$aipaitai = TRUE;
	}
	else {
		$aipaitai = FALSE;
	}
	$preparedText = self::_prepare_string($text);
	$s             = str_split($preparedText);
	$i             = 0;
	$l             = count($s);
	$d = NULL;
	$sb = array();
	while ( $i < $l ) {
		$c = $s[$i];
		if ( $c === 'i' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$d = 0x1404;
				}    # ii
				else { $d = 0x1403; $i--; }
			}
			else {
				$d = 0x1403;
				$i--;
			}
		}
		elseif ( $c === 'u' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'u' ) {
					$d = 0x1406;
				}    # uu
				else { $d = 0x1405; $i--; }

			}
			else {
				$d = 0x1405;
				$i--;
			}
		}
		elseif ( $c === 'A' ) {
			if ($aipaitai) { $d = 0x1401; }    # ai
			else { array_push($sb,0x140a); $d = 0x1403; }    # ai
		}
		elseif ( $c === 'a' ) {                               # a
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'a' ) {
					$d = 0x140b;
				}                                           # aa
				else { $d = 0x140A; $i--; }
			}
			else {
				$d = 0x140A;
				$i--;
			}
		}
		elseif ( $c === 'b' || $c === 'p' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) {
							$d = 0x1432;
						}    # pii
						else { $d = 0x1431; $i--; }    # pi
					}
					else {
						$d = 0x1431;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) {
							$d = 0x1434;
						}    # puu
						else { $d = 0x1433; $i--; }    # pu
					}
					else {
						$d = 0x1433;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x142f;
					}    # pai
					else { array_push($sb,0x1438); $d = 0x1403; }    # pai
				}
				elseif ( $e === 'a' ) {                               # pa
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) {
							$d = 0x1439;
						}                                           # paa
						else { $d = 0x1438; $i--; }             # pa
					}
					else {
						$d = 0x1438;                            # pa
						$i--;
					}
				}
				else { $d = 0x1449; $i--; }                     # p
			}
			else {
				$i--;
				$d = 0x1449;                                    # p
			}
		}
		elseif ( $c === 'd' || $c === 't' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x144f; }       # tii
						else { $d = 0x144e; $i--; }             # ti
					}
					else {
						$d = 0x144e;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x1451; }       # tuu
						else { $d = 0x1450; $i--; }             # tu
					}
					else {
						$d = 0x1450;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x144c;
					}    # tai
					else { array_push($sb,0x1455); $d = 0x1403; }    # tai
				}
				elseif ( $e === 'a' ) {                               # ta
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x1456; }       # taa
						else { $d = 0x1455; $i--; }             # ta
					}
					else {
						$d = 0x1455;                            # ta
						$i--;
					}
				}
				else { $d = 0x1466; $i--; }                     # t
			}
			else {
				$i--;
				$d = 0x1466;                                    # t
			}
		}
		elseif ( $c === 'k' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x146e; }       # kii
						else { $d = 0x146d; $i--; }             # ki
					}
					else {
						$d = 0x146d;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x1470; }       # kuu
						else { $d = 0x146f; $i--; }             # ku
					}
					else {
						$d = 0x146f;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x146b;
					}    # kai
					else { array_push($sb,0x1472); $d = 0x1403; }    # kai
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x1473; }       # kaa
						else { $d = 0x1472; $i--; }             # ka
					}
					else {
						$d = 0x1472;                            # ka
						$i--;
					}
				}
				else { $d = 0x1483; $i--; }                     # k
			}
			else {
				$i--;
				$d = 0x1483;                                    # k
			}
		}
		elseif ( $c === 'g' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x148c; }       # gii
						else { $d = 0x148b; $i--; }             # gi
					}
					else {
						$d = 0x148b;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x148e; }       # guu
						else { $d = 0x148d; $i--; }             # gu
					}
					else {
						$d = 0x148d;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x1489;
					}    # gai
					else { array_push($sb,0x1490); $d = 0x1403; }    # gai
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x1491; }       # gaa
						else { $d = 0x1490; $i--; }             # ga
					}
					else {
						$d = 0x1490;                            # ga
						$i--;
					}
				}
				else { $d = 0x14a1; $i--; }                     # g
			}
			else {
				$i--;
				$d = 0x14a1;                                    # g
			}
		}
		elseif ( $c === 'm' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x14a6; }       # mii
						else { $d = 0x14a5; $i--; }             # mi
					}
					else {
						$d = 0x14a5;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x14a8; }       # muu
						else { $d = 0x14a7; $i--; }             # mu
					}
					else {
						$d = 0x14a7;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x14a3;
					}    # mai
					else { array_push($sb,0x14aa); $d = 0x1403; }    # mai
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x14ab; }       #maa
						else { $d = 0x14aa; $i--; }             # ma
					}
					else {
						$d = 0x14aa;                            # ma
						$i--;
					}
				}
				else { $d = 0x14bb; $i--; }                     # m
			}
			else {
				$i--;
				$d = 0x14bb;                                    # m
			}
		}
		elseif ( $c === 'n' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x14c3; }       # nii
						else { $d = 0x14c2; $i--; }             # ni
					}
					else {
						$d = 0x14c2;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x14c5; }       # nuu
						else { $d = 0x14c4; $i--; }             # nu
					}
					else {
						$d = 0x14c4;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x14c0;
					}    # nai
					else { array_push($sb,0x14c7); $d = 0x1403; }    # nai
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x14c8; }       #naa
						else { $d = 0x14c7; $i--; }             # na
					}
					else {
						$d = 0x14c7;                            # na
						$i--;
					}
				}
				else { $d = 0x14d0; $i--; }                     # n
			}
			else {
				$i--;
				$d = 0x14d0;                                    # n
			}
		}
		elseif ( $c === 's' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x14f0; }       # sii
						else { $d = 0x14ef; $i--; }             # si
					}
					else {
						$d = 0x14ef;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x14f2; }       # suu
						else { $d = 0x14f1; $i--; }             # su
					}
					else {
						$d = 0x14f1;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x14ed;
					}    # sai
					else { array_push($sb,0x14f4); $d = 0x1403; }    # sai
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x14f5; }       #saa
						else { $d = 0x14f4; $i--; }             # sa
					}
					else {
						$d = 0x14f4;                            # sa
						$i--;
					}
				}
				else { $d = 0x1505; $i--; }                     # s
			}
			else {
				$i--;
				$d = 0x1505;                                    # s
			}
		}
		elseif ( $c === 'l' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x14d6; }       # lii
						else { $d = 0x14d5; $i--; }             # li
					}
					else {
						$d = 0x14d5;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x14d8; }       # luu
						else { $d = 0x14d7; $i--; }             # lu
					}
					else {
						$d = 0x14d7;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x14d3;
					}    # lai
					else { array_push($sb,0x14da); $d = 0x1403; }    # lai
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x14db; }       #laa
						else { $d = 0x14da; $i--; }             # la
					}
					else {
						$d = 0x14da;                            # la
						$i--;
					}
				}
				else { $d = 0x14ea; $i--; }                     # l
			}
			else {
				$i--;
				$d = 0x14ea;                                    # l
			}
		}
		elseif ( $c === 'j' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x1529; }       # jii
						else { $d = 0x1528; $i--; }             # ji
					}
					else {
						$d = 0x1528;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x152b; }       # juu
						else { $d = 0x152a; $i--; }             # ju
					}
					else {
						$d = 0x152a;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x1526;
					}    # jai
					else { array_push($sb,0x152d); $d = 0x1403; }    # jai
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x152e; }       #jaa
						else { $d = 0x152d; $i--; }             # ja
					}
					else {
						$d = 0x152d;                            # ja
						$i--;
					}
				}
				else { $d = 0x153e; $i--; }                     # j
			}
			else {
				$i--;
				$d = 0x153e;                                    # j
			}
		}
		elseif ( $c === 'v' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x1556; }       # vii
						else { $d = 0x1555; $i--; }             # vi
					}
					else {
						$d = 0x1555;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x1558; }       # vuu
						else { $d = 0x1557; $i--; }             # vu
					}
					else {
						$d = 0x1557;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x1553;
					}    # nai
					else { array_push($sb,0x1559); $d = 0x1403; }    # vai
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x155a; }       #vaa
						else { $d = 0x1559; $i--; }             # va
					}
					else {
						$d = 0x1559;                            # va
						$i--;
					}
				}
				else { $d = 0x155d; $i--; }                     # v
			}
			else {
				$i--;
				$d = 0x155d;                                    # v
			}
		}
		elseif ( $c === 'r' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x1547; }       # rii
						else { $d = 0x1546; $i--; }             # ri
					}
					else {
						$d = 0x1546;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x1549; }       # ruu
						else { $d = 0x1548; $i--; }             # ru
					}
					else {
						$d = 0x1548;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x1542;
					}    # nai
					else { array_push($sb,0x154b); $d = 0x1403; }    # nai
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x154c; }       #raa
						else { $d = 0x154b; $i--; }             # ra
					}
					else {
						$d = 0x154b;                            # ra
						$i--;
					}
				}
				else { $d = 0x1550; $i--; }                     # r
			}
			else {
				$i--;
				$d = 0x1550;                                    # r
			}
		}
		elseif ( $c === 'q' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x1580; }       # qii
						else { $d = 0x157f; $i--; }             # qi
					}
					else {
						$d = 0x157f;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x1582; }       # quu
						else { $d = 0x1581; $i--; }             # qu
					}
					else {
						$d = 0x1581;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x166f;
					}    # nai
					else { array_push($sb,0x1583); $d = 0x1403; }    # nai
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x1584; }       #qaa
						else { $d = 0x1583; $i--; }             # qa
					}
					else {
						$d = 0x1583;                            # qa
						$i--;
					}
				}
				else { $d = 0x1585; $i--; }                     # q
			}
			else {
				$i--;
				$d = 0x1585;                                    # q
			}
		}
		elseif ( $c === '&' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x15a1; }       # &ii
						else { $d = 0x15a0; $i--; }             # &i
					}
					else {
						$d = 0x15a0;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x15a3; }       # &uu
						else { $d = 0x15a2; $i--; }             # &u
					}
					else {
						$d = 0x15a2;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					array_push($sb,0x15a4);
					$d = 0x1403;
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x15a5; }       #&aa
						else { $d = 0x15a4; $i--; }             # &a
					}
					else {
						$d = 0x15a4;                            # &a
						$i--;
					}
				}
				else { $d = 0x15a6; $i--; }                     # &
			}
			else {
				$i--;
				$d = 0x15a6;                                    # &
			}
		}
		elseif ( $c === 'N' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x1590; }       # ngii
						else { $d = 0x158f; $i--; }             # ngi
					}
					else {
						$d = 0x158f;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x1592; }       # nguu
						else { $d = 0x1591; $i--; }             # ngu
					}
					else {
						$d = 0x1591;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x1670;
					}    # nai
					else { array_push($sb,0x1593); $d = 0x1403; }    # ngai
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x1594; }       #ngaa
						else { $d = 0x1593; $i--; }             # nga
					}
					else {
						$d = 0x1593;                            # nga
						$i--;
					}
				}
				else { $d = 0x1595; $i--; }                     # ng
			}
			else {
				$i--;
				$d = 0x1595;                                    # ng
			}
		}
		elseif ( $c === 'X' ) {
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x1672; }       # nngii
						else { $d = 0x1671; $i--; }             # nngi
					}
					else {
						$d = 0x1671;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x1674; }       # nnguu
						else { $d = 0x1673; $i--; }             # nngu
					}
					else {
						$d = 0x1673;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						array_push($sb,0x1596);
						$d = 0x1489;
					} else {
						array_push($sb,0x1675);
						$d = 0x1403;
					}
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x1676; }       #nngaa
						else { $d = 0x1675; $i--; }             # nnga
					}
					else {
						$d = 0x1675;                            # nnga
						$i--;
					}
				}
				else { $d = 0x1596; $i--; }                     # nng
			}
			else {
				$i--;
				$d = 0x1596;                                    # nng
			}
		}

		elseif ($c === 'H') { $d = 0x157C; } # Nunavut H
		#            elseif ($c === 'b') { $d = 0x15AF; } # Aivilik B
		elseif ($c === 'h') { # Nunavik h
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) { $d = 0x1576; }       # hii
						else { $d = 0x1575; $i--; }             # hi
					}
					else {
						$d = 0x1575;
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) { $d = 0x1578; }       # huu
						else { $d = 0x1577; $i--; }             # hu
					}
					else {
						$d = 0x1577;
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x1574;
					}    # nai
					else { array_push($sb,0x1579); $d = 0x1403; }    # hai
				}
				elseif ( $e === 'a' ) {                               #
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) { $d = 0x157a; }       #haa
						else { $d = 0x1579; $i--; }             # ha
					}
					else {
						$d = 0x1579;
						$i--;
					}
				}
				else { $d = 0x157B; $i--; }                     # h
			}
			else {
				$i--;
				$d = 0x157B;                                    # h
			}
		}
		elseif ( $c === 'Q' ) {                                       #qq
			array_push($sb,0x1585);
			$i++;
			if ( $i < $l ) {
				$e = $s[$i];
				if ( $e === 'i' ) {
					$d = 0x146d;
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'i' ) {
							$d = 0x146e;
						}    # qqii = q+kii
						else { $i--; }    # qqi = q+ki
					}
					else {
						$i--;
					}
				}
				elseif ( $e === 'u' ) {
					$d = 0x146f;
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'u' ) {
							$d = 0x1470;
						}    # qquu = q+kuu
						else { $i--; }    # qqu = q+ku
					}
					else {
						$i--;
					}
				}
				elseif ( $e === 'A' ) {
					if ($aipaitai) {
						$d = 0x146b;
					}    # nai
					else { array_push($sb,0x1472); $d = 0x1403; }    # nai
				}
				elseif ( $e === 'a' ) {
					$d = 0x1472;
					$i++;
					if ( $i < $l ) {
						$e = $s[$i];
						if ( $e === 'a' ) {
							$d = 0x1473;
						}    # qqaa = q+kaa
						else { $i--; }    # qqa = q+ka
					}
					else {

						# qqa = q+ka
						$i--;
					}
				}
				else {                    # shouldn't happen
					$d = 0x1585;
					$i--;
				}    # qq
			}
			else {    # shouldn't happen
				$i--;
				$d = 0x1585;
				$sb = substr( $sb, 0, length($sb) - 1 );
			}
		}
		elseif ( $c === 'H' ) { $d = 0x157c; }
		else { $d = ord($c); }
		$i++;
		array_push($sb,$d);
	}
	$out = '';
	$out = utf8::numeric_to_utf8($sb);
//	foreach ($sb as $n) {
//		$str = utf8::numeric_to_utf8($n);
//		$out .= $str;
//	}
	return $out;
//	return implode('',array_map('utf8::numeric_to_utf8',$sb));
}

public static function is_syllabic_word ( $word )
{
  	$logger = Logger::getLogger('Syllabics.is_syllabic_word');
	$i = 0;
	$long = mb_strlen($word);
	$logger->debug("\$word= '$word' ($long chars)");
	while ( $i < $long)
	{
		$c = mb_substr($word, $i++, 1);
		$num_c = utf8::utf8_to_numeric($c);
		if ( ! array_key_exists($num_c, self::$unicodes) )
			return false;
	}
	return true;
}


static $unicodes = array(
	0x1403 => 'i',
	0x1431 => 'pi',
	0x144E => 'ti',
	0x146D => 'ki',
	0x148B => 'gi',
	0x14A5 => 'mi',
	0x14C2 => 'ni',
	0x14EF => 'si',
	0x14D5 => 'li',
	0x1528 => 'ji',
	0x1555 => 'vi',
	0x1546 => 'ri',
	0x157F => 'qi',
	0x158F => 'ngi',
	0x1671 => 'nngi',
	0x15A0 => '&i',
	0x1405 => 'u',
	0x1433 => 'pu',
	0x1450 => 'tu',
	0x146F => 'ku',
	0x148D => 'gu',
	0x14A7 => 'mu',
	0x14C4 => 'nu',
	0x14F1 => 'su',
	0x14D7 => 'lu',
	0x152A => 'ju',
	0x1557 => 'vu',
	0x1548 => 'ru',
	0x1581 => 'qu',
	0x1591 => 'ngu',
	0x1673 => 'nngu',
	0x15A2 => '&u',
	0x140A => 'a',
	0x1438 => 'pa',
	0x1455 => 'ta',
	0x1472 => 'ka',
	0x1490 => 'ga',
	0x14AA => 'ma',
	0x14C7 => 'na',
	0x14F4 => 'sa',
	0x14DA => 'la',
	0x152D => 'ja',
	0x1559 => 'va',
	0x154B => 'ra',
	0x1583 => 'qa',
	0x1593 => 'nga',
	0x1675 => 'nnga',
	0x15A4 => '&a',
	0x1404 => 'ii',
	0x1432 => 'pii',
	0x144F => 'tii',
	0x146E => 'kii',
	0x148C => 'gii',
	0x14A6 => 'mii',
	0x14C3 => 'nii',
	0x14F0 => 'sii',
	0x14D6 => 'lii',
	0x1529 => 'jii',
	0x1556 => 'vii',
	0x1547 => 'rii',
	0x1580 => 'qii',
	0x1590 => 'ngii',
	0x1672 => 'nngii',
	0x15A1 => '&ii',
	0x1406 => 'uu',
	0x1434 => 'puu',
	0x1451 => 'tuu',
	0x1470 => 'kuu',
	0x148E => 'guu',
	0x14A8 => 'muu',
	0x14C5 => 'nuu',
	0x14F2 => 'suu',
	0x14D8 => 'luu',
	0x152B => 'juu',
	0x1558 => 'vuu',
	0x1549 => 'ruu',
	0x1582 => 'quu',
	0x1592 => 'nguu',
	0x1674 => 'nnguu',
	0x15A3 => '&uu',
	0x140B => 'aa',
	0x1439 => 'paa',
	0x1456 => 'taa',
	0x1473 => 'kaa',
	0x1491 => 'gaa',
	0x14AB => 'maa',
	0x14C8 => 'naa',
	0x14F5 => 'saa',
	0x14DB => 'laa',
	0x152E => 'jaa',
	0x155A => 'vaa',
	0x154C => 'raa',
	0x1584 => 'qaa',
	0x1594 => 'ngaa',
	0x1676 => 'nngaa',
	0x15A5 => '&aa',
	0x1449 => 'p',
	0x1466 => 't',
	0x1483 => 'k',
	0x14A1 => 'g',
	0x14BB => 'm',
	0x14D0 => 'n',
	0x1505 => 's',
	0x14EA => 'l',
	0x153E => 'j',
	0x155D => 'v',
	0x1550 => 'r',
	0x1585 => 'q',
	0x1595 => 'ng',
	0x1596 => 'nng',
	0x15A6 => '&',
	0x157C => 'H',
	0x1574 => 'hai',
	0x1575 => 'hi',
	0x1576 => 'hii',
	0x1577 => 'hu',
	0x1578 => 'huu',
	0x1579 => 'ha',
	0x157A => 'haa',
	0x157B => 'h',
	0x15AF => 'b',
	0x1401 => 'ai',
	0x142f => 'pai',
	0x144c => 'tai',
	0x146b => 'kai',
	0x1489 => 'gai',
	0x14a3 => 'mai',
	0x14c0 => 'nai',
	0x14ed => 'sai',
	0x14d3 => 'lai',
	0x1526 => 'jai',
	0x1553 => 'vai',
	0x1543 => 'rai',
	0x1542 => 'rai',
	0x166f => 'qai',
	0x1670 => 'ngai'
);

public static function get_alphabet_latin_de_unicode ($nc) {
	return self::$unicodes[$nc];
}

public static function unicodeToLatinAlphabet ($text) {
	$i      = 0;
	$l      = mb_strlen($text,'UTF-8');
	$sb = array();
	while ( $i < $l ) {
		$c = mb_substr($text,$i,1,'UTF-8');
		$nc = utf8::utf8_to_numeric($c);
		$d = self::get_alphabet_latin_de_unicode($nc);
		if ( $d==NULL ) $d = $c;
		if ( $nc == 0x1550 ) {    # r
			$i++;
			if ( $i < $l ) {
				$e = mb_substr($text,$i,1,'UTF-8');
				$ne = utf8::utf8_to_numeric($e);
				# r+k. > q.
				if    ( $ne == 0x146D ) { $d = 'qi'; }
				elseif ( $ne == 0x146F ) { $d = 'qu'; }
				elseif ( $ne == 0x1472 ) { $d = 'qa'; }
				elseif ( $ne == 0x146E ) { $d = 'qii'; }
				elseif ( $ne == 0x1470 ) { $d = 'quu'; }
				elseif ( $ne == 0x1473 ) { $d = 'qaa'; }
				elseif ( $ne == 0x146B ) { $d = 'qai'; }
				else { $d = 'r'; $i--; }
			}
			else {
				$i--;
				$d = 'r';
			}
		}
		elseif ( $nc == 0x1585 ) {    # q
			$i++;
			if ( $i < $l ) {
				$e = mb_substr($text,$i,1,'UTF-8');
				$ne = utf8::utf8_to_numeric($e);
				# q+k. > qq.
				if    ( $ne == 0x146D ) { $d = 'qqi'; }
				elseif ( $ne == 0x146F ) { $d = 'qqu'; }
				elseif ( $ne == 0x1472 ) { $d = 'qqa'; }
				elseif ( $ne == 0x146E ) { $d = 'qqii'; }
				elseif ( $ne == 0x1470 ) { $d = 'qquu'; }
				elseif ( $ne == 0x1473 ) { $d = 'qqaa'; }
				elseif ( $ne == 0x146B ) { $d = 'qqai'; }
				else { $d = 'q'; $i--; }
			}
			else {
				$i--;
				$d = 'q';
			}
		}
		elseif ( $nc == 0x14D0 ) {    # n
			$i++;
			if ( $i < $l ) {
				$e = mb_substr($text,$i,1,'UTF-8');
				$ne = utf8::utf8_to_numeric($e);
				# n+g. > ng.
				# n+ng. > nng.
				if    ( $ne == 0x148B ) { $d = 'ngi'; }
				elseif ( $ne == 0x148D ) { $d = 'ngu'; }
				elseif ( $ne == 0x1490 ) { $d = 'nga'; }
				elseif ( $ne == 0x148C ) { $d = 'ngii'; }
				elseif ( $ne == 0x148E ) { $d = 'nguu'; }
				elseif ( $ne == 0x1491 ) { $d = 'ngaa'; }
				elseif ( $ne == 0x1489 ) { $d = 'ngai'; }
				elseif ( $ne == 0x158F ) { $d = 'nngi'; }
				elseif ( $ne == 0x1591 ) { $d = 'nngu'; }
				elseif ( $ne == 0x1593 ) { $d = 'nnga'; }
				elseif ( $ne == 0x1590 ) { $d = 'nngii'; }
				elseif ( $ne == 0x1592 ) { $d = 'nnguu'; }
				elseif ( $ne == 0x1594 ) { $d = 'nngaa'; }
				elseif ( $ne == 0x1670 ) { $d = 'nngai'; }
				else { $d = 'n'; $i--; }
			}
			else {
				$i--;
				$d = 'n';
			}
		}
		elseif ( $nc == 0x1595 ) {    # ng
			$i++;
			if ( $i < $l ) {
				$e = mb_substr($text,$i,1,'UTF-8');
				$ne = utf8::utf8_to_numeric($e);
				# ng+g. > ng.
				# ng+ng. > nng.
				if    ( $ne == 0x148B ) { $d = 'ngi'; }
				elseif ( $ne == 0x148D ) { $d = 'ngu'; }
				elseif ( $ne == 0x1490 ) { $d = 'nga'; }
				elseif ( $ne == 0x148C ) { $d = 'ngii'; }
				elseif ( $ne == 0x148E ) { $d = 'nguu'; }
				elseif ( $ne == 0x1491 ) { $d = 'ngaa'; }
				elseif ( $ne == 0x1489 ) { $d = 'ngai'; }
				elseif ( $ne == 0x158F ) { $d = 'nngi'; }
				elseif ( $ne == 0x1591 ) { $d = 'nngu'; }
				elseif ( $ne == 0x1593 ) { $d = 'nnga'; }
				elseif ( $ne == 0x1590 ) { $d = 'nngii'; }
				elseif ( $ne == 0x1592 ) { $d = 'nnguu'; }
				elseif ( $ne == 0x1594 ) { $d = 'nngaa'; }
				elseif ( $ne == 0x1670 ) { $d = 'nngai'; }
				else { $d = 'ng'; $i--; }
			}
			else {
				$i--;
				$d = 'ng';
			}
		}
		elseif ( $nc == 0x1596 ) {    # nng
			$i++;
			if ( $i < $l ) {
				$e = mb_substr($text,$i,1,'UTF-8');
				$ne = utf8::utf8_to_numeric($e);
				# // nng+g. > nng.
				if    ( $ne == 0x148B ) { $d = 'nngi'; }
				elseif ( $ne == 0x148D ) { $d = 'nngu'; }
				elseif ( $ne == 0x1490 ) { $d = 'nnga'; }
				elseif ( $ne == 0x148C ) { $d = 'nngii'; }
				elseif ( $ne == 0x148E ) { $d = 'nnguu'; }
				elseif ( $ne == 0x1491 ) { $d = 'nngaa'; }
				elseif ( $ne == 0x1489 ) { $d = 'nngai'; }
				else { $d = 'nng'; $i--; }
			}
			else {
				$i--;
				$d = 'nng';
			}
		}
		$i++;
		array_push($sb,$d);
	}
	return implode('',$sb);
}

public static function get_aipaitai ($nc) {
	if (array_key_exists($nc,self::$aipaitais))
		return self::$aipaitais[$nc];
	else
		return NULL;
}

static $aipaitais = array(
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
	0x1675 => array(0x1596,0x1489), # nngai
);

public static function iciUnicodeToLatinAlphabet ($text) {
	$cnt = mb_strlen($text,'UTF-8');
	$text_aipaitai = "";
	for ($i=0; $i<$cnt; $i++) {
		$c = mb_substr($text,$i,1,'UTF-8');
		$nc = utf8::utf8_to_numeric($c);
		if ( $i != $cnt-1) {
			$c1 = mb_substr($text,$i+1,1,'UTF-8');
			$nc1 = utf8::utf8_to_numeric($c1);
			if ( $nc1 == 0x1403 && ($a=self::get_aipaitai($nc))!=NULL ) {
				$utf8 = utf8::numeric_to_utf8($a);
				$text_aipaitai .= $utf8;
				$i++;
			} else {
				$text_aipaitai .= $c;
			}
		} else {
			$text_aipaitai .= $c;
		}
	}
	return $text_aipaitai;
}

public static function iciUnicodeToNoAipaitai ($text) {
	$cnt = mb_strlen($text,'UTF-8');
	$text_noaipaitai = "";
	for ($i=0; $i<$cnt; $i++) {
		$c = mb_substr($text,$i,1,'UTF-8');
		$nc = utf8::utf8_to_numeric($c);
		if ( array_key_exists($nc,self::$ais_in_unicode) ) {
			$a = self::$ais_in_unicode[$nc]['UCnoaipaitai'];
			$utf8 = utf8::numeric_to_utf8($a);
			$text_noaipaitai .= $utf8;
		} else {
			$text_noaipaitai .= $c;
		}
	}
	return $text_noaipaitai;
}

static function _prepare_string ($str) {
	$s = preg_replace('/nng/','X',$str);
	$s = preg_replace('/ng/','N',$s);
	$s = preg_replace('/[rq]q/','Q',$s);
	$s = preg_replace('/ai/','A',$s);
	return $s;
}

static $ais_in_unicode = array(
	0x1401 => array( 'ra' => 'ai', 'UCaipaitai' => 0x1401, 'UCnoaipaitai' => array(0x140A,0x1403) ), 
    0x142F => array( 'ra' => 'pai', 'UCaipaitai' => 0x142F, 'UCnoaipaitai' => array(0x1438,0x1403) ), 
    0x144C => array( 'ra' => 'tai', 'UCaipaitai' => 0x144C, 'UCnoaipaitai' => array(0x1455,0x1403) ),
    0x146B => array( 'ra' => 'kai',  'UCaipaitai' => 0x146B, 'UCnoaipaitai' => array(0x1472,0x1403) ), 
    0x1489 => array( 'ra' => 'gai', 'UCaipaitai' => 0x1489, 'UCnoaipaitai' => array(0x1490,0x1403) ), 
    0x14A3 => array( 'ra' => 'mai', 'UCaipaitai' => 0x14A3, 'UCnoaipaitai' => array(0x14AA,0x1403) ), 
    0x14C0 => array( 'ra' => 'nai', 'UCaipaitai' => 0x14C0, 'UCnoaipaitai' => array(0x14C7,0x1403) ), 
    0x14D3 => array( 'ra' => 'lai', 'UCaipaitai' => 0x14D3, 'UCnoaipaitai' => array(0x14DA,0x1403) ), 
    0x14ED => array( 'ra' => 'sai', 'UCaipaitai' => 0x14ED, 'UCnoaipaitai' => array(0x14F4,0x1403) ),
    0x1526 => array( 'ra' => 'jai', 'UCaipaitai' => 0x1526, 'UCnoaipaitai' => array(0x152D,0x1403) ), 
    0x1542 => array( 'ra' => 'rai', 'UCaipaitai' => 0x1542, 'UCnoaipaitai' => array(0x154B,0x1403) ), 
    0x1553 => array( 'ra' => 'vai', 'UCaipaitai' => 0x1553, 'UCnoaipaitai' => array(0x1559,0x1403) ), 
    0x166F => array( 'ra' => 'qai', 'UCaipaitai' => 0x166F, 'UCnoaipaitai' => array(0x1583,0x1403) ), 
    0x1670 => array( 'ra' => 'ngai', 'UCaipaitai' => 0x1670, 'UCnoaipaitai' => array(0x1593,0x1403) )
);

public static function getUCaipaitai($key) {
	return utf8::numeric_to_utf8(self::$ais_in_unicode[$key]['UCaipaitai']);
}

public static function getUCnoaipaitai($key) {
	$s = '';
	foreach (self::$ais_in_unicode[$key]['UCnoaipaitai'] as $charCode) {
		$s .= utf8::numeric_to_utf8($charCode);
	}
	return $s;
}

public static function highlight_aipaitai_chars ( $text ) {
	
	$UCaipaitai_chars = array_map(
		'self::getUCaipaitai',
		array_keys(self::$ais_in_unicode));
	$UCnoaipaitai_chars = array_map(
		'self::getUCnoaipaitai',
		array_keys(self::$ais_in_unicode));
	$pat = '/(' . implode('|',$UCaipaitai_chars) . '|' . implode('|',$UCnoaipaitai_chars) . ')/';
	$rep = '<<<$1>>>';
	$highlighted_text = preg_replace($pat,$rep,$text);	
	return $highlighted_text;
}



}

?>