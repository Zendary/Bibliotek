public class Menu {
    static void menu() {
        String svar;

        svar = Input.getString("Hvad ønsker du? \n"+
                "Opret Låner (1) / Udskriv Låner (2) / Slet Låner (3) \n" +
                "Opret By (4) / Udskriv Byer (5)\n" +
                "Opret Bog (6) / Udskriv bøger (7) / Slet bog (8) \n" +
                "Udlån bog (9)");

        switch (svar){
            case "1":
                Lånere.opretLåner();
                break;
            case "2":
                Lånere.udskrivLånere();
                break;
            case "3":
                Lånere.sletLåner(); //kan ikke slette hvis der er udlånt bog til dem
                break;
            case "4":
                By.opretBy(); //virker ikke
                break;
            case "5":
                By.udskrivByer();
                break;
            case "6":
                Bøger.opretBog();
                break;
            case "7":
                Bøger.udskrivBøger();
                break;
            case "8":
                Bøger.sletBog(); //kan ikke slette bog der er udlånt
                break;
            case "9":
                Udlån.udlånBog();
                break;
            default:
                System.out.println("ugyldigt valg, prøv igen");
                break;
        }
        menu();
    }
}
