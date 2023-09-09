package minechem.minetweaker;

import minechem.item.ChemicalRoomStateEnum;
import minechem.item.molecule.MoleculeEnum;
import minechem.item.molecule.MoleculeItem;
import minechem.potion.PotionChemical;

public class CustomMoleculeEnum extends MoleculeEnum {

    private final MoleculeItem moleculeItem;

    public CustomMoleculeEnum(
        String name,
        int id,
        ChemicalRoomStateEnum state,
        MoleculeItem moleculeItem,
        PotionChemical... chemicals) {

        super(name, id, state, chemicals);

        this.moleculeItem = moleculeItem;
    }

    @Override
    public MoleculeItem getItem() {
        return moleculeItem;
    }
}
