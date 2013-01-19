<?php
/*
 * To change the template for this generated file go to
 * Window - Preferences  - PHP - Code Style - Code Templates - Code - Simple php file
 *
 * Auteur/Author: Benoît Farley
 * Date: 2011-04-27
 *
 */

 require_once 'lib/log4php/Logger.php';

class LinguaENParticiple
{

		static $no_double = array(
'abandon', 'accouter', 'accredit', 'adhibit', 'administer', 'alter', 'anchor', 'answer', 'attrit', 'audit',
'author', 'ballot', 'banner', 'batten', 'bedizen', 'bespatter', 'betoken', 'bewilder', 'billet', 'blacken',
'blither', 'blossom', 'bother', 'brighten', 'broaden', 'broider', 'burden', 'caparison', 'catalog', 'censor',
'center', 'charter', 'chatter', 'cheapen', 'chipper', 'chirrup', 'christen', 'clobber', 'cluster', 'coarsen',
'cocker', 'coedit', 'cohabit', 'concenter', 'corner', 'cover', 'covet', 'cower', 'credit', 'custom', 'dampen',
'deafen', 'decipher', 'deflower', 'delimit', 'deposit', 'develop', 'differ', 'disaccustom', 'discover',
'discredit', 'disencumber', 'dishearten', 'disinherit', 'dismember', 'dispirit', 'dither', 'dizen',
'dodder', 'edit', 'elicit', 'embitter', 'embolden', 'embosom', 'embower', 'empoison', 'empower', 'enamor',
'encipher', 'encounter', 'endanger', 'enfetter', 'engender', 'enlighten', 'enter', 'envelop', 'envenom',
'environ', 'exhibit', 'exit', 'fasten', 'fatten', 'feather', 'fester', 'filter', 'flatten', 'flatter',
'flounder', 'fluster', 'flutter', 'foreshorten', 'founder', 'fritter', 'gammon', 'gather', 'gladden',
'glimmer', 'glisten', 'glower', 'greaten', 'hamper', 'hanker', 'happen', 'harden', 'harken', 'hasten',
'hearten', 'hoarsen', 'honor', 'imprison', 'inhabit', 'inhibit', 'inspirit', 'interpret', 'iron', 'laten',
'launder', 'lengthen', 'liken', 'limber', 'limit', 'linger', 'litter', 'liven', 'loiter', 'lollop', 'louden',
'lower', 'lumber', 'madden', 'malinger', 'market', 'matter', 'misinterpret', 'misremember', 'monitor',
'moulder', 'murder', 'murmur', 'muster', 'number', 'offer', 'open', 'order', 'outmaneuver', 'overmaster',
'pamper', 'pilot', 'pivot', 'plaster', 'plunder', 'powder', 'power', 'prohibit', 'reckon', 'reconsider',
'recover', 'redden', 'redeliver', 'register', 'rejigger', 'remember', 'renumber', 'reopen', 'reposit',
'rewaken', 'richen', 'roister', 'roughen', 'sadden', 'savor', 'scatter', 'scupper', 'sharpen', 'shatter',
'shelter', 'shimmer', 'shiver', 'shorten', 'shower', 'sicken', 'smolder', 'smoothen', 'soften', 'solicit', 
'squander', 'stagger', 'stiffen', 'stopper', 'stouten', 'straiten', 'strengthen', 'stutter', 'suffer',
'sugar', 'summon', 'surrender', 'swelter', 'sypher', 'tamper', 'tauten', 'tender', 'thicken', 'threaten',
'thunder', 'totter', 'toughen', 'tower', 'transit', 'tucker', 'unburden', 'uncover', 'unfetter', 'unloosen',
'upholster', 'utter', 'visit', 'vomit', 'wander', 'water', 'weaken', 'whiten', 'winter', 'wonder', 'worsen'
);
	
