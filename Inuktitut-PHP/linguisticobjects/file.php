<?php
/*
 * Created on 2010-10-29
 *
 * To change the template for this generated file go to
 * Window - Preferences - PHPeclipse - PHP - Code Templates
 */
 
 echo 'starting...'."\n";
 $subject = '(1) to /offer, /give, /bring s.t. to someone (-mut); (2) to /take s.t. away forcibly from someone (-mit)';
 $pattern_pm = '/(to )?\/([a-zA-Zàâéèêëîïôùûü\-]+)/';
 
    $offset = 0;
    $match_count = 0;
    while(preg_match($pattern_pm, $subject, $matches, PREG_OFFSET_CAPTURE, $offset))
    {
        // Increment counter
        $match_count++;
   
        // Get byte offset and byte length (assuming single byte encoded)
        $match_start = $matches[0][1];
        $match_length = strlen($matches[0][0]);
        $before_match = substr($subject,$offset,$match_start-$offset);

//        // (Optional) Transform $matches to the format it is usually set as (without PREG_OFFSET_CAPTURE set)
//        foreach($matches as $k => $match) $newmatches[$k] = $match[0];
//        $matches = $new_matches;
   
        // Your code here
        echo "Before match numper $match_count : '$before_match'\n";
        echo "Match number $match_count, at byte offset $match_start, $match_length bytes long: '".$matches[0][0]."'\r\n";
        
        echo "'".$matches[1][0]."'  '".$matches[2][0]."'\n";
        // Update offset to the end of the match
        $offset = $match_start + $match_length;
    }
    echo "and the rest: '".substr($subject,$offset)."'\n";
 
?>
