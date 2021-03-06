<?php

require_once 'tools/NunHanSearch/ProcessQuery.php';
require_once 'script/Syllabics.php';
require_once 'lib/log4php/Logger.php';
Logger::configure("../log4php.properties.xml");

/**
 * Test class for ProcessQuery.
 * Generated by PHPUnit on 2010-05-17 at 15:52:40.
 */
class ProcessQueryTest extends PHPUnit_Framework_TestCase
{
    /**
     * @var ProcessQuery
     */
    protected $object;

    /**
     * Sets up the fixture, for example, opens a network connection.
     * This method is called before a test is executed.
     */
    protected function setUp()
    {
        $this->object = new ProcessQuery;
    }

    /**
     * Tears down the fixture, for example, closes a network connection.
     * This method is called after a test is executed.
     */
    protected function tearDown()
    {
    }

      public function test__remove_duplicates()
    {
    	$list = array(1,4,3,5,3,2,1);
    	$expected_list = array(1,4,3,5,2);
    	$new_list = $this->object->_remove_duplicates($list);
    	$this->assertTrue($this->arrays_are_similar($new_list,$expected_list));
    }

    public function test__grep__Case_syllabic_inuktitut_query_with_wild_char () {
    	$query_roman = '\S*valliasimaliratta';
    	$query_syll = Syllabics::latinAlphabetToUnicode($query_roman, '0');
    	$grepper = new Grepper($query_roman, $query_syll, 'iu', 'syll');
    	$this->object->set_grepper( $grepper );
    	$grepped = $this->object->_grep();
    	$expected = 'qimirruvalliasimaliratta:2:25673075:59233783';
    	$this->assertEquals($expected, $grepped, "The grepping returned a wrong value");
    }
    
    
    
    function arrays_are_similar($a, $b) {
    	// if the indexes don't match, return immediately
    	if (count(array_diff_assoc($a, $b))) {
    		return false;
    	}
    	// we know that the indexes, but maybe not values, match.
    	// compare the values between the two arrays
    	foreach($a as $k => $v) {
    		if ($v !== $b[$k]) {
    			return false;
    		}
    	}
    	// we have identical indexes, and no unequal values
    	return true;
    }

}
?>
