<?php

class NotSupportedOnServerException extends Exception {
	
	function __construct($message=null) {
		parent::__construct($message);
	}
	
}