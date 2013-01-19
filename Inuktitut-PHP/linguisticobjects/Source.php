<?php
/*
 * Conseil national de recherche Canada 2006/
 * National Research Council Canada 2006
 * 
 * Cr�� le / Created on Nov 22, 2006
 * par / by Benoit Farley
 * 
 */

require_once 'linguisticobjects/ID.php';


class Source {
	//id,type,authorSurName,authorMidName,authorFirstName,title,subtitle,publisher,publisherMisc,city/country,year
	
        private $id = NULL;
        private $type = NULL;
        private $authorSurName = NULL;
        private $authorMidName = NULL;
        private $authorFirstName = NULL;
        private $title = NULL;
        private $subtitle = NULL;
        private $publisher = NULL;
        private $publisherMisc = NULL;
        private $location = NULL;
        private $year = NULL;

	public function __construct ($champsValeurs) {
        $this->id = $champsValeurs['id'];
        $this->type = $champsValeurs['type'];
        $this->authorSurName = $champsValeurs['authorSurName'];
        $this->authorMidName = $champsValeurs['authorMidName'];
        $this->authorFirstName = $champsValeurs['authorFirstName'];
        $this->title = $champsValeurs['title'];
        $this->subtitle = $champsValeurs['subtitle'];
        $this->publisher = $champsValeurs['publisher'];
        $this->publisherMisc = $champsValeurs['publisherMisc'];
        $this->location = $champsValeurs['city/country'];
        $this->year = $champsValeurs['year'];
    }
    
    public function id() {
    	return $this->id;
    }
    
    public function type() {
    	return $this->type;
    }
    
    public function getAuthorSurName () {
    	return $this->authorSurName;
    }
           
    public function getAuthorMidName () {
    	return $this->authorMidName;
    }

    public function getAuthorFirstName () {
    	return $this->authorFirstName;
    }
           
    public function getTitle () {
    	return $this->title;
    }

    public function getSubTitle () {
    	return $this->subtitle;
    }

    public function getPublisher () {
    	return $this->publisher;
    }

    public function getPublisherMisc () {
    	return $this->publisherMisc;
    }

    public function getLocation () {
    	return $this->location;
    }

    public function getYear () {
    	return $this->year;
    }

    static public function generates_multiple_objects() {
    	return false;
    }
    
    static public function make_id ($field_values) {
    	$morpheme_id_object = new ID( $field_values['id'] );
    	return $morpheme_id_object;
    }
    
    

}
?>