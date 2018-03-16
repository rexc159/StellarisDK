package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Helper.DataEntry;
import StellarisDK.GUI.ArmyUI;
import javafx.scene.control.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class Army extends GenericData {

    @Override
    public void setDataEntries() {
        DataEntry health = new DataEntry<>("health", "=", "...", 1111);
        DataEntry morale = new DataEntry<>("morale", "=", "...", 1110);
        DataEntry damage = new DataEntry<>("damage", "=", "...", 1111);

        DataEntry morale_damage = new DataEntry<>("morale_damage", "=", "...", 1110);
        DataEntry collateral_damage = new DataEntry<>("collateral_damage", "=", "...", 1110);

        DataEntry war_exhaustion = new DataEntry<>("war_exhaustion", "=", "...", 1110);
        DataEntry time = new DataEntry<>("time", "=", "...", 1110);
        DataEntry maintenance = new DataEntry<>("maintenance", "=", "...", 1110);
        DataEntry icon_frame = new DataEntry<>("icon_frame", "=", "...", 1111);

        DataEntry has_morale = new DataEntry<>("has_morale", "=", "...", 1110);
        DataEntry has_species = new DataEntry<>("has_species", "=", "...", 1110);
        DataEntry pop_limited = new DataEntry<>("pop_limited", "=", "...", 1110);
        DataEntry defensive = new DataEntry<>("defensive", "=", "...", 1110);
        DataEntry is_building_spawned = new DataEntry<>("is_building_spawned", "=", "...", 1110);

        DataEntry cost = new DataEntry<>("cost", 1010);

        DataEntry potential = new DataEntry<>("potential", 1001);
        DataEntry allow = new DataEntry<>("allow", 1000);
        DataEntry on_queued = new DataEntry<>("on_queued", 1000);
        DataEntry on_unqueued = new DataEntry<>("on_unqueued", 1000);
        DataEntry show_tech_unlock_if = new DataEntry<>("show_tech_unlock_if", 1000);
        DataEntry prerequisites = new DataEntry<>("prerequisites", 1000);

        health.editor = new Spinner<Double>(0,Integer.MAX_VALUE,1);
        morale.editor = new Spinner<Double>(0,Integer.MAX_VALUE,1);
        damage.editor = new Spinner<Double>(0,Integer.MAX_VALUE,1);
        morale_damage.editor = new Spinner<Double>(0,Integer.MAX_VALUE,1);
        collateral_damage.editor = new Spinner<Double>(0,Integer.MAX_VALUE,1);
        war_exhaustion.editor = new Spinner<Double>(0,Integer.MAX_VALUE,1);
        time.editor = new Spinner<Double>(0,Integer.MAX_VALUE,1);
        maintenance.editor = new Spinner<Double>(0,Integer.MAX_VALUE,1);
        icon_frame.editor = new Spinner<Double>(0,Integer.MAX_VALUE,1);

        this.dataEntries = new ArrayList<>(
                Arrays.asList(damage, health, morale, morale_damage, collateral_damage, war_exhaustion,
                        time, maintenance, icon_frame, has_morale, has_species, pop_limited, defensive,
                        is_building_spawned, cost, potential, allow, on_queued, on_unqueued,
                        show_tech_unlock_if, prerequisites));
    }

    public Army() {
        super();
        this.type = new DataEntry("army", 1010);
        ui = new ArmyUI(this);
    }

    public Army(String input, String type) {
        super(input, type);
        ui = new ArmyUI(this);
    }

    @Override
    public GenericData createNew() {
        return new Army();
    }

}