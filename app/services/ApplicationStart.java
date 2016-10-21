package services;

import com.avaje.ebean.Ebean;
import models.Contact;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;
import java.util.Random;
import java.util.stream.IntStream;

@Singleton
public class ApplicationStart {

    private static String[] Beginning = {"Kr", "Ca", "Ra", "Mrok", "Cru",
            "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
            "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
            "Mar", "Luk"};
    private static String[] Middle = {"air", "ir", "mi", "sor", "mee", "clo",
            "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
            "marac", "zoir", "slamar", "salmar", "urak"};
    private static String[] End = {"d", "ed", "ark", "arc", "es", "er", "der",
            "tron", "med", "ure", "zur", "cred", "mur"};
    private static Random rand = new Random();
    @Inject
    public ApplicationStart() {
        Instant start = Instant.now();
        Logger.info("ApplicationTimer demo: Starting application at " + start);
        populateDB();
    }

    public static String generateName() {

        return Beginning[rand.nextInt(Beginning.length)] +
                Middle[rand.nextInt(Middle.length)] +
                End[rand.nextInt(End.length)];

    }

    private void populateDB() {
        if (Ebean.find(Contact.class).findRowCount() == 0) {
            IntStream.range(0, 100).boxed().forEach(i -> {
                Contact contact = new Contact();
                contact.email = generateName().toLowerCase() + "@email.com";
                contact.title = i+"";
                contact.name = generateName();
                contact.save();
            });
        }
    }

}
