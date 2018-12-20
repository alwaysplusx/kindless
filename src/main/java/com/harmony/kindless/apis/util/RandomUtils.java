package com.harmony.kindless.apis.util;

import com.harmony.kindless.apis.domain.user.User;
import com.harmony.kindless.apis.domain.user.UserDetails;
import com.harmony.kindless.apis.domain.user.UserSettings;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wuxii
 */
public class RandomUtils {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static Random random = new Random();

    private static final String prefix = "a,ac,anti,auto,be,bi,co,com,de,dif,dis,e,en,in,inter,kilo," +
            "micro,mini,neg,non,ob,out,over,per,post,pre,pro,re,se,sub,sur,tele,trans,un,up,uni";

    private static final String suffix = "ability,able,acy,age,al,an,ance,ant,ent,ary,ate,ation,craft," +
            "cy,dom,ed,ence,er,ern,ese,ess,hood,ic,ice,ics,ing,ion,ism,ist,ive,less,logy,ly,ment,ness,ous,ship,sion,th,ty,ure,y";

    private static final String nickname = "4-Wheel,Ace,Admiral,Amazon,Amethyst,Ami,Amiga,Amigo,Amor,Amorcita," +
            "Amore,Amour,Angel,Anvil,Apple,Apple Jack,Ash,Autumn,Azkaban,Babe,Babs,Baby,Baby Bird,Baby Boo,Baby Cakes," +
            "Baby Carrot,Baby Maker,Backbone,Bacon,Baldie,Bambi,Bambino,Bandit,Barbie,Bean,Beanpole,Beast,Beautiful," +
            "Beauty,Bebe,Beef,Beetle,Belch,Bellbottoms,Belle,Bello,Bessie,Betty Boop,Biffle,Big Bird,Big Guy,Big Mac," +
            "Big Nasty,Birdy,Biscuit,Blimpie,Blondie,Boo,Boo Bear,Boo Boo,Boo Bug,Boomer,Boomhauer,Bootsie,Bossy," +
            "Braniac,Braveheart,Bridge,Brown Sugar,Bruiser,Brutus,Bub,Bubba,Bubble Butt,Bubblegum,Bubbles,Buck," +
            "Buckeye,Bud,Buddy,Buds,Buffalo,Bug,Bumblebee,Bumpkin,Bunny,Bunny Rabbit,Buster,Butter,Butterbuns," +
            "Buttercup,Butterfinger,Butternut,Button,Buzz,C-Dawg,Candy,Candycane,Cannoli,Captain,Captain Crunch," +
            "Carrot,Cat,Catwoman,Cello,Chain,Champ,Chance,Cheddar,Cheeky,Cheerio,Cheese,Cheesestick,Cheeto,Chef," +
            "Cherry,Chewbacca,Chica,Chicken Legs,Chicken Wing,Chickie,Chico,Chief,Chili,Chip,Chiquita,Chubs,Chuckles," +
            "Chum,Chump,Cindy Lou Who,Cinnamon,Cloud,Coach,Coke Zero,Cold Brew,Cold Front,Colonel,Con,Conductor," +
            "Cookie,Cookie Dough,Cotton,Cottonball,Cowboy,Creedence,Creep,Cricket,Cruella,Crumbles,Cuddle Pig," +
            "Cuddles,Cumulus,Cupcake,Cutie,Cutie Pie,Daffodil,Daria,Darling,Dear,Dearest,Dearey,Diesel,Diet Coke," +
            "Dilly Dally,Dimples,Dimpling,Dingo,Dino,Dinosaur,Dirt,Dirty Harry,DJ,Doc,Doctor,Doll,Dolly,Donut,Donuts," +
            "Doobie,Doofus,Dorito,Dork,Dot,Dots,Dottie,Double Bubble,Double Double,Dracula,Dragon,Dragonfly,Drake," +
            "Dreamey,Dropout,Duckling,Ducky,Dud,Dulce,Dum Dum,Dumbledore,Dummy,Dunce,Eagle,Einstein,Elf,Fatty,Fattykins," +
            "Fellow,Fido,Fiesta,Fifi,Figgy,Filly Fally,First Mate,Flower,Fly,Flyby,Focker,Fox,Foxy,Foxy Lady,Foxy Mama," +
            "Frankfurter,Frau Frau,Frauline,Freak,Freckles,French Fry,Friendo,Frodo,Frogger,Fun Dip,Fun Size,Fury," +
            "Gams,Gator,General,Genius,Ghoulie,Giggles,Ginger,Gingersnap,Gizmo,Goblin,Golden Graham,Goldilocks," +
            "Goon,Goonie,Goose,Gordo,Grease,Green Giant,Grumpy,Guapo,Gumdrop,Gummi Bear,Gummy Pop,Half Pint," +
            "Halfling,Halfmast,Hammer,Happy,Harry Potter,Hawk,Headlights,Heisenberg,Hermione," +
            "Herp Derp,Highbeam,Homer,Honey Locks,Honey Pie,Honeybun,Hot Pepper,Hot Sauce,Huggie," +
            "Hulk,Ice Queen,Inchworm,Itchy,Jackrabbit,Janitor,Jet,Joker,Juicy,Junior,Kid,Kirby," +
            "Kit-Kat,Kitten,Kitty,Knucklebutt,Lady,Ladybug,LaLa,Lefty,Lil Girl,Lil Mama,Lion,Little Bear,Lobster," +
            "Loosetooth,Loser,Lovely,Lover,Lovey,Lulu,Luna,Mad Max,Maestro,Manatee,Marge,Marshmallow,Master,Matey," +
            "Midge,Mimi,Mini Me,Mini Mini,Mini Skirt,Miss Piggy,Mistress,MomBod,Monkey,Moose,Mountain,Mouse," +
            "Mr. Clean,Ms. Congeniality,Muffin,Munchkin,Muscles,Music Man,Mustache,Nerd,Numbers,Oompa Loompa," +
            "Pansy,Papito,PB&J,Pearl,Pebbles,Pecan,Peep,Peppa Pig,Peppermint,Pickle,Pickles,Pig,Piggy,Piglet,Pinata," +
            "Pinkie,Pintsize,Pixie,Pixie Stick,Pookie,Pop Tart,Popeye,Pork Chop,Prego,Pretty Lady,Princess,Psycho," +
            "Punk,Pyscho,Queen Bee,Queenie,Rabbit,Rainbow,Raindrop,Raisin,Rambo,Rapunzel,Red,Red Hot,Red Velvet," +
            "Redbull,Righty,Ringo,Robin,Rocketfuel,Rockette,Romeo,Rosebud,Rosie,Rubber,Rufio,Rumplestiltskin," +
            "Salt,Sassafras,Sassy,Scarlet,Schnookums,Scout,Senior,Senorita,Sherlock,Shnookie,Short Shorts," +
            "Shorty,Shrinkwrap,Shuttershy,Silly Sally,Silly Gilly,Skinny Jeans,Skinny Minny,Skipper,Skunk," +
            "Sleeping Beauty,Slick,Slim,Smarty,Smiley,Smirk,Smoochie,Smudge,Snake,Snickerdoodle,Snickers," +
            "Snoopy,Snow White,Snuggles,Sourdough,Speedy,Spicy,Sport,Spud,Squirt,Starbuck,Stitch,String Bean," +
            "Stud,Sugar,Sunny,Sunshine,Superman,Sweety,Sweet Tea,Sweet 'n Sour,Sweetums,Swiss Miss,T-Dawg," +
            "Taco,Tank,Tarzan,Tata,Tater,Tater Tot,Teeny,Terminator,Guy,Hulk,Thor,Thumper,Thunder Thighs," +
            "Ticklebutt,Tickles,Tiny,Tomcat,Toodles,Toots,Tootsie,Tough Guy,Turkey,Turtle,Twig,Twiggy,Twinkie," +
            "Twinkly,Twix,Twizzler,Tyke,Weiner,Weirdo,Wifey,Hubby,Wilma,Winnie,Shining Goldbeast,Rabbit Running," +
            "Moron Lefty,Camel Kid,Modern Baroness,Sad Mistress Moron,Dangerous Saint,Warm Panther,Rat Rapid," +
            "Lady Seal,Panda Foxy,Puppy Wild,Unique Waterbuck,Gruesome Meaty Gravy,Sir Hippopotamus," +
            "Judge Willy,Meaty Countess Pirate,Doc Duck,Screaming Sunny Lovebird,The Wildcat,Countess Rough Elephant," +
            "El Bison,The Snake,Seriously Armadillo,Lucky Doc Serpent,Doctor Gold Fish,Bandit Camel,Parrot Raw," +
            "Pet Disco Sugar,Pet Kiddie,Mouse Stray,Kid Sad Cat,Dusty Wildcat,Lieutenant Rhino,Python Bandit," +
            "Hungry Pony,Der Duckling,Alpha Brave Emerald,Trooper Homeless,Icy Hawk,Screamy Angel,Kitten Hungry," +
            "Fatty Butterfly,Heavy Golden Believer,Kid Forgotten,SugarSugar,Seriously Lost Baron,Foal Beauty," +
            "Tapir Beauty,Dame Agent Mole,Believer Maximum,Barbaric Tumbler,LionLion,Los Sun,Doll Knight,Los Skunk," +
            "Rat Mysterious,Bat Itchy,Stupid Willy Pirate,DetectiveDetective,Honey Basilisk,Blue Fairy," +
            "Yodelers Strange,The Gazette,Ape Brave,Kiddo Alligator,Moose Steamy,Doc Man,Dreaded Pink,Alpha Bunny," +
            "Maxi Ocelot,Forsaken Rusty Beam,Steamy Slimy Monkey,Madam Bison,Little Lion,Rapid Leopard,Gruesome Wolf," +
            "Streaming Doctor Dog,Stony Wolf,The Ox,Barbaric Gorilla,Runny Dolly,Steel Barbaric,The Ocelot," +
            "Genius Indigo,The Panther,Disco Lovebird,Musk-ox Sherif,Furious Dancer,Mini Antelope,Lost Zebra," +
            "Panda Needless,Fast Pet,Gold Flapper,Crisp Spider,Madam Captain Pig,Jemima Berger,Shannan Maruo," +
            "Angelle Maruo,Lowrance Pritchard,Allissa Vallely,Halsy Labor,Stacey Pritchard,Peter Park,Lesley Waite," +
            "Tedd Wisner,Karen Labor,Teodor Zimmermann,Nissie Anthes,Igor Sinsheimer,Jerrie Park,Tobin Hutchison," +
            "Daniele Di felice,Fredric Ascherio,Harri Wisner,Jordan Piaia,Brenna Camiel,Etienne Blasdel," +
            "Jonell Zimmermann,Nicky Giacomello,Annette Carnago,Adolpho Lipkind,Brande Sinsheimer,Kennith Boos," +
            "Delores Orel,Trevor Welby,Phebe Hutchison,Leonidas Garboczi,Erena Dragotlovic,Kasper Cantwell," +
            "Alma Ascherio,Bax Holton,Orel Metelka,Codie Prodromou,Danica Piaia,Tulley Tobias,Aime Schulze," +
            "Hi Hanning,Chloris Blasdel,Hyatt Palleschi,Vannie Monari,Dilan Austen,Margalit Giacomello," +
            "Kerby Plant,Happy Gonzaga,Weider Bonner,Simonette Sanjuan,Ernesto Vitkosky,Cthrine Vitkosky," +
            "Devin Targett,Phyllys Calotychos,Leigh Gerstein,Jessie Targett,Fidole Snidman,Mallorie Aronson," +
            "Royal Myrvold,Charlotta Gerstein,Waylin Paynter,Coralie Mccorkle,Flint Omoregie,Asia Snidman," +
            "Hobie Collette,Casey Laisne,Carmine Parzen,Maegan Myrvold,Chev Romer,Libbi Sternlieb,Errick Csuja," +
            "Dyane Paynter,Zack Wiemann,Ardelia Linz,Ignace Hyland,Queenie Omoregie,Zed Tzu,Mellisent Veeh," +
            "Sid Oh,Jesse Collette,Tom Key,Camila Eilenberg,Hagen Schonhorn,Tobi Parzen,Kingston Sommer," +
            "Lara Korrick,Daryl Barro,Garnet Romer,Brigham Benedict-dye,Edna Rabin,Giusto Petty,Kirbie Csuja," +
            "Maje Viola,Pierrette Momose,Pennie Boyer,Juliette Wiemann,Christopher Strauss,Moreen Heraclitus," +
            "Moritz Legler,Adrian Pung,Malvin Bardoness,Leisha Bardoness,Shaun Treyz,Nelly Jiang,Elijah Lopez," +
            "Consuela Treyz,Thorvald Chazen,Sheilah Hur,Maurits Swain,Mandy Lopez,Westleigh Haft,Maude Martino," +
            "Bendick Poulios,Channa Chazen,Gregor Mesrobian,Agace Traebert,Crawford Duganzich,Bobbe Swain," +
            "Nappie Arellano,Jen Munoz,Culley Ferraroni,Dorelia Haft,Mal Cervantes,Delia Ioanna," +
            "Abram Mongelli,Justina Poulios,Filmore Brock-broido,Berta Mc cracken,Bil Mostafavi," +
            "Cally Mesrobian,Eward Zaun,Yolanthe Valentine,Huntington Page,Phylis Duganzich,Konstantin Honan," +
            "Gerianna Harraway,Moshe Yager,Leola Arellano,August Berlin,Cherilyn Zakaria,Krishna Gargiullo," +
            "Jsandye Ferraroni,Ethan Mittal,Tessy Dabars,Wilhelm Long,Edithe Cervantes,Colman Blair,Latashia Schillig," +
            "Gearalt Shraiman,Leah Surette,Orren Auden,Tanya Auden,Orazio Randall,Sadie Montgomery,Raimundo Foat," +
            "Ginevra Randall,Ricky Coutu,Ophelia Beardsley,Rockey Redden-tyler,Goldia Foat,Killian Toste,Yettie Foote," +
            "Samson Swander,Roseann Coutu,Walden Pelzel,Hope Bebrin,Sigvard Shaban,Engracia Redden-tyler," +
            "Gayle Tolliday,Lynnet Spengler,Rutter Weissenhorn,Marris Toste,Rossie Ito,Emilie Burleson," +
            "Barnabe Meurer,Kaila Swander,Silas Stumpo,Melva Avrett,Neale Ure,Joela Pelzel,Kristo Wolcowitz," +
            "Henriette Altobelli,Howard Mugglestone,Alvira Shaban,Tony Bochner,Noami Jona-lasinio,Thom Ammons," +
            "Brittaney Tolliday,Galen Mccomiskey,Marleah Watson,Randell Ettenberg,Veradis Weissenhorn," +
            "D'arcy Crossan,Bernardine Shillady,Dun Loprete,Cherey Ito,Erhart Daube,Leta Ehler,Andros Dejong," +
            "Nesta Pinderhughes,Zak Straits,Michele Straits,Tim Midgley,Bab Schulman,Currey Howze,Eugine Midgley," +
            "Olav Bortolami,Jemmie Bass,Jeremiah Wilkinson,Karry Howze,Pernell Prescod,Brook Repina,Orville Chinlyn," +
            "Madeleine Bortolami,Valentino Grisorio,Sydney Kleckner,Brent Macedo,Ainslee Wilkinson,Alexandre Woo," +
            "Quinta Ibba,Newton Debordes-jackson,Bobbye Prescod,Ken Jensen,Wilow Zachary,Sandor Messana," +
            "Maxie Chinlyn,Virgie Deperalta,Betta Skiotis,Tammie Castellano,Mildrid Grisorio,Cordy Sitkin," +
            "Leontine Lada,Westley Sponheim,Charil Macedo,Winnie Mcfarland,Becky Tomassoni,Hewie Enright," +
            "Tamiko Woo,Benjy Bellow,Dorian Naeeni,Alphonso Sidhu,Florentia Debordes-jackson,Carny Enis," +
            "Lina Sandall,Tanny Beaulieu,Rivalee Jensen,Gradey Mohammadzadeh,Karlie Ridgell,Trev Trollope," +
            "Minda Grayson,Dael Sirel,Margery Sirel,Tommy Goode,Kai Barr,Barron Shirey,Gladi Goode,Vern Trenholm," +
            "Edita Norrie,Sherwin Heaney,Malena Shirey,Dexter Heald,Mignonne Cannell,Richard Swenson," +
            "Becki Trenholm,Agustin Macneill,Melloney Struhl,Roth Ripley,Audrey Heaney,Jarid Juvenal," +
            "Jacintha Barbini,Dur O'connell,Gisela Heald,Teodoor Vincent,Liva Moliere,Robinet Gilboa," +
            "Cacilie Swenson,Torrin Denault,Minnnie Ayis,Haydon Kibbe,Dore Macneill,Gerry Bertrand," +
            "Ardelle Nagatomi,Peyton Shaw,Gabbey Ripley,Hilario Britton,Nerty Hugo,Hill Imrich,Enrica Juvenal," +
            "Elliott Doweiko,Janka Chui,Niven Spielman,Lona O'connell,Harp Dumouchel,Trudey Bissex,Eamon Francisco," +
            "Karisa Vincent,Chaddy Kobzik,Luciana Ozment,Doy Ko,Jemmy Macy,Carver Sidhu,Easter Sidhu,Chicky Enis," +
            "Heath Tomich,Romeo Beaulieu,Daphne Enis,Keven Mohammadzadeh,Caren Ownsby,Urban Trollope,Fianna Beaulieu," +
            "Yance Orias,Celie Bott,Andy Koten,Roxane Mohammadzadeh,Zebedee Zobel,Rebekkah Allen-willoughby," +
            "Johnathan Krogh,Dalia Trollope,Byrom Geffroy,Cris Carrington-crouch,Dwayne Hagans,Heidi Orias," +
            "Artemus Moretta,Evangelin Touserkani,Xymenes Tager,Freddy Koten,Bucky Zavanella,May Aykroyd," +
            "Linc Romeo,Melodie Zobel,Demetris Person,Noell Mcconway,Mead Young,Bertine Krogh,Nial Rilke," +
            "Daveta Graffo,Roy Sens,Mae Geffroy,Justino Krewski,Betty Facey,Mendel Birne,Mandi Hagans," +
            "Mohammed Schiferl,Kassey Vandegrift,Ogdon Burch,Netta Moretta,Buddie Godwin,Evita Moro," +
            "Jeremie Cancelliere";

