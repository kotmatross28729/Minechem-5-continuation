package minechem.reference;

import cpw.mods.fml.common.ModMetadata;
import java.util.Arrays;

import static minechem.reference.Reference.VERSION_FULL;

public class MetaData
{
    /**
     * Setup mod metadata
     *
     * @param metadata
     * @return edited metadata object
     */
    public static ModMetadata init(ModMetadata metadata)
    {
        metadata.modId = Reference.ID;
        metadata.name = Reference.NAME;
        metadata.description = Reference.NAME + " is a mod about chemistry, allowing you to research blocks and items, and then break them down into their base compounds and elements.";
        metadata.url = "http://www.minechemmod.com/";
        metadata.logoFile = "assets/" + Reference.ID + "/logo.png";
        metadata.version = VERSION_FULL;
        metadata.authorList = Arrays.asList("jakimfett");
        metadata.credits = "You can view a full list of contributors on github!";
        metadata.autogenerated = false;
        return metadata;
    }
}
