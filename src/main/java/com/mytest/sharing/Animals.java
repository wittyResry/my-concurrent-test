/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mytest.sharing;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author liqingyu
 * @since 2018/07/20
 */
public class Animals {

    private Ark ark = new Ark();

    /**
     * 计算有多少配对的动物
     *
     * @param candidates
     * @return
     */
    public int loadTheArk(Collection<Animal> candidates) {
        SortedSet<Animal> animals;
        int numPairs = 0;
        Animal candidate = null;

        // animals confined to method, don't let them escape!
        animals = new TreeSet<Animal>(new SpeciesGenderComparator());
        animals.addAll(candidates);
        for (Animal a : animals) {
            if (candidate == null || !candidate.isPotentialMate(a))
                candidate = a;
            else {
                ark.load(new AnimalPair(candidate, a));
                ++numPairs;
                candidate = null;
            }
        }
        return numPairs;
    }

    class SpeciesGenderComparator implements Comparator<Animal> {
        @Override
        public int compare(Animal one, Animal two) {
            if (one == null || two == null) {
                if (one != null) {
                    return -1;
                } else if (two != null) {
                    return 1;
                }
                return 0;
            }
            int speciesCompare = one.species.compareTo(two.species);
            return speciesCompare != 0 ? speciesCompare : one.gender.compareTo(two.gender);
        }
    }

    public enum Species {
                         AARDVARK, BENGAL_TIGER, CARIBOU, DINGO, ELEPHANT, FROG, GNU, HYENA, IGUANA, JAGUAR, KIWI, LEOPARD, MASTADON, NEWT, OCTOPUS, PIRANHA, QUETZAL, RHINOCEROS, SALAMANDER, THREE_TOED_SLOTH, UNICORN, VIPER, WEREWOLF, XANTHUS_HUMMINBIRD, YAK, ZEBRA
    }

    public enum Gender {
                        MALE, FEMALE
    }

    static class Animal {
        private Species species;
        private Gender  gender;

        public Animal(Species species, Gender gender) {
            this.species = species;
            this.gender = gender;
        }

        /**
         * 是否匹配同类异性
         *
         * @param other
         * @return true 匹配; false 不匹配
         */
        public boolean isPotentialMate(Animal other) {
            if (other == null) {
                return false;
            }
            return species == other.species && gender != other.gender;
        }
    }

    /**
     *
     */
    class AnimalPair {
        private final Animal one, two;

        public AnimalPair(Animal one, Animal two) {
            this.one = one;
            this.two = two;
        }
    }

    class Ark {
        private final Set<AnimalPair> loadedAnimals = new HashSet<AnimalPair>();

        public void load(AnimalPair pair) {
            loadedAnimals.add(pair);
        }
    }
}
