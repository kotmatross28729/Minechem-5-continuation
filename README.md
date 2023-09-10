# Minechem 5 continuation
## REQUIRES COFH CORE TO WORK!

### this is a fork of [DrParadox7/Minechem](https://github.com/DrParadox7/Minechem) which is a fork of [iopleke/Minechem](https://github.com/iopleke/Minechem)

##### WARNING: the author doesn't know how to normal format repository on github!
   - What you see next may destroy your eyes


## Changes:
+ Several changes to ru_RU.lang
+ Updated build script using example mod 1.7.10 gtnh
+ Added option in the config "Enable place blocks using tubes"
it allows you to place blocks of elements or molecules from test tubes, by default it is disabled, which means that you can place blocks of elements/molecules only using buckets
+ Several changes in MineTweaker/CraftTweaker support:

	- #### now addPotionEffect works from the id of the effect, and not from the name, this means it is easier to add mod effects, since usually the id of the effect is indicated in mod configs, and not its name.
	  - works on the principle:   `Chemicals.addPotionEffect(name of the molecule , effect id , duration of effect, strength of effect);`

           - Example: `Chemicals.addPotionEffect(abracadabra,31,3,3);` gives a molecule named abracadabra (specified as `val abracadabra = <minechem:minechemMolecule:id molecule>;`) effect with id 31, for 3 seconds and 3 (4) strength
	
	- #### the same with addCureEffect, you can cure the effects specified there.
      - works on the principle:   `Chemicals.addCureEffect(name of the molecule , id of the effect that should be cured);`

        - Example: `Chemicals.addCureEffect(lectonide,19);` a molecule called lectonide (specified as `val lectonide = <minechem:minechemMolecule:id molecule>;`) removes the effect from id 19, if available (19 is a standard minecraft effect, poison)
	
	- #### added removeMoleculeEffect (Not to be confused with removeMoleculeEffects (s at the end)) it works on the principle of Chemicals.removeMoleculeEffect(name of the molecule, id of the effect that should be removed from the molecule);
	    - For example. we have :
	```java
  Chemicals.addPotionEffect(abracadabra,31,3,3);
	Chemicals.addPotionEffect(abracadabra,32,3,3);
	Chemicals.addPotionEffect(abracadabra,33,3,3);
 in some file, we need to remove the effect from id 32, and in order not to delete the line itself, we can write:
	`Chemicals.removeMoleculeEffect(abracadabra,32);`
	and the effect with id 32 will not be applied to us when using the abracadabra molecule, everything is simple.




+ Fixed a bug where if you right-click on a block while holding a test tube of an element/molecule several times in the same place, the test tubes will be wasted. Now you cannot place an element/molecule block if it is already there.

### Current TODO:

+ ability to specify the color of the molecule in minetweaker

+ fix the description of molecules that do not appear when adding molecules through minetweaker (for example SHIFT to show effect information, Potion Effect, Duration, Power if the molecule has effects) now, how it works remains a mystery to me, because I even tried to create molecule in the code of the mod itself, however the only difference I got was the ability to specify a color for the molecule, even with the molecule effects configured in the code, "shift to show effect information" was not shown

+ create a README that doesn't destroy your eyes


## Credits:

#### [jakimfett](https://github.com/jakimfett) for creating the [original mod](https://github.com/iopleke/Minechem/tree/master)
#### [DrParadox7](https://github.com/DrParadox7) for creating a [fork](https://github.com/DrParadox7/Minechem)
#### [GTNH](https://github.com/GTNewHorizons) for creating [ExampleMod 1.7.10](https://github.com/GTNewHorizons/ExampleMod1.7.10)