    private static List<String> nicknames = new ArrayList<>();
    private static List<String> prefixes;
    private static List<String> suffixes;

    private static long baseBirthday = 725817600000l;

    static {
        String[] arrays = nickname.split(",");
        Arrays.stream(arrays).forEach(e -> nicknames.addAll(Arrays.asList(e.split(" "))));
        prefixes = Arrays.asList(prefix.split(","));
        suffixes = Arrays.asList(suffix.split(","));
    }

    public static Long randomId() {
        return org.apache.commons.lang3.RandomUtils.nextLong(100000, 999999);
    }

    public static User randomUser() {
        String password = randomPassword();
        String username = randomUsername();
        String nickname = randomNickname();
        Date registerAt = randomRegisterTime();
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setPassword(password);
        user.setRegisterAt(registerAt);

        UserDetails userDetails = new UserDetails(user);
        userDetails.setAreaId((long) random.nextInt(100));
        userDetails.setBirthday(randomBirthday());
        user.setUserDetails(userDetails);

        UserSettings userSettings = new UserSettings(user);
        userSettings.setNotificationEnabled(Math.random() > 0.3);
        user.setUserSettings(userSettings);

        return user;
    }

    private static String randomPassword() {
        return passwordEncoder.encode("123456");
    }

    private static String randomNickname() {
        int size = nicknames.size();
        String name = nicknames.get(random.nextInt(size));
        if (Math.random() < 0.8) {
            name = name + " " + nicknames.get(random.nextInt(size));
        }
        return name;
    }

    private static String randomUsername() {
        int first = random.nextInt(prefixes.size());
        int second = random.nextInt(suffixes.size());
        return prefixes.get(first) + suffixes.get(second);
    }

    private static Date randomRegisterTime() {
        long interval = random.nextInt((int) TimeUnit.DAYS.toMillis(1));
        long time = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(random.nextInt(452)) + interval;
        return new Date(time);
    }

    private static Date randomBirthday() {
        long time = baseBirthday + (TimeUnit.DAYS.toMillis(503) * random());
        return new Date(time);
    }

    private static int random() {
        return Math.random() > 0.5 ? -1 : 1;
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(randomBirthday()));
    }

}
