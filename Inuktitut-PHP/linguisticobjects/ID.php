<?php

class ID {
	
	private $id;
	
	public function __construct($id) {
		$this->id = $id;
	}
	
	public function toString() {
		return $this->id;
	}
	
}

?>