class clasePrueba {
    private int entero3 = 15;
    private String cadena3 = "Cadena 3 ñ";
    private char caracter1 = 'h';

    public static void main(String[] args) {
        metodo1("Tremenda cadena");
        metodo2(5);
        // metodo3("Cadena");
    }

    public void metodo1(String variable1) {
        int entero1 = 10;
        double decimal1 = 15.5;
        String cadena1 = "Esto es una cadena";
        boolean boleano = true;
        if (boleano) {
            entero1 = 30;
        } else {
            cadena1 = "Cadena uno cambiada";
        }
    }

    public void metodo2(int entero2) {
        switch (entero2) {
            case 1:
                Object objeto = new Object();
                break;
            case 15:
                Object objeto2 = new Object();
                break;
            default:
                Object objeto3 = new Object();
                break;
        }
    }

    public void metodo3(String cadena2) {
        int entero3 = 1;
        while (entero3 < 15) {
            entero3++;
            if (entero3 >= 14) {
                /* este es el primer comentario */
                metodo2(entero3);
            } else if (entero3 <= 2) {
                // Este es el segundo comentario
                metodo1("Esto es una cadena");
            } else {
                metodo2(entero3);
            }
            if (entero3 != 15) {
                // Este es el tercer comentario
                entero3--;
            }
        }
    }
}
