package com.mytest.sharing;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author liqingyu
 * @since 2018/07/20
 */
public class AnimalsTest {

    @Test
    public void loadTheArk() {
        Animals animals = new Animals();
        Collection<Animals.Animal> collection = new ArrayList<Animals.Animal>();
        collection.add(new Animals.Animal(Animals.Species.AARDVARK, Animals.Gender.FEMALE));
        collection.add(new Animals.Animal(Animals.Species.BENGAL_TIGER, Animals.Gender.MALE));
        collection.add(new Animals.Animal(Animals.Species.BENGAL_TIGER, Animals.Gender.MALE));
        collection.add(new Animals.Animal(Animals.Species.AARDVARK, Animals.Gender.FEMALE));
        collection.add(new Animals.Animal(Animals.Species.AARDVARK, Animals.Gender.FEMALE));
        collection.add(new Animals.Animal(Animals.Species.CARIBOU, Animals.Gender.FEMALE));
        collection.add(new Animals.Animal(Animals.Species.CARIBOU, Animals.Gender.MALE));
        collection.add(new Animals.Animal(Animals.Species.BENGAL_TIGER, Animals.Gender.FEMALE));
        collection.add(null);
        Assert.assertEquals("", 2, animals.loadTheArk(collection));
    }
}