	static $irreg = array(
'awake' => 'awoken',
'be' => 'been',
'bear' => 'born',
'beat' => 'beat',
'become' => 'become',
'begin' => 'begun',
'bend' => 'bent',
'beset' => 'beset',
'bet' => 'bet',
'bid' => 'bidden',
'bind' => 'bound',
'bite' => 'bitten',
'bleed' => 'bled',
'blow' => 'blown',
'break' => 'broken',
'breed' => 'bred',
'bring' => 'brought',
'broadcast' => 'broadcast',
'build' => 'built',
'burn' => 'burnt',
'burst' => 'burst',
'buy' => 'bought',
'cast' => 'cast',
'catch' => 'caught',
'choose' => 'chosen',
'cling' => 'clung',
'come' => 'come',
'cost' => 'cost',
'creep' => 'crept',
'cut' => 'cut',
'deal' => 'dealt',
'dig' => 'dug',
'dive' => 'dived',
'do' => 'done',
'draw' => 'drawn',
'dream' => 'dreamt',
'drive' => 'driven',
'drink' => 'drunk',
'dye' => 'dyed',
'eat' => 'eaten',
'fall' => 'fallen',
'feed' => 'fed',
'feel' => 'felt',
'fight' => 'fought',
'find' => 'found',
'fit' => 'fit',
'flee' => 'fled',
'fling' => 'flung',
'fly' => 'flown',
'forbid' => 'forbidden',
'forget' => 'forgotten',
'forego' => 'foregone',
'forgo' => 'forgone',
'forgive' => 'forgiven',
'forsake' => 'forsaken',
'freeze' => 'frozen',
'get' => 'gotten',
'give' => 'given',
'go' => 'gone',
'grind' => 'ground',
'grow' => 'grown',
'hang' => 'hung',
'have' => 'had',
'hear' => 'heard',
'hide' => 'hidden',
'hit' => 'hit',
'hold' => 'held',
'hurt' => 'hurt',
'keep' => 'kept',
'kneel' => 'knelt',
'knit' => 'knit',
'know' => 'know',
'lay' => 'laid',
'lead' => 'led',
'leap' => 'lept',
'learn' => 'learnt',
'leave' => 'left',
'lend' => 'lent',
'let' => 'let',
'lie' => 'lain',
'light' => 'lighted',
'lose' => 'lost',
'make' => 'made',
'mean' => 'meant',
'meet' => 'met',
'misspell' => 'misspelt',
'mistake' => 'mistaken',
'mow' => 'mown',
'overcome' => 'overcome',
'overdo' => 'overdone',
'overtake' => 'overtaken',
'overthrow' => 'overthrown',
'pay' => 'paid',
'plead' => 'pled',
'prove' => 'proven',
'put' => 'put',
'quit' => 'quit',
'read' => 'read',
'rid' => 'rid',
'ride' => 'ridden',
'ring' => 'rung',
'rise' => 'risen',
'run' => 'run',
'saw' => 'sawn',
'say' => 'said',
'see' => 'seen',
'seek' => 'sought',
'sell' => 'sold',
'send' => 'sent',
'set' => 'set',
'sew' => 'sewn',
'shake' => 'shaken',
'shave' => 'shaven',
'shear' => 'shorn',
'shed' => 'shed',
'shine' => 'shone',
'shoe' => 'shod',
'shoot' => 'shot',
'show' => 'shown',
'shrink' => 'shrunk',
'shut' => 'shut',
'sing' => 'sung',
'sink' => 'sunk',
'sit' => 'sat',
'sleep' => 'slept',
'slay' => 'slain',
'slide' => 'slid',
'sling' => 'slung',
'slit' => 'slit',
'smite' => 'smitten',
'sow' => 'sown',
'speak' => 'spoken',
'speed' => 'sped',
'spend' => 'spent',
'spill' => 'spilt',
'spin' => 'spun',
'spit' => 'spit',
'split' => 'split',
'spread' => 'spread',
'spring' => 'sprung',
'stand' => 'stood',
'steal' => 'stolen',
'stick' => 'stuck',
'sting' => 'stung',
'stride' => 'stridden',
'strike' => 'struck',
'string' => 'strung',
'strive' => 'striven',
'swear' => 'sworn',
'sweep' => 'swept',
'swell' => 'swollen',
'swim' => 'swum',
'swing' => 'swung',
'take' => 'taken',
'teach' => 'taught',
'tear' => 'torn',
'tell' => 'told',
'think' => 'thought',
'thrive' => 'thrived',
'throw' => 'thrown',
'thrust' => 'thrust',
'tread' => 'trodden',
'understand' => 'understood',
'uphold' => 'upheld',
'upset' => 'upset',
'wake' => 'woken',
'wear' => 'worn',
'weave' => 'woven',
'wed' => 'wed',
'weep' => 'wept',
'wind' => 'wound',
'win' => 'won',
'withhold' => 'withheld',
'withstand' => 'withstood',
'wring' => 'wrung',
'write' => 'written'
);


public static function participle ($inf)
	{
		if ( array_key_exists($inf, self::$irreg) )
		{
			return self::$irreg[$inf];
		}
		
		$stem = self::stem($inf);
		$part = $stem . 'ed';
		$part = preg_replace('/([bcdfghjklmnpqrstvwxyz])eed$/',"$1ed", $part); 
		$part = preg_replace('/([bcdfghjklmnpqrstvwxyz])yed$/',"$1ied", $part); 
		$part = preg_replace('/eed$/',"ed", $part); 
		
		return $part;
	} 
	
	static function stem ($inf)
	{
		$logger = Logger::getLogger('LinguaENParticiple.stem');
		$logger->debug("\$inf='$inf'");
		$stem = $inf;
		if ( preg_match('/[bcdfghjklmnpqrstvwxyz][aeiou][bcdfghjklmnpqrstv]$/', $stem) )
		/*
		 * If the word ends in CVC pattern (but final consonant is not w, x, y, or z)
		 * and if the stress is not on the penultimate syllable, then
		 * double the final consonant.
		 * 
		 * The stop list (stored in $no_double) is a list of words ending in CVC,
		 * with the stress on the penultimate syllable such as 'visit', 'happen', 'enter', etc.
		 */
		{
			if ( ! in_array($stem, self::$no_double) )
			{
				$stem = preg_replace( '/(\w)$/', "$1$1", $stem);
			}
		}
		
		return $stem;
	}
	
	
	
}

?>