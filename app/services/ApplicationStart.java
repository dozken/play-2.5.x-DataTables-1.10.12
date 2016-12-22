package services;

import com.avaje.ebean.Ebean;
import models.Contact;
import models.SimpleModel;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.Instant;
import java.util.Random;
import java.util.stream.IntStream;

@Singleton
public class ApplicationStart {

    @Inject
    public ApplicationStart() {
        Instant start = Instant.now();
        Logger.info("ApplicationTimer demo: Starting application at " + start);
        populateDB();
    }

    private void populateDB() {
        if (Ebean.find(Contact.class).findRowCount() == 0) {
            IntStream.range(0, 100).boxed().forEach(i -> {
                Contact contact = new Contact();
                contact.email = generateName().toLowerCase() + "@email.com";
                contact.title = i + "";
                contact.name = generateName();
                contact.save();
            });
        }
        if (Ebean.find(SimpleModel.class).findRowCount() == 0) {
            IntStream.range(0, 100).boxed().forEach(i -> {
                SimpleModel simpleModel = new SimpleModel();
                simpleModel.name = "name" + i;
                simpleModel.save();
            });
        }
    }

    private String generateName() {
        String[] Beginning = {"Kr", "Ca", "Ra", "Mrok", "Cru",
                "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
                "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
                "Mar", "Luk"};
        String[] Middle = {"air", "ir", "mi", "sor", "mee", "clo",
                "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
                "marac", "zoir", "slamar", "salmar", "urak"};
        String[] End = {"d", "ed", "ark", "arc", "es", "er", "der",
                "tron", "med", "ure", "zur", "cred", "mur"};
        Random rand = new Random();

        return Beginning[rand.nextInt(Beginning.length)] +
                Middle[rand.nextInt(Middle.length)] +
                End[rand.nextInt(End.length)];

    }

}
