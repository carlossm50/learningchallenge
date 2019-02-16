package com.vladan.dragdropimages01;

public class controller {
    public int[] IDItems = new int[10];
    public int[] IDItems_bg = new int[10];
    public int[] IDSounds = new int[10];
    public controller(int i){

        switch (i) {
            case 1: {
                llenafrutas();
                break;
            }
            case 2: {
                llenanumeros();
                break;
            }
            case 3: {
                llenafiguras();
                break;
            }
            default:
                break;
        }
    }
    public void llenafrutas(){
        IDItems[0] = R.drawable.apple;
        IDItems[1] = R.drawable.bananas;
        IDItems[2] = R.drawable.carrot;
        IDItems[3] = R.drawable.cherries;
        IDItems[4] = R.drawable.grapes;
        IDItems[5] = R.drawable.lemon;
        IDItems[6] = R.drawable.pear;
        IDItems[7] = R.drawable.strawberry;
        IDItems[8] = R.drawable.tomato;
        IDItems[9] = R.drawable.watermelon;

        IDItems_bg[0] = R.drawable.apple_bg;
        IDItems_bg[1] = R.drawable.bananas_bg;
        IDItems_bg[2] = R.drawable.carrot_bg;
        IDItems_bg[3] = R.drawable.cherries_bg;
        IDItems_bg[4] = R.drawable.grapes_bg;
        IDItems_bg[5] = R.drawable.lemon_bg;
        IDItems_bg[6] = R.drawable.pear_bg;
        IDItems_bg[7] = R.drawable.strawberry_bg;
        IDItems_bg[8] = R.drawable.tomato_bg;
        IDItems_bg[9] = R.drawable.watermelon_bg;
        
        IDSounds[0] = R.string.manzana;
        IDSounds[1] = R.string.platano;
        IDSounds[2] = R.string.zanahoria;
        IDSounds[3] = R.string.cereza;
        IDSounds[4] = R.string.uva;
        IDSounds[5] = R.string.limon;
        IDSounds[6] = R.string.pera;
        IDSounds[7] = R.string.fresa;
        IDSounds[8] = R.string.tomate;
        IDSounds[9] = R.string.sandia;
    }
    public void llenanumeros(){
        IDItems[0] = R.drawable.number1;
        IDItems[1] = R.drawable.number2;
        IDItems[2] = R.drawable.number3;
        IDItems[3] = R.drawable.number4;
        IDItems[4] = R.drawable.number5;
        IDItems[5] = R.drawable.number6;
        IDItems[6] = R.drawable.number7;
        IDItems[7] = R.drawable.number8;
        IDItems[8] = R.drawable.number9;
        IDItems[9] = R.drawable.number10;

        IDItems_bg[0] = R.drawable.number1_bg;
        IDItems_bg[1] = R.drawable.number2_bg;
        IDItems_bg[2] = R.drawable.number3_bg;
        IDItems_bg[3] = R.drawable.number4_bg;
        IDItems_bg[4] = R.drawable.number5_bg;
        IDItems_bg[5] = R.drawable.number6_bg;
        IDItems_bg[6] = R.drawable.number7_bg;
        IDItems_bg[7] = R.drawable.number8_bg;
        IDItems_bg[8] = R.drawable.number9_bg;
        IDItems_bg[9] = R.drawable.number10_bg;

        /*IDSounds[0] = R.string.manzana;
        IDSounds[1] = R.string.platano;
        IDSounds[2] = R.string.zanahoria;
        IDSounds[3] = R.string.cereza;
        IDSounds[4] = R.string.uva;
        IDSounds[5] = R.string.limon;
        IDSounds[6] = R.string.pera;
        IDSounds[7] = R.string.fresa;
        IDSounds[8] = R.string.tomate;
        IDSounds[9] = R.string.sandia;*/
    }
    public void llenafiguras(){
        IDItems[0] = R.drawable.circle;
        IDItems[1] = R.drawable.diamond;
        IDItems[2] = R.drawable.hexagon;
        IDItems[3] = R.drawable.pentagon;
        IDItems[4] = R.drawable.rectangle;
        IDItems[5] = R.drawable.oval;
        IDItems[6] = R.drawable.square;
        IDItems[7] = R.drawable.triangle;
        IDItems[8] = R.drawable.star;
        IDItems[9] = R.drawable.heart;

        IDItems_bg[0] = R.drawable.circle_bg;
        IDItems_bg[1] = R.drawable.diamond_bg;
        IDItems_bg[2] = R.drawable.hexagon_bg;
        IDItems_bg[3] = R.drawable.pentagon_bg;
        IDItems_bg[4] = R.drawable.rectangle_bg;
        IDItems_bg[5] = R.drawable.oval_bg;
        IDItems_bg[6] = R.drawable.square_bg;
        IDItems_bg[7] = R.drawable.triangle_bg;
        IDItems_bg[8] = R.drawable.star_bg;
        IDItems_bg[9] = R.drawable.heart_bg;

        /*IDSounds[0] = R.string.manzana;
        IDSounds[1] = R.string.platano;
        IDSounds[2] = R.string.zanahoria;
        IDSounds[3] = R.string.cereza;
        IDSounds[4] = R.string.uva;
        IDSounds[5] = R.string.limon;
        IDSounds[6] = R.string.pera;
        IDSounds[7] = R.string.fresa;
        IDSounds[8] = R.string.tomate;
        IDSounds[9] = R.string.sandia;*/
    }
}
