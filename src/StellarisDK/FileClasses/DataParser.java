package StellarisDK.FileClasses;

import StellarisDK.FileClasses.Anomaly.Anomaly;
import StellarisDK.FileClasses.Anomaly.AnomalyCategory;
import StellarisDK.FileClasses.Component.CompSet;
import StellarisDK.FileClasses.Component.Component;
import StellarisDK.FileClasses.Helper.DataMap;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataParser {

    private final static Pattern pattern = Pattern.compile("(?s)(?m)(^\\w+)\\s=\\s\\{(.+?)[\\r\\n]\\}[\\r\\n]?");
    private final static Pattern constants = Pattern.compile("(\\@\\w+) = (.+)");

    public static void parseToConsole(File file) throws IOException{
        Scanner scan = new Scanner(file);
        Component data;
        String temp;
        do {
            temp = scan.findWithinHorizon(constants, 0);
            if (temp != null)
                System.out.println(temp);
        } while (temp != null);
        while (scan.hasNext()) {
            String objectDat = scan.findWithinHorizon(pattern, 0);
            if (objectDat != null) {
                Matcher obj = pattern.matcher(objectDat);
                obj.find();
                data = new Component();
                data.setData((DataMap)data.load(obj.group(2)));
                System.out.println(data.export());
            } else {
                break;
            }
        }
    }

    public static boolean bracketCheck(String input) {
        Matcher test = Pattern.compile("(\\{)|(\\})").matcher(input);
        int tester = 0;
        while (test.find()) {
            if (test.group(1) != null) {
                tester++;
            } else if (test.group(2) != null) {
                tester--;
            }
        }
        return tester == 0;
    }

    /*
        Either follow the extremely long stack of elifs, or just use the folder name as reference
        which is way easier anyway.

        Different types of the same objects are identified within the data class itself
     */
    public static ArrayList<TreeItem> parseAll(File file) throws IOException {
        /*
            Special Handler for attitudes, bombardment, required
        */
        ArrayList<TreeItem> out = new ArrayList<>();
        Scanner scan = new Scanner(file);
        String temp;
        TreeItem<String> consts = new TreeItem<>("Constants");
        TreeItem<String> namespace = new TreeItem<>("Namespace");
        Pattern name = Pattern.compile("namespace\\s*=\\s*\"(\\w+)\"");
        do {
            temp = scan.findWithinHorizon(name, 0);
            if (temp != null)
                namespace.getChildren().add(new TreeItem<>(temp));
        }while (temp != null);
        if(!namespace.getChildren().isEmpty())
            out.add(namespace);
        do {
            temp = scan.findWithinHorizon(constants, 0);
            if (temp != null)
                consts.getChildren().add(new TreeItem<>(temp));
        } while (temp != null);
        if (!consts.getChildren().isEmpty())
            out.add(consts);
        while (scan.hasNext()) {
            String objectDat = scan.findWithinHorizon(pattern, 0);
            if (objectDat != null) {
                Matcher obj = pattern.matcher(objectDat);
                obj.find();
                String tabby = obj.group(2).replaceAll(" {4}", "\t");
                GenericData gData;
                String search = file.getParent().split("\\\\")[file.getParent().split("\\\\").length-1];
                System.out.println(bracketCheck(tabby));
                switch (search) {
                    case "agendas":
                        gData = new Agenda(tabby, obj.group(1));
                        break;
                    case "ambient_objects":
                        gData = new AmbientObject(tabby);
                        break;
                    case "anomalies":
                        if(obj.group(1).equals("anomaly")){
                            gData = new Anomaly(tabby);
                        }else{
                            gData = new AnomalyCategory(tabby);
                        }
                        break;
                    case "armies":
                        gData = new Army(tabby, obj.group(1));
                        break;
                    case "ascension_perks":
                        gData = new Tradition(tabby, obj.group(1), false);
                        break;
                    case "attitudes":
                        gData = new Attitude(tabby, obj.group(1));
                        break;
                    case "bombardment_stances":
                    case "buildable_pops":
                    case "building tags":
                    case "buildings":
                    case "colors":
                        gData = new Component(tabby, obj.group(1));
                        break;
                    case "component_sets":
                        gData = new CompSet(tabby);
                        break;
                    case "component_templates":
                        gData = new Component(tabby, obj.group(1));
                        break;
                    case "country_types":
                    case "defines":
                    case "deposits":
                    case "diplo_phrases":
                    case "edicts":
                    case "event_chains":
                    case "fallen_empires":
                    case "game_rules":
                    case "global_ship_designs":
                    case "governments":
                    case "authorities":
                    case "civics":
                    case "graphical_culture":
                    case "mandates":
                    case "map_modes":
                    case "megastructures":
                    case "name_lists":
                    case "notification_modifiers":
                    case "observation_station_missions":
                    case "on_actions":
                    case "opinion_modifiers":
                    case "personalities":
                    case "planet_classes":
                    case "planet_modifiers":
                    case "policies":
                    case "pop_faction_types":
                    case "precursor_civilizations":
                    case "random_names":
                    case "base":
                    case "scripted_effects":
                    case "scripted_loc":
                    case "scripted_triggers":
                    case "scripted_variables":
                    case "section_templates":
                    case "sector_settings":
                    case "sector_types":
                    case "ship_behaviors":
                    case "ship_sizes":
                    case "solar_system_initializers":
                    case "special_projects":
                    case "species_archetypes":
                    case "species_classes":
                    case "species_names":
                    case "species_rights":
                    case "star_classes":
                    case "starbase_buildings":
                    case "starbase_levels":
                    case "starbase_modules":
                    case "starbase_types":
                    case "start_screen_messages":
                    case "static_modifiers":
                    case "strategic_resources":
                    case "subjects":
                    case "system_types":
                    case "technology":
                    case "category":
                    case "tier":
                    case "terraform":
                    case "tile_blockers":
                    case "tradition_categories":
                        gData = new Component(tabby, obj.group(1));
                        break;
                    case "traditions":
                        gData = new Tradition(tabby, obj.group(1), true);
                        break;
                    case "traits":
                    case "events":
                        gData = new Event(tabby, obj.group(1));
                        break;
                    case "localisation":
                    default:
                        gData = new Component(tabby, obj.group(1));
                }
                out.add(new TreeItem<>(gData));
            } else {
                break;
            }
        }
        scan.close();
        return out;
    }